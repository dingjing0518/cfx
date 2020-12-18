/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bVarietiesExtDto;

/**
 * 
 * @author
 * @version 1.0
 * @since 1.0
 * */
public interface B2bVarietiesExtDao extends B2bVarietiesDao {

	int searchGridExtCount(Map<String, Object> map);

	List<B2bVarietiesExtDto> searchGridExt(Map<String, Object> map);

	int updateVarieties(B2bVarietiesExtDto extDto);

    int getByCondition(Map<String, Object> m);
}
