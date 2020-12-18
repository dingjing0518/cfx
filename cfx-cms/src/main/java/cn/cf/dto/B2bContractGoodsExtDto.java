package cn.cf.dto;

import cn.cf.entity.CfGoods;
import cn.cf.entity.SxGoods;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bContractGoodsExtDto extends B2bContractGoodsDto{

    private String goodsName;
    private Double totalWeight;
    private Double disCountPrice;

    private Double contractTotalPrice;
    private Double totalFreight;
    private Double contractRealityPrice;
    
    private String block;
    
    private CfGoods cfGoods;
    private SxGoods sxGoods;

    
    

    public CfGoods getCfGoods() {
		return cfGoods;
	}

	public void setCfGoods(CfGoods cfGoods) {
		this.cfGoods = cfGoods;
	}

	public SxGoods getSxGoods() {
		return sxGoods;
	}

	public void setSxGoods(SxGoods sxGoods) {
		this.sxGoods = sxGoods;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Double getDisCountPrice() {
        return disCountPrice;
    }

    public void setDisCountPrice(Double disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public Double getContractTotalPrice() {
        return contractTotalPrice;
    }

    public void setContractTotalPrice(Double contractTotalPrice) {
        this.contractTotalPrice = contractTotalPrice;
    }
    public Double getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(Double totalFreight) {
        this.totalFreight = totalFreight;
    }

	public Double getContractRealityPrice() {
		return contractRealityPrice;
	}

	public void setContractRealityPrice(Double contractRealityPrice) {
		this.contractRealityPrice = contractRealityPrice;
	}
    
    
}