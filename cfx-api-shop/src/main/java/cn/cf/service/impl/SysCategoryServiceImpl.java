package cn.cf.service.impl;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.cf.dao.SysCategoryDao;
import cn.cf.dto.SysCategoryDto;
import cn.cf.service.SysCategoryService;

@Service
public class SysCategoryServiceImpl implements SysCategoryService{

	
	@Autowired
	private SysCategoryDao sysCategoryDao;
	

	@Override
	public List<SysCategoryDto> searchCategorys(Map<String, Object> map) throws Exception{
		List<SysCategoryDto> list=sysCategoryDao.searchGrid(map);
		return list;
	}

}
