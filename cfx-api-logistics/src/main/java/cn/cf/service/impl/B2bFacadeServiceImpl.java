package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.LgCompanyDaoEx;
import cn.cf.dao.LgMenuDao;
import cn.cf.dao.LgRoleTypeDao;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.dto.LgMenuDto;
import cn.cf.dto.LgRoleTypeDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.B2bRoleMenuNode;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bFacadeService;
import cn.cf.util.DateUtil;

@Service
public class B2bFacadeServiceImpl implements B2bFacadeService {
	
	@Autowired
	private LgCompanyDaoEx companyDao;
	
	@Autowired
	private LgMenuDao menuDao;
	
	@Autowired
	private LgRoleTypeDao lgRoleTypeDao;
	
	public final static Integer superAdmin=0;
	public final static Integer commonAdmin=1;
	
	@Override
	public Sessions addSessions(LgMemberDtoEx lgMemberDto, String sessionId,Integer source) {
		//插入company
		LgCompanyDto lgCompanyDto = null;
		if (null != lgMemberDto.getCompanyPk() && !"".equals(lgMemberDto.getCompanyPk())) {
			lgCompanyDto = companyDao.getByPk(lgMemberDto.getCompanyPk());
			if (null != lgCompanyDto) {
				JedisUtils.set(lgCompanyDto.getPk(), lgCompanyDto,
						PropertyConfig.getIntProperty("session_time", 3600));
			}
		}
		Sessions session = new Sessions(sessionId, lgMemberDto,lgCompanyDto);
		session.setSource(source);//请求来源，1：PC,2:WAP，3：APP
		// 获取用户权限树形列表(默认一级id为'-1')
		//承运商用户
		if ("-1".equals(lgMemberDto.getParantPk())) {
			LgRoleTypeDto role = lgRoleTypeDao.getByCompanyType(superAdmin);
			List<B2bRoleMenuNode> purlist = this.getChildren("-1",role.getPk(), 0, null, 0);
			session.setPurMenus(JsonUtils.convertToString(purlist));
		}else{
			LgRoleTypeDto role = lgRoleTypeDao.getByCompanyType(commonAdmin);
			List<B2bRoleMenuNode> purlist = this.getChildren("-1",role.getPk(), 0, null, 1);
			session.setPurMenus(JsonUtils.convertToString(purlist));
		}
		session.setLastLoginTime(DateUtil.formatDateAndTime(new Date()));
		// 根据memberPk将用户信息存入缓存
		JedisUtils.set(lgMemberDto.getPk(), lgMemberDto, 3600);
		// 将用户登录session信息存入缓存
		JedisUtils.set(sessionId, session, 3600);
		//单点登陆限制
		JedisUtils.setInvalid("login-lg"+lgMemberDto.getMobile(), sessionId,3600);
		return session;
	}
	
	

	/**
	 * 
	 * @param id
	 *            模块id
	 * @param rolePk
	 *            角色
	 * @param type
	 *            菜单级数
	 * @param slist
	 *            集合
	 * @param companyType
	 *            0:超级管理员(不用校验角色),1:普通业务员
	 * @return
	 */
	private List<B2bRoleMenuNode> getChildren(String id, String rolePk,
			int type, List<LgMenuDto> slist, int companyType) {
		List<B2bRoleMenuNode> tlist = new ArrayList<B2bRoleMenuNode>();
		if (null != rolePk && !"".equals(rolePk)) {

			Map<String, Object> map = new HashMap<String, Object>();
			if (companyType > 0) {
				map.put("rolePk", rolePk);
			}
			map.put("parentPk", id);
			map.put("type", type);
			List<LgMenuDto> list = menuDao.searchMenuList(map);
			B2bRoleMenuNode tree = null;
			for (LgMenuDto dto : list) {
				tree = new B2bRoleMenuNode();
				tree.setId(dto.getPk());
				tree.setText(dto.getDisplayName());
				tree.setEnText(dto.getName());
				tree.setUrl(dto.getUrl());
				List<B2bRoleMenuNode> tl = this.getChildren(dto.getPk(),
						rolePk, type, slist, companyType);

				if (null != tl && tl.size() > 0) {
					tree.setChildren(tl);
				}
				tlist.add(tree);
			}
		}
		return tlist;

	}

}
