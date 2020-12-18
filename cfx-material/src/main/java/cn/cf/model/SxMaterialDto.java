package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SxMaterialDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private String pk;
	private String name;
	private Integer sort;
	private Integer isDelete;
	private Integer isVisable;
	private Date insertTime;
	private String detils;
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
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getSort() {
		return this.sort;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsVisable(Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public Integer getIsVisable() {
		return this.isVisable;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public Date getInsertTime() {
		return this.insertTime;
	}
	public void setDetils(String detils) {
		this.detils = detils;
	}
	
	public String getDetils() {
		return this.detils;
	}
	

}