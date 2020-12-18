package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.common.Constant;
import cn.cf.constant.GoodsType;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bPlantDaoEx;
import cn.cf.dao.B2bProductDaoEx;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dao.LgCompanyDao;
import cn.cf.dao.LogisticsErpExtDao;
import cn.cf.dao.LogisticsErpMemberExtDao;
import cn.cf.dao.LogisticsErpStepInfoExtDao;
import cn.cf.dao.SysRegionsDaoEx;
import cn.cf.dto.B2bAddressDtoEx;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LogisticsErpDto;
import cn.cf.dto.LogisticsErpDtoEx;
import cn.cf.dto.LogisticsErpMemberDto;
import cn.cf.dto.LogisticsErpStepInfoDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.B2bBindDtoEx;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.B2bOrderGoodsUpdateOrderShiped;
import cn.cf.entity.B2bOrderGoodsUpdateOrderStatus;
import cn.cf.entity.B2bOrderGoodsUpdateWeight;
import cn.cf.entity.CfGoods;
import cn.cf.entity.ErpGoodsShow;
import cn.cf.entity.ErpGoodsSync;
import cn.cf.entity.LogisticsInfoSyncRule;
import cn.cf.entity.LogisticsSyncRule;
import cn.cf.entity.LogisticsSyncRuleEmployee;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bAddress;
import cn.cf.model.B2bPlant;
import cn.cf.model.B2bProduct;
import cn.cf.service.B2bAddressService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bOrderGoodsService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.HuaxianhuiService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class HuaxianhuiServiceImpl implements HuaxianhuiService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private B2bStoreService b2bStoreService;
	
	@Autowired
	private B2bGoodsService b2bGoodsService;
	
	@Autowired
	private B2bOrderGoodsService b2bOrderGoodsService;
	
	@Autowired
	private B2bOrderService b2bOrderService;
	
	@Autowired
	private B2bAddressService b2bAddressService;
	
	@Autowired
	private LogisticsErpExtDao logisticsErpExtDao;
	
	@Autowired
	private LogisticsErpStepInfoExtDao logisticsErpStepInfoExtDao;
	
	@Autowired
	private LogisticsErpMemberExtDao logisticsErpMemberExtDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private LgCompanyDao lgCompanyDao;
	
	@Autowired
	private B2bPlantDaoEx b2bPlantDaoEx;
	
	@Autowired
	private B2bProductDaoEx b2bProductDaoEx;
	
	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;
	
	@Autowired
	private SysRegionsDaoEx sysRegionsDaoEx;
	
	@Autowired
	private B2bStoreDaoEx b2bStoreDaoEx;
	
	
	/**
	 * 同步商品
	 */
	@Override
	public void syncGoods(String storePk, ErpGoodsSync erpGoods,Boolean openFlag,Boolean showFlag) {
		// 关店状态
		if (!openFlag) {
			// 所有商品全部下架 不显示 库存价格不变
			b2bGoodsService.updateDataZero(storePk, true, true, true, true);
		}
		if(null != erpGoods && null != erpGoods.getInsertGoodsList() && null != erpGoods.getUpdateGoodsList()){
			B2bStoreDto store = b2bStoreService.getByPk(storePk);
			b2bGoodsService.insertBatch(erpGoods.getInsertGoodsList(), erpGoods.getUpdateGoodsList(), store.getCompanyPk(), storePk, openFlag);
		}
		if(!showFlag){
			b2bGoodsService.updateShowByStorePk(storePk);
		}
	}

	
	/**
	 * 更新商品库存
	 */
	@Override
	public void syncGoodsStock(String storePk, List<B2bGoodsDtoEx> list,Boolean openFlag){
			// 关店状态
			if (!openFlag) {
				//所有商品库存全部清零
				b2bGoodsService.updateDataZero(storePk, true, false, false, false);
			}
			if (null != list && list.size() > 0) {
				b2bGoodsService.updateWeightBatch(list);
			}		
	}

	
	/**
	 * 同步商品价格
	 */
	@Override
	public void syncGoodsPrice(String storePk, List<B2bGoodsDtoEx> list,Boolean openFlag){
		// 关店状态
		if (!openFlag) {
			//所有商品价格全部清零
			b2bGoodsService.updateDataZero(storePk, false, true, false, false);
		}
		if (null != list && list.size() > 0) {
			b2bGoodsService.updatePriceBatch(list);
		}
	}

	
	/**
	 * 同步商品隐藏状态
	 */
	@Override
	public void syncGoodsShow(String storePk, JSONArray array){
		if (null != array && array.size() > 0) {
			List<ErpGoodsShow> list = new ArrayList<ErpGoodsShow>();
			for (int i = 0; i < array.size(); i++) {
				ErpGoodsShow  goodsShow= JSON.parseObject(array.getJSONObject(i).toString(), ErpGoodsShow.class);
				goodsShow.setStorePk(storePk);
				if (null != goodsShow) {
					list.add(goodsShow);
				}
			}
			if (null != list && list.size() > 0) {
				b2bGoodsService.updateIsNewProductBatch(list);
			}
		} else {
			logger.error("parseAllGoodsShow Size:" + 0);
		}
	}

	@Override
	public void updateDivision(String orderNumber,List<B2bOrderGoodsUpdateWeight> items){
		Integer firstStatus = null;//待审核状态
		Integer secondStatus = null;//待付款状态
		Integer thridStatus = null;//已关闭状态
		List<B2bOrderGoodsDtoEx> orderGoodsList = b2bOrderGoodsService.getByOrderNumber(orderNumber);
		List<B2bOrderGoodsDtoEx> newOrderGoods = new ArrayList<B2bOrderGoodsDtoEx>();
		if (null != items && items.size() > 0 && null != orderGoodsList && orderGoodsList.size()>0) {
			for(B2bOrderGoodsDtoEx ogx : orderGoodsList){
				//已关闭的订单不做更新操作
				if(ogx.getOrderStatus() == -1){
					continue;
				}
				for (B2bOrderGoodsUpdateWeight ogw : items) {
					//匹配子订单相同的数据进行更新
					if(ogw.getChildOrderNumber().equals(ogx.getChildOrderNumber())){
						//设置子订单状态
						ogx.setOrderStatus(null==ogw.getStatus()?ogx.getOrderStatus():Integer.parseInt(ogw.getStatus()));
						setOrderGoods(ogw, ogx);
						newOrderGoods.add(ogx);
					}
				}
				switch (ogx.getOrderStatus()) {
				case 0:
					firstStatus = 0;
					break;
				case 1:
					secondStatus = 1;
					break;
				case -1:
					thridStatus = -1;
					break;
				default:
					break;
				}
			}
			//更新数据和mongoDB
			b2bOrderService.updateOrderAndOrderGoods(orderNumber, 
					// 待审核优先,其次待付款,最后已关闭
					null==firstStatus?(secondStatus==null?thridStatus:secondStatus):firstStatus, newOrderGoods);
		}
	}


	@Override
	public void updateOrderStatus(String orderNumber,List<B2bOrderGoodsUpdateOrderStatus> items){
		Integer orderStatus = -1;//已关闭状态
		Integer temporaryStatus = -1;//临时状态
		List<B2bOrderGoodsDtoEx> orderGoodsList = b2bOrderGoodsService.getByOrderNumber(orderNumber);
		List<B2bOrderGoodsDtoEx> newOrderGoods = new ArrayList<B2bOrderGoodsDtoEx>();
		if(null != orderGoodsList && orderGoodsList.size()>0 
				&& null != items && items.size()>0){
			for(B2bOrderGoodsDtoEx ogx : orderGoodsList){
				for(B2bOrderGoodsUpdateOrderStatus ogs : items){
					if(ogs.getChildOrderNumber().equals(ogx.getChildOrderNumber())){
						//设置子订单状态
						ogx.setOrderStatus(null==ogs.getStatus()?ogx.getOrderStatus():Integer.parseInt(ogs.getStatus()));
						newOrderGoods.add(ogx);
					}
				}
			}
			for(B2bOrderGoodsDtoEx ogx : orderGoodsList){
				//状态-1默认
				if(-1 == ogx.getOrderStatus()){
					continue;
				}else{
					//存临时状态
					temporaryStatus = ogx.getOrderStatus();
					if(-1 == orderStatus){
						orderStatus = ogx.getOrderStatus();
						continue;
					}
					if(orderStatus == 5 && temporaryStatus == 4){
						continue;
					}
					if(orderStatus == 4 && temporaryStatus == 5){
						orderStatus = temporaryStatus;
						continue;
					}
					if(temporaryStatus<orderStatus){
						orderStatus = temporaryStatus;
					}
				}
			}
			//更新数据和mongoDB
			b2bOrderService.updateOrderAndOrderGoods(orderNumber, orderStatus, newOrderGoods);
		}
	}


	@Override
	public void updateOrderShiped(String orderNumber,List<B2bOrderGoodsUpdateOrderShiped> items){
		Integer orderStatus = -1;//已关闭状态
		Integer temporaryStatus = -1;//临时状态
		List<B2bOrderGoodsDtoEx> orderGoodsList = b2bOrderGoodsService.getByOrderNumber(orderNumber);
		List<B2bOrderGoodsDtoEx> newOrderGoods = new ArrayList<B2bOrderGoodsDtoEx>();
		if (null != orderGoodsList &&orderGoodsList.size()>0 
				&& null !=items && items.size() > 0) {
			for(B2bOrderGoodsDtoEx ogx : orderGoodsList){
				for (B2bOrderGoodsUpdateOrderShiped shiped : items) {
					logger.info("updateOrderGoodsShipedupdateOrderGoodsOrderShiped......."+ JsonUtils.convertToString(shiped));
					if(null!= shiped.getChildOrderNumber() &&  shiped.getChildOrderNumber().equals(ogx.getChildOrderNumber())){
						if (null == shiped.getWeightShipped() || null == shiped.getBoxesShipped()) {//注：新风鸣订单是全部发货，不会传发货箱数跟重量
							shiped.setWeightShipped(Double.toString(ogx.getAfterWeight()));
							shiped.setBoxesShipped(Integer.toString(ogx.getAfterBoxes()));
						}
						setBoxAndWeight(shiped, ogx);
						newOrderGoods.add(ogx);
					}
				}
			}
			for(B2bOrderGoodsDtoEx ogx : orderGoodsList){
				//状态-1默认
				if(-1 == ogx.getOrderStatus()){
					continue;
				}else{
					//存临时状态
					temporaryStatus = ogx.getOrderStatus();
					if(-1 == orderStatus){
						orderStatus = ogx.getOrderStatus();
						continue;
					}
					if(orderStatus == 5 && temporaryStatus == 4){
						continue;
					}
					if(orderStatus == 4 && temporaryStatus == 5){
						orderStatus = temporaryStatus;
						continue;
					}
					if(temporaryStatus<orderStatus){
						orderStatus = temporaryStatus;
					}
				}
			}
			//更新数据和mongoDB
			b2bOrderService.updateOrderAndOrderGoods(orderNumber, orderStatus, newOrderGoods);
		}
	}

	/**
	 * 同步竞价商品
	 */
	@Override
	public void syncAuctionGoods(String storePk, JSONArray array){
		if (null != array && array.size() > 0) {
			logger.error("parseAllGoodsAuction Size:" + array.size());
			List<B2bGoodsDto> list = new ArrayList<B2bGoodsDto>();
			B2bGoodsDtoEx b2bgoodsEx = new B2bGoodsDtoEx();
			for (int i = 0; i < array.size(); i++) {
				b2bgoodsEx = JSON.parseObject(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				b2bgoodsEx.setStorePk(storePk);
				b2bgoodsEx.setPk1(null == b2bgoodsEx.getBatchNumber()?"":b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(null == b2bgoodsEx.getDistinctNumber()?"":b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(null == b2bgoodsEx.getGradeName()?"":b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(null == b2bgoodsEx.getPackNumber()?"":b2bgoodsEx.getPackNumber());
				B2bGoodsDto b2bGoodsDto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				if (null!=b2bGoodsDto) {
					b2bGoodsDto.setType("auction");
					if(null != b2bgoodsEx.getPrice() && b2bgoodsEx.getPrice() >0){
						b2bGoodsDto.setPrice(ArithUtil.div(b2bgoodsEx.getPrice(), 1000, 2));
						b2bGoodsDto.setSalePrice(b2bgoodsEx.getPrice());
						b2bGoodsDto.setTonPrice(b2bgoodsEx.getTonPrice());
					}
					list.add(b2bGoodsDto);
				}
			}
			logger.error("auctionGoodsList----------------:"+list.size());
			if (null != list && list.size() > 0) {
				b2bGoodsService.updateGoodsAuctionBatch(list);
			}
		}else{
			logger.error("parseAllGoodsAuction Size:" + 0);
		}
	}


	/**
	 * 同步拼团商品
	 */
	@Override
	public void syncBindGoods(String storePk, JSONArray array){
		//将所有捆绑商品更新成预售
		b2bGoodsService.updateGoodsType(storePk, GoodsType.BINDING.getValue(), GoodsType.PRESALE.getValue());
		if (null != array && array.size() > 0) {
			logger.error("parseAllGoodsBind Size:" + array.size());
			List<B2bBindDtoEx> list = getGoodsBindList(storePk, array);
			logger.info("endSysBindGoods............................"+JsonUtils.convertToString(list));
			if (null != list && list.size() > 0) {
				b2bGoodsService.updateGoodsBindBatch(list);
			}
		}else{
			logger.error("parseAllGoodsBind Size:" + 0);
		}
	}
	
	private void setOrderGoods(B2bOrderGoodsUpdateWeight b2bOrderGoodsUpdateWeight,B2bOrderGoodsDtoEx orderGoods) {
		B2bOrderGoodsDtoMa orderGoodsDtoMa = new B2bOrderGoodsDtoMa();
		orderGoodsDtoMa.UpdateMine(orderGoods);
		CfGoods cfGoods = orderGoodsDtoMa.getCfGoods();
		orderGoods.setChildOrderNumber(b2bOrderGoodsUpdateWeight.getChildOrderNumber());
		//double packageFee = orderGoodsDto.getPackageFee() == null ? 0d : orderGoodsDto.getPackageFee();//包装费
		double managementFee = 0d;//自提管理费
		double loadingFee = 0d;// 装车费
		double presentPrice = 0d;//商品单价
		double presentFreight = 0d;//运费单价
		int afterBoxes = 0;//分货数量
		double afterWeight = 0d;//分货重量
		if(b2bOrderGoodsUpdateWeight.getLoadingFee() != null&& !"".equals(b2bOrderGoodsUpdateWeight.getLoadingFee())){
			loadingFee = Double.valueOf(b2bOrderGoodsUpdateWeight.getLoadingFee());
		}
		if (b2bOrderGoodsUpdateWeight.getManagementFee() != null&& !"".equals(b2bOrderGoodsUpdateWeight.getManagementFee())) {
			managementFee = Double.valueOf(b2bOrderGoodsUpdateWeight.getManagementFee());
		}
		if(null != b2bOrderGoodsUpdateWeight.getPresentPrice() && !"".equals(b2bOrderGoodsUpdateWeight.getPresentPrice())){
			presentPrice = Double.valueOf(b2bOrderGoodsUpdateWeight.getPresentPrice());
		}
		if(null != b2bOrderGoodsUpdateWeight.getPresentFreight() && !"".equals(b2bOrderGoodsUpdateWeight.getPresentFreight())){
			presentFreight = Double.valueOf(b2bOrderGoodsUpdateWeight.getPresentFreight());
		}
		if(null != b2bOrderGoodsUpdateWeight.getAfterBoxes() && !"".equals(b2bOrderGoodsUpdateWeight.getAfterBoxes())){
			afterBoxes = ArithUtil.getInt(b2bOrderGoodsUpdateWeight.getAfterBoxes());
		}
		if(null != b2bOrderGoodsUpdateWeight.getAfterWeight() && !"".equals(b2bOrderGoodsUpdateWeight.getAfterWeight())){
			afterWeight = Double.valueOf(b2bOrderGoodsUpdateWeight.getAfterWeight());
		}
		//修改前价格
		orderGoods.setBeforeAdminFee(null==cfGoods.getAdminFee()?0d:cfGoods.getAdminFee());
		orderGoods.setBeforeloadingFee(null==cfGoods.getLoadFee()?0d:cfGoods.getLoadFee());
		orderGoods.setBeforePrice(orderGoods.getPresentPrice());
		orderGoods.setBeforeFreight(orderGoods.getPresentFreight());
		orderGoods.setBeforeTotalFreight(orderGoods.getPresentTotalFreight());
		//修改后价格
		orderGoods.setAfterBoxes(afterBoxes);
		orderGoods.setAfterWeight(afterWeight);
		cfGoods.setLoadFee(loadingFee);
		cfGoods.setAdminFee(managementFee);
		orderGoods.setGoodsInfo(JSON.toJSONString(cfGoods));
		orderGoods.setPresentPrice(presentPrice);
		orderGoods.setPresentFreight(presentFreight);
		orderGoods.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(presentFreight,afterWeight), 2));// 运费结算总价
	}
	
	private void setBoxAndWeight(
			B2bOrderGoodsUpdateOrderShiped shiped,
			B2bOrderGoodsDtoEx ogx) {
		String subWeightStr = shiped.getWeightShipped();// 发货重量
		if (null != subWeightStr && !"".equals(subWeightStr)) {
			ogx.setWeightShipped(ArithUtil.add(ogx.getWeightShipped()==null?0d:ogx.getWeightShipped(), Double.valueOf(subWeightStr)));
			if (ogx.getWeightShipped() >= ogx.getAfterWeight()) {// 全部发货
				ogx.setOrderStatus(Constant.ORDER_STATUS_FOUR);
			}else{
				ogx.setOrderStatus(Constant.ORDER_STATUS_FIVE);
			}
		}
		String subBoxesStr = shiped.getBoxesShipped();// 发货数量
		Integer boxShippment = 0;
		//如果没有发货箱数则根据总重量*1000/箱重计算
		if (null == subBoxesStr || "".equals(subBoxesStr) || "0".equals(subBoxesStr)) {
			if(null !=ogx.getTareWeight() && ogx.getTareWeight() >0d){
				boxShippment = (int) Math.ceil(ArithUtil.div(Double.parseDouble(subWeightStr)*1000, ogx.getTareWeight()));
			}
		}else{
			boxShippment = Integer.parseInt(subBoxesStr);
		}
		ogx.setBoxesShipped((ogx.getBoxesShipped()==null?0:ogx.getBoxesShipped())+boxShippment);
	}
	
	@SuppressWarnings("rawtypes")
	private List<B2bBindDtoEx> getGoodsBindList(String storePk, JSONArray array) {
		B2bStoreDto storeDto = b2bStoreService.getByPk(storePk);
		List<B2bBindDtoEx> list = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			Set<B2bBindGoodsDtoEx> bindGoodsList = new HashSet<B2bBindGoodsDtoEx>();
			Map<String, Class> map = new HashMap<String, Class>();
			map.put("items", B2bBindGoodsDtoEx.class);
			B2bBindDtoEx b2bBindDtoEx = JsonUtils.toJSONMapBean(array.getString(i), B2bBindDtoEx.class, map);
			b2bBindDtoEx.setStorePk(storePk);
			b2bBindDtoEx.setStoreName(null==storeDto.getName()?"":storeDto.getName());
			if (b2bBindDtoEx != null && b2bBindDtoEx.getItems() != null && b2bBindDtoEx.getItems().size() > 0) {
				for (B2bBindGoodsDtoEx b2bBindGoodsDto : b2bBindDtoEx.getItems()) {
					B2bGoodsDto b2bGoodsDto = b2bGoodsService.getB2bGoodsInfoByBindGoods(b2bBindGoodsDto,storePk);
					if (null != b2bGoodsDto) {
						// 需要修改捆绑类型的商品
						b2bBindGoodsDto.setGoodsPk(b2bGoodsDto.getPk());
						b2bBindGoodsDto.setBoxes(b2bGoodsDto.getTotalBoxes());
						b2bBindGoodsDto.setTotalBoxes(b2bGoodsDto.getTotalBoxes());
						b2bBindGoodsDto.setUpdateTime(new Date());
						b2bBindGoodsDto.setWeight(b2bGoodsDto.getTotalWeight());
						b2bBindGoodsDto.setInsertTime(new Date());
						bindGoodsList.add(b2bBindGoodsDto);
					}
				}
			}
			b2bBindDtoEx.setItems(bindGoodsList);
			list.add(b2bBindDtoEx);
		}
		return list;
	}


	
	/**
	 * 同步客户收货地址
	 */
	@Override
	public void syncAddress(String companyPk, List<B2bAddressDtoEx> addressList){
		if (null != addressList && addressList.size() > 0) {
			logger.error("parseAllAddressData Size:" + addressList.size());
			B2bAddress addressModel =null;
			for (B2bAddressDtoEx addressDtoEx : addressList) {
				addressModel = new B2bAddress();
				addressModel.UpdateDTO(addressDtoEx);
				addressModel.setCompanyPk(companyPk);
				addressModel.setSyncStatus(1);
				b2bAddressService.insertOrUpdate(b2bAddressService.getAddressByDetails(addressModel));
			}
		}else{
			logger.error("parseAllAddressData Size:" + 0);
		} 
	}
	
	
	/**
	 * 处理盛虹返回的物流模板
	 */
	@Override
	public void updateAllLogistics(String storePk, String jsonData,String block){
		JSONArray array=null;
		if (null!=jsonData && !"".equals(jsonData)) {
			array = getJsonArray(jsonData);
		}
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
						info.setBlock(block);
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
	
	private JSONArray getJsonArray(String jsonData) {
		JSONArray array = null; 
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		if (null != jsonObject && !"".equals(jsonObject.get("status")) && Constant.RESPONSE_STATUS_SUCCESS.equals(jsonObject.get("status"))) {
			array = new JSONArray();
			if (jsonObject.get("message") != null || !"".equals(jsonObject.get("message").toString())) {
				array = JSONArray.parseArray(jsonObject.get("message").toString());
			}
		}
		return array;
	}

public static void main(String[] args) {
	System.out.println(Integer.parseInt("2.00"));
}
}
