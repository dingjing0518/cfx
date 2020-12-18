package cn.cf.task.economicsReportSchedule;

import cn.cf.common.Constants;
import cn.cf.dao.B2bProductPriceIndexExtDao;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.entity.*;
import cn.cf.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class ProductPriceIndexSchedule {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bProductPriceIndexExtDao b2bProductPriceIndexExtDao;

	/**
	 * 执行时间:每天凌晨0:05执行一次
	 * @throws ParseException 
	 */
	@Scheduled(cron = "0 5 0 * * ?")
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void updateProductPriceIndex() {

		List<ProductPriceIndexEntry> list = b2bProductPriceIndexExtDao.getLoanNumberInfo();
		if (list != null && list.size() > 0){
			for (ProductPriceIndexEntry entry:list) {
				Criteria criteria = new Criteria();
				criteria.and("childOrderNumber").is(entry.getChildOrderNumber());
				Query query = Query.query(criteria);
				//只可能有一条数据
				List<ProductPriceIndexEntry> entryList = mongoTemplate.find(query,ProductPriceIndexEntry.class);
				if (entryList.size()==0){
					entry.setInsertTime(DateUtil.formatDateAndTime(new Date()));
					entry.setUpdateTime(DateUtil.formatDateAndTime(new Date()));
					entry.setIsConfirm(Constants.ONE);
					mongoTemplate.insert(entry);
				}

			}
		}
	}

}
