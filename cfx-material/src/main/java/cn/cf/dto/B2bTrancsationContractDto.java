package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bTrancsationContractDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String storePk;
	private java.lang.String companyPk;
	private java.lang.Double contractAmounts;
	private java.lang.Double auditAmounts;
	private java.lang.Double shipAmounts;
	private java.lang.Integer contractCounts;
	private java.lang.Integer auditCounts;
	private java.lang.Integer shipCounts;
	private java.lang.String transactionDate;
	private java.lang.Integer isDelete;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setContractAmounts(java.lang.Double contractAmounts) {
		this.contractAmounts = contractAmounts;
	}
	
	public java.lang.Double getContractAmounts() {
		return this.contractAmounts;
	}
	public void setAuditAmounts(java.lang.Double auditAmounts) {
		this.auditAmounts = auditAmounts;
	}
	
	public java.lang.Double getAuditAmounts() {
		return this.auditAmounts;
	}
	public void setShipAmounts(java.lang.Double shipAmounts) {
		this.shipAmounts = shipAmounts;
	}
	
	public java.lang.Double getShipAmounts() {
		return this.shipAmounts;
	}
	public void setContractCounts(java.lang.Integer contractCounts) {
		this.contractCounts = contractCounts;
	}
	
	public java.lang.Integer getContractCounts() {
		return this.contractCounts;
	}
	public void setAuditCounts(java.lang.Integer auditCounts) {
		this.auditCounts = auditCounts;
	}
	
	public java.lang.Integer getAuditCounts() {
		return this.auditCounts;
	}
	public void setShipCounts(java.lang.Integer shipCounts) {
		this.shipCounts = shipCounts;
	}
	
	public java.lang.Integer getShipCounts() {
		return this.shipCounts;
	}
	
	public java.lang.String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(java.lang.String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	

}