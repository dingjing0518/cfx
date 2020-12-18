/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bOnlinepayGoodsExtDto;
import cn.cf.model.B2bOnlinepayGoods;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOnlinepayGoodsExtDao extends B2bOnlinepayGoodsDao {

    List<B2bOnlinepayGoodsExtDto> searchGridExt(Map<String, Object> map);
    int searchGridExtCount(Map<String, Object> map);
    int updateObj(B2bOnlinepayGoodsExtDto dto);
}
