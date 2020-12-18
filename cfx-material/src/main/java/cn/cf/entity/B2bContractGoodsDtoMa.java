package cn.cf.entity;

import com.alibaba.fastjson.JSON;
import cn.cf.constant.Block;
import cn.cf.dto.B2bContractGoodsDto;

public class B2bContractGoodsDtoMa extends B2bContractGoodsDto{

	private static final long serialVersionUID = 1L;
	
	private CfGoods cfGoods;
	private SxGoods sxGoods;
	
	public B2bContractGoodsDtoMa(){
		
	}
	
	
	/**
	 * 获取cfGoods
	 * @return
	 */
	public CfGoods getCfGoods() {
		if(null == this.cfGoods){
			if(null !=this.getGoodsInfo() && !"".equals(this.getGoodsInfo()) && Block.CF.getValue().equals(this.getBlock())
					){
				this.cfGoods = JSON.parseObject(this.getGoodsInfo(),CfGoods.class);
			}	
		}
		return cfGoods;
	}
	
	public void setCfGoods(CfGoods cfGoods) {
		this.cfGoods = cfGoods;
	}
	
	/**
	 * 获取sxGoods
	 * @return
	 */
	public SxGoods getSxGoods() {
		if(null == this.sxGoods){
			if(null !=this.getGoodsInfo() && !"".equals(this.getGoodsInfo()) && Block.SX.getValue().equals(this.getBlock())){
				this.sxGoods = JSON.parseObject(this.getGoodsInfo(),SxGoods.class);
			}	
		}
		return sxGoods;
	}
	
	
	public void setSxGoods(SxGoods sxGoods) {
		this.sxGoods = sxGoods;
	}
	
	
	

}
