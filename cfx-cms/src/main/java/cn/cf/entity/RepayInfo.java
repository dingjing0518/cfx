package cn.cf.entity;

public class RepayInfo {
	private String companyPk; 
	private Double amount;
	private String bankPk;
	private Integer source;
	private Integer productType;
	
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getBankPk() {
		return bankPk;
	}
	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
}
