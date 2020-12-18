package cn.cf.service.bill;

import cn.cf.dto.B2bBillCustomerDto;

public interface BillCustomerService {

	B2bBillCustomerDto getByPk(String pk);
	
	B2bBillCustomerDto getByCompanyPk(String companyPk);
}
