/**
 * 
 */
package cn.cf.entity;

import java.util.List;

import cn.cf.common.RestCode;

/**
 * @author bin
 * 
 */
public class BackPgoods {
	private RestCode restCode;
	private List<Pgoods> pgList;

	public BackPgoods() {
	}

	public BackPgoods(RestCode code) {
		restCode = code;
	}

	public RestCode getRestCode() {
		return restCode;
	}

	public void setRestCode(RestCode restCode) {
		this.restCode = restCode;
	}

	public List<Pgoods> getPgList() {
		return pgList;
	}

	public void setPgList(List<Pgoods> pgList) {
		this.pgList = pgList;
	}

}
