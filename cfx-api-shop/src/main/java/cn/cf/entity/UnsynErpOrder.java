package cn.cf.entity;

import org.springframework.data.annotation.Id;

public class UnsynErpOrder {
	
	@Id
	private String id;
	private String orderNumber;
	private String insertTime;
	private String storePk;
	private Integer type;//1正常订单 2合同订单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStorePk() {
		return storePk;
	}
	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	
	
}
