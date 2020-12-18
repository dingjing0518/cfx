package cn.cf.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.service.HuaxianhuiBankService;
import cn.cf.service.B2bLoanNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.BillType;
import cn.cf.constant.Block;
import cn.cf.constant.GoodsType;
import cn.cf.constant.OrderRecordType;
import cn.cf.constant.SmsCode;
import cn.cf.dao.B2bBindDaoEx;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dao.B2bBindOrderDaoEx;
import cn.cf.dao.B2bCartDaoEx;
import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.B2bOrderDaoEx;
import cn.cf.dao.B2bOrderGoodsDaoEx;
import cn.cf.dao.B2bReserveOrderDaoEx;
import cn.cf.dao.LogisticsErpDaoEx;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bAuctionOfferDtoEx;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bBindOrderDto;
import cn.cf.dto.B2bCartDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractDtoEx;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.B2bReserveOrderDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.ExportOrderDto;
import cn.cf.dto.LogisticsErpDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.dto.PageModelAuctionGoods;
import cn.cf.dto.PageModelAuctionGoodsByMember;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.BackGoods;
import cn.cf.entity.BackPgoods;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.CallBackContract;
import cn.cf.entity.CallBackOrder;
import cn.cf.entity.Cgoods;
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.entity.Corder;
import cn.cf.entity.DeliveryDto;
import cn.cf.entity.DeliveryGoods;
import cn.cf.entity.ForB2BLgPriceDto;
import cn.cf.entity.LogisticsCart;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.OrderSyncToMongo;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.Sessions;
import cn.cf.entity.UpdateGoods;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bAuctionOffer;
import cn.cf.model.B2bBind;
import cn.cf.model.B2bBindGoods;
import cn.cf.model.B2bBindOrder;
import cn.cf.model.B2bBindOrderGoodsEx;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bGoodsEx;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bOrderGoods;
import cn.cf.model.B2bReserveGoods;
import cn.cf.model.B2bReserveOrder;
import cn.cf.service.B2bAddressService;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.service.B2bAuctionOfferService;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.service.B2bBindOrderService;
import cn.cf.service.B2bBindService;
import cn.cf.service.B2bCartService;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bContractGoodsService;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.B2bOrderRecordService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bPayVoucherService;
import cn.cf.service.B2bPaymentService;
import cn.cf.service.B2bReserveService;
import cn.cf.service.B2bTokenService;
import cn.cf.service.CmsService;
import cn.cf.service.CommonService;
import cn.cf.service.CompanyBankcardService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.ForeignBankService;
import cn.cf.service.LogisticsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class B2bFacadeServiceImpl implements B2bFacadeService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private B2bOrderService b2bOrderService;

	@Autowired
	private B2bReserveService reserveService;

	@Autowired
	private B2bCompanyService b2bCompanyService;

	@Autowired
	private B2bGoodsService b2bGoodsService;

	@Autowired
	private B2bAuctionGoodsService b2bAuctionGoodsService;

	@Autowired
	private B2bAuctionOfferService b2bAuctionOfferService;

	@Autowired
	private LogisticsService logisticsService;

	@Autowired
	private B2bAddressService addressService;

	@Autowired
	private B2bCartService b2bCartService;

	@Autowired
	private B2bBindOrderService bindOrderService;

	@Autowired
	private B2bCustomerSaleManService b2bCustomerSalesmanService;

	@Autowired
	private CuccSmsService smsService;

	@Autowired
	private B2bTokenService b2bTokenService;

	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bPayVoucherService b2bPayVoucherService;

	@Autowired
	private B2bPaymentService b2bPaymentService;

	@Autowired
	private B2bOrderRecordService b2bOrderRecordService;

	@Autowired
	private ForeignBankService foreignBankService;

	@Autowired
	private B2bContractService b2bContractService;

	@Autowired
	private B2bContractGoodsService b2bContractGoodsService;

	@Autowired
	private B2bBindService b2bBindService;

	@Autowired
	private B2bBindOrderService b2bBindOrderService;

	@Autowired
	private B2bBindGoodsService b2bBindGoodsService;

	@Autowired
	private B2bOrderDaoEx orderDaoExt;

	@Autowired
	private B2bOrderGoodsDaoEx orderGoodsDaoExt;

	@Autowired
	private B2bGoodsDaoEx goodsDaoEx;

	@Autowired
	private B2bCartDaoEx cartDaoEx;

	@Autowired
	private B2bBindOrderDaoEx b2bBindOrderDaoEx;

	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;

	@Autowired
	private B2bBindDaoEx b2bBindDaoEx;

	@Autowired
	private LogisticsErpDaoEx logisticsErpDaoEx;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PlatformTransactionManager txManager;

	@Autowired
	private CompanyBankcardService bankcardService;

	@Autowired
	private B2bContractGoodsDaoEx contractGoodsDaoEx;

	@Autowired
	private B2bReserveOrderDaoEx reserveOrderDao;

	@Autowired
	private CmsService cmsService;

	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;

	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;

	/**
	 * 普通商品上架至竞拍
	 */
	@Override
	public RestCode upToAuction(String goodsPks, String auctionPks,
			Date activityTime) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			String[] goods = StringUtils.splitStrs(goodsPks);
			for (String goodsPk : goods) {
				/*
				 * B2bGoodsDtoEx oldGoodsEx =
				 * b2bGoodsService.getDetailsById(goodsPk); if
				 * (null!=auctionPks&&!"".equals(auctionPks)) {
				 * b2bAuctionGoodsService
				 * .addToAuctionGoods(oldGoodsEx,auctionPks
				 * ,activityTime);//添加“商品-场次”对应记录 }
				 */
				B2bGoodsDtoEx oldGoodsEx = b2bGoodsService
						.getDetailsById(goodsPk);
				B2bGoodsEx goodsEx = new B2bGoodsEx();
				goodsEx.setPk(goodsPk);
				goodsEx.setType("auction");
				goodsEx.setIsUpdown(2);
				goodsEx.setUpdateTime(new Date());
				b2bGoodsService.updateGoods(goodsEx);// 普通商品状态更新为竞拍商品
				// 如果修改商品类型，删除购物车数据
				if (!oldGoodsEx.getType().equals("auction")) {
					b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 竞拍中的商品列表
	 */
	@Override
	public PageModelAuctionGoods<B2bAuctionGoodsDtoEx> searchAuctionGoodsList(Map<String, Object> map) {
		PageModelAuctionGoods<B2bAuctionGoodsDtoEx> pm = new PageModelAuctionGoods<B2bAuctionGoodsDtoEx>();
		List<B2bAuctionGoodsDtoEx> list = b2bAuctionGoodsService.searchAuctionGoodsGridNew(map);
		int count = b2bAuctionGoodsService.countAuctionGoodsGridNew(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		map.put("acStatus", 0);// 全部数量
		pm.setAllNum(b2bAuctionGoodsService.countAuctionGoodsGridNew(map));
		map.put("acStatus", 1);// 未上线数量
		pm.setNoOnLineNum(b2bAuctionGoodsService.countAuctionGoodsGridNew(map));
		map.put("acStatus", 2);// 未开始数量
		pm.setNoStartNum(b2bAuctionGoodsService.countAuctionGoodsGridNew(map));
		map.put("acStatus", 3);// 进行中数量
		pm.setUnderwayNum(b2bAuctionGoodsService.countAuctionGoodsGridNew(map));
		map.put("acStatus", 4);// 已结束
		pm.setFinishedNum(b2bAuctionGoodsService.countAuctionGoodsGridNew(map));
		// pm.setCountMap(b2bAuctionGoodsService.searchAuctionGoodsGridNewCount(map));
		return pm;
	}

	/**
	 * 竞拍商品更新为正常商品
	 */
	@Override
	public RestCode downToNormal(String goodsPks, String type) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			if (goodsPks.length() > 0) {
				String[] temps = StringUtils.splitStrs(goodsPks);
				B2bAuctionGoodsDtoEx temp = null;
				for (String goodsPk : temps) {
					temp = b2bAuctionGoodsService
							.checkGoodsAuctionStatus(goodsPk);
					if (null != temp && temp.getAcStatus() == 3) {
						return RestCode.CODE_P010;
					}
				}

				for (String goodsPk : temps) {
					// 1：该商品还未设置场次
					// 2：但未开始
					// 3：进行中
					// 4:已结束
					B2bGoodsDtoEx oldGoodsEx = b2bGoodsService
							.getDetailsById(goodsPk);
					temp = b2bAuctionGoodsService
							.checkGoodsAuctionStatus(goodsPk);
					// 1：该商品还未设置场次
					if (null == temp) {
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(goodsPk);
						goodsEx.setType(type);
						goodsEx.setIsUpdown(3);
						goodsEx.setUpdateTime(new Date());
						// goodsEx.setAuctionStatus(null);
						b2bGoodsService.updateGoods(goodsEx);
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals(type)) {
							b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
						}
					}
					// 2：未开始
					if (null != temp && temp.getAcStatus() == 2) {
						b2bAuctionGoodsService.deleteByPk(temp.getPk());
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(goodsPk);
						goodsEx.setType(type);
						goodsEx.setIsUpdown(3);
						goodsEx.setUpdateTime(new Date());
						// goodsEx.setAuctionStatus(null);
						b2bGoodsService.updateGoods(goodsEx);
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals(type)) {
							b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
						}
					}
					// 4:已结束
					if (null != temp && temp.getAcStatus() == 4) {
						b2bAuctionGoodsService.deleteByPk(temp.getPk());
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(goodsPk);
						goodsEx.setType(type);
						goodsEx.setIsUpdown(3);
						goodsEx.setUpdateTime(new Date());
						// goodsEx.setAuctionStatus(null);
						b2bGoodsService.updateGoods(goodsEx);
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals(type)) {
							b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
						}
					}
				}
			} else {
				return RestCode.CODE_S999;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 设置最低起拍量
	 * 
	 * @param goodsPks
	 *            商品pk
	 * @param minimumBoxes
	 *            最低起拍量
	 * @return
	 */
	@Override
	public RestCode setMinimumBoxes(String goodsPks, int minimumBoxes) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			String[] pksTemp = StringUtils.splitStrs(goodsPks);
			B2bAuctionGoodsDtoEx dto = null;
			B2bGoodsDtoEx goodsDtoEx = null;
			for (String goodsPk : pksTemp) {
				// 未上线，进行中，已结束，不可以修改起拍量
				dto = b2bAuctionGoodsService.checkGoodsAuctionStatus(goodsPk);
				if (null != dto && dto.getAcStatus() == 1) {
					return RestCode.CODE_J002;
				}
				if (null != dto && dto.getAcStatus() == 3) {
					return RestCode.CODE_J001;
				}
				if (null != dto && dto.getAcStatus() == 4) {
					return RestCode.CODE_J003;
				}
				// 判断设置的最低起拍量不能大于库存量
				goodsDtoEx = b2bGoodsService.getDetailsById(goodsPk);
				if (null != goodsDtoEx && null != goodsDtoEx.getPk()) {
					if (minimumBoxes > goodsDtoEx.getTotalBoxes()) {
						return RestCode.CODE_P011;
					}
				}
			}

			for (String goodsPk : pksTemp) {
				dto = b2bAuctionGoodsService.checkGoodsAuctionStatus(goodsPk);
				if (null != dto && null != dto.getPk()) {
					if (null != dto.getAcStatus() && dto.getAcStatus() == 2) {
						dto.setMinimumBoxes(minimumBoxes);
						b2bAuctionGoodsService.setMinimumBoxes(dto);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 设置场次
	 * 
	 * @param goodsPks
	 *            商品pk
	 * @param auctionPk
	 * @param activityTime
	 * @return
	 */
	@Override
	public RestCode setAuction(String goodsPks, String auctionPk,
			Date activityTime) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			if (goodsPks.length() > 0) {
				String[] temps = StringUtils.splitStrs(goodsPks);
				B2bAuctionGoodsDtoEx temp = null;
				B2bGoodsDto tempGoodsDto = null;
				// 验证正在进行中的竞拍不可以修改场次，验证挂牌价，箱数，重量
				for (String goodsPk : temps) {
					tempGoodsDto = goodsDaoEx.getByPk(goodsPk);
					temp = b2bAuctionGoodsService
							.checkGoodsAuctionStatus(goodsPk);
					if (null != temp && temp.getAcStatus() == 3) {
						return RestCode.CODE_J001;
					}
					// 商品价格必须大于0
					if (null != tempGoodsDto
							&& null != tempGoodsDto.getTonPrice()
							&& 0.0 == tempGoodsDto.getTonPrice()) {
						return RestCode.CODE_G015;
					}
					// 箱数必须大于0
					if (null != tempGoodsDto
							&& null != tempGoodsDto.getTotalBoxes()
							&& 0 == tempGoodsDto.getTotalBoxes()) {
						return RestCode.CODE_G021;
					}
					// 重量必须大于0
					if (null != tempGoodsDto
							&& null != tempGoodsDto.getTotalWeight()
							&& 0 == tempGoodsDto.getTotalWeight()) {
						return RestCode.CODE_G022;
					}

				}
				for (String goodsPk : temps) {
					// 1：该商品还未设置场次
					// 2：但未开始
					// 3：进行中
					// 4:已结束
					temp = b2bAuctionGoodsService
							.checkGoodsAuctionStatus(goodsPk);
					B2bGoodsDtoEx oldGoodsEx = b2bGoodsService
							.getDetailsById(goodsPk);
					// 1：该商品还未设置场次
					if (null == temp) {
						b2bAuctionGoodsService.addToAuctionGoods(oldGoodsEx,
								auctionPk, activityTime);// 添加“商品-场次”对应记录
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(goodsPk);
						goodsEx.setType("auction");
						goodsEx.setIsUpdown(2);
						goodsEx.setUpdateTime(new Date());
						b2bGoodsService.updateGoods(goodsEx);
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals("auction")) {
							b2bCartService.delByGoodsPk(goodsPk);
						}

					}
					// 2：未开始
					if (null != temp && temp.getAcStatus() == 2) {
						b2bAuctionGoodsService.deleteByPk(temp.getPk());
						b2bAuctionGoodsService.addToAuctionGoods(oldGoodsEx,
								auctionPk, activityTime);// 添加“商品-场次”对应记录
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(goodsPk);
						goodsEx.setType("auction");
						goodsEx.setIsUpdown(2);
						goodsEx.setUpdateTime(new Date());
						b2bGoodsService.updateGoods(goodsEx);
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals("auction")) {
							b2bCartService.delByGoodsPk(goodsPk);
						}
					}
					// 4:已结束
					if (null != temp && temp.getAcStatus() == 4) {
						b2bAuctionGoodsService.deleteByPk(temp.getPk());// 删除b2b_auction_goods数据
						b2bAuctionOfferService.setOfferOverDate(temp.getPk());// 以前该商品未中标的出价记录设置为已过期
						b2bAuctionGoodsService.addToAuctionGoods(oldGoodsEx,
								auctionPk, activityTime);// 添加“商品-场次”对应记录
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(goodsPk);
						goodsEx.setType("auction");
						goodsEx.setIsUpdown(2);
						goodsEx.setUpdateTime(new Date());
						b2bGoodsService.updateGoods(goodsEx);
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals("auction")) {
							b2bCartService.delByGoodsPk(goodsPk);
						}
					}
				}
			} else {
				return RestCode.CODE_S999;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 代拍 -竞拍中的商品
	 *
	 * @param map
	 * @return
	 */
	@Override
	public PageModelAuctionGoodsByMember<B2bAuctionGoodsDtoEx> auctionGoodsListByMember(
			Map<String, Object> map, String memberPk, String companyPk) {
		PageModelAuctionGoodsByMember<B2bAuctionGoodsDtoEx> pm = new PageModelAuctionGoodsByMember<B2bAuctionGoodsDtoEx>();
		List<B2bAuctionGoodsDtoEx> list = b2bAuctionGoodsService
				.searchAuctionGoodsGridNewByMember(map);
		int count = b2bAuctionGoodsService
				.countAuctionGoodsGridNewByMember(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		// 查询成交价和当前出价
		B2bAuctionOfferDto tempDto = null;
		for (B2bAuctionGoodsDtoEx b2bAuctionGoodsDtoEx : list) {
			// 1：成交价
			if (b2bAuctionGoodsDtoEx.getAcStatus() == 1) {// 进行中，商品成交价显示为上次中标最高价，
															// 如果此商品无中标记录，取起拍价
				List<B2bAuctionGoodsDto> list2 = b2bAuctionGoodsService
						.getAuctionGoodsListByGoodsPk(b2bAuctionGoodsDtoEx
								.getGoodsPk());
				if (null != list2 && list2.size() >= 2) {
					B2bAuctionGoodsDto auctionGoodsDto = list2.get(1);
					B2bAuctionOfferDto offerDto = b2bAuctionOfferService
							.getMaxBidByAuctionPk(auctionGoodsDto.getPk());// 查村某个场次的最高中标价
					if (null != offerDto && null != offerDto.getPk()) {
						b2bAuctionGoodsDtoEx.setDealPrice(offerDto
								.getAuctionPrice());
					} else {
						b2bAuctionGoodsDtoEx.setDealPrice(b2bAuctionGoodsDtoEx
								.getStartingPrice());
					}
				} else {
					b2bAuctionGoodsDtoEx.setDealPrice(b2bAuctionGoodsDtoEx
							.getStartingPrice());
				}
			}
			if (b2bAuctionGoodsDtoEx.getAcStatus() == 2) {// 已结束，成交价为当前的最高出价
				b2bAuctionGoodsDtoEx.setDealPrice(b2bAuctionGoodsDtoEx
						.getMaximumPrice());
			}

			// 2：当前出价（这家公司出的最新价）
			tempDto = b2bAuctionOfferService.getLatestAuctionPriceForCompany(
					b2bAuctionGoodsDtoEx.getPk(), companyPk);
			if (null != tempDto && null != tempDto.getAuctionPrice()) {
				b2bAuctionGoodsDtoEx.setCurrentPrice(tempDto.getAuctionPrice());
				b2bAuctionGoodsDtoEx.setCurrentPriceTime(tempDto
						.getInsertTime());
			}
		}
		map.put("acStatus", 0);// 全部数量
		pm.setAllNum(b2bAuctionGoodsService
				.countAuctionGoodsGridNewByMember(map));
		map.put("acStatus", 1);// 进行中
		pm.setUnderwayNum(b2bAuctionGoodsService
				.countAuctionGoodsGridNewByMember(map));
		map.put("acStatus", 2);// 已结束
		pm.setFinishedNum(b2bAuctionGoodsService
				.countAuctionGoodsGridNewByMember(map));
		return pm;
	}

	/**
	 * 一键加价
	 */
	@Override
	public RestCode auctionOfferOneStep(String pks, String companyPk,
			B2bMemberDto operator, B2bMemberDto saleMan, Double andMore) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			String[] pksTemp = StringUtils.splitStrs(pks);
			B2bAuctionOfferDto tempDto = null;
			// 活动已结束，不可以出价
			for (String pk : pksTemp) {
				if (!b2bAuctionGoodsService.IsAuctionEffective(pk)) {
					return RestCode.CODE_U000;
				}
			}
			for (String pk : pksTemp) {
				// 查看活动是否已结束
				if (b2bAuctionGoodsService.IsAuctionEffective(pk)) {
					B2bAuctionGoodsDto auctionGoodsDto = b2bAuctionGoodsService
							.getById(pk);
					B2bGoodsDto b2bGoodsDto = b2bGoodsService
							.getB2bGoods(auctionGoodsDto.getGoodsPk());
					tempDto = b2bAuctionOfferService
							.getLatestAuctionPriceForCompany(pk, companyPk);
					if (null != tempDto && null != tempDto.getPk()) {
						// 1：以前出过价，在原来出价的基础上加价
						tempDto.setPk(KeyUtils.getUUID());
						tempDto.setAuctionPrice(tempDto.getAuctionPrice()
								+ andMore);// 设置新的竞拍价格为原来的最高竞拍价+一键出价的加价金额
						tempDto.setThanStartingPrice(tempDto.getAuctionPrice()
								- auctionGoodsDto.getStartingPrice());
						tempDto.setMemberPk(operator.getPk());
						tempDto.setEmployeeName(operator.getEmployeeName());
						tempDto.setEmployeeNumber(operator.getEmployeeNumber());
						tempDto.setSaleManPk(saleMan.getPk());
						tempDto.setSaleManContacts(saleMan.getEmployeeName());
						tempDto.setSaleManContactsTel(saleMan.getMobile());
						tempDto.setInsertTime(new Date());
						tempDto.setBidTime(null);
						tempDto.setOrderTime(null);
						tempDto.setOverDateTime(null);
						// 存offer表出价记录
						b2bAuctionOfferService.insert(tempDto);
						// 更新活动场次表的当前最高出价
						updateAuctionGoods(pk);
					} else {
						// 2:以前业务员没有出过价
						if (null != auctionGoodsDto
								&& null != auctionGoodsDto.getPk()) {
							B2bCompanyDto companyDto = b2bCompanyService
									.getCompany(companyPk);
							B2bGoodsDtoEx tempGoods = b2bGoodsService
									.getDetailsById(auctionGoodsDto
											.getGoodsPk());
							B2bAuctionOfferDto offerDto = new B2bAuctionOfferDto();
							offerDto.setPk(KeyUtils.getUUID());
							offerDto.setAuctionPk(pk);
							offerDto.setAuctionPrice(auctionGoodsDto
									.getStartingPrice() + andMore);
							offerDto.setThanStartingPrice(andMore);
							offerDto.setBoxes(auctionGoodsDto.getMinimumBoxes() == null ? 1
									: auctionGoodsDto.getMinimumBoxes());
							if (null != b2bGoodsDto.getBlock()
									&& b2bGoodsDto.getBlock().equals(
											Block.CF.getValue())) {
								offerDto.setWeight(ArithUtil.div(
										(tempGoods.getTareWeight() == null ? 0.0
												: tempGoods.getTareWeight())
												* offerDto.getBoxes(), 1000));
							} else {
								offerDto.setWeight((tempGoods.getTareWeight() == null ? 0.0
										: tempGoods.getTareWeight())
										* offerDto.getBoxes());
							}
							offerDto.setProvideBoxes(0);
							offerDto.setProvideWeight(0d);
							offerDto.setCompanyPk(companyDto.getPk());
							offerDto.setCompanyName(companyDto.getName());
							offerDto.setContacts(companyDto.getContacts());
							offerDto.setContactsTel(companyDto.getContactsTel());
							offerDto.setMemberPk(operator.getPk());
							offerDto.setEmployeeName(operator.getEmployeeName());
							offerDto.setEmployeeNumber(operator
									.getEmployeeNumber());
							offerDto.setSaleManPk(saleMan.getPk());
							offerDto.setSaleManContacts(saleMan
									.getEmployeeName());
							offerDto.setSaleManContactsTel(saleMan.getMobile());
							offerDto.setInsertTime(new Date());
							offerDto.setBidStatus(0);
							offerDto.setType(2);
							// 存offer表出价记录
							b2bAuctionOfferService.insert(offerDto);
							// 更新活动场次表的当前最高出价
							updateAuctionGoods(pk);

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	private void updateAuctionGoods(String auctionPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auctionPk", auctionPk);
		B2bAuctionOfferDto offerDto = b2bAuctionOfferService
				.searchMaxPrice(map);
		B2bAuctionGoodsDto goodsDto = b2bAuctionGoodsService.getById(auctionPk);
		if (null != goodsDto) {
			B2bAuctionGoods auctionGoods = new B2bAuctionGoods();
			auctionGoods.setPk(goodsDto.getPk());
			if (null != offerDto) {
				auctionGoods.setMaximumPrice(offerDto.getAuctionPrice());
			} else {
				auctionGoods.setMaximumPrice(goodsDto.getStartingPrice());
			}
			b2bAuctionGoodsService.updateEx(auctionGoods);
		}
	}

	/**
	 * 某个场次-某个业务员的出价记录
	 */
	@Override
	public PageModel<B2bAuctionOfferDtoEx> auctionOfferRecordsByMember(
			Map<String, Object> map) {
		PageModel<B2bAuctionOfferDtoEx> pm = new PageModel<B2bAuctionOfferDtoEx>();
		List<B2bAuctionOfferDtoEx> list = b2bAuctionOfferService
				.searchAuctionOfferRecordsByMember(map);
		int count = b2bAuctionOfferService
				.countAuctionOfferRecordsByMember(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	/**
	 * 操作人出价
	 */
	@Override
	public RestCode auctionOffer(String pk, Integer boxes, Double tareWeight,
			Double auctionPrice, B2bMemberDto operator, B2bMemberDto saleman,
			String companyPk) {
		RestCode restCode = RestCode.CODE_0000;
		// 查看活动是否已结束
		if (b2bAuctionGoodsService.IsAuctionEffective(pk)) {
			// 1：检查是否低于最低起拍量
			B2bAuctionGoodsDto auctionGoodsDto = b2bAuctionGoodsService
					.getById(pk);
			B2bGoodsDto goodsDto = b2bGoodsService
					.getDetailsById(auctionGoodsDto.getGoodsPk());
			if (boxes < auctionGoodsDto.getMinimumBoxes()) {
				return RestCode.CODE_P005;
			}
			// 2：检查出价是否大于当前商品起拍价
			if (auctionPrice < auctionGoodsDto.getStartingPrice()) {
				return RestCode.CODE_P007;
			}
			// 3：出价的采购量是否大于库存量
			if (null != goodsDto && null != goodsDto.getPk()) {
				if (boxes > goodsDto.getTotalBoxes()) {
					return RestCode.CODE_P009;
				}
			}
			B2bGoodsDto b2bGoodsDto = b2bGoodsService
					.getB2bGoods(auctionGoodsDto.getGoodsPk());
			B2bCompanyDto companyDto = b2bCompanyService.getCompany(companyPk);
			B2bAuctionOfferDto dto = new B2bAuctionOfferDto();
			dto.setPk(KeyUtils.getUUID());
			dto.setAuctionPk(pk);
			dto.setAuctionPrice(auctionPrice);
			dto.setThanStartingPrice(auctionPrice
					- auctionGoodsDto.getStartingPrice());
			dto.setBoxes(boxes);
			if (null != b2bGoodsDto.getBlock()
					&& b2bGoodsDto.getBlock().equals(Block.CF.getValue())) {
				dto.setWeight(ArithUtil.div(ArithUtil.mul(tareWeight, boxes),
						1000));
			} else {
				dto.setWeight(ArithUtil.mul(tareWeight, boxes));
			}
			dto.setProvideBoxes(0);
			dto.setProvideWeight(0d);
			dto.setCompanyPk(companyDto.getPk());
			dto.setCompanyName(companyDto.getName());
			dto.setContacts(companyDto.getContacts());
			dto.setContactsTel(companyDto.getContactsTel());
			dto.setMemberPk(operator.getPk());
			dto.setEmployeeName(operator.getEmployeeName());
			dto.setEmployeeNumber(operator.getEmployeeNumber());
			dto.setSaleManPk(saleman.getPk());
			dto.setSaleManContacts(saleman.getEmployeeName());
			dto.setSaleManContactsTel(saleman.getMobile());
			dto.setInsertTime(new Date());
			dto.setBidStatus(0);
			dto.setType(2);
			// 存offer表出价记录
			b2bAuctionOfferService.insert(dto);
			// 更新活动场次表的当前最高出价
			updateAuctionGoods(pk);
		} else {
			restCode = RestCode.CODE_U000;
		}
		return restCode;
	}

	@Override
	public PageModel<B2bOrderDtoEx> searchPuOrderList(Map<String, Object> map,
			B2bCompanyDto company) {
		// 总公司
		if ("-1".equals(company.getParentPk())) {
			String[] purchaserPks = companyPks(company);
			if (purchaserPks.length == 0) {
				map.put("purchaserPks", new String[] { "null" });
			} else {
				map.put("purchaserPks", purchaserPks);
			}
			// 子公司
		} else {
			map.put("purchaserPk", company.getPk());
		}
		// 采购商删除状态为未删除
		map.put("isDelete", 1);
		return b2bOrderService.searchOrderList(map);
	}

	private String[] companyPks(B2bCompanyDto company) {
		List<B2bCompanyDtoEx> list = b2bCompanyService.searchCompanyList(1,
				company.getPk(), 1, null);
		String[] companyPks = new String[list.size()];
		for (int i = 0; i < companyPks.length; i++) {

			companyPks[i] = list.get(i).getPk();
		}
		return companyPks;
	}

	@Override
	public PageModel<B2bOrderDtoEx> searchSpOrderList(Map<String, Object> map,
			B2bStoreDto store, B2bCompanyDto company) {
		// 总公司查询店铺所有订单 子公司查询当前公司的订单
		if ("-1".equals(company.getParentPk())) {
			map.put("storePk", null == store ? "" : store.getPk());
		} else {
			map.put("supplierPk", company.getPk());
		}
		// 供应商删除状态为未删除
		map.put("isDeleteSp", 1);
		return b2bOrderService.searchOrderList(map);
	}

	@Override
	public PageModel<B2bOrderDtoEx> searchEmOrderList(Map<String, Object> map,
			Sessions session) {
		// 总公司查询店铺所有订单 子公司查询当前公司的订单
		if ("-1".equals(session.getCompanyDto().getParentPk())) {
			map.put("storePk", null == session.getStoreDto() ? "" : session
					.getStoreDto().getPk());
		} else {
			map.put("supplierPk", session.getCompanyDto().getPk());
		}
		// 超级管理员查看所有,业务员查看自己的订单,业务助理查看对应业务员的订单
		if (session.getIsAdmin() != 1) {
			if ("-1".equals(session.getMemberDto().getParentPk())) {
				map.put("employeePk", session.getMemberDto().getPk());
			} else {
				String[] employeePks = commonService.getEmployeePks(session.getMemberDto().getPk());
				if(employeePks.length>0){
					map.put("employeePks", employeePks);
				//没匹配到业务员不给查询
				}else{
					map.put("employeePk", session.getMemberDto().getPk());
				}
			}
		}
		// 供应商删除状态为未删除
		map.put("isDeleteSp", 1);
		return b2bOrderService.searchOrderList(map);
	}

	@Override
	public Map<String, Object> searchOrderCounts(Map<String, Object> map,
			Sessions session) {
		searchType(map, session.getCompanyDto(), session.getStoreDto(),
				session.getMemberDto(), session.getIsAdmin());
		return b2bOrderService.searchOrderCounts(map);
	}

	/**
	 * 根据searchType组合订单查询固定条件
	 * 
	 * @param map
	 * @param company
	 * @param memberDto
	 * @param isAdmin
	 */
	private void searchType(Map<String, Object> map, B2bCompanyDto company,
			B2bStoreDto store, B2bMemberDto memberDto, Integer isAdmin) {
		switch (map.get("searchType").toString()) {
		case "1":
			// 总公司
			if ("-1".equals(company.getParentPk())) {
				String[] purchaserPks = companyPks(company);
				if (purchaserPks.length == 0) {
					map.put("purchaserPks", new String[] { "null" });
				} else {
					map.put("purchaserPks", purchaserPks);
				}
				// 子公司
			} else {
				map.put("purchaserPk", company.getPk());
			}
			// 采购商删除状态为未删除
			map.put("isDelete", 1);
			break;
		case "2":
			// 总公司查询店铺所有订单 子公司查询当前公司的订单
			if ("-1".equals(company.getParentPk())) {
				map.put("storePk", null == store ? "" : store.getPk());
			} else {
				map.put("supplierPk", company.getPk());
			}
			// 供应商删除状态为未删除
			map.put("isDeleteSp", 1);
			break;
		case "3":
			// 总公司查询店铺所有订单 子公司查询当前公司的订单
			if ("-1".equals(company.getParentPk())) {
				map.put("storePk", null == store ? "" : store.getPk());
			} else {
				map.put("supplierPk", company.getPk());
			}
			// 超级管理员查看所有,业务员查看自己的订单,业务助理查看对应业务员的订单
			if (isAdmin != 1) {
				String[] employeePks = commonService.getEmployeePks(memberDto.getPk());
				if(employeePks.length>0){
					map.put("employeePks", employeePks);
				//没匹配到业务员不给查询
				}else{
					map.put("employeePk", memberDto.getPk());
				}
			}
			// 供应商删除状态为未删除
			map.put("isDeleteSp", 1);
			break;
		default:
			break;
		}
	}

	@Override
	public CallBackOrder submitOrder(String orders, B2bCompanyDto c,
			B2bMemberDto m, int source) {
		RestCode code = RestCode.CODE_0000;
		CallBackOrder callBack = new CallBackOrder();
		BatchOrder bo = new BatchOrder();
		Porder o = null;
		// 使用编程式事务处理事务回滚
		TransactionStatus status = txManager
				.getTransaction(new DefaultTransactionDefinition());
		try {
			if (null == c) {
				code = RestCode.CODE_M008;
			} else {
				o = new Porder(orders, c, m);
				o.setSource(source);
				code = prepareOrder(c, o);// 下单前准备
				if ("0000".equals(code.getCode())) {
					BackPgoods bp = getPgoods(o);
					code = bp.getRestCode();
					if ("0000".equals(code.getCode())) {
						Map<String, List<Pgoods>> map = this.diffServ(bp
								.getPgList());
						if ("0000".equals(code.getCode())
								&& map.containsKey("bind")) {// 拼团需求单
							code = submitBindOrder(o, map.get("bind"), bo);
						}
						if ("0000".equals(code.getCode())
								&& map.containsKey("other")) {// 订单
							code = submitOtherOrder(o, map.get("other"), bo);
						}
						if ("0000".equals(code.getCode())) {
							code = this.submitBatch(bo, o, map);// 统一提交订单
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorSubmitOrder", e);
			code = RestCode.CODE_S999;
			// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} finally {
			if (null != o.getCarts() && o.getCarts().size() > 0) {
				for (Pgoods pg : o.getCarts()) {
					if (null != pg.getGoodsPk() && !"".equals(pg.getGoodsPk())) {
						JedisUtils.releaseLock(pg.getGoodsPk());
					}
				}
			}
			// 事务提交
			if ("0000".equals(code.getCode())) {
				txManager.commit(status);
				// 事务回滚
			} else {
				txManager.rollback(status);
			}
			callBack.setCode(code);
			callBack.setBorder(bo);
			callBack.setPorder(o);
		}
		return callBack;
	}

	@Async
	@Override
	public void afterOrder(BatchOrder bo, Porder o) {
		// TODO Auto-generated method stub
		afterBindOrder(bo.getBolist(), o.getMember(), o.getOfferPk());
		afterOtherOrder(bo.getOlist(), o.getMember(), o.getOfferPk());
		if(o.getPurchaseType()==1){
			commonService.DuplicatedGoods(bo.getOlist());
		}
		
	}

	private RestCode submitBatch(BatchOrder bo, Porder o,
			Map<String, List<Pgoods>> map) {
		RestCode code = RestCode.CODE_0000;
		List<B2bOrderGoods> oglist = bo.getOglist();
		List<B2bOrder> olist = bo.getOlist();
		if (null != olist && olist.size() > 0 && null != oglist
				&& oglist.size() > 0) {
			this.orderGoodsDaoExt.insertBatch(oglist);
			this.orderDaoExt.insertBatch(olist);
			if (map.containsKey("other")) {// 订单
				List<UpdateGoods> glist = new ArrayList<UpdateGoods>();
				for (Pgoods pg : map.get("other")) {
					if (!(pg.getGdto().getType()).equals(GoodsType.PRESALE
							.getValue())
							|| (null != o.getOfferPk() && !"".equals(o
									.getOfferPk()))) {
						Double endWeight = pg.getWeight();
						if (null != pg.getWeight() && pg.getWeight() > 0d) {
							endWeight = pg.getWeight();
						} else {
							if (pg.getGdto().getBlock()
									.equals(Block.CF.getValue())) {
								endWeight = ArithUtil.round(ArithUtil.div(
										ArithUtil.mul(pg.getBoxes(), pg
												.getGdto().getTareWeight()),
										1000), 5);
							} else if (pg.getGdto().getBlock()
									.equals(Block.SX.getValue())) {
								endWeight = ArithUtil.round(ArithUtil.mul(pg
										.getBoxes(), pg.getGdto()
										.getTareWeight()), 5);
							} else {
								endWeight = ArithUtil.round(ArithUtil.div(
										ArithUtil.mul(pg.getBoxes(), pg
												.getGdto().getTareWeight()),
										1000), 5);
							}
						}

						glist.add(new UpdateGoods(pg.getGoodsPk(), pg
								.getBoxes(), endWeight));
					}
				}
				// 修改库存
				if (glist.size() > 0) {
					this.goodsDaoEx.updateBatch(glist);
				}
			}
			/*
			 * try { // 新增物流承运商（子订单） b2bOrderService.addCarrier(oglist, olist);
			 * } catch (Exception e) { logger.error("匹配承运商" + e);
			 * e.printStackTrace(); }
			 */
		}

		List<B2bBindOrder> bolist = bo.getBolist();
		if (null != bolist && bolist.size() > 0) {
			if (null != o.getBindPk() && !"".equals(o.getBindPk())) {
				b2bBindOrderDaoEx.updateStatus(o.getBindPk());// 一键成团的，将原先的需求单变更未已取消
			}
			b2bBindOrderDaoEx.insertBatch(bolist);
			if (map.containsKey("bind")) {// 拼团需求单
				List<UpdateGoods> glist = new ArrayList<UpdateGoods>();
				for (Pgoods pg : map.get("bind")) {
					glist.add(new UpdateGoods(pg.getBindGood().getPk(), pg
							.getBoxes(), ArithUtil.div(ArithUtil.mul(
							pg.getBoxes(), pg.getGdto().getTareWeight()), 1000)));
				}
				// 修改库存
				if (glist.size() > 0) {
					this.b2bBindGoodsDaoEx.updateBatch(glist);
				}
			}
		}
		// 删除购物车
		if (null != o.getCartList() && o.getCartList().size() > 0) {
			this.cartDaoEx.deleteBatch(o.getCartList());
		}
		return code;
	}

	private Map<String, List<Pgoods>> diffServ(List<Pgoods> pgList) {
		Map<String, List<Pgoods>> map = new HashMap<String, List<Pgoods>>();
		for (Pgoods pg : pgList) {
			if (null != pg.getBindPk() && !"".equals(pg.getBindPk())) {
				if (!map.containsKey("bind")) {
					map.put("bind", new ArrayList<Pgoods>());
				}
				map.get("bind").add(pg);
			} else {
				if (!map.containsKey("other")) {
					map.put("other", new ArrayList<Pgoods>());
				}
				map.get("other").add(pg);
			}
		}
		return map;
	}

	private void afterBindOrder(List<B2bBindOrder> oList, B2bMemberDto member,
			String offerPk) {
		// 订单提交成功后回调
		if (null != oList && oList.size() > 0) {
			for (B2bBindOrder bo : oList) {
				b2bBindOrderService.checkBindBoxes(bo.getBindPk());
				// backList.add(new BackOrder(bo));// 返回参数
				this.sendSms(bo.getOrderNumber(), bo.getPurchaserPk(), null,
						member);// 发送短信
				this.saveCustomer(bo.getPurchaserPk(), bo.getStorePk());// 保存客户关系
			}
		}
	}

	private void afterOtherOrder(List<B2bOrder> oList, B2bMemberDto member,
			String offerPk) {
		// 订单提交成功后回调
		if (null != oList && oList.size() > 0) {
			for (B2bOrder bo : oList) {
				this.sendErp(bo.getOrderNumber(), bo.getStorePk(), 1);// 发送给erp
				// backList.add(new BackOrder(bo));// 返回参数
				this.saveOrderRecord(member, bo);// 保存订单记录
				this.sendSms(bo.getOrderNumber(), bo.getPurchaserPk(),
						bo.getSupplierPk(), member);// 发送短信
				this.saveCustomer(bo.getPurchaserPk(), bo.getStorePk());// 保存客户关系
				this.sendCms(bo.getOrderNumber(), bo.getBlock(),1);
			}
			this.updateAuction(offerPk);
		}
	}

	private RestCode submitBindOrder(Porder o, List<Pgoods> list, BatchOrder bo) {
		RestCode code = RestCode.CODE_0000;
		Map<String, List<Pgoods>> map = new HashMap<String, List<Pgoods>>();// 团购分组
		String key = null;
		for (Pgoods good : list) {
			key = o.getPurchaserPk() + "," + good.getBindPk();
			if (!map.containsKey(key)) {// 采购商+团购ID
				map.put(key, new ArrayList<Pgoods>());
			}
			map.get(key).add(good);
		}
		code = this.bindOrderService.submitBindOrder(o, map, bo);
		return code;
	}

	private RestCode submitOtherOrder(Porder o, List<Pgoods> pgs, BatchOrder bo) {
		Map<String, List<Pgoods>> map = new HashMap<String, List<Pgoods>>();
		RestCode code = RestCode.CODE_0000;
		try {
			for (Pgoods pg : pgs) {
				if (!map.containsKey(pg.getCompanyPk())) {
					map.put(pg.getCompanyPk(), new ArrayList<Pgoods>());
				}
				map.get(pg.getCompanyPk()).add(pg);
			}
			// 提交订单
			code = b2bOrderService.submitOrder(o, map, bo);
		} catch (Exception e) {
			logger.error("errorSubmitOrder", e);
			code = RestCode.CODE_S999;
		}
		return code;
	}

	private void saveOrderRecord(B2bMemberDto member, B2bOrder bo) {
		try {
			// 存订单日志
			b2bOrderRecordService.insertOrderRecord(member, bo,
					OrderRecordType.INSERT.toString(),
					"订单号:" + bo.getOrderNumber());
		} catch (Exception e) {
			logger.error("saveOrderRecord", e);
		}
	}

	@Override
	public void sendErp(String orderNumber, String storePk, Integer type) {
		B2bTokenDto token = b2bTokenService.getByStorePk(storePk);
		if (null != token) {
			if (type == 1) {
				OrderSyncToMongo syncToMongo = new OrderSyncToMongo();
				syncToMongo.setStorePk(storePk);
				syncToMongo.setId(orderNumber);
				syncToMongo.setIsPush(1);
				syncToMongo.setInsertTime(DateUtil
						.formatYearMonthDay(new Date()));
				syncToMongo.setOrderNumber(orderNumber);
				syncToMongo.setDetail("");
				mongoTemplate.save(syncToMongo);
			} else {
				ContractSyncToMongo syncToMongo = new ContractSyncToMongo();
				syncToMongo.setId(orderNumber);
				syncToMongo.setIsPush(1);//
				syncToMongo.setStorePk(storePk);
				syncToMongo.setInsertTime(DateUtil
						.formatYearMonthDay(new Date()));
				syncToMongo.setContractNumber(orderNumber);
				syncToMongo.setDetail("");
				mongoTemplate.save(syncToMongo);
			}
		}
	}

	private void sendSms(String orderNumber, String purchaserPk,
			String supplierPk, B2bMemberDto member) {
		Map<String, String> smsMap = new HashMap<String, String>();
		smsMap.put("order_id", orderNumber);
		try {
			// 发送短信给采购商
			smsService.sendMessage(member.getPk(), purchaserPk, false,
					SmsCode.ORDER_AD_PU.getValue(), smsMap);
		} catch (Exception e) {
			logger.error("errorPurchaserSms", e);
		}
		try {
			// 发送短信给供应商
			smsService.sendMessage(member.getPk(), supplierPk, true,
					SmsCode.ORDER_AD_SP.getValue(), smsMap);
		} catch (Exception e) {
			logger.error("errorSupplierSms", e);
		}

	}

	private void saveCustomer(String purchaserPk, String storePk) {
		// 录入客户信息
		try {
			b2bCustomerSalesmanService.addCustomerManagement(purchaserPk,
					storePk);
		} catch (Exception e) {
			logger.error("addCustomerManagement", e);
		}

	}

	private RestCode prepareOrder(B2bCompanyDto c, Porder o) {
		RestCode code = RestCode.CODE_0000;
		// 判断发票
		if (null != o.getInvoicePk() && !"".equals(o.getInvoicePk())) {
			B2bCompanyDtoEx getInvoice = b2bCompanyService.getCompany(o
					.getInvoicePk());
			o.setInvoice(getInvoice);
		}
		// 1自采 2代采
		if (!"".equals(o.getPurchaserPk()) && !"".equals(o.getPurchaserName())) {
			if (o.getMember().getCompanyPk().equals(o.getPurchaserPk())) {
				o.setPurchaseType(1);
			} else {
				c.setPk(o.getPurchaserPk());
				c.setName(o.getPurchaserName());
				B2bCompanyDtoEx purchaser = b2bCompanyService.getCompany(o
						.getPurchaserPk());
				if (null != purchaser) {
					c.setOrganizationCode(purchaser.getOrganizationCode());
					c.setContactsTel(purchaser.getContactsTel());
				}
				o.setPurchaseType(2);
			}
		} else {
			o.setPurchaseType(1);
		}
		if (null != o.getOfferPk() && !"".equals(o.getOfferPk())) {
			B2bAuctionOfferDto offerDto = b2bAuctionOfferService
					.getAuctionOffer(o.getOfferPk());
			if (null != offerDto && offerDto.getIsFinished() == 1) {
				return RestCode.CODE_P026;
			}
		}
		// 判断物流方式
		LogisticsModelDto lmdto = logisticsService.getLogisticModel(o
				.getLogisticsModelPk());
		if (null == lmdto) {
			return RestCode.CODE_L001;
		} else {
			o.setLmdto(lmdto);
		}
		// 判断收货地址
		if (lmdto.getType() == 1 || lmdto.getType() == 2) {
			B2bAddressDto a = addressService.getAddress(o.getAddressPk());
			if (null == a) {
				return RestCode.CODE_D002;
			} else {
				o.setAdto(a);
			}
		}
		if (null != o.getOfferPk() && !"".equals(o.getOfferPk())) {
			o.setOrderType(3);// 竞拍订单
		} else {
			o.setOrderType(1);// 正常订单
		}
		return code;
	}

	private void updateAuction(String offerPk) {
		try {
			// 竞拍订单修改出价记录
			if (null != offerPk) {
				B2bAuctionOffer offer = new B2bAuctionOffer();
				offer.setPk(offerPk);
				offer.setIsFinished(1);
				offer.setOrderTime(new Date());
				b2bAuctionOfferService.updateAuctionOffer(offer);
			}
		} catch (Exception e) {
			logger.error("errorupdateAuctionOffer", e);
		}

	}

	/**
	 * 验证订单商品
	 * 
	 * @param goods
	 * @return
	 */
	private BackGoods checkGoods(Pgoods goods, Integer purchaserType) {
		B2bGoodsDto gdto = null;
		BackGoods bg = new BackGoods(RestCode.CODE_0000);
		if (goods.getIncreasePrice() < 0) {
			bg.setRestCode(RestCode.CODE_G015);
		}
		if (null == goods.getBoxes() || goods.getBoxes() <= 0) {
			bg.setRestCode(RestCode.CODE_G009);
		} else {
			// 代采不判断店铺营业状态
			if (null != purchaserType && purchaserType == 2) {
				gdto = b2bGoodsService.getB2bGoods(goods.getGoodsPk());
				// 店铺是否营业
			} else {
				gdto = b2bGoodsService.getOpenDetails(goods.getGoodsPk());
			}
			if (null == gdto) {
				bg.setRestCode(RestCode.CODE_C000);
			} else {
				// 商品未上架
				if (2 != gdto.getIsUpdown()) {
					bg.setRestCode(RestCode.CODE_G003);
				}
				// 商品价格不能为'0';
				if ("0000".equals(bg.getRestCode().getCode())
						&& (null == gdto.getSalePrice() || gdto.getSalePrice() <= 0)) {
					bg.setRestCode(RestCode.CODE_G000);
				}
				// 商品箱重不能为'0'
				if ("0000".equals(bg.getRestCode().getCode())
						&& (null == gdto.getTareWeight() || gdto
								.getTareWeight() <= 0)) {
					bg.setRestCode(RestCode.CODE_G007);
				}
				// 预售的商品不判断库存
				if ("0000".equals(bg.getRestCode().getCode())
						&& !GoodsType.PRESALE.getValue().equals(gdto.getType())) {
					// 如果不传商品重量则自行计算
					Double needWeight = 0d;
					if (null != goods.getWeight() && goods.getWeight() > 0d) {
						needWeight = goods.getWeight();
					} else {
						needWeight = Block.CF.getValue()
								.equals(gdto.getBlock()) ? ArithUtil
								.round(ArithUtil.div(
										ArithUtil.mul(goods.getBoxes(),
												gdto.getTareWeight()), 1000), 5)
								: ArithUtil.round(
										ArithUtil.mul(goods.getBoxes(),
												gdto.getTareWeight()), 5);
					}
					if (needWeight > gdto.getTotalWeight()
							|| goods.getBoxes() > gdto.getTotalBoxes()) {
						// 商品库存不足
						bg.setRestCode(RestCode.CODE_G004);
					}
				}
				// 增加商品锁
				if ("0000".equals(bg.getRestCode().getCode())) {
					Object goodPkLock = JedisUtils.get(gdto.getPk());
					if (goodPkLock instanceof Boolean) {
						JedisUtils.lock(gdto.getPk());
					} else {
						logger.error("------商品pk:" + gdto.getPk()
								+ "---受并发影响--");
						bg.setRestCode(RestCode.CODE_G008);
						// 库存异常
					}
				}

			}
		}
		if ("0000".equals(bg.getRestCode().getCode())) {
			B2bGoodsDtoMa gdm = new B2bGoodsDtoMa();
			gdm.UpdateMine(gdto);
			bg.setGdto(gdm);
		}
		return bg;
	}

	/**
	 * 验证合同订单商品
	 * 
	 * @param goods
	 * @return
	 */
	private Map<String, Object> checkCGoods(Cgoods goods) {
		boolean flag = true;
		B2bGoodsDto gdto = null;
		RestCode code = RestCode.CODE_0000;
		Map<String, Object> map = new HashMap<String, Object>();
		// 店铺是否营业
		if (flag) {
			gdto = b2bGoodsService.getOpenDetails(goods.getGoodsPk());
		}
		if (flag && null == gdto) {
			code = RestCode.CODE_C000;
			flag = false;
		}
		// 商品未上架
		if (flag && 2 != gdto.getIsUpdown()) {
			code = RestCode.CODE_G003;
			flag = false;
		}
		// 商品价格不能为'0';
		if (flag && (null == gdto.getSalePrice() || gdto.getSalePrice() <= 0)) {
			flag = false;
			code = RestCode.CODE_G000;
		}
		// 商品箱重不能为'0'
		if (flag && (null == gdto.getTareWeight() || gdto.getTareWeight() <= 0)) {
			flag = false;
			code = RestCode.CODE_G007;
		}
		map.put("code", code.getCode());
		if (RestCode.CODE_0000.getCode().equals(code.getCode())) {
			B2bGoodsDtoMa bm = new B2bGoodsDtoMa();
			bm.UpdateMine(gdto);
			map.put("data", bm);
		} else {
			map.put("data", code.toJson());
		}
		return map;
	}

	public BackPgoods getPgoods(Porder o) {
		B2bCartDto cdto = null;
		BackPgoods bp = new BackPgoods(RestCode.CODE_0000);
		B2bGoodsDtoMa gdto = null;
		List<String> cartList = new ArrayList<String>();
		List<Pgoods> pgList = new ArrayList<Pgoods>();
		if (null == o.getCarts() || o.getCarts().size() == 0) {
			bp.setRestCode(RestCode.CODE_G020);
			return bp;
		}
		for (Pgoods pg : o.getCarts()) {
			if (null != pg.getCartPk() && !"".equals(pg.getCartPk())) {
				cdto = b2bCartService.getCart(pg.getCartPk());
				if (null == cdto) {
					bp.setRestCode(RestCode.CODE_G020);
					return bp;
				}
				cartList.add(pg.getCartPk());
				pg.setBoxes(cdto.getBoxes());
				pg.setGoodsPk(cdto.getGoodsPk());
				pg.setTotalBoxes(cdto.getBoxes());
				pg.setBindPk(cdto.getBindPk());
				if (null == cdto.getWeight() || cdto.getWeight() == 0d) {
					if (null != gdto && null != gdto.getTareWeight()
							&& gdto.getTareWeight() > 0d) {
						pg.setWeight(Block.CF.getValue()
								.equals(gdto.getBlock()) ? ArithUtil.div(
								cdto.getBoxes() * gdto.getTareWeight(), 1000)
								: cdto.getBoxes() * gdto.getTareWeight());
					} else {
						pg.setWeight(0d);
					}
				} else {
					pg.setWeight(cdto.getWeight());
				}
			}
			BackGoods bg = checkGoods(pg, o.getPurchaseType());
			if (!"0000".equals(bg.getRestCode().getCode())) {
				bp.setRestCode(bg.getRestCode());
				break;
			} else {
				gdto = bg.getGdto();
			}
			// 拼团 竞拍不给直接提交订单
			if (GoodsType.BINDING.getValue().equals(bg.getGdto().getType())
					&& (null == pg.getBindPk() || "".equals(pg.getBindPk()))) {
				bp.setRestCode(RestCode.CODE_G030);
				return bp;
			}
			if (GoodsType.AUCTION.getValue().equals(bg.getGdto().getType())
					&& (null == o.getOfferPk() || "".equals(o.getOfferPk()))) {
				bp.setRestCode(RestCode.CODE_G030);
				return bp;
			}
			if (null != pg.getBindPk() && !"".equals(pg.getBindPk())) {
				B2bBindGoodsDtoEx bindgood = b2bBindGoodsService
						.getByGoodPkAndBindPk(pg.getGoodsPk(), pg.getBindPk());
				if (null == bindgood) {
					bp.setRestCode(RestCode.CODE_G003);
					return bp;
				} else if (null != bindgood && bindgood.getBisUpDown() == 2) {
					bp.setRestCode(RestCode.CODE_C018);
					return bp;
				}
				pg.setBindGood(bindgood);
			}			
			pg.setGdto(gdto);
			// 调用物流系统提供的运费
			if (o.getLmdto().getType() == 1 || o.getLmdto().getType() == 2) {
				pg = this.setLogisticsPrice(o, gdto, pg);
			}
			if(o.getLmdto().getType() == 2 && pg.getFreight()<=0d){
				bp.setRestCode(RestCode.CODE_L004);
				return bp;
			}
			pgList.add(pg);
		}
		bp.setPgList(pgList);
		return bp;
	}

	private Pgoods setLogisticsPrice(Porder o, B2bGoodsDtoMa gdto, Pgoods pg) {
		Double tonWeight = 0d;
		if (pg.getWeight() == 0d) {
			// tonWeight = ArithUtil.div(
			// ArithUtil.mul(pg.getBoxes(), gdto.getTareWeight()), 1000);
			tonWeight = Block.CF.getValue().equals(gdto.getBlock()) ? ArithUtil
					.div(ArithUtil.mul(pg.getBoxes(), gdto.getTareWeight()),
							1000) : ArithUtil.mul(pg.getBoxes(),
					gdto.getTareWeight());
		} else {
			// tonWeight = Block.CF.getValue().equals(gdto.getBlock()) ? pg
			// .getWeight() : ArithUtil.div(pg.getWeight(), 1000);
			tonWeight = pg.getWeight();
		}
		LogisticsCart cart = logisticsService.getLogisticsErpPriceByGoods(
				o.getLogisticsModelPk(), o.getAddressPk(), pg.getGoodsPk(),
				pg.getBoxes(), tonWeight, o.getCompany().getPk(), 2);
		if (null != cart) {
			pg.setLogisticsPk(cart.getLogisticsPk());
			pg.setLogisticsStepInfoPk(cart.getLogisticsStepInfoPk());
		} else {
			pg.setLogisticsPk("");
			pg.setLogisticsStepInfoPk("");
		}
		if (o.getLmdto().getType() == 1) {
			// 物流系统返回总运费
			if (null != pg.getLogisticsPk() && !"".equals(pg.getLogisticsPk())
					&& !"-1".equals(pg.getLogisticsPk())) {
				ForB2BLgPriceDto lgPriceDto = commonService
						.searchLgPriceForB2BByLinePk(pg.getLogisticsPk(),
								pg.getLogisticsStepInfoPk());
				pg.setFreight(lgPriceDto.getPrice() == null ? 0d : lgPriceDto
						.getPrice());
				if (null != lgPriceDto.getLowPrice()
						&& lgPriceDto.getLowPrice() == 0.0) {
					pg.setTotalFreight(ArithUtil.mul(lgPriceDto.getPrice(),
							tonWeight));
				} else {
					pg.setTotalFreight(lgPriceDto.getLowPrice() == null ? 0d
							: lgPriceDto.getLowPrice());
				}
				pg.setLogisticsCarrierPk(lgPriceDto.getLogisticsCarrierPk() == null ? ""
						: lgPriceDto.getLogisticsCarrierPk());
				pg.setLogisticsCarrierName(lgPriceDto.getLogisticsCarrierName() == null ? ""
						: lgPriceDto.getLogisticsCarrierName());
			} else {
				pg.setTotalFreight(0d);
				pg.setFreight(0d);
			}
		}
		// 商家承运(调用商城运费模板)
		if (o.getLmdto().getType() == 2 && null != pg.getLogisticsPk()
				&& !"".equals(pg.getLogisticsPk())) {
			Map<String, Object> logisticsMap = logisticsService
					.searchLogisticsErp(pg.getLogisticsPk(),
							pg.getLogisticsStepInfoPk(), gdto, tonWeight);
			// 物流模板
			if (null != logisticsMap) {
				pg.setLogisticsCarrierPk(null == logisticsMap
						.get("lgCompanyPk") ? "" : logisticsMap.get(
						"lgCompanyPk").toString());// 承运商pk
				pg.setLogisticsCarrierName(null == logisticsMap
						.get("lgCompanyName") ? "" : logisticsMap.get(
						"lgCompanyName").toString());// 承运商名称
				pg.setFreight(null == logisticsMap.get("price") ? 0d : Double
						.parseDouble(logisticsMap.get("price").toString()));
				// 没有阶梯运价则取最低起运价
				if ((null == pg.getFreight() || 0d == pg.getFreight())) {
					pg.setTotalFreight(null == logisticsMap.get("lowPrice") ? 0d
							: Double.parseDouble(logisticsMap.get("lowPrice")
									.toString()));
				}
			}
		}
		return pg;
	}

	@Override
	public String payOrder(B2bMemberDto member, String orderNumber,
			Integer paymentType, String paymentName) {
		RestCode code = RestCode.CODE_0000;
		Boolean flag = true;
		B2bOrderDtoEx order = b2bOrderService.getOrder(orderNumber);
		if (null == order) {
			code = RestCode.CODE_O001;
			flag = false;
		}
		// 订单已取消
		if (flag && -1 == order.getOrderStatus()) {
			code = RestCode.CODE_O010;
			flag = false;
		}
		// 订单已支付
		if (flag && null != order.getOrderStatus()
				&& order.getOrderStatus() >= 2) {
			code = RestCode.CODE_O000;
			flag = false;
		}
		if (flag) {
			B2bOrder o = new B2bOrder();
			o.setPaymentType(paymentType);
			o.setPaymentName(paymentName);
			o.setPaymentTime(new Date());
			o.setOrderNumber(orderNumber);
			o.setOrderStatus(2);
			o.setOwnAmount(order.getActualAmount());
			SysCompanyBankcardDto card = bankcardService
					.getCompanyBankCard(order.getSupplierPk());
			B2bOrderDtoMa orderEx = b2bOrderService.updateOrderStatus(o,card);
			// 如果订单状态未操作过
			if (null != orderEx) {
				// 存订单日志
				try {
					b2bOrderRecordService.insertOrderRecord(
							member,
							o,
							OrderRecordType.PAYMENT.toString(),
							"订单号:"
									+ orderNumber
									+ ",支付方式:线下支付,支付金额:"
									+ ArithUtil.roundBigDecimal(new BigDecimal(
											orderEx.getActualAmount()), 2));
				} catch (Exception e) {
					logger.error("errorOrderLog", e);
				}
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("order_id", orderEx.getOrderNumber());
				try {
					// 发送短信给采购商
					smsMap.put("uname", orderEx.getPurchaser()
							.getPurchaserName());

					smsService.sendMessage(member.getPk(),
							orderEx.getPurchaserPk(), true,
							SmsCode.PAY_SUCCESS_PU.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorPurchaserSms", e);
				}
				try {
					// 发送短信给供应商
					smsMap.put("uname", orderEx.getSupplier().getSupplierName());
					smsMap.put("pname", orderEx.getPurchaser()
							.getPurchaserName());
					smsService.sendMessage(member.getPk(),
							orderEx.getSupplierPk(), true,
							SmsCode.PAY_SUCCESS_SP.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorSupplierSms", e);
				}

			} else {
				code = RestCode.CODE_O000;
			}
		}
		return code.toJson();
	}

	@Override
	public String payContract(B2bMemberDto member, String contractNo,
			Integer paymentType, String paymentName) {
		RestCode code = RestCode.CODE_0000;
		Boolean flag = true;
		B2bContractDto contractDto = b2bContractService.getB2bContract(contractNo);
		if (null == contractDto) {
			code = RestCode.CODE_O001;
			flag = false;
		}
		if (flag) {
			if (contractDto.getContractStatus() == -1|| contractDto.getContractStatus() >=  2) {
				code = RestCode.CODE_O002;
				flag = false;
			}
		}
		if (flag) {
			B2bContract contract = new B2bContract();
			contract.setContractNo(contractNo);
			contract.setContractStatus(2);
			contract.setPaymentName(paymentName);
			contract.setPaymentType(paymentType);
			contract.setOwnAmount(contractDto.getTotalAmount());
			contract.setPaymentTime(new Date());
			SysCompanyBankcardDto card = bankcardService.getCompanyBankCard(contractDto.getSupplierPk());
			B2bContractDtoMa bm = b2bContractService.updateContractStatus(contract, card);
			// 存订单日志
			try {
				B2bOrder o = new B2bOrder();
				o.setOrderNumber(contractNo);
				o.setOrderStatus(2);
				b2bOrderRecordService.insertOrderRecord(
						member,
						o,
						OrderRecordType.CPAYMENT.toString(),
						"合同号:"
								+ contractNo
								+ ",支付方式:线下支付,支付金额:"
								+ ArithUtil.roundBigDecimal(new BigDecimal(
										contractDto.getTotalAmount()), 2));
			} catch (Exception e) {
				logger.error("errorOrderLog", e);
			}
			Map<String, String> smsMap = new HashMap<String, String>();
			smsMap.put("order_id", contractNo);
			try {
				// 发送短信给采购商
				smsMap.put("uname", bm.getPurchaser().getPurchaserName());
				smsService.sendMessage(member.getPk(),
						contractDto.getPurchaserPk(), true,
						SmsCode.PAY_SUCCESS_PU.getValue(), smsMap);
			} catch (Exception e) {
				logger.error("errorPurchaserSms", e);
			}
			try {
				// 发送短信给供应商
				smsMap.put("uname", bm.getSupplier().getSupplierName());
				smsMap.put("pname", bm.getPurchaser().getPurchaserName());
				smsService.sendMessage(member.getPk(),
						contractDto.getSupplierPk(), true,
						SmsCode.PAY_SUCCESS_SP.getValue(), smsMap);
			} catch (Exception e) {
				logger.error("errorSupplierSms", e);
			}
			if (null != contractDto && 4 != contractDto.getSource()) {
				this.sendErp(contractNo, contractDto.getStorePk(), 2);
			}
		}
		return code.toJson();
	}

	@Override
	public String receivablesOrder(B2bMemberDto member, String orderNumber) {
		RestCode code = RestCode.CODE_0000;
		Boolean flag = true;
		B2bOrderDtoEx order = b2bOrderService.getOrder(orderNumber);
		B2bLoanNumberDto loanNumberDto = b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		if (null == order) {
			code = RestCode.CODE_O001;
			flag = false;
		}
		if (flag && -1 == order.getOrderStatus()) {
			code = RestCode.CODE_O010;
			flag = false;
		}
		if (flag) {
			B2bOrder o = new B2bOrder();
			o.setOrderNumber(orderNumber);
			o.setOrderStatus(3);
			o.setReceivablesTime(new Date());
			B2bOrderDtoEx orderEx = b2bOrderService.updateOrderStatus(o);
			// 如果订单状态未操作过
			if (null != orderEx) {
				// 存订单日志
				try {
					b2bOrderRecordService.insertOrderRecord(member, o,
							OrderRecordType.UPDATE.toString(),
							"订单号:" + o.getOrderNumber() + "已确认收款,状态为待发货");
				} catch (Exception e) {
					logger.error("errorOrderLog", e);
				}
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("order_id", orderEx.getOrderNumber());
				smsMap.put("money",
						ArithUtil.roundStr(orderEx.getActualAmount(), 2));
				try {
					// 发送短信给采购商
					smsService.sendMessage(member.getPk(),
							orderEx.getPurchaserPk(), true,
							SmsCode.CONF_PAY.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorPurchaserSms", e);
				}
				if (null != o.getOrderStatus() && o.getOrderStatus() == 3) {
					//订单状态更新后通知中国银行
                    //判断支付方式  1:线下支付，2余额支付，3金融产品，4线上支付，5票据支付
                    if (order.getPaymentType() == 3) {
                        if (null != loanNumberDto && loanNumberDto.getBankName().equals("中国银行")
                                && loanNumberDto.getEconomicsGoodsName().equals("化纤白条")) {
                            huaxianhuiBankService.orderStatusUpd(orderEx.getOrderNumber(), 1);
                        }
                    }
				}
			} else {
				code = RestCode.CODE_O000;
			}
		}
		return code.toJson();
	}

	@Override
	public String deliverOrder(B2bMemberDto member, String orderNumber, String orders) {
		String rest = RestCode.CODE_0000.toJson();
		int orderStatus = 4;
		List<B2bOrderGoods> goodsList = new ArrayList<B2bOrderGoods>();
		B2bOrderDtoEx orderEx = b2bOrderService.getOrder(orderNumber);
		B2bLoanNumberDto loanNumberDto = b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		Boolean flag = true;
		if (null == orderEx) {
			rest = RestCode.CODE_O001.toJson();
			flag = false;
		}
		// 已取消的不能发货
		if (flag && -1 == orderEx.getOrderStatus()) {
			rest = RestCode.CODE_O010.toJson();
			flag = false;
		}
		// 已发货和已完成的订单不能发货
		if (flag && (4 == orderEx.getOrderStatus() || 6 == orderEx.getOrderStatus())) {
			rest = RestCode.CODE_O002.toJson();
			flag = false;
		}
		if (flag) {
			List<B2bOrderGoodsDtoEx> orderGoods = b2bOrderService.searchOrderGoodsList(orderNumber);
			// 全部发货
			if (null == orders || "".equals(orders)) {
				for (B2bOrderGoodsDtoEx o : orderGoods) {
					// 订单已经取消则不做处理
					if (-1 == o.getOrderStatus()) {
						continue;
					}
					B2bOrderGoods og = new B2bOrderGoods();
					og.setChildOrderNumber(o.getChildOrderNumber());
					og.setBoxesShipped(o.getAfterBoxes() == 0 ? o.getBoxes() : o.getAfterBoxes());
					og.setWeightShipped(o.getAfterWeight() == 0 ? o.getWeight() : o.getAfterWeight());
					og.setOrderStatus(orderStatus);
					goodsList.add(og);
					// 发货/部分发货将订单同步给物流系统
					try {
						// 当前发货重量
						B2bOrderGoodsDtoMa om = new B2bOrderGoodsDtoMa();
						om.UpdateMine(o);
						om.setBoxesShipped(og.getBoxesShipped());
						om.setWeightShipped(og.getWeightShipped());
						commonService.confirmFaHuoForB2B(om);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// 部分发货
			} else {
				orderStatus = 5;
				List<B2bOrderGoods> ogList = JsonUtils.toList(orders,
						B2bOrderGoods.class);
				for (B2bOrderGoodsDtoEx o : orderGoods) {
					// 订单已经取消则不做处理
					if (-1 == o.getOrderStatus()) {
						continue;
					}
					for (B2bOrderGoods og : ogList) {
						if (o.getChildOrderNumber().equals(og.getChildOrderNumber())) {
							// 已发货重量
							og.setBoxesShipped(o.getBoxesShipped() + og.getBoxesShipped());
							og.setWeightShipped(ArithUtil.add(o.getWeightShipped(), og.getWeightShipped()));
							og.setOrderStatus(5);
							goodsList.add(og);
							// 发货单数据同步接口(物流系统提供)
							try {
								// 当前发货重量
								B2bOrderGoodsDtoMa om = new B2bOrderGoodsDtoMa();
								om.UpdateMine(o);
								om.setBoxesShipped(og.getBoxesShipped() - o.getBoxesShipped());
								om.setWeightShipped(og.getWeightShipped() - o.getWeightShipped());
								// 发货/部分发货将订单同步给物流系统
								commonService.confirmFaHuoForB2B(om);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			B2bOrder order = new B2bOrder();
			order.setOrderNumber(orderNumber);
			order.setOrderStatus(orderStatus);
			B2bOrderDtoEx odex = b2bOrderService.updateOrder(order, goodsList);
			if (null == odex) {
				rest = RestCode.CODE_O001.toJson();
			} else {
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("order_id", orderNumber);
				// 存订单日志/发送短信
				if (orderStatus == 4) {
					try {
						b2bOrderRecordService.insertOrderRecord(member, order,
								OrderRecordType.UPDATE.toString(), "订单号:"
										+ orderNumber + "已全部发货,状态为已发货");
					} catch (Exception e) {
						logger.error("errorOrderLog", e);
					}
					try {
						smsService.sendMessage(member.getPk(),
								odex.getPurchaserPk(), true,
								SmsCode.ORDER_DEL_PU.getValue(), smsMap);
					} catch (Exception e) {
						logger.error("errorPurchaserSms", e);
					}
					if (null != order.getOrderStatus() && order.getOrderStatus() == 4) {
						//订单状态更新后通知中国银行
						if (orderEx.getPaymentType() == 3) {
							if (null != loanNumberDto && loanNumberDto.getBankName().equals("中国银行")
									&& loanNumberDto.getEconomicsGoodsName().equals("化纤白条")) {
								huaxianhuiBankService.orderStatusUpd(order.getOrderNumber(), 3);
							}
						}
					}
				} else {
					try {
						String content = "";
						for (B2bOrderGoods og : goodsList) {
							content += "子订单:"
									+ og.getChildOrderNumber()
									+ "已发货数量:"
									+ og.getBoxesShipped()
									+ ",已发货重量:"
									+ new BigDecimal(og.getWeightShipped()
											.toString()).toPlainString() + " ";
						}
						b2bOrderRecordService.insertOrderRecord(member, order,
								OrderRecordType.UPDATE.toString(), "订单号:"
										+ orderNumber + "部分发货;" + content);
					} catch (Exception e) {
						logger.error("errorOrderLog", e);
					}
					try {
						smsService.sendMessage(member.getPk(),
								odex.getPurchaserPk(), true,
								SmsCode.SOME_DEL_PU.getValue(), smsMap);
					} catch (Exception e) {
						logger.error("errorPurchaserSms", e);
					}
					if (null != order.getOrderStatus() && order.getOrderStatus() == 5) {
						//订单状态更新后通知中国银行
						if (orderEx.getPaymentType() == 3) {
							if (null != loanNumberDto && loanNumberDto.getBankName().equals("中国银行")
									&& loanNumberDto.getEconomicsGoodsName().equals("化纤白条")) {
								huaxianhuiBankService.orderStatusUpd(order.getOrderNumber(), 2);
							}
						}
					}
					// 检验订单是否有未完成发货的商品
					try {
						List<B2bOrderGoodsDtoEx> nsOdto = b2bOrderService.searchOrderGoodsList(orderNumber, false, false);
						if (null == nsOdto || nsOdto.size() == 0) {
							B2bOrder o = new B2bOrder();
							o.setOrderNumber(orderNumber);
							o.setOrderStatus(4);
							B2bOrderDtoEx ox = b2bOrderService.updateOrderStatus(o);
							if (null != ox) {
								b2bOrderRecordService.insertOrderRecord(member,o,OrderRecordType.UPDATE.toString(),"订单号:" 
																		+ orderNumber + "已全部发货,状态为已发货");
							}
                            if (null != o.getOrderStatus() && o.getOrderStatus() == 4) {
                                //订单状态更新后通知中国银行
								if (orderEx.getPaymentType() == 3) {
									if (null != loanNumberDto && loanNumberDto.getBankName().equals("中国银行")
											&& loanNumberDto.getEconomicsGoodsName().equals("化纤白条")) {
										huaxianhuiBankService.orderStatusUpd(order.getOrderNumber(), 3);
									}
								}
                            }
						}
					} catch (Exception e) {
						
					}
				}
			}
		}
		return rest;
	}

	@Override
	public String deliverContract(B2bMemberDto member, String contractNo,
			String orders) {
		String rest = RestCode.CODE_0000.toJson();
		int orderStatus = 5;
		List<B2bContractGoods> goodsList = new ArrayList<B2bContractGoods>();
		B2bContractDto contract = b2bContractService.getB2bContract(contractNo);
		Boolean flag = true;
		if (null == contract) {
			rest = RestCode.CODE_O001.toJson();
			flag = false;
		}
		// 已取消的不能发货
		if (flag && -1 == contract.getContractStatus()) {
			rest = RestCode.CODE_O010.toJson();
			flag = false;
		}
		// 已发货和已完成的订单不能发货
		if (flag
				&& (5 == contract.getContractStatus() || 6 == contract
						.getContractStatus())) {
			rest = RestCode.CODE_O002.toJson();
			flag = false;
		}
		if (flag) {
			List<B2bContractGoodsDtoEx> orderGoods = b2bContractGoodsService
					.searchListByContractNo(contractNo, false);
			// 全部发货
			if (null == orders || "".equals(orders)) {
				for (B2bContractGoodsDto o : orderGoods) {
					// 订单已经取消则不做处理
					if (-1 == o.getContractStatus()) {
						continue;
					}
					B2bContractGoods cg = new B2bContractGoods();
					cg.setChildOrderNumber(o.getChildOrderNumber());
					cg.setBoxesShipped(o.getBoxes());
					cg.setWeightShipped(o.getWeight());
					cg.setContractStatus(orderStatus);
					goodsList.add(cg);
				}
				// 部分发货
			} else {
				orderStatus = 4;
				List<B2bContractGoods> ogList = JsonUtils.toList(orders,
						B2bContractGoods.class);
				for (B2bContractGoodsDto o : orderGoods) {
					// 订单已经取消则不做处理
					if (-1 == o.getContractStatus()) {
						continue;
					}
					for (B2bContractGoods og : ogList) {
						if (o.getChildOrderNumber().equals(
								og.getChildOrderNumber())) {
							// 已发货重量
							og.setBoxesShipped(o.getBoxesShipped()
									+ og.getBoxesShipped());
							og.setWeightShipped(ArithUtil.add(
									o.getWeightShipped(), og.getWeightShipped()));
							og.setContractStatus(orderStatus);
							goodsList.add(og);
						}
					}
				}
			}
			B2bContract c = new B2bContract();
			c.setContractNo(contractNo);
			c.setContractStatus(orderStatus);
			B2bContractDto odex = b2bContractService.updateContract(c,
					goodsList);
			if (null == odex) {
				rest = RestCode.CODE_O001.toJson();
			} else {
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("order_id", contractNo);
				// 存订单日志/发送短信
				B2bOrder order = new B2bOrder();
				order.setOrderNumber(contractNo);
				order.setOrderStatus(orderStatus);
				String content = "";
				for (B2bContractGoods og : goodsList) {
					content += "子合同:"
							+ og.getChildOrderNumber()
							+ "已发货数量:"
							+ og.getBoxesShipped()
							+ ",已发货重量:"
							+ new BigDecimal(og.getWeightShipped().toString())
									.toPlainString() + " ";
				}
				if (orderStatus == 5) {
					try {
						b2bOrderRecordService.insertOrderRecord(member, order,
								OrderRecordType.CUPDATE.toString(), "合同号:"
										+ contractNo + "已全部发货,状态为已发货;"
										+ content);
					} catch (Exception e) {
						logger.error("errorOrderLog", e);
					}
					try {
						smsService.sendMessage(member.getPk(),
								odex.getPurchaserPk(), true,
								SmsCode.ORDER_DEL_PU.getValue(), smsMap);
					} catch (Exception e) {
						logger.error("errorPurchaserSms", e);
					}
				} else {
					try {
						b2bOrderRecordService.insertOrderRecord(member, order,
								OrderRecordType.CUPDATE.toString(), "合同号:"
										+ contractNo + "部分发货;" + content);
					} catch (Exception e) {
						logger.error("errorOrderLog", e);
					}
					try {
						smsService.sendMessage(member.getPk(),
								odex.getPurchaserPk(), true,
								SmsCode.SOME_DEL_PU.getValue(), smsMap);
					} catch (Exception e) {
						logger.error("errorPurchaserSms", e);
					}
					// 检验订单是否有未完成发货的商品
					try {
						List<B2bContractGoodsDtoEx> nsOdto = b2bContractGoodsService.searchListByContractNo(contractNo, false);
						if (null == nsOdto || nsOdto.size() == 0) {
							B2bContract o = new B2bContract();
							o.setContractNo(contractNo);
							o.setContractStatus(5);
							b2bContractService.updateContractStatus(o,null);
							order.setOrderStatus(5);
							b2bOrderRecordService.insertOrderRecord(member,
									order, OrderRecordType.CUPDATE.toString(),
									"合同号:" + contractNo + "已全部发货,状态为已发货");
						}
					} catch (Exception e) {
						logger.error("deliverContract", e);
					}
				}
			}
		}
		return rest;
	}

	@Override
	public RestCode completeOrder(B2bMemberDto member, String orderNumber) {
		RestCode code = RestCode.CODE_0000;
		B2bOrder b2bOrder = new B2bOrder();
		b2bOrder.setOrderStatus(6);
		b2bOrder.setOrderNumber(orderNumber);
		b2bOrder.setCompleteTime(new Date());
		B2bOrderDtoEx orderEx = b2bOrderService.updateOrderStatus(b2bOrder);
		B2bLoanNumberDto loanNumberDto = b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		if (null != orderEx) {

			// 订单完成存会员积分
			try {
				b2bOrderService.memberPointByOrder(member, orderEx);
			} catch (Exception e) {
				logger.error("errorMemberPoint", e);
			}
			// 存订单日志
			try {
				b2bOrderRecordService.insertOrderRecord(member, b2bOrder,
						OrderRecordType.UPDATE.toString(),
						"订单号:" + b2bOrder.getOrderNumber() + ",状态为已完成");
			} catch (Exception e) {
				logger.error("errorOrderLog", e);
			}
			try {
				Map<String, String> smsMap = new HashMap<String, String>();
				B2bOrderDtoMa om = new B2bOrderDtoMa();
				om.UpdateMine(orderEx);
				smsMap.put("order_id", orderNumber);
				smsMap.put("uname", om.getSupplier().getSupplierName());
				smsMap.put("pname", om.getPurchaser().getPurchaserName());
				smsService.sendMessage(member.getPk(), orderEx.getSupplierPk(),
						true, SmsCode.ORDER_RECE_SP.getValue(), smsMap);
			} catch (Exception e) {
				logger.error("errorSupplierSms", e);
			}
			if (null != b2bOrder.getOrderStatus() && b2bOrder.getOrderStatus() == 6){
				//订单状态更新后通知中国银行
				if (orderEx.getPaymentType() == 3) {
					if (null != loanNumberDto && loanNumberDto.getBankName().equals("中国银行")
							&& loanNumberDto.getEconomicsGoodsName().equals("化纤白条")) {
						huaxianhuiBankService.orderStatusUpd(b2bOrder.getOrderNumber(), 4);
					}
				}
			}
		} else {
			code = RestCode.CODE_O000;
		}
		return code;
	}

	@Override
	public String cancelOrder(B2bMemberDto member, String orderNumber, Boolean flage) {
		String str = flage ? "手动" : "自动";
		String rest = RestCode.CODE_0000.toJson();
		// 订单加锁
		Object orderLock = JedisUtils.get(orderNumber);
		if (orderLock instanceof Boolean) {
			JedisUtils.lock(orderNumber);// 增加订单锁
			B2bOrderDtoEx odex = b2bOrderService.getOrder(orderNumber);
			if (-1 != odex.getOrderStatus() && odex.getOrderStatus() < 3) {
				List<B2bOrderGoodsDtoEx> orderGoods = b2bOrderService.searchOrderGoodsList(orderNumber);
				boolean flag = true;
				// 循环增加商品锁
				String goodsPk = null;
				for (int i = 0; i < orderGoods.size(); i++) {
					goodsPk = orderGoods.get(i).getGoodsPk();
					if (i > 0 && goodsPk.equals(orderGoods.get(i - 1).getGoodsPk())) {
						continue;
					}
					Object goodsLock = JedisUtils.get(goodsPk);
					if (goodsLock instanceof Boolean) {
						JedisUtils.lock(goodsPk);
					} else {
						flag = false;
						rest = RestCode.CODE_O000.toJson();
						break;
					}
				}
				// 如果是白条货化纤贷支付的订单则调用银行取消订单接口
				if (null != odex.getPaymentType() && 3 == odex.getPaymentType()) {
					try {
						Map<String, Object> map = foreignBankService.delLoanOrder(orderNumber);
						if (null != map.get("code")
                                && !RestCode.CODE_0000.getCode().equals(map.get("code").toString())) {
							RestCode code = RestCode.CODE_Z000;
							if (null != map.get("msg") && !"".equals(map.get("msg").toString())) {
								code.setMsg(map.get("msg").toString());
							} else {
								code.setMsg("银行处理失败");
							}
							rest = code.toJson();
							flag = false;
						}
					} catch (Exception e) {
						flag = false;
						logger.error("errorCancelOrder", e);
						rest = RestCode.CODE_S999.toJson();
					}
				}
				// 如果是线上支付
				if (null != odex.getPaymentType() && 4 == odex.getPaymentType()) {
					try {
						Map<String, Object> map = foreignBankService.delOnlinePayOrder(orderNumber);
						if (null != map.get("code")
								&& !RestCode.CODE_0000.getCode().equals(map.get("code").toString())) {
							RestCode code = RestCode.CODE_Z000;
							if (null != map.get("msg").toString()
									&& !"".equals(map.get("msg").toString())) {
								code.setMsg(map.get("msg").toString());
							} else {
								code.setMsg("银行处理失败");
							}
							rest = code.toJson();
							flag = false;
						}
					} catch (Exception e) {
						flag = false;
						logger.error("errorCancelOrder", e);
						rest = RestCode.CODE_S999.toJson();
					}
				}
				// 如果是票据支付
				if (null != odex.getPaymentType() && 5 == odex.getPaymentType()) {
					try {
						Map<String, Object> map = foreignBankService.delBillPayOrder(orderNumber);
						if (null != map.get("code")
								&& !RestCode.CODE_0000.getCode().equals(map.get("code").toString())) {
							RestCode code = RestCode.CODE_Z000;
							if (null != map.get("msg").toString()
									&& !"".equals(map.get("msg").toString())) {
								code.setMsg(map.get("msg").toString());
							} else {
								code.setMsg("银行处理失败");
							}
							rest = code.toJson();
							flag = false;
						}
					} catch (Exception e) {
						flag = false;
						logger.error("errorCancelOrder", e);
						rest = RestCode.CODE_S999.toJson();
					}
				}
				if (flag) {
					try {
						b2bOrderService.cancelOrder(odex, orderGoods);

						// 删除同步crm未成功的数据
						try {
							Query querys = Query.query(Criteria.where("orderNumber").is(odex.getOrderNumber()));
							mongoTemplate.remove(querys, OrderSyncToMongo.class);
						} catch (Exception e) {
							logger.error("errorOrderSyncToMongo", e);
						}
						// 存订单日志
						try {
							B2bOrder o = new B2bOrder();
							o.setOrderNumber(orderNumber);
							o.setOrderStatus(-1);
							b2bOrderRecordService.insertOrderRecord(member, o,
                                    OrderRecordType.UPDATE.toString(),
                                    "订单号:" + o.getOrderNumber() + "," + str + "取消");
						} catch (Exception e) {
							logger.error("errorOrderLog", e);
						}
						// 短信
						if (null != member) {
							Map<String, String> smsMap = new HashMap<String, String>();
							smsMap.put("order_id", orderNumber);
							try {
								smsService
										.sendMessage(
												member.getPk(),
												odex.getPurchaserPk(),
												false,
												SmsCode.ORDER_CAL_PU.getValue(),
												smsMap);
							} catch (Exception e) {
								logger.error("errorPurchaserSms", e);
							}
							try {
								smsService
										.sendMessage(
												member.getPk(),
												odex.getSupplierPk(),
												true,
												SmsCode.ORDER_CAL_SP.getValue(),
												smsMap);
							} catch (Exception e) {
								logger.error("errorSupplierSms", e);
							}
						}
					} catch (Exception e) {
						logger.error("errorcancelOrder", e);
					} finally {
						// 释放订单锁
						JedisUtils.releaseLock(orderNumber);
						// 释放商品锁
						for (B2bOrderGoodsDtoEx dtoEx : orderGoods) {
							JedisUtils.releaseLock(dtoEx.getGoodsPk());
						}
					}
				} else {
					// 释放订单锁
					JedisUtils.releaseLock(orderNumber);
					// 释放商品锁
					for (B2bOrderGoodsDtoEx dtoEx : orderGoods) {
						JedisUtils.releaseLock(dtoEx.getGoodsPk());
					}
				}
				// 已取消
			} else {
				JedisUtils.releaseLock(orderNumber);
				rest = RestCode.CODE_O000.toJson();
			}
		} else {
			rest = RestCode.CODE_O000.toJson();
		}
		return rest;
	}

	@Override
	public RestCode completeContract(B2bMemberDto member, String contractNo) {
		RestCode code = RestCode.CODE_0000;
		Boolean flag = true;
		B2bContractDto dto = b2bContractService.getB2bContract(contractNo);
		if (null == dto) {
			code = RestCode.CODE_O001;
			flag = false;
		}
		if (-1 == dto.getContractStatus() || 6 == dto.getContractStatus()) {
			code = RestCode.CODE_O002;
			flag = false;
		}
		if (flag) {
			B2bContract contract = new B2bContract();
			contract.setContractNo(contractNo);
			contract.setContractStatus(6);
			contract.setUpdateTime(new Date());
			b2bContractService.updateContractStatus(contract,null);
			// 订单完成存会员积分
			// try {
			// b2bOrderService.memberPointByOrder(member,orderEx);
			// } catch (Exception e) {
			// logger.error("errorMemberPoint", e);
			// }
			// 存合同日志
			try {
				B2bOrder b2bOrder = new B2bOrder();
				b2bOrder.setOrderStatus(6);
				b2bOrder.setOrderNumber(contractNo);
				b2bOrderRecordService.insertOrderRecord(member, b2bOrder,
						OrderRecordType.CUPDATE.toString(),
						"合同号:" + b2bOrder.getOrderNumber() + ",状态为已完成");
			} catch (Exception e) {
				logger.error("errorOrderLog", e);
			}
		}
		return code;

	}

	@Override
	public String cancelContract(B2bMemberDto member, String contractNo) {

		String rest = RestCode.CODE_0000.toJson();
		B2bContractDto dto = b2bContractService.getB2bContract(contractNo);
		Boolean flag = true;
		if (null == dto) {
			flag = false;
			rest = RestCode.CODE_O001.toJson();
		}
		if (flag && -1 == dto.getContractStatus()) {
			flag = false;
			rest = RestCode.CODE_O002.toJson();
		}
		if (flag) {
			B2bContract contract = new B2bContract();
			contract.setContractNo(contractNo);
			contract.setContractStatus(-1);
			b2bContractService.updateContractStatus(contract,null);
			try {
				Query querys = Query.query(Criteria.where("contractNumber").is(
						contractNo));
				mongoTemplate.remove(querys, ContractSyncToMongo.class);
			} catch (Exception e) {
				logger.error("errorContractNumberLog", e);
			}
			// 存合同日志
			try {
				B2bOrder o = new B2bOrder();
				o.setOrderNumber(contractNo);
				o.setOrderStatus(-1);
				b2bOrderRecordService.insertOrderRecord(member, o,
						OrderRecordType.CUPDATE.toString(),
						"合同号:" + o.getOrderNumber() + ",已取消");
			} catch (Exception e) {
				logger.error("errorOrderLog", e);
			}
			// 短信
			if (null != member) {
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("order_id", contractNo);
				try {
					smsService.sendMessage(member.getPk(),
							dto.getPurchaserPk(), false,
							SmsCode.ORDER_CAL_PU.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorPurchaserSms", e);
				}
				try {
					smsService.sendMessage(member.getPk(), dto.getSupplierPk(),
							true, SmsCode.ORDER_CAL_SP.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorSupplierSms", e);
				}
			}
		}
		return rest;

	}

	@Override
	public String updateOrderPrice(B2bMemberDto member, String orderNumber,
			String orders) {
		String rest = RestCode.CODE_0000.toJson();
		B2bOrderDtoEx orderEx = b2bOrderService.getOrder(orderNumber);
		Boolean flag = true;
		Integer wStatus = null;
		if (null == orderEx) {
			rest = RestCode.CODE_O001.toJson();
			flag = false;
		}
		if (flag && (orderEx.getOrderStatus() == -1)) {
			rest = RestCode.CODE_O010.toJson();
			flag = false;
		}
		if (flag && (orderEx.getOrderStatus() > 1)) {
			rest = RestCode.CODE_O002.toJson();
			flag = false;
		}
		if (flag) {
			Double actualAmount = 0d;
			List<B2bOrderGoods> goodsList = new ArrayList<B2bOrderGoods>();
			B2bOrder order = new B2bOrder();
			if(orderEx.getOrderClassify()==2){
				order.setReceivablesTime(new Date());
			}
			order.setOrderNumber(orderNumber);
			List<B2bOrderGoodsDtoEx> orderGoods = b2bOrderService
					.searchOrderGoodsList(orderNumber);
			List<B2bOrderGoods> ogList = JsonUtils.toList(orders,
					B2bOrderGoods.class);
			// 待审核的订单
			if (0 == orderEx.getOrderStatus()) {
				order.setOrderStatus(1);
				wStatus = 1;// 分货标识
			}
			if (orderEx.getOrderClassify() == 2) {// 合同订单
				order.setOrderStatus(3);// 待发货
				wStatus = 1;// 分货标识
			}
			Double totalWeight = 0d;
			Double packageFee = 0d;// 包装费
			Double adminFee = 0d;// 管理费
			Double loadFee = 0d;// 装车费
			ko: for (B2bOrderGoodsDtoEx odex : orderGoods) {
				for (B2bOrderGoods og : ogList) {
					if (og.getChildOrderNumber().equals(
							odex.getChildOrderNumber())) {
						if (null != og.getPresentPrice()
								&& og.getPresentPrice() <= 0) {
							flag = false;
							rest = RestCode.CODE_G015.toJson();
							break ko;
						}
						if (null != og.getPresentFreight()
								&& og.getPresentFreight() < 0) {
							flag = false;
							rest = RestCode.CODE_G016.toJson();
							break ko;
						}
						// 合同订单不能超过订单重量
						if (orderEx.getOrderClassify() == 2
								&& og.getAfterWeight() > odex.getWeight()) {
							flag = false;
							rest = RestCode.CODE_O004.toJson();
							break ko;

						}
						B2bOrderGoodsDtoMa om = new B2bOrderGoodsDtoMa();
						om.UpdateMine(odex);
						if(Block.CF.getValue().equals(odex.getBlock())){
							packageFee = null == om.getCfGoods().getPackageFee()?0d:om.getCfGoods().getPackageFee();
									
							adminFee = null == om.getCfGoods().getAdminFee()?0d:om.getCfGoods().getAdminFee();
											
							loadFee = null == om.getCfGoods().getLoadFee()?0d:om.getCfGoods().getLoadFee();
													
						}
						/**
						 * 判断是化纤还是纱线计算运费
						 */
						double presentToalFreight = 0d;
						if (Block.CF.getValue().equals(odex.getBlock())) {
							presentToalFreight = ArithUtil.mul(og
									.getPresentFreight(), (null == og
									.getAfterWeight() ? odex.getAfterWeight()
									: og.getAfterWeight()));
						} else if (Block.SX.getValue().equals(odex.getBlock())) {
							presentToalFreight = ArithUtil.div(ArithUtil.mul(og
									.getPresentFreight(), (null == og
									.getAfterWeight() ? odex.getAfterWeight()
									: og.getAfterWeight())), 1000);
						} else {
							presentToalFreight = ArithUtil.mul(og
									.getPresentFreight(), (null == og
									.getAfterWeight() ? odex.getAfterWeight()
									: og.getAfterWeight()));
						}
						og.setPresentTotalFreight(presentToalFreight);
						// 订单总价
						actualAmount = ArithUtil
								.add(actualAmount,
										ArithUtil.sub(
												ArithUtil.add(
														ArithUtil.round(
																ArithUtil
																		.mul((og.getPresentPrice()
																						+ packageFee
																						+ loadFee + adminFee),
																				(null == og
																						.getAfterWeight() ? odex
																						.getAfterWeight()
																						: og.getAfterWeight())),
																2),
														ArithUtil.round(
																ArithUtil
																		.mul(og.getPresentFreight(),
																				Block.CF.getValue()
																						.equals(odex
																								.getBlock()) ? (null == og
																						.getAfterWeight() ? odex
																						.getAfterWeight()
																						: og.getAfterWeight())
																						: ArithUtil
																						.div((null == og
																										.getAfterWeight() ? odex
																										.getAfterWeight()
																										: og.getAfterWeight()),
																								1000)),
																2)), null == og.getOtherDiscount() ? 0d : og.getOtherDiscount()));
						// 合同订单 分货后直接 3待发货
						if (orderEx.getOrderClassify() == 2) {
							og.setOrderStatus(3);
							totalWeight += og.getAfterWeight();
						} else {
							// 将订单状态改成待付款
							if (0 == orderEx.getOrderStatus()) {
								og.setOrderStatus(1);
								totalWeight += og.getAfterWeight();
							}
						}

						goodsList.add(og);
						// 做记录日志操作时使用
						odex.setFinalPrice(og.getPresentPrice());
						odex.setFinalFreight(og.getPresentFreight());
						//todo 最终其他优惠
					}
				}
			}
			if (flag) {
				// 如果只分了0箱
				if (null != wStatus && totalWeight == 0d) {
					rest = RestCode.CODE_O003.toJson();
				} else {
					order.setActualAmount(ArithUtil.round(actualAmount, 2));
					b2bOrderService.updateOrder(order, goodsList);
					// 存订单日志
					try {
						B2bOrder o = new B2bOrder();
						o.setOrderNumber(orderNumber);
						for (B2bOrderGoodsDtoEx odex : orderGoods) {
							b2bOrderRecordService
									.insertOrderRecord(
											member,
											o,
											OrderRecordType.UPDATE.toString(),
											"子订单号:"
													+ odex.getChildOrderNumber()
													+ ",商品单价由:"
													+ ArithUtil
															.roundBigDecimal(
																	new BigDecimal(
																			odex.getPresentPrice()),
																	2)
													+ "修改为:"
													+ ArithUtil
															.roundBigDecimal(
																	new BigDecimal(
																			odex.getFinalPrice()),
																	2)
													+ ",运费单价由:"
													+ ArithUtil
															.roundBigDecimal(
																	new BigDecimal(
																			odex.getPresentFreight()),
																	2)
													+ "修改为:"
													+ ArithUtil
															.roundBigDecimal(
																	new BigDecimal(
																			odex.getFinalFreight()),
																	2));
						}
						b2bOrderRecordService.insertOrderRecord(
								member,
								o,
								OrderRecordType.UPDATE.toString(),
								"订单号:"
										+ o.getOrderNumber()
										+ ",总价由"
										+ ArithUtil.roundBigDecimal(
												new BigDecimal(orderEx
														.getActualAmount()), 2)
										+ "修改为"
										+ ArithUtil
												.roundBigDecimal(
														new BigDecimal(
																actualAmount),
														2));
					} catch (Exception e) {
						logger.error("errorUpdateOrderPrice", e);
					}
					// 发送短信
					try {
						Map<String, String> smsMap = new HashMap<String, String>();
						B2bOrderDtoMa om = new B2bOrderDtoMa();
						om.UpdateMine(orderEx);
						smsMap.put("order_id", orderEx.getOrderNumber());
						smsMap.put("uname", om.getSupplier().getSupplierName());
						smsMap.put("pname", om.getPurchaser()
								.getPurchaserName());
						smsService.sendMessage(member.getPk(),
								orderEx.getPurchaserPk(), true,
								SmsCode.ORDER_ED_PRICE_PU.getValue(), smsMap);
					} catch (Exception e) {
						logger.error("errorPurchaserSms", e);
					}
				}
			}
		}
		return rest;
	}

	@Override
	public String orderDetails(String orderNumber) {
		String rest = null;
		B2bOrderDtoEx orderEx = b2bOrderService.getOrder(orderNumber);
		if (null == orderEx) {
			rest = RestCode.CODE_O001.toJson();
		} else {
			// 订单日志
			Query query = new Query(Criteria.where("orderNumber").is(
					orderNumber));
			query.with(new Sort(Direction.DESC, "insertTime"));
			List<OrderRecord> recordList = mongoTemplate.find(query,
					OrderRecord.class);
			orderEx.setOrderRecordList(recordList);
			// 订单商品列表
			List<B2bOrderGoodsDtoEx> orderGoodsList = b2bOrderService
					.searchOrderGoodsList(orderNumber);
			orderEx.setOrderGoodsList(orderGoodsList);
			// 付款凭证列表
			List<B2bPayVoucher> voucherList = b2bPayVoucherService
					.searchB2bPayVoucherList(orderNumber);
			if (null != voucherList && voucherList.size() > 0) {
				orderEx.setPayVoucherList(voucherList);
			}
			if (null != orderEx.getPaymentType()
					&&  orderEx.getPaymentType() == 3) {
				try {
					B2bLoanNumberDto boanNumber = foreignBankService
							.getB2bLoanNumberDto(orderEx.getOrderNumber());
					if (null != boanNumber) {
						orderEx.setLoanNumberStatus(boanNumber
								.getLoanStatus());
					}

				} catch (Exception e) {
					logger.error("借据状态" + e);
				}
			}
			if (null != orderEx.getPaymentType()
					&&  orderEx.getPaymentType() == 4) {
				try {
					B2bOnlinepayRecordDto onlineRecords = foreignBankService
							.getOnlinepayRecordDto(orderEx.getOrderNumber());
					if (null != onlineRecords) {
						orderEx.setOnlinePayStatus(onlineRecords.getStatus());
					}

				} catch (Exception e) {
					logger.error("线上支付状态" + e);
				}
			}
			if (null != orderEx.getPaymentType()
					&&  orderEx.getPaymentType() == 5) {
				try {
					B2bBillOrderDto billOrder = foreignBankService
							.getBillOrder(orderEx.getOrderNumber());
					if (null != billOrder && null != billOrder.getStatus()) {
						//1处理中 2完成 3取消  4锁定  
						orderEx.setBillPayStatus(billOrder.getStatus());
						orderEx.setBillGoodsType(BillType.TX.equals(billOrder.getGoodsShotName())?
								BillType.TX:BillType.PFT);
						//锁定状态下的特殊处理
						if(null != billOrder.getSerialNumber() && !"".equals(billOrder.getSerialNumber())
								&& 4 == billOrder.getStatus()){
							List<B2bBillInventoryDto> inventoryList = foreignBankService.searchBillInventoryList(orderEx.getOrderNumber());
							if(null != inventoryList && inventoryList.size()>0){
								for(B2bBillInventoryDto i : inventoryList){
									if(null !=i.getStatus() && (1 ==i.getStatus()) ||
											2 ==i.getStatus() || 3==i.getStatus()){
										orderEx.setBillPayStatus(2);
										break;
									}
								}
							}
						}
						//显示票付通按钮
						if((null == billOrder.getSerialNumber() || "".equals(billOrder.getSerialNumber()))
								&& BillType.PFT.equals(orderEx.getBillGoodsType())){
							orderEx.setShowPft(1);//1显示票付通按钮
						}
					}

				} catch (Exception e) {
					logger.error("票据支付状态" + e);
				}
			}
			
			rest = RestCode.CODE_0000.toJson(orderEx);
		}
		return rest;
	}

	@Override
	public List<ExportOrderDto> exportOrderList(Map<String, Object> map,
			Sessions session) {
		searchType(map, session.getCompanyDto(), session.getStoreDto(),
				session.getMemberDto(), session.getIsAdmin());
		return b2bOrderService.exportOrderList(map);
	}

	@Override
	public CallBackContract submitContract(String contacts, B2bCompanyDto c,
			B2bMemberDto m, Integer source) {
		String rest = null;
		CallBackContract back = new CallBackContract();
		boolean flag = true;
		if (null == c) {
			rest = RestCode.CODE_M008.toJson();
			flag = false;
		}
		B2bAddressDto a = null;
		LogisticsModelDto lmdto = null;
		// B2bInvoiceDto i = null;
		Corder o = new Corder(contacts);
		o.setSource(source);// 下单来源
		// 1自采 2代采
		if (!"".equals(o.getPurchaserPk()) && !"".equals(o.getPurchaserName())) {
			c = b2bCompanyService.getCompany(o.getPurchaserPk());
			o.setPurchaserType(2);
		} else {
			o.setPurchaserType(1);
		}
		// 判断物流方式
		if (flag) {
			lmdto = logisticsService.getLogisticModel(o.getLogisticsModelPk());
			if (null == lmdto) {
				rest = RestCode.CODE_L001.toJson();
				flag = false;
			}
			o.setLmdto(lmdto);
		}
		// 判断收货地址
		if (flag && (lmdto.getType() == 1 || lmdto.getType() == 2)) {
			a = addressService.getAddress(o.getAddressPk());
			if (null == a) {
				rest = RestCode.CODE_D002.toJson();
				flag = false;
			}
		}
		if (flag) {
			// 判断发票
			if (null != o.getInvoicePk() && !"".equals(o.getInvoicePk())) {
				B2bCompanyDtoEx invoice = b2bCompanyService.getCompany(o
						.getInvoicePk());
				if (null == invoice) {
					rest = RestCode.CODE_I001.toJson(); // 发票不存在
					flag = false;
				} else {
					o.setInvoice(invoice);
				}

			}

		}
		List<Cgoods> gs = null;
		if (flag) {
			gs = o.getGoods();
			if (null != gs && gs.size() > 0) {
				Map<String, List<Cgoods>> map = new HashMap<String, List<Cgoods>>();
				List<Cgoods> list = null;
				B2bGoodsDtoMa gdto = null;
				try {
					for (Cgoods pg : gs) {
						Map<String, Object> goodsCode = checkCGoods(pg);
						if (!RestCode.CODE_0000.getCode().equals(
								goodsCode.get("code"))) {
							rest = goodsCode.get("data").toString();
							flag = false;
							break;
						} else {
							gdto = (B2bGoodsDtoMa) goodsCode.get("data");
						}
						if (map.containsKey(gdto.getCompanyPk())) {
							list = map.get(gdto.getCompanyPk());
						} else {
							list = new ArrayList<Cgoods>();
						}
						// 商家承运不加自提管理费
						// if (lmdto.getType() == 2) {
						// gdto.setAdminFee(0d);
						// }
						if (null != pg.getLogisticsPk()
								&& !"".equals(pg.getLogisticsPk())) {
							LogisticsErpDto lg = logisticsErpDaoEx.getByPk(pg
									.getLogisticsPk());
							pg.setLogisticsCarrierName(null == lg ? "" : lg
									.getLgCompanyName());
							pg.setLogisticsCarrierPk(null == lg ? "" : lg
									.getLgCompanyPk());
						}
						pg.setGoodsDto(gdto);
						list.add(pg);
						map.put(gdto.getCompanyPk(), list);
					}
					// 提交合同订单
					if (flag) {
						List<B2bContractDtoEx> oList = b2bContractService
								.submitContract(o, c, m, a, map);
						// 合同提交成功后回调
						if (null != oList && oList.size() > 0) {
							back.setoList(oList);
						}
					}
				} catch (Exception e) {
					logger.error("errorSubmitOrder", e);
					back.setRest(RestCode.CODE_S999.toJson());
				}
			} else {
				back.setRest(RestCode.CODE_A001.toJson());
			}
		}
		if(null == back.getoList() && null == back.getRest()){
			back.setRest(rest);
		}
		return back;
	}
	
	@Async
	@Override
	public void afterContract(B2bMemberDto m, List<B2bContractDtoEx> contractList) {
		if(null != contractList && contractList.size()>0){
			B2bOrder bo = new B2bOrder();
			for(B2bContractDtoEx contract : contractList){
				bo.setOrderNumber(contract.getContractNo());
				bo.setOrderStatus(1);
				try {
					//存合同日志
					b2bOrderRecordService.insertOrderRecord(m,bo,OrderRecordType.CINSERT.toString(),
							"合同号:" + bo.getOrderNumber());
				} catch (Exception e) {
					logger.error("errorOrderLog", e);
				}
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("order_id", bo.getOrderNumber());
				try {
					// 发送短信给采购商
					smsService.sendMessage(m.getPk(),
							contract.getPurchaserPk(), false,
							SmsCode.ORDER_AD_PU.getValue(),
							smsMap);
				} catch (Exception e) {
					logger.error("errorPurchaserSms", e);
				}
				try {
					// 发送短信给供应商
					smsService.sendMessage(m.getPk(),
							contract.getSupplierPk(), true,
							SmsCode.ORDER_AD_SP.getValue(),
							smsMap);
				} catch (Exception e) {
					logger.error("errorSupplierSms", e);
				}
				// 录入客户信息
				this.saveCustomer(contract.getPurchaserPk(),
						contract.getStorePk());
				this.sendCms(bo.getOrderNumber(), contract.getBlock(),3);
			}
		}
	}

	@Override
	public String auditContract(B2bMemberDto member, String contractNo,
			Integer type) {
		String rest = RestCode.CODE_0000.toJson();

		try {
			// 修改合同状态为审批中
			B2bContractDto dto = b2bContractService.getB2bContract(contractNo);
			if (null != dto
					&& (3 == dto.getContractStatus() || -1 == dto
							.getContractStatus())) {
				rest = RestCode.CODE_O002.toJson();
			} else {
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				if (2 == type) {
					contract.setContractStatus(2);
				} else {
					contract.setContractStatus(1 == type ? 3 : -1);
				}

				b2bContractService.updateContractStatus(contract,null);

				//存合同日志
				try {
					B2bOrder o = new B2bOrder();
					o.setOrderNumber(contractNo);
					if (1 == type) {
						o.setOrderStatus(3);
						b2bOrderRecordService.insertOrderRecord(member, o,
								OrderRecordType.CUPDATE.toString(), "合同号:"
										+ contractNo + ",审核通过");
					} else if (3 == type) {
						b2bOrderRecordService.insertOrderRecord(member, o,
								OrderRecordType.CUPDATE.toString(), "合同号:"
										+ contractNo + ",审核不通过");
						// 审核不通过删除mongoDB日志
						Query querys = Query.query(Criteria.where(
								"contractNumber").is(contractNo));
						mongoTemplate.remove(querys, ContractSyncToMongo.class);
					} else if (2 == type) {
						b2bOrderRecordService.insertOrderRecord(member, o,
								OrderRecordType.CUPDATE.toString(), "合同号:"
										+ contractNo + ",审核被驳回");
					}
				} catch (Exception e) {
					logger.error("errorOrderLog", e);
				}
			}
		} catch (Exception e) {
			logger.error("auditContract", e);
		}
		return rest;
	}

	@Override
	public String uploadVoucher(B2bMemberDto member, String orderNumber,
			String imgUrl,Integer type) {
		String rest = RestCode.CODE_0000.toJson();
		Boolean flag = true;
		B2bOrderDtoEx orderEx = b2bOrderService.getOrder(orderNumber);
		if (null == orderEx) {
			rest = RestCode.CODE_A001.toJson();
			flag = false;
		}
		B2bOrderDtoMa om = null;
		if (flag) {
			// 如果是待付款状态上传付款凭证之后将订单设置成为待确认状态
			if (orderEx.getOrderStatus() == 1 && type == 1) {
				B2bOrder order = new B2bOrder();
				B2bPaymentDto payment = b2bPaymentService.getPaymentByType(1);
				order.setOrderNumber(orderNumber);
				if (null != payment) {
					order.setPaymentType(payment.getType());
					order.setPaymentName(payment.getName());
					order.setPaymentTime(new Date());
					order.setOwnAmount(orderEx.getActualAmount());
				}
				order.setOrderStatus(2);
				SysCompanyBankcardDto card = bankcardService
						.getCompanyBankCard(orderEx.getSupplierPk());
				om = b2bOrderService.updateOrderStatus(order,card);
				// 存订单日志
				try {
					b2bOrderRecordService.insertOrderRecord(member, order,
							OrderRecordType.PAYMENT.toString(), "订单号:"
									+ orderNumber + ",支付方式:线下支付");
				} catch (Exception e) {
					logger.error("errorOrderLog", e);
				}
				// 发送短信
				try {
					Map<String, String> smsMap = new HashMap<String, String>();
					smsMap.put("order_id", orderNumber);
					smsMap.put("pay_account", om.getPurchaser().getPurchaserName());
					smsService.sendMessage(member.getPk(), orderEx.getSupplierPk(),
							true, SmsCode.UP_VOUCHER.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorSupplierSms", e);
				}
			}
			// 上传凭证
//			b2bPayVoucherService.addPayVoucher(orderEx, imgUrl,1);
			commonService.savePayvoucher(orderNumber, imgUrl, type);
			
		}
		return rest;
	}

	@Override
	public String delVoucher(B2bMemberDto member, String id) {
		String rest = RestCode.CODE_0000.toJson();
		try {
//			Query query = new Query(Criteria.where("id").is(id));
//			B2bPayVoucher b2bVoucher = mongoTemplate.findOne(query,B2bPayVoucher.class);
//			if (null != b2bVoucher) {
//				String temp="";
//				if (type==1) {
//					temp=OrderRecordType.UPDATE.toString();
//				}else {
//					temp=OrderRecordType.CUPDATE.toString();
//				}
//				// 增加订单删除凭证日志
//				String content = (b2bVoucher.getType() == 1 ? "付款" : "发货")+ "凭证:<img src='" + b2bVoucher.getUrl()
//						+ "' style='display:block;'/>已被删除作废";
//				B2bOrder order = new B2bOrder();
//				order.setOrderNumber(b2bVoucher.getOrderNumber());
//				b2bOrderRecordService.insertOrderRecord(member, order,temp, content);
//				// 删除对应记录
//				mongoTemplate.remove(b2bVoucher);
//			}
			commonService.delPayvoucher(id);
		} catch (Exception e) {
			logger.error("errorDelVoucher", e);
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}

	@Override
	public String uploadVoucherByContract(B2bMemberDto member,
			String contractNo, String imgUrl,Integer type) {

		String rest = RestCode.CODE_0000.toJson();
		Boolean flag = true;
		B2bContractDto cdto = b2bContractService.getB2bContract(contractNo);
		if (null == cdto) {
			rest = RestCode.CODE_O001.toJson();
			flag = false;
		}
		if (flag) {
			B2bContractDtoMa cm = null;
			// 如果是待付款状态上传付款凭证之后将订单设置成为待确认状态
			if (cdto.getContractStatus() == 1 && type == 1) {
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				B2bPaymentDto payment = b2bPaymentService.getPaymentByType(1);
				if (null != payment) {
					contract.setPaymentName(payment.getName());
					contract.setPaymentType(1);
					contract.setPaymentTime(new Date());
					contract.setOwnAmount(cdto.getTotalAmount());
				}
				contract.setContractStatus(2);
				SysCompanyBankcardDto card = bankcardService
						.getCompanyBankCard(cdto.getSupplierPk());
				cm = b2bContractService.updateContractStatus(contract,card);
				// 存合同日志
				try {
					B2bOrder order = new B2bOrder();
					order.setOrderNumber(contractNo);
					order.setOrderStatus(2);
					b2bOrderRecordService.insertOrderRecord(member, order,
							OrderRecordType.CPAYMENT.toString(), "合同号:"
									+ contractNo + ",支付方式:线下支付");
				} catch (Exception e) {
					logger.error("errorOrderLog", e);
				}
				// crm推送过来的合同不进行推送
				if (null != cdto && 4 != cdto.getSource()) {
					this.sendErp(contractNo, cdto.getStorePk(), 2);
				}
				// 发送短信
				try {
					Map<String, String> smsMap = new HashMap<String, String>();
					smsMap.put("order_id", contractNo);
					smsMap.put("pay_account", cm.getPurchaser().getPurchaserName());
					smsService.sendMessage(member.getPk(), cdto.getSupplierPk(),
							true, SmsCode.UP_VOUCHER.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorSupplierSms", e);
				}
			}
			// 上传凭证
//			B2bOrderDtoEx dto = new B2bOrderDtoEx(cdto);
//			b2bPayVoucherService.addPayVoucher(dto, imgUrl,2);
			commonService.savePayvoucher(contractNo, imgUrl, type);
		}
		return rest;

	}

	@Override
	public String bindToOrder(String orderNumber, B2bMemberDto member) {
		RestCode code = RestCode.CODE_0000;
		B2bBindOrderDto bindOrder = b2bBindOrderService
				.searchBindOrder(orderNumber);
		if (null == bindOrder) {
			return RestCode.CODE_A001.toJson();
		}
		if (null == member && null != bindOrder.getMemberPk()
				&& !"".equals(bindOrder.getMemberPk())) {
			member = b2bMemberService.getByPk(bindOrder.getMemberPk());
		}
		if (null == member) {
			member = new B2bMemberDto();
			member.setPk(bindOrder.getMemberPk());
			member.setMobile(bindOrder.getMemberName());
		}
		// 物流方式
		LogisticsModelDto lmdto = logisticsService.getLogisticModel(bindOrder
				.getLogisticsModelPk());
		AddressInfo address = JsonUtils.toBean(bindOrder.getAddressInfo(),
				AddressInfo.class);
		PurchaserInfo purchaserInfo = JsonUtils.toBean(
				bindOrder.getPurchaserInfo(), PurchaserInfo.class);
		List<B2bBindOrderGoodsEx> rlist = JsonUtils.getStringToList(
				bindOrder.getGoodsJson(), B2bBindOrderGoodsEx.class);
		Porder o = new Porder(bindOrder, lmdto, address, purchaserInfo, member);
		BackPgoods bp = getPgoodslistByBind(o, rlist);
		code = bp.getRestCode();
		if ("0000".equals(code.getCode())) {
			code = transformationOrder(o, bp.getPgList());
		}
		// 订单提交成功后回调
		if ("0000".equals(code.getCode())) {
			// 转化订单成功之后修改需求单状态
			b2bBindOrderService.updateB2bBindOrderStatus(orderNumber, 2);
		}
		for (Pgoods pg : bp.getPgList()) {
			if (null != pg.getGoodsPk() && !"".equals(pg.getGoodsPk())) {
				JedisUtils.releaseLock(pg.getGoodsPk());
			}
		}

		return code.toJson();
	}

	private BackPgoods getPgoodslistByBind(Porder o,
			List<B2bBindOrderGoodsEx> rlist) {
		BackPgoods bp = new BackPgoods(RestCode.CODE_0000);
		B2bGoodsDtoMa gdto = null;
		List<Pgoods> pgList = new ArrayList<Pgoods>();
		for (B2bBindOrderGoodsEx reserve : rlist) {
			Pgoods pg = new Pgoods();
			pg.setBoxes(reserve.getBoxes());
			pg.setGoodsPk(reserve.getGoodsPk());
			pg.setTotalBoxes(reserve.getBoxes());
			if (null == reserve.getWeight() || reserve.getWeight() == 0d) {
				B2bGoodsDto g = b2bGoodsService.getB2bGoods(reserve
						.getGoodsPk());
				if (null != g && null != g.getTareWeight()
						&& g.getTareWeight() > 0d) {
					pg.setWeight(ArithUtil.div(
							reserve.getBoxes() * g.getTareWeight(), 1000));
				} else {
					pg.setWeight(0d);
				}
			} else {
				pg.setWeight(reserve.getWeight());
			}

			BackGoods bg = checkGoods(pg, o.getPurchaseType());
			if (!"0000".equals(bg.getRestCode().getCode())) {
				bp.setRestCode(bg.getRestCode());
				break;
			} else {
				gdto = bg.getGdto();
			}
			gdto.setSalePrice(reserve.getPrice());
			pg.setGdto(gdto);
			// 调用物流系统提供的运费
			if (o.getLmdto().getType() == 1 || o.getLmdto().getType() == 2) {
				pg = this.setLogisticsPrice(o, gdto, pg);
			}
			pgList.add(pg);
		}
		bp.setPgList(pgList);
		return bp;
	}

	/*
	 * @Override public String bindToOrder(String orderNumber, B2bMemberDto
	 * member) { // 查询需求单表 B2bBindOrderDto bindOrder = b2bBindOrderService
	 * .searchBindOrder(orderNumber); if (null == bindOrder) { return
	 * RestCode.CODE_A001.toJson(); } String bindProductId =
	 * bindOrder.getBindProductId(); if (null == member && null !=
	 * bindOrder.getMemberPk() && !"".equals(bindOrder.getMemberPk())) { member
	 * = b2bMemberService.getByPk(bindOrder.getMemberPk()); } if (null ==
	 * member) { member = new B2bMemberDto(); } // 查询需求单商品表
	 * List<B2bBindOrderGoodsDtoEx> bindOrderGoods = b2bBindOrderGoodsService
	 * .searchByOrderNumber(orderNumber,bindOrder.getBlock());
	 * 
	 * Map<String, List<B2bBindOrderGoodsDtoEx>> map = new HashMap<String,
	 * List<B2bBindOrderGoodsDtoEx>>(); RestCode code = RestCode.CODE_0000;
	 * List<BackOrder> backList = new ArrayList<BackOrder>();
	 * 
	 * LogisticsModelDto ldto = logisticsService.getLogisticModel(bindOrder
	 * .getLogisticsModelPk()); List<B2bOrder> oList = null; // 使用编程式事务处理事务回滚
	 * TransactionStatus status = txManager .getTransaction(new
	 * DefaultTransactionDefinition()); try { B2bGoodsDtoMa gdto = null; Pgoods
	 * pg = new Pgoods(); for (B2bBindOrderGoodsDtoEx bg : bindOrderGoods) {
	 * pg.setGoodsPk(bg.getGoodsPk()); pg.setBoxes(bg.getBoxes()); BackGoods
	 * goodsCode = checkGoods(pg, null); if
	 * (!"0000".equals(goodsCode.getRestCode().getCode())) { code =
	 * goodsCode.getRestCode(); break; } else { gdto = goodsCode.getGdto(); //
	 * if (null != ldto && ldto.getType() == 2) { // gdto.setAdminFee(0d); // }
	 * }
	 * 
	 * bg.setGdto(gdto); if (null != bg.getLogisticsPk() &&
	 * !"".equals(bg.getLogisticsPk())) { LogisticsErpDto lg =
	 * logisticsErpDaoEx.getByPk(bg .getLogisticsPk());
	 * bg.setLogisticsCarrierPk(lg.getLgCompanyPk());
	 * bg.setLogisticsCarrierName(lg.getLgCompanyName()); } if
	 * (!map.containsKey(gdto.getCompanyPk())) { map.put(gdto.getCompanyPk(),
	 * new ArrayList<B2bBindOrderGoodsDtoEx>()); }
	 * map.get(gdto.getCompanyPk()).add(bg); } // 提交订单 if
	 * ("0000".equals(code.getCode())) { oList =
	 * b2bOrderService.submitBindOrder(bindOrder, map, bindProductId,ldto); } //
	 * 订单提交成功后回调 if (null != oList && oList.size() > 0) { // 转化订单成功之后修改需求单状态
	 * b2bBindOrderService.updateB2bBindOrderStatus(orderNumber, 2); for
	 * (B2bOrder order : oList) { backList.add(new BackOrder(order)); } } }
	 * catch (Exception e) { logger.error("errorSubmitOrder", e); code =
	 * RestCode.CODE_S999; // 删除商品锁 } finally { for (B2bBindOrderGoodsDtoEx pg :
	 * bindOrderGoods) { if (null != pg.getGoodsPk() &&
	 * !"".equals(pg.getGoodsPk())) { JedisUtils.releaseLock(pg.getGoodsPk()); }
	 * } // 订单提交后 if ("0000".equals(code.getCode())) { txManager.commit(status);
	 * } else { txManager.rollback(status); } } // 订单提交后 if
	 * ("0000".equals(code.getCode())) { this.afterOtherOrder(oList, member,
	 * null); } return code.toJson(backList); }
	 */

	@Override
	public void addPoint(String memberPk, String companyPk, String dimenType) {
		try {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("dimenType", dimenType);
			paraMap.put("companyPk", companyPk);
			/*
			 * String result =
			 * HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+
			 * "member/searchPointList", paraMap); try { result =
			 * EncodeUtil.des3Decrypt3Base64(result)[1]; } catch (Exception e) {
			 * e.printStackTrace(); } JSONArray jsonarray =
			 * JSONArray.fromObject(JsonUtils.getJsonData(result)); if (null ==
			 * jsonarray || jsonarray.size() == 0) {
			 * paraMap.remove("dimenType"); paraMap.remove("companyPk");
			 * paraMap.put("memberPk", memberPk); paraMap.put("companyPk",
			 * companyPk); paraMap.put("pointType", "1"); paraMap.put("active",
			 * dimenType);
			 * HttpHelper.doPost(PropertyConfig.getProperty("api_member_url"
			 * )+"member/addPoint", paraMap); }
			 */
			List<MangoMemberPoint> list = commonService
					.searchPointList(paraMap);
			if (null == list || list.size() == 0) {
				commonService.addPoint(memberPk, companyPk, 1, dimenType);
			}
		} catch (Exception e) {
			logger.error("addPoint   ", e);
			e.printStackTrace();
		}
	}

	/**
	 * 添加拼团活动
	 */
	@Override
	public RestCode addBind(B2bBindDto bindDto, String goodsPks) {
		RestCode restCode = RestCode.CODE_0000;
		// 验证商品参团的合法性
		if (null != goodsPks && !"".equals(goodsPks)) {
			String[] goods = StringUtils.splitStrs(goodsPks);
			for (String goodsPk : goods) {
				B2bGoodsDtoEx goodsDtoEx = b2bGoodsService
						.getDetailsById(goodsPk);
				if (null == goodsDtoEx || null == goodsDtoEx.getPk()) {
					return RestCode.CODE_P014;
				}
				if ("binding".equals(goodsDtoEx.getType())) {
					return RestCode.CODE_P016;
				}
				if ("auction".equals(goodsDtoEx.getType())) {
					return RestCode.CODE_P015;
				}
			}
		}
		B2bBind model = new B2bBind();
		String bindPk = KeyUtils.getUUID();
		model.UpdateDTO(bindDto);
		model.setPk(bindPk);
		model.setUpdateTime(new Date());
		model.setInsertTime(new Date());
		model.setStatus(0);// 状态 0未成团 1已成团 2失效
		model.setIsUpDown(2);// 上下架 1:上架， 2:下架
		b2bBindService.addBind(model);
		if (null != goodsPks && !"".equals(goodsPks)) {
			String[] goods = StringUtils.splitStrs(goodsPks);
			for (String goodsPk : goods) {
				B2bGoodsDtoEx oldGoodsEx = b2bGoodsService
						.getDetailsById(goodsPk);
				b2bBindGoodsService.upToBindGoods(bindPk, oldGoodsEx);
				B2bGoodsEx goodsEx = new B2bGoodsEx();
				goodsEx.setPk(goodsPk);
				goodsEx.setType("binding");
				goodsEx.setIsUpdown(2);
				goodsEx.setUpdateTime(new Date());
				b2bGoodsService.updateGoods(goodsEx);// 普通商品状态更新为拼团商品
				// 如果修改商品类型，删除购物车数据
				if (!oldGoodsEx.getType().equals("binding")) {
					b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
				}
			}
		}
		return restCode;
	}

	/**
	 * 编辑拼团活动
	 */
	@Override
	public RestCode editBind(B2bBindDto b2bBindDto, String goodsPks) {
		RestCode restCode = RestCode.CODE_0000;
		// 先验证商品的合法性
		if (!"".equals(goodsPks)) {
			String[] goods = StringUtils.splitStrs(goodsPks);
			for (String goodsPk : goods) {
				B2bGoodsDtoEx goodsDtoEx = b2bGoodsService
						.getDetailsById(goodsPk);
				if (null == goodsDtoEx || null == goodsDtoEx.getPk()) {
					return RestCode.CODE_P014;
				}
				if ("auction".equals(goodsDtoEx.getType())) {
					return RestCode.CODE_P015;
				}
				if (goodsDtoEx.getTotalBoxes() <= 0
						|| goodsDtoEx.getTotalWeight() <= 0) {
					return RestCode.CODE_G004;
				}
			}
		}
		B2bBindDto oldBindDto = b2bBindService.getBind(b2bBindDto.getPk());
		if (null == oldBindDto || null == oldBindDto.getPk()) {
			return RestCode.CODE_P012;
		}
		if (null != oldBindDto && oldBindDto.getIsUpDown() == 1) {
			return RestCode.CODE_P023;
		}
		B2bBind model = new B2bBind();
		model.UpdateDTO(b2bBindDto);
		model.setUpdateTime(new Date());
		b2bBindService.updateEx(model);
		try {
			if (!"".equals(goodsPks)) {
				String[] goods = StringUtils.splitStrs(goodsPks);
				/*
				 * // 1:将删除的bind_goods中的goods修改为正常商品 Map<String, Object> map =
				 * new HashMap<>(); map.put("bindPk", b2bBindDto.getPk());
				 * List<B2bBindGoodsDto> list =
				 * b2bBindGoodsService.getBindGoodList(map); if (null != list &&
				 * list.size() > 0) { B2bGoodsEx goodsEx = new B2bGoodsEx(); for
				 * (int i = 0; i < list.size(); i++) {
				 * goodsEx.setPk(list.get(i).getGoodsPk());
				 * goodsEx.setType("normal"); goodsEx.setIsUpdown(3);
				 * b2bGoodsService.updateGoods(goodsEx);// 拼团商品更新为普通商品 } } //
				 * 2:删除原有bind_goods数据
				 * b2bBindGoodsService.delBindGoodsByBindPk(b2bBindDto.getPk());
				 */
				// 3:插入新的bind_goods数据
				for (String goodsPk : goods) {
					B2bGoodsDtoEx oldGoodsEx = b2bGoodsService
							.getDetailsById(goodsPk);
					b2bBindGoodsService.upToBindGoods(b2bBindDto.getPk(),
							oldGoodsEx);
					B2bGoodsEx goodsEx = new B2bGoodsEx();
					goodsEx.setPk(goodsPk);
					goodsEx.setType("binding");
					goodsEx.setIsUpdown(2);
					goodsEx.setUpdateTime(new Date());
					b2bGoodsService.updateGoods(goodsEx);// 普通商品状态更新为拼团商品
					// 如果修改商品类型，删除购物车数据
					if (!oldGoodsEx.getType().equals("binding")) {
						b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
					}
				}
			}
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 删除拼团活动
	 */
	@Override
	public RestCode delBind(String bindPk) {
		RestCode restCode = RestCode.CODE_0000;
		B2bBindDto bindDto = b2bBindService.getBind(bindPk);
		if (null == bindDto || null == bindDto.getPk()
				|| "".equals(bindDto.getPk())) {
			return RestCode.CODE_S999;
		}
		if (b2bBindService.delBind(bindPk)) {
			Map<String, Object> map = new HashMap<>();
			map.put("bindPk", bindPk);
			List<B2bBindGoodsDto> bindGoodsDtoList = b2bBindGoodsService
					.getBindGoodList(map);
			if (null != bindGoodsDtoList && bindGoodsDtoList.size() > 0) {
				if (b2bBindGoodsService.delBindGoodsByBindPk(bindPk)) {
					B2bBindGoodsDto temp = null;
					B2bGoodsDtoEx oldGoodsEx = null;
					for (int i = 0; i < bindGoodsDtoList.size(); i++) {
						temp = bindGoodsDtoList.get(i);
						oldGoodsEx = b2bGoodsService.getDetailsById(temp
								.getGoodsPk());
						B2bGoodsEx goodsEx = new B2bGoodsEx();
						goodsEx.setPk(temp.getGoodsPk());
						goodsEx.setType("normal");
						goodsEx.setIsUpdown(3);
						goodsEx.setUpdateTime(new Date());
						b2bGoodsService.updateGoods(goodsEx);// 团购商品更新为普通商品
						// 如果修改商品类型，删除购物车数据
						if (!oldGoodsEx.getType().equals("normal")) {
							b2bCartService.delByGoodsPk(temp.getGoodsPk());// 删除购物车中的数据
						}
					}
				}
			}
		} else {
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 查询供拼团选择的商品
	 */
	@Override
	public PageModel<B2bGoodsDtoEx> searchBindGoodsList(Map<String, Object> map) {
		PageModel<B2bGoodsDtoEx> pm = new PageModel<B2bGoodsDtoEx>();
		List<B2bGoodsDtoEx> list = b2bGoodsService.searchBindGoodsList(map);
		int count = b2bGoodsService.searchBindGoodsCount(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	/**
	 * 查询拼团中商品
	 */
	@Override
	public PageModel<B2bBindGoodsDtoEx> searchGoodsListOnBind(Map<String, Object> map) {
		PageModel<B2bBindGoodsDtoEx> pm = new PageModel<B2bBindGoodsDtoEx>();
		List<B2bBindGoodsDtoEx> list = b2bBindGoodsService.searchGoodsListOnBind(map);
		int count = b2bBindGoodsService.searchGoodsListOnBindCount(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	/**
	 * 拼团商品设置为正常商品
	 */
	@Override
	public RestCode backToNormalGoods(String goodsPks, String type) {
		RestCode restCode = RestCode.CODE_0000;
		String[] goods = StringUtils.splitStrs(goodsPks);
		Map<String, Object> map = new HashMap<>();
		List<B2bBindGoodsDto> list = null;
		// 1:验证商品的合法性
		for (String goodsPk : goods) {
			B2bGoodsDtoEx goodsDtoEx = b2bGoodsService.getDetailsById(goodsPk);
			if (null == goodsDtoEx || null == goodsDtoEx.getPk()
					|| "".equals(goodsDtoEx.getPk())) {
				return RestCode.CODE_P017;
			}
			map.put("goodsPk", goodsPk);
			list = b2bBindGoodsService.getBindGoodList(map);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					B2bBindGoodsDto bindGoodsDto = list.get(i);
					B2bBindDto bindDto = b2bBindService.getBind(bindGoodsDto
							.getBindPk());
					if (null != bindDto && null != bindDto.getPk()) {
						if (bindDto.getIsUpDown() == 1) {
							return RestCode.CODE_P018;
						}
					}
				}
			}
		}
		try {
			// 2:把拼团商品设置为正常商品
			for (String goodsPk : goods) {
				B2bGoodsDtoEx oldGoodsEx = b2bGoodsService
						.getDetailsById(goodsPk);
				B2bGoodsEx goodsEx = new B2bGoodsEx();
				goodsEx.setPk(goodsPk);
				goodsEx.setType(type);
				goodsEx.setIsUpdown(3);
				goodsEx.setUpdateTime(new Date());
				b2bGoodsService.updateGoods(goodsEx);// 团购商品更新为普通商品
				map.put("goodsPk", goodsPk);
				b2bBindGoodsService.delBindGoodsByGoodsPk(goodsPk);// 删除b2b_bind_goods中数据
				// 如果修改商品类型，删除购物车数据
				if (!oldGoodsEx.getType().equals(type)) {
					b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
				}
			}
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 设置拼团活动
	 */
	@Override
	public RestCode setBindingActivity(String goodsPks, String bindPk) {
		RestCode restCode = RestCode.CODE_0000;
		String[] temps = StringUtils.splitStrs(goodsPks);
		// 1:检查跟换后的拼团活动的合法性
		B2bBindDto bindDto = b2bBindService.getBind(bindPk);
		B2bGoodsDtoEx oldGoodsEx = null;
		if (null == bindDto) {
			return RestCode.CODE_P012;
		}
		// 2:检验是否有商品在上架的拼团活动中
		for (String goodsPk : temps) {
			oldGoodsEx = b2bGoodsService.getDetailsById(goodsPk);
			if (null == oldGoodsEx.getTotalBoxes()
					|| oldGoodsEx.getTotalBoxes() <= 0) {
				return RestCode.CODE_G004;
			}
			if (b2bBindGoodsService.checkGoodsOnUpBinding(goodsPk)) {
				return RestCode.CODE_P019;
			}
			if ("auction".equals(oldGoodsEx.getType())) {
				return RestCode.CODE_P015;
			}
		}
		// 3:设置拼团活动
		for (String goodsPk : temps) {
			oldGoodsEx = b2bGoodsService.getDetailsById(goodsPk);
			b2bBindGoodsService.delBindGoodsByGoodsPk(goodsPk);// 删除原来的
			b2bBindGoodsService.upToBindGoods(bindPk, oldGoodsEx);// 添加新的
			B2bGoodsEx goodsEx = new B2bGoodsEx();
			goodsEx.setPk(goodsPk);
			goodsEx.setType("binding");
			goodsEx.setIsUpdown(2);
			goodsEx.setUpdateTime(new Date());
			b2bGoodsService.updateGoods(goodsEx);// 商品状态更新为拼团商品
			// 如果修改商品类型，删除购物车数据
			if (!oldGoodsEx.getType().equals("binding")) {
				b2bCartService.delByGoodsPk(goodsPk);// 删除购物车中的数据
			}

		}
		return restCode;
	}

	/**
	 * 上架/下架 拼团活动
	 */
	@Override
	public RestCode upDownBinding(String bindPk, int isUpDown) {
		RestCode restCode = RestCode.CODE_0000;
		B2bBindDto bindDto = b2bBindService.getBind(bindPk);
		if (null == bindDto || null == bindDto.getPk()
				|| "".equals(bindDto.getPk())) {
			return RestCode.CODE_P017;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("bindPk", bindPk);
		List<B2bBindGoodsDto> list = b2bBindGoodsService.getBindGoodList(map);
		// 上架：1：如果拼团下没有商品，不可以上架；2：查看团下面的所有商品，没有库存，商品处于待上架，下架中 团不许上架
		if (isUpDown == 1) {
			if (null == list || list.size() == 0) {
				return RestCode.CODE_P020;
			}
			B2bGoodsDtoEx goodsDtoEx = null;
			for (int i = 0; i < list.size(); i++) {
				goodsDtoEx = b2bGoodsService.getDetailsById(list.get(i)
						.getGoodsPk());
				if (null != goodsDtoEx
						&& (goodsDtoEx.getTotalBoxes() <= 0 || goodsDtoEx
								.getTotalWeight() <= 0.0)) {
					return RestCode.CODE_G004;
				}
				if (null != goodsDtoEx && goodsDtoEx.getIsUpdown() != 2) {
					return RestCode.CODE_G003;
				}
			}
		}

		try {
			B2bBind model = new B2bBind();
			model.setPk(bindPk);
			model.setIsUpDown(isUpDown);
			model.setUpdateTime(new Date());
			b2bBindService.updateEx(model);
			// 1:上架操作
			if (isUpDown == 1) {
				// 如果这个团已经成团，则属于二次上架，（1）取消这个团未转换的需求单，（2）成团状态改为未成团
				if (bindDto.getStatus() == 1) {
					b2bBindOrderService.cancelBindOrderByBindPk(bindPk);
					B2bBind temp = new B2bBind();
					temp.setPk(bindPk);
					temp.setStatus(0);
					temp.setUpdateTime(new Date());
					b2bBindService.updateEx(temp);
				}
				// 更新一下bind_goods表库存;
				B2bGoodsDtoEx goodsDtoEx = null;
				for (int i = 0; i < list.size(); i++) {
					goodsDtoEx = b2bGoodsService.getDetailsById(list.get(i)
							.getGoodsPk());
					B2bBindGoods bindGoodsModel = new B2bBindGoods();
					bindGoodsModel.setPk(list.get(i).getPk());
					bindGoodsModel.setWeight(goodsDtoEx.getTotalWeight());
					bindGoodsModel.setBoxes(goodsDtoEx.getTotalBoxes());
					bindGoodsModel.setTotalBoxes(goodsDtoEx.getTotalBoxes());
					b2bBindGoodsService.updateEx(bindGoodsModel);
				}
			}
			// 2：下架操作
			if (isUpDown == 2) {
				// 未成团需求单取消
				if (bindDto.getStatus() == 0) {
					bindOrderService.cancelBindOrderByBindPk(bindDto.getPk());
				}
			}
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 删除某个拼团活动下的商品
	 */
	@Override
	public RestCode delGoodsInBind(String bindPk, String goodsPks) {
		RestCode restCode = RestCode.CODE_0000;
		String[] temps = StringUtils.splitStrs(goodsPks);
		B2bBindDto bindDto = b2bBindService.getBind(bindPk);
		if (null == bindDto) {
			return RestCode.CODE_P012;
		}
		if (null != bindDto && bindDto.getIsUpDown() == 1) {
			return RestCode.CODE_P023;
		}
		try {
			for (String goodsPk : temps) {
				b2bBindGoodsService.delBindGoodsByGoodsPk(goodsPk);
			}
			// 重新判断该团是否已成团
			Map<String, Object> map = new HashMap<>();
			map.put("bindPk", bindDto.getPk());
			List<B2bBindGoodsDto> list = b2bBindGoodsService
					.getBindGoodList(map);
			if (null == list || list.size() == 0) {
				B2bBind bind = new B2bBind();
				bind.setPk(bindDto.getPk());
				bind.setStatus(0);
				b2bBindService.updateEx(bind);
			}
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (null != list.get(i).getBoxes()
							&& list.get(i).getBoxes() != 0) {
						B2bBind bind = new B2bBind();
						bind.setPk(bindDto.getPk());
						bind.setStatus(0);
						b2bBindService.updateEx(bind);
						break;
					}
				}
			}
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	@Override
	public void updateOverdueBind() {
		b2bBindDaoEx.updateOverdue();
		b2bBindOrderDaoEx.updateOverdue();

	}

	@Override
	public String contractToOrder(String contractNo, B2bMemberDto member,
			String orders, String address) {
		RestCode code = RestCode.CODE_0000;
		List<B2bContractGoods> cgList = new ArrayList<B2bContractGoods>();
		List<B2bOrder> oList = null;
		B2bContractDto contract = b2bContractService.getB2bContract(contractNo);
		Boolean flag = true;
		if (null == contract) {
			code = RestCode.CODE_O001;
			flag = false;
		}
		// System.out.println("=========================="+contractNo+"----------"+contract.getSupplierInfo());
		// 已取消的不能创建订单
		if (flag && -1 == contract.getContractStatus()) {
			code = RestCode.CODE_O010;
			flag = false;
		}
		// 已发货和已完成的订单不能创建订单
		if (flag
				&& (5 == contract.getContractStatus() || 6 == contract
						.getContractStatus())) {
			code = RestCode.CODE_O002;
			flag = false;
		}
		if (null == member) {
			member = new B2bMemberDto();
		}
		try {
			if (flag) {
				if (null != orders || !"".equals(orders)) {
					List<B2bContractGoods> ogList = JsonUtils.toList(orders,
							B2bContractGoods.class);
					for (B2bContractGoods og : ogList) {
						B2bContractGoodsDto cgEntiy = contractGoodsDaoEx
								.getByChildOrderNumber(og.getChildOrderNumber());
						if (null != cgEntiy
								&& null != cgEntiy.getContractStatus()
								&& cgEntiy.getContractNo().equals(contractNo)) {
							// 子合同 3:待发货,4:部分发货
							if (cgEntiy.getContractStatus() == 3
									|| cgEntiy.getContractStatus() == 4) {
								if (null == og.getWeightShipped()
										|| og.getWeightShipped() <= 0) {
									code = RestCode.CODE_G022;
									flag = false;
									break;
								}
								B2bOrderGoodsDtoEx oGood = orderGoodsDaoExt
										.getbyChildContractNo(og
												.getChildOrderNumber());
								// 合同重量-订单重量>参数重量
								if (ArithUtil.sub(cgEntiy.getWeight(),
										oGood.getWeight()) < og
										.getWeightShipped()) {
									code = RestCode.CODE_O004;
									flag = false;
									break;
								} else {
									cgList.add(og);
								}
							} else {// 非待发货，非部分发货状态
								code = RestCode.CODE_O002;
								flag = false;
								break;
							}
						} else {
							code = RestCode.CODE_O001;
							flag = false;
							break;
						}
					}
					if (flag) {
						// 提交订单
						if ("0000".equals(code.getCode())) {
							oList = b2bOrderService.submitContractOrder(
									contract, cgList, member, address);
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error("errorSubmitContractOrder", e);
			code = RestCode.CODE_S999;
		}
		// 订单提交后
		if ("0000".equals(code.getCode())) {
			this.afterOtherOrder(oList, member, null);
		}
		return code.toJson(oList);
	}

	@Override
	public CallBackOrder submitReserve(String orders, B2bCompanyDto c,
			B2bMemberDto m, int source) {
		RestCode code = RestCode.CODE_0000;
		CallBackOrder callBack = new CallBackOrder();
		BatchOrder bo = new BatchOrder();
		Porder o = null;
		// 使用编程式事务处理事务回滚
		TransactionStatus status = txManager
				.getTransaction(new DefaultTransactionDefinition());
		try {
			if (null == c) {
				code = RestCode.CODE_M008;
			} else {
				o = new Porder(orders, c, m);
				o.setSource(source);
				code = prepareOrder(c, o);// 下单前准备
				if ("0000".equals(code.getCode())) {
					BackPgoods bp = getPgoods(o);
					code = bp.getRestCode();
					if ("0000".equals(code.getCode())) {
						Map<String, List<Pgoods>> map = this.diffServ(bp
								.getPgList());
						if ("0000".equals(code.getCode())
								&& map.containsKey("other")) {// 订单
							code = submitReserveOrder(o, map.get("other"), bo);
						}
						if ("0000".equals(code.getCode())) {
							code = this.submitReBatch(bo, o, map);// 统一提交订单
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorSubmitReserve", e);
			code = RestCode.CODE_S999;
		} finally {
			if (null != o.getCarts() && o.getCarts().size() > 0) {
				for (Pgoods pg : o.getCarts()) {
					if (null != pg.getGoodsPk() && !"".equals(pg.getGoodsPk())) {
						JedisUtils.releaseLock(pg.getGoodsPk());
					}
				}
			}
			// 事务提交
			if ("0000".equals(code.getCode())) {
				txManager.commit(status);
				// 事务回滚
			} else {
				txManager.rollback(status);
			}
			callBack.setCode(code);
			callBack.setBorder(bo);
			callBack.setPorder(o);
		}
		return callBack;
	}

	private RestCode submitReBatch(BatchOrder bo, Porder o,
			Map<String, List<Pgoods>> map) {
		RestCode code = RestCode.CODE_0000;
		List<B2bReserveOrder> rlist = bo.getRolist();
		if (null != rlist && rlist.size() > 0) {
			this.reserveOrderDao.insertBatch(rlist);
		}
		// 删除购物车
		if (null != o.getCartList() && o.getCartList().size() > 0) {
			this.cartDaoEx.deleteBatch(o.getCartList());
		}
		return code;
	}

	private RestCode submitReserveOrder(Porder o, List<Pgoods> pgs,
			BatchOrder bo) {
		Map<String, List<Pgoods>> map = new HashMap<String, List<Pgoods>>();
		RestCode code = RestCode.CODE_0000;
		try {
			for (Pgoods pg : pgs) {
				if (!map.containsKey(pg.getGdto().getStorePk())) {
					map.put(pg.getGdto().getStorePk(), new ArrayList<Pgoods>());
				}
				map.get(pg.getGdto().getStorePk()).add(pg);
			}
			// 提交订单
			code = reserveService.submitReserveOrder(o, map, bo);
		} catch (Exception e) {
			logger.error("errorSubmitReserveOrder", e);
			code = RestCode.CODE_S999;
		}
		return code;
	}

	public RestCode transformationOrder(Porder o, List<Pgoods> list) {
		BatchOrder bo = new BatchOrder();
		// 使用编程式事务处理事务回滚
		TransactionStatus status = txManager
				.getTransaction(new DefaultTransactionDefinition());
		Map<String, List<Pgoods>> map = new HashMap<String, List<Pgoods>>();
		map.put("other", list);
		RestCode code = submitOtherOrder(o, list, bo);
		if ("0000".equals(code.getCode())) {
			code = this.submitBatch(bo, o, map);// 统一提交订单
		}
		// 事务提交
		if ("0000".equals(code.getCode())) {
			txManager.commit(status);
			// 事务回滚
		} else {
			txManager.rollback(status);
		}
		this.afterOrder(bo, o);
		return code;
	}

	@Override
	public String reservesToOrder(String orderNumber, B2bMemberDto member) {
		RestCode code = RestCode.CODE_0000;
		String[] pks = StringUtils.splitStrs(orderNumber);
		for (int i = 0; i < pks.length; i++) {
			B2bReserveOrderDto reserve = reserveOrderDao
					.getByOrderNumber(pks[i]);
			// 物流方式
			LogisticsModelDto lmdto = logisticsService.getLogisticModel(reserve
					.getLogisticsModelPk());

			AddressInfo address = JsonUtils.toBean(reserve.getAddressJson(),
					AddressInfo.class);
			B2bCompanyDtoEx purchaser = b2bCompanyService.getCompany(reserve
					.getPurchaserPk());
			B2bCompanyDtoEx invoice = JsonUtils.toBean(
					reserve.getInvoiceJson(), B2bCompanyDtoEx.class);
			List<B2bReserveGoods> rlist = JsonUtils.getStringToList(
					reserve.getGoodsJson(), B2bReserveGoods.class);
			if(null==member){
				// 提交预约单的操作者
				member=b2bMemberService.getByPk(reserve.getMemberPk());
			}
			Porder o = new Porder(reserve, lmdto, address, purchaser, member,
					invoice);
			BackPgoods bp = getPgoodslist(o, rlist);
			code = bp.getRestCode();
			if ("0000".equals(code.getCode())) {
				code = transformationOrder(o, bp.getPgList());
				if ("0000".equals(code.getCode())) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderNumber", reserve.getOrderNumber());
					map.put("orderStatus", 2);
					reserveOrderDao.updateReserve(map);

				}
			}
			for (Pgoods pg : bp.getPgList()) {
				if (null != pg.getGoodsPk() && !"".equals(pg.getGoodsPk())) {
					JedisUtils.releaseLock(pg.getGoodsPk());
				}
			}

		}
		return code.toJson();
	}

	public BackPgoods getPgoodslist(Porder o, List<B2bReserveGoods> rlist) {
		BackPgoods bp = new BackPgoods(RestCode.CODE_0000);
		B2bGoodsDtoMa gdto = null;
		List<Pgoods> pgList = new ArrayList<Pgoods>();
		for (B2bReserveGoods reserve : rlist) {
			Pgoods pg = new Pgoods();
			pg.setBoxes(reserve.getBoxes());
			pg.setGoodsPk(reserve.getGoodsPk());
			pg.setTotalBoxes(reserve.getBoxes());
			if (null == reserve.getWeight()
					|| reserve.getWeight().doubleValue() == 0d) {
				B2bGoodsDto g = b2bGoodsService.getB2bGoods(reserve
						.getGoodsPk());
				if (null != g && null != g.getTareWeight()
						&& g.getTareWeight() > 0d) {
					pg.setWeight(ArithUtil.div(
							reserve.getBoxes() * g.getTareWeight(), 1000));
				} else {
					pg.setWeight(0d);
				}
			} else {
				pg.setWeight(reserve.getWeight().doubleValue());
			}

			BackGoods bg = checkGoods(pg, o.getPurchaseType());
			if (!"0000".equals(bg.getRestCode().getCode())) {
				bp.setRestCode(bg.getRestCode());
				break;
			} else {
				gdto = bg.getGdto();
			}

			pg.setGdto(gdto);
			// 调用物流系统提供的运费
			if (o.getLmdto().getType() == 1 || o.getLmdto().getType() == 2) {
				pg = this.setLogisticsPrice(o, gdto, pg);
			}
			pgList.add(pg);
		}
		bp.setPgList(pgList);
		return bp;
	}

	private void sendCms(String orderNumber, String value,Integer type) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderPk", orderNumber);
			map.put("block", value);
			map.put("type", type);
			cmsService.insertSaleManByOrder(map);
		} catch (Exception e) {
			logger.error("sendcms", e);
		}

	}

	@Override
	public DeliveryDto searchDeliveryDto(String orderNumber, Integer status) {
		DeliveryDto dto = new DeliveryDto();
		dto.setDeliveryBasic(commonService.getDeliveryBasic(orderNumber));
		List<Integer> list = new ArrayList<Integer>();
		if(status == 1){
			list.add(1);
			list.add(2);
		}else{
			list.add(status);
		}
		dto.setDoList(commonService.searchDeliveryOrderList(orderNumber, list));
		dto.setOgList(b2bOrderService.searchOrderGoodsList(orderNumber, true, false));
		dto.setPvList(commonService.searchPayVoucherList(orderNumber, 2));
		return dto;
	}

	@Override
	public String createDeliveryOrder(String orderNumber, String json) {
		List<DeliveryGoods> dg = JsonUtils.toList(json, DeliveryGoods.class);
		return commonService.creditDeliveryOrder(orderNumber, dg);
	}
}
