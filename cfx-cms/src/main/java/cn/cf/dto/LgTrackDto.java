package cn.cf.dto;


import java.util.Date;

public class LgTrackDto  implements java.io.Serializable{
    private static final long serialVersionUID = 5454155825314635342L;
    private String pk;
    private String deliveryPk;
    private Integer orderStatus;
    private String orderStatusName;
    private String stepDetail;
    private Date finishedTime;
    private Date insertTime;
    private Date updateTime;
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public String getDeliveryPk() {
        return deliveryPk;
    }
    public void setDeliveryPk(String deliveryPk) {
        this.deliveryPk = deliveryPk;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getStepDetail() {
        return stepDetail;
    }
    public void setStepDetail(String stepDetail) {
        this.stepDetail = stepDetail;
    }
    public Date getFinishedTime() {
        return finishedTime;
    }
    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
    public Date getInsertTime() {
        return insertTime;
    }
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getOrderStatusName() {
        return orderStatusName;
    }
    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }



}