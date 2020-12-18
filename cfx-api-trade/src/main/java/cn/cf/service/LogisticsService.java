package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.LogisticsErpBubbleDto;
import cn.cf.dto.LogisticsErpBubbleDtoEx;
import cn.cf.dto.LogisticsErpDto;
import cn.cf.dto.LogisticsErpDtoEx;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.BackLogistics;
import cn.cf.entity.LogisticsCart;

public interface LogisticsService {
	/**
	 * 获取物流方式
	 * 
	 * @param pk
	 * @return
	 */
	LogisticsModelDto getLogisticModel(String pk);

	/**
	 * 获取物流方式
	 * 
	 * @return
	 */
	List<LogisticsModelDto> searchLogisticModelList();

	/**
	 * 根据阶梯运费Pk获取阶梯价格运费
	 * 
	 * @param logisticsPk
	 * @return
	 */

	/**
	 * 调取物流模板
	 * 
	 * @param logisticsModelPk
	 * @param addressPk
	 * @param cartPks
	 * @param goodsPk
	 * @param boxes
	 * @param goods
	 * @return
	 */

	/**
	 * 新增编辑物流模板
	 * 
	 * @param dto
	 * @param memberPk
	 * @param companyPk
	 * @return
	 */

	/**
	 * 删除物流模板
	 * 
	 * @param dto
	 * @return
	 */

	/**
	 * 分页查询物流模板
	 * 
	 * @param map
	 * @return
	 */

	/**
	 * 获取物流模板详情
	 * 
	 * @param pk
	 * @return
	 */

	/**
	 * 批量更新物流模板(保存至mongoDB)
	 * 
	 * @param storePk
	 * @return
	 */


	/**
	 * 增，删，改物流模板
	 * 
	 * @param dto
	 * @return
	 */
	RestCode updateLogisticsErp(LogisticsErpDtoEx dto);

	PageModel<LogisticsErpDto> searchLogisticsErpList(Map<String, Object> map);

	RestCode delLogisticsErp(LogisticsErpDtoEx dto);

	/**
	 * 增，删，改 泡货管理
	 * 
	 * @param dto
	 * @return
	 */
	RestCode updateLogisticsErpBubble(LogisticsErpBubbleDtoEx dto);

	RestCode delLogisticsErpBubble(LogisticsErpBubbleDtoEx dto);

	PageModel<LogisticsErpBubbleDto> searchBubbleList(Map<String, Object> map);

	BackLogistics getLogisticsErpPrice(String logisticsModelPk, String addressPk,
			String goods, String purchaserPk,Integer type);

	LogisticsErpDto getLogisticsErp(String pk);

	LogisticsErpBubbleDto getBubbleDetail(String pk);

	Map<String, Object> searchLogisticsErpCounts(Map<String, Object> map);

	Map<String, Object> searchBubbleCounts(Map<String, Object> map);

	RestCode updateBubble(LogisticsErpBubbleDtoEx dto);

	RestCode updateLogisticsErpStatus(LogisticsErpDtoEx dto);
	Map<String, Object> searchLogisticsErp(String logisticsPk,String stepInfoPk,B2bGoodsDtoMa goods,Double weight);
	
	/**
	 * 获取单商品物流模板
	 * @param logisticsModelPk
	 * @param addressPk
	 * @param goodsPk
	 * @param boxes
	 * @param weight
	 * @param purchaserPk
	 * @param type
	 * @param block
	 * @return
	 */
	LogisticsCart getLogisticsErpPriceByGoods(String logisticsModelPk,
			String addressPk, String goodsPk, Integer boxes, Double weight,
			String purchaserPk, Integer type);
 
}
