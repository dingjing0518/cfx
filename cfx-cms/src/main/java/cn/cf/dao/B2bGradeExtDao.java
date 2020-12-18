/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bGradeExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bGradeExtDao extends B2bGradeDao{

	int updateGrade(B2bGradeExtDto extDto);

	List<B2bGradeExtDto> searchGridExt(Map<String, Object> map);

	int searchGridExtCount(Map<String, Object> map);

	int isExitGrade(Map<String, Object> map);
	

}
