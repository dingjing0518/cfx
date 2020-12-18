package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysSmsTemplateDto  extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String templateCode;
	private java.lang.String freeSignName;
	private java.lang.String content;
	private java.lang.Integer isSms;
	private java.lang.Integer isMail;
	private java.lang.String name;
	private java.lang.Integer type;
	private java.lang.Integer flag;
	private java.lang.Integer isDelete;
	//columns END

	public void setTemplateCode(java.lang.String templateCode) {
		this.templateCode = templateCode;
	}
	
	public java.lang.String getTemplateCode() {
		return this.templateCode;
	}
	public void setFreeSignName(java.lang.String freeSignName) {
		this.freeSignName = freeSignName;
	}
	
	public java.lang.String getFreeSignName() {
		return this.freeSignName;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setIsSms(java.lang.Integer isSms) {
		this.isSms = isSms;
	}
	
	public java.lang.Integer getIsSms() {
		return this.isSms;
	}
	public void setIsMail(java.lang.Integer isMail) {
		this.isMail = isMail;
	}
	
	public java.lang.Integer getIsMail() {
		return this.isMail;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}
	
	public java.lang.Integer getFlag() {
		return this.flag;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	

}