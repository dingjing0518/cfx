package cn.cf.dao;

import java.util.List;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.model.B2bOrderGoods;

public interface B2bOrderGoodsDaoEx extends B2bOrderGoodsDao {

	List<B2bOrderGoodsDtoEx> getByOrderNumberEx(String orderNumber);

	int updateOrderGoods(B2bOrderGoods orderGoods);

	List<B2bOrderGoodsDto> getByGoodsOrderNumber(String orderNumber);

}
