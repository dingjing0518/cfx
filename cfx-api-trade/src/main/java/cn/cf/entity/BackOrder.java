package cn.cf.entity;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

import cn.cf.model.B2bBindOrder;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bReserveOrder;

public class BackOrder {

	/**
	 * 
	 */
	private String orderNumber;
	private String orderAmount;
	private String address;
	private String contacts;
	private String contactsTel;

	public BackOrder() {
		// TODO Auto-generated constructor stub
	}

	public BackOrder(B2bOrder bo) {
		orderNumber = bo.getOrderNumber();
		orderAmount = new BigDecimal(bo.getActualAmount().toString()).toPlainString();
		AddressInfo addressInfo = JSON.parseObject(bo.getAddressInfo(),AddressInfo.class);
		if(null != addressInfo){
			contacts = addressInfo.getContacts();
			contactsTel = addressInfo.getContactsTel();
			address = (null == addressInfo.getProvinceName() ? "" : addressInfo.getProvinceName())
					+ (null == addressInfo.getCityName() ? "" : addressInfo.getCityName())
					+ (null == addressInfo.getAreaName() ? "" : addressInfo.getAreaName())
					+ (null == addressInfo.getTownName() ? "" : addressInfo.getTownName());
		}
	}

	public BackOrder(B2bBindOrder bo) {
		orderNumber = bo.getOrderNumber();
		orderAmount = new BigDecimal(bo.getActualAmount().toString()).toPlainString();
		AddressInfo addressInfo = JSON.parseObject(bo.getAddressInfo(),AddressInfo.class);
		if(null != addressInfo){
			contacts = addressInfo.getContacts();
			contactsTel = addressInfo.getContactsTel();
			address = (null == addressInfo.getProvinceName() ? "" : addressInfo.getProvinceName())
					+ (null == addressInfo.getCityName() ? "" : addressInfo.getCityName())
					+ (null == addressInfo.getAreaName() ? "" : addressInfo.getAreaName())
					+ (null == addressInfo.getTownName() ? "" : addressInfo.getTownName());
		}
	}
	
	public BackOrder(B2bReserveOrder bo) {
		orderNumber = bo.getOrderNumber();
		orderAmount  = new BigDecimal(bo.getActualAmount().toString()).toPlainString();
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

}
