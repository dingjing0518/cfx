package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelAuction<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部
    private Integer visableNum;//启用
    private Integer forbiddenNum;//禁用
    
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getVisableNum() {
		return visableNum;
	}
	public void setVisableNum(Integer visableNum) {
		this.visableNum = visableNum;
	}
	public Integer getForbiddenNum() {
		return forbiddenNum;
	}
	public void setForbiddenNum(Integer forbiddenNum) {
		this.forbiddenNum = forbiddenNum;
	}
    

    

}
