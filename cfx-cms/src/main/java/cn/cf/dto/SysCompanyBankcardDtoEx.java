package cn.cf.dto;

public class SysCompanyBankcardDtoEx extends SysCompanyBankcardDto {
	private String companyName;
	private int status;
	private String companyNameCol;
	private java.lang.String bankAccountCol;
	private java.lang.String bankNameCol;
	private java.lang.String bankNoCol;
	private java.lang.String ecbankNameCol;
	//判断总公司和子公司
	private java.lang.String isCompanyType;

	private String modelType;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNameCol() {
		return companyNameCol;
	}

	public void setCompanyNameCol(String companyNameCol) {
		this.companyNameCol = companyNameCol;
	}

	public String getBankAccountCol() {
		return bankAccountCol;
	}

	public void setBankAccountCol(String bankAccountCol) {
		this.bankAccountCol = bankAccountCol;
	}

	public String getBankNameCol() {
		return bankNameCol;
	}

	public void setBankNameCol(String bankNameCol) {
		this.bankNameCol = bankNameCol;
	}

	public String getBankNoCol() {
		return bankNoCol;
	}

	public void setBankNoCol(String bankNoCol) {
		this.bankNoCol = bankNoCol;
	}

	public String getEcbankNameCol() {
		return ecbankNameCol;
	}

	public void setEcbankNameCol(String ecbankNameCol) {
		this.ecbankNameCol = ecbankNameCol;
	}

	public String getIsCompanyType() {
		return isCompanyType;
	}

	public void setIsCompanyType(String isCompanyType) {
		this.isCompanyType = isCompanyType;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
