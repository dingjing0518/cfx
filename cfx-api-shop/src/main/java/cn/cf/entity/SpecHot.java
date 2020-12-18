/**
 * 
 */
package cn.cf.entity;

import java.util.List;

/**
 * @author bin
 * 
 */
public class SpecHot {
	private java.lang.String firstLevelPk;
	private java.lang.String firstLevelName;
	private java.lang.String secondLevelPk;
	private java.lang.String secondLevelName;
	private java.lang.String thirdLevelPk;
	private java.lang.String thirdLevelName;
	private java.lang.String linkUrl;
	private java.lang.String fourthLevelPk;
	private java.lang.String fourthLevelName;
	private List<SpecHot> cList;

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public java.lang.String getFirstLevelPk() {
		return firstLevelPk;
	}

	public void setFirstLevelPk(java.lang.String firstLevelPk) {
		this.firstLevelPk = firstLevelPk;
	}

	public java.lang.String getFirstLevelName() {
		return firstLevelName;
	}

	public void setFirstLevelName(java.lang.String firstLevelName) {
		this.firstLevelName = firstLevelName;
	}

	public java.lang.String getSecondLevelPk() {
		return secondLevelPk;
	}

	public void setSecondLevelPk(java.lang.String secondLevelPk) {
		this.secondLevelPk = secondLevelPk;
	}

	public java.lang.String getSecondLevelName() {
		return secondLevelName;
	}

	public void setSecondLevelName(java.lang.String secondLevelName) {
		this.secondLevelName = secondLevelName;
	}

	public java.lang.String getThirdLevelPk() {
		return thirdLevelPk;
	}

	public void setThirdLevelPk(java.lang.String thirdLevelPk) {
		this.thirdLevelPk = thirdLevelPk;
	}

	public java.lang.String getThirdLevelName() {
		return thirdLevelName;
	}

	public void setThirdLevelName(java.lang.String thirdLevelName) {
		this.thirdLevelName = thirdLevelName;
	}

	public java.lang.String getFourthLevelPk() {
		return fourthLevelPk;
	}

	public void setFourthLevelPk(java.lang.String fourthLevelPk) {
		this.fourthLevelPk = fourthLevelPk;
	}

	public java.lang.String getFourthLevelName() {
		return fourthLevelName;
	}

	public void setFourthLevelName(java.lang.String fourthLevelName) {
		this.fourthLevelName = fourthLevelName;
	}

	public List<SpecHot> getcList() {
		return cList;
	}

	public void setcList(List<SpecHot> cList) {
		this.cList = cList;
	}



}
