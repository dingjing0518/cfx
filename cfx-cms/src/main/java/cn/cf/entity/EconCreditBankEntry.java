package cn.cf.entity;

/**
 * 客户申请金融产品每种来源每月月份合计;
 * 
 * @author hxh
 *
 */
public class EconCreditBankEntry {
	
	private String typeGroup;
	
	private Integer source;
	
	private String companyPk;

	public String getTypeGroup() {
		return typeGroup;
	}

	public void setTypeGroup(String typeGroup) {
		this.typeGroup = typeGroup;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	
}
