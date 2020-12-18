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
public class BackLogistics {
	private RestCode restCode;
	List<LogisticsCart> list;

	public BackLogistics() {
	}

	public BackLogistics(RestCode code) {
		restCode = code;
	}

	public RestCode getRestCode() {
		return restCode;
	}

	public void setRestCode(RestCode restCode) {
		this.restCode = restCode;
	}

	public List<LogisticsCart> getList() {
		return list;
	}

	public void setList(List<LogisticsCart> list) {
		this.list = list;
	}

}
