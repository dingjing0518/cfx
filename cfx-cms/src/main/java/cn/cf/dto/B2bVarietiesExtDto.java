package cn.cf.dto;

public class B2bVarietiesExtDto extends B2bVarietiesDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2561815649931950434L;
private String isVisableName;
	
	private String parentName;

	public String getIsVisableName() {
		return isVisableName;
	}

	public void setIsVisableName(String isVisableName) {
		this.isVisableName = isVisableName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
