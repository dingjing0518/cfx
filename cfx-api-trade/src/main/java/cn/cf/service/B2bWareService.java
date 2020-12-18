package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWareDtoEx;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.model.B2bWare;

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

	B2bWarehouseNumberDto getOneByNumber(B2bGoodsDto gdto);



}
