package cn.cf.dto;

public class OffLineRuleDetail {

	private String salesVolume;
	private String tradeYear;
	private String tradeScore;
	
	private String invoiceAmount;
	private String invoiceScore;
	private String salesAmount;
	private String salesScore;
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getTradeYear() {
		return tradeYear;
	}
	public void setTradeYear(String tradeYear) {
		this.tradeYear = tradeYear;
	}
	public String getTradeScore() {
		return tradeScore;
	}
	public void setTradeScore(String tradeScore) {
		this.tradeScore = tradeScore;
	}
	public String getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getInvoiceScore() {
		return invoiceScore;
	}
	public void setInvoiceScore(String invoiceScore) {
		this.invoiceScore = invoiceScore;
	}
	public String getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(String salesAmount) {
		this.salesAmount = salesAmount;
	}
	public String getSalesScore() {
		return salesScore;
	}
	public void setSalesScore(String salesScore) {
		this.salesScore = salesScore;
	}
	
}
