/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LogisticsErpMember;
import cn.cf.dto.LogisticsErpMemberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpMemberDao {
	int insert(LogisticsErpMember model);
	int update(LogisticsErpMember model);
	int delete(String id);
	List<LogisticsErpMemberDto> searchGrid(Map<String, Object> map);
	List<LogisticsErpMemberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LogisticsErpMemberDto getByPk(java.lang.String pk); 
	 LogisticsErpMemberDto getByMemberPk(java.lang.String memberPk); 
	 LogisticsErpMemberDto getByLogisticsPk(java.lang.String logisticsPk); 
	 LogisticsErpMemberDto getByEmployeeName(java.lang.String employeeName); 
	 LogisticsErpMemberDto getByEmployeeNumber(java.lang.String employeeNumber); 

}
