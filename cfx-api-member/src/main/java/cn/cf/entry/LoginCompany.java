package cn.cf.entry;

import org.springframework.data.annotation.Id;

public class LoginCompany {

	@Id
	private String id;
	
	private String loginTime;//yyyy-MM-dd
	
	private String companyPk;
	
	private int auditStatus;//采购商审核状态
	
	private int auditSpStatus;//供应商审核状态
	
	private int source;//登录来源(1 pc 2 wap)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getAuditSpStatus() {
		return auditSpStatus;
	}

	public void setAuditSpStatus(int auditSpStatus) {
		this.auditSpStatus = auditSpStatus;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
	
	
}
