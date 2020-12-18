package cn.cf.entity;

public class OrderPay {
	private String orderNumber;
	private String paymentPk;
	private String paymentName;
	private String orderStatus;
	private String payPassword;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPaymentPk() {
		return paymentPk;
	}
	public void setPaymentPk(String paymentPk) {
		this.paymentPk = paymentPk;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	private String loanStatus;
	
	
	
	
	
}
