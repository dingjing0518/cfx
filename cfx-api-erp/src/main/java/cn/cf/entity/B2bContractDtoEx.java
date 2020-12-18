package cn.cf.entity;

import java.util.List;

import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractGoodsDto;

public class B2bContractDtoEx extends B2bContractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<B2bContractGoodsDto> contractGoodsList;

	public List<B2bContractGoodsDto> getContractGoodsList() {
		return contractGoodsList;
	}

	public void setContractGoodsList(List<B2bContractGoodsDto> contractGoodsList) {
		this.contractGoodsList = contractGoodsList;
	}
	
	
	
	
}
