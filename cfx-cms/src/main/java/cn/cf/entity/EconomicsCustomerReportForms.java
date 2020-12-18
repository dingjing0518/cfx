package cn.cf.entity;

import javax.persistence.Id;

/**
 * 客户申请金融产品mongo实体;
 * 
 * @author hxh
 *
 */
public class EconomicsCustomerReportForms {
	@Id
	private String id;
	// 化纤白条
	private Integer blankSH = 0;// 来源盛虹
	private Integer blankXFM = 0;// 新凤鸣
	private Integer blankYX = 0;// 来源营销
	private Integer blankPT = 0;// 平台
	private Integer blankOther = 0;// 其他
	private Integer blankGoodsTypeCount = 0;// 合计
	// 化纤贷
	private Integer loanSH = 0;// 来源盛虹
	private Integer loanXFM = 0;// 新凤鸣
	private Integer loanYX = 0;// 来源营销
	private Integer loanPT = 0;// 平台
	private Integer loanOther = 0;// 其他
	private Integer loanGoodsTypeCount = 0;// 合计
	// 化纤白条+化纤贷
	private Integer blaLoSH = 0;// 来源盛虹
	private Integer blaLoXFM = 0;// 新凤鸣
	private Integer blaLoYX = 0;// 来源营销
	private Integer blaLoPT = 0;// 平台
	private Integer blaLoOther = 0;// 其他
	private Integer blaLoGoodsTypeCount = 0;// 合计
	// 累计
	private Integer addUpSH = 0;// 盛虹累计
	private Integer addUpXFM = 0;// 新凤鸣累计
	private Integer addUpYX = 0;// 营销累计
	private Integer addUpPT = 0;// 平台累计
	private Integer addUpOther = 0;// 其他累计
	private Integer addUpCount = 0;// 合计
	private String year;// 年份
	private Integer month;// 月份:-2昨日,-1本周,0上周,1一月份,2二月份,....
	private Integer times;// 类型:1.昨日,2.当周,3.上周,4,月份
	private String insertTime;
	private String monthName;
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
	public Integer getAddUpSH() {
		return addUpSH;
	}
	public void setAddUpSH(Integer addUpSH) {
		this.addUpSH = addUpSH;
	}
	public Integer getAddUpXFM() {
		return addUpXFM;
	}
	public void setAddUpXFM(Integer addUpXFM) {
		this.addUpXFM = addUpXFM;
	}
	public Integer getAddUpYX() {
		return addUpYX;
	}
	public void setAddUpYX(Integer addUpYX) {
		this.addUpYX = addUpYX;
	}
	public Integer getAddUpPT() {
		return addUpPT;
	}
	public void setAddUpPT(Integer addUpPT) {
		this.addUpPT = addUpPT;
	}
	public Integer getAddUpOther() {
		return addUpOther;
	}
	public void setAddUpOther(Integer addUpOther) {
		this.addUpOther = addUpOther;
	}
	public Integer getAddUpCount() {
		return addUpCount;
	}
	public void setAddUpCount(Integer addUpCount) {
		this.addUpCount = addUpCount;
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
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	
	
}
