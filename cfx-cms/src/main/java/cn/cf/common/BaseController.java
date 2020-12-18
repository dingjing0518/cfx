/**
 * 
 */
package cn.cf.common;

import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.MemberShip;
import cn.cf.model.ManageAccount;

import javax.servlet.http.HttpServletRequest;


/**
 * @author bin
 * 
 */
public class BaseController {
	protected ManageAccount getAccount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = request.getSession().getId();
		return JedisMaterialUtils.get(sessionId,ManageAccount.class);
	}

	
	protected ManageAccountExtDto getAccountExt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = request.getSession().getId();
		return JedisMaterialUtils.get(sessionId,ManageAccountExtDto.class);
	}
	
	protected MemberShip getMemberShipByAccount(String account) {
		// TODO Auto-generated method stub
			return JedisMaterialUtils.get(account, MemberShip.class);
		
	}
}
