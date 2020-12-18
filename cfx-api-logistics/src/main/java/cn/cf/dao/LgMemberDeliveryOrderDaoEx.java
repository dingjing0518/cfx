package cn.cf.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.LgMemberDeliveryOrderDtoEx;

public interface LgMemberDeliveryOrderDaoEx {
	
	int insert(LgMemberDeliveryOrderDtoEx dto);
	
	List<String> getVisibleDeliveryOrderByMemberPk(@Param("memberPk") String memberPk);
}
