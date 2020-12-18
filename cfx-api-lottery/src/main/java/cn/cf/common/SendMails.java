package cn.cf.common;

import javax.persistence.Id;

import cn.cf.dto.BaseDTO;

public class SendMails extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3156640441400361308L;
	@Id
	private String id;
	private String freeSignName;
	private String companyPk;
	private String companyName;
	private String memberPk;
	private String mobile;
	private String content;
	private Integer isRead;
	private String insertTime;
	 

	private String nextId;
	private String preId;
	public String getFreeSignName() {
		return freeSignName;
	}

	public void setFreeSignName(String freeSignName) {
		this.freeSignName = freeSignName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

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

	public String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	public String getPreId() {
		return preId;
	}

	public void setPreId(String preId) {
		this.preId = preId;
	}
	
}
