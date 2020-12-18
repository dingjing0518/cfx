package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ch.qos.logback.classic.Logger;
import cn.cf.common.SendMails;
import cn.cf.constant.Source;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bMemberRoleDaoEx;
import cn.cf.dao.B2bMenuDaoEx;
import cn.cf.dao.B2bRoleDao;
import cn.cf.dao.B2bStoreDao;
import cn.cf.dao.B2bTokenDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberRoleDto;
import cn.cf.dto.B2bMenuDto;
import cn.cf.dto.B2bRoleDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.B2bRoleMenuNode;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.SessionService;
import cn.cf.util.DateUtil;
import cn.cf.util.StringUtils;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private B2bMenuDaoEx menuDao;
	
	@Autowired
	private B2bStoreDao storeDao;
	
	@Autowired
	private B2bRoleDao b2bRoleDao;
	
	@Autowired
	private B2bMemberRoleDaoEx memberRoleDao;
	
	@Autowired
	private B2bCompanyDaoEx companyDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;
	
	@Autowired
	private B2bTokenDao b2bTokenDao;

	private final static Logger logger = (Logger) LoggerFactory.getLogger(SessionServiceImpl.class);

	@Override
	public Sessions addSessions(B2bMemberDto dto, String sessionId,Integer source) {
		B2bCompanyDto company = null;
		Integer companyType = null;
		Integer isPerfect = null;
		String block = null;
		String tokenType = null;
		if (null != dto.getCompanyPk() && !"".equals(dto.getCompanyPk())) {
			company = companyDao.getByPk(dto.getCompanyPk());
			if (null != company) {
				companyType = company.getCompanyType();
				isPerfect = company.getIsPerfect();
				block = company.getBlock();
			}
		}
		// 创建用户session信息
		Sessions session = new Sessions(sessionId, companyType, isPerfect,dto.getPk(), dto.getMobile(), dto.getCompanyPk(),dto.getCompanyName(),
				dto.getInvitationCode(), source, block);
		session.setIsAdmin(0);
		if (null != company) {
			// 如果公司是审核通过的供应商则存入店铺信息
			B2bStoreDto store = this.saveStoreBySession(company);
			if (null != store) {
				session.setStoreDto(store);
				//判断所属店铺是否是康海
				String storePk = PropertyConfig.getProperty("storePk", "");
				if (!"".equals(storePk) && storePk.equals(store.getPk())) {
					session.setStampDuty(2);
				}else {
					session.setStampDuty(1);
				}
				B2bTokenDto token = b2bTokenDao.getByStorePk(store.getPk());
				if (null != token) {
					tokenType = "2";
					if(1 == token.getAccType()){
						tokenType = "1";
					}
				}
			}
			//设置权限
			setMenuRoleList(dto, company, tokenType, session);
			//设置公司信息
			session.setCompanyDto(company);
//			List<String> list = JedisUtils.getList(company.getPk());
//			if (null == list) {
//				list = new ArrayList<String>();
//			}
//			list.add(sessionId);
//			JedisUtils.setList(company.getPk(), list, Source
//					.getBySource(source).getSessionTime());
		}
		// 上一次登录时间
		if (null != dto.getLoginTime()) {
			session.setLastLoginTime(DateUtil.formatDateAndTime(dto
					.getLoginTime()));
			try {
				updateMailByActivity(
						DateUtil.formatDateAndTime(dto.getLoginTime()), dto);
			} catch (Exception e) {
				logger.error("error", e);
			}
		}
		b2bMemberDaoEx.updateLoginTime(dto.getPk());
		session.setMemberDto(dto);
		// 将用户登录session信息存入缓存
		JedisUtils.set(sessionId, session, Source.getBySource(source).getSessionTime());
		// 单点登陆限制
		JedisUtils.setInvalid(Source.getBySource(source).getSourceType() + dto.getMobile(),sessionId, Source.getBySource(source).getSessionTime());
		return session;
	}

	
	/**
	 * 设置用户的权限
	 * @param dto  会员信息
	 * @param company  公司信息
	 * @param tokenType  token类型
	 * @param session  session
	 */
	private void setMenuRoleList(B2bMemberDto dto, B2bCompanyDto company,String tokenType, Sessions session) {
		List<B2bMemberRoleDto> roleList = memberRoleDao.getByMemberPk(dto.getPk());
		// 获取用户权限树形列表(默认一级id为'-1')
		if (null != roleList && roleList.size() > 0) {
			for (B2bMemberRoleDto mdDto : roleList) {
				B2bRoleDto rdto = b2bRoleDao.getByPk(mdDto.getRolePk());
				if (rdto == null || rdto.getIsDelete() == 2) {
					break;
				}
				// 超级管理员
				if (rdto.getCompanyType() == 0) {
					List<B2bRoleMenuNode> purlist = this.getChildren("-1", mdDto.getRolePk(), 0, null, 0, 1,tokenType,company.getBlock());
					session.setPurMenus(JsonUtils.convertToString(purlist));
					// 如果是供应商给予供应商权限
					if (null != company.getAuditSpStatus()&& company.getAuditSpStatus() == 2) {
						List<B2bRoleMenuNode> suplist = this.getChildren("-1", mdDto.getRolePk(),0, null, 0, 2, tokenType,company.getBlock());
						session.setSupMenus(JsonUtils.convertToString(suplist));
					}
					session.setIsAdmin(1);
					break;
				}
				// 采购商
				if (rdto.getCompanyType() == 1) {
					List<B2bRoleMenuNode> purlist = this.getChildren("-1", mdDto.getRolePk(), 0, null,rdto.getCompanyType(), 1, tokenType,company.getBlock());
					session.setPurMenus(JsonUtils.convertToString(purlist));
				}
				// 供应商
				if (rdto.getCompanyType() == 2&& null != company.getAuditSpStatus()&& company.getAuditSpStatus() == 2) {
					List<B2bRoleMenuNode> suplist = this.getChildren("-1", mdDto.getRolePk(), 0, null,rdto.getCompanyType(), 2, tokenType,company.getBlock());
					session.setSupMenus(JsonUtils.convertToString(suplist));
				}
			}
		}
	}

	private B2bStoreDto saveStoreBySession(B2bCompanyDto company) {
		B2bStoreDto store = null;
		if (company.getAuditSpStatus() != null
				&& company.getAuditSpStatus() == 2) {
			String cpk = null;
			if ("-1".equals(company.getParentPk())) {
				cpk = company.getPk();
			} else {
				cpk = company.getParentPk();
			}
			store = storeDao.getByCompanyPk(cpk);

		}
		return store;
	}

	/***
	 * 当天再次登陆，则白条活动站内信更新为已阅读
	 * 
	 * @param string
	 * @param dto
	 */
	private void updateMailByActivity(String string, B2bMemberDto dto) {
		int day = DateUtil.getCalculatedDays(string,
				DateUtil.formatYearMonthDay(new Date()));
		if (day == 0) {
			Query querys = Query.query(Criteria.where("freeSignName")
					.is("白条活动").and("memberPk").is(dto.getPk()).and("isRead")
					.is(1));
			Update update = new Update();
			update.set("isRead", 2);
			mongoTemplate.updateMulti(querys, update, SendMails.class);
		}

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
	 *            0:超级管理员(不用校验角色)
	 * @param searchType
	 *            0 查询全部 1查询采购商 2查询供应商
	 * @param tokenType
	 *            null 盛虹单独按钮 非null 无
	 * @return
	 */
	private List<B2bRoleMenuNode> getChildren(String id, String rolePk,
			int type, List<B2bMenuDto> slist, int companyType, int searchType,
			String tokenType,String block) {
		List<B2bRoleMenuNode> tlist = new ArrayList<B2bRoleMenuNode>();
		if (null != rolePk && !"".equals(rolePk)) {

			Map<String, Object> map = new HashMap<String, Object>();
			if (companyType > 0) {
				map.put("rolePk", rolePk);
			}
			if (searchType > 0) {
				map.put("companyType", searchType);
			}
			map.put("parentPk", id);
			map.put("type", type);
			map.put("tokenType", tokenType);
			if(null!=block&&!"".equals(block)){
				map.put("blocks", StringUtils.splitStrs(block));
			}
			List<B2bMenuDto> list = menuDao.searchMenuList(map);
			B2bRoleMenuNode tree = null;
			for (B2bMenuDto dto : list) {
				tree = new B2bRoleMenuNode();
				tree.setId(dto.getPk());
				tree.setText(dto.getDisplayName());
				tree.setEnText(dto.getName());
				List<B2bRoleMenuNode> tl = this
						.getChildren(dto.getPk(), rolePk, type, slist,
								companyType, searchType, tokenType,block);

				if (null != tl && tl.size() > 0) {
					tree.setChildren(tl);
				}
				tlist.add(tree);
			}
		}
		return tlist;

	}
}
