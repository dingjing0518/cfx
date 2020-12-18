package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bCartDtoEx;
import cn.cf.entity.FirmOrder;

public interface B2bCartDaoEx extends B2bCartDao{

	List<B2bCartDtoEx> searchCompanyList(Map<String,Object> map);
	
	List<B2bCartDtoEx> searchGoodsList(Map<String,Object> map);

	int searchCounts(Map<String,Object> map);
	
	int  deleteBatch(List<String> cartList);
	

	int deleteByBindPk(@Param("bindPk") String bindPk);

	List<FirmOrder> searchFirmOrder(Map<String, Object> map);
	
	int delByGoodsPk(@Param("goodsPk") String goodsPk);
}
