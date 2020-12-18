package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsErpBubbleStepInfoDto extends BaseDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.Double minTareWeight;
	private java.lang.Double maxTareWeight;
	private java.lang.Double coefficient;
	private java.lang.String bubblePk;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMinTareWeight(java.lang.Double minTareWeight) {
		this.minTareWeight = minTareWeight;
	}
	
	public java.lang.Double getMinTareWeight() {
		return this.minTareWeight;
	}
	public void setMaxTareWeight(java.lang.Double maxTareWeight) {
		this.maxTareWeight = maxTareWeight;
	}
	
	public java.lang.Double getMaxTareWeight() {
		return this.maxTareWeight;
	}
	public void setCoefficient(java.lang.Double coefficient) {
		this.coefficient = coefficient;
	}
	
	public java.lang.Double getCoefficient() {
		return this.coefficient;
	}
	public void setBubblePk(java.lang.String bubblePk) {
		this.bubblePk = bubblePk;
	}
	
	public java.lang.String getBubblePk() {
		return this.bubblePk;
	}
	

}