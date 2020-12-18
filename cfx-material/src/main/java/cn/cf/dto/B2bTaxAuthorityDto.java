package cn.cf.dto;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bTaxAuthorityDto extends BaseDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String taxAuthorityCode;
	private java.lang.String taxAuthorityName;
	private java.lang.Integer isDelete;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setTaxAuthorityCode(java.lang.String taxAuthorityCode) {
		this.taxAuthorityCode = taxAuthorityCode;
	}
	
	public java.lang.String getTaxAuthorityCode() {
		return this.taxAuthorityCode;
	}
	public void setTaxAuthorityName(java.lang.String taxAuthorityName) {
		this.taxAuthorityName = taxAuthorityName;
	}
	
	public java.lang.String getTaxAuthorityName() {
		return this.taxAuthorityName;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	

}