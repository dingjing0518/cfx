package cn.cf.service;


public interface B2bCartService {

	/**
	 * 根据goodsPk删除购物车
	 * @param goodsPk
	 * @return
	 */
	int delByGoodsPk(String goodsPk);
}
