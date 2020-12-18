package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.entity.BankApproveAmountExport;
import cn.cf.entity.EcnoProductUseExport;
import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.entity.EconomicsProductOrder;
import cn.cf.entity.RepayInfo;

public interface EconReportFacadeService {
	/**
	 * 
	 * 金融产品审批：统计新增审批情况
	 * @param customerExtDtos
	 * @return
	 */
	public List<EconCustomerApproveExport> setCustomerApproveStatus (List<B2bEconomicsCustomerExtDto> customerExtDtos);
	/**
	 * 
	 *  金融产品审批：统计累计审批情况
	 * @param nowece
	 * @param ece
	 */
	public void accumulativeCustomerApprove(EconCustomerApproveExport nowece, EconCustomerApproveExport ece);
	/**
	 * 
	 * 银行审批额度：统计新增银行审批额度
	 * @param b2bEconomicsBankCompanyExtDto
	 * @param nowBam
	 */
	public void setBankApproveAmount(B2bEconomicsBankCompanyExtDto b2bEconomicsBankCompanyExtDto,BankApproveAmountExport nowBam);
	/**
	 * 
	 * 银行审批额度：统计累计银行审批额度
	 * @param nowBam
	 * @param bam
	 */
	public void accumulativeBankApproveAmount(BankApproveAmountExport nowBam, BankApproveAmountExport bam);
	/**
	 * 
	 * 银行审批额度：统计昨日银行审批额度
	 * @param bamList
	 */
	public void countApproveAmountYesterDay(List<BankApproveAmountExport> bamList);
	   /**
     * 
     * 银行审批额度：统计月银行审批额度
     * @param bamList
     */
	public void countApproveAmountMonth(List<BankApproveAmountExport> bamList);
	/**
	 * 
	 * 金融产品使用：统计新增使用情况
	 * @param epo
	 * @param dto
	 */
	void setNewEconomicsProduct(EconomicsProductOrder epo, EcnoProductUseExport dto);
	/**
	 * 
	 * 金融产品使用：统计累计使用情况
	 * @param pUseExport
	 * @param dto
	 */
	public void setAccumteEconomicsProduct(EcnoProductUseExport pUseExport, EcnoProductUseExport dto);
	/**
	 * 
	 * 金融产品使用：统计新增的还款情况
	 * @param repayInfo
	 * @param dto
	 */
	public void setRepayEconomicsProduct(RepayInfo repayInfo, EcnoProductUseExport dto);
	/**
	 * 
	 * 金融产品使用：统计累计的还款情况
	 * @param pUseExport
	 * @param dto
	 */
	public void setAccumteRepayEconomicsProduct(EcnoProductUseExport pUseExport, EcnoProductUseExport dto);
	/**
	 * 
	 * 金融产品使用：统计目前为止的可使用的情况
	 * @param pk
	 * @param dto
	 */
	public void setNowAvailableAmount(String pk, EcnoProductUseExport dto);
	/**
	 * 
	 * 金融产品使用：统计目前为止的累计可使用的情况
	 * @param nowece
	 * @param ep
	 * @param i
	 */
	public void accumulativeEcnoProductUse(EcnoProductUseExport nowece, EcnoProductUseExport ep, int i);


}
