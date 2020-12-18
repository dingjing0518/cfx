package cn.cf.service.foreign.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.service.CommonService;
import cn.cf.service.foreign.ForeignCompanyService;

@Service
public class ForeignCompanyServiceImpl implements ForeignCompanyService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	  @Override
	    public B2bCompanyDto getCompany(String pk) {
	    	B2bCompanyDto company = null;
//	    	Map<String,String> map = new HashMap<String,String>();
//	    	map.put("pk", pk);
	    	try {
//	    		String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url") + "shop/getByCompany", map);
//	    		rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
//	    		String data = JsonUtils.getJsonData(rest);
//	    		if(null != data && !"".equals(data)){
//	    			company = JSON.parseObject(data, B2bCompanyDtoEx.class); 
//	    		}
	    		company = 	commonService.getCompanyByPk(pk);
			} catch (Exception e) {
				logger.error("getCompany",e);
			}
	        return company;
	    }



	    @Override
	    public List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map) {
	    	List<B2bCompanyDto> list = null;
	    	Map<String,String> sendMap = new HashMap<String,String>();
	    	for (String string : map.keySet()) {
	    		if(null != map.get(string)){
	    			sendMap.put(string, map.get(string).toString());
	    		}
			}
	    	try {
//	    		String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url") + "shop/getByCompanyList", sendMap);
//	    		rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
//	    		JSONObject json = JSONObject.fromObject(rest);
//	    		if(null != json && json.containsKey("data")){
//	    			JSONArray array = json.getJSONArray("data");
//	    			list = new ArrayList<B2bCompanyDto>();
//	    			for (int i = 0; i < array.size(); i++) {
//	    				B2bCompanyDtoEx company = JSON.parseObject(array.getString(i), B2bCompanyDtoEx.class);
//	    				list.add(company);
//	    			}
//	    		}
	    		list = commonService.getCompanyDtoByMap(map);
	    	} catch (Exception e) {
	    		logger.error("searchCompanyList",e);
			}
	    	return list;
	    }


		@Override
		public B2bCompanyDto getCompanyByName(String companyName) {
			B2bCompanyDto companyDto = null;
//	    	Map<String,String> sendMap = new HashMap<String,String>();
//	    	sendMap.put("name", companyName);
	    	try {
//	    		String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url") + "shop/getByCompanyList", sendMap);
//	    		rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
//	    		JSONArray array = JSONArray.fromObject(JSONObject.fromObject(rest).get("data"));
//	    		if(null != array && array.size()>0){
//	    			 companyDto = JSON.parseObject(array.getString(0), B2bCompanyDtoEx.class);
//	    		}
	    		companyDto = commonService.getCompanyByName(companyName);
	    	} catch (Exception e) {
	    		logger.error("searchCompanyList",e);
			}
			return companyDto;
		}

}
