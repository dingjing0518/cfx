package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillInventory extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String orderNumber;
	private java.lang.String billNumber;
	private java.lang.Integer status;
	private java.lang.Integer payStatus;
	private java.lang.Integer transfer;
	private java.lang.String billCode;
	private java.lang.Double amount;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private java.lang.String drawer;
	private java.lang.String drawerCode;
	private java.lang.String payee;
	private java.lang.String payeeCode;
	private java.lang.String acceptor;
	private java.lang.String acceptorCode;
	private java.lang.String acceptorAccount;
	private java.lang.String acceptorBankNo;
	private java.lang.Double discountRate;
	private java.lang.Double discountInterest;
	private java.lang.Double discountAmount;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setBillNumber(java.lang.String billNumber) {
		this.billNumber = billNumber;
	}
	
	public java.lang.String getBillNumber() {
		return this.billNumber;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setPayStatus(java.lang.Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	public java.lang.Integer getPayStatus() {
		return this.payStatus;
	}
	public void setTransfer(java.lang.Integer transfer) {
		this.transfer = transfer;
	}
	
	public java.lang.Integer getTransfer() {
		return this.transfer;
	}
	public void setBillCode(java.lang.String billCode) {
		this.billCode = billCode;
	}
	
	public java.lang.String getBillCode() {
		return this.billCode;
	}
	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}
	
	public java.lang.Double getAmount() {
		return this.amount;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	public void setDrawer(java.lang.String drawer) {
		this.drawer = drawer;
	}
	
	public java.lang.String getDrawer() {
		return this.drawer;
	}
	public void setDrawerCode(java.lang.String drawerCode) {
		this.drawerCode = drawerCode;
	}
	
	public java.lang.String getDrawerCode() {
		return this.drawerCode;
	}
	public void setPayee(java.lang.String payee) {
		this.payee = payee;
	}
	
	public java.lang.String getPayee() {
		return this.payee;
	}
	public void setPayeeCode(java.lang.String payeeCode) {
		this.payeeCode = payeeCode;
	}
	
	public java.lang.String getPayeeCode() {
		return this.payeeCode;
	}
	public void setAcceptor(java.lang.String acceptor) {
		this.acceptor = acceptor;
	}
	
	public java.lang.String getAcceptor() {
		return this.acceptor;
	}
	public void setAcceptorCode(java.lang.String acceptorCode) {
		this.acceptorCode = acceptorCode;
	}
	
	public java.lang.String getAcceptorCode() {
		return this.acceptorCode;
	}
	public void setAcceptorAccount(java.lang.String acceptorAccount) {
		this.acceptorAccount = acceptorAccount;
	}
	
	public java.lang.String getAcceptorAccount() {
		return this.acceptorAccount;
	}
	public void setAcceptorBankNo(java.lang.String acceptorBankNo) {
		this.acceptorBankNo = acceptorBankNo;
	}
	
	public java.lang.String getAcceptorBankNo() {
		return this.acceptorBankNo;
	}
	public void setDiscountRate(java.lang.Double discountRate) {
		this.discountRate = discountRate;
	}
	
	public java.lang.Double getDiscountRate() {
		return this.discountRate;
	}
	public void setDiscountInterest(java.lang.Double discountInterest) {
		this.discountInterest = discountInterest;
	}
	
	public java.lang.Double getDiscountInterest() {
		return this.discountInterest;
	}
	public void setDiscountAmount(java.lang.Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	public java.lang.Double getDiscountAmount() {
		return this.discountAmount;
	}
	

}