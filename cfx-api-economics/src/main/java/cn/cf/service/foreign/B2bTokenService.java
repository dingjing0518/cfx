package cn.cf.service.foreign;

import java.util.Map;

import cn.cf.dto.B2bTokenDto;

public interface B2bTokenService {

	B2bTokenDto getByCompanyPk(String companyPk);
	
	B2bTokenDto searchToken(Map<String, Object> map);
}
