package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bDimensionalityDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.Integer type;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.String updateStartTime;
	private java.lang.String updateEndTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
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