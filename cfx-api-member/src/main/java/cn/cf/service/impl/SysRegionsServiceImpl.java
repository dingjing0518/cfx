package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.cf.dao.SysRegionsDaoEx;
import cn.cf.dto.SysRegionsDto;
import cn.cf.service.SysRegionsService;

@Service
public class SysRegionsServiceImpl implements SysRegionsService {

	@Autowired
	private SysRegionsDaoEx sysRegionsDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public List<SysRegionsDto> getSysregisList(String parentPk) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		map.put("parentPk", parentPk);
		List<SysRegionsDto>  list= sysRegionsDao.searchList(map);
		return list;
	}
	@Override
	public List<SysRegionsDto> findall() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		List<SysRegionsDto>  list= sysRegionsDao.searchList(map);
		for(SysRegionsDto  s:list){
			mongoTemplate.insert(s);
		}
		return null;
	}
	
	@Override
	public SysRegionsDto getRegionsPk(Map<String,Object> map){
		map.put("nameOne", map.get("name")+"省");
		map.put("nameTwo", map.get("name")+"市");
		map.put("nameThree", map.get("name")+"区");
		map.put("nameFour", map.get("name")+"县");
		map.put("nameFive", map.get("name")+"镇");
		return sysRegionsDao.getRegionByNames(map);
	}

}
