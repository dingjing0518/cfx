package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bPriceMovementExtDto  extends B2bPriceMovementDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4123565766067425413L;
	
	private String productName;
	
	private String varietiesName;
	
	private String specName;
	
	private String seriesName;
	
	private String specifications;//商品规格
	
	private String batchNumber;
	
	private String brandName;
	
	private Double tonPrice;
	
	private java.util.Date platformUpdateTime;
	
	private String platformUpdateTimeStart;
	
	private String platformUpdateTimeEnd;
	
	private String updateTimeStart;
	
	private String updateTimeEnd;
	
	private String dateStart;
	
	private String dateEnd;
	
	private String dateHistoryStart;
	
	private String dateHistoryEnd;
	
	private String movementPk;//mongo表查询条件
	
	private String dateStr;
	
	private String goodsInfo;
	
	private Integer isEdit;
	
	private String gradeName;

	//添加纱线板块字段

	private String technologyName;

	private String firstMaterialName;

	private String secondMaterialName;

	private Double yarnPrice;

	private Integer isShowHome;

	
    public Integer getIsShowHome() {
        return isShowHome;
    }

    public void setIsShowHome(Integer isShowHome) {
        this.isShowHome = isShowHome;
    }

    public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getTonPrice() {
		return tonPrice;
	}

	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}

	public java.util.Date getPlatformUpdateTime() {
		return platformUpdateTime;
	}

	public void setPlatformUpdateTime(java.util.Date platformUpdateTime) {
		this.platformUpdateTime = platformUpdateTime;
	}

	public String getPlatformUpdateTimeStart() {
		return platformUpdateTimeStart;
	}

	public void setPlatformUpdateTimeStart(String platformUpdateTimeStart) {
		this.platformUpdateTimeStart = platformUpdateTimeStart;
	}

	public String getPlatformUpdateTimeEnd() {
		return platformUpdateTimeEnd;
	}

	public void setPlatformUpdateTimeEnd(String platformUpdateTimeEnd) {
		this.platformUpdateTimeEnd = platformUpdateTimeEnd;
	}

	public String getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(String updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public String getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(String updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDateHistoryStart() {
		return dateHistoryStart;
	}

	public void setDateHistoryStart(String dateHistoryStart) {
		this.dateHistoryStart = dateHistoryStart;
	}

	public String getDateHistoryEnd() {
		return dateHistoryEnd;
	}

	public void setDateHistoryEnd(String dateHistoryEnd) {
		this.dateHistoryEnd = dateHistoryEnd;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getMovementPk() {
		return movementPk;
	}

	public void setMovementPk(String movementPk) {
		this.movementPk = movementPk;
	}

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public Double getYarnPrice() {
		return yarnPrice;
	}

	public void setYarnPrice(Double yarnPrice) {
		this.yarnPrice = yarnPrice;
	}

	public String getFirstMaterialName() {
		return firstMaterialName;
	}

	public void setFirstMaterialName(String firstMaterialName) {
		this.firstMaterialName = firstMaterialName;
	}

	public String getSecondMaterialName() {
		return secondMaterialName;
	}

	public void setSecondMaterialName(String secondMaterialName) {
		this.secondMaterialName = secondMaterialName;
	}
}