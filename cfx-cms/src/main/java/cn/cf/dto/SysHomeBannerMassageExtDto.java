package cn.cf.dto;

/**
 * Created by Thinkpad on 2018/5/10.
 */
public class SysHomeBannerMassageExtDto extends  SysHomeBannerMassageDto {

    private java.lang.String insertTimeStart;
    private java.lang.String insertTimeEnd;
    private java.lang.String productName;
    private String[] urlObj;
    private java.lang.Integer updateStatus;



    public String getInsertTimeStart() {
        return insertTimeStart;
    }

    public void setInsertTimeStart(String insertTimeStart) {
        this.insertTimeStart = insertTimeStart;
    }

    public String getInsertTimeEnd() {
        return insertTimeEnd;
    }

    public void setInsertTimeEnd(String insertTimeEnd) {
        this.insertTimeEnd = insertTimeEnd;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String[] getUrlObj() {
        return urlObj;
    }

    public void setUrlObj(String[] urlObj) {
        this.urlObj = urlObj;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }
}
