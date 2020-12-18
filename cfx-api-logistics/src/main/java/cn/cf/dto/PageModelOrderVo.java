package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelOrderVo<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer toPayNum;//待付款
    private Integer toConfirmNum;//待确认
    private Integer toDealNum; //待处理
    private Integer inDeliveryNum; //提货中
    private Integer outFordeliveryNum;//派送中
    private Integer beReceivedNum; //已签收
    private Integer canceledNum;
    private Integer allNum; //全部
    private Integer abnormalNum; //异常数量
    private Integer finishedNum; //已完成数量
    private Integer hasClosedNum;//支付已关闭数量
    
    public Integer getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}

	//用户发票
    private Integer unbilledNum;//未开票数量
    private Integer billedNum;//已经开票数量
    private Integer mailedNum;//已寄送数量

    //承运商发票
    private Integer unsendNum; //未开票数量
    private Integer sendNum; //已开票数量
    private Integer recievedNum;//已确认数量
    private Integer processingNum;//进行中的数量

    public Integer getAbnormalNum() {
		return abnormalNum;
	}

	public void setAbnormalNum(Integer abnormalNum) {
		this.abnormalNum = abnormalNum;
	}

	public Integer getProcessingNum() {
		return processingNum;
	}

	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}

	public Integer getUnsendNum() {
        return unsendNum;
    }

    public void setUnsendNum(Integer unsendNum) {
        this.unsendNum = unsendNum;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public Integer getHasClosedNum() {
		return hasClosedNum;
	}

	public void setHasClosedNum(Integer hasClosedNum) {
		this.hasClosedNum = hasClosedNum;
	}

	public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public Integer getRecievedNum() {
        return recievedNum;
    }

    public void setRecievedNum(Integer recievedNum) {
        this.recievedNum = recievedNum;
    }

    public Integer getUnbilledNum() {
        return unbilledNum;
    }

    public void setUnbilledNum(Integer unbilledNum) {
        this.unbilledNum = unbilledNum;
    }

    public Integer getBilledNum() {
        return billedNum;
    }

    public void setBilledNum(Integer billedNum) {
        this.billedNum = billedNum;
    }

    public Integer getMailedNum() {
        return mailedNum;
    }

    public void setMailedNum(Integer mailedNum) {
        this.mailedNum = mailedNum;
    }

    public Integer getToPayNum() {
        return toPayNum;
    }

    public void setToPayNum(Integer toPayNum) {
        this.toPayNum = toPayNum;
    }

    public Integer getToConfirmNum() {
        return toConfirmNum;
    }

    public void setToConfirmNum(Integer toConfirmNum) {
        this.toConfirmNum = toConfirmNum;
    }

    public Integer getCanceledNum() {
        return canceledNum;
    }

    public void setCanceledNum(Integer canceledNum) {
        this.canceledNum = canceledNum;
    }

    private Integer confirmedNum;  //已确认的数量
    private Integer unConfirmedNum;//未确认的数量

    public Integer getConfirmedNum() {
        return confirmedNum;
    }

    public void setConfirmedNum(Integer confirmedNum) {
        this.confirmedNum = confirmedNum;
    }

    public Integer getUnConfirmedNum() {
        return unConfirmedNum;
    }

    public void setUnConfirmedNum(Integer unConfirmedNum) {
        this.unConfirmedNum = unConfirmedNum;
    }

    public Integer getToDealNum() {
        return toDealNum;
    }

    public void setToDealNum(Integer toDealNum) {
        this.toDealNum = toDealNum;
    }

    public Integer getInDeliveryNum() {
        return inDeliveryNum;
    }

    public void setInDeliveryNum(Integer inDeliveryNum) {
        this.inDeliveryNum = inDeliveryNum;
    }

    public Integer getOutFordeliveryNum() {
        return outFordeliveryNum;
    }

    public void setOutFordeliveryNum(Integer outFordeliveryNum) {
        this.outFordeliveryNum = outFordeliveryNum;
    }

    public Integer getBeReceivedNum() {
        return beReceivedNum;
    }

    public void setBeReceivedNum(Integer beReceivedNum) {
        this.beReceivedNum = beReceivedNum;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }


}
