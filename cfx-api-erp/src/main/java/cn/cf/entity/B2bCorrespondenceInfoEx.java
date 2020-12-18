package cn.cf.entity;

import java.util.List;

public class B2bCorrespondenceInfoEx {

	private String id;

	private String name;
	private String storePk;
	private String organizationCode;
	
	private List<ErpCorrespondenceInfo> correspondenceInfo;

	

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public List<ErpCorrespondenceInfo> getCorrespondenceInfo() {
		return correspondenceInfo;
	}

	public void setCorrespondenceInfo(List<ErpCorrespondenceInfo> correspondenceInfo) {
		this.correspondenceInfo = correspondenceInfo;
	}
	
	
	
	
	
}
