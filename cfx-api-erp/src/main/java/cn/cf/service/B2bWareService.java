package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.model.B2bWare;

public interface B2bWareService {

	B2bWare getByName(Map<String, Object> map);

	String createNewWare(String wareName, String wareCode, String wareAddress,String storePk);

	B2bWarehouseNumberDto getOneByNumber(B2bGoodsDto  gdto);

}
