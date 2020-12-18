package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bAddressDto;

public interface B2bAddressService {
	/**
	 * 获取守护地址详情
	 * @param pk
	 * @return
	 */
	B2bAddressDto getAddress(String pk);
	/**
	 * 分页获取收货地址
	 * @param map
	 * @return
	 */
	PageModel<B2bAddressDto> searchAddressList(Map<String,Object> map);
	/**
	 * 新增收货地址
	 * @param address
	 * @param memberPk 
	 * @return
	 */
	String addAddress(B2bAddressDto address, String memberPk);
	/**
	 * 修改收货地址
	 * @param address
	 * @return
	 */
	String updateAddress(B2bAddressDto address);
	/**
	 * 删除收货地址
	 * @param pk
	 * @return
	 */
	String delAddress(String pk);
	
	/***
	 * 根据地址详情查询
	 * 返回一条记录
	 * @param address
	 * @return
	 */
	B2bAddressDto searchOne(B2bAddressDto address,String companyPk);
	
}
