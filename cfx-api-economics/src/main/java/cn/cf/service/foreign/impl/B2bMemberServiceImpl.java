package cn.cf.service.foreign.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dto.B2bMemberDto;
import cn.cf.service.CommonService;
import cn.cf.service.foreign.B2bMemberService;

@Service
public class B2bMemberServiceImpl implements B2bMemberService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public List<B2bMemberDto> searchList(Map<String, Object> map) {
		List<B2bMemberDto> list = null;
		try {
			list = commonService.getByMember(map);
		} catch (Exception e) {
			logger.error("searchList",e);
		}
		return list;
	}

	@Override
	public B2bMemberDto getMember(String mobile) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		B2bMemberDto memberDto = null;
		try {
			List<B2bMemberDto>	list = commonService.getByMember(map);
		if(null != list){
			memberDto = list.get(0);
		}
		} catch (Exception e) {
			logger.error("getMember",e);
		}
		return memberDto;
	}

}
