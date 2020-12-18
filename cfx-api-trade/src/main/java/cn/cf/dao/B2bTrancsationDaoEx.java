package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bTrancsationDto;
import cn.cf.model.B2bTrancsation;

public interface B2bTrancsationDaoEx extends B2bTrancsationDao{

	List<B2bTrancsation> trancsations();
	
	int searchCount(Map<String,Object> map);
	
	List<B2bTrancsationDto> searchTrancsationList(Map<String,Object> map);
}
