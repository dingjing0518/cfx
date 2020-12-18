/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bSpecHot;
import cn.cf.dto.B2bSpecHotDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bSpecHotDao {
	int insert(B2bSpecHot model);
	int update(B2bSpecHot model);
	int delete(String id);
	List<B2bSpecHotDto> searchGrid(Map<String, Object> map);
	List<B2bSpecHotDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bSpecHotDto getByPk(java.lang.String pk); 
	 B2bSpecHotDto getByName(java.lang.String name); 
	 B2bSpecHotDto getByProductPk(java.lang.String productPk); 
	 B2bSpecHotDto getByProductName(java.lang.String productName); 
	 B2bSpecHotDto getByVarietiesPk(java.lang.String varietiesPk); 
	 B2bSpecHotDto getByVarietiesName(java.lang.String varietiesName); 
	 B2bSpecHotDto getByLinkUrl(java.lang.String linkUrl); 

}
