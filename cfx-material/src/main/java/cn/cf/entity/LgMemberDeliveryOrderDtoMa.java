package cn.cf.entity;

import java.util.Date;

public class LgMemberDeliveryOrderDtoMa implements java.io.Serializable{

	private static final long serialVersionUID = 5454155825314635342L;
	
	private String pk;
    private String memberPk;//业务员pk
    private String deliveryOrderPk;//发货单pk
    private Date insertTime;
    private Integer isDelete;
    
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}
	public String getDeliveryOrderPk() {
		return deliveryOrderPk;
	}
	public void setDeliveryOrderPk(String deliveryOrderPk) {
		this.deliveryOrderPk = deliveryOrderPk;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	
}
