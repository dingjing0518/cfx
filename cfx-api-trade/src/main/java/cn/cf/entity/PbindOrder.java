package cn.cf.entity;


import cn.cf.util.EncodeUtil;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PbindOrder {
    private String logisticsModelPk;// 物流方式
    private String logisticsModelName;// 物流方式
    private String meno;// 备注
    private String addressPk;// 地址
    private String invoicePk;// 发票
    private List<PbindGoods> goods;
    private List<PbindGoods> carts;// 商品集合
    private String purchaserName;//采购商
    private String purchaserPk;//采购商
    private String bindPk;//拼团活动pk
    private int source;//订单来源
    private int purchaseType;//订单类型1自采 2代采 3平台采
    private List<String> cartList;

    public PbindOrder(String value) {
        JSONObject js = JSON.parseObject(value);
        logisticsModelPk = js.containsKey("logisticsModelPk") ? js
                .getString("logisticsModelPk") : "";
        logisticsModelName = EncodeUtil.URLDecode(js.containsKey("logisticsModelName") ? js
                .getString("logisticsModelName") : "");
        meno = js.containsKey("meno") ? js.getString("meno") : "";
        addressPk = js.containsKey("addressPk") ? js.getString("addressPk")
                : "";
        invoicePk = js.containsKey("invoicePk") ? js.getString("invoicePk")
                : "";
        purchaserPk = js.containsKey("purchaserPk") ? js.getString("purchaserPk") : "";
        bindPk = js.containsKey("bindPk") ? js.getString("bindPk") : "";
        purchaserName = EncodeUtil.URLDecode(js.containsKey("purchaserName") ? js
                .getString("purchaserName") : "");
        JSONObject g = js.containsKey("goods")?js.getJSONObject("goods"):null;
        if(null !=g&& g.size() > 0){
            for (int i = 0; i < g.size(); i++) {
                goods.add(new PbindGoods(g));
            }
        }
        carts = new ArrayList<PbindGoods>();
        JSONArray gs = js.containsKey("carts") ? js.getJSONArray("carts")
                : null;
        if (null != gs && gs.size() > 0) {
            for (int i = 0; i < gs.size(); i++) {
                carts.add(new PbindGoods(gs.getJSONObject(i)));
            }
        }
    }

    public String getBindPk() {
        return bindPk;
    }

    public void setBindPk(String bindPk) {
        this.bindPk = bindPk;
    }

    public List<String> getCartList() {
        return cartList;
    }

    public void setCartList(List<String> cartList) {
        this.cartList = cartList;
    }

    public List<PbindGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<PbindGoods> goods) {
        this.goods = goods;
    }

    public List<PbindGoods> getCarts() {
        return carts;
    }

    public void setCarts(List<PbindGoods> carts) {
        this.carts = carts;
    }

    public String getLogisticsModelPk() {
        return logisticsModelPk;
    }

    public String getLogisticsModelName() {
        return logisticsModelName;
    }

    public int getSource() {
        return source;
    }

    public String getMeno() {
        return meno;
    }

    public String getAddressPk() {
        return addressPk;
    }

    public String getInvoicePk() {
        return invoicePk;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public String getPurchaserPk() {
        return purchaserPk;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

}
