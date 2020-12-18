package cn.cf.entity;

import cn.cf.dto.B2bCompanyDto;

public class B2bCompanyDtoMa extends B2bCompanyDto{

	private static final long serialVersionUID = 1L;
	
	private String storePk;
	private String storeName;
	private String parentName;
	private String supplierPk;
	private String purchaserPk;
	private String supplierName;
	private String purchaserName;
	private String mobile;
	private String productPk;
	private String productName;
	private String filiale;
	private String filialeName;
	
	private String employeeName;//业务员姓名
	private String employeeNumber;//业务员工号
	private String memberParentPk;//业务员上级pk
	private String memberParentName;//业务员上级Name
	
	public B2bCompanyDtoMa() {
		this.verify.put("provinceName", true);
		this.verify.put("cityName", true);
		this.verify.put("areaName", true);
		this.verify.put("blUrl", true);
		this.verify.put("ecUrl", true);
		this.verify.put("organizationCode", true);

	}

	public String getMemberParentPk() {
		return memberParentPk;
	}

	public String getMemberParentName() {
		return memberParentName;
	}

	public void setMemberParentName(String memberParentName) {
		this.memberParentName = memberParentName;
	}

	public void setMemberParentPk(String memberParentPk) {
		this.memberParentPk = memberParentPk;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public void updateIsPerfect() {
		int isp = 2;
		if (null == this.getContactsTel() || "".equals(this.getContactsTel())) {
			isp = 1;
		}
		if (null == this.getContacts() || "".equals(this.getContacts())) {
			isp = 1;
		}
		if (null == this.getName() || "".equals(this.getName())) {
			isp = 1;
		}
		if (null == this.getProvince() || "".equals(this.getProvince())) {
			isp = 1;
		}
		if (null == this.getCity() || "".equals(this.getCity())) {
			isp = 1;
		}
		if (null == this.getArea() || "".equals(this.getArea())) {
			isp = 1;
		}
		if (null == this.getBlUrl() || "".equals(this.getBlUrl())) {
			isp = 1;
		}
		if (null == this.getOrganizationCode() || "".equals(this.getOrganizationCode())) {
			isp = 1;
		}
		if (null == this.getEcUrl() || "".equals(this.getEcUrl())) {
			isp = 1;
		}
		this.setIsPerfect(isp);
	}

	public String getSupplierPk() {
		return supplierPk;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public void setSupplierPk(String supplierPk) {
		this.supplierPk = supplierPk;
	}

	public String getPurchaserPk() {
		return purchaserPk;
	}

	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductPk() {
		return productPk;
	}

	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFiliale() {
		return filiale;
	}

	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
