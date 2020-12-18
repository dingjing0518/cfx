package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bCartDaoEx;
import cn.cf.service.B2bCartService;

@Service
public class B2bCartServiceImpl implements B2bCartService {

	@Autowired
	private B2bCartDaoEx b2bCartDaoEx;


	/**
	 * 根据goodsPk删除购物车
	 */
	@Override
	public int delByGoodsPk(String goodsPk) {
		return b2bCartDaoEx.delByGoodsPk(goodsPk);
	}
}
