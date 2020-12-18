package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.entity.SpecHot;

public interface B2bSpecHotDaoEx extends B2bSpecHotDao{

	List<SpecHot> searchSepcHotGroup(Map<String,Object> map);
}
