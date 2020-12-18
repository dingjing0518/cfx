package cn.cf.entity;

public class EconomicsProductOrder {
	private String  orderNumber;
	private String  purchaserPk;
	private Integer source ;
	private Double loanAmount;//金融产品支付金额
	private Integer productType;//金融产品类型
	private String economicsGoodsPk;
	private String bankPk;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPurchaserPk() {
		return purchaserPk;
	}
	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getEconomicsGoodsPk() {
		return economicsGoodsPk;
	}
	public void setEconomicsGoodsPk(String economicsGoodsPk) {
		this.economicsGoodsPk = economicsGoodsPk;
	}
	public String getBankPk() {
		return bankPk;
	}
	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}
	
	
	
	

}
