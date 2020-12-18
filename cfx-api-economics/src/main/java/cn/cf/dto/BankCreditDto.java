package cn.cf.dto;

import java.util.Date;

public class BankCreditDto {
	
	private Date loanStartDate;//放款开始日期
	private Date loanEndDate;//放假结束日期
	private Double loanAmount;//放款金额
	private String iouNumber;//借据编号
	private String contractNumber;//合同编号
	private Integer payStatus;//支付状态
	private Integer loanStatus;//放款状态
	private String loanAccount;//贷款账户
	private Double loanRate;//贷款利率

	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public Date getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getIouNumber() {
		return iouNumber;
	}
	public void setIouNumber(String iouNumber) {
		this.iouNumber = iouNumber;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLoanAccount() {
		return loanAccount;
	}
	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}
	public Double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}
	
	
	
}
