package cn.cf.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bMemubarManageDao;
import cn.cf.dto.B2bMemubarManageDto;
import cn.cf.service.B2bMemubarManageService;

@Service
public class B2bMemubarManageServiceImpl implements B2bMemubarManageService{

	@Autowired
	private B2bMemubarManageDao memubarManageDao;
	

	@Override
	public List<B2bMemubarManageDto> searchList(Map<String, Object> map) {
		return memubarManageDao.searchList(map);
	}
	
	
}
