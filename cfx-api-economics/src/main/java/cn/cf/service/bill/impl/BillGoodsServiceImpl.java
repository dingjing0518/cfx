package cn.cf.service.bill.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBillGoodsDao;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.model.B2bBillGoods;
import cn.cf.service.bill.BillGoodsService;

@Service
public class BillGoodsServiceImpl implements BillGoodsService {
	@Autowired
	private B2bBillGoodsDao billGoodsDao;

	@Override
	public B2bBillGoodsDto getByPk(String pk) {
		// TODO Auto-generated method stub
		return billGoodsDao.getByPk(pk);
	}

	@Override
	public List<B2bBillGoodsDto> searchBillGoods(Map<String, Object> map) {
		map.put("isVisable",1);
		map.put("isDelete", 1);
		return billGoodsDao.searchList(map);
	}

	@Override
	public B2bBillGoodsDto getByShotName(String shotName) {
		// TODO Auto-generated method stub
		return billGoodsDao.getByShotName(shotName);
	}

	@Override
	public void update(B2bBillGoods goods) {
		// TODO Auto-generated method stub
		billGoodsDao.update(goods);
	}

}
