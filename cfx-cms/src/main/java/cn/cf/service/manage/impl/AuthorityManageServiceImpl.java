/**
 *
 */
package cn.cf.service.manage.impl;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.entity.AccountAuthColEntity;
import cn.cf.entity.BtnTree;
import cn.cf.model.*;
import cn.cf.property.PropertyConfig;
import cn.cf.service.manage.AuthorityManageService;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.cf.common.utils.JedisMaterialUtils.*;

/**
 * @author bin
 */
@Service
public class AuthorityManageServiceImpl implements AuthorityManageService {

    @Autowired
    private ManageAccountExtDao manageAccountExtDao;

    @Autowired
    private ManageAuthorityExtDao manageAuthorityExtDao;

    @Autowired
    private ManageRoleExtDao manageRoleExtDao;

    @Autowired
    private ActUserDao actUserDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private B2bTokenDao b2bTokenDao;

    @Autowired
    private B2bTokenExtDao b2bTokenExtDao;
    @Autowired
    private SysPropertyExtDao sysPropertyDao;


    public ManageAccountExtDto getManageAccountByLoginName(String username) {

        return manageAccountExtDao.getManageAccountByLoginName(username);
    }

    public List<ManageAuthorityDto> getManageAuthorityByAccount(String parentPk,
                                                                String rolePk, int type, int inCludeBtn, String account) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentPk", parentPk);
        map.put("rolePk", rolePk);
        map.put("type", type);
        map.put("isBtn", inCludeBtn);
        if (account != null) {
            MemberShip memberShip = actUserDao.getMemberShipByUserId(account);
            if (memberShip == null || memberShip.getGroupId().equals("jingrongzhuanyuan")) {
                ManageAuthorityDto manageAuthorityDto = manageAuthorityExtDao.getByName("EM_RF_APPROVAL_MG");
                if (manageAuthorityDto != null) {
                    map.put("noPk", manageAuthorityDto.getPk());
                }
            }
        }
        return manageAuthorityExtDao.getManageAuthorityByAccount(map);
    }

    @Override
    public List<ManageAuthorityDto> getBtnRoleList(String rolePk, String btnName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rolePk", rolePk);
        map.put("name", btnName);
        return manageAuthorityExtDao.getBtnManageAuthorityByRolePk(map);
    }

    @Override
    public List<BtnTree> getBtnByPartentPk(String partentPk, String rolePk) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentPk", partentPk);
        map.put("rolePk", rolePk);
        return manageAuthorityExtDao.getBtnByPartentPk(map);
    }


    @Override
    public ManageRoleDto getManageRoleByPk(String rolePk) {
        ManageRoleDto dto = null;
        if (null != rolePk && !"".equals(rolePk)) {
            dto = this.manageRoleExtDao.getManageRoleByPk(rolePk);
        }
        return dto;
    }

    @Override
    public PageModel<ManageRoleDto> searchRoleGrid(QueryModel<ManageRoleDto> qm) {
        PageModel<ManageRoleDto> pm = new PageModel<ManageRoleDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);
        int totalCount = manageRoleExtDao.searchGridCount(map);
        List<ManageRoleDto> list = manageRoleExtDao.searchGrid(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }


    @Override
    public B2bTokenDto searchB2bTokenByName(B2bTokenExtDto dto) {
        return b2bTokenDao.getByStoreName(dto.getStoreName());
    }

    @Override
    public int updateB2bToken(B2bTokenExtDto dto) {
        B2bToken token = new B2bToken();
        token.UpdateDTO(dto);
        int result = 0;
        if (dto.getPk() != null && !"".equals(dto.getPk())) {
            result = b2bTokenExtDao.updateObj(token);
        } else {
            token.setPk(KeyUtils.getUUID());
            token.setIsDelete(1);
            token.setIsVisable(1);
            result = b2bTokenDao.insert(token);
        }
        return result;
    }


    @Override
    public int updateB2bTokenStatus(String pk, Integer isVisable, Integer isDelete) {
        B2bToken model = new B2bToken();
        int result = 0;
        if (isVisable != null) {
            model.setPk(pk);
            model.setIsVisable(isVisable);
            result = b2bTokenDao.update(model);
        }
        if (isDelete != null) {
            result = b2bTokenDao.delete(pk);
        }
        return result;
    }

    @Override
    public List<ManageAuthorityDto> getManageAuthorityByRolePk(String rolePk) {
        return manageAuthorityExtDao.getManageAuthorityByRolePk(rolePk);
    }

    public List<ManageRoleDto> getManageRoleByName(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        return manageRoleExtDao.getManageRoleByName(map);
    }

    @Override
    public String updatePassword(ManageAccount account) {
        String pk = null;
        if (account.getPk() != null && !"".equals(account.getPk())) {
            pk = account.getPk();
            manageAccountExtDao.updatePassword(account);
        }
        return pk;
    }

    @Override
    public int isAuthority(String rolePk) {
        return manageAuthorityExtDao.isAuthorityCount(rolePk);
    }

    @Override
    public int updateManaegRole(ManageRole role, String node) {
        String pk = null;
        int result = 0;
        if (role.getPk() != null && !"".equals(role.getPk())) {
            result += manageRoleExtDao.update(role);
            pk = role.getPk();
        } else {
            pk = KeyUtils.getUUID();
            role.setPk(pk);
            role.setIsDelete(1);
            role.setInsertTime(new Date());
            result += manageRoleExtDao.insert(role);
        }


        if (null != pk && !"".equals(pk)) {
            ManageRoleAuthority mra = new ManageRoleAuthority();
            //查询所有此角色下的账户
            List<ManageAccountDto> accountList = manageAccountExtDao.getAccountsByRolePk(pk);
            //先删除redis缓存中的列显示权限数据
            delRedisCache(pk, accountList);

            mra.setRolePk(pk);
            if (pk != null && !"".equals(pk)) {
                //删除关系表
                result += manageAuthorityExtDao.deleteByManagRolePk(mra);
            }
            if (node != null && !"".equals(node)) {
                node = node.substring(1, node.length() - 1);
                String[] id = node.split(",");
                for (int i = 0; i < id.length; i++) {
                    mra.setPk(KeyUtils.getUUID());
                    mra.setAuthorityPk(id[i].replace("\"", ""));
                    //重新添加权限关系
                    result += manageAuthorityExtDao.insertMaRoAu(mra);
                }
            }
            //列显示权限保存到redis
            setToRedisCache(pk, accountList);
        }
        return result;
    }

    private void delRedisCache(String pk, List<ManageAccountDto> accountList) {
        //先删除mongo表中的列显示权限数据
        //mongoTemplate.dropCollection(AccountAuthColEntity.class);
        List<ManageAuthorityDto> delDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(pk);
        if (accountList != null && accountList.size() > 0) {
            if (delDtoList != null && delDtoList.size() > 0) {
                for (ManageAccountDto accountDto : accountList) {
                    for (ManageAuthorityDto delDto : delDtoList) {
                        try {
                            Query query = Query.query(Criteria.where("key").is(accountDto.getPk() + "_" + delDto.getName()));
                            mongoTemplate.remove(query, AccountAuthColEntity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    // 列显示权限保存到mongo
    private void setToRedisCache(String pk, List<ManageAccountDto> accountList) {
        //查询此角色下的所有用户，为该角色下所有用户分配列显示权限。
        List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(pk);
        String insertTime = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(new Date());
        if (setDtoList != null && setDtoList.size() > 0) {
            if (accountList != null && accountList.size() > 0) {
                for (ManageAccountDto accountDto : accountList) {
                    for (ManageAuthorityDto authorityDto : setDtoList) {
                        try {

                            AccountAuthColEntity entity = new AccountAuthColEntity();
                            entity.setInsertTime(insertTime);
                            entity.setKey(accountDto.getPk() + "_" + authorityDto.getName());
                            entity.setValue(authorityDto.getName());
                            mongoTemplate.insert(entity);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> getAuthManageIsBtnToThree() {
        return manageAuthorityExtDao.getListName();
    }



    @Override
    public PageModel<SysPropertyDto> searchSysPropertyGrid(QueryModel<SysPropertyDto> qm) {
        PageModel<SysPropertyDto> pm = new PageModel<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("type", qm.getEntity().getType());
        map.put("productName", qm.getEntity().getProductName());
        int totalCount = sysPropertyDao.searchGridExtCount(map);
        List<SysPropertyDto> list = sysPropertyDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public List<SysPropertyDto> getSysProperty(Map<String, Object> map) {
        return sysPropertyDao.searchList(map);
    }

    @Override
    public SysPropertyDto getSysPropertyBypk(String pk) {
        return sysPropertyDao.getByPk(pk);
    }

    @Override
    public List<SysPropertyDto> getSysPropertyByType(Integer type,String productPk) {
        return sysPropertyDao.getByProductType(type,productPk);
    }

    @Override
    public int updateSysPropertyByType(SysPropertyDto dto) {
        int result = 0;
        //返回结果只可能有一个
        if(dto.getType() != Constants.ONE && dto.getType() != Constants.SIX) {
            List<SysPropertyDto> list = sysPropertyDao.getByProductType(dto.getType(), dto.getProductPk());
            if (list != null && list.size() > 0) {
                SysPropertyDto sys = list.get(0);
                dto.setProductShotName(sys.getProductShotName());
            }
        }
        if (CommonUtil.isNotEmpty(dto.getPk())){
            result = sysPropertyDao.updateObj(dto);
        }else{
            //判断唯一性
            if (dto.getType() == Constants.ONE || dto.getType() == Constants.SIX){
                result = getInsertResult(dto);
            } else{
                Map<String,Object> map = new HashMap<>();
                map.put("type",dto.getType());
                map.put("productPk",dto.getProductPk());
                result = getResult(dto, map);
                map.remove("productPk");
            }
        }
        if (result > 0) {
            //刷新配置
            Map<String, Object> refreshMap = new HashMap<>();
            refreshMap.put("type", dto.getType());
            if (CommonUtil.isNotEmpty(dto.getProductPk())){
                refreshMap.put("productPk", dto.getProductPk());
            }
            List<SysPropertyDto> list = sysPropertyDao.searchList(refreshMap);
            if (list != null && list.size() > 0) {
                for (SysPropertyDto sysDto : list) {
                    PropertyConfig.init(sysDto.getContent());
                }
            }
        }
        return result;
    }

    private int getResult(SysPropertyDto dto, Map<String, Object> map) {
        int result;
        List<SysPropertyDto> list = sysPropertyDao.searchList(map);
        if (list != null && list.size() > 0) {
            result = -1;
        } else {
            result = getInsertResult(dto);
        }
        return result;
    }

    private int getInsertResult(SysPropertyDto dto) {
        int result = 0;
        SysProperty property = new SysProperty();
        property.setPk(KeyUtils.getUUID());
        property.setContent(dto.getContent());
        property.setProductName(dto.getProductName());
        property.setProductPk(dto.getProductPk());
        property.setType(dto.getType());
        property.setProductShotName(dto.getProductShotName());
        result = sysPropertyDao.insert(property);
        return result;
    }

    @Override
    public int delSysPropertyByPk(String pk) {
        return sysPropertyDao.delete(pk);
    }
}
