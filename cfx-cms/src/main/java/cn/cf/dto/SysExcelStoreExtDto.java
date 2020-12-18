package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysExcelStoreExtDto extends SysExcelStoreDto {

    private String insertTimeStart;

    private String insertTimeEnd;

    private String accountName;

    private String account;

    private String roleName;

    private String rolePk;

    private java.util.Date preTimes ;

    public String getInsertTimeStart() {
        return insertTimeStart;
    }

    public void setInsertTimeStart(String insertTimeStart) {
        this.insertTimeStart = insertTimeStart;
    }

    public String getInsertTimeEnd() {
        return insertTimeEnd;
    }

    public void setInsertTimeEnd(String insertTimeEnd) {
        this.insertTimeEnd = insertTimeEnd;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRolePk() {
        return rolePk;
    }

    public void setRolePk(String rolePk) {
        this.rolePk = rolePk;
    }

    public Date getPreTimes() {
        return preTimes;
    }

    public void setPreTimes(Date preTimes) {
        this.preTimes = preTimes;
    }
}