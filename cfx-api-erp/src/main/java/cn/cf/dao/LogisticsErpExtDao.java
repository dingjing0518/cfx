/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;

import cn.cf.dto.LogisticsErpDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpExtDao extends LogisticsErpDao{
	int insertLogisticsErp(LogisticsErpDto dto);
	int updateLogisticsErp(LogisticsErpDto dto);
	int deleteByStorePk(String storePk);
	
	List<LogisticsErpDto> getListByStorePk(String storePk);

}
