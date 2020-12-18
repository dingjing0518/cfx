package cn.cf.entity;

import cn.cf.common.RestCode;

public class CallBackOrder {

	private RestCode code;
	
	private BatchOrder border;
	
	private Porder porder;

	public RestCode getCode() {
		return code;
	}

	public void setCode(RestCode code) {
		this.code = code;
	}

	public BatchOrder getBorder() {
		return border;
	}

	public void setBorder(BatchOrder border) {
		this.border = border;
	}

	public Porder getPorder() {
		return porder;
	}

	public void setPorder(Porder porder) {
		this.porder = porder;
	}

	
	
	
}
