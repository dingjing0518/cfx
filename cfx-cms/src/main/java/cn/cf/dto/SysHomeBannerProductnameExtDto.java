package cn.cf.dto;


public class SysHomeBannerProductnameExtDto extends SysHomeBannerProductnameDto {
    private java.lang.String insertTimeStart;
    private java.lang.String insertTimeEnd;
    private java.lang.Integer updateStatus;
    private java.lang.Integer linkUrl;

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

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(Integer linkUrl) {
        this.linkUrl = linkUrl;
    }
}
