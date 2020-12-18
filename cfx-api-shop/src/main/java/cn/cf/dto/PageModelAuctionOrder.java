package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelAuctionOrder<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部数量
    private Integer noSubmitNum;//未提交数量
    private Integer hasSubmitedNum;//已提交数量
    
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getNoSubmitNum() {
		return noSubmitNum;
	}
	public void setNoSubmitNum(Integer noSubmitNum) {
		this.noSubmitNum = noSubmitNum;
	}
	public Integer getHasSubmitedNum() {
		return hasSubmitedNum;
	}
	public void setHasSubmitedNum(Integer hasSubmitedNum) {
		this.hasSubmitedNum = hasSubmitedNum;
	}

}
