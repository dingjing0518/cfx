package cn.cf.service.platform.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bCreditDaoEx;
import cn.cf.dao.B2bEconomicsCreditcustomerDao;
import cn.cf.dao.B2bEconomicsCustomerDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.B2bCreditDtoMa;
import cn.cf.entity.CreditInfo;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bEconomicsCreditcustomer;
import cn.cf.service.platform.B2bCreditService;
import cn.cf.util.KeyUtils;

@Service
public class B2bCreditServiceImpl implements B2bCreditService {
	
	@Autowired
	private B2bCreditDaoEx b2bCreditDao;
	
	@Autowired
	private B2bEconomicsCustomerDao b2bEconomicsCustomerDao;
	
	@Autowired
	private B2bEconomicsCreditcustomerDao b2bEconomicsCreditcustomerDao;
	
	@Override
	public B2bCreditDto getCredit(String companyPk,Integer creditStatus) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		if(null != creditStatus){
			map.put("creditStatus", creditStatus);
		}
		List<B2bCreditDto> list =  b2bCreditDao.searchList(map);
		B2bCreditDto dto = null;
		if(null != list && list.size()>0){
			dto =  list.get(0);
		}
		return dto;
	}

	@Override
	public int addCredit(B2bCreditDtoEx b2bcredit,B2bCompanyDto company) {
		int result = 0;
		B2bCreditDto creditDto = this.getCredit(company.getPk(),null);
		B2bEconomicsCreditcustomer customer = new B2bEconomicsCreditcustomer();
		customer.setPk(KeyUtils.getUUID());
		customer.setCompanyPk(company.getPk());
		customer.setCompanyName(company.getName());
		customer.setContacts(b2bcredit.getCreditContacts());
		customer.setContactsTel(b2bcredit.getCreditContactsTel());
		customer.setAssiFlag(1);
		customer.setStatus(1);
		customer.setInsertTime(new Date());
		customer.setProductPks(b2bcredit.getProductPks());
		customer.setGoodsName(b2bcredit.getGoodsNames());
		B2bEconomicsCustomerDto economicsCustomer =  b2bEconomicsCustomerDao.getByCompanyPk(company.getPk());
		if(null != economicsCustomer){
			customer.setAssiFlag(2);//不需要分配金融专员
			customer.setAssidirPk(economicsCustomer.getAssidirPk());
			customer.setAssidirName(economicsCustomer.getAssidirName());
			customer.setSource(economicsCustomer.getSource());
		}
		result=b2bEconomicsCreditcustomerDao.insert(customer);
		return result;
	}

	@Override
	public int updateByCompany(B2bCreditDtoEx b2bcredit) {
		
		return b2bCreditDao.updateByCompany(b2bcredit);
	}

	@Override
	public List<B2bCreditDto> searchCreditList() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isDelete", 1);
		return b2bCreditDao.searchList(map);
		
	}

	@Override
	public void updateCreditAudit(String companyPk, CreditInfo info) {

		B2bCreditDto dto = b2bCreditDao.getByCompanyPk(companyPk);
		if(null != dto){
			B2bCreditDtoMa cm = new B2bCreditDtoMa();
			cm.UpdateMine(dto);
			CreditInfo ninfo = null;
			if(null !=cm.getInfo()){
				ninfo = cm.getInfo();
				ninfo.setUserId(info.getUserId());
				ninfo.setCustomerNumber(info.getCustomerNumber());
				ninfo.setCreditNumber(info.getCreditNumber());
				ninfo.setTotalAmount(info.getTotalAmount());
			}else{
				ninfo = info;
			}
			B2bCreditDtoEx b2bcredit = new B2bCreditDtoEx();
			b2bcredit.setCompanyPk(companyPk);
			b2bcredit.setCreditInfo(JsonUtils.convertToString(ninfo));
			this.updateByCompany(b2bcredit);
		}
	}

}
