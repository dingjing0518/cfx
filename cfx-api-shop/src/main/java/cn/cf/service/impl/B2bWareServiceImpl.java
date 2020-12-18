package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bWareDaoEx;
import cn.cf.dao.B2bWarehouseNumberDaoEx;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWareDtoEx;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.model.B2bWare;
import cn.cf.model.B2bWarehouseNumber;
import cn.cf.service.B2bWareService;
import cn.cf.service.CommonService;
import cn.cf.util.KeyUtils;

@Service
public class B2bWareServiceImpl implements B2bWareService {
	
	@Autowired
	private B2bWareDaoEx b2bWareDaoEx;
	
 
	
	@Autowired 
	private CommonService commonService;
	
	 
	@Autowired
	private B2bWarehouseNumberDaoEx numberDaoEx;

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
				b.setType(commonService.isDeleteWare(b.getPk()));
			}
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		pm.setTotalCount(count);
		pm.setDataList(list);
		return pm;
	}

//	private Integer isDeleteWare(String warePk) {
//		Integer type = null;
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("isDelete", 1);
//		m.put("warePk",warePk);
//		int result = b2bGoodsDaoEx.searchGridCount(m);
//		if (result > 0) {
//			type =1;// 1商品关联,2商品未关联
//		} else {
//			type =2;
//		}
//		return type;
//	}

	@Override
	public B2bWareDto getByPk(String pk) {
		return b2bWareDaoEx.getByPk(pk);
	}

	@Override
	public int addWareNumber(B2bWarehouseNumber model) {
		model.setPk(KeyUtils.getUUID());
		model.setIsDelete(1);
		return numberDaoEx.insert(model);
	}

	@Override
	public int updateWareNumber(B2bWarehouseNumber model) {
		// TODO Auto-generated method stub
		return numberDaoEx.update(model);
	}

	@Override
	public PageModel<B2bWarehouseNumberDto> searchWareNumberList(Map<String, Object> map) {
		map.put("isDelete", 1);
		PageModel<B2bWarehouseNumberDto> pm = new PageModel<B2bWarehouseNumberDto>();
		List<B2bWarehouseNumberDto> list = numberDaoEx.searchGrid(map);
		int count = numberDaoEx.searchGridCount(map);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		pm.setTotalCount(count);
		pm.setDataList(list);
		
		return pm;
	}

	@Override
	public B2bWarehouseNumberDto searchWareNumberByPk(String pk) {
		// TODO Auto-generated method stub
		return numberDaoEx.getByPk(pk);
	}

	@Override
	public int selectEntity(B2bWarehouseNumberDto dto) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("type", dto.getType());
		m.put("number", dto.getNumber());
		m.put("storePk", dto.getStorePk());
		return numberDaoEx.selectEntity(m);
	}



}
