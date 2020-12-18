package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysPropertyDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.String productShotName;
	private java.lang.String content;
	private java.lang.Integer type;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setProductPk(java.lang.String productPk) {
		this.productPk = productPk;
	}
	
	public java.lang.String getProductPk() {
		return this.productPk;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setProductShotName(java.lang.String productShotName) {
		this.productShotName = productShotName;
	}
	
	public java.lang.String getProductShotName() {
		return this.productShotName;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	

}