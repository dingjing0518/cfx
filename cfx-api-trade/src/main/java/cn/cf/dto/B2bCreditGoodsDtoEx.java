package cn.cf.dto;

public class B2bCreditGoodsDtoEx extends B2bCreditGoodsDto{
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
	
	
}
