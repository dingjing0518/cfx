package cn.cf.entity;

import java.math.BigDecimal;
import java.util.List;

public class BankBalanceEntity {
	
	private String bankPk ;
	private String bankName;
	private BigDecimal btAmount ;//白条金额
	private BigDecimal dAmount;//化纤贷金额
	private List<LoanDetail>  btList;
	private List<LoanDetail>  dList;
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
	public BigDecimal getBtAmount() {
		return btAmount;
	}
	public void setBtAmount(BigDecimal btAmount) {
		this.btAmount = btAmount;
	}
	public BigDecimal getdAmount() {
		return dAmount;
	}
	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}
	public List<LoanDetail> getBtList() {
		return btList;
	}
	public void setBtList(List<LoanDetail> btList) {
		this.btList = btList;
	}
	public List<LoanDetail> getdList() {
		return dList;
	}
	public void setdList(List<LoanDetail> dList) {
		this.dList = dList;
	}
	
	
	
	
	

}
