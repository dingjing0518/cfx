package cn.cf.dao;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bWarehouseNumberDto;



public interface B2bWarehouseNumberDaoEx  extends B2bWarehouseNumberDao{

	int selectEntity(Map<String, Object> map);

	B2bWarehouseNumberDto getOneByNumber(@Param("number") String number, @Param("storePk") String storePk);


}
