package cn.cf.dto;

import cn.cf.entity.CfGoods;
import cn.cf.entity.SxGoods;

public class B2bGoodsExtDto extends B2bGoodsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7076191949636743651L;

	private String insertStartTime;

	private String insertEndTime;

	private String updateStartTime;

	private String updateEndTime;

	private String upStartTime;

	private String upEndTime;

	private String isUpdownName;

	private String b2bGoodsInfo;

	private String storeNameCol;

	private String companyNameCol;

	private String plantNameCol;

	private String brandNameCol;

	private String typeName;

	private String isNewProductName;

	// 化纤
	private String productPk;
	private String varietiesPk;
	private String seedvarietyPk;
	private String specPk;
	private String seriesPk;
	private String batchNumber;
	private CfGoods cfgoods;
	private  String  stampDutyName ;
	/* end */

	// 纱线
	private String technologyPk;
	private String technologyTypePk;
	private String rawMaterialPk;
	private String rawMaterialParentPk;
	private String specifications;
	private String materialName;
	private SxGoods SxGoodsExt;
	private Integer isBlend;
	/* end */

	public CfGoods getCfgoods() {
		return cfgoods;
	}

	public Integer getIsBlend() {
		return isBlend;
	}

	public void setIsBlend(Integer isBlend) {
		this.isBlend = isBlend;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRawMaterialParentPk() {
		return rawMaterialParentPk;
	}

	public void setRawMaterialParentPk(String rawMaterialParentPk) {
		this.rawMaterialParentPk = rawMaterialParentPk;
	}


	public SxGoods getSxGoodsExt() {
		return SxGoodsExt;
	}

	public void setSxGoodsExt(SxGoods sxGoodsExt) {
		SxGoodsExt = sxGoodsExt;
	}

	public String getTechnologyPk() {
		return technologyPk;
	}

	public void setTechnologyPk(String technologyPk) {
		this.technologyPk = technologyPk;
	}

	public String getTechnologyTypePk() {
		return technologyTypePk;
	}

	public void setTechnologyTypePk(String technologyTypePk) {
		this.technologyTypePk = technologyTypePk;
	}

	public String getRawMaterialPk() {
		return rawMaterialPk;
	}

	public void setRawMaterialPk(String rawMaterialPk) {
		this.rawMaterialPk = rawMaterialPk;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setCfgoods(CfGoods cfgoods) {
		this.cfgoods = cfgoods;
	}

	public String getProductPk() {
		return productPk;
	}

	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}

	public String getVarietiesPk() {
		return varietiesPk;
	}

	public void setVarietiesPk(String varietiesPk) {
		this.varietiesPk = varietiesPk;
	}

	public String getSeedvarietyPk() {
		return seedvarietyPk;
	}

	public void setSeedvarietyPk(String seedvarietyPk) {
		this.seedvarietyPk = seedvarietyPk;
	}

	public String getSpecPk() {
		return specPk;
	}

	public void setSpecPk(String specPk) {
		this.specPk = specPk;
	}

	public String getSeriesPk() {
		return seriesPk;
	}

	public void setSeriesPk(String seriesPk) {
		this.seriesPk = seriesPk;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getIsNewProductName() {
		return isNewProductName;
	}

	public void setIsNewProductName(String isNewProductName) {
		this.isNewProductName = isNewProductName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public String getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public String getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public String getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public String getUpStartTime() {
		return upStartTime;
	}

	public void setUpStartTime(String upStartTime) {
		this.upStartTime = upStartTime;
	}

	public String getUpEndTime() {
		return upEndTime;
	}

	public void setUpEndTime(String upEndTime) {
		this.upEndTime = upEndTime;
	}

	public String getIsUpdownName() {
		return isUpdownName;
	}

	public void setIsUpdownName(String isUpdownName) {
		this.isUpdownName = isUpdownName;
	}

	public String getB2bGoodsInfo() {
		return b2bGoodsInfo;
	}

	public void setB2bGoodsInfo(String b2bGoodsInfo) {
		this.b2bGoodsInfo = b2bGoodsInfo;
	}

	public String getStoreNameCol() {
		return storeNameCol;
	}

	public void setStoreNameCol(String storeNameCol) {
		this.storeNameCol = storeNameCol;
	}

	public String getCompanyNameCol() {
		return companyNameCol;
	}

	public void setCompanyNameCol(String companyNameCol) {
		this.companyNameCol = companyNameCol;
	}

	public String getPlantNameCol() {
		return plantNameCol;
	}

	public void setPlantNameCol(String plantNameCol) {
		this.plantNameCol = plantNameCol;
	}

	public String getBrandNameCol() {
		return brandNameCol;
	}

	public void setBrandNameCol(String brandNameCol) {
		this.brandNameCol = brandNameCol;
	}

	public String getStampDutyName() {
		return stampDutyName;
	}

	public void setStampDutyName(String stampDutyName) {
		this.stampDutyName = stampDutyName;
	}
	
	
}
