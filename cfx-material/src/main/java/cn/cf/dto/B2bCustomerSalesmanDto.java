package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCustomerSalesmanDto  extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String customerPk;
	private java.lang.String memberPk;
	private java.lang.String mobile;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.String filiale;
	private java.lang.String filialeName;
	private java.lang.String employeeNumber;
	private java.lang.String employeeName;
	private java.lang.String purchaserPk;
	private java.lang.String purchaserName;
	private java.lang.String storePk;
	private String block;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public java.lang.String getPk() {
		return this.pk;
	}
	public void setCustomerPk(java.lang.String customerPk) {
		this.customerPk = customerPk;
	}
	
	public java.lang.String getCustomerPk() {
		return this.customerPk;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setProductPk(java.lang.String productPk) {
		this.productPk = productPk;
	}
	
	public java.lang.String getProductPk() {
		return this.productPk;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setFiliale(java.lang.String filiale) {
		this.filiale = filiale;
	}
	
	public java.lang.String getFiliale() {
		return this.filiale;
	}
	public void setFilialeName(java.lang.String filialeName) {
		this.filialeName = filialeName;
	}
	
	public java.lang.String getFilialeName() {
		return this.filialeName;
	}
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public java.lang.String getEmployeeNumber() {
		return this.employeeNumber;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	
	public java.lang.String getEmployeeName() {
		return this.employeeName;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	

}