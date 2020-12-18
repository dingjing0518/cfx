package cn.cf.dto;

public class B2bBrandExtDto extends B2bBrandDto{
	
	private String modelType;
	
	
	public String getModelType() {
        return modelType;
    }


    public void setModelType(String modelType) {
        this.modelType = modelType;
    }


    /**
	 * 
	 */
	private static final long serialVersionUID = -3439179338497444506L;
	
	
	private String isVisableName;


	public String getIsVisableName() {
		return isVisableName;
	}


	public void setIsVisableName(String isVisableName) {
		this.isVisableName = isVisableName;
	}
	
	

}
