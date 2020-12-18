package cn.cf.json;

import java.io.Serializable;
import java.util.List;

public class B2bRoleMenuNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id; // 节点ID -- 必须
	private String text; // 节点显示的文本 -- 必须
	private String enText;//节点内容
	private List<B2bRoleMenuNode> children=null;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<B2bRoleMenuNode> getChildren() {
		return children;
	}
	public void setChildren(List<B2bRoleMenuNode> children) {
		this.children = children;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEnText() {
		return enText;
	}
	public void setEnText(String enText) {
		this.enText = enText;
	}
	
	

}
