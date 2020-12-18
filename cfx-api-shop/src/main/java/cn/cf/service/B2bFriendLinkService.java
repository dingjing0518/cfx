package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bFriendLinkDto;

public interface B2bFriendLinkService {
	
	List<B2bFriendLinkDto> searchFriendLinkList(Integer start,Integer limit);
}
