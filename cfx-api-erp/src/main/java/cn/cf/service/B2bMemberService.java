package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDto;

public interface B2bMemberService {

	

	List<B2bMemberDto> getByEmployeeInfo(Map<String, Object> employeeMap);
	
	List<B2bMemberDto> getByEmployeeByCompany(Map<String, Object> employeeMap);

}
