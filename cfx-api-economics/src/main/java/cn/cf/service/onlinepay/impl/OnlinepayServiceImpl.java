package cn.cf.service.onlinepay.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bOnlinepayGoodsDao;
import cn.cf.dao.B2bOnlinepayRecordDao;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.model.B2bOnlinepayRecord;
import cn.cf.service.onlinepay.OnlinepayService;

@Service
public class OnlinepayServiceImpl implements OnlinepayService {
	
	@Autowired
	private B2bOnlinepayGoodsDao b2bOnlinepayGoodsDao;
	
	@Autowired
	private B2bOnlinepayRecordDao b2bOnlinepayRecordDao;
	
	@Override
	public List<B2bOnlinepayGoodsDto> searchOnlinePayGoods() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		return b2bOnlinepayGoodsDao.searchList(map);
	}

	@Override
	public B2bOnlinepayRecordDto getByOrderNumer(String orderNumber) {
		// TODO Auto-generated method stub
		return b2bOnlinepayRecordDao.getByOrderNumber(orderNumber);
	}

	@Override
	public void updateOnlineRecord(B2bOnlinepayRecord record) {
		b2bOnlinepayRecordDao.update(record);
	}

	@Override
	public void insertOnlineRecord(B2bOnlinepayRecord record) {
		b2bOnlinepayRecordDao.insert(record);
		
	}

	@Override
	public B2bOnlinepayGoodsDto getByPk(String pk) {
		// TODO Auto-generated method stub
		return b2bOnlinepayGoodsDao.getByPk(pk);
	}

	@Override
	public List<B2bOnlinepayRecordDto> searchList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return b2bOnlinepayRecordDao.searchList(map);
	}

}
