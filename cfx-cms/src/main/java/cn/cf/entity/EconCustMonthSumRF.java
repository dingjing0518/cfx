package cn.cf.entity;

/**
 * 客户申请金融产品每种来源每月月份合计;
 * 
 * @author hxh
 *
 */
public class EconCustMonthSumRF {
	
	// 化纤白条	
	private Integer blankSHMonthCount = 0;//盛虹每月合计
	private Integer blankXFMMonthCount = 0;//新凤鸣每月合计
	private Integer blankYXMonthCount = 0;//营销每月合计
	private Integer blankPTMonthCount = 0;//平台每月合计
	private Integer blankOtherMonthCount = 0;//其他每月合计
	private Integer blankMonthAll = 0;//每月所有来源合计
	// 化纤贷
	private Integer loanSHMonthCount = 0;//盛虹每月合计
	private Integer loanXFMMonthCount = 0;//新凤鸣每月合计
	private Integer loanYXMonthCount = 0;//营销每月合计
	private Integer loanPTMonthCount = 0;//平台每月合计
	private Integer loanOtherMonthCount = 0;//其他每月合计
	private Integer loanMonthAll = 0;//每月所有来源合计
	// 化纤白条+化纤贷
	private Integer blaLoSHMonthCount = 0;//盛虹每月合计
	private Integer blaLoXFMMonthCount = 0;//新凤鸣每月合计
	private Integer blaLoYXMonthCount = 0;//营销每月合计
	private Integer blaLoPTMonthCount = 0;//平台每月合计
	private Integer blaLoOtherMonthCount = 0;//其他每月合计
	private Integer blaLoMonthAll = 0;//每月所有来源合计
	
	//累计合计
	private Integer addUpSHMonthCount = 0;//盛虹每月累计合计
	private Integer addUpXFMMonthCount = 0;//新凤鸣每月累计合计
	private Integer addUpYXMonthCount = 0;//营销每月累计合计
	private Integer addUpPTMonthCount = 0;//平台每月累计合计
	private Integer addUpOtherMonthCount = 0;//其他每月累计合计
	private Integer addUpMonthAll = 0;//每月所有来源累计合计
	
	private String name;//合计
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
	public Integer getBlankMonthAll() {
		return blankMonthAll;
	}
	public void setBlankMonthAll(Integer blankMonthAll) {
		this.blankMonthAll = blankMonthAll;
	}
	public Integer getLoanMonthAll() {
		return loanMonthAll;
	}
	public void setLoanMonthAll(Integer loanMonthAll) {
		this.loanMonthAll = loanMonthAll;
	}
	public Integer getBlaLoMonthAll() {
		return blaLoMonthAll;
	}
	public void setBlaLoMonthAll(Integer blaLoMonthAll) {
		this.blaLoMonthAll = blaLoMonthAll;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAddUpSHMonthCount() {
		return addUpSHMonthCount;
	}
	public void setAddUpSHMonthCount(Integer addUpSHMonthCount) {
		this.addUpSHMonthCount = addUpSHMonthCount;
	}
	public Integer getAddUpXFMMonthCount() {
		return addUpXFMMonthCount;
	}
	public void setAddUpXFMMonthCount(Integer addUpXFMMonthCount) {
		this.addUpXFMMonthCount = addUpXFMMonthCount;
	}
	public Integer getAddUpYXMonthCount() {
		return addUpYXMonthCount;
	}
	public void setAddUpYXMonthCount(Integer addUpYXMonthCount) {
		this.addUpYXMonthCount = addUpYXMonthCount;
	}
	public Integer getAddUpPTMonthCount() {
		return addUpPTMonthCount;
	}
	public void setAddUpPTMonthCount(Integer addUpPTMonthCount) {
		this.addUpPTMonthCount = addUpPTMonthCount;
	}
	public Integer getAddUpOtherMonthCount() {
		return addUpOtherMonthCount;
	}
	public void setAddUpOtherMonthCount(Integer addUpOtherMonthCount) {
		this.addUpOtherMonthCount = addUpOtherMonthCount;
	}
	public Integer getAddUpMonthAll() {
		return addUpMonthAll;
	}
	public void setAddUpMonthAll(Integer addUpMonthAll) {
		this.addUpMonthAll = addUpMonthAll;
	}
	
	
}
