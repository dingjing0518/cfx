package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bCompanyDao;
import cn.cf.dao.SysSupplierRecommedDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.SysSupplierRecommedDtoEx;
import cn.cf.entity.CollectionCompany;
import cn.cf.service.SysSupplierRecommedService;

@Service
public class SysSupplierRecommedServiceImpl implements SysSupplierRecommedService{


	@Autowired
	private SysSupplierRecommedDaoEx sysSupplierRecommedDaoEx;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private B2bCompanyDao b2bCompanyDao;
	
	@Override
	public List<SysSupplierRecommedDtoEx> getSysSupplierRecommeds(Map<String, Object> map) throws Exception {
		
		
		List<SysSupplierRecommedDtoEx> list=sysSupplierRecommedDaoEx.searchRecommedList(map);
		if (null != list && list.size() > 0) {
			List<SysSupplierRecommedDtoEx> newList = new ArrayList<SysSupplierRecommedDtoEx>();
			for (int i = 0; i < list.size(); i++) {
				SysSupplierRecommedDtoEx d = list.get(i);
				String companyPk = d.getCompanyPk();
				d.setCompanyPk(companyPk);
				newList.add(d);
			}
			list=newList;
		}
        return list;
	}

	@Override
	public PageModel<SysSupplierRecommedDtoEx> getSysSupplierRecommedsByPage(Map<String, Object> map) {

		List<SysSupplierRecommedDtoEx> list = sysSupplierRecommedDaoEx.searchRecommedList(map);
		String provinceName = null;
		String cityName = null;
		String areaName = null;
		if (null != list && list.size() > 0) {
			List<SysSupplierRecommedDtoEx> newList = new ArrayList<SysSupplierRecommedDtoEx>();
			for (int i = 0; i < list.size(); i++) {
				SysSupplierRecommedDtoEx d = list.get(i);
				String companyPk=d.getCompanyPk();
				if (null!=companyPk&&!"".equals(companyPk)) {
					B2bCompanyDto b = b2bCompanyDao.getByPk(companyPk);
					if(null != b){
						provinceName = b.getProvinceName();
						cityName = b.getCityName();
						areaName = b.getAreaName();
					}
				}
				if (null != map.get("memberPk")) {
					Query qu = new Query(Criteria.where("storePk").is(d.getStorePk())
							.orOperator(Criteria.where("memberPk").is(map.get("memberPk"))));
					List<CollectionCompany> c = mongoTemplate.find(qu, CollectionCompany.class);
					if (null != c && c.size() > 0) {
						d.setIsCollection(2);
					}
				}
				d.setProvinceName(provinceName);
				d.setCityName(cityName);
				d.setAreaName(areaName);
				d.setCompanyPk(companyPk);
				newList.add(d);
			}
			list = newList;
		}
		int counts = sysSupplierRecommedDaoEx.searchGridCount(map);
		PageModel<SysSupplierRecommedDtoEx> pm = new PageModel<SysSupplierRecommedDtoEx>();
		pm.setDataList(list);
		pm.setTotalCount(counts);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

}
