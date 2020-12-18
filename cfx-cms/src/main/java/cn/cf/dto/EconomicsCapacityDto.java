package cn.cf.dto;

import java.util.Date;

public class EconomicsCapacityDto implements java.io.Serializable{

    private static final long serialVersionUID = 5454155825314635342L;

    private String pk;
    private Integer minCapacity;
    private Integer maxCapacity;
    private boolean isOpen;
    private Date insertTime;
    private Integer score;
    private String name;
    private Date insertStartTime;
    private Date insertEndTime;


    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInsertStartTime() {
        return insertStartTime;
    }

    public void setInsertStartTime(Date insertStartTime) {
        this.insertStartTime = insertStartTime;
    }

    public Date getInsertEndTime() {
        return insertEndTime;
    }

    public void setInsertEndTime(Date insertEndTime) {
        this.insertEndTime = insertEndTime;
    }
}
