/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bMemberGrade;
import cn.cf.dto.B2bMemberGradeDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberGradeDao {
	int insert(B2bMemberGrade model);
	int update(B2bMemberGrade model);
	int delete(String id);
	List<B2bMemberGradeDto> searchGrid(Map<String, Object> map);
	List<B2bMemberGradeDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bMemberGradeDto getByPk(java.lang.String pk); 
	 B2bMemberGradeDto getByGradeName(java.lang.String gradeName); 
	 B2bMemberGradeDto getDtoByGradeNumber(java.lang.Integer gradeNumber);

}
