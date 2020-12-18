package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.entity.CollectionCompany;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.model.B2bCompany;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.CommonService;
import cn.cf.util.KeyUtils;

@Service
public class B2bCompanyServiceImpl  implements B2bCompanyService {

    @Autowired
    private B2bCompanyDaoEx companyDao;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private CommonService commonService;

    @Override
    public int addCompany(B2bCompany company) {
        return companyDao.insert(company);
    }

    @Override
    public RestCode updateCompany(B2bCompany company) {
    	RestCode restCode =RestCode.CODE_0000;
        B2bCompanyDtoEx  dto = companyDao.getCompany(company.getPk());
        if(null != dto){
        	//审核不通过的公司变成待审核状态
        	if (dto.getAuditStatus()==3) {
        		company.setAuditStatus(1);
        	}
        	companyDao.update(company);
//        	if (result == 1) {
//        		try {
//        			dto = companyDao.getCompany(company.getPk());
//        			// 将原有的店铺信息更新进去
//        			B2bCompanyDtoEx oldCompany = JedisUtils.get(company.getPk(), B2bCompanyDtoEx.class);
//        			dto.setStorePk(oldCompany.getStorePk());
//        			dto.setStoreName(oldCompany.getStoreName());
//        			dto.setParentName(oldCompany.getParentName());
//        			// 更新缓存
//        			JedisUtils.set(company.getPk(), dto, PropertyConfig.getIntProperty("session_time", 3600));
//        		} catch (Exception e) {
//        			e.printStackTrace();
//        		}
//        	}
        }
		return restCode;
    }

    @Override
    public B2bCompanyDtoEx getCompany(String pk) {
        return companyDao.getCompany(pk);
    }

    /**
     * 计算店铺被收藏的数量
     *
     * @param storePk
     * @return
     */
    @Override
    public Integer countCollectCom(String storePk) {
        Criteria cr = new Criteria();
        cr.andOperator(Criteria.where("storePk").is(storePk));
        int countCollectCom = (int) mongoTemplate.count(new Query(cr), CollectionCompany.class);
        return countCollectCom;
    }

    @Override
    public List<B2bCompanyDtoEx> searchCompanyList(Integer companyType, String companyPk, Integer returnType,
                                                   Map<String, Object> map) {
        if (null == map) {
            map = new HashMap<String, Object>();
        }
        map.put("isVisable", 1);
        map.put("isDelete", 1);
        map.put("auditStatus", 2);
        // 供应商
        if (companyType == 2) {
            map.put("auditSpStatus", 2);
        }
        // 查询自己包含子公司
        if (returnType == 1) {
            map.put("parentChildPk", companyPk);
            // 只查询子公司
        } else {
            map.put("parentPk", companyPk);
        }
        return companyDao.searchCompanyList(map);
    }

    @Override
    public String[] getCompanyPks(B2bCompanyDto company, Integer companyType, Integer returnType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisable", 1);
        map.put("isDelete", 1);
        map.put("auditStatus", 2);
        // 供应商
        if (companyType == 2) {
            map.put("auditSpStatus", 2);
        }
        // 查询自己包含子公司
        if (returnType == 1) {
            map.put("parentChildPk", company.getPk());
            // 只查询子公司
        } else {
            map.put("parentPk", company.getPk());
        }
        String[] comPks = new String[1];
        if ("-1".equals(company.getParentPk())) {
            List<B2bCompanyDtoEx> l = companyDao.searchCompanyList(map);
            comPks = new String[l.size()];
            for (int i = 0; i < l.size(); i++) {
                comPks[i] = l.get(i).getPk();
            }
        } else {
            comPks[0] = company.getPk();
        }

        return comPks;
    }

    @Override
    public RestCode addSubCompany(B2bCompany model) {
        RestCode result = RestCode.CODE_0000;
        String pk = KeyUtils.getUUID();
        model.setPk(pk);
        model.setIsDelete(1);
        model.setAuditStatus(1);
        model.setAuditSpStatus(1);
        model.setInsertTime(new Date());
        model.setIsVisable(1);
        model.setIsPerfect(2);
        int res = companyDao.insert(model);
        if (res == 0) {
            result = RestCode.CODE_S999;
        }else {
			
		}
        return result;
    }

    @Override
    public List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map) {
        return companyDao.searchGrid(map);
    }

    @Override
    public B2bCompanyDto getByCustomerPk(String customerPk) {
        return companyDao.getByCustomerPk(customerPk);
    }
    
	@Override
	public List<B2bCompanyDtoEx> searchPurchaserCompany(Map<String, Object> map) {
	        map.put("auditStatus", 2);
	        map.put("isVisable", 1);
	        map.put("isDelete", 1);
            return  companyDao.searchCompanyList(map);
	}

	@Override
	public String[] findCompanys(String companyPk, Integer companyType) {
		B2bCompanyDto coms = companyDao.getByPk(companyPk);
		coms.setCompanyType(companyType);
		String[] comPks = new String[1];
		if (coms != null && "-1".equals(coms.getParentPk())
				&& coms.getAuditStatus() == 2) {
			List<B2bCompanyDtoEx> l = companyDao.searchCompanyByMember(coms);
			comPks = new String[l.size()];
			for (int i = 0; i < l.size(); i++) {
				comPks[i] = l.get(i).getPk();
			}
		} else {
			comPks[0] = coms.getPk();
		}
		return comPks;
	}
	
	@Override
	public B2bCompanyDtoEx getCompanyDto(String pk) {
		return companyDao.getCompany(pk);
	}

	//采购商/供应商 公司管理，分页查询公司列表
	@Override
	public PageModel<B2bCompanyDtoEx> searchCompanyListByLimit(Integer companyType, String companyPk,Integer returnType, 
			Map<String, Object> map,Integer auditStatus,Integer auditSpStatus) {
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		map.put("auditStatus", auditStatus);
		map.remove("companyType");
		map.remove("companyPk");
		map.remove("returnType");
		//查询供应商公司
        if (companyType == 2) {
            map.put("auditSpStatus", auditSpStatus);
        }
		//查询自己包含子公司
		if (returnType == 1) {
			map.put("parentChildPk", companyPk);
		//只查询子公司
		} else {
			map.put("parentPk", companyPk);
		}
		PageModel<B2bCompanyDtoEx> pm = new PageModel<B2bCompanyDtoEx>();
		List<B2bCompanyDtoEx> list = companyDao.searchCompanyList(map);
		if (list!=null && list.size()>0) {
			for (B2bCompanyDtoEx companyDtoEx: list) {
				if (!"-1".equals(companyDtoEx.getParentPk())) {
					B2bCompanyDto dto = companyDao.getByPk(companyDtoEx.getParentPk());
					companyDtoEx.setParentName(dto.getName());
				}else{
					companyDtoEx.setParentName(companyDtoEx.getName());
				}
			/*	B2bInvoiceDto invoice=companyDao.searchInvoiceIs(companyDtoEx.getPk());
				if(null!=invoice&&!"".equals(invoice.getPk())){
					companyDtoEx.setConsummateFlag(1);
					companyDtoEx.setInvoicePk(invoice.getPk());
				}else{
					companyDtoEx.setConsummateFlag(2);
					companyDtoEx.setInvoicePk("");
				}*/
			}
			int counts = companyDao.searchGridCount(map);
			pm.setDataList(list);
			pm.setTotalCount(counts);
		}
		
		if (null != map.get("start")) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	@Override
	public B2bCompanyDtoEx getCompanyByName(String companyName) {
		B2bCompanyDtoEx company = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("realName", companyName);//精确匹配
		List<B2bCompanyDtoEx> list = companyDao.searchCompanyList(map);
		if(null != list && list.size()>0){
			company = list.get(0);
		}
		return company;
	}

	
	/**
	 * 更新公司的发票信息
	 */
	@Override
	public String updateInvoice(B2bCompanyDto companyDto, String memberPk) {
	    String rest = null;
        int result = 0;
        Boolean flag = true;
        if(null != companyDto.getOrganizationCode() && !"".equals(companyDto.getOrganizationCode())){
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("organizationCode", companyDto.getOrganizationCode());
        	map.put("pk", companyDto.getPk());
        	int count = companyDao.searchOrganizationCode(map);
        	if(count>0){
        		rest = RestCode.CODE_C030.toJson();
        		flag = false;
        	}
        }
        if(flag){
        	B2bCompany model = new B2bCompany();
        	model.UpdateDTO(companyDto);
        	result = companyDao.update(model);
//        	if (result==1) {
//        		B2bCompanyDto companyDtoTemp = companyDao.getByPk(companyDto.getPk());
//        		JedisUtils.set(companyDto.getPk(), companyDtoTemp);
//        	}
        	//添加首次完善发票信息的记录
        	if (result == 1) {
        		addInvoicePoint(companyDto, memberPk);
        	}
        	if (result != 1) {
        		rest = RestCode.CODE_S999.toJson();
        	} else {
        		rest = RestCode.CODE_0000.toJson(model);
        	}
        }
        return rest;
	}
	
	
	/**
	 * 更新公司的发票信息加会员积分
	 * @param companyDto
	 * @param memebrPk
	 */
    private void addInvoicePoint(B2bCompanyDto companyDto, String memebrPk) {
        try {
            //是否完善过
            String dimenType = MemberSys.ACCOUNT_DIMEN_INV.getValue();
        	//会员体系加积分
            Map<String, String> paraMap=new HashMap<>();
			paraMap.put("dimenType", dimenType);
			paraMap.put("companyPk", companyDto.getPk());
			/*String result= HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/searchPointList", paraMap);
			try {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonarray = JSONArray.fromObject(JsonUtils.getJsonData(result));  
			if (null == jsonarray || jsonarray.size() == 0) {
				paraMap.remove("dimenType");
				paraMap.remove("companyPk");
				paraMap.put("memberPk", memebrPk);
				paraMap.put("companyPk", companyDto.getPk());
				paraMap.put("pointType", "1");
				paraMap.put("active", dimenType);
				HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/addPoint", paraMap);
			}*/
			List<MangoMemberPoint> list = commonService.searchPointList(paraMap);
			if (null==list || list.size() == 0) {
				commonService.addPoint(memebrPk, companyDto.getPk(), 1, dimenType);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
