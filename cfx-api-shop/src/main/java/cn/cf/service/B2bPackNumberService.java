package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bPackNumberDto;
import cn.cf.model.B2bPackNumber;

public interface B2bPackNumberService {

	Integer update(B2bPackNumber b2bPackNumber);

	Integer insert(B2bPackNumber b2bPackNumber);

	PageModel<B2bPackNumberDto> searchPackNumberList(Map<String, Object> map);

}
