package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysNewsStorageDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String categoryPk;
	private java.lang.String categoryName;
	private java.lang.String newsPk;
	private java.lang.Integer parentId;
	private java.lang.String title;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.String content;
	private java.lang.Integer recommend;
	private java.lang.Integer top;
	private java.lang.String keyword;
	private java.lang.String newAbstrsct;
	private java.lang.String url;
	private java.util.Date estimatedTime;
	private java.lang.Integer status;
	private java.lang.String newSource;
	private java.lang.Long pvCount;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setCategoryPk(java.lang.String categoryPk) {
		this.categoryPk = categoryPk;
	}
	
	public java.lang.String getCategoryPk() {
		return this.categoryPk;
	}
	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}
	
	public java.lang.String getCategoryName() {
		return this.categoryName;
	}
	public void setNewsPk(java.lang.String newsPk) {
		this.newsPk = newsPk;
	}
	
	public java.lang.String getNewsPk() {
		return this.newsPk;
	}
	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}
	
	public java.lang.Integer getParentId() {
		return this.parentId;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
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
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setRecommend(java.lang.Integer recommend) {
		this.recommend = recommend;
	}
	
	public java.lang.Integer getRecommend() {
		return this.recommend;
	}
	public void setTop(java.lang.Integer top) {
		this.top = top;
	}
	
	public java.lang.Integer getTop() {
		return this.top;
	}
	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	public void setNewAbstrsct(java.lang.String newAbstrsct) {
		this.newAbstrsct = newAbstrsct;
	}
	
	public java.lang.String getNewAbstrsct() {
		return this.newAbstrsct;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setEstimatedTime(java.util.Date estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	
	public java.util.Date getEstimatedTime() {
		return this.estimatedTime;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setNewSource(java.lang.String newSource) {
		this.newSource = newSource;
	}
	
	public java.lang.String getNewSource() {
		return this.newSource;
	}
	public void setPvCount(java.lang.Long pvCount) {
		this.pvCount = pvCount;
	}
	
	public java.lang.Long getPvCount() {
		return this.pvCount;
	}
	

}