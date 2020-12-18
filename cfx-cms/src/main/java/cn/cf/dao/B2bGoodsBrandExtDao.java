/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.dto.B2bGoodsBrandExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bGoodsBrandExtDao extends B2bGoodsBrandDao{
	
	List<B2bGoodsBrandExtDto> searchGridExt(Map<String, Object> map);
	
	int searchGridExtCount(Map<String, Object> map);
	
	int updateGoodsBrand(B2bGoodsBrandExtDto extDto);

    int insertExt(B2bGoodsBrandExtDto goodsBrand);

	List<B2bGoodsBrandDto> searchDisGoodsBrand();
}
