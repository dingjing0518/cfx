package cn.cf.dto;

import java.util.Map;

import cn.cf.PageModel;

public class PageModelEx<T> extends PageModel<T> {
    private static final long serialVersionUID = 1L;
    Map<String, Object> countMap;
    
	public Map<String, Object> getCountMap() {
		return countMap;
	}
	public void setCountMap(Map<String, Object> countMap) {
		this.countMap = countMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    

}
