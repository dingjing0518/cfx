package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.dao.B2bGradeDaoEx;
import cn.cf.dto.B2bGradeDto;
import cn.cf.model.B2bGrade;
import cn.cf.service.B2bGradeService;
import cn.cf.util.KeyUtils;


@Service
public class B2bGradeServiceImpl implements B2bGradeService {

	@Autowired
	private B2bGradeDaoEx b2bGradeDaoEx;
	
	@Override
	public B2bGradeDto getByName(String gradeName) {
		B2bGradeDto b2bGradeDto = null;
		Map<String, Object> map = new HashMap<>();
		map.put("name", gradeName);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		List<B2bGradeDto> list = b2bGradeDaoEx.searchList(map);
		if (null!=list && list.size()>0) {
			b2bGradeDto = list.get(0);
		}
		return b2bGradeDto;
	}

	@Override
	@Transactional
	public String createNewGrade(String gradeName){
		B2bGrade b2bGrade = new B2bGrade();
		b2bGrade.setName(gradeName);
		String pk = KeyUtils.getUUID();
		b2bGrade.setPk(pk);
		b2bGrade.setInsertTime(new Date());
		b2bGrade.setIsDelete(1);
		b2bGrade.setIsVisable(1);
		b2bGrade.setSort(1);
		b2bGradeDaoEx.insert(b2bGrade);
		return pk;
	}

}
