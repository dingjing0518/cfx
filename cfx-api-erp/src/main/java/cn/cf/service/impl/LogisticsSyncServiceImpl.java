package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bPlantDao;
import cn.cf.dao.B2bProductDao;
import cn.cf.dao.SysRegionsDao;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.service.LogisticsSyncService;


@Service
public class LogisticsSyncServiceImpl implements LogisticsSyncService {

	@Autowired
	private SysRegionsDao sysRegionsDao;
	
	@Autowired
	private B2bProductDao b2bProductDao;
	
	@Autowired
	private B2bPlantDao b2bPlantDao;

	@Override
	public SysRegionsDto getRegionsByName(String name) {
		return sysRegionsDao.getByName(name);
	}

	@Override
	public B2bProductDto getByProductName(String name) {
		return b2bProductDao.getByName(name);
	}

	@Override
	public B2bPlantDto getByPlantName(String name) {
		return b2bPlantDao.getByName(name);
	}

	
}
