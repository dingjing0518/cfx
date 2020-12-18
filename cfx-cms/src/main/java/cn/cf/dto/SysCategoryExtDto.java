package cn.cf.dto;

import java.util.List;

public class SysCategoryExtDto extends SysCategoryDto{

	private List<SysCategoryExtDto> categorys;
	private String categoryPk;
	private Integer flag;
	private String pName;
	private Integer pId;
	public List<SysCategoryExtDto> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<SysCategoryExtDto> categorys) {
		this.categorys = categorys;
	}
	public String getCategoryPk() {
		return categoryPk;
	}
	public void setCategoryPk(String categoryPk) {
		this.categoryPk = categoryPk;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
}
