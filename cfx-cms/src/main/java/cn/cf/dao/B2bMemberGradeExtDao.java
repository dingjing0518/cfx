/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberGradeExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberGradeExtDao extends B2bMemberGradeDao{

	int updateMemberGrade(B2bMemberGradeExtDto extDto);

	List<B2bMemberGradeExtDto> searchGridExt(Map<String, Object> map);
	
	int searchGridExtCount(Map<String, Object> map);
	
	List<B2bMemberGradeExtDto> isExistName(Map<String, Object> map);
	
	List<B2bMemberGradeExtDto> getByGradeNumber(Integer grade);
	

}
