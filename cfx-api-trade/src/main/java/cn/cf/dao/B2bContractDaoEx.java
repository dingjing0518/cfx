package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bContractDtoEx;
import cn.cf.model.B2bContract;

public interface B2bContractDaoEx extends B2bContractDao{
	
	void insertBatch(List<B2bContractDtoEx> dtos);
	
	List<B2bContractDtoEx> searchContractList(Map<String,Object> map);
	
	int searchContractCount(Map<String,Object> map);
	
	int updateContract(B2bContract contract);
	
	Map<String,Object> searchContractStatusCounts(Map<String,Object> map);
}
