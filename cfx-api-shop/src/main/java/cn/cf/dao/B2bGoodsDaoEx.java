package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bGoodsEx;

public interface B2bGoodsDaoEx  extends B2bGoodsDao{

	int isGoodsRepeated(Map<String, Object> map);

	int updateGoods(B2bGoodsEx goods);

	int searchGoodsGridCountList(Map<String, Object> map);

	List<B2bGoodsDtoEx> searchGoodsGrid(Map<String, Object> map);

	int searchGoodsGridCount(Map<String, Object> map);

	List<B2bGoodsDto> getGoodDetailsByPks(@Param("goodPks") String[] goodPks);

	List<B2bGoodsDtoEx> searchUpdownCounts(Map<String, Object> map);

	boolean updateDataZero(Map<String, Object> params);

	void updateImportGoods(B2bGoodsDtoEx good);

	B2bGoodsDto getOpenDetails(String goodsPk);

	List<B2bGoodsDtoEx> searchListBySalesMan(Map<String, Object> map);

	int searchGridCountBySalesMan(Map<String, Object> map);

	int searchSaleGoodsCounts(Map<String, Object> map);



	B2bGoodsDto getDetailsById(String pk);


	List<B2bGoodsDtoEx> searchGoodsDetail(Map<String, Object> map);
 
	
}
