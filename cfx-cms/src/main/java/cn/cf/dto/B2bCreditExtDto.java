package cn.cf.dto;


import java.util.Date;

public class B2bCreditExtDto extends B2bCreditDto {
	
	private String productNames;
	
    private String creditInsertTimeBegin;
    private String creditInsertTimeEnd;
    private String creditAuditTimeBegin;
    private String creditAuditTimeEnd;
    private Double availableAmount;
    private String searchType;
    private String creditStartTimeBegin;
    private String creditStartTimeEnd;
    private String creditEndTimeBegin;
    private String creditEndTimeEnd;
    //判断虚拟账号是否为空
    private String isNullVirtualAccountName;

    private String virtualUpdateStartTime;
    private String virtualUpdateEndTime;


    private String virtualInsertTimeBegin;
    private String virtualInsertTimeEnd;

    private String customerNumber;

    private String virtualAccount;
    private String virtualAccountName;//虚拟子账号名称
    private String virtualContactName;
    private String virtualContactPhone;
    private Integer virtualStatus;
    private Double virtualBalanceAmount;
    private Double virtualAvailableBalanceAmount;
    private String virtualPayPassword;
    private Date virtualInsertTime;
    private Date virtualUpdateTime;
    private Integer isDelete;

    private String roleName;
    private String bankName;
    private Integer showModal;

    private String accountPk;

    private String creditStatusName;

	public String getAccountPk() {
        return accountPk;
    }

    public void setAccountPk(String accountPk) {
        this.accountPk = accountPk;
    }

    public String getProductNames() {
		return productNames;
	}

	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}

	public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getIsNullVirtualAccountName() {
        return isNullVirtualAccountName;
    }

    public void setIsNullVirtualAccountName(String isNullVirtualAccountName) {
        this.isNullVirtualAccountName = isNullVirtualAccountName;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public String getVirtualAccountName() {
        return virtualAccountName;
    }

    public void setVirtualAccountName(String virtualAccountName) {
        this.virtualAccountName = virtualAccountName;
    }

    public String getVirtualContactName() {
        return virtualContactName;
    }

    public void setVirtualContactName(String virtualContactName) {
        this.virtualContactName = virtualContactName;
    }

    public String getVirtualContactPhone() {
        return virtualContactPhone;
    }

    public void setVirtualContactPhone(String virtualContactPhone) {
        this.virtualContactPhone = virtualContactPhone;
    }

    public Integer getVirtualStatus() {
        return virtualStatus;
    }

    public void setVirtualStatus(Integer virtualStatus) {
        this.virtualStatus = virtualStatus;
    }

    public Double getVirtualBalanceAmount() {
        return virtualBalanceAmount;
    }

    public void setVirtualBalanceAmount(Double virtualBalanceAmount) {
        this.virtualBalanceAmount = virtualBalanceAmount;
    }

    public Double getVirtualAvailableBalanceAmount() {
        return virtualAvailableBalanceAmount;
    }

    public void setVirtualAvailableBalanceAmount(Double virtualAvailableBalanceAmount) {
        this.virtualAvailableBalanceAmount = virtualAvailableBalanceAmount;
    }

    @Override
    public String getVirtualPayPassword() {
        return virtualPayPassword;
    }

    @Override
    public void setVirtualPayPassword(String virtualPayPassword) {
        this.virtualPayPassword = virtualPayPassword;
    }

    public Date getVirtualInsertTime() {
        return virtualInsertTime;
    }

    public void setVirtualInsertTime(Date virtualInsertTime) {
        this.virtualInsertTime = virtualInsertTime;
    }

    public Date getVirtualUpdateTime() {
        return virtualUpdateTime;
    }

    public void setVirtualUpdateTime(Date virtualUpdateTime) {
        this.virtualUpdateTime = virtualUpdateTime;
    }

    @Override
    public Integer getIsDelete() {
        return isDelete;
    }

    @Override
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreditInsertTimeBegin() {
        return creditInsertTimeBegin;
    }

    public void setCreditInsertTimeBegin(String creditInsertTimeBegin) {
        this.creditInsertTimeBegin = creditInsertTimeBegin;
    }

    public String getCreditInsertTimeEnd() {
        return creditInsertTimeEnd;
    }

    public void setCreditInsertTimeEnd(String creditInsertTimeEnd) {
        this.creditInsertTimeEnd = creditInsertTimeEnd;
    }

    public String getCreditAuditTimeBegin() {
        return creditAuditTimeBegin;
    }

    public void setCreditAuditTimeBegin(String creditAuditTimeBegin) {
        this.creditAuditTimeBegin = creditAuditTimeBegin;
    }

    public String getCreditAuditTimeEnd() {
        return creditAuditTimeEnd;
    }

    public void setCreditAuditTimeEnd(String creditAuditTimeEnd) {
        this.creditAuditTimeEnd = creditAuditTimeEnd;
    }

    public Double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getCreditStartTimeBegin() {
        return creditStartTimeBegin;
    }

    public void setCreditStartTimeBegin(String creditStartTimeBegin) {
        this.creditStartTimeBegin = creditStartTimeBegin;
    }

    public String getCreditStartTimeEnd() {
        return creditStartTimeEnd;
    }

    public void setCreditStartTimeEnd(String creditStartTimeEnd) {
        this.creditStartTimeEnd = creditStartTimeEnd;
    }

    public String getCreditEndTimeBegin() {
        return creditEndTimeBegin;
    }

    public void setCreditEndTimeBegin(String creditEndTimeBegin) {
        this.creditEndTimeBegin = creditEndTimeBegin;
    }

    public String getCreditEndTimeEnd() {
        return creditEndTimeEnd;
    }

    public void setCreditEndTimeEnd(String creditEndTimeEnd) {
        this.creditEndTimeEnd = creditEndTimeEnd;
    }

    public String getVirtualUpdateStartTime() {
        return virtualUpdateStartTime;
    }

    public void setVirtualUpdateStartTime(String virtualUpdateStartTime) {
        this.virtualUpdateStartTime = virtualUpdateStartTime;
    }

    public String getVirtualUpdateEndTime() {
        return virtualUpdateEndTime;
    }

    public void setVirtualUpdateEndTime(String virtualUpdateEndTime) {
        this.virtualUpdateEndTime = virtualUpdateEndTime;
    }

    public String getVirtualInsertTimeBegin() {
        return virtualInsertTimeBegin;
    }

    public void setVirtualInsertTimeBegin(String virtualInsertTimeBegin) {
        this.virtualInsertTimeBegin = virtualInsertTimeBegin;
    }

    public String getVirtualInsertTimeEnd() {
        return virtualInsertTimeEnd;
    }

    public void setVirtualInsertTimeEnd(String virtualInsertTimeEnd) {
        this.virtualInsertTimeEnd = virtualInsertTimeEnd;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getShowModal() {
		return showModal;
	}

	public void setShowModal(Integer showModal) {
		this.showModal = showModal;
	}

    public String getCreditStatusName() {
        return creditStatusName;
    }

    public void setCreditStatusName(String creditStatusName) {
        this.creditStatusName = creditStatusName;
    }
}
