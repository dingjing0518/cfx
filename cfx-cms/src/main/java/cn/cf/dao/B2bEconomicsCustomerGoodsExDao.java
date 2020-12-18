package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsCustomerGoodsDto;

public interface B2bEconomicsCustomerGoodsExDao  extends B2bEconomicsCustomerGoodsDao{

	void insertBatch(List<B2bEconomicsCustomerGoodsDto> list);

	void deleteByB2bEconomicsCustomerPk(String b2bEconomicsCustomerPk);

	void updateBatch(List<B2bEconomicsCustomerGoodsDto> list);

	List<B2bEconomicsCustomerGoodsDto> searchListOrderByBankPk(Map<String, Object> map);

}
