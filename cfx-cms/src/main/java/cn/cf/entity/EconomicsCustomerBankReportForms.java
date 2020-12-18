package cn.cf.entity;

import javax.persistence.Id;

/**
 * 客户申请金融产品mongo实体;
 * 
 * @author hxh
 *
 */
public class EconomicsCustomerBankReportForms {
	@Id
	private String id;
	// 化纤白条
	private Integer blankSH = 0;// 来源盛虹
	private Integer blankXFM = 0;// 新凤鸣
	private Integer blankYX = 0;// 来源营销
	private Integer blankPT = 0;// 平台
	private Integer blankOther = 0;// 其他
	private Integer blankGoodsTypeCount = 0;// 合计
	private Integer blankGoodsTypeAllCount = 0;//总合计
	// 化纤贷
	private Integer loanSH = 0;// 来源盛虹
	private Integer loanXFM = 0;// 新凤鸣
	private Integer loanYX = 0;// 来源营销
	private Integer loanPT = 0;// 平台
	private Integer loanOther = 0;// 其他
	private Integer loanGoodsTypeCount = 0;// 合计
	private Integer loanGoodsTypeAllCount = 0;//总合计
	// 化纤白条+化纤贷
	private Integer blaLoSH = 0;// 来源盛虹
	private Integer blaLoXFM = 0;// 新凤鸣
	private Integer blaLoYX = 0;// 来源营销
	private Integer blaLoPT = 0;// 平台
	private Integer blaLoOther = 0;// 其他
	private Integer blaLoGoodsTypeCount = 0;// 合计
	private Integer blaLoGoodsTypeAllCount = 0;//总合计
	
	// 累计
	private Integer blankAddUpSH = 0;// 盛虹累计
	private Integer blankAddUpXFM = 0;// 新凤鸣累计
	private Integer blankAddUpYX = 0;// 营销累计
	private Integer blankAddUpPT = 0;// 平台累计
	private Integer blankAddUpOther = 0;// 其他累计
	
	private Integer loanAddUpSH = 0;// 盛虹累计
	private Integer loanAddUpXFM = 0;// 新凤鸣累计
	private Integer loanAddUpYX = 0;// 营销累计
	private Integer loanAddUpPT = 0;// 平台累计
	private Integer loanAddUpOther = 0;// 其他累计
	
	private Integer blaLoAddUpSH = 0;// 盛虹累计
	private Integer blaLoAddUpXFM = 0;// 新凤鸣累计
	private Integer blaLoAddUpYX = 0;// 营销累计
	private Integer blaLoAddUpPT = 0;// 平台累计
	private Integer blaLoAddUpOther = 0;// 其他累计
	
	private Integer blankAddUpCount = 0;// 累计合计
	private Integer loanAddUpCount = 0;// 累计合计
	private Integer blaLoAddUpCount = 0;// 累计合计
	private String year;// 年份
	private Integer month;// 月份 上周-2，本周-1 昨日0
	private String monthName;
	private Integer times;// 1.昨日,2.当周,3.上周,4,月份
	private String insertTime;
	private String bankPk;
	private String bankName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getBlankSH() {
		return blankSH;
	}
	public void setBlankSH(Integer blankSH) {
		this.blankSH = blankSH;
	}
	public Integer getBlankXFM() {
		return blankXFM;
	}
	public void setBlankXFM(Integer blankXFM) {
		this.blankXFM = blankXFM;
	}
	public Integer getBlankYX() {
		return blankYX;
	}
	public void setBlankYX(Integer blankYX) {
		this.blankYX = blankYX;
	}
	public Integer getBlankPT() {
		return blankPT;
	}
	public void setBlankPT(Integer blankPT) {
		this.blankPT = blankPT;
	}
	public Integer getBlankOther() {
		return blankOther;
	}
	public void setBlankOther(Integer blankOther) {
		this.blankOther = blankOther;
	}
	public Integer getBlankGoodsTypeCount() {
		return blankGoodsTypeCount;
	}
	public void setBlankGoodsTypeCount(Integer blankGoodsTypeCount) {
		this.blankGoodsTypeCount = blankGoodsTypeCount;
	}
	public Integer getBlankGoodsTypeAllCount() {
		return blankGoodsTypeAllCount;
	}
	public void setBlankGoodsTypeAllCount(Integer blankGoodsTypeAllCount) {
		this.blankGoodsTypeAllCount = blankGoodsTypeAllCount;
	}
	public Integer getLoanSH() {
		return loanSH;
	}
	public void setLoanSH(Integer loanSH) {
		this.loanSH = loanSH;
	}
	public Integer getLoanXFM() {
		return loanXFM;
	}
	public void setLoanXFM(Integer loanXFM) {
		this.loanXFM = loanXFM;
	}
	public Integer getLoanYX() {
		return loanYX;
	}
	public void setLoanYX(Integer loanYX) {
		this.loanYX = loanYX;
	}
	public Integer getLoanPT() {
		return loanPT;
	}
	public void setLoanPT(Integer loanPT) {
		this.loanPT = loanPT;
	}
	public Integer getLoanOther() {
		return loanOther;
	}
	public void setLoanOther(Integer loanOther) {
		this.loanOther = loanOther;
	}
	public Integer getLoanGoodsTypeCount() {
		return loanGoodsTypeCount;
	}
	public void setLoanGoodsTypeCount(Integer loanGoodsTypeCount) {
		this.loanGoodsTypeCount = loanGoodsTypeCount;
	}
	public Integer getLoanGoodsTypeAllCount() {
		return loanGoodsTypeAllCount;
	}
	public void setLoanGoodsTypeAllCount(Integer loanGoodsTypeAllCount) {
		this.loanGoodsTypeAllCount = loanGoodsTypeAllCount;
	}
	public Integer getBlaLoSH() {
		return blaLoSH;
	}
	public void setBlaLoSH(Integer blaLoSH) {
		this.blaLoSH = blaLoSH;
	}
	public Integer getBlaLoXFM() {
		return blaLoXFM;
	}
	public void setBlaLoXFM(Integer blaLoXFM) {
		this.blaLoXFM = blaLoXFM;
	}
	public Integer getBlaLoYX() {
		return blaLoYX;
	}
	public void setBlaLoYX(Integer blaLoYX) {
		this.blaLoYX = blaLoYX;
	}
	public Integer getBlaLoPT() {
		return blaLoPT;
	}
	public void setBlaLoPT(Integer blaLoPT) {
		this.blaLoPT = blaLoPT;
	}
	public Integer getBlaLoOther() {
		return blaLoOther;
	}
	public void setBlaLoOther(Integer blaLoOther) {
		this.blaLoOther = blaLoOther;
	}
	public Integer getBlaLoGoodsTypeCount() {
		return blaLoGoodsTypeCount;
	}
	public void setBlaLoGoodsTypeCount(Integer blaLoGoodsTypeCount) {
		this.blaLoGoodsTypeCount = blaLoGoodsTypeCount;
	}
	public Integer getBlaLoGoodsTypeAllCount() {
		return blaLoGoodsTypeAllCount;
	}
	public void setBlaLoGoodsTypeAllCount(Integer blaLoGoodsTypeAllCount) {
		this.blaLoGoodsTypeAllCount = blaLoGoodsTypeAllCount;
	}
	public Integer getBlankAddUpSH() {
		return blankAddUpSH;
	}
	public void setBlankAddUpSH(Integer blankAddUpSH) {
		this.blankAddUpSH = blankAddUpSH;
	}
	public Integer getBlankAddUpXFM() {
		return blankAddUpXFM;
	}
	public void setBlankAddUpXFM(Integer blankAddUpXFM) {
		this.blankAddUpXFM = blankAddUpXFM;
	}
	public Integer getBlankAddUpYX() {
		return blankAddUpYX;
	}
	public void setBlankAddUpYX(Integer blankAddUpYX) {
		this.blankAddUpYX = blankAddUpYX;
	}
	public Integer getBlankAddUpPT() {
		return blankAddUpPT;
	}
	public void setBlankAddUpPT(Integer blankAddUpPT) {
		this.blankAddUpPT = blankAddUpPT;
	}
	public Integer getBlankAddUpOther() {
		return blankAddUpOther;
	}
	public void setBlankAddUpOther(Integer blankAddUpOther) {
		this.blankAddUpOther = blankAddUpOther;
	}
	public Integer getLoanAddUpSH() {
		return loanAddUpSH;
	}
	public void setLoanAddUpSH(Integer loanAddUpSH) {
		this.loanAddUpSH = loanAddUpSH;
	}
	public Integer getLoanAddUpXFM() {
		return loanAddUpXFM;
	}
	public void setLoanAddUpXFM(Integer loanAddUpXFM) {
		this.loanAddUpXFM = loanAddUpXFM;
	}
	public Integer getLoanAddUpYX() {
		return loanAddUpYX;
	}
	public void setLoanAddUpYX(Integer loanAddUpYX) {
		this.loanAddUpYX = loanAddUpYX;
	}
	public Integer getLoanAddUpPT() {
		return loanAddUpPT;
	}
	public void setLoanAddUpPT(Integer loanAddUpPT) {
		this.loanAddUpPT = loanAddUpPT;
	}
	public Integer getLoanAddUpOther() {
		return loanAddUpOther;
	}
	public void setLoanAddUpOther(Integer loanAddUpOther) {
		this.loanAddUpOther = loanAddUpOther;
	}
	public Integer getBlaLoAddUpSH() {
		return blaLoAddUpSH;
	}
	public void setBlaLoAddUpSH(Integer blaLoAddUpSH) {
		this.blaLoAddUpSH = blaLoAddUpSH;
	}
	public Integer getBlaLoAddUpXFM() {
		return blaLoAddUpXFM;
	}
	public void setBlaLoAddUpXFM(Integer blaLoAddUpXFM) {
		this.blaLoAddUpXFM = blaLoAddUpXFM;
	}
	public Integer getBlaLoAddUpYX() {
		return blaLoAddUpYX;
	}
	public void setBlaLoAddUpYX(Integer blaLoAddUpYX) {
		this.blaLoAddUpYX = blaLoAddUpYX;
	}
	public Integer getBlaLoAddUpPT() {
		return blaLoAddUpPT;
	}
	public void setBlaLoAddUpPT(Integer blaLoAddUpPT) {
		this.blaLoAddUpPT = blaLoAddUpPT;
	}
	public Integer getBlaLoAddUpOther() {
		return blaLoAddUpOther;
	}
	public void setBlaLoAddUpOther(Integer blaLoAddUpOther) {
		this.blaLoAddUpOther = blaLoAddUpOther;
	}
	public Integer getBlankAddUpCount() {
		return blankAddUpCount;
	}
	public void setBlankAddUpCount(Integer blankAddUpCount) {
		this.blankAddUpCount = blankAddUpCount;
	}
	public Integer getLoanAddUpCount() {
		return loanAddUpCount;
	}
	public void setLoanAddUpCount(Integer loanAddUpCount) {
		this.loanAddUpCount = loanAddUpCount;
	}
	public Integer getBlaLoAddUpCount() {
		return blaLoAddUpCount;
	}
	public void setBlaLoAddUpCount(Integer blaLoAddUpCount) {
		this.blaLoAddUpCount = blaLoAddUpCount;
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
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getBankPk() {
		return bankPk;
	}
	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
