package cn.cf.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.EncodeUtil;
import cn.cf.common.RestCode;
import cn.cf.constant.BillType;
import cn.cf.constant.Block;
import cn.cf.constant.OrderRecordType;
import cn.cf.constant.UnitType;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bContractDaoEx;
import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dao.B2bPackNumberDaoEx;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractDtoEx;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.B2bPackNumberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.Cgoods;
import cn.cf.entity.Corder;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.Sessions;
import cn.cf.http.HttpHelper;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bOrder;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bContractGoodsService;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bOrderRecordService;
import cn.cf.service.B2bPayVoucherService;
import cn.cf.service.CommonService;
import cn.cf.service.ForeignBankService;
import cn.cf.service.LogisticsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;

@Service
public class B2bContractServiceImpl implements B2bContractService {
	
	@Autowired
	private B2bContractDaoEx b2bContractDao;
	
	@Autowired
	private B2bContractGoodsDaoEx b2bContractGoodsDao;
	
	@Autowired
	private B2bContractGoodsService contractGoodsService;
	
	@Autowired
	private B2bCompanyDaoEx companyDaoEx;
	
	@Autowired
	private B2bStoreDaoEx storeDao;
	
	@Autowired
	private B2bPackNumberDaoEx b2bPackNumberDaoEx;
	
	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;
	
	@Autowired
	private B2bPayVoucherService b2bPayVoucherService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private LogisticsService logisticsService;
	
	@Autowired
	private B2bCompanyService b2bCompanyService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private B2bOrderRecordService b2bOrderRecordService;
	
	@Autowired
	private ForeignBankService foreignBankService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public void submitOrder(List<B2bContractDtoEx> b2bContract,
			List<B2bContractGoodsDtoEx> b2bContractGoodsDtoEx) {
		b2bContractDao.insertBatch(b2bContract);
		b2bContractGoodsDao.insertBatch(b2bContractGoodsDtoEx);
	}

	@Override
	public List<B2bContractDtoEx> submitContract(Corder order,B2bCompanyDto company, B2bMemberDto member,
			B2bAddressDto address,Map<String, List<Cgoods>> map) {
		B2bContractDtoEx model = null;
		String contractNumber = null;
		B2bContractGoodsDtoEx og = null;
		List<B2bContractGoodsDtoEx> oglist = new ArrayList<B2bContractGoodsDtoEx>();
		List<B2bContractDtoEx> olist = new ArrayList<B2bContractDtoEx>();
		B2bStoreDto sdto = null;
		String cpk = null;
		B2bCompanyDto p = null;
		// 根据营销公司对订单商品进行拆分
		for (String key : map.keySet()) {
			p = companyDaoEx.getByPk(key);
			if ("-1".equals(p.getParentPk())) {
				cpk = p.getPk();
			} else {
				cpk = p.getParentPk();
			}
			// 先绑定所有业务员并根据板块拆单
			Map<String, List<Cgoods>> opMap  = openGoodsBySalesman(map.get(key), company.getPk());
			for(String m : opMap.keySet()){
				Map<String, List<Cgoods>> nopMap = combinationGoods(openOrderGoodsByPack(opMap.get(m)), 1);
				// 对订单进行业务员分组拆单
				sdto = storeDao.getByCompanyPk(cpk);
				for (String k : nopMap.keySet()) {
					List<Cgoods> cGoods = nopMap.get(k);
					if(null == cGoods || cGoods.size() == 0){
						continue;
					}
					// 对订单根据承运商进行分组拆单
					Map<String, List<Cgoods>> lgMap = combinationGoods(cGoods, 3);
					for(String l : lgMap.keySet()){
						List<Cgoods> cGoodsEx = lgMap.get(l);
						if(null == cGoodsEx || cGoodsEx.size() == 0){
							continue;
						}
						double orderAmount = 0.00;
						double totalWeight = 0.00;
						if(m.contains(Block.CF.getValue())){
							contractNumber = Block.CF.getContractType()+KeyUtils.getOrderNumber();
						}else{
							contractNumber = Block.SX.getContractType()+KeyUtils.getOrderNumber();
						}
						LogisticsModelDto lmdto = order.getLmdto();
						for (int i = 0; i < cGoodsEx.size(); i++) {
							og = new B2bContractGoodsDtoEx(cGoodsEx.get(i),contractNumber,i+1,lmdto);
							oglist.add(og);
							orderAmount = ArithUtil.add(orderAmount,
									og.getTotalPrice());
							totalWeight = ArithUtil.add(totalWeight,
									og.getWeight());
						}
						model = new B2bContractDtoEx(order, contractNumber, orderAmount, totalWeight, p, company, member, address,sdto,cGoodsEx.get(0));
						if(ArithUtil.round(orderAmount, 2)==0){
							olist = new ArrayList<B2bContractDtoEx>();
							olist.add(model);	
							return olist;
						}
						olist.add(model);	
					}
				}
			}
		}
			this.submitOrder(olist, oglist);
			return olist;
	
	}

	@Override
	public PageModel<B2bContractDtoEx> searchListByType(String type,
			B2bStoreDto store,B2bCompanyDto company,Map<String,Object> map,Sessions session,B2bMemberDto memberDto) {
		map = searchMap(type, store, company, map, session, memberDto);
		PageModel<B2bContractDtoEx> pm = new PageModel<B2bContractDtoEx>();
		List<B2bContractDtoEx> list = b2bContractDao.searchContractList(map);
		int count = b2bContractDao.searchContractCount(map);
		if(count >0) {
			for(B2bContractDtoEx contract : list){
				contract.setContractGoods(contractGoodsService.searchListByContractNo(contract.getContractNo(), true));
				// 付款凭证列表
				List<B2bPayVoucher> voucherList = b2bPayVoucherService.searchB2bPayVoucherList(contract.getContractNo());
				if (null != voucherList && voucherList.size() > 0) {
					contract.setPayVoucherList(b2bPayVoucherService.searchB2bPayVoucherList(contract.getContractNo()));
				}
				if (null != contract.getPaymentType()
						&&  contract.getPaymentType() == 3) {
					try {
						B2bLoanNumberDto boanNumber = foreignBankService
								.getB2bLoanNumberDto(contract.getContractNo());
						if (null != boanNumber) {
							contract.setLoanNumberStatus(boanNumber
									.getLoanStatus());
							contract.setBank(boanNumber.getBankName());
						}

					} catch (Exception e) {
						logger.error("借据状态" + e);
					}
				}
				if (null != contract.getPaymentType()
						&&  contract.getPaymentType() == 4) {
					try {
						B2bOnlinepayRecordDto onlineRecords = foreignBankService
								.getOnlinepayRecordDto(contract.getContractNo());
						if (null != onlineRecords) {
							contract.setOnlinePayStatus(onlineRecords.getStatus());
						}

					} catch (Exception e) {
						logger.error("线上支付状态" + e);
					}
				}
				if (null != contract.getPaymentType()
						&&  contract.getPaymentType() == 5) {
					try {
						B2bBillOrderDto billOrder = foreignBankService
								.getBillOrder(contract.getContractNo());
						if (null != billOrder && null != billOrder.getStatus()) {
							//1处理中 2完成 3取消  4锁定  
							contract.setBillPayStatus(billOrder.getStatus());
							contract.setBillGoodsType(BillType.TX.equals(billOrder.getGoodsShotName())?
									BillType.TX:BillType.PFT);
							//锁定状态下的特殊处理
							if(null != billOrder.getSerialNumber() && !"".equals(billOrder.getSerialNumber())
									&& 4 == billOrder.getStatus()){
								List<B2bBillInventoryDto> inventoryList = foreignBankService.searchBillInventoryList(contract.getContractNo());
								if(null != inventoryList && inventoryList.size()>0){
									for(B2bBillInventoryDto i : inventoryList){
										if(null !=i.getStatus() && (1 ==i.getStatus()) ||
												2 ==i.getStatus() || 3==i.getStatus()){
											contract.setBillPayStatus(2);
											break;
										}
									}
								}
							}
							//显示票付通按钮
							if((null == billOrder.getSerialNumber() || "".equals(billOrder.getSerialNumber()))
									&& BillType.PFT.equals(contract.getBillGoodsType())){
								contract.setShowPft(1);//1显示票付通按钮
							}
						}

					} catch (Exception e) {
						logger.error("票据支付状态" + e);
					}
				}
				
			}
		}
		pm.setDataList(list);
		pm.setTotalCount(count);
		if(null != map.get("start")){
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		}
		if(null != map.get("limit")){
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	private Map<String, Object> searchMap(String type, B2bStoreDto store,
			B2bCompanyDto company, Map<String, Object> map, Sessions session,
			B2bMemberDto memberDto) {
		if(null == map){
			map = new HashMap<String,Object>();
		}
		if(null !=type && "1".equals(type)){
			String[] purchaserPks = companyPks(company);
			if (purchaserPks.length == 0) {
				map.put("purchaserPks", new String[] { "null" });
			} else {
				map.put("purchaserPks", purchaserPks);
			}
			// 子公司
		}
		if(null != type && "2".equals(type)){
			if("-1".equals(company.getParentPk())){
				map.put("storePk", store.getPk());
			}else{
				map.put("supplierPk", company.getPk());
			}
		}
		if(null != type && "3".equals(type)){
			if("-1".equals(company.getParentPk())){
				map.put("storePk", store.getPk());
			}else{
				map.put("supplierPk", company.getPk());
			}
			if (session.getIsAdmin() != 1) {
				String[] employeePks = commonService.getEmployeePks(memberDto.getPk());
				if(employeePks.length>0){
					map.put("employeePks", employeePks);
				//没匹配到业务员不给查询
				}else{
					map.put("employeePk", memberDto.getPk());
				}
			}
		}
		map.put("isDelete", 1);
		return map;
	}


	@Override
	public int updateContract(B2bContract dto) {
		return b2bContractDao.updateContract(dto);
	}

	@Override
	public B2bContractDto getB2bContract(String contractNo) {
		// TODO Auto-generated method stub
		return b2bContractDao.getByContractNo(contractNo);
	}

	@Override
	public void updateContractStatus(B2bContract contract) {
		B2bContractGoods contractGoods = new B2bContractGoods();
		if(null != contract){
			contractGoods.setContractNo(contract.getContractNo());
			if(null != contract.getContractStatus()){
				contractGoods.setContractStatus(contract.getContractStatus());
			}
			if(null != contract.getIsDelete()){
				contractGoods.setIsDelete(contract.getIsDelete());
			}
			b2bContractDao.updateContract(contract);
			b2bContractGoodsDao.updateContractGoods(contractGoods);
		}
	}

	@Override
	public List<B2bContractGoodsDtoEx> exportContractList(String type,B2bStoreDto store,B2bCompanyDto company,Map<String, Object> map,Sessions session,B2bMemberDto memberDto) {
		map = searchMap(type, store, company, map, session, memberDto);
		List<B2bContractGoodsDtoEx> list=b2bContractGoodsDao.exportContractList(map);
		Map<String,Object> info = new HashMap<String,Object>();
		for(B2bContractGoodsDtoEx dto:list){
			Double otherPrice = 0d;
			String purchaserInfo=dto.getPurchaserInfo();
			String supplierInfo=dto.getSupplierInfo();
			if(null != purchaserInfo && !"".equals(purchaserInfo)){
				info= JsonUtils.stringToCollect(purchaserInfo);
				String purchaserName=null==info.get("purchaserName")?"":info.get("purchaserName").toString();
				dto.setPurchaserName(purchaserName);
			}else{
				dto.setPurchaserName("");
			}
			if(null != supplierInfo && !"".equals(supplierInfo)){
				info=JsonUtils.stringToCollect(supplierInfo);
				String supplierName=null==info.get("supplierName")?"":info.get("supplierName").toString();
				dto.setSupplierName(supplierName);
			}else{
				dto.setSupplierName("");
			}
			String addressInfo=dto.getAddressInfo();
			if (null!=addressInfo&&!"".equals(addressInfo)) {
				info=JsonUtils.stringToCollect(addressInfo);
				dto.setProvinceName(null==info.get("provinceName")?"":info.get("provinceName").toString());
				dto.setCityName(null==info.get("cityName")?"":info.get("cityName").toString());
				dto.setAreaName(null==info.get("areaName")?"":info.get("areaName").toString());
				dto.setTownName(null==info.get("townName")?"":info.get("townName").toString());
				dto.setAddress(null==info.get("address")?"":info.get("address").toString());
				dto.setContacts(null==info.get("contacts")?"":info.get("contacts").toString());
				dto.setContactsTel(null==info.get("contactsTel")?"":info.get("contactsTel").toString());
			}else{
				dto.setProvinceName("");
				dto.setCityName("");
				dto.setAreaName("");
				dto.setTownName("");
				dto.setAddress("");
				dto.setContacts("");
				dto.setContactsTel("");
			}
			String weights = new BigDecimal(dto.getWeight().toString()).toPlainString();
            String weightNoShippeds = new BigDecimal(dto.getWeightNoShipped().toString()).toPlainString();
            String weightShippeds = new BigDecimal(dto.getWeightShipped().toString()).toPlainString();
           
            //商品信息
            String goodsInfo = dto.getGoodsInfo();
            if (null != goodsInfo && !"".equals(goodsInfo)) {
            	info = JsonUtils.stringToCollect(goodsInfo);
			}
            //1:供应商中心导出
            if (type.equals("2")||type.equals("3")) {
            	B2bCompanyDto b2bCompanyDto = session.getCompanyDto();
            	String block = b2bCompanyDto.getBlock();
				if (block.equals(Block.CF.getValue())) {//cf
					if (null != info && !"".equals(info)) {
						dto.setProductName(null != info.get("productName") && !"".equals(info.get("productName")) ? info.get("productName").toString() : "");
						dto.setVarietiesName(null != info.get("varietiesName") && !"".equals(info.get("varietiesName")) ? info.get("varietiesName").toString() : "");
						dto.setSeedvarietyName(null != info.get("seedvarietyName") && !"".equals(info.get("seedvarietyName")) ? info.get("seedvarietyName").toString() : "");
						dto.setSpecName(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName").toString() : "");
						dto.setSpecifications(null != info.get("specifications") && !"".equals(info.get("specifications")) ? info.get("specifications").toString() : "");
						dto.setSeriesName(null != info.get("seriesName") && !"".equals(info.get("seriesName")) ? info.get("seriesName").toString() : "");
						dto.setBatchNumber(null != info.get("batchNumber") && !"".equals(info.get("batchNumber")) ? info.get("batchNumber").toString() : "");
						dto.setGradeName(null != info.get("gradeName") && !"".equals(info.get("gradeName")) ? info.get("gradeName").toString() : "");
						dto.setDistinctNumber(null != info.get("distinctNumber") && !"".equals(info.get("distinctNumber")) ? info.get("distinctNumber").toString() : "");
						dto.setPackNumber(null != info.get("packNumber") && !"".equals(info.get("packNumber")) ? info.get("packNumber").toString() : "");
	                    dto.setPlantName((null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : ""));
	                    dto.setLoadFee(null==info.get("loadFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("loadFee").toString()),2)));
	                    dto.setAdminFee(null==info.get("adminFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("adminFee").toString()),2)));
	                    dto.setPackFee(null==info.get("packageFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("packageFee").toString()),2)));
	                    dto.setWeight(dto.getWeight());
	                    dto.setBoxes(dto.getBoxes());
	                    dto.setBoxesNoShipped(dto.getBoxesNoShipped());
	                    dto.setWeightNoShipped(dto.getWeightNoShipped());
	                    dto.setWeightShipped(dto.getWeightShipped());
	                    dto.setBoxesShipped(dto.getBoxesShipped());
	                    otherPrice =  (null==info.get("packageFee")?0d:Double.parseDouble(info.get("packageFee").toString()))+
	                    		(null==info.get("loadFee")?0d:Double.parseDouble(info.get("loadFee").toString()))+
	                    		(null==info.get("adminFee")?0d:Double.parseDouble(info.get("adminFee").toString()));
	                    dto.setTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getFreight(), dto.getWeight()), 2));
					}
				}
				if (block.equals(Block.SX.getValue())) {//sx
					if (null!=info&&!"".equals(info)) {
						dto.setRawMaterialParentName(null != info.get("rawMaterialParentName") && !"".equals(info.get("rawMaterialParentName")) ? info.get("rawMaterialParentName").toString() : "");
						dto.setRawMaterialName(null != info.get("rawMaterialName") && !"".equals(info.get("rawMaterialName")) ? info.get("rawMaterialName").toString() : "");
						dto.setTechnologyName(null != info.get("technologyName") && !"".equals(info.get("technologyName")) ? info.get("technologyName").toString() : "");
						dto.setMaterialName(null != info.get("materialName") && !"".equals(info.get("materialName")) ? info.get("materialName").toString() : "");
						dto.setSpecName(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName").toString() : "");
						dto.setPlantName((null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : ""));
						dto.setLoadFee(null==info.get("loadFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("loadFee").toString()),2)));
						dto.setAdminFee(null==info.get("adminFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("adminFee").toString()),2)));
						dto.setWeightS(weights+"");
		                dto.setBoxesS(dto.getBoxes()+"");
		                dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+"");
		                dto.setWeightNoShippedS(weightNoShippeds+"");
		                dto.setWeightShippedS(weightShippeds+"");
		                dto.setBoxesShippedS(dto.getBoxesShipped()+"");
		                dto.setTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getFreight(), ArithUtil.div(dto.getWeight(), 1000)), 2));
		                dto.setPackFee(null==info.get("packageFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("packageFee").toString()),2)));
					}
				}
				if (block.contains(Block.CF.getValue()) && block.contains(Block.SX.getValue())) {//cf,sx
					if (null!=dto.getBlock()&& Block.CF.getValue().equals(dto.getBlock())) {
						if (null != info && !"".equals(info)) {
							StringBuffer buf = new StringBuffer();
							buf.append(null != info.get("productName") && !"".equals(info.get("productName")) ? info.get("productName") : "");
							buf.append(null != info.get("varietiesName") && !"".equals(info.get("varietiesName")) ? info.get("varietiesName") : "");
							buf.append(null != info.get("seriesName") && !"".equals(info.get("seriesName")) ? info.get("seriesName") : "");
							buf.append(null != info.get("batchNumber") && !"".equals(info.get("batchNumber")) ? info.get("batchNumber") : "");
							buf.append(null != info.get("gradeName") && !"".equals(info.get("gradeName")) ? info.get("gradeName") : "");
							String d = buf.toString();
							dto.setGoodsInfo(d);
		                    dto.setPlantName((null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : ""));
		                    dto.setLoadFee(null==info.get("loadFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("loadFee").toString()),2)));
		                    dto.setAdminFee(null==info.get("adminFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("adminFee").toString()),2)));
		                    dto.setPackFee(null==info.get("packageFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("packageFee").toString()),2)));
		                    dto.setWeightS(weights+"吨");
		                    String unit = null==info.get("unit")?"":info.get("unit")+"";
		                    dto.setBoxesS(dto.getBoxes()+""+UnitType.fromInt(unit).toString());
		                    dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+""+UnitType.fromInt(unit).toString());
		                    dto.setWeightNoShippedS(weightNoShippeds+"吨");
		                    dto.setWeightShippedS(weightShippeds+"吨");
		                    dto.setBoxesShippedS(dto.getBoxesShipped()+""+UnitType.fromInt(unit).toString());
		                    otherPrice =  (null==info.get("packageFee")?0d:Double.parseDouble(info.get("packageFee").toString()))+
		                    		(null==info.get("loadFee")?0d:Double.parseDouble(info.get("loadFee").toString()))+
		                    		(null==info.get("adminFee")?0d:Double.parseDouble(info.get("adminFee").toString()));
		                    dto.setTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getFreight(), dto.getWeight()), 2));
						}
					}
					else if (null!=dto.getBlock()&& Block.SX.getValue().equals(dto.getBlock())){
							if (null!=info&&!"".equals(info)) {
								StringBuffer buf = new StringBuffer();
								buf.append(null != info.get("rawMaterialParentName") && !"".equals(info.get("rawMaterialParentName")) ? info.get("rawMaterialParentName") : "");
								buf.append(null != info.get("rawMaterialName") && !"".equals(info.get("rawMaterialName")) ? info.get("rawMaterialName") : "");
								buf.append(null != info.get("technologyName") && !"".equals(info.get("technologyName")) ? info.get("technologyName") : "");
								buf.append(null != info.get("materialName") && !"".equals(info.get("materialName")) ? info.get("materialName") : "");
								buf.append(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName") : "");
								String d = buf.toString();
								dto.setPlantName((null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : ""));
								dto.setGoodsInfo(d);
								dto.setLoadFee(null==info.get("loadFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("loadFee").toString()),2)));
								dto.setAdminFee(null==info.get("adminFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("adminFee").toString()),2)));
								dto.setPackFee(null==info.get("packageFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("packageFee").toString()),2)));
								dto.setWeightS(weights+"千克");
								String unit = null==info.get("unit")?"":info.get("unit")+"";
				                dto.setBoxesS(dto.getBoxes()+""+UnitType.fromInt(unit).toString());
				                dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+""+UnitType.fromInt(unit).toString());
				                dto.setWeightNoShippedS(weightNoShippeds+"千克");
				                dto.setWeightShippedS(weightShippeds+"千克");
				                dto.setBoxesShippedS(dto.getBoxesShipped()+""+UnitType.fromInt(unit).toString());
				                dto.setTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getFreight(), ArithUtil.div(dto.getWeight(), 1000)), 2));
							}
					}
				}
			}else {
				//2:采購商中心导出
				if (null!=dto.getBlock()&& Block.CF.getValue().equals(dto.getBlock())) {
					if (null != info && !"".equals(info)) {
						StringBuffer buf = new StringBuffer();
						buf.append(null != info.get("productName") && !"".equals(info.get("productName")) ? info.get("productName") : "");
						buf.append(null != info.get("varietiesName") && !"".equals(info.get("varietiesName")) ? info.get("varietiesName") : "");
						buf.append(null != info.get("seriesName") && !"".equals(info.get("seriesName")) ? info.get("seriesName") : "");
						buf.append(null != info.get("batchNumber") && !"".equals(info.get("batchNumber")) ? info.get("batchNumber") : "");
						buf.append(null != info.get("gradeName") && !"".equals(info.get("gradeName")) ? info.get("gradeName") : "");
						String d = buf.toString();
						dto.setGoodsInfo(d);
	                    dto.setPlantName((null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : ""));
	                    dto.setLoadFee(null==info.get("loadFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("loadFee").toString()),2)));
	                    dto.setAdminFee(null==info.get("adminFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("adminFee").toString()),2)));
	                    dto.setPackFee(null==info.get("packageFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("packageFee").toString()),2)));
	                    dto.setWeightS(weights+"吨");
	                    String unit = null==info.get("unit")?"":info.get("unit")+"";
	                    dto.setBoxesS(dto.getBoxes()+""+UnitType.fromInt(unit).toString());
	                    dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+""+UnitType.fromInt(unit).toString());
	                    dto.setWeightNoShippedS(weightNoShippeds+"吨");
	                    dto.setWeightShippedS(weightShippeds+"吨");
	                    dto.setBoxesShippedS(dto.getBoxesShipped()+""+UnitType.fromInt(unit).toString());
	                    otherPrice =  (null==info.get("packageFee")?0d:Double.parseDouble(info.get("packageFee").toString()))+
	                    		(null==info.get("loadFee")?0d:Double.parseDouble(info.get("loadFee").toString()))+
	                    		(null==info.get("adminFee")?0d:Double.parseDouble(info.get("adminFee").toString()));
	                    dto.setTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getFreight(), dto.getWeight()), 2));
					}
				}else if (null!=dto.getBlock()&& Block.SX.getValue().equals(dto.getBlock())){
						if (null!=info&&!"".equals(info)) {
							StringBuffer buf = new StringBuffer();
							buf.append(null != info.get("rawMaterialParentName") && !"".equals(info.get("rawMaterialParentName")) ? info.get("rawMaterialParentName") : "");
							buf.append(null != info.get("rawMaterialName") && !"".equals(info.get("rawMaterialName")) ? info.get("rawMaterialName") : "");
							buf.append(null != info.get("technologyName") && !"".equals(info.get("technologyName")) ? info.get("technologyName") : "");
							buf.append(null != info.get("materialName") && !"".equals(info.get("materialName")) ? info.get("materialName") : "");
							buf.append(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName") : "");
							String d = buf.toString();
							dto.setPlantName((null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : ""));
							dto.setGoodsInfo(d);
							dto.setLoadFee(null==info.get("loadFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("loadFee").toString()),2)));
							dto.setAdminFee(null==info.get("adminFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("adminFee").toString()),2)));
							dto.setPackFee(null==info.get("packageFee")?"0":Double.toString(ArithUtil.round(Double.parseDouble(info.get("packageFee").toString()),2)));
							dto.setWeightS(weights+"千克");
							String unit = null==info.get("unit")?"":info.get("unit")+"";
			                dto.setBoxesS(dto.getBoxes()+""+UnitType.fromInt(unit).toString());
			                dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+""+UnitType.fromInt(unit).toString());
			                dto.setWeightNoShippedS(weightNoShippeds+"千克");
			                dto.setWeightShippedS(weightShippeds+"千克");
			                dto.setBoxesShippedS(dto.getBoxesShipped()+""+UnitType.fromInt(unit).toString());
			                dto.setTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getFreight(), ArithUtil.div(dto.getWeight(), 1000)), 2));
						}
				}
			}
			dto.setTotalPrice(ArithUtil.add(ArithUtil.round(ArithUtil.mul((dto.getContractPrice()+otherPrice),dto.getWeight()),2),dto.getTotalFreight()));
		}
		return list;
	}

	@Override
	public B2bContractDto updateContract(B2bContract contract, List<B2bContractGoods> list) {
		B2bContractDto dto = b2bContractDao.getByContractNo(contract.getContractNo());
		if(null != dto){
			b2bContractDao.updateContract(contract);
			if(null != list && list.size() >0){
				for (B2bContractGoods bg : list) {
					b2bContractGoodsDao.updateByPk(bg);
				}
			}
		}
		return dto;
	}

	@Override
	public B2bContractDtoEx getB2bContractDetails(String contractNo) {
		B2bContractDtoEx dtoEx = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contractNo", contractNo);
		List<B2bContractDtoEx> list =  b2bContractDao.searchContractList(map);
		if(null != list && list.size()>0){
			dtoEx = list.get(0);
			dtoEx.setContractGoods(contractGoodsService.searchListByContractNo(contractNo, true));
		}
		if(null != dtoEx){
			// 订单日志
			Query query = new Query(Criteria.where("orderNumber").is(
					contractNo));
			query.with(new Sort(Direction.DESC, "insertTime"));
			List<OrderRecord> recordList = mongoTemplate.find(query,
					OrderRecord.class);
			dtoEx.setOrderRecordList(recordList);
			// 付款凭证列表
			List<B2bPayVoucher> voucherList = b2bPayVoucherService
					.searchB2bPayVoucherList(contractNo);
			if (null != voucherList && voucherList.size() > 0) {
				dtoEx.setPayVoucherList(voucherList);
			}
			// 采购商联系人
			B2bCompanyDtoEx purchaser = b2bCompanyService.getCompany(dtoEx
					.getPurchaserPk());
			if (null != purchaser) {
				dtoEx.setPurchaserMobile(purchaser.getContactsTel());
			}
			// 物流方式
			LogisticsModelDto mdto = logisticsService.getLogisticModel(dtoEx
					.getLogisticsModelPk());
			if (null != mdto) {
				dtoEx.setLogisticsModelType(mdto.getType());
			}
			dtoEx.setMemberMobile(dtoEx.getMember());
			dtoEx.setActualAmount(dtoEx.getOrderAmount());
			if (null != dtoEx.getPaymentType()
					&&  dtoEx.getPaymentType() == 3) {
				try {
					B2bLoanNumberDto boanNumber = foreignBankService
							.getB2bLoanNumberDto(dtoEx.getContractNo());
					if (null != boanNumber) {
						dtoEx.setLoanNumberStatus(boanNumber
								.getLoanStatus());
					}

				} catch (Exception e) {
					logger.error("借据状态" + e);
				}
			}
			if (null != dtoEx.getPaymentType()
					&&  dtoEx.getPaymentType() == 4) {
				try {
					B2bOnlinepayRecordDto onlineRecords = foreignBankService
							.getOnlinepayRecordDto(dtoEx.getContractNo());
					if (null != onlineRecords) {
						dtoEx.setOnlinePayStatus(onlineRecords.getStatus());
					}

				} catch (Exception e) {
					logger.error("线上支付状态" + e);
				}
			}
			if (null != dtoEx.getPaymentType()
					&&  dtoEx.getPaymentType() == 5) {
				try {
					B2bBillOrderDto billOrder = foreignBankService
							.getBillOrder(dtoEx.getContractNo());
					if (null != billOrder && null != billOrder.getStatus()) {
						//1处理中 2完成 3取消  4锁定  完成和锁定状态都可以确认收款 
						dtoEx.setBillPayStatus(billOrder.getStatus());
						dtoEx.setBillGoodsType(BillType.TX.equals(billOrder.getGoodsShotName())?
								BillType.TX:BillType.PFT);
					}

				} catch (Exception e) {
					logger.error("票据支付状态" + e);
				}
			}
			
		}
		return dtoEx;
	}
	
	
	/**
	 * 对订单商品进行同一类别组合
	 * 
	 * @param list
	 * @param type
	 *            1业务员 2商品
	 * @return
	 */
	public Map<String, List<Cgoods>> combinationGoods(List<Cgoods> list, Integer type) {
		Map<String, List<Cgoods>> skuIdMap = new HashMap<String, List<Cgoods>>();
		if (null != list && list.size() > 0) {
			String key = null;
			for (Cgoods p : list) {
				// 根据会员Pk分组(业务员)
				if (type == 1) {
					if(null !=p.getMemberDto()){
						key = p.getMemberDto().getPk();
					}else{
						key ="";
					}
					// 根据商品pk分组
				} else if(type == 2){
					key = p.getGoodsDto()
							.getPk();
				//根据承运商分组
				}else{
					key = p.getLogisticsCarrierPk();
				}
				List<Cgoods> tempList = skuIdMap.get(key);
				/* 如果取不到数据,那么直接new一个空的ArrayList* */
				if (tempList == null) {
					tempList = new ArrayList<Cgoods>();
					tempList.add(p);
					skuIdMap.put(key, tempList);
				} else {
					/* 某个sku之前已经存放过了,则直接追加数据到原来的List里* */
					tempList.add(p);
				}
			}
		}
		return skuIdMap;
	}

	/**
	 * 根据包装批拆订单商品包装
	 * 
	 * @param list
	 * @return
	 */
	public List<Cgoods> openOrderGoodsByPack(List<Cgoods> list) {
		if (null == list || list.size() == 0) {
			return null;
		}
		List<Cgoods> newList = new ArrayList<Cgoods>();
		for (int j = 0; j < list.size(); j++) {
			Cgoods p = list.get(j);
			B2bGoodsDtoMa good = p.getGoodsDto();
			// 根据品名、店铺、箱重(大于等于)、包装数量倒序
			if(Block.CF.getValue().equals(good.getBlock())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("storePk", good.getStorePk());
				map.put("productPk", good.getCfGoods().getProductPk());
				map.put("batchNumber",  good.getCfGoods().getBatchNumber());
				map.put("tareWeight", good.getTareWeight());
				List<B2bPackNumberDto> packList = b2bPackNumberDaoEx.getB2bPackNumberByGoods(map);
				if (null != packList && packList.size() > 0) {
					// 根据购买箱数与包装箱数取余
					Integer otherCount = p.getBoxes();
					for (int k = 0; k < packList.size(); k++) {
						if (otherCount < packList.get(k).getBucketsNum()) {
							continue;
						}
						Integer counts = otherCount / packList.get(k).getBucketsNum();
						Cgoods np = (Cgoods) p.clone();
						np.setBoxes(counts * packList.get(k).getBucketsNum());
						np.setPackNumber(packList.get(k).getPackNumber());
						newList.add(np);
						// 如果取余等于0 包装全部匹配完成
						if (otherCount % packList.get(k).getBucketsNum() == 0) {
							otherCount = 0;
							break;
							// 如果取余等于1 包装无需匹配
						} else if (otherCount % packList.get(k).getBucketsNum() == 1) {
							otherCount = 1;
							break;
							// 如果取余不等于0 则继续匹配
						} else {
							otherCount = otherCount % packList.get(k).getBucketsNum();
						}
					}
					// 如果有剩余的下箱数
					if (otherCount > 0) {
						Cgoods np = (Cgoods) p.clone();
						np.setBoxes(otherCount);
						np.setPackNumber("");
						newList.add(np);
					}
					// 如果没有包装
				} else {
					newList.add(p);
				}
			}else{
				newList.add(p);
			}
		}
		return newList;
	}

	
	/**
	 * 订单商品根据业务员为最小单位划分
	 * 
	 * @param list
	 * @param purchaserPk
	 *            采购商
	 * @return
	 */
	public Map<String, List<Cgoods>>  openGoodsBySalesman(List<Cgoods> list, String purchaserPk) {
		Map<String, List<Cgoods>> map = new HashMap<String, List<Cgoods>>();
		// 业务员拆分好后 再根据是否满足分货条件进行拆单(此时订单商品列表增加上订单号)
		List<Cgoods> cfList = new ArrayList<Cgoods>();// 化纤正常商品
		List<Cgoods> sxList = new ArrayList<Cgoods>();// 化纤预售商品
		if (null != list && list.size() > 0) {
			// 先根据业务员拆单
			for (Cgoods cg : list) {
				// 此处调用member系统返回业务员信息
				B2bGoodsDtoMa ma = cg.getGoodsDto();
				B2bMemberDto memberDto = b2bCustomerSaleManService.getSaleMan(ma.getCompanyPk(), purchaserPk,
						null!=ma.getCfGoods()?ma.getCfGoods().getProductPk():null, ma.getStorePk());
				if (null != memberDto) {
					cg.setMemberDto(memberDto);
				}
				if(Block.CF.getValue().equals(ma.getBlock())){
					cfList.add(cg);
				}else{
					sxList.add(cg);
				}
				map.put("cfList", cfList);
				map.put("sxList", sxList);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> searchStatusByType(String type,
		B2bStoreDto store,	B2bCompanyDto company, Map<String, Object> map,Sessions session,B2bMemberDto memberDto) {
		map = searchMap(type, store, company, map, session, memberDto);
		return b2bContractDao.searchContractStatusCounts(map);
	}

	
	/**
	 * （合同）提交审批
	 */
	@Override
	public String submitAudit(String contractNo) {
		Map<String, String> erpMap = new HashMap<String, String>();
		String url = "shop2erp/submitAudit";
		erpMap.put("contractNo", contractNo);
		String result="";
		try {
			result = HttpHelper.doPost(PropertyConfig.getProperty("api_erp_url")+ url, erpMap);
			if (null!=result && !"".equals(result)) {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
			}else {
				result = RestCode.CODE_S999.toJson();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<B2bContractGoodsDtoEx> searchListByContractNo(String contractNo) {
		return b2bContractGoodsDao.searchListByContractNo(contractNo );
	}

	@Override
	public B2bContractDtoMa updateContractStatus(B2bContract contract,
			SysCompanyBankcardDto card) {
		B2bContractDtoMa ma = null;
		B2bContractDto contractdto = b2bContractDao.getByContractNo(contract.getContractNo());
		if(null != contractdto){
			ma = new B2bContractDtoMa();
			ma.UpdateMine(contractdto);
			if(null!= card){
				ma.getSupplier().setBankName(card.getBankName());
				ma.getSupplier().setBankAccount(card.getBankAccount());
				ma.getSupplier().setBankNo(card.getBankNo());
				contract.setSupplierInfo(JSON.toJSONString(ma.getSupplier()));
			}
			B2bContractGoods contractGoods = new B2bContractGoods();
			contractGoods.setContractNo(contract.getContractNo());
			if(null != contract.getContractStatus()){
				contractGoods.setContractStatus(contract.getContractStatus());
			}
			b2bContractDao.updateContract(contract);
			b2bContractGoodsDao.updateContractGoods(contractGoods);
		}
		return ma;
	}
	private String[] companyPks(B2bCompanyDto company) {
		List<B2bCompanyDtoEx> list = b2bCompanyService.searchCompanyList(1,
				company.getPk(), 1, null);
		String[] companyPks = new String[list.size()];
		for (int i = 0; i < companyPks.length; i++) {
			companyPks[i] = list.get(i).getPk();
		}
		return companyPks;
	}

	@Override
	public List<B2bContractDto> cancelContract() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractStatus", 1);
		return b2bContractDao.searchList(map);
	}

	
	@Override
	public void autoCompleteContract(int days,int contractStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractStatus", contractStatus);
		List<B2bContractDto> list = b2bContractDao.searchList(map);
		Date date = DateUtil.dateBeforeNowDay(days,23,59,59);
		List<OrderRecord> recordList = new ArrayList<>();
		for (B2bContractDto b2bContractDto : list) {
			B2bLoanNumberDto loan = foreignBankService.getB2bLoanNumberDto(b2bContractDto.getContractNo());
			//化纤贷未还款成功的不做处理
			if(null != loan && (loan.getEconomicsGoodsType() == 2 || loan.getEconomicsGoodsType() == 4)
					&& loan.getLoanStatus() != 5){
				continue;
			}
			boolean updateFlag=false;
			String contractNo = b2bContractDto.getContractNo();
			//4是部分发货，mongoDb有多条记录
			if (contractStatus==4) {
				Criteria criteria = new Criteria().andOperator(
						Criteria.where("orderNumber").is(contractNo),
						Criteria.where("status").is(contractStatus),
						Criteria.where("insertTime").gt(DateUtil.formatDateAndTime(date)));
				Query query = new Query(criteria);
				recordList = mongoTemplate.find(query, OrderRecord.class);
				if (recordList.size()==0) {
					updateFlag = true;
				}
			}else {
				Criteria criteria = new Criteria().andOperator(
						Criteria.where("orderNumber").is(contractNo),
						Criteria.where("status").is(contractStatus),
						Criteria.where("insertTime").lte(DateUtil.formatDateAndTime(date))
						);
				Query query = new Query(criteria);
				recordList = mongoTemplate.find(query, OrderRecord.class);
				if (recordList.size()>0) {
					updateFlag = true;
				}
			}
			if (updateFlag) {
				//1：更新合同状态
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				contract.setContractStatus(6);
				contract.setUpdateTime(new Date());
				b2bContractDao.updateContract(contract);
				//2：更新子合同状态
				B2bContractGoods contractGoods = new B2bContractGoods();
				contractGoods.setContractNo(contractNo);
				contractGoods.setUpdateTime(new Date());
				contractGoods.setContractStatus(6);
				b2bContractGoodsDao.updateContractGoods(contractGoods);
				//3：存合同日志
				B2bOrder order = new B2bOrder();
				order.setOrderNumber(contractNo);
				order.setOrderStatus(6);
				b2bOrderRecordService.insertOrderRecord(null,order,OrderRecordType.CUPDATE.toString(),"合同号:"+contractNo+"，合同待收货已超过"+days+"天,系统自动完成收货;");
			}
		}
	}
	
}
