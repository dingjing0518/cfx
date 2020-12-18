package cn.cf.service.impl;

import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.constant.GoodsType;
import cn.cf.constant.MemberSys;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bGoodsBrandDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.B2bWarehouseNumberDaoEx;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bAuctionGoodsEx;
import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDtoEx;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.Sessions;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bGoodsEx;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.service.B2bAuctionService;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.service.B2bBindService;
import cn.cf.service.B2bCartService;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.B2bWareService;
import cn.cf.service.CommonService;
import cn.cf.service.FacadeService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Service
public class FacadeServiceImpl implements FacadeService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private B2bGoodsDaoEx goodsDaoEx;
	@Autowired
	private CommonService commonService;

	@Autowired
	private B2bAuctionService b2bAuctionService;
	
	@Autowired
	private B2bAuctionGoodsService b2bAuctionGoodsService;
	@Autowired
	private B2bBindGoodsService b2bBindGoodsService;
	
	@Autowired
	private B2bBindService b2bBindService;
	
	@Autowired
	private B2bCompanyService b2bCompanyService;
	@Autowired
	private B2bCartService b2bCartService;
	
	@Autowired
	private B2bGoodsService b2bGoodsService;
	@Autowired
	private B2bStoreService b2bStoreService;
	@Autowired
	private B2bAuctionGoodsDaoEx b2bAuctionGoodsDaoEx;
	@Autowired
	private B2bCustomerSaleManService  customerSaleManService;
	
	@Autowired
	private B2bWareService wareService;
    @Autowired
    private B2bWarehouseNumberDaoEx warehouseNumberDaoEx;
    
    @Autowired
    private B2bGoodsBrandDaoEx goodsBrandDaoEx;

	
	@Override
	@Transactional
	public RestCode addGood(B2bGoodsEx goods, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		goods.setStorePk(session.getStoreDto().getPk());
		goods.setStoreName(session.getStoreDto().getName());
		if(null==goods.getBlock()||"".equals(goods.getBlock())){
			goods.setBlock("cf");
		}
		Map<String, Object> goodInfo = JsonUtils.getStringToMap(goods.getGoodsInfo());
		// 验证商品是否存在
		int result =this.isGoodsRepeated(goods,goodInfo);
		if (result > 0) {
			restCode = RestCode.CODE_G001;
		} else {
			try {
				goodInfo.put("block", goods.getBlock());
				goodInfo.put("brandPk", goods.getBrandPk());
				goodInfo.put("brandName", goods.getBrandName());
				goodInfo.put("tareWeight", goods.getTareWeight());
				String goodsPk = "";
				if("cf".equals(goods.getBlock())){
					goods.setPk1(checkMap(goodInfo, "batchNumber"));
					goods.setPk2(checkMap(goodInfo,"distinctNumber"));
					goods.setPk3(checkMap(goodInfo,"gradeName"));
					goods.setPk4(checkMap(goodInfo,"packNumber"));
					goods.setSalePrice(goods.getSalePrice() == null ? goods.getTonPrice() : goods.getSalePrice());
					String stampDuty=checkMap(goodInfo, "stampDuty");
					if(!"".equals(stampDuty)){
						if(Integer.valueOf(stampDuty)==2){//印花税 1否2是
							Double  saleP= ArithUtil.mulBigDecimal(goods.getSalePrice(),Double.valueOf(PropertyConfig.getProperty("duty"))).setScale(2, RoundingMode.CEILING).doubleValue();
							goods.setSalePrice(saleP);
						}
					}
					//库位号
					B2bWarehouseNumberDto wnumber=warehouseNumberDaoEx.getOneByNumber(checkMap(goodInfo, "warehouseNumber"),session.getStoreDto().getPk());
					if(null!=wnumber){
						goodInfo.put("warehouseType", wnumber.getType());
					}
				}else{
					goods.setPk1(checkMap(goodInfo, "materialName"));
					goods.setSalePrice(goods.getSalePrice() == null ? goods.getPrice() : goods.getSalePrice());
					
				}
				//获取仓库信息
				B2bWareDto ware=wareService.getByPk(checkMap(goodInfo, "warePk"));
				if(null!=ware){
					goodInfo.put("wareName", ware.getName());
					goodInfo.put("wareCode", ware.getWareCode());
					goodInfo.put("wareAddress", ware.getAddress());
				}
			
				// 编辑
				if (goods.getPk() != null && !goods.getPk().equals("")) {
					goods.setGoodsInfo(JsonUtils.collectToString(goodInfo));
					restCode=this.updateGood(goods, session);
				} else {
					goodsPk = KeyUtils.getUUID();
					goodInfo.put("pk", goodsPk);
					goods.setPk(goodsPk);
					goods.setGoodsInfo(JsonUtils.collectToString(goodInfo));
					restCode = this.insertGood(goods);
				}
				commonService.addPoint(session.getMemberDto().getPk(), session.getCompanyDto().getPk(), 1, MemberSys.GOODS_DIMEN_UPDATE_GOODS.getValue());
			} catch (Exception e) {
				logger.error("============================", e);
				restCode = RestCode.CODE_S999;
			}
		}
		return restCode;
	}
 
	private String checkMap(Map<String, Object> goodInfo, String key) {
		if(goodInfo.containsKey(key)&&!"".equals(goodInfo.get(key).toString())){
			 return goodInfo.get(key).toString();
		}
		return "";
	}

	private RestCode insertGood(B2bGoodsEx goods) {
		RestCode restCode = RestCode.CODE_0000;
		// 新增
		goods.setIsDelete(1);
		goods.setInsertTime(new Date());
		goods.setIsUpdown(1);
		B2bGoodsDtoEx goodsDtoEx = new B2bGoodsDtoEx();
		goodsDtoEx.UpdateModel(goods);
		restCode=checkGoods(goods);
		if(!"0000".equals(restCode.getCode())){
			return restCode;
		}else{
			//添加商品
			goodsDaoEx.insert(goods);
			// 1:添加的商品是竞拍商品
			if (null != goods.getAuctionPk() && !"".equals(goods.getAuctionPk()) && null != goods.getType()
					&& GoodsType.AUCTION.getValue().equals(goods.getType())) {
				addAuction(goodsDtoEx);
				
			}
			// 2：添加的商品是拼团商品
			if (null != goods.getBindPk() && !"".equals(goods.getBindPk())
					&& GoodsType.BINDING.getValue().equals(goods.getType())) {
			 addBind(goodsDtoEx);
				
			}
		}
		return restCode;
	}

	private void addBind(B2bGoodsDtoEx goodsDtoEx) {
		b2bBindGoodsService.upToBindGoods(goodsDtoEx.getBindPk(), goodsDtoEx);
		B2bGoodsEx goodsEx = new B2bGoodsEx();
		goodsEx.setPk(goodsDtoEx.getPk());
		goodsEx.setType(GoodsType.BINDING.getValue());
		goodsEx.setIsUpdown(2);
		goodsDaoEx.updateGoods(goodsEx);// 普通商品状态更新为拼团商品
		
	}

	private void addAuction(B2bGoodsDtoEx goodsDtoEx ) {
		Date temp = DateUtil.parseDateFormat(goodsDtoEx.getActivityTime(), "yyyy-MM-dd");
		b2bAuctionGoodsService.addToAuctionGoods(goodsDtoEx, goodsDtoEx.getAuctionPk(), temp);// 添加“商品-场次”对应记录
		B2bGoodsEx goodsEx = new B2bGoodsEx();
		goodsEx.setPk(goodsDtoEx.getPk());
		goodsEx.setType(GoodsType.AUCTION.getValue());
		goodsEx.setIsUpdown(2);
		goodsDaoEx.updateGoods(goodsEx);  // 普通商品状态更新为竞拍商品
		
	}

	private int isGoodsRepeated(B2bGoodsEx goods, Map<String, Object> map) {
		Map<String, Object> map1 = new HashMap<String, Object>(map);
		 map1.put("storePk", goods.getStorePk()); 
		 map1.put("block", goods.getBlock()); 
		 map1.put("brandPk", goods.getBrandPk()); 
		 
		return goodsDaoEx.isGoodsRepeated(map1);
	}
	
	private RestCode updateGood(B2bGoodsEx goods, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		B2bGoodsDto oldGoodsEx=goodsDaoEx.getByPk(goods.getPk());
		restCode=checkGoods(goods);
		if(!"0000".equals(restCode.getCode())){
			return restCode;
		}else{
			// 修改商品信息
		 
			goodsDaoEx.updateGoods(goods);
			// 1:是否竞拍商品
			if (null != goods.getAuctionPk() && !"".equals(goods.getAuctionPk()) && null != goods.getType()
					&& GoodsType.AUCTION.getValue().equals(goods.getType())) {
				updateAuction(goods,session);
			}
			
			// 2:是否是拼团商品
			if (null != goods.getBindPk() && !"".equals(goods.getBindPk()) && null != goods.getType()
					&& GoodsType.BINDING.getValue().equals(goods.getType())) {
				updateBind(goods);
				
			}
			
			try {
				//如果修改商品类型，删除购物车数据
				if (null != goods.getType()&&!goods.getType().equals("")  ) {
					if (!oldGoodsEx.getType().equals(goods.getType())) {
						b2bCartService.delByGoodsPk(goods.getPk());//删除购物车中的数据
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return restCode;
	}

	private void updateBind(B2bGoodsEx goods) {
					// 可以修改的话，删除之前的拼团活动，重新添加拼团活动
					B2bGoodsDtoEx goodsDtoEx = new B2bGoodsDtoEx();
					goodsDtoEx.UpdateModel(goods);
					b2bBindGoodsService.delBindGoodsByGoodsPk(goods.getPk());// 删除原有的
					b2bBindGoodsService.upToBindGoods(goods.getBindPk(), goodsDtoEx);// 添加新的
					B2bGoodsEx goodsEx = new B2bGoodsEx();
					goodsEx.setPk(goods.getPk());
					goodsEx.setType(GoodsType.BINDING.getValue());
					goodsEx.setIsUpdown(2);
					goodsDaoEx.updateGoods(goodsEx);// 普通商品状态更新为拼团商品
	}

	private void updateAuction(B2bGoodsEx goods, Sessions session) {
		//查询设置的新场次
		B2bAuctionDto auctionDto=b2bAuctionService.getByPk(goods.getAuctionPk());
		B2bAuctionGoodsDtoEx auctionGoodsDtoEx = b2bAuctionGoodsService.checkGoodsAuctionStatus(goods.getPk());
		//删除所在的原来的场次
		if (null!=auctionGoodsDtoEx && null!= auctionGoodsDtoEx.getPk() && !"".equals(auctionGoodsDtoEx.getPk()) ) {
			b2bAuctionGoodsService.deleteByPk(auctionGoodsDtoEx.getPk());
		}
		B2bAuctionGoodsEx	model=new B2bAuctionGoodsEx(goods,auctionDto,session);
		b2bAuctionGoodsService.insert(model);
		B2bGoodsEx goodsEx = new B2bGoodsEx();
		goodsEx.setPk(goods.getPk());
		goodsEx.setType(GoodsType.AUCTION.getValue());
		goodsEx.setIsUpdown(2);
		goodsDaoEx.updateGoods(goodsEx); // 普通商品状态更新为竞拍商品
		
	}

	private RestCode checkGoods(B2bGoodsEx goods) {
		        //1：如果是竞拍商品，验证竞拍信息的合法性
				if (null != goods.getAuctionPk() && !"".equals(goods.getAuctionPk()) && null != goods.getType()
						&& GoodsType.AUCTION.getValue().equals(goods.getType())) {
					// 查询场次
					B2bAuctionDto auctionDto = b2bAuctionService.getByPk(goods.getAuctionPk());
					String startTime = goods.getActivityTime() + " " + auctionDto.getStartTime() + ":00";
					//竞拍开始时间不能小于当前时间
					if (!DateUtil.isDateBefore(startTime)) {
						return RestCode.CODE_G011;
					}
					B2bAuctionGoodsDtoEx auctionGoodsDtoEx = b2bAuctionGoodsService.checkGoodsAuctionStatus(goods.getPk());
					if (null != auctionGoodsDtoEx && auctionGoodsDtoEx.getAcStatus() == 3) {
						return RestCode.CODE_G012;
					}
				}
				//2:如果是拼团商品，验证拼团信息的合法性
				if (null != goods.getBindPk() && !"".equals(goods.getBindPk()) && null != goods.getType()
						&& GoodsType.BINDING.getValue().equals(goods.getType())) {
					// 判断商品是否处于上架的拼团活动中，如果商品处于上架的拼团活动中，则不可以修改
					// 可以修改的话，删除之前的拼团活动，重新添加拼团活动
					B2bBindGoodsDtoEx bindGoodsDtoEx = b2bBindGoodsService.getBindGoodDtoExByGoodsPk(goods.getPk());
					if (null != bindGoodsDtoEx && null != bindGoodsDtoEx.getPk() && !"".equals(bindGoodsDtoEx.getPk())
							&& bindGoodsDtoEx.getBisUpDown()==1) {
						return RestCode.CODE_P021;
					} else {
						B2bBindDto bindDto = b2bBindService.getBind(goods.getBindPk());
						if (null!=bindDto && bindDto.getIsUpDown()==1) {
							return RestCode.CODE_P022;
						}
					}
				}
				//3:验证商品价格
				if (goods.getPrice() == 0 || goods.getTonPrice() == 0) {
		           return RestCode.CODE_G000;
		        }
				//4:验证商品箱数
		        if (goods.getTotalBoxes()==0) {
					return RestCode.CODE_G020;
				}
		        //5:验证商品箱重
		        if (goods.getTareWeight()==0) {
					return RestCode.CODE_G007;
				}
		        //6:验证商品总重量
		        if (goods.getTotalWeight()==0) {
					return RestCode.CODE_G021;
				}
				return RestCode.CODE_0000;
	}

	@Override
	public B2bGoodsDtoEx getB2bGoodsByPk(String pk, String memberPk) {
		// 1.查询商品详情
		B2bGoodsDto gdto =  b2bGoodsService.getDetailsById(pk);
		if(null!=gdto&&!"".equals(gdto)){
			B2bGoodsDtoEx goods = new B2bGoodsDtoEx();
			goods.UpdateMine(gdto);
			// 2.商品所属店铺被收藏的量
			if(null != goods){
				goods.setCollectCompanysNum(b2bCompanyService.countCollectCom(goods.getStorePk()));
				// 3.查询是否已经收藏 1未收藏 2已收藏
				if (memberPk != null && !"".equals(memberPk)) {
					List<CollectionGoods> collections = b2bGoodsService.iscollected(pk, memberPk);
					goods.setIsCollected(collections != null && collections.size() > 0 ? 2 : 1);
				}
				// 4.查询商品收藏次数
				Long count = b2bGoodsService.countCollectGoods(pk);
				goods.setCollectGoodsNum(null != count ? count : 0l);
				// 5.获取头像
				B2bStoreDtoEx store = b2bStoreService.getCompStoreByStorePk(goods.getStorePk());
				if (null != store) {
					goods.setHeadPortrait(store.getHeadPortrait());
					goods.setQq(store.getQq());
				}
				// 5如果是竞拍商品
				if (null != goods.getType() && GoodsType.AUCTION.getValue().equals(goods.getType())) {
					
					B2bAuctionGoodsDtoEx auctionGoodsDtoEx = b2bAuctionGoodsDaoEx.checkGoodsAuctionStatus(goods.getPk());
					if (null != auctionGoodsDtoEx && null != auctionGoodsDtoEx.getPk()
							&& !"".equals(auctionGoodsDtoEx.getPk())) {
						goods.setAuctionPk(auctionGoodsDtoEx.getAuctionPk());
						goods.setActivityTime(
								null == auctionGoodsDtoEx.getActivityTime() ? "" : auctionGoodsDtoEx.getActivityTime() + "");
						goods.setStartingPrice(
								null == auctionGoodsDtoEx.getStartingPrice() ? 0.0 : auctionGoodsDtoEx.getStartingPrice());
					}
				}
				// 6如果是拼团商品
				if (null != goods.getType() && GoodsType.BINDING.getValue().equals(goods.getType())) {
					Map<String, Object> map = new HashMap<>();
					map.put("goodsPk", pk);
					List<B2bBindGoodsDto> list = b2bBindGoodsService.getBindGoodList(map);
					if (null != list && list.size() > 0) {
						goods.setBindPk(list.get(0).getBindPk());
					}
				}
			}

			return goods;	
		}
		return null;
		
	}
/***
 *  纱线商品属性：产品大类（品牌简称+原料构成+工艺）、物料名称（品牌简称+原料构成简称+规格+工艺简称）、批号、产品类型;
	化纤商品属性：品名、品种、子品种、规格大类、规格系列、区分号、批号、包装形式、等级；
 */
	@Override
	public RestCode importGoods(String strs, Sessions session ) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			JSONArray array = JSONArray.parseArray(strs);
			JSONObject json = null;
			for (int i = 0; i < array.size(); i++) {
				json = array.getJSONObject(i);
				// 1校验参数
					restCode = b2bGoodsService.IsGoodsParamNull(json);
				if (!restCode.getCode().equals("0000")) {
					break;
				}  
			}
			// 更新商品
			if (restCode.getCode().equals("0000")) {
				restCode = this.updateImportGoods(strs, session.getStoreDto().getPk());
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}

		return restCode;
	}

	private RestCode updateImportGoods(String strs, String storePk) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			List<B2bGoodsDtoEx> goos=JsonUtils.getStringToList(strs, B2bGoodsDtoEx.class);
			// 将店铺所有商品设为下架
			if (updateDateZero(storePk)) {
				// 更新商品相关信息
				for (B2bGoodsDtoEx good : goos) {
					//原商品信息
				    B2bGoodsDto gDto= goodsDaoEx.getByPk(good.getPk());
					Map<String, Object> map=JsonUtils.getStringToMap(gDto.getGoodsInfo());
					if(null!=good.getUnitName()&&!"".equals(good.getUnitName())){
						map.put("unit",getUnit(good.getUnitName()));
					}
					if(null!=good.getUpDown()&&!"".equals(good.getUpDown())){
						good.setIsUpdown(getIsUpDown(good.getUpDown()));
					}
					if(null!=good.getTareWeight()&&!"".equals(good.getTareWeight())){
						map.put("tareWeight",good.getTareWeight());
					}
					// 如果特卖价为0 则默认取挂牌价
					if(good.getSalePrice()==0){
						good.setSalePrice(good.getTonPrice());
						 
					}else{
						good.setSalePrice(good.getSalePrice());
					}
					if(Block.CF.getValue().equals(gDto.getBlock())){
						map.put("packageFee",good.getPackageFee());
						map.put("loadFee",good.getLoadFee());
						map.put("adminFee",good.getAdminFee());
						if(null!=good.getSuitRangeDescribe()&&!"".equals(good.getSuitRangeDescribe())){
							map.put("suitRangeDescribe",good.getSuitRangeDescribe());
						}
						if(null!=good.getPurposeDescribe()&&!"".equals(good.getPurposeDescribe())){
							map.put("purposeDescribe",good.getPurposeDescribe());
						}
						good.setPrice(ArithUtil.div(good.getSalePrice(), 1000));
					}else{
						good.setPrice(good.getSalePrice());
						map.put("meno",good.getSuitRangeDescribe());
					}
					good.setGoodsInfo(JsonUtils.collectToString(map));
					
	 
					goodsDaoEx.updateImportGoods(good);
				}
			} else {
				restCode = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}
	
	private boolean updateDateZero(String storePk) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clearKuCun", true);
		params.put("clearUpdown", true);
		params.put("clearPrice", true);
		params.put("storePk", storePk);
		params.put("isDelete", 1);
		return goodsDaoEx.updateDataZero(params);
	}
 
	private Integer getUnit(String unitName) {
		Integer unit = null;
		switch (unitName) {
		case "箱":
			unit = 1;
			break;
		case "锭":
			unit = 2;
			break;
		case "件":
			unit = 3;
			break;
		case "粒":
			unit = 4;
			break;
		default:
			unit = 1;
			break;
		}
		return unit;
	}
	
	private Integer getIsUpDown(String isUpdownName) {
		Integer isUpdown = null;
		switch (isUpdownName) {
		case "待上架":
			isUpdown = 1;
			break;
		case "上架":
			isUpdown = 2;
			break;
		case "下架":
			isUpdown = 3;
			break;
		default:
			isUpdown = 2;
			break;
		}
		return isUpdown;
	}

	@Override
	public PageModel<B2bGoodsDtoEx> searchGoodsListBySalesMan(
			Map<String, Object> map, Integer isAdmin, B2bMemberDto member,
			B2bCompanyDto companyDto) {
		if (isAdmin != 1) {
			if (null == member.getParentPk() || "".equals(member.getParentPk()) || "-1".equals(member.getParentPk())) {
				map.put("memberPk", member.getPk());
			} else {
				map.put("memberPk", member.getParentPk());
			}
			// 查询业余员的商品类型
			map = customerSaleManService.getGoodsByMember(map);
		}
		return b2bGoodsService.searchGoodsListBySalesMan(map);
	
	}
 public static void main(String[] args) {
	 System.out.println(ArithUtil.mulBigDecimal(0.00111000001, 1).setScale(2, RoundingMode.CEILING).doubleValue());
		
}
}
