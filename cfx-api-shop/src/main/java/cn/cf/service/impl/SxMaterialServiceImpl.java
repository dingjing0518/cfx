package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.SxMaterialDao;
import cn.cf.dao.SxSpecDao;
import cn.cf.dao.SxTechnologyDao;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxSpecDto;
import cn.cf.dto.SxTechnologyDto;
import cn.cf.service.SxMaterialService;
@Service
public class SxMaterialServiceImpl implements SxMaterialService {
	@Autowired
	private SxTechnologyDao sxTechnologyDao;
	
	@Autowired
	private  SxMaterialDao sxMaterialDao;
 
	
	@Autowired
	private SxSpecDao sxSpecDao;

	@Override
	public List<SxTechnologyDto> searchTechnologyList(Map<String, Object> map) {
		map.put("isDelete",  1);
		map.put("isVisable", 1);
		return sxTechnologyDao.searchList(map);
	}

	@Override
	public List<SxMaterialDto> searchSxMaterialList(Map<String, Object> map) {
		map.put("isDelete",  1);
		map.put("isVisable", 1);
		return sxMaterialDao.searchList(map);
	}

 

	@Override
	public List<SxSpecDto> getSxSpecList(Map<String, Object> map) {
		map.put("isVisable", 1);
		return sxSpecDao.searchList(map);
	}

}
