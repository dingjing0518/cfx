package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.LgRoleDaoEx;
import cn.cf.dto.LgRoleDto;
import cn.cf.dto.LgRoleDtoEx;
import cn.cf.model.LgRole;
import cn.cf.service.LgRoleService;

@Service
public class LgRoleServiceImpl implements LgRoleService {

	@Autowired
	private LgRoleDaoEx lgRoleDaoEx;

	@Override
	public boolean isReapet(Map<String, Object> map) {
		int count = lgRoleDaoEx.isReapet(map);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void addRole(LgRoleDto dto) {
		LgRole model = new LgRole();
		model.UpdateDTO(dto);
		lgRoleDaoEx.insert(model);
	}

	@Override
	public void updateRole(LgRoleDto dto) {
		LgRole model = new LgRole();
		model.UpdateDTO(dto);
		lgRoleDaoEx.update(model);
	}

	@Override
	public PageModel<LgRoleDtoEx> searchLgRoleList(Map<String, Object> map) {
		PageModel<LgRoleDtoEx> pm = new PageModel<LgRoleDtoEx>();
		List<LgRoleDtoEx> list = lgRoleDaoEx.searchGrid(map);
		if (list!=null&&list.size()>0) {
			for (LgRoleDtoEx lgRoleDtoEx : list) {
				String address = null;
				address = lgRoleDtoEx.getProvinceName()+","+lgRoleDtoEx.getCityName()+","
				+lgRoleDtoEx.getAreaName();
				if (null!=lgRoleDtoEx.getTownName()&&!"".equals(lgRoleDtoEx.getTownName())) {
					address =address+","+lgRoleDtoEx.getTownName();
				}
				lgRoleDtoEx.setAddress(address);
			}
		}
		int count = lgRoleDaoEx.searchGridCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public LgRoleDto getByPk(String pk) {
		return lgRoleDaoEx.getByPk(pk) ;
	}

	@Override
	public List<LgRoleDtoEx> searchLgRole(Map<String, Object> par) {
		return lgRoleDaoEx.searchGrid(par);
	}

	
}
