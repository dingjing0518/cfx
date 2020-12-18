package cn.cf.dto;

import java.util.Date;

public class EconomicsLimitDto {
    private String pk;
    private String category;
    private Integer limit;
    private Date insertTime;
    private Integer open;
    private String insertStartTime;
    private String insertEndTime;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getInsertStartTime() {
        return insertStartTime;
    }

    public void setInsertStartTime(String insertStartTime) {
        this.insertStartTime = insertStartTime;
    }

    public String getInsertEndTime() {
        return insertEndTime;
    }

    public void setInsertEndTime(String insertEndTime) {
        this.insertEndTime = insertEndTime;
    }
}
