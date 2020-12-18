package cn.cf.service.foreign;


import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDto;


public interface B2bMemberService {
	
	/**
	 * 根据条件查询会员
	 * @param map
	 * @return
	 */
	List<B2bMemberDto> searchList(Map<String,Object> map);
	
	B2bMemberDto getMember(String mobile);
	
	
	
}
