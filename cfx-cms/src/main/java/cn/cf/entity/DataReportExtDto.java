package cn.cf.entity;

import cn.cf.dto.DataReportDto;

public class DataReportExtDto extends DataReportDto {


    private String profitPresentFreight;

    private String profitOriginalFreight;

    private String invoiceName;

   
       public String getProfitPresentFreight() {
        return profitPresentFreight;
    }

    public void setProfitPresentFreight(String profitPresentFreight) {
        this.profitPresentFreight = profitPresentFreight;
    }

    public String getProfitOriginalFreight() {
        return profitOriginalFreight;
    }

    public void setProfitOriginalFreight(String profitOriginalFreight) {
        this.profitOriginalFreight = profitOriginalFreight;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }



}
