package cn.cf.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsErpBubbleDto  extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.util.Date insetTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	//columns END
	private String storePk;
	//columns END
	List<LogisticsErpBubbleStepInfoDto> bubbleStepInfo;
	public List<LogisticsErpBubbleStepInfoDto> getBubbleStepInfo() {
		return bubbleStepInfo;
	}

	public void setBubbleStepInfo(List<LogisticsErpBubbleStepInfoDto> bubbleStepInfo) {
		this.bubbleStepInfo = bubbleStepInfo;
	}
	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
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
	public void setInsetTime(java.util.Date insetTime) {
		this.insetTime = insetTime;
	}
	
	public java.util.Date getInsetTime() {
		return this.insetTime;
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
	

}