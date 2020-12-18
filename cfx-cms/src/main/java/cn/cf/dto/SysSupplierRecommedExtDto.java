package cn.cf.dto;

public class SysSupplierRecommedExtDto extends SysSupplierRecommedDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8166931635296363309L;

	private String positionName;
	private String blockName;
	private String platformName;
	private String address;
	private String supplierNameCol;
	private String prePicCol;
	private String insertTimeBegin;
	private String insertTimeEnd;


	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getSupplierNameCol() {
		return supplierNameCol;
	}

	public void setSupplierNameCol(String supplierNameCol) {
		this.supplierNameCol = supplierNameCol;
	}

	public String getPrePicCol() {
		return prePicCol;
	}

	public void setPrePicCol(String prePicCol) {
		this.prePicCol = prePicCol;
	}

	public String getInsertTimeBegin() {
		return insertTimeBegin;
	}

	public void setInsertTimeBegin(String insertTimeBegin) {
		this.insertTimeBegin = insertTimeBegin;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}
}
