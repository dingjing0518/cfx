package cn.cf.entity;

import cn.cf.dto.B2bCreditGoodsDto;

public class B2bCreditGoodsDtoMa extends B2bCreditGoodsDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double proportion;
	private String startTime;
	private String endTime;
	private Double avaiableAmount;
	private String bankAccount;
	private String bankName;
	private String contractNumber;
	private String storeInfo;

	public Double getProportion() {
		return proportion;
	}
	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Double getAvaiableAmount() {
		return avaiableAmount;
	}
	public void setAvaiableAmount(Double avaiableAmount) {
		this.avaiableAmount = avaiableAmount;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(String storeInfo) {
		this.storeInfo = storeInfo;
	}
}
