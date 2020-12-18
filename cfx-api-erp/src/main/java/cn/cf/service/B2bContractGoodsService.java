package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bContractGoodsDto;

public interface B2bContractGoodsService {

	List<B2bContractGoodsDto> getB2bContractGoods(String contractNo);
}
