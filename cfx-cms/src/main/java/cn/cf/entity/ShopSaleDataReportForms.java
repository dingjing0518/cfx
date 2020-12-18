package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

/**
 * 客户申请金融产品mongo实体;
 * 
 * @author hxh
 *
 */
public class ShopSaleDataReportForms {
	@Id
	private String id;
	
	private Double weightSH = 0d;
	private BigDecimal amountSH = new BigDecimal(0.0);
	private Integer countsSH = 0;
	private Double percentSH;
	private Double addPercentSH;
	
	private Double weightXFM = 0d;
	private BigDecimal amountXFM = new BigDecimal(0.0);
	private Integer countsXFM = 0;
	private Double percentXFM;
	private Double addPercentXFM;
	
	private Double weightOther = 0d;
	private BigDecimal amountOther = new BigDecimal(0.0);
	private Integer countsOther = 0;
	private Double percentOther;
	private Double addPercentOther;
	
	private Double addUpWeigth = 0d;	
	private BigDecimal addUpAmount = new BigDecimal(0.0);
	private Integer addUpCounts = 0;
	
	private String year;// 年份
	private String month;// 月份
	private String insertTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getWeightSH() {
		return weightSH;
	}
	public void setWeightSH(Double weightSH) {
		this.weightSH = weightSH;
	}
	public BigDecimal getAmountSH() {
		return amountSH;
	}
	public void setAmountSH(BigDecimal amountSH) {
		this.amountSH = amountSH;
	}
	public Integer getCountsSH() {
		return countsSH;
	}
	public void setCountsSH(Integer countsSH) {
		this.countsSH = countsSH;
	}
	public Double getPercentSH() {
		return percentSH;
	}
	public void setPercentSH(Double percentSH) {
		this.percentSH = percentSH;
	}
	public Double getAddPercentSH() {
		return addPercentSH;
	}
	public void setAddPercentSH(Double addPercentSH) {
		this.addPercentSH = addPercentSH;
	}
	public Double getWeightXFM() {
		return weightXFM;
	}
	public void setWeightXFM(Double weightXFM) {
		this.weightXFM = weightXFM;
	}
	public BigDecimal getAmountXFM() {
		return amountXFM;
	}
	public void setAmountXFM(BigDecimal amountXFM) {
		this.amountXFM = amountXFM;
	}
	public Integer getCountsXFM() {
		return countsXFM;
	}
	public void setCountsXFM(Integer countsXFM) {
		this.countsXFM = countsXFM;
	}
	public Double getPercentXFM() {
		return percentXFM;
	}
	public void setPercentXFM(Double percentXFM) {
		this.percentXFM = percentXFM;
	}
	public Double getAddPercentXFM() {
		return addPercentXFM;
	}
	public void setAddPercentXFM(Double addPercentXFM) {
		this.addPercentXFM = addPercentXFM;
	}
	public Double getWeightOther() {
		return weightOther;
	}
	public void setWeightOther(Double weightOther) {
		this.weightOther = weightOther;
	}
	public BigDecimal getAmountOther() {
		return amountOther;
	}
	public void setAmountOther(BigDecimal amountOther) {
		this.amountOther = amountOther;
	}
	public Integer getCountsOther() {
		return countsOther;
	}
	public void setCountsOther(Integer countsOther) {
		this.countsOther = countsOther;
	}
	public Double getPercentOther() {
		return percentOther;
	}
	public void setPercentOther(Double percentOther) {
		this.percentOther = percentOther;
	}
	public Double getAddPercentOther() {
		return addPercentOther;
	}
	public void setAddPercentOther(Double addPercentOther) {
		this.addPercentOther = addPercentOther;
	}
	public Double getAddUpWeigth() {
		return addUpWeigth;
	}
	public void setAddUpWeigth(Double addUpWeigth) {
		this.addUpWeigth = addUpWeigth;
	}
	public BigDecimal getAddUpAmount() {
		return addUpAmount;
	}
	public void setAddUpAmount(BigDecimal addUpAmount) {
		this.addUpAmount = addUpAmount;
	}
	public Integer getAddUpCounts() {
		return addUpCounts;
	}
	public void setAddUpCounts(Integer addUpCounts) {
		this.addUpCounts = addUpCounts;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	

}
