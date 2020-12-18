package cn.cf.entity;

public class BtnTree {
    private String pk;
    private String name;
    private String displayName;
    private String parentPk;
    private Integer isBtn;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParentPk() {
        return parentPk;
    }

    public void setParentPk(String parentPk) {
        this.parentPk = parentPk;
    }

    public Integer getIsBtn() {
        return isBtn;
    }

    public void setIsBtn(Integer isBtn) {
        this.isBtn = isBtn;
    }
}
