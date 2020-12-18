package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SxSpec  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private String pk;
	private String name;
	private Date insertTime;
	private Date updateTime;
	private Integer sort;
	private Integer isVisable;
	//columns END

	public void setPk(String pk) {
		this.pk = pk;
	}
	
	public String getPk() {
		return this.pk;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getSort() {
		return this.sort;
	}

	public Integer getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(Integer isVisable) {
		this.isVisable = isVisable;
	}
}