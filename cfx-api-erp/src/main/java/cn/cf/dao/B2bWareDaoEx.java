package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.model.B2bWare;

public interface B2bWareDaoEx extends B2bWareDao{
	
	
	public List<B2bWare> getByName(Map<String, Object> map) ;
}
