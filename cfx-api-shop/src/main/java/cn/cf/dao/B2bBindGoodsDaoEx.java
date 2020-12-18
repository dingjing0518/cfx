package cn.cf.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;

public interface B2bBindGoodsDaoEx  extends B2bBindGoodsDao{
	int delBindGoodsByGoodsPk(@Param("goodsPk") String goodsPk);
	B2bBindGoodsDtoEx getBindGoodDtoExByGoodsPk(@Param("goodsPk") String goodsPk);
	List<B2bBindGoodsDto> searchBindingGoods(Map<String,Object> map);
	
	
}
