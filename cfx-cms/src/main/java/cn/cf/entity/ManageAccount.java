package cn.cf.entity;

import java.util.Date;

public class ManageAccount {
	private String pk;
	private String rolePk;
	private String email;
	private String name;
	private String account;
	private String password;
	private String mobile;
	private int isDelete;
	private Date insertTime;
	private int isVisable;
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getRolePk() {
		return rolePk;
	}

	public void setRolePk(String rolePk) {
		this.rolePk = rolePk;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public int getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(int isVisable) {
		this.isVisable = isVisable;
	}

}
