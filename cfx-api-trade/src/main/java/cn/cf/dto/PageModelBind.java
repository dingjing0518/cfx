package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelBind<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部数量
    private Integer upNum;//上架数量
    private Integer downNum;//下架数量
    
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getUpNum() {
		return upNum;
	}
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}
	public Integer getDownNum() {
		return downNum;
	}
	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	
	
	
}
