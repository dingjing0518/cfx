package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bGradeDao;
import cn.cf.dto.B2bGradeDto;
import cn.cf.service.B2bGradeService;

@Service
public class B2bGradeServiceImpl implements B2bGradeService {
	
	@Autowired
	private B2bGradeDao  gradeDao;

	@Override
	public List<B2bGradeDto> searchGradeList(Map<String, Object> map) {
		return gradeDao.searchList(map);
	}

	@Override
	public B2bGradeDto getGrade(String pk) {
		return gradeDao.getByPk(pk);
	}
	
	
	

}
