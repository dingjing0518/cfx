package cn.cf.dto;

import java.util.List;


public class SysHomeBannerVarietiesDtoEx extends SysHomeBannerVarietiesDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productPk;
	private List<SysHomeBannerSeriesDto> seriesList;
	
	public String getProductPk() {
		return productPk;
	}
	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}
	public List<SysHomeBannerSeriesDto> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<SysHomeBannerSeriesDto> seriesList) {
		this.seriesList = seriesList;
	}

	
	
	
}
