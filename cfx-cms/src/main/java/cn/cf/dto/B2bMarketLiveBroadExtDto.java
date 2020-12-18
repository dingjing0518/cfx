package cn.cf.dto;

import java.util.Date;

public class B2bMarketLiveBroadExtDto {

    private String pk;

    private String livebroadcategoryName;

    private String livebroadcategoryPk;

    private Date insertTime;

    private String content;

    private String keyword;

    private Integer isDelete;

    private Date updateTime;

    private String strStartTime;

    private String strEndTime;


    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getLivebroadcategoryName() {
        return livebroadcategoryName;
    }

    public void setLivebroadcategoryName(String livebroadcategoryName) {
        this.livebroadcategoryName = livebroadcategoryName;
    }

    public String getLivebroadcategoryPk() {
        return livebroadcategoryPk;
    }

    public void setLivebroadcategoryPk(String livebroadcategoryPk) {
        this.livebroadcategoryPk = livebroadcategoryPk;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
