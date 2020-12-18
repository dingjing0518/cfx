package cn.cf.dao;

import java.util.Map;

import cn.cf.dto.B2bMemberDto;

public interface B2bMemberDaoEx {
	
	B2bMemberDto searchEntityByMobileAndCompanyName(Map<String, Object> map);
}
