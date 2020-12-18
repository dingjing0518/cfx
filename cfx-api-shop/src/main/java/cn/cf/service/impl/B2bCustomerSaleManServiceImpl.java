package cn.cf.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.CommonService;

@Service
public class B2bCustomerSaleManServiceImpl implements B2bCustomerSaleManService {

	@Autowired
	private CommonService commonService;
	
	
	@Override
	public Map<String, Object> getGoodsByMember(Map<String, Object> map) {
		Map<String,String> sendMap = new HashMap<String,String>();
		for (String string : map.keySet()) {
			if(null !=map.get(string)){
				sendMap.put(string, map.get(string).toString());
			}
		}
		/*try {
			String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/getGoodsByMember", sendMap);
			rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
			map = JsonUtils.toHashMap(JsonUtils.getJsonData(rest));
		} catch (Exception e) {
			logger.error("getGoodsByMember",e);
		}*/
		map = commonService.getGoodsByMember(map);
		return map;
	}

}
