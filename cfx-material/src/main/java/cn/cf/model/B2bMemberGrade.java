package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bMemberGrade  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String gradeName;
	private java.lang.Integer gradeNumber;
	private java.lang.Integer numberStart;
	private java.lang.Integer numberEnd;
	private java.lang.Double fbGradeRatio;
	private java.lang.Double emGradeRatio;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.util.Date updateTime;
	private java.util.Date insertTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setGradeName(java.lang.String gradeName) {
		this.gradeName = gradeName;
	}
	
	public java.lang.String getGradeName() {
		return this.gradeName;
	}
	public void setGradeNumber(java.lang.Integer gradeNumber) {
		this.gradeNumber = gradeNumber;
	}
	
	public java.lang.Integer getGradeNumber() {
		return this.gradeNumber;
	}
	public void setNumberStart(java.lang.Integer numberStart) {
		this.numberStart = numberStart;
	}
	
	public java.lang.Integer getNumberStart() {
		return this.numberStart;
	}
	public void setNumberEnd(java.lang.Integer numberEnd) {
		this.numberEnd = numberEnd;
	}
	
	public java.lang.Integer getNumberEnd() {
		return this.numberEnd;
	}
	public void setFbGradeRatio(java.lang.Double fbGradeRatio) {
		this.fbGradeRatio = fbGradeRatio;
	}
	
	public java.lang.Double getFbGradeRatio() {
		return this.fbGradeRatio;
	}
	public void setEmGradeRatio(java.lang.Double emGradeRatio) {
		this.emGradeRatio = emGradeRatio;
	}
	
	public java.lang.Double getEmGradeRatio() {
		return this.emGradeRatio;
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
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	

}