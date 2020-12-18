package cn.cf.entity;


import com.alibaba.fastjson.JSONObject;

import cn.cf.dto.B2bCartDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGradeDto;
import cn.cf.dto.B2bMemberDto;

public class PbindGoods  implements  Cloneable{
    private String cartPk;
    private String goodsPk;
    private String bindPk;
    private String bindGoodsPk;
    private String logisticsPk;//物流模板Pk
    private String logisticsStepInfoPk;//阶梯价格Pk
    private String linePk;//线路Pk
    private Double totalFreight;//总运费
    private B2bGoodsDto gdto;
    private B2bCartDtoEx cdto;
    private LogisticsStepInfoDto lsdto;
    private B2bGradeDto grade;
    private B2bMemberDto mdto;
    private Integer boxes;
    private String packNumber;
    private String wareCode;
    private Integer totalBoxes;

    public PbindGoods(JSONObject js) {
        cartPk = js.containsKey("cartPk") ? js.getString("cartPk") : "";
        goodsPk = js.containsKey("goodsPk") ? js.getString("goodsPk") : "";
        bindPk = js.containsKey("bindPk") ? js.getString("bindPk") : "";
        bindGoodsPk = js.containsKey("bindGoodsPk") ? js.getString("bindGoodsPk") : "";
        boxes = js.containsKey("boxes") ? js.getInteger("boxes") : 0;
        totalBoxes = this.getBoxes();
        logisticsPk = js.containsKey("logisticsPk") ? js
                .getString("logisticsPk") : "";
        logisticsStepInfoPk = js.containsKey("logisticsStepInfoPk") ? js
                .getString("logisticsStepInfoPk") : "";
        linePk = js.containsKey("linePk") ? js
                .getString("linePk") : "";
    }
    public PbindGoods() {
        // TODO Auto-generated constructor stub
    }
    public B2bGradeDto getGrade() {
        return grade;
    }

    public void setGrade(B2bGradeDto grade) {
        this.grade = grade;
    }

    public LogisticsStepInfoDto getLsdto() {
        return lsdto;
    }

    public void setLsdto(LogisticsStepInfoDto lsdto) {
        this.lsdto = lsdto;
    }

    public String getBindPk() {
        return bindPk;
    }

    public void setBindPk(String bindPk) {
        this.bindPk = bindPk;
    }

    public String getBindGoodsPk() {
        return bindGoodsPk;
    }

    public void setBindGoodsPk(String bindGoodsPk) {
        this.bindGoodsPk = bindGoodsPk;
    }

    public B2bCartDtoEx getCdto() {
        return cdto;
    }

    public void setCdto(B2bCartDtoEx cdto) {
        this.cdto = cdto;
    }

    public B2bGoodsDto getGdto() {
        return gdto;
    }

    public void setGdto(B2bGoodsDto gdto) {
        this.gdto = gdto;
    }

    public String getCartPk() {
        return cartPk;
    }

    public String getGoodsPk() {
        return goodsPk;
    }

    public void setGoodsPk(String goodsPk) {
        this.goodsPk = goodsPk;
    }
    public String getLogisticsPk() {
        return logisticsPk;
    }

    public String getLogisticsStepInfoPk() {
        return logisticsStepInfoPk;
    }

    public B2bMemberDto getMdto() {
        return mdto;
    }

    public void setMdto(B2bMemberDto mdto) {
        this.mdto = mdto;
    }
    public void setCartPk(String cartPk) {
        this.cartPk = cartPk;
    }
    public void setLogisticsPk(String logisticsPk) {
        this.logisticsPk = logisticsPk;
    }
    public void setLogisticsStepInfoPk(String logisticsStepInfoPk) {
        this.logisticsStepInfoPk = logisticsStepInfoPk;
    }
    public Integer getBoxes() {
        return boxes;
    }
    public void setBoxes(Integer boxes) {
        this.boxes = boxes;
    }
    public String getPackNumber() {
        return packNumber;
    }
    public void setPackNumber(String packNumber) {
        this.packNumber = packNumber;
    }
    public String getWareCode() {
        return wareCode;
    }
    public void setWareCode(String wareCode) {
        this.wareCode = wareCode;
    }

    public String getLinePk() {
        return linePk;
    }
    public void setLinePk(String linePk) {
        this.linePk = linePk;
    }

    public Double getTotalFreight() {
        return totalFreight;
    }
    public void setTotalFreight(Double totalFreight) {
        this.totalFreight = totalFreight;
    }
    @Override
    public Object clone()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public Integer getTotalBoxes() {
        return totalBoxes;
    }
    public void setTotalBoxes(Integer totalBoxes) {
        this.totalBoxes = totalBoxes;
    }

}
