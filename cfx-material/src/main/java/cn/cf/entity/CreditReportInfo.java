package cn.cf.entity;

import javax.persistence.Id;

public class CreditReportInfo {

	@Id
	private String id;
	
	private String companyPk;
	
	private String companyName;
	
	private String detail;
	
	private String shotName;
	
	private String fileUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getShotName() {
		return shotName;
	}

	public void setShotName(String shotName) {
		this.shotName = shotName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
	
	
	
}
