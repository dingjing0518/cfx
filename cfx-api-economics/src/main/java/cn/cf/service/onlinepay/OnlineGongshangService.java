package cn.cf.service.onlinepay;

import java.util.List;

import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.OrderGoodsDtoEx;

public interface OnlineGongshangService {
	/**
	 * 线上支付提交
	 * @param odto
	 * @param goodsList
	 * @param card
	 * @return
	 */
	BankBaseResp onlinePay(B2bOrderDtoMa odto,List<OrderGoodsDtoEx> goodsList,SysCompanyBankcardDto card);
	/**
	 * 线上支付查询
	 * @param recordDto
	 * @return
	 */
	BankBaseResp onlinePaySearch(B2bOnlinepayRecordDto recordDto);
	/**
	 * 线上支付取消
	 * @param recordDto
	 * @return
	 */
	BankBaseResp onlinePayCancel(B2bOnlinepayRecordDto recordDto);
}
