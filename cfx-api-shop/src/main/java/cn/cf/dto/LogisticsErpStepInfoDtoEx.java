package cn.cf.dto;

import com.alibaba.fastjson.JSONObject;


public class LogisticsErpStepInfoDtoEx   extends LogisticsErpStepInfoDto  {
 
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogisticsErpStepInfoDtoEx(JSONObject js) {
		this.setProductPk(js.containsKey("productPk")?js.getString("productPk"):"");
		this.setProductName(js.containsKey("productName")?js.getString("productName"):"");
		this.setStartWeight(js.containsKey("startWeight") ? js.getDouble("startWeight") : 0d );
		this.setEndWeight(js.containsKey("endWeight") ? js.getDouble("endWeight") : 0d);
		this.setPackNumber(js.containsKey("packNumber")?js.getString("packNumber"):"");
		this.setPrice(js.containsKey("price") ? js.getDouble("price") : 0d);
		this.setIsDelete(1);
		this.setIsStore(js.containsKey("isStore") ? js.getInteger("isStore") : 2);
	}


 
}
