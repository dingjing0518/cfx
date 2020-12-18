package cn.cf.service.bill;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.model.B2bBillGoods;

public interface BillGoodsService {

	B2bBillGoodsDto getByPk(String pk);
/***
 * 查询票据产品
 * @param map
 * @return
 */
	List<B2bBillGoodsDto> searchBillGoods(Map<String, Object> map);
	
	
	B2bBillGoodsDto getByShotName(String shotName);
	
	void update(B2bBillGoods goods);

}
