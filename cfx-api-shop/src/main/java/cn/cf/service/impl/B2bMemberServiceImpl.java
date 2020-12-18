package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dto.B2bMemberDto;
import cn.cf.service.B2bMemberService;
import cn.cf.service.CommonService;

@Service
public class B2bMemberServiceImpl implements B2bMemberService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;

	@Override
	public List<B2bMemberDto> searchList(Map<String, Object> map) {
		List<B2bMemberDto> list = null;
		try {
		/*	String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/getByMember", map);
			rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
			String data = JsonUtils.getJsonData(rest);
			if(null != data && !"".equals(data)){
				JSONArray array = JSONArray.fromObject(JSONObject.fromObject(rest).get("data"));
				if(null != array && array.size()>0){
					list = new ArrayList<B2bMemberDto>();
					for (int i = 0; i < array.size(); i++) {
						B2bMemberDto member = JsonUtils.toJSONBean(array.get(i).toString(), B2bMemberDto.class);
						list.add(member);
					}
				}
			}*/
			list=commonService.getByMember(map);
		} catch (Exception e) {
			logger.error("searchList",e);
		}
		return list;
	}

	@Override
	public B2bMemberDto getMember(String mobile) {
		B2bMemberDto memberDto = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mobile", mobile);
			/*String result = HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/getByMember", map);
			try {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray array = JSONArray.fromObject(JSONObject.fromObject(result).get("data"));
			if(null != array && array.size()>0){
				memberDto = JsonUtils.toJSONBean(array.get(0).toString(), B2bMemberDto.class);
			}*/
			List<B2bMemberDto> list = commonService.getByMember(map);
			if (null!=list && list.size()>0) {
				memberDto = list.get(0);
			}
		} catch (Exception e) {
			logger.error("getMember",e);
		}
		return memberDto;
	}

	@Override
	public B2bMemberDto getByPk(String memberPk) {
		B2bMemberDto memberDto = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pk", memberPk);
			/*String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/getByMember", map);
			try {
				rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray array = JSONArray.fromObject(JSONObject.fromObject(rest).get("data"));
			if (null != array && array.size() > 0) {
				memberDto = JsonUtils.toJSONBean(array.get(0).toString(), B2bMemberDtoEx.class);
			}*/
			List<B2bMemberDto> list = commonService.getByMember(map);
			if (null!=list && list.size()>0) {
				memberDto = list.get(0);
			}
		} catch (Exception e) {
			logger.error("getByPk",e);
		}
		return memberDto;
	}

}
