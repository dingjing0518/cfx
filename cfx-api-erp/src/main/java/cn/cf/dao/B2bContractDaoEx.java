package cn.cf.dao;

import cn.cf.dto.B2bContractDto;
import cn.cf.entity.B2bContractDtoEx;

public interface B2bContractDaoEx extends B2bContractDao{

	/**
	 * 根据合同单号查询合同
	 * @param contractNo 合同单号
	 * @return
	 */
	B2bContractDtoEx getContractByNo(String contractNo);
	
	/**
	 * 更新合同信息
	 * @param contractDto 合同对象
	 * @return
	 */
	int updateContractDto(B2bContractDto contractDto);
	
	
}
