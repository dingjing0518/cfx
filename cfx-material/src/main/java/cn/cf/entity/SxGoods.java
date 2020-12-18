package cn.cf.entity;

public class SxGoods {
	
	private String pk;
	private Integer isBlend;//是否混纺，1.是；2.否
	private String technologyPk;//工艺
	private String technologyName;
	private String rawMaterialParentPk;//原料一级
	private String rawMaterialParentName;
	private String rawMaterialPk;//原料二级
	private String rawMaterialName;
	private String specPk;//规格
	private String specName;
	private String materialName;//物料名称
	private String unit;
	private String plantPk;
	private String plantName;
	private String warePk;
	private String wareName;
	private String wareCode;
	private String wareAddress;
	private String meno;
	private String block;
	private String brandPk;
	private String brandName;
    private Double tareWeight;
    private String certificatePk;
    private String certificateName;//证书名称
	
	 
	public Double getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
	public String getBrandPk() {
		return brandPk;
	}
	public void setBrandPk(String brandPk) {
		this.brandPk = brandPk;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getWareAddress() {
		return wareAddress;
	}
	public void setWareAddress(String wareAddress) {
		this.wareAddress = wareAddress;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
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
 
	public String getSpecPk() {
		return specPk;
	}
	public void setSpecPk(String specPk) {
		this.specPk = specPk;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public String getPlantPk() {
		return plantPk;
	}
	public void setPlantPk(String plantPk) {
		this.plantPk = plantPk;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getWarePk() {
		return warePk;
	}
	public void setWarePk(String warePk) {
		this.warePk = warePk;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	public String getCertificatePk() {
		return certificatePk;
	}
	public void setCertificatePk(String certificatePk) {
		this.certificatePk = certificatePk;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	
}
