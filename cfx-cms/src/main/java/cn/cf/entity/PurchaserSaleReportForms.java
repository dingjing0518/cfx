package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

/**
 * 客户申请金融产品mongo实体;
 * 
 * @author hxh
 *
 */
public class PurchaserSaleReportForms {
	@Id
	private String id;
	
	private Integer counts1 = 0;
	private BigDecimal amount1 = new BigDecimal(0.0);
	private Double weight1 = 0d;
	
	private Integer counts2 = 0;
	private BigDecimal amount2 = new BigDecimal(0.0);
	private Double weight2 = 0d;
	
	private Integer counts3 = 0;
	private BigDecimal amount3 = new BigDecimal(0.0);
	private Double weight3 = 0d;
	
	private Integer counts4 = 0;
	private BigDecimal amount4 = new BigDecimal(0.0);
	private Double weight4 = 0d;
	
	private Integer counts5 = 0;
	private BigDecimal amount5 = new BigDecimal(0.0);
	private Double weight5 = 0d;
	
	private Integer counts6 = 0;
	private BigDecimal amount6 = new BigDecimal(0.0);
	private Double weight6 = 0d;
	
	private Integer counts7 = 0;
	private BigDecimal amount7 = new BigDecimal(0.0);
	private Double weight7 = 0d;
	
	private Integer counts8 = 0;
	private BigDecimal amount8 = new BigDecimal(0.0);
	private Double weight8 = 0d;
	
	private Integer counts9 = 0;
	private BigDecimal amount9 = new BigDecimal(0.0);
	private Double weight9 = 0d;
	
	private Integer counts10 = 0;
	private BigDecimal amount10 = new BigDecimal(0.0);
	private Double weight10 = 0d;
	
	private Integer counts11 = 0;
	private BigDecimal amount11 = new BigDecimal(0.0);
	private Double weight11 = 0d;
	
	private Integer counts12 = 0;
	private BigDecimal amount12 = new BigDecimal(0.0);
	private Double weight12 = 0d;
	
	private Integer countsAll = 0;
	private BigDecimal amountAll = new BigDecimal(0.0);
	private Double weightAll = 0d;
	
	private String year;// 年份
	private String insertTime;
	private String purchaserName;// 采购商名称
	private String accountName;// 业务员名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCounts1() {
		return counts1;
	}
	public void setCounts1(Integer counts1) {
		this.counts1 = counts1;
	}
	public BigDecimal getAmount1() {
		return amount1;
	}
	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}
	public Double getWeight1() {
		return weight1;
	}
	public void setWeight1(Double weight1) {
		this.weight1 = weight1;
	}
	public Integer getCounts2() {
		return counts2;
	}
	public void setCounts2(Integer counts2) {
		this.counts2 = counts2;
	}
	public BigDecimal getAmount2() {
		return amount2;
	}
	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}
	public Double getWeight2() {
		return weight2;
	}
	public void setWeight2(Double weight2) {
		this.weight2 = weight2;
	}
	public Integer getCounts3() {
		return counts3;
	}
	public void setCounts3(Integer counts3) {
		this.counts3 = counts3;
	}
	public BigDecimal getAmount3() {
		return amount3;
	}
	public void setAmount3(BigDecimal amount3) {
		this.amount3 = amount3;
	}
	public Double getWeight3() {
		return weight3;
	}
	public void setWeight3(Double weight3) {
		this.weight3 = weight3;
	}
	public Integer getCounts4() {
		return counts4;
	}
	public void setCounts4(Integer counts4) {
		this.counts4 = counts4;
	}
	public BigDecimal getAmount4() {
		return amount4;
	}
	public void setAmount4(BigDecimal amount4) {
		this.amount4 = amount4;
	}
	public Double getWeight4() {
		return weight4;
	}
	public void setWeight4(Double weight4) {
		this.weight4 = weight4;
	}
	public Integer getCounts5() {
		return counts5;
	}
	public void setCounts5(Integer counts5) {
		this.counts5 = counts5;
	}
	public BigDecimal getAmount5() {
		return amount5;
	}
	public void setAmount5(BigDecimal amount5) {
		this.amount5 = amount5;
	}
	public Double getWeight5() {
		return weight5;
	}
	public void setWeight5(Double weight5) {
		this.weight5 = weight5;
	}
	public Integer getCounts6() {
		return counts6;
	}
	public void setCounts6(Integer counts6) {
		this.counts6 = counts6;
	}
	public BigDecimal getAmount6() {
		return amount6;
	}
	public void setAmount6(BigDecimal amount6) {
		this.amount6 = amount6;
	}
	public Double getWeight6() {
		return weight6;
	}
	public void setWeight6(Double weight6) {
		this.weight6 = weight6;
	}
	public Integer getCounts7() {
		return counts7;
	}
	public void setCounts7(Integer counts7) {
		this.counts7 = counts7;
	}
	public BigDecimal getAmount7() {
		return amount7;
	}
	public void setAmount7(BigDecimal amount7) {
		this.amount7 = amount7;
	}
	public Double getWeight7() {
		return weight7;
	}
	public void setWeight7(Double weight7) {
		this.weight7 = weight7;
	}
	public Integer getCounts8() {
		return counts8;
	}
	public void setCounts8(Integer counts8) {
		this.counts8 = counts8;
	}
	public BigDecimal getAmount8() {
		return amount8;
	}
	public void setAmount8(BigDecimal amount8) {
		this.amount8 = amount8;
	}
	public Double getWeight8() {
		return weight8;
	}
	public void setWeight8(Double weight8) {
		this.weight8 = weight8;
	}
	public Integer getCounts9() {
		return counts9;
	}
	public void setCounts9(Integer counts9) {
		this.counts9 = counts9;
	}
	public BigDecimal getAmount9() {
		return amount9;
	}
	public void setAmount9(BigDecimal amount9) {
		this.amount9 = amount9;
	}
	public Double getWeight9() {
		return weight9;
	}
	public void setWeight9(Double weight9) {
		this.weight9 = weight9;
	}
	public Integer getCounts10() {
		return counts10;
	}
	public void setCounts10(Integer counts10) {
		this.counts10 = counts10;
	}
	public BigDecimal getAmount10() {
		return amount10;
	}
	public void setAmount10(BigDecimal amount10) {
		this.amount10 = amount10;
	}
	public Double getWeight10() {
		return weight10;
	}
	public void setWeight10(Double weight10) {
		this.weight10 = weight10;
	}
	public Integer getCounts11() {
		return counts11;
	}
	public void setCounts11(Integer counts11) {
		this.counts11 = counts11;
	}
	public BigDecimal getAmount11() {
		return amount11;
	}
	public void setAmount11(BigDecimal amount11) {
		this.amount11 = amount11;
	}
	public Double getWeight11() {
		return weight11;
	}
	public void setWeight11(Double weight11) {
		this.weight11 = weight11;
	}
	public Integer getCounts12() {
		return counts12;
	}
	public void setCounts12(Integer counts12) {
		this.counts12 = counts12;
	}
	public BigDecimal getAmount12() {
		return amount12;
	}
	public void setAmount12(BigDecimal amount12) {
		this.amount12 = amount12;
	}
	public Double getWeight12() {
		return weight12;
	}
	public void setWeight12(Double weight12) {
		this.weight12 = weight12;
	}
	public Integer getCountsAll() {
		return countsAll;
	}
	public void setCountsAll(Integer countsAll) {
		this.countsAll = countsAll;
	}
	public BigDecimal getAmountAll() {
		return amountAll;
	}
	public void setAmountAll(BigDecimal amountAll) {
		this.amountAll = amountAll;
	}
	public Double getWeightAll() {
		return weightAll;
	}
	public void setWeightAll(Double weightAll) {
		this.weightAll = weightAll;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
