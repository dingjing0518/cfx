package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bMemubarManageDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private String pk;
	private String url;
	private String name;
	private String urlLink;
	private Integer sort;
	private Integer source;
	private Date insertTime;
	private Date updateTime;
	//columns END

	public void setPk(String pk) {
		this.pk = pk;
	}
	
	public String getPk() {
		return this.pk;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	
	public String getUrlLink() {
		return this.urlLink;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getSort() {
		return this.sort;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	
	public Integer getSource() {
		return this.source;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	
}