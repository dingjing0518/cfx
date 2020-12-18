package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.B2bEconomicsBankExtDto;
import cn.cf.model.B2bEconomicsBank;

public interface B2bEconomicsBankExtDao extends B2bEconomicsBankDao{
    int searchEconomicsBankCounts(Map<String, Object> map);
    List<B2bEconomicsBankExtDto> searchEconomicsBankList(Map<String, Object> map);
    B2bEconomicsBank searchEconomicsBankByName(B2bEconomicsBank bank);
    int updateEconomicsBank(B2bEconomicsBank bank);
    int insertEconomicsBank(B2bEconomicsBank bank);
    List<B2bEconomicsBankCompanyDto> searchAuthorizedBanks(Map<String,Object> map);
    int searchAuthorizedBankCounts(Map<String,Object> map);
    void delCompanyBank(Map<String,Object> map);
    void insertCompanyBank(Map<String,Object> map);
	List<B2bEconomicsBankDto> searchListOrderName();
}
