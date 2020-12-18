package cn.cf.model;

public class LogisticsRouteModel  implements java.io.Serializable{

    private static final long serialVersionUID = 5454155825314635342L;
    private String companyPk;
    private String fromAddress;
    private String toAddress;
    private String status;
    private String fromUpdateTime;
    private String toUpdateTime;

    public String getCompanyPk() {
        return companyPk;
    }
    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFromUpdateTime() {
        return fromUpdateTime;
    }
    public void setFromUpdateTime(String fromUpdateTime) {
        this.fromUpdateTime = fromUpdateTime;
    }
    public String getToUpdateTime() {
        return toUpdateTime;
    }
    public void setToUpdateTime(String toUpdateTime) {
        this.toUpdateTime = toUpdateTime;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
