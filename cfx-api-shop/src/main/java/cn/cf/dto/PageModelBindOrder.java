package cn.cf.dto;

import cn.cf.PageModel;

public class PageModelBindOrder<T>  extends PageModel<T>{
	    private static final long serialVersionUID = 1L;
	    private Integer allNum; //全部数量
	    private Integer noBindOrderNum;//未转换订单数量
	    private Integer BindOrderNum;//已转换订单数量
	    private Integer cBindOrderNum;//取消订单
		public Integer getAllNum() {
			return allNum;
		}
		public void setAllNum(Integer allNum) {
			this.allNum = allNum;
		}
		public Integer getNoBindOrderNum() {
			return noBindOrderNum;
		}
		public void setNoBindOrderNum(Integer noBindOrderNum) {
			this.noBindOrderNum = noBindOrderNum;
		}
		public Integer getBindOrderNum() {
			return BindOrderNum;
		}
		public void setBindOrderNum(Integer bindOrderNum) {
			BindOrderNum = bindOrderNum;
		}
		public Integer getcBindOrderNum() {
			return cBindOrderNum;
		}
		public void setcBindOrderNum(Integer cBindOrderNum) {
			this.cBindOrderNum = cBindOrderNum;
		}

}
