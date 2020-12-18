package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.dao.B2bGoodsBrandDaoEx;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.model.B2bGoodsBrand;
import cn.cf.service.B2bGoodsBrandService;
import cn.cf.util.KeyUtils;

@Service
public class B2bGoodsBrandServiceImpl implements B2bGoodsBrandService {

	@Autowired
	private B2bGoodsBrandDaoEx b2bGoodsBrandDaoEx;
	
	@Override
	public B2bGoodsBrandDto getByBrandName(Map<String, Object> map) {
		B2bGoodsBrandDto b2bGoodsBrandDto = null;
		List<B2bGoodsBrandDto> list = b2bGoodsBrandDaoEx.getByBrandName(map);
		if (null!=list && list.size()>0) {
			b2bGoodsBrandDto = list.get(0);
		}
		return b2bGoodsBrandDto;
	}

	@Override
	@Transactional
	public String createNewGoodsBrand(String brandName, String brandPk, String storePk,String storeName) {
		B2bGoodsBrand b2bGoodsBrand = new B2bGoodsBrand();
		String pk=KeyUtils.getUUID();
		b2bGoodsBrand.setPk(pk);
		b2bGoodsBrand.setBrandName(brandName);
		b2bGoodsBrand.setStorePk(storePk);
		b2bGoodsBrand.setStoreName(storeName);
		b2bGoodsBrand.setIsDelete(1);
		b2bGoodsBrand.setAuditStatus(1);
		b2bGoodsBrand.setInsertTime(new Date());
		b2bGoodsBrand.setBrandPk(brandPk);
		b2bGoodsBrandDaoEx.insert(b2bGoodsBrand);
		return pk;
	}

}
