package cn.cf.entity;


import cn.cf.dto.B2bLoanNumberDto;

public class DeliveryBasic {
	
	private B2bOrderDtoMa order;
	private B2bLoanNumberDto loan;
	private Double surplusAmount;//剩余提货金额
	private Double downPaymentAmount;//首付款金额
	
	
	public B2bOrderDtoMa getOrder() {
		return order;
	}
	public void setOrder(B2bOrderDtoMa order) {
		this.order = order;
	}
	public B2bLoanNumberDto getLoan() {
		return loan;
	}
	public void setLoan(B2bLoanNumberDto loan) {
		this.loan = loan;
	}
	public Double getSurplusAmount() {
		return surplusAmount;
	}
	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	public Double getDownPaymentAmount() {
		return downPaymentAmount;
	}
	public void setDownPaymentAmount(Double downPaymentAmount) {
		this.downPaymentAmount = downPaymentAmount;
	}
}
