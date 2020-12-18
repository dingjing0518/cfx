package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.B2bWareDaoEx;
import cn.cf.dao.B2bWarehouseNumberDao;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWareDtoEx;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.model.B2bWare;
import cn.cf.service.B2bWareService;

@Service
public class B2bWareServiceImpl implements B2bWareService {
	
	@Autowired
	private B2bWareDaoEx b2bWareDaoEx;
	
	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;
    
	@Autowired
	private B2bWarehouseNumberDao warehouseNumberDao;
	@Override
	public Integer update(B2bWare model) {
		return b2bWareDaoEx.update(model);
	}

	@Override
	public B2bWareDto isWareRepeated(Map<String, Object> map) {
		return b2bWareDaoEx.isWareRepeated(map);
	}

	@Override
	public int insert(B2bWare model) {
		return b2bWareDaoEx.insert(model);
	}

	@Override
	public PageModel<B2bWareDtoEx> searchWareList(Map<String, Object> map) {
		PageModel<B2bWareDtoEx> pm = new PageModel<B2bWareDtoEx>();
		List<B2bWareDtoEx> list = b2bWareDaoEx.searchWareGrid(map);
		int count = b2bWareDaoEx.searchGridCount(map);
		if (Integer.parseInt(map.get("limit").toString()) > 0) {
			for (B2bWareDtoEx b : list) {
			b.setType( isDeleteWare(b.getPk()));
			}
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		pm.setTotalCount(count);
		pm.setDataList(list);
		
		return pm;
	}

	private Integer isDeleteWare(String warePk) {
		Integer type = null;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("isDelete", 1);
		m.put("warePk",warePk);
		int result = b2bGoodsDaoEx.searchGridCount(m);
		if (result > 0) {
			type =1;// 1商品关联,2商品未关联
		} else {
			type =2;
		}
		return type;
	}

	@Override
	public B2bWareDto getByPk(String pk) {
		return b2bWareDaoEx.getByPk(pk);
	}

	@Override
	public B2bWarehouseNumberDto getOneByNumber(B2bGoodsDto gdto) {
		B2bWarehouseNumberDto wareNum =new B2bWarehouseNumberDto();
		B2bGoodsDtoMa ogm = new B2bGoodsDtoMa();
		ogm.UpdateMine(gdto);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("number", ogm.getCfGoods().getWarehouseNumber());
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
