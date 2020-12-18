
package cn.cf.service.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.SysParamsExtDao;
import cn.cf.service.manage.LogManageService;

/**
 * @author hxh
 * 
 */
@Service
public class LogManageServiceImpl implements LogManageService {

	@Autowired
	private SysParamsExtDao sysParamsExtDao;
	
//	@Override
//	public PageModel<SysParamsDto> searchSysParamsList(QueryModel<SysParamsDto> qm) {
//		PageModel<SysParamsDto> pm = new PageModel<SysParamsDto>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("start", qm.getStart());
//		map.put("limit", qm.getLimit());
//		map.put("orderName", qm.getFirstOrderName());
//		map.put("orderType", qm.getFirstOrderType());
//		
//		List<SysParamsDto> list = sysParamsExtDao.searchGrid(map);
//		int counts = sysParamsExtDao.searchGridCount(map);
//		pm.setTotalCount(counts);
//		pm.setDataList(list);
//		return pm;
//	}
//
//	@Override
//	public int updateSysParams(SysParamsDto dto) {
//		// TODO Auto-generated method stub
//		return sysParamsExtDao.updateSysParams(dto);
//	}
	
}
