package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsErpStepInfo extends BaseModel  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String logisticsPk;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.Double startWeight;
	private java.lang.Double endWeight;
	private java.lang.String packNumber;
	private java.lang.Double price;
	private java.lang.Integer isDelete;
	private java.lang.Integer isStore;
	private  String block;
	//columns END

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setLogisticsPk(java.lang.String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	
	public java.lang.String getLogisticsPk() {
		return this.logisticsPk;
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
	
	public java.lang.Double getStartWeight() {
		return startWeight;
	}

	public void setStartWeight(java.lang.Double startWeight) {
		this.startWeight = startWeight;
	}

	public java.lang.Double getEndWeight() {
		return endWeight;
	}

	public void setEndWeight(java.lang.Double endWeight) {
		this.endWeight = endWeight;
	}

	public void setPackNumber(java.lang.String packNumber) {
		this.packNumber = packNumber;
	}
	
	public java.lang.String getPackNumber() {
		return this.packNumber;
	}
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	
	public java.lang.Double getPrice() {
		return this.price;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public java.lang.Integer getIsStore() {
		return isStore;
	}

	public void setIsStore(java.lang.Integer isStore) {
		this.isStore = isStore;
	}
	

}