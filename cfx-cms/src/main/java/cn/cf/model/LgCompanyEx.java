package cn.cf.model;


public class LgCompanyEx  extends LgCompany{
    private String mobile;
    private String memberPk;
    private String password;
    private String parantPk;//lg_member表下的parantPk
    

    public String getMemberPk() {
        return memberPk;
    }

    public void setMemberPk(String memberPk) {
        this.memberPk = memberPk;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public String getParantPk() {
		return parantPk;
	}

	public void setParantPk(String parantPk) {
		this.parantPk = parantPk;
	}
}
