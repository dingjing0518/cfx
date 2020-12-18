package cn.cf.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import cn.cf.common.Constant;
import cn.cf.common.OrderRecordType;
import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.dao.B2bContractDaoEx;
import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.B2bOrderDaoEx;
import cn.cf.dao.B2bOrderGoodsDaoEx;
import cn.cf.dao.LgCompanyDao;
import cn.cf.dao.LogisticsModelDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.B2bContractDtoEx;
import cn.cf.entity.B2bContractGoodsDtoMa;
import cn.cf.entity.B2bCorrespondenceInfoEx;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.entity.ContractGoodsSync;
import cn.cf.entity.ContractStatusSync;
import cn.cf.entity.ContractSync;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.SubContractGoodsSync;
import cn.cf.entity.SubContractSync;
import cn.cf.entity.SupplierInfo;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bAddress;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bOrderGoods;
import cn.cf.service.B2bAddressService;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bCustomerManagementService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bPaymentService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.B2bWareService;
import cn.cf.service.CmsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class B2bOrderServiceImpl implements B2bOrderService {
	private final static Logger logger = LoggerFactory.getLogger(B2bOrderServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bOrderDaoEx b2bOrderDaoEx;

	@Autowired
	private B2bOrderGoodsDaoEx b2bOrderGoodsDaoEx;

	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;

	@Autowired
	private B2bStoreService b2bStoreService;

	@Autowired
	private B2bCompanyService b2bCompanyService;

	@Autowired
	private B2bContractDaoEx b2bContractDaoEx;

	@Autowired
	private B2bContractGoodsDaoEx b2bContractGoodsDaoEx;

	@Autowired
	private B2bCustomerManagementService b2bCustomerManagementService;

	@Autowired
	private B2bGoodsService b2bGoodsService;
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	@Autowired
	private LogisticsModelDao logisticsModelDao;
	
	@Autowired
	private LgCompanyDao lgcompanyDao;
	
	@Autowired
	private B2bPaymentService b2bPaymentService;
	
	@Autowired
	private B2bWareService wareService;

	@Autowired
	private B2bAddressService b2bAddressService;
	
	@Autowired
	private CmsService cmsService;
	
	@Override
	public B2bOrderDtoEx getByOrderNumber(String orderNumber) {
		return b2bOrderDaoEx.getByOrderNumberEx(orderNumber);
	}

	// 根据总订单状态以及总订单其他信息
	private void modifyOrderStatus(String orderNumber,int orderStatus) {
		B2bOrderDto order = b2bOrderDaoEx.getByOrderNumber(orderNumber);
		StringBuffer sb = new StringBuffer();
		// 计算总订单状态
			B2bOrderDto orderDto = new B2bOrderDto();
			orderDto.setOrderNumber(orderNumber);
            if(orderStatus == -1){
            	orderDto.setActualAmount(0d);
            }else{
            	List<B2bOrderGoodsDto> goodsDtoList = b2bOrderGoodsDaoEx.getByGoodsOrderNumber(orderNumber);
            	Double allOrderGoodsPresentTotalPrice=0d;
            	Double allOrderGoodsPresentTotalFreight=0d;
            	B2bOrderGoodsDtoMa goodsDtoMa = new B2bOrderGoodsDtoMa();
            	CfGoods cfGoods = new CfGoods();
            	if (null!=goodsDtoList && goodsDtoList.size()>0) {
            		for (B2bOrderGoodsDto b2bOrderGoodsDto : goodsDtoList) {
            			goodsDtoMa.UpdateMine(b2bOrderGoodsDto);
            			cfGoods = goodsDtoMa.getCfGoods();
            			Double temp = b2bOrderGoodsDto.getPresentPrice()+cfGoods.getAdminFee()+cfGoods.getPackageFee()+cfGoods.getLoadFee();
            			allOrderGoodsPresentTotalPrice += ArithUtil.mul(temp, b2bOrderGoodsDto.getAfterWeight()>0?b2bOrderGoodsDto.getAfterWeight():b2bOrderGoodsDto.getWeight());
            			allOrderGoodsPresentTotalFreight += b2bOrderGoodsDto.getPresentTotalFreight();
    				}
				}
    			if (null!=goodsDtoList && goodsDtoList.size()>0) {
    				// 总运费+总价格
    				orderDto.setActualAmount(ArithUtil.round(allOrderGoodsPresentTotalPrice, 2) + allOrderGoodsPresentTotalFreight);
    				sb.append(" 总订单修改后实际金额【运费:").append(allOrderGoodsPresentTotalFreight).append(" 实际总价格:").append(allOrderGoodsPresentTotalPrice).append("】").append(orderDto.getActualAmount());
    			}else{
    				orderDto.setActualAmount(0d);
    				orderStatus=-1;
    			}
            }
            orderDto.setOrderStatus(orderStatus);
			// 通过sum计算不为-1的子订单的价格
			if(orderStatus >= 3){
				//如果没有支付方式则默认设置为线下支付
				if(null == order.getPaymentType() ||  order.getPaymentType()==0){
						B2bPaymentDto dto = b2bPaymentService.getByType(1);
						if(null != dto ){
							orderDto.setPaymentType(dto.getType());
							orderDto.setPaymentName(dto.getName());
							orderDto.setPaymentTime(new Date());
					}
				}
				if(null == order.getReceivablesTime()){
					orderDto.setReceivablesTime(new Date());
				}
			}
			b2bOrderDaoEx.updateOrderStatusAndAmount(orderDto);
			saveOrderLog(orderDto, order);
			logger.error("总订单：" + orderNumber + "\r\n" + sb.toString());
	}
	
	
	private void saveOrderLog(B2bOrderDto orderDto, B2bOrderDto order) {
		try {
			// 总订单录入改价记录
			OrderRecord or = new OrderRecord();
			String content = null;
			or.setId(KeyUtils.getUUID());
			or.setOrderNumber(orderDto.getOrderNumber());
			if (null != order) {
				if (null != orderDto.getActualAmount()) {
					if (!order.getActualAmount().toString().equals(orderDto.getActualAmount().toString())) {
						content = OrderRecordType.UPDATE.toString() + "订单号:" + order.getOrderNumber() + "的订单总价由"
								+ ArithUtil.roundBigDecimal(new BigDecimal(order.getActualAmount()), 2) + "变更为:"
								+ ArithUtil.roundBigDecimal(new BigDecimal(orderDto.getActualAmount()), 2);
						or.setContent(content);
						or.setInsertTime(DateUtil.getDateFormat(new Date()));
						mongoTemplate.save(or);
					}
				}
				OrderRecord or1 = new OrderRecord();
				String content1 = null;
				or1.setId(KeyUtils.getUUID());
				or1.setOrderNumber(order.getOrderNumber());
				or1.setInsertTime(DateUtil.getDateFormat(new Date()));
				content1 = OrderRecordType.UPDATE.toString() + "订单号:" + order.getOrderNumber()+ getOrderStatusStr(orderDto.getOrderStatus());
				or1.setStatus(orderDto.getOrderStatus());
				StringBuffer sb = new StringBuffer();
				String str = "";
				if (orderDto.getOrderStatus() == 5 || orderDto.getOrderStatus() == 4) {
					List<B2bOrderGoodsDtoEx> ogs = b2bOrderGoodsDaoEx.getByOrderNumberEx(order.getOrderNumber());
					if (null != ogs) {
						for (B2bOrderGoodsDto b2bOrderGoodsDto : ogs) {
							sb.append("子订单号:").append(b2bOrderGoodsDto.getChildOrderNumber()).append(":")
									.append("已发货箱数：").append(b2bOrderGoodsDto.getBoxesShipped()).append("已发货重量：")
									.append(new BigDecimal(b2bOrderGoodsDto.getWeightShipped().toString()).toPlainString()).append("\r\n");
							str += sb.toString();
						}
					}
					content1 += "\r\n" + str;
				}
				or1.setContent(content1);
				mongoTemplate.save(or1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveContractLog(String contractNo,Integer status) {
		try {
			// 总订单录入改价记录
			OrderRecord or = new OrderRecord();
			String content = null;
			or.setId(KeyUtils.getUUID());
			or.setOrderNumber(contractNo);
			or.setInsertTime(DateUtil.getDateFormat(new Date()));
			if(null != status && 1 == status){
				content = OrderRecordType.CUPDATE.toString() + "合同号:" + contractNo + "创建成功,CRM同步状态为待付款";
			}
			if(null != status && 3 == status){
				content = OrderRecordType.CUPDATE.toString() + "合同号:" + contractNo + "审核通过,CRM同步状态为待发货";
			}
			if(null != status && (4 == status || 5 == status)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("contractNo", contractNo);
				List<B2bContractGoodsDto> list = b2bContractGoodsDaoEx.searchListExt(map);
				StringBuffer sb = new StringBuffer();
				if(null != list && list.size()>0){
					sb.append(OrderRecordType.CUPDATE.toString() + "合同号:" + contractNo +"发货,CRM同步状态为"+(status==4?"部分发货":"全部发货")).append("\r\n");
					for (B2bContractGoodsDto dto : list) {
						sb.append("合同序号:").append(dto.getDetailNumber()).append(":")
						.append("已发货箱数：").append(dto.getBoxesShipped()).append("已发货重量：")
						.append(new BigDecimal(dto.getWeightShipped().toString()).toPlainString()).append("\r\n");
					}	
				}
				content = sb.toString();
			}
			if(null != status && -1 == status){
				content = OrderRecordType.CUPDATE.toString() + "合同号:" + contractNo +"审核不通过,CRM同步状态为已关闭";
			}
			or.setContent(content);
			or.setStatus(status);
			mongoTemplate.save(or);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getOrderStatusStr(Integer orderStatus) {
		String status = "";
		switch (orderStatus.intValue()) {
		case 0:
			status = "待审核";
			break;
		case 1:
			status = "待付款";
			break;
		case 2:
			status = "确认付款";
			break;
		case 3:
			status = "确认收款";
			break;
		case 4:
			status = "已发货";
			break;
		case 5:
			status = "部分发货";
			break;
		case 6:
			status = "已完成";
			break;
		case -1:
			status = "订单取消";
			break;
		default:
			break;
		}
		return status;
	}

	/**
	 * 保存子订单日志
	 * @param orderGoodsDto  原来
	 * @param orderGoods  变更后
	 */
	private void saveChargeLog(B2bOrderGoodsDtoEx orderGoodsDto) {
		try {
			OrderRecord or = new OrderRecord();
			String content = null;
			or.setId(KeyUtils.getUUID());
			or.setOrderNumber(orderGoodsDto.getOrderNumber());
			or.setChildOrderNumber(orderGoodsDto.getChildOrderNumber());
			Boolean flag = false;
			content = OrderRecordType.UPDATE.toString() + "子订单:" + orderGoodsDto.getChildOrderNumber();
			if(null != orderGoodsDto.getOrderStatus() && orderGoodsDto.getOrderStatus() == 1 || orderGoodsDto.getOrderStatus() == 0){
				if(null != orderGoodsDto.getAfterBoxes() && orderGoodsDto.getAfterBoxes() >0 ){
					content +="需求箱数修改为:"+orderGoodsDto.getAfterBoxes();
				}
				if(null != orderGoodsDto.getAfterWeight() && orderGoodsDto.getAfterWeight() >0d ){
					content +="需求重量修改为:"+orderGoodsDto.getAfterWeight();
				}
				flag = true;
			}
			if (null != orderGoodsDto.getPresentPrice() && null != orderGoodsDto.getBeforePrice()) {
				if (orderGoodsDto.getPresentPrice().toString().equals(orderGoodsDto.getBeforePrice().toString())) {
				} else {
					content += " 实际单价由:" + ArithUtil.roundBigDecimal(new BigDecimal(orderGoodsDto.getBeforePrice()), 2)
							+ " 变更为:" + ArithUtil.roundBigDecimal(new BigDecimal(orderGoodsDto.getPresentPrice()), 2);
					flag = true;
				}
			}
			if (null != orderGoodsDto.getPresentFreight() && null != orderGoodsDto.getBeforeFreight()) {
				if (orderGoodsDto.getPresentFreight().toString().equals(orderGoodsDto.getBeforeFreight().toString())) {
				} else {
					content += ",实际运费单价由:"
							+ ArithUtil.roundBigDecimal(new BigDecimal(orderGoodsDto.getBeforeFreight()), 2) + " 变更为:"
							+ ArithUtil.roundBigDecimal(new BigDecimal(orderGoodsDto.getPresentFreight()), 2);
					flag = true;
				}
			}
			if (null != orderGoodsDto.getPresentTotalFreight() && null != orderGoodsDto.getBeforeTotalFreight()) {
				if (orderGoodsDto.getPresentTotalFreight().toString().equals(orderGoodsDto.getBeforeTotalFreight().toString())) {
				} else {
					content += ",实际运费总价由:"
							+ ArithUtil.roundBigDecimal(new BigDecimal(orderGoodsDto.getBeforeTotalFreight()), 2)
							+ " 变更为:"
							+ ArithUtil.roundBigDecimal(new BigDecimal(orderGoodsDto.getPresentTotalFreight()), 2);
					flag = true;
				}
			}
			if (flag) {
				or.setContent(content);
				or.setInsertTime(DateUtil.getDateFormat(new Date()));
				mongoTemplate.save(or);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String updateContract(ContractSync contractSync) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		if (contractSync != null && contractSync.getContractNo() != null && !"".equals(contractSync.getContractNo())) {
			// 先查询，如果存在不添加
			B2bContractDto b2bContractDto = b2bContractDaoEx.getByContractNo(contractSync.getContractNo());
			logger.info("updateContract-contractSync_start:" + JsonUtils.convertToString(contractSync));
			if (b2bContractDto == null) {
				if (contractSync.getApprovalstatus() != null && contractSync.getApprovalstatus() == 1) {
					contractSync.setApprovalstatus(Constant.ORDER_STATUS_THREE);
				}
				if (contractSync.getApprovalstatus() != null && contractSync.getApprovalstatus() == 2) {
					contractSync.setApprovalstatus(Constant.ORDER_STATUS_MINUS_ONE);
				}
				// 设置model
				B2bContract model = setContractModel(contractSync);
				List<SubContractSync> list = contractSync.getItems();
				if (list != null && list.size() > 0) {
					B2bContractGoods contractGoodsModel = new B2bContractGoods();
					logger.info("updateContract-SubContractSync_start:" + JsonUtils.convertToString(list));
					for (SubContractSync subContractSync : list) {
						// 设置model
						contractGoodsModel = setContractGoodsModel(contractSync, subContractSync,model);
						b2bContractGoodsDaoEx.insert(contractGoodsModel);
					}
					B2bContractGoodsDtoMa contractGoodsDtoMa =  new B2bContractGoodsDtoMa();
					B2bContractGoodsDto contractGoodsDto = new B2bContractGoodsDto();
					BeanUtils.copyProperties(contractGoodsModel, contractGoodsDto);
					contractGoodsDtoMa.UpdateMine(contractGoodsDto);
					CfGoods cfGoods = contractGoodsDtoMa.getCfGoods();
					System.out.println("1111111111111111111111111111111:"+cfGoods.getPk());
					B2bGoodsDto goods = b2bGoodsDaoEx.getByPk(cfGoods.getPk());
					if(null != goods){
						B2bGoodsDtoMa goodsDtoMa = new B2bGoodsDtoMa();
						goodsDtoMa.UpdateMine(goods);
						CfGoods tempCfGoods = goodsDtoMa.getCfGoods();
						model.setPlantPk(tempCfGoods.getPlantPk());
						model.setPlantName(tempCfGoods.getPlantName());
					}
				}
				b2bContractDaoEx.insert(model);
				saveContractLog(model.getContractNo(), model.getContractStatus());
			} else {
				result = RestCode.CODE_0000.toJson();
			}
		}
		logger.info("updateContract_end:" + result);
		return result;
	}

	private B2bContractGoods setContractGoodsModel(ContractSync contractSync, SubContractSync subContractSync,B2bContract contract) {
		CfGoods cfGoods =  new CfGoods();
		B2bContractGoods contractGoods = new B2bContractGoods();
		contractGoods.setChildOrderNumber(contractSync.getContractNo()+subContractSync.getDetailNumber());
		contractGoods.setContractNo(contractSync.getContractNo());
		cfGoods.setAdminFee(subContractSync.getAdminFee());
		contractGoods.setBasicPrice(subContractSync.getBasicPrice());
		cfGoods.setBatchNumber(subContractSync.getBatchNumber());
		contractGoods.setBoxes(subContractSync.getBoxes());
		contractGoods.setContractPrice(subContractSync.getContractPrice());
		contractGoods.setDetailNumber(Integer.valueOf(subContractSync.getDetailNumber()));
		contractGoods.setDiscount(subContractSync.getDiscount());
		cfGoods.setDistinctNumber(subContractSync.getDistinctNumber());
		contractGoods.setFreight(subContractSync.getFreight());
		cfGoods.setGradeName(subContractSync.getLevel());
		cfGoods.setLoadFee(subContractSync.getLoadFee());
		cfGoods.setPackageFee(subContractSync.getPackageFee());
		cfGoods.setPackNumber(subContractSync.getPackageNumber());
		contractGoods.setWeight(subContractSync.getWeight());
		contractGoods.setIsDelete(1);
		contractGoods.setInsertTime(new Date());
		contractGoods.setUpdateTime(new Date());
		contractGoods.setWeightShipped(0d);
		contractGoods.setBoxesShipped(0);
		if(null != contract.getCarrier() && !"".equals(contract.getCarrier())){
			contractGoods.setCarrier(contract.getCarrier());
			contractGoods.setCarrierPk(contract.getCarrierPk());
		}
		B2bGoodsDtoEx gdto = new B2bGoodsDtoEx();
		gdto.setPk1(cfGoods.getBatchNumber());
		gdto.setPk3(cfGoods.getGradeName());
		gdto.setPk4(cfGoods.getPackNumber());
		gdto.setPk2(cfGoods.getDistinctNumber());
		gdto.setStorePk(contract.getStorePk());
		B2bGoodsDto goods = b2bGoodsService.searchGoodsIs(gdto);
		if(null != goods){
			B2bGoodsDtoMa goodsDtoMa = new B2bGoodsDtoMa();
			goodsDtoMa.UpdateMine(goods);
			CfGoods cfGoodsTemp= goodsDtoMa.getCfGoods();
			cfGoods.setPk(goods.getPk());
			cfGoods.setPlantName(cfGoodsTemp.getPlantName());
			cfGoods.setProductName(cfGoodsTemp.getProductName());
			cfGoods.setVarietiesName(cfGoodsTemp.getVarietiesName());
			cfGoods.setSeedvarietyName(cfGoodsTemp.getSeedvarietyName());
			cfGoods.setSpecName(cfGoodsTemp.getSpecName());
			cfGoods.setSeriesName(cfGoodsTemp.getSeriesName());
			cfGoods.setSpecifications(cfGoodsTemp.getSpecifications());
			cfGoods.setWarehouseType(cfGoodsTemp.getWarehouseType());
			contractGoods.setBrandName(goods.getBrandName());
			contractGoods.setContractStatus(contract.getContractStatus());
			contractGoods.setGoodsPk(goods.getPk());
			contractGoods.setGoodsType(goods.getType());
			if (null != cfGoodsTemp.getWarehouseNumber() && !"".equals(cfGoodsTemp.getWarehouseNumber())) {
				cfGoods.setWarehouseNumber(cfGoodsTemp.getWarehouseNumber());
				B2bWarehouseNumberDto wareNum=wareService.getOneByNumber(goods);
				if(null!=wareNum){
					cfGoods.setWarehouseType(null==wareNum.getType()||"".equals(wareNum.getType())?"正常类型":wareNum.getType());
				} 
			}
		}
		contractGoods.setGoodsInfo(JSON.toJSONString(cfGoods));
		contractGoods.setLogisticsPk(subContractSync.getLogisticsPk());
		contractGoods.setLogisticsStepInfoPk(subContractSync.getLogisticsStepInfoPk());
		contractGoods.setBlock(Block.CF.getValue());
		return contractGoods;
	}

	private B2bContract setContractModel(ContractSync contractSync) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		B2bContract model = new B2bContract();
		model.setContractStatus(1);
		AddressInfo addressInfo = new AddressInfo();
		PurchaserInfo purchaserInfo = new PurchaserInfo();
		SupplierInfo supplierInfo = new SupplierInfo();
		model.setCarrier(contractSync.getCarrier());
		model.setContractCount(contractSync.getContractCount());
		model.setContractNo(contractSync.getContractNo());
		model.setContractRate(contractSync.getContractRate());
		model.setContractSource(2);
		model.setCovenant(contractSync.getCovenant());
		model.setDays(contractSync.getDays());
		if (contractSync.getEndTime() != null && !"".equals(contractSync.getEndTime())) {
			Date utilDate = sdf.parse(contractSync.getEndTime());
			model.setEndTime(utilDate);
		}
		model.setPriceType(contractSync.getPriceType());
		
		purchaserInfo.setInvoiceName(contractSync.getRequireAccount());
		supplierInfo.setSupplierName(contractSync.getSaleCompany());
		model.setSaleDepartment(contractSync.getSaleDepartment());
		model.setSalesman(contractSync.getSalesman());
		model.setSalesmanNumber(contractSync.getSalesmanNumber());
		if (contractSync.getStartTime() != null && !"".equals(contractSync.getStartTime())) {
			Date utilDate = sdf.parse(contractSync.getStartTime());
			model.setStartTime(utilDate);
		}
		model.setSupplementary(contractSync.getSupplementary());
		model.setPlantName(contractSync.getPlantName());

		//供应商信息
		B2bCompanyDto company = b2bCompanyService.getByName(supplierInfo.getSupplierName());
		if(null != company){
			model.setSupplierPk(company.getPk());
			supplierInfo.setSupplierPk(company.getPk());
			supplierInfo.setSupplierName(company.getName());
			supplierInfo.setOrganizationCode(company.getOrganizationCode());
			supplierInfo.setContacts(null==company.getContacts()?"":company.getContacts());
			supplierInfo.setContactsTel(null==company.getContactsTel()?"":company.getContactsTel());
			B2bStoreDto sdto = null;
			if("-1".equals(company.getParentPk())){
				sdto = b2bStoreService.getByCompanyPk(company.getPk());
			}else{
				sdto = b2bStoreService.getByCompanyPk(company.getParentPk());
			}
			if(null != sdto){
				model.setStorePk(sdto.getPk());
				model.setStoreName(sdto.getName());
				//业务员pk
				if(null != contractSync.getSalesman() && null != contractSync.getSalesmanNumber()
						){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("employeeNumber", contractSync.getSalesmanNumber());
					map.put("employeeName", contractSync.getSalesman());
					map.put("companyPk", sdto.getCompanyPk());
					List<B2bMemberDto> list = b2bMemberService.getByEmployeeByCompany(map);
					if(null != list && list.size() >0){
						model.setEmployeePk(list.get(0).getPk());
					}
				}
			}
		}
		
		//采购商信息
		purchaserInfo.setPurchaserName(contractSync.getRequireAccount());
		B2bCompanyDto purchaser = b2bCompanyService.getByName(contractSync.getRequireAccount());
		if(null != purchaser){
			model.setPurchaserPk(purchaser.getPk());
			purchaserInfo.setPurchaserPk(purchaser.getPk());
			purchaserInfo.setPurchaserName(null==purchaser.getName()?"":purchaser.getName());
			purchaserInfo.setContacts(null==purchaser.getContacts()?"":purchaser.getContacts());
			purchaserInfo.setContactsTel(null==purchaser.getContactsTel()?"":purchaser.getContactsTel());
			purchaserInfo.setInvoicePk(null==purchaser.getPk()?"":purchaser.getPk());
			purchaserInfo.setInvoiceName(null==purchaser.getName()?"":purchaser.getName());
			purchaserInfo.setInvoiceBankName(null==purchaser.getBankName()?"":purchaser.getBankName());
			purchaserInfo.setInvoiceBankAccount(null==purchaser.getBankAccount()?"":purchaser.getBankAccount());
			purchaserInfo.setInvoiceRegAddress(null==purchaser.getRegAddress()?"":purchaser.getRegAddress());
			purchaserInfo.setInvoiceRegPhone(null==purchaser.getContactsTel()?"":purchaser.getContactsTel());
			purchaserInfo.setInvoiceTaxidNumber(null==purchaser.getOrganizationCode()?"":purchaser.getOrganizationCode());
			purchaserInfo.setOrganizationCode(null==purchaser.getOrganizationCode()?"":purchaser.getOrganizationCode());
		}
		
		//收货地址信息
		addressInfo.setProvinceName(contractSync.getProvinceName());
		addressInfo.setCityName(contractSync.getCityName());
		addressInfo.setAreaName(contractSync.getAreaName());
		addressInfo.setTownName(contractSync.getTownName());
		addressInfo.setAddress(contractSync.getToAddress());
		B2bAddress address = new B2bAddress();
		address.setCompanyPk(purchaser.getPk());
		address.setProvinceName(contractSync.getProvinceName());
		address.setCityName(contractSync.getCityName());
		address.setAreaName(contractSync.getAreaName());
		address.setTownName(contractSync.getTownName());
		address.setAddress(contractSync.getToAddress());
		address.setIsDelete(1);
		address = b2bAddressService.getAddressByDetails(address);
		addressInfo.setAddressPk(null == address.getPk() ? "" : address.getPk());
		addressInfo.setContacts(null == address.getContacts() ? "" : address.getContacts());
		addressInfo.setContactsTel(null == address.getContactsTel() ? "" : address.getContactsTel());
		addressInfo.setSigningCompany(null == address.getSigningCompany() ? "" : address.getSigningCompany());

		model.setPurchaserType(2);
		model.setContractType(1);
		model.setOrderAmount(contractSync.getContractAmount());
		model.setTotalAmount(contractSync.getContractAmount());
		model.setIsDelete(1);
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		LogisticsModelDto lmDto = null;
		if(null != contractSync.getLogisticsModel() && "自提".equals(contractSync.getLogisticsModel())){
			lmDto = logisticsModelDao.getByType("3");
		}else{
			lmDto = logisticsModelDao.getByType("2");
		}
		//承运商
		if(null != lmDto){
			model.setLogisticsModelPk(lmDto.getPk());
			model.setLogisticsModel(lmDto.getName());
			model.setLogisticsModelType(lmDto.getType());
		}
		if(null != contractSync.getCarrier() && !"".equals(contractSync.getCarrier())){
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("name", contractSync.getCarrier());
			map.put("isDelete", 1);
			List<LgCompanyDto> lgcompanyList = lgcompanyDao.searchList(map);
			if(null !=lgcompanyList && lgcompanyList.size() >0){
				model.setCarrierPk(lgcompanyList.get(0).getPk());
			}
		}
		model.setSource(4);
		model.setAddressInfo(JSONObject.toJSONString(addressInfo));
		model.setPurchaserInfo(JSONObject.toJSONString(purchaserInfo));
		model.setSupplierInfo(JSONObject.toJSONString(supplierInfo));
		model.setBlock(Block.CF.getValue());
		return model;
	}

	/**
	 * 合同发货
	 */
	@Override
	public String shipmentContractGoods(ContractGoodsSync contractSync) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		String contractNo = contractSync.getContractNo();
		List<SubContractGoodsSync> list = contractSync.getItems();
		if (contractNo != null && !"".equals(contractNo) && list != null && list.size() > 0) {
			B2bContractDtoEx dtoEx = b2bContractDaoEx.getContractByNo(contractSync.getContractNo());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contractNo", contractNo);
			for (SubContractGoodsSync subContractGoodsSync : list) {
				map.put("detailNumber", subContractGoodsSync.getDetailNumber());
				List<B2bContractGoodsDto> contractGoodsList = b2bContractGoodsDaoEx.searchListExt(map);
				Double weightShipped = 0d;
				if (contractGoodsList != null && contractGoodsList.size() > 0) {
					B2bContractGoodsDto dto = contractGoodsList.get(0);
					weightShipped = (null==dto.getWeightShipped()?0d:dto.getWeightShipped())+subContractGoodsSync.getWeight();
					weightShipped =  ArithUtil.round(weightShipped, 5);
					map.put("weightShipped",weightShipped);
					if(dto.getWeight()<=weightShipped){
						map.put("contractStatus", 5);
					}else{
						map.put("contractStatus", 4);
					}
					//如果没有发货箱数则根据总重量*1000/箱重计算
					if (null == subContractGoodsSync.getBoxes() || 0 == subContractGoodsSync.getBoxes()) {
						B2bContractGoodsDtoMa contractGoodsDtoMa = new B2bContractGoodsDtoMa();
						contractGoodsDtoMa.UpdateMine(dto);
						B2bGoodsDto gdto = b2bGoodsDaoEx.getByPk(contractGoodsDtoMa.getCfGoods().getPk());
						if(null != gdto && null !=gdto.getTareWeight() && gdto.getTareWeight() >0d){
							subContractGoodsSync.setBoxes((int) Math.ceil(ArithUtil.div(subContractGoodsSync.getWeight()*1000, gdto.getTareWeight())));
						}
					}
					map.put("boxesShipped", (null==dto.getBoxesShipped()?0:dto.getBoxesShipped())+subContractGoodsSync.getBoxes());
					logger.info("updateContractGoods-subContractSync_contractGoods:" + JsonUtils.convertToString(map));
					b2bContractGoodsDaoEx.updateContractGoodsDto(map);
				}
				map.remove("detailNumber");
				map.remove("boxesShipped");
				map.remove("weightShipped");
			}
			//修改总订单状态
			map.put("flag", "no");
			List<B2bContractGoodsDto> contractGoodsList = b2bContractGoodsDaoEx.searchListExt(map);
			B2bContractDto contractDto = new B2bContractDto();
			contractDto.setContractNo(contractNo);
			if(null == contractGoodsList || contractGoodsList.size() == 0){
				contractDto.setContractStatus(5);
			}else{
				contractDto.setContractStatus(4);
			}
			//如果没有支付方式则默认设置为线下支付
			if(null == dtoEx.getPaymentType()|| 0 == dtoEx.getPaymentType()){
					B2bPaymentDto dto = b2bPaymentService.getByType(1);
					if(null != dto ){
						contractDto.setPaymentName(dto.getName());
						contractDto.setPaymentType(dto.getType());
						contractDto.setPaymentTime(new Date());
					}
			}
			logger.info("updateContractGoods-subContractSync_contract:" + JsonUtils.convertToString(contractDto));
			b2bContractDaoEx.updateContractDto(contractDto);
			saveContractLog(contractDto.getContractNo(), contractDto.getContractStatus());
		}
		return result;
	}

	@Override
	public String updateContractStatus(ContractStatusSync contractSync) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bContractDto contractDto = new B2bContractDto();
		contractDto.setContractNo(contractSync.getContractNo());
		B2bContractDtoEx dtoEx = b2bContractDaoEx.getContractByNo(contractSync.getContractNo());
		//合同在发货后调用无修改任何状态
		if(null !=dtoEx && dtoEx.getContractStatus() <= 3){
			if (contractSync.getApprovalstatus() != null && contractSync.getApprovalstatus() == 1) {
				contractDto.setContractStatus(Constant.ORDER_STATUS_THREE);
				//审核通过 如果没有支付方式 默认为线下付款
				if(null == dtoEx.getPaymentType() || 0==dtoEx.getPaymentType()){
					B2bPaymentDto dto = b2bPaymentService.getByType(1);
					if(null != dto ){
						contractDto.setPaymentName(dto.getName());
						contractDto.setPaymentType(dto.getType());
						contractDto.setPaymentTime(new Date());
					}
				}
				logger.info("updateContractStatus-ContractStatusSync_start:" + JsonUtils.convertToString(contractSync));
				contractDto.setReceivablesTime(new Date());
				b2bContractDaoEx.updateContractDto(contractDto);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("contractNo", contractSync.getContractNo());
				map.put("contractStatus", contractDto.getContractStatus());
				b2bContractGoodsDaoEx.updateContractGoodsDto(map);
				saveContractLog(contractDto.getContractNo(), contractDto.getContractStatus());
			}
			if (contractSync.getApprovalstatus() != null && contractSync.getApprovalstatus() == 2) {
				contractDto.setContractStatus(Constant.ORDER_STATUS_MINUS_ONE);
				logger.info("updateContractStatus-ContractStatusSync_start:" + JsonUtils.convertToString(contractSync));
				b2bContractDaoEx.updateContractDto(contractDto);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("contractNo", contractSync.getContractNo());
				map.put("contractStatus", contractDto.getContractStatus());
				b2bContractGoodsDaoEx.updateContractGoodsDto(map);
				saveContractLog(contractDto.getContractNo(), contractDto.getContractStatus());
			}
		}
		logger.info("updateContractStatus_end:" + result);
		return result;
	}
	

	@Override
	public String updateCorrespondenceInfo(B2bCorrespondenceInfoEx correspondenceInfoEx, B2bTokenDto tokenDto) {
		String result = RestCode.CODE_0000.toJson();
		List<B2bCorrespondenceInfoEx> list = new ArrayList<B2bCorrespondenceInfoEx>();
		B2bCompanyDto companyDto = null;
		try {
			if (correspondenceInfoEx != null && correspondenceInfoEx.getCorrespondenceInfo() != null
					&& correspondenceInfoEx.getCorrespondenceInfo().size() > 0) {
				// 先根据id查询，查询不到根据name，organizationCode查询
				if (correspondenceInfoEx.getId() != null && !correspondenceInfoEx.getId().equals("")) {
					companyDto = b2bCompanyService.getByPk(correspondenceInfoEx.getId());
				}
				if (companyDto != null) {
					list.add(correspondenceInfoEx);
				} else {
					Map<String, Object> param = new HashMap<>();
					param.put("name", correspondenceInfoEx.getName());
					param.put("organizationCode", correspondenceInfoEx.getOrganizationCode());
					companyDto = b2bCompanyService.getCompanyInfoByMap(param);
					if (companyDto != null) {
						correspondenceInfoEx.setId(companyDto.getPk());
						list.add(correspondenceInfoEx);
					}
				}
			} else {
				logger.info(
						"b2bCorrespondenceInfoEx:is null or b2bCorrespondenceInfoEx.getCorrespondenceInfo().size() <0");
			}
			if (list != null && list.size() > 0) {
				b2bCustomerManagementService.insertCustomerSale(list, tokenDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_I002.toJson();
		}

		return result;
	}

	@Override
	public String editCorrespondenceInfo(B2bCorrespondenceInfoEx correspondenceInfoEx,B2bTokenDto b2btoken) {
		String result = RestCode.CODE_0000.toJson();
		List<B2bCorrespondenceInfoEx> list = new ArrayList<B2bCorrespondenceInfoEx>();
		B2bCompanyDto companyDto = null;
		try {
			if (correspondenceInfoEx != null && correspondenceInfoEx.getCorrespondenceInfo() != null
					&& correspondenceInfoEx.getCorrespondenceInfo().size() > 0) {
				correspondenceInfoEx.setStorePk(b2btoken.getStorePk());
				// 先根据id查询，查询不到根据name，organizationCode查询
				if (correspondenceInfoEx.getId() != null && !correspondenceInfoEx.getId().equals("")) {
					companyDto = b2bCompanyService.getByPk(correspondenceInfoEx.getId());
				}
				if (companyDto != null) {
					list.add(correspondenceInfoEx);
				} else {
					Map<String, Object> param = new HashMap<>();
					param.put("name", correspondenceInfoEx.getName());
					param.put("organizationCode", correspondenceInfoEx.getOrganizationCode());
					companyDto = b2bCompanyService.getCompanyInfoByMap(param);
					if (companyDto != null) {
						correspondenceInfoEx.setId(companyDto.getPk());
						list.add(correspondenceInfoEx);
					}
				}
			} else {
				logger.info(
						"b2bCorrespondenceInfoEx:is null or b2bCorrespondenceInfoEx.getCorrespondenceInfo().size() <0");
			}
			if (list != null && list.size() > 0) {
				b2bCustomerManagementService.editCustomerSale(list,b2btoken);
			} else {
//				result = RestCode.CODE_I002.toJson();
				logger.info("++++++++++++++++++++++公司不存在");
			}
		} catch (Exception e) {
			logger.error("editCorrespondenceInfo",e);
			result = RestCode.CODE_I002.toJson();
		}
		return result;
	}

	@Override
	public void updateOrderAndOrderGoods(String orderNumber,
			Integer orderStatus, List<B2bOrderGoodsDtoEx> newOrderGoods) {
		//修改子订单
		if(null != newOrderGoods && newOrderGoods.size()>0){
			for(B2bOrderGoodsDtoEx ogEx : newOrderGoods){
				B2bOrderGoods order  = new B2bOrderGoods();
				order.UpdateDTO(ogEx);
				int result = b2bOrderGoodsDaoEx.updateOrderGoods(order);
				if (result > 0) {
					saveChargeLog(ogEx);
				}
			}
			//修改主订单
			modifyOrderStatus(orderNumber, orderStatus);
		}
		
	}

	@Override
	@Async
	public void afterCmsContract(String contractNo, Integer type, String block) {
		Map<String, Object> cmsmap = new HashMap<String,Object>();
		cmsmap.put("orderPk", contractNo);
		cmsmap.put("type", type);
		cmsmap.put("block", block);
		try {
			cmsService.insertSaleManByOrder(cmsmap);
		} catch (Exception e) {
			logger.info("++++++++++++++++++++++cms系统同步异常",e);
		}
		
	}
}
