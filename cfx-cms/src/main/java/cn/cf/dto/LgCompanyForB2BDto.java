package cn.cf.dto;


public class LgCompanyForB2BDto extends BaseDTO implements java.io.Serializable{
    private static final long serialVersionUID = 5454155825314635342L;
    private String pk;
    private String carrierName;
    private Integer isDelete;
    private Integer isVisable;

    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public String getCarrierName() {
        return carrierName;
    }
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public Integer getIsVisable() {
        return isVisable;
    }
    public void setIsVisable(Integer isVisable) {
        this.isVisable = isVisable;
    }



}
