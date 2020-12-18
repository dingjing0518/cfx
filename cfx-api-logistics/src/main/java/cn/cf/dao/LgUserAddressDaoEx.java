package cn.cf.dao;

import org.apache.ibatis.annotations.Param;
import cn.cf.dto.LgUserAddressDto;

public interface LgUserAddressDaoEx extends LgUserAddressDao{
	
	void updateIsDefault(@Param("userPk") String userPk,@Param("companyPk") String companyPk, @Param("type")Integer type); 
	
	//更新部分字段
	int updatePartField(LgUserAddressDto dto);
	
	//多字段查询验证地址是否已经存在
	int searchEntity(LgUserAddressDto dto);
	
	/**
	 * 查询地址
	 * @param pk
	 * @return
	 */
	LgUserAddressDto getByPkEx(String pk); 
	
}
