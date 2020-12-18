package cn.cf.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bTrancsationContractDto;
import cn.cf.dto.B2bTrancsationDto;
import cn.cf.dto.ExportOrderDto;
import cn.cf.entity.Sessions;

public interface FileOperationService {

	/**
	 * 订单导出excel
	 * @param map
	 * @param request
	 * @param response
	 */
	void exportOrder(List<ExportOrderDto> list, HttpServletRequest request, HttpServletResponse response, Sessions session,String searchType);
	
	/**
	 * 合同订单导出excel
	 * @param map
	 * @param request
	 * @param response
	 */
	void exportContract(List<B2bContractGoodsDtoEx> list,Sessions sessions ,String searchType, HttpServletRequest request,HttpServletResponse response);
	/**
	 * 正常订单下载
	 * @param orderNumber
	 * @param req
	 * @param resp
	 */
	void downContract(String orderNumber,HttpServletRequest req,HttpServletResponse resp);
	/**
	 * 合同订单下载
	 * @param orderNumber
	 * @param req
	 * @param resp
	 */
	void downContractOrder(String contractNo,HttpServletRequest req,HttpServletResponse resp);
	/**
	 * 商品导出excel
	 * @param map
	 * @param request
	 * @param response
	 */
	void exportGoods(List<B2bGoodsDtoEx> list,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 订单交易数据导出excel
	 * @param map
	 * @param request
	 * @param response
	 */
	void exportTrancsation(List<B2bTrancsationDto> list,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 合同交易数据导出excel
	 * @param map
	 * @param request
	 * @param response
	 */
	void exportTrancsationContract(List<B2bTrancsationContractDto> list,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 导出提货申请单、提货委托书
	 * @param deliveryNumber
	 * @param type 1 提货申请单 2提货委托书
	 * @param response
	 */
	void downloadDelivery(String deliveryNumber,Integer type,HttpServletResponse response);
}
