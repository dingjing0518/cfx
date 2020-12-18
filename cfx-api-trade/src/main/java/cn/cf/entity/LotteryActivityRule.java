/**
 * 
 */
package cn.cf.entity;

import javax.persistence.Id;

/**
 * @author Administrator
 *
 */
public class LotteryActivityRule {
	@Id
	private String id;
	private Integer ruleType;//规则类型 1.优惠券规则 
	private String ruleDetail;//规则详情
	private String insertTime;
	private String updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getRuleType() {
		return ruleType;
	}
	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuleDetail() {
		return ruleDetail;
	}
	public void setRuleDetail(String ruleDetail) {
		this.ruleDetail = ruleDetail;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
