package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bPackNumberDto;

public interface B2bPackNumberDaoEx extends B2bPackNumberDao {
	List<B2bPackNumberDto> getB2bPackNumberByGoods(B2bGoodsDto goods);
}
