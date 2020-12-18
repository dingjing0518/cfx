package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bOrderGoodsDtoEx;

public interface B2bOrderGoodsService {

	List<B2bOrderGoodsDtoEx> getByOrderNumber(String orderNumber);

}
