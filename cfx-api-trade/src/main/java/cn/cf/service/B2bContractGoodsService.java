package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bContractGoodsDtoEx;

public interface B2bContractGoodsService {

	List<B2bContractGoodsDtoEx> searchListByContractNo(String contractNo,Boolean flag);
	
}
