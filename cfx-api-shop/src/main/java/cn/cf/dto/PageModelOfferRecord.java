package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelOfferRecord<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部
    private Integer onGoingNum;//进行中
    private Integer hasFinishedNum;//已结束
    private Integer datedNum;//已过期
    
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getOnGoingNum() {
		return onGoingNum;
	}
	public void setOnGoingNum(Integer onGoingNum) {
		this.onGoingNum = onGoingNum;
	}
	public Integer getHasFinishedNum() {
		return hasFinishedNum;
	}
	public void setHasFinishedNum(Integer hasFinishedNum) {
		this.hasFinishedNum = hasFinishedNum;
	}
	public Integer getDatedNum() {
		return datedNum;
	}
	public void setDatedNum(Integer datedNum) {
		this.datedNum = datedNum;
	}

}
