/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bDimensionalityRewardExtDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author
 * @version 1.0
 * @since 1.0
 */
public interface B2bDimensionalityRewardExtDao extends B2bDimensionalityRewardDao {

    int updateDimenReward(B2bDimensionalityRewardExtDto extDto);

    List<B2bDimensionalityRewardExtDto> searchGridExt(Map<String, Object> map);

    int searchGridExtCount(Map<String, Object> map);

    int batchDel(@Param("dimenCategory")int dimenCategory);

    List<B2bDimensionalityRewardExtDto> isExistName(Map<String, Object> map);

    /**
     * 逻辑删除奖励维度
     * @param pk
     * @return
     */
    int logicDelete(@Param("pk") String pk);
    
}
