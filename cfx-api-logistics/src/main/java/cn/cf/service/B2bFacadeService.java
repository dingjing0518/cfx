package cn.cf.service;

import cn.cf.dto.LgMemberDtoEx;
import cn.cf.entity.Sessions;

public interface B2bFacadeService {

	/**
	 * 添加session
	 * @param dto  物流系统会员
	 * @param sessionId  sessionId
	 * @param source  请求来源，1：PC,2:WAP，3：APP
	 * @return
	 */
	Sessions addSessions(LgMemberDtoEx dto, String sessionId,Integer source);

}
