package cn.cf.service.bill.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBillSigningDao;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.model.B2bBillSigning;
import cn.cf.service.bill.BillSingingService;

@Service
public class BillSingingServiceImpl implements BillSingingService {
	
	@Autowired
	private B2bBillSigningDao b2bBillSigningDao;
	
	
	@Override
	public B2bBillSigningDto getByCompanyPk(String companyPk) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("isDelete", 1);
		List<B2bBillSigningDto> list = b2bBillSigningDao.searchList(map);
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}


	@Override
	public void updateByDto(B2bBillSigningDto sign) {
		B2bBillSigning signing = new B2bBillSigning();
		signing.UpdateDTO(sign);
		b2bBillSigningDao.update(signing);
	}


	@Override
	public List<B2bBillSigningDto> getByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return b2bBillSigningDao.searchList(map);
	}

}
