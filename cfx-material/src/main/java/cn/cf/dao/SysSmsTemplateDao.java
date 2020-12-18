/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.model.SysSmsTemplate;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysSmsTemplateDao {
	int insert(SysSmsTemplate model);
	int update(SysSmsTemplate model);
	int delete(String id);
	List<SysSmsTemplateDto> searchGrid(Map<String, Object> map);
	List<SysSmsTemplateDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysSmsTemplateDto getByTemplateCode(java.lang.String templateCode); 
	 SysSmsTemplateDto getByFreeSignName(java.lang.String freeSignName); 
	 SysSmsTemplateDto getByContent(java.lang.String content); 
	 SysSmsTemplateDto getByName(java.lang.String name); 
	
	 

}
