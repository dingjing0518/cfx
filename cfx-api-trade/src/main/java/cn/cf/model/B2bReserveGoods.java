package cn.cf.model;

import java.math.BigDecimal;

import cn.cf.constant.Block;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.entity.Pgoods;
import cn.cf.entity.SxGoods;
import cn.cf.util.ArithUtil;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bReserveGoods  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String goodsPk;
	private java.lang.String productName;
	private java.lang.String productPk;
	private java.lang.String varietiesName;
	private java.lang.String varietiesPk;
	private java.lang.String specName;
	private java.lang.String specPk;
	private java.lang.String seriesName;
	private java.lang.String seriesPk;
	private java.lang.String distinctNumber;
	private java.lang.String batchNumber;
	private java.lang.String gradeChineseName;
	private java.lang.String gradeName;
	private java.lang.String gradePk;
	private String wareCode;
	private BigDecimal weight;
	private java.lang.Integer boxes;
	
	private java.lang.Double presentPrice;
	private java.lang.Double presentFreight;
	
	private java.lang.String packNumber;
	private java.lang.String logisticsPk;
	private java.lang.String logisticsStepInfoPk;
	private java.lang.Double packageFee;
	private java.lang.Double loadFee;
	private java.lang.Double adminFee;

	private Double presentTotalFreight;
	private Double goodAmount;
	private java.lang.String employeeNumber;
	private java.lang.String employeeName;
	private java.lang.String employeePk;
 
	private java.lang.String supplierName;
	private java.lang.String supplierPk;
	private java.lang.String specifications;
	private String block;
	
	//----------------sx分割线-----------------
	private String technologyPk;
	private String technologyName;
	private String materialName;
	private Integer isBlend;//是否混纺，1.是；2.否
	private String rawMaterialParentPk;//原料一级
	private String rawMaterialParentName;
	private String rawMaterialPk;//原料二级
	private String rawMaterialName;
	
	public B2bReserveGoods (){
		
	}
	
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getTechnologyPk() {
		return technologyPk;
	}

	public void setTechnologyPk(String technologyPk) {
		this.technologyPk = technologyPk;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	 

	public String getRawMaterialPk() {
		return rawMaterialPk;
	}

	public void setRawMaterialPk(String rawMaterialPk) {
		this.rawMaterialPk = rawMaterialPk;
	}

	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

 
	 

	public Integer getIsBlend() {
		return isBlend;
	}

	public void setIsBlend(Integer isBlend) {
		this.isBlend = isBlend;
	}

	public String getRawMaterialParentPk() {
		return rawMaterialParentPk;
	}

	public void setRawMaterialParentPk(String rawMaterialParentPk) {
		this.rawMaterialParentPk = rawMaterialParentPk;
	}

	public String getRawMaterialParentName() {
		return rawMaterialParentName;
	}

	public void setRawMaterialParentName(String rawMaterialParentName) {
		this.rawMaterialParentName = rawMaterialParentName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.lang.String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(java.lang.String specifications) {
		this.specifications = specifications;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	public java.lang.String getSupplierPk() {
		return supplierPk;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	public java.lang.String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public java.lang.String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	public java.lang.String getEmployeePk() {
		return employeePk;
	}
	public void setEmployeePk(java.lang.String employeePk) {
		this.employeePk = employeePk;
	}
 
	public java.lang.String getGoodsPk() {
		return goodsPk;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public java.lang.String getProductPk() {
		return productPk;
	}
	public void setProductPk(java.lang.String productPk) {
		this.productPk = productPk;
	}
	public java.lang.String getVarietiesName() {
		return varietiesName;
	}
	public void setVarietiesName(java.lang.String varietiesName) {
		this.varietiesName = varietiesName;
	}
	public java.lang.String getVarietiesPk() {
		return varietiesPk;
	}
	public void setVarietiesPk(java.lang.String varietiesPk) {
		this.varietiesPk = varietiesPk;
	}
	public java.lang.String getSpecName() {
		return specName;
	}
	public void setSpecName(java.lang.String specName) {
		this.specName = specName;
	}
	public java.lang.String getSpecPk() {
		return specPk;
	}
	public void setSpecPk(java.lang.String specPk) {
		this.specPk = specPk;
	}
	public java.lang.String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(java.lang.String seriesName) {
		this.seriesName = seriesName;
	}
	public java.lang.String getSeriesPk() {
		return seriesPk;
	}
	public void setSeriesPk(java.lang.String seriesPk) {
		this.seriesPk = seriesPk;
	}
	public java.lang.String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(java.lang.String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}
	public java.lang.String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(java.lang.String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public java.lang.String getGradeChineseName() {
		return gradeChineseName;
	}
	public void setGradeChineseName(java.lang.String gradeChineseName) {
		this.gradeChineseName = gradeChineseName;
	}
	public java.lang.String getGradeName() {
		return gradeName;
	}
	public void setGradeName(java.lang.String gradeName) {
		this.gradeName = gradeName;
	}
	public java.lang.String getGradePk() {
		return gradePk;
	}
	public void setGradePk(java.lang.String gradePk) {
		this.gradePk = gradePk;
	}
 
	public java.lang.Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(java.lang.Integer boxes) {
		this.boxes = boxes;
	}
	public java.lang.Double getPresentPrice() {
		return presentPrice;
	}
	public void setPresentPrice(java.lang.Double presentPrice) {
		this.presentPrice = presentPrice;
	}
	public java.lang.Double getPresentFreight() {
		return presentFreight;
	}
	public void setPresentFreight(java.lang.Double presentFreight) {
		this.presentFreight = presentFreight;
	}
	public java.lang.String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(java.lang.String packNumber) {
		this.packNumber = packNumber;
	}
	public java.lang.String getLogisticsPk() {
		return logisticsPk;
	}
	public void setLogisticsPk(java.lang.String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	public java.lang.String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}
	public void setLogisticsStepInfoPk(java.lang.String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}
	public java.lang.Double getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(java.lang.Double packageFee) {
		this.packageFee = packageFee;
	}
	public java.lang.Double getLoadFee() {
		return loadFee;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public void setLoadFee(java.lang.Double loadFee) {
		this.loadFee = loadFee;
	}
	public java.lang.Double getAdminFee() {
		return adminFee;
	}
	public void setAdminFee(java.lang.Double adminFee) {
		this.adminFee = adminFee;
	}
	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}
	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}
	public Double getGoodAmount() {
		return goodAmount;
	}
	public void setGoodAmount(Double goodAmount) {
		this.goodAmount = goodAmount;
	}
	
	public B2bReserveGoods(Pgoods pg, Integer type) {
		B2bGoodsDtoMa ma = pg.getGdto();
		this.setBlock(ma.getBlock());
		if(Block.CF.getValue().equals(ma.getBlock())){
			CfGoods  cfgood=ma.getCfGoods();
			if(null!=cfgood){
				if(null!=type&&type!=2){
					this.setAdminFee(cfgood.getAdminFee()==null?0.0:cfgood.getAdminFee());//管理费单价
				}else{
					this.setAdminFee(0.0);//管理费单价
				}
				this.setLoadFee(cfgood.getLoadFee()==null?0.0:cfgood.getLoadFee());//装车费单价
				this.setPackageFee(cfgood.getPackageFee()==null?0.0:cfgood.getPackageFee());//包装费单价
				this.setProductPk(cfgood.getProductPk());
				this.setProductName(cfgood.getProductName());
				this.setVarietiesName(cfgood.getVarietiesName());
				this.setVarietiesPk(cfgood.getVarietiesPk());
				this.setSpecName(cfgood.getSpecName());
				this.setSpecPk(cfgood.getSpecPk());
				this.setSeriesName(cfgood.getSeriesName());
				this.setSeriesPk(cfgood.getSeriesPk());
				this.setGradeName(cfgood.getGradeName());
				this.setGradePk(cfgood.getGradePk());
				this.setGradeChineseName(cfgood.getGradeChineseName());
				this.setBatchNumber(cfgood.getBatchNumber());
				this.setSpecifications(cfgood.getSpecifications());
				this.setPackNumber(cfgood.getPackNumber());//包装批号
				this.setDistinctNumber(cfgood.getDistinctNumber());
				
			}
			
			
		}
	
		if(pg.getWeight()>0d){
			this.setWeight(new BigDecimal(String.valueOf(pg.getWeight())));
		}else{
			this.setWeight(ArithUtil.mulBigDecimal(ma.getTareWeight(), pg.getBoxes())); //吨
			if(Block.CF.getValue().equals(ma.getBlock())){
				this.setWeight(ArithUtil.divBigDecimal(this.getWeight(), 1000));
			}
		}
		if(Block.SX.getValue().equals(ma.getBlock())){
			SxGoods  sxgood=ma.getSxGoods();
			if(null!=sxgood){
				this.setIsBlend(sxgood.getIsBlend());
				this.setTechnologyPk(sxgood.getTechnologyPk());
				this.setTechnologyName(sxgood.getTechnologyName());
				this.setRawMaterialParentPk(sxgood.getRawMaterialParentPk());
				this.setRawMaterialParentName(sxgood.getRawMaterialParentName());
				this.setRawMaterialPk(sxgood.getRawMaterialPk());
				this.setRawMaterialName(sxgood.getRawMaterialName());
				this.setMaterialName(sxgood.getMaterialName());
				this.setSpecName(sxgood.getSpecName());
				this.setSpecPk(sxgood.getSpecPk());
			}
		}
		this.setSupplierName(ma.getCompanyName());
		this.setSupplierPk(ma.getCompanyPk());
		this.setGoodsPk(ma.getPk());
		this.setBoxes(pg.getBoxes());
	
		this.setPresentPrice(ma.getSalePrice());// 商品单价
		//有阶梯运价
		if(null != pg.getFreight()&& pg.getFreight() >0d){
			this.setPresentFreight(pg.getFreight());// 运费成交单价
			if(Block.CF.getValue().equals(ma.getBlock())){
				this.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(pg.getFreight(), this.getWeight().doubleValue()), 2));// 运费成交价
			}else{
				this.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(pg.getFreight(), ArithUtil.div(this.getWeight().doubleValue(), 1000)), 2));// 运费成交价
			}
			//无阶梯运价
		}else{
			this.setPresentFreight(0d);// 运费成交单价
			this.setPresentTotalFreight(null==pg.getTotalFreight()?0d:pg.getTotalFreight());// 运费成交价
		}
		if(Block.CF.getValue().equals(ma.getBlock())){
             double otherAmount=ArithUtil.add(ArithUtil.add(this.getPresentPrice(), this.getAdminFee()), ArithUtil.add(this.getLoadFee(),this.getPackageFee()));
             this.setGoodAmount(
			ArithUtil.add(ArithUtil.round(ArithUtil.mul(otherAmount,  this.getWeight().doubleValue()), 2), 
					this.getPresentTotalFreight()));
		}else{
			this.setGoodAmount(
					ArithUtil.add(ArithUtil.round(ArithUtil.mul(this.getPresentPrice() ,  this.getWeight().doubleValue()), 2), 
							this.getPresentTotalFreight()));
		}
		this.setLogisticsPk(pg.getLogisticsPk());
		this.setLogisticsStepInfoPk(pg.getLogisticsStepInfoPk());
		this.setEmployeePk(pg.getMdto()==null?"":pg.getMdto().getPk());
		this.setEmployeeName(pg.getMdto()==null?"":pg.getMdto().getEmployeeName()==null?"":pg.getMdto().getEmployeeName());
		this.setEmployeeNumber(pg.getMdto()==null?"":pg.getMdto().getEmployeeNumber()==null?"":pg.getMdto().getEmployeeNumber());
		this.setWareCode(pg.getWareCode());
		
	}
 

}