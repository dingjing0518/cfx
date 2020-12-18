package cn.cf.task;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.dao.B2bTokenDao;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.entity.OrderSyncToMongo;
import cn.cf.service.B2bOrderService;
import cn.cf.service.HuaxianhuiCrmService;

@Component
@EnableScheduling
public class ErpSchuduler {

	@Autowired
	private B2bTokenDao b2bTokenDao;

	@Autowired
	private B2bOrderService b2bOrderService;

	@Autowired
	private HuaxianhuiCrmService huaxianhuiCrmService;

	@Autowired
	private MongoTemplate mongoTemplate;

	private Logger logger = LoggerFactory.getLogger(ErpSchuduler.class);

	/**
	 * 每5分钟同步未同步成功的订单
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void syncOrderNumber() throws Exception {
		// 查询未同步过的订单
		Criteria orderSync = new Criteria();
		orderSync.andOperator(Criteria.where("isPush").is(2));
		Query orderStatusQuery = new Query(orderSync);
		orderStatusQuery.with(new Sort(Direction.ASC, "orderNumber"));
		//orderStatusQuery.skip(0).limit(10);
		List<OrderSyncToMongo> orderSyncList = mongoTemplate.find(
				orderStatusQuery, OrderSyncToMongo.class);
		//this.syncOrderSchuduler(orderSyncList);
		this.syncOrder(orderSyncList);


	}

	private void syncOrder(List<OrderSyncToMongo> orderSyncList) {
		if (orderSyncList != null && orderSyncList.size() > 0) {
			logger.info("*********************syncOrderNumber_Start*****************");
			for (OrderSyncToMongo orderSyncToMongo : orderSyncList) {
				B2bTokenDto b2bTokenDto = b2bTokenDao
						.getByStorePk(orderSyncToMongo.getStorePk());
				if (null != b2bTokenDto && 1 == b2bTokenDto.getIsDelete()
						&& 1 == b2bTokenDto.getIsVisable()) {
					String str = huaxianhuiCrmService
							.syncOrder(orderSyncToMongo.getOrderNumber());
					logger.info("*********************syncOrderNumber_resultData*****************"
							+ str
							+ " orderNumer:"
							+ orderSyncToMongo.getOrderNumber());
					// 如果同步成功，标记已推送
					if (!"".equals(str) && str != null) {
						JSONObject jsonData = JSONObject.fromObject(str);
						if (jsonData.get("code") != null
								&& "0000".equals(jsonData.get("code"))) {
							Query querys = Query.query(Criteria.where(
									"orderNumber").is(
									orderSyncToMongo.getOrderNumber()));
							mongoTemplate
									.remove(querys, OrderSyncToMongo.class);
						}
					}
				}
			}
		}
		
	}

	private void syncOrderSchuduler(List<OrderSyncToMongo> orderSyncList) {
		if (orderSyncList != null && orderSyncList.size() > 0) {
			logger.info("*********************syncOrderNumber_Start*****************");
			List<OrderSyncToMongo> olist = new ArrayList<OrderSyncToMongo>();
			for (OrderSyncToMongo orderSyncToMongo : orderSyncList) {
				B2bTokenDto b2bTokenDto = b2bTokenDao.getByStorePk(orderSyncToMongo.getStorePk());
				if (null != b2bTokenDto && 1 == b2bTokenDto.getIsDelete()
						&& 1 == b2bTokenDto.getIsVisable()) {
					olist.add(orderSyncToMongo);
				}
			}
			if (null != olist && olist.size() > 0) {
				huaxianhuiCrmService.syncNoPushOrder(olist);
			}
			logger.info("*********************syncOrderNumber_End*****************");
		}
	}

	/**
	 * 每10分钟同步未同步成功的合同
	 * 
	 * @throws Exception
	 */

	@Scheduled(cron = "0 0/10 * * * ?")
	public void syncContractNo() throws Exception {
		// 查询未同步过的订单
		Criteria orderSync = new Criteria();
		orderSync.andOperator(Criteria.where("isPush").is(2));
		Query orderStatusQuery = new Query(orderSync);
		orderStatusQuery.with(new Sort(Direction.ASC, "contractNumber"));
		List<ContractSyncToMongo> orderSyncList = mongoTemplate.find(
				orderStatusQuery, ContractSyncToMongo.class);
		if (orderSyncList != null && orderSyncList.size() > 0) {
			logger.info("*********************syncContractNo_Start*****************");
			for (ContractSyncToMongo contractSyncToMongo : orderSyncList) {
				B2bTokenDto b2bTokenDto = b2bTokenDao
						.getByStorePk(contractSyncToMongo.getStorePk());
				if (b2bTokenDto != null) {
					String str = huaxianhuiCrmService
							.syncContract(contractSyncToMongo
									.getContractNumber());
					logger.info("*********************syncContractNo_resultData*****************"
							+ str
							+ " contractNumber:"
							+ contractSyncToMongo.getContractNumber());
					// 如果同步成功，标记已推送
					if (!"".equals(str) && str != null) {
						JSONObject jsonData = JSONObject.fromObject(str);
						if (jsonData.get("code") != null
								&& "0000".equals(jsonData.get("code"))) {
							Query querys = Query.query(Criteria.where(
									"contractNumber").is(
									contractSyncToMongo.getContractNumber()));
							mongoTemplate.remove(querys,
									ContractSyncToMongo.class);
						}
					}
				}
			}
		}

	}

	/**
	 * 每10秒钟同步刚提交的订单
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void syncNoPushOrderNumber() throws Exception {
		// 查询未同步过的订单
		Criteria orderSync = new Criteria();
		orderSync.andOperator(Criteria.where("isPush").is(1));
		Query orderStatusQuery = new Query(orderSync);
		orderStatusQuery.with(new Sort(Direction.ASC, "orderNumber"));
		//orderStatusQuery.skip(0).limit(10);
		List<OrderSyncToMongo> orderSyncList = mongoTemplate.find(
				orderStatusQuery, OrderSyncToMongo.class);
		//syncOrderSchuduler(orderSyncList);
		syncOrder(orderSyncList);
	}

	/**
	 * 每分钟同步刚提交的合同
	 * 
	 * @throws Exception
	 */

	@Scheduled(cron = "0 0/1 * * * ?")
	public void syncNoPushContractNo() throws Exception {
		// 查询未同步过的订单
		Criteria orderSync = new Criteria();
		orderSync.andOperator(Criteria.where("isPush").is(1));
		Query orderStatusQuery = new Query(orderSync);
		orderStatusQuery.with(new Sort(Direction.ASC, "contractNumber"));
		List<ContractSyncToMongo> orderSyncList = mongoTemplate.find(
				orderStatusQuery, ContractSyncToMongo.class);
		if (orderSyncList != null && orderSyncList.size() > 0) {
			logger.info("*********************syncContractNo_Start*****************");
			for (ContractSyncToMongo contractSyncToMongo : orderSyncList) {
				B2bTokenDto b2bTokenDto = b2bTokenDao
						.getByStorePk(contractSyncToMongo.getStorePk());
				if (b2bTokenDto != null) {
					String str = huaxianhuiCrmService
							.syncContract(contractSyncToMongo
									.getContractNumber());
					logger.info("*********************syncContractNo_resultData*****************"
							+ str
							+ " contractNumber:"
							+ contractSyncToMongo.getContractNumber());
					// 如果同步成功，标记已推送
					if (!"".equals(str) && str != null) {
						JSONObject jsonData = JSONObject.fromObject(str);
						if (jsonData.get("code") != null
								&& "0000".equals(jsonData.get("code"))) {
							Query querys = Query.query(Criteria.where(
									"contractNumber").is(
									contractSyncToMongo.getContractNumber()));
							mongoTemplate.remove(querys,
									ContractSyncToMongo.class);
						}
					}
				}
			}
		}

	}

}
