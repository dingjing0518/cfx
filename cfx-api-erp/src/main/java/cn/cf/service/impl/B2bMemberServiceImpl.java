package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.service.B2bMemberService;
@Service
public class B2bMemberServiceImpl implements B2bMemberService {

	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;

	@Override
	public List<B2bMemberDto> getByEmployeeInfo(Map<String, Object> map) {
		
		return b2bMemberDaoEx.getByEmployeeInfo(map);
	}

	@Override
	public List<B2bMemberDto> getByEmployeeByCompany(
			Map<String, Object> employeeMap) {
		// TODO Auto-generated method stub
		return b2bMemberDaoEx.getByEmployeeByCompany(employeeMap);
	}

}
