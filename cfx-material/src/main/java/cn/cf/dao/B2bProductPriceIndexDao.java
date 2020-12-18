/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bProductPriceIndex;
import cn.cf.dto.B2bProductPriceIndexDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bProductPriceIndexDao {
	int insert(B2bProductPriceIndex model);
	int update(B2bProductPriceIndex model);
	int delete(String id);
	List<B2bProductPriceIndexDto> searchGrid(Map<String, Object> map);
	List<B2bProductPriceIndexDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bProductPriceIndexDto getByPk(java.lang.String pk); 
	 B2bProductPriceIndexDto getByProductPk(java.lang.String productPk); 
	 B2bProductPriceIndexDto getByProductName(java.lang.String productName); 

}
