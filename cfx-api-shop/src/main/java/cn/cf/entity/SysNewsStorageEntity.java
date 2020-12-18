package cn.cf.entity;

import javax.persistence.Id;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysNewsStorageEntity {
	@Id
	private java.lang.String id;
	private java.lang.String pk;
	private java.lang.String categoryPk;
	private java.lang.String categoryName;
	private String[] categoryNameArr;
	private java.lang.String newsPk;
	private java.lang.Integer parentId;
	private String[] parentIdArr;
	private java.lang.String title;
	private java.lang.String insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.String content;
	private java.lang.Integer recommend;
	private java.lang.Integer top;
	private java.lang.String keyword;
	private java.lang.String newAbstrsct;
	private java.lang.String url;
	private java.lang.String estimatedTime;
	private java.lang.Integer status;
	private java.lang.String newSource;
	private java.lang.Long pvCount;
	private String estimatedStartTime;
	private String estimatedEndTime;
	private String categoryNames;
	private String belongSys;
	private String belongSysNames;
	private String block;// cf(化纤)，sx（纱线）
	//columns END

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getCategoryPk() {
		return categoryPk;
	}

	public void setCategoryPk(java.lang.String categoryPk) {
		this.categoryPk = categoryPk;
	}

	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	public String[] getCategoryNameArr() {
		return categoryNameArr;
	}

	public void setCategoryNameArr(String[] categoryNameArr) {
		this.categoryNameArr = categoryNameArr;
	}

	public java.lang.String getNewsPk() {
		return newsPk;
	}

	public void setNewsPk(java.lang.String newsPk) {
		this.newsPk = newsPk;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.lang.String insertTime) {
		this.insertTime = insertTime;
	}

	public java.lang.Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}

	public java.lang.Integer getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(java.lang.Integer recommend) {
		this.recommend = recommend;
	}

	public java.lang.Integer getTop() {
		return top;
	}

	public void setTop(java.lang.Integer top) {
		this.top = top;
	}

	public java.lang.String getKeyword() {
		return keyword;
	}

	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}

	public java.lang.String getNewAbstrsct() {
		return newAbstrsct;
	}

	public void setNewAbstrsct(java.lang.String newAbstrsct) {
		this.newAbstrsct = newAbstrsct;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(java.lang.String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getNewSource() {
		return newSource;
	}

	public void setNewSource(java.lang.String newSource) {
		this.newSource = newSource;
	}

	public java.lang.Long getPvCount() {
		return pvCount;
	}

	public void setPvCount(java.lang.Long pvCount) {
		this.pvCount = pvCount;
	}

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

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
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

	public String[] getParentIdArr() {
		return parentIdArr;
	}

	public void setParentIdArr(String[] parentIdArr) {
		this.parentIdArr = parentIdArr;
	}

	public java.lang.String getPk() {
		return pk;
	}

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
	
}