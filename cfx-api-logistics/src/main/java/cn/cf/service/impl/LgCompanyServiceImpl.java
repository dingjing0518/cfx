package cn.cf.service.impl;

import cn.cf.dao.LgCompanyDaoEx;
import cn.cf.dao.LgDeliveryOrderDaoEx;
import cn.cf.dao.LgDeliveryOrderGoodsDaoEx;
import cn.cf.dao.LgLineDaoEx;
import cn.cf.dao.LgLinePriceDaoEx;
import cn.cf.dao.LgMemberDeliveryOrderDaoEx;
import cn.cf.dao.LgRoleDaoEx;
import cn.cf.dao.LgTrackDetailDaoEx;
import cn.cf.dao.SysRegionsDaoEx;
import cn.cf.dto.ForB2BLgPriceDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgDeliveryOrderForB2BDto;
import cn.cf.dto.LgDeliveryOrderForB2BPTDto;
import cn.cf.dto.LgLineDto;
import cn.cf.dto.LgLinePriceDto;
import cn.cf.dto.LgMemberDeliveryOrderDtoEx;
import cn.cf.dto.LgSearchPriceForB2BDto;
import cn.cf.dto.LogisticsLinePriceDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.SearchLgLine;
import cn.cf.model.LgDeliveryOrder;
import cn.cf.model.LgDeliveryOrderGoods;
import cn.cf.model.LgTrackDetail;
import cn.cf.service.LgCompanyService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.caucho.hessian.server.HessianServlet;


@Service
@WebServlet(name="LgCompanyServiceExporter", urlPatterns={"/logistics/service/lgCompany"})
public class LgCompanyServiceImpl extends HessianServlet implements LgCompanyService {
	
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private LgCompanyDaoEx lgCompanyDaoEx;

	@Autowired
	private LgDeliveryOrderDaoEx lgDeliveryOrderDaoEx;
	
	@Autowired
	private LgDeliveryOrderGoodsDaoEx lgDeliveryOrderGoodsDaoEx;
	
	@Autowired
	private LgTrackDetailDaoEx lgTrackDetailDaoEx;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private LgLineDaoEx lgLineDaoEx;
	
	@Autowired
	private LgRoleDaoEx lgRoleDaoEx;
	
	@Autowired
	private LgMemberDeliveryOrderDaoEx lgMemberDeliveryOrderDaoEx;

	@Autowired
	LgLinePriceDaoEx lgLinePriceDaoEx;
	
	@Autowired
	SysRegionsDaoEx regionsDaoEx;
	/**
	 * 根据承运商名称查询承运商
	 */
	@Override
	public LgCompanyDto getLogisticsByName(String logisticsName) {
		return lgCompanyDaoEx.getLogisticsByName(logisticsName);
	}
	
	/**
	 * 商城确认发货(商家承运)
	 */
	@Override
	public boolean confirmFaHuoForB2BSJ(LgDeliveryOrderForB2BDto dto) {
		// lg_delivery_order
		LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
		String deliveryPk = KeyUtils.getUUID();
		Date tempDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lgDeliveryOrder.setPk(deliveryPk);
		lgDeliveryOrder.setOrderPk(dto.getOrderPk() == null ? "" : dto.getOrderPk());
		lgDeliveryOrder.setParentPk("-1");
		lgDeliveryOrder.setOrderStatus(6);
		lgDeliveryOrder.setIsMatched(1);
		lgDeliveryOrder.setOriginalTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对外总价
		lgDeliveryOrder.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对内总价
		lgDeliveryOrder.setInsertTime(tempDate);
		lgDeliveryOrder.setUpdateTime(tempDate);
		Date orderTimeTemp;
		if (dto.getOrderTime() == null || dto.getOrderTime().equals("")) {
			orderTimeTemp = null;
		} else {
			try {
				orderTimeTemp = sdf.parse(dto.getOrderTime());
			} catch (ParseException e) {
				e.printStackTrace();
				orderTimeTemp = null;
			}
		}
		lgDeliveryOrder.setOrderTime(orderTimeTemp);
		lgDeliveryOrder.setLogisticsCompanyPk(dto.getLogisticsCompanyPk() == null ? "" : dto.getLogisticsCompanyPk());

		// System.out.println("===============================LogisticsCompanyPk:"+lgDeliveryOrderForB2BDto.getLogisticsCompanyPk());
		LgCompanyDto tempCompany = lgCompanyDaoEx
				.getByPk(dto.getLogisticsCompanyPk() == null ? "" : dto.getLogisticsCompanyPk());
		if (null != tempCompany && null != tempCompany.getPk()) {
			lgDeliveryOrder.setLogisticsCompanyName(tempCompany.getName() == null ? "" : tempCompany.getName());
		}
		// System.out.println("================================:LogisticsCompanyName"+tempCompany.getName());
		lgDeliveryOrder.setFromCompanyName(dto.getFromCompanyName() == null ? "" : dto.getFromCompanyName());
		lgDeliveryOrder.setFromAddress(dto.getFromAddress() == null ? "" : dto.getFromAddress());
		lgDeliveryOrder.setFromContacts(dto.getFromContacts() == null ? "" : dto.getFromContacts());
		lgDeliveryOrder.setFromContactsTel(dto.getFromContactsTel() == null ? "" : dto.getFromContactsTel());
		lgDeliveryOrder.setFromProvincePk(dto.getFromProvincePk() == null ? "" : dto.getFromProvincePk());
		lgDeliveryOrder.setFromProvinceName(dto.getFromProvinceName() == null ? "" : dto.getFromProvinceName());
		lgDeliveryOrder.setFromCityPk(dto.getFromCityPk() == null ? "" : dto.getFromCityPk());
		lgDeliveryOrder.setFromCityName(dto.getFromCityName() == null ? "" : dto.getFromCityName());
		lgDeliveryOrder.setFromAreaPk(dto.getFromAreaPk() == null ? "" : dto.getFromAreaPk());
		lgDeliveryOrder.setFromAreaName(dto.getFromAreaName() == null ? "" : dto.getFromAreaName());
		lgDeliveryOrder.setFromTownPk(dto.getFromTownPk() == null ? "" : dto.getFromTownPk());
		lgDeliveryOrder.setFromTownName(dto.getFromTownName() == null ? "" : dto.getFromTownName());
		lgDeliveryOrder.setToCompanyName(dto.getToCompanyName() == null ? "" : dto.getToCompanyName());
		lgDeliveryOrder.setToAddress(dto.getToAddress() == null ? "" : dto.getToAddress());
		lgDeliveryOrder.setToContacts(dto.getToContacts() == null ? "" : dto.getToContacts());
		lgDeliveryOrder.setToContactsTel(dto.getToContactsTel() == null ? "" : dto.getToContactsTel());
		lgDeliveryOrder.setToProvinceName(dto.getToProvinceName() == null ? "" : dto.getToProvinceName());
		Map<String, Object> map=new HashMap<>();
		map.put("name", dto.getToProvinceName());
		map.put("parentPk", -1);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		SysRegionsDto provinceDto = regionsDaoEx.getByNameEx(map);
		if (null!=provinceDto && null!=provinceDto.getPk()) {
			lgDeliveryOrder.setToProvincePk(provinceDto.getPk()==null?"":provinceDto.getPk());
		}
		
		lgDeliveryOrder.setToCityName(dto.getToCityName() == null ? "" : dto.getToCityName());
		map.put("name", dto.getToCityName());
		map.put("parentPk", provinceDto.getPk()==null?"":provinceDto.getPk());
		SysRegionsDto cityDto = regionsDaoEx.getByNameEx(map);
		if (null!=cityDto && null!=cityDto.getPk()) {
			lgDeliveryOrder.setToCityPk(cityDto.getPk()==null?"":cityDto.getPk());
		}
		
		lgDeliveryOrder.setToAreaName(dto.getToAreaName() == null ? "" : dto.getToAreaName());
		map.put("name", dto.getToAreaName());
		map.put("parentPk", cityDto.getPk()==null?"":cityDto.getPk());
		SysRegionsDto areaDto = regionsDaoEx.getByNameEx(map);
		if (null!=areaDto && null!=areaDto.getPk()) {
			lgDeliveryOrder.setToAreaPk(areaDto.getPk()==null?"":areaDto.getPk());
		}
		
		lgDeliveryOrder.setToTownName(dto.getToTownName() == null ? "" : dto.getToTownName());
		map.put("name",dto.getToTownName());
		map.put("parentPk", areaDto.getPk()==null?"":areaDto.getPk());
		SysRegionsDto townDto = regionsDaoEx.getByNameEx(map);
		if (null!=townDto && null!=townDto.getPk()) {
			lgDeliveryOrder.setToTownPk(null==townDto.getPk()?"":townDto.getPk());
		}
		
		lgDeliveryOrder.setSupplierInvoiceStatus(0);// 物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认
		lgDeliveryOrder.setMemberInvoiceStatus(1);// 开票状态：1：未开发票，2：已开发票，3：已寄送
		lgDeliveryOrder.setSource(1);
		lgDeliveryOrder.setMember(dto.getMember() == null ? "" : dto.getMember());
		lgDeliveryOrder.setMemberPk(dto.getMemberPk() == null ? "" : dto.getMemberPk());
		lgDeliveryOrder.setRemark(dto.getRemark() == null ? "" : dto.getRemark());
		// 设置订单的提货单号
		int temp = lgDeliveryOrderDaoEx.getDeliveryCountByOrderPk(dto.getOrderPk());
		if (temp == 0) {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk());
		} else if (temp == 1) {
			// 把第一个部分发货的发货单的deliveryNumber改成 abc-1
			String tempPk = lgDeliveryOrderDaoEx.getPkByDeliveryNumber(dto.getOrderPk());
			lgDeliveryOrderDaoEx.updateDeliveryNumberByPk(tempPk, dto.getOrderPk() + "-1");
			// 第二个部分发货的deliveryNumber设置为abc-2
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-2");
		} else {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-" + (temp + 1));
		}
		lgDeliveryOrder.setIsAbnormal(1);
		lgDeliveryOrder.setPaymentName(dto.getPaymentName() == null ? "" : dto.getPaymentName());
		Date paymentTimeTemp;
		if (dto.getPaymentTime() == null || dto.getPaymentTime().equals("")) {
			paymentTimeTemp = null;
		} else {
			try {
				paymentTimeTemp = sdf.parse(dto.getPaymentTime());
			} catch (ParseException e) {
				e.printStackTrace();
				paymentTimeTemp = null;
			}
		}
		lgDeliveryOrder.setPaymentTime(paymentTimeTemp);
		lgDeliveryOrder.setPurchaserName(dto.getPurchaserName() == null ? "" : dto.getPurchaserName());
		lgDeliveryOrder.setPurchaserPk(dto.getPurchaserPk() == null ? "" : dto.getPurchaserPk());
		lgDeliveryOrder.setSupplierName(dto.getSupplierName() == null ? "" : dto.getSupplierName());
		lgDeliveryOrder.setSupplierPk(dto.getSupplierPk() == null ? "" : dto.getSupplierPk());
		lgDeliveryOrder.setInvoiceName(dto.getInvoiceName() == null ? "" : dto.getInvoiceName());
		lgDeliveryOrder.setInvoiceTaxidNumber(dto.getInvoiceTaxidNumber() == null ? "" : dto.getInvoiceTaxidNumber());
		lgDeliveryOrder.setInvoiceRegPhone(dto.getInvoiceRegPhone() == null ? "" : dto.getInvoiceRegPhone());
		lgDeliveryOrder.setInvoiceBankAccount(dto.getInvoiceBankAccount() == null ? "" : dto.getInvoiceBankAccount());
		lgDeliveryOrder.setInvoiceBankName(dto.getInvoiceBankName() == null ? "" : dto.getInvoiceBankName());
		lgDeliveryOrder
				.setInvoiceProvinceName(dto.getInvoiceProvinceName() == null ? "" : dto.getInvoiceProvinceName());
		lgDeliveryOrder.setInvoiceCityName(dto.getInvoiceCityName() == null ? "" : dto.getInvoiceCityName());
		lgDeliveryOrder.setInvoiceAreaName(dto.getInvoiceAreaName() == null ? "" : dto.getInvoiceAreaName());
		lgDeliveryOrder.setInvoiceRegAddress(dto.getInvoiceRegAddress() == null ? "" : dto.getInvoiceRegAddress());
		lgDeliveryOrder.setBasisLinePrice(dto.getBasisLinePrice() == null ? 0.0 : dto.getBasisLinePrice());
		lgDeliveryOrder.setSettleWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgDeliveryOrder.setIsSettleFreight(1);
		// lg_order_goods
		LgDeliveryOrderGoods lgOrderGoods = new LgDeliveryOrderGoods();
		lgOrderGoods.setPk(KeyUtils.getUUID());
		lgOrderGoods.setDeliveryPk(deliveryPk);
		lgOrderGoods.setProductName(dto.getProductName() == null ? "" : dto.getProductName());
		lgOrderGoods.setProductPk(dto.getProductPk() == null ? "" : dto.getProductPk());
		lgOrderGoods.setVarietiesName(dto.getVarietiesName() == null ? "" : dto.getVarietiesName());
		lgOrderGoods.setSeedvarietyName(dto.getSeedvarietyName() == null ? "" : dto.getSeedvarietyName());
		lgOrderGoods.setSpecName(dto.getSpecName() == null ? "" : dto.getSpecName());
		lgOrderGoods.setSeriesName(dto.getSeriesName() == null ? "" : dto.getSeriesName());
		lgOrderGoods.setGradeName(dto.getGradeName() == null ? "" : dto.getGradeName());
		lgOrderGoods.setGradePk(dto.getGradePk() == null ? "" : dto.getGradePk());
		lgOrderGoods.setBatchNumber(dto.getBatchNumber() == null ? "" : dto.getBatchNumber());
		lgOrderGoods.setWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgOrderGoods.setBoxes(dto.getBoxes() == null ? 0 : dto.getBoxes());
		// lgOrderGoods.setOrderNumber(lgDeliveryOrderForB2BDto.getOrderPk()==null?"":lgDeliveryOrderForB2BDto.getOrderPk());
		lgOrderGoods.setTareWeight(dto.getTareWeight() == null ? 0.0 : dto.getTareWeight());
		lgOrderGoods.setOriginalFreight(dto.getPresentFreight() == null ? 0.0 : dto.getPresentFreight());
		lgOrderGoods.setPresentFreight(dto.getPresentFreight() == null ? 0.0 : dto.getPresentFreight());
		lgOrderGoods.setGoodsName(dto.getGoodsName() == null ? "" : dto.getGoodsName());
		lgOrderGoods.setGoodsOriginalFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对外总价
		lgOrderGoods.setGoodsPresentFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对内总价
		lgOrderGoods.setUnit(dto.getUnit() == null ? null : dto.getUnit());
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
				//??匹配该运货单属于哪些业务员
				//logisticsCompanyPk,deliveryPk,fromProvincePk,fromCityPk,fromAreaPk,fromTownPk
				Map<String, Object> par=new HashMap<>();
				par.put("companyPk", dto.getLogisticsCompanyPk()==null?"":dto.getLogisticsCompanyPk());
				par.put("province", dto.getFromProvincePk()==null?"":dto.getFromProvincePk());
				par.put("city", dto.getFromCityPk()==null?"":dto.getFromCityPk());
				par.put("area", dto.getFromAreaPk()==null?"":dto.getFromAreaPk());
				par.put("town", dto.getFromTownPk()==null?"":dto.getFromTownPk());
				par.put("isDelete", 1);
				par.put("isVisable", 1);
				List<String> members= lgRoleDaoEx.matchMembers(par);
				LgMemberDeliveryOrderDtoEx memberDeliveryOrderDtoEx=new LgMemberDeliveryOrderDtoEx();
				for (String member : members) {
					memberDeliveryOrderDtoEx.setPk(KeyUtils.getUUID());
					memberDeliveryOrderDtoEx.setMemberPk(member);
					memberDeliveryOrderDtoEx.setDeliveryOrderPk(lgDeliveryOrder.getPk());
					memberDeliveryOrderDtoEx.setIsDelete(1);
					lgMemberDeliveryOrderDaoEx.insert(memberDeliveryOrderDtoEx);
				}
				return true;
			} else {
				lgDeliveryOrderDaoEx.delete(deliveryPk);
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 提供给商城查询线路价格
	 * @param searchLgLine
	 * @param weight
	 * @return
	 */
	@Override
	public ForB2BLgPriceDto searchLgPriceForB2B(LgSearchPriceForB2BDto lgSearchPriceForB2BDto) {
		ForB2BLgPriceDto forB2BLgPriceDto=new ForB2BLgPriceDto();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromProvicePk", lgSearchPriceForB2BDto.getFromProvicePk()==null?"":lgSearchPriceForB2BDto.getFromProvicePk());
		map.put("fromCityPk", lgSearchPriceForB2BDto.getFromCityPk()==null?"":lgSearchPriceForB2BDto.getFromCityPk());
		map.put("fromAreaPk", lgSearchPriceForB2BDto.getFromAreaPk()==null?"":lgSearchPriceForB2BDto.getFromAreaPk());
		map.put("fromTownPk", lgSearchPriceForB2BDto.getFromTownPk()==null?"":lgSearchPriceForB2BDto.getFromTownPk());
		map.put("toProvicePk", lgSearchPriceForB2BDto.getToProvicePk()==null?"":lgSearchPriceForB2BDto.getToProvicePk());
		map.put("toCityPk", lgSearchPriceForB2BDto.getToCityPk()==null?"":lgSearchPriceForB2BDto.getToCityPk());
		map.put("toAreaPk", lgSearchPriceForB2BDto.getToAreaPk()==null?"":lgSearchPriceForB2BDto.getToAreaPk());
		map.put("toTownPk", lgSearchPriceForB2BDto.getToTownPk()==null?"":lgSearchPriceForB2BDto.getToTownPk());
		map.put("productPk", lgSearchPriceForB2BDto.getProductPk()==null?"":lgSearchPriceForB2BDto.getProductPk());
		map.put("gradePk", lgSearchPriceForB2BDto.getGradePk()==null?"":lgSearchPriceForB2BDto.getGradePk());
		map.put("status", 1);// 线路启用
		map.put("isDelete", 1);
		map.put("companyIsDelete", 1);// 物流公司启用
		map.put("companyIsVisable", 1);
		map.put("companyAuditStatus", 1);
		
		SearchLgLine searchLgLine=new SearchLgLine();
		searchLgLine.setFromProvicePk(lgSearchPriceForB2BDto.getFromProvicePk()==null?"":lgSearchPriceForB2BDto.getFromProvicePk());
		searchLgLine.setFromCityPk(lgSearchPriceForB2BDto.getFromCityPk()==null?"":lgSearchPriceForB2BDto.getFromCityPk());
		searchLgLine.setFromAreaPk(lgSearchPriceForB2BDto.getFromAreaPk()==null?"":lgSearchPriceForB2BDto.getFromAreaPk());
		searchLgLine.setFromTownPk(lgSearchPriceForB2BDto.getFromTownPk()==null?"":lgSearchPriceForB2BDto.getFromTownPk());
		searchLgLine.setToProvicePk(lgSearchPriceForB2BDto.getToProvicePk()==null?"":lgSearchPriceForB2BDto.getToProvicePk());
		searchLgLine.setToCityPk(lgSearchPriceForB2BDto.getToCityPk()==null?"":lgSearchPriceForB2BDto.getToCityPk());
		searchLgLine.setToAreaPk(lgSearchPriceForB2BDto.getToAreaPk()==null?"":lgSearchPriceForB2BDto.getToAreaPk());
		searchLgLine.setToTownPk(lgSearchPriceForB2BDto.getToTownPk()==null?"":lgSearchPriceForB2BDto.getToTownPk());
		searchLgLine.setProductPk(lgSearchPriceForB2BDto.getProductPk()==null?"":lgSearchPriceForB2BDto.getProductPk());
		searchLgLine.setGradePk(lgSearchPriceForB2BDto.getGradePk()==null?"":lgSearchPriceForB2BDto.getGradePk());
		searchLgLine.setWeight(lgSearchPriceForB2BDto.getWeight()==null?0.0:lgSearchPriceForB2BDto.getWeight());
		
		List<LogisticsRouteDto> list = getMatcheRoute(map, searchLgLine);
		//匹配到线路
		if (list.size() > 0) {
			LogisticsRouteDto logisticsRouteDto = list.get(0);
			forB2BLgPriceDto.setLinePk(logisticsRouteDto.getPk());// 线路pk
			//合同下单，如果匹配的是最低运费，直接返回
			if (lgSearchPriceForB2BDto.getType() == 1 && null != logisticsRouteDto.getLeastWeight()&& logisticsRouteDto.getLeastWeight() > lgSearchPriceForB2BDto.getWeight() ) {
				forB2BLgPriceDto.setLinePk("");
				forB2BLgPriceDto.setLogisticsStepInfoPk("");
				forB2BLgPriceDto.setPrice(0.0);
				forB2BLgPriceDto.setLowPrice(0.0);
				return forB2BLgPriceDto;
			}
			//最低运价
			if (null != logisticsRouteDto.getLeastWeight()&& logisticsRouteDto.getLeastWeight() > lgSearchPriceForB2BDto.getWeight()) {
				forB2BLgPriceDto.setLogisticsStepInfoPk("");
				forB2BLgPriceDto.setPrice(0.0);
				forB2BLgPriceDto.setLowPrice(logisticsRouteDto.getFreight());
			} else {
				//最低运价（合同除外）
				if (ArithUtil.sub(ArithUtil.mul(logisticsRouteDto.getList().get(0).getSalePrice(),lgSearchPriceForB2BDto.getWeight()), logisticsRouteDto.getFreight()) < 0
						&& lgSearchPriceForB2BDto.getType() != 1) {
					forB2BLgPriceDto.setLogisticsStepInfoPk("");
					forB2BLgPriceDto.setPrice(0.0);
					forB2BLgPriceDto.setLowPrice(logisticsRouteDto.getFreight());
				} else {
					//阶梯价
					forB2BLgPriceDto.setLogisticsStepInfoPk(logisticsRouteDto.getList().get(0).getPk());
					forB2BLgPriceDto.setPrice(logisticsRouteDto.getList().get(0).getSalePrice());
					forB2BLgPriceDto.setLowPrice(0.0);
				}
			}
		} else {
			// 未匹配到线路
			forB2BLgPriceDto.setLinePk("");
			forB2BLgPriceDto.setLogisticsStepInfoPk("");
			forB2BLgPriceDto.setPrice(0.0);
			forB2BLgPriceDto.setLowPrice(0.0);
		}
		return forB2BLgPriceDto;
	}
	
	
	/**
	 * 查询线路
	 */
	private List<LogisticsRouteDto> getMatcheRoute(Map<String, Object> map,SearchLgLine searchLgLine) {
		List<LogisticsRouteDto> list = new ArrayList<LogisticsRouteDto>();
		list = queryMongo(map);
		if (null == list || list.size() == 0) {
			map.put("toTownPk", "");
			list = queryMongo(map);
			System.out.println("========================"+queryMongo(map));
			if (null == list || list.size() == 0) {
				map.put("toAreaPk", "");
				list = queryMongo(map);
				System.out.println("========================"+queryMongo(map));
				if (null == list || list.size() == 0) {
					map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
					map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
					map.put("gradePk", "");
					list = queryMongo(map);
					if (null == list || list.size() == 0) {
						map.put("toTownPk", "");
						list = queryMongo(map);
						if (null == list || list.size() == 0) {
							map.put("toAreaPk", "");
							list = queryMongo(map);
							if (null == list || list.size() == 0) {
								map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
								map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
								map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
								map.put("productPk", "");
								list = queryMongo(map);
								if (null == list || list.size() == 0) {
									map.put("toTownPk", "");
									list = queryMongo(map);
									if (null == list || list.size() == 0) {
										map.put("toAreaPk", "");
										list = queryMongo(map);
										if (null == list || list.size() == 0) {
											map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
											map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
											map.put("gradePk", "");
											list = queryMongo(map);
											if (null == list || list.size() == 0) {
												map.put("toTownPk", "");
												list = queryMongo(map);
												if (null == list || list.size() == 0) {
													map.put("toAreaPk", "");
													list = queryMongo(map);
													if (null == list || list.size() == 0) {
														map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
														map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
														map.put("productPk", searchLgLine.getProductPk()==null?"":searchLgLine.getProductPk());
														map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
														map.put("fromTownPk", "");
														list = queryMongo(map);
														if (null == list || list.size() == 0) {
															map.put("fromAreaPk", "");
															list = queryMongo(map);
															if (null == list || list.size() == 0) {
																map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																map.put("gradePk", "");
																list = queryMongo(map);
																if (null == list || list.size() == 0) {
																	map.put("fromTownPk", "");
																	list = queryMongo(map);
																	if (null == list || list.size() == 0) {
																		map.put("fromAreaPk", "");
																		list = queryMongo(map);
																		if (null == list || list.size() == 0) {
																			map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																			map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																			map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
																			map.put("productPk", "");
																			list = queryMongo(map);
																			if (null == list || list.size() == 0) {
																				map.put("fromTownPk", "");
																				list = queryMongo(map);
																				if (null == list || list.size() == 0) {
																					map.put("fromAreaPk", "");
																					list = queryMongo(map);
																					if (null == list || list.size() == 0) {
																						map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																						map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																						map.put("gradePk", "");
																						list = queryMongo(map);
																						if (null == list || list.size() == 0) {
																							map.put("fromTownPk", "");
																							list = queryMongo(map);
																							if (null == list || list.size() == 0) {
																								map.put("fromAreaPk", "");
																								list = queryMongo(map);
																							}
																							
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (null!=list.get(i).getLeastWeight() && searchLgLine.getWeight() < list.get(i).getLeastWeight()) {
					List<LogisticsLinePriceDto> dtoList = new ArrayList<LogisticsLinePriceDto>();
					LogisticsLinePriceDto dto = new LogisticsLinePriceDto();
					dto.setFromWeight(0.0);
					dto.setToWeight(searchLgLine.getWeight());
					dto.setBasisPrice(list.get(i).getBasicPrice()); // 结算价
					dto.setSalePrice(list.get(i).getFreight()); // 对外价
					dtoList.add(dto);
					list.get(i).setList(dtoList);
				} else {
					Query query = new Query();
					query.addCriteria(Criteria.where("linePk").is(list.get(i).getPk()));
					query.addCriteria(Criteria.where("fromWeight").lte(searchLgLine.getWeight()));
					query.addCriteria(Criteria.where("toWeight").gt(searchLgLine.getWeight()));
					Criteria stepInfo = new Criteria();
					stepInfo.andOperator(Criteria.where("linePk").is(list.get(i).getPk()),
							Criteria.where("fromWeight").lte(searchLgLine.getWeight()), Criteria.where("toWeight").gt(searchLgLine.getWeight()));
					Query query1 = new Query(stepInfo);
					List<LogisticsLinePriceDto> logisticsPrice = mongoTemplate.find(query1,
							LogisticsLinePriceDto.class);
					if (null != logisticsPrice && logisticsPrice.size() > 0) {
						list.get(i).setList(logisticsPrice);
					} else {
						list.remove(i);
					}
				}
			}
		}
		return list;
	}
	
	
	
	/**
	 * 查询mongo数据库
	 * 
	 * @param map
	 * @return
	 */
	private List<LogisticsRouteDto> queryMongo(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		System.out.println("============="+query);
		List<LogisticsRouteDto> logisticsLine = mongoTemplate.find(query, LogisticsRouteDto.class);
		return logisticsLine;
	}

	/**
	 * 商城确认发货（平台承运）
	 * 
	 */
	@Override
	public boolean confirmFaHuoForB2BPT(LgDeliveryOrderForB2BPTDto dto) {
		// System.out.println("===========================平台承运，确认发货=========================");
		// lg_delivery_order
		LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
		LgDeliveryOrderGoods lgOrderGoods = new LgDeliveryOrderGoods();
		LgLineDto lgLineDto = lgLineDaoEx.getByPk(dto.getLgLinePk());
		LgLinePriceDto lgLinePriceDto = lgLinePriceDaoEx.getByPk(dto.getLgLinePricePk()==null?"-1":dto.getLgLinePricePk());
		String deliveryPk = KeyUtils.getUUID();
		Date tempDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lgDeliveryOrder.setPk(deliveryPk);
		lgDeliveryOrder.setOrderPk(dto.getOrderPk() == null ? "" : dto.getOrderPk());
		lgDeliveryOrder.setParentPk("-1");
		lgDeliveryOrder.setOrderStatus(6);
		lgDeliveryOrder.setIsMatched(1);
		//1：匹配的是最低起运价
		if (null == lgLinePriceDto || null == lgLinePriceDto.getPk()) {
			lgDeliveryOrder.setPresentTotalFreight(null==lgLineDto.getBasicPrice()?0.0:lgLineDto.getBasicPrice());//对内总价
			//商城供应商修改过运费单价
			if (null!=dto.getBasisLinePrice()&& 0.0!=dto.getBasisLinePrice()) {
				lgDeliveryOrder.setOriginalTotalFreight(ArithUtil.mul(dto.getBasisLinePrice(),dto.getWeight()));//对外总价
			}else{
				lgDeliveryOrder.setOriginalTotalFreight(null==lgLineDto.getFreight()?0.0:lgLineDto.getFreight());//对外总价
			}
			lgOrderGoods.setGoodsPresentFreight(null==lgLineDto.getBasicPrice()?0.0:lgLineDto.getBasicPrice());//对内总价
			lgOrderGoods.setGoodsOriginalFreight(null==lgLineDto.getFreight()?0.0:lgLineDto.getFreight());//对外总价
			lgOrderGoods.setPresentFreight(null);
			lgOrderGoods.setOriginalFreight(dto.getBasisLinePrice());//对外单价
		//2:匹配到阶梯运价
		}else {
			lgDeliveryOrder.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(lgLinePriceDto.getBasisPrice(), dto.getWeight()),2));//对内总价
			lgDeliveryOrder.setOriginalTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getBasisLinePrice(), dto.getWeight()),2));//对外总价
			lgDeliveryOrder.setBasisLinePrice(lgLinePriceDto.getBasisPrice());
			lgOrderGoods.setGoodsPresentFreight(ArithUtil.round(ArithUtil.mul(lgLinePriceDto.getBasisPrice(), dto.getWeight()),2));//对内总价
			lgOrderGoods.setGoodsOriginalFreight(ArithUtil.round(ArithUtil.mul(dto.getBasisLinePrice(), dto.getWeight()),2));//对外总价
			lgOrderGoods.setPresentFreight(lgLinePriceDto.getBasisPrice());//对内单价
			lgOrderGoods.setOriginalFreight(dto.getBasisLinePrice());//对外单价
		}
		lgDeliveryOrder.setInsertTime(tempDate);
		lgDeliveryOrder.setUpdateTime(tempDate);
		Date orderTimeTemp;
		if (dto.getOrderTime() == null || dto.getOrderTime().equals("")) {
			orderTimeTemp = null;
		} else {
			try {
				orderTimeTemp = sdf.parse(dto.getOrderTime());
			} catch (ParseException e) {
				e.printStackTrace();
				orderTimeTemp = null;
			}
		}
		lgDeliveryOrder.setOrderTime(orderTimeTemp);
		
		LgCompanyDto lgCompanyDto = new  LgCompanyDto();
		if (null!=lgLineDto && null!=lgLineDto.getPk() ) {
			lgCompanyDto = lgCompanyDaoEx.getByPk(lgLineDto.getCompanyPk());
			lgDeliveryOrder.setLogisticsCompanyPk(lgCompanyDto.getPk() == null ? "" : lgCompanyDto.getPk());
			lgDeliveryOrder.setLogisticsCompanyName(lgCompanyDto.getName() == null ? "" : lgCompanyDto.getName());
		}
		lgDeliveryOrder.setFromCompanyName(dto.getFromCompanyName() == null ? "" : dto.getFromCompanyName());
		lgDeliveryOrder.setFromAddress(dto.getFromAddress() == null ? "" : dto.getFromAddress());
		lgDeliveryOrder.setFromContacts(dto.getFromContacts() == null ? "" : dto.getFromContacts());
		lgDeliveryOrder.setFromContactsTel(dto.getFromContactsTel() == null ? "" : dto.getFromContactsTel());
		lgDeliveryOrder.setFromProvincePk(dto.getFromProvincePk() == null ? "" : dto.getFromProvincePk());
		lgDeliveryOrder.setFromProvinceName(dto.getFromProvinceName() == null ? "" : dto.getFromProvinceName());
		lgDeliveryOrder.setFromCityPk(dto.getFromCityPk() == null ? "" : dto.getFromCityPk());
		lgDeliveryOrder.setFromCityName(dto.getFromCityName() == null ? "" : dto.getFromCityName());
		lgDeliveryOrder.setFromAreaPk(dto.getFromAreaPk() == null ? "" : dto.getFromAreaPk());
		lgDeliveryOrder.setFromAreaName(dto.getFromAreaName() == null ? "" : dto.getFromAreaName());
		lgDeliveryOrder.setFromTownPk(dto.getFromTownPk() == null ? "" : dto.getFromTownPk());
		lgDeliveryOrder.setFromTownName(dto.getFromTownName() == null ? "" : dto.getFromTownName());
		lgDeliveryOrder.setToCompanyName(dto.getToCompanyName() == null ? "" : dto.getToCompanyName());
		lgDeliveryOrder.setToAddress(dto.getToAddress() == null ? "" : dto.getToAddress());
		lgDeliveryOrder.setToContacts(dto.getToContacts() == null ? "" : dto.getToContacts());
		lgDeliveryOrder.setToContactsTel(dto.getToContactsTel() == null ? "" : dto.getToContactsTel());
		lgDeliveryOrder.setToProvinceName(dto.getToProvinceName() == null ? "" : dto.getToProvinceName());
		Map<String, Object> map=new HashMap<>();
		map.put("name", dto.getToProvinceName());
		map.put("parentPk", -1);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		SysRegionsDto provinceDto = regionsDaoEx.getByNameEx(map);
		lgDeliveryOrder.setToProvincePk(provinceDto.getPk()==null?"":provinceDto.getPk());
		lgDeliveryOrder.setToCityName(dto.getToCityName() == null ? "" : dto.getToCityName());
		map.put("name", dto.getToCityName());
		map.put("parentPk", provinceDto.getPk()==null?"":provinceDto.getPk());
		SysRegionsDto cityDto = regionsDaoEx.getByNameEx(map);
		if (null!=cityDto && null!=cityDto.getPk()) {
			lgDeliveryOrder.setToCityPk(cityDto.getPk()==null?"":cityDto.getPk());
		}
		lgDeliveryOrder.setToAreaName(dto.getToAreaName() == null ? "" : dto.getToAreaName());
		map.put("name", dto.getToAreaName());
		map.put("parentPk", cityDto.getPk()==null?"":cityDto.getPk());
		SysRegionsDto areaDto = regionsDaoEx.getByNameEx(map);
		if (null!=areaDto && null!=areaDto.getPk()) {
			lgDeliveryOrder.setToAreaPk(areaDto.getPk()==null?"":areaDto.getPk());
		}
		lgDeliveryOrder.setToTownName(dto.getToTownName() == null ? "" : dto.getToTownName());
		map.put("name",dto.getToTownName());
		map.put("parentPk", areaDto.getPk()==null?"":areaDto.getPk());
		SysRegionsDto townDto = regionsDaoEx.getByNameEx(map);
		if (null!=townDto && null!=townDto.getPk()) {
			lgDeliveryOrder.setToTownPk(null==townDto.getPk()?"":townDto.getPk());
		}
		
		lgDeliveryOrder.setSupplierInvoiceStatus(0);// 物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认
		lgDeliveryOrder.setMemberInvoiceStatus(1);// 开票状态:1：未开发票，2：已开发票，3：已寄送
		lgDeliveryOrder.setSource(2);
		lgDeliveryOrder.setMember(dto.getMember() == null ? "" : dto.getMember());
		lgDeliveryOrder.setMemberPk(dto.getMemberPk() == null ? "" : dto.getMemberPk());
		lgDeliveryOrder.setRemark(dto.getRemark() == null ? "" : dto.getRemark());
		// 设置订单的提货单号
		int temp = lgDeliveryOrderDaoEx.getDeliveryCountByOrderPk(dto.getOrderPk());
		if (temp == 0) {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk());
		} else if (temp == 1) {
			// 把第一个部分发货的发货单的deliveryNumber改成 abc-1
			String tempPk = lgDeliveryOrderDaoEx.getPkByDeliveryNumber(dto.getOrderPk());
			lgDeliveryOrderDaoEx.updateDeliveryNumberByPk(tempPk, dto.getOrderPk() + "-1");
			// 第二个部分发货的deliveryNumber设置为abc-2
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-2");
		} else {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-" + (temp + 1));
		}
		lgDeliveryOrder.setIsAbnormal(1);
		lgDeliveryOrder.setPaymentName(dto.getPaymentName() == null ? "" : dto.getPaymentName());
		Date paymentTimeTemp;
		if (dto.getPaymentTime() == null || dto.getPaymentTime().equals("")) {
			paymentTimeTemp = null;
		} else {
			try {
				paymentTimeTemp = sdf.parse(dto.getPaymentTime());
			} catch (ParseException e) {
				e.printStackTrace();
				paymentTimeTemp = null;
			}
		}
		lgDeliveryOrder.setPaymentTime(paymentTimeTemp);
		lgDeliveryOrder.setPurchaserName(dto.getPurchaserName() == null ? "" : dto.getPurchaserName());
		lgDeliveryOrder.setPurchaserPk(dto.getPurchaserPk() == null ? "" : dto.getPurchaserPk());
		lgDeliveryOrder.setSupplierName(dto.getSupplierName() == null ? "" : dto.getSupplierName());
		lgDeliveryOrder.setSupplierPk(dto.getSupplierPk() == null ? "" : dto.getSupplierPk());
		lgDeliveryOrder.setInvoiceName(dto.getInvoiceName() == null ? "" : dto.getInvoiceName());
		lgDeliveryOrder.setInvoiceTaxidNumber(dto.getInvoiceTaxidNumber() == null ? "" : dto.getInvoiceTaxidNumber());
		lgDeliveryOrder.setInvoiceRegPhone(dto.getInvoiceRegPhone() == null ? "" : dto.getInvoiceRegPhone());
		lgDeliveryOrder.setInvoiceBankAccount(dto.getInvoiceBankAccount() == null ? "" : dto.getInvoiceBankAccount());
		lgDeliveryOrder.setInvoiceBankName(dto.getInvoiceBankName() == null ? "" : dto.getInvoiceBankName());
		lgDeliveryOrder.setInvoiceProvinceName(dto.getInvoiceProvinceName() == null ? "" : dto.getInvoiceProvinceName());
		lgDeliveryOrder.setInvoiceCityName(dto.getInvoiceCityName() == null ? "" : dto.getInvoiceCityName());
		lgDeliveryOrder.setInvoiceAreaName(dto.getInvoiceAreaName() == null ? "" : dto.getInvoiceAreaName());
		lgDeliveryOrder.setInvoiceRegAddress(dto.getInvoiceRegAddress() == null ? "" : dto.getInvoiceRegAddress());
		//lgDeliveryOrder.setBasisLinePrice(dto.getBasisLinePrice() == null ? 0.0 : dto.getBasisLinePrice());
		lgDeliveryOrder.setSettleWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgDeliveryOrder.setIsSettleFreight(1);
		// lg_order_goods
		lgOrderGoods.setPk(KeyUtils.getUUID());
		lgOrderGoods.setDeliveryPk(deliveryPk);
		lgOrderGoods.setProductName(dto.getProductName() == null ? "" : dto.getProductName());
		lgOrderGoods.setProductPk(dto.getProductPk() == null ? "" : dto.getProductPk());
		lgOrderGoods.setVarietiesName(dto.getVarietiesName() == null ? "" : dto.getVarietiesName());
		lgOrderGoods.setSeedvarietyName(dto.getSeedvarietyName() == null ? "" : dto.getSeedvarietyName());
		lgOrderGoods.setSpecName(dto.getSpecName() == null ? "" : dto.getSpecName());
		lgOrderGoods.setSeriesName(dto.getSeriesName() == null ? "" : dto.getSeriesName());
		lgOrderGoods.setGradeName(dto.getGradeName() == null ? "" : dto.getGradeName());
		lgOrderGoods.setGradePk(dto.getGradePk() == null ? "" : dto.getGradePk());
		lgOrderGoods.setBatchNumber(dto.getBatchNumber() == null ? "" : dto.getBatchNumber());
		lgOrderGoods.setWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgOrderGoods.setBoxes(dto.getBoxes() == null ? 0 : dto.getBoxes());
		// lgOrderGoods.setOrderNumber(lgDeliveryOrderForB2BDto.getOrderPk()==null?"":lgDeliveryOrderForB2BDto.getOrderPk());
		lgOrderGoods.setTareWeight(dto.getTareWeight() == null ? 0.0 : dto.getTareWeight());
		lgOrderGoods.setGoodsName(dto.getGoodsName() == null ? "" : dto.getGoodsName());
		lgOrderGoods.setUnit(dto.getUnit() == null ? null : dto.getUnit());
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
				// ??匹配该运货单属于哪些业务员
				// logisticsCompanyPk,deliveryPk,fromProvincePk,fromCityPk,fromAreaPk,fromTownPk
				Map<String, Object> par = new HashMap<>();
				par.put("companyPk", lgCompanyDto.getPk() == null ? "" : lgCompanyDto.getPk());
				par.put("province", dto.getFromProvincePk() == null ? "" : dto.getFromProvincePk());
				par.put("city", dto.getFromCityPk() == null ? "" : dto.getFromCityPk());
				par.put("area", dto.getFromAreaPk() == null ? "" : dto.getFromAreaPk());
				par.put("town", dto.getFromTownPk() == null ? "" : dto.getFromTownPk());
				par.put("isDelete", 1);
				par.put("isVisable", 1);
				List<String> members = lgRoleDaoEx.matchMembers(par);
				LgMemberDeliveryOrderDtoEx memberDeliveryOrderDtoEx = new LgMemberDeliveryOrderDtoEx();
				for (String member : members) {
					memberDeliveryOrderDtoEx.setPk(KeyUtils.getUUID());
					memberDeliveryOrderDtoEx.setMemberPk(member);
					memberDeliveryOrderDtoEx.setDeliveryOrderPk(lgDeliveryOrder.getPk());
					memberDeliveryOrderDtoEx.setIsDelete(1);
					lgMemberDeliveryOrderDaoEx.insert(memberDeliveryOrderDtoEx);
				}
				return true;
			} else {
				lgDeliveryOrderDaoEx.delete(deliveryPk);
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 *  商城下单时根据线路pk,weight查询运费对外总价
	 */
	@Override
	public ForB2BLgPriceDto searchLgPriceForB2BByLinePk(String lgLinePk, String lgLineStepPk) {
		ForB2BLgPriceDto dto=new ForB2BLgPriceDto();
		LgLineDto lineDto = lgLineDaoEx.getByPk(lgLinePk);
		LgCompanyDto companyDto= lgCompanyDaoEx.getByPk(lineDto.getCompanyPk());
		dto.setLinePk(lgLinePk);
		dto.setLogisticsCarrierPk(companyDto.getPk());
		dto.setLogisticsCarrierName(companyDto.getName());
		//没有阶梯价
		if ("".equals(lgLineStepPk)) {
			dto.setLowPrice(lineDto.getFreight());
			dto.setPrice(0d);
			dto.setTotalFreight(lineDto.getFreight());
		} else {
			//有阶梯价
			LgLinePriceDto priceDto = lgLinePriceDaoEx.getByPk(lgLineStepPk);
			dto.setPrice(priceDto.getSalePrice());
			dto.setLowPrice(0d);
			dto.setTotalFreight(0d);
		}
		return dto;
	}

	@Override
	public List<LgCompanyDto> searchLgCompanyList(Map<String, Object> map) {
		 List<LgCompanyDto> list=lgCompanyDaoEx.searchLgCompanyList(map);
		return list;
	}

	


}
