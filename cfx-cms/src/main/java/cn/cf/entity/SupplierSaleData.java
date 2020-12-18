package cn.cf.entity;

import java.math.BigDecimal;

public class SupplierSaleData {

	private Integer counts = 0;
	private BigDecimal amount = new BigDecimal(0.0);
	private Double weight = 0d;
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
