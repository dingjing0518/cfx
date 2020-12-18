package cn.cf.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.dao.LgArayacakMandateDaoEx;
import cn.cf.dto.LgArayacakMandateDto;
import cn.cf.model.LgArayacakMandate;
import cn.cf.service.LgArayacakMandateService;


@Service
public class LgArayacakMandateServiceImpl implements LgArayacakMandateService{
	
	@Autowired
    private LgArayacakMandateDaoEx lgArayacakMandateDaoEx;


	// 查询自提委托书(分页)
	@Override
	public PageModel<LgArayacakMandate> queryMandateByLimit(Map<String, Object> map) {
		PageModel<LgArayacakMandate> pm = new PageModel<LgArayacakMandate>();
		List<LgArayacakMandate> dataList = lgArayacakMandateDaoEx.queryMandateByLimit(map);
		Integer amount = lgArayacakMandateDaoEx.selectMandateCount(map);
		pm.setTotalCount(amount);
		pm.setDataList(dataList);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	//新增自提委托书
	@Override
	public Integer insert(LgArayacakMandate model) {
		return lgArayacakMandateDaoEx.insert(model);
	}
	
	//根据pk检查自提委托书的删除状态
	@Override
	public int checkDelStatusByPk(String mandatePk) {
		return lgArayacakMandateDaoEx.checkDelStatusByPk(mandatePk);
	}
	
	//删除自提委托书
	@Override
	public Integer deleteLgArayacakMandate(String mandatePk) {
		return lgArayacakMandateDaoEx.deleteLgArayacakMandate(mandatePk);
	}

	//根据Pk查询自提委托书
	@Override
	public LgArayacakMandateDto getMandateByPK(String mandatePk) {
		return lgArayacakMandateDaoEx.getByPk(mandatePk);
	}
	
	
	//根据PK查询自提委托书URL
	@Override
	public String getMandateUrlByPK(String mandatePk) {
		return lgArayacakMandateDaoEx.getMandateUrlByPK(mandatePk);
	}

	//修改自提委托书
	@Override
	public Integer updateMandate(LgArayacakMandateDto dto ) {
		return lgArayacakMandateDaoEx.updateMandate(dto);
	}

	/*@Override
	public Integer searchEntity(LgArayacakMandateDto dto) {
		return lgArayacakMandateDao.searchEntity(dto);
	}*/



	
}
