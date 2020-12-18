/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bGoodsBrand;
import cn.cf.dto.B2bGoodsBrandDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bGoodsBrandDao {
	int insert(B2bGoodsBrand model);
	int update(B2bGoodsBrand model);
	int delete(String id);
	List<B2bGoodsBrandDto> searchGrid(Map<String, Object> map);
	List<B2bGoodsBrandDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bGoodsBrandDto getByPk(java.lang.String pk); 
	 B2bGoodsBrandDto getByBrandName(java.lang.String brandName); 
	 B2bGoodsBrandDto getByBrandPk(java.lang.String brandPk); 
	 B2bGoodsBrandDto getByStorePk(java.lang.String storePk); 
	 B2bGoodsBrandDto getByStoreName(java.lang.String storeName); 
	 B2bGoodsBrandDto getByAuditPk(java.lang.String auditPk); 

}
