/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bPackNumber;
import cn.cf.dto.B2bPackNumberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bPackNumberDao {
	int insert(B2bPackNumber model);
	int update(B2bPackNumber model);
	int delete(String id);
	List<B2bPackNumberDto> searchGrid(Map<String, Object> map);
	List<B2bPackNumberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bPackNumberDto getByPk(java.lang.String pk); 
	 B2bPackNumberDto getByProductPk(java.lang.String productPk); 
	 B2bPackNumberDto getByProductName(java.lang.String productName); 
	 B2bPackNumberDto getByPackNumber(java.lang.String packNumber); 
	 B2bPackNumberDto getByBatchNumber(java.lang.String batchNumber); 
	 B2bPackNumberDto getByStorePk(java.lang.String storePk); 
	 B2bPackNumberDto getByStoreName(java.lang.String storeName); 

}
