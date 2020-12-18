/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsBank;
import cn.cf.dto.B2bEconomicsBankDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsBankDao {
	int insert(B2bEconomicsBank model);
	int update(B2bEconomicsBank model);
	int delete(String id);
	List<B2bEconomicsBankDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsBankDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsBankDto getByPk(java.lang.String pk); 
	 B2bEconomicsBankDto getByBankName(java.lang.String bankName); 
	 B2bEconomicsBankDto getByGateway(java.lang.String gateway); 

}
