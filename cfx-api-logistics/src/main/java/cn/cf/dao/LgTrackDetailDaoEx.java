

package cn.cf.dao;

import cn.cf.dto.LgTrackDetailDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgTrackDetailDaoEx extends LgTrackDetailDao {
	
	//根据运货单查询查询物流轨迹
	List<LgTrackDetailDto> selectTrackDetailBydeliveryPk(Map<String,Object> par);

}
