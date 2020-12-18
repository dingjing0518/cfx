package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelAuctionGoods<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部
    private Integer noOnLineNum;//未上线数量
    private Integer noStartNum;//未开始数量
    private Integer underwayNum;//进行中数量
    private Integer finishedNum;//已结束数量
    
	public Integer getNoOnLineNum() {
		return noOnLineNum;
	}
	public void setNoOnLineNum(Integer noOnLineNum) {
		this.noOnLineNum = noOnLineNum;
	}
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getNoStartNum() {
		return noStartNum;
	}
	public void setNoStartNum(Integer noStartNum) {
		this.noStartNum = noStartNum;
	}
	public Integer getUnderwayNum() {
		return underwayNum;
	}
	public void setUnderwayNum(Integer underwayNum) {
		this.underwayNum = underwayNum;
	}
	public Integer getFinishedNum() {
		return finishedNum;
	}
	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}
    

}
