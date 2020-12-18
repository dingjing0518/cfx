package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;

public interface TokenService {
	B2bTokenDto searchToken(Map<String, Object> map);

	B2bStoreDto getByStorePk(String storePk);
	
	B2bTokenDto getB2bTokenByStorePk(String storePk);
}
