package cn.cf.service;

import cn.cf.dto.B2bGradeDto;

public interface B2bGradeService {

	B2bGradeDto getByName(String gradeName);

	String createNewGrade(String gradeName);

}
