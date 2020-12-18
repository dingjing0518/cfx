package cn.cf.dto;

import java.util.Date;

public class EconomicsGuaranteeTargetDto {
    private String pk;
    private String target;
    private String accountName;
    private String rateName;
    private String year;
    private Integer minAccount;
    private Integer maxAccount;
    private Integer minRate;
    private Integer maxRate;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getMinAccount() {
        return minAccount;
    }

    public void setMinAccount(Integer minAccount) {
        this.minAccount = minAccount;
    }

    public Integer getMaxAccount() {
        return maxAccount;
    }

    public void setMaxAccount(Integer maxAccount) {
        this.maxAccount = maxAccount;
    }

    public Integer getMinRate() {
        return minRate;
    }

    public void setMinRate(Integer minRate) {
        this.minRate = minRate;
    }

    public Integer getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(Integer maxRate) {
        this.maxRate = maxRate;
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
