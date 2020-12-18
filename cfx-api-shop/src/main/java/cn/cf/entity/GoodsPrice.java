package cn.cf.entity;


import java.util.List;

public class GoodsPrice {

    private String pk;

    private List<GoodsPriceData> data;


    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public List<GoodsPriceData> getData() {
        return data;
    }

    public void setData(List<GoodsPriceData> data) {
        this.data = data;
    }
}
