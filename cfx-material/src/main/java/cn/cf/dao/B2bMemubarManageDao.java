/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bMemubarManage;
import cn.cf.dto.B2bMemubarManageDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemubarManageDao {
	int insert(B2bMemubarManage model);
	int update(B2bMemubarManage model);
	int delete(String id);
	List<B2bMemubarManageDto> searchGrid(Map<String, Object> map);
	List<B2bMemubarManageDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bMemubarManageDto getByPk(String pk);
	 B2bMemubarManageDto getByUrl(String url);
	 B2bMemubarManageDto getByName(String name);
	 B2bMemubarManageDto getByUrlLink(String urlLink);

}
