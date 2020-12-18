package cn.cf.entity;

import javax.persistence.Id;


public class B2bPayVoucher {
	
	@Id
	private String id;
	private String orderNumber;


	private String url;
	private String insertTime;
	private Integer type;//1付款凭证 2发货凭证
	private Integer status;//1未入账 2已入账 3失效
	private String purchaserPk;
	private String supplierPk;
	private String storePk;
	private String memberPk;
	private String employeeNumber;
	private String employeeName;
	private String employeePk;
	private  String purchaserName;
	private  String supplierName;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getPurchaserPk() {
		return purchaserPk;
	}
	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	public String getSupplierPk() {
		return supplierPk;
	}
	public void setSupplierPk(String supplierPk) {
		this.supplierPk = supplierPk;
	}
	public String getStorePk() {
		return storePk;
	}
	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}
	public String getEmployeePk() {
		return employeePk;
	}
	public void setEmployeePk(String employeePk) {
		this.employeePk = employeePk;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
}
