/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bSpecExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bSpecExtDao extends B2bSpecDao{
	
	List<B2bSpecExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);
	
	int updateSpec(B2bSpecExtDto dto);
	
	int isExtSpec(Map<String, Object> map);
}
