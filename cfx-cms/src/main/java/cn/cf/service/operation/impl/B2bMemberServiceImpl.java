package cn.cf.service.operation.impl;

import cn.cf.PageModel;
import cn.cf.common.AddMemberSysPoint;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.constant.MemberSys;
import cn.cf.constant.SmsCode;
import cn.cf.dao.*;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.dto.B2bRoleDto;
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.model.B2bMember;
import cn.cf.model.B2bMemberRole;
import cn.cf.model.ManageAccount;
import cn.cf.service.CuccSmsService;
import cn.cf.service.operation.B2bMemberService;
import cn.cf.util.KeyUtils;
import cn.cf.util.MD5Utils;
import cn.cf.util.RandomUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class B2bMemberServiceImpl implements B2bMemberService {

    @Autowired
    private B2bMemberExtDao b2bMemberExtDao;

    @Autowired
    private B2bMemberRoleExDao b2bMemberRoleExDao;

    @Autowired
    private B2bRoleExtDao b2bRoleExtDao;

    @Autowired
    private AddMemberSysPoint addMemberSysPoint;

    @Autowired
    private B2bMemberGradeDao b2bMemberGradeDao;

    @Autowired
    private CuccSmsService sysService;

    @Autowired
    private B2bMemberGroupExtDao  b2bMemberGroupExtDao;

    private int removeMember(String pk) throws Exception {

        B2bMemberDto m = b2bMemberExtDao.getByPk(pk);
        m.setUpdateTime(new Date());
        m.setCompanyPk("");
        m.setCompanyName("");
        m.setAuditStatus(1);
        m.setAuditPk("");
        //TODO
        //m.setIsDelete(1);
        B2bMember b2bMember = new B2bMember();
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        BeanUtils.copyProperties(b2bMember, m);
        int result = b2bMemberExtDao.update(b2bMember);
        b2bMemberExtDao.delB2bMemberComponyByPk(pk);

        b2bMemberRoleExDao.deleteByMemberPks(m.getPk());

        b2bMemberGroupExtDao.deleteByMemberPk(m.getPk());
        return result;
    }

    @Override
    public B2bMemberDto getMemberByPk(String pk) {

        return this.b2bMemberExtDao.getByPk(pk);
    }

    @Override
    public String delMember(String pk) throws Exception {

        B2bMemberDto member = this.getMemberByPk(pk);
        if (member != null) {
            int re = this.removeMember(pk);
            if (re != 0) {
                // TODO 发送短信
            }
        }
        return Constants.RESULT_SUCCESS_MSG;
    }

    private B2bMemberDto getMemberByMobile(String mobile) {
        return b2bMemberExtDao.getByMobile(mobile);
    }

    private B2bMemberDto getMemberByEmployeeNumber(String companyPk,
                                                   String employeeNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyPk", companyPk);
        map.put("employeeNumber", employeeNumber);
        map.put("isDelete", 1);
        return b2bMemberExtDao.searchMemberByEmployeeNumber(map);
    }

    @Override
    public String updateMember(ManageAccount adto, B2bMember b2bMember, String isAuditStatusTwo)
            throws Exception {

        if (null == b2bMember.getPk() || "".equals(b2bMember.getPk())) {
            B2bMemberDto memberDto = getMemberByMobile(b2bMember.getMobile());
            // 已注册
            if (null != memberDto) {
                // 如果用户有绑定公司信息 则不给于添加
                if (null != memberDto.getCompanyPk()) {
                    return Constants.MOBILE_REPEAT;
                    // 如果是已删除的用户 则重新绑定公司
                } else {
                    b2bMember.setPk(memberDto.getPk());
                }
            }
        }
        if (null != b2bMember.getEmployeeNumber()
                && !"".equals(b2bMember.getEmployeeNumber())) {

            B2bMemberDto memberDto = getMemberByEmployeeNumber(
                    b2bMember.getCompanyPk(), b2bMember.getEmployeeNumber());
            if (null != memberDto) {
                return Constants.EMPLOYEE_NUMBER_EXIST;
            }
        }
        if (b2bMember.getAuditStatus() != null
                && b2bMember.getAuditStatus() > 1) {
            b2bMember.setAuditPk(adto.getPk());
            b2bMember.setAuditTime(new Date());
        }
        int result = updateB2bMember(b2bMember, isAuditStatusTwo);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result <= 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    public Integer updateB2bMember(B2bMember b2bMember, String isAuditStatusTwo) {
        int result = 0;
        if (b2bMember.getPk() != null && !"".equals(b2bMember.getPk())) {
            b2bMember.setUpdateTime(new Date());
            if ("2".equals(isAuditStatusTwo)) {
                B2bMemberDto memberDto = b2bMemberExtDao.getByPk(b2bMember.getPk());
                if (memberDto != null && memberDto.getAuditStatus() != 2) {//没有审核通过的会员
                    result = b2bMemberExtDao.update(b2bMember);
                    addMemberSysPoint.addPoint(memberDto.getPk(), memberDto.getCompanyPk(), MemberSys.ACCOUNT_DIMEN_ADDME.getValue(),null);
                }
            } else {
                result = b2bMemberExtDao.update(b2bMember);
            }
            // 更新缓存
            B2bMemberDto newMember = b2bMemberExtDao.getByPk(b2bMember.getPk());
            JedisMaterialUtils.set(b2bMember.getPk(), newMember, 1800);
            result = 1;
        } else {
            String pk = KeyUtils.getUUID();
            b2bMember.setPk(pk);
            b2bMember.setIsVisable(1);
            //TODO
            //b2bMember.setIsDelete(1);
            b2bMember.setAuditStatus(1);
            b2bMember.setAuditTime(new Date());
            b2bMember.setInsertTime(new Date());
            b2bMember.setRegisterSource(4);
            b2bMember.setExperience(0.0);
            b2bMember.setShell(0.0);
            b2bMember.setLevel(1);
            B2bMemberGradeDto  gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(1);
            if (gradeDto!=null) {
                b2bMember.setLevelName(gradeDto.getGradeName());
            }
            //绑定邀请码  当公司会员数注册达到100W时,此方法得修改
            String invitationCode = RandomUtil.getRandomPassword();
            for (int i = 0; i < 1000000; i++) {
                int count =  b2bMemberExtDao.memberCountByInvitationCode(invitationCode);
                if(count == 0){
                    break;
                }
                invitationCode = RandomUtil.getRandomPassword();
            }
            b2bMember.setInvitationCode(invitationCode);

            b2bMember.setPassword(MD5Utils.MD5Encode(b2bMember.getMobile(), "utf-8"));
            result = b2bMemberExtDao.insert(b2bMember);
            // 发短信通知、向关联表中添加数据
            if (result == 1) {
                try {
                    Map<String, String> smsMap = new HashMap<String, String>();
                    smsMap.put("product", b2bMember.getCompanyName());
                    B2bMemberDto memberDto = b2bMemberExtDao.getByPk(pk);
                    sysService.sendMSM(memberDto,b2bMember.getMobile(), SmsCode.AD_MEMBER.getValue(),smsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("companyPk", b2bMember.getCompanyPk());
                List<B2bMemberDto> list = b2bMemberExtDao.searchList(map);
                if (list != null && list.size() > 1) {

                } else {
                    B2bMemberRole dto = new B2bMemberRole();
                    map.put("isVisable", 1);
                    map.put("isDelete", 1);
                    map.put("companyType", Constants.ZERO);
                    List<B2bRoleDto> roleList = b2bRoleExtDao.searchList(map);//获取超级管理员
                    if (roleList != null && roleList.size() > 0) {
                        String rolePk = roleList.get(0).getPk();
                        dto.setPk(KeyUtils.getUUID());
                        dto.setMemberPk(pk);
                        dto.setRolePk(rolePk);
                        result = b2bMemberRoleExDao.insert(dto);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public PageModel<B2bRoleExtDto> getAllRoleList(QueryModel<B2bRoleDto> qm,
                                                   String memberPk) {

        Map<String, Object> map = new HashMap<String, Object>();
        PageModel<B2bRoleExtDto> pm = new PageModel<B2bRoleExtDto>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("isVisable", 1);
        map.put("isDelete", 1);
        map.put("memberPk", memberPk);
        List<B2bRoleExtDto> list = b2bRoleExtDao.getRoleList(map);
        int counts = b2bRoleExtDao.getRoleCount(map);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int insertMemberRole(List<Map<String, Object>> list)
            throws Exception {

        if (list != null && list.size() > 0) {
            String memberPk = list.get(0).get("memberPk").toString();
            b2bRoleExtDao.delMemberRole(memberPk);
            for (int i = 0; i < list.size(); i++) {
                b2bRoleExtDao.insertMemberRole(list.get(i));
            }
        }
        return 1;
    }

    @Override
    public List<B2bRoleDto> getRoleByMember(String memberPk) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberPk", memberPk);
        return b2bRoleExtDao.getRoleByMember(map);
    }

}
