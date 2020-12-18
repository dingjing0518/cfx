package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bPriceMovementDtoEx;

public interface B2bPriceMovementService {
	
	PageModel<B2bPriceMovementDtoEx> searchB2bPriceMovementList(Map<String, Object> map);
	

}
