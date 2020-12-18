package cn.cf.task;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.RestCode;
import cn.cf.entity.CustomerDataImport;
import cn.cf.entry.RespCustomer;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bCustomerMangementService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.MemberFacadeService;
import cn.cf.service.impl.B2bCustomerMangementServiceImpl;
import cn.cf.util.DateUtil;
import cn.cf.util.SavaZipUtil;

@Component
public class Scheduler {

	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MemberFacadeService memberFacadeService;

	/**
	 * 执行时间:每天凌晨0:00执行一次 定时任务:周期性增加会员积分
	 */
	/*
	 * @Scheduled(cron = "0 0 0 * * ?") public void addPointPeriod() {
	 * b2bMemberService.addPointPeriod(); }
	 */

	/**
	 * 执行时间:每天凌晨0:00执行一次 定时任务:会员等级更新
	 */

	@Scheduled(cron = "0 0 0 * * ?")
	public void statusCheck() {
		// System.out.println("q222");
		b2bMemberService.upgradeMembers();
	}

	/**
	 * 执行时间:每1分钟执行一次 定时任务:同步客户导入信息
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void updateAgentPayStatus() {
		Criteria c = new Criteria();
		c.and("status").is(1);
		c.and("insertTime").lte(DateUtil.timeOfBeforeMin(-10));
		Query qu = new Query(c);
		List<CustomerDataImport> list = mongoTemplate.find(qu,
				CustomerDataImport.class);
		if (null != list && list.size() > 0) {
			for (CustomerDataImport dto : list) {
				RestCode code = memberFacadeService.customerImport(
						dto.getUrl(), dto.getStorePk());
				Update update = new Update();
				if (RestCode.CODE_0000.getCode().equals(code.getCode())) {
					update.set("status", 2);// 成功
				} else {
					update.set("status", 3);// 失败
					update.set("remark", code.getMsg());
				}
				mongoTemplate.upsert(
						new Query(Criteria.where("id").is(dto.getId())),
						update, CustomerDataImport.class);
			}
		}
	}

	public static void main(String[] args) {
		RestCode code = RestCode.CODE_0000;
		String filePath = "D://工作//工作-纤联//盛虹//20190422//20190422.zip";
		SavaZipUtil.saveZip(filePath,
				PropertyConfig.getProperty("customer_file_path"));
		File file = new File(PropertyConfig.getProperty("customer_file_path"));
		B2bCustomerMangementService service = new B2bCustomerMangementServiceImpl();
		RespCustomer rc = service.customerList(file);
		if (rc.getCode().equals(RestCode.CODE_0000.getCode())) {
			service.b2bCompany(rc.getCustomerList(), "1");
		} else {
			code = RestCode.CODE_Z000;
			code.setMsg(rc.getMsg());
		}
	}
}
