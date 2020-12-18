package cn.cf.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.BaseDTO;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

public class CollectionGoods extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String memberPk;// 用户id

	private String employeeName;

	private String mobile;

	private String sessionId;

	private String insertTime;

	private String companyPk;

	private String contactsTel;// 公司联系人

	private String companyName;

	private String goodPk;

	private Double price;

	private Double salePrice;

	private String productName;

	private String specName;

	private String seriesName;

	private String gradeName;

	private String varietiesName;

	private String seedvarietyName;

	private Date updateTime;

	private String batchNumber;

	private Double tareWeight;

	private Integer totalBoxes;

	private Double totalWeight;

	private B2bGoodsDto goods;

	private String block;

	private String collectionTime;

	private String specifications;

	private String rawMaterialPk;

	private String technologyPk;

	private String technologyTypePk;

	private String type;
    private String goodsInfo;
	public CollectionGoods() {

	}

	public CollectionGoods(Sessions session, B2bGoodsDto g) {
		this.id = KeyUtils.getUUID();
		this.goodPk = g.getPk();
		this.memberPk = session.getMemberPk();
		this.insertTime = DateUtil.formatDateAndTime(new Date());
		this.block = g.getBlock();
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTechnologyTypePk() {
		return technologyTypePk;
	}

	public void setTechnologyTypePk(String technologyTypePk) {
		this.technologyTypePk = technologyTypePk;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getRawMaterialPk() {
		return rawMaterialPk;
	}

	public void setRawMaterialPk(String rawMaterialPk) {
		this.rawMaterialPk = rawMaterialPk;
	}

	public String getTechnologyPk() {
		return technologyPk;
	}

	public void setTechnologyPk(String technologyPk) {
		this.technologyPk = technologyPk;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	private List<B2bAuctionGoodsDto> auctionGoods;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMobile() {
		return mobile;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public B2bGoodsDto getGoods() {
		return goods;
	}

	public void setGoods(B2bGoodsDto goods) {
		this.goods = goods;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Integer getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Integer totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getGoodPk() {
		return goodPk;
	}

	public void setGoodPk(String goodPk) {
		this.goodPk = goodPk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<B2bAuctionGoodsDto> getAuctionGoods() {
		return auctionGoods;
	}

	public void setAuctionGoods(List<B2bAuctionGoodsDto> auctionGoods) {
		this.auctionGoods = auctionGoods;
	}

}
