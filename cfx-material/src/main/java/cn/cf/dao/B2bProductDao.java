/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bProduct;
import cn.cf.dto.B2bProductDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bProductDao {
	int insert(B2bProduct model);
	int update(B2bProduct model);
	int delete(String id);
	List<B2bProductDto> searchGrid(Map<String, Object> map);
	List<B2bProductDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bProductDto getByPk(java.lang.String pk); 
	 B2bProductDto getByName(java.lang.String name); 
	 B2bProductDto getByDetils(java.lang.String detils); 

}
