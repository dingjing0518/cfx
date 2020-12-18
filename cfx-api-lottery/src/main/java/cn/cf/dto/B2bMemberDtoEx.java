package cn.cf.dto;

import java.util.List;

public class B2bMemberDtoEx extends B2bMemberDto{
	

	private static final long serialVersionUID = 1L;
	
	private String contacts;
	private String code;
	private String refuseReason;
	private List<B2bMemberRoleDto> memberRoles;
	private String purchaserPk;
	private String companyType;
	private String parentPk;
	private String isAdmin;//判断该member是否是超级管理员，0：不是，1：是
	private double loanAmount;//白条累计金额
	private Integer cardAmount;//购物卡金额
	
	public Integer getCardAmount() {
		return cardAmount;
	}


	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}

 

	public double getLoanAmount() {
		return loanAmount;
	}


	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	
	public String getContacts() {
		return contacts;
	}


	public void setContacts(String contacts) {
		this.contacts = contacts;
	}


	public String getRefuseReason() {
		return refuseReason;
	}


	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}


	public List<B2bMemberRoleDto> getMemberRoles() {
		return memberRoles;
	}


	public void setMemberRoles(List<B2bMemberRoleDto> memberRoles) {
		this.memberRoles = memberRoles;
	}


	public String getPurchaserPk() {
		return purchaserPk;
	}


	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}


	public String getCompanyType() {
		return companyType;
	}


	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getParentPk() {
		return parentPk;
	}


	public void setParentPk(String parentPk) {
		this.parentPk = parentPk;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}


	public B2bMemberDtoEx() {
		this.verify.put("mobile", true);
		this.verify.put("password", true);
		this.verify.put("code", true);
		
	}
}
