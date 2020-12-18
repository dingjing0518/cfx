package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCart extends BaseModel  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String goodsPk;
	private java.lang.Integer boxes;
	private java.util.Date insertTime;
	private java.lang.String memberPk;
	private java.lang.String purchaserName;
	private java.lang.String purchaserPk;
	private java.lang.String supplierName;
	private java.lang.String supplierPk;
	private java.lang.Integer cartType;
	private java.lang.String bindPk;
	private java.lang.Double price;

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	private java.lang.Double weight;
	private java.lang.String block;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setBoxes(java.lang.Integer boxes) {
		this.boxes = boxes;
	}
	
	public java.lang.Integer getBoxes() {
		return this.boxes;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
	}
	public void setCartType(java.lang.Integer cartType) {
		this.cartType = cartType;
	}
	
	public java.lang.Integer getCartType() {
		return this.cartType;
	}

	public java.lang.String getBindPk() {
		return bindPk;
	}

	public void setBindPk(java.lang.String bindPk) {
		this.bindPk = bindPk;
	}

	public java.lang.Double getPrice() {
		return price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	public java.lang.Double getWeight() {
		return weight;
	}

	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}
	

}