/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bLotteryActivity;
import cn.cf.dto.B2bLotteryActivityDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bLotteryActivityDao {
	int insert(B2bLotteryActivity model);
	int update(B2bLotteryActivity model);
	int delete(String id);
	List<B2bLotteryActivityDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bLotteryActivityDto getByPk(java.lang.String pk); 
	 B2bLotteryActivityDto getByName(java.lang.String name);

}
