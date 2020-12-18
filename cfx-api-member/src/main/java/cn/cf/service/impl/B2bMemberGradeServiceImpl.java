package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bMemberGradeDao;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.service.B2bMemberGradeService;


@Service
public class B2bMemberGradeServiceImpl implements B2bMemberGradeService{

	@Autowired
	private B2bMemberGradeDao b2bMemberGradeDao;
	
	/**
	 * 根据等级数查询会员等级dto
	 */
	@Override
	public B2bMemberGradeDto getDtoByGradeNumber(Integer gradeNumber) {
		return b2bMemberGradeDao.getDtoByGradeNumber(gradeNumber);
	}

}
