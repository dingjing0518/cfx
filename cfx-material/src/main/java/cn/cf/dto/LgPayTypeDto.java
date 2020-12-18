package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgPayTypeDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String payTypeName;
	private java.lang.Integer isUsable;
	private java.lang.Integer delStatus;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setPayTypeName(java.lang.String payTypeName) {
		this.payTypeName = payTypeName;
	}
	
	public java.lang.String getPayTypeName() {
		return this.payTypeName;
	}
	public void setIsUsable(java.lang.Integer isUsable) {
		this.isUsable = isUsable;
	}
	
	public java.lang.Integer getIsUsable() {
		return this.isUsable;
	}
	public void setDelStatus(java.lang.Integer delStatus) {
		this.delStatus = delStatus;
	}
	
	public java.lang.Integer getDelStatus() {
		return this.delStatus;
	}
	

}