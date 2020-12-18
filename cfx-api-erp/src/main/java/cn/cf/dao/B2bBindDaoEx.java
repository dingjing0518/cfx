package cn.cf.dao;

import java.util.Map;

import cn.cf.dto.B2bBindDto;

public interface B2bBindDaoEx  extends B2bBindDao{

	int updateBind(Map<String, Object> map);

	B2bBindDto getOneByBindProductId(String bindProductID);

}
