package cn.cf.entity;

import javax.persistence.Id;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class AccountAuthColEntity {
	@Id
	private String id;

	private String key;

	private String value;

	private String insertTime;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}