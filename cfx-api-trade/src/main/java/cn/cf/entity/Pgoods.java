/**
 * 
 */
package cn.cf.entity;

import com.alibaba.fastjson.JSONObject;

import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bCartDto;
import cn.cf.dto.B2bGradeDto;
import cn.cf.dto.B2bMemberDto;

/**
 * @author bin
 * 
 */
public class Pgoods implements Cloneable {
	private String cartPk;
	private String goodsPk;
	private String logisticsPk;// 物流模板Pk
	private String logisticsStepInfoPk;// 阶梯价格Pk
	private B2bGoodsDtoMa gdto;
	private B2bCartDto cdto;
	private B2bGradeDto grade;
	private B2bMemberDto mdto;
	private Integer boxes;
	private Double weight;
	private String packNumber;
	private String wareCode;
	private Integer totalBoxes;
	private String companyPk;
	private String bindPk;
	private B2bBindGoodsDtoEx bindGood;
	private double increasePrice;
	private Double freight;//运费单价
	private Double totalFreight;//运费总价
	private String logisticsCarrierPk;//承运商Pk
	private String logisticsCarrierName;//承运商名称
	private String warehouseNumber;
	private String warehouseType;

	public Pgoods(JSONObject js) {
		cartPk = js.containsKey("cartPk") ? js.getString("cartPk") : "";
		goodsPk = js.containsKey("goodsPk") ? js.getString("goodsPk") : "";
		boxes = js.containsKey("boxes") ? js.getInteger("boxes") : 0;
		totalBoxes = this.getBoxes();
		logisticsPk = js.containsKey("logisticsPk") ? js
				.getString("logisticsPk") : "";
		logisticsStepInfoPk = js.containsKey("logisticsStepInfoPk") ? js
				.getString("logisticsStepInfoPk") : "";
		bindPk = js.containsKey("bindPk") ? js.getString("bindPk") : "";
		increasePrice = js.containsKey("increasePrice") ? js
				.getDouble("increasePrice") : 0;
		weight = js.containsKey("weight") ? js
						.getDouble("weight") : 0;		
	}

	public Pgoods() {
		// TODO Auto-generated constructor stub
	}

	public double getIncreasePrice() {
		return increasePrice;
	}

	public void setIncreasePrice(double increasePrice) {
		this.increasePrice = increasePrice;
	}

	public B2bBindGoodsDtoEx getBindGood() {
		return bindGood;
	}

	public void setBindGood(B2bBindGoodsDtoEx bindGood) {
		this.bindGood = bindGood;
	}

	public B2bGradeDto getGrade() {
		return grade;
	}

	public void setGrade(B2bGradeDto grade) {
		this.grade = grade;
	}

	public B2bCartDto getCdto() {
		return cdto;
	}

	public void setCdto(B2bCartDto cdto) {
		this.cdto = cdto;
	}

	public B2bGoodsDtoMa getGdto() {
		return gdto;
	}

	public void setGdto(B2bGoodsDtoMa gdto) {
		if (null != gdto) {
			this.companyPk = gdto.getCompanyPk();
		}
		this.gdto = gdto;
	}

	public String getCartPk() {
		return cartPk;
	}

	public String getGoodsPk() {
		return goodsPk;
	}

	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}

	public String getLogisticsPk() {
		return logisticsPk;
	}

	public String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}

	public B2bMemberDto getMdto() {
		return mdto;
	}

	public void setMdto(B2bMemberDto mdto) {
		this.mdto = mdto;
	}

	public void setCartPk(String cartPk) {
		this.cartPk = cartPk;
	}

	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}

	public void setLogisticsStepInfoPk(String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}

	public Integer getBoxes() {
		return boxes;
	}

	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}

	public String getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}

	public String getWareCode() {
		return wareCode;
	}

	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}

	public Double getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Integer getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Integer totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public String getBindPk() {
		return bindPk;
	}

	public void setBindPk(String bindPk) {
		this.bindPk = bindPk;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getLogisticsCarrierPk() {
		return logisticsCarrierPk;
	}

	public void setLogisticsCarrierPk(String logisticsCarrierPk) {
		this.logisticsCarrierPk = logisticsCarrierPk;
	}

	public String getLogisticsCarrierName() {
		return logisticsCarrierName;
	}

	public void setLogisticsCarrierName(String logisticsCarrierName) {
		this.logisticsCarrierName = logisticsCarrierName;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	

		public String getWarehouseNumber() {
			return warehouseNumber;
		}

		public void setWarehouseNumber(String warehouseNumber) {
			this.warehouseNumber = warehouseNumber;
		}

		public String getWarehouseType() {
			return warehouseType;
		}

		public void setWarehouseType(String warehouseType) {
			this.warehouseType = warehouseType;
		}
}
