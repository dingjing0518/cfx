package cn.cf.entity;

import java.util.List;

public class ContractGoodsSync {
	private String contractNo;
	
	private List<SubContractGoodsSync> items;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public List<SubContractGoodsSync> getItems() {
		return items;
	}

	public void setItems(List<SubContractGoodsSync> items) {
		this.items = items;
	}
	
}
