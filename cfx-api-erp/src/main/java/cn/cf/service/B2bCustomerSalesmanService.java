package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bCustomerSalesmanDto;

public interface B2bCustomerSalesmanService {

	void deleteByCustomerPk(String customerPk);

	void insertBatch(List<B2bCustomerSalesmanDto> customerSalesmanLst);

}
