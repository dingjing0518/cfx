package cn.cf.entity;

import java.util.List;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bContractDtoEx;

public class CallBackContract {

	private RestCode code;
	
	private String rest;
	
    private List<B2bContractDtoEx> oList;

	public RestCode getCode() {
		return code;
	}

	public void setCode(RestCode code) {
		this.code = code;
	}

	public List<B2bContractDtoEx> getoList() {
		return oList;
	}

	public void setoList(List<B2bContractDtoEx> oList) {
		this.oList = oList;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}
   
    
}
