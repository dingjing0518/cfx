package cn.cf.service;

import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;

public interface SessionService {
	
	Sessions addSessions(B2bMemberDto dto, String sessionId,Integer source);
}
