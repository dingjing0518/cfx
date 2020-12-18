package cn.cf.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.cf.model.LgPayType;

public interface LgPayTypeDaoEx extends LgPayTypeDao {

	// 查询启用的支付方式
	public List<LgPayType> getPayTypes();

	// 根据pk查询支付方式名
	public String getPayTypeNameByPK(@Param("paymentPk") String paymentPk);
}
