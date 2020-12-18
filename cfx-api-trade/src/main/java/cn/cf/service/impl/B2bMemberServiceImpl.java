package cn.cf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dto.B2bMemberDto;
import cn.cf.service.B2bMemberService;
import cn.cf.service.CommonService;

@Service
public class B2bMemberServiceImpl implements B2bMemberService {
	
	
	@Autowired
	CommonService commonService;
	
	@Override
	public B2bMemberDto getByPk(String memberPk) {
		B2bMemberDto memberDto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pk", memberPk);
		/*try {
			String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/getByMember", map);
			rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
			JSONArray array = JSONArray.fromObject(JSONObject.fromObject(rest).get("data"));
			if(null != array && array.size()>0){
				memberDto = JsonUtils.toJSONBean(array.get(0).toString(), B2bMemberDtoEx.class);
			}
		} catch (Exception e) {
			logger.error("getByPk",e);
		}*/
		memberDto = commonService.getByMember(map).get(0);
		return memberDto;
	}

}
