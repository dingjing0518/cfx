package cn.cf.dao;

import java.util.List;
import java.util.Map;

public interface LgDeliveryExceptionPicDaoEx extends LgDeliveryExceptionPicDao{
	
	List<String> selectPicUrlByDeliveryPk(Map<String,Object> par);
	
}
