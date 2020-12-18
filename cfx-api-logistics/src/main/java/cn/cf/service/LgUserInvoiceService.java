package cn.cf.service;

import cn.cf.dto.LgUserInvoiceDto;
import cn.cf.model.LgUserInvoice;

public interface LgUserInvoiceService {

	/**
	 * 添加发票信息
	 * @param dto  发票信息
	 * @return
	 */
	String  addInvoiceInfo(LgUserInvoiceDto dto);

	/**
	 * 获取发票信息
	 * @param companyPk  公司pk
	 * @param userPk  用户pk
	 * @return
	 */
	LgUserInvoice getInvoiceInfo(String companyPk, String userPk);

	/**
	 * 更新发票信息
	 * @param dto  发票信息
	 */
	void updateInvoiceInfo(LgUserInvoiceDto dto);

	/**
	 * 重复查询
	 * @param dto 对象参数
	 * @return
	 */
	int searchEntity(LgUserInvoiceDto dto);

	/**
	 * 查询发票信息
	 * @param pk  pk
	 * @return
	 */
	LgUserInvoiceDto getByPk(String pk);
}
