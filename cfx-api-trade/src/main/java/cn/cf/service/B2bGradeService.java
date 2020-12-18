package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bGradeDto;

public interface B2bGradeService {

	List<B2bGradeDto> searchGradeList(Map<String, Object> map);
	
	B2bGradeDto getGrade(String pk);

}
