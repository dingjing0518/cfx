package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsErpStepInfoDto extends BaseDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8022747381661522166L;
	private java.lang.String pk;
	private java.lang.String logisticsPk;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.Double startWeight;
	private java.lang.Double endWeight;
	private java.lang.String packNumber;
	private java.lang.Double price;
	private java.lang.Integer isDelete;
	private java.lang.Integer isStore;
	private  String block;
	//columns END

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public java.lang.String getPk() {
		return pk;
	}
 
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	public java.lang.String getLogisticsPk() {
		return logisticsPk;
	}
	public void setLogisticsPk(java.lang.String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	public java.lang.String getProductPk() {
		return productPk;
	}
	public void setProductPk(java.lang.String productPk) {
		this.productPk = productPk;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	
	public java.lang.Double getStartWeight() {
		return startWeight;
	}

	public void setStartWeight(java.lang.Double startWeight) {
		this.startWeight = startWeight;
	}

	public java.lang.Double getEndWeight() {
		return endWeight;
	}

	public void setEndWeight(java.lang.Double endWeight) {
		this.endWeight = endWeight;
	}

	public java.lang.String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(java.lang.String packNumber) {
		this.packNumber = packNumber;
	}
	public java.lang.Double getPrice() {
		return price;
	}
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	public java.lang.Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	public java.lang.Integer getIsStore() {
		return isStore;
	}
	public void setIsStore(java.lang.Integer isStore) {
		this.isStore = isStore;
	}
	
}