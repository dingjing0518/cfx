package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.Sessions;

public interface B2bPayVoucherService {
	/**
	 * 上传凭证
	 * @param orderEx
	 * @param imgUrl
	 * @param type 类型，1：订单，2：合同
	 */
	void addPayVoucher(B2bOrderDtoEx orderEx,String imgUrl,int type);
	/**
	 * 根据订单查询凭证列表
	 * @param orderNumber
	 * @return
	 */
	List<B2bPayVoucher> searchB2bPayVoucherList(String orderNumber);
	/**
	 * 分页查询凭证列表
	 * @param map
	 * @return
	 */
	PageModel<B2bPayVoucher> searchB2bPayVoucherList(Map<String,Object> map,Sessions session);
	/**
	 * 更新凭证入账状态
	 * @param id
	 * @param status
	 */
	String updatePayVoucher(String id,Integer status);
	/**
	 * 查询凭证页签数量
	 * @param map
	 * @param company
	 * @return
	 */
	Map<String,Object> searchB2bPayVoucherCounts(Map<String,Object> map,Sessions session);
}
