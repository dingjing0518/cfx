package cn.cf.entity;

import javax.persistence.Id;

/**
 * 
 * @author xht
 *
 * 2018年10月17日
 */
public class MemberDataReport{
	@Id
	private String id;
	private String date;
	/**
	 * 新注册采购商数
	 */
	private Integer newPurNum;
	/**
	 * PC端登陆采购商数
	 */
	private Integer pcLoginPurNum;
	/**
	 * WAP端登录采购商数
	 */
	private Integer wapLoginPurNum;
	/**
	 * 审核通过采购商数
	 */
	private Integer passPurNum;
	/**
	 * 待审核采购商数
	 */
	private Integer auditPurNum;
	/**
	 * 累计注册采购商数
	 */
	private Integer accumPurNum;
	/**
	 * 累计审核通过采购商数
	 */
	private Integer accumPassPurNum;
	/**
	 * 新注册供应商数
	 */
	private Integer newSupNum;
	/**
	 * 审核通过供应商数
	 */
	private Integer passSupNum;
	/**
	 * 待审核供应商数
	 */
	private Integer auditSupNum;
	/**
	 * 登录供应商数
	 */
	private Integer loginSupNum;
	/**
	 * WAP端登录供应商数
	 */
	private Integer wapLoginSupNum;
	/**
	 * 累计注册供应商数
	 */
	private Integer accumSupNum;
	/**
	 * 累计审核通过供应商数
	 */
	private Integer accumPassSupNum;
	/**
	 * 化纤白条申请数（次数）
	 */
	private Integer btApplyNum;
	/**
	 * 已授信白条数
	 */
	private Integer btCreditNum;
	/**
	 * 待审核白条数
	 */
	private Integer btAuditNum;
	/**
	 * 累计申请白条数（客户数）
	 */
	private Integer btAccumApplyNum;
	/**
	 * 累计授信白条数
	 */
	private Integer btAccumCreditNum;
	/**
	 * 化纤贷申请数（次数）
	 */
	private Integer dApplyNum;
	/**
	 * 已授信化纤贷数
	 */
	private Integer dCreditNum;
	/**
	 * 待审核化纤贷数
	 */
	private Integer dAuditNum;
	/**
	 * 累计申请化纤贷数（客户数）
	 */
	private Integer dAccumApplyNum;
	/**
	 * 累计授信化纤贷数
	 */
	private Integer dAccumCreditNum;

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getNewPurNum() {
		return newPurNum;
	}
	public void setNewPurNum(Integer newPurNum) {
		this.newPurNum = newPurNum;
	}
	public Integer getPcLoginPurNum() {
		return pcLoginPurNum;
	}
	public void setPcLoginPurNum(Integer pcLoginPurNum) {
		this.pcLoginPurNum = pcLoginPurNum;
	}
	public Integer getWapLoginPurNum() {
		return wapLoginPurNum;
	}
	public void setWapLoginPurNum(Integer wapLoginPurNum) {
		this.wapLoginPurNum = wapLoginPurNum;
	}
	public Integer getPassPurNum() {
		return passPurNum;
	}
	public void setPassPurNum(Integer passPurNum) {
		this.passPurNum = passPurNum;
	}
	
	public Integer getLoginSupNum() {
		return loginSupNum;
	}
	public void setLoginSupNum(Integer loginSupNum) {
		this.loginSupNum = loginSupNum;
	}
	public Integer getWapLoginSupNum() {
		return wapLoginSupNum;
	}
	public void setWapLoginSupNum(Integer wapLoginSupNum) {
		this.wapLoginSupNum = wapLoginSupNum;
	}
	public Integer getBtApplyNum() {
		return btApplyNum;
	}
	public void setBtApplyNum(Integer btApplyNum) {
		this.btApplyNum = btApplyNum;
	}
	public Integer getBtCreditNum() {
		return btCreditNum;
	}
	public void setBtCreditNum(Integer btCreditNum) {
		this.btCreditNum = btCreditNum;
	}
	public Integer getBtAuditNum() {
		return btAuditNum;
	}
	public void setBtAuditNum(Integer btAuditNum) {
		this.btAuditNum = btAuditNum;
	}
	public Integer getdApplyNum() {
		return dApplyNum;
	}
	public void setdApplyNum(Integer dApplyNum) {
		this.dApplyNum = dApplyNum;
	}
	public Integer getdCreditNum() {
		return dCreditNum;
	}
	public void setdCreditNum(Integer dCreditNum) {
		this.dCreditNum = dCreditNum;
	}
	public Integer getdAuditNum() {
		return dAuditNum;
	}
	public void setdAuditNum(Integer dAuditNum) {
		this.dAuditNum = dAuditNum;
	}
	
	public Integer getAccumPassPurNum() {
		return accumPassPurNum;
	}
	public void setAccumPassPurNum(Integer accumPassPurNum) {
		this.accumPassPurNum = accumPassPurNum;
	}

	public Integer getAuditPurNum() {
		return auditPurNum;
	}
	public void setAuditPurNum(Integer auditPurNum) {
		this.auditPurNum = auditPurNum;
	}
	public Integer getAccumPurNum() {
		return accumPurNum;
	}
	public void setAccumPurNum(Integer accumPurNum) {
		this.accumPurNum = accumPurNum;
	}
	public Integer getNewSupNum() {
		return newSupNum;
	}
	public void setNewSupNum(Integer newSupNum) {
		this.newSupNum = newSupNum;
	}
	public Integer getPassSupNum() {
		return passSupNum;
	}
	public void setPassSupNum(Integer passSupNum) {
		this.passSupNum = passSupNum;
	}
	public Integer getAuditSupNum() {
		return auditSupNum;
	}
	public void setAuditSupNum(Integer auditSupNum) {
		this.auditSupNum = auditSupNum;
	}
	public Integer getAccumSupNum() {
		return accumSupNum;
	}
	public void setAccumSupNum(Integer accumSupNum) {
		this.accumSupNum = accumSupNum;
	}
	public Integer getAccumPassSupNum() {
		return accumPassSupNum;
	}
	public void setAccumPassSupNum(Integer accumPassSupNum) {
		this.accumPassSupNum = accumPassSupNum;
	}
	public Integer getBtAccumApplyNum() {
		return btAccumApplyNum;
	}
	public void setBtAccumApplyNum(Integer btAccumApplyNum) {
		this.btAccumApplyNum = btAccumApplyNum;
	}
	public Integer getBtAccumCreditNum() {
		return btAccumCreditNum;
	}
	public void setBtAccumCreditNum(Integer btAccumCreditNum) {
		this.btAccumCreditNum = btAccumCreditNum;
	}
	public Integer getdAccumApplyNum() {
		return dAccumApplyNum;
	}
	public void setdAccumApplyNum(Integer dAccumApplyNum) {
		this.dAccumApplyNum = dAccumApplyNum;
	}
	public Integer getdAccumCreditNum() {
		return dAccumCreditNum;
	}
	public void setdAccumCreditNum(Integer dAccumCreditNum) {
		this.dAccumCreditNum = dAccumCreditNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
