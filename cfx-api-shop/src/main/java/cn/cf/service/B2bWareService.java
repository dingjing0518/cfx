package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWareDtoEx;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.model.B2bWare;
import cn.cf.model.B2bWarehouseNumber;

public interface B2bWareService {

	Integer update(B2bWare model);
	
	/**
	 * 仓库是否重复
	 * @param map
	 * @return
	 */
	B2bWareDto isWareRepeated(Map<String, Object> map);

	int insert(B2bWare model);

	PageModel<B2bWareDtoEx> searchWareList(Map<String, Object> map);

	B2bWareDto getByPk(String pk);

	int addWareNumber(B2bWarehouseNumber model);

	int updateWareNumber(B2bWarehouseNumber model);

	PageModel<B2bWarehouseNumberDto> searchWareNumberList(Map<String, Object> map);

	B2bWarehouseNumberDto searchWareNumberByPk(String pk);
	  
	int selectEntity(B2bWarehouseNumberDto dto);



}
