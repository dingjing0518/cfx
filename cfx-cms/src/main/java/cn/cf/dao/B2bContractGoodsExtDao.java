/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.dto.B2bContractGoodsExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bContractGoodsExtDao extends B2bContractGoodsDao{
    List<B2bContractGoodsExtDto> searchGridExt(Map<String, Object> map);
    int searchGridCountExt(Map<String, Object> map);
    int updateContractGoods(B2bContractGoodsDto dto);
    B2bContractGoodsExtDto getTotalPriceAndFreight(String contractNo);
}
