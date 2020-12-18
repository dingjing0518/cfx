package cn.cf.service.platform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bCreditGoodsDaoEx;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.service.platform.B2bCreditGoodsService;

@Service
public class B2bCreditGoodsServiceImpl implements B2bCreditGoodsService {
	
	@Autowired
	private B2bCreditGoodsDaoEx b2bCreditGoodsDao;
	
	@Override
	public List<B2bCreditGoodsDtoMa> searchCreditGoodsByCompany(String companyPk) {
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("status", 2);
		map.put("endTime", "true");
		return b2bCreditGoodsDao.getCreditGoods(map);
	}

	@Override
	public B2bCreditGoodsDtoMa searchCreditGoodsByPk(String pk) {
		Map<String,Object> map= new HashMap<String, Object>();
		B2bCreditGoodsDtoMa dto = null;
		map.put("pk",pk);
		map.put("status", 2);
		map.put("endTime", "true");
		map.put("startTime", "true");
		List<B2bCreditGoodsDtoMa> list = b2bCreditGoodsDao.getCreditGoods(map);
		if(null != list && list.size()>0){
			dto = list.get(0);
		}
		return dto;
	}

	@Override
	public List<B2bCreditGoodsDto> searchList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return b2bCreditGoodsDao.searchList(map);
	}

	@Override
	public void updateByCreditGoods(B2bCreditGoodsDto dto) {
		b2bCreditGoodsDao.updateByCreditGoods(dto);
		
	}

	@Override
	public List<B2bCreditGoodsDtoMa> searchAllCreditGoodsByCompany(
			String companyPk) {
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		return b2bCreditGoodsDao.getEconomicsGoods(map);
	}

	@Override
	public List<B2bCreditGoodsDto> searchListGroupBank(String companyPk) {
		// TODO Auto-generated method stub
		return b2bCreditGoodsDao.getCreditGroupBank(companyPk);
	}

}
