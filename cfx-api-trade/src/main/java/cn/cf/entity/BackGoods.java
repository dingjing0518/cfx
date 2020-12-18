/**
 * 
 */
package cn.cf.entity;

import cn.cf.common.RestCode;

/**
 * @author bin
 * 
 */
public class BackGoods {
	private RestCode restCode;
	private B2bGoodsDtoMa gdto;

	public BackGoods() {
	}

	public BackGoods(RestCode code) {
		restCode = code;
	}

	public RestCode getRestCode() {
		return restCode;
	}

	public void setRestCode(RestCode restCode) {
		this.restCode = restCode;
	}

	public B2bGoodsDtoMa getGdto() {
		return gdto;
	}

	public void setGdto(B2bGoodsDtoMa gdto) {
		this.gdto = gdto;
	}

	

}
