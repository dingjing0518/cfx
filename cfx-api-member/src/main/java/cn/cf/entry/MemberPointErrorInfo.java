package cn.cf.entry;
/**
 * 
 * @author xht
 * @describe 会员分贝插入失败记录
 * 2018年5月22日
 */
public class MemberPointErrorInfo {
	/**
	 * 事件
	 */
	private String active;
	/**
	 * 基础值/经验
	 */
	private Double expValue;
	/**
	 * 基础值/纤贝
	 */
	private Double fiberValue;
	private String memberPk;
	private String companyPk;
	private String insertTime;
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Double getExpValue() {
		return expValue;
	}
	public void setExpValue(Double expValue) {
		this.expValue = expValue;
	}
	public Double getFiberValue() {
		return fiberValue;
	}
	public void setFiberValue(Double fiberValue) {
		this.fiberValue = fiberValue;
	}
	public String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	
	

}
