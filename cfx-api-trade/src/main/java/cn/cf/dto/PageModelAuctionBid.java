package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelAuctionBid<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部
    private Integer bidNum;//中表数量
    private Integer noBidNum;//未中标数量
    
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getBidNum() {
		return bidNum;
	}
	public void setBidNum(Integer bidNum) {
		this.bidNum = bidNum;
	}
	public Integer getNoBidNum() {
		return noBidNum;
	}
	public void setNoBidNum(Integer noBidNum) {
		this.noBidNum = noBidNum;
	}
	

}
