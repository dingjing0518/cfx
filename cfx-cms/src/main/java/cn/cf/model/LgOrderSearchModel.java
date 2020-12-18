package cn.cf.model;

import java.io.Serializable;

public class LgOrderSearchModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1114197924271198802L;

    private String orderNumber;
    /**
     * 下单时间
     */
    private String orderTimeStart;
    private String orderTimeEnd;
    /**
     * 配送时间
     */
    private String arrivedTimeStart;
    private String arrivedTimeEnd;
    private String goodsInfo;
    /**
     * 提货人手机号
     */
    private String fromCompanyName;
    private String fromContactsTel;
    /**
     * 收货人手机号
     */
    private String toCompanyName;
    private String toContactsTel;
   
    private String orderStatus;
    private Integer isAbnormal;
    private Integer isConfirmed;
    private Integer isMatched;


    public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderTimeStart() {
        return orderTimeStart;
    }

    public void setOrderTimeStart(String orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }

    public String getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(String orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public String getArrivedTimeStart() {
        return arrivedTimeStart;
    }

    public void setArrivedTimeStart(String arrivedTimeStart) {
        this.arrivedTimeStart = arrivedTimeStart;
    }

    public String getArrivedTimeEnd() {
        return arrivedTimeEnd;
    }

    public void setArrivedTimeEnd(String arrivedTimeEnd) {
        this.arrivedTimeEnd = arrivedTimeEnd;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getFromCompanyName() {
        return fromCompanyName;
    }

    public void setFromCompanyName(String fromCompanyName) {
        this.fromCompanyName = fromCompanyName;
    }

    public String getToContactsTel() {
        return toContactsTel;
    }

    public void setToContactsTel(String toContactsTel) {
        this.toContactsTel = toContactsTel;
    }

    public String getToCompanyName() {
        return toCompanyName;
    }

    public void setToCompanyName(String toCompanyName) {
        this.toCompanyName = toCompanyName;
    }

    public String getFromContactsTel() {
        return fromContactsTel;
    }

    public void setFromContactsTel(String fromContactsTel) {
        this.fromContactsTel = fromContactsTel;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public Integer getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Integer isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Integer getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(Integer isMatched) {
        this.isMatched = isMatched;
    }


}