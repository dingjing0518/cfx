package cn.cf.dto;

import java.util.Date;

public class EconomicsGuaranteeCountDto {

    private String pk;
    private String name;
    private Integer minNumber;
    private Integer maxNumber;
    private Date insertTime;
    private Integer open;
    private Integer score;
    private String insertStartTime;
    private String insertEndTime;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(Integer minNumber) {
        this.minNumber = minNumber;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
