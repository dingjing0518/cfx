package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDtoEx;

public interface B2bMemberDaoEx extends B2bMemberDao{
	List<B2bMemberDtoEx> searchMemberByOrderForEconomicsGoodsTypeIsOne(
			Map<String, Object> map);

 
}
