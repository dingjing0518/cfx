package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysSupplierRecommedDtoEx;

public interface SysSupplierRecommedDaoEx extends SysSupplierRecommedDao{

	List<SysSupplierRecommedDtoEx> searchRecommedList(Map<String, Object> map);

}
