package cn.cf.entity;

import java.util.Date;

import javax.persistence.Id;


import com.alibaba.fastjson.JSONObject;
import cn.cf.dto.BaseDTO;
import cn.cf.util.DateUtil;

/**
 * 
 * @author
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsStepInfoDto extends BaseDTO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	@Id
	private String pk;
	private String logisticsPk;
	private Integer startWeight;
	private Integer endWeight;
	private Double price;
	private Integer isDelete;
	private String updateTime;
	
	public LogisticsStepInfoDto() {
		// TODO Auto-generated constructor stub
	}
	
	public LogisticsStepInfoDto(JSONObject js) {
		startWeight = js.containsKey("startWeight") ? js.getInteger("startWeight") : 0;
		endWeight = js.containsKey("endWeight") ? js.getInteger("endWeight") : 0;
		price = js.containsKey("price") ? js.getDouble("price") : 0d;
		isDelete = 1;
		updateTime = DateUtil.formatDateAndTime(new Date());
		
	}

	// columns END

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return this.pk;
	}

	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}

	public String getLogisticsPk() {
		return this.logisticsPk;
	}

	public void setStartWeight(Integer startWeight) {
		this.startWeight = startWeight;
	}

	public Integer getStartWeight() {
		return this.startWeight;
	}

	public void setEndWeight(Integer endWeight) {
		this.endWeight = endWeight;
	}

	public Integer getEndWeight() {
		return this.endWeight;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		return this.price;
	}

}