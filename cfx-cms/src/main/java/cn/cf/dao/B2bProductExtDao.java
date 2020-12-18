/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bProductExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bProductExtDao extends B2bProductDao{
	
	/**
	 * 删除或启用禁用
	 * @param dto
	 * @return
	 */
	int updateProduct(B2bProductExtDto dto);
	/**
	 * 查询品名列表
	 * @param map
	 * @return
	 */
	List<B2bProductExtDto> searchGridExt(Map<String, Object> map);
	
	/**
	 * 统计数量
	 * @param map
	 * @return
	 */
	int searchGridExtCount(Map<String, Object> map);


	List<B2bProductDto> getAllProductList();
	
	/**
	 * 根据品名查询
	 * @param productName
	 * @return
	 */
	B2bProductDto getByNameEx(String productName);
	
	List<B2bProductDto> getAllIsvisableProductList();
	
}
