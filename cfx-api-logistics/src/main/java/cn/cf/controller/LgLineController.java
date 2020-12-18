package cn.cf.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.cf.common.RestCode;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.dto.SearchLgLineDto;
import cn.cf.entity.GoodInfo;
import cn.cf.entity.SearchLgLine;
import cn.cf.entity.SearchLgPrice;
import cn.cf.service.LgLineService;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("/logistics")
public class LgLineController extends BaseController {

	@Autowired
	private LgLineService lgLineService;
	
	/**
	 * 首页快速找车
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchLgLine", method = RequestMethod.POST)
	public String searchLgLine(HttpServletRequest request) {
		SearchLgLineDto dto=new SearchLgLineDto();
		dto.bind(request);
		if (dto.getWeight()<=0) {
			return RestCode.CODE_P000.toJson();
		}
		SearchLgLine searchLgLine = new SearchLgLine();
		searchLgLine.setFromProvicePk(dto.getFromProvicePk()==null?"":dto.getFromProvicePk());
		searchLgLine.setFromCityPk(dto.getFromCityPk()==null?"":dto.getFromCityPk());
		searchLgLine.setFromAreaPk(dto.getFromAreaPk()==null?"":dto.getFromAreaPk());
		searchLgLine.setFromTownPk(dto.getFromTownPk()==null?"":dto.getFromTownPk());
		searchLgLine.setToProvicePk(dto.getToProvicePk()==null?"":dto.getToProvicePk());
		searchLgLine.setToCityPk(dto.getToCityPk()==null?"":dto.getToCityPk());
		searchLgLine.setToAreaPk(dto.getToAreaPk()==null?"":dto.getToAreaPk());
		searchLgLine.setToTownPk(dto.getToTownPk()==null?"":dto.getToTownPk());
		searchLgLine.setProductPk(dto.getProductPk()==null?"":dto.getProductPk());
		searchLgLine.setGradePk(dto.getGradePk()==null?"":dto.getGradePk());
		try {
			List<LogisticsRouteDto> list = lgLineService.getLogisticsSetpinfos(searchLgLine, dto.getWeight());
			if (list.size()>0) {
				if (list.get(0).getList().size()>0) {
					if (dto.getWeight()<list.get(0).getLeastWeight()) {
						double SalePrice=new BigDecimal((list.get(0).getFreight()/dto.getWeight())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						list.get(0).getList().get(0).setSalePrice(SalePrice);
					}
				}
			}
			
			return RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}

/*	@ApiOperation(value = "订单运费查询", notes = "订单运费查询")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "toProvicePk", value = "送货地", required = false, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "toCityPk", value = "送货地", required = false, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "toAreaPk", value = "送货地", required = false, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "toTownPk", value = "送货地", required = false, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "list", value = "发货地的json字符串", required = false, dataType = "String"),
	})*/
	@RequestMapping(value = "/searchOrderFreight", method = RequestMethod.POST)
	public String searchOrderFreight(@RequestParam("list") String list,
			@RequestParam("toProvicePk") String toProvicePk,
			@RequestParam("toCityPk") String toCityPk,
			@RequestParam("toAreaPk") String toAreaPk,
			@RequestParam("toTownPk") String toTownPk) {
		try {
			SearchLgPrice searchLgPrice = new SearchLgPrice();
			searchLgPrice.setToProvicePk(toProvicePk);
			searchLgPrice.setToCityPk(toCityPk);
			searchLgPrice.setToAreaPk(toAreaPk);
			searchLgPrice.setToTownPk(toTownPk);
			JSONArray json=JSONArray.parseArray(list);
			JSONObject jsonOne;
			List<GoodInfo> goodInfos = new ArrayList<GoodInfo>(); 
			for(int i=0;i<json.size();i++){
			          jsonOne = json.getJSONObject(i); 
			          GoodInfo  goodInfo = new GoodInfo();
			          goodInfo.setFromProvicePk(String.valueOf( jsonOne.get("fromProvicePk")));
			          goodInfo.setFromCityPk(String.valueOf(jsonOne.get("fromCityPk")));
			          goodInfo.setFromAreaPk(String.valueOf(jsonOne.get("fromAreaPk")));
			          goodInfo.setFromTownPk(String.valueOf(jsonOne.get("fromTownPk")));
			          goodInfo.setGradePk(String.valueOf(jsonOne.get("gradePk")));
			          goodInfo.setProductPk(String.valueOf(jsonOne.get("productPk")) );
			          goodInfo.setWeight(Double.parseDouble(jsonOne.get("weight").toString()));
			          goodInfos.add(goodInfo);
			          searchLgPrice.setList(goodInfos);
			 }
			
			List<LogisticsRouteDto> logisticsRouteDtos = lgLineService.getOrderFreight(searchLgPrice);
			return RestCode.CODE_0000.toJson(logisticsRouteDtos);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	

}
