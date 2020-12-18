package cn.cf.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import cn.cf.dto.LgArayacakMandateDto;
import cn.cf.model.LgArayacakMandate;

public interface LgArayacakMandateDaoEx extends LgArayacakMandateDao{
	
	
	//查询自提委托书
	public List<LgArayacakMandate> queryMandateByLimit(Map<String, Object> map);
	
	//根据userPk或companyPk查询自提委托书的所有数量
	Integer selectMandateCount(Map<String,Object> par);
	
	//根据pk检查自提委托书的删除状态
	int checkDelStatusByPk(@Param("mandatePk") String mandatePk);
	
	//删除自提委托书
	public Integer deleteLgArayacakMandate(@Param("mandatePk") String mandatePk);
	
	//根据pk查询自提委托书URL
	public String getMandateUrlByPK(@Param("mandatePk") String mandatePk);
	
	//根据pk查询自提委托书
	public LgArayacakMandateDto getMandateByPK(@Param("mandatePk") String mandatePk);
	
	//修改自提委托书
	public Integer updateMandate(LgArayacakMandateDto lgArayacakMandateDto);
	
	
	//int searchEntity(LgArayacakMandateDto dto);
	

}
