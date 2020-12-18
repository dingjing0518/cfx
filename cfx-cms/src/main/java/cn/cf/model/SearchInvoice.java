package cn.cf.model;

public class SearchInvoice {
	
	//供应商
	private String orderPk;// 订单编号
	private String supplierInvoiceStatus;//供应商开票状态
	private String applicationStartTime;//申请时间
	private String applicationEndTime;
	private String logisticsCompanyName;//公司抬头
	
	//平台用户
	private String invoiceName;// 公司抬头
	private String invoiceRegPhone;//联系方式
	private String invoiceStartTime;
	private String invoiceEndTime;
	private String applicateStartTime;//申请时间
	private String applicateEndTime;
	private Integer memberInvoiceStatus;
	
	
	//结算
	private String logisticsCompanyContactsTel;
	private Integer isSettleFreight;// 订单物流结算状态
	private String placeOrderStartTime;// 订单下单时间
	private String placeOrderEndTime;// 订单下单时间
	private Integer isAbnormal;// 是否异常 1正常 2异常
	private Integer orderStatus;
	private Integer tempStatus;
	
	private String accountPk;
	
	
	public String getAccountPk() {
        return accountPk;
    }

    public void setAccountPk(String accountPk) {
        this.accountPk = accountPk;
    }

    public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}


	public String getApplicationStartTime() {
		return applicationStartTime;
	}

	public void setApplicationStartTime(String applicationStartTime) {
		this.applicationStartTime = applicationStartTime;
	}

	public String getApplicationEndTime() {
		return applicationEndTime;
	}

	public void setApplicationEndTime(String applicationEndTime) {
		this.applicationEndTime = applicationEndTime;
	}

	public String getInvoiceRegPhone() {
		return invoiceRegPhone;
	}

	public void setInvoiceRegPhone(String invoiceRegPhone) {
		this.invoiceRegPhone = invoiceRegPhone;
	}

	public String getInvoiceStartTime() {
		return invoiceStartTime;
	}

	public void setInvoiceStartTime(String invoiceStartTime) {
		this.invoiceStartTime = invoiceStartTime;
	}

	public String getInvoiceEndTime() {
		return invoiceEndTime;
	}

	public void setInvoiceEndTime(String invoiceEndTime) {
		this.invoiceEndTime = invoiceEndTime;
	}

	public String getOrderPk() {
		return orderPk;
	}

	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}

	public Integer getIsSettleFreight() {
		return isSettleFreight;
	}

	public void setIsSettleFreight(Integer isSettleFreight) {
		this.isSettleFreight = isSettleFreight;
	}

	public String getPlaceOrderStartTime() {
		return placeOrderStartTime;
	}

	public void setPlaceOrderStartTime(String placeOrderStartTime) {
		this.placeOrderStartTime = placeOrderStartTime;
	}

	public String getPlaceOrderEndTime() {
		return placeOrderEndTime;
	}

	public void setPlaceOrderEndTime(String placeOrderEndTime) {
		this.placeOrderEndTime = placeOrderEndTime;
	}

	public Integer getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(Integer isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSupplierInvoiceStatus() {
		return supplierInvoiceStatus;
	}

	public void setSupplierInvoiceStatus(String supplierInvoiceStatus) {
		this.supplierInvoiceStatus = supplierInvoiceStatus;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Integer getMemberInvoiceStatus() {
		return memberInvoiceStatus;
	}

	public void setMemberInvoiceStatus(Integer memberInvoiceStatus) {
		this.memberInvoiceStatus = memberInvoiceStatus;
	}

	public String getApplicateStartTime() {
		return applicateStartTime;
	}

	public void setApplicateStartTime(String applicateStartTime) {
		this.applicateStartTime = applicateStartTime;
	}

	public String getApplicateEndTime() {
		return applicateEndTime;
	}

	public void setApplicateEndTime(String applicateEndTime) {
		this.applicateEndTime = applicateEndTime;
	}

	public String getLogisticsCompanyContactsTel() {
		return logisticsCompanyContactsTel;
	}

	public void setLogisticsCompanyContactsTel(String logisticsCompanyContactsTel) {
		this.logisticsCompanyContactsTel = logisticsCompanyContactsTel;
	}

	public Integer getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(Integer tempStatus) {
		this.tempStatus = tempStatus;
	}

	
}
