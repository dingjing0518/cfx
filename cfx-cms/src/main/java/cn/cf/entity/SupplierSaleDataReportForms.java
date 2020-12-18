package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class SupplierSaleDataReportForms{

	@Id
	private String id;
	
	private Double precentSH = 0d;
	private Double addUpPrecentSH = 0d;
	private Integer countsSH = 0;
	private BigDecimal amountSH = new BigDecimal(0.0);
	private Double weightSH = 0d;

	private Double precentXFM = 0d;
	private Double addUpPrecentXFM = 0d;
	private Integer countsXFM = 0;
	private BigDecimal amountXFM = new BigDecimal(0.0);
	private Double weightXFM = 0d;

	private Double precentOther = 0d;
	private Double addUpPrecentOther = 0d;
	private Integer countsOther = 0;
	private BigDecimal amountOther = new BigDecimal(0.0);
	private Double weightOther = 0d;

	private Integer countsAddUp = 0;
	private BigDecimal amountAddUp = new BigDecimal(0.0);
	private Double weightAddUp = 0d;

	private String year;

	private Integer month;
	
	private String monthName;

	private String insertTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPrecentSH() {
		return precentSH;
	}

	public void setPrecentSH(Double precentSH) {
		this.precentSH = precentSH;
	}

	public Double getAddUpPrecentSH() {
		return addUpPrecentSH;
	}

	public void setAddUpPrecentSH(Double addUpPrecentSH) {
		this.addUpPrecentSH = addUpPrecentSH;
	}

	public Integer getCountsSH() {
		return countsSH;
	}

	public void setCountsSH(Integer countsSH) {
		this.countsSH = countsSH;
	}

	public BigDecimal getAmountSH() {
		return amountSH;
	}

	public void setAmountSH(BigDecimal amountSH) {
		this.amountSH = amountSH;
	}

	public Double getWeightSH() {
		return weightSH;
	}

	public void setWeightSH(Double weightSH) {
		this.weightSH = weightSH;
	}

	public Double getPrecentXFM() {
		return precentXFM;
	}

	public void setPrecentXFM(Double precentXFM) {
		this.precentXFM = precentXFM;
	}

	public Double getAddUpPrecentXFM() {
		return addUpPrecentXFM;
	}

	public void setAddUpPrecentXFM(Double addUpPrecentXFM) {
		this.addUpPrecentXFM = addUpPrecentXFM;
	}

	public Integer getCountsXFM() {
		return countsXFM;
	}

	public void setCountsXFM(Integer countsXFM) {
		this.countsXFM = countsXFM;
	}

	public BigDecimal getAmountXFM() {
		return amountXFM;
	}

	public void setAmountXFM(BigDecimal amountXFM) {
		this.amountXFM = amountXFM;
	}

	public Double getWeightXFM() {
		return weightXFM;
	}

	public void setWeightXFM(Double weightXFM) {
		this.weightXFM = weightXFM;
	}

	public Double getPrecentOther() {
		return precentOther;
	}

	public void setPrecentOther(Double precentOther) {
		this.precentOther = precentOther;
	}

	public Double getAddUpPrecentOther() {
		return addUpPrecentOther;
	}

	public void setAddUpPrecentOther(Double addUpPrecentOther) {
		this.addUpPrecentOther = addUpPrecentOther;
	}

	public Integer getCountsOther() {
		return countsOther;
	}

	public void setCountsOther(Integer countsOther) {
		this.countsOther = countsOther;
	}

	public BigDecimal getAmountOther() {
		return amountOther;
	}

	public void setAmountOther(BigDecimal amountOther) {
		this.amountOther = amountOther;
	}

	public Double getWeightOther() {
		return weightOther;
	}

	public void setWeightOther(Double weightOther) {
		this.weightOther = weightOther;
	}

	public Integer getCountsAddUp() {
		return countsAddUp;
	}

	public void setCountsAddUp(Integer countsAddUp) {
		this.countsAddUp = countsAddUp;
	}

	public BigDecimal getAmountAddUp() {
		return amountAddUp;
	}

	public void setAmountAddUp(BigDecimal amountAddUp) {
		this.amountAddUp = amountAddUp;
	}

	public Double getWeightAddUp() {
		return weightAddUp;
	}

	public void setWeightAddUp(Double weightAddUp) {
		this.weightAddUp = weightAddUp;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
