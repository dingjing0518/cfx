package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.dto.LogisticsLinePriceDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.GoodInfo;
import cn.cf.entity.SearchLgLine;
import cn.cf.entity.SearchLgPrice;
import cn.cf.service.LgLineService;
import cn.cf.service.SysService;

@Service
@Transactional
public class LgLineServiceImpl implements LgLineService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	SysService sysService;
	
	/**
	 * 推荐合适的物流供应商
	 * 
	 * @param searchLgLine
	 * @param weight
	 * @param gradePk
	 * @param weight
	 * @return
	 */
	@Override
	public List<LogisticsRouteDto> getLogisticsSetpinfos(SearchLgLine searchLgLine, Double weight) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromProvicePk", searchLgLine.getFromProvicePk());
		map.put("fromCityPk", searchLgLine.getFromCityPk());
		map.put("fromAreaPk", searchLgLine.getFromAreaPk());
		map.put("fromTownPk", searchLgLine.getFromTownPk());
		map.put("toProvicePk", searchLgLine.getToProvicePk());
		map.put("toCityPk", searchLgLine.getToCityPk());
		map.put("toAreaPk", searchLgLine.getToAreaPk());
		map.put("toTownPk", searchLgLine.getToTownPk());
		map.put("productPk", searchLgLine.getProductPk());
		map.put("gradePk", searchLgLine.getGradePk());
		map.put("status", 1);// 线路启用
		map.put("isDelete", 1);
		map.put("companyIsDelete", 1);// 物流公司启用
		map.put("companyIsVisable", 1);
		map.put("companyAuditStatus", 1);
		searchLgLine.setWeight(weight);
		List<LogisticsRouteDto> list = getMatcheRoute(map,searchLgLine);
		return list;
	}
	
	/**
	 * 查询线路
	 */
	private List<LogisticsRouteDto> getMatcheRoute(Map<String, Object> map,SearchLgLine searchLgLine) {
		List<LogisticsRouteDto> list = new ArrayList<LogisticsRouteDto>();
		list = queryMongo(map);
		if (null == list || list.size() == 0) {
			map.put("toTownPk", "");
			list = queryMongo(map);
			if (null == list || list.size() == 0) {
				map.put("toAreaPk", "");
				list = queryMongo(map);
				if (null == list || list.size() == 0) {
					map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
					map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
					map.put("gradePk", "");
					list = queryMongo(map);
					if (null == list || list.size() == 0) {
						map.put("toTownPk", "");
						list = queryMongo(map);
						if (null == list || list.size() == 0) {
							map.put("toAreaPk", "");
							list = queryMongo(map);
							if (null == list || list.size() == 0) {
								map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
								map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
								map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
								map.put("productPk", "");
								list = queryMongo(map);
								if (null == list || list.size() == 0) {
									map.put("toTownPk", "");
									list = queryMongo(map);
									if (null == list || list.size() == 0) {
										map.put("toAreaPk", "");
										list = queryMongo(map);
										if (null == list || list.size() == 0) {
											map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
											map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
											map.put("gradePk", "");
											list = queryMongo(map);
											if (null == list || list.size() == 0) {
												map.put("toTownPk", "");
												list = queryMongo(map);
												if (null == list || list.size() == 0) {
													map.put("toAreaPk", "");
													list = queryMongo(map);
													if (null == list || list.size() == 0) {
														map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
														map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
														map.put("productPk", searchLgLine.getProductPk()==null?"":searchLgLine.getProductPk());
														map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
														map.put("fromTownPk", "");
														list = queryMongo(map);
														if (null == list || list.size() == 0) {
															map.put("fromAreaPk", "");
															list = queryMongo(map);
															if (null == list || list.size() == 0) {
																map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																map.put("gradePk", "");
																list = queryMongo(map);
																if (null == list || list.size() == 0) {
																	map.put("fromTownPk", "");
																	list = queryMongo(map);
																	if (null == list || list.size() == 0) {
																		map.put("fromAreaPk", "");
																		list = queryMongo(map);
																		if (null == list || list.size() == 0) {
																			map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																			map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																			map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
																			map.put("productPk", "");
																			list = queryMongo(map);
																			if (null == list || list.size() == 0) {
																				map.put("fromTownPk", "");
																				list = queryMongo(map);
																				if (null == list || list.size() == 0) {
																					map.put("fromAreaPk", "");
																					list = queryMongo(map);
																					if (null == list || list.size() == 0) {
																						map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																						map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																						map.put("gradePk", "");
																						list = queryMongo(map);
																						if (null == list || list.size() == 0) {
																							map.put("fromTownPk", "");
																							list = queryMongo(map);
																							if (null == list || list.size() == 0) {
																								map.put("fromAreaPk", "");
																								list = queryMongo(map);
																							}
																							
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (null!=list.get(i).getLeastWeight()&&searchLgLine.getWeight() < list.get(i).getLeastWeight()) {
					List<LogisticsLinePriceDto> dtoList = new ArrayList<LogisticsLinePriceDto>();
					LogisticsLinePriceDto dto = new LogisticsLinePriceDto();
					dto.setFromWeight(0.0);
					dto.setToWeight(searchLgLine.getWeight());
					dto.setBasisPrice(list.get(i).getBasicPrice()); // 结算价
					dto.setSalePrice(list.get(i).getFreight()); // 对外价
					dtoList.add(dto);
					list.get(i).setList(dtoList);
				} else {
					Query query = new Query();
					query.addCriteria(Criteria.where("linePk").is(list.get(i).getPk()));
					query.addCriteria(Criteria.where("fromWeight").lte(searchLgLine.getWeight()));
					query.addCriteria(Criteria.where("toWeight").gt(searchLgLine.getWeight()));
					Criteria stepInfo = new Criteria();
					stepInfo.andOperator(Criteria.where("linePk").is(list.get(i).getPk()),
							Criteria.where("fromWeight").lte(searchLgLine.getWeight()), Criteria.where("toWeight").gt(searchLgLine.getWeight()));
					Query query1 = new Query(stepInfo);
					List<LogisticsLinePriceDto> logisticsPrice = mongoTemplate.find(query1,
							LogisticsLinePriceDto.class);
					if (null != logisticsPrice && logisticsPrice.size() > 0) {
						list.get(i).setList(logisticsPrice);
					} else {
						list.remove(i);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 商品的运费单价
	 * 
	 * @param searchLgPrice
	 */
	@Override
	public List<LogisticsRouteDto> getOrderFreight(SearchLgPrice searchLgPrice) {
		SearchLgLine searchLgLine=new SearchLgLine();
		searchLgLine.setToProvicePk(searchLgPrice.getToProvicePk()==null?"":searchLgPrice.getToProvicePk());
		searchLgLine.setToCityPk(searchLgPrice.getToCityPk()==null?"":searchLgPrice.getToCityPk());
		searchLgLine.setToAreaPk(searchLgPrice.getToAreaPk()==null?"":searchLgPrice.getToAreaPk());
		searchLgLine.setToTownPk(searchLgPrice.getToTownPk()==null?"":searchLgPrice.getToTownPk());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("toProvicePk", searchLgPrice.getToProvicePk()==null?"":searchLgPrice.getToProvicePk());
		map.put("toCityPk", searchLgPrice.getToCityPk()==null?"":searchLgPrice.getToCityPk());
		map.put("toAreaPk", searchLgPrice.getToAreaPk()==null?"":searchLgPrice.getToAreaPk());
		map.put("toTownPk", searchLgPrice.getToTownPk()==null?"":searchLgPrice.getToTownPk());
		map.put("status", 1);
		map.put("isDelete", 1);
		List<LogisticsRouteDto> list = null;
		List<LogisticsRouteDto> lists = new ArrayList<LogisticsRouteDto>();
		if (searchLgPrice.getList() != null && searchLgPrice.getList().size() > 0) {
			for (GoodInfo goodInfo : searchLgPrice.getList()) {
				list = new ArrayList<LogisticsRouteDto>();
				map.put("fromProvicePk", goodInfo.getFromProvicePk()==null?"":goodInfo.getFromProvicePk());
				map.put("fromCityPk", goodInfo.getFromCityPk()==null?"":goodInfo.getFromCityPk());
				map.put("fromAreaPk", goodInfo.getFromAreaPk()==null?"":goodInfo.getFromAreaPk());
				map.put("fromTownPk", goodInfo.getFromTownPk()==null?"":goodInfo.getFromTownPk());
				map.put("productPk", goodInfo.getProductPk()==null?"":goodInfo.getProductPk());
				map.put("gradePk", goodInfo.getGradePk()==null?"":goodInfo.getGradePk());
				searchLgLine.setFromProvicePk(goodInfo.getFromProvicePk()==null?"":goodInfo.getFromProvicePk());
				searchLgLine.setFromCityPk(goodInfo.getFromCityPk()==null?"":goodInfo.getFromCityPk());
				searchLgLine.setFromAreaPk(goodInfo.getFromAreaPk()==null?"":goodInfo.getFromAreaPk());
				searchLgLine.setFromTownPk(goodInfo.getFromTownPk()==null?"":goodInfo.getFromTownPk());
				searchLgLine.setProductPk(goodInfo.getProductPk()==null?"":goodInfo.getProductPk());
				
				list = getMatcheRoute(map,searchLgLine);
				if (list.size() > 0) {
					lists.add(list.get(0));
				} else {
					LogisticsRouteDto dto = new LogisticsRouteDto();
					lists.add(dto);
				}
			}
		}
		return lists;
	}


	/**
	 * 查询mongo数据库
	 * 
	 * @param map
	 * @return
	 */
	private List<LogisticsRouteDto> queryMongo(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		//System.out.println(query);
		List<LogisticsRouteDto> logisticsLine = mongoTemplate.find(query, LogisticsRouteDto.class);
		return logisticsLine;
	}


/*	@Override
	@Transactional
	public Integer selectByStatus() {
		int vert = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isMatched", 0);
		map.put("isAbnormal", 1);
		List<SearchLgLine> orNumList = lgDeliveryOrderDaoEx.selectByStatus(map);
		if (orNumList.size() > 0) {
			for (SearchLgLine list : orNumList) {
				map = new HashMap<String, Object>();
				map.put("fromProvicePk", list.getFromProvicePk());
				map.put("fromCityPk", list.getFromCityPk());
				map.put("fromAreaPk", list.getFromAreaPk());
				map.put("fromTownPk", list.getFromTownPk());
				map.put("toProvicePk", list.getToProvicePk());
				map.put("toCityPk", list.getToCityPk());
				map.put("toAreaPk", list.getToAreaPk());
				map.put("toTownPk", list.getToTownPk());
				map.put("productPk", list.getProductPk());
				map.put("gradePk", list.getGradePk());
				map.put("status", 1);
				map.put("isDelete", 1);
				map.put("companyIsDelete", 1);// 物流公司启用
				map.put("companyIsVisable", 1);
				map.put("companyAuditStatus", 1);
				// 自动匹配线路
				vert = updateLgCompany(map, list);
			}
		}
		return vert;
	}*/
	
/*	private int updateLgCompany(Map<String, Object> map, SearchLgLine searchLgLine) {
		int vert = 1;
		// 查找匹配线路
		List<LogisticsRouteDto> list = getMatcheRoute(map,   searchLgLine.getToAreaPk() ,  searchLgLine.getToTownPk() ,
				searchLgLine.getGradePk(),searchLgLine.getWeight(),searchLgLine.getFromTownPk());
		
		// 跟新配送物流
		vert = updatePushLgCompany(list,searchLgLine);
		return vert;
	}*/
	
/*	private Integer updatePushLgCompany(List<LogisticsRouteDto> list,SearchLgLine searchLgLine) {
		// vert:2 匹配成功；3：线路不存在 ；<2 :匹配失败
		int vert = 1;
		if (null != list && list.size() > 0) {
			Double basisLinePrice = list.get(0).getList().get(0).getBasisPrice();
			Double salePrice = list.get(0).getList().get(0).getSalePrice();
			Map<String, Object> lgmap = new HashMap<String, Object>();
			lgmap.put("logisticsCompanyPk", list.get(0).getCompanyPk());
			lgmap.put("logisticsCompanyName", list.get(0).getCompanyName());
			lgmap.put("basisLinePrice",basisLinePrice);
			lgmap.put("linePricePk", list.get(0).getList().get(0).getPk());
			lgmap.put("settleWeight", searchLgLine.getWeight());
			lgmap.put("presentTotalFreight", searchLgLine.getWeight() * salePrice);
			lgmap.put("originalTotalFreight", searchLgLine.getWeight() * salePrice );
			lgmap.put("originalFreight", salePrice);
			lgmap.put("presentFreight", salePrice);
			lgmap.put("goodsOriginalFreight", searchLgLine.getWeight() * salePrice);
			lgmap.put("goodsPresentFreight", searchLgLine.getWeight() * salePrice);
			lgmap.put("isMatched", 1);
			lgmap.put("pk", searchLgLine.getPk());
			vert = savePushLgCompanyName(lgmap);// 更新物流公司，order_goods表单价
		} else {
			vert = 3;
		}
		return vert;
	}*/
	
/*	private int savePushLgCompanyName(Map<String, Object> map) {
		int temp = lgDeliveryOrderDaoEx.savePushLgCompanyName(map)+ lgOrderGoodsDaoEx.updateOrderGoodsByDeliveryPk(map);	
		return temp;
	}*/

/*	@Override
	public LogisticsRouteDto selectLineByPk(String linePk) {
		return lgLineDaoEx.selectLineByPk(linePk);
	}*/

/*	@Override
	public LogisticsLinePriceDto selectLinePriceByPk(String linePricePk) {
		return lgLineDaoEx.selectLinePriceByPk(linePricePk);
	}*/
	
	
}
