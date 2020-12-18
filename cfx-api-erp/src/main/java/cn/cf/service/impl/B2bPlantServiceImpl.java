package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.dao.B2bPlantDaoEx;
import cn.cf.dto.B2bPlantDto;
import cn.cf.model.B2bPlant;
import cn.cf.service.B2bPlantService;
import cn.cf.util.KeyUtils;

@Service
public class B2bPlantServiceImpl implements B2bPlantService{

	@Autowired
	private B2bPlantDaoEx b2bPlantDaoEx;
	@Override
	public B2bPlantDto getByName(Map<String, Object> map) {
		B2bPlantDto b2bPlantDto = null;
		List<B2bPlantDto> list = b2bPlantDaoEx.getByName(map);
		if (null!=list && list.size()>0) {
			b2bPlantDto = list.get(0);
		}
		return b2bPlantDto;
	}

	@Override
	@Transactional
	public String createNewPlant(String plantName, String plantCode, String plantAddress, String storePk){
		B2bPlant b2bPlant = new B2bPlant();
		b2bPlant.setName(plantName);
		String pk = KeyUtils.getUUID();
		b2bPlant.setPk(pk);
		b2bPlant.setAddress(plantAddress);
		b2bPlant.setPlantCode(plantCode);
		b2bPlant.setStorePk(storePk);
		b2bPlant.setInsertTime(new Date());
		b2bPlant.setIsDelete(1);
		b2bPlantDaoEx.insert(b2bPlant);
		return pk;
	}

}
