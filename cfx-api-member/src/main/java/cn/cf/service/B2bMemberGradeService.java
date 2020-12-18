package cn.cf.service;

import cn.cf.dto.B2bMemberGradeDto;

public interface B2bMemberGradeService {
	
	/**
	 * 根据等级数查询会员等级dto
	 * @param gradeNumber
	 * @return
	 */
	B2bMemberGradeDto getDtoByGradeNumber(Integer gradeNumber);
	
}
