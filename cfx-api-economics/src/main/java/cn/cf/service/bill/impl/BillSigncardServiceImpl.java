package cn.cf.service.bill.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBillSigncardDaoEx;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.model.B2bBillSigncard;
import cn.cf.service.bill.BillSigncardService;
import cn.cf.util.KeyUtils;

@Service
public class BillSigncardServiceImpl implements BillSigncardService {
	
	@Autowired
	private B2bBillSigncardDaoEx b2bBillSigncardDao;
	
	@Override
	public List<B2bBillSigncardDto> getByCompanyAndCard(String companyPk,Integer status,
			String account, String bankNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		map.put("account", account);
		map.put("bankNo", bankNo);
		map.put("status", status);
		return b2bBillSigncardDao.searchList(map);
	}

	@Override
	public void insert(B2bBillSigncardDto dto) {
		B2bBillSigncard card = new B2bBillSigncard();
		card.UpdateDTO(dto);
		card.setPk(KeyUtils.getUUID());
		card.setStatus(1);
		b2bBillSigncardDao.insert(card);
	}

	@Override
	public void update(List<B2bBillSigncardDto> list, Integer status) {
		if(null != list && list.size()>0){
			for(B2bBillSigncardDto dto : list){
				dto.setStatus(status);
				b2bBillSigncardDao.updateByDto(dto);
			}
		}
	}

	@Override
	public B2bBillSigncardDto getCompanyBankCard(String companyPk,
			String bankPk) {
		B2bBillSigncardDto dto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		map.put("status", 2);
		List<B2bBillSigncardDto> list = b2bBillSigncardDao.searchList(map);
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
	public void insertOrUpdate(B2bBillSigncardDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", dto.getCompanyPk());
		map.put("bankAccount", dto.getBankAccount());
		map.put("bankNo", dto.getBankNo());
		List<B2bBillSigncardDto> list = b2bBillSigncardDao.searchList(map);
		if(dto.getStatus() == 2){
			if(null != list && list.size()>0){
				b2bBillSigncardDao.updateByDto(dto);
			}else{
				B2bBillSigncard card = new B2bBillSigncard();
				card.UpdateDTO(dto);
				card.setPk(KeyUtils.getUUID());
				b2bBillSigncardDao.insert(card);
			}
		}
		if(dto.getStatus() == 0){
			if(null != list && list.size()>0){
				for(B2bBillSigncardDto d : list){
					b2bBillSigncardDao.delete(d.getPk());
				}
			}
		}
	}

}
