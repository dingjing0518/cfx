package cn.cf.entity;

import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderGoodsDto;

public class ErpChildOrder  {
	
	private String childOrderNumber;
	private String goodsId;
	private String goodsType;
	private String originalPrice;
	private String presentPrice;
	private String originalTotalPrice;
	private String presentTotalPrice;
	private String originalFreight;
	private String presentFreight;
	private String originalTotalFreight;
	private String presentTotalFreight;
	private String purchQuantity;
	private String purchWeight;
	private String productName;
	private String varietiesName;
	private String seedvarietyName;
	private String specName;
	private String seriesName;
	private String specifications;
	private String batchNumber;
	private String gradeName;
	private String chineseName;
	private String wareCode;
	private String wareName;
	private String plantCode;
	private String plantName;
	private String brandName;
	private String packNumber;
	private String status;
	private String unit;
	private String logisticsPk;
	private String logisticsStepInfoPk;
	
	public String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}

	public void setLogisticsStepInfoPk(String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}

	public String getLogisticsPk() {
		return logisticsPk;
	}

	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}

	public String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(String presentPrice) {
		this.presentPrice = presentPrice;
	}

	public String getOriginalTotalPrice() {
		return originalTotalPrice;
	}

	public void setOriginalTotalPrice(String originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}

	public String getPresentTotalPrice() {
		return presentTotalPrice;
	}

	public void setPresentTotalPrice(String presentTotalPrice) {
		this.presentTotalPrice = presentTotalPrice;
	}

	public String getOriginalFreight() {
		return originalFreight;
	}

	public void setOriginalFreight(String originalFreight) {
		this.originalFreight = originalFreight;
	}

	public String getPresentFreight() {
		return presentFreight;
	}

	public void setPresentFreight(String presentFreight) {
		this.presentFreight = presentFreight;
	}

	public String getOriginalTotalFreight() {
		return originalTotalFreight;
	}

	public void setOriginalTotalFreight(String originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}

	public String getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(String presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public String getPurchQuantity() {
		return purchQuantity;
	}

	public void setPurchQuantity(String purchQuantity) {
		this.purchQuantity = purchQuantity;
	}

	public String getPurchWeight() {
		return purchWeight;
	}

	public void setPurchWeight(String purchWeight) {
		this.purchWeight = purchWeight;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVarietiesName() {
		return varietiesName;
	}

	public void setVarietiesName(String varietiesName) {
		this.varietiesName = varietiesName;
	}

	public String getSeedvarietyName() {
		return seedvarietyName;
	}

	public void setSeedvarietyName(String seedvarietyName) {
		this.seedvarietyName = seedvarietyName;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getWareCode() {
		return wareCode;
	}

	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public ErpChildOrder(B2bOrderGoodsDto b2bOrderGoodsDto,B2bOrderDto b2bOrderDto,B2bGoodsDtoEx goodsDto){
		B2bOrderGoodsDtoMa orderGoodsDtoMa = new B2bOrderGoodsDtoMa();
		orderGoodsDtoMa.UpdateMine(b2bOrderGoodsDto);
		CfGoods cfGoods =orderGoodsDtoMa.getCfGoods();
		this.childOrderNumber = b2bOrderGoodsDto.getChildOrderNumber();
		this.goodsId = cfGoods.getPk();
		if(goodsDto != null){
			this.goodsType = goodsDto.getType();
		}
		this.originalPrice = b2bOrderGoodsDto.getOriginalPrice() == null?"0.0":String.valueOf(b2bOrderGoodsDto.getOriginalPrice());
		this.presentPrice = b2bOrderGoodsDto.getPresentPrice() == null?"0.0":String.valueOf(b2bOrderGoodsDto.getPresentPrice());
		this.originalFreight = b2bOrderGoodsDto.getOriginalFreight() == null?"0.0":String.valueOf(b2bOrderGoodsDto.getOriginalFreight());
		this.presentFreight = b2bOrderGoodsDto.getPresentFreight() == null?"0.0":String.valueOf(b2bOrderGoodsDto.getPresentFreight());
		this.originalTotalFreight = b2bOrderGoodsDto.getOriginalTotalFreight() == null?"0.0":String.valueOf(b2bOrderGoodsDto.getOriginalTotalFreight());
		this.presentTotalFreight = b2bOrderGoodsDto.getPresentTotalFreight() == null?"0.0":String.valueOf(b2bOrderGoodsDto.getPresentTotalFreight());
		this.purchQuantity = b2bOrderGoodsDto.getBoxes() == null?null:String.valueOf(b2bOrderGoodsDto.getBoxes());
		this.purchWeight = b2bOrderGoodsDto.getWeight() == null?null:String.valueOf(b2bOrderGoodsDto.getWeight());
		this.productName = cfGoods.getProductName();
		this.varietiesName =cfGoods.getVarietiesName();
		this.seedvarietyName = cfGoods.getSeedvarietyName();
		this.specName = cfGoods.getSpecName();
		this.seriesName = cfGoods.getSeriesName();
		this.specifications = cfGoods.getSpecifications();
		this.batchNumber =cfGoods.getBatchNumber();
		this.gradeName = cfGoods.getGradeName();
		this.chineseName = cfGoods.getGradeChineseName();
		this.wareCode = cfGoods.getWareCode();
		this.wareName = cfGoods.getWareName();
		this.plantCode =cfGoods.getPlantPk();
		this.plantName =cfGoods.getPlantName();
		this.brandName = goodsDto.getBrandName();
		this.packNumber = cfGoods.getPackNumber();
		this.status = b2bOrderGoodsDto.getOrderStatus() == null?"":String.valueOf(b2bOrderGoodsDto.getOrderStatus());
		this.unit = cfGoods.getUnit() == null?"":String.valueOf(cfGoods.getUnit());
		this.logisticsPk=b2bOrderGoodsDto.getLogisticsPk();
		this.logisticsStepInfoPk=b2bOrderGoodsDto.getLogisticsStepInfoPk();
	
	}
	
}
