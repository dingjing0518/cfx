package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bPlantDaoEx;
import cn.cf.dao.B2bProductDaoEx;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dao.LgCarDao;
import cn.cf.dao.LgCompanyDao;
import cn.cf.dao.LgDeliveryOrderDaoEx;
import cn.cf.dao.LgDeliveryOrderGoodsDao;
import cn.cf.dao.LgDriverDao;
import cn.cf.dao.LgTrackDetailDao;
import cn.cf.dao.LogisticsErpExtDao;
import cn.cf.dao.LogisticsErpMemberExtDao;
import cn.cf.dao.LogisticsErpStepInfoExtDao;
import cn.cf.dao.SysRegionsDao;
import cn.cf.dao.SysRegionsDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.LgCarDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgDeliveryOrderGoodsDto;
import cn.cf.dto.LgDriverDto;
import cn.cf.dto.LogisticsErpDto;
import cn.cf.dto.LogisticsErpDtoEx;
import cn.cf.dto.LogisticsErpMemberDto;
import cn.cf.dto.LogisticsErpStepInfoDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.entity.GoodsDeliverySync;
import cn.cf.entity.LogisticsDeliverySync;
import cn.cf.entity.LogisticsInfoSyncRule;
import cn.cf.entity.LogisticsSyncRule;
import cn.cf.entity.LogisticsSyncRuleEmployee;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bPlant;
import cn.cf.model.B2bProduct;
import cn.cf.model.LgCar;
import cn.cf.model.LgCompany;
import cn.cf.model.LgDeliveryOrder;
import cn.cf.model.LgDriver;
import cn.cf.model.LgTrackDetail;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.LogisticsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
public class LogisticsServiceImpl implements LogisticsService {
	private final static Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	@Autowired
	private LogisticsErpExtDao logisticsErpExtDao;

	@Autowired
	private LogisticsErpStepInfoExtDao logisticsErpStepInfoExtDao;

	@Autowired
	private LogisticsErpMemberExtDao logisticsErpMemberExtDao;

	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bPlantDaoEx b2bPlantDaoEx;

	@Autowired
	private B2bProductDaoEx b2bProductDaoEx;
	
	@Autowired
	private B2bStoreDaoEx b2bStoreDaoEx;

	@Autowired
	private B2bGoodsService b2bGoodsService;

	@Autowired
	private LgDeliveryOrderDaoEx lgDeliveryOrderDao;
	
	@Autowired
	private LgDeliveryOrderGoodsDao lgDeliveryOrderGoodsDao;

	@Autowired
	private LgCompanyDao lgCompanyDao;

	@Autowired
	private LgCarDao lgCarDao;

	@Autowired
	private LgTrackDetailDao lgTrackDetailDao;
	
	@Autowired
	private LgDriverDao  lgDriverDao;
	
	@Autowired
	private B2bCompanyDaoEx b2bCompanyDaoEx;
	
	@Autowired
	private SysRegionsDao regionsDao;
	
	@Autowired
	private SysRegionsDaoEx sysRegionsDaoEx;
	
	@Override
	@Transactional
	public void updateAllLogistics(String storePk, JSONArray array) throws Exception {
		if (null != array && array.size() > 0) {
			// 数据拼装
			logger.error("updateAllLogistics-------------size:"+array.size());
			List<LogisticsErpDtoEx> list = getErpLogisticsDto(storePk, array);
			
			//先删除数据
			List<LogisticsErpDto> erpList = logisticsErpExtDao.getListByStorePk(storePk);
			if(erpList != null && erpList.size() > 0){
				for (LogisticsErpDto logisticsErpDto : erpList) {
					logisticsErpStepInfoExtDao.deleteByLogisticsPk(logisticsErpDto.getPk());
					logisticsErpMemberExtDao.deleteByLogisticsPk(logisticsErpDto.getPk());
					Query query = Query.query(Criteria.where("logisticsPk").is(logisticsErpDto.getPk()));
					mongoTemplate.remove(query, LogisticsErpStepInfoDto.class);
				}
			}
			Query query = Query.query(Criteria.where("storePk").is(storePk));
			mongoTemplate.remove(query, LogisticsErpDtoEx.class);
			logisticsErpExtDao.deleteByStorePk(storePk);
			// 更新物流主表
			for (LogisticsErpDtoEx dto : list) {
				LogisticsErpDto ldto = logisticsErpExtDao.getByPk(dto.getPk());
				if (null != ldto) {
					logisticsErpExtDao.updateLogisticsErp(dto);
				} else {
					logisticsErpExtDao.insertLogisticsErp(dto);
				}
				// 更新关联关系表
				if (null != dto.getLogisticsErpMembers() && dto.getLogisticsErpMembers().size() > 0) {
					for (LogisticsErpMemberDto memberRf : dto.getLogisticsErpMembers()) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("employeeNumber", memberRf.getEmployeeNumber());
						map.put("logisticsPk", dto.getPk());
						List<LogisticsErpMemberDto> memberRfList = logisticsErpMemberExtDao.searchGrid(map);
						if (memberRfList != null && memberRfList.size() > 0) {
							LogisticsErpMemberDto memberRfDto = memberRfList.get(0);
							memberRf.setPk(memberRfDto.getPk());
							logisticsErpMemberExtDao.updateLgMemberRf(memberRf);
						} else {
							memberRf.setPk(KeyUtils.getUUID());
							logisticsErpMemberExtDao.insertLgMemberRf(memberRf);
						}
					}
				}
				// 更新阶梯价
				if (null != dto.getStepInfos() && dto.getStepInfos().size() > 0) {
					for (LogisticsErpStepInfoDto info : dto.getStepInfos()) {
						
						LogisticsErpStepInfoDto linfo = logisticsErpStepInfoExtDao.getByPk(info.getPk());
						if (null != linfo) {
							logisticsErpStepInfoExtDao.updateLgErpStepInfo(info);
						} else {
							logisticsErpStepInfoExtDao.insertLgErpStepInfo(info);
						}
						//更新mongo
						mongoTemplate.save(info);
					}
				}
				//更新LogisticsErpDtoExmongo
				//把不需要保存到mongo的数据设置null
				dto.setStepInfos(null);
				//dto.setLogisticsErpMembers(null);
				mongoTemplate.save(dto);
				
			}
		} else {
			logger.error("updateAllLogistics-------------size:"+0);
		}
	}

	@SuppressWarnings("rawtypes")
	private List<LogisticsErpDtoEx> getErpLogisticsDto(String storePk, JSONArray array) {

		List<LogisticsErpDtoEx> logisticsList = new ArrayList<LogisticsErpDtoEx>();
		for (int i = 0; i < array.size(); i++) {
			// json数据转对象
			Map<String, Class> map = new HashMap<String, Class>();
			map.put("priceItems", LogisticsInfoSyncRule.class);
			map.put("manageItems", LogisticsSyncRuleEmployee.class);
			LogisticsSyncRule logisticsSyncRule = JsonUtils.toJSONMapBean(array.getString(i), LogisticsSyncRule.class,map);
			LogisticsErpDtoEx dto = new LogisticsErpDtoEx();
			dto.setPk(logisticsSyncRule.getId());
			dto.setStatus(logisticsSyncRule.getStatus() == null?1:logisticsSyncRule.getStatus());
			dto.setPlantName(logisticsSyncRule.getPlantName()== null?"":logisticsSyncRule.getPlantName());
			dto.setProvinceName(logisticsSyncRule.getProvinceName()== null?"":logisticsSyncRule.getProvinceName());
			dto.setCityName(logisticsSyncRule.getCityName()==null?"":logisticsSyncRule.getCityName());
			dto.setAreaName(logisticsSyncRule.getAreaName()==null?"":logisticsSyncRule.getAreaName());
			dto.setTownName(logisticsSyncRule.getTownName()==null?"":logisticsSyncRule.getTownName());
			dto.setStorePk(storePk == null?"":storePk);
			dto.setLowPrice(logisticsSyncRule.getLowPrice() == null?0d:Double.valueOf(logisticsSyncRule.getLowPrice()));
			dto.setName(logisticsSyncRule.getName() == null?"":logisticsSyncRule.getName());
			dto.setLgCompanyName(logisticsSyncRule.getCarrierName() == null?"":logisticsSyncRule.getCarrierName());
			dto.setIsDelete(1);
			dto.setMemo("");
			LgCompanyDto lgCompanyDto = lgCompanyDao.getByName(logisticsSyncRule.getCarrierName() == null?"":logisticsSyncRule.getCarrierName());
			if(lgCompanyDto != null){
				dto.setLgCompanyPk(lgCompanyDto.getPk());	
			}else{
				dto.setLgCompanyPk("");	
			}
			// 根据传入的名称获取对应的PK
			dto.setProvince("");
			dto.setCity("");
			dto.setArea("");
			dto.setTown("");
			dto.setPlantPk("");
			setIdByName(dto);
			
			// 设置阶梯价格
			if (logisticsSyncRule.getPriceItems() != null && logisticsSyncRule.getPriceItems().size() > 0) {
				setLogisticsPrice(logisticsSyncRule, dto);
			}
			// 设置关联关系
			if (logisticsSyncRule.getManageItems() != null && logisticsSyncRule.getManageItems().size() > 0) {
				setLogisticsMemberRf(logisticsSyncRule, dto);
			}else{
				List<LogisticsErpMemberDto> loMeRfList = new ArrayList<LogisticsErpMemberDto>();
				LogisticsErpMemberDto loMeRf = new LogisticsErpMemberDto();
				loMeRf.setPk(KeyUtils.getUUID());
				loMeRf.setEmployeeNumber("-1");
				loMeRfList.add(loMeRf);
				dto.setLogisticsErpMembers(loMeRfList);
			}
			logisticsList.add(dto);
		}
		return logisticsList;
	}

	private void setLogisticsMemberRf(LogisticsSyncRule logisticsSyncRule, LogisticsErpDtoEx dto) {
		List<LogisticsErpMemberDto> loMeRfList = new ArrayList<LogisticsErpMemberDto>();
		for (LogisticsSyncRuleEmployee manageItems : logisticsSyncRule.getManageItems()) {
			LogisticsErpMemberDto loMeRf = new LogisticsErpMemberDto();
			loMeRf.setPk(KeyUtils.getUUID());
			B2bMemberDto memberDto = b2bMemberDaoEx.getByEmployeeNumber(manageItems.getEmployeeNumber());
			if (memberDto != null) {
				loMeRf.setMemberPk(memberDto.getPk());
				loMeRf.setEmployeeName(manageItems.getEmployeeName() == null?"":manageItems.getEmployeeName());
				loMeRf.setLogisticsPk(logisticsSyncRule.getId() == null?"":logisticsSyncRule.getId());
				loMeRf.setEmployeeNumber(manageItems.getEmployeeNumber());
			}else{
				loMeRf.setEmployeeNumber("-1");
				loMeRf.setMemberPk("");	
			}
			loMeRfList.add(loMeRf);
			
		}
		dto.setLogisticsErpMembers(loMeRfList);
	}
	
	/*private void setLogisticsMemberRfToMongo(LogisticsSyncRule logisticsSyncRule, LogisticsErpDtoEx dto) {
		List<LogisticsErpMemberDto> loMeRfTomongoList = new ArrayList<LogisticsErpMemberDto>();
		for (LogisticsSyncRuleEmployee manageItems : logisticsSyncRule.getManageItems()) {
			LogisticsErpMemberDto loMeRf = new LogisticsErpMemberDto();
			loMeRf.setPk(KeyUtils.getUUID());
			B2bMemberDto memberDto = b2bMemberDaoEx.getByEmployeeNumber(manageItems.getEmployeeNumber());
			loMeRf.setEmployeeName(manageItems.getEmployeeName());
			loMeRf.setEmployeeNumber(manageItems.getEmployeeNumber());
			loMeRf.setLogisticsPk(logisticsSyncRule.getId());
			if (memberDto == null) {
				loMeRf.setEmployeeName(null);
				loMeRf.setEmployeeNumber("-1");
				loMeRf.setLogisticsPk(null);
			}else{
				loMeRf.setMemberPk(memberDto.getPk());	
			}
			loMeRfTomongoList.add(loMeRf);
			
		}
		dto.setLgErpMembersToMongo(loMeRfTomongoList);
	}*/

	private void setLogisticsPrice(LogisticsSyncRule logisticsSyncRule, LogisticsErpDtoEx dto) {
		List<LogisticsErpStepInfoDto> infoDtoList = new ArrayList<LogisticsErpStepInfoDto>();
		for (LogisticsInfoSyncRule rule : logisticsSyncRule.getPriceItems()) {
			LogisticsErpStepInfoDto stepInfoDto = new LogisticsErpStepInfoDto();
			stepInfoDto.setPk(rule.getId());
			stepInfoDto.setIsDelete(1);
			stepInfoDto.setPrice(rule.getPrice() == null?0d:rule.getPrice());
			stepInfoDto.setEndWeight(rule.getEndWeight()==null?0d:Double.parseDouble(rule.getEndWeight().toString()));
			stepInfoDto.setStartWeight(rule.getStartWeight()==null?0d:Double.parseDouble(rule.getStartWeight().toString()));
			stepInfoDto.setLogisticsPk(logisticsSyncRule.getId()==null?"":logisticsSyncRule.getId());
			stepInfoDto.setProductName(rule.getProductName()==null?"":rule.getProductName());
			stepInfoDto.setPackNumber(rule.getPackNumber() == null?"":rule.getPackNumber());
			stepInfoDto.setProductPk("");
			if(rule.getIsStore() == null){
				stepInfoDto.setIsStore(2);//默认为否
			}else{
				stepInfoDto.setIsStore(rule.getIsStore());
			}
			if (null != stepInfoDto.getProductName() && !"".equals(stepInfoDto.getProductName())) {
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("name", stepInfoDto.getProductName());
				searchMap.put("isDelete", 1);
				List<B2bProductDto> list = b2bProductDaoEx.searchGrid(searchMap);
				if (null != list && list.size() > 0) {
					stepInfoDto.setProductPk(list.get(0).getPk());
				}else{
					//如果没有添加一个
					insertProduct(stepInfoDto);
				}
			}
			infoDtoList.add(stepInfoDto);
		}
		dto.setStepInfos(infoDtoList);
	}

	private void insertProduct(LogisticsErpStepInfoDto stepInfoDto) {
		B2bProduct product = new B2bProduct();
		String pk = KeyUtils.getUUID();
		product.setInsertTime(new Date());
		product.setIsVisable(1);
		product.setIsDelete(1);
		product.setName(stepInfoDto.getProductName());
		product.setPk(pk);
		b2bProductDaoEx.insert(product);
		stepInfoDto.setProductPk(pk);
	}
	
	private String getRegionsPk(Map<String,Object> map){
		String pk = "";
		map.put("nameOne", map.get("name")+"省");
		map.put("nameTwo", map.get("name")+"市");
		map.put("nameThree", map.get("name")+"区");
		map.put("nameFour", map.get("name")+"县");
		map.put("nameFive", map.get("name")+"镇");
		SysRegionsDto dto = sysRegionsDaoEx.getRegionByNames(map);
		if(null != dto){
			pk = dto.getPk();
		}
		return pk;
	}

	private void setIdByName(LogisticsErpDto dto) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("parentPk", "-1");
		searchMap.put("name", dto.getProvinceName());
		String provincePk = getRegionsPk(searchMap);
		dto.setProvince(provincePk);
		// 按照地区名称查询对应的地区PK
		if (null != provincePk && !"".equals(provincePk) && null != dto.getCityName() && !"".equals(dto.getCityName())) {
			searchMap.put("parentPk", provincePk);
			searchMap.put("name", dto.getCityName());
			String cityPk = getRegionsPk(searchMap);
			dto.setCity(cityPk);
			if (null != cityPk && !"".equals(cityPk) && null != dto.getAreaName() && !"".equals(dto.getAreaName())) {
				searchMap.put("parentPk", cityPk);
				searchMap.put("name", dto.getAreaName());
				String areaPk = getRegionsPk(searchMap);
				dto.setArea(areaPk);
				if (null != areaPk && !"".equals(areaPk) && null !=dto.getTownName() && !"".equals(dto.getTownName())) {
					searchMap.put("parentPk", areaPk);
					searchMap.put("name", dto.getTownName());
					String townPk = getRegionsPk(searchMap);
					dto.setTown(townPk);
				}
			}
		}
		if (null != dto.getPlantName() && !"".equals(dto.getPlantName())) {
			searchMap.put("name", dto.getPlantName());
			searchMap.put("isDelete", 1);
			searchMap.put("storePk", dto.getStorePk());
			List<B2bPlantDto> list = b2bPlantDaoEx.searchGrid(searchMap);
			if (null != list && list.size() > 0) {
				dto.setPlantPk(list.get(0).getPk());
			}else{
				//添加一个
				insertPlant( dto);
			}
		}
	}
	@Override
	public String syncDelivery(LogisticsDeliverySync logisticsDeliverySync) {
		String result = RestCode.CODE_0000.toJson();
			if (logisticsDeliverySync != null && !logisticsDeliverySync.equals("")) {
				logisticsDeliverySync.setPk(KeyUtils.getUUID());
				List<LgDeliveryOrderGoodsDto> deliveryOrderGoodsList = getLogisticsDeliverySync(logisticsDeliverySync);
					logger.info("deliveryOrderGoodsList-------"+JsonUtils.convertToString(deliveryOrderGoodsList));
					result = insertDeliveryOrder(deliveryOrderGoodsList, logisticsDeliverySync);
			} else {
				logger.error("syncDelivery:is null or b2bCorrespondenceInfoEx.getCorrespondenceInfo().size() <0");
				result = RestCode.CODE_I002.toJson();
			}
		return result;
	}

	private String insertDeliveryOrder(List<LgDeliveryOrderGoodsDto> deliveryOrderGoodsList,
			LogisticsDeliverySync logisticsDeliverySync) {
		String logisticPk = "";
		RestCode code = RestCode.CODE_0000;
			// 校验是否已同步
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderPk", logisticsDeliverySync.getOrderPk());
			map.put("deliveryNumber", logisticsDeliverySync.getDeliveryNumber());
			LgDeliveryOrderDto dto = lgDeliveryOrderDao.getByMap(map);
			LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
			lgDeliveryOrder.setPk(logisticsDeliverySync.getPk());
			lgDeliveryOrder.setOrderPk(logisticsDeliverySync.getOrderPk());
			lgDeliveryOrder.setDeliveryNumber(logisticsDeliverySync.getDeliveryNumber());
			lgDeliveryOrder.setOriginalTotalFreight(logisticsDeliverySync.getOriginalTotalFreight());
			lgDeliveryOrder.setPresentTotalFreight(logisticsDeliverySync.getOriginalTotalFreight());
			if (logisticsDeliverySync.getLogisticsCompanyName() != null
					&& !logisticsDeliverySync.getLogisticsCompanyName().equals("")) {
				lgDeliveryOrder.setLogisticsCompanyName(logisticsDeliverySync.getLogisticsCompanyName());
				// 承运商是否存在，不存在新增
				LgCompanyDto lgCompanyDto = lgCompanyDao.getByName(logisticsDeliverySync.getLogisticsCompanyName());
				if (lgCompanyDto == null || lgCompanyDto.equals("")) {
					logisticPk = createLogisticsCompany(logisticsDeliverySync.getLogisticsCompanyName());
				} else {
					logisticPk = lgCompanyDto.getPk();
				}
				lgDeliveryOrder.setLogisticsCompanyPk(logisticPk);
				lgDeliveryOrder.setIsMatched(1);
			}else{
				lgDeliveryOrder.setIsMatched(0);
			}
			lgDeliveryOrder.setToCompanyName(logisticsDeliverySync.getToCompanyName());
			lgDeliveryOrder.setPurchaserName(logisticsDeliverySync.getToCompanyName());
			map.put("name", logisticsDeliverySync.getToCompanyName());
			List<B2bCompanyDto> companyList = b2bCompanyDaoEx.getByCompanyName(map);
			if(null != companyList && companyList.size()>0){
				lgDeliveryOrder.setToCompanyPk(companyList.get(0).getPk());
				lgDeliveryOrder.setPurchaserPk(companyList.get(0).getPk());
				//B2bInvoiceDto invoice = b2bInvoiceDaoEx.getByCompanyPk(companyList.get(0).getPk());
				B2bCompanyDto b2bCompanyDto = b2bCompanyDaoEx.getByPk(companyList.get(0).getPk());
				if(null != b2bCompanyDto){
					lgDeliveryOrder.setInvoiceName(b2bCompanyDto.getName());
					lgDeliveryOrder.setInvoiceTaxidNumber(b2bCompanyDto.getOrganizationCode());
					lgDeliveryOrder.setInvoiceRegPhone(b2bCompanyDto.getContactsTel());
					lgDeliveryOrder.setInvoiceBankAccount(b2bCompanyDto.getBankAccount());
					lgDeliveryOrder.setInvoiceBankName(b2bCompanyDto.getBankName());
					lgDeliveryOrder.setInvoiceProvinceName(b2bCompanyDto.getProvinceName());
					lgDeliveryOrder.setInvoiceCityName(b2bCompanyDto.getCityName());
					lgDeliveryOrder.setInvoiceAreaName(b2bCompanyDto.getAreaName());
					lgDeliveryOrder.setInvoiceRegAddress(b2bCompanyDto.getRegAddress());
				}
			}
			SysRegionsDto region = null;
			if(null !=logisticsDeliverySync.getToProvinceName() && !"".equals(logisticsDeliverySync.getToProvinceName())){
				lgDeliveryOrder.setToProvinceName(logisticsDeliverySync.getToProvinceName());
				region = getRegionByName(logisticsDeliverySync.getToProvinceName());
				lgDeliveryOrder.setToProvincePk(null==region?"":region.getPk());
			}
			if(null !=logisticsDeliverySync.getToCityName() && !"".equals(logisticsDeliverySync.getToCityName())){
				lgDeliveryOrder.setToCityName(logisticsDeliverySync.getToCityName());
				region = getRegionByName(logisticsDeliverySync.getToCityName());
				lgDeliveryOrder.setToCityPk(null==region?"":region.getPk());
			}
			if(null !=logisticsDeliverySync.getToAreaName() && !"".equals(logisticsDeliverySync.getToAreaName())){
				lgDeliveryOrder.setToAreaName(logisticsDeliverySync.getToAreaName());
				region = getRegionByName(logisticsDeliverySync.getToAreaName());
				lgDeliveryOrder.setToAreaPk(null==region?"":region.getPk());
			}
			if(null !=logisticsDeliverySync.getToTownName() && !"".equals(logisticsDeliverySync.getToTownName())){
				lgDeliveryOrder.setToTownName(logisticsDeliverySync.getToTownName());
				region = getRegionByName(logisticsDeliverySync.getToTownName());
				lgDeliveryOrder.setToTownPk(null==region?"":region.getPk());
			}
			lgDeliveryOrder.setToAddress(logisticsDeliverySync.getToAddress());
			lgDeliveryOrder.setFromCompanyName(logisticsDeliverySync.getFromCompanyName());
			lgDeliveryOrder.setFromProvinceName(logisticsDeliverySync.getFromProvinceName());
			lgDeliveryOrder.setFromCityName(logisticsDeliverySync.getFromCityName());
			lgDeliveryOrder.setFromAreaName(logisticsDeliverySync.getFromAreaName());
			lgDeliveryOrder.setFromAddress(logisticsDeliverySync.getFromAddress());
			lgDeliveryOrder.setFromTownName(logisticsDeliverySync.getFromTownName());
			lgDeliveryOrder.setRemark(logisticsDeliverySync.getRemark());
			if(null != logisticPk && !"".equals(logisticPk)){
				driverAndCar(logisticsDeliverySync, logisticPk, lgDeliveryOrder);
			}
			// 订单状态和订单轨迹
			addTrack(logisticsDeliverySync);
			lgDeliveryOrder.setOrderStatus(logisticsDeliverySync.getOrderStatus());
			lgDeliveryOrder.setInsertTime(new Date());
			lgDeliveryOrder.setUpdateTime(new Date());
			lgDeliveryOrder.setSource(3);
			lgDeliveryOrder.setSupplierInvoiceStatus(0);
			lgDeliveryOrder.setMemberInvoiceStatus(1);
			lgDeliveryOrder.setIsAbnormal(1);
			lgDeliveryOrder.setOrderTime(new Date());
			lgDeliveryOrder.setParentPk("-1");
			if (dto == null) {
				logger.info("LgDeliveryOrderDto is null--------");
				lgDeliveryOrderDao.insert(lgDeliveryOrder);
				lgDeliveryOrderDao.BatchDeliveryGoods(deliveryOrderGoodsList);
			}else{
				lgDeliveryOrder.setPk(dto.getPk());
				lgDeliveryOrderDao.updatelgDeliveryOrder(lgDeliveryOrder);
				for (LgDeliveryOrderGoodsDto ldogDto : deliveryOrderGoodsList) {
					LgDeliveryOrderGoodsDto ldog = lgDeliveryOrderGoodsDao.getByPk(ldogDto.getPk());
					if(null == ldog){
						logger.info("LgDeliveryOrderGoodsDto is null--------");
						lgDeliveryOrderDao.insertDeliveryGoods(ldogDto);
					}else{
						lgDeliveryOrderDao.updateDeliveryGoods(ldogDto);
					}
				}
			}
		return code.toJson();
	}
	
	private SysRegionsDto getRegionByName(String name){
		SysRegionsDto region = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		List<SysRegionsDto> list = regionsDao.searchList(map);
		if(null != list && list.size()>0){
			region = list.get(0);
		}
		return region;
	}

	private void driverAndCar(LogisticsDeliverySync logisticsDeliverySync, String logisticPk,
			LgDeliveryOrder lgDeliveryOrder) {
		Map<String, Object> map;
		// 车辆是否存在，不存在新增
		lgDeliveryOrder.setCarPlate(logisticsDeliverySync.getCarPlate());
		map = new HashMap<String, Object>();
		map.put("plateNumber", logisticsDeliverySync.getCarPlate());
		map.put("companyPk", logisticPk);
		LgCarDto carDto = lgDeliveryOrderDao.getCarByMap(map);
		if (carDto == null || carDto.equals("")) {
			String carPK = createCar(logisticsDeliverySync, logisticPk);
			lgDeliveryOrder.setCarPk(carPK);
		} else {
			lgDeliveryOrder.setCarPk(carDto.getPk());
		}
		// 司机是否存在，不存在新增
		lgDeliveryOrder.setDriverContact(logisticsDeliverySync.getDriverContact());
		lgDeliveryOrder.setDriver(logisticsDeliverySync.getDriver());
		map = new HashMap<String, Object>();
		map.put("name", logisticsDeliverySync.getDriver());
		map.put("companyPk", logisticPk);
		LgDriverDto driverDto = lgDeliveryOrderDao.getDriverByMap(map);
		if (carDto == null || carDto.equals("")) {
			String driverPk = createDriver(logisticsDeliverySync, logisticPk);
			lgDeliveryOrder.setDriverPk(driverPk);
		} else {
			lgDeliveryOrder.setDriverPk(driverDto.getPk());
		}
	}

	private String createDriver(LogisticsDeliverySync logisticsDeliverySync, String logisticPk) {
		String pk = KeyUtils.getUUID();
		LgDriver model = new LgDriver();
		model.setPk(pk);
		model.setCompanyPk(logisticPk);
		model.setName(logisticsDeliverySync.getDriver());
		model.setMobile(logisticsDeliverySync.getDriverContact());
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		model.setIsDelete(1);
		lgDriverDao.insert(model);
		return pk;
	}

	private void addTrack(LogisticsDeliverySync logisticsDeliverySync) {
		if (logisticsDeliverySync.getOrderStatus() != null && !logisticsDeliverySync.getOrderStatus().equals("")) {
			Integer status = logisticsDeliverySync.getOrderStatus();
			LgTrackDetail lgTrackDetail = new LgTrackDetail();
			lgTrackDetail.setDeliveryPk(logisticsDeliverySync.getPk());
			lgTrackDetail.setPk(KeyUtils.getUUID());
				Date date = new Date();
				lgTrackDetail.setFinishedTime(date);
				lgTrackDetail.setInsertTime(date);
				lgTrackDetail.setUpdateTime(date);
			
			if (status == 1) {
				logisticsDeliverySync.setOrderStatus(5);
				lgTrackDetail.setStepDetail("订单提货中");
				lgTrackDetail.setOrderStatus(5);

			}
			if (status == 2) {
				logisticsDeliverySync.setOrderStatus(5);
				lgTrackDetail.setStepDetail("订单已扫描");
				lgTrackDetail.setOrderStatus(5);

			}
			if (status == 3) {
				logisticsDeliverySync.setOrderStatus(5);
				lgTrackDetail.setStepDetail("订单已进磅");
				lgTrackDetail.setOrderStatus(5);

			}
			if (status == 4) {
				logisticsDeliverySync.setOrderStatus(5);
				lgTrackDetail.setStepDetail("订单已装货");
				lgTrackDetail.setOrderStatus(5);

			}
			if (status == 5) {
				logisticsDeliverySync.setOrderStatus(3);
				lgTrackDetail.setStepDetail("订单已签收");
				lgTrackDetail.setOrderStatus(3);
			}
			lgTrackDetailDao.insert(lgTrackDetail);
		}

	}

	private String createCar(LogisticsDeliverySync logisticsDeliverySync, String logisticPk) {
		String pk = KeyUtils.getUUID();
		LgCar model = new LgCar();
		model.setPk(pk);
		model.setCompanyPk(logisticPk);
		model.setCarType(logisticsDeliverySync.getCarType());
		model.setPlateNumber(logisticsDeliverySync.getCarPlate());
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		model.setIsDeleted(1);
		lgCarDao.insert(model);
		return pk;
	}

	private String createLogisticsCompany(String logisticsCompanyName) {
		String pk = KeyUtils.getUUID();
		LgCompany model = new LgCompany();
		model.setPk(pk);
		model.setName(logisticsCompanyName);
		model.setIsDelete(1);
		model.setAuditStatus(1);
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		lgCompanyDao.insert(model);
		return pk;
	}

	/**
	 * 承运商品是否存在
	 * 
	 * @param logisticsDeliverySync
	 * @return
	 */
	private List<LgDeliveryOrderGoodsDto> getLogisticsDeliverySync(LogisticsDeliverySync logisticsDeliverySync) {
		List<LgDeliveryOrderGoodsDto> deliveryOrderGoodsList = new ArrayList<LgDeliveryOrderGoodsDto>();
		if (logisticsDeliverySync.getGoods() != null && logisticsDeliverySync.getGoods().size() > 0) {
			for (GoodsDeliverySync goods : logisticsDeliverySync.getGoods()) {
				B2bGoodsDtoEx b2bgoodsEx = new B2bGoodsDtoEx();
				CfGoods cfGoods = new CfGoods();
				cfGoods.setBatchNumber(goods.getBatchNumber());
				cfGoods.setPackNumber(goods.getPackNumber());
				cfGoods.setDistinctNumber(goods.getDistinctNumber());
				cfGoods.setGradeName(goods.getGradeName());
				b2bgoodsEx.setGoodsInfo(JSON.toJSONString(cfGoods));
				b2bgoodsEx.setStorePk(logisticsDeliverySync.getStorePk());
				LgDeliveryOrderGoodsDto deliveryOrderGoods = new LgDeliveryOrderGoodsDto();
				deliveryOrderGoods.setDeliveryPk(logisticsDeliverySync.getPk());
				deliveryOrderGoods.setPk(null!=goods.getChildOrderNumber()?goods.getChildOrderNumber():KeyUtils.getUUID());
				deliveryOrderGoods.setSpecName(goods.getSpecName());
				deliveryOrderGoods.setWeight(null!=goods.getWeight()?(goods.getWeight()/1000):0d);
				deliveryOrderGoods.setBoxes(goods.getBoxes());
				deliveryOrderGoods.setOrderNumber(logisticsDeliverySync.getOrderPk());
				if(null != logisticsDeliverySync.getOriginalTotalFreight() &&  logisticsDeliverySync.getOriginalTotalFreight() >0d && goods.getWeight()>0d){
					deliveryOrderGoods.setOriginalFreight(ArithUtil.div(logisticsDeliverySync.getOriginalTotalFreight(), deliveryOrderGoods.getWeight(), 2));
				}
				deliveryOrderGoods.setPresentFreight(deliveryOrderGoods.getOriginalFreight());
				B2bGoodsDto goodsDto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				if (null!=goodsDto) {
					B2bGoodsDtoMa goodsDtoMa = new B2bGoodsDtoMa();
					goodsDtoMa.UpdateMine(goodsDto);
					CfGoods tempCfGoods = goodsDtoMa.getCfGoods();
					deliveryOrderGoods.setGoodsPk(goodsDto.getPk());
					deliveryOrderGoods.setProductName(tempCfGoods.getProductName());
					deliveryOrderGoods.setProductPk(tempCfGoods.getProductPk());
					deliveryOrderGoods.setVarietiesName(tempCfGoods.getVarietiesName());
					deliveryOrderGoods.setSeedvarietyName(tempCfGoods.getSeedvarietyName());
					deliveryOrderGoods.setSeriesName(tempCfGoods.getSeriesName());
					deliveryOrderGoods.setGradeName(tempCfGoods.getGradeName());
					deliveryOrderGoods.setGradePk(tempCfGoods.getGradePk());
					deliveryOrderGoods.setBatchNumber(tempCfGoods.getBatchNumber());
					deliveryOrderGoods.setTareWeight(goodsDto.getTareWeight());
					deliveryOrderGoods.setUnit(tempCfGoods.getUnit());
				}
				deliveryOrderGoodsList.add(deliveryOrderGoods);
			}
		}
		return deliveryOrderGoodsList;
	}

	@Override
	public String updateDelivery(LogisticsDeliverySync logisticsDeliverySync) {
		String result = RestCode.CODE_0000.toJson();
		try {
			if (logisticsDeliverySync != null && !logisticsDeliverySync.equals("")) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("deliveryNumber", logisticsDeliverySync.getDeliveryNumber());
				List<LgDeliveryOrderDto> ldtoList= lgDeliveryOrderDao.searchGrid(map);
				if (null !=ldtoList && ldtoList.size()>0) {
					for (LgDeliveryOrderDto dto : ldtoList) {
						LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
						// 订单状态和订单轨迹
						LogisticsDeliverySync newSync = new LogisticsDeliverySync();
						newSync.setPk(dto.getPk());
						addTrack(newSync);
						lgDeliveryOrder.setPk(dto.getPk());
						lgDeliveryOrder.setOrderStatus(logisticsDeliverySync.getOrderStatus());
						lgDeliveryOrderDao.updatelgDeliveryOrder(lgDeliveryOrder);
						
					}
				}else{
					logger.info("ldtoList:is null==========================");
				}
			} else {
				logger.info("updateDelivery:is null==========================");
				result = RestCode.CODE_I002.toJson();
			}
		} catch (Exception e) {
			logger.error("updateDelivery",e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	private void insertPlant(LogisticsErpDto dto) {
		B2bPlant plant = new B2bPlant();
		String pk = KeyUtils.getUUID();
		plant.setPk(pk);
		plant.setProvince(dto.getProvince());
		plant.setProvinceName(dto.getProvinceName());
		plant.setCity(dto.getCity());
		plant.setCityName(dto.getCityName());
		plant.setArea(dto.getArea());
		plant.setAreaName(dto.getAreaName());
		plant.setTown(dto.getTown());
		plant.setTownName(dto.getTownName());
		plant.setInsertTime(new Date());
		B2bStoreDto storeDto = b2bStoreDaoEx.getByPk(dto.getStorePk());
		if(storeDto != null){
			plant.setStorePk(dto.getStorePk());
			plant.setStoreName(storeDto.getName());
		}
		plant.setName(dto.getPlantName());
		b2bPlantDaoEx.insert(plant);
		dto.setPlantPk(pk);
	}

	
}
