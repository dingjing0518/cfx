package cn.cf.service;

import cn.cf.dto.B2bGradeDto;
import java.util.List;

public interface B2bGradeService {
	
	/**
	 * 查询所有商品等级
	 * @return
	 */
	List<B2bGradeDto> searchAllGrade();

	
	/**
	 * 查询等级
	 * @param gradeName  等级名称
	 * @return
	 */
	B2bGradeDto getGradeByName(String gradeName);
}
