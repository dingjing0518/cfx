package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bFuturesTypeDto;
import cn.cf.dto.B2bFuturesTypeDtoEx;
import cn.cf.model.B2bFuturesType;

public interface  B2bFuturesTypeDaoEx  extends B2bFuturesTypeDao{

	void insertEx(B2bFuturesType futuresType);

	void updateEx(B2bFuturesType futuresType);

	B2bFuturesTypeDto isExist(Map<String, Object> map);

	int searchGridExtCount(Map<String, Object> map);

	List<B2bFuturesTypeDtoEx> searchGridExt(Map<String, Object> map);
	

}
