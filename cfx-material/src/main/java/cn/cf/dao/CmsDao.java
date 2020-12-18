package cn.cf.dao;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.MarketingOrderMemberDto;

public interface CmsDao {
    /**
     * 店铺业务员
     * @param storePk
     * @return
     */
    MarketingOrderMemberDto getSaleByStorePk(String storePk);

    /**
     * 采购商业务员
     * @param purchaserPk
     * @return
     */
    MarketingOrderMemberDto getSaleByPurCompanyPk(String purchaserPk);

    /**
     *根据订单号查询化纤订单
     * @param orderPk
     * @return
     */
    B2bOrderDto getCfOrderByOredrPk(String orderPk);

    /***
     * 新增会员关系
     * @param purDto
     */
    void insertOrderMember(MarketingOrderMemberDto purDto);

	B2bCompanyDto getByPk(String pk);

	B2bCompanyDto getByStorePk(String storePk);
	
	/**
	 * 根据pk查询合同订单
	 * @param orderPk
	 * @return
	 */
	B2bContractDto getCfContractByOredrPk(String orderPk);

	MarketingOrderMemberDto getMarketingOrderMemberByPk(String orderPk);
	
	

}
