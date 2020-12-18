package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.dao.B2bSpecDaoEx;
import cn.cf.dto.B2bSpecDto;
import cn.cf.model.B2bSpec;
import cn.cf.service.B2bSpecService;
import cn.cf.util.KeyUtils;

@Service
public class B2bSpecServiceImpl implements B2bSpecService {

	@Autowired
	private B2bSpecDaoEx b2bSpecDaoEx;
	
	@Override
	public B2bSpecDto getByNameParent(String specName) {
		B2bSpecDto b2bSpecDto=null;
		List<B2bSpecDto> list =  b2bSpecDaoEx.getByNameParent(specName);
		if (null!=list && list.size()>0) {
			b2bSpecDto = list.get(0);
		}
		return b2bSpecDto;
	}

	@Override
	@Transactional
	public String createNewSpec(String parentPk, String specName) {
		B2bSpec Spec = new B2bSpec();// 商品规格大类
		Spec.setName(specName);
		Spec.setParentPk(parentPk);
		String pk = KeyUtils.getUUID();
		Spec.setPk(pk);
		Spec.setInsertTime(new Date());
		Spec.setIsDelete(1);
		Spec.setIsVisable(1);
		Spec.setSort(1);
		b2bSpecDaoEx.insert(Spec);
		return pk;
		
	}

	@Override
	public B2bSpecDto getBySeariesName(String seriesName) {
		B2bSpecDto b2bSpecDto = null;
		List<B2bSpecDto> list = b2bSpecDaoEx.getBySeriesName(seriesName);
		if (null!=list && list.size()>0) {
			b2bSpecDto = list.get(0);
		}
		return b2bSpecDto;
	}

}
