package cn.cf.dao;

import org.apache.ibatis.annotations.Param;
import cn.cf.dto.LgUserInvoiceDto;
import cn.cf.model.LgUserInvoice;

public interface LgUserInvoiceDaoEx extends LgUserInvoiceDao{
	
	//根据companyPk或者userPk去匹配用户发票信息
	LgUserInvoice getByCompanyPkOrUserPk(@Param("companyPk") String companyPk, @Param("userPk")String userPk);
	
	//对发票信息做存在性验证
	int searchEntity(LgUserInvoiceDto dto);
	
	//更新部分字段
	int updatePartField(LgUserInvoice model);
	
	
}
