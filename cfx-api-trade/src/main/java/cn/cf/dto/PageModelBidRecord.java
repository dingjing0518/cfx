package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelBidRecord<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部
    private Integer noBidNum;//未中标
    private Integer hasBidNum;//已中标
    private Integer datedNum;//已过期
    
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getNoBidNum() {
		return noBidNum;
	}
	public void setNoBidNum(Integer noBidNum) {
		this.noBidNum = noBidNum;
	}
	public Integer getHasBidNum() {
		return hasBidNum;
	}
	public void setHasBidNum(Integer hasBidNum) {
		this.hasBidNum = hasBidNum;
	}
	public Integer getDatedNum() {
		return datedNum;
	}
	public void setDatedNum(Integer datedNum) {
		this.datedNum = datedNum;
	}

}
