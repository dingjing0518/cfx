package cn.cf.dao;


import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditExtDto;
import cn.cf.dto.B2bTaxAuthorityDto;
import cn.cf.entity.EconCreditBankEntry;
import cn.cf.model.B2bCredit;

public interface B2bCreditExtDao {
    int update(B2bCreditExtDto model);
    int creditUpdate(Map<String, Object> map);
    int delete(String id);
    List<B2bCreditExtDto> searchGrid(Map<String, Object> map);
    List<B2bCreditDto> searchList(Map<String, Object> map);
    int searchGridCount(Map<String, Object> map);
    B2bCreditDto getByPk(java.lang.String pk);
    B2bCreditDto getByCompanyPk(java.lang.String companyPk);
    B2bCreditDto getByCreditReason(java.lang.String creditReason);
    B2bCreditDto getByCreditContacts(java.lang.String creditContacts);
    B2bCreditDto getByCreditContactsTel(java.lang.String creditContactsTel);
    B2bCreditDto getByCreditAddress(java.lang.String creditAddress);
    List<B2bCreditDto> getCreditExpirationReminder();
    B2bCreditDto getCreditPhoneByPk(String pk);
	int updateCreditStatus(Map<String, Object> map);
	int updateCreditStatusAndRemarks(Map<String, Object> map);
	int updateCreditInfo(Map<String, Object> map);
	int searchGridCountEx(Map<String, Object> map);
	List<B2bCreditExtDto> searchGridEx(Map<String, Object> map);
	
	List<EconCreditBankEntry> searchCreditByBankLastDay(Map<String, Object> map);
	int insertCreditInfo(Map<String, Object> map);
	void updateBankAccountNumber(Map<String, Object> map);

	List<B2bTaxAuthorityDto> getTaxAuthorityCode(Map<String, Object> map);

    int updateCreditOfBillTax(B2bCreditDto dto);
}
