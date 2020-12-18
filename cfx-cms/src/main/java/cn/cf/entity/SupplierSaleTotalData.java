package cn.cf.entity;

import java.math.BigDecimal;

public class SupplierSaleTotalData {

	private Integer countsAddup = 0;
	private BigDecimal amountAddup = new BigDecimal(0.0);
	private Double weightAddup = 0d;
	
	private Integer countsSH = 0;
	private BigDecimal amountSH = new BigDecimal(0.0);
	private Double weightSH = 0d;
	
	private Integer countsXFM = 0;
	private BigDecimal amountXFM = new BigDecimal(0.0);
	private Double weightXFM = 0d;
	
	private Integer countsOther = 0;
	private BigDecimal amountOther = new BigDecimal(0.0);
	private Double weightOther = 0d;
	
	public Integer getCountsAddup() {
		return countsAddup;
	}
	public void setCountsAddup(Integer countsAddup) {
		this.countsAddup = countsAddup;
	}
	public BigDecimal getAmountAddup() {
		return amountAddup;
	}
	public void setAmountAddup(BigDecimal amountAddup) {
		this.amountAddup = amountAddup;
	}
	public Double getWeightAddup() {
		return weightAddup;
	}
	public void setWeightAddup(Double weightAddup) {
		this.weightAddup = weightAddup;
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
	
}
