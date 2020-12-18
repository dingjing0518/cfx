package cn.cf.model;

import java.io.Serializable;
import java.util.Date;

public class B2bEconomicsDimensionModel implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    private String pk;
    private String category;
    private Date updateTime;
    private String item;
    private String content;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
