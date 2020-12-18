package cn.cf.service;

import cn.cf.dto.B2bProductDto;

public interface B2bProductService {

	/**
	 * 查询品名
	 * @param productName  品名名称
	 * @return
	 */
	B2bProductDto getProductByName(String productName);

}
