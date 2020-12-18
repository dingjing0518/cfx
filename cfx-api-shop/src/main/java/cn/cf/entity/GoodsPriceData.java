package cn.cf.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GoodsPriceData {
    private String date;
    private Double price;
    private Integer isUpDown;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIsUpDown() {
        return isUpDown;
    }

    public void setIsUpDown(Integer isUpDown) {
        this.isUpDown = isUpDown;
    }
}
