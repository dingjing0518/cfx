package cn.cf.entity;

import cn.cf.dto.B2bCreditDto;
import cn.cf.json.JsonUtils;

public class B2bCreditDtoMa extends B2bCreditDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CreditInfo info;

	public CreditInfo getInfo() {
		if(null == this.info){
			if(null != this.getCreditInfo() && !"".equals(this.getCreditInfo())){
				this.info = JsonUtils.toBean(this.getCreditInfo(),CreditInfo.class);
			}
		}
		return info;
	}

	public void setInfo(CreditInfo info) {
		this.info = info;
	}

	
	
	
}
