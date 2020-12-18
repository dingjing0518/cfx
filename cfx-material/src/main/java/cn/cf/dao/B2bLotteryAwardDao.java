/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bLotteryAward;
import cn.cf.dto.B2bLotteryAwardDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
public interface B2bLotteryAwardDao {
	int insert(B2bLotteryAward model);

	int update(B2bLotteryAward model);

	int delete(String id);

	List<B2bLotteryAwardDto> searchGrid(Map<String, Object> map);

	List<B2bLotteryAwardDto> searchList(Map<String, Object> map);

	int searchGridCount(Map<String, Object> map);
	
	B2bLotteryAwardDto getByPk(java.lang.String pk);

	B2bLotteryAwardDto getByActivityPk(java.lang.String activityPk);

	B2bLotteryAwardDto getByName(java.lang.String name);

	B2bLotteryAwardDto getByAwardName(java.lang.String awardName);

	B2bLotteryAwardDto getByRelevancyPk(java.lang.String relevancyPk);

	B2bLotteryAwardDto getByAwardRule(java.lang.String awardRule);

	int updateAwardObj(B2bLotteryAward dto);

}
