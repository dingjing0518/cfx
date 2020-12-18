package cn.cf.dto;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCreditInvoiceExtDto extends B2bCreditInvoiceDto{


    private String billingDateStart;
    private String billingDateEnd;
    private String stateName;
    private String dataTypeName;
    private String invoiceTypeName;
    private String creditPk;

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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getInvoiceTypeName() {
        return invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }

    public String getCreditPk() {
        return creditPk;
    }

    public void setCreditPk(String creditPk) {
        this.creditPk = creditPk;
    }
}