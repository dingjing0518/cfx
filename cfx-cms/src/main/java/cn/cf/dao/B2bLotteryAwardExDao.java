package cn.cf.dao;

import cn.cf.dto.B2bLotteryAwardExDto;
import cn.cf.model.B2bLotteryAward;

import java.util.List;
import java.util.Map;

public interface B2bLotteryAwardExDao extends B2bLotteryAwardDao{
	
	//查询奖项一览列表的数量
	int searchGridCountEx(Map<String, Object> map);
	
	//查询奖项列表的一览数据
	List<B2bLotteryAwardExDto> searchGridEx(Map<String, Object> map);
	
	//根据pk查询奖项
	B2bLotteryAwardExDto getLotteryAwardByAwardPk(String pk);

	int updateAwardObjEx(B2bLotteryAward award);

	int updateAwardStatus(B2bLotteryAwardExDto award);
	
}
