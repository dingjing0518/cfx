package cn.cf.dto;

public class SysNewsExtDto extends SysNewsDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588208349258428253L;
	private String estimatedStartTime;
	private String estimatedEndTime;
	private String categoryPk;
	private String categoryName;
	private String estiTime;

	private Integer catePID;
	private String catePIDName;
	private String newsCategoryPk;
	
	private String belongSys;
	
	private String belongSysNames;
	
	private String categoryNames;
	
	private String parentId;

	public String getEstimatedStartTime() {
		return estimatedStartTime;
	}

	public void setEstimatedStartTime(String estimatedStartTime) {
		this.estimatedStartTime = estimatedStartTime;
	}

	public String getEstimatedEndTime() {
		return estimatedEndTime;
	}

	public void setEstimatedEndTime(String estimatedEndTime) {
		this.estimatedEndTime = estimatedEndTime;
	}

	public String getCategoryPk() {
		return categoryPk;
	}

	public void setCategoryPk(String categoryPk) {
		this.categoryPk = categoryPk;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getEstiTime() {
		return estiTime;
	}

	public void setEstiTime(String estiTime) {
		this.estiTime = estiTime;
	}

	public Integer getCatePID() {
		return catePID;
	}

	public void setCatePID(Integer catePID) {
		this.catePID = catePID;
	}

	public String getCatePIDName() {
		return catePIDName;
	}

	public void setCatePIDName(String catePIDName) {
		this.catePIDName = catePIDName;
	}

	public String getNewsCategoryPk() {
		return newsCategoryPk;
	}

	public void setNewsCategoryPk(String newsCategoryPk) {
		this.newsCategoryPk = newsCategoryPk;
	}

	public String getBelongSys() {
		return belongSys;
	}

	public void setBelongSys(String belongSys) {
		this.belongSys = belongSys;
	}

	public String getBelongSysNames() {
		return belongSysNames;
	}

	public void setBelongSysNames(String belongSysNames) {
		this.belongSysNames = belongSysNames;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
	
}
