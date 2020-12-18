package cn.cf.service.enconmics;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.ActMemberShipDto;
import cn.cf.dto.MemberShip;

public interface MemberShipService {
	
	/**
	 * 登陆时获取账户的memberShip
	 * @param account
	 * @return
	 */
	MemberShip getMemberShip(String account);
	/**
	 * 账户列表
	 * 
	 * @param qm
	 * @param accountPk 
	 * @return
	 */
	PageModel<ActMemberShipDto> searchActUserList(QueryModel<ActMemberShipDto> qm, String accountPk);
	/**
	 * 
	 * 删除金融账户
	 * @param map
	 * @return
	 */
	int deleteActListGroup(Map<String, Object> map);

}
