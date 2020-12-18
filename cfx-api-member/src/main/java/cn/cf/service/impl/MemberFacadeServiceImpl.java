package cn.cf.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.constant.SmsCode;
import cn.cf.constant.Source;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.CustomerDataImport;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.Sessions;
import cn.cf.entry.CustomerImport;
import cn.cf.entry.LoginCompany;
import cn.cf.entry.PackCustomer;
import cn.cf.entry.RespCustomer;
import cn.cf.jedis.JedisUtils;
import cn.cf.model.B2bCompany;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.model.B2bCustomerSalesman;
import cn.cf.model.B2bMember;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bCustomerMangementService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bMemberGradeService;
import cn.cf.service.B2bMemberRoleService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.MemberCompanyService;
import cn.cf.service.MemberFacadeService;
import cn.cf.service.MemberGroupService;
import cn.cf.service.MemberPointService;
import cn.cf.service.SendSmsService;
import cn.cf.service.SessionService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.SysRegionsService;
import cn.cf.util.DateUtil;
import cn.cf.util.EncodeUtil;
import cn.cf.util.EntityUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.MD5Utils;
import cn.cf.util.OSSUtils;
import cn.cf.util.SavaZipUtil;

@Service
public class MemberFacadeServiceImpl implements MemberFacadeService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SMS_CODE = "code";

	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MemberCompanyService b2bCompanyService;

	@Autowired
	private SendSmsService smsService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bCustomerSaleManService customerSaleManService;

	@Autowired
	private B2bMemberRoleService b2bMemberRoleService;

	@Autowired
	private B2bCustomerMangementService b2bCustomerMangementService;

	@Autowired
	private MemberPointService  memberPointService;


    @Autowired
    private B2bMemberGradeService b2bMemberGradeService;

    @Autowired
    private SysRegionsService sysRegionsService;
    
    @Autowired
    private MemberGroupService  memberGroupService;
    
    @Autowired
    private CuccSmsService smsServiceMaterial;


	@Override
	public String login(String mobile, String password, String sessionId,int source) {
		B2bMemberDto memberDto = b2bMemberService.getMember(mobile);
		RestCode restCode = checkMember(memberDto, password);
		if (restCode.getCode().equals(RestCode.CODE_0000.getCode())) {
			// 同一账号只能登陆一次限制
			checkLogined(mobile,source);
			//登录加积分的条件：注册后完善公司信息，并且cms后台审核通过的会员登录才能加积分
			if (null!=memberDto.getCompanyPk() && !"".equals(memberDto.getCompanyPk())) {
				B2bCompanyDtoEx company = b2bCompanyService.getCompany(memberDto.getCompanyPk());
				if (null != company && company.getAuditStatus()==2) {
					try {
						memberPointService.addPoint(memberDto.getPk(), memberDto.getCompanyPk(),1,MemberSys.FLOW_DIMEN_PER_LOGIN.getValue());
					} catch (Exception e) {
						logger.error("addPoint   ", e);
					}
				}
			//记录登录公司信息
				try {
					loginCompany(source, memberDto, company);
				} catch (Exception e) {
					logger.error("LoginCompany ",e);
				}
			}

			Sessions session = sessionService.addSessions(memberDto, sessionId,source);
			return restCode.toJson(session);
		}
		return restCode.toJson();

	}

	private void loginCompany(int source, B2bMemberDto memberDto,
			B2bCompanyDtoEx company) {
		Criteria c = new Criteria();
		c.and("companyPk").is(memberDto.getCompanyPk()).and("source").is(source).and("loginTime").is(DateUtil.formatYearMonthDay(new Date()));
		List<LoginCompany> list = mongoTemplate.find(new Query(c), LoginCompany.class);
		LoginCompany lc = new LoginCompany();
		String id = null;
		if(null == list  || list.size() == 0){
			id = KeyUtils.getUUID();
			lc.setId(id);
			lc.setCompanyPk(memberDto.getCompanyPk());
			lc.setAuditStatus(company.getAuditStatus());
			lc.setAuditSpStatus(company.getAuditSpStatus());
			lc.setLoginTime(DateUtil.formatYearMonthDay(new Date()));
			lc.setSource(source);
			mongoTemplate.save(lc);
		}else{
			Update update = new Update();
			id = list.get(0).getId();
			update.set("auditStatus", company.getAuditStatus());
			update.set("auditSpStatus", company.getAuditSpStatus());
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, LoginCompany.class);
		}
	}

	private boolean checkLogined(String mobile,Integer source) {
		Object oldSession = JedisUtils.get(Source.getBySource(source).getSourceType() + mobile);
		String oldSessionId = null;
		if (!(oldSession instanceof Boolean)) {
			oldSessionId = oldSession.toString();
			// 踢除上一个用户
			 JedisUtils.del(oldSessionId);
			JedisUtils.del(Source.getBySource(source).getSourceType() + mobile);
		}
		return false;
	}

	private RestCode checkMember(B2bMemberDto memberDto, String password) {
		RestCode restCode = RestCode.CODE_0000;
		// 用户是否存在
		if (null == memberDto) {
			restCode = RestCode.CODE_M002;
			return restCode;
		}
		// 验证密码
		if (!password.equals(memberDto.getPassword())) {
			restCode = RestCode.CODE_M005;
			return restCode;
		}
		// 验证启用状态
		if (2 == memberDto.getIsVisable()) {
			restCode = RestCode.CODE_M004;
			return restCode;
		}
		// 判断公司状态
		if (null != memberDto.getCompanyPk() && !"".equals(memberDto.getCompanyPk())) {
			B2bCompanyDtoEx company = b2bCompanyService.getCompany(memberDto.getCompanyPk());
			if (null == company) {
				restCode = RestCode.CODE_C001;
				return restCode;
			}
			if (2 == company.getIsVisable()) {
				restCode = RestCode.CODE_C002;
				return restCode;
			}
		}
		return restCode;
	}

	@Override
	public String register(B2bMemberDtoEx member, String sessionId,Integer source) {
		String rest = null;
		// 验证手机验证码
		boolean flag = smsService.checkSmsCode(member.getMobile(), member.getCode());
		if (flag) {
			//System.out.println("=====================：验证用户是否已经注册");
			// 验证用户是否已经注册
			B2bMemberDto memberDto = b2bMemberService.getMember(member.getMobile());
			if (null != memberDto) {
				flag = false;
				rest = RestCode.CODE_M001.toJson();
			}
			// 注册会员
			if (flag) {
				rest = addMember(member, sessionId,source);
			}
		} else {
			rest = RestCode.CODE_S004.toJson();
		}
		return rest;
	}

	private String addMember(B2bMemberDtoEx member, String sessionId,Integer source) {
		String rest;
		B2bMember m = new B2bMember();
		m.UpdateDTO(member);
		String memberPk = b2bMemberService.addMember(m);
		member.setPk(memberPk);
		// 发短信 
		if (null != memberPk) {
			//1:发短信
			try {
				smsServiceMaterial.sendMSM(member, member.getMobile(), SmsCode.REGISTER.toString(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Sessions session = sessionService.addSessions(member, sessionId,source);
			// 验证通过清除验证码
			JedisUtils.del(SMS_CODE + member.getMobile());
			rest = RestCode.CODE_0000.toJson(session);
		} else {
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}

	@Override
	public String logout(Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		boolean falge = JedisUtils.del(session.getSessionId());
		if (!falge) {
			restCode = RestCode.CODE_S999;
		}
		boolean falge1 = JedisUtils.del(Source.getBySource(session.getSource()).getSourceType() + session.getMobile());
		if(null != session.getCompanyDto() && null != session.getCompanyDto().getPk() && !"".equals(session.getCompanyDto().getPk())){
			Set<String> set = JedisUtils.getSet(session.getCompanyDto().getPk());
			if(null != set){
				set.remove(session.getSessionId());
				if(set.size()==0){
					JedisUtils.del(session.getCompanyDto().getPk());
				}else{
					JedisUtils.set(session.getCompanyDto().getPk(), set, Source.getBySource(session.getSource()).getSessionTime());
				}
			}
		}
		if (!falge1) {
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}

	@Override
	public String resetPassword(String mobile, String password) {
		RestCode code = RestCode.CODE_0000;
		// 验证用户
		B2bMemberDto memberDto = b2bMemberService.getMember(mobile);
		if (null != memberDto) {
			// 更新会员
			B2bMember member = new B2bMember();
			member.setPk(memberDto.getPk());
			member.setPassword(password);
			int result = b2bMemberService.updateMember(member);
			// 发短信
			if (result == 1) {
				try {
					smsServiceMaterial.sendMSM(memberDto, mobile, SmsCode.FIND_PWD.getValue(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				code = RestCode.CODE_S999;
			}
		} else {
			RestCode.CODE_M002.toJson();
		}
		return code.toJson();
	}

	@Override
	public String updatePassword(String mobile, String password, String newPassword) {
		RestCode code = RestCode.CODE_0000;
		// 验证用户
		B2bMemberDto memberDto = b2bMemberService.getMember(mobile);
		boolean flag = true;
		if (null == memberDto) {
			code = RestCode.CODE_M002;
			flag = false;
		}
		// 验证原密码
		if (flag && !password.equals(memberDto.getPassword())) {
			code = RestCode.CODE_M005;
			flag = false;
		}
		// 更新会员
		if (flag) {
			B2bMember member = new B2bMember();
			member.setPk(memberDto.getPk());
			member.setPassword(newPassword);
			int result = b2bMemberService.updateMember(member);
			// 发短信
			if (result == 1) {
				try {
					smsServiceMaterial.sendMSM(memberDto, mobile,SmsCode.ED_PWD.getValue(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				code = RestCode.CODE_S999;
			}
		}
		return code.toJson();
	}

	@Override
	public String prefectCompany(B2bCompanyDtoEx companyDto, B2bMemberDto memberDto, Sessions session) {
		String rest = null;
		B2bCompany newCompany = null;
		B2bCompanyDto company = b2bCompanyService.getByPk(null==session.getCompanyDto()?"":session.getCompanyDto().getPk());
		//验证统一社会信息代码已存在
//		Map<String, Object> tempmap=new HashMap<>();
//		tempmap.put("organizationCode", companyDto.getOrganizationCode());
//		tempmap.put("isDelete", 1);
//		List<B2bCompanyDto> list = b2bCompanyService.getCompanyList(tempmap);
//		if (list != null && list.size() > 0) {
//			if (null==companyDto.getPk() || "".equals(companyDto.getPk())) {
//				return RestCode.CODE_C004.toJson();
//			}
//			B2bCompanyDto tempCompany = list.get(0);
//			if (!tempCompany.getPk().equals(companyDto.getPk())) {
//				return RestCode.CODE_C004.toJson();
//			}
//		}
		
		// 1:当前会员已有所属公司
		if (null != company) {
			// 如果公司审核已经通过，则不可以修改公司信息，直接返回
			if (company.getAuditStatus() == 2) {
				session.setCompanyDto(company);
				rest = RestCode.CODE_C003.toJson(session);
				JedisUtils.expire(session.getSessionId(), Source.getBySource(session.getSource()).getSessionTime());
			} else {
				// 所属公司还没有审核通过，则修改公司信息
				newCompany = b2bCompanyService.perfectCompanyDto(companyDto, memberDto);
			}
		} else {
			// 2：如果当前会员没有所属公司，新增公司
			newCompany = b2bCompanyService.addCompany(companyDto, memberDto);
		}
		if (null != newCompany && newCompany.getIsPerfect() == 2) {
			B2bMemberDto dto = b2bMemberService.getMember(memberDto.getMobile());
			session = sessionService.addSessions(dto, session.getSessionId(),session.getSource());
			// 发送短信通知
			Map<String, String> map = new HashMap<String, String>();
			map.put("uname", session.getCompanyName());
			smsServiceMaterial.sendMSM(memberDto, memberDto.getMobile(), SmsCode.AUD_MEMBER.getValue(), map);
		}
		rest = RestCode.CODE_0000.toJson(session);
		return rest;
	}

	@Override
	public B2bGoodsDto getB2bGoodsByPk(String pk, String memberPk, String auctionPk) {
		return null;
	}

	@Override
	public RestCode addCustomerSaleMan(B2bCustomerSalesmanDto cus) {
		RestCode restCode = RestCode.CODE_0000;
		if (null != cus.getMemberPk() && !"".equals(cus.getMemberPk())) {
			cus.setPk(KeyUtils.getUUID());
			// 是否可匹配该类型
			if (customerSaleManService.isCanSuit(cus)) {
				// 是否已匹配该类型
				if (customerSaleManService.isRepeat(cus)) {
					restCode = customerSaleManService.insertSaleman(cus);

				} else {
					restCode = RestCode.CODE_O0010;
				}
			}else {
				restCode = RestCode.CODE_O0011;
			}
		} else {
			restCode = RestCode.CODE_A001;
		}
		return restCode;
	}

	@Override
	@Transactional
	public RestCode addMember(B2bMemberDtoEx member) {
		RestCode restCode = RestCode.CODE_0000;
		try {
				// 新增人员
				if (member.getPk() == null || "".equals(member.getPk())) {
					B2bMemberDto mdto = b2bMemberService.getByMobile(member.getMobile());
					if(null == mdto){
						insertMember(member);
						// 添加发送短信
						try {
							Map<String, String> smsMap = new HashMap<String, String>();
							smsMap.put("product", member.getCompanyName());
							smsServiceMaterial.sendMSM(member, member.getMobile(),  SmsCode.AD_MEMBER.getValue(), smsMap);
						} catch (Exception e) {
							logger.error("errorSendMessageContent",e);
						}
					}else if(null != mdto && (null == mdto.getCompanyPk() || "".equals(mdto.getCompanyPk()))){
						member.setPk(mdto.getPk());
						editMember(member );
					}else{
						restCode = RestCode.CODE_M001;
					}
				} else {
					if(!b2bMemberService.isRepeat(member)){
						B2bMemberDto m = b2bMemberService.getByPk(member.getPk());
						if (member.getParentPk() != null && member.getParentPk().equals(m.getPk())) {
							restCode = RestCode.CODE_M0010;
						} else {
							//会员原先是组长，如果更改为非组长的情况下
							if (member.getParentPk() != null && m.getParentPk()!=null&&
									"-1".equals(m.getParentPk()) && !member.getParentPk().equals(m.getParentPk())) {
								b2bMemberService.updateParentPk(member.getPk());
								memberGroupService.deleteByParentPk(member.getPk());
							} 
							memberGroupService.deleteByMemberPk(member.getPk());
							editMember(member );
						}
					}else{
						restCode = RestCode.CODE_M001;
					}
				}
		} catch (Exception e) {
			logger.error("errorAddMember",e);
			restCode = RestCode.CODE_S999;
		}

		return restCode;
	}
	
	
	

	private void editMember(B2bMemberDtoEx member ) throws Exception {
		// 删除人员角色
		b2bMemberRoleService.deleteByMemberPkAndType(member.getPk(), member.getCompanyType());
		 //当前会员非组长，且绑定组长一个或多个，逗号分隔
        if(null!=member.getParentPk()&&!"".equals(member.getParentPk().trim())&&!"-1".equals(member.getParentPk())){
        	memberGroupService.insert(member);
        	member.setParentPk("");
        }
		b2bMemberService.update(member);
		if (!"".equals(member.getRolePk())) {
			b2bMemberRoleService.insert(member.getRolePk(), member.getPk());
		}
	}

	private void insertMember(B2bMemberDtoEx member) throws Exception {
		String pk = KeyUtils.getUUID();
		member.setPk(pk);
		member.setPassword(EncodeUtil.MD5Encode(member.getMobile()));
		// 绑定邀请码 当公司会员数注册达到100W时,此方法得修改
		member.setInvitationCode(b2bMemberService.getInvitationCode());
		//System.out.println("-------------------"+member.getAddPk());
		//新会员的默认等级，默认等级名称
        member.setLevel(1);
        B2bMemberGradeDto  gradeDto = b2bMemberGradeService.getDtoByGradeNumber(1);
        if (gradeDto!=null) {
        	member.setLevelName(gradeDto.getGradeName());
		}
        //当前会员非组长，且绑定组长一个或多个，逗号分隔
        if(null!=member.getParentPk()&&!"".equals(member.getParentPk().trim())&&!"-1".equals(member.getParentPk())){
        	memberGroupService.insert(member);
        	member.setParentPk("");
        }
		b2bMemberService.insert(member);
		//更新当前账号已添加人数,满足额外奖励添加人员加积分
		updateAddMemberCount(member);
		//首次添加人员添加积分
		addPoint3(member.getAddPk(),member.getCompanyPk(),MemberSys.ACCOUNT_DIMEN_ADDME.getValue(),member.getCompanyType());
		// 添加用户角色
		if (!"".equals(member.getRolePk())) {
			b2bMemberRoleService.insert(member.getRolePk(), pk);
		}

	}

	private void updateAddMemberCount(B2bMemberDtoEx member) throws Exception {
		try {
			B2bMemberDto  dto = b2bMemberService.getByPk(member.getAddPk());
			B2bMemberDtoEx  dtoEx = new B2bMemberDtoEx();
			dtoEx.setPk(member.getAddPk());
			dtoEx.setAddMembers(dto.getAddMembers()==null?1:dto.getAddMembers()+1);
			b2bMemberService.update(dtoEx);
			//累计添加人数---添加积分
			List<B2bDimensionalityExtrewardDto>  extrewardDto	= memberPointService.getDimensionalityExtReward(MemberSys.ACCOUNT_DIMEN_ADDME.getValue());
			if(extrewardDto.size()>0){
				//是否满足累计添加人数
				if (b2bMemberService.isAccumulativeAddMember(extrewardDto.get(0),member.getAddPk())) {
					memberPointService.addPointForExtReward(member.getAddPk(),member.getCompanyPk(), 1, MemberSys.ACCOUNT_DIMEN_ADDME.getValue());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public RestCode updateMember(B2bMemberDtoEx dto, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		// 删除
		try {
			if (null != dto.getIsDelete() && dto.getIsDelete() == 2) {
				dto.setUpdateTime(new Date());
				dto.setCompanyPk("");
				dto.setCompanyName("");
				dto.setAuditStatus(1);
				dto.setAuditPk("");
				dto.setEmployeeNumber("");
				dto.setParentPk("");
				// 删除改业务员的相关关系
				b2bMemberService.updateParentPk(dto.getPk());
				b2bMemberRoleService.deleteByMemberPk(dto.getPk());
				customerSaleManService.deleteByMemberPk(dto.getPk());
				memberGroupService.deleteByParentPkAndMemberPk(dto.getPk());//解除组长组员关联关系

				B2bMemberDto m = b2bMemberService.getByPk(dto.getPk());
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("product", m.getCompanyName());
				smsMap.put("uname", m.getMobile());
				smsServiceMaterial.sendMSM(m, m.getMobile(),SmsCode.UN_COMPANY.toString(), smsMap);
			} else {// 审核
				dto.setAuditPk(session.getMemberPk());
				dto.setAuditTime(new Date());
			}
			b2bMemberService.update(dto);
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
			e.printStackTrace();
		}

		return restCode;
	}

	@Override
	public RestCode customerImport(String filePath, String storePk) {
		RestCode code = RestCode.CODE_0000;
		String ossPath = filePath;
		try {
			filePath = OSSUtils.downloadOSS(filePath);
			//解压zip文件
			SavaZipUtil.saveZip(filePath,PropertyConfig.getProperty("customer_file_path"));
			File file = new File(PropertyConfig.getProperty("customer_file_path"));
			RespCustomer rc = b2bCustomerMangementService.customerList(file);
			if(rc.getCode().equals(RestCode.CODE_0000.getCode())){
				b2bCustomerMangementService.b2bCompany(rc.getCustomerList(),storePk);
			}else{
				code = RestCode.CODE_Z000;
				code.setMsg(rc.getMsg());
			}
		} catch (Exception e) {
			logger.error("customerImport:----------",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			code = RestCode.CODE_Z000;
			code.setMsg("文件格式错误");
		}finally{
			OSSUtils.deleteDir(PropertyConfig.getProperty("customer_file_path"));
			OSSUtils.deleteDir(PropertyConfig.getProperty("customer_zip_path"));
			if(null != ossPath && !"".equals(ossPath)){
				String[] file = ossPath.split(PropertyConfig.getProperty("oss_url"));
				if(file.length>1){
					OSSUtils.delete(file[1]);
				}
			}
		}
		return code;
	}


	private void addPoint3(String memberPk, String companyPk, String dimenType, String companyType) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dimenType", dimenType);
            map.put("companyPk", companyPk);
            map.put("orderNumber", companyType);
            List<MangoMemberPoint> list = memberPointService.searchPointList(map);
            if (list == null || list.size() == 0) {
                memberPointService.addPoint3(memberPk, companyPk, 1, dimenType, companyType);
            }
        } catch (Exception e) {
            logger.error("addPoint   ", e);
            e.printStackTrace();
        }
    }

	@Override
	public String noVerificationLogin(String mobile,String erpToken,String sessionId,Integer source) {
			RestCode code = RestCode.CODE_0000;
			String rest = null;
			Boolean flag = true;
			B2bMemberDto memberDto = null;
			B2bTokenDto token = JedisUtils.get(erpToken, B2bTokenDto.class);
			if(null == token){
				flag = false;
				code = RestCode.CODE_S001;
			}
			if(flag){
				memberDto = b2bMemberService.getMember(mobile);
				if(null == memberDto){
					code = RestCode.CODE_M002;
					flag = false;
				}
			}
			if(flag){
				// 同一账号只能登陆一次限制
				if (checkLogined(mobile,source)) {
					code =  RestCode.CODE_S999;
					flag = false;
				}
			}
			if(flag){
				Sessions session = sessionService.addSessions(memberDto, sessionId,source);
				rest = code.toJson(session);
			}else{
				rest = code.toJson();
			}
			
			return rest;
	}

	@Override
	public String importCustomerData(CustomerDataImport customerDataImport) {
		String rest = RestCode.CODE_0000.toJson();
		try {
			customerDataImport.setStatus(1);
			customerDataImport.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			mongoTemplate.save(customerDataImport);
		} catch (Exception e) {
			logger.error("importCustomer:",e);
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}

	@Override
	public PageModel<CustomerDataImport> searchCustomerDataImport(String storePk,Integer start,Integer limit) {
			PageModel<CustomerDataImport> pm = new PageModel<CustomerDataImport>();
			if(null != storePk && !"".equals(storePk)){
				Criteria c = new Criteria();
				c.and("storePk").is(storePk);
				Query query = new Query(c);
				query.with(new Sort(Sort.Direction.DESC, "insertTime"));
				query.skip(start).limit(limit);
				
				List<CustomerDataImport> list = mongoTemplate.find(query, CustomerDataImport.class);
				int counts = (int) mongoTemplate.count(query, CustomerDataImport.class);
				pm.setTotalCount(counts);
				pm.setDataList(list);
				if (null != start) {
					pm.setStartIndex(start);
					pm.setPageSize(limit);
				}
			}
	        return pm;
	}

	@Override
	public RestCode importCustomer(List<CustomerImport> list,String storePk){
		RestCode code = RestCode.CODE_0000;
		if(null != list && list.size() != 0){
			List<PackCustomer> packList = new ArrayList<PackCustomer>();
			for(CustomerImport customer : list){
				//判断excel表里属性 只要有空值则不导入
				if(!EntityUtil.checkObjAllFieldsIsNotNull(customer)){
					throw new RuntimeException();
				}
				//数据封装
				packList.add(new PackCustomer(customer, storePk));
			}
			String companyPk = null;
			for(PackCustomer pc : packList){
				B2bMemberDto oldMember = b2bMemberService.getByMobile(pc.getMember().getMobile());
				B2bCompanyDto oldCompany = b2bCompanyService.getCompanyByName(pc.getCompany().getName());
				if(null == oldCompany){
					companyPk = KeyUtils.getUUID();
					if(null == oldMember){
						pc.getMember().setCompanyPk(companyPk);
						pc.getMember().setCompanyName(pc.getCompany().getName());
						pc.getCompany().setParentPk("-1");//默认为总公司
						//创建会员
						String memberPk = createMember(pc.getMember());
						//将会员设为超级管理员
						b2bMemberRoleService.setAdmin(memberPk);
					}else{
						//更新会员返回会员的总公司pk
						String parentPk = updateMember(oldMember);
						//将公司归属到会员的总公司下
						pc.getCompany().setParentPk(parentPk);
					}
					//创建公司
					createOrUpdateCompay(pc.getCompany(),companyPk,1);
				}
				if(null != oldCompany){
					companyPk = oldCompany.getPk();
					if(null == oldMember){
						pc.getMember().setCompanyPk(companyPk);
						pc.getMember().setCompanyName(pc.getCompany().getName());
						//创建会员
						createMember(pc.getMember());
						//更新公司
						createOrUpdateCompay(pc.getCompany(),companyPk,2);
					}else{
						//更新会员
						String parentPk = updateMember(oldMember);
						if(oldMember.getCompanyPk().equals(oldCompany.getPk())){
							//更新公司
							createOrUpdateCompay(pc.getCompany(),companyPk,2);
						}else{
							//更新公司以及修改对应子公司的归属
							pc.getCompany().setParentPk(parentPk);
							updateCompanyAndChilds(pc.getCompany(),companyPk);
						}
					}
				}
				//更新客户业务员信息
				updateCustomerManagement(pc.getCustomerManagement(), pc.getSalesman(), companyPk);
			}
		}
		return code;
	}
	
	private String createMember(B2bMember member){
		String memberPk = KeyUtils.getUUID();
		member.setPk(memberPk);
		member.setAuditStatus(2);
		member.setAuditTime(new Date());
		member.setInsertTime(new Date());
		member.setUpdateTime(new Date());
		member.setIsVisable(1);
		member.setPassword(MD5Utils.MD5Encode(member.getMobile(), "utf-8"));
		member.setRegisterSource(1);
		b2bMemberService.insert(member);
		return memberPk;
	}
	
	private String updateMember(B2bMemberDto member){
		//会员没审核通过更新会员
		if(null !=member.getAuditStatus() && member.getAuditStatus() !=2){
			member.setAuditStatus(2);
			member.setAuditTime(new Date());
			member.setUpdateTime(new Date());
			b2bMemberService.update(member);
		}
		B2bCompanyDto company = b2bCompanyService.getCompany(member.getCompanyPk());
		if(null != company){
			return "-1".equals(company.getParentPk())?company.getPk():company.getParentPk();
		}else{
			return null;
		}
	}
	
	
	private void createOrUpdateCompay(B2bCompany company,String companyPk,Integer type){
		company.setPk(companyPk);
		company.setAuditStatus(2);
		company.setAuditTime(new Date());
		company.setInsertTime(new Date());
		company.setUpdateTime(new Date());
		company.setIsDelete(1);
		company.setIsVisable(1);
		company.setIsPerfect(2);
		//绑定省市区pk
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentPk", "-1");
		map.put("name",company.getProvinceName());
		SysRegionsDto province =  sysRegionsService.getRegionsPk(map);
		if(null != province){
			company.setProvince(province.getPk());
			company.setProvinceName(province.getName());
			map.put("parentPk", province.getPk());
			map.put("name", company.getCityName());
			SysRegionsDto city =  sysRegionsService.getRegionsPk(map);
			if(null != city){
				company.setCity(city.getPk());
				company.setCityName(city.getName());
				map.put("parentPk", city.getPk());
				map.put("name", company.getAreaName());
				SysRegionsDto area =  sysRegionsService.getRegionsPk(map);
				if(null != area){
					company.setArea(area.getPk());
					company.setAreaName(area.getName());
				}
			}
		}
		b2bCompanyService.insertOrUpdateCompany(company, type);
	}

	
	private void updateCompanyAndChilds(B2bCompany company,String companyPk){
		createOrUpdateCompay(company, companyPk, 2);
		b2bCompanyService.updateParentPk(company.getPk(),company.getParentPk());
	}
	
	private void updateCustomerManagement(B2bCustomerManagement management,B2bCustomerSalesman saleMan,String companyPk){
		management.setPurchaserPk(companyPk);
		String pk = b2bCustomerMangementService.insertOrUpdate(management);
		saleMan.setCustomerPk(pk);
		saleMan.setPurchaserPk(companyPk);
		customerSaleManService.insertOrUpdateSaleMan(saleMan);
	}
	
}
