package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.service.B2bContractGoodsService;

@Service
public class B2bContractGoodsServiceImpl implements B2bContractGoodsService {
	
	@Autowired
	private B2bContractGoodsDaoEx b2bContractGoodsDaoEx;

	@Override
	public List<B2bContractGoodsDto> getB2bContractGoods(String contractNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		return b2bContractGoodsDaoEx.searchListExt(map);
	}

}
