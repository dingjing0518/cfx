package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelOrderRecord<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    private Integer allNum; //全部
    private Integer noSubmitNum;//未提交
    private Integer hasSubmitNum;//已提交
    private Integer datedNum;//已过期
    
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
	public Integer getHasSubmitNum() {
		return hasSubmitNum;
	}
	public void setHasSubmitNum(Integer hasSubmitNum) {
		this.hasSubmitNum = hasSubmitNum;
	}
	public Integer getDatedNum() {
		return datedNum;
	}
	public void setDatedNum(Integer datedNum) {
		this.datedNum = datedNum;
	}

}
