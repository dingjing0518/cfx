/**
 * 
 */
package cn.cf.service.impl;


import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import cn.cf.dao.B2bTokenDao;


import cn.cf.dto.B2bTokenDto;

import cn.cf.service.SysService;

/**
 * @author bin
 * 
 */
@Service
public class SysServiceImpl implements SysService {

	@Autowired
	private B2bTokenDao b2bTokenDao;
	

	@Override
	public B2bTokenDto searchToken(Map<String, Object> map) {
		List<B2bTokenDto> list = b2bTokenDao.searchGrid(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public SortedMap<Object, Object> mySignByParameters(B2bTokenDto b2btoken) {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appId", b2btoken.getAppId());
		parameters.put("appSecret", b2btoken.getAppSecret());
		parameters.put("pk", b2btoken.getPk());
		parameters.put("storePk", b2btoken.getStorePk());
		parameters.put("storeName", b2btoken.getStoreName());
		return parameters;
	}

	


 
 
	 
 
}
