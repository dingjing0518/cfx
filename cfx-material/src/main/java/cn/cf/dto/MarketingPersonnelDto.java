package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class MarketingPersonnelDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.Integer type;
	private java.util.Date insertTime;
	private java.lang.String accountPk;
	private java.lang.String accountName;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.String regionPk;
    private java.util.Date updateTime;
	//columns END

	
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	

    public java.util.Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }


    public java.lang.String getRegionPk() {
        return regionPk;
    }


    public void setRegionPk(java.lang.String regionPk) {
        this.regionPk = regionPk;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public java.lang.String getPk() {
		return this.pk;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setAccountPk(java.lang.String accountPk) {
		this.accountPk = accountPk;
	}
	
	public java.lang.String getAccountPk() {
		return this.accountPk;
	}

	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}
	
	public java.lang.String getAccountName() {
		return this.accountName;
	}
	
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	

}