package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bTrancsationContractDto;
import cn.cf.model.B2bTrancsationContract;

public interface B2bTrancsationContractDaoEx extends B2bTrancsationContractDao{

	List<B2bTrancsationContract> trancsations();
	
	List<B2bTrancsationContractDto> searchTrancsationList(Map<String,Object> map);
	
	int searchCount(Map<String,Object> map);
	
	
}
