package cn.cf.dto;

import java.io.Serializable;

public class SettleAccountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String pk;
	
	private String logisticsCompanyName;
	
	private String  logisticsCompanyContacts;
	
	private String logisticsCompanyContactsTel;
	
	private String orderPk;
	
	private String insertTime;
	
	private String   orderTime;
	
	private Integer orderStatus;
	
	private String orderStatusName;
	
	private Double weight;
	
	private Double settleWeight;
	
	private Double basisLinePrice;//单价
	
	private Double presentTotalFreight;//总价
	
	private Integer isSettleFreight;
	
	private  String isSettleFreightName;
	
	private Integer isAbnormal;//是否异常 1正常 2异常
	
	private String isAbnormalName;
	
	private Integer isConfirmed;//订单异常是否确认1,已确认，2未确认
	
	private Integer presentFreight;
	
	

	public Integer getPresentFreight() {
		return presentFreight;
	}

	public void setPresentFreight(Integer presentFreight) {
		this.presentFreight = presentFreight;
	}

	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	public String getLogisticsCompanyContacts() {
		return logisticsCompanyContacts;
	}

	public void setLogisticsCompanyContacts(String logisticsCompanyContacts) {
		this.logisticsCompanyContacts = logisticsCompanyContacts;
	}

	public String getLogisticsCompanyContactsTel() {
		return logisticsCompanyContactsTel;
	}

	public void setLogisticsCompanyContactsTel(String logisticsCompanyContactsTel) {
		this.logisticsCompanyContactsTel = logisticsCompanyContactsTel;
	}

	public String getOrderPk() {
		return orderPk;
	}

	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getBasisLinePrice() {
		return basisLinePrice;
	}

	public void setBasisLinePrice(Double basisLinePrice) {
		this.basisLinePrice = basisLinePrice;
	}

	public Integer getIsSettleFreight() {
		return isSettleFreight;
	}

	public void setIsSettleFreight(Integer isSettleFreight) {
		this.isSettleFreight = isSettleFreight;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(Integer isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public String getIsSettleFreightName() {
		return isSettleFreightName;
	}

	public void setIsSettleFreightName(String isSettleFreightName) {
		this.isSettleFreightName = isSettleFreightName;
	}

	public Double getSettleWeight() {
		return settleWeight;
	}

	public void setSettleWeight(Double settleWeight) {
		this.settleWeight = settleWeight;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getIsAbnormalName() {
		return isAbnormalName;
	}

	public void setIsAbnormalName(String isAbnormalName) {
		this.isAbnormalName = isAbnormalName;
	}

	public Integer getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	
}
