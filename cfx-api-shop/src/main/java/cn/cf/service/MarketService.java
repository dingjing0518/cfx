package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bFuturesTypeDto;
import cn.cf.entity.B2bFutures;
import cn.cf.entity.Meg;
import cn.cf.entity.Oil;
import cn.cf.entity.Pta;

public interface MarketService {

    List<Meg> searchMegList( int days);

    List<Meg> searchMegList( int days, String name);

    List<Pta> searchPtaList( int days, String name);

    List<Oil> searchOilList( int days);

	List<B2bFuturesTypeDto> searchFuturesType(Map<String, Object> map);

	/**
	 * 行情数据
	 * @param days  天数
	 * @param futuresPk  期货品种pk
	 * @param block 板块 cf(化纤)，sx（纱线）
	 * @return
	 */
	List<B2bFutures> searchFutures(Integer days, String futuresPk,String block);
}
