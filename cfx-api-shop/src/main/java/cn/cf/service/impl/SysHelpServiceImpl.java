package cn.cf.service.impl;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.SysHelpsCategoryDaoEx;
import cn.cf.dao.SysHelpsDaoEx;
import cn.cf.dto.SysHelpsCategoryDtoEx;
import cn.cf.dto.SysHelpsDto;
import cn.cf.service.SysHelpService;

@Service
public class SysHelpServiceImpl implements SysHelpService {

	@Autowired
	private SysHelpsCategoryDaoEx helpsCategoryDaoEx;
	
	@Autowired
	private SysHelpsDaoEx  helpsDaoEx;
	
	@Override
	public List<SysHelpsCategoryDtoEx> searchAll(Map<String, Object> map) throws Exception {
		List<SysHelpsCategoryDtoEx> list = helpsCategoryDaoEx.searchGridEx(map);
		if (null != list && list.size()>0) {
			for (SysHelpsCategoryDtoEx helpsCategoryDtoEx : list) {
				map.put("helpCategoryPk", helpsCategoryDtoEx.getPk());
				List<SysHelpsDto> helps = helpsDaoEx.searchHelpMenu(map);
				helpsCategoryDtoEx.setHelps(helps);
			}
		}
		return list;
	}

	@Override
	public SysHelpsDto searchByPk(String pk) {
		return helpsDaoEx.getByPk(pk);
	}
	
	@Override
	public SysHelpsDto getAboutUs(Map<String, Object> map) {
		SysHelpsDto helpsDto=null;
		List<SysHelpsDto> helpList = helpsDaoEx.searchHelpMenu(map);
		if (null!=helpList && helpList.size()>0) {
			helpsDto = helpList.get(0);
		}
		return helpsDto;
	}


}
