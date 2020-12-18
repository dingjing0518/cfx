package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCreditInvoice extends BaseModel  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String invoiceCode;
	private java.lang.String invoiceNumber;
	private java.util.Date billingDate;
	private java.lang.Double totalAmount;
	private java.lang.Double taxRate;
	private java.lang.Double totalTax;
	private java.lang.Integer dataType;
	private java.lang.String invoiceType;
	private java.lang.Integer state;
	private java.lang.String companyPk;
	private java.lang.String purchaserTaxNo;
	private java.lang.String purchaserName;
	private java.lang.String salesTaxNo;
	private java.lang.String salesTaxName;
	private java.lang.String commodityName;
	private java.lang.Double quantity;
	private java.lang.Double unitPrice;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setInvoiceCode(java.lang.String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	public java.lang.String getInvoiceCode() {
		return this.invoiceCode;
	}
	public void setInvoiceNumber(java.lang.String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	public java.lang.String getInvoiceNumber() {
		return this.invoiceNumber;
	}
	public void setBillingDate(java.util.Date billingDate) {
		this.billingDate = billingDate;
	}
	
	public java.util.Date getBillingDate() {
		return this.billingDate;
	}
	public void setTotalAmount(java.lang.Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public java.lang.Double getTotalAmount() {
		return this.totalAmount;
	}
	public void setTaxRate(java.lang.Double taxRate) {
		this.taxRate = taxRate;
	}
	
	public java.lang.Double getTaxRate() {
		return this.taxRate;
	}
	public void setTotalTax(java.lang.Double totalTax) {
		this.totalTax = totalTax;
	}
	
	public java.lang.Double getTotalTax() {
		return this.totalTax;
	}
	
	public java.lang.Integer getDataType() {
		return dataType;
	}

	public void setDataType(java.lang.Integer dataType) {
		this.dataType = dataType;
	}

	public void setInvoiceType(java.lang.String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	public java.lang.String getInvoiceType() {
		return this.invoiceType;
	}
	public void setState(java.lang.Integer state) {
		this.state = state;
	}
	
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public java.lang.String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}

	public void setPurchaserTaxNo(java.lang.String purchaserTaxNo) {
		this.purchaserTaxNo = purchaserTaxNo;
	}
	
	public java.lang.String getPurchaserTaxNo() {
		return this.purchaserTaxNo;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setSalesTaxNo(java.lang.String salesTaxNo) {
		this.salesTaxNo = salesTaxNo;
	}
	
	public java.lang.String getSalesTaxNo() {
		return this.salesTaxNo;
	}
	public void setSalesTaxName(java.lang.String salesTaxName) {
		this.salesTaxName = salesTaxName;
	}
	
	public java.lang.String getSalesTaxName() {
		return this.salesTaxName;
	}
	public void setCommodityName(java.lang.String commodityName) {
		this.commodityName = commodityName;
	}
	
	public java.lang.String getCommodityName() {
		return this.commodityName;
	}
	
	public java.lang.Double getQuantity() {
		return quantity;
	}

	public void setQuantity(java.lang.Double quantity) {
		this.quantity = quantity;
	}

	public void setUnitPrice(java.lang.Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public java.lang.Double getUnitPrice() {
		return this.unitPrice;
	}
	

}