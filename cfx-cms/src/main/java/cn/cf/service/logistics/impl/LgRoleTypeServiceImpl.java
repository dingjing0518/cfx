package cn.cf.service.logistics.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.LgMenuExtDao;
import cn.cf.dao.LgRoleTypeExDao;
import cn.cf.dao.LgRoleTypeMenuExDao;
import cn.cf.dto.LgMenuDto;
import cn.cf.dto.LgRoleMenuDto;
import cn.cf.dto.LgRoleTypeDto;
import cn.cf.model.LgRoleMenu;
import cn.cf.model.LgRoleType;
import cn.cf.service.logistics.LgRoleTypeService;
import cn.cf.util.KeyUtils;


@Service
public class LgRoleTypeServiceImpl implements LgRoleTypeService{

	@Autowired
	private LgRoleTypeExDao lgRoleTypeExDao;
	
	@Autowired
	private LgRoleTypeMenuExDao lgRoleTypeMenuExDao;
	
	@Autowired
	private LgMenuExtDao lgMenuExtDao;
	
	/**
	 * 角色类型管理列表
	 */
	@Override
	public PageModel<LgRoleTypeDto> searchLgRoleTypeGrid(QueryModel<LgRoleTypeDto> qm) {
		PageModel<LgRoleTypeDto> pm = new PageModel<LgRoleTypeDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
		map.put("companyType", qm.getEntity().getCompanyType());
		int totalCount = lgRoleTypeExDao.searchGridCountEx(map);
		List<LgRoleTypeDto> list = lgRoleTypeExDao.searchGridEx(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	/**
	 * 根据rolePk查询该角色有哪些权限
	 */
	@Override
	public List<LgRoleMenuDto> getLgRoleMenuByRolepk(String rolePk) {
		return lgRoleTypeMenuExDao.getRoleMenuByRolePk(rolePk);
	}

	/**
	 * 
	 * @param parentPk
	 * @param companyType
	 * @return
	 */
	@Override
	public List<LgMenuDto> getLgMenuByParentPk(String parentPk,Integer companyType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentPk", parentPk);
		map.put("companyType", companyType);
		map.put("isDelete", 1);
		return lgMenuExtDao.searchGrid(map);
	}
	
	/**
	 * 检查角色名称是否已经存在
	 * @param name
	 * @return
	 */
	@Override
	public LgRoleTypeDto getLgRoleByName(String name) {
		return lgRoleTypeExDao.getByName(name);
	}
	
	/**
	 * 保存角色树
	 * @param role
	 * @param node
	 * @return
	 */
	@Override
	public String saveLgRole(LgRoleType role, String node) {
		String pk = null;
		if (role.getPk() != null && !"".equals(role.getPk())) {
			lgRoleTypeExDao.updateEx(role);
			pk = role.getPk();
		} else {
			pk = KeyUtils.getUUID();
			role.setPk(pk);
			role.setIsVisable(1);
			role.setInsertTime(new Date());
			lgRoleTypeExDao.insert(role);
		}
		if (null != pk && !"".equals(pk)) {
			LgRoleMenu mra = new LgRoleMenu();
			mra.setRolePk(pk);
			if (pk != null && !"".equals(pk)) {
				lgRoleTypeMenuExDao.deleteByLgRoleMenuPk(mra);
			}
			if (node != null && !"".equals(node)) {
				node = node.substring(1, node.length() - 1);
				String[] id = node.split(",");
				for (int i = 0; i < id.length; i++) {
					pk = KeyUtils.getUUID();
					mra.setPk(pk);
					mra.setMenuPk(id[i].replace("\"", ""));
					lgRoleTypeMenuExDao.insert(mra);
				}
			}
		}
		return pk;
	}
	
	
}
