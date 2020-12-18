package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysHelpsCategory  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.Integer sort;
	private java.lang.Integer showType;
	private java.lang.String showPlace;
	private java.lang.String block;
	//columns END

	
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getBlock() {
        return block;
    }

    public void setBlock(java.lang.String block) {
        this.block = block;
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
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setShowType(java.lang.Integer showType) {
		this.showType = showType;
	}
	
	public java.lang.Integer getShowType() {
		return this.showType;
	}

	public java.lang.String getShowPlace() {
		return showPlace;
	}

	public void setShowPlace(java.lang.String showPlace) {
		this.showPlace = showPlace;
	}
	

}