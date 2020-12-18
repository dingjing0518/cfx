package cn.cf.dto;


public class B2bBindGoodsDtoEx extends B2bBindGoodsDto {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsName;
    private String brandName;
    private String productName;
    private String specifications;
    private String specName;
    private String seriesName;
    private String varietiesName;
    private String seedvarietyName;
    private Double progress;
    private String batchNumber;//主批号
    private String distinctNumber;//区分号
    private String packNumber;//包装形式
    private Integer gisUpdown;//商品上下架状态
    private String gisUpdownName;
    private Integer bisUpDown;//拼团上下架状态
    private String bisUpDownName;
    
    private Integer status;//商品所在拼团活动的成团状态
    private String gradeName;
    private Double tonPrice;//挂牌价（元/吨）
    private Integer unit;//单位
    private String unitName;//单位
    private String bindName;//拼团名称
    private Double totalWeight;
    
    private Double bindProductPrice;//团购价
    

	public Double getBindProductPrice() {
		return bindProductPrice;
	}

	public void setBindProductPrice(Double bindProductPrice) {
		this.bindProductPrice = bindProductPrice;
	}

	public Integer getGisUpdown() {
		return gisUpdown;
	}

	public void setGisUpdown(Integer gisUpdown) {
		this.gisUpdown = gisUpdown;
	}

	public String getGisUpdownName() {
		return gisUpdownName;
	}

	public void setGisUpdownName(String gisUpdownName) {
		this.gisUpdownName = gisUpdownName;
	}

	public Integer getBisUpDown() {
		return bisUpDown;
	}

	public void setBisUpDown(Integer bisUpDown) {
		this.bisUpDown = bisUpDown;
	}

	public String getBisUpDownName() {
		return bisUpDownName;
	}

	public void setBisUpDownName(String bisUpDownName) {
		this.bisUpDownName = bisUpDownName;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
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

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
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


	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Double getTonPrice() {
		return tonPrice;
	}

	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
    
    
}
