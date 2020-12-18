package cn.cf.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bStoreAlbumDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bStoreAlbumDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.dto.C2bMarrieddealDto;
import cn.cf.dto.C2bMarrieddealDtoEx;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bCompany;
import cn.cf.model.B2bPackNumber;
import cn.cf.model.B2bPlant;
import cn.cf.model.B2bStoreAlbum;
import cn.cf.model.B2bWare;
import cn.cf.model.B2bWarehouseNumber;
import cn.cf.model.C2bMarrieddeal;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.service.B2bAuctionService;
import cn.cf.service.B2bBindService;
import cn.cf.service.B2bCartService;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.B2bPackNumberService;
import cn.cf.service.B2bPlantService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.B2bWareService;
import cn.cf.service.C2bMarrieddealService;
import cn.cf.service.CommonService;
import cn.cf.util.CommonUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class B2bFacadeServiceImpl implements B2bFacadeService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private B2bCompanyService b2bCompanyService;

	@Autowired
	private B2bGoodsService b2bGoodsService;

	@Autowired
	private B2bStoreService b2bStoreService;

	@Autowired
	private B2bAuctionGoodsService b2bAuctionGoodsService;

	@Autowired
	private B2bPlantService b2bPlantService;

	@Autowired
	private B2bWareService b2bWareService;

	@Autowired
	private B2bPackNumberService b2bPackNumberService;

	@Autowired
	private B2bCartService b2bCartService;

	@Autowired
	private B2bCustomerSaleManService b2bCustomerSalesmanService;

	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private C2bMarrieddealService c2bMarrieddealService;

	@Autowired
	private B2bAuctionService b2bAuctionService;
	
	@Autowired
	private B2bBindService b2bBindService;

	 
	
	@Autowired
	private B2bAuctionGoodsDaoEx b2bAuctionGoodsDaoEx;

	@Autowired
	private B2bStoreAlbumDao albumDao;
	
	@Autowired
	private CommonService commonService;
	
	
//	@Override
//	public Object search(Map<String, Object> map) {
//
//		String result = "";
//		PageModel<B2bGoodsDtoEx> pm = b2bGoodsService.searchGoodsList(map);
//
//		String searchKey = (String) map.get("searchKey");
//		if (pm.getTotalCount() == 0 && null != searchKey && !searchKey.equals("")) {
//			pm = b2bGoodsService.findIndex(map);
//			result = RestCode.CODE_B005.toJson(pm);
//		} else {
//			result = RestCode.CODE_0000.toJson(pm);
//		}
//		return result;
//	}

	   

	 

	@Override
	public RestCode addPlant(B2bPlantDto dto, String memberPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		RestCode restCode = RestCode.CODE_0000;
		map.put("name", dto.getName());
		map.put("storePk", dto.getStorePk());
		map.put("pk", dto.getPk());
		// 是否已存在
		B2bPlantDto plant = b2bPlantService.isPlantRepeated(map);
		if (plant != null) {
			restCode = RestCode.CODE_P001;
		} else {
			B2bPlant plantModel = new B2bPlant();
			plantModel.UpdateDTO(dto);
			// 新增
			if (null == dto.getPk() || "".equals(dto.getPk())) {
				int result = b2bPlantService.insert(plantModel);
				if (result == 0) {
					restCode = RestCode.CODE_A001;
				}
			} else {// 编辑
				int result = b2bPlantService.update(plantModel);
				if (result == 0) {
					return RestCode.CODE_A001;
				}
			}
			if (restCode == RestCode.CODE_0000) {
				if (dto.getStorePk() != null) {
					B2bMemberDto memberDto = b2bMemberService.getByPk(memberPk);
					addPoint(memberPk, memberDto.getCompanyPk(), MemberSys.ACCOUNT_DIMEN_PLANT.getValue());
				}
			}
		}
		return restCode;
	}

	@Override
	public RestCode addWare(B2bWareDto dto, String memberPk) {
		RestCode restCode = RestCode.CODE_0000;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", dto.getName());
		map.put("storePk", dto.getStorePk());
		map.put("pk", dto.getPk());
		B2bWareDto w = b2bWareService.isWareRepeated(map);
		if (w != null) {
			return RestCode.CODE_W001;
		} else {
			B2bWare model = new B2bWare();
			model.UpdateDTO(dto);
			// 新增
			if (null == dto.getPk() || "".equals(dto.getPk())) {
				int result = b2bWareService.insert(model);
				if (result == 0) {
					restCode = RestCode.CODE_A001;
				}
			} else {// 编辑
				int result = b2bWareService.update(model);
				if (result == 0) {
					restCode = RestCode.CODE_A001;
				}
			}

			if (restCode == RestCode.CODE_0000) {
				if (dto.getStorePk() != null) {
					B2bMemberDto memberDto = b2bMemberService.getByPk(memberPk);
					addPoint(memberPk, memberDto.getCompanyPk(), MemberSys.ACCOUNT_DIMEN_WARE.getValue());
				}
			}
		}
		return restCode;
	}

	@Override
	public void addPoint(String memberPk, String companyPk, String dimenType) {
		try {
			//会员体系加积分
			Map<String, String> paraMap=new HashMap<>();
			paraMap.put("dimenType", dimenType);
			paraMap.put("companyPk", companyPk);
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
				paraMap.put("memberPk", memberPk);
				paraMap.put("companyPk", companyPk);
				paraMap.put("pointType", "1");
				paraMap.put("active", dimenType);
				HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/addPoint", paraMap);
			}*/
			List<MangoMemberPoint> list = commonService.searchPointList(paraMap);
			if (null==list || list.size()==0) {
				commonService.addPoint(memberPk, companyPk, 1, dimenType);
			}
		} catch (Exception e) {
			logger.error("addPoint   ", e);
			e.printStackTrace();
		}
	}

	@Override
	public String exportGood(Map<String, Object> map)
			throws ParsePropertyException, InvalidFormatException, IOException {
		PageModel<B2bGoodsDtoEx> pm = b2bGoodsService.searchGoodsList(map);
		String templateFileName = CommonUtil.getPath() + "goods.xls";
		String path = CommonUtil.getPath();
		path = path.substring(0, path.indexOf("classes"));
		path = path.substring(0, path.indexOf("WEB-INF"));
		String fileName = "goods" + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".xls";
		String destFileName = path + fileName;
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("dto", pm.getDataList());
		beans.put("dates", new Date());
		beans.put("counts", pm.getTotalCount());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		transformer.transformXLS(templateFileName, beans, destFileName);
		return fileName;
	}
 
	@Override
	public RestCode addSubCompany(B2bCompanyDtoEx dto, String memberPk) {
		RestCode restCode = RestCode.CODE_0000;
		// 子公司用户名称不可重复
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", dto.getName());
		map.put("isDelete", 1);
		List<B2bCompanyDto> companyDtoByMap = b2bCompanyService.getCompanyDtoByMap(map);
		if (companyDtoByMap != null && companyDtoByMap.size() > 0) {
			restCode = RestCode.CODE_C006;
		} else {
			// 公司组织机构代码不可重复
			map = new HashMap<String, Object>();
			map.put("organizationCode", dto.getOrganizationCode());
			map.put("auditStatus", 2);
			map.put("isDelete", 1);
			List<B2bCompanyDto> list = b2bCompanyService.getCompanyDtoByMap(map);
			if (list != null && list.size() > 0) {
				restCode = RestCode.CODE_C003;
			} else {
				B2bCompany b2bCompany = new B2bCompany();
				b2bCompany.UpdateDTO(dto);
				//如果总公司是供应商则子公司存入供应商入驻时间
				B2bCompanyDtoEx companyDto = b2bCompanyService.getCompany(dto.getParentPk());
				if(null!=companyDto && companyDto.getAuditSpStatus() == 2){
					b2bCompany.setEnterTime(new Date());
				}
				b2bCompany.setUpdateTime(new Date());
				b2bCompany.setInfoUpdateTime(new Date());
				restCode = b2bCompanyService.addSubCompany(b2bCompany);
				// 添加子公司，添加积分放到后台审核做
			}
		}
		return restCode;
	}

	@Override
	public RestCode addPackNumber(B2bPackNumber b2bPackNumber, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			Integer result = null;
			// 编辑
			b2bPackNumber.setStoreName(session.getStoreDto().getName());
			b2bPackNumber.setStorePk(session.getStoreDto().getPk());

			if (b2bPackNumber.getPk() != null && !"".equals(b2bPackNumber.getPk())) {
				result = b2bPackNumberService.update(b2bPackNumber);
			} else {
				result = b2bPackNumberService.insert(b2bPackNumber);
			}
			if (result == 0) {
				restCode = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	@Override
	public PageModel<B2bGoodsDtoEx> searchGoodsListBySalesMan(Map<String, Object> map, Integer isAdmin,
			B2bMemberDto member, B2bCompanyDto companyDtoEx) {
		if (isAdmin != 1) {
			if (null == member.getParentPk() || "".equals(member.getParentPk()) || "-1".equals(member.getParentPk())) {
				map.put("memberPk", member.getPk());
			} else {
				map.put("memberPk", member.getParentPk());
			}
			// 查询业余员的商品类型
			map = b2bCustomerSalesmanService.getGoodsByMember(map);
		}
		return b2bGoodsService.searchGoodsListBySalesMan(map);
	}

	@Override
	public String addMarrieddeal(C2bMarrieddealDto marrieddeal, B2bMemberDto memberDto, B2bCompanyDto companyDto) {
		String result = RestCode.CODE_0000.toJson();

		try {
			marrieddeal.setMemberPk(memberDto.getPk());
			marrieddeal.setMemberName(memberDto.getMobile());
			marrieddeal.setInsertTime(new Date());
			marrieddeal.setUpdateTime(new Date());
			marrieddeal.setPurchaserPk(companyDto.getPk());
			marrieddeal.setPurchaserName(companyDto.getName());
			marrieddeal.setContacts(companyDto.getContacts());
			marrieddeal.setContactsTel(companyDto.getContactsTel());
			marrieddeal.setStartTime(DateUtil.formatYearMonthDay(new Date()));
			int exist = c2bMarrieddealService.exist(marrieddeal); // 判断采购需求是否已经存在
			if (exist == 0) {
				c2bMarrieddealService.addMarrieddeal(marrieddeal);
				result = RestCode.CODE_0000.toJson();
			} else {
				result = RestCode.CODE_O008.toJson();
			}
		} catch (Exception e) {
			logger.error("addMarrieddeal", e);
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}

		return result;
	}

	@Override
	public void updateMarrieddeal(C2bMarrieddealDto marrieddeal, Integer type) {

		C2bMarrieddeal model = new C2bMarrieddeal();
		BeanUtils.copyProperties(marrieddeal, model);
		if (type == 1) {
			model.setStartTime(DateUtil.formatYearMonthDay(new Date()));
			model.setFlag(1);
			model.setStatus(9);
			model.setAuditStatus(0);
		} else {
			model.setIsDelete(2);
		}

		c2bMarrieddealService.update(model);

	}

	@Override
	public C2bMarrieddealDto searchC2bMarrieddealByPk(String marrieddealPk) {
		return c2bMarrieddealService.getByMarrieddealId(marrieddealPk);
	}

	@Override
	public void reSummitMarrieddeal(String marrieddealPk) {
		C2bMarrieddealDto marrieddealDto = c2bMarrieddealService.getByMarrieddealId(marrieddealPk);
		C2bMarrieddeal model = new C2bMarrieddeal();
		BeanUtils.copyProperties(marrieddealDto, model);
		model.setStartTime(DateUtil.formatYearMonthDay(new Date()));
		model.setFlag(1);
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		model.setStatus(C2bMarrieddeal.MARRIEDDEAL_BIDING);
		model.setAuditStatus(0);
		model.setAuditPk(null);
		model.setAuditTime(null);
		model.setRefuseReason(null);
		model.setRemarks(null);
		model.setPk("CG" + StringUtils.getTimesRandomCode(2));
		c2bMarrieddealService.insert(model);

	}

	@Override
	public PageModel<C2bMarrieddealDtoEx> searchC2bMarrieddealByMem(Map<String, Object> map) {
		map.put("isDelete", 1);
		map.put("isHidden", 1);
		PageModel<C2bMarrieddealDtoEx> pm = new PageModel<C2bMarrieddealDtoEx>();
		List<C2bMarrieddealDtoEx> list = c2bMarrieddealService.searchMyGrid(map);
		for (C2bMarrieddealDtoEx c2bMarrieddealDto : list) {
			if (c2bMarrieddealDto.getStatus() == C2bMarrieddeal.MARRIEDDEAL_BIDING
					&& null != c2bMarrieddealDto.getSupplierPk() && !"".equals(c2bMarrieddealDto.getSupplierPk())
					&& 1 == c2bMarrieddealDto.getBidStatus()) {
				c2bMarrieddealDto.setStatus(C2bMarrieddeal.MARRIEDDEAL_BIDED);
			} else if (c2bMarrieddealDto.getStatus() == C2bMarrieddeal.MARRIEDDEAL_FINISHED) {

			}
		}
		int counts = c2bMarrieddealService.searchMyGridCount(map);
		pm.setDataList(list);
		pm.setTotalCount(counts);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}







	// 修改公司头像
	@Override
	public void saveHeadPortrait(String companyPk, String headPortrait) {
		B2bCompany company = new B2bCompany();
		company.setPk(companyPk);
		company.setHeadPortrait(headPortrait);
		company.setUpdateTime(new Date());
		company.setInfoUpdateTime(new Date());
		b2bCompanyService.updateCompany(company);
	}




	/**
	 * 商品列表
	 */
	@Override
	public PageModel<B2bGoodsDtoEx> getB2bGoodsList(Map<String, Object> map) {
		PageModel<B2bGoodsDtoEx> pm = new PageModel<B2bGoodsDtoEx>();
		map.put("isUpdown", 2);
		map.put("isNewProduct", 0);
		map.put("auditStatus", 2);
		map.put("companyStatus", 1);// 商城列表的商品不显示公司未审核通过以及删除了的
		pm = b2bGoodsService.searchGoodsList(map);
		return pm;
	}

	@Override
	public String[] findCompanys(String companyPk, Integer companyType) {
		String[] comPks = new String[1];
		comPks = b2bCompanyService.findCompanys(companyPk, companyType);
		return comPks;
	}

	@Override
	public RestCode addStoreAlbum(String storePk, String url) {
		 B2bStoreAlbum album=new B2bStoreAlbum();
		 album.setPk(KeyUtils.getUUID());
		 album.setStorePk(storePk);
		 album.setUrl(url);
		int result= albumDao.insert(album);
		if(result==1){
			return  RestCode.CODE_0000;
		}else{
			return  RestCode.CODE_S999;
		}
	}

	@Override
	public RestCode deleteStoreAlbum(String pk) {
		 int result=albumDao.delete(pk);
		 if(result==1){
				return  RestCode.CODE_0000;
			}else{
				return  RestCode.CODE_S999;
			}
	}

	@Override
	public PageModel<B2bStoreAlbumDto> searchStoreAlbum(Map<String, Object> map) {
		PageModel<B2bStoreAlbumDto> pm = new PageModel<B2bStoreAlbumDto>();
		List<B2bStoreAlbumDto> list = albumDao.searchGrid(map);
		int count = albumDao.searchGridCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public RestCode addWareNumber(B2bWarehouseNumberDto dto) {
		/* //库位号和类型唯一                放入下次需求中
		int results=b2bWareService.selectEntity(dto);
		if(results>0){
			return RestCode.CODE_O009;
		} */
		RestCode restCode = RestCode.CODE_0000;
		B2bWarehouseNumber model = new B2bWarehouseNumber();
			model.UpdateDTO(dto);
			// 新增
			if (null == dto.getPk() || "".equals(dto.getPk())) {
				int result = b2bWareService.addWareNumber(model);
				if (result == 0) {
					restCode = RestCode.CODE_A001;
				}
			} else {// 编辑
				int result = b2bWareService.updateWareNumber(model);
				if (result == 0) {
					restCode = RestCode.CODE_A001;
				}
			}
		return restCode;
	}
}