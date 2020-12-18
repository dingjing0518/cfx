package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;

public interface B2bCreditGoodsDaoEx extends B2bCreditGoodsDao{
	
	void updateByCreditGoods(B2bCreditGoodsDto creditGoods);
	
	List<B2bCreditGoodsDtoMa> getEconomicsGoods(Map<String,Object> map);
	
	List<B2bCreditGoodsDto> getCreditGroupBank(String companyPk);
	
	List<B2bCreditGoodsDtoMa> getCreditGoods(Map<String,Object> map);
	
}
