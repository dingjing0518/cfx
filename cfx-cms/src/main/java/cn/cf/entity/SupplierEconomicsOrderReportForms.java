package cn.cf.entity;

import javax.persistence.Id;

/**
 * 客户申请金融产品mongo实体;
 * 
 * @author hxh
 *
 */
public class SupplierEconomicsOrderReportForms {
	@Id
	private String id;
	private Integer blankOrderAllCount = 0;// 化纤白条订单数合计
	private Double blankOrderAllAmount = 0d;// 化纤白条订单金额合计
	private Integer loanOrderAllCount = 0;// 化纤贷订单数合计
	private Double loanOrderAllAmount = 0d;// 化纤贷订单金额合计
	
	private String year;// 年份
	private Integer maxMonth;//统计数据的最大月份
	private Integer blankOrderCount1 = 0;// 化纤白条订单数
	private Double blankOrderAmount1 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount1 = 0;// 化纤贷订单数
	private Double loanOrderAmount1 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount2 = 0;// 化纤白条订单数
	private Double blankOrderAmount2 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount2 = 0;// 化纤贷订单数
	private Double loanOrderAmount2 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount3 = 0;// 化纤白条订单数
	private Double blankOrderAmount3 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount3 = 0;// 化纤贷订单数
	private Double loanOrderAmount3 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount4 = 0;// 化纤白条订单数
	private Double blankOrderAmount4 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount4 = 0;// 化纤贷订单数
	private Double loanOrderAmount4 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount5 = 0;// 化纤白条订单数
	private Double blankOrderAmount5 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount5 = 0;// 化纤贷订单数
	private Double loanOrderAmount5 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount6 = 0;// 化纤白条订单数
	private Double blankOrderAmount6 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount6 = 0;// 化纤贷订单数
	private Double loanOrderAmount6 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount7 = 0;// 化纤白条订单数
	private Double blankOrderAmount7 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount7 = 0;// 化纤贷订单数
	private Double loanOrderAmount7 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount8 = 0;// 化纤白条订单数
	private Double blankOrderAmount8 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount8 = 0;// 化纤贷订单数
	private Double loanOrderAmount8 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount9 = 0;// 化纤白条订单数
	private Double blankOrderAmount9 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount9 = 0;// 化纤贷订单数
	private Double loanOrderAmount9 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount10 = 0;// 化纤白条订单数
	private Double blankOrderAmount10 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount10 = 0;// 化纤贷订单数
	private Double loanOrderAmount10 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount11 = 0;// 化纤白条订单数
	private Double blankOrderAmount11 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount11 = 0;// 化纤贷订单数
	private Double loanOrderAmount11 = 0d;// 化纤贷订单金额
	
	private Integer blankOrderCount12 = 0;// 化纤白条订单数
	private Double blankOrderAmount12 = 0d;// 化纤白条订单金额
	private Integer loanOrderCount12 = 0;// 化纤贷订单数
	private Double loanOrderAmount12 = 0d;// 化纤贷订单金额
	
	private String insertTime;
	private String storePk;// 供应商店铺pk
	private String storeName;// 供应商店铺名称
	private String bankPk;// 银行Pk
	private String bankName;// 银行名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getBlankOrderAllCount() {
		return blankOrderAllCount;
	}
	public void setBlankOrderAllCount(Integer blankOrderAllCount) {
		this.blankOrderAllCount = blankOrderAllCount;
	}
	public Double getBlankOrderAllAmount() {
		return blankOrderAllAmount;
	}
	public void setBlankOrderAllAmount(Double blankOrderAllAmount) {
		this.blankOrderAllAmount = blankOrderAllAmount;
	}
	public Integer getLoanOrderAllCount() {
		return loanOrderAllCount;
	}
	public void setLoanOrderAllCount(Integer loanOrderAllCount) {
		this.loanOrderAllCount = loanOrderAllCount;
	}
	public Double getLoanOrderAllAmount() {
		return loanOrderAllAmount;
	}
	public void setLoanOrderAllAmount(Double loanOrderAllAmount) {
		this.loanOrderAllAmount = loanOrderAllAmount;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getMaxMonth() {
		return maxMonth;
	}
	public void setMaxMonth(Integer maxMonth) {
		this.maxMonth = maxMonth;
	}
	public Integer getBlankOrderCount1() {
		return blankOrderCount1;
	}
	public void setBlankOrderCount1(Integer blankOrderCount1) {
		this.blankOrderCount1 = blankOrderCount1;
	}
	public Double getBlankOrderAmount1() {
		return blankOrderAmount1;
	}
	public void setBlankOrderAmount1(Double blankOrderAmount1) {
		this.blankOrderAmount1 = blankOrderAmount1;
	}
	public Integer getLoanOrderCount1() {
		return loanOrderCount1;
	}
	public void setLoanOrderCount1(Integer loanOrderCount1) {
		this.loanOrderCount1 = loanOrderCount1;
	}
	public Double getLoanOrderAmount1() {
		return loanOrderAmount1;
	}
	public void setLoanOrderAmount1(Double loanOrderAmount1) {
		this.loanOrderAmount1 = loanOrderAmount1;
	}
	public Integer getBlankOrderCount2() {
		return blankOrderCount2;
	}
	public void setBlankOrderCount2(Integer blankOrderCount2) {
		this.blankOrderCount2 = blankOrderCount2;
	}
	public Double getBlankOrderAmount2() {
		return blankOrderAmount2;
	}
	public void setBlankOrderAmount2(Double blankOrderAmount2) {
		this.blankOrderAmount2 = blankOrderAmount2;
	}
	public Integer getLoanOrderCount2() {
		return loanOrderCount2;
	}
	public void setLoanOrderCount2(Integer loanOrderCount2) {
		this.loanOrderCount2 = loanOrderCount2;
	}
	public Double getLoanOrderAmount2() {
		return loanOrderAmount2;
	}
	public void setLoanOrderAmount2(Double loanOrderAmount2) {
		this.loanOrderAmount2 = loanOrderAmount2;
	}
	public Integer getBlankOrderCount3() {
		return blankOrderCount3;
	}
	public void setBlankOrderCount3(Integer blankOrderCount3) {
		this.blankOrderCount3 = blankOrderCount3;
	}
	public Double getBlankOrderAmount3() {
		return blankOrderAmount3;
	}
	public void setBlankOrderAmount3(Double blankOrderAmount3) {
		this.blankOrderAmount3 = blankOrderAmount3;
	}
	public Integer getLoanOrderCount3() {
		return loanOrderCount3;
	}
	public void setLoanOrderCount3(Integer loanOrderCount3) {
		this.loanOrderCount3 = loanOrderCount3;
	}
	public Double getLoanOrderAmount3() {
		return loanOrderAmount3;
	}
	public void setLoanOrderAmount3(Double loanOrderAmount3) {
		this.loanOrderAmount3 = loanOrderAmount3;
	}
	public Integer getBlankOrderCount4() {
		return blankOrderCount4;
	}
	public void setBlankOrderCount4(Integer blankOrderCount4) {
		this.blankOrderCount4 = blankOrderCount4;
	}
	public Double getBlankOrderAmount4() {
		return blankOrderAmount4;
	}
	public void setBlankOrderAmount4(Double blankOrderAmount4) {
		this.blankOrderAmount4 = blankOrderAmount4;
	}
	public Integer getLoanOrderCount4() {
		return loanOrderCount4;
	}
	public void setLoanOrderCount4(Integer loanOrderCount4) {
		this.loanOrderCount4 = loanOrderCount4;
	}
	public Double getLoanOrderAmount4() {
		return loanOrderAmount4;
	}
	public void setLoanOrderAmount4(Double loanOrderAmount4) {
		this.loanOrderAmount4 = loanOrderAmount4;
	}
	public Integer getBlankOrderCount5() {
		return blankOrderCount5;
	}
	public void setBlankOrderCount5(Integer blankOrderCount5) {
		this.blankOrderCount5 = blankOrderCount5;
	}
	public Double getBlankOrderAmount5() {
		return blankOrderAmount5;
	}
	public void setBlankOrderAmount5(Double blankOrderAmount5) {
		this.blankOrderAmount5 = blankOrderAmount5;
	}
	public Integer getLoanOrderCount5() {
		return loanOrderCount5;
	}
	public void setLoanOrderCount5(Integer loanOrderCount5) {
		this.loanOrderCount5 = loanOrderCount5;
	}
	public Double getLoanOrderAmount5() {
		return loanOrderAmount5;
	}
	public void setLoanOrderAmount5(Double loanOrderAmount5) {
		this.loanOrderAmount5 = loanOrderAmount5;
	}
	public Integer getBlankOrderCount6() {
		return blankOrderCount6;
	}
	public void setBlankOrderCount6(Integer blankOrderCount6) {
		this.blankOrderCount6 = blankOrderCount6;
	}
	public Double getBlankOrderAmount6() {
		return blankOrderAmount6;
	}
	public void setBlankOrderAmount6(Double blankOrderAmount6) {
		this.blankOrderAmount6 = blankOrderAmount6;
	}
	public Integer getLoanOrderCount6() {
		return loanOrderCount6;
	}
	public void setLoanOrderCount6(Integer loanOrderCount6) {
		this.loanOrderCount6 = loanOrderCount6;
	}
	public Double getLoanOrderAmount6() {
		return loanOrderAmount6;
	}
	public void setLoanOrderAmount6(Double loanOrderAmount6) {
		this.loanOrderAmount6 = loanOrderAmount6;
	}
	public Integer getBlankOrderCount7() {
		return blankOrderCount7;
	}
	public void setBlankOrderCount7(Integer blankOrderCount7) {
		this.blankOrderCount7 = blankOrderCount7;
	}
	public Double getBlankOrderAmount7() {
		return blankOrderAmount7;
	}
	public void setBlankOrderAmount7(Double blankOrderAmount7) {
		this.blankOrderAmount7 = blankOrderAmount7;
	}
	public Integer getLoanOrderCount7() {
		return loanOrderCount7;
	}
	public void setLoanOrderCount7(Integer loanOrderCount7) {
		this.loanOrderCount7 = loanOrderCount7;
	}
	public Double getLoanOrderAmount7() {
		return loanOrderAmount7;
	}
	public void setLoanOrderAmount7(Double loanOrderAmount7) {
		this.loanOrderAmount7 = loanOrderAmount7;
	}
	public Integer getBlankOrderCount8() {
		return blankOrderCount8;
	}
	public void setBlankOrderCount8(Integer blankOrderCount8) {
		this.blankOrderCount8 = blankOrderCount8;
	}
	public Double getBlankOrderAmount8() {
		return blankOrderAmount8;
	}
	public void setBlankOrderAmount8(Double blankOrderAmount8) {
		this.blankOrderAmount8 = blankOrderAmount8;
	}
	public Integer getLoanOrderCount8() {
		return loanOrderCount8;
	}
	public void setLoanOrderCount8(Integer loanOrderCount8) {
		this.loanOrderCount8 = loanOrderCount8;
	}
	public Double getLoanOrderAmount8() {
		return loanOrderAmount8;
	}
	public void setLoanOrderAmount8(Double loanOrderAmount8) {
		this.loanOrderAmount8 = loanOrderAmount8;
	}
	public Integer getBlankOrderCount9() {
		return blankOrderCount9;
	}
	public void setBlankOrderCount9(Integer blankOrderCount9) {
		this.blankOrderCount9 = blankOrderCount9;
	}
	public Double getBlankOrderAmount9() {
		return blankOrderAmount9;
	}
	public void setBlankOrderAmount9(Double blankOrderAmount9) {
		this.blankOrderAmount9 = blankOrderAmount9;
	}
	public Integer getLoanOrderCount9() {
		return loanOrderCount9;
	}
	public void setLoanOrderCount9(Integer loanOrderCount9) {
		this.loanOrderCount9 = loanOrderCount9;
	}
	public Double getLoanOrderAmount9() {
		return loanOrderAmount9;
	}
	public void setLoanOrderAmount9(Double loanOrderAmount9) {
		this.loanOrderAmount9 = loanOrderAmount9;
	}
	public Integer getBlankOrderCount10() {
		return blankOrderCount10;
	}
	public void setBlankOrderCount10(Integer blankOrderCount10) {
		this.blankOrderCount10 = blankOrderCount10;
	}
	public Double getBlankOrderAmount10() {
		return blankOrderAmount10;
	}
	public void setBlankOrderAmount10(Double blankOrderAmount10) {
		this.blankOrderAmount10 = blankOrderAmount10;
	}
	public Integer getLoanOrderCount10() {
		return loanOrderCount10;
	}
	public void setLoanOrderCount10(Integer loanOrderCount10) {
		this.loanOrderCount10 = loanOrderCount10;
	}
	public Double getLoanOrderAmount10() {
		return loanOrderAmount10;
	}
	public void setLoanOrderAmount10(Double loanOrderAmount10) {
		this.loanOrderAmount10 = loanOrderAmount10;
	}
	public Integer getBlankOrderCount11() {
		return blankOrderCount11;
	}
	public void setBlankOrderCount11(Integer blankOrderCount11) {
		this.blankOrderCount11 = blankOrderCount11;
	}
	public Double getBlankOrderAmount11() {
		return blankOrderAmount11;
	}
	public void setBlankOrderAmount11(Double blankOrderAmount11) {
		this.blankOrderAmount11 = blankOrderAmount11;
	}
	public Integer getLoanOrderCount11() {
		return loanOrderCount11;
	}
	public void setLoanOrderCount11(Integer loanOrderCount11) {
		this.loanOrderCount11 = loanOrderCount11;
	}
	public Double getLoanOrderAmount11() {
		return loanOrderAmount11;
	}
	public void setLoanOrderAmount11(Double loanOrderAmount11) {
		this.loanOrderAmount11 = loanOrderAmount11;
	}
	public Integer getBlankOrderCount12() {
		return blankOrderCount12;
	}
	public void setBlankOrderCount12(Integer blankOrderCount12) {
		this.blankOrderCount12 = blankOrderCount12;
	}
	public Double getBlankOrderAmount12() {
		return blankOrderAmount12;
	}
	public void setBlankOrderAmount12(Double blankOrderAmount12) {
		this.blankOrderAmount12 = blankOrderAmount12;
	}
	public Integer getLoanOrderCount12() {
		return loanOrderCount12;
	}
	public void setLoanOrderCount12(Integer loanOrderCount12) {
		this.loanOrderCount12 = loanOrderCount12;
	}
	public Double getLoanOrderAmount12() {
		return loanOrderAmount12;
	}
	public void setLoanOrderAmount12(Double loanOrderAmount12) {
		this.loanOrderAmount12 = loanOrderAmount12;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
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
	
	
}
