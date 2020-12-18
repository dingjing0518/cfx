package cn.cf.dto;

public class B2bGoodsBrandExtDto extends B2bGoodsBrandDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2550794519882069385L;

	private String companyNameCol;
	private String goodsBrandCol;
	private String brandShortName;
	
	

    public String getBrandShortName() {
        return brandShortName;
    }

    public void setBrandShortName(String brandShortName) {
        this.brandShortName = brandShortName;
    }

    public String getCompanyNameCol() {
		return companyNameCol;
	}

	public void setCompanyNameCol(String companyNameCol) {
		this.companyNameCol = companyNameCol;
	}

	public String getGoodsBrandCol() {
		return goodsBrandCol;
	}

	public void setGoodsBrandCol(String goodsBrandCol) {
		this.goodsBrandCol = goodsBrandCol;
	}
}
