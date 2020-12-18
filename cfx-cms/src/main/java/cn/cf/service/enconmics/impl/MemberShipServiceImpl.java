package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.ActGroupDao;
import cn.cf.dao.ActMemberShipDao;
import cn.cf.dao.ActUserDao;
import cn.cf.dto.ActGroupDto;
import cn.cf.dto.ActMemberShipDto;
import cn.cf.dto.ActUserDto;
import cn.cf.dto.MemberShip;
import cn.cf.service.enconmics.MemberShipService;

@Service
public class MemberShipServiceImpl implements MemberShipService {

	@Autowired
	private ActGroupDao groupDao;
	@Autowired
	private ActUserDao userDao;
	
	@Autowired
	private ActMemberShipDao memberShipDao;

	@Override
	public MemberShip getMemberShip(String account) {
		MemberShip memberShip = new MemberShip();
		Map<String, Object> map = new HashMap<>();
		map.put("id", account);
		ActUserDto userDto = userDao.selectByPk(map);
		if (userDto != null) {
			
			memberShip.setUserId(userDto.getId());
			memberShip.setActUserDto(userDto);
			if(null!=userDto.getGroupId()){
				memberShip.setGroupId(userDto.getGroupId());
				Map<String, Object> mapp = new HashMap<>();
				mapp.put("id", userDto.getGroupId());
				ActGroupDto groupDto = groupDao.selectByPk(mapp);
				memberShip.setActGroupDto(groupDto);
			}
			
		}
		return memberShip;
	}

	@Override
	public PageModel<ActMemberShipDto> searchActUserList(QueryModel<ActMemberShipDto> qm,String accountPk) {
		PageModel<ActMemberShipDto> pm = new PageModel<ActMemberShipDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("id", qm.getEntity().getId());
		map.put("colAccountName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_EMPACCOUNT_ACCOUNT_COL_ACCOUNTNAME));
		map.put("colName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_EMPACCOUNT_ACCOUNT_COL_NAME));
		int totalCount = memberShipDao.searchActMemShipCount(map);
		List<ActMemberShipDto> list = memberShipDao.searchActMemShipList(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int deleteActListGroup(Map<String, Object> map) {
		
		return memberShipDao.deleteActListGroup(map);
	}

}
