package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.SysSupplierRecommedDtoEx;

public interface SysSupplierRecommedService {

	List<SysSupplierRecommedDtoEx> getSysSupplierRecommeds(Map<String, Object> map) throws Exception;

	PageModel<SysSupplierRecommedDtoEx> getSysSupplierRecommedsByPage(Map<String, Object> map);

}
