/**
 *
 */
package cn.cf.service.manage.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dao.*;
import cn.cf.entity.AccountAuthColEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bManageRegionDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.ManageRoleDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.dto.MarketingPersonnelExtDto;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingPersonnel;
import cn.cf.service.manage.AccountManageService;
import cn.cf.util.EncodeUtil;
import cn.cf.util.KeyUtils;

/**
 * @author bin
 */
@Service
public class AccountManageServiceImpl implements AccountManageService {

    @Autowired
    private ManageAuthorityDao manageAuthorityDao;

    @Autowired
    private ManageRoleExtDao manageRoleExtDao;

    @Autowired
    private ManageAccountDao manageAccountDao;

    @Autowired
    private ManageAccountExtDao manageAccountExtDao;
    @Autowired
    private MarketingPersonnelExtDao marketingPersonnelExtDao;

    @Autowired
    private MarketingPersonnelExtDao marketingPersonnelDao;

	@Autowired
	private ActUserDao actUserDao;
    
	@Autowired
	private B2bManageRegionDao  b2bManageRegionDao;
	
	@Autowired
	private MarketingCompanyExtDao   marketingCompanyExtDao;

    @Autowired
    private ManageAuthorityExtDao manageAuthorityExtDao;


    @Autowired
    private MongoTemplate mongoTemplate;
	
    @Override
    public ManageAuthorityDto getManageAuthorityByPk(String pk) {
        return manageAuthorityDao.getByPk(pk);
    }

    @Override
    public List<ManageRoleDto> getManageRoleByName(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        //
        return manageRoleExtDao.getManageRoleByName(map);
    }

    @Override
    public PageModel<ManageAccountExtDto> searchManageAccountList(QueryModel<ManageAccountExtDto> qm) {
        PageModel<ManageAccountExtDto> pm = new PageModel<ManageAccountExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        int totalCount = manageAccountExtDao.searchAccounts(map);
        List<ManageAccountExtDto> list = manageAccountExtDao.searchAccountList(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateIsVisableOrDelete(ManageAccount model) {
        //如果该账户被删除，清除营销中心人员管理该账户的相关信息
        Map<String , Object > map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("accountPk", model.getPk());
        MarketingPersonnelDto  dto = marketingPersonnelExtDao.getByMap(map);
        MarketingPersonnel  marketingPersonnel = new MarketingPersonnel();
        if(null != dto && model.getIsDelete()==2){
	        marketingPersonnel.setPk(dto.getPk());
	        marketingPersonnel.setIsDelete(2);
	        marketingPersonnel.setType(dto.getType());
        
	        try {
	            this.insertPersonnel(marketingPersonnel);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

        }
        return manageAccountExtDao.updateIsVisableOrDelete(model);
    }

    @Override
    public String updateManageAccount(ManageAccount account) {
        String pk = null;
        if (account.getPk() != null && !"".equals(account.getPk())) {
            pk = account.getPk();
            if ((account.getIsDelete() != null && account.getIsDelete() == 2)
                    || (account.getIsVisable() != null && account.getIsVisable() > 0)) {
                manageAccountExtDao.updateManageAccount(account);

            } else {
            	ManageAccountDto  lastAcount = manageAccountDao.getByPk(account.getPk());
            	if (lastAcount!=null) {
            		if (lastAcount.getAccount() .equals(account.getAccount())) {//更新了账号，判断是否影响金融的账号
    					Map<String, Object> map1 = new HashMap<>();
    					map1.put("accountId", account.getAccount());
    					map1.put("name", account.getName());
    					map1.put("email", account.getEmail());
    					actUserDao.update(map1);
    				}
				}
                manageAccountExtDao.updateManageAccount(account);
            }
        } else {
            pk = KeyUtils.getUUID();
            account.setPk(pk);
            account.setIsDelete(1);
            account.setIsVisable(1);
            account.setInsertTime(new Date());
            account.setPassword(EncodeUtil.MD5Encode("123456"));
            manageAccountDao.insert(account);
        }
        //先删除本账户列权限数据
        delMongoCache(account.getRolePk(),account);
        //后添加列权限数据
        setToMongoCache(account.getRolePk(),account);
        return pk;
    }

    private void delMongoCache(String rolePk,ManageAccount  account) {
        //先删除mongo表中的列显示权限数据
        List<ManageAuthorityDto> delDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(rolePk);
            if (delDtoList != null && delDtoList.size() > 0) {
                    for (ManageAuthorityDto delDto : delDtoList) {
                        try {
                            Query query = Query.query(Criteria.where("key").is(account.getPk() + "_" + delDto.getName()));
                            mongoTemplate.remove(query, AccountAuthColEntity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
            }
        }

    }
    // 列显示权限保存到mongo
    private void setToMongoCache(String rolePk,ManageAccount  account) {
        //查询此角色下的所有用户，为该角色下所有用户分配列显示权限。
        List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(rolePk);
        String insertTime = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(new Date());
        if (setDtoList != null && setDtoList.size() > 0) {
                    for (ManageAuthorityDto authorityDto : setDtoList) {
                        try {
                            AccountAuthColEntity entity = new AccountAuthColEntity();
                            entity.setInsertTime(insertTime);
                            entity.setKey(account.getPk() + "_" + authorityDto.getName());
                            entity.setValue(authorityDto.getName());
                            mongoTemplate.insert(entity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
            }
        }
    }



    @Override
    public int updateRePassword(String pk) {
        return manageAccountExtDao.rePassword(pk);
    }

    @Override
    public List<ManageAccountDto> getOnline() {
        return manageAccountExtDao.getOnlineAccount();
    }

    @Override
    public List<MarketingPersonnelDto> getPersonByType(Integer type) {

        return marketingPersonnelExtDao.getPersonByType(type);
    }

    @Override
    public PageModel<MarketingPersonnelExtDto> searchPersonnelList(QueryModel<MarketingPersonnelExtDto> qm,String accountPk) {

        PageModel<MarketingPersonnelExtDto> pm = new PageModel<MarketingPersonnelExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("type", qm.getEntity().getType());
        map.put("accountPk", qm.getEntity().getAccountPk());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("regionPk", qm.getEntity().getRegionPk());
        map.put("colAccountName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_PERSON_COL_ACCOUNTNAME));
        int totalCount = marketingPersonnelExtDao.searchPersonnels(map);
        List<MarketingPersonnelExtDto> list = marketingPersonnelExtDao.searchPersonnelList(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int insertPersonnel(MarketingPersonnel dto){

        int result = 0;
        if (null == dto.getPk() || "".equals(dto.getPk())) {
            dto.setPk(KeyUtils.getUUID());
            dto.setInsertTime(new Date());
            dto.setUpdateTime(new Date());
            dto.setIsVisable(1);
            dto.setIsDelete(1);
            result = marketingPersonnelDao.insert(dto);
        } else {
             //删除业务经理，同时删除分配采购商/分配供应商下已分配的该业务经理
            if (dto.getIsDelete()!=null&& dto.getType()!=null&&dto.getIsDelete()==2 && dto.getType()==1) {
                MarketingPersonnelDto  lastDto = marketingPersonnelDao.getByPk(dto.getPk());
                marketingCompanyExtDao.deleteAccount(lastDto.getAccountPk());
            }
            //编辑了业务经理类型，需要清除已分配的改业务经理
            if (dto.getIsDelete()==null && dto.getIsVisable()==null) {
                    MarketingPersonnelDto  lastDto = marketingPersonnelDao.getByPk(dto.getPk());
                    if (lastDto.getType()==1 &&(dto.getType()!=1||!dto.getAccountPk().equals(lastDto.getAccountPk()))) {
                        marketingCompanyExtDao.deleteAccount(lastDto.getAccountPk());
                    }
                
            }
            result = marketingPersonnelDao.updateExt(dto);
        }
        return result;
    }

	@Override
	public List<ManageAccountExtDto> getAllAccountList() {

		return  manageAccountExtDao.getAllAccountList();
	}

    @Override
    public List<B2bManageRegionDto> getRegionAllList() {
        Map<String, Object> map = new HashMap<String, Object>();
        return b2bManageRegionDao.searchList(map);
    }

    @Override
    public List<B2bManageRegionDto> getRegionNoIsVisiablList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisable", 1);
        return b2bManageRegionDao.searchList(map);
    }

    @Override
    public List<ManageAccountDto> getAllNoisDeleteAccountList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isDelete", 1);
        return manageAccountExtDao.searchList(map);
    }


}
