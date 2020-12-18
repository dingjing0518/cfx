package cn.cf.service;

import cn.cf.dto.B2bContractDto;
import cn.cf.entity.B2bContractDtoEx;

public interface B2bContractService {
	
	String getContractNo(String contractNo);
	
	B2bContractDto getByContractNo(String contractNo);
	
	B2bContractDtoEx getContractDtoEx(String contractNo);
}
