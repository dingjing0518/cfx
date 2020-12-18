/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.entity.GoodsUpdateReport;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bGoodsExtDao extends  B2bGoodsDao{
	
	int searchGridExtCount(Map<String, Object> map);
	
	List<B2bGoodsExtDto> searchGridExt(Map<String, Object> map);


	int searchGridPriceTrendExtCount(Map<String, Object> map);
	
	List<B2bGoodsExtDto> searchGridPriceTrendExt(Map<String, Object> map);
	
	int updateGoods(B2bGoodsExtDto dto);
	
	List<B2bGoodsExtDto> getAllGoodsForLucene();

	List<GoodsUpdateReport> searchGoodsUpdateReportList(Map<String, Object> map);

	List<GoodsUpdateReport> getProductUpdateByOneday(@Param("date") String date);

	String searchOriginalUpdateDate();

	int searchSxGridExtCount(Map<String, Object> map);

	List<B2bGoodsExtDto> searchSxGridExt(Map<String, Object> map);

	
}
