package cn.cf.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.common.RestCode;
import cn.cf.constant.SmsCode;
import cn.cf.dao.LgArayacakMandateDaoEx;
import cn.cf.dao.LgCarDaoEx;
import cn.cf.dao.LgCompanyDaoEx;
import cn.cf.dao.LgDeliveryExceptionPicDaoEx;
import cn.cf.dao.LgDeliveryOrderDaoEx;
import cn.cf.dao.LgDeliveryOrderGoodsDaoEx;
import cn.cf.dao.LgDriverDaoEx;
import cn.cf.dao.LgLogisticsInvoicesDao;
import cn.cf.dao.LgMemberInvoicesDao;
import cn.cf.dao.LgTrackDetailDaoEx;
import cn.cf.dao.LgUserAddressDaoEx;
import cn.cf.dao.LgUserInvoiceDaoEx;
import cn.cf.dto.LgArayacakMandateDto;
import cn.cf.dto.LgCarDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgDeliveryOrderForB2BDto;
import cn.cf.dto.LgDeliveryOrderGoodsDto;
import cn.cf.dto.LgDriverDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgUserAddressDto;
import cn.cf.dto.LgUserInvoiceDto;
import cn.cf.dto.LogisticsLinePriceDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.dto.OrderDeliveryDto;
import cn.cf.dto.PageModelOrderVo;
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.entity.SearchLgLine;
import cn.cf.exception.ErrorParameterException;
import cn.cf.model.LgDeliveryExceptionPic;
import cn.cf.model.LgDeliveryOrder;
import cn.cf.model.LgDeliveryOrderGoods;
import cn.cf.model.LgLogisticsInvoices;
import cn.cf.model.LgMemberInvoices;
import cn.cf.model.LgTrackDetail;
import cn.cf.service.LgDeliveryOrderService;
import cn.cf.service.LgLineService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.SysService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
//@WebServlet(name="LgDeliveryOrderServiceExporter", urlPatterns={"/logistics/service/lgDeliveryOrder"})
public class LgDeliveryOrderServiceImpl  implements LgDeliveryOrderService {
	
	@Autowired
	private LgDeliveryOrderDaoEx lgDeliveryOrderDaoEx;
	
	@Autowired
	private LgDeliveryOrderGoodsDaoEx lgDeliveryOrderGoodsDaoEx;
	
	@Autowired
	private LgDeliveryExceptionPicDaoEx lgDeliveryExceptionPicDaoEx;
	
	@Autowired
	private LgTrackDetailDaoEx lgTrackDetailDaoEx;
	
	@Autowired
	private LgLineService lgLineService;
	
	@Autowired
	private LgUserInvoiceDaoEx lgUserInvoiceDaoEx;
	
	@Autowired
	private LgUserAddressDaoEx lgUserAddressDaoEx;
	
	@Autowired
	private LgMemberInvoicesDao lgMemberInvoicesDao;
	
	@Autowired
	private LgDriverDaoEx lgDriverDaoEx;
	
	@Autowired
	private LgCarDaoEx lgCarDaoEx;
	
	@Autowired
	SysService sysService;
	
	@Autowired
	private LgLogisticsInvoicesDao lgLogisticsInvoicesDao;

	@Autowired
	private LgCompanyDaoEx companyDaoEx;
	
	@Autowired
	private LgArayacakMandateDaoEx lgArayacakMandateDaoEx;
	
	@Autowired
	private CuccSmsService smsService;
	
	
	// 平台用户 我的订单
	@Override
	public PageModelOrderVo<OrderDeliveryDto> selectMemberDeliveryList(Map<String, Object> par) {
		PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
		par.put("orderName", "updateTime");
		par.put("orderType", "desc");
		List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryList(par);
		Integer amount = lgDeliveryOrderDaoEx.selectDeliveryCount(par);
		pm.setTotalCount(amount);
		pm.setDataList(dataList);
		if (par.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(par.get("start").toString()));
			pm.setPageSize(Integer.parseInt(par.get("limit").toString()));
		}
		par.put("orderStatus", 10);// 已关闭的数量
		pm.setHasClosedNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", 9);// 待付款的数量
		pm.setToPayNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", 8);// 待确认的数量
		pm.setToConfirmNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", 5);// 提货中的数量
		pm.setInDeliveryNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", 4);// 派送中的数量
		pm.setOutFordeliveryNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", 3);// 已签收的数量
		pm.setBeReceivedNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", 2);// 已取消的数量
		pm.setCanceledNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		par.put("orderStatus", null);
		par.put("orderStatuses", "6");// 待处理的数量
		pm.setToDealNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));

		par.put("orderStatuses", "10,9,8,6,5,4,3,2");
		pm.setAllNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
		return pm;
	}
	
	
	// 导出订单
	@Override
	public List<OrderDeliveryDto> exportDeliveryOrder(Map<String, Object> par) {
		//普通业务员登录
		if (par.get("isMember")!=null&&(Integer)par.get("isMember")==1) {
			par.put("orderName", "updateTime");
			par.put("orderType", "desc");
			List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryListForMember(par);
			return dataList;
		}else {
			//超级管理员登录
			par.put("orderName", "updateTime");
			par.put("orderType", "desc");
			List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryList(par);
			return dataList;
		}
	}
	
	
	//异常订单查询
	@Override
	public PageModelOrderVo<OrderDeliveryDto> selectAbDeliveryList(Map<String, Object> par) {
		//普通业务员登录
		if ((Integer) par.get("isMember")==1) {
			PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
			par.put("orderName", "updateTime");
			par.put("orderType", "desc");
			List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryListForMember(par);
			Integer amount = lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par);
			pm.setTotalCount(amount);
			pm.setDataList(dataList);
			if (par.get("start") != null) {
				pm.setStartIndex(Integer.parseInt(par.get("start").toString()));
				pm.setPageSize(Integer.parseInt(par.get("limit").toString()));
			}
			par.put("isConfirmed", 1);// 已确认
			pm.setConfirmedNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			par.put("isConfirmed", 2);// 未确认
			pm.setUnConfirmedNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			par.put("isConfirmed", null);
			pm.setAllNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			return pm;
		}else {
			//超级管理员登录
			PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
			par.put("orderName", "updateTime");
			par.put("orderType", "desc");
			List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryList(par);
			Integer amount = lgDeliveryOrderDaoEx.selectDeliveryCount(par);
			pm.setTotalCount(amount);
			pm.setDataList(dataList);
			if (par.get("start") != null) {
				pm.setStartIndex(Integer.parseInt(par.get("start").toString()));
				pm.setPageSize(Integer.parseInt(par.get("limit").toString()));
			}
			par.put("isConfirmed", 1);// 已确认
			pm.setConfirmedNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			par.put("isConfirmed", 2);// 未确认
			pm.setUnConfirmedNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			par.put("isConfirmed", null);
			pm.setAllNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			return pm;
		}
	}
	
	//订单详情
	@Override
	public HashMap<String, Object> selectDetailByDeliveryPkOnMemeber(Map<String, Object> par) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		OrderDeliveryDto vo = lgDeliveryOrderDaoEx.selectDetailByPk(par);
		data.put("mainData", vo);
		data.put("picList", lgDeliveryExceptionPicDaoEx.selectPicUrlByDeliveryPk(par));
		data.put("trackDetail", lgTrackDetailDaoEx.selectTrackDetailBydeliveryPk(par));
		return data;
	}
	
	// 根据pk检查订单的状态
	@Override
	public int checkOrderStatusByPk(String deliveryPk) {
		return lgDeliveryOrderDaoEx.checkOrderStatusByPk(deliveryPk);
	}

	
	//取消订单
	@Override
	public void cancelDelivery(String deliveryPk) throws Exception {
		LgDeliveryOrderDto dto = new LgDeliveryOrderDto();
		dto.setPk(deliveryPk);
		dto.setOrderStatus(2);
		dto.setSignTime(new Date());
		dto.setFinishedTime(new Date());
		dto.setUpdateTime(new Date());
		LgDeliveryOrder vo = new LgDeliveryOrder();
		BeanUtils.copyProperties(vo, dto);
		int num = lgDeliveryOrderDaoEx.updateByPkSelective(dto);
		if (num == 0) {
			throw new Exception();
		} else {
			lgTrackDetailDaoEx.insert(createTrackDto(vo, 2));
		}

	}
	
	
	//创建物流记录
	public LgTrackDetail createTrackDto(LgDeliveryOrder vo, Integer status) {
		LgTrackDetail lgTrackDetail = new LgTrackDetail();
		lgTrackDetail.setPk(KeyUtils.getUUID());
		lgTrackDetail.setDeliveryPk(vo.getPk());
		if (status == 9) {
			lgTrackDetail.setFinishedTime(vo.getOrderTime());
			lgTrackDetail.setStepDetail("成功下单，等待付款");
			lgTrackDetail.setOrderStatus(9);
		} else if (status == 8) {
			lgTrackDetail.setFinishedTime(vo.getPaymentTime());
			lgTrackDetail.setStepDetail("付款成功，等待财务确认");
			lgTrackDetail.setOrderStatus(8);
		} else if (status == 7) {
			lgTrackDetail.setFinishedTime(new Date());
			lgTrackDetail.setStepDetail("未匹配，等待匹配物流公司");
			lgTrackDetail.setOrderStatus(7);
		} else if (status == 6) {
			lgTrackDetail.setFinishedTime(vo.getFinancialTime());
			lgTrackDetail.setStepDetail("财务已确认，待指派车辆");
			lgTrackDetail.setOrderStatus(6);
		} else if (status == 5) {
			String driver = vo.getDriver() == null ? "" : vo.getDriver();
			String driverContact = vo.getDriverContact() == null ? "" : vo.getDriverContact();
			lgTrackDetail.setStepDetail("已指派车辆，司机姓名：" + driver + "，手机号码：" + driverContact + "，司机正在提货的路上");
			lgTrackDetail.setOrderStatus(5);
			lgTrackDetail.setFinishedTime(new Date());
		} else if (status == 4) {
			String logisticsCompanyName = vo.getLogisticsCompanyName() == null ? "" : vo.getLogisticsCompanyName();
			String driver = vo.getDriver() == null ? "" : vo.getDriver();
			String driverContact = vo.getDriverContact() == null ? "" : vo.getDriverContact();
			lgTrackDetail.setStepDetail("物流公司" + logisticsCompanyName + "已经提货成功，正在配送中。司机：" + driver + "，手机号码：" + driverContact);
			lgTrackDetail.setOrderStatus(4);
			lgTrackDetail.setFinishedTime(new Date());
		} else if (status == 3) {
			String toCompanyName = vo.getToCompanyName() == null ? "" : vo.getToCompanyName();
			lgTrackDetail.setStepDetail("确认送达，" + toCompanyName + "已经成功签收订单");
			lgTrackDetail.setOrderStatus(3);
			lgTrackDetail.setFinishedTime(new Date());
		} else if (status == 2) {
			lgTrackDetail.setStepDetail("已取消订单");
			lgTrackDetail.setOrderStatus(2);
			lgTrackDetail.setFinishedTime(new Date());
		}
		lgTrackDetail.setInsertTime(new Date());
		lgTrackDetail.setUpdateTime(lgTrackDetail.getInsertTime());
		return lgTrackDetail;

	}
	
	
	//平台用户下单(在物流系统下单)
	@Override
	public RestCode commitOrder(Map<String, Object> map) {
		RestCode restCode=RestCode.CODE_0000;
		LgDeliveryOrder lgDeliveryOrder=new LgDeliveryOrder();
		String deliveryPk = KeyUtils.getUUID();
		Date tempDate=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lgDeliveryOrder.setPk(deliveryPk);
		lgDeliveryOrder.setOrderPk(KeyUtils.getOrderNumber());
		lgDeliveryOrder.setParentPk("-1");//订单Pk(订单编号)
		lgDeliveryOrder.setOrderStatus(9);//订单状态
		lgDeliveryOrder.setIsMatched(0);//是否匹配物流承运商，0：未匹配，1：已匹配
		lgDeliveryOrder.setOriginalTotalFreight(0.0);
		lgDeliveryOrder.setPresentTotalFreight(0.0);
		lgDeliveryOrder.setInsertTime(tempDate);
		lgDeliveryOrder.setUpdateTime(tempDate);
		lgDeliveryOrder.setOrderTime(tempDate);
		//logisticsCompanyPk
		//logisticsCompanyName
		String arrivedHourStart=map.get("arrivedHourStart")==null||map.get("arrivedHourStart").toString().equals("")?"00:00":map.get("arrivedHourStart").toString().trim();
		String arrivedHourEnd=map.get("arrivedHourEnd")==null||map.get("arrivedHourEnd").toString().equals("")?"00:00":map.get("arrivedHourEnd").toString().trim();
		if (null!=map.get("arrivedTimeStart")&&!map.get("arrivedTimeStart").toString().equals("")) {
			try {
				lgDeliveryOrder.setArrivedTimeStart(sdf.parse(map.get("arrivedTimeStart").toString().trim()+" "+arrivedHourStart+":00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (null != map.get("arrivedTimeEnd") && !map.get("arrivedTimeEnd").toString().equals("")) {
			try {
				lgDeliveryOrder.setArrivedTimeEnd(
						sdf.parse(map.get("arrivedTimeEnd").toString().trim() + " " + arrivedHourEnd + ":00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		lgDeliveryOrder.setFromAddressPk(map.get("fromAddressPk").toString()); 
		LgUserAddressDto fromAddressDto= lgUserAddressDaoEx.getByPkEx(map.get("fromAddressPk").toString());
		if (null == fromAddressDto || null == fromAddressDto.getPk() || "".equals(fromAddressDto.getPk())) {
			return RestCode.CODE_LO004;
		}
		lgDeliveryOrder.setFromCompanyName(fromAddressDto.getTitle() == null ? "" : fromAddressDto.getTitle());//提货公司的抬头
		lgDeliveryOrder.setFromAddress(fromAddressDto.getAddress() == null ? "" : fromAddressDto.getAddress());
		lgDeliveryOrder.setFromContacts(fromAddressDto.getContacts() == null ? "" : fromAddressDto.getContacts());
		lgDeliveryOrder.setFromContactsTel(fromAddressDto.getContactsTel() == null ? "" : fromAddressDto.getContactsTel());
		lgDeliveryOrder.setFromProvincePk(fromAddressDto.getProvince() == null ? "" : fromAddressDto.getProvince());
		lgDeliveryOrder.setFromProvinceName(fromAddressDto.getProvinceName() == null ? "" : fromAddressDto.getProvinceName());
		lgDeliveryOrder.setFromCityPk(fromAddressDto.getCity() == null ? "" : fromAddressDto.getCity());
		lgDeliveryOrder.setFromCityName(fromAddressDto.getCityName() == null ? "" : fromAddressDto.getCityName());
		lgDeliveryOrder.setFromAreaPk(fromAddressDto.getArea() == null ? "" : fromAddressDto.getArea());
		lgDeliveryOrder.setFromAreaName(fromAddressDto.getAreaName() == null ? "" : fromAddressDto.getAreaName());
		lgDeliveryOrder.setFromTownPk(fromAddressDto.getTown() == null ? "" : fromAddressDto.getTown());
		lgDeliveryOrder.setFromTownName(fromAddressDto.getTownName() == null ? "" : fromAddressDto.getTownName());
		lgDeliveryOrder.setToAddressPk(map.get("toAddressPk").toString());
		//toCompanyPk
		LgUserAddressDto toAddressDto= lgUserAddressDaoEx.getByPkEx(map.get("toAddressPk").toString());
		if (null == toAddressDto || null == toAddressDto.getPk() || "".equals(toAddressDto.getPk())) {
			return RestCode.CODE_LO003;
		}
		lgDeliveryOrder.setToCompanyName(toAddressDto.getTitle() == null ? "" : toAddressDto.getTitle());// 收货公司的抬头
		lgDeliveryOrder.setToAddress(toAddressDto.getAddress() == null ? "" : toAddressDto.getAddress());
		lgDeliveryOrder.setToContacts(toAddressDto.getContacts() == null ? "" : toAddressDto.getContacts());
		lgDeliveryOrder.setToContactsTel(toAddressDto.getContactsTel() == null ? "" : toAddressDto.getContactsTel());
		lgDeliveryOrder.setToProvincePk(toAddressDto.getProvince() == null ? "" : toAddressDto.getProvince());
		lgDeliveryOrder.setToProvinceName(toAddressDto.getProvinceName() == null ? "" : toAddressDto.getProvinceName());
		lgDeliveryOrder.setToCityPk(toAddressDto.getCity() == null ? "" : toAddressDto.getCity());
		lgDeliveryOrder.setToCityName(toAddressDto.getCityName() == null ? "" : toAddressDto.getCityName());
		lgDeliveryOrder.setToAreaPk(toAddressDto.getArea() == null ? "" : toAddressDto.getArea());
		lgDeliveryOrder.setToAreaName(toAddressDto.getAreaName() == null ? "" : toAddressDto.getAreaName());
		lgDeliveryOrder.setToTownPk(toAddressDto.getTown() == null ? "" : toAddressDto.getTown());
		lgDeliveryOrder.setToTownName(toAddressDto.getTownName() == null ? "" : toAddressDto.getTownName());
		//supplierInvoicePk
		lgDeliveryOrder.setSupplierInvoiceStatus(0);//物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认
		//memberInvoicePk
		lgDeliveryOrder.setMemberInvoiceStatus(1);//开票状态：1：未开发票，2：已开发票，3：已寄送
		//driver
		//driverPk
		//driverContact
		//carPk
		//carPlate
		lgDeliveryOrder.setSource(0);
		lgDeliveryOrder.setMember(map.get("member").toString());
		lgDeliveryOrder.setMemberPk(map.get("memberPk").toString());
		//abnormalRemark
		lgDeliveryOrder.setRemark(map.get("remark").toString()); 
		lgDeliveryOrder.setDeliveryNumber(lgDeliveryOrder.getOrderPk());//提货单号跟orderPk一样
		//deliveryTime
		//signTime
		lgDeliveryOrder.setIsAbnormal(1);//是否异常 1正常 2异常
		lgDeliveryOrder.setIsConfirmed(1);//订单异常是否确认1,已确认，2未确认
		//paymentPk
		//paymentName
		//paymentTime
		//finishedTime
		//financialTime
		lgDeliveryOrder.setPurchaserName(map.get("companyName").toString());
		lgDeliveryOrder.setPurchaserPk(map.get("companyPK").toString());
		//supplierName 
		//supplierPk
	 	lgDeliveryOrder.setInvoicePk(map.get("invoicePk")==null?"":map.get("invoicePk").toString());   
	 	LgUserInvoiceDto invoiceDto= lgUserInvoiceDaoEx.getByPk(map.get("invoicePk").toString());
	 	if (null == invoiceDto || null == invoiceDto.getPk()) {
			return RestCode.CODE_A008;
		}
		lgDeliveryOrder.setInvoiceName(invoiceDto.getRecipt() == null ? "" : invoiceDto.getRecipt());// 发票抬头
		lgDeliveryOrder.setInvoiceTaxidNumber(invoiceDto.getTaxidNumber() == null ? "" : invoiceDto.getTaxidNumber());// 纳税人识别号
		lgDeliveryOrder.setInvoiceRegPhone(invoiceDto.getRegPhone() == null ? "" : invoiceDto.getRegPhone());// 注册电话
		lgDeliveryOrder.setInvoiceBankAccount(invoiceDto.getBankAccount() == null ? "" : invoiceDto.getBankAccount());// 银行账号
		lgDeliveryOrder.setInvoiceBankName(invoiceDto.getBankName() == null ? "" : invoiceDto.getBankName());// 开户银行
		lgDeliveryOrder.setInvoiceProvinceName(invoiceDto.getProvinceName() == null ? "" : invoiceDto.getProvinceName());// 注册地区省
		lgDeliveryOrder.setInvoiceCityName(invoiceDto.getCityName() == null ? "" : invoiceDto.getCityName());// 注册地区市
		lgDeliveryOrder.setInvoiceAreaName(invoiceDto.getAreaName() == null ? "" : invoiceDto.getAreaName());// 注册地区区县
		lgDeliveryOrder.setInvoiceRegAddress(invoiceDto.getRegAddress() == null ? "" : invoiceDto.getRegAddress());// 注册地址
		//linePricePk
		//basisLinePrice
		//settleWeight
		lgDeliveryOrder.setIsSettleFreight(1);//运费结算标识：1 未结算，2 已结算
		//自提委托书,多个逗号隔开
		if (null!=map.get("mandatePk")&&!"".equals(map.get("mandatePk").toString())) {
			String mandateUrl="";
			String[] mandatePks=map.get("mandatePk").toString().split(",");
			for (String pk : mandatePks) {
				LgArayacakMandateDto mandateDto=lgArayacakMandateDaoEx.getByPk(pk);
				if (null!=mandateDto && null!=mandateDto.getPk() && null!=mandateDto.getMandateUrl()) {
					mandateUrl=mandateUrl+mandateDto.getMandateUrl().toString()+",";
				}
			}
			map.put("mandateUrl", mandateUrl);
		}
		lgDeliveryOrder.setMandatePk(map.get("mandatePk")==null?"":map.get("mandatePk").toString());
		lgDeliveryOrder.setMandateUrl(map.get("mandateUrl")==null?"":map.get("mandateUrl").toString());
		//85个参数
		
		LgDeliveryOrderGoods lgDeliveryOrderGoods=new LgDeliveryOrderGoods();
		lgDeliveryOrderGoods.setPk(KeyUtils.getUUID());
		lgDeliveryOrderGoods.setDeliveryPk(deliveryPk);
		//goodsPk
		lgDeliveryOrderGoods.setProductName(map.get("productName")==null?"":map.get("productName").toString());//品名
		lgDeliveryOrderGoods.setProductPk(map.get("productPk")==null?"":map.get("productPk").toString());//品名Pk
		//varietiesName 品种
		//varietiesPk 品种
		//seedvarietyName 子品种
		//seedvarietyPk  子品种
		//specName    规格大类
		//specPk	      规格大类
		//seriesName   规格系列
		//seriesPk	   规格系列
		lgDeliveryOrderGoods.setGradeName(map.get("gradeName")==null?"":map.get("gradeName").toString());//等级名称
		lgDeliveryOrderGoods.setGradePk(map.get("gradePk")==null?"":map.get("gradePk").toString());//等级PK
		lgDeliveryOrderGoods.setBatchNumber(map.get("batchNumber")==null?"":map.get("batchNumber").toString());//批号
		lgDeliveryOrderGoods.setWeight(Double.parseDouble(map.get("weight").toString()));//重量
		lgDeliveryOrderGoods.setUnit(Integer.parseInt(map.get("unit").toString()) );//单位(1:箱 2:锭 3:件 4:粒)
		lgDeliveryOrderGoods.setBoxes(Integer.parseInt(map.get("boxes").toString()));//箱数
		lgDeliveryOrderGoods.setOrderNumber(KeyUtils.getOrderNumber());
		//设置箱重为：weight*1000/boxes
		Double tareWeight=Double.parseDouble(map.get("weight").toString())*1000/Integer.parseInt(map.get("boxes").toString());
		lgDeliveryOrderGoods.setTareWeight(tareWeight);//箱重
		lgDeliveryOrderGoods.setOriginalFreight(0.0);
		lgDeliveryOrderGoods.setPresentFreight(0.0);
		//goodsName
		lgDeliveryOrderGoods.setGoodsOriginalFreight(0.0);
		lgDeliveryOrderGoods.setGoodsPresentFreight(0.0);
		if (lgDeliveryOrderDaoEx.insert(lgDeliveryOrder)>0) {
			if (lgDeliveryOrderGoodsDaoEx.insert(lgDeliveryOrderGoods)>0) {
				//创建订单轨迹
				lgTrackDetailDaoEx.insert(createTrackDto(lgDeliveryOrder, 9));
				//匹配路线的参数
				SearchLgLine searchLgLine = new SearchLgLine();
				searchLgLine.setFromProvicePk(fromAddressDto.getProvince()==null?"":fromAddressDto.getProvince());
				searchLgLine.setFromCityPk(fromAddressDto.getCity()==null?"":fromAddressDto.getCity());
				searchLgLine.setFromAreaPk(fromAddressDto.getArea()==null?"":fromAddressDto.getArea());
				searchLgLine.setFromTownPk(fromAddressDto.getTown()==null?"":fromAddressDto.getTown());
				searchLgLine.setToProvicePk(toAddressDto.getProvince()==null?"":toAddressDto.getProvince());
				searchLgLine.setToCityPk(toAddressDto.getCity()==null?"":toAddressDto.getCity());
				searchLgLine.setToAreaPk(toAddressDto.getArea()==null?"":toAddressDto.getArea());
				searchLgLine.setToTownPk(toAddressDto.getTown()==null?"":toAddressDto.getTown());
				searchLgLine.setProductPk(map.get("productPk")==null?"":map.get("productPk").toString());
				searchLgLine.setGradePk(map.get("gradePk")==null?"":map.get("gradePk").toString());
				//匹配线路
				List<LogisticsRouteDto> list = lgLineService.getLogisticsSetpinfos(searchLgLine, Double.parseDouble(map.get("weight").toString()));
				//匹配到,修改订单的线路及价格信息
				if (list.size()>0&&list.get(0)!=null) {
					LogisticsRouteDto logisticsRouteDto= list.get(0);
					LogisticsLinePriceDto linePriceDto =logisticsRouteDto.getList().get(0);
					Double originalTotalFreight=0.0;//外部总价
					Double presentTotalFreight = 0.0;//内部总价
					Double basisPrice=0.0;//内部单价
					Double salePrice=0.0;//外部单价
					basisPrice=(linePriceDto.getBasisPrice()==null?0.0:linePriceDto.getBasisPrice());//内部单价
					salePrice=(linePriceDto.getSalePrice()==null?0.0:linePriceDto.getSalePrice());//外部单价
					if (null!=logisticsRouteDto.getLeastWeight()&&Double.parseDouble(map.get("weight").toString())<logisticsRouteDto.getLeastWeight()) {
						originalTotalFreight=logisticsRouteDto.getFreight();//对外总价
						presentTotalFreight=logisticsRouteDto.getBasicPrice();//对内总价
						basisPrice=0.0;
						salePrice=0.0;
					}else {
						if (ArithUtil.mul(Double.parseDouble(map.get("weight").toString()),salePrice)<logisticsRouteDto.getFreight() ) {
							originalTotalFreight=logisticsRouteDto.getFreight();//对外总价
							presentTotalFreight=logisticsRouteDto.getBasicPrice();//对内总价
							basisPrice=0.0;
							salePrice=0.0;
						}else {
							originalTotalFreight=ArithUtil.mul(Double.parseDouble(map.get("weight").toString()), salePrice);//外
							presentTotalFreight= ArithUtil.mul(Double.parseDouble(map.get("weight").toString()), basisPrice);//内
							basisPrice=(linePriceDto.getBasisPrice()==null?0.0:linePriceDto.getBasisPrice());//内部单价
							salePrice=(linePriceDto.getSalePrice()==null?0.0:linePriceDto.getSalePrice());//外部单价
						}
					}
					lgDeliveryOrder.setOriginalTotalFreight(originalTotalFreight);//内部总价
					lgDeliveryOrder.setPresentTotalFreight(presentTotalFreight);//外部总价(开票结算金额)
					lgDeliveryOrder.setLogisticsCompanyPk(logisticsRouteDto.getCompanyPk()==null?"":logisticsRouteDto.getCompanyPk());//物流承运商
					LgCompanyDto companyDto= companyDaoEx.getByPk(logisticsRouteDto.getCompanyPk());
					lgDeliveryOrder.setLogisticsCompanyName(companyDto.getName()==null?"":companyDto.getName());//物流承运商
					lgDeliveryOrder.setLinePricePk(linePriceDto.getPk()==null?"":linePriceDto.getPk());//选中线路价格pk
					lgDeliveryOrder.setBasisLinePrice(basisPrice);//线路的内部结算价(下单时）
					lgDeliveryOrder.setSettleWeight(Double.parseDouble(map.get("weight").toString()));
					lgDeliveryOrder.setIsMatched(logisticsRouteDto.getCompanyPk()==null?0:1);//是否已经匹配物流承运商，0：未匹配，1：已匹配
					lgDeliveryOrder.setUpdateTime(new Date());
					lgDeliveryOrderDaoEx.update(lgDeliveryOrder);//更新订单信息

					// 更新商品表的运费
					lgDeliveryOrderGoods.setOriginalFreight(salePrice==0.0?null:salePrice);//外
					lgDeliveryOrderGoods.setPresentFreight(basisPrice==0.0?null:basisPrice);//内
					lgDeliveryOrderGoods.setGoodsOriginalFreight(originalTotalFreight);
					lgDeliveryOrderGoods.setGoodsPresentFreight(presentTotalFreight);
					lgDeliveryOrderGoodsDaoEx.update(lgDeliveryOrderGoods);// 更新商品信息
				}
				return restCode;
			}else {
				lgDeliveryOrderDaoEx.delete(deliveryPk);
				restCode=RestCode.CODE_S999;
				return restCode;
			}
		}else {
			restCode=RestCode.CODE_S999;
			return restCode;
		}
	}

	// 付款前获取付款的相关信息
	@Override
	public LgDeliveryOrderDto getInfoBeforePay(String deliveryPk) {
		return lgDeliveryOrderDaoEx.getInfoBeforePay(deliveryPk);
	}
	
	
	// 平台用户物流订单付款（orderStatus:9->8）
	@Override
	public Integer payForLogistics(String deliveryPk, String paymentPk, String paymentName, Date tempDate) {
		if (lgDeliveryOrderDaoEx.payForLogistics(deliveryPk, paymentPk, paymentName, tempDate) > 0) {
			// 创建订单轨迹
			LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
			lgDeliveryOrder.setPk(deliveryPk);
			lgDeliveryOrder.setPaymentTime(tempDate);
			lgTrackDetailDaoEx.insert(createTrackDto(lgDeliveryOrder, 8));// 付款订单的轨迹
			return 1;
		} else {
			return 0;
		}
	}
	
	// 查询订单信息（再来一单）
	@Override
	public HashMap<String, Object> getInfo4MoreOrder(Map<String, Object> par) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		OrderDeliveryDto vo = lgDeliveryOrderDaoEx.getInfo4MoreOrder(par);
		data.put("mainData", vo);
		return data;
	}
	
	// 平台用户开票订单管理
	@Override
	public PageModelOrderVo<OrderDeliveryDto> memberDeliveryOrder(Map<String, Object> par) {
		PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
		par.put("orderName", "updateTime");
		par.put("orderType", "desc");
		List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectInvoiceList4member(par);
		for (OrderDeliveryDto orderDeliveryDto : dataList) {
			if (orderDeliveryDto.getIsAbnormal()==2&&orderDeliveryDto.getIsConfirmed()==1) {
				orderDeliveryDto.setOrderStatusName("异常-已确认");
			}
		}
		Integer amount = lgDeliveryOrderDaoEx.selectInvoiceCount4member(par);
		pm.setTotalCount(amount);
		pm.setDataList(dataList);
		if (par.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(par.get("start").toString()));
			pm.setPageSize(Integer.parseInt(par.get("limit").toString()));
		}
		par.put("memberInvoiceStatus", 1);// 未开发票数量
		pm.setUnbilledNum(lgDeliveryOrderDaoEx.selectInvoiceCount4member(par));
		par.put("memberInvoiceStatus", 2);// 已开发票数量
		pm.setBilledNum(lgDeliveryOrderDaoEx.selectInvoiceCount4member(par));
		par.put("memberInvoiceStatus", 3);// 已寄送数量
		pm.setMailedNum(lgDeliveryOrderDaoEx.selectInvoiceCount4member(par));
		par.put("memberInvoiceStatus", 0);// 全部
		pm.setAllNum(lgDeliveryOrderDaoEx.selectInvoiceCount4member(par));
		return pm;
	}
	
	/**
	 * 平台用户开票
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Override
	@Transactional
	public RestCode bill4member(Map<String, Object> par) throws Exception {
		String pks = par.get("pks").toString();
		String month = par.get("month").toString();
		String[] strings = cn.cf.common.utils.StringUtils.splitStrs(pks);
		Double billAmount = 0.0;
		String uuid = KeyUtils.getUUID();
		LgMemberInvoices lgMemberInvoices = new LgMemberInvoices();
		lgMemberInvoices.setPk(uuid);
		LgDeliveryOrderDto tempDto=null;
		// 验证用户是否已经开过票
		for (String pk : strings) {
			tempDto = lgDeliveryOrderDaoEx.getByPk(pk);
			if (null!=tempDto && tempDto.getMemberInvoiceStatus() != 1) {
				return RestCode.CODE_Q003;
			}
		}
		for (String pk : strings) {
			LgDeliveryOrderDto dto = lgDeliveryOrderDaoEx.getByPk(pk);
			if (null!= dto && null!= dto.getOrderStatus()&& null!= dto.getMemberInvoiceStatus()&& dto.getMemberInvoiceStatus() == 1) {
				Double presentTotalFreight = dto.getPresentTotalFreight();
				billAmount += presentTotalFreight;
				dto.setMemberInvoiceStatus(2); // 1未开票 2 已开票 3 已寄送
				dto.setMemberInvoicePk(uuid);// 平台用户开票记录的PK
				// dto.setInvoicePk(par.get("invoicePk")==null?"":par.get("invoicePk").toString());//平台用户发票信息的PK
				// dto.setInvoiceName(par.get("name")==null?"":par.get("name").toString());
				// dto.setInvoiceTaxidNumber(par.get("taxID")==null?"":par.get("taxID").toString());
				// dto.setInvoiceRegPhone(par.get("regTel")==null?"":par.get("regTel").toString());
				// dto.setInvoiceBankAccount(par.get("bankAccount")==null?"":par.get("bankAccount").toString());
				// dto.setInvoiceBankName(par.get("bankName")==null?"":par.get("bankName").toString());
				// dto.setInvoiceProvince(par.get("invoiceProvince")==null?"":par.get("invoiceProvince").toString());
				// dto.setInvoiceProvinceName(par.get("invoiceProvinceName")==null?"":par.get("invoiceProvinceName").toString());
				// dto.setInvoiceCity(par.get("invoiceCity")==null?"":par.get("invoiceCity").toString());
				// dto.setInvoiceCityName(par.get("invoiceCityName")==null?"":par.get("invoiceCityName").toString());
				// dto.setInvoiceArea(par.get("invoiceArea")==null?"":par.get("invoiceArea").toString());
				// dto.setInvoiceAreaName(par.get("invoiceAreaName")==null?"":par.get("invoiceAreaName").toString());;
				// dto.setInvoiceRegAddress(par.get("shortRegAddress")==null?"":par.get("shortRegAddress").toString());
				dto.setUpdateTime(new Date());
				lgDeliveryOrderDaoEx.updateByPkSelective(dto);
			} else {
				return RestCode.CODE_S999;
			}
		}
		Date tempDate = new Date();
		lgMemberInvoices.setMonth(month);
		lgMemberInvoices.setBillingAmount(billAmount);
		lgMemberInvoices.setInsertTime(tempDate);
		lgMemberInvoices.setUpdateTime(tempDate);
		lgMemberInvoices.setContactTel(par.get("contactTel") == null ? "" : par.get("contactTel").toString());
		lgMemberInvoices.setContactName(par.get("contactName") == null ? "" : par.get("contactName").toString());
		lgMemberInvoices.setContactAddress(par.get("contactAddress") == null ? "" : par.get("contactAddress").toString());
		lgMemberInvoices.setApplyTime(tempDate);
		lgMemberInvoices.setStatus(2);
		lgMemberInvoices.setBankAccount(par.get("bankAccount") == null ? "" : par.get("bankAccount").toString());
		lgMemberInvoices.setBankName(par.get("bankName") == null ? "" : par.get("bankName").toString());
		lgMemberInvoices.setRegTel(par.get("regTel") == null ? "" : par.get("regTel").toString());
		lgMemberInvoices.setRegAddress(par.get("regAddress") == null ? "" : par.get("regAddress").toString());
		lgMemberInvoices.setTaxId(par.get("taxID") == null ? "" : par.get("taxID").toString());
		lgMemberInvoices.setName(par.get("name") == null ? "" : par.get("name").toString());
		lgMemberInvoices.setInvoiceTime(tempDate);
		lgMemberInvoicesDao.insert(lgMemberInvoices);
		return RestCode.CODE_0000;
	}
	
	//承运商我的订单
	@Override
	public PageModelOrderVo<OrderDeliveryDto> selectDeliveryList(Map<String, Object> par) {
		//业务员登录
		if ("1".equals(par.get("isMember").toString())) {
			//System.out.println("===============业务员登录");
			PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
			par.put("orderName", "updateTime");
			par.put("orderType", "desc");
			List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryListForMember(par);
			Integer amount = lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par);
			pm.setTotalCount(amount);
			pm.setDataList(dataList);
			if (par.get("start") != null) {
				pm.setStartIndex(Integer.parseInt(par.get("start").toString()));
				pm.setPageSize(Integer.parseInt(par.get("limit").toString()));
			}
			par.put("orderStatus", null);// 待处理
			par.put("orderStatuses", "6");
			pm.setToDealNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			par.put("orderStatuses", null);// 待处理
			par.put("orderStatus", 5);// 提货中
			pm.setInDeliveryNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			par.put("orderStatus", 4);// 派送中
			pm.setOutFordeliveryNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			par.put("orderStatus", 3);// 已签收
			pm.setBeReceivedNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			par.put("orderStatus", null);// 全部
			par.put("orderStatuses", "6,5,4,3");
			pm.setAllNum(lgDeliveryOrderDaoEx.selectDeliveryCountForMember(par));
			return pm;
		}else {
			//System.out.println("===============超级管理员登陆");
			//超级管理员登陆
			PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
			par.put("orderName", "updateTime");
			par.put("orderType", "desc");
			List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectDeliveryList(par);
			Integer amount = lgDeliveryOrderDaoEx.selectDeliveryCount(par);
			pm.setTotalCount(amount);
			pm.setDataList(dataList);
			if (par.get("start") != null) {
				pm.setStartIndex(Integer.parseInt(par.get("start").toString()));
				pm.setPageSize(Integer.parseInt(par.get("limit").toString()));
			}
			par.put("orderStatus", null);// 待处理
			par.put("orderStatuses", "6");
			pm.setToDealNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			par.put("orderStatuses", null);// 待处理
			par.put("orderStatus", 5);// 提货中
			pm.setInDeliveryNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			par.put("orderStatus", 4);// 派送中
			pm.setOutFordeliveryNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			par.put("orderStatus", 3);// 已签收
			pm.setBeReceivedNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			par.put("orderStatus", null);// 全部
			par.put("orderStatuses", "6,5,4,3");
			pm.setAllNum(lgDeliveryOrderDaoEx.selectDeliveryCount(par));
			return pm;
		}
		
	}
	
	

	//承运商订单详情
	@Override
	public HashMap<String, Object> selectDetailByDeliveryPkOnSupplier(Map<String, Object> par) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		OrderDeliveryDto vo = lgDeliveryOrderDaoEx.selectDetailByPk(par);
		data.put("mainData", vo);
		data.put("picList", lgDeliveryExceptionPicDaoEx.selectPicUrlByDeliveryPk(par));
		data.put("trackDetail", lgTrackDetailDaoEx.selectTrackDetailBydeliveryPk(par));
		return data;
	}
	
	

	// 承运商订单拆分
	@Override
	@Transactional
	public void orderSplit(String deliveryPk, Integer boxes) throws Exception {
		Integer overplusBoxes = 0;// 剩余的箱数
		LgDeliveryOrderDto delivery = lgDeliveryOrderDaoEx.getByPk(deliveryPk);
		LgDeliveryOrderGoodsDto good = lgDeliveryOrderGoodsDaoEx.getByDeliveryPk(deliveryPk);
		int totalBoxes=good.getBoxes();
		Double totalPresentTotalFreight=delivery.getPresentTotalFreight();
		Double totalOriginalTotalFreight=delivery.getOriginalTotalFreight();
		Double totalGoodsPresentFreight=good.getGoodsPresentFreight();
		Double totalGoodsOriginalFreight=good.getGoodsOriginalFreight();
		
		if (good.getBoxes() <= boxes) {
			throw new ErrorParameterException("请求参数错误！");
		}
		overplusBoxes = good.getBoxes() - boxes;
		good.setBoxes(boxes);
		good.setWeight(good.getTareWeight() / 1000 * boxes);
		good.setGoodsPresentFreight(ArithUtil.mul(ArithUtil.div(boxes, totalBoxes),totalGoodsPresentFreight));
		good.setGoodsOriginalFreight(ArithUtil.mul(ArithUtil.div(boxes, totalBoxes),totalGoodsOriginalFreight));
		delivery.setPresentTotalFreight( ArithUtil.mul(ArithUtil.div(boxes, totalBoxes),totalPresentTotalFreight));
		delivery.setOriginalTotalFreight(ArithUtil.mul(ArithUtil.div(boxes, totalBoxes),totalOriginalTotalFreight));
		
		delivery.setSettleWeight(good.getTareWeight() / 1000 * boxes);
		int deliveryCount = lgDeliveryOrderDaoEx.getDeliveryCountByOrderPk(delivery.getOrderPk());// 查询该订单编号的所有订单数量
		if (deliveryCount == 1) {
			delivery.setDeliveryNumber(delivery.getOrderPk() + "-1");
		}
		delivery.setUpdateTime(new Date());
		lgDeliveryOrderDaoEx.updateByPkSelective(delivery);
		lgDeliveryOrderGoodsDaoEx.updateByPkSelective(good);
		// 订单拆分，剩余的箱数生成新的运货单
		String parentPk = delivery.getParentPk();
		if (parentPk != null && parentPk.equals("-1")) {
			delivery.setParentPk(delivery.getPk());
		}
		delivery.setPk(KeyUtils.getUUID());
		delivery.setPresentTotalFreight(ArithUtil.mul(ArithUtil.div(overplusBoxes, totalBoxes),totalPresentTotalFreight));
		delivery.setOriginalTotalFreight(ArithUtil.mul(ArithUtil.div(overplusBoxes, totalBoxes),totalOriginalTotalFreight));
		delivery.setDeliveryNumber(delivery.getOrderPk() + "-" + (deliveryCount + 1));
		delivery.setSettleWeight(good.getTareWeight() / 1000 * overplusBoxes);
		good.setBoxes(overplusBoxes);
		good.setWeight(good.getTareWeight() / 1000 * overplusBoxes);
		good.setGoodsPresentFreight(ArithUtil.mul(ArithUtil.div(overplusBoxes, totalBoxes),totalGoodsPresentFreight));
		good.setGoodsOriginalFreight(ArithUtil.mul(ArithUtil.div(overplusBoxes, totalBoxes),totalGoodsOriginalFreight));
		good.setDeliveryPk(delivery.getPk());
		good.setPk(KeyUtils.getUUID());
		good.setOrderNumber(KeyUtils.getOrderNumber());
		LgDeliveryOrder destDelivery = new LgDeliveryOrder();
		LgDeliveryOrderGoods destGood = new LgDeliveryOrderGoods();
		BeanUtils.copyProperties(destDelivery, delivery);
		BeanUtils.copyProperties(destGood, good);
		// 插入物流轨迹信息
		destDelivery.setInsertTime(new Date());
		destDelivery.setUpdateTime(new Date());
		lgTrackDetailDaoEx.insert(createTrackDto(destDelivery, 9));
		lgTrackDetailDaoEx.insert(createTrackDto(destDelivery, 8));
		lgTrackDetailDaoEx.insert(createTrackDto(destDelivery, 6));
		// 插入拆分后新的订单
		lgDeliveryOrderDaoEx.insert(destDelivery);
		lgDeliveryOrderGoodsDaoEx.insert(destGood);
	}
	
	
	public Double calculateTotalFreight(Integer boxes, LgDeliveryOrderGoodsDto good) {
		BigDecimal bd = new BigDecimal(boxes.toString());
		BigDecimal price = BigDecimal.valueOf(good.getPresentFreight());
		BigDecimal weight = BigDecimal.valueOf((good.getTareWeight() / 1000));
		return bd.multiply(price).multiply(weight).doubleValue();
	}
	
	/**
	 * 承运商指派车辆
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Override
	public boolean assignVehicle(Map<String, Object> par) throws Exception {
		String deliveryPks = par.get("deliveryPks").toString();
		String carPk = par.get("carPk").toString();
		String driverPk = par.get("driverPk").toString();
		LgCarDto carDto = lgCarDaoEx.getByPk(carPk);
		LgDriverDto driverDto = lgDriverDaoEx.getByPk(driverPk);
		if (carDto != null && driverDto != null) {
			String[] deliveryArray = StringUtils.split(deliveryPks, ",");
			if (deliveryArray != null && deliveryArray.length > 0) {
				// 检查订单的状态，是否处于待指派车辆的状态
				for (String string : deliveryArray) {
					int orderStatus = lgDeliveryOrderDaoEx.checkOrderStatusByPk(string);
					if (orderStatus != 6) {
						return false;
					}
				}
				for (String str : deliveryArray) {
					LgDeliveryOrderDto dto = new LgDeliveryOrderDto();
					dto.setPk(str);
					dto.setOrderStatus(5);
					dto.setDriver(driverDto.getName());
					dto.setDriverPk(driverDto.getPk());
					dto.setDriverContact(driverDto.getMobile());
					dto.setCarPk(carDto.getPk());
					dto.setCarPlate(carDto.getPlateNumber());
					dto.setUpdateTime(new Date());
					lgDeliveryOrderDaoEx.updateByPkSelective(dto);
					LgDeliveryOrder vo = new LgDeliveryOrder();
					BeanUtils.copyProperties(vo, dto);
					lgTrackDetailDaoEx.insert(createTrackDto(vo, 5));
					// 给每个订单的Member发送短信，告诉平台用户系统已经派车成功
					LgDeliveryOrderDto deliveryOrderDto = lgDeliveryOrderDaoEx.getByPk(str);
					SysSmsTemplateDto sysSmsTemplateDto = sysService.getSmsByName(SmsCode.LG_ASSIGN.getValue());
					if (null!=sysSmsTemplateDto && null!=sysSmsTemplateDto.getIsSms() && sysSmsTemplateDto.getIsSms()==1) {
						Map<String, String> mapTemp = new HashMap<>();
						mapTemp.put("phone", driverDto.getMobile());
						mapTemp.put("plateNumber", carDto.getPlateNumber());
						/*LgMemberDto member = new LgMemberDto();
						member.setPk(deliveryOrderDto.getMemberPk());// 下单用户
						member.setMobile(deliveryOrderDto.getMember());// 下单用户的电话
						member.setCompanyPk(deliveryOrderDto.getPurchaserPk());// 采购商pk
*/						try {
							String content= sysService.getContent(mapTemp, sysSmsTemplateDto).getContent();
							smsService.sendCUCCMsg(deliveryOrderDto.getMember(), SmsCode.LG_ASSIGN.getValue(),content , deliveryOrderDto.getPurchaserPk(), deliveryOrderDto.getPurchaserName());
							//SmsUtils.send(sysService.getContent(mapTemp, sysSmsTemplateDto), member, member, mongoTemplate);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				return false;
			}

		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 承运商 确认提货
	 */
	@Override
	@Transactional
	public void deliveryConfirmation(String deliveryPk) throws Exception {
		LgDeliveryOrderDto dto = lgDeliveryOrderDaoEx.getByPk(deliveryPk);
		dto.setDeliveryTime(new Date());
		dto.setOrderStatus(4);// 订单状态 9待付款9 8待财务确认8 6财务已确认 待指派车辆6 5提货中5 4配送中4 3已签收3 2已取消2 1完成1
		dto.setUpdateTime(new Date());
		LgDeliveryOrder vo = new LgDeliveryOrder();
		BeanUtils.copyProperties(vo, dto);
		int num = lgDeliveryOrderDaoEx.updateByPkSelective(dto);
		if (num == 0) {
			throw new Exception();
		} else {
			// 插入一条订单状态改变记录
			lgTrackDetailDaoEx.insert(createTrackDto(vo, 4));
			// 发送短信，告诉平台用户平台正在为您送货中
			LgDeliveryOrderDto dtoTemp = lgDeliveryOrderDaoEx.getByPk(deliveryPk);
			SysSmsTemplateDto sysSmsTemplateDto = sysService.getSmsByName(SmsCode.LG_DELIVERY.getValue());
			if (null!=sysSmsTemplateDto && null!=sysSmsTemplateDto.getIsSms() && sysSmsTemplateDto.getIsSms()==1) {
				Map<String, String> mapTemp = new HashMap<>();
				mapTemp.put("phone", dtoTemp.getDriverContact());
				mapTemp.put("plateNumber", dtoTemp.getCarPlate());
				LgMemberDto member = new LgMemberDto();
				member.setPk(dtoTemp.getMemberPk());// 下单用户
				member.setMobile(dtoTemp.getMember());// 下单用户的电话
				member.setCompanyPk(dtoTemp.getPurchaserPk());// 采购商pk
				try {
					String content= sysService.getContent(mapTemp, sysSmsTemplateDto).getContent();
					smsService.sendCUCCMsg(dtoTemp.getMember(), SmsCode.LG_DELIVERY.getValue(),content , dtoTemp.getPurchaserPk(), dtoTemp.getPurchaserName());
					//SmsUtils.send(sysService.getContent(mapTemp, sysSmsTemplateDto), member, member, mongoTemplate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//承运商取消指派车辆
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelAssign(String deliveryPk) throws Exception {
		LgDeliveryOrderDto dto = lgDeliveryOrderDaoEx.getByPk(deliveryPk);
		dto.setOrderStatus(6);
		dto.setDriver(null);
		dto.setDriverPk(null);
		dto.setDriverContact(null);
		dto.setCarPk(null);
		dto.setCarPlate(null);
		dto.setUpdateTime(new Date());
		int num = lgDeliveryOrderDaoEx.updateByPk(dto);
		if (num == 0) {
			throw new Exception();
		}
		lgTrackDetailDaoEx.insert(createTrackDto2(dto.getPk(), "已取消指派车辆", 6));
	}

	
	public LgTrackDetail createTrackDto2(String deliveryPk, String detail, Integer status) {
		LgTrackDetail lgTrackDetail = new LgTrackDetail();
		lgTrackDetail.setPk(KeyUtils.getUUID());
		lgTrackDetail.setDeliveryPk(deliveryPk);
		lgTrackDetail.setOrderStatus(status);
		lgTrackDetail.setStepDetail(detail);
		lgTrackDetail.setFinishedTime(new Date());
		lgTrackDetail.setUpdateTime(new Date());
		lgTrackDetail.setInsertTime(new Date());
		return lgTrackDetail;
	}
	

	/**
	 * 承运商确认送达
	 */
	@Override
	@Transactional
	public void confirmReceived(String deliveryPk) throws Exception {
		LgDeliveryOrderDto dto = lgDeliveryOrderDaoEx.getByPk(deliveryPk);
		dto.setOrderStatus(3);
		dto.setSupplierInvoiceStatus(1);//物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认222
		dto.setSignTime(new Date());
		dto.setFinishedTime(new Date());
		dto.setUpdateTime(new Date());
		LgDeliveryOrder vo = new LgDeliveryOrder();
		BeanUtils.copyProperties(vo, dto);
		int num = lgDeliveryOrderDaoEx.updateByPkSelective(dto);
		if (num == 0) {
			throw new Exception();
		} else {
			lgTrackDetailDaoEx.insert(createTrackDto(vo, 3));
		}
	}
	
	@Override
	public LgDeliveryOrderDto getBypk(String pk) {
		return lgDeliveryOrderDaoEx.getByPk(pk);
	}
	
	
	// 根据orderPk查询所有订单（包括子订单）的订单状态
	@Override
	public List<Integer> getAllStatusByOrderPk(String orderPk) {
		return lgDeliveryOrderDaoEx.getAllStatusByOrderPk(orderPk);
	}
	
	//承运商-派送中发货单-异常反馈
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void abnormalFeedback(Map<String, Object> map) throws Exception {
		String deliveryPk = map.get("deliveryPk").toString();
		String picUrls = map.get("picUrls").toString();
		String abnormalRemark = map.get("remark").toString();
		if (abnormalRemark.length() > 500) {
			throw new ErrorParameterException();
		}
		LgDeliveryOrderDto dto = new LgDeliveryOrderDto();
		dto.setPk(deliveryPk);
		dto.setIsAbnormal(2);
		dto.setIsConfirmed(2);
		dto.setAbnormalRemark(abnormalRemark);
		dto.setUpdateTime(new Date());
		int num = lgDeliveryOrderDaoEx.updateByPkSelective(dto);
		if (num != 0) {
			String[] picArray = StringUtils.split(picUrls, ",");
			if (picArray != null && picArray.length > 0) {
				for (String str : picArray) {
					LgDeliveryExceptionPic vo = new LgDeliveryExceptionPic();
					vo.setPk(KeyUtils.getUUID());
					vo.setDeliveryPk(deliveryPk);
					vo.setExceptionPicUrl(str);
					vo.setInsertTime(new Date());
					vo.setUpdateTime(vo.getInsertTime());
					lgDeliveryExceptionPicDaoEx.insert(vo);
				}
			}
		}
	}
	

	@Override
	public OrderDeliveryDto selectDetailByDeliveryPk(Map<String, Object> map) {
		OrderDeliveryDto orderDeliveryDto= lgDeliveryOrderDaoEx.selectDetailByPk(map);
		if (null==orderDeliveryDto.getFromProvinceName()||"null".equals(orderDeliveryDto.getFromProvinceName())||"NULL".equals(orderDeliveryDto.getFromProvinceName())) {
			orderDeliveryDto.setFromProvinceName("");
		}
		if (null==orderDeliveryDto.getFromCityName()||"null".equals(orderDeliveryDto.getFromCityName())||"NULL".equals(orderDeliveryDto.getFromCityName())) {
			orderDeliveryDto.setFromCityName("");
		}
		if (null==orderDeliveryDto.getFromAreaName()||"null".equals(orderDeliveryDto.getFromAreaName())||"NULL".equals(orderDeliveryDto.getFromAreaName())) {
			orderDeliveryDto.setFromAreaName("");
		}
		if (null==orderDeliveryDto.getFromTownName()||"null".equals(orderDeliveryDto.getFromTownName())||"NULL".equals(orderDeliveryDto.getFromTownName())) {
			orderDeliveryDto.setFromTownName("");
		}
		if (null==orderDeliveryDto.getToProvinceName()||"null".equals(orderDeliveryDto.getToProvinceName())||"NULL".equals(orderDeliveryDto.getToProvinceName())) {
			orderDeliveryDto.setToProvinceName("");
		}
		if (null==orderDeliveryDto.getToCityName()||"null".equals(orderDeliveryDto.getToCityName())||"NULL".equals(orderDeliveryDto.getToCityName())) {
			orderDeliveryDto.setToCityName("");
		}
		if (null==orderDeliveryDto.getToAreaName()||"null".equals(orderDeliveryDto.getToAreaName())||"NULL".equals(orderDeliveryDto.getToAreaName())) {
			orderDeliveryDto.setToAreaName("");
		}
		if (null==orderDeliveryDto.getToTownName()||"null".equals(orderDeliveryDto.getToTownName())||"NULL".equals(orderDeliveryDto.getToTownName())) {
			orderDeliveryDto.setToTownName("");
		}
		return orderDeliveryDto;
	}
	
	
	// 承运商开票订单管理列表
	@Override
	public PageModelOrderVo<OrderDeliveryDto> supplierBillsDeliveryOrder(Map<String, Object> map) {
		PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
		map.put("orderName", "updateTime");
		map.put("orderType", "desc");
		List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.selectInvoiceList4supplier(map);
		if (dataList.size()>0) {
			for (OrderDeliveryDto orderDeliveryDto : dataList) {
				if (null!=orderDeliveryDto.getIsAbnormal()&& orderDeliveryDto.getIsAbnormal()==2) {
					if (null!=orderDeliveryDto.getIsConfirmed()&& orderDeliveryDto.getIsConfirmed()==1) {
						orderDeliveryDto.setOrderStatusName("异常-已确认");
					}else {
						orderDeliveryDto.setOrderStatusName("异常-未确认");
					}
				}
			}
		}
		Integer amount = lgDeliveryOrderDaoEx.selectInvoiceCount4supplier(map);
		pm.setTotalCount(amount);
		pm.setDataList(dataList);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		// 进行中数量,
		map.put("orderStatus", 1);// 1对应订单状态为 4 or 5 or 6
		map.put("supplierInvoiceStatus", 0);//supplierInvoiceStatus开票状态  0
		pm.setProcessingNum(lgDeliveryOrderDaoEx.selectInvoiceCount4supplier(map));
		// 未开票数量
		map.put("orderStatus", 0);// 0对应订单状态为 1 or 3
		map.put("supplierInvoiceStatus", 1);//supplierInvoiceStatus开票状态  0
		pm.setUnsendNum(lgDeliveryOrderDaoEx.selectInvoiceCount4supplier(map));
		// 已开票数量，
		map.put("orderStatus", 0);// 0对应订单状态为 1 or 3
		map.put("supplierInvoiceStatus", 2);//supplierInvoiceStatus开票状态  2，3
		pm.setSendNum(lgDeliveryOrderDaoEx.selectInvoiceCount4supplier(map));
		// 全部数量
		map.put("orderStatus", 9);// 9对应订单的状态为1 or 3 or 4 or 5 or 6
		map.put("supplierInvoiceStatus", 3);//supplierInvoiceStatus开票状态  1，2，3
		pm.setAllNum(lgDeliveryOrderDaoEx.selectInvoiceCount4supplier(map));
		return pm;
	}
	
	
	// 承运商导出开票发货单
	@Override
	public List<OrderDeliveryDto> exportInvoiceDeliveryOrder(Map<String, Object> map) {
		map.put("orderName", "updateTime");
		map.put("orderType", "desc");
		List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.exportInvoiceDeliveryOrder(map);
		return dataList;
	}
	
	
	//承运商开票操作
	@Override
	public boolean supplierBillOrders(Map<String, Object> map) {
		// 参数：月份month，发票金额billAccount，订单数量orderCount
		String pks = map.get("pks").toString();
		String[] strings = cn.cf.common.utils.StringUtils.splitStrs(pks);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date temp = new Date();
		String month = sdf.format(temp) + map.get("month").toString();
		int orderCount = Integer.parseInt(map.get("orderCount").toString());
		String uuid = KeyUtils.getUUID();
		LgLogisticsInvoices lgLogisticsInvoices = new LgLogisticsInvoices();
		lgLogisticsInvoices.setPk(uuid);
		lgLogisticsInvoices.setInsertTime(temp);
		lgLogisticsInvoices.setUpdateTime(temp);
		lgLogisticsInvoices.setMonth(month);
		lgLogisticsInvoices.setOrderCount(orderCount);
		lgLogisticsInvoices.setApplyTime(temp);
		Double billAccount = 0.0;
		for (String pk : strings) {
			LgDeliveryOrderDto dto = lgDeliveryOrderDaoEx.getByPk(pk);
			if (dto.getOrderStatus() != null && (dto.getOrderStatus() == 3 || dto.getOrderStatus() == 1)
					&& dto.getSupplierInvoiceStatus() == 1) {
				Double originalTotalFreight = dto.getOriginalTotalFreight() == null ? 0 : dto.getOriginalTotalFreight();
				billAccount = billAccount + originalTotalFreight;
				dto.setSupplierInvoiceStatus(2);// 物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认
				dto.setSupplierInvoicePk(uuid);
				dto.setUpdateTime(new Date());
				lgDeliveryOrderDaoEx.updateByPkSelective(dto);
			} else {
				continue;
			}
		}
		lgLogisticsInvoices.setBillAccount(billAccount);
		lgLogisticsInvoicesDao.insert(lgLogisticsInvoices);
		return true;
	}
	
	// 承运商收入明细
	@Override
	public PageModelOrderVo<OrderDeliveryDto> supplierIncomeDetail(Map<String, Object> map) {
		PageModelOrderVo<OrderDeliveryDto> pm = new PageModelOrderVo<OrderDeliveryDto>();
		map.put("orderName", "updateTime");
		map.put("orderType", "desc");
		// 承运商收入明细订单列表
		List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.supplierIncomeDetailList(map);
		if (dataList.size()>0) {
			for (OrderDeliveryDto orderDeliveryDto : dataList) {
				if (null!=orderDeliveryDto.getIsAbnormal()&& orderDeliveryDto.getIsAbnormal()==2) {
					if (null!=orderDeliveryDto.getIsConfirmed()&& orderDeliveryDto.getIsConfirmed()==1) {
						orderDeliveryDto.setOrderStatusName("异常-已确认");
					}else {
						orderDeliveryDto.setOrderStatusName("异常-未确认");
					}
				}
			}
		}
		pm.setDataList(dataList);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		Integer totalCount = lgDeliveryOrderDaoEx.supplierIncomeDetailCount(map);
		pm.setTotalCount(totalCount);
		// 进行中数量 6已匹配 待指派车辆6 5提货中5 4配送中4
		map.put("orderStatus", 1);// 1对应orderStatus: 4 or 5 or 6,isAbnormal:1
		pm.setProcessingNum(lgDeliveryOrderDaoEx.supplierIncomeDetailCount(map));
		// 已完成数量 1，3
		map.put("orderStatus", 0);// 0对应订单状态为 1 or 3,isAbnormal:1
		pm.setFinishedNum(lgDeliveryOrderDaoEx.supplierIncomeDetailCount(map));
		// 异常数量 isAbnormal 是否异常 1正常 2异常
		map.put("orderStatus", 2);// 2对应orderStatus订单状态为 :1,3,4,5,6  isAbnormal:2,isConfirmed:1
		pm.setAbnormalNum(lgDeliveryOrderDaoEx.supplierIncomeDetailCount(map));
		// 全部数量 orderStatus不管
		map.put("orderStatus", 9);// 9对应orderStatus: 1,3,4,5,6
		pm.setAllNum(lgDeliveryOrderDaoEx.supplierIncomeDetailCount(map));
		return pm;
	}

	// 承运商收入明细导出
	@Override
	public List<OrderDeliveryDto> supplierIncomeDetailExport(Map<String, Object> map) {
		map.put("orderName", "updateTime");
		map.put("orderType", "desc");
		List<OrderDeliveryDto> dataList = lgDeliveryOrderDaoEx.supplierIncomeDetailExport(map);
		if (dataList.size()>0) {
			for (OrderDeliveryDto orderDeliveryDto : dataList) {
				if (null!=orderDeliveryDto.getIsAbnormal()&& orderDeliveryDto.getIsAbnormal()==2) {
					if (null!=orderDeliveryDto.getIsConfirmed()&& orderDeliveryDto.getIsConfirmed()==1) {
						orderDeliveryDto.setOrderStatusName("异常-已确认");
					}else {
						orderDeliveryDto.setOrderStatusName("异常-未确认");
					}
				}
			}
		}
		return dataList;
	}
	
	// 未付款的订单第二天自动关闭
	@Override
	public void autoClosePay() {
		List<String> orders = lgDeliveryOrderDaoEx.searchAutoClosePayOrders();
		if (null!=orders && orders.size()>0) {
			for (String pk : orders) {
				//关闭订单
				lgDeliveryOrderDaoEx.closeOrder(pk);
				//存追踪记录
				Date tempDate = new Date();
				LgTrackDetail model=new LgTrackDetail();
				model.setPk(KeyUtils.getUUID());
				model.setDeliveryPk(pk);
				model.setOrderStatus(10);
				model.setStepDetail("订单已关闭");
				model.setFinishedTime(tempDate);
				model.setInsertTime(tempDate);
				model.setUpdateTime(tempDate);
				lgTrackDetailDaoEx.insert(model);
			}
		}
		
		
	}
	
	//待财务确认订单，48小时后关闭
	@Override
	public void closeConfirmOrder() {
		List<String> orders = lgDeliveryOrderDaoEx.searchCloseConfirmOrder();
		if (null!=orders && orders.size()>0) {
			for (String pk : orders) {
				//关闭订单
				lgDeliveryOrderDaoEx.closeOrder(pk);
				//存追踪记录
				Date tempDate = new Date();
				LgTrackDetail model=new LgTrackDetail();
				model.setPk(KeyUtils.getUUID());
				model.setDeliveryPk(pk);
				model.setOrderStatus(10);
				model.setStepDetail("订单已关闭");
				model.setFinishedTime(tempDate);
				model.setInsertTime(tempDate);
				model.setUpdateTime(tempDate);
				lgTrackDetailDaoEx.insert(model);
			}
		}
	}
	
	
	// 商城确认发货
	@Override
	public boolean confirmFaHuoForB2B(LgDeliveryOrderForB2BDto lgDeliveryOrderForB2BDto) {
		// lg_delivery_order
		LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
		String deliveryPk = KeyUtils.getUUID();
		Date tempDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lgDeliveryOrder.setPk(deliveryPk);
		lgDeliveryOrder.setOrderPk(lgDeliveryOrderForB2BDto.getOrderPk() == null ? "" : lgDeliveryOrderForB2BDto.getOrderPk());
		lgDeliveryOrder.setParentPk("-1");
		lgDeliveryOrder.setOrderStatus(6);
		lgDeliveryOrder.setIsMatched(1);
		lgDeliveryOrder.setOriginalTotalFreight(lgDeliveryOrderForB2BDto.getOriginalTotalFreight() == null ? 0.0
				: lgDeliveryOrderForB2BDto.getOriginalTotalFreight());
		lgDeliveryOrder.setPresentTotalFreight(lgDeliveryOrderForB2BDto.getPresentTotalFreight() == null ? 0.0
				: lgDeliveryOrderForB2BDto.getPresentTotalFreight());
		lgDeliveryOrder.setInsertTime(tempDate);
		lgDeliveryOrder.setUpdateTime(tempDate);
		Date orderTimeTemp;
		if (lgDeliveryOrderForB2BDto.getOrderTime() == null || lgDeliveryOrderForB2BDto.getOrderTime().equals("")) {
			orderTimeTemp = null;
		} else {
			try {
				orderTimeTemp = sdf.parse(lgDeliveryOrderForB2BDto.getOrderTime());
			} catch (ParseException e) {
				e.printStackTrace();
				orderTimeTemp = null;
			}
		}
		lgDeliveryOrder.setOrderTime(orderTimeTemp);
		lgDeliveryOrder.setLogisticsCompanyPk(lgDeliveryOrderForB2BDto.getLogisticsCompanyPk() == null ? ""
				: lgDeliveryOrderForB2BDto.getLogisticsCompanyPk());
		lgDeliveryOrder.setLogisticsCompanyName(lgDeliveryOrderForB2BDto.getLogisticsCompanyName() == null ? ""
				: lgDeliveryOrderForB2BDto.getLogisticsCompanyName());
		lgDeliveryOrder.setFromCompanyName(lgDeliveryOrderForB2BDto.getFromCompanyName() == null ? ""
				: lgDeliveryOrderForB2BDto.getFromCompanyName());
		lgDeliveryOrder.setFromAddress(
				lgDeliveryOrderForB2BDto.getFromAddress() == null ? "" : lgDeliveryOrderForB2BDto.getFromAddress());
		lgDeliveryOrder.setFromContacts(
				lgDeliveryOrderForB2BDto.getFromContacts() == null ? "" : lgDeliveryOrderForB2BDto.getFromContacts());
		lgDeliveryOrder.setFromContactsTel(lgDeliveryOrderForB2BDto.getFromContactsTel() == null ? ""
				: lgDeliveryOrderForB2BDto.getFromContactsTel());
		lgDeliveryOrder.setFromProvinceName(lgDeliveryOrderForB2BDto.getFromProvinceName() == null ? ""
				: lgDeliveryOrderForB2BDto.getFromProvinceName());
		lgDeliveryOrder.setFromCityName(
				lgDeliveryOrderForB2BDto.getFromCityName() == null ? "" : lgDeliveryOrderForB2BDto.getFromCityName());
		lgDeliveryOrder.setFromAreaName(
				lgDeliveryOrderForB2BDto.getFromAreaName() == null ? "" : lgDeliveryOrderForB2BDto.getFromAreaName());
		lgDeliveryOrder.setFromTownName(
				lgDeliveryOrderForB2BDto.getFromTownName() == null ? "" : lgDeliveryOrderForB2BDto.getFromTownName());
		lgDeliveryOrder.setToCompanyName(
				lgDeliveryOrderForB2BDto.getToCompanyName() == null ? "" : lgDeliveryOrderForB2BDto.getToCompanyName());
		lgDeliveryOrder.setToAddress(
				lgDeliveryOrderForB2BDto.getToAddress() == null ? "" : lgDeliveryOrderForB2BDto.getToAddress());
		lgDeliveryOrder.setToContacts(
				lgDeliveryOrderForB2BDto.getToContacts() == null ? "" : lgDeliveryOrderForB2BDto.getToContacts());
		lgDeliveryOrder.setToContactsTel(
				lgDeliveryOrderForB2BDto.getToContactsTel() == null ? "" : lgDeliveryOrderForB2BDto.getToContactsTel());
		lgDeliveryOrder.setToProvinceName(lgDeliveryOrderForB2BDto.getToProvinceName() == null ? ""
				: lgDeliveryOrderForB2BDto.getToProvinceName());
		lgDeliveryOrder.setToCityName(
				lgDeliveryOrderForB2BDto.getToCityName() == null ? "" : lgDeliveryOrderForB2BDto.getToCityName());
		lgDeliveryOrder.setToAreaName(
				lgDeliveryOrderForB2BDto.getToAreaName() == null ? "" : lgDeliveryOrderForB2BDto.getToAreaName());
		lgDeliveryOrder.setToTownName(
				lgDeliveryOrderForB2BDto.getToTownName() == null ? "" : lgDeliveryOrderForB2BDto.getToTownName());
		lgDeliveryOrder.setSupplierInvoiceStatus(1);
		lgDeliveryOrder.setMemberInvoiceStatus(1);
		lgDeliveryOrder.setSource(1);
		lgDeliveryOrder
				.setMember(lgDeliveryOrderForB2BDto.getMember() == null ? "" : lgDeliveryOrderForB2BDto.getMember());
		lgDeliveryOrder.setMemberPk(
				lgDeliveryOrderForB2BDto.getMemberPk() == null ? "" : lgDeliveryOrderForB2BDto.getMemberPk());
		lgDeliveryOrder
				.setRemark(lgDeliveryOrderForB2BDto.getRemark() == null ? "" : lgDeliveryOrderForB2BDto.getRemark());
		// 设置订单的提货单号
		int temp = lgDeliveryOrderDaoEx.getDeliveryCountByOrderPk(lgDeliveryOrderForB2BDto.getOrderPk());
		if (temp == 0) {
			lgDeliveryOrder.setDeliveryNumber(lgDeliveryOrderForB2BDto.getOrderPk());
		} else if (temp == 1) {
			//把第一个部分发货的发货单的deliveryNumber改成 abc-1
			String tempPk = lgDeliveryOrderDaoEx.getPkByDeliveryNumber(lgDeliveryOrderForB2BDto.getOrderPk());
			lgDeliveryOrderDaoEx.updateDeliveryNumberByPk(tempPk, lgDeliveryOrderForB2BDto.getOrderPk() + "-1");
			//第二个部分发货的deliveryNumber设置为abc-2
			lgDeliveryOrder.setDeliveryNumber(lgDeliveryOrderForB2BDto.getOrderPk() + "-2");
		} else {
			lgDeliveryOrder.setDeliveryNumber(lgDeliveryOrderForB2BDto.getOrderPk() + "-" + (temp + 1));
		}
		lgDeliveryOrder.setIsAbnormal(1);
		lgDeliveryOrder.setPaymentName(
				lgDeliveryOrderForB2BDto.getPaymentName() == null ? "" : lgDeliveryOrderForB2BDto.getPaymentName());
		Date paymentTimeTemp;
		if (lgDeliveryOrderForB2BDto.getPaymentTime() == null || lgDeliveryOrderForB2BDto.getPaymentTime().equals("")) {
			paymentTimeTemp = null;
		} else {
			try {
				paymentTimeTemp = sdf.parse(lgDeliveryOrderForB2BDto.getPaymentTime());
			} catch (ParseException e) {
				e.printStackTrace();
				paymentTimeTemp = null;
			}
		}
		lgDeliveryOrder.setPaymentTime(paymentTimeTemp);
		lgDeliveryOrder.setPurchaserName(
				lgDeliveryOrderForB2BDto.getPurchaserName() == null ? "" : lgDeliveryOrderForB2BDto.getPurchaserName());
		lgDeliveryOrder.setPurchaserPk(
				lgDeliveryOrderForB2BDto.getPurchaserPk() == null ? "" : lgDeliveryOrderForB2BDto.getPurchaserPk());
		lgDeliveryOrder.setSupplierName(
				lgDeliveryOrderForB2BDto.getSupplierName() == null ? "" : lgDeliveryOrderForB2BDto.getSupplierName());
		lgDeliveryOrder.setSupplierPk(
				lgDeliveryOrderForB2BDto.getSupplierPk() == null ? "" : lgDeliveryOrderForB2BDto.getSupplierPk());
		lgDeliveryOrder.setInvoiceName(
				lgDeliveryOrderForB2BDto.getInvoiceName() == null ? "" : lgDeliveryOrderForB2BDto.getInvoiceName());
		lgDeliveryOrder.setInvoiceTaxidNumber(lgDeliveryOrderForB2BDto.getInvoiceTaxidNumber() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceTaxidNumber());
		lgDeliveryOrder.setInvoiceRegPhone(lgDeliveryOrderForB2BDto.getInvoiceRegPhone() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceRegPhone());
		lgDeliveryOrder.setInvoiceBankAccount(lgDeliveryOrderForB2BDto.getInvoiceBankAccount() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceBankAccount());
		lgDeliveryOrder.setInvoiceBankName(lgDeliveryOrderForB2BDto.getInvoiceBankName() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceBankName());
		lgDeliveryOrder.setInvoiceProvinceName(lgDeliveryOrderForB2BDto.getInvoiceProvinceName() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceProvinceName());
		lgDeliveryOrder.setInvoiceCityName(lgDeliveryOrderForB2BDto.getInvoiceCityName() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceCityName());
		lgDeliveryOrder.setInvoiceAreaName(lgDeliveryOrderForB2BDto.getInvoiceAreaName() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceAreaName());
		lgDeliveryOrder.setInvoiceRegAddress(lgDeliveryOrderForB2BDto.getInvoiceRegAddress() == null ? ""
				: lgDeliveryOrderForB2BDto.getInvoiceRegAddress());
		lgDeliveryOrder.setBasisLinePrice(lgDeliveryOrderForB2BDto.getBasisLinePrice() == null ? 0.0
				: lgDeliveryOrderForB2BDto.getBasisLinePrice());
		lgDeliveryOrder.setSettleWeight(
				lgDeliveryOrderForB2BDto.getSettleWeight() == null ? 0.0 : lgDeliveryOrderForB2BDto.getSettleWeight());
		lgDeliveryOrder.setIsSettleFreight(1);
		// lg_order_goods
		LgDeliveryOrderGoods lgOrderGoods = new LgDeliveryOrderGoods();
		lgOrderGoods.setPk(KeyUtils.getUUID());
		lgOrderGoods.setDeliveryPk(deliveryPk);
		lgOrderGoods.setProductName(
				lgDeliveryOrderForB2BDto.getProductName() == null ? "" : lgDeliveryOrderForB2BDto.getProductName());
		lgOrderGoods.setVarietiesName(
				lgDeliveryOrderForB2BDto.getVarietiesName() == null ? "" : lgDeliveryOrderForB2BDto.getVarietiesName());
		lgOrderGoods.setSeedvarietyName(lgDeliveryOrderForB2BDto.getSeedvarietyName() == null ? ""
				: lgDeliveryOrderForB2BDto.getSeedvarietyName());
		lgOrderGoods.setSpecName(
				lgDeliveryOrderForB2BDto.getSpecName() == null ? "" : lgDeliveryOrderForB2BDto.getSpecName());
		lgOrderGoods.setSeriesName(
				lgDeliveryOrderForB2BDto.getSeriesName() == null ? "" : lgDeliveryOrderForB2BDto.getSeriesName());
		lgOrderGoods.setGradeName(
				lgDeliveryOrderForB2BDto.getGradeName() == null ? "" : lgDeliveryOrderForB2BDto.getGradeName());
		lgOrderGoods.setBatchNumber(
				lgDeliveryOrderForB2BDto.getBatchNumber() == null ? "" : lgDeliveryOrderForB2BDto.getBatchNumber());
		lgOrderGoods
				.setWeight(lgDeliveryOrderForB2BDto.getWeight() == null ? 0.0 : lgDeliveryOrderForB2BDto.getWeight());
		lgOrderGoods.setBoxes(lgDeliveryOrderForB2BDto.getBoxes() == null ? 0 : lgDeliveryOrderForB2BDto.getBoxes());
		// lgOrderGoods.setOrderNumber(lgDeliveryOrderForB2BDto.getOrderPk()==null?"":lgDeliveryOrderForB2BDto.getOrderPk());
		lgOrderGoods.setTareWeight(
				lgDeliveryOrderForB2BDto.getTareWeight() == null ? 0.0 : lgDeliveryOrderForB2BDto.getTareWeight());
		lgOrderGoods.setOriginalFreight(lgDeliveryOrderForB2BDto.getOriginalFreight() == null ? 0.0
				: lgDeliveryOrderForB2BDto.getOriginalFreight());
		lgOrderGoods.setPresentFreight(lgDeliveryOrderForB2BDto.getPresentFreight() == null ? 0.0
				: lgDeliveryOrderForB2BDto.getPresentFreight());
		lgOrderGoods.setGoodsName(
				lgDeliveryOrderForB2BDto.getGoodsName() == null ? "" : lgDeliveryOrderForB2BDto.getGoodsName());
		lgOrderGoods.setGoodsOriginalFreight(
				lgDeliveryOrderForB2BDto.getOriginalFreight() * lgDeliveryOrderForB2BDto.getWeight());
		lgOrderGoods.setGoodsPresentFreight(
				lgDeliveryOrderForB2BDto.getPresentFreight() * lgDeliveryOrderForB2BDto.getWeight());
		lgOrderGoods.setUnit(lgDeliveryOrderForB2BDto.getUnit() == null ? null : lgDeliveryOrderForB2BDto.getUnit());
		if (lgDeliveryOrderDaoEx.insert(lgDeliveryOrder) > 0) {
			if (lgDeliveryOrderGoodsDaoEx.insert(lgOrderGoods) > 0) {
				// 插入一条物流轨迹，“商城确认发货”
				LgTrackDetail lgTrackDetail = new LgTrackDetail();
				lgTrackDetail.setPk(KeyUtils.getUUID());
				lgTrackDetail.setDeliveryPk(lgDeliveryOrder.getPk());
				lgTrackDetail.setStepDetail("商城确认发货，推送发货单成功");
				lgTrackDetail.setOrderStatus(6);
				lgTrackDetail.setFinishedTime(tempDate);
				lgTrackDetail.setInsertTime(tempDate);
				lgTrackDetail.setUpdateTime(tempDate);
				lgTrackDetailDaoEx.insert(lgTrackDetail);
				return true;
			} else {
				lgDeliveryOrderDaoEx.delete(deliveryPk);
				return false;
			}
		} else {
			return false;
		}
	}


	/*@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean translateDeliveryOrder(String orderNumber) {
		boolean result = true;
		// 判断是否已经存在该订单，如果存在直接返回false。
		List<LgDeliveryOrderDto> byOrderNumber = lgDeliveryOrderDaoEx.getByOrderNumber(orderNumber);
		if (byOrderNumber != null && byOrderNumber.size() > 0)
			return false;
		// 订单状态判断，已经付款的平台承运订单。否则退出。
		B2bOrderDto b2bOrderDto = b2bOrderService.searchOrderDetails(orderNumber);
		if (b2bOrderDto.getOrderStatus() == null || b2bOrderDto.getOrderStatus() != 3)
			return false;
		if (b2bOrderDto.getLogisticsModelPk() == null || b2bOrderDto.getLogisticsModelPk() != 1)
			return false;
		// 获取订单信息和订单商品信息 转为对应的提货单 todo 接口调用,此处直接查表

		Date orderTime = b2bOrderDto.getInsertTime();
		String town = b2bOrderDto.getTown();
		String townName = b2bOrderDto.getTownName();
		String area = b2bOrderDto.getArea();
		String areaName = b2bOrderDto.getAreaName();
		String province = b2bOrderDto.getProvince();
		String provinceName = b2bOrderDto.getProvinceName();
		String city = b2bOrderDto.getCity();
		String cityName = b2bOrderDto.getCityName();
		String memberPk = b2bOrderDto.getMemberPk();
		String memberName = b2bOrderDto.getMemberName();
		String supplierPk = b2bOrderDto.getSupplierPk();
		String supplierName = b2bOrderDto.getSupplierName();
		String purchaserName = b2bOrderDto.getPurchaserName();
		String purchaserPk = b2bOrderDto.getPurchaserPk();
		String address1 = b2bOrderDto.getAddress();
		String fromProvincePk = "1643";
		String fromCityPk = "1692";
		String fromAreaPk = "1702";
		String fromTownPk = "3267";

		List<B2bOrderGoodsDto> orderGoods = b2bOrderDto.getOrderGoods();
		for (B2bOrderGoodsDto b2bOrderGoodsDto : orderGoods) {

			LgDeliveryOrder deliveryOrder = new LgDeliveryOrder();
			String pk = KeyUtils.getUUID();
			deliveryOrder.setPk(pk);
			LgOrderGoods lgOrderGoods = new LgOrderGoods();
			lgOrderGoods.setDeliveryPk(pk);
			lgOrderGoods.setPk(KeyUtils.getUUID());

			String batchNumber = b2bOrderGoodsDto.getBatchNumber();
			lgOrderGoods.setBatchNumber(batchNumber);
			// 仓库
			String warePk = b2bOrderGoodsDto.getWarePk();
			B2bWareDto wareDto = b2bWareDao.getByPk(warePk);
			if (wareDto != null) {
				String address = wareDto.getAddress();
				deliveryOrder.setFromAddress(address);

			}
			// 提货地址
			deliveryOrder.setFromProvincePk(fromProvincePk);
			deliveryOrder.setFromCityPk(fromCityPk);
			deliveryOrder.setFromAreaPk(fromAreaPk);
			deliveryOrder.setFromTownPk(fromTownPk);

			// 付款
			deliveryOrder.setPaymentPk(b2bOrderDto.getPaymentPk());
			deliveryOrder.setPaymentName(b2bOrderDto.getPaymentName());
			deliveryOrder.setPaymentTime(b2bOrderDto.getRepaymentTime());
			deliveryOrder.setRepaymentTime(b2bOrderDto.getRepaymentTime());
			deliveryOrder.setIsConfirmed(2);

			// 店铺
			String storePk = b2bOrderDto.getStorePk();
			B2bStoreDto storeDto = b2bStoreDao.getByPk(storePk);
			if (storeDto != null) {
				String contacts = storeDto.getContacts();
				String contactsTel = storeDto.getContactsTel();
				deliveryOrder.setParentPk("-1");
				deliveryOrder.setFromContacts(contacts);
				deliveryOrder.setFromContactsTel(contactsTel);
				deliveryOrder.setFromCompanyName(supplierName);
				deliveryOrder.setFromCompanyPk(supplierPk);
				deliveryOrder.setToCompanyPk(purchaserPk);
				deliveryOrder.setToCompanyName(purchaserName);
				deliveryOrder.setToAddress(address1);
				deliveryOrder.setToAreaName(areaName);
				deliveryOrder.setToAreaPk(area);
				deliveryOrder.setToTownName(townName);
				deliveryOrder.setToTownPk(town);
				deliveryOrder.setToCityName(cityName);
				deliveryOrder.setToCityPk(city);
				deliveryOrder.setToProvinceName(provinceName);
				deliveryOrder.setToProvincePk(province);
				deliveryOrder.setMember(memberName);
				deliveryOrder.setMemberPk(memberPk);
				deliveryOrder.setOrderTime(orderTime);
				deliveryOrder.setSource(1); // 1电商0物流系统
				deliveryOrder.setIsAbnormal(1);
				deliveryOrder.setOrderPk(orderNumber);
				deliveryOrder.setDeliveryNumber(KeyUtils.getOrderNumber());

			}

			// 商品
			String goodsPk = b2bOrderGoodsDto.getGoodsPk();
			B2bGoodsDto goodsDto = b2bGoodsDao.getByPk(goodsPk);
			if (goodsDto != null) {
				Integer boxes = b2bOrderGoodsDto.getBoxes();
				String goodsName = b2bOrderGoodsDto.getGoodsName();
				String gradeName = b2bOrderGoodsDto.getGradeName();
				String gradePk = b2bOrderGoodsDto.getGradePk();
				Double originalFreight = b2bOrderGoodsDto.getOriginalFreight();
				Double presentFreight = b2bOrderGoodsDto.getPresentFreight();
				String productName = b2bOrderGoodsDto.getProductName();
				String productPk = b2bOrderGoodsDto.getProductPk();
				String seedvarietyName = b2bOrderGoodsDto.getSeedvarietyName();
				String seedvarietyPk = b2bOrderGoodsDto.getSeedvarietyPk();
				String seriesName = b2bOrderGoodsDto.getSeriesName();
				String seriesPk = b2bOrderGoodsDto.getSeriesPk();
				String specName = b2bOrderGoodsDto.getSpecName();
				String specPk = b2bOrderGoodsDto.getSpecPk();
				String varietiesName = b2bOrderGoodsDto.getVarietiesName();
				String varietiesPk = b2bOrderGoodsDto.getVarietiesPk();
				Double tareWeight = b2bOrderGoodsDto.getTareWeight();
				Double weight = b2bOrderGoodsDto.getWeight();

				lgOrderGoods.setBoxes(boxes);
				lgOrderGoods.setGoodsPk(goodsPk);
				lgOrderGoods.setGoodsName(goodsName);
				lgOrderGoods.setGradeName(gradeName);
				lgOrderGoods.setGradePk(gradePk);
				lgOrderGoods.setOriginalFreight(originalFreight);
				lgOrderGoods.setPresentFreight(presentFreight);
				lgOrderGoods.setProductName(productName);
				lgOrderGoods.setProductPk(productPk);
				lgOrderGoods.setSeedvarietyName(seedvarietyName);
				//lgOrderGoods.setSeedvarietyPk(seedvarietyPk);
				lgOrderGoods.setSeriesName(seriesName);
				//lgOrderGoods.setSeriesPk(seriesPk);
				lgOrderGoods.setSpecName(specName);
				//lgOrderGoods.setSpecPk(specPk);
				lgOrderGoods.setVarietiesName(varietiesName);
				//lgOrderGoods.setVarietiesPk(varietiesPk);
				lgOrderGoods.setWeight(weight);
				lgOrderGoods.setTareWeight(tareWeight);
			}
			// 插入物流轨迹信息
			trackDao.insert(createTrackDto(deliveryOrder, 9));
			trackDao.insert(createTrackDto(deliveryOrder, 8));
			// 匹配物流公司
			LogisticsRouteDto routeDto = findSupplierCompanyInfo(deliveryOrder, lgOrderGoods);
			if (routeDto == null || routeDto.getCompanyPk() == null) {
				deliveryOrder.setOrderStatus(7);// 未找到匹配的物流公司
			} else {
				deliveryOrder.setOrderStatus(6);// 已匹配
				deliveryOrder.setLogisticsCompanyPk(routeDto.getCompanyPk());
				deliveryOrder.setLogisticsCompanyName(routeDto.getCompanyName());
			}
			// 同步父级采购商，供应商信息
			deliveryOrder.setPurchaserName(purchaserName);
			deliveryOrder.setPurchaserPk(purchaserPk);
			deliveryOrder.setSupplierName(supplierName);
			deliveryOrder.setSupplierPk(supplierPk);
			B2bCompayDto purDto = companyDao.selectParentDetail(deliveryOrder.getPurchaserPk());
			B2bCompayDto supplierDto = companyDao.selectParentDetail(deliveryOrder.getSupplierPk());
			if (purDto != null) {
				deliveryOrder.setPurchaserParentPk(purDto.getPk());
				deliveryOrder.setPurchaserParentName(purDto.getName());
			}
			if (supplierDto != null) {
				deliveryOrder.setSupplierParentPk(supplierDto.getPk());
				deliveryOrder.setSupplierParentName(supplierDto.getName());
			}

			// 同步发票信息
			deliveryOrder.setInvoicePk(b2bOrderDto.getInvoicePk());
			deliveryOrder.setInvoiceName(b2bOrderDto.getInvoiceName());
			deliveryOrder.setInvoiceTaxidNumber(b2bOrderDto.getInvoiceTaxidNumber());
			deliveryOrder.setInvoiceRegPhone(b2bOrderDto.getInvoiceRegPhone());
			deliveryOrder.setInvoiceBankAccount(b2bOrderDto.getInvoiceBankAccount());
			deliveryOrder.setInvoiceBankName(b2bOrderDto.getInvoiceBankName());
			deliveryOrder.setInvoiceRegAddress(b2bOrderDto.getInvoiceRegAddress());

			int insert = lgDeliveryOrderDao.insert(deliveryOrder);
			int insert1 = lgOrderGoodsDao.insert(lgOrderGoods);
		}

		return result;
	}

	// 查询线路，找出物流供应商
	public LogisticsRouteDto findSupplierCompanyInfo(LgDeliveryOrder delivery, LgOrderGoods good) {
		LogisticsRouteDto dto = new LogisticsRouteDto();
		SearchLgLine searchVo = new SearchLgLine();
		searchVo.setFromProvicePk(delivery.getFromProvincePk());
		searchVo.setFromCityPk(delivery.getFromCityPk());
		searchVo.setFromAreaPk(delivery.getFromAreaPk());
		searchVo.setFromTownPk(delivery.getFromTownPk());
		searchVo.setToProvicePk(delivery.getToProvincePk());
		searchVo.setToCityPk(delivery.getToCityPk());
		searchVo.setToAreaPk(delivery.getToAreaPk());
		searchVo.setToTownPk(delivery.getToTownPk());
		searchVo.setProductPk(good.getProductPk());
		searchVo.setGradePk(good.getGradePk());
		List<LogisticsRouteDto> lineList = lineService.getLogisticsSetpinfos(searchVo, good.getWeight());
		if (lineList != null && lineList.size() > 0) {
			dto = lineList.get(0);
			return dto;
		}
		return dto;
	}

	@Override
	public boolean existDeliveryOrder(String orderNumber) {
		return false;
	}

	// ERP订单同步
	@Override
	@Transactional
	public RestCode orderSynchronous(ErpOrderSynchronous vo) {
		if (vo.getOrderPk() == null && vo.getOrderPk().equals("") && vo.getDeliveryNumber() == null
				&& vo.getDeliveryNumber().equals("")) {
			return RestCode.CODE_T000;
		}
		Map<String, Object> par = new HashMap<String, Object>();
		par.put("orderPk", vo.getDeliveryNumber() == null || vo.getDeliveryNumber().equals("") ? vo.getOrderPk()
				: vo.getDeliveryNumber());
		par.put("goodsPk", "");
		Integer num = lgDeliveryOrderDao.selectUniquefromDeliveryAndGoods(par);
		if (num > 0) {
			return RestCode.CODE_O010;
		}
		try {
			// 查找地区pk
			LgRegionsPk FromRegions = findRegionsPk(vo.getFromProvinceName(), vo.getFromCityName(),
					vo.getFromAreaName(), vo.getFromTownName());
			LgRegionsPk toRegions = findRegionsPk(vo.getToProvinceName(), vo.getToCityName(), vo.getToAreaName(),
					vo.getToTownName());
			B2bGradeDto b2bGradeDto = b2bGradeService.getGradeByName(vo.getGradeName());
			B2bProductDto b2bProductDto = b2bProductService.getProductByName(vo.getProductName());
			vo.setProductPk(b2bProductDto.getPk());
			vo.setGradePk(b2bGradeDto.getPk());
			vo.setFromProvincePk(FromRegions.getProvincePk());
			vo.setFromCityPk(FromRegions.getCityPk());
			vo.setFromAreaPk(FromRegions.getAreaPk());
			vo.setFromTownPk(FromRegions.getTownPk());
			vo.setToProvincePk(toRegions.getProvincePk());
			vo.setToCityPk(toRegions.getCityPk());
			vo.setToAreaPk(toRegions.getAreaPk());
			vo.setToTownPk(toRegions.getTownPk());
			LgDeliveryOrder deliveryOrder = createDeliveryOrder(vo);
			LgOrderGoods good = createGoods(vo, deliveryOrder.getPresentTotalFreight(), deliveryOrder.getPk());
			good.setDeliveryPk(deliveryOrder.getPk());
			if (deliveryOrder.getOrderStatus() == 6) {
				trackDao.insert(createTrackDto(deliveryOrder, 9));
				trackDao.insert(createTrackDto(deliveryOrder, 8));
			} else {
				trackDao.insert(createTrackDto(deliveryOrder, 9));
			}
			lgDeliveryOrderDao.insert(deliveryOrder);
			lgOrderGoodsDao.insert(good);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RestCode.CODE_S999;
		}
		return RestCode.CODE_0000;
	}

	private LgRegionsPk findRegionsPk(String fromProvinceName, String fromCityName, String fromAreaName,
			String fromTownName) throws UnsupportedEncodingException {
		String pk = "";
		LgRegionsPk lgRegionsName = new LgRegionsPk();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		// 去除最后一个字符
		if (fromProvinceName != null && !fromProvinceName.equals("")) {
			map.put("name", cancelLastVal(fromProvinceName));
			map.put("parentPk", -1);
			pk = sysRegionsDao.selectRegionsNameByName(map);
			lgRegionsName.setProvincePk(pk);
			if (fromCityName != null && !fromCityName.equals("") && pk != null && !pk.equals("")) {
				map.put("name", cancelLastVal(fromCityName));
				map.put("parentPk", pk);
				pk = sysRegionsDao.selectRegionsNameByName(map);
				lgRegionsName.setCityPk(pk);
				if (pk != null && !pk.equals("") && fromAreaName != null && !fromAreaName.equals("")) {
					map.put("name", cancelLastVal(fromAreaName));
					map.put("parentPk", pk);
					pk = sysRegionsDao.selectRegionsNameByName(map);
					lgRegionsName.setAreaPk(pk);
					if (pk != null && !pk.equals("") && fromTownName != null && !fromTownName.equals("")) {
						map.put("name", cancelLastVal(fromTownName));
						map.put("parentPk", pk);
						pk = sysRegionsDao.selectRegionsNameByName(map);
						lgRegionsName.setTownPk(pk);
					} else {
						return lgRegionsName;
					}
				} else {
					return lgRegionsName;
				}
			}

		}

		return lgRegionsName;
	}

	private String cancelLastVal(String name) throws UnsupportedEncodingException {
		if (name == null || name.equals("")) {
			return name;
		}
		String str = "省市区镇";
		String valtemp = name.substring(name.length() - 1, name.length());
		if (new String(str.getBytes(), "utf-8").indexOf(new String(valtemp.getBytes(), "utf-8")) != -1) {
			return name.substring(0, name.length() - 1);
		} else {
			return name;
		}
	}

	private LgOrderGoods createGoods(ErpOrderSynchronous vo, Double presentTotalFreight, String deliveryPk) {
		LgOrderGoods go = new LgOrderGoods();
		String pk = KeyUtils.getUUID();
		go.setPk(pk);
		go.setDeliveryPk(deliveryPk);
		// go.setGoodsPk(vo.getGoodsPk());
		go.setGoodsName(vo.getGoodsName());
		go.setProductName(vo.getProductName());
		go.setProductPk(vo.getProductPk());
		go.setVarietiesName(vo.getVarietiesName());
		// go.setVarietiesPk(vo.getVarietiesPk());
		go.setSeedvarietyName(vo.getSeedvarietyName());
		// go.setSeedvarietyPk(vo.getSeedvarietyPk());
		go.setSpecName(vo.getSpecName());
		//go.setSpecPk(vo.getSpecPk());
		go.setSeriesName(vo.getSeriesName());
		// go.setSeriesPk(vo.getSeriesPk());
		go.setGradeName(vo.getGradeName());
		go.setGradePk(vo.getGradePk());
		go.setBatchNumber(vo.getBatchNumber());
		go.setWeight(vo.getWeight());
		go.setBoxes(vo.getBoxes());
		go.setOrderNumber(vo.getDeliveryNumber() == null || vo.getDeliveryNumber().equals("") ? vo.getOrderPk()
				: vo.getDeliveryNumber());
		go.setTareWeight(vo.getTareWeight());
		go.setOriginalFreight(vo.getOriginalFreight());
		go.setPresentFreight(vo.getPresentFreight());
		go.setGoodsOriginalFreight(presentTotalFreight);
		go.setGoodsPresentFreight(presentTotalFreight);
		go.setUnit(vo.getUnit());
		return go;
	}

	public LgDeliveryOrder createDeliveryOrder(ErpOrderSynchronous vo) throws ParseException {
		LgDeliveryOrder deliveryOrder = new LgDeliveryOrder();
		Date tempDate = new Date();
		String pk = KeyUtils.getUUID();
		deliveryOrder.setPk(pk);
		deliveryOrder.setOrderPk(vo.getDeliveryNumber() == null || vo.getDeliveryNumber().equals("") ? vo.getOrderPk()
				: vo.getDeliveryNumber());
		deliveryOrder.setParentPk("-1");
		deliveryOrder.setInsertTime(tempDate);
		deliveryOrder.setUpdateTime(deliveryOrder.getInsertTime());
		deliveryOrder.setOrderTime(tempDate);
		deliveryOrder.setOrderStatus(vo.getOrderStatus());
		// 匹配物流公司
		if (vo.getOrderStatus() == 9) {
			LogisticsRouteDto routeDto = findSupplierCompanyInfo(vo);
			if (routeDto == null || routeDto.getCompanyPk() == null) {
				// deliveryOrder.setOrderStatus(9);
				deliveryOrder.setIsMatched(0);
				;// 未找到匹配的物流公司
				deliveryOrder.setOrderTime(new Date());
				deliveryOrder.setPresentTotalFreight(0.0);
				deliveryOrder.setOriginalTotalFreight(0.0);
			} else {
				// deliveryOrder.setOrderStatus(9);
				deliveryOrder.setIsMatched(1);
				deliveryOrder.setOrderTime(new Date());
				deliveryOrder.setLogisticsCompanyPk(routeDto.getCompanyPk());
				deliveryOrder.setLogisticsCompanyName(routeDto.getCompanyName());
				deliveryOrder.setLinePricePk(routeDto.getList().get(0).getPk());
				deliveryOrder.setBasisLinePrice(routeDto.getList().get(0).getBasisPrice());
				vo.setOriginalFreight(routeDto.getList().get(0).getSalePrice());
				vo.setPresentFreight(routeDto.getList().get(0).getSalePrice());
				deliveryOrder.setSettleWeight(vo.getWeight());
				deliveryOrder.setPresentTotalFreight(vo.getWeight() * routeDto.getList().get(0).getSalePrice());
				deliveryOrder.setOriginalTotalFreight(vo.getWeight() * routeDto.getList().get(0).getSalePrice());
			}
		} else {
			// deliveryOrder.setOrderStatus(6);
			deliveryOrder.setIsMatched(1);
			deliveryOrder.setSettleWeight(vo.getWeight());
			// deliveryOrder.setLogisticsCompanyPk(vo.getLogisticsCompanyPk());
			deliveryOrder.setLogisticsCompanyName(vo.getLogisticsCompanyName());
			// deliveryOrder.setLinePricePk(vo.getLinePricePk());
			deliveryOrder.setBasisLinePrice(vo.getBasisLinePrice());
			deliveryOrder.setPresentTotalFreight(vo.getPresentTotalFreight());
			deliveryOrder.setOriginalTotalFreight(vo.getPresentTotalFreight());
		}
		deliveryOrder.setArrivedTimeStart(vo.getArrivedTimeStart() == null || vo.getArrivedTimeStart().equals("") ? null
				: DateUtil.dateStringToTimestamp(vo.getArrivedTimeStart()));
		deliveryOrder.setArrivedTimeEnd(vo.getArrivedTimeEnd() == null || vo.getArrivedTimeEnd().equals("") ? null
				: DateUtil.dateStringToTimestamp(vo.getArrivedTimeEnd()));
		deliveryOrder.setFromContacts(vo.getFromContacts());
		deliveryOrder.setFromContactsTel(vo.getFromContactsTel());
		deliveryOrder.setFromAddress(vo.getFromAddress());
		deliveryOrder.setFromCompanyName(vo.getFromCompanyName());
		// deliveryOrder.setFromCompanyPk(vo.getFromCompanyPk());
		deliveryOrder.setFromProvincePk(vo.getFromProvincePk());
		deliveryOrder.setFromCityPk(vo.getFromCityPk());
		deliveryOrder.setFromAreaPk(vo.getFromAreaPk());
		deliveryOrder.setFromTownPk(vo.getFromTownPk());
		deliveryOrder.setFromProvinceName(vo.getFromProvinceName());
		deliveryOrder.setFromCityName(vo.getFromCityName());
		deliveryOrder.setFromAreaName(vo.getFromAreaName());
		deliveryOrder.setFromTownName(vo.getFromTownName());

		deliveryOrder.setToContacts(vo.getToContacts());
		deliveryOrder.setToContactsTel(vo.getToContactsTel());
		deliveryOrder.setToAddress(vo.getToAddress());
		deliveryOrder.setToCompanyName(vo.getToCompanyName());
		deliveryOrder.setToCompanyPk(vo.getToCompanyPk());
		deliveryOrder.setToProvincePk(vo.getToProvincePk());
		deliveryOrder.setToCityPk(vo.getToCityPk());
		deliveryOrder.setToAreaPk(vo.getToAreaPk());
		deliveryOrder.setToTownPk(vo.getToTownPk());
		deliveryOrder.setToProvinceName(vo.getToProvinceName());
		deliveryOrder.setToCityName(vo.getToCityName());
		deliveryOrder.setToAreaName(vo.getToAreaName());
		deliveryOrder.setToTownName(vo.getToTownName());

		deliveryOrder.setSource(2);
		deliveryOrder.setMember(vo.getMember());
		// deliveryOrder.setMemberPk(vo.getMemberPk());
		deliveryOrder.setRemark(vo.getRemark());
		deliveryOrder.setDeliveryNumber(vo.getDeliveryNumber() == null || vo.getDeliveryNumber().equals("")
				? vo.getOrderPk() : vo.getDeliveryNumber());
		deliveryOrder.setOrderNumber2(vo.getDeliveryNumber() != null && !vo.getDeliveryNumber().equals("")
				&& vo.getOrderPk() != null && !vo.getOrderPk().equals("") ? vo.getOrderPk() : "");
		deliveryOrder.setIsAbnormal(1);
		// deliveryOrder.setPaymentPk(vo.getPaymentPk());
		deliveryOrder.setPaymentName(vo.getPaymentName());
		deliveryOrder.setPaymentTime(tempDate);
		deliveryOrder.setRepaymentTime(vo.getRepaymentTime() == null || vo.getRepaymentTime().equals("") ? null
				: DateUtil.dateStringToTimestamp(vo.getRepaymentTime()));
		deliveryOrder.setPurchaserName(vo.getPurchaserName());
		// deliveryOrder.setPurchaserPk(vo.getPurchaserPk());
		deliveryOrder.setSupplierName(vo.getSupplierName());
		// deliveryOrder.setSupplierPk(vo.getSupplierPk());
		deliveryOrder.setPurchaserParentPk("-1");
		deliveryOrder.setSupplierParentPk("-1");
		// deliveryOrder.setInvoicePk(vo.getInvoicePk());
		deliveryOrder.setInvoiceName(vo.getInvoiceName());
		deliveryOrder.setInvoiceTaxidNumber(vo.getInvoiceTaxidNumber());
		deliveryOrder.setInvoiceRegPhone(vo.getInvoiceRegPhone());
		deliveryOrder.setInvoiceBankAccount(vo.getInvoiceBankAccount());
		deliveryOrder.setInvoiceBankName(vo.getInvoiceBankName());
		// deliveryOrder.setInvoiceProvince(vo.getInvoiceProvince());
		deliveryOrder.setInvoiceProvinceName(vo.getInvoiceProvinceName());
		// deliveryOrder.setInvoiceCity(vo.getInvoiceCity());
		deliveryOrder.setInvoiceCityName(vo.getInvoiceCityName());
		// deliveryOrder.setInvoiceArea(vo.getInvoiceArea());
		deliveryOrder.setInvoiceAreaName(vo.getInvoiceAreaName());

		deliveryOrder.setInvoiceRegAddress(vo.getInvoiceRegAddress());
		deliveryOrder.setIsSettleFreight(1);// 未结算
		deliveryOrder.setMemberInvoiceStatus(1);// 未开票
		deliveryOrder.setSupplierInvoiceStatus(1);// 未开票
		// deliveryOrder.setPayClosed(0);//支付开关，0：未关闭，1：已关闭 （待付款的订单，第二天自动关闭）
		return deliveryOrder;
	}

	// 查询线路，找出物流供应商
	public LogisticsRouteDto findSupplierCompanyInfo(ErpOrderSynchronous vo) {
		LogisticsRouteDto dto = new LogisticsRouteDto();
		SearchLgLine searchVo = new SearchLgLine();

		searchVo.setFromProvicePk(vo.getFromProvincePk());
		searchVo.setFromCityPk(vo.getFromCityPk());
		searchVo.setFromAreaPk(vo.getFromAreaPk());
		searchVo.setFromTownPk(vo.getFromTownPk());

		searchVo.setToProvicePk(vo.getToProvincePk());
		searchVo.setToCityPk(vo.getToCityPk());
		searchVo.setToAreaPk(vo.getToAreaPk());
		searchVo.setToTownPk(vo.getToTownPk());

		searchVo.setProductPk(vo.getProductPk());
		searchVo.setGradePk(vo.getGradePk());
		List<LogisticsRouteDto> lineList = lineService.getLogisticsSetpinfos(searchVo, vo.getWeight());
		if (lineList != null && lineList.size() > 0) {
			dto = lineList.get(0);
			return dto;
		}
		return dto;
	}

	// ERP出库
	@Override
	public void deliveryConfirmationForErp(String deliveryPk) throws Exception {
		LgDeliveryOrderDto dto = lgDeliveryOrderDao.getByPk(deliveryPk);
		Date tempDate = new Date();
		dto.setDeliveryTime(tempDate);
		dto.setOrderStatus(4);// 订单状态 9待付款9 8待财务确认8 6财务已确认 待指派车辆6 5提货中5 4配送中4
								// 3已签收3 2已取消2 1完成1
		dto.setUpdateTime(tempDate);
		LgDeliveryOrder vo = new LgDeliveryOrder();
		BeanUtils.copyProperties(vo, dto);
		int num = lgDeliveryOrderDao.updateByPkSelective(dto);
		if (num == 0) {
			throw new Exception();
		} else {
			// 插入订单信息追踪记录
			LgTrackDetailDto trackDetailDto = new LgTrackDetailDto();
			trackDetailDto.setPk(KeyUtils.getUUID());
			trackDetailDto.setDeliveryPk(vo.getPk());
			String logisticsCompanyName = vo.getLogisticsCompanyName() == null ? "" : vo.getLogisticsCompanyName();
			String driver = vo.getDriver() == null ? "" : vo.getDriver();
			String driverContact = vo.getDriverContact() == null ? "" : vo.getDriverContact();
			trackDetailDto.setStepDetail(
					"ERP出库成功，物流公司：" + logisticsCompanyName + "正在配送中。司机：" + driver + "，手机号码：" + driverContact);
			trackDetailDto.setOrderStatus(4);
			trackDetailDto.setFinishedTime(tempDate);
			trackDetailDto.setInsertTime(tempDate);
			trackDetailDto.setUpdateTime(tempDate);
			trackDao.insert(trackDetailDto);
			// 发送短信，告诉平台用户平台正在为您送货中
			LgDeliveryOrderDto dtoTemp = lgDeliveryOrderDao.getByPk(deliveryPk);
			SysSmsTemplateDto sysSmsTemplateDto = sysService.getSmsByName("comfirmDelivery");
			Map<String, String> mapTemp = new HashMap<>();
			mapTemp.put("mobile", dtoTemp.getDriverContact());
			mapTemp.put("plateNumber", dtoTemp.getCarPlate());
			LgMemberDto member = new LgMemberDto();
			member.setPk(dtoTemp.getMemberPk());// 下单用户
			member.setMobile(dtoTemp.getMember());// 下单用户的电话
			member.setCompanyPk(dtoTemp.getPurchaserPk());// 采购商pk
			SmsUtils.send(sysService.getContent(mapTemp, sysSmsTemplateDto), member, member, mongoTemplate);
		}

	}

	// 根据deliveryNumber检查订单状态
	@Override
	public int checkOrderStatusByDeliveryNumber(String deliveryNumber) {
		return lgDeliveryOrderDao.checkOrderStatusByDeliveryNumber(deliveryNumber);
	}


	//根据deliveryNumber查询订单主键
	@Override
	public String getPkByDeliveryNumber(String deliveryNumber) {
		return lgDeliveryOrderDao.getPkByDeliveryNumber(deliveryNumber);
	}


	//根据deliveryNumber检查订单是否存在
	@Override
	public int checkOrderExistByDeliveryNumber(String deliveryNumber) {
		return lgDeliveryOrderDao.checkOrderExistByDeliveryNumber(deliveryNumber);
	}

	//根据deliveryNumber查询订单
	@Override
	public LgDeliveryOrderDto getOrderByDeliveryNumber(String deliveryNumber) {
		return lgDeliveryOrderDao.getByDeliveryNumber(deliveryNumber);
	}

	//根据orderPk查询订单的数量
	@Override
	public Integer getDeliveryCountByOrderPk(String orderPk) {
		return lgDeliveryOrderDao.getDeliveryCountByOrderPk(orderPk);
	}

	//根据pk修改deliveryNumber
	@Override
	public Integer updateDeliveryNumberByPk(String pk, String deliveryNumber) {
		return lgDeliveryOrderDao.updateDeliveryNumberByPk(pk,deliveryNumber);
	}

	*/

	
	

}
