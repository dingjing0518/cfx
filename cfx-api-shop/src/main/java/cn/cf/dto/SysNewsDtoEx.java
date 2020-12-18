package cn.cf.dto;

import java.util.Map;

public class SysNewsDtoEx extends SysNewsDto{

	
	private static final long serialVersionUID = -8289020904477388401L;

	private String categoryPk;
	
	private String insertTimeStr;
	
	
	private Map<String,Object> prevNews;
	private Map<String,Object> nextNews;
	
	

	public Map<String, Object> getPrevNews() {
		return prevNews;
	}

	public void setPrevNews(Map<String, Object> prevNews) {
		this.prevNews = prevNews;
	}

	public Map<String, Object> getNextNews() {
		return nextNews;
	}

	public void setNextNews(Map<String, Object> nextNews) {
		this.nextNews = nextNews;
	}

	public String getCategoryPk() {
		return categoryPk;
	}

	public void setCategoryPk(String categoryPk) {
		this.categoryPk = categoryPk;
	}

	public String getInsertTimeStr() {
		return insertTimeStr;
	}

	public void setInsertTimeStr(String insertTimeStr) {
		this.insertTimeStr = insertTimeStr;
	}
	
	
}
