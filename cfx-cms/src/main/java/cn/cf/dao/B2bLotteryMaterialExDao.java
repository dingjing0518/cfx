package cn.cf.dao;

import java.util.List;
import cn.cf.dto.B2bLotteryMaterialDto;

public interface B2bLotteryMaterialExDao extends B2bLotteryMaterialDao{
	
	 List<B2bLotteryMaterialDto> getAllMaterial();
	 
}
