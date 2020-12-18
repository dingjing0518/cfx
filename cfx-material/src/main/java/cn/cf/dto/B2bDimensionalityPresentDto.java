package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bDimensionalityPresentDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String rewardPk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String contactsTel;
	private java.lang.Integer dimenCategory;
	private java.lang.String dimenName;
	private java.lang.Integer dimenType;
	private java.lang.String dimenTypeName;
	private java.lang.Integer periodType;
	private java.lang.Integer fibreShellNumber;
	private java.lang.Integer empiricalValue;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String updateStartTime;
	private java.lang.String updateEndTime;
	private java.lang.Double fibreShellRatio;
	private java.lang.Double empiricalRatio;
	private java.lang.Double fbGradeRatio;
	private java.lang.Double emGradeRatio;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private String orderNumber;
	private Integer type;
	private Double fbShellNumberWeighting;
	private Double empiricalValueWeighting;
	
	
	
	//columns END

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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getFibreShellRatio() {
		return fibreShellRatio;
	}

	public java.lang.Double getFbGradeRatio() {
		return fbGradeRatio;
	}

	public void setFbGradeRatio(java.lang.Double fbGradeRatio) {
		this.fbGradeRatio = fbGradeRatio;
	}

	public java.lang.Double getEmGradeRatio() {
		return emGradeRatio;
	}

	public void setEmGradeRatio(java.lang.Double emGradeRatio) {
		this.emGradeRatio = emGradeRatio;
	}

	public java.lang.String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}

	public java.lang.String getMemberName() {
		return memberName;
	}

	public void setMemberName(java.lang.String memberName) {
		this.memberName = memberName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setFibreShellRatio(Double fibreShellRatio) {
		this.fibreShellRatio = fibreShellRatio;
	}

	public Double getEmpiricalRatio() {
		return empiricalRatio;
	}

	public void setEmpiricalRatio(Double empiricalRatio) {
		this.empiricalRatio = empiricalRatio;
	}

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setRewardPk(java.lang.String rewardPk) {
		this.rewardPk = rewardPk;
	}
	
	public java.lang.String getRewardPk() {
		return this.rewardPk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	
	public java.lang.String getCompanyName() {
		return this.companyName;
	}
	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}
	
	public java.lang.String getContactsTel() {
		return this.contactsTel;
	}
	public void setDimenCategory(java.lang.Integer dimenCategory) {
		this.dimenCategory = dimenCategory;
	}
	
	public java.lang.Integer getDimenCategory() {
		return this.dimenCategory;
	}
	public void setDimenName(java.lang.String dimenName) {
		this.dimenName = dimenName;
	}
	
	public java.lang.String getDimenName() {
		return this.dimenName;
	}
	public void setDimenType(java.lang.Integer dimenType) {
		this.dimenType = dimenType;
	}
	
	public java.lang.Integer getDimenType() {
		return this.dimenType;
	}
	public void setDimenTypeName(java.lang.String dimenTypeName) {
		this.dimenTypeName = dimenTypeName;
	}
	
	public java.lang.String getDimenTypeName() {
		return this.dimenTypeName;
	}
	public void setPeriodType(java.lang.Integer periodType) {
		this.periodType = periodType;
	}
	
	public java.lang.Integer getPeriodType() {
		return this.periodType;
	}
	public void setFibreShellNumber(java.lang.Integer fibreShellNumber) {
		this.fibreShellNumber = fibreShellNumber;
	}
	
	public java.lang.Integer getFibreShellNumber() {
		return this.fibreShellNumber;
	}
	public void setEmpiricalValue(java.lang.Integer empiricalValue) {
		this.empiricalValue = empiricalValue;
	}
	
	public java.lang.Integer getEmpiricalValue() {
		return this.empiricalValue;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public java.lang.String getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(java.lang.String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public java.lang.String getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(java.lang.String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}
	

}