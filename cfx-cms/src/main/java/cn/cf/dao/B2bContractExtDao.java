/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bContractExtDto;
import cn.cf.entity.OrderNumEntity;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bContractExtDao  extends B2bContractDao{

    List<B2bContractExtDto> searchGridExt(Map<String, Object> map);
    
    int searchGridCountExt(Map<String, Object> map);

    int updateContract(B2bContractExtDto dto);
    
	OrderNumEntity getContractByPurchaserPk(Map<String, Object> map);

}
