package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDto;

public interface B2bMemberDaoEx extends B2bMemberDao{

	List<B2bMemberDto> getByEmployeeInfo(Map<String, Object> map);
	
	List<B2bMemberDto> getByEmployeeByCompany(Map<String, Object> map);

}
