package cn.cf.entity;

public class PricProductIndexParams {


	private String loanNumber;

	private String purchaserName;

	private String goodsInfo;

	private Integer isConfirm;//1未确认，2已确认

	private String repaymentTimeStart;//查询条件

	private String repaymentTimeEnd;//查询条件

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public Integer getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getRepaymentTimeStart() {
		return repaymentTimeStart;
	}

	public void setRepaymentTimeStart(String repaymentTimeStart) {
		this.repaymentTimeStart = repaymentTimeStart;
	}

	public String getRepaymentTimeEnd() {
		return repaymentTimeEnd;
	}

	public void setRepaymentTimeEnd(String repaymentTimeEnd) {
		this.repaymentTimeEnd = repaymentTimeEnd;
	}
}
