package cn.cf.service;

import cn.cf.dto.B2bProductDto;

public interface B2bProductService {

	public B2bProductDto getByName(String productName);

	public String createNewProduct(String productName ) ;

}
