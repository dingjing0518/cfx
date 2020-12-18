package cn.cf.model;


import java.util.Date;
import java.util.List;

import cn.cf.dto.B2bBindDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.Porder;
import cn.cf.entity.PurchaserInfo;
import cn.cf.json.JsonUtils;

import com.alibaba.fastjson.JSON;

public class B2bBindOrderEx extends B2bBindOrder{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

 
	public B2bBindOrderEx(Porder o, String orderNumber, double orderAmount,
			B2bBindDto bind, List<B2bBindOrderGoodsEx> oglist) {
		this.setPurchaseType(o.getPurchaseType());
        this.setOrderNumber(orderNumber);
        this.setBindPk(bind.getPk());
        this.setPurchaserPk(o.getPurchaserPk());
        this.setInsertTime(new Date());
        this.setMeno(o.getMeno());
        this.setMemberPk(null==o.getMember()?"":o.getMember().getPk());
        this.setMemberName(null==o.getMember()?"":o.getMember().getMobile());
        this.setStoreName(bind.getStoreName());
        this.setStorePk(bind.getStorePk());
        this.setSource(o.getSource());
        this.setLogisticsModelPk(o.getLogisticsModelPk());
        this.setLogisticsModelName(o.getLogisticsModelName());
	    this.setSource(o.getSource());
	    this.setActualAmount(orderAmount);
	    this.setStatus(1);
	    this.setBindProductId(bind.getBindProductID());
		this.setGoodsJson(JsonUtils.convertToString(oglist));
		//采购商信息
				PurchaserInfo purchaser = new PurchaserInfo(o.getCompany().getPk(), 
						o.getCompany().getName(),o.getCompany().getContacts(), 
						o.getCompany().getContactsTel(), o.getCompany().getOrganizationCode(),
						o.getInvoicePk(), o.getInvoice().getName(), o.getInvoice().getOrganizationCode(), 
						o.getInvoice().getContactsTel(), o.getInvoice().getBankAccount(), 
						o.getInvoice().getBankName(),o.getInvoice().getRegAddress());
	  		this.setPurchaserInfo(JSON.toJSONString(purchaser));
	  		this.setPurchaserPk(null == o.getCompany()?"":o.getCompany().getPk());
	  	//收货地址信息
			if(null != o.getAddressPk() && !"".equals(o.getAddressPk())){
				AddressInfo address = new AddressInfo(o.getAddressPk(), o.getAdto().getProvinceName(),
						o.getAdto().getCityName(), o.getAdto().getAreaName(), o.getAdto().getTownName(),
						 o.getAdto().getAddress(), o.getAdto().getContacts(),
						o.getAdto().getContactsTel(), o.getAdto().getSigningCompany());
				this.setAddressInfo(JSON.toJSONString(address));
			}
	}
}
