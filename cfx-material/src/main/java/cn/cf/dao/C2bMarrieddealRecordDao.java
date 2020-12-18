/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.C2bMarrieddealRecord;
import cn.cf.dto.C2bMarrieddealRecordDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface C2bMarrieddealRecordDao {
	int insert(C2bMarrieddealRecord model);
	int update(C2bMarrieddealRecord model);
	int delete(String id);
	List<C2bMarrieddealRecordDto> searchGrid(Map<String, Object> map);
	List<C2bMarrieddealRecordDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 C2bMarrieddealRecordDto getByPk(java.lang.String pk); 
	 C2bMarrieddealRecordDto getByMarrieddealPk(java.lang.String marrieddealPk); 
	 C2bMarrieddealRecordDto getByCompanyPk(java.lang.String companyPk); 

}
