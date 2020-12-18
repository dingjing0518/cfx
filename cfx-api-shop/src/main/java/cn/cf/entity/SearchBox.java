package cn.cf.entity;

import org.springframework.data.annotation.Id;


public class SearchBox {
	@Id
	private String id;
	private String memberPk;
	private String content;
	private String insertTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
