package cn.cf.entity;

import java.util.Date;

public class EconomicsBank  {
    private String pk;
    private Integer minCount;
    private Integer maxCount;
    private Integer open;
    private Date insertTime;
    private Integer score;
    private String name;
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

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public Integer getMinCount() {
		return minCount;
	}

	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
