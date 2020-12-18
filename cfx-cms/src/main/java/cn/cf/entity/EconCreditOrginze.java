package cn.cf.entity;

import java.util.Date;

public class EconCreditOrginze {
	
	private String pk;
    private Integer minCount ;
    private Integer maxCount ;
  
    //得分
    private Integer score;
    //状态
    private Integer open;
    private Date insertTime;
    private String countName;
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
	public String getCountName() {
		return countName;
	}
	public void setCountName(String countName) {
		this.countName = countName;
	}
	

}
