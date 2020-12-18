/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bGrade;
import cn.cf.dto.B2bGradeDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bGradeDao {
	int insert(B2bGrade model);
	int update(B2bGrade model);
	int delete(String id);
	List<B2bGradeDto> searchGrid(Map<String, Object> map);
	List<B2bGradeDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bGradeDto getByPk(java.lang.String pk); 
	 B2bGradeDto getByName(java.lang.String name); 
	 B2bGradeDto getByChineseName(java.lang.String chineseName); 

}
