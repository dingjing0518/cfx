package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCustomerSalesmanDto;

public interface B2bCustomerSalesmanDaoEx extends B2bCustomerSalesmanDao{

	int countFilialeAndProductPkIs(Map<String, Object> map);

	int isRepeat(B2bCustomerSalesmanDto cus);

	List<B2bCustomerSalesmanDto> searchProductAndCompanyPKIsnull(Map<String, Object> map);

	List<B2bCustomerSalesmanDto> searchGoodsBySaleMan(Map<String, Object> map);
	
	List<B2bCustomerSalesmanDto> searchMemberBySaleMan(Map<String, Object> map);

	void deleteByMemberPk(String memberPk);

	void deleteByCustomerPk(String customerPk);

	int isRepeatBySx(B2bCustomerSalesmanDto cus);



}
