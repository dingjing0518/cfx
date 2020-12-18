package cn.cf.service.platform.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bCreditDaoEx;
import cn.cf.dao.B2bLoanNumberDaoEx;
import cn.cf.dao.SysCompanyBankcardDaoEx;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.service.platform.B2bCompanyBankcardService;

@Service
public class B2bCompanyBankcardServiceImpl  implements B2bCompanyBankcardService {
	
	@Autowired
	private SysCompanyBankcardDaoEx bankcardDaoEx;
	
	@Autowired
	private B2bCreditDaoEx b2bCreditDaoEx;
	
	@Autowired
	private B2bLoanNumberDaoEx b2bLoanNumberDaoEx;
	
	@Override
	public SysCompanyBankcardDto getCompanyBankCard(String supplierPk,String bankPk) {
		SysCompanyBankcardDto dto = null;
		List<SysCompanyBankcardDto> list = bankcardDaoEx.getCompanyBankcard(supplierPk);
		if(null != list && list.size()>0){
				if(null != bankPk){
					for (int i = 0; i < list.size(); i++) {
						//根据采购商的授信银行匹配供应商收款账户
						if( null != list.get(i).getEcbankPk() && null != bankPk
								&& !"".equals(bankPk) && bankPk.equals(list.get(i).getEcbankPk())){
							dto = list.get(i);
							break;
						}else if(null == list.get(i).getEcbankPk() || "".equals(list.get(i).getEcbankPk())){
							dto = list.get(i);
						}
					}
				}
				//如果未匹配到对应的则随机取一个收款账户
				if(null == dto){
					dto = list.get(0);
				}
		}
		return dto;
	}

	@Override
	public List<SysCompanyBankcardDto> getCompanyBankCardList(String companyPk) {
		// TODO Auto-generated method stub
		return bankcardDaoEx.getCompanyBankcard(companyPk);
	}
}
