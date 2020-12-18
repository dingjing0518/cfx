package cn.cf.dto;

import java.util.List;

public class SxHomeSecondnavigationDtoEx extends SxHomeSecondnavigationDto {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    List<SxHomeThirdnavigationDto> thList;

    List<SysSupplierRecommedDto> reList;

    String url;


    public List<SxHomeThirdnavigationDto> getThList() {
		return thList;
	}

	public void setThList(List<SxHomeThirdnavigationDto> thList) {
		this.thList = thList;
	}

	public List<SysSupplierRecommedDto> getReList() {
		return reList;
	}

	public void setReList(List<SysSupplierRecommedDto> reList) {
		this.reList = reList;
	}

	@Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
