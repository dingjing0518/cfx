package cn.cf.model;

import java.util.Date;
import java.util.List;

import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.Invoice;
import cn.cf.entity.Porder;
import cn.cf.json.JsonUtils;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class B2bReserveOrderEx extends B2bReserveOrder {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public B2bReserveOrderEx(Porder o, String storePk, String storeName,
			String orderNumber, double amount, B2bMemberDto mdto, List<B2bReserveGoods> rglist) {
		this.setPurchaseType(o.getPurchaseType());
		this.setOrderNumber(orderNumber);
		this.setOrderAmount(ArithUtil.round(amount, 2));
		this.setActualAmount(this.getOrderAmount());
		this.setPurchaserName(null == o.getCompany()?"":o.getCompany().getName());
		this.setPurchaserPk(null == o.getCompany()?"":o.getCompany().getPk());
		this.setPurchaserMobile(null == o.getCompany()?"":o.getCompany().getContactsTel());
		this.setOrderStatus(1);//默认待提交
		this.setInsertTime(new Date());
		//收货地址信息
		if(null != o.getAddressPk() && !"".equals(o.getAddressPk())){
			AddressInfo address = new AddressInfo(o.getAddressPk(), o.getAdto().getProvinceName(),
					o.getAdto().getCityName(), o.getAdto().getAreaName(), o.getAdto().getTownName(),
					 o.getAdto().getAddress(), o.getAdto().getContacts(),
					o.getAdto().getContactsTel(), o.getAdto().getSigningCompany());
			this.setAddressJson(JsonUtils.convertObjectToJSON(address));
		}
		this.setMeno(o.getMeno());
		 
		this.setMemberPk(o.getMember().getPk());
		this.setMemberName(o.getMember().getMobile());
		this.setStoreName(storeName);
		this.setStorePk(storePk);
		this.setSource(o.getSource());
		this.setLogisticsModelPk(o.getLogisticsModelPk());
		this.setLogisticsModelName(o.getLogisticsModelName());
		Invoice invoice= new Invoice(o.getInvoice());
		this.setInvoiceJson(JsonUtils.convertObjectToJSON(invoice));
		 
	 
		this.setDeliverDetails(o.getDeliverDetails());
		this.setGoodsJson(JsonUtils.convertToString(rglist).toString());
		this.setIsBatches(o.getIsBatches());
		this.setAppointmentTime(DateUtil.numberToStringTwo(o.getAppointmentTime()));
		
	}
	

}
