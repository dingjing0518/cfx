package cn.cf.entity;

import java.util.List;

public class SearchLgPrice {
	private String  toProvicePk;
	private String  toCityPk;
	private String  toAreaPk;
	private String  toTownPk;
	private List<GoodInfo> list ;
	public String getToProvicePk() {
		return toProvicePk;
	}
	public void setToProvicePk(String toProvicePk) {
		this.toProvicePk = toProvicePk;
	}
	public String getToCityPk() {
		return toCityPk;
	}
	public void setToCityPk(String toCityPk) {
		this.toCityPk = toCityPk;
	}
	public String getToAreaPk() {
		return toAreaPk;
	}
	public void setToAreaPk(String toAreaPk) {
		this.toAreaPk = toAreaPk;
	}
	public String getToTownPk() {
		return toTownPk;
	}
	public void setToTownPk(String toTownPk) {
		this.toTownPk = toTownPk;
	}
	public List<GoodInfo> getList() {
		return list;
	}
	public void setList(List<GoodInfo> list) {
		this.list = list;
	}
	
	
	
	

}
