package cn.cf.service.bill;

import java.util.List;

import cn.cf.dto.B2bBillSigncardDto;

public interface BillSigncardService {

	List<B2bBillSigncardDto> getByCompanyAndCard(String companyPk,Integer status,String account,String bankNo);
	
	void insert(B2bBillSigncardDto dto);
	
	void update(List<B2bBillSigncardDto> list,Integer status);
	
	void insertOrUpdate(B2bBillSigncardDto dto);
	
	B2bBillSigncardDto getCompanyBankCard(String companyPk,String bankPk);
}
