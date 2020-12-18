package cn.cf.dto;

public class B2bCreditGoodsExtDto extends B2bCreditGoodsDto {
    private String startTime;
    private String endTime;

    private String bankAccountNumber;
    private String source;
    private String bank;
    private String bankPk;
    private String financeContacts;
    private String financePk;

    private String creditContacts;
    private String creditContactsTel;
    private String roleName;
    
    //银行额度
    private Double bankContractAmount;
    private String  bankCreditStartTime;
    private String  bankCreditEndTime;
    private Double bankAvailableAmount;
    private String economicsBankCompanyPk;
    
    private String bankAccountNumbers;
    private String bankPks;
    
    
	public String getBankAccountNumbers() {
		return bankAccountNumbers;
	}

	public void setBankAccountNumbers(String bankAccountNumbers) {
		this.bankAccountNumbers = bankAccountNumbers;
	}

	public String getBankPks() {
		return bankPks;
	}

	public void setBankPks(String bankPks) {
		this.bankPks = bankPks;
	}

	public String getEconomicsBankCompanyPk() {
		return economicsBankCompanyPk;
	}

	public void setEconomicsBankCompanyPk(String economicsBankCompanyPk) {
		this.economicsBankCompanyPk = economicsBankCompanyPk;
	}

	public Double getBankAvailableAmount() {
		return bankAvailableAmount;
	}

	public void setBankAvailableAmount(Double bankAvailableAmount) {
		this.bankAvailableAmount = bankAvailableAmount;
	}

	public Double getBankContractAmount() {
		return bankContractAmount;
	}

	public void setBankContractAmount(Double bankContractAmount) {
		this.bankContractAmount = bankContractAmount;
	}

	

	public String getBankCreditStartTime() {
		return bankCreditStartTime;
	}

	public void setBankCreditStartTime(String bankCreditStartTime) {
		this.bankCreditStartTime = bankCreditStartTime;
	}

	public void setBankCreditEndTime(String bankCreditEndTime) {
		this.bankCreditEndTime = bankCreditEndTime;
	}

	

	public String getBankCreditEndTime() {
		return bankCreditEndTime;
	}

	public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreditContacts() {
        return creditContacts;
    }

    public void setCreditContacts(String creditContacts) {
        this.creditContacts = creditContacts;
    }

    public String getCreditContactsTel() {
        return creditContactsTel;
    }

    public void setCreditContactsTel(String creditContactsTel) {
        this.creditContactsTel = creditContactsTel;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankPk() {
        return bankPk;
    }

    public void setBankPk(String bankPk) {
        this.bankPk = bankPk;
    }

    public String getFinanceContacts() {
        return financeContacts;
    }

    public void setFinanceContacts(String financeContacts) {
        this.financeContacts = financeContacts;
    }

    public String getFinancePk() {
        return financePk;
    }

    public void setFinancePk(String financePk) {
        this.financePk = financePk;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
