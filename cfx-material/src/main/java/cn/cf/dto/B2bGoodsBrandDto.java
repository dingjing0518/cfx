package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bGoodsBrandDto  extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	private java.lang.String pk;
	private java.lang.String brandName;
	private java.lang.String brandPk;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer auditStatus;
	private java.util.Date auditTime;
	private java.lang.String auditPk;
	private java.lang.String addMemberPk;//添加该品牌的操作人员
	//columns END
    private  String shortName;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


    public void setPk(java.lang.String pk) {
        this.pk = pk;
    }

    public java.lang.String getPk() {
		return this.pk;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	
	public java.lang.String getBrandName() {
		return this.brandName;
	}
	public void setBrandPk(java.lang.String brandPk) {
		this.brandPk = brandPk;
	}
	
	public java.lang.String getBrandPk() {
		return this.brandPk;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
	public java.lang.String getStoreName() {
		return this.storeName;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setAuditPk(java.lang.String auditPk) {
		this.auditPk = auditPk;
	}
	
	public java.lang.String getAuditPk() {
		return this.auditPk;
	}
 

	public java.lang.String getAddMemberPk() {
		return addMemberPk;
	}

	public void setAddMemberPk(java.lang.String addMemberPk) {
		this.addMemberPk = addMemberPk;
	}
	

}