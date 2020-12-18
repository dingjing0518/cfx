package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.model.B2bContractGoods;

public interface B2bContractGoodsDaoEx extends B2bContractGoodsDao{
	
	void insertBatch(List<B2bContractGoodsDtoEx> goodsDtoEx);
	
	List<B2bContractGoodsDtoEx> exportContractList(Map<String,Object> map);
	
	void updateContractGoods(B2bContractGoods contractGoods);
	
	void updateByPk(B2bContractGoods contractGoods);
	
	List<B2bContractGoodsDtoEx> searchContractGoodsList(Map<String,Object> map);

	List<B2bContractGoodsDtoEx> searchListByContractNo(String contractNo);

}
