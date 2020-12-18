package cn.cf.dto;

public class B2bDimensionalityPresentExDto extends B2bDimensionalityPresentDto{

	
	private static final long serialVersionUID = 7727405339034661703L;
	
	
	private String periodTypeName;
	
	private String isVisableName;
	
	private String updateStartTime;
	
	private String updateEndTime;
	
	private Double fbShellNumberWeighting;
	
	private Double empiricalValueWeighting;
	
	private String timesDetail;
	
	private String conditionTypeName;

	private String companyNameCol;
	private String contactTelCol;

	public Double getFbShellNumberWeighting() {
		return fbShellNumberWeighting;
	}

	public void setFbShellNumberWeighting(Double fbShellNumberWeighting) {
		this.fbShellNumberWeighting = fbShellNumberWeighting;
	}

	public Double getEmpiricalValueWeighting() {
		return empiricalValueWeighting;
	}

	public void setEmpiricalValueWeighting(Double empiricalValueWeighting) {
		this.empiricalValueWeighting = empiricalValueWeighting;
	}

	public String getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public String getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public String getPeriodTypeName() {
		return periodTypeName;
	}

	public void setPeriodTypeName(String periodTypeName) {
		this.periodTypeName = periodTypeName;
	}

	public String getIsVisableName() {
		return isVisableName;
	}

	public void setIsVisableName(String isVisableName) {
		this.isVisableName = isVisableName;
	}

	public String getTimesDetail() {
		return timesDetail;
	}

	public void setTimesDetail(String timesDetail) {
		this.timesDetail = timesDetail;
	}

	public String getConditionTypeName() {
		return conditionTypeName;
	}

	public void setConditionTypeName(String conditionTypeName) {
		this.conditionTypeName = conditionTypeName;
	}

	public String getCompanyNameCol() {
		return companyNameCol;
	}

	public void setCompanyNameCol(String companyNameCol) {
		this.companyNameCol = companyNameCol;
	}

	public String getContactTelCol() {
		return contactTelCol;
	}

	public void setContactTelCol(String contactTelCol) {
		this.contactTelCol = contactTelCol;
	}
}
