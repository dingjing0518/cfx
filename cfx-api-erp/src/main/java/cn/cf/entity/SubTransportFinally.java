package cn.cf.entity;

import java.util.Date;

public class SubTransportFinally {

	private Integer status;
	private  Date stepTime;
	private String distinctNumber;
	private String gradeName;
	private String packageNumber;
	private String batchNumber;
	private Double weight;
	private Integer boxes;
	private String  ExceptionInfo;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStepTime() {
		return stepTime;
	}
	public void setStepTime(Date stepTime) {
		this.stepTime = stepTime;
	}
	public String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getPackageNumber() {
		return packageNumber;
	}
	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}
	public String getExceptionInfo() {
		return ExceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		ExceptionInfo = exceptionInfo;
	}
	
	
}
