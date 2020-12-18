package cn.cf.entity;

/**
 * 银行审批客户数每种来源每月月份合计;
 * 
 * @author hxh
 *
 */
public class EconCustBankMonthSumRF {

	// 化纤白条
	private Integer blankSHMonthCount = 0;// 盛虹每月合计
	private Integer blankXFMMonthCount = 0;// 新凤鸣每月合计
	private Integer blankYXMonthCount = 0;// 营销每月合计
	private Integer blankPTMonthCount = 0;// 平台每月合计
	private Integer blankOtherMonthCount = 0;// 其他每月合计
	private Integer blankMonthAllCount = 0;// 每月合计总计
	// 化纤贷
	private Integer loanSHMonthCount = 0;// 盛虹每月合计
	private Integer loanXFMMonthCount = 0;// 新凤鸣每月合计
	private Integer loanYXMonthCount = 0;// 营销每月合计
	private Integer loanPTMonthCount = 0;// 平台每月合计
	private Integer loanOtherMonthCount = 0;// 其他每月合计
	private Integer loanMonthAllCount = 0;// 每月合计总计
	// 化纤白条+化纤贷
	private Integer blaLoSHMonthCount = 0;// 盛虹每月合计
	private Integer blaLoXFMMonthCount = 0;// 新凤鸣每月合计
	private Integer blaLoYXMonthCount = 0;// 营销每月合计
	private Integer blaLoPTMonthCount = 0;// 平台每月合计
	private Integer blaLoOtherMonthCount = 0;// 其他每月合计
	private Integer blaLoMonthAllCount = 0;// 每月合计总计

	// 累计
	private Integer blankAddUpMonthSH = 0;// 盛虹累计
	private Integer blankAddUpMonthXFM = 0;// 新凤鸣累计
	private Integer blankAddUpMonthYX = 0;// 营销累计
	private Integer blankAddUpMonthPT = 0;// 平台累计
	private Integer blankAddUpMonthOther = 0;// 其他累计

	private Integer loanAddUpMonthSH = 0;// 盛虹累计
	private Integer loanAddUpMonthXFM = 0;// 新凤鸣累计
	private Integer loanAddUpMonthYX = 0;// 营销累计
	private Integer loanAddUpMonthPT = 0;// 平台累计
	private Integer loanAddUpMonthOther = 0;// 其他累计

	private Integer blaLoAddUpMonthSH = 0;// 盛虹累计
	private Integer blaLoAddUpMonthXFM = 0;// 新凤鸣累计
	private Integer blaLoAddUpMonthYX = 0;// 营销累计
	private Integer blaLoAddUpMonthPT = 0;// 平台累计
	private Integer blaLoAddUMonthpOther = 0;// 其他累计

	private Integer blankAddUpMonthCount = 0;// 累计合计
	private Integer loanAddUpMonthCount = 0;// 累计合计
	private Integer blaLoAddUpMonthCount = 0;// 累计合计
	private String name;// 合计名称

	public Integer getBlankSHMonthCount() {
		return blankSHMonthCount;
	}

	public void setBlankSHMonthCount(Integer blankSHMonthCount) {
		this.blankSHMonthCount = blankSHMonthCount;
	}

	public Integer getBlankXFMMonthCount() {
		return blankXFMMonthCount;
	}

	public void setBlankXFMMonthCount(Integer blankXFMMonthCount) {
		this.blankXFMMonthCount = blankXFMMonthCount;
	}

	public Integer getBlankYXMonthCount() {
		return blankYXMonthCount;
	}

	public void setBlankYXMonthCount(Integer blankYXMonthCount) {
		this.blankYXMonthCount = blankYXMonthCount;
	}

	public Integer getBlankPTMonthCount() {
		return blankPTMonthCount;
	}

	public void setBlankPTMonthCount(Integer blankPTMonthCount) {
		this.blankPTMonthCount = blankPTMonthCount;
	}

	public Integer getBlankOtherMonthCount() {
		return blankOtherMonthCount;
	}

	public void setBlankOtherMonthCount(Integer blankOtherMonthCount) {
		this.blankOtherMonthCount = blankOtherMonthCount;
	}

	public Integer getLoanSHMonthCount() {
		return loanSHMonthCount;
	}

	public void setLoanSHMonthCount(Integer loanSHMonthCount) {
		this.loanSHMonthCount = loanSHMonthCount;
	}

	public Integer getLoanXFMMonthCount() {
		return loanXFMMonthCount;
	}

	public void setLoanXFMMonthCount(Integer loanXFMMonthCount) {
		this.loanXFMMonthCount = loanXFMMonthCount;
	}

	public Integer getLoanYXMonthCount() {
		return loanYXMonthCount;
	}

	public void setLoanYXMonthCount(Integer loanYXMonthCount) {
		this.loanYXMonthCount = loanYXMonthCount;
	}

	public Integer getLoanPTMonthCount() {
		return loanPTMonthCount;
	}

	public void setLoanPTMonthCount(Integer loanPTMonthCount) {
		this.loanPTMonthCount = loanPTMonthCount;
	}

	public Integer getLoanOtherMonthCount() {
		return loanOtherMonthCount;
	}

	public void setLoanOtherMonthCount(Integer loanOtherMonthCount) {
		this.loanOtherMonthCount = loanOtherMonthCount;
	}

	public Integer getBlaLoSHMonthCount() {
		return blaLoSHMonthCount;
	}

	public void setBlaLoSHMonthCount(Integer blaLoSHMonthCount) {
		this.blaLoSHMonthCount = blaLoSHMonthCount;
	}

	public Integer getBlaLoXFMMonthCount() {
		return blaLoXFMMonthCount;
	}

	public void setBlaLoXFMMonthCount(Integer blaLoXFMMonthCount) {
		this.blaLoXFMMonthCount = blaLoXFMMonthCount;
	}

	public Integer getBlaLoYXMonthCount() {
		return blaLoYXMonthCount;
	}

	public void setBlaLoYXMonthCount(Integer blaLoYXMonthCount) {
		this.blaLoYXMonthCount = blaLoYXMonthCount;
	}

	public Integer getBlaLoPTMonthCount() {
		return blaLoPTMonthCount;
	}

	public void setBlaLoPTMonthCount(Integer blaLoPTMonthCount) {
		this.blaLoPTMonthCount = blaLoPTMonthCount;
	}

	public Integer getBlaLoOtherMonthCount() {
		return blaLoOtherMonthCount;
	}

	public void setBlaLoOtherMonthCount(Integer blaLoOtherMonthCount) {
		this.blaLoOtherMonthCount = blaLoOtherMonthCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBlankMonthAllCount() {
		return blankMonthAllCount;
	}

	public void setBlankMonthAllCount(Integer blankMonthAllCount) {
		this.blankMonthAllCount = blankMonthAllCount;
	}

	public Integer getLoanMonthAllCount() {
		return loanMonthAllCount;
	}

	public void setLoanMonthAllCount(Integer loanMonthAllCount) {
		this.loanMonthAllCount = loanMonthAllCount;
	}

	public Integer getBlaLoMonthAllCount() {
		return blaLoMonthAllCount;
	}

	public void setBlaLoMonthAllCount(Integer blaLoMonthAllCount) {
		this.blaLoMonthAllCount = blaLoMonthAllCount;
	}

	public Integer getBlankAddUpMonthSH() {
		return blankAddUpMonthSH;
	}

	public void setBlankAddUpMonthSH(Integer blankAddUpMonthSH) {
		this.blankAddUpMonthSH = blankAddUpMonthSH;
	}

	public Integer getBlankAddUpMonthXFM() {
		return blankAddUpMonthXFM;
	}

	public void setBlankAddUpMonthXFM(Integer blankAddUpMonthXFM) {
		this.blankAddUpMonthXFM = blankAddUpMonthXFM;
	}

	public Integer getBlankAddUpMonthYX() {
		return blankAddUpMonthYX;
	}

	public void setBlankAddUpMonthYX(Integer blankAddUpMonthYX) {
		this.blankAddUpMonthYX = blankAddUpMonthYX;
	}

	public Integer getBlankAddUpMonthPT() {
		return blankAddUpMonthPT;
	}

	public void setBlankAddUpMonthPT(Integer blankAddUpMonthPT) {
		this.blankAddUpMonthPT = blankAddUpMonthPT;
	}

	public Integer getBlankAddUpMonthOther() {
		return blankAddUpMonthOther;
	}

	public void setBlankAddUpMonthOther(Integer blankAddUpMonthOther) {
		this.blankAddUpMonthOther = blankAddUpMonthOther;
	}

	public Integer getLoanAddUpMonthSH() {
		return loanAddUpMonthSH;
	}

	public void setLoanAddUpMonthSH(Integer loanAddUpMonthSH) {
		this.loanAddUpMonthSH = loanAddUpMonthSH;
	}

	public Integer getLoanAddUpMonthXFM() {
		return loanAddUpMonthXFM;
	}

	public void setLoanAddUpMonthXFM(Integer loanAddUpMonthXFM) {
		this.loanAddUpMonthXFM = loanAddUpMonthXFM;
	}

	public Integer getLoanAddUpMonthYX() {
		return loanAddUpMonthYX;
	}

	public void setLoanAddUpMonthYX(Integer loanAddUpMonthYX) {
		this.loanAddUpMonthYX = loanAddUpMonthYX;
	}

	public Integer getLoanAddUpMonthPT() {
		return loanAddUpMonthPT;
	}

	public void setLoanAddUpMonthPT(Integer loanAddUpMonthPT) {
		this.loanAddUpMonthPT = loanAddUpMonthPT;
	}

	public Integer getLoanAddUpMonthOther() {
		return loanAddUpMonthOther;
	}

	public void setLoanAddUpMonthOther(Integer loanAddUpMonthOther) {
		this.loanAddUpMonthOther = loanAddUpMonthOther;
	}

	public Integer getBlaLoAddUpMonthSH() {
		return blaLoAddUpMonthSH;
	}

	public void setBlaLoAddUpMonthSH(Integer blaLoAddUpMonthSH) {
		this.blaLoAddUpMonthSH = blaLoAddUpMonthSH;
	}

	public Integer getBlaLoAddUpMonthXFM() {
		return blaLoAddUpMonthXFM;
	}

	public void setBlaLoAddUpMonthXFM(Integer blaLoAddUpMonthXFM) {
		this.blaLoAddUpMonthXFM = blaLoAddUpMonthXFM;
	}

	public Integer getBlaLoAddUpMonthYX() {
		return blaLoAddUpMonthYX;
	}

	public void setBlaLoAddUpMonthYX(Integer blaLoAddUpMonthYX) {
		this.blaLoAddUpMonthYX = blaLoAddUpMonthYX;
	}

	public Integer getBlaLoAddUpMonthPT() {
		return blaLoAddUpMonthPT;
	}

	public void setBlaLoAddUpMonthPT(Integer blaLoAddUpMonthPT) {
		this.blaLoAddUpMonthPT = blaLoAddUpMonthPT;
	}

	public Integer getBlaLoAddUMonthpOther() {
		return blaLoAddUMonthpOther;
	}

	public void setBlaLoAddUMonthpOther(Integer blaLoAddUMonthpOther) {
		this.blaLoAddUMonthpOther = blaLoAddUMonthpOther;
	}

	public Integer getBlankAddUpMonthCount() {
		return blankAddUpMonthCount;
	}

	public void setBlankAddUpMonthCount(Integer blankAddUpMonthCount) {
		this.blankAddUpMonthCount = blankAddUpMonthCount;
	}

	public Integer getLoanAddUpMonthCount() {
		return loanAddUpMonthCount;
	}

	public void setLoanAddUpMonthCount(Integer loanAddUpMonthCount) {
		this.loanAddUpMonthCount = loanAddUpMonthCount;
	}

	public Integer getBlaLoAddUpMonthCount() {
		return blaLoAddUpMonthCount;
	}

	public void setBlaLoAddUpMonthCount(Integer blaLoAddUpMonthCount) {
		this.blaLoAddUpMonthCount = blaLoAddUpMonthCount;
	}

}
