package cn.cf.dto;

public class B2bBindGoodsDtoEx extends B2bBindGoodsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String batchNumber;
	private java.lang.String gradeName;
	private java.lang.String distinctNumber;
	private java.lang.String packNumber;
	public java.lang.String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(java.lang.String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public java.lang.String getGradeName() {
		return gradeName;
	}
	public void setGradeName(java.lang.String gradeName) {
		this.gradeName = gradeName;
	}
	public java.lang.String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(java.lang.String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}
	public java.lang.String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(java.lang.String packNumber) {
		this.packNumber = packNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batchNumber == null) ? 0 : batchNumber.hashCode());
		result = prime * result
				+ ((distinctNumber == null) ? 0 : distinctNumber.hashCode());
		result = prime * result
				+ ((gradeName == null) ? 0 : gradeName.hashCode());
		result = prime * result
				+ ((packNumber == null) ? 0 : packNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		B2bBindGoodsDtoEx other = (B2bBindGoodsDtoEx) obj;
		if (batchNumber == null) {
			if (other.batchNumber != null)
				return false;
		} else if (!batchNumber.equals(other.batchNumber))
			return false;
		if (distinctNumber == null) {
			if (other.distinctNumber != null)
				return false;
		} else if (!distinctNumber.equals(other.distinctNumber))
			return false;
		if (gradeName == null) {
			if (other.gradeName != null)
				return false;
		} else if (!gradeName.equals(other.gradeName))
			return false;
		if (packNumber == null) {
			if (other.packNumber != null)
				return false;
		} else if (!packNumber.equals(other.packNumber))
			return false;
		return true;
	}
	
	
}
