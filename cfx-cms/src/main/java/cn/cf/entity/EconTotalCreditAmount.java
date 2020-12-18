package cn.cf.entity;

import java.util.Date;

public class EconTotalCreditAmount {
	private String pk;
    private Integer minAmount;
    private Integer maxAmount;
    private Integer open;
    private Date insertTime;
    private Integer score;
    private String name;
    private String year;
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
	public Integer getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}
	public Integer getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
    

}
