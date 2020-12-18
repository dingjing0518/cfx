package cn.cf.dao;


import cn.cf.dto.LgDeliveryExceptionPicDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LgDeliveryExceptionPicExtDao extends LgDeliveryExceptionPicDao{
	
    int insert(Map<String, Object> map);
    List<LgDeliveryExceptionPicDto> selectPic(@Param("pk") String pk);

}
