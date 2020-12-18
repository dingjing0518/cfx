package cn.cf.json;

import java.io.Serializable;
import java.util.List;

public class ExtTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // 节点ID -- 必须
	private String text; // 节点显示的文本 -- 必须
	private String parentText;//父标题
	private String image;
	private List<ExtTreeNode> children=null;
	private ExtTreeState state = new ExtTreeState();
	private Object srcObj; // 属性的源对象
	private String url;
	private boolean checked = false;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getSrcObj() {
		return srcObj;
	}

	public void setSrcObj(Object srcObj) {
		this.srcObj = srcObj;
	}

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

	public List<ExtTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ExtTreeNode> children) {
		this.children = children;
	}

	public ExtTreeState getState() {
		return state;
	}

	public void setState(ExtTreeState state) {
		this.state = state;
	}

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

}
