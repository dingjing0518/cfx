/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bBrandExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBrandExtDao extends B2bBrandDao{
	
	int searchGridExtCount(Map<String, Object> map);
	
	List<B2bBrandExtDto> searchGridExt(Map<String, Object> map);
	
	int updateBrand(B2bBrandExtDto dto);

    B2bBrandDto getByMap(Map<String, Object> param);
}
