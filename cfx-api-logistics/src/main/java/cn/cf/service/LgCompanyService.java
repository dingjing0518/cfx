package cn.cf.service;


import java.util.List;
import java.util.Map;
import cn.cf.dto.ForB2BLgPriceDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgDeliveryOrderForB2BDto;
import cn.cf.dto.LgDeliveryOrderForB2BPTDto;
import cn.cf.dto.LgSearchPriceForB2BDto;

public interface LgCompanyService {
	
	/**
	 * 根据name查询物流承运商
	 * @param logisticsName
	 * @return
	 */
	public LgCompanyDto getLogisticsByName(String logisticsName);
	
    /**
     * 商城确认发货(商家承运)
     * @param lgDeliveryOrderForB2BDto
     * @return
     */
    boolean confirmFaHuoForB2BSJ(LgDeliveryOrderForB2BDto lgDeliveryOrderForB2BDto );
	
    /**
     * 提供给商城查询线路价格
     * @param lgSearchPriceForB2BDto
     * @return
     */
    ForB2BLgPriceDto searchLgPriceForB2B(LgSearchPriceForB2BDto lgSearchPriceForB2BDto);
    
    
    /**
     * 商城确认发货（平台承运）
     * @param lgDeliveryOrderForB2BPTDto
     * @return
     */
    boolean confirmFaHuoForB2BPT(LgDeliveryOrderForB2BPTDto lgDeliveryOrderForB2BPTDto );
    
    
    
    /**
     * 商城下单时根据线路pk,阶梯价pk查询运费对外总价
     * @param lgLinePk,线路pk
     * @param lgLineStepPk,阶梯价pk
     * @return
     */
    ForB2BLgPriceDto searchLgPriceForB2BByLinePk(String lgLinePk,String lgLineStepPk );

	/**
	 * 查询所有承运商
	 * @param map
	 * @return
	 */
	public List<LgCompanyDto> searchLgCompanyList(Map<String, Object> map);
    
	
	
    
}
