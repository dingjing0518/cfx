package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.entity.SpecHot;


public interface B2bSpecHotService {
	
	List<SpecHot> searchSpecHotList(	Map<String, Object> map );
}
