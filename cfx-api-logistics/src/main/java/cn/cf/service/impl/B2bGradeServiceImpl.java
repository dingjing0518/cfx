package cn.cf.service.impl;

import cn.cf.dao.B2bGradeDao;
import cn.cf.dto.B2bGradeDto;
import cn.cf.service.B2bGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class B2bGradeServiceImpl implements B2bGradeService {
	
	@Autowired
	private B2bGradeDao b2bGradeDao;

	@Override
	public List<B2bGradeDto> searchAllGrade() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		return b2bGradeDao.searchList(map);
	}

	@Override
	public B2bGradeDto getGradeByName(String gradeName) {
		return b2bGradeDao.getByName(gradeName);
	}

}
