package cn.cf.service.enconmics;

import java.io.IOException;
import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.entity.B2bEconomicsImprovingdataEntity;
import cn.cf.model.B2bBillGoods;
import jxl.read.biff.BiffException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public interface B2bEconomicsGoodsService {
    /**
     * 金融产品列表
     * @param qm
     * @return
     */
	PageModel<B2bEconomicsGoodsExDto> searchEcGoodsList(QueryModel<B2bEconomicsGoodsExDto> qm);

	/**
	 * 票据产品列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bBillGoodsExtDto> searchBillGoodsList(QueryModel<B2bBillGoodsExtDto> qm);
	/**
	 * 
	 * 更新金融产品
	 * @param economicsGoods
	 * @return
	 */
	String update(B2bEconomicsGoodsExDto economicsGoods);
	/**
	 * 
	 * 根据pk查询金融产品
	 * @param pk
	 * @return
	 */
	B2bEconomicsGoodsDto getByPk(String pk);
	/**
	 * 
	 *根据name查询金融产品
	 * @param name
	 * @return
	 */
	B2bEconomicsGoodsDto getByName(String name);
	/**
	 * 
	 * 查询金融产品
	 * @return
	 */
	List<B2bEconomicsGoodsDto> searchList();

	/**
	 * 线上支付产品列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bOnlinepayGoodsExtDto> searchOnlinePayGoodsList(QueryModel<B2bOnlinepayGoodsExtDto> qm);

	/**
	 * 征信产品产品列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bCreditreportGoodsExtDto> searchCreditReportGoodsList(QueryModel<B2bCreditreportGoodsExtDto> qm);

	/**
	 * 新增票据产品数据
	 * @param dto
	 * @return
	 */
	String insertBillGoods(B2bBillGoods dto);

	/**
	 * 更新票据产品数据
	 * @param dto
	 * @return
	 */
	String updateBillGoods(B2bBillGoodsExtDto dto);


	/**
	 * 更新支付产品数据
	 * @param dto
	 * @return
	 */
	String updateOnlinePayGoods(B2bOnlinepayGoodsExtDto dto);

	/**
	 * 更新支付产品状态
	 * @param dto
	 * @return
	 */
	String updateStatusOnlinePayGoods(B2bOnlinepayGoodsExtDto dto);


	/**
	 * 更新征信产品数据
	 * @param dto
	 * @return
	 */
	String updateCreditReportGoods(B2bCreditreportGoodsExtDto dto);

	/**
	 * 更新征信产品状态
	 * @param dto
	 * @return
	 */
	String updateStatusCreditReportGoods(B2bCreditreportGoodsExtDto dto);

	List<B2bCreditreportGoodsDto> searchCreditReportGoods();

	/**
	 * 统计采购商线上近12个月累计交易金额
	 * @param purchaserPk
	 * @return
	 */
    Double countPurchaserSalesAmount(String purchaserPk);

	/**
	 * 企业原料采购环比稳定性(百分比)
	 * @param purchaserPk
	 * @return
	 */
	Double rawPurchaseIncrePer(String purchaserPk);

	/**
	 * 企业线上近12个月交易月数
	 *当月不算，从系统当前时间，往前倒推12个月；进行统计；
	 *备注：该客户最近一个月订单(待发货，部分发货，待收货，已完成)，合同（待发货，部分发货，待收货，已完成），算一次；一年最多12次
	 * @param purchaserPk
	 * @return
	 */
	Integer addUpOnlineSalesNumbers(String purchaserPk);

	/**
	 * 当月不算，从系统当前时间，往前倒推12个月；进行统计，化纤贷，化纤白条算一个金融产品
	 * 备注：该客户一个月借款单（已放款，已还款，部分还款）有多笔，算一次；一年最多12次
	 * @param purchaserPk
	 * @return
	 */
	Integer addUpEconSalesNumbers(String purchaserPk);

	/**
	 * 当月不算，从系统当前时间，往前倒推12个月；进行统计 1输入整数部分不做限定2.小数部分默认保留两位
	 * @param purchaserPk
	 * @return
	 */
	Double countEconSalesAmount(String purchaserPk);

	/**
	 * 通过该企业对应的借款单中所有逾期状态为逾期的数量，有多少次，计算多少次
	 * @param purchaserPk
	 * @return
	 */
	int countEconGoodsIsOver(String purchaserPk);

	/**
	 * 编辑和新增完善资料
	 * @param dto
	 * @return
	 */
	String updateperfectCustDatum(B2bEconomicsImprovingdataEntity dto);

	/**
	 * 线上企业近12个月发生业务店铺数
	 * @param purchaserPk
	 * @return
	 */
	int countStoreSalesNum(String purchaserPk);



	String importPerfectCustDatumScoreFile(MultipartFile file,String companyPk,String processInstanceId) throws IOException, BiffException;


	/**
	 * 平台收入
	 * @param purchaserPk
	 * @return
	 */
	Double getPlatformCustDatum(@Param("purchaserPk") String purchaserPk);

	/**
	 * 企业授信额度
	 * @param purchaserPk
	 * @return
	 */
	Double getCreditCustDatum(@Param("purchaserPk") String purchaserPk);


	/**
	 * 根据申请金融产品客户表Pk获取金融客户产品表数据
	 * @param pk
	 * @return
	 */
	List<B2bEconomicsCustomerGoodsDto> getEconomicsCustomerGoods(String pk);

	/**
	 * 根据申请金融客户表Pk获取金融客户表数据
	 * @param pk
	 * @return
	 */
	B2bEconomicsCustomerDto getEconomicsCustomer(String pk);

}
