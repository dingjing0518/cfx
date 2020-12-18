package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bMemberDto;


public interface B2bMemberService {
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	B2bMemberDto getMember(String mobile);
	
	/**
	 * 根据pk查询memberDto
	 * @param memberPk
	 * @return
	 */
	B2bMemberDto getByPk(String memberPk);
	
	/**
	 * 根据条件查询会员
	 * @param map
	 * @return
	 */
	List<B2bMemberDto> searchList(Map<String,Object> map);
	
}
