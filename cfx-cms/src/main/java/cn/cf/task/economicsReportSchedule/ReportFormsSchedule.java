package cn.cf.task.economicsReportSchedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.Constants;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.entity.BankApproveAmountExport;
import cn.cf.entity.EcnoProductUseExport;
import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.service.enconmics.EconReportBankApproveAmountService;
import cn.cf.service.enconmics.EconReportCustomerService;
import cn.cf.service.enconmics.EconReportProductUseService;

@Component
@EnableScheduling
public class ReportFormsSchedule {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private EconReportBankApproveAmountService conReportBankApproveAmountService;

	@Autowired
	private EconReportCustomerService econReportCustomerService;
	
	@Autowired
	private EconReportProductUseService econReportProductUseService;

	/**
	 * 执行时间:每天凌晨0:30执行一次 定时任务:统计前天的金融产品审批表(内部审批) 来源：1:盛虹;2：平台;3：新凤鸣;4：营销;5:其他
	 * 产品类型：1:化纤白条；2：化纤贷；3：化纤白条+化纤贷 审批类型：1：审核中；2：通过；3：否定
	 * @throws ParseException 
	 */
	@Scheduled(cron = "0 30 0 * * ?")
//	@Scheduled(cron = "0 0/2 * * * ?")
	public void countCustomerApprove() throws ParseException {
 		List<EconCustomerApproveExport> ecelist = new ArrayList<EconCustomerApproveExport>();// 昨天的最新数据
		// 1.删除昨日的记录
 		Query qu = new Query(Criteria.where("flag").is(Constants.ONE));
		mongoTemplate.remove(qu, EconCustomerApproveExport.class);

		// 2.插入新的昨日的记录 ； 查询前一天的申请记录
		List<EconCustomerMongo> list =	econReportCustomerService.getApproveBeforeDay();
		ecelist = econReportCustomerService.getYesterdayCustomerApprove(list);
		mongoTemplate.insertAll(ecelist);
		
		// 3.更新本周数据:当前日期是周一，删除本周数据，更新上周数据；非周一，更新本周数据
		econReportCustomerService.UpdateWeekData(ecelist);
		
		// 4.更新当月的数据
		econReportCustomerService.UpdateMonthData(ecelist);
		
		
	}

	/**
	 * 银行审批额度 每个月21号执行
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	public void bankApproveAmount() {
		List<BankApproveAmountExport> bamList = new ArrayList<BankApproveAmountExport>();
		
		// 获取昨日的新有效的额度,按银行排序
		List<B2bEconomicsBankCompanyExtDto> list = conReportBankApproveAmountService.searchBankApproveAmount();
		// 1.删除芒果 所有银行昨日的记录
		mongoTemplate.remove(new Query(Criteria.where("flag").is(Constants.ONE)), BankApproveAmountExport.class);
		// 2.插入新的昨日数据
		bamList =conReportBankApproveAmountService.getYesterdayBankApproveAmount(list); 
		mongoTemplate.insertAll(bamList);
		// 3.更新本周数据:当前日期是周一，删除本周数据，更新上周数据；非周一，更新本周数据
		conReportBankApproveAmountService.UpdateWeekData(bamList);
		// 4.更新上月的数据:按月统计有效额度
		conReportBankApproveAmountService.UpdateMonthData();
	}

	/**
	 * 金融产品的使用
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	// @Scheduled(cron = "0 50 21 * * ?")
	public void ecnoProductUse() {
		// 获取昨日数据
		List<EcnoProductUseExport> list =econReportProductUseService.searchEcnoProductUse();
		// 1.删除芒果 所有银行昨日的记录
		mongoTemplate.remove(new Query(Criteria.where("flag").is(Constants.ONE)), EcnoProductUseExport.class);
		// 2.插入新的昨日数据
		mongoTemplate.insertAll(list);
		// 3.更新本周数据:当前日期是周一，删除本周数据，更新上周数据；非周一，更新本周数据
		econReportProductUseService.UpdateWeekData(list);
		// 4.更新上月的数据:按月统计有效额度
		econReportProductUseService.UpdateMonthData(list);	
	}

}
