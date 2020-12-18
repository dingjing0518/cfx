package cn.cf.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.BillType;
import cn.cf.constant.Block;
import cn.cf.constant.GoodsType;
import cn.cf.constant.OrderRecordType;
import cn.cf.constant.UnitType;
import cn.cf.dao.B2bAuctionOfferDaoEx;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.B2bOrderDaoEx;
import cn.cf.dao.B2bOrderGoodsDaoEx;
import cn.cf.dao.B2bPlantDaoEx;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dao.LogisticsModelDao;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.ExportOrderDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bContractGoodsDtoMa;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bGoods;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bOrderEx;
import cn.cf.model.B2bOrderGoods;
import cn.cf.model.B2bOrderGoodsEx;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bOrderRecordService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bPayVoucherService;
import cn.cf.service.CommonService;
import cn.cf.service.ForeignBankService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;

@Service
public class B2bOrderServiceImpl implements B2bOrderService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private B2bOrderDaoEx orderDaoExt;

	@Autowired
	private B2bOrderGoodsDaoEx orderGoodsDaoExt;

	@Autowired
	private B2bCompanyDaoEx companyDaoEx;

	@Autowired
	private B2bStoreDaoEx storeDao;

	@Autowired
	private B2bGoodsDaoEx goodsDaoEx;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;

	@Autowired
	private B2bPayVoucherService b2bPayVoucherService;

	@Autowired
	private LogisticsModelDao logisticsModelDao;

	@Autowired
	private B2bPlantDaoEx b2bPlantDao;
 
	@Autowired
	private CommonService commonService;

	@Autowired
	private B2bAuctionOfferDaoEx auctionOfferDaoEx;

	@Autowired
	private ForeignBankService foreignBankService;
	
    @Autowired
    private B2bContractGoodsDaoEx  contractGoodsDaoEx;
 
	@Autowired
	private B2bOrderRecordService b2bOrderRecordService;
	
    
	@Override
	public PageModel<B2bOrderDtoEx> searchOrderList(Map<String, Object> map) {
		PageModel<B2bOrderDtoEx> pm = new PageModel<B2bOrderDtoEx>();
		List<B2bOrderDtoEx> list = orderDaoExt.searchB2bOrderList(map);
		Map<String, Object> cre = new HashMap<String, Object>();
		if (null != list && list.size() > 0) {
			// 订单商品列表
			Map<String, Object> o = new HashMap<String, Object>();
			for (B2bOrderDtoEx order : list) {
				o.put("orderNumber", order.getOrderNumber());
				order.setOrderGoodsList(searchOrderGoodsListByMap(o));
				//康海订单“温馨提示”
				//if (order.getStorePk().equals(PropertyConfig.getProperty("storePk"))&&(order.getOrderStatus()==0||order.getOrderStatus()==1)&&Block.CF.getValue().equals(order.getBlock())) {
                if (null != order.getEconomicsGoodsType() && order.getEconomicsGoodsType()==2&&(order.getOrderStatus()==0||order.getOrderStatus()==1)&&Block.CF.getValue().equals(order.getBlock())) {
					String purchaserPk=order.getPurchaserPk();
					cre.put("companyPk",purchaserPk);
					cre.put("goodsType",order.getEconomicsGoodsType());
					cre.put("status", 2);
					cre.put("isVisable", 1);
					List<B2bCreditGoodsDto> creditGoodsList = commonService.searchCreditGoods(cre);
					if (null != creditGoodsList && creditGoodsList.size()>0) {
						StringBuilder promnt = new StringBuilder("温馨提示：");
						//待审核
						if (order.getOrderStatus()==0) {
							for (B2bCreditGoodsDto b2bCreditGoodsDto : creditGoodsList) {
								B2bEconomicsGoodsDto b2bEconomicsGoodsDto = commonService.getEconomicsGoods(b2bCreditGoodsDto.getEconomicsGoodsPk());
								if (null!=b2bEconomicsGoodsDto) {
									//尾款
									Double balance = ArithUtil.round(ArithUtil.mul(order.getActualAmount(), b2bEconomicsGoodsDto.getProportion()), 2);
									Integer balancePer = (int) ArithUtil.mul(100, b2bEconomicsGoodsDto.getProportion());
									//预付款
									Double advance = ArithUtil.sub(order.getActualAmount(),balance);
									Integer advancePer = (int)ArithUtil.sub(100,balancePer);
									promnt.append("如果该订单使用化纤贷产品"+b2bEconomicsGoodsDto.getName()+"，先付"+advance+"（"+advancePer+"%预付款）至康海账户");
									promnt.append("<br/>");
									promnt.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								}
							}
						}
						//待付款
						if (order.getOrderStatus()==1) {
							for (B2bCreditGoodsDto b2bCreditGoodsDto : creditGoodsList) {
								B2bEconomicsGoodsDto b2bEconomicsGoodsDto = commonService.getEconomicsGoods(b2bCreditGoodsDto.getEconomicsGoodsPk());
								if (null!=b2bEconomicsGoodsDto) {
									//尾款
									Double balance = ArithUtil.round(ArithUtil.mul(order.getActualAmount(), b2bEconomicsGoodsDto.getProportion()), 2);
									Integer balancePer = (int) ArithUtil.mul(100, b2bEconomicsGoodsDto.getProportion());
									promnt.append("如果该订单使用化纤贷产品"+b2bEconomicsGoodsDto.getName()+"，支付尾款"+balance+"（"+balancePer+"%尾款）至康海账户");
									promnt.append("<br/>");
									promnt.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								}
							}
						}
						order.setPromnt(promnt.toString());
					}
				}
				// 付款凭证列表
				List<B2bPayVoucher> voucherList = b2bPayVoucherService.searchB2bPayVoucherList(order.getOrderNumber());
				if (null != voucherList && voucherList.size() > 0) {
					order.setPayVoucherList(voucherList);
				}
				if (null != order.getPaymentType()&&  order.getPaymentType() == 3) {
					try {
						B2bLoanNumberDto boanNumber = foreignBankService.getB2bLoanNumberDto(order.getOrderNumber());
						if (null != boanNumber) {
							order.setLoanNumberStatus(boanNumber.getLoanStatus());
							order.setBank(boanNumber.getBankName());
							order.setEconomicsGoodsType(boanNumber.getEconomicsGoodsType());
						}
					} catch (Exception e) {
						logger.error("借据状态" + e);
					}
				}
				if (null != order.getPaymentType()&&  order.getPaymentType() == 4) {
					try {
						B2bOnlinepayRecordDto onlineRecords = foreignBankService
								.getOnlinepayRecordDto(order.getOrderNumber());
						if (null != onlineRecords) {
							order.setOnlinePayStatus(onlineRecords.getStatus());
						}

					} catch (Exception e) {
						logger.error("线上支付状态" + e);
					}
				}
				if (null != order.getPaymentType()
						&&  order.getPaymentType() == 5) {
					try {
						B2bBillOrderDto billOrder = foreignBankService
								.getBillOrder(order.getOrderNumber());
						if (null != billOrder && null != billOrder.getStatus()) {
							//1处理中 2完成 3取消  4锁定  
							order.setBillPayStatus(billOrder.getStatus());
							order.setBillGoodsType(BillType.TX.equals(billOrder.getGoodsShotName())?
									BillType.TX:BillType.PFT);
							//锁定状态下的特殊处理
							if(null != billOrder.getSerialNumber() && !"".equals(billOrder.getSerialNumber())
									&& 4 == billOrder.getStatus()){
								List<B2bBillInventoryDto> inventoryList = foreignBankService.searchBillInventoryList(order.getOrderNumber());
								if(null != inventoryList && inventoryList.size()>0){
									for(B2bBillInventoryDto i : inventoryList){
										if(null !=i.getStatus() && (1 ==i.getStatus()) ||
												2 ==i.getStatus() || 3==i.getStatus()){
											order.setBillPayStatus(2);
											break;
										}
									}
								}
							}
							//显示票付通按钮
							if((null == billOrder.getSerialNumber() || "".equals(billOrder.getSerialNumber()))
									&& BillType.PFT.equals(order.getBillGoodsType())){
								order.setShowPft(1);//1显示票付通按钮
							}
						}

					} catch (Exception e) {
						logger.error("票据支付状态" + e);
					}
				}


			}
		}
		int count = orderDaoExt.searchB2bOrderCounts(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public B2bOrderDtoEx getOrder(String orderNumber) {
		return orderDaoExt.getB2bOrder(orderNumber);
	}

	@Override
	public RestCode submitOrder(Porder o, Map<String, List<Pgoods>> map,
			BatchOrder bo) {
		boolean falge = true;
		List<Pgoods> list = null;
		B2bOrder model = null;
		String orderNumber = null;
		B2bOrderGoods og = null;
		List<B2bOrderGoods> oglist = new ArrayList<B2bOrderGoods>();
		List<B2bOrder> olist = new ArrayList<B2bOrder>();
		B2bStoreDto sdto = null;
		String cpk = null;
		B2bCompanyDto p = null;
		B2bAuctionOfferDto offer = null;
		// 出价单
		if (null != o.getOfferPk() && !"".equals(o.getOfferPk())) {
			offer = auctionOfferDaoEx.getByPk(o.getOfferPk());
		}
		// 根据营销公司对订单商品进行拆分
		for (String key : map.keySet()) {
			p = companyDaoEx.getByPk(key);
			if ("-1".equals(p.getParentPk())) {
				cpk = p.getPk();
			} else {
				cpk = p.getParentPk();
			}
			sdto = storeDao.getByCompanyPk(cpk);
			list = map.get(key);
			// 先绑定所有业务员,拆分正常商品和预售商品
			Map<String, List<Pgoods>> opMap = openGoodsBySalesman(list, o
					.getCompany().getPk(),offer);
			// 如果业务员没有返回
			if (null == opMap) {
				falge = false;
				break;
			}
			// 对不需要改价正常的商品进行业务员分组
			for(String m : opMap.keySet()){
				Map<String, List<Pgoods>> nopMap = combinationGoods(
						opMap.get(m), 1);
				// 对不需要改价正常的商品进行订单拆分
				for (String k : nopMap.keySet()) {
					double orderAmount = 0.00;
					//区分订单板块
					if(m.contains(Block.CF.getValue())){
						orderNumber = Block.CF.getOrderType()+KeyUtils.getOrderNumber();
					}else{
						orderNumber = Block.SX.getOrderType()+KeyUtils.getOrderNumber();
					}
					List<Pgoods> pgList = nopMap.get(k);
					if (null == pgList) {
						break;
					}
					int childCount=0;
					for (Pgoods pg : pgList) {
						childCount++;
						og = new B2bOrderGoodsEx(pg, orderNumber, m.contains(GoodsType.PRESALE.getValue())?0:1, offer,o.getLmdto().getType(),childCount);// 该订单进入待付款
						oglist.add(og);
						if(m.contains(Block.CF.getValue())){
							orderAmount = ArithUtil.add(
									orderAmount,
									ArithUtil.add(ArithUtil.round(ArithUtil.mul(og.getPresentPrice()+
											(null==pg.getGdto().getCfGoods().getAdminFee()?0d:pg.getGdto().getCfGoods().getAdminFee())+
											(null==pg.getGdto().getCfGoods().getLoadFee()?0d:pg.getGdto().getCfGoods().getLoadFee())+
											(null==pg.getGdto().getCfGoods().getPackageFee()?0d:pg.getGdto().getCfGoods().getPackageFee()), og.getWeight()), 2), 
											ArithUtil.round(og.getPresentTotalFreight(), 2)));
						}else{
							orderAmount = ArithUtil.add(
									orderAmount,
									ArithUtil.add(ArithUtil.round(ArithUtil.mul(og.getPresentPrice(), og.getWeight()), 2), 
											ArithUtil.round(og.getPresentTotalFreight(), 2)));
							
						}
					}
					if(ArithUtil.round(orderAmount, 2)==0){
						return RestCode.CODE_G015;
					}
					// 非预售判断库存
					if(!m.contains(GoodsType.PRESALE.getValue())){
						falge = this.judgeBoxesAndWeight(pgList);
					}
					if (falge) {
						model = new B2bOrderEx(o, p,sdto, orderNumber, m.contains(GoodsType.PRESALE.getValue())?0:1,
								orderAmount, pgList.get(0).getMdto());
					}else{
						break;
					}
					olist.add(model);
				}
			}
		}
		if (falge) {
			bo.setOglist(oglist);
			bo.setOlist(olist);
		}
		return RestCode.CODE_0000;
	}
 

	/**
	 * 订单商品根据业务员为最小单位划分
	 * 
	 * @param list
	 * @param purchaserPk
	 *            采购商
	 * @return
	 */
	public Map<String, List<Pgoods>> openGoodsBySalesman(List<Pgoods> list,
			String purchaserPk,B2bAuctionOfferDto offer) {
		Map<String, List<Pgoods>> map = new HashMap<String, List<Pgoods>>();
		// 业务员拆分好后 再根据是否满足分货条件进行拆单(此时订单商品列表增加上订单号)
		List<Pgoods> cfnList = new ArrayList<Pgoods>();// 化纤正常商品
		List<Pgoods> cfpList = new ArrayList<Pgoods>();// 化纤预售商品
		List<Pgoods> sxnList = new ArrayList<Pgoods>();// 纱线正常商品
		List<Pgoods> sxpList = new ArrayList<Pgoods>();// 纱线预售商品
		List<Pgoods> cfbindList = new ArrayList<Pgoods>();// 化纤拼团商品
		List<Pgoods> sxbindList = new ArrayList<Pgoods>();// 纱线拼团商品
		List<Pgoods> cfauctionList = new ArrayList<Pgoods>();// 化纤竞拍商品
		List<Pgoods> sxauctionList = new ArrayList<Pgoods>();// 纱线竞拍商品
		if (null != list && list.size() > 0) {
			// 先根据业务员拆单
			for (Pgoods goods : list) {
				// 此处调用member系统返回业务员信息
				B2bGoodsDtoMa g = goods.getGdto();
				B2bMemberDto memberDto = b2bCustomerSaleManService.getSaleMan(
								g.getCompanyPk(), purchaserPk, null !=g.getCfGoods()?
										g.getCfGoods().getProductPk():null,g.getStorePk());
				if (null != memberDto) {
					goods.setMdto(memberDto);
				}
				if(null != offer){
					if(Block.CF.getValue().equals(g.getBlock())){
						cfnList.add(goods);
					}else{
						sxnList.add(goods);
					}
				}else{
					if(GoodsType.PRESALE.getValue().equals(g.getType())
							&& Block.CF.getValue().equals(g.getBlock())){
						cfpList.add(goods);
					}
					if((GoodsType.NORMAL.getValue().equals(g.getType())
							|| GoodsType.SALE.getValue().equals(g.getType()))
							&& Block.CF.getValue().equals(g.getBlock())){
						cfnList.add(goods);
					}
					if(GoodsType.PRESALE.getValue().equals(g.getType())
							&& Block.SX.getValue().equals(g.getBlock())){
						sxpList.add(goods);
					}
					if((GoodsType.NORMAL.getValue().equals(g.getType())
							|| GoodsType.SALE.getValue().equals(g.getType()))
							&& Block.SX.getValue().equals(g.getBlock())){
						sxnList.add(goods);
					}
					
				 
					if(GoodsType.BINDING.getValue().equals(g.getType())
							&& Block.CF.getValue().equals(g.getBlock())){
						cfbindList.add(goods);
					}
					if(GoodsType.BINDING.getValue().equals(g.getType())
							&& Block.SX.getValue().equals(g.getBlock())){
						sxbindList.add(goods);
					}
					if(GoodsType.AUCTION.getValue().equals(g.getType())
							&& Block.CF.getValue().equals(g.getBlock())){
						cfauctionList.add(goods);
					}
					if(GoodsType.AUCTION.getValue().equals(g.getType())
							&& Block.SX.getValue().equals(g.getBlock())){
						sxauctionList.add(goods);
					}
					
					
				}
			}
			
			map.put("cfnormal", cfnList);
			map.put("cfpresale", cfpList);
			map.put("sxnormal", sxnList);
			map.put("sxpresale", sxpList);
			
			map.put("cfbinding", cfbindList);
			map.put("cfauction", cfauctionList);
			map.put("sxbinding", sxbindList);
			map.put("sxauction", sxauctionList);
		}
		return map;
	}
 

	/**
	 * 对订单商品进行同一类别组合
	 * 
	 * @param list
	 * @param type
	 *            1业务员 2商品
	 * @return
	 */
	public Map<String, List<Pgoods>> combinationGoods(List<Pgoods> list,
			Integer type) {
		Map<String, List<Pgoods>> skuIdMap = new HashMap<String, List<Pgoods>>();
		if (null != list && list.size() > 0) {
			String key = null;
			for (Pgoods p : list) {
				// 根据会员Pk分组(业务员)
				if (type == 1) {
					if (null != p.getMdto()) {
						key = p.getMdto().getPk();
					} else {
						key = "";
					}
					// 根据商品pk分组
				} else {
					key = p.getGdto().getPk();
				}
				List<Pgoods> tempList = skuIdMap.get(key);
				/* 如果取不到数据,那么直接new一个空的ArrayList* */
				if (tempList == null) {
					tempList = new ArrayList<Pgoods>();
					tempList.add(p);
					skuIdMap.put(key, tempList);
				} else {
					/* 某个sku之前已经存放过了,则直接追加数据到原来的List里* */
					tempList.add(p);
				}
			}
		}
		return skuIdMap;
	}

 

	/**
	 * 根据包装批拆订单商品包装
	 * 
	 * @param list
	 * @return
	 */
//	private List<Pgoods> openOrderGoodsByPack(List<Pgoods> list) {
//		if (null == list || list.size() == 0) {
//			return null;
//		}
//		List<Pgoods> newList = new ArrayList<Pgoods>();
//		for (int j = 0; j < list.size(); j++) {
//			Pgoods p = list.get(j);
//			B2bGoodsDtoMa good = p.getGdto();
//			// 根据品名、店铺、箱重(大于等于)、包装数量倒序
//			if(Block.CF.getValue().equals(good.getBlock())){
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("storePk", good.getStorePk());
//				map.put("productPk", good.getCfGoods().getProductPk());
//				map.put("batchNumber",  good.getCfGoods().getBatchNumber());
//				map.put("tareWeight", good.getTareWeight());
//				List<B2bPackNumberDto> packList = b2bPackNumberDaoEx.getB2bPackNumberByGoods(map);
//				if (null != packList && packList.size() > 0) {
//					// 根据购买箱数与包装箱数取余
//					Integer otherCount = p.getBoxes();
//					for (int k = 0; k < packList.size(); k++) {
//						if (otherCount < packList.get(k).getBucketsNum()) {
//							continue;
//						}
//						Integer counts = otherCount / packList.get(k).getBucketsNum();
//						Pgoods np = (Pgoods) p.clone();
//						np.setBoxes(counts * packList.get(k).getBucketsNum());
//						np.setWeight(ArithUtil.round(ArithUtil.div(ArithUtil.mul(good.getTareWeight(), np.getBoxes()), 1000	), 5));
//						np.setPackNumber(packList.get(k).getPackNumber());
//						newList.add(np);
//						// 如果取余等于0 包装全部匹配完成
//						if (otherCount % packList.get(k).getBucketsNum() == 0) {
//							otherCount = 0;
//							break;
//							// 如果取余等于1 包装无需匹配
//						} else if (otherCount % packList.get(k).getBucketsNum() == 1) {
//							otherCount = 1;
//							break;
//							// 如果取余不等于0 则继续匹配
//						} else {
//							otherCount = otherCount % packList.get(k).getBucketsNum();
//						}
//					}
//					// 如果有剩余的下箱数
//					if (otherCount > 0) {
//						Pgoods np = (Pgoods) p.clone();
//						np.setBoxes(otherCount);
//						np.setWeight(ArithUtil.round(ArithUtil.div(ArithUtil.mul(good.getTareWeight(), np.getBoxes()), 1000	), 5));
//						np.setPackNumber("");
//						newList.add(np);
//					}
//					// 如果没有包装
//				} else {
//					newList.add(p);
//				}
//			}else{
//				newList.add(p);
//			}
//		}
//		return newList;
//	}

	/**
	 * 验证库存
	 * 
	 * @param list
	 *            传入商品的总库存和购买的商品数
	 * @param glist
	 *            需要更新的库存
	 * @return
	 */
	public boolean judgeBoxesAndWeight(List<Pgoods> list) {
		boolean flag = true;
		Map<String, List<Pgoods>> map = this.combinationGoods(list, 2);
		if (null != map) {
			for (String k : map.keySet()) {
				List<Pgoods> pgList = map.get(k);
				Integer boxes = 0;
				Double weight = 0d;
				if (null == pgList || pgList.size() == 0) {
					continue;
				}
				B2bGoodsDto gdto = pgList.get(0).getGdto();
				for (int i = 0; i < pgList.size(); i++) {
					boxes += pgList.get(0).getBoxes();
					weight = ArithUtil.add(weight, ArithUtil.div(
							ArithUtil.mul(pgList.get(0).getBoxes(),
									gdto.getTareWeight()), 1000));
				}
				if (gdto.getTotalBoxes() < boxes
						|| gdto.getTotalWeight() < weight) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 验证库存
	 * 
	 * @param list
	 *            传入商品的总库存和购买的商品数
	 * @param glist
	 *            需要更新的库存
	 * @return
	 */
/*	public boolean judgeBindBoxesAndWeight(List<B2bBindOrderGoodsDtoEx> list,
			List<UpdateGoods> glist) {
		boolean flag = true;
		Map<String, List<B2bBindOrderGoodsDtoEx>> map = this
				.combinationBindGoods(list, 2);
		if (null != map) {
			for (String k : map.keySet()) {
				List<B2bBindOrderGoodsDtoEx> pgList = map.get(k);
				Integer boxes = 0;
				Double weight = 0d;
				if (null == pgList || pgList.size() == 0) {
					continue;
				}
				B2bGoodsDto gdto = pgList.get(0).getGdto();
				for (int i = 0; i < pgList.size(); i++) {
					// B2bCartDto cdto = pgList.get(i).getCdto();
					// boxes += cdto.getBoxes();
					boxes += pgList.get(0).getBoxes();
					weight = ArithUtil.add(weight, ArithUtil.div(
							ArithUtil.mul(pgList.get(0).getBoxes(),
									gdto.getTareWeight()), 1000));
				}
				if (gdto.getTotalBoxes() < boxes
						|| gdto.getTotalWeight() < weight) {
					flag = false;
					break;
				}
//				Integer otherBoxes = gdto.getTotalBoxes() - boxes;
//				Double otherWeight = ArithUtil.sub(gdto.getTotalWeight(),
//						weight);
				glist.add(new UpdateGoods(gdto.getPk(), boxes, weight));
			}
		}
		return flag;
	}*/

	@Override
	public B2bOrderDtoEx updateOrderStatus(B2bOrder o) {
		B2bOrderDtoEx order = orderDaoExt.getB2bOrder(o.getOrderNumber());
		// 订单已是要做操作的状态 不做处理
		if (null == order || o.getOrderStatus() == order.getOrderStatus()) {
			order = null;
		} else {
			orderDaoExt.updateOrder(o);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNumber", o.getOrderNumber());
			map.put("orderStatus", o.getOrderStatus());
			orderGoodsDaoExt.updateOrderStatus(map);
		}
		return order;
	}

	@Override
	public B2bOrderDtoEx updateOrder(B2bOrder order,
			List<B2bOrderGoods> goodsList) {
		B2bOrderDtoEx orderEx = orderDaoExt.getB2bOrder(order.getOrderNumber());
		if (null != orderEx) {
			orderDaoExt.updateOrder(order);
			if (null != goodsList && goodsList.size() > 0) {
				for (B2bOrderGoods o : goodsList) {
					orderGoodsDaoExt.updateOrderGoods(o);
				}
			}
		}
		return orderEx;
	}

	@Override
	public List<B2bOrderGoodsDtoEx> searchOrderGoodsList(String orderNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNumber", orderNumber);
		return searchOrderGoodsListByMap(map);
	}

	@Override
	public void cancelOrder(B2bOrderDtoEx orderEx,
			List<B2bOrderGoodsDtoEx> goodsList) {
		B2bOrder order = new B2bOrder();
		order.setOrderNumber(orderEx.getOrderNumber());
		order.setOrderStatus(-1);
		orderDaoExt.updateOrder(order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNumber", orderEx.getOrderNumber());
		map.put("orderStatus", -1);
		orderGoodsDaoExt.updateOrderStatus(map);
		if(null!=orderEx.getOrderClassify()&&orderEx.getOrderClassify()!=2){
			if (null != goodsList && goodsList.size() > 0) {
				for (B2bOrderGoodsDtoEx dtoEx : goodsList) {
					B2bGoodsDto goods = goodsDaoEx.getByPk(dtoEx.getGoodsPk()); 
					if (GoodsType.PRESALE.getValue().equals(goods.getType())) {
						continue;
					}
					// 还原库存
					B2bGoods g = new B2bGoods();
					g.UpdateDTO(goods);
					g.setTotalBoxes(goods.getTotalBoxes()
							+ (dtoEx.getAfterBoxes() == 0 ? dtoEx.getBoxes()
									: dtoEx.getAfterBoxes()));
					g.setTotalWeight(ArithUtil.add(goods.getTotalWeight(), dtoEx
							.getAfterWeight() == 0d ? dtoEx.getAfterWeight()
							: dtoEx.getWeight()));
					goodsDaoEx.update(g);
				}
			}
		}
		commonService.cancelOrder(order.getOrderNumber());
	}

	@Override
	public Map<String, Object> searchOrderCounts(Map<String, Object> map) {

		return orderDaoExt.orderStatusCounts(map);
	}

	@Override
	public List<ExportOrderDto> exportOrderList(Map<String, Object> map) {
		List<ExportOrderDto> list=orderDaoExt.exportOrderList(map);
		Map<String,Object> info = new HashMap<String,Object>();
		for(ExportOrderDto dto:list){
			String purchaserInfo=dto.getPurchaserInfo();
			if(null != purchaserInfo &&!"".equals(purchaserInfo)){
				info=JsonUtils.stringToCollect(purchaserInfo);
				dto.setPurchaserName(info.containsKey("purchaserName")?info.get("purchaserName").toString():"");
			}else{
				dto.setPurchaserName("");
			}
			String supplierInfo=dto.getSupplierInfo();
			if(null != supplierInfo && !"".equals(supplierInfo)){
				info=JsonUtils.stringToCollect(supplierInfo);
				dto.setSupplierName(info.containsKey("supplierName")?info.get("supplierName").toString():"");
			}else{
				dto.setSupplierName("");
			}
			String addressInfo=dto.getAddressInfo();
			if (null!=addressInfo&&!"".equals(addressInfo)) {
				info=JsonUtils.stringToCollect(addressInfo);
				dto.setProvinceName(null==info.get("provinceName")?"":info.get("provinceName").toString());
				dto.setCityName(null==info.get("cityName")?"":info.get("cityName").toString());
				dto.setAreaName(null==info.get("areaName")?"":info.get("areaName").toString());
				dto.setTownName(null==info.get("townName")?"":info.get("townName").toString());
				dto.setAddress(null==info.get("address")?"":info.get("address").toString());
				dto.setContacts(null==info.get("contacts")?"":info.get("contacts").toString());
				dto.setContactsTel(null==info.get("contactsTel")?"":info.get("contactsTel").toString());
			}else{
				dto.setProvinceName("");
				dto.setCityName("");
				dto.setAreaName("");
				dto.setTownName("");
				dto.setAddress("");
				dto.setContacts("");
				dto.setContactsTel("");
			}
			 String weights = new BigDecimal(dto.getWeight().toString()).toPlainString();
             String afterWeights = new BigDecimal(dto.getAfterWeight().toString()).toPlainString();
             String weightNoShippeds = new BigDecimal(dto.getWeightNoShipped().toString()).toPlainString();
             String weightShippeds = new BigDecimal(dto.getWeightShipped().toString()).toPlainString();
             Double otherPrice = 0d;
             if (null !=dto.getBlock()&&Block.CF.getValue().equals(dto.getBlock())){
				String goodsInfo = dto.getGoodsInfo();
				info = JsonUtils.stringToCollect(goodsInfo);
				if (null != info && !"".equals(info)) {
					String unit = null==info.get("unit")?"":info.get("unit")+"";
					String plantName = (null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : "");
					StringBuffer buf = new StringBuffer();
					buf.append(null != info.get("productName") && !"".equals(info.get("productName")) ? info.get("productName") : "");
					buf.append(null != info.get("varietiesName") && !"".equals(info.get("varietiesName")) ? info.get("varietiesName") : "");
					buf.append(null != info.get("seriesName") && !"".equals(info.get("seriesName")) ? info.get("seriesName") : "");
					buf.append(null != info.get("batchNumber") && !"".equals(info.get("batchNumber")) ? info.get("batchNumber") : "");
					buf.append(null != info.get("gradeName") && !"".equals(info.get("gradeName")) ? info.get("gradeName") : "");
					String d = buf.toString();
					dto.setGoodsInfo(d);
                    dto.setProductName(null==info.get("productName")?"":info.get("productName").toString());
                    dto.setVarietiesName(null==info.get("varietiesName")?"":info.get("varietiesName").toString());
                    dto.setSeedvarietyName(null==info.get("seedvarietyName")?"":info.get("seedvarietyName").toString());
                    dto.setSpecName(null==info.get("specName")?"":info.get("specName").toString());
                    dto.setSpecifications(null==info.get("specifications")?"":info.get("specifications").toString());
                    dto.setSeriesName(null==info.get("seriesName")?"":info.get("seriesName").toString());
                    dto.setBatchNumber(null==info.get("batchNumber")?"":info.get("batchNumber").toString());
                    dto.setGradeName(null==info.get("gradeName")?"":info.get("gradeName").toString());
                    dto.setDistinctNumber(null==info.get("distinctNumber")?"":info.get("distinctNumber").toString());
                    dto.setPackNumber(null==info.get("packNumber")?"":info.get("packNumber").toString());
                    dto.setPlantName(plantName);
                    dto.setWeightS(weights+"吨");
                    dto.setcWeightS(dto.getWeight());
                    dto.setBoxesS(dto.getBoxes()+""+UnitType.fromInt(unit).toString());
                    dto.setAfterBoxesS(dto.getAfterBoxes()+""+UnitType.fromInt(unit).toString());
	                dto.setAfterWeightS(afterWeights+"吨");
	                dto.setcAfterWeightS(dto.getAfterWeight());
	                dto.setcAfterBoxesS(dto.getAfterBoxes());
                    dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+""+UnitType.fromInt(unit).toString());
                    dto.setcBoxesNoShippedS(dto.getBoxesNoShipped());
                    dto.setWeightNoShippedS(weightNoShippeds+"吨");
                    dto.setcWeightNoShippedS(dto.getWeightNoShipped());
                    dto.setWeightShippedS(weightShippeds+"吨");
                    dto.setcWeightShippedS(dto.getWeightShipped());


                    dto.setBoxesShippedS(dto.getBoxesShipped()+""+UnitType.fromInt(unit).toString());
                    dto.setcBoxesShippedS(dto.getBoxesShipped());
                    otherPrice =  (null==info.get("packageFee")?0d:Double.parseDouble(info.get("packageFee").toString()))+
                    		(null==info.get("loadFee")?0d:Double.parseDouble(info.get("loadFee").toString()))+
                    		(null==info.get("adminFee")?0d:Double.parseDouble(info.get("adminFee").toString()));
				}
             }else if (null !=dto.getBlock()&&Block.SX.getValue().equals(dto.getBlock())){
				String goodsInfo=dto.getGoodsInfo();
				info=JsonUtils.stringToCollect(goodsInfo);
				if (null!=info&&!"".equals(info)) {
					String unit = null==info.get("unit")?"":info.get("unit")+"";
					String plantName = (null != info.get("plantName") && !"".equals(info.get("plantName")) ? info.get("plantName").toString() : "");
					StringBuffer buf = new StringBuffer();
					buf.append(null != info.get("rawMaterialParentName") && !"".equals(info.get("rawMaterialParentName")) ? info.get("rawMaterialParentName") : "");
					buf.append(null != info.get("rawMaterialName") && !"".equals(info.get("rawMaterialName")) ? info.get("rawMaterialName") : "");
					buf.append(null != info.get("technologyName") && !"".equals(info.get("technologyName")) ? info.get("technologyName") : "");
					buf.append(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName") : "");
					buf.append(null != info.get("tshortName") && !"".equals(info.get("tshortName")) ? info.get("tshortName") : "");
					String d = buf.toString();
					dto.setPlantName(plantName);
					dto.setGoodsInfo(d);
					dto.setBrandName(null==info.get("brandName")?"":info.get("brandName").toString());
					dto.setTechnologyName(null==info.get("technologyName")?"":info.get("technologyName").toString());
					dto.setRawMaterialParentName(null==info.get("rawMaterialParentName")?"":info.get("rawMaterialParentName").toString());
					dto.setRawMaterialName(null==info.get("rawMaterialName")?"":info.get("rawMaterialName").toString());
					dto.setSpecName(null==info.get("specName")?"":info.get("specName").toString());
                    dto.setMaterialName(null==info.get("materialName")?"":info.get("materialName").toString());
					dto.setWeightS(weights+"千克");
	                dto.setBoxesS(dto.getBoxes()+""+UnitType.fromInt(unit).toString());
	                dto.setAfterBoxesS(dto.getAfterBoxes()+""+UnitType.fromInt(unit).toString());
	                dto.setAfterWeightS(afterWeights+"千克");
	                dto.setBoxesNoShippedS(dto.getBoxesNoShipped()+""+UnitType.fromInt(unit).toString());
	                dto.setWeightNoShippedS(weightNoShippeds+"千克");
	                dto.setWeightShippedS(weightShippeds+"千克");
	                dto.setBoxesShippedS(dto.getBoxesShipped()+""+UnitType.fromInt(unit).toString());
				}
			}
             //todo 其他优惠
			dto.setActualAmount(ArithUtil.sub(ArithUtil.add(ArithUtil.round(ArithUtil.mul((null == dto.getPresentPrice() ? 0d : dto.getPresentPrice()) + otherPrice
					, 0d == dto.getAfterWeight() ? dto.getWeight() : dto.getAfterWeight()), 2), dto.getPresentTotalFreight()), null == dto.getOtherDiscount() ? 0d : dto.getOtherDiscount()));
		}
		return list;
	}

	@Override
	public B2bOrderGoodsDtoEx getTotalPrice(String orderNumber) {
		Map<String,Object> map = new HashMap<String,Object>();
		B2bOrderGoodsDtoEx dtoex = new  B2bOrderGoodsDtoEx();
		map.put("orderNumber", orderNumber);
		map.put("cancel", "no");
		List<B2bOrderGoodsDtoEx> list =  searchOrderGoodsListByMap(map);
		if(null !=list && list.size()>0){
			Double totalPrice = 0d;
			Double totalFreight = 0d;
			for(B2bOrderGoodsDtoEx dto : list){
				totalPrice += dto.getPresentTotalPrice();
				totalFreight += dto.getPresentFreight();
			}
			dtoex.setTotalPrice(totalPrice);
			dtoex.setTotalFreight(totalFreight);
		}
		return dtoex;
	}



	@Override
	public List<B2bOrderGoodsDtoEx> searchOrderGoodsList(String orderNumber,
			boolean flag,boolean cancel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNumber", orderNumber);
		// 查未完成发货的商品
		if (!flag) {
			map.put("flag", "no");
		}
		if (!cancel) {
			map.put("cancel", "no");
		}
		return searchOrderGoodsListByMap(map);
	}

	@Override
	public void memberPointByOrder(B2bMemberDto memberDto, B2bOrderDtoEx o) {
		// 会员加积分只限自采订单
		if (1 == o.getPurchaseType()) {
			commonService.addPointForOrder(o.getOrderNumber(), 1);
		}

		try {
			// 给会员加额外奖励积分（只针对交易维度）
			commonService.addExtPointForOrder(o.getOrderNumber(), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	@Override
	public List<B2bOrder> submitContractOrder(B2bContractDto contract,List<B2bContractGoods> cgList,B2bMemberDto member,String address) {
		String orderNumber= Block.SX.getValue().equals(contract.getBlock())?Block.SX.getOrderType()+KeyUtils.getOrderNumber()
				:Block.CF.getOrderType()+KeyUtils.getOrderNumber();
		double orderAmount = 0.00;
		B2bOrderGoodsEx og = null;
		B2bOrder model = null;
		List<B2bOrder> olist = new ArrayList<B2bOrder>();
		List<B2bOrderGoods> oglist = new ArrayList<B2bOrderGoods>();
		for (B2bContractGoods c:cgList) {
			B2bContractGoodsDto cgEntity=contractGoodsDaoEx.getByChildOrderNumber(c.getChildOrderNumber());
				og = new B2bOrderGoodsEx( orderNumber,cgEntity,c,contract.getBlock());// 该订单进入待审核
				oglist.add(og);
				B2bContractGoodsDtoMa cm = new B2bContractGoodsDtoMa();
				cm.UpdateMine(cgEntity);
				if(Block.CF.getValue().equals(cgEntity.getBlock())){
					orderAmount = ArithUtil.add(
							orderAmount,
							ArithUtil.add(ArithUtil.round(ArithUtil.mul(og.getPresentPrice()+
									(null==cm.getCfGoods().getAdminFee()?0d:cm.getCfGoods().getAdminFee())+
									(null==cm.getCfGoods().getLoadFee()?0d:cm.getCfGoods().getLoadFee())+
									(null==cm.getCfGoods().getPackageFee()?0d:cm.getCfGoods().getPackageFee()), 
									og.getWeight()), 2), og.getPresentTotalFreight()));
				}else{
					orderAmount = ArithUtil.add(
							orderAmount,
							ArithUtil.add(ArithUtil.round(ArithUtil.mul(og.getPresentPrice(), og.getWeight()), 2), og.getPresentTotalFreight()));
				}
		}
		model = new B2bOrderEx(contract, orderNumber,  orderAmount,member ,address);
		olist.add(model);
		this.orderGoodsDaoExt.insertBatch(oglist);
		this.orderDaoExt.insertBatch(olist);
		return olist;
	}

	@Override
	public B2bOrderDtoMa updateOrderStatus(B2bOrder o,SysCompanyBankcardDto card) {
		B2bOrderDtoMa om = null;
		B2bOrderDtoEx order = orderDaoExt.getB2bOrder(o.getOrderNumber());
		// 订单已是要做操作的状态 不做处理
		if (null == order || o.getOrderStatus() == order.getOrderStatus()) {
			order = null;
		} else {
			om = new B2bOrderDtoMa();
			om.UpdateMine(order);
			if(null != card){
				om.getSupplier().setBankName(card.getBankName());
				om.getSupplier().setBankAccount(card.getBankAccount());
				om.getSupplier().setBankNo(card.getBankNo());
				o.setSupplierInfo(JSON.toJSONString(om.getSupplier()));
			}
			orderDaoExt.updateOrder(o);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNumber", o.getOrderNumber());
			map.put("orderStatus", o.getOrderStatus());
			orderGoodsDaoExt.updateOrderStatus(map);
		}
		return om;
	}
	
	private List<B2bOrderGoodsDtoEx> searchOrderGoodsListByMap(Map<String,Object> map){
		List<B2bOrderGoodsDtoEx> list =  orderGoodsDaoExt.searchOrderGoodsList(map);
		List<B2bOrderGoodsDtoEx> newList =  new ArrayList<B2bOrderGoodsDtoEx>();;
		if(null !=list && list.size()>0){
			for(B2bOrderGoodsDtoEx dto : list){
				B2bOrderGoodsDtoMa om = new B2bOrderGoodsDtoMa();
				om.UpdateMine(dto);
				if(Block.CF.getValue().equals(dto.getBlock())){
					Double adminFee = null == om.getCfGoods().getAdminFee()?0d:om.getCfGoods().getAdminFee();
					Double loadFee = null == om.getCfGoods().getLoadFee()?0d:om.getCfGoods().getLoadFee();
					Double packageFee = null == om.getCfGoods().getPackageFee()?0d:om.getCfGoods().getPackageFee();
					dto.setOriginalTotalPrice( ArithUtil.round(ArithUtil.mul(om.getOriginalPrice()+adminFee+
							loadFee+packageFee,om.getWeight()),2));
					//todo 计算商品总价
					dto.setPresentTotalPrice(ArithUtil.sub(ArithUtil.round(ArithUtil.mul(om.getPresentPrice() + adminFee +
									loadFee + packageFee,
							(0d == om.getAfterWeight() ? om.getWeight() : om.getAfterWeight())), 2), null == dto.getOtherDiscount() ? 0d : dto.getOtherDiscount()));
				}else{
					dto.setOriginalTotalPrice(ArithUtil.round(ArithUtil.mul(om.getOriginalPrice(), 
							om.getWeight()), 2));
					dto.setPresentTotalPrice(ArithUtil.sub(ArithUtil.round(ArithUtil.mul(om.getPresentPrice(),
							(0d == om.getAfterWeight() ? om.getWeight() : om.getAfterWeight())), 2), null == dto.getOtherDiscount() ? 0d : dto.getOtherDiscount()));
				}
				dto.setDiscountPrice((dto.getOriginalPrice()-dto.getPresentPrice()>0?
						ArithUtil.round(ArithUtil.sub(dto.getOriginalPrice(), dto.getPresentPrice()), 2):0d));
//				B2bGoodsDto goods = goodsDaoEx.getByPk(om.getGoodsPk());
//				if(null != goods){
//					dto.setTareWeight(goods.getTareWeight());
//				}
				newList.add(dto);
			}
		}
		return newList;
	}

	@Override
	public void autoCompleteOrder(int days,int orderStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderStatus", orderStatus);
		List<B2bOrderDtoEx> list = orderDaoExt.searchB2bOrderList(map);
		Date date = DateUtil.dateBeforeNowDay(days,23,59,59);
		List<OrderRecord> recordList = new ArrayList<>();
		for (B2bOrderDtoEx b2bOrderDtoEx : list) {
			B2bLoanNumberDto loan = foreignBankService.getB2bLoanNumberDto(b2bOrderDtoEx.getOrderNumber());
			//化纤贷未还款成功的不做处理
			if(null != loan && (loan.getEconomicsGoodsType() == 2 || loan.getEconomicsGoodsType() == 4)
					&& loan.getLoanStatus() != 5){
				continue;
			}
			boolean updateFlag=false;//是否符合更新条件
			String orderNumber = b2bOrderDtoEx.getOrderNumber();
			//5是部分发货,mongoDb有多条记录
			if (orderStatus==5) {
				Criteria criteria = new Criteria().andOperator(
						Criteria.where("orderNumber").is(orderNumber),
						Criteria.where("status").is(orderStatus),
						Criteria.where("insertTime").gt(DateUtil.formatDateAndTime(date))
						);
				Query query = new Query(criteria);
				recordList = mongoTemplate.find(query, OrderRecord.class);
				if (recordList.size()==0) {
					updateFlag = true;
				}
			}else {
				Criteria criteria = new Criteria().andOperator(
						Criteria.where("orderNumber").is(orderNumber),
						Criteria.where("status").is(orderStatus),
						Criteria.where("insertTime").lte(DateUtil.formatDateAndTime(date))
						);
				Query query = new Query(criteria);
				recordList = mongoTemplate.find(query, OrderRecord.class);
				if (recordList.size()>0) {
					updateFlag = true;
				}
			}
			if (updateFlag) {
				//1：更新订单状态
				B2bOrder order = new B2bOrder();
				order.setOrderNumber(orderNumber);
				order.setOrderStatus(6);
				order.setCompleteTime(new Date());
				orderDaoExt.updateOrder(order);
				//2：更新子订单状态
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("orderNumber", orderNumber);
				tempMap.put("orderStatus", 6);
				orderGoodsDaoExt.updateOrderStatus(tempMap);
				//3：存订单日志
				b2bOrderRecordService.insertOrderRecord(null,order,OrderRecordType.UPDATE.toString(),"订单号:"+orderNumber+"，订单待收货已超过"+days+"天,系统自动完成收货;");
			}
		}
	}
	
}
