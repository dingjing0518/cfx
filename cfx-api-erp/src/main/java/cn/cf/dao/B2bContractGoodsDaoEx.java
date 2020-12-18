package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bContractGoodsDto;

public interface B2bContractGoodsDaoEx extends B2bContractGoodsDao{

	int updateContractGoodsDto(Map<String,Object> map);
	
	List<B2bContractGoodsDto> searchListExt(Map<String,Object> map);
}
