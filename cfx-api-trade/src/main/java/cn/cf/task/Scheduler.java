package cn.cf.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.dao.B2bOrderDaoEx;
import cn.cf.dao.B2bTrancsationContractDaoEx;
import cn.cf.dao.B2bTrancsationDaoEx;
import cn.cf.dto.B2bBindOrderDtoEx;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bReserveOrderDto;
import cn.cf.entity.UnsynErpOrder;
import cn.cf.model.B2bTrancsation;
import cn.cf.model.B2bTrancsationContract;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.service.B2bAuctionOfferService;
import cn.cf.service.B2bAuctionService;
import cn.cf.service.B2bBindOrderService;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bReserveService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

/**
 * @author:FJM
 * @describe:定时任务类
 * @time:2017-12-15 上午12:48:54
 */
@Component
public class Scheduler {

    @Autowired
    private B2bTrancsationDaoEx b2bTrancsationDaoEx;
    
    @Autowired
    private B2bTrancsationContractDaoEx b2bTrancsationContractDaoEx;

    @Autowired
    private B2bOrderService b2bOrderService;

    @Autowired
    private B2bOrderDaoEx b2bOrderDaoEx;

    @Autowired
    private B2bFacadeService b2bFacadeService;
    
    @Autowired
    private B2bAuctionService b2bAuctionService;
    
    @Autowired
    private B2bAuctionGoodsService b2bAuctionGoodsService;
    
    @Autowired
    private B2bAuctionOfferService b2bAuctionOfferService;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private B2bBindOrderService b2bBindOrderService;
    
    @Autowired
    private B2bContractService b2bContractService;
	
	@Autowired
	private B2bReserveService reserveService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 执行时间:每天凌晨0:00执行一次
     * 定时任务:自动关闭订单(目前关闭待付款的订单)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void statusCheck() {
        List<B2bOrderDto> orderList = b2bOrderDaoEx.cancelOrder();
        if (orderList != null && orderList.size() > 0) {
            for (B2bOrderDto o : orderList) {
                b2bFacadeService.cancelOrder(null, o.getOrderNumber(), false);
            }
        }
       List<B2bContractDto> contractList =  b2bContractService.cancelContract();
       if(null != contractList && contractList.size()>0){
    	   for(B2bContractDto c : contractList){
    		   b2bFacadeService.cancelContract(null, c.getContractNo());
    	   }
       }
    }
    /**
     * 执行时间:每天凌程0:30执行一次
     * 清空所有拼团
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void updateOverdueBind() {
    	b2bFacadeService.updateOverdueBind();
    }

    /**
     * 执行时间:每天凌晨1:00执行一次
     * 定时任务:统计交易数据(订单数据与合同数据)
     */
    @Scheduled(cron = "0 0 1 * * ?")
    // 每天凌晨1点执行一次
    public void orderTrancsation() {
        //订单交易数据
    	List<B2bTrancsation> list = b2bTrancsationDaoEx.trancsations();
        if (list != null && list.size() > 0) {
            for (B2bTrancsation b : list) {
                b.setPk(KeyUtils.getUUID());
                b.setIsDelete(1);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                Date time = cal.getTime();
                b.setTrancsationDate(DateUtil.formatYearMonthDay(time));
                try {
                	b2bTrancsationDaoEx.insert(b);
				} catch (Exception e) {
					logger.error("orderTrancsation",e);
				}
            }
        }
        
        //合同交易数据
        List<B2bTrancsationContract> contractList = b2bTrancsationContractDaoEx.trancsations();
        if (contractList != null && contractList.size() > 0) {
            for (B2bTrancsationContract b : contractList) {
                b.setPk(KeyUtils.getUUID());
                b.setIsDelete(1);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                Date time = cal.getTime();
                b.setTransactionDate(DateUtil.formatYearMonthDay(time));
                try {
                	b2bTrancsationContractDaoEx.insert(b);
				} catch (Exception e) {
					logger.error("orderTrancsation",e);
				}
            }
        }
        

    }

    
    /**
     * 执行时间:每1分钟执行一次
     * 定时任务:自动中标（判断有没有活动结束的场次，有场次，则对该场次进行自动中标）
     */
    @Scheduled(cron = "0 */1 * * * ?")
    // 每分钟执行一次操作自动中标
    public void afterAuction() {
		b2bAuctionService.automaticBidauctionOffer();
    }

    /**
     * 执行时间:凌晨00：00
     * 定时任务:未提交的出价记录设置为已过期
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAuctionIsFinishedAt24() {
    	b2bAuctionOfferService.updateAuctionIsFinishedAt24();
    }
    
    
    /**
     * 执行时间:每天凌晨10分执行
     * 定时任务:竞拍中的商品，已经结束后，自动更新为正常商品
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void updateAuctionGoodsToNormal() {
    	b2bAuctionGoodsService.updateAuctionGoodsToNormal();
    }

    
    /**
     * 每小时定时推送erp同步失败的订单
     * 
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void synchronizedErpOrder() {
    	List<UnsynErpOrder> list =  mongoTemplate.findAll(UnsynErpOrder.class);
    	if(null != list && list.size()>0){
    		for(UnsynErpOrder o : list){
    			Query query = new Query(Criteria.where("orderNumber").is(
						o.getOrderNumber()));
				mongoTemplate.remove(query, UnsynErpOrder.class);
				b2bFacadeService.sendErp(o.getOrderNumber(), o.getStorePk(), o.getType());
    		}
    	}
    }
    
    /**
     *拼团需求单转化成订单
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void searchBindToOrder() {
    	List<B2bBindOrderDtoEx> list = b2bBindOrderService.searchBindToOrder();
    	if(null != list && list.size()>0){
    		for(B2bBindOrderDtoEx dto : list){
    			b2bFacadeService.bindToOrder(dto.getOrderNumber(), null);
    		}
    	}
    }
    /**
     * 每天凌晨6点自动转化预约单
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void reservesToOrder() {
		 List<B2bReserveOrderDto>  list=reserveService.searchReserveOrderListForToday();
		 if (null!=list&&list.size()>0) {
			for (B2bReserveOrderDto order :list) {
				b2bFacadeService.reservesToOrder(order.getOrderNumber(), null);
			}
		}
    }
    
    /**
     * 执行时间:每天凌程00:45执行一次
     * 执行任务：订单，合同，自动完成
     */
    @Scheduled(cron = "0 45 0 * * ?")
    public void autoCompleteOrderContract() {
    	
    	//部分发货订单15天后自动完成
    	b2bOrderService.autoCompleteOrder(15,5);
    	
    	//待收货订单15天自动完成
    	b2bOrderService.autoCompleteOrder(15,4);
    	
    	
    	//部分发货合同30天自动完成
    	b2bContractService.autoCompleteContract(30,4);
    	
    	//待收货合同30天自动完成
    	b2bContractService.autoCompleteContract(30,5);
    }
}
