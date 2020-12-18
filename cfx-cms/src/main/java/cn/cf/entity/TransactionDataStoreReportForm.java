package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class TransactionDataStoreReportForm {
	
	@Id
	private String id;
	
	private String storePk;
	
	private String storeName;
	
	private BigDecimal amountBlank = new BigDecimal(0.0);
	
	private BigDecimal weightBlank = new BigDecimal(0.0);
	
	private Integer countBlank = 0;
	
	private BigDecimal amountLoan = new BigDecimal(0.0);
	
	private BigDecimal weightLoan = new BigDecimal(0.0);
	
	private Integer countLoan = 0;
	
	private String countTime;
	
	private String insertTime;
	
	//查询条件字段
	private String countTimeStart;
	
	private String countTimeEnd;
	
	//导出勾选数据条件
	private String ids;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public BigDecimal getAmountBlank() {
		return amountBlank;
	}

	public void setAmountBlank(BigDecimal amountBlank) {
		this.amountBlank = amountBlank;
	}

	public BigDecimal getWeightBlank() {
		return weightBlank;
	}

	public void setWeightBlank(BigDecimal weightBlank) {
		this.weightBlank = weightBlank;
	}

	public Integer getCountBlank() {
		return countBlank;
	}

	public void setCountBlank(Integer countBlank) {
		this.countBlank = countBlank;
	}

	public BigDecimal getAmountLoan() {
		return amountLoan;
	}

	public void setAmountLoan(BigDecimal amountLoan) {
		this.amountLoan = amountLoan;
	}

	public BigDecimal getWeightLoan() {
		return weightLoan;
	}

	public void setWeightLoan(BigDecimal weightLoan) {
		this.weightLoan = weightLoan;
	}

	public Integer getCountLoan() {
		return countLoan;
	}

	public void setCountLoan(Integer countLoan) {
		this.countLoan = countLoan;
	}

	public String getCountTime() {
		return countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getCountTimeStart() {
		return countTimeStart;
	}

	public void setCountTimeStart(String countTimeStart) {
		this.countTimeStart = countTimeStart;
	}

	public String getCountTimeEnd() {
		return countTimeEnd;
	}

	public void setCountTimeEnd(String countTimeEnd) {
		this.countTimeEnd = countTimeEnd;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
