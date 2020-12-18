package cn.cf.service.bill;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillSigningDto;

public interface BillSingingService {

	B2bBillSigningDto getByCompanyPk(String companyPk);
	
	void updateByDto(B2bBillSigningDto sign);
	
	List<B2bBillSigningDto> getByMap(Map<String,Object> map);
}
