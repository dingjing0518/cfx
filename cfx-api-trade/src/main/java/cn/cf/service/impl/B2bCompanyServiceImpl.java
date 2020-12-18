package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.CommonService;

@Service
public class B2bCompanyServiceImpl implements B2bCompanyService {

	
	@Autowired
	CommonService commonService;
	
    @Override
    public B2bCompanyDtoEx getCompany(String pk) {
    	B2bCompanyDtoEx companyDtoEx = new B2bCompanyDtoEx();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("pk", pk);
    	/*try {
    		String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url") + "shop/getByCompany", map);
    		rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
    		String data = JsonUtils.getJsonData(rest);
    		if(null != data && !"".equals(data)){
    			company = JsonUtils.toJSONBean(data, B2bCompanyDtoEx.class); 
    		}
		} catch (Exception e) {
			logger.error("getCompany",e);
		}*/
    	List<B2bCompanyDto> list = commonService.getCompanyDtoByMap(map);
    	if (null!=list && list.size()>0) {
    		B2bCompanyDto companyDto =list.get(0);
			BeanUtils.copyProperties(companyDto, companyDtoEx);
		}
        return companyDtoEx;
    }


    @Override
    public List<B2bCompanyDtoEx> searchCompanyList(Integer companyType, String companyPk, Integer returnType,
                                                   Map<String, Object> map) {
        if (null == map) {
            map = new HashMap<String, Object>();
        }
    	List<B2bCompanyDtoEx> listEx = new ArrayList<>();
    	List<B2bCompanyDto> list = new ArrayList<>();
    	/*try {
    		String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url") + "shop/getByCompanyList", sendMap);
    		rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
    		JSONArray array = JSONArray.fromObject(JSONObject.fromObject(rest).get("data"));
    		if(null != array && array.size()>0){
    			list = new ArrayList<B2bCompanyDtoEx>();
    			for (int i = 0; i < array.size(); i++) {
    				B2bCompanyDtoEx company = JsonUtils.toJSONBean(array.getString(i), B2bCompanyDtoEx.class);
    				list.add(company);
    			}
    		}
    	} catch (Exception e) {
    		logger.error("searchCompanyList",e);
		}*/
    	if (!"".equals(returnType)) {
    		list = commonService.searchCompanyList(companyType, companyPk, returnType, map);
		}else{
			list = commonService.getCompanyDtoByMap(map);
		}

    	for (B2bCompanyDto b2bCompanyDto : list) {
			B2bCompanyDtoEx b2bCompanyDtoEx = new B2bCompanyDtoEx();
    		BeanUtils.copyProperties(b2bCompanyDto, b2bCompanyDtoEx);
    		listEx.add(b2bCompanyDtoEx);
		}
    	return listEx;
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
    	/*try {
    		String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url") + "shop/getByCompanyList", sendMap);
    		rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
    		JSONArray array = JSONArray.fromObject(JSONObject.fromObject(rest).get("data"));
    		if(null != array && array.size()>0){
    			list = new ArrayList<B2bCompanyDto>();
    			for (int i = 0; i < array.size(); i++) {
    				B2bCompanyDtoEx company = JsonUtils.toJSONBean(array.getString(i), B2bCompanyDtoEx.class);
    				list.add(company);
    			}
    		}
    	} catch (Exception e) {
    		logger.error("searchCompanyList",e);
		}*/
    	list = commonService.getCompanyDtoByMap(map);
    	return list;
    }

    
    /**
     * 根据公司名称查询公司
     */
	@Override
	public B2bCompanyDto getCompanyByName(String name) {
		B2bCompanyDto companyDto=new B2bCompanyDto();
		/*companyDto.setName(name);
		String jsonData = JsonUtils.convertToString(companyDto);
		Map<String, String> paraMap=new HashMap<>();
		paraMap.put("jsonData", jsonData);
		String resultTemp="";
		String data="";
		try {
			resultTemp = HttpHelper.doPost(PropertyConfig.getProperty("api_shop_url")+"shop/getCompanyByName", paraMap);
			data = EncodeUtil.des3Decrypt3Base64(resultTemp)[1];
			companyDto = JsonUtils.toJSONBean(JsonUtils.getJsonData(data),B2bCompanyDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		companyDto = commonService.getCompanyByName(name);
		return companyDto;
	}

}
