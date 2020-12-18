package cn.cf.dto;

import java.util.List;

public class SysHomeBannerProductnameDtoEx extends SysHomeBannerProductnameDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SysHomeBannerSpecDto> specList;
	private List<SysHomeBannerSeriesDto> seiesList;
	private List<SysHomeBannerVarietiesDto> varietiesList;
	private List<SysHomeBannerMassageDto> smallUrls;
	private List<SysHomeBannerMassageDto> bigUrls;
	
	

	public List<SysHomeBannerSpecDto> getSpecList() {
		return specList;
	}

	public void setSpecList(List<SysHomeBannerSpecDto> specList) {
		this.specList = specList;
	}

	public List<SysHomeBannerSeriesDto> getSeiesList() {
		return seiesList;
	}

	public void setSeiesList(List<SysHomeBannerSeriesDto> seiesList) {
		this.seiesList = seiesList;
	}

	public List<SysHomeBannerVarietiesDto> getVarietiesList() {
		return varietiesList;
	}

	public void setVarietiesList(List<SysHomeBannerVarietiesDto> varietiesList) {
		this.varietiesList = varietiesList;
	}

	public List<SysHomeBannerMassageDto> getSmallUrls() {
		return smallUrls;
	}

	public void setSmallUrls(List<SysHomeBannerMassageDto> smallUrls) {
		this.smallUrls = smallUrls;
	}

	public List<SysHomeBannerMassageDto> getBigUrls() {
		return bigUrls;
	}

	public void setBigUrls(List<SysHomeBannerMassageDto> bigUrls) {
		this.bigUrls = bigUrls;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
