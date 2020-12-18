package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bBrandDaoEx;
import cn.cf.dao.B2bGoodsBrandDaoEx;
import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.model.B2bBrand;
import cn.cf.model.B2bGoodsBrand;
import cn.cf.service.B2bBrandService;
import cn.cf.util.KeyUtils;

@Service
public class B2bBrandServiceImpl implements B2bBrandService {

	@Autowired
	private B2bBrandDaoEx brandDao;

	@Autowired
	private B2bGoodsBrandDaoEx goodsBrandDao;
	
	@Override
	public List<B2bBrandDto> searchBrand(Map<String, Object> map) {
		return brandDao.searchList(map);
	}

	@Override
	public List<B2bGoodsBrandDto> searchGoodsBrand(Map<String, Object> map) {
		return goodsBrandDao.searchList(map);
	}

	@Override
	public RestCode addBrand(String brandName, String storePk, String companyPk,String storeName,String memberPk) {
		B2bBrandDto brand = brandDao.getByName(brandName);
		if (brand == null) {
			brand = new B2bBrandDto();
			brand.setPk(KeyUtils.getUUID());
			brand.setName(brandName);
			brand.setSort(0);
			brand.setIsVisable(1);
			B2bBrand model = new B2bBrand();
			model.UpdateDTO(brand);
			brandDao.insert(model);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandName", brandName);
		map.put("storePk", storePk);
		map.put("isDelete", 1);
		List<B2bGoodsBrandDto> dto = goodsBrandDao.searchList(map);
		if (dto == null || dto.size() == 0) {
			B2bGoodsBrand goodsbrand = new B2bGoodsBrand();
			goodsbrand.setPk(KeyUtils.getUUID());
			goodsbrand.setBrandName(brandName);
			goodsbrand.setBrandPk(brand.getPk());
			goodsbrand.setStorePk(storePk);
			goodsbrand.setStoreName(storeName);
			goodsbrand.setAddMemberPk(memberPk);
			goodsBrandDao.insert(goodsbrand);
			//首次添加品牌积分,（王成：改为在cms后台审核通过的时候加会员体系积分，孙赛男跟产品确认过）
		} else {
			return RestCode.CODE_B001;
		}
		return RestCode.CODE_0000;
	}

	
	@Override
	public PageModel<B2bGoodsBrandDto> searchMyGoodsBrandList(Map<String, Object> map) {
		PageModel<B2bGoodsBrandDto> pm = new PageModel<B2bGoodsBrandDto>();
		map.put("isDelete", 1);
		map.put("orderName", "insertTime");
		map.put("orderType", "DESC");
		List<B2bGoodsBrandDto> list=goodsBrandDao.searchGrid(map);
		int count=goodsBrandDao.searchGridCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public void updateGoodsBrand(B2bGoodsBrandDto dto) {
		B2bGoodsBrand model = new B2bGoodsBrand();
		model.UpdateDTO(dto);
		goodsBrandDao.update(model);
	}

	@Override
	public PageModel<B2bBrandDto> searchBrandPage(Map<String, Object> map) {
		PageModel<B2bBrandDto> pm = new PageModel<B2bBrandDto>();
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		List<B2bBrandDto> list=brandDao.searchBrand(map);
		int count=brandDao.searchBrandCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	 
	}

	@Override
	public List<B2bBrandDto> searchBrandNameByBrandPks(String brandPks) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(!"-1".equals(brandPks)){
			String [] brandPk=brandPks.split(",");
			if(brandPk.length>0){
				map.put("brandPks", brandPk);
			}	
		}
		return brandDao.searchbrandNameBybrandPks(map);
		
	}
  
}
