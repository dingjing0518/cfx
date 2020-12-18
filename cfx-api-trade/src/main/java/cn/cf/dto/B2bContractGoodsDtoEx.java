package cn.cf.dto;

import java.util.Date;

import cn.cf.constant.Block;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.Cgoods;
import cn.cf.json.JsonUtils;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

public class B2bContractGoodsDtoEx extends B2bContractGoodsDto{
	
	private Double finalPrice;
	private Double finalFreight;
	private Double finalTotalPrice;
	private Double finalTotalFreight;
	private Double finalPackageFee;
	private Double finalLoadFee;
	private Double finalAdminFee;
	private String contractSourceName;
	private String orderStatusName;
	private Integer boxesNoShipped;
	private Double weightNoShipped;
	private String purchaserTypeName;
	private String salesman;
	private String salesmanNumber;
	private String purchaserName;
	private String supplierName;
	private String storeName;
	private String paymentName;
	private String economicsGoodsName;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String toAddress;
	private String contacts;
	private String contactsTel;
	private String remark;
	private String member;
	private Double tareWeight;
	private String companyName;
	private Double basicTotalPrice;
	private Double totalFreight;
	private Double contractTotalPrice;
	private Double totalPrice;
	private String orderWeight;// 已转订单重量
	private String orderBoxes;// 已转订单箱数
	private String purchaserInfo;
	private String addressInfo;
	private String supplierInfo;
	private String address;
	private String plantName;
	private String loadFee;
	private String adminFee;
	private String packFee;
	private String boxesS;
	private String weightS;
	private String boxesShippedS;
	private String weightShippedS;
	private String boxesNoShippedS;
	private String weightNoShippedS;
	private String productName;
	private String varietiesName;
	private String seedvarietyName;
	private String specName;
	private String specifications;
	private String seriesName;
	private String batchNumber;
	private String gradeName;
	private String distinctNumber;
	private String packNumber;
	private String rawMaterialParentName;
	private String rawMaterialName;
	private String technologyName;
	private String materialName;
    
	public String getOrderBoxes() {
		return orderBoxes;
	}

	public void setOrderBoxes(String orderBoxes) {
		this.orderBoxes = orderBoxes;
	}

	public String getBoxesS() {
		return boxesS;
	}

	public void setBoxesS(String boxesS) {
		this.boxesS = boxesS;
	}

	public String getWeightS() {
		return weightS;
	}

	public void setWeightS(String weightS) {
		this.weightS = weightS;
	}

	public String getBoxesShippedS() {
		return boxesShippedS;
	}

	public void setBoxesShippedS(String boxesShippedS) {
		this.boxesShippedS = boxesShippedS;
	}

	public String getWeightShippedS() {
		return weightShippedS;
	}

	public void setWeightShippedS(String weightShippedS) {
		this.weightShippedS = weightShippedS;
	}

	public String getBoxesNoShippedS() {
		return boxesNoShippedS;
	}

	public void setBoxesNoShippedS(String boxesNoShippedS) {
		this.boxesNoShippedS = boxesNoShippedS;
	}

	public String getWeightNoShippedS() {
		return weightNoShippedS;
	}

	public void setWeightNoShippedS(String weightNoShippedS) {
		this.weightNoShippedS = weightNoShippedS;
	}

	public String getLoadFee() {
		return loadFee;
	}

	public void setLoadFee(String loadFee) {
		this.loadFee = loadFee;
	}

	public String getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(String adminFee) {
		this.adminFee = adminFee;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPurchaserInfo() {
		return purchaserInfo;
	}

	public void setPurchaserInfo(String purchaserInfo) {
		this.purchaserInfo = purchaserInfo;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getSupplierInfo() {
		return supplierInfo;
	}

	public void setSupplierInfo(String supplierInfo) {
		this.supplierInfo = supplierInfo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public B2bContractGoodsDtoEx() {
		// TODO Auto-generated constructor stub
	}

	public B2bContractGoodsDtoEx(Cgoods goods,String contractNumber,Integer detailNumber,LogisticsModelDto lmdto) {
		this.setChildOrderNumber(KeyUtils.getchildOrderNumber(contractNumber,detailNumber));
		this.setContractNo(contractNumber);
		this.setDetailNumber(detailNumber*10);
		B2bGoodsDtoMa gdto = goods.getGoodsDto();
		//平台承运不计算自提管理费
		if(null !=lmdto && lmdto.getType() == 2){
			if(Block.CF.getValue().equals(gdto.getBlock())){
				gdto.getCfGoods().setAdminFee(0d);
				gdto.setGoodsInfo(JsonUtils.convertToString(gdto.getCfGoods()));
			}
		}
		this.setBrandName(gdto.getBrandName());
		this.setBoxes(goods.getBoxes());
		if(goods.getWeight()>0d){
			this.setWeight(goods.getWeight());
		}else{
			if(Block.CF.getValue().equals(gdto.getBlock())){
				this.setWeight(ArithUtil.div(ArithUtil.mul(goods.getBoxes(), gdto.getTareWeight()), 1000));
			}else{
				this.setWeight(ArithUtil.mul(goods.getBoxes(), gdto.getTareWeight()));
			}
		}
		this.setBasicPrice(gdto.getSalePrice());
		this.setFreight(null == goods.getFreight()?0d:ArithUtil.round(goods.getFreight(), 2));
		this.setContractPrice(goods.getContractPrice() == null ? 0d : goods.getContractPrice());
		Double totalPrice = (this.getContractPrice()==null?0d:this.getContractPrice());
		if(Block.CF.getValue().equals(gdto.getBlock())){
			totalPrice+= (null==gdto.getCfGoods().getAdminFee()?0d:gdto.getCfGoods().getAdminFee()+
					(null==gdto.getCfGoods().getPackageFee()?0d:gdto.getCfGoods().getPackageFee())+
					(null==gdto.getCfGoods().getLoadFee()?0d:gdto.getCfGoods().getLoadFee()));
		}
		if(null != goods.getTotalFreight() && goods.getTotalFreight()>0 && this.getFreight() ==0d){
			//如果物流费用是最小起步价(总=商品总价+最小起步价)
			this.setTotalPrice(ArithUtil.round(ArithUtil.add(ArithUtil.mul(totalPrice, this.getWeight()), goods.getTotalFreight()), 2));
			
		}else{
			//物流总运费
			Double totalFreight =Block.CF.getValue().equals(gdto.getBlock())?ArithUtil.round(ArithUtil.mul(this.getFreight(), this.getWeight()),2):
				ArithUtil.round(ArithUtil.mul(this.getFreight(), ArithUtil.div(this.getWeight(), 1000)), 2);
			//总价格=总商品价+总运费
			this.setTotalPrice(ArithUtil.add(ArithUtil.round(ArithUtil.mul(totalPrice, this.getWeight()), 2),totalFreight));
		}
		Double d = ArithUtil.sub(gdto.getSalePrice(), goods.getContractPrice());
		this.setDiscount(ArithUtil.round(d, 2));
		this.setCarrier(null);
		this.setIsDelete(1);
		this.setInsertTime(new Date());
		this.setUpdateTime(new Date());
		this.setBoxesShipped(0);
		this.setWeightShipped(0d);
		this.setContractStatus(1);
		this.setLogisticsPk(null==goods.getLogisticsPk()?"":goods.getLogisticsPk());
		this.setLogisticsStepInfoPk(null==goods.getLogisticsStepInfoPk()?"":goods.getLogisticsStepInfoPk());
		this.setGoodsInfo(gdto.getGoodsInfo());
		this.setGoodsPk(gdto.getPk());
		this.setBlock(gdto.getBlock());
		this.setGoodsType(gdto.getType());
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Double getFinalFreight() {
		return finalFreight;
	}

	public void setFinalFreight(Double finalFreight) {
		this.finalFreight = finalFreight;
	}

	public Double getFinalTotalPrice() {
		return finalTotalPrice;
	}

	public void setFinalTotalPrice(Double finalTotalPrice) {
		this.finalTotalPrice = finalTotalPrice;
	}

	public Double getFinalTotalFreight() {
		return finalTotalFreight;
	}

	public void setFinalTotalFreight(Double finalTotalFreight) {
		this.finalTotalFreight = finalTotalFreight;
	}

	public Double getFinalPackageFee() {
		return finalPackageFee;
	}

	public void setFinalPackageFee(Double finalPackageFee) {
		this.finalPackageFee = finalPackageFee;
	}

	 
	public Double getFinalLoadFee() {
		return finalLoadFee;
	}

	public void setFinalLoadFee(Double finalLoadFee) {
		this.finalLoadFee = finalLoadFee;
	}

	 
	public Double getFinalAdminFee() {
		return finalAdminFee;
	}

	public void setFinalAdminFee(Double finalAdminFee) {
		this.finalAdminFee = finalAdminFee;
	}

	 

	public String getContractSourceName() {
		return contractSourceName;
	}

	public void setContractSourceName(String contractSourceName) {
		this.contractSourceName = contractSourceName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public Integer getBoxesNoShipped() {
		return boxesNoShipped;
	}

	public void setBoxesNoShipped(Integer boxesNoShipped) {
		this.boxesNoShipped = boxesNoShipped;
	}

	public Double getWeightNoShipped() {
		return weightNoShipped;
	}

	public void setWeightNoShipped(Double weightNoShipped) {
		this.weightNoShipped = weightNoShipped;
	}

	public String getPurchaserTypeName() {
		return purchaserTypeName;
	}

	public void setPurchaserTypeName(String purchaserTypeName) {
		this.purchaserTypeName = purchaserTypeName;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getSalesmanNumber() {
		return salesmanNumber;
	}

	public void setSalesmanNumber(String salesmanNumber) {
		this.salesmanNumber = salesmanNumber;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getEconomicsGoodsName() {
		return economicsGoodsName;
	}

	public void setEconomicsGoodsName(String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getBasicTotalPrice() {
		return basicTotalPrice;
	}

	public void setBasicTotalPrice(Double basicTotalPrice) {
		this.basicTotalPrice = basicTotalPrice;
	}

	public Double getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}

	public Double getContractTotalPrice() {
		return contractTotalPrice;
	}

	public void setContractTotalPrice(Double contractTotalPrice) {
		this.contractTotalPrice = contractTotalPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderWeight() {
		return orderWeight;
	}

	public void setOrderWeight(String orderWeight) {
		this.orderWeight = orderWeight;
	}

	public String getPackFee() {
		return packFee;
	}

	public void setPackFee(String packFee) {
		this.packFee = packFee;
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

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getDistinctNumber() {
		return distinctNumber;
	}

	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}

	public String getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}

	public String getRawMaterialParentName() {
		return rawMaterialParentName;
	}

	public void setRawMaterialParentName(String rawMaterialParentName) {
		this.rawMaterialParentName = rawMaterialParentName;
	}

	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
}
