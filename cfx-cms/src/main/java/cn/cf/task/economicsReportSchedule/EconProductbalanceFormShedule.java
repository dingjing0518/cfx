package cn.cf.task.economicsReportSchedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dao.B2bLoanNumberExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.entity.BankBalanceEntity;
import cn.cf.entity.EconProductbalanceReport;
import cn.cf.entity.LoanDetail;
@Component
@EnableScheduling
public class EconProductbalanceFormShedule {
	
	private final static Logger logger = LoggerFactory.getLogger(EconProductbalanceFormShedule.class);
	
	@Autowired
	private B2bEconomicsBankExtDao b2bEconomicsBankExtDao;
	
	@Autowired
	private B2bLoanNumberExtDao b2bLoanNumberExtDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
 	@Scheduled(cron = "0 0 6 * * ?")
	public void EconProductbalance() {
		String yesterDay = CommonUtil.yesterDay();// 昨日
		EconProductbalanceReport  report = new EconProductbalanceReport();
		report.setDate(yesterDay);
		//获取所有存在的银行 
		List<B2bEconomicsBankDto> list = b2bEconomicsBankExtDao.searchListOrderName();
		List<BankBalanceEntity>  bankBalancelist = new ArrayList<BankBalanceEntity>();
		if (list != null && list.size() > 0) {
			for (B2bEconomicsBankDto b2bEconomicsBankDto : list) {
				BankBalanceEntity entity = new BankBalanceEntity();
				entity.setBankPk(b2bEconomicsBankDto.getPk());
				entity.setBankName(b2bEconomicsBankDto.getBankName());

				// 查找白条订单
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("bankPk", b2bEconomicsBankDto.getPk());
				map.put("economicsGoodsType", 1);
				String btAmount = b2bLoanNumberExtDao.searchBankAmount(map);
				entity.setBtAmount(new BigDecimal(btAmount));
				List<LoanDetail> btList = b2bLoanNumberExtDao.searchLoanDetail(map);
				if (btList.size()>0) {
					entity.setBtList(btList);
				}
			
				map.put("economicsGoodsType", 2);
				String dAmount = b2bLoanNumberExtDao.searchBankAmount(map);
				entity.setdAmount(new BigDecimal(dAmount));
				List<LoanDetail> dList = b2bLoanNumberExtDao.searchLoanDetail(map);
				if (dList.size()>0) {
					entity.setdList(dList);
				}
				bankBalancelist.add(entity);
			}
			report.setBankList(bankBalancelist);
		}
		
		mongoTemplate.insert(report);
		}

}
