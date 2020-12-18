package cn.cf.entity;

import cn.cf.constant.Block;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.json.JsonUtils;

public class B2bGoodsDtoMa extends B2bGoodsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CfGoods cfGoods;
	private SxGoods sxGoods;
	public CfGoods getCfGoods() {
		if(null == this.cfGoods){
			if(null != this.getBlock() && !"".equals(this.getBlock())
					&& Block.CF.getValue().equals(this.getBlock())){
				cfGoods = JsonUtils.toBean(this.getGoodsInfo(),CfGoods.class);
			}
		}
		return cfGoods;
	}
	public void setCfGoods(CfGoods cfGoods) {
		if(null != this.getBlock() && !"".equals(this.getBlock())
				&& Block.CF.getValue().equals(this.getBlock())){
			this.setGoodsInfo(JsonUtils.convertToString(cfGoods));
		}
		this.cfGoods = cfGoods;
	}
	public SxGoods getSxGoods() {
		if(null == this.sxGoods){
			if(null != this.getBlock() && !"".equals(this.getBlock())
					&& Block.SX.getValue().equals(this.getBlock())){
				sxGoods = JsonUtils.toBean(this.getGoodsInfo(),SxGoods.class);
			}
		}
		return sxGoods;
	}
	public void setSxGoods(SxGoods sxGoods) {
		if(null != this.getBlock() && !"".equals(this.getBlock())
				&& Block.SX.getValue().equals(this.getBlock())){
			this.setGoodsInfo(JsonUtils.convertToString(sxGoods));
		}
		this.sxGoods = sxGoods;
	}
	
	
}
