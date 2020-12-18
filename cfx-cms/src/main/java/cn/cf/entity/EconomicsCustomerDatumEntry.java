package cn.cf.entity;

import java.util.List;

public class EconomicsCustomerDatumEntry {

	private String companyPlace;
	
	private Double capacity;
	
	private List<EconCustDatumRuleEntry> rule;
	
	private String productionType;
	
	

	public String getProductionType() {
		return productionType;
	}

	public void setProductionType(String productionType) {
		this.productionType = productionType;
	}

	public String getCompanyPlace() {
		return companyPlace;
	}

	public void setCompanyPlace(String companyPlace) {
		this.companyPlace = companyPlace;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public List<EconCustDatumRuleEntry> getRule() {
		return rule;
	}

	public void setRule(List<EconCustDatumRuleEntry> rule) {
		this.rule = rule;
	}
	
	
}
