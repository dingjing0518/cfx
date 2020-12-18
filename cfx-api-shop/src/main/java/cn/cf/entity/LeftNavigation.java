/**
 * 
 */
package cn.cf.entity;

import java.util.ArrayList;
import java.util.List;

import cn.cf.dto.SxHomeSecondnavigationDtoEx;
import cn.cf.dto.SysSupplierRecommedDto;

/**
 * @author bin
 *
 */
public class LeftNavigation {
	List<SxHomeSecondnavigationDtoEx> list=new ArrayList<SxHomeSecondnavigationDtoEx>();
	List<SysSupplierRecommedDto> reList=new ArrayList<SysSupplierRecommedDto>();
	public List<SxHomeSecondnavigationDtoEx> getList() {
		return list;
	}
	public void setList(List<SxHomeSecondnavigationDtoEx> list) {
		this.list = list;
	}
	public List<SysSupplierRecommedDto> getReList() {
		return reList;
	}
	public void setReList(List<SysSupplierRecommedDto> reList) {
		this.reList = reList;
	}
	
}
