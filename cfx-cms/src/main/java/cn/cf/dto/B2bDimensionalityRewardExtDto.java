package cn.cf.dto;

public class B2bDimensionalityRewardExtDto extends B2bDimensionalityRewardDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7080097046319673004L;
	
	
	private String updateStartTime;
	
	private String updateEndTime;
	
	private String fbShellNumberWeighting;
	
	private String empiricalValueWeighting;
	
	private String selectDimen;
	
	private String selectDimenCategory;
	
	private String periodType1;
	
	

	public String getPeriodType1() {
		return periodType1;
	}

	public void setPeriodType1(String periodType1) {
		this.periodType1 = periodType1;
	}

	public String getSelectDimen() {
		return selectDimen;
	}

	public void setSelectDimen(String selectDimen) {
		this.selectDimen = selectDimen;
	}

	public String getSelectDimenCategory() {
		return selectDimenCategory;
	}

	public void setSelectDimenCategory(String selectDimenCategory) {
		this.selectDimenCategory = selectDimenCategory;
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

	public String getFbShellNumberWeighting() {
		return fbShellNumberWeighting;
	}

	public void setFbShellNumberWeighting(String fbShellNumberWeighting) {
		this.fbShellNumberWeighting = fbShellNumberWeighting;
	}

	public String getEmpiricalValueWeighting() {
		return empiricalValueWeighting;
	}

	public void setEmpiricalValueWeighting(String empiricalValueWeighting) {
		this.empiricalValueWeighting = empiricalValueWeighting;
	}
	
	
}
