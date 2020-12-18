package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bCartDto;
import cn.cf.dto.B2bCartDtoEx;
import cn.cf.entity.FirmOrder;
import cn.cf.entity.Sessions;

public interface B2bCartService {
	/**
	 * 自采购物车列表
	 * 
	 * @param memberPk
	 *            登录人
	 * @return
	 */
	List<B2bCartDtoEx> searchCartList(String memberPk);

	/**
	 * 供应商代采购物车列表
	 * 
	 * @param memberPk
	 *            登录人
	 * @return
	 */
	List<B2bCartDtoEx> searchCartSpList(String memberPk);

	/**
	 * 查询购物车数量
	 * 
	 * @param memberPk
	 * @param cartType
	 *            1自采 2代采
	 * @return
	 */
	int searchCartCounts(String memberPk, int cartType);

	/**
	 * 加入购物车
	 * 
	 * @param goodsPk
	 * @param boxes
	 * @param purchaserPk
	 * @param purchaserName
	 * @return
	 */
	String addCart(B2bCartDtoEx dto, Sessions session);

	/**
	 * 修改购物车
	 * 
	 * @param pk
	 * @param boxes
	 * @return
	 */
	String updateCart(String pk, int boxes,Double weight);

	/**
	 * 删除购物车
	 * 
	 * @param pk
	 * @return
	 */
	String delCart(String pks);

	/**
	 * 获取单个购物车信息
	 * 
	 * @param pk
	 * @return
	 */
	B2bCartDto getCart(String pk);

	 

	List<FirmOrder> searchFirmOrder(String pks);

	/**
	 * 根据goodsPk删除购物车
	 * @param goodsPk
	 * @return
	 */
	int delByGoodsPk(String goodsPk);
}
