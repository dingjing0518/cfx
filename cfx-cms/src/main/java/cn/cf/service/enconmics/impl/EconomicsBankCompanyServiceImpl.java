package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bCreditExtDao;
import cn.cf.dao.B2bEconomicsBankCompanyExtDao;
import cn.cf.dto.B2bCreditGoodsExtDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.service.enconmics.EconomicsBankCompanyService;
import cn.cf.util.StringUtils;

@Service
public class EconomicsBankCompanyServiceImpl implements EconomicsBankCompanyService {

	@Autowired
	private B2bEconomicsBankCompanyExtDao bankCompanyDao;
	
	@Autowired
	private B2bCreditExtDao b2bCreditExtDao;

	@Override
	public B2bEconomicsBankCompanyDto getDtoByCompanyPk(String companyPk) {
		B2bEconomicsBankCompanyDto dto = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		List<B2bEconomicsBankCompanyDto> list = bankCompanyDao.getByCompanyPk(companyPk);
		if (null != list && list.size() > 0) {
			dto = list.get(0);
		}
		return dto;
	}

	@Override
	public int updateCustomerNumber(String companyPk, String customerNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("customerNumber", customerNumber);
		return bankCompanyDao.updateCustomer(map);
	}
	
	@Override
	public List<B2bEconomicsBankCompanyExtDto> countAvailableAmount(Map<String, Object> map) {
		return bankCompanyDao.countAvailableAmount(map);
	}

	@Override
	public List<B2bEconomicsBankCompanyExtDto> searchBankApproveAmount(Map<String, Object> map) {
		return bankCompanyDao.searchBankApproveAmount(map);
	}

	@Override
	public List<B2bEconomicsBankCompanyExtDto> searchAmountByMonth(Map<String, Object> map) {
		return bankCompanyDao.searchAmountByMonth(map);
	}



	@Override
	public boolean updateBankAccountNumber(B2bCreditGoodsExtDto dto) {
		boolean flag = true;
		
		try {
			if (dto.getBankPks()!=""&&dto.getBankPks()!=null) {
				String[] bankPkArr = StringUtils.splitStrs(dto.getBankPks());
				String[] bankAccountNumberArr = StringUtils.splitStrs(dto.getBankAccountNumbers());
				for (int i = 0; i < bankPkArr.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("bankAccountNumber", bankAccountNumberArr[i].equals("null")?"":bankAccountNumberArr[i]);
					map.put("creditPk", dto.getCreditPk());
					map.put("bankPk", bankPkArr[i]);
					b2bCreditExtDao.updateBankAccountNumber(map);
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}


	
}
