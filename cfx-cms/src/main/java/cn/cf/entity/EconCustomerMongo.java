package cn.cf.entity;

import java.util.Date;

import javax.persistence.Id;

public class EconCustomerMongo {

	@Id
	private String id;
	private String pk;
	private String companyPk;
	private String companyName;
	private String contacts;
	private String contactsTel;
	private Date insertTime;
	private Integer auditStatus;
	private Integer source;
	private String assidirPk;
	private String assidirName;
	private String bankPk;
	private String bankName;
	private Integer score;
	private String productPks;
	private String content;// 建议额度
	private String econDatumPk;
	private String datumContent;
	private String remarks;
	private String rule;
	private Date 	approveTime;
	private String processInstanceId;
	private String productName;
	private  B2bEconomicsImprovingdataEntity improvingdataDto;

	private Date approvalTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getDatumContent() {
		return datumContent;
	}

	public void setDatumContent(String datumContent) {
		this.datumContent = datumContent;
	}

	public String getEconDatumPk() {
		return econDatumPk;
	}

	public void setEconDatumPk(String econDatumPk) {
		this.econDatumPk = econDatumPk;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getAssidirPk() {
		return assidirPk;
	}

	public void setAssidirPk(String assidirPk) {
		this.assidirPk = assidirPk;
	}

	public String getAssidirName() {
		return assidirName;
	}

	public void setAssidirName(String assidirName) {
		this.assidirName = assidirName;
	}

	public String getBankPk() {
		return bankPk;
	}

	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProductPks() {
		return productPks;
	}

	public void setProductPks(String productPks) {
		this.productPks = productPks;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public B2bEconomicsImprovingdataEntity getImprovingdataDto() {
		return improvingdataDto;
	}

	public void setImprovingdataDto(B2bEconomicsImprovingdataEntity improvingdataDto) {
		this.improvingdataDto = improvingdataDto;
	}

	

	
}
