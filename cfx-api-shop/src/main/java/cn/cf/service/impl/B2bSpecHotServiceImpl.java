package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bSpecHotDaoEx;
import cn.cf.entity.SpecHot;
import cn.cf.service.B2bSpecHotService;

@Service
public class B2bSpecHotServiceImpl implements B2bSpecHotService {

	@Autowired
	private B2bSpecHotDaoEx b2bSpecHotDaoEx;

	@Override
	public List<SpecHot> searchSpecHotList(	Map<String, Object> map ) {
		map.put("groupName", "first");
		List<SpecHot> list = b2bSpecHotDaoEx.searchSepcHotGroup(map);
		if (null != list && list.size() > 0) {
			for (SpecHot dto : list) {
				map.put("groupName", "second");
				map.put("firstLevelPk", dto.getFirstLevelPk());
				map.put("secondLevelPk", dto.getSecondLevelPk());
				map.put("thirdLevelPk", dto.getThirdLevelPk());
				List<SpecHot> list2 = b2bSpecHotDaoEx.searchSepcHotGroup(map);
				if (null != list2 && list2.size() > 0) {
					dto.setcList(list2);
				}
			}
		}
		return list;
	}

}
