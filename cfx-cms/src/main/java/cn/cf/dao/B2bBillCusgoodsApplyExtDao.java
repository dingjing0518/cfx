package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bBillCusgoodsApplyDto;

public interface B2bBillCusgoodsApplyExtDao extends  B2bBillCusgoodsApplyDao{

	void updateBatch(List<B2bBillCusgoodsApplyDto> list);

}
