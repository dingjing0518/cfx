/**
 * 
 */
package cn.cf.entity;


import cn.cf.dto.B2bMemberDto;

import com.alibaba.fastjson.JSONObject;


/**
 * @author bin
 * 
 */
public class Cgoods implements  Cloneable{
	private String goodsPk;
	private B2bGoodsDtoMa goodsDto;
	private B2bMemberDto memberDto;//业务员
	private String logisticsPk;//物流模板Pk
	private String logisticsStepInfoPk;//阶梯价格Pk
	private Double freight;//运费单价
	private Double totalFreight;//总运费
	private Integer boxes;//箱数
    private Double weight;//吨数
    private Double contractPrice;//合同价
	private Double loadFeePrice;//装车费单价
	private Double adminFeePrice;//管理费单价
	private String packNumber;
	private String logisticsCarrierPk;
	private String logisticsCarrierName;
	private String warehouseNumber;
	private String warehouseType;
	public Cgoods(JSONObject js) {
		goodsPk = js.containsKey("goodsPk") ? js.getString("goodsPk") : "";
		boxes = js.containsKey("boxes") ? js.getInteger("boxes") : 0;
		logisticsPk = js.containsKey("logisticsPk") ? js
				.getString("logisticsPk") : "";
		logisticsStepInfoPk = js.containsKey("logisticsStepInfoPk") ? js
				.getString("logisticsStepInfoPk") : "";
		weight = js.containsKey("weight") ? js.getDouble("weight") : 0d;				
		contractPrice = js.containsKey("contractPrice") ? js.getDouble("contractPrice") : 0d;				
		loadFeePrice = js.containsKey("loadFeePrice") ? js.getDouble("loadFeePrice") : 0d;				
		freight = (js.containsKey("freight") && !"".equals(js.getString("freight"))) ? js.getDouble("freight") : 0d;				
		totalFreight = (js.containsKey("totalFreight") && !"".equals(js.getString("totalFreight"))) ? js.getDouble("totalFreight") : 0d;				
		adminFeePrice = js.containsKey("adminFeePrice") ? js.getDouble("adminFeePrice") : 0d;				
	}
	public Cgoods() {
		// TODO Auto-generated constructor stub
	}
	public String getGoodsPk() {
		return goodsPk;
	}
	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public B2bGoodsDtoMa getGoodsDto() {
		return goodsDto;
	}
	public void setGoodsDto(B2bGoodsDtoMa goodsDto) {
		this.goodsDto = goodsDto;
	}
	public String getLogisticsPk() {
		return logisticsPk;
	}
	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	public String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}
	public void setLogisticsStepInfoPk(String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}
	public Double getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}
	public Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getContractPrice() {
		return contractPrice;
	}
	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	public Double getLoadFeePrice() {
		return loadFeePrice;
	}
	public void setLoadFeePrice(Double loadFeePrice) {
		this.loadFeePrice = loadFeePrice;
	}
	public Double getAdminFeePrice() {
		return adminFeePrice;
	}
	public void setAdminFeePrice(Double adminFeePrice) {
		this.adminFeePrice = adminFeePrice;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public B2bMemberDto getMemberDto() {
		return memberDto;
	}
	public void setMemberDto(B2bMemberDto memberDto) {
		this.memberDto = memberDto;
	}
	public String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}
	
	public String getLogisticsCarrierPk() {
		return logisticsCarrierPk;
	}
	public void setLogisticsCarrierPk(String logisticsCarrierPk) {
		this.logisticsCarrierPk = logisticsCarrierPk;
	}
	public String getLogisticsCarrierName() {
		return logisticsCarrierName;
	}
	public void setLogisticsCarrierName(String logisticsCarrierName) {
		this.logisticsCarrierName = logisticsCarrierName;
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
	public String getWarehouseNumber() {
		return warehouseNumber;
	}
	public void setWarehouseNumber(String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}
	public String getWarehouseType() {
		return warehouseType;
	}
	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	
	
}
