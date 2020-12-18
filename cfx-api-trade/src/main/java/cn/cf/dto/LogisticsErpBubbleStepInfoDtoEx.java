package cn.cf.dto;

import com.alibaba.fastjson.JSONObject;

public class LogisticsErpBubbleStepInfoDtoEx extends
		LogisticsErpBubbleStepInfoDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double minTareWeight;
	private Double maxTareWeight;
	private Double coefficient;

	public LogisticsErpBubbleStepInfoDtoEx(JSONObject js) {
		minTareWeight = js.containsKey("minTareWeight") ? js
				.getDouble("minTareWeight") : 0;
		maxTareWeight = js.containsKey("maxTareWeight") ? js
				.getDouble("maxTareWeight") : 0;
		coefficient = js.containsKey("coefficient") ? js
				.getDouble("coefficient") : 0;
	}

	public Double getMinTareWeight() {
		return minTareWeight;
	}

	public void setMinTareWeight(Double minTareWeight) {
		this.minTareWeight = minTareWeight;
	}

	public Double getMaxTareWeight() {
		return maxTareWeight;
	}

	public void setMaxTareWeight(Double maxTareWeight) {
		this.maxTareWeight = maxTareWeight;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

}