/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bManageRegionDto;
import cn.cf.dto.B2bManageRegionExtDto;
import cn.cf.model.B2bManageRegion;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bManageRegionExtDao extends  B2bManageRegionDao{
	int updateObj(B2bManageRegionExtDto dto);
	List<B2bManageRegionExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);


}
