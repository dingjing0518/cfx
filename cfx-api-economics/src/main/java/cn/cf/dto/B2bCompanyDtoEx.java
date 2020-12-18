package cn.cf.dto;


public class B2bCompanyDtoEx extends B2bCompanyDto{

	private static final long serialVersionUID = 1L;
	
	private String storePk;
	
	private String storeName;
	
	private String parentName;
	
	private String bankPk;
	
	private String bankName;
	
	private String creditPk;
	
	private String customerNumber;
	

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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBankPk() {
		return bankPk;
	}

	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCreditPk() {
		return creditPk;
	}

	public void setCreditPk(String creditPk) {
		this.creditPk = creditPk;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	

}
