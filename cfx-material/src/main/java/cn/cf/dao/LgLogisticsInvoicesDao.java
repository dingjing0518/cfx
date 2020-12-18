/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgLogisticsInvoices;
import cn.cf.dto.LgLogisticsInvoicesDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgLogisticsInvoicesDao {
	int insert(LgLogisticsInvoices model);
	int update(LgLogisticsInvoices model);
	int delete(String id);
	List<LgLogisticsInvoicesDto> searchGrid(Map<String, Object> map);
	List<LgLogisticsInvoicesDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgLogisticsInvoicesDto getByPk(java.lang.String pk); 
	 LgLogisticsInvoicesDto getByMonth(java.lang.String month); 

}
