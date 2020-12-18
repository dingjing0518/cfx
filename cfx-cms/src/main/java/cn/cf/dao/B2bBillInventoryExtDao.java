package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillInventoryExtDto;

public interface B2bBillInventoryExtDao  extends B2bBillInventoryDao{

	int searchGridCountExt(Map<String, Object> map);

	List<B2bBillInventoryExtDto> searchGridExt(Map<String, Object> map);
	
	
	

}
