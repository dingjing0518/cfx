package cn.cf.entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class B2bSysRegions {
    @Id
    private String id;
    private String pk;
    private String name;
    private String parentPk;
    private String isDelete;
    private String isVisable;
    private List<B2bSysRegions> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getParentPk() {
        return parentPk;
    }

    public void setParentPk(String parentPk) {
        this.parentPk = parentPk;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsVisable() {
        return isVisable;
    }

    public void setIsVisable(String isVisable) {
        this.isVisable = isVisable;
    }

    public List<B2bSysRegions> getChildren() {
        return children;
    }

    public void setChildren(List<B2bSysRegions> children) {
        this.children = children;
    }

}
