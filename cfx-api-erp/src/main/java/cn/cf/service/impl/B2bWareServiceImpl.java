package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bWareDaoEx;
import cn.cf.dao.B2bWarehouseNumberDao;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.model.B2bWare;
import cn.cf.service.B2bWareService;
import cn.cf.util.KeyUtils;

@Service
public class B2bWareServiceImpl implements B2bWareService {

	@Autowired
	private B2bWareDaoEx b2bWareDaoEx;
	
	@Autowired
	private B2bWarehouseNumberDao warehouseNumberDao; 
	
	@Override
	public B2bWare getByName(Map<String, Object> map) {
		B2bWare b2bWare = null;
		List<B2bWare> list = b2bWareDaoEx.getByName(map);
		if (null!=list && list.size()>0) {
			b2bWare = list.get(0);
		}
		return b2bWare;
	}

	@Override
	@Transactional
	public String createNewWare(String wareName, String wareCode, String wareAddress,String storePk){
		String pk = KeyUtils.getUUID();
		B2bWare b2bWare = new B2bWare();
		b2bWare.setName(wareName);
		b2bWare.setStorePk(storePk);
		b2bWare.setPk(pk);
		b2bWare.setAddress(wareAddress);
		b2bWare.setWareCode(wareCode);
		b2bWare.setInsertTime(new Date());
		b2bWare.setIsDelete(1);
		b2bWareDaoEx.insert(b2bWare);
		return pk;
	}

	@Override
	public B2bWarehouseNumberDto getOneByNumber(B2bGoodsDto gdto) {
		B2bWarehouseNumberDto wareNum =new B2bWarehouseNumberDto();
		B2bGoodsDtoMa goodsDtoMa = new B2bGoodsDtoMa();
		goodsDtoMa.UpdateMine(gdto);
		CfGoods cfGoods = goodsDtoMa.getCfGoods();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("number", cfGoods.getWarehouseNumber());
		map.put("storePk", gdto.getStorePk());
		map.put("isDelete",1 );
		map.put("start",0 );
		map.put("limit",1 );
		List<B2bWarehouseNumberDto> list=warehouseNumberDao.searchList(map);
		if(list.size()>0){
			  wareNum=list.get(0);
		}
		return wareNum;
	}

}
