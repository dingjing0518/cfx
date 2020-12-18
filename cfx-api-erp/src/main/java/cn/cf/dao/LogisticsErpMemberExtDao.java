/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.dto.LogisticsErpMemberDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpMemberExtDao extends LogisticsErpMemberDao{
	int insertLgMemberRf(LogisticsErpMemberDto model);
	int updateLgMemberRf(LogisticsErpMemberDto model);
	int deleteByLogisticsPk(String logisticsPk);

}
