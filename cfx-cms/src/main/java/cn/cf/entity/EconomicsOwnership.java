package cn.cf.entity;

import java.util.Date;

public class EconomicsOwnership {
    private String ownership;
    private Integer score;
    private Integer open;
    private Date insertTime;
    private String pk;
    private String insertStartTime;
    private String insertEndTime;
    
    
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

	public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
