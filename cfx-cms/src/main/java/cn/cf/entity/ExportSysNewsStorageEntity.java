package cn.cf.entity;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class ExportSysNewsStorageEntity {
	

	private java.lang.String insertTime;
	private java.lang.String statusName;
	private java.lang.String topName;
	private java.lang.String title;
	private java.lang.String keyword;
	private java.lang.Long pvCount;
	private java.lang.String recommendPosition;
	private String categoryNames;
	private String isVisableName;
	
	private String belongSysNames;

	public java.lang.String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.lang.String insertTime) {
		this.insertTime = insertTime;
	}

	public java.lang.String getStatusName() {
		return statusName;
	}

	public void setStatusName(java.lang.String statusName) {
		this.statusName = statusName;
	}

	public java.lang.String getTopName() {
		return topName;
	}

	public void setTopName(java.lang.String topName) {
		this.topName = topName;
	}

	public java.lang.String getKeyword() {
		return keyword;
	}

	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}

	public java.lang.Long getPvCount() {
		return pvCount;
	}

	public void setPvCount(java.lang.Long pvCount) {
		this.pvCount = pvCount;
	}

	public java.lang.String getRecommendPosition() {
		return recommendPosition;
	}

	public void setRecommendPosition(java.lang.String recommendPosition) {
		this.recommendPosition = recommendPosition;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getBelongSysNames() {
		return belongSysNames;
	}

	public void setBelongSysNames(String belongSysNames) {
		this.belongSysNames = belongSysNames;
	}

	public String getIsVisableName() {
		return isVisableName;
	}

	public void setIsVisableName(String isVisableName) {
		this.isVisableName = isVisableName;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
}