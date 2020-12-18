package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bContractDaoEx;
import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.entity.B2bContractDtoEx;
import cn.cf.entity.ErpContractSync;
import cn.cf.json.JsonUtils;
import cn.cf.service.B2bContractService;

@Service
public class B2bContractServiceImp implements B2bContractService {
	
	@Autowired
	private B2bContractDaoEx b2bContractDaoEx;
	
	@Autowired
	private B2bContractGoodsDaoEx b2bContractGoodsDao;
	
	@Override
	public String getContractNo(String contractNo) {
		B2bContractDtoEx dtoEx =  b2bContractDaoEx.getContractByNo(contractNo);
		String rest = null;
		if(null != dtoEx){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("contractNo", contractNo);
			map.put("isDelete",1);
			List<B2bContractGoodsDto> list = b2bContractGoodsDao.searchListExt(map);
			if(null != list && list.size() >0 ){
				dtoEx.setContractGoodsList(list);
			}
			ErpContractSync erpContractSync = new ErpContractSync(dtoEx, list);
			if(erpContractSync != null){
			rest = JsonUtils.convertToString(erpContractSync);
			}
		}
		return rest;
	}

	@Override
	public B2bContractDto getByContractNo(String contractNo) {

		return b2bContractDaoEx.getByContractNo(contractNo);
	}

	@Override
	public B2bContractDtoEx getContractDtoEx(String contractNo) {
		// TODO Auto-generated method stub
		return b2bContractDaoEx.getContractByNo(contractNo);
	}

	
}
