package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.common.RestCode;
import cn.cf.model.B2bStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dao.B2bTokenDao;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bStoreDtoEx;
import cn.cf.dto.B2bTokenDto;
import cn.cf.service.B2bStoreService;

@Service
public class B2bStoreServiceImpl implements B2bStoreService {

    @Autowired
    private B2bStoreDaoEx storeDao;
    
    @Autowired
    private B2bTokenDao b2bTokenDao;

    @Override
    public B2bStoreDtoEx getCompStoreByStorePk(String storePk) {
        B2bStoreDtoEx store =  storeDao.getCompStoreByStorePk(storePk);
        if(null != store){
        	B2bTokenDto token =  b2bTokenDao.getByStorePk(store.getPk());
        	if(null != token && token.getIsDelete() ==1 && token.getIsVisable() == 1){
        		store.setIsOperative(1);
        	}else{
        		store.setIsOperative(2);
        	}
        }
        return store;
    }

    @Override
    public RestCode updateIsOpen(B2bStore store) {
        int result = storeDao.update(store);
        if (result == 0) {
            return RestCode.CODE_A004;
        }
        return RestCode.CODE_0000;
    }
	@Override
	public List<B2bStoreDto> searchStoreByBrandPk(String brandPk) {
		Map<String , Object> map=new HashMap<String, Object>();
		List<B2bStoreDto> list=new ArrayList<B2bStoreDto>();
		if(null!=brandPk&&!"".equals(brandPk)){
			    String[] brandPks = brandPk.split(","); 
				map.put("brandPks",brandPks);
			  list=storeDao.searchStoreByBrandPk(map);
		}
	
		
		return list;
	}

}
