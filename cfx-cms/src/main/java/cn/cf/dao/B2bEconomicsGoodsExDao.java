package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bEconomicsGoodsExDto;
import org.apache.ibatis.annotations.Param;

public interface B2bEconomicsGoodsExDao  extends B2bEconomicsGoodsDao {

	int countEcGoodsGrid(Map<String, Object> map);

	List<B2bEconomicsGoodsExDto> searchEcGoodsGrid(Map<String, Object> map);

	int isRepeat(B2bEconomicsGoodsExDto economicsGoods);

	int insertEx(B2bEconomicsGoodsExDto economicsGoods);

	int updateEx(B2bEconomicsGoodsExDto economicsGoods);

	B2bEconomicsCustomerExtDto getExtByPk(String econCustPk);

	List<B2bEconomicsGoodsDto> getGoodsType(List<B2bEconomicsGoodsDto> list);

	B2bEconomicsGoodsDto getEconomicsGoodsByPk(Map<String,Object> map);


	Double countPurchaserSalesAmount(@Param("purchaserPk") String purchaserPk);

	List<Double> rawPurchaseIncrePerList(@Param("purchaserPk") String purchaserPk);

	Integer addUpOnlineSalesNumbers(Map<String,Object> map);

	Integer addUpEconSalesNumbers(Map<String,Object> map);

	Double countEconSalesAmount(@Param("purchaserPk") String purchaserPk);

	int countEconGoodsIsOver(@Param("purchaserPk") String purchaserPk);

	int countStoreSalesNumbers(@Param("purchaserPk") String purchaserPk);

	Double countPlatformAmount(@Param("purchaserPk") String purchaserPk);

	Double countCreditAmount(@Param("purchaserPk") String purchaserPk);
}
