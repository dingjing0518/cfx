/**
 * 
 */
package cn.cf.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.cf.dto.LogisticsModelDto;
import cn.cf.util.EncodeUtil;

/**
 * @author bin
 * 
 */
public class Corder {
	private String logisticsModelPk;// 物流方式
	private String logisticsModelName;// 物流方式
	private String saleDepartment;// 销售部门
	private String purchaserName;//采购商
	private String purchaserPk;//采购商
	private String startTime;//合同开始时间
	private String endTime;//合同结束时间
	private Integer days;//合同天数
	private String priceType;//价格类型
	private String contractRate;//合同比例
	private String addressPk;// 地址
	private List<Cgoods> goods;// 商品集合
	private Integer contractSource;//合同来源
	private String covenant;//约定事项
	private String supplementary;//补充事项
	private String remark;//备注
	private Integer source;//合同下单平台
	private Integer purchaserType;//自采代采
	private String invoicePk;//自采代采
	private LogisticsModelDto lmdto;

	public Corder(String value) {
		JSONObject js = JSONObject.parseObject(value);
		logisticsModelPk = js.containsKey("logisticsModelPk") ? js
				.getString("logisticsModelPk") : "";
		logisticsModelName = EncodeUtil.URLDecode(js.containsKey("logisticsModelName") ? js
				.getString("logisticsModelName") : "");
		addressPk = js.containsKey("addressPk") ? js.getString("addressPk")
				: "";
		purchaserPk = js.containsKey("purchaserPk") ? js.getString("purchaserPk")
				: "";
		purchaserName = EncodeUtil.URLDecode(js.containsKey("purchaserName") ? js
				.getString("purchaserName") : "");
		saleDepartment = js.containsKey("saleDepartment") ? js.getString("saleDepartment")
				: "";
		startTime = js.containsKey("startTime") ? js.getString("startTime"):"";
		endTime = js.containsKey("endTime") ? js.getString("endTime"):"";
		days = js.containsKey("days") ? js.getInteger("days")
				: 0;
		priceType = EncodeUtil.URLDecode(js.containsKey("priceType") ? js
				.getString("priceType") : "");
		contractRate = js.containsKey("contractRate") ? js.getString("contractRate"):"";
		contractSource = js.containsKey("contractSource") ? js.getInteger("contractSource")
				: 0;
		covenant = js.containsKey("covenant") ? js.getString("covenant"):"";
		supplementary = js.containsKey("supplementary") ? js.getString("supplementary"):"";
		remark = js.containsKey("remark") ? js.getString("remark"):"";
		goods = new ArrayList<Cgoods>();
		JSONArray gs = js.containsKey("goods") ? js.getJSONArray("goods")
				: null;
		if (null != gs && gs.size() > 0) {
			for (int i = 0; i < gs.size(); i++) {
				goods.add(new Cgoods(gs.getJSONObject(i)));
			}
		}
		contractSource = 1;//默认B2B
		invoicePk = js.containsKey("invoicePk") ? js.getString("invoicePk"):"";
	}

	public String getLogisticsModelPk() {
		return logisticsModelPk;
	}

	public void setLogisticsModelPk(String logisticsModelPk) {
		this.logisticsModelPk = logisticsModelPk;
	}

	public String getLogisticsModelName() {
		return logisticsModelName;
	}

	public void setLogisticsModelName(String logisticsModelName) {
		this.logisticsModelName = logisticsModelName;
	}

	

	public String getSaleDepartment() {
		return saleDepartment;
	}

	public void setSaleDepartment(String saleDepartment) {
		this.saleDepartment = saleDepartment;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserPk() {
		return purchaserPk;
	}

	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getContractRate() {
		return contractRate;
	}

	public void setContractRate(String contractRate) {
		this.contractRate = contractRate;
	}

	public String getAddressPk() {
		return addressPk;
	}

	public void setAddressPk(String addressPk) {
		this.addressPk = addressPk;
	}

	public List<Cgoods> getGoods() {
		return goods;
	}

	public void setGoods(List<Cgoods> goods) {
		this.goods = goods;
	}

	public Integer getContractSource() {
		return contractSource;
	}

	public void setContractSource(Integer contractSource) {
		this.contractSource = contractSource;
	}

	public String getCovenant() {
		return covenant;
	}

	public void setCovenant(String covenant) {
		this.covenant = covenant;
	}

	public String getSupplementary() {
		return supplementary;
	}

	public void setSupplementary(String supplementary) {
		this.supplementary = supplementary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getPurchaserType() {
		return purchaserType;
	}

	public void setPurchaserType(Integer purchaserType) {
		this.purchaserType = purchaserType;
	}

	public String getInvoicePk() {
		return invoicePk;
	}

	public void setInvoicePk(String invoicePk) {
		this.invoicePk = invoicePk;
	}

	public LogisticsModelDto getLmdto() {
		return lmdto;
	}

	public void setLmdto(LogisticsModelDto lmdto) {
		this.lmdto = lmdto;
	}
	 
}
