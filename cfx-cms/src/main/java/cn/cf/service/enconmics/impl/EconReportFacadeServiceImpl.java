package cn.cf.service.enconmics.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bEconomicsBankCompanyExtDao;
import cn.cf.dao.B2bEconomicsGoodsExDao;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.entity.BankApproveAmountExport;
import cn.cf.entity.EcnoProductUseExport;
import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.entity.EconomicsProductOrder;
import cn.cf.entity.RepayInfo;
import cn.cf.service.enconmics.EconReportFacadeService;
import cn.cf.service.enconmics.EconomicsBankCompanyService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.StringUtils;

@Service
public class EconReportFacadeServiceImpl implements EconReportFacadeService {

	@Autowired
	private B2bEconomicsGoodsExDao economicsGoodsDao;

	@Autowired
	private B2bEconomicsBankCompanyExtDao b2bEconomicsBankCompanyExtDao;

	@Autowired
	private EconomicsBankCompanyService economicsBankCompanyService;

	@Autowired
	private EconomicsBankService economicsBankService;

	@Override
	public  List<EconCustomerApproveExport>  setCustomerApproveStatus(List<B2bEconomicsCustomerExtDto> list) {
		 List<EconCustomerApproveExport> ecelist = new ArrayList<EconCustomerApproveExport>();
		 String bankPk = "";
		if (list.size()>0) {
			EconCustomerApproveExport ece = new  EconCustomerApproveExport();
			ece.setFlag(Constants.ONE);
			ece.setYear(CommonUtil.yesterday(1).toString());
			ece.setMonth(CommonUtil.yesterday(2));
			ece.setBankPk(list.get(0).getBankPk());	
			bankPk = list.get(0).getBankPk();
			getApprovre(ece, list.get(0));
			for (int i = 1; i < list.size(); i++) {
				if (bankPk.equals(list.get(i).getBankPk())) {
					bankPk = list.get(i).getBankPk();
					getApprovre(ece, list.get(i));
				}else{
					ecelist.add(ece);
					ece = new  EconCustomerApproveExport();
					ece.setFlag(Constants.ONE);
					ece.setYear(CommonUtil.yesterday(1).toString());
					ece.setMonth(CommonUtil.yesterday(2));
					ece.setBankPk( list.get(i).getBankPk());	
					bankPk =  list.get(i).getBankPk();
					getApprovre(ece, list.get(i));
				}
			
			}
			ecelist.add(ece);
		}
		return ecelist;
	}


	private void getApprovre(EconCustomerApproveExport ece, B2bEconomicsCustomerExtDto dto) {
		if (dto.getProductType()!=null && !dto.getProductType().equals("") ) {
			switch (dto.getAuditStatus()) {
			case Constants.TWO:// 审批中
				setApprovingStatus(ece, dto);
				break;
			case Constants.THREE:// 通过
				setPassStatus(ece, dto);
				break;
			case Constants.FOUR:// 驳回
				setUnPassStatus(ece, dto);
				break;
			default:
				break;
			}
		}
	}
	
	
	private Integer getProductType(String productType) {
		Integer flag = Constants.ONE;
		if (productType.contains("1")) {// 是否含有化纤白条
			if (productType.contains("2")) {// 是否含有化纤贷
				flag = Constants.THREE;
			} else {
				flag = Constants.ONE;
			}
		} else {
			flag = Constants.TWO;
		}
		return flag;
	}

	private void setUnPassStatus(EconCustomerApproveExport ece, B2bEconomicsCustomerExtDto ec) {
		// 查询产品类型：1:化纤白条；2：化纤贷；3：化纤白条+化纤贷
		Integer productType = getProductType(ec.getProductType());
		// 盛虹
		if (ec.getSource() == Constants.ONE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setsBTUnpass(ece.getsBTUnpass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setsDUnpass(ece.getsDUnpass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setsBTDUnpass(ece.getsBTDUnpass() + 1);
			}
		}
		// 平台
		if (ec.getSource() == Constants.TWO) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setpBTUnpass(ece.getpBTUnpass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setpDUnpass(ece.getpDUnpass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setpBTDUnpass(ece.getpBTDUnpass() + 1);
			}
		}

		// 新凤鸣
		if (ec.getSource() == Constants.THREE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setxBTUnpass(ece.getxBTUnpass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setxDUnpass(ece.getxDUnpass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setxBTDUnpass(ece.getxBTDUnpass() + 1);
			}

		}
		// 营销
		if (ec.getSource() == Constants.FOUR) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setyBTUnpass(ece.getyBTUnpass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setyDUnpass(ece.getyDUnpass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setyBTDUnpass(ece.getyBTDUnpass() + 1);
			}

		}
		// 其他
		if (ec.getSource() == Constants.FIVE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setqBTUnpass(ece.getqBTUnpass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setqDUnpass(ece.getqDUnpass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setqBTDUnpass(ece.getqBTDUnpass() + 1);
			}

		}
	}

	private void setPassStatus(EconCustomerApproveExport ece, B2bEconomicsCustomerExtDto ec) {
		// 查询产品类型：1:化纤白条；2：化纤贷；3：化纤白条+化纤贷
		Integer productType = getProductType(ec.getProductType());
		// 盛虹
		if (ec.getSource() == Constants.ONE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setsBTPass(ece.getsBTPass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setsDPass(ece.getsDPass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setsBTDPass(ece.getsBTDPass() + 1);
			}
		}
		// 平台
		if (ec.getSource() == Constants.TWO) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setpBTPass(ece.getpBTPass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setpDPass(ece.getpDPass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setpBTDPass(ece.getpBTDPass() + 1);
			}
		}

		// 新凤鸣
		if (ec.getSource() == Constants.THREE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setxBTPass(ece.getxBTPass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setxDPass(ece.getxDPass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setxBTDPass(ece.getxBTDPass() + 1);
			}

		}
		// 营销
		if (ec.getSource() == Constants.FOUR) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setyBTPass(ece.getyBTPass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setyDPass(ece.getyDPass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setyBTDPass(ece.getyBTDPass() + 1);
			}

		}
		// 其他
		if (ec.getSource() == Constants.FIVE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setqBTPass(ece.getqBTPass() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setqDPass(ece.getqDPass() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setqBTDPass(ece.getqBTDPass() + 1);
			}

		}

	}

	private void setApprovingStatus(EconCustomerApproveExport ece, B2bEconomicsCustomerExtDto  ec) {
		// 查询产品类型：1:化纤白条；2：化纤贷；3：化纤白条+化纤贷
		Integer productType = getProductType(ec.getProductType());
		// 盛虹
		if (ec.getSource() == Constants.ONE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setsBTApproving(ece.getsBTApproving() + 1);

			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setsDApproving(ece.getsDApproving() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setsBTDApproving(ece.getsBTDApproving() + 1);
			}
		}
		// 平台
		if (ec.getSource() == Constants.TWO) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setpBTApproving(ece.getpBTApproving() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setpDApproving(ece.getpDApproving() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setpBTDApproving(ece.getpBTDApproving() + 1);
			}
		}

		// 新凤鸣
		if (ec.getSource() == Constants.THREE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setxBTApproving(ece.getxBTApproving() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setxDApproving(ece.getxDApproving() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setxBTDApproving(ece.getxBTDApproving() + 1);
			}

		}
		// 营销
		if (ec.getSource() == Constants.FOUR) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setyBTApproving(ece.getyBTApproving() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setyDApproving(ece.getyDApproving() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setyBTDApproving(ece.getyBTDApproving() + 1);
			}

		}
		// 其他
		if (ec.getSource() == Constants.FIVE) {
			if (productType == Constants.ONE) {// 化纤白条
				ece.setqBTApproving(ece.getqBTApproving() + 1);
			}
			if (productType == Constants.TWO) {// 化纤贷
				ece.setqDApproving(ece.getqDApproving() + 1);
			}
			if (productType == Constants.THREE) {// 化纤白条+化纤贷
				ece.setqBTDApproving(ece.getqBTDApproving() + 1);
			}
		}
	}

	private Integer getProductTypeByPks(String productPks) {
		Integer flag = Constants.ONE;
		String[] temp = StringUtils.splitStrs(productPks);
		String productType = "";
		for (int i = 0; i < temp.length; i++) {
			B2bEconomicsGoodsDto dto = economicsGoodsDao.getByPk(temp[i]);
			productType = productType + dto.getGoodsType();
		}
		if (productType.contains("1")) {// 是否含有化纤白条
			if (productType.contains("2")) {// 是否含有化纤贷
				flag = Constants.THREE;
			} else {
				flag = Constants.ONE;
			}
		} else {
			flag = Constants.TWO;
		}
		return flag;
	}

	@Override
	public void accumulativeCustomerApprove(EconCustomerApproveExport nowece, EconCustomerApproveExport ece) {
		
		nowece.setyBTApproving(nowece.getyBTApproving() + ece.getyBTApproving());
		nowece.setyBTPass(nowece.getyBTPass() + ece.getyBTPass());
		nowece.setyBTUnpass(nowece.getyBTUnpass() + ece.getyBTUnpass());
		nowece.setyDApproving(nowece.getyDApproving() + ece.getyDApproving());
		nowece.setyDPass(nowece.getyDPass() + ece.getyDPass());
		nowece.setyDUnpass(nowece.getyDUnpass() + ece.getyDUnpass());
		nowece.setyBTDApproving(nowece.getyBTDApproving() + ece.getyBTDApproving());
		nowece.setyBTDPass(nowece.getyBTDPass() + ece.getyBTDPass());
		nowece.setyBTDUnpass(nowece.getyBTDUnpass() + ece.getyBTDUnpass());

		nowece.setpBTApproving(nowece.getpBTApproving() + ece.getpBTApproving());
		nowece.setpBTPass(nowece.getpBTPass() + ece.getpBTPass());
		nowece.setpBTUnpass(nowece.getpBTUnpass() + ece.getpBTUnpass());
		nowece.setpDApproving(nowece.getpDApproving() + ece.getpDApproving());
		nowece.setpDPass(nowece.getpDPass() + ece.getpDPass());
		nowece.setpDUnpass(nowece.getpDUnpass() + ece.getpDUnpass());
		nowece.setpBTDApproving(nowece.getpBTDApproving() + ece.getpBTDApproving());
		nowece.setpBTDPass(nowece.getpBTDPass() + ece.getpBTDPass());
		nowece.setpBTDUnpass(nowece.getpBTDUnpass() + ece.getpBTDUnpass());

		nowece.setsBTApproving(nowece.getsBTApproving() + ece.getsBTApproving());
		nowece.setsBTPass(nowece.getsBTPass() + ece.getsBTPass());
		nowece.setsBTUnpass(nowece.getsBTUnpass() + ece.getsBTUnpass());
		nowece.setsDApproving(nowece.getsDApproving() + ece.getsDApproving());
		nowece.setsDPass(nowece.getsDPass() + ece.getsDPass());
		nowece.setsDUnpass(nowece.getsDUnpass() + ece.getsDUnpass());
		nowece.setsBTDApproving(nowece.getsBTDApproving() + ece.getsBTDApproving());
		nowece.setsBTDPass(nowece.getsBTDPass() + ece.getsBTDPass());
		nowece.setsBTDUnpass(nowece.getsBTDUnpass() + ece.getsBTDUnpass());

		nowece.setxBTApproving(nowece.getxBTApproving() + ece.getxBTApproving());
		nowece.setxBTPass(nowece.getxBTPass() + ece.getxBTPass());
		nowece.setxBTUnpass(nowece.getxBTUnpass() + ece.getxBTUnpass());
		nowece.setxDApproving(nowece.getxDApproving() + ece.getxDApproving());
		nowece.setxDPass(nowece.getxDPass() + ece.getxDPass());
		nowece.setxDUnpass(nowece.getxDUnpass() + ece.getxDUnpass());
		nowece.setxBTDApproving(nowece.getxBTDApproving() + ece.getxBTDApproving());
		nowece.setxBTDPass(nowece.getxBTDPass() + ece.getxBTDPass());
		nowece.setxBTDUnpass(nowece.getxBTDUnpass() + ece.getxBTDUnpass());

		nowece.setqBTApproving(nowece.getqBTApproving() + ece.getqBTApproving());
		nowece.setqBTPass(nowece.getqBTPass() + ece.getqBTPass());
		nowece.setqBTUnpass(nowece.getqBTUnpass() + ece.getqBTUnpass());
		nowece.setqDApproving(nowece.getqDApproving() + ece.getqDApproving());
		nowece.setqDPass(nowece.getqDPass() + ece.getqDPass());
		nowece.setqDUnpass(nowece.getqDUnpass() + ece.getqDUnpass());
		nowece.setqBTDApproving(nowece.getqBTDApproving() + ece.getqBTDApproving());
		nowece.setqBTDPass(nowece.getqBTDPass() + ece.getqBTDPass());
		nowece.setqBTDUnpass(nowece.getqBTDUnpass() + ece.getqBTDUnpass());
	}

	@Override
	public void setBankApproveAmount(B2bEconomicsBankCompanyExtDto ebc, BankApproveAmountExport nowBam) {
		if (ebc.getType()!=null) {
			switch (ebc.getType()) {
			case Constants.ONE:// 化纤白条
				setBankBTAmount(ebc, nowBam);
				break;
			case Constants.TWO:// 化纤贷
				setBankDAmount(ebc, nowBam);
				break;
			default:
				break;
			}
		}
		
	}

	private void setBankDAmount(B2bEconomicsBankCompanyExtDto ebc, BankApproveAmountExport nowBam) {
		if (ebc.getSource()!=null) {
			// 盛虹
			if (ebc.getSource() == Constants.ONE) {
				nowBam.setsDAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getsDAmount()));
			}
			// 平台
			if (ebc.getSource() == Constants.TWO) {
				nowBam.setpDAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getpDAmount()));
			}
	
			// 新凤鸣
			if (ebc.getSource() == Constants.THREE) {
				nowBam.setxDAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getxDAmount()));
			}
			// 营销
			if (ebc.getSource() == Constants.FOUR) {
				nowBam.setyDAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getyDAmount()));
			}
			// 其他
			if (ebc.getSource() == Constants.FIVE) {
				nowBam.setqDAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getqDAmount()));
			}
		}
	}

	private void setBankBTAmount(B2bEconomicsBankCompanyExtDto ebc, BankApproveAmountExport nowBam) {
		if (ebc.getSource()!=null) {
			// 盛虹
			if (ebc.getSource() == Constants.ONE) {
				nowBam.setsBTAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getsBTAmount()));
			}
			// 平台
			if (ebc.getSource() == Constants.TWO) {
				nowBam.setpBTAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getpBTAmount()));
			}

			// 新凤鸣
			if (ebc.getSource() == Constants.THREE) {
				nowBam.setxBTAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getxBTAmount()));
			}
			// 营销
			if (ebc.getSource() == Constants.FOUR) {
				nowBam.setyBTAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getyBTAmount()));
			}
			// 其他
			if (ebc.getSource() == Constants.FIVE) {
				nowBam.setqBTAmount(ArithUtil.addStrD(ebc.getContractAmount(), nowBam.getqBTAmount()));
			}
		}
		

	}

	@Override
	public void accumulativeBankApproveAmount(BankApproveAmountExport nowBam, BankApproveAmountExport bam) {
		nowBam.setsBTAmount(ArithUtil.addString(nowBam.getsBTAmount(), bam.getsBTAmount()));
		nowBam.setxBTAmount(ArithUtil.addString(nowBam.getxBTAmount(), bam.getxBTAmount()));
		nowBam.setyBTAmount(ArithUtil.addString(nowBam.getyBTAmount(), bam.getyBTAmount()));
		nowBam.setpBTAmount(ArithUtil.addString(nowBam.getpBTAmount(), bam.getpBTAmount()));
		nowBam.setqBTAmount(ArithUtil.addString(nowBam.getqBTAmount(), bam.getqBTAmount()));

		nowBam.setsDAmount(ArithUtil.addString(nowBam.getsDAmount(), bam.getsDAmount()));
		nowBam.setxDAmount(ArithUtil.addString(nowBam.getxDAmount(), bam.getxDAmount()));
		nowBam.setyDAmount(ArithUtil.addString(nowBam.getyDAmount(), bam.getyDAmount()));
		nowBam.setpDAmount(ArithUtil.addString(nowBam.getpDAmount(), bam.getpDAmount()));
		nowBam.setqDAmount(ArithUtil.addString(nowBam.getqDAmount(), bam.getqDAmount()));

		nowBam.setsBTTotalAmount(bam.getsBTTotalAmount());
		nowBam.setxBTTotalAmount(bam.getxBTTotalAmount());
		nowBam.setyBTTotalAmount(bam.getyBTTotalAmount());
		nowBam.setpBTTotalAmount(bam.getpBTTotalAmount());
		nowBam.setqBTTotalAmount(bam.getqBTTotalAmount());

		nowBam.setsDTotalAmount(bam.getsDTotalAmount());
		nowBam.setxDTotalAmount(bam.getxDTotalAmount());
		nowBam.setyDTotalAmount(bam.getyDTotalAmount());
		nowBam.setpDTotalAmount(bam.getpDTotalAmount());
		nowBam.setqDTotalAmount(bam.getqDTotalAmount());
	}

	/**
	 * 计算昨日的有效银行额度
	 */
	@Override
	public void countApproveAmountYesterDay(List<BankApproveAmountExport> bamList) {
		String temp = "";
		// 1.昨日存在新增有效额度的银行
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creditTime", DateUtil.dateBeforeNowDay(1, 0, 0, 0));// 昨天
		if (bamList != null && bamList.size() > 0) {
			for (BankApproveAmountExport bam : bamList) {
				temp = temp + bam.getBankPk() + ",";
				map.put("bankPk", bam.getBankPk());
				getTotalAmount(map, bam);
			}
		}

		// 2.昨日无新增有效额度的银行
		// 获取所有银行
		List<B2bEconomicsBankDto> bankList = economicsBankService.searchAllList();//
		if (bankList != null && bankList.size() > 0) {
			for (B2bEconomicsBankDto bankDto : bankList) {
				BankApproveAmountExport bankApproveAmountExport = new BankApproveAmountExport();
				if (temp.equals("") || !temp.contains(bankDto.getPk())) {
					map.put("bankPk", bankDto.getPk());
					bankApproveAmountExport.setFlag(Constants.ONE);
					bankApproveAmountExport.setYear(CommonUtil.yesterday(1).toString());
					bankApproveAmountExport.setMonth(CommonUtil.yesterday(2));
					bankApproveAmountExport.setBankPk(bankDto.getPk());
					getTotalAmount(map, bankApproveAmountExport);
					bamList.add(bankApproveAmountExport);
				}
			}
		}
	}

	/**
	 * 计算当前有效额度 来源：1:盛虹;2：平台;3：新凤鸣;4：营销;5:其他 产品类型：1:化纤白条；2：化纤贷；
	 * 
	 * @param map
	 * @param bam
	 */
	private void getTotalAmount(Map<String, Object> map, BankApproveAmountExport bam) {
		List<B2bEconomicsBankCompanyExtDto> list = new ArrayList<B2bEconomicsBankCompanyExtDto>();
		// 化纤白条
		map.put("type", Constants.ONE);
		list = b2bEconomicsBankCompanyExtDao.countTotalAmount(map);
		setBTAmountBySoure(bam, list);
		// 化纤贷
		map.put("type", Constants.TWO);
		list = b2bEconomicsBankCompanyExtDao.countTotalAmount(map);
		setDAmountBySoure(bam, list);
	}

	// 设置化纤贷的银额度
	private void setDAmountBySoure(BankApproveAmountExport bam, List<B2bEconomicsBankCompanyExtDto> list) {
		if (list != null && list.size() > 0) {
			for (B2bEconomicsBankCompanyExtDto eb : list) {
				String total = new BigDecimal(eb.getTotal()).toString();
				switch (eb.getSource()) {
				case Constants.ONE:
					bam.setsDTotalAmount(total);
					break;
				case Constants.TWO:
					bam.setpDTotalAmount(total);
					break;
				case Constants.THREE:
					bam.setxDTotalAmount(total);
					break;
				case Constants.FOUR:
					bam.setyDTotalAmount(total);
					break;
				case Constants.FIVE:
					bam.setqDTotalAmount(total);
					break;
				default:
					break;
				}

			}
		}
	}

	// 设置化纤白条的银额度
	private void setBTAmountBySoure(BankApproveAmountExport bam, List<B2bEconomicsBankCompanyExtDto> list) {
		if (list != null && list.size() > 0) {
			for (B2bEconomicsBankCompanyExtDto eb : list) {
				if (eb.getSource()!=null) {
					String total = new BigDecimal(eb.getTotal()).toString();
					switch (eb.getSource()) {
					case Constants.ONE:
						bam.setsBTTotalAmount(total);
						break;
					case Constants.TWO:
						bam.setpBTTotalAmount(total);
						break;
					case Constants.THREE:
						bam.setxBTTotalAmount(total);
						break;
					case Constants.FOUR:
						bam.setyBTTotalAmount(total);
						break;
					case Constants.FIVE:
						bam.setqBTTotalAmount(total);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	@Override
	public void countApproveAmountMonth(List<BankApproveAmountExport> bamList) {
		String temp = "";
		// 1.昨日有新增的银行的有效额度
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", "");// 上个月21号
		map.put("endTime", CommonUtil.yesterDay());// 昨天（当月的20号）
		if (bamList != null && bamList.size() > 0) {
			for (BankApproveAmountExport bam : bamList) {
				temp = temp + bam.getBankPk() + ",";
				map.put("bankPk", bam.getBankPk());
				getTotalAmountMonth(map, bam);
			}
		}

		// 2.昨日无新增的银行的有效额度
		// 获取所有有额度的银行
		List<B2bEconomicsBankCompanyDto> banklist = b2bEconomicsBankCompanyExtDao.getBank();
		if (banklist != null && banklist.size() > 0) {
			for (B2bEconomicsBankCompanyDto bankDto : banklist) {
				BankApproveAmountExport bankApproveAmountExport = new BankApproveAmountExport();
				if (temp.equals("") || !temp.contains(bankDto.getBankPk())) {
					map.put("bankPk", bankDto.getBankPk());
					bankApproveAmountExport.setFlag(Constants.FOUR);
					bankApproveAmountExport.setYear(CommonUtil.yesterday(1).toString());
					bankApproveAmountExport.setMonth(CommonUtil.yesterday(2));
					bankApproveAmountExport.setBankPk(bankDto.getBankPk());
					getTotalAmountMonth(map, bankApproveAmountExport);
					bamList.add(bankApproveAmountExport);
				}
			}
		}
	}

	/**
	 * 以月为范围 计算当前有效额度 来源：1:盛虹;2：平台;3：新凤鸣;4：营销;5:其他 产品类型：1:化纤白条；2：化纤贷；
	 * 
	 * @param map
	 * @param bam
	 */
	private void getTotalAmountMonth(Map<String, Object> map, BankApproveAmountExport bam) {
		List<B2bEconomicsBankCompanyExtDto> list = new ArrayList<B2bEconomicsBankCompanyExtDto>();
		// 化纤白条
		map.put("type", Constants.ONE);
		list = b2bEconomicsBankCompanyExtDao.countTotalAmountMonth(map);
		setBTAmountBySoure(bam, list);

		// 化纤贷
		map.put("type", Constants.TWO);
		list = b2bEconomicsBankCompanyExtDao.countTotalAmountMonth(map);
		setDAmountBySoure(bam, list);
	}

	// 来源：1:盛虹;2：平台;3：新凤鸣;4：营销;5:其他
	@Override
	public void setNewEconomicsProduct(EconomicsProductOrder epo, EcnoProductUseExport dto) {
		// 白条
		if (epo.getProductType() == Constants.ONE) {
			if (epo.getSource()!=null) {
				switch (epo.getSource()) {
				case Constants.ONE:
					dto.setsBTAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getsBTAmount()));
					break;
				case Constants.TWO:
					dto.setpBTAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getpBTAmount()));
					break;
				case Constants.THREE:
					dto.setxBTAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getxBTAmount()));
					break;
				case Constants.FOUR:
					dto.setyBTAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getyBTAmount()));
					break;
				case Constants.FIVE:
					dto.setqBTAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getqBTAmount()));
					break;
				default:
					break;
				}
			}
			
		}

		// 化纤贷
		if (epo.getProductType() == Constants.TWO) {
			if (epo.getSource()!=null) {
			switch (epo.getSource()) {
			case Constants.ONE:
				dto.setsDAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getsDAmount()));
				break;
			case Constants.TWO:
				dto.setpDAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getpDAmount()));
				break;
			case Constants.THREE:
				dto.setxDAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getxDAmount()));
				break;
			case Constants.FOUR:
				dto.setyDAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getyDAmount()));
				break;
			case Constants.FIVE:
				dto.setqDAmount(ArithUtil.addStrD(epo.getLoanAmount(), dto.getqDAmount()));
				break;
			default:
				break;
			}
			}
		}

		dto.setsBTTotalAmount(dto.getsBTAmount());
		dto.setxBTTotalAmount(dto.getxBTAmount());
		dto.setyBTTotalAmount(dto.getyBTAmount());
		dto.setqBTTotalAmount(dto.getqBTAmount());
		dto.setpBTTotalAmount(dto.getpBTAmount());

		dto.setsDTotalAmount(dto.getsDAmount());
		dto.setxDTotalAmount(dto.getxDAmount());
		dto.setyDTotalAmount(dto.getyDAmount());
		dto.setqDTotalAmount(dto.getqDAmount());
		dto.setpDTotalAmount(dto.getpDAmount());
	}

	@Override
	public void setAccumteEconomicsProduct(EcnoProductUseExport pUseExport,EcnoProductUseExport  dto) {
		dto.setsBTTotalAmount(ArithUtil.addString(pUseExport.getsBTTotalAmount(), dto.getsBTAmount()));
		dto.setxBTTotalAmount(ArithUtil.addString(pUseExport.getxBTTotalAmount(), dto.getxBTAmount()));
		dto.setyBTTotalAmount(ArithUtil.addString(pUseExport.getyBTTotalAmount(), dto.getyBTAmount()));
		dto.setqBTTotalAmount(ArithUtil.addString(pUseExport.getqBTTotalAmount(), dto.getqBTAmount()));
		dto.setpBTTotalAmount(ArithUtil.addString(pUseExport.getpBTTotalAmount(), dto.getpBTAmount()));

		dto.setsDTotalAmount(ArithUtil.addString(pUseExport.getsDTotalAmount(), dto.getsDAmount()));
		dto.setxDTotalAmount(ArithUtil.addString(pUseExport.getxDTotalAmount(), dto.getxDAmount()));
		dto.setyDTotalAmount(ArithUtil.addString(pUseExport.getyDTotalAmount(), dto.getyDAmount()));
		dto.setqDTotalAmount(ArithUtil.addString(pUseExport.getqDTotalAmount(), dto.getqDAmount()));
		dto.setpDTotalAmount(ArithUtil.addString(pUseExport.getpDTotalAmount(), dto.getpDAmount()));

	}

	@Override
	public void setRepayEconomicsProduct(RepayInfo repayInfo, EcnoProductUseExport dto) {
		// 白条
		if (repayInfo.getProductType() == Constants.ONE) {
			switch (repayInfo.getSource()) {
			case Constants.ONE:
				dto.setsPayBTAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getsPayBTAmount()));
				break;
			case Constants.TWO:
				dto.setpPayBTAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getpPayBTAmount()));
				break;
			case Constants.THREE:
				dto.setxPayBTAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getxPayBTAmount()));
				break;
			case Constants.FOUR:
				dto.setyPayBTAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getyPayBTAmount()));
				break;
			case Constants.FIVE:
				dto.setqPayBTAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getqPayBTAmount()));
				break;
			default:
				break;
			}
		}

		// 化纤贷
		if (repayInfo.getProductType() == Constants.TWO) {

			switch (repayInfo.getSource()) {
			case Constants.ONE:
				dto.setsPayDAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getsPayDAmount()));
				break;
			case Constants.TWO:
				dto.setpPayDAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getpPayDAmount()));
				break;
			case Constants.THREE:
				dto.setxPayDAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getxPayDAmount()));
				break;
			case Constants.FOUR:
				dto.setyPayDAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getyPayDAmount()));
				break;
			case Constants.FIVE:
				dto.setqPayDAmount(ArithUtil.addStrD(repayInfo.getAmount(), dto.getqPayDAmount()));
				break;
			default:
				break;
			}
		}
		dto.setsPayBTTotalAmount(dto.getsPayBTAmount());
		dto.setxPayBTTotalAmount(dto.getxPayBTAmount());
		dto.setyPayBTTotalAmount(dto.getyPayBTAmount());
		dto.setqPayBTTotalAmount(dto.getqPayBTAmount());
		dto.setpPayBTTotalAmount(dto.getpPayBTAmount());

		dto.setsPayDTotalAmount(dto.getsPayDAmount());
		dto.setxPayDTotalAmount(dto.getxPayDAmount());
		dto.setyPayDTotalAmount(dto.getyPayDAmount());
		dto.setqPayDTotalAmount(dto.getqPayDAmount());
		dto.setpPayDTotalAmount(dto.getpPayDAmount());

	}

	@Override
	public void setAccumteRepayEconomicsProduct(EcnoProductUseExport pUseExport, EcnoProductUseExport dto) {
		dto.setsPayBTTotalAmount(ArithUtil.addString(pUseExport.getsPayBTTotalAmount(), dto.getsPayBTAmount()));
		dto.setxPayBTTotalAmount(ArithUtil.addString(pUseExport.getxPayBTTotalAmount(), dto.getxPayBTAmount()));
		dto.setyPayBTTotalAmount(ArithUtil.addString(pUseExport.getyPayBTTotalAmount(), dto.getyPayBTAmount()));
		dto.setqPayBTTotalAmount(ArithUtil.addString(pUseExport.getqPayBTTotalAmount(), dto.getqPayBTAmount()));
		dto.setpPayBTTotalAmount(ArithUtil.addString(pUseExport.getpPayBTTotalAmount(), dto.getpPayBTAmount()));

		dto.setsPayDTotalAmount(ArithUtil.addString(pUseExport.getsPayDTotalAmount(), dto.getsPayDAmount()));
		dto.setxPayDTotalAmount(ArithUtil.addString(pUseExport.getxPayDTotalAmount(), dto.getxPayDAmount()));
		dto.setyPayDTotalAmount(ArithUtil.addString(pUseExport.getyPayDTotalAmount(), dto.getyPayDAmount()));
		dto.setqPayDTotalAmount(ArithUtil.addString(pUseExport.getqPayDTotalAmount(), dto.getqPayDAmount()));
		dto.setpPayDTotalAmount(ArithUtil.addString(pUseExport.getpPayDTotalAmount(), dto.getpPayDAmount()));
	}

	@Override
	public void setNowAvailableAmount(String bankPk, EcnoProductUseExport dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creditTime", DateUtil.dateBeforeNowDay(1, 0, 0, 0));// 昨天
		map.put("bankPk", bankPk);
		map.put("type", Constants.ONE);// 白条
		List<B2bEconomicsBankCompanyExtDto> econList = economicsBankCompanyService.countAvailableAmount(map);
		if (econList != null && econList.size() > 0) {
			for (B2bEconomicsBankCompanyExtDto eb : econList) {
				String total = eb.getTotal();
				switch (eb.getSource()) {
				case Constants.ONE:
					dto.setsNowBTAmount(total);
					break;
				case Constants.TWO:
					dto.setpNowBTAmount(total);
					break;
				case Constants.THREE:
					dto.setxNowBTAmount(total);
					break;
				case Constants.FOUR:
					dto.setyNowBTAmount(total);
					break;
				case Constants.FIVE:
					dto.setqNowBTAmount(total);
					break;
				default:
					break;
				}
			}
		}

		map.put("type", Constants.TWO);// 白条
		econList = economicsBankCompanyService.countAvailableAmount(map);
		if (econList != null && econList.size() > 0) {
			for (B2bEconomicsBankCompanyExtDto eb : econList) {
				String total = eb.getTotal();
				switch (eb.getSource()) {
				case Constants.ONE:
					dto.setsNowDAmount(total);
					break;
				case Constants.TWO:
					dto.setpNowDAmount(total);
					break;
				case Constants.THREE:
					dto.setxNowDAmount(total);
					break;
				case Constants.FOUR:
					dto.setyNowDAmount(total);
					break;
				case Constants.FIVE:
					dto.setqNowDAmount(total);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void accumulativeEcnoProductUse(EcnoProductUseExport nowece, EcnoProductUseExport ep, int flag) {
		if (flag == 1) {
			nowece.setsBTAmount(ArithUtil.addString(nowece.getsBTAmount() , ep.getsBTAmount()));
			nowece.setxBTAmount(ArithUtil.addString(nowece.getxBTAmount() , ep.getxBTAmount()));
			nowece.setyBTAmount(ArithUtil.addString(nowece.getyBTAmount() , ep.getyBTAmount()));
			nowece.setqBTAmount(ArithUtil.addString(nowece.getqBTAmount() , ep.getqBTAmount()));
			nowece.setpBTAmount(ArithUtil.addString(nowece.getpBTAmount() , ep.getpBTAmount()));

			nowece.setsDAmount(ArithUtil.addString(nowece.getsDAmount() , ep.getsDAmount()));
			nowece.setxDAmount(ArithUtil.addString(nowece.getxDAmount() , ep.getxDAmount()));
			nowece.setyDAmount(ArithUtil.addString(nowece.getyDAmount() , ep.getyDAmount()));
			nowece.setqDAmount(ArithUtil.addString(nowece.getqDAmount() , ep.getqDAmount()));
			nowece.setpDAmount(ArithUtil.addString(nowece.getpDAmount() , ep.getpDAmount()));

			nowece.setsPayBTAmount(ArithUtil.addString(nowece.getsPayBTAmount() , ep.getsPayBTAmount()));
			nowece.setxPayBTAmount(ArithUtil.addString(nowece.getxPayBTAmount() , ep.getxPayBTAmount()));
			nowece.setyPayBTAmount(ArithUtil.addString(nowece.getyPayBTAmount() , ep.getyPayBTAmount()));
			nowece.setqPayBTAmount(ArithUtil.addString(nowece.getqPayBTAmount() , ep.getqPayBTAmount()));
			nowece.setpPayBTAmount(ArithUtil.addString(nowece.getpPayBTAmount() , ep.getpPayBTAmount()));

			nowece.setsPayDAmount(ArithUtil.addString(nowece.getsPayDAmount() , ep.getsPayDAmount()));
			nowece.setxPayDAmount(ArithUtil.addString(nowece.getxPayDAmount() , ep.getxPayDAmount()));
			nowece.setyPayDAmount(ArithUtil.addString(nowece.getyPayDAmount() , ep.getyPayDAmount()));
			nowece.setqPayDAmount(ArithUtil.addString(nowece.getqPayDAmount() , ep.getqPayDAmount()));
			nowece.setpPayDAmount(ArithUtil.addString(nowece.getpPayDAmount() , ep.getpPayDAmount()));
		}

		nowece.setsBTTotalAmount(ep.getsBTTotalAmount());
		nowece.setxBTTotalAmount(ep.getxBTTotalAmount());
		nowece.setyBTTotalAmount(ep.getyBTTotalAmount());
		nowece.setqBTTotalAmount(ep.getqBTTotalAmount());
		nowece.setpBTTotalAmount(ep.getpBTTotalAmount());

		nowece.setsDTotalAmount(ep.getsDTotalAmount());
		nowece.setxDTotalAmount(ep.getxDTotalAmount());
		nowece.setyDTotalAmount(ep.getyDTotalAmount());
		nowece.setqDTotalAmount(ep.getqDTotalAmount());
		nowece.setpDTotalAmount(ep.getpDTotalAmount());

		nowece.setsPayBTTotalAmount(ep.getsPayBTTotalAmount());
		nowece.setxPayBTTotalAmount(ep.getxPayBTTotalAmount());
		nowece.setyPayBTTotalAmount(ep.getyPayBTTotalAmount());
		nowece.setqPayBTTotalAmount(ep.getqPayBTTotalAmount());
		nowece.setpPayBTTotalAmount(ep.getpPayBTTotalAmount());

		nowece.setsPayDTotalAmount(ep.getsPayDTotalAmount());
		nowece.setxPayDTotalAmount(ep.getxPayDTotalAmount());
		nowece.setyPayDTotalAmount(ep.getyPayDTotalAmount());
		nowece.setqPayDTotalAmount(ep.getqPayDTotalAmount());
		nowece.setpPayDTotalAmount(ep.getpPayDTotalAmount());

		nowece.setsNowBTAmount(ep.getsNowBTAmount());
		nowece.setxNowBTAmount(ep.getxNowBTAmount());
		nowece.setyNowBTAmount(ep.getyNowBTAmount());
		nowece.setqNowBTAmount(ep.getqNowBTAmount());
		nowece.setpNowBTAmount(ep.getpNowBTAmount());

		nowece.setsNowDAmount(ep.getsNowDAmount());
		nowece.setxNowDAmount(ep.getxNowDAmount());
		nowece.setyNowDAmount(ep.getyNowDAmount());
		nowece.setqNowDAmount(ep.getqNowDAmount());
		nowece.setpNowDAmount(ep.getpNowDAmount());
	}

}
