package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.ActGroupDao;
import cn.cf.dao.ActUserDao;
import cn.cf.dao.ManageAccountExtDao;
import cn.cf.dto.ActGroupDto;
import cn.cf.dto.ActUserDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.model.ActGroup;
import cn.cf.service.enconmics.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private ActGroupDao actGroupDao;
	@Autowired
	private ManageAccountExtDao manageAccountExtDao;
	@Autowired
	private ActUserDao actUserDao;

	@Override
	public PageModel<ActGroupDto> searchManageAccountList(QueryModel<ActGroupDto> qm) {

		PageModel<ActGroupDto> pm = new PageModel<ActGroupDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		int totalCount = actGroupDao.searchActGroupCount(map);
		List<ActGroupDto> list = actGroupDao.searchActGroupList(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int deleteActGroup(ActGroup actGroup) throws Exception {
	
		return actGroupDao.deleteActGroup(actGroup);
	}

	@Override
	public int updateActGroup(ActGroup actGroup) {
	
		 int result=0;
	        if (!"".equals(actGroup.getType()) && actGroup.getType() != null ) {
	        	result=actGroupDao.updateActGroup(actGroup);
	           
	        } else {
	        	List<ActGroup> actGroups=actGroupDao.findActGroup(actGroup);
				if(null!=actGroups && actGroups.size()>0){
					result=-2;
				}else{
					result=actGroupDao.addActGroup(actGroup);
				}
	        	
	        }
	  
		return result;
	}

	@Override
	public List<ActGroup> findActGroup(ActGroup actGroup) {
		return actGroupDao.findActGroup(actGroup);
	}

	@Override
	public List<ActGroupDto> findByUserId(String userId) {
		
		return actGroupDao.findByUserId(userId);
	}

	@Override
	public List<ActGroupDto> getAllGroupList() {
		
		return actGroupDao.getAllGroupList();
	}

	@Override
	public int updateActUserGroup(Map<String,Object>map) {
		int result=0;
		String accountId=(String) map.get("accountId");
		ManageAccountDto manageAccountDto=manageAccountExtDao.getManageAccountByLoginName(accountId);
		if(null!=manageAccountDto){
			map.put("accountId", manageAccountDto.getAccount());
			List<ActGroupDto> lstGroup=actGroupDao.findByUserId(manageAccountDto.getAccount());
			if(null!=lstGroup && lstGroup.size()==1){
				if(map.get("type").equals("add")){
					result=-2;
				}else{
					result=actGroupDao.updateActUserGroup(map);
				}
				
				
			}else if(null!=lstGroup && lstGroup.size()>1){
				result=-2;
			}else{
				Map<String,Object>map1=new HashMap<String,Object>();
				
				
				map1.put("id", manageAccountDto.getAccount());
				ActUserDto actUser=actUserDao.selectByPk(map1);
				if(null==actUser){
					map1.clear();
					map1.put("accountId", manageAccountDto.getAccount());
					map1.put("name", manageAccountDto.getName());
					map1.put("email", manageAccountDto.getEmail());
					actUserDao.insertActUser(map1);
				}
				
				
					result=actGroupDao.insertActUserGroup(map);
				
				
			}
		}
		
        return result;
	}

	@Override
	public int deleteActListGroup(Map<String, Object> map) {
		
		return actGroupDao.deleteActListGroup(map);
	}

	
	

}
