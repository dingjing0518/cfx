package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bCustomerSalesmanDto;

public interface B2bCustomerSalesmanDaoEx extends B2bCustomerSalesmanDao {

	void deleteByCustomerPk(String customerPk);

	void insertBatch(List<B2bCustomerSalesmanDto> customerSalesmanLst);

}
