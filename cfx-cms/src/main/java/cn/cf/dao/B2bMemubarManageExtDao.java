/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bMemubarManageExtDto;
import cn.cf.model.B2bMemubarManage;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemubarManageExtDao extends  B2bMemubarManageDao{

	int updateObj(B2bMemubarManageExtDto dto);
	List<B2bMemubarManageExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);

}
