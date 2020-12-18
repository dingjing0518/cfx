/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bBrand;
import cn.cf.dto.B2bBrandDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBrandDao {
	int insert(B2bBrand model);
	int update(B2bBrand model);
	int delete(String id);
	List<B2bBrandDto> searchGrid(Map<String, Object> map);
	List<B2bBrandDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBrandDto getByPk(java.lang.String pk); 
	 B2bBrandDto getByName(java.lang.String name); 

}
