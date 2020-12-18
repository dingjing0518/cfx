package cn.cf.dto;


public class B2bBindGoodsDtoEx extends B2bBindGoodsDto {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsName;
    private String brandName;
    private Double progress;
    private Integer gisUpdown;//商品上下架状态
    private String gisUpdownName;
    private Integer bisUpDown;//拼团上下架状态
    private String bisUpDownName;
    
    private Integer status;//商品所在拼团活动的成团状态
    private Double tonPrice;//挂牌价（元/吨）
    private String bindName;//拼团名称
    private Double totalWeight;
    
    private Double bindProductPrice;//团购价
    private Double tareWeight;
    private String goodsInfo;
    private String block;

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

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

    

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	 
	public Double getTonPrice() {
		return tonPrice;
	}

	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}

	 
	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
    
    
}
