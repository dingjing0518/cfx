package cn.cf.service.operation.impl;

import cn.cf.dao.SysRegionsExtDao;
import cn.cf.dao.SysRegionsDao;
import cn.cf.dto.SysRegionsDto;
import cn.cf.model.SysRegions;
import cn.cf.service.operation.SysRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRegionsServiceImpl implements SysRegionsService {

	@Autowired
	private SysRegionsDao sysRegionsDao;

	@Autowired
	private SysRegionsExtDao sysRegionsExtDao;
	
	@Override
	public List<SysRegionsDto> getSysregisByProvenceList() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("parentPk", "-1");
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		return sysRegionsDao.searchList(map);
	}

	@Override
	public List<SysRegionsDto> getSysregisByCityList(String parentPk) {
		return sysRegionsExtDao.getByParentPkList(parentPk);
	}

	@Override
	public List<SysRegionsDto> getSysRegonsAll() {
		
		return sysRegionsExtDao.getAllRegions();
	}

	@Override
	public int add(SysRegions model) {
		return sysRegionsDao.insert(model);
	}

	@Override
	public int update(SysRegions model) {
		return sysRegionsDao.update(model);
	}

	@Override
	public int delete(String pk) {
		return sysRegionsDao.delete(pk);
	}

	@Override
	public SysRegionsDto getbyName(String name) {
		return sysRegionsDao.getByName(name);
	}

	
	public List<SysRegionsDto> getAllSubRegionsDesc(String parentPk) {
		// TODO Auto-generated method stub
		return sysRegionsExtDao.getAllSubRegionsDesc(parentPk);
	}
	
	@Override
	public int getMaxRegionsPk(){
		return sysRegionsExtDao.getMaxPk();
	}
	@Override
	public SysRegionsDto getbyPk(String pk){
		
		return sysRegionsDao.getByPk(pk);
	}

	@Override
	public List<SysRegionsDto> getByParentPk(String parentPK) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("parentPk", parentPK);
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		return sysRegionsDao.searchList(map);
	}

	
}
