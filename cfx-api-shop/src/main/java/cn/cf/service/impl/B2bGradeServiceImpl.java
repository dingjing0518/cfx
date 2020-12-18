package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bGradeDaoEx;
import cn.cf.dto.B2bGradeDto;
import cn.cf.service.B2bGradeService;

@Service
public class B2bGradeServiceImpl implements B2bGradeService {
	
	@Autowired
	private B2bGradeDaoEx  gradeDao;

	@Override
	public List<B2bGradeDto> searchGradeList(Map<String, Object> map) {
		return gradeDao.searchList(map);
	}

	@Override
	public B2bGradeDto getGrade(String pk) {
		return gradeDao.getByPk(pk);
	}

	@Override
	public List<B2bGradeDto> searchGradeNameByGradePks(String gradePks) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(!"-1".equals(gradePks)){
			String [] gradePk=gradePks.split(",");
			if(gradePk.length>0){
				map.put("gradePks", gradePk);
			}
		}
		return gradeDao.searchGradeNameByGradePks(map);
		
	}
	
	
	

}
