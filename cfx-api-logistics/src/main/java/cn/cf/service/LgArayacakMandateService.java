package cn.cf.service;

import java.util.Map;
import cn.cf.PageModel;
import cn.cf.dto.LgArayacakMandateDto;
import cn.cf.model.LgArayacakMandate;


public interface LgArayacakMandateService {
	
	/**
	 * 查询自提委托书（分页）
	 * @param map 查询参数
	 * @return
	 */
	public PageModel<LgArayacakMandate> queryMandateByLimit(Map<String, Object> map);
	
	/**
	 * 新增自提委托书
	 * @param model 参数
	 * @return
	 */
	public Integer insert(LgArayacakMandate model);
	
	/**
	 * 根据pk检查自提委托书的删除状态
	 * @param mandatePk pk
	 * @return
	 */
	int checkDelStatusByPk(String mandatePk);

	/**
	 * 删除自提委托书
	 * @param mandatePk  pk
	 * @return
	 */
	public Integer deleteLgArayacakMandate(String mandatePk);
	
	/**
	 * 根据pk查询自提委托书url
	 * @param mandatePk pk
	 * @return
	 */
	public String getMandateUrlByPK(String mandatePk);
	
	/**
	 * 根据pk查询自提委托书
	 * @param mandatePk  pk
	 * @return
	 */
	public LgArayacakMandateDto getMandateByPK(String mandatePk);

	/**
	 * 修改自提委托书
	 * @param dto  参数
	 * @return
	 */
	public Integer updateMandate(LgArayacakMandateDto dto);
	
	
}
