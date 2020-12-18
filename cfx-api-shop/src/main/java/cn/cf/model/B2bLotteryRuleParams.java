package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLotteryRuleParams  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String rulePk;
	private java.lang.String name;
	private java.lang.Integer number;
	private java.util.Date insertTime;
	private java.util.Date updataTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setRulePk(java.lang.String rulePk) {
		this.rulePk = rulePk;
	}
	
	public java.lang.String getRulePk() {
		return this.rulePk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdataTime(java.util.Date updataTime) {
		this.updataTime = updataTime;
	}
	
	public java.util.Date getUpdataTime() {
		return this.updataTime;
	}
	

}