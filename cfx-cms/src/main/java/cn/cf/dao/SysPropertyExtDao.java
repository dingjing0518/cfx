/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.SysPropertyDto;
import cn.cf.model.SysProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysPropertyExtDao extends SysPropertyDao{

	List<SysPropertyDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);

	List<SysPropertyDto> getByProductType(@Param("type") Integer type,@Param("productPk")String productPk);

	int updateObj(SysPropertyDto dto);
}
