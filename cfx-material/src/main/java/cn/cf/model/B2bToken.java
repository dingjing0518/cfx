package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bToken  extends  BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String appId;
	private java.lang.String appSecret;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.lang.Integer isVisable;
	private java.lang.Integer isDelete;
	private java.lang.String tokenType;
	private java.lang.Integer accType;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setAppId(java.lang.String appId) {
		this.appId = appId;
	}
	
	public java.lang.String getAppId() {
		return this.appId;
	}
	public void setAppSecret(java.lang.String appSecret) {
		this.appSecret = appSecret;
	}
	
	public java.lang.String getAppSecret() {
		return this.appSecret;
	}
	
	public java.lang.String getStorePk() {
		return storePk;
	}

	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}

	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}

	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setTokenType(java.lang.String tokenType) {
		this.tokenType = tokenType;
	}
	
	public java.lang.String getTokenType() {
		return this.tokenType;
	}
	
	public void setAccType(java.lang.Integer accType) {
		this.accType = accType;
	}
	
	public java.lang.Integer getAccType() {
		return this.accType;
	}
	

}