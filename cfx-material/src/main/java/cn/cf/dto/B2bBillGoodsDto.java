package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillGoodsDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.String shotName;
	private java.lang.String bankPk;
	private java.lang.String bankName;
	private java.lang.Integer type;
	private java.lang.Integer isVisable;
	private java.lang.Integer isDelete;
	private java.lang.Double platformAmount;
	private java.lang.Double platformUseAmount;
	private java.lang.String gateway;
	private java.lang.String imgUrl;
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
	public void setShotName(java.lang.String shotName) {
		this.shotName = shotName;
	}
	
	public java.lang.String getShotName() {
		return this.shotName;
	}
	public void setBankPk(java.lang.String bankPk) {
		this.bankPk = bankPk;
	}
	
	public java.lang.String getBankPk() {
		return this.bankPk;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setPlatformAmount(java.lang.Double platformAmount) {
		this.platformAmount = platformAmount;
	}
	
	public java.lang.Double getPlatformAmount() {
		return this.platformAmount;
	}
	public void setPlatformUseAmount(java.lang.Double platformUseAmount) {
		this.platformUseAmount = platformUseAmount;
	}
	
	public java.lang.Double getPlatformUseAmount() {
		return this.platformUseAmount;
	}
	public void setGateway(java.lang.String gateway) {
		this.gateway = gateway;
	}
	
	public java.lang.String getGateway() {
		return this.gateway;
	}
	public void setImgUrl(java.lang.String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public java.lang.String getImgUrl() {
		return this.imgUrl;
	}
	

}