package cn.cf.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cf.entity.LgMemberDeliveryOrderDtoMa;


public interface LgMemberDeliveryOrderDao {
	
	int insert(LgMemberDeliveryOrderDtoMa dto);
	
	List<String> getVisibleDeliveryOrderByMemberPk(@Param("memberPk") String memberPk);
}
