package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import cn.cf.StringHelper;
import cn.cf.dao.B2bBindDaoEx;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bGradeDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bSpecDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bVarietiesDto;
import cn.cf.entity.B2bBindDtoEx;
import cn.cf.entity.CfGoods;
import cn.cf.entity.ErpGoodsShow;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bBind;
import cn.cf.model.B2bBindGoods;
import cn.cf.model.B2bGoods;
import cn.cf.model.B2bWare;
import cn.cf.service.B2bBrandService;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bGoodsBrandService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bGradeService;
import cn.cf.service.B2bPlantService;
import cn.cf.service.B2bProductService;
import cn.cf.service.B2bSpecService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.B2bVarietiesService;
import cn.cf.service.B2bWareService;
import cn.cf.util.KeyUtils;

@Service

public class B2bGoodsServiceImpl implements B2bGoodsService {

	@Autowired
	private B2bCompanyService b2bCompanyService;

	@Autowired
	private B2bStoreService b2bStoreService;

	@Autowired
	private B2bProductService b2bProductService;

	@Autowired
	private B2bVarietiesService b2bVarietiesService;

	@Autowired
	private B2bSpecService b2bSpecService;

	@Autowired
	private B2bGradeService b2bGradeService;

	@Autowired
	private B2bPlantService b2bPlantService;
	
	@Autowired
	private B2bWareService b2bWareService;
	
	@Autowired
	private B2bBrandService b2bBrandService;
	
	@Autowired
	private B2bGoodsBrandService b2bGoodsBrandService;
	
	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;

	@Autowired
	private B2bBindDaoEx b2bBindDaoEx;

	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;
	

	private final static Logger logger = LoggerFactory.getLogger(B2bGoodsServiceImpl.class);
	

	@Override
	public void updateDataZero(String storePk, boolean clearKuCun,
			boolean clearPrice, boolean ckearUpdown, boolean clearIsNewPrice)
			 {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clearKuCun", clearKuCun);
		params.put("clearUpdown", ckearUpdown);
		params.put("clearPrice", clearPrice);
		params.put("clearIsNewPrice", clearIsNewPrice);
		params.put("storePk", storePk);
		logger.info(" updateDataZero.........." + JsonUtils.convertToString(params));
		b2bGoodsDaoEx.updateDataZero(params);
		
	}

	/**
	 * 构建商品
	 * @param b2bgoodsEx
	 * @param currentCompanyPk
	 * @param storePk
	 * @param flag
	 * @return
	 */
	private B2bGoodsDtoEx buildGoods(B2bGoodsDtoEx b2bgoodsEx, String currentCompanyPk, String storePk,Boolean flag)  {
		Map<String, Object> map = new HashMap<String, Object>();
		b2bgoodsEx.setIsDelete(1);
		b2bgoodsEx.setInsertTime(new Date());
		if (b2bgoodsEx.getIsUpDown() != null) {
//			if (b2bgoodsEx.getIsUpDown() == 1) {
//				// 开店的时候显示设置成待上架
//				if (flag) {
//					b2bgoodsEx.setIsUpdown(1);
//				} else {
//					b2bgoodsEx.setIsUpdown(2);
//					b2bgoodsEx.setUpTime(new Date());
//				}
//			} else if (b2bgoodsEx.getIsUpDown() == 2) {
//				b2bgoodsEx.setIsUpdown(3);
//			}
			if(b2bgoodsEx.getIsUpDown() == 1){
				b2bgoodsEx.setIsUpdown(2);
			}
			if(b2bgoodsEx.getIsUpDown() == 2){
				b2bgoodsEx.setIsUpdown(3);
			}
		}
		if (null == b2bgoodsEx.getUnit()) {
			b2bgoodsEx.setUnit(1);
		}
		if (null == b2bgoodsEx.getPurposeDescribe()) {
			b2bgoodsEx.setPurpose("");
			b2bgoodsEx.setPurposeDescribe("");
		} else {
			b2bgoodsEx.setPurposeDescribe(b2bgoodsEx.getPurposeDescribe());
		}
		if (null == b2bgoodsEx.getSuitRangeDescribe()) {
			b2bgoodsEx.setDetails("");
			b2bgoodsEx.setSuitRangeDescribe("");
		} else {
			b2bgoodsEx.setSuitRangeDescribe(b2bgoodsEx.getSuitRangeDescribe());
		}

		B2bCompanyDto company = b2bCompanyService.getByName(b2bgoodsEx.getCompanyName());
		if (null == company) {
			String pk = b2bCompanyService.createNewCompany(b2bgoodsEx.getCompanyName(), currentCompanyPk);
			b2bgoodsEx.setCompanyPk(pk);
		} else {
			b2bgoodsEx.setCompanyPk(company.getPk());
		}

		B2bStoreDto store = null;
		store = b2bStoreService.getByPk(storePk);
		if (store != null) {
			b2bgoodsEx.setStorePk(store.getPk());
			b2bgoodsEx.setStoreName(store.getName());
		}

		// 商品品名
		if (b2bgoodsEx.getProductName() != null && !b2bgoodsEx.getProductName().equals("")) {
			B2bProductDto bP = b2bProductService.getByName(b2bgoodsEx.getProductName());
			if (bP == null) {
				String pk = b2bProductService.createNewProduct(b2bgoodsEx.getProductName());
				b2bgoodsEx.setProductPk(pk);
			} else {
				b2bgoodsEx.setProductPk(bP.getPk());
			}
		} else {
			b2bgoodsEx.setProductName("");
			b2bgoodsEx.setProductPk("");
		}

		// 品种
		String varietiesName = b2bgoodsEx.getVarietiesName();
		if (varietiesName != null && !varietiesName.equals("")) {
			varietiesName = StringHelper.verifySign(varietiesName);
			addVarieties(b2bgoodsEx, map, varietiesName);
		} else {
			b2bgoodsEx.setVarietiesPk("");
			b2bgoodsEx.setVarietiesName("");
		}
		// 子品种
		String seedvarietyName = b2bgoodsEx.getSeedvarietyName();
		if (seedvarietyName != null && !"".equals(seedvarietyName)) {
			seedvarietyName = StringHelper.verifySign(seedvarietyName);
			map.clear();
			//1 根据名称和parentPk查询子品种 如果没有品种则不带上parentPk条件
			if("".equals(b2bgoodsEx.getVarietiesPk())){
				map.put("noParent", "-1");
			}else{
				map.put("parentPk", b2bgoodsEx.getVarietiesPk());
			}
			map.put("name", seedvarietyName);
			B2bVarietiesDto sebv = b2bVarietiesService.getByParentAndName(map);
			if (sebv == null) {
				if("".equals(b2bgoodsEx.getVarietiesPk())){
					addVarieties(b2bgoodsEx, map, seedvarietyName);
					map.put("parentPk", b2bgoodsEx.getVarietiesPk());
				}
				String pk = b2bVarietiesService.createNewVarieties(map);
				b2bgoodsEx.setSeedvarietyPk(pk);
			} else {
				b2bgoodsEx.setSeedvarietyPk(sebv.getPk());
				if("".equals(b2bgoodsEx.getVarietiesPk())){
					B2bVarietiesDto var = b2bVarietiesService.getByParentAndName(map);
					b2bgoodsEx.setVarietiesPk(null==var?"":var.getPk());
					b2bgoodsEx.setVarietiesName(null==var?"":var.getName());
				}
			}
		} else {
			b2bgoodsEx.setSeedvarietyPk(b2bgoodsEx.getVarietiesPk());
			b2bgoodsEx.setSeedvarietyName(b2bgoodsEx.getVarietiesName());
		}
		
		// 商品规格大类
		if(b2bgoodsEx.getSpecName()!=null && !b2bgoodsEx.getSpecName().equals("")){
			addSpec(b2bgoodsEx);
		}else{
			if (b2bgoodsEx.getSeriesName()!=null&&!b2bgoodsEx.getSeriesName().equals("")) {
				String[] spec= b2bgoodsEx.getSeriesName().split("/");
				if(null!=spec&&spec.length>0){
					if(spec.length==1){
						 b2bgoodsEx.setSpecName(spec[0]);
						 b2bgoodsEx.setSeriesName("");
					}else{
						 b2bgoodsEx.setSpecName(spec[0]);
					}
					 addSpec(b2bgoodsEx);
				}
			}
		}
		
		// 商品规格系列
		if(null != b2bgoodsEx.getSeriesName() && !"".equals(b2bgoodsEx.getSeriesName())){
			B2bSpecDto sebSpec = null;
			try {
				 sebSpec = b2bSpecService.getBySeariesName(b2bgoodsEx.getSeriesName());
			} catch (Exception e) {
				logger.error("---------------------重复的specName:"+b2bgoodsEx.getSeriesName(),e);
			}
			if (sebSpec == null) {
				String pk = b2bSpecService.createNewSpec(b2bgoodsEx.getSpecPk(), b2bgoodsEx.getSeriesName());
				b2bgoodsEx.setSeriesPk(pk);
			} else {
				b2bgoodsEx.setSeriesPk(sebSpec.getPk());
			}
		}

		//商品规格（erp专用属性）
		if (b2bgoodsEx.getSpecifications()==null||b2bgoodsEx.getSpecifications().equals("")) {
			b2bgoodsEx.setSpecifications(b2bgoodsEx.getSpecName());
		}
		
				
		// 商品等级
		B2bGradeDto bGrade = b2bGradeService.getByName(b2bgoodsEx.getGradeName());
		if (bGrade == null) {
			String pk = b2bGradeService.createNewGrade(b2bgoodsEx.getGradeName());
			b2bgoodsEx.setGradePk(pk);
		} else {
			b2bgoodsEx.setGradePk(bGrade.getPk());
			b2bgoodsEx.setGradeChineseName(null==bGrade.getChineseName()?"":bGrade.getChineseName());
		}

		// 厂区号 非必填
		String plantCode = b2bgoodsEx.getPlantCode();
		// 厂区名称
		String plantName = b2bgoodsEx.getPlantName();
		// 厂区地址
		String plantAddress = b2bgoodsEx.getPlantAddress();

		map.clear();
		if(null != plantName && !"".equals(plantName)){
			map.put("name", plantName);
			map.put("storePk", storePk);
			B2bPlantDto bPlant = b2bPlantService.getByName(map);
			if (bPlant == null) {
				String pk = b2bPlantService.createNewPlant(plantName, plantCode, plantAddress, storePk);
				b2bgoodsEx.setPlantPk(pk);
			} else {
				b2bgoodsEx.setPlantPk(bPlant.getPk());
			}
		}
		// 仓库号 非必填
		String wareCode = b2bgoodsEx.getWareCode();
		// 仓库名称
		String wareName = b2bgoodsEx.getWareName();
		// 仓库地址
		String wareAddress = b2bgoodsEx.getWareAddress();

		map.clear();
		if(wareName != null && !"".equals(wareName)){
			map.put("name", wareName);
			map.put("storePk", storePk);
			B2bWare bWare = b2bWareService.getByName(map);
			if (bWare == null) {
				String pk = b2bWareService.createNewWare(wareName, wareCode, wareAddress, storePk);
				b2bgoodsEx.setWarePk(pk);
			} else {
				b2bgoodsEx.setWarePk(bWare.getPk());
			}
		}
		// 品牌
		String brandName = b2bgoodsEx.getBrandName();
		if (null != brandName && !"".equals(brandName)) {
			map.clear();
			map.put("name", brandName);
			List<B2bBrandDto> brands = b2bBrandService.searchGrid(map);
			String brandPk = "";
			if (null != brands && brands.size() < 1) {
				brandPk = b2bBrandService.createNewBrand(brandName);
			}
			map.clear();
			map.put("brandName", brandName);
			map.put("storePk", storePk);
			B2bGoodsBrandDto goodsBrand = b2bGoodsBrandService.getByBrandName(map);
			if (goodsBrand == null) {
				String pk = b2bGoodsBrandService.createNewGoodsBrand(brandName, brandPk, storePk,b2bgoodsEx.getStoreName());
				b2bgoodsEx.setBrandPk(pk);
			} else {
				b2bgoodsEx.setBrandPk(goodsBrand.getPk());
			}
		}

		if (b2bgoodsEx.getGoodsType() == null || "".equals(b2bgoodsEx.getGoodsType())) {
			b2bgoodsEx.setType("normal");
		}else{
			b2bgoodsEx.setType(b2bgoodsEx.getGoodsType());
		}
		if (b2bgoodsEx.getIsNewProduct() == null || "".equals(b2bgoodsEx.getIsNewProduct())) {
			b2bgoodsEx.setIsNewProduct(0);// 默认显示
		}
		
		if (null!=b2bgoodsEx.getPackageNumber()) {
			b2bgoodsEx.setPackNumber(b2bgoodsEx.getPackageNumber());
		}else{
			b2bgoodsEx.setPackNumber(b2bgoodsEx.getPackNumber());
		}
		return b2bgoodsEx;
	}

	private void addVarieties(B2bGoodsDtoEx b2bgoodsEx,Map<String, Object> map, String varietiesName)  {
		map.clear();
		map.put("parentPk", "-1");
		map.put("name", varietiesName);
		B2bVarietiesDto bv = b2bVarietiesService.getByParentAndName(map);
		if (bv == null) {
			String pk = b2bVarietiesService.createNewVarieties(map);
			b2bgoodsEx.setVarietiesPk(pk);
		} else {
			b2bgoodsEx.setVarietiesPk(bv.getPk());
		}
	}

	private void addSpec(B2bGoodsDtoEx b2bgoodsEx) {
		B2bSpecDto bSpec = b2bSpecService.getByNameParent(b2bgoodsEx.getSpecName());
		if (bSpec == null) {
			String pk = b2bSpecService.createNewSpec("-1", b2bgoodsEx.getSpecName());
			b2bgoodsEx.setSpecPk(pk);
		} else {
			b2bgoodsEx.setSpecPk(bSpec.getPk());
		}
	}

	@Override
	public int updateWeightBatch(List<B2bGoodsDtoEx> list){
		Integer pointsDataLimit = 1000;//限制条数
		Integer size = list.size();
		if(pointsDataLimit<size){
			int part = size/pointsDataLimit;//分批数
				//分批执行的数据
				for (int i = 0; i < part; i++) {
					List<B2bGoodsDtoEx> listPage = list.subList(0, pointsDataLimit);
					b2bGoodsDaoEx.updateWeightBatch(listPage);
					list.subList(0, pointsDataLimit).clear();
				}
				//剩余的数据
				if(!list.isEmpty()){
					b2bGoodsDaoEx.updateWeightBatch(list);
				}
		}else{
			b2bGoodsDaoEx.updateWeightBatch(list);
		}
		return 1;
	}

	@Override
	public int updatePriceBatch(List<B2bGoodsDtoEx> list){
		
		Integer pointsDataLimit = 1000;//限制条数
		Integer size = list.size();
		if(pointsDataLimit<size){
			int part = size/pointsDataLimit;//分批数
				//分批执行的数据
				for (int i = 0; i < part; i++) {
					List<B2bGoodsDtoEx> listPage = list.subList(0, pointsDataLimit);
					b2bGoodsDaoEx.updatePriceBatch(listPage);
					list.subList(0, pointsDataLimit).clear();
				}
				//剩余的数据
				if(!list.isEmpty()){
					b2bGoodsDaoEx.updatePriceBatch(list);
				}
		}else{
			b2bGoodsDaoEx.updatePriceBatch(list);
		}
		return 1;
	}

	@Override
	@Transactional
	public int updateIsNewProductBatch(List<ErpGoodsShow> list){
		Integer pointsDataLimit = 1000;//限制条数
		Integer size = list.size();
		if(pointsDataLimit<size){
			int part = size/pointsDataLimit;//分批数
				//分批执行的数据
				for (int i = 0; i < part; i++) {
					List<ErpGoodsShow> listPage = list.subList(0, pointsDataLimit);
					b2bGoodsDaoEx.updateIsNewProduct(listPage);
					list.subList(0, pointsDataLimit).clear();
				}
				//剩余的数据
				if(!list.isEmpty()){
					b2bGoodsDaoEx.updateIsNewProduct(list);
				}
		}else{
			b2bGoodsDaoEx.updateIsNewProduct(list);
		}
		return 1;
	}

	/**
	 * 以batchNumber,packNumber(可能为空),gradeName,distinctNumber(可能为空)为主键查找商品
	 */
	@Override
	public B2bGoodsDto searchGoodsIs(B2bGoodsDtoEx b2bgoodsEx) {
		return b2bGoodsDaoEx.searchGoodsIs(b2bgoodsEx);
		
	}

	@Override
	public void insertBatch(List<B2bGoodsDtoEx> insertList, List<B2bGoodsDtoEx> updateList, String companyPk,
			String storePk, Boolean flag) {
		if (null != insertList && insertList.size() > 0) {
			for (B2bGoodsDtoEx b2bGoodsDtoEx : insertList) {
				B2bGoodsDtoEx b2bgoodsEx = buildGoods(b2bGoodsDtoEx, companyPk, storePk, flag);
				B2bGoods model = new B2bGoods();
				b2bgoodsEx.setPk(KeyUtils.getUUID());
				b2bgoodsEx.setUpdateTime(new Date());
				CfGoods cfGoods = new CfGoods();
				BeanUtils.copyProperties(b2bgoodsEx, cfGoods);
				b2bgoodsEx.setGoodsInfo(JSON.toJSONString(cfGoods));
				BeanUtils.copyProperties(b2bgoodsEx, model);
				b2bGoodsDaoEx.insertEx(model);
			}
		}
		if (null != updateList && updateList.size() > 0) {
			for (B2bGoodsDtoEx b2bGoodsDtoEx : updateList) {
				B2bGoodsDtoEx b2bgoodsEx = buildGoods(b2bGoodsDtoEx, companyPk, storePk, flag);
				B2bGoodsDto model = new B2bGoodsDto();
				CfGoods cfGoods = new CfGoods();
				BeanUtils.copyProperties(b2bgoodsEx, cfGoods);
				b2bgoodsEx.setGoodsInfo(JSON.toJSONString(cfGoods));
				BeanUtils.copyProperties(b2bgoodsEx, model);
				b2bGoodsDaoEx.updateEx(model);
			}
		}

	}

	@Override
	public int updateGoodsAuctionBatch(List<B2bGoodsDto> list){
		return b2bGoodsDaoEx.updateGoodsAuctionBatch(list);
	}

	@Override
	public int updateGoodsBindBatch(List<B2bBindDtoEx> list){
		int i = 0;
		Map<String, Object> map = new HashMap<>();
		List<B2bBindGoodsDto> goodsList = new ArrayList<B2bBindGoodsDto>();
		for (B2bBindDtoEx b2bBindDtoEx : list) {
			// 判断捆绑表
			String pk = insertBind(map, b2bBindDtoEx);
			// 捆绑商品表(删除原有捆绑，插入新商品);
			b2bBindGoodsDaoEx.deleteBybBindPk(pk);
			if (b2bBindDtoEx.getItems() != null && b2bBindDtoEx.getItems().size() > 0) {
				List<String> strs = new ArrayList<String>();
				for (B2bBindGoodsDtoEx bindGoodsDto : b2bBindDtoEx.getItems()) {
					// 排除主键相同的商品
					if (!strs.contains(bindGoodsDto.getGoodsPk() + pk)) {
						strs.contains(bindGoodsDto.getGoodsPk() + pk);
						bindGoodsDto.setBindPk(pk);
						bindGoodsDto.setPk(KeyUtils.getUUID());
						goodsList.add(bindGoodsDto);
						B2bBindGoods model = new B2bBindGoods();
						BeanUtils.copyProperties(bindGoodsDto, model);
						i = b2bBindGoodsDaoEx.insert(model);
					}
				}
				strs.clear();
			}
			// 更新商品类型为捆绑
			if (goodsList != null && goodsList.size() > 0) {
				b2bGoodsDaoEx.updateGoodsType(goodsList);
			}
		}
		return i;
	}

	private String insertBind(Map<String, Object> map, B2bBindDtoEx b2bBindDtoEx) {
		String pk = KeyUtils.getUUID();
		B2bBindDto bindDto = b2bBindDaoEx.getOneByBindProductId(b2bBindDtoEx.getBindProductID());
		if (bindDto != null && !bindDto.equals("")) {
			pk = bindDto.getPk();
			// 更新
			map.put("pk", pk);
			map.put("bindName", b2bBindDtoEx.getBindName());
			map.put("bindProductCount", b2bBindDtoEx.getBindProductCount());
			map.put("bindReason", b2bBindDtoEx.getBindReason());
			map.put("bindProductDetails", b2bBindDtoEx.getBindProductDetails());
			map.put("bindProductPrice", b2bBindDtoEx.getBindProductPrice());
			map.put("storePk", b2bBindDtoEx.getStorePk());
			map.put("storeName", b2bBindDtoEx.getStoreName());
			map.put("isUpDown", 1);
			b2bBindDaoEx.updateBind(map);
		} else {
			B2bBind model = new B2bBind();
			b2bBindDtoEx.setPk(pk);
			BeanUtils.copyProperties(b2bBindDtoEx, model);
			model.setUpdateTime(new Date());
			model.setStatus(0);
			model.setIsUpDown(1);
			model.setInsertTime(new Date());
			b2bBindDaoEx.insert(model);
		}
		return pk;
	}

	@Override
	public B2bGoodsDto getB2bGoodsInfoByBindGoods(B2bBindGoodsDtoEx b2bBindGoodsDto, String storePk) {
		B2bGoodsDtoEx gdto = new B2bGoodsDtoEx();
		gdto.setPk1(b2bBindGoodsDto.getBatchNumber());
		gdto.setPk3(b2bBindGoodsDto.getGradeName());
		gdto.setPk2(b2bBindGoodsDto.getDistinctNumber());
		gdto.setPk4(b2bBindGoodsDto.getPackNumber());
		gdto.setStorePk(storePk);
		return b2bGoodsDaoEx.searchGoodsIs(gdto);
	}

	 

	@Override
	public int updateGoodsType(String storePk, String oldType, String newType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("storePk", storePk);
		map.put("oldType", oldType);
		map.put("newType", newType);
		return b2bGoodsDaoEx.updateNewGoodsType(map);
	}

	@Override
	public List<B2bGoodsDtoEx> searchGoodsList(B2bGoodsDtoEx goodsDtoEx) {
		return b2bGoodsDaoEx.searchGoodsList(goodsDtoEx);
	}

	@Override
	public void updateShowByStorePk(String storePk) {
		b2bGoodsDaoEx.updateShowByStorePk(storePk);
		
	}

}
