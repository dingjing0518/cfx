package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPackNumberDto;

public interface B2bPackNumberDaoEx extends B2bPackNumberDao {
	List<B2bPackNumberDto> getB2bPackNumberByGoods(Map<String,Object> map);
}
