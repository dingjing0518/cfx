package cn.cf.dao;

import java.util.Map;

public interface SysNewsDaoEx extends SysNewsDao{

	Map<String,Object> getNewsByNext(Map<String,Object> map);
}
