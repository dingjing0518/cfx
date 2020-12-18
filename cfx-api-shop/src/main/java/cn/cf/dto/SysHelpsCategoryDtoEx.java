package cn.cf.dto;

import java.util.List;

public class SysHelpsCategoryDtoEx extends SysHelpsCategoryDto{

	
	private static final long serialVersionUID = 8306374093906335695L;
	
	private List<SysHelpsDto> helps;

	public List<SysHelpsDto> getHelps() {
		return helps;
	}

	public void setHelps(List<SysHelpsDto> helps) {
		this.helps = helps;
	}
	
	
	

}
