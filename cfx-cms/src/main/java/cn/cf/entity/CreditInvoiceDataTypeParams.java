package cn.cf.entity;

public class CreditInvoiceDataTypeParams {

    private String billingDateStart;
    private String billingDateEnd;
    private String dataType;
    private String uuid;
    private String companyPk;

    public String getBillingDateStart() {
        return billingDateStart;
    }

    public void setBillingDateStart(String billingDateStart) {
        this.billingDateStart = billingDateStart;
    }

    public String getBillingDateEnd() {
        return billingDateEnd;
    }

    public void setBillingDateEnd(String billingDateEnd) {
        this.billingDateEnd = billingDateEnd;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompanyPk() {
        return companyPk;
    }

    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }
}
