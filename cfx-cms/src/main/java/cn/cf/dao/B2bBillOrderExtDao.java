package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillOrderExtDto;

public interface B2bBillOrderExtDao  extends B2bBillOrderDao{

	int searchGridCountExt(Map<String, Object> map);

	List<B2bBillOrderExtDto> searchGridExt(Map<String, Object> map);

}
