package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBindGoodsDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String bindPk;
	private java.lang.String goodsPk;
	private java.util.Date updateTime;
	private java.util.Date insertTime;
	private java.lang.Double weight;
	private java.lang.Integer boxes;
	private java.lang.Integer totalBoxes;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setBindPk(java.lang.String bindPk) {
		this.bindPk = bindPk;
	}
	
	public java.lang.String getBindPk() {
		return this.bindPk;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}
	
	public java.lang.Double getWeight() {
		return this.weight;
	}
	public void setBoxes(java.lang.Integer boxes) {
		this.boxes = boxes;
	}
	
	public java.lang.Integer getBoxes() {
		return this.boxes;
	}

	public java.lang.Integer getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(java.lang.Integer totalBoxes) {
		this.totalBoxes = totalBoxes;
	}
	

}