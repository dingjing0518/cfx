package cn.cf.service.enconmics.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bBillCusgoodsApplyExtDao;
import cn.cf.dao.B2bBillCustomerApplyExtDao;
import cn.cf.dao.ManageAccountExtDao;
import cn.cf.dto.B2bBillCusgoodsApplyDto;
import cn.cf.dto.B2bBillCustomerApplyDto;
import cn.cf.dto.B2bBillCustomerApplyExtDto;
import cn.cf.dto.MemberShip;
import cn.cf.entity.BillCustomerMongo;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.service.enconmics.BillCustomerApplyService;
import cn.cf.util.StringUtils;

@Service
public class BillCustomerApplyServiceImpl  implements  BillCustomerApplyService{
	
	@Autowired
	private ManageAccountExtDao manageAccountExtDao;
	
	@Autowired
	private B2bBillCustomerApplyExtDao  b2bBillCustomerApplyExtDao;
	
	@Autowired
	private B2bBillCusgoodsApplyExtDao b2bBillCusgoodsApplyDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	   @Autowired
	    private TaskService taskService;
	
	@Override
	public PageModel<B2bBillCustomerApplyExtDto> searchBillCustomerApplyList(QueryModel<B2bBillCustomerApplyExtDto> qm,
			MemberShip currentMemberShip) {

		PageModel<B2bBillCustomerApplyExtDto> pm = new PageModel<B2bBillCustomerApplyExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("status", qm.getEntity().getStatus());
		map.put("isDelete", Constants.ONE);
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("contactsTel", qm.getEntity().getContactsTel());
		map.put("updateTimeBegin", qm.getEntity().getUpdateStartTime());
		map.put("updateTimeEnd", qm.getEntity().getUpdateEndTime());
		map.put("insertTimeBegin", qm.getEntity().getInsertStartTime());
		map.put("insertTimeEnd", qm.getEntity().getInsertEndTime());
		
		map.put("colCompanyName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_ECONOMICS_BILL_CUSTOMER_COL_COMNAME));
        map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_ECONOMICS_BILL_CUSTOMER_COL_CONTACTS));
        map.put("colContactsTel", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_ECONOMICS_BILL_CUSTOMER_COL_CONTACTSTEL));

		if (null != currentMemberShip.getGroupId()) {
			if ("jingrongzhuanyuan".equals(currentMemberShip.getActGroupDto().getId())) {
				map.put("assidirPk", manageAccountExtDao.getByAccount(currentMemberShip.getUserId()).getPk());
			} else {
				map.put("assidirPk", qm.getEntity().getAssidirPk());
			}
		}
		
		int totalCount = b2bBillCustomerApplyExtDao.searchGridExtCount(map);
		List<B2bBillCustomerApplyExtDto> list = b2bBillCustomerApplyExtDao.searchGridExt(map);

		if (list != null && list.size() > 0) {
			for (B2bBillCustomerApplyExtDto billCustomerApplyExtDto : list) {
				
				if (null != currentMemberShip.getActGroupDto() && null != currentMemberShip.getActGroupDto().getId()) {
					billCustomerApplyExtDto.setRoleName(currentMemberShip.getActGroupDto().getId());
				}
				
				// 该公司是否存在申请记录标识:1存在；0没有
				if (isExitHistory(billCustomerApplyExtDto.getCompanyPk())) {
					billCustomerApplyExtDto.setIsExitHistory(Constants.ONE);
				} else {
					billCustomerApplyExtDto.setIsExitHistory(Constants.ZERO);
				}
				
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	
	
	private boolean isExitHistory(String companyPk) {
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("companyPk").is(companyPk));
		Query query = new Query(c);
		List<BillCustomerMongo> list = mongoTemplate.find(query, BillCustomerMongo.class);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public int updateBillCustomer(B2bBillCustomerApplyExtDto dto) {
		return b2bBillCustomerApplyExtDao.updateExt(dto);
	}


	@Override
	public void insertBillCustomerMongo(String pk) {
		B2bBillCustomerApplyDto  dto = b2bBillCustomerApplyExtDao.getByPk(pk);
		BillCustomerMongo  mongo = new  BillCustomerMongo();
		mongo.setPk(dto.getPk());
		mongo.setCompanyPk(dto.getCompanyPk());
		mongo.setCompanyName(dto.getCompanyName());
		mongo.setContacts(dto.getContacts());
		mongo.setContactsTel(dto.getContactsTel());
		mongo.setInsertTime(dto.getInsertTime());
		mongo.setStatus(dto.getStatus());
		mongo.setAssidirPk(dto.getAssidirPk());
		mongo.setAssidirName(dto.getAssidirName());
		mongo.setProcessInstanceId(dto.getProcessInstanceId());
		mongo.setApproveTime(dto.getApprovalTime());
		Map<String, Object> goodsMap = new HashMap<String, Object>();
		goodsMap.put("customerPk", pk);
		mongo.setGoodsList(b2bBillCusgoodsApplyDao.searchList(goodsMap));
		mongoTemplate.insert(mongo);
	}


	@Override
	public PageModel<B2bBillCustomerApplyExtDto> searchBillTask(int start, int limit, String groupId, String accountPk) {
        long total = 0l;
        PageModel<B2bBillCustomerApplyExtDto> pm = new PageModel<B2bBillCustomerApplyExtDto>();
        List<B2bBillCustomerApplyExtDto> dataList = new ArrayList<B2bBillCustomerApplyExtDto>();
        if (groupId != null) {
            // 获取总记录数
            total = taskService.createTaskQuery()
                    // 根据用户id查询
            		.processDefinitionKey("billProcess").taskCandidateGroup(groupId).count(); // 获取总记录数
            List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("billProcess").taskCandidateGroup(groupId).orderByTaskCreateTime().desc()
                    // 返回带分页的结果集合
                    .listPage(start, limit);
            for (Task t : taskList) {
                String customerPk = (String) taskService.getVariable(t.getId(), "billCustPk");
                Map<String, Object> map = new HashMap<>();
                map.put("pk", customerPk);
                B2bBillCustomerApplyExtDto dto = b2bBillCustomerApplyExtDao.getByMap(map);
                if (dto != null) {
                    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYING_BTN_COM_NAME)) {
                    	dto.setCompanyName(CommonUtil.hideCompanyName(dto.getCompanyName()));
                    }
                    
                    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYING_BTN_CONTACTS)) {
                    	dto.setContacts(CommonUtil.hideContacts(dto.getContacts()));
                    }
                    
                    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYING_BTN_CONTACTSTEL)) {
                    	dto.setContactsTel(CommonUtil.hideContactTel(dto.getContactsTel()));
                    }
                	Map<String, Object> param = new HashMap<String, Object>();
            		param.put("customerPk", dto.getPk());
            		List<B2bBillCusgoodsApplyDto> goodsList = b2bBillCusgoodsApplyDao.searchList(param);
            		dto.setGoodsName(getGoodsName(goodsList));
                    dto.setTaskId(t.getId());
                    dataList.add(dto);
                }
            }
        }
        pm.setDataList(dataList);
        pm.setTotalCount((int) total);
        return pm;
    }


	@Override
	public PageModel<BillCustomerMongo> getByMap(Map<String, Object> map, QueryModel<BillCustomerMongo> qm, String accountPk,
			int flag) {
		PageModel<BillCustomerMongo> pm = new PageModel<BillCustomerMongo>();
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("status").is(map.get("status")));
		Query query = new Query(c);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());

		List<BillCustomerMongo> list = mongoTemplate.find(query, BillCustomerMongo.class);
		int counts = (int) mongoTemplate.count(query, BillCustomerMongo.class);
		
		for (BillCustomerMongo econCustomerMongo : list) {
		    //审批通过
		    if (flag ==1) {
		        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYPASS_BTN_COM_NAME)) {
		            econCustomerMongo.setCompanyName(CommonUtil.hideCompanyName(econCustomerMongo.getCompanyName()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYPASS_BTN_CONTACTS)) {
                    econCustomerMongo.setContacts(CommonUtil.hideContacts(econCustomerMongo.getContacts()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYPASS_BTN_CONTACTSTEL)) {
                    econCustomerMongo.setContactsTel(CommonUtil.hideContactTel(econCustomerMongo.getContactsTel()));
                }
            }else if (flag == 2){ //审批驳回
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYUNPASS_BTN_COM_NAME)) {
                    econCustomerMongo.setCompanyName(CommonUtil.hideCompanyName(econCustomerMongo.getCompanyName()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYUNPASS_BTN_CONTACTS)) {
                    econCustomerMongo.setContacts(CommonUtil.hideContacts(econCustomerMongo.getContacts()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYUNPASS_BTN_CONTACTSTEL)) {
                    econCustomerMongo.setContactsTel(CommonUtil.hideContactTel(econCustomerMongo.getContactsTel()));
                }
            }
			
			if (econCustomerMongo.getGoodsList()!=null&&econCustomerMongo.getGoodsList().size()>0) {
				String productName = "";
				for (B2bBillCusgoodsApplyDto cusgoodsDto : econCustomerMongo.getGoodsList()) {
					productName = productName + cusgoodsDto.getGoodsName()+ ",";
				}
				econCustomerMongo.setProductName(StringUtils.subStrs(productName));
			}
		}
		
		pm.setTotalCount(counts);
		pm.setDataList(list);

		return pm;
	}


	@Override
	public B2bBillCustomerApplyExtDto getCustGoodsByCustomerPk(String billCustPk, String accountPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", billCustPk);
		B2bBillCustomerApplyExtDto dto = b2bBillCustomerApplyExtDao.getByMap(map);
		if (dto!=null) {
		    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYING_BTN_COM_NAME)) {
		        dto.setCompanyName(CommonUtil.hideCompanyName(dto.getCompanyName()));
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYING_BTN_CONTACTS)) {
                dto.setContacts(CommonUtil.hideContacts(dto.getContacts()));
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_MG_APPLYING_BTN_CONTACTSTEL)) {
                dto.setContactsTel(CommonUtil.hideContactTel(dto.getContactsTel()));
            }
        }
		// 获取申请的产品信息
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("customerPk", billCustPk);
		List<B2bBillCusgoodsApplyDto> goodsList = b2bBillCusgoodsApplyDao.searchList(param);
		dto.setGoodsName(getGoodsName(goodsList));
		dto.setCusgoodsDtos(goodsList);
		return dto;
	}


	private String getGoodsName(List<B2bBillCusgoodsApplyDto> goodsList) {
		String productName = "";
		if (goodsList!=null&&goodsList.size()>0) {
			for (B2bBillCusgoodsApplyDto  cusgoodsDto: goodsList) {
				productName = productName + cusgoodsDto.getGoodsName()+ ",";
			}			
		}
		return StringUtils.subStrs(productName);
	}


	@Override
	public B2bBillCustomerApplyExtDto getDtoByMap(Map<String, Object> map)  {
		return b2bBillCustomerApplyExtDao.getByMap(map);
	}


	@Override
	public void updateBillMongo(String pk) {
		B2bBillCustomerApplyDto custDto = b2bBillCustomerApplyExtDao.getByPk(pk);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerPk", pk);
		List<B2bBillCusgoodsApplyDto> cusgoodsApplyDto = b2bBillCusgoodsApplyDao.searchList(map);
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("processInstanceId").is(custDto.getProcessInstanceId()));
		Query query = new Query(c);
		Update update = Update.update("status", custDto.getStatus()).set("approveTime",custDto.getApprovalTime()).set("goodsList", cusgoodsApplyDto);
		mongoTemplate.updateFirst(query, update,BillCustomerMongo.class);
	}


	@Override
	public PageModel<BillCustomerMongo> getByCompanyPk(String companyPk, QueryModel<BillCustomerMongo> qm, String accountPk) {
		PageModel<BillCustomerMongo> pm = new PageModel<BillCustomerMongo>();

		Criteria c = new Criteria();
		c.andOperator(Criteria.where("companyPk").is(companyPk));
		Query query = new Query(c);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());

		List<BillCustomerMongo> list = mongoTemplate.find(query, BillCustomerMongo.class);
		int counts = (int) mongoTemplate.count(query, BillCustomerMongo.class);
		if (list.size()>0) {
            
	         for (BillCustomerMongo mongo : list) {
	             //权限
	             if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_CUSTOMER_APPLICATIONRECORD_COL_COM_NAME)) {
	            	 mongo.setCompanyName(CommonUtil.hideCompanyName(mongo.getCompanyName()));
	             }
	             if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_CUSTOMER_APPLICATIONRECORD_COL_CONTACTS)) {
	            	 mongo.setContacts(CommonUtil.hideContacts(mongo.getContacts()));
	             }
	             if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_CUSTOMER_APPLICATIONRECORD_COL_CONTACTSTEL)) {
	            	 mongo.setContactsTel(CommonUtil.hideContactTel(mongo.getContactsTel()));
	             }
	             
	            if (mongo.getGoodsList()!=null||mongo.getGoodsList().size()>0) {
	                String productName = "";
	                for(B2bBillCusgoodsApplyDto cusgoodsApplyDto : mongo.getGoodsList()){
	                	productName = productName + cusgoodsApplyDto.getGoodsName() + ",";
	                }
	                mongo.setProductName(StringUtils.subStrs(productName));
	                }
	            }
	        }
		
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}


	@Override
	public int updateAuditBillCustomer(B2bBillCustomerApplyExtDto dto, int status) {
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			// update
			result = b2bBillCustomerApplyExtDao.updateExt(dto);
			if (status != Constants.FOUR) {
				List<B2bBillCusgoodsApplyDto> list = new ArrayList<B2bBillCusgoodsApplyDto>();
				if (dto.getPks() != null && !dto.getPks().equals("")) {
					String[] econGoodsPk = StringUtils.splitStrs(dto.getPks());
					String[] limits = StringUtils.splitStrs(dto.getLimits());
					for (int i = 0; i < econGoodsPk.length; i++) {
						B2bBillCusgoodsApplyDto model = new B2bBillCusgoodsApplyDto();
						model.setPk(econGoodsPk[i]);
						model.setSuggestAmount(Double.parseDouble(limits[i]));
						list.add(model);
					}
					b2bBillCusgoodsApplyDao.updateBatch(list);
				}
			}
		}
		return result ;
	}
}
