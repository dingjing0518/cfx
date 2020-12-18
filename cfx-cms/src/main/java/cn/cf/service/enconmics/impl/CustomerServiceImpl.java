package cn.cf.service.enconmics.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Comment;
import org.springframework.beans.BeanUtils;
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
import cn.cf.dao.B2bEconomicsCustomerExtDao;
import cn.cf.dao.B2bEconomicsCustomerGoodsExDao;
import cn.cf.dao.B2bEconomicsDatumExtDao;
import cn.cf.dao.B2bEconomicsGoodsExDao;
import cn.cf.dao.ManageAccountExtDao;
import cn.cf.dao.SysBankNamecodeExtDao;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.B2bEconomicsCustomerGoodsDto;
import cn.cf.dto.B2bEconomicsDatumDto;
import cn.cf.dto.B2bEconomicsDatumExtDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.MemberShip;
import cn.cf.dto.SysBankNamecodeDto;
import cn.cf.entity.B2bEconomicsImprovingdataEntity;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.entity.ProcessEntity;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bEconomicsCustomer;
import cn.cf.model.B2bEconomicsDatum;
import cn.cf.service.enconmics.CustomerService;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private B2bEconomicsCustomerExtDao b2bEconomicsCustomerExtDao;

	@Autowired
	private B2bEconomicsDatumExtDao b2bEconomicsDatumExtDao;

	@Autowired
	private B2bEconomicsGoodsExDao economicsGoodsDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SysBankNamecodeExtDao sysBankNamecodeExtDao;


	@Autowired
	private ManageAccountExtDao manageAccountExtDao;

	@Autowired
	private B2bEconomicsCustomerGoodsExDao economicsCustomerGoodsExtDao;
	

	@Override
	public PageModel<B2bEconomicsCustomerExtDto> searchEconCustList(QueryModel<B2bEconomicsCustomerExtDto> qm,
			MemberShip currentMemberShip) {

		PageModel<B2bEconomicsCustomerExtDto> pm = new PageModel<B2bEconomicsCustomerExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("auditStatus", qm.getEntity().getAuditStatus());
		map.put("isDelete", Constants.ONE);
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("contactsTel", qm.getEntity().getContactsTel());
		map.put("updateTimeBegin", qm.getEntity().getUpdateStartTime());
		map.put("updateTimeEnd", qm.getEntity().getUpdateEndTime());
		map.put("insertTimeBegin", qm.getEntity().getInsertStartTime());
		map.put("insertTimeEnd", qm.getEntity().getInsertEndTime());
		
		map.put("colCompanyName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_APPLY_COL_COM_NAME));
        map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_APPLY_COL_CONTACTS));
        map.put("colContactsTel", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_APPLY_COL_CONTACTSTEL));

		if (currentMemberShip != null && null != currentMemberShip.getGroupId()) {
			if ("jingrongzhuanyuan".equals(currentMemberShip.getActGroupDto().getId())) {
				map.put("assidirPk", manageAccountExtDao.getByAccount(currentMemberShip.getUserId()).getPk());
			} else {
				map.put("assidirPk", qm.getEntity().getAssidirPk());
			}
		}

		int totalCount = b2bEconomicsCustomerExtDao.searchGridExtCount(map);
		List<B2bEconomicsCustomerExtDto> list = b2bEconomicsCustomerExtDao.searchGridExt(map);

		if (list != null && list.size() > 0) {
			for (B2bEconomicsCustomerExtDto b2bEconomicsCustomerExtDto : list) {

				if (currentMemberShip != null && null != currentMemberShip.getActGroupDto() && null != currentMemberShip.getActGroupDto().getId()) {
					b2bEconomicsCustomerExtDto.setRoleName(currentMemberShip.getActGroupDto().getId());
				}
				// 该公司是否存在申请记录标识:1存在；0没有
				if (isExitHistory(b2bEconomicsCustomerExtDto.getCompanyPk())) {
					b2bEconomicsCustomerExtDto.setIsExitHistory(Constants.ONE);
				} else {
					b2bEconomicsCustomerExtDto.setIsExitHistory(Constants.ZERO);
				}
				// 该申请是否已存在银行:1存在；0没有
				if (IsHaveBank(b2bEconomicsCustomerExtDto.getPk())) {
					b2bEconomicsCustomerExtDto.setIsHaveBank(Constants.ONE);
				} else {
					b2bEconomicsCustomerExtDto.setIsHaveBank(Constants.ZERO);
				}
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private boolean IsHaveBank(String pk) {
		boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("economicsCustomerPk", pk);
		List<B2bEconomicsCustomerGoodsDto> list = economicsCustomerGoodsExtDao.searchList(map);
		if (list.size() > 0) {
			for (B2bEconomicsCustomerGoodsDto dto : list) {
				if (dto.getBankPk() == null || dto.getBankPk().equals("")) {
					flag = false;
					break;
				}

			}
		} else {
			flag = false;
		}

		return flag;
	}

	private boolean isExitHistory(String companyPk) {
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("companyPk").is(companyPk));
		Query query = new Query(c);
		List<EconCustomerMongo> list = mongoTemplate.find(query, EconCustomerMongo.class);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<B2bEconomicsDatumDto> getByEconomicsCustomerPk(String econCustPk, String datumType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("econCustmerPk", econCustPk);
		map.put("datumType", datumType);
		return b2bEconomicsDatumExtDao.searchList(map);
	}

	@Override
	public int updateEconomicsCustomer(B2bEconomicsDatumExtDto customerExtDto) {
		int result = 0;
		String newPk = KeyUtils.getUUID();
		if (customerExtDto.getPk() != null && !"".equals(customerExtDto.getPk())) {
			// update
			result = b2bEconomicsDatumExtDao.updateEconomicsDatum(customerExtDto);
		} else {
			B2bEconomicsDatum datum = new B2bEconomicsDatum();

			datum.setPk(newPk);
			datum.setCompanyName(customerExtDto.getCompanyName());
			datum.setCompanyPk(customerExtDto.getCompanyPk());
			datum.setContacts(customerExtDto.getContacts());
			datum.setContactsTel(customerExtDto.getContactsTel());
			datum.setEconCustmerPk(customerExtDto.getEconCustmerPk());
			datum.setDatumType(customerExtDto.getDatumType());
			datum.setRemarks(customerExtDto.getRemarks());
			datum.setRule(customerExtDto.getRule());
			datum.setUrl(customerExtDto.getUrl());
			result = b2bEconomicsDatumExtDao.insert(datum);
		}
		return result;
	}

	/************************************** 企业经营资料匹配END ***********************************************/

	@Override
	public List<SysBankNamecodeDto> getSysBankByBankName(String bankName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankname", bankName);
		List<SysBankNamecodeDto> list = sysBankNamecodeExtDao.searchList(map);
		return list;
	}

	@Override
	public int updateEconomicsAssidirName(B2bEconomicsCustomerExtDto customerExtDto) {
		int result = 0;
		if (customerExtDto.getPk() != null && !"".equals(customerExtDto.getPk())) {
			// update
			result = b2bEconomicsCustomerExtDao.updateEconomicsCustomer(customerExtDto);

			if (customerExtDto.getEconomicsBankPksStr() != null
					&& !customerExtDto.getEconomicsBankPksStr().equals("")) {
				List<B2bEconomicsCustomerGoodsDto> list = new ArrayList<B2bEconomicsCustomerGoodsDto>();
				String[] economicsBankPks = StringUtils.splitStrs(customerExtDto.getEconomicsBankPksStr());
				String[] economicsBankNames = StringUtils.splitStrs(customerExtDto.getEconomicsBankNamesStr());
				String[] customerGoodsPks = StringUtils.splitStrs(customerExtDto.getCustomerGoodsPksStr());
				for (int i = 0; i < customerGoodsPks.length; i++) {
					B2bEconomicsCustomerGoodsDto dto = new B2bEconomicsCustomerGoodsDto();
					dto.setPk(customerGoodsPks[i]);
					dto.setBankPk(economicsBankPks[i]);
					dto.setBankName(economicsBankNames[i]);
					list.add(dto);
				}
				economicsCustomerGoodsExtDao.updateBatch(list);
			}
		}
		return result;
	}

	@Override
	public B2bEconomicsCustomerExtDto getByEconomCustPk(String econCustPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", econCustPk);
		return b2bEconomicsCustomerExtDao.getByMap(map);
	}

	@Override
	public B2bEconomicsCustomerDto getByPk(String customerPk) {
		return b2bEconomicsCustomerExtDao.getByPk(customerPk);
	}

	@Override
	public B2bEconomicsCustomerExtDto getByMap(Map<String, Object> map) {
		return b2bEconomicsCustomerExtDao.getByMap(map);
	}

	@Override
	public int updateEconomics(B2bEconomicsCustomerDto customer) {
		int result = 0;
		if (customer.getPk() != null && !"".equals(customer.getPk())) {
			B2bEconomicsCustomer model = new B2bEconomicsCustomer();
			BeanUtils.copyProperties(customer, model);
			result = b2bEconomicsCustomerExtDao.update(model);

		}
		return result;
	}

	@Override
	public List<B2bEconomicsCustomerExtDto> getByCompanyPkEx(String companyPk) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		return b2bEconomicsCustomerExtDao.getByCompanyPkEx(map);
	}

	@Override
	public void insertEconCustomerMongo(String pk) {
		B2bEconomicsCustomerDto economicsCustomerDto = b2bEconomicsCustomerExtDao.getByPk(pk);
		EconCustomerMongo template = new EconCustomerMongo();
		template.setPk(economicsCustomerDto.getPk());
		template.setCompanyPk(economicsCustomerDto.getCompanyPk());
		template.setCompanyName(economicsCustomerDto.getCompanyName());
		template.setContacts(economicsCustomerDto.getContacts());
		template.setContactsTel(economicsCustomerDto.getContactsTel());
		template.setInsertTime(economicsCustomerDto.getInsertTime());
		template.setAuditStatus(economicsCustomerDto.getAuditStatus());
		template.setSource(economicsCustomerDto.getSource());
		template.setAssidirPk(economicsCustomerDto.getAssidirPk());
		template.setAssidirName(economicsCustomerDto.getAssidirName());
		template.setScore(economicsCustomerDto.getScore());
		template.setProcessInstanceId(economicsCustomerDto.getProcessInstanceId());
		template.setApproveTime(economicsCustomerDto.getApprovalTime());
		template.setProductName(economicsCustomerDto.getGoodsName());
		// 资料
		B2bEconomicsImprovingdataEntity  improvingdataDto = JsonUtils.toBean(economicsCustomerDto.getImprovingdataInfo(),
				B2bEconomicsImprovingdataEntity.class); 
		template.setImprovingdataDto(improvingdataDto);
		// 审批所含产品
		Map<String, Object> customer = new HashMap<String, Object>();
		customer.put("economicsCustomerPk", economicsCustomerDto.getPk());
		List<B2bEconomicsCustomerGoodsDto> goodsDtos = economicsCustomerGoodsExtDao.searchListOrderByBankPk(customer);
		String bankPk = "";
		if (goodsDtos.size() > 0) {
			for (B2bEconomicsCustomerGoodsDto dto : goodsDtos) {
				bankPk = bankPk + dto.getBankPk() + ",";
			}
			template.setBankPk(StringUtils.subStrs(bankPk));
			template.setContent(JsonUtils.convertToString(goodsDtos));
		}
		mongoTemplate.insert(template);
	}
	

	@Override
	public PageModel<EconCustomerMongo> getByCompanyPk(String companyPk, QueryModel<EconCustomerMongo> qm,String accountPk) {
		PageModel<EconCustomerMongo> pm = new PageModel<EconCustomerMongo>();

		Criteria c = new Criteria();
		c.andOperator(Criteria.where("companyPk").is(companyPk));
		Query query = new Query(c);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()).and(new Sort(Direction.DESC,"approveTime")));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()).and(new Sort(Direction.ASC,"approveTime")));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());

		List<EconCustomerMongo> list = mongoTemplate.find(query, EconCustomerMongo.class);
		int counts = (int) mongoTemplate.count(query, EconCustomerMongo.class);
		if (list.size()>0) {
            
	         for (EconCustomerMongo econCustomerMongo : list) {
	             //权限
	             if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_CUST_APPLY_COL_COM_NAME)) {
	                 econCustomerMongo.setCompanyName(CommonUtil.hideCompanyName(econCustomerMongo.getCompanyName()));
	             }
	             if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_CUST_APPLY_COL_CONTACTS)) {
	                 econCustomerMongo.setContacts(CommonUtil.hideContacts(econCustomerMongo.getContacts()));
	             }
	             if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_CUST_APPLY_COL_CONTACTSTEL)) {
	                 econCustomerMongo.setContactsTel(CommonUtil.hideContactTel(econCustomerMongo.getContactsTel()));
	             }
	            if (econCustomerMongo.getProductName()==null||econCustomerMongo.getProductName().equals("")) {
	                String productName = "";
	                String[] productPks = StringUtils.splitStrs(econCustomerMongo.getProductPks());
	                if (productPks != null) {
	                    for (int i = 0; i < productPks.length; i++) {
	                        B2bEconomicsGoodsDto dto = economicsGoodsDao.getByPk(productPks[i]);
	                        if (dto!=null) {
	                            productName = productName + dto.getName() + ",";
	                        }
	                    }
	                    econCustomerMongo.setProductName(StringUtils.subStrs(productName));
	                }
	            }
	        }
        }

		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public void updateEconomicsMongo(String pk) {
		B2bEconomicsCustomerDto custDto = b2bEconomicsCustomerExtDao.getByPk(pk);
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("processInstanceId").is(custDto.getProcessInstanceId()));
		Query query = new Query(c);
		Update update = Update.update("auditStatus", custDto.getAuditStatus()).set("approveTime",
				custDto.getUpdateTime());
		mongoTemplate.updateFirst(query, update, EconCustomerMongo.class);
	}



	@Override
	public int updateEconomicsCustomerEx(B2bEconomicsDatumExtDto customerExtDto) {
		int result = 0;
		String newPk = KeyUtils.getUUID();
		if (customerExtDto.getPk() != null && !"".equals(customerExtDto.getPk())) {
			// update
			result = b2bEconomicsDatumExtDao.updateEconomicsDatum(customerExtDto);
		} else {
			B2bEconomicsDatum datum = b2bEconomicsDatumExtDao.selectEconomicsDatum(customerExtDto);
			if (null == datum) {
				datum = new B2bEconomicsDatum();
				datum.setPk(newPk);
				datum.setCompanyName(customerExtDto.getCompanyName());
				datum.setCompanyPk(customerExtDto.getCompanyPk());
				datum.setContacts(customerExtDto.getContacts());
				datum.setContactsTel(customerExtDto.getContactsTel());
				datum.setEconCustmerPk(customerExtDto.getEconCustmerPk());
				datum.setDatumType(customerExtDto.getDatumType());
				datum.setRemarks(customerExtDto.getRemarks());
				datum.setRule(customerExtDto.getRule());
				datum.setUrl(customerExtDto.getUrl());
				result = b2bEconomicsDatumExtDao.insert(datum);
				customerExtDto.setPk(newPk);
			} else {
				result = b2bEconomicsDatumExtDao.updateEconomicsDatumEx(customerExtDto);
			}
		}
		
		return result;
	}

	

	@Override
	public void updateEconomicsMongoForContent(String pk) {
		B2bEconomicsCustomerDto custDto = b2bEconomicsCustomerExtDao.getByPk(pk);
		// 获取申请产品信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("economicsCustomerPk", pk);
		List<B2bEconomicsCustomerGoodsDto> list = economicsCustomerGoodsExtDao.searchList(map);
		
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("processInstanceId").is(custDto.getProcessInstanceId()));
		Query query = new Query(c);
		Update update = Update.update("auditStatus", custDto.getAuditStatus()).set("content", JsonUtils.formatJsonObject(list));
		mongoTemplate.updateFirst(query, update, EconCustomerMongo.class);
	}

	@Override
	public PageModel<EconCustomerMongo> getByMap(Map<String, Object> map, QueryModel<EconCustomerMongo> qm, String accountPk,int flag) {
		PageModel<EconCustomerMongo> pm = new PageModel<EconCustomerMongo>();

		Criteria c = new Criteria();
		c.andOperator(Criteria.where("auditStatus").is(map.get("auditStatus")));
		Query query = new Query(c);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());

		List<EconCustomerMongo> list = mongoTemplate.find(query, EconCustomerMongo.class);
		int counts = (int) mongoTemplate.count(query, EconCustomerMongo.class);
		
		for (EconCustomerMongo econCustomerMongo : list) {
		    //审批通过
		    if (flag ==1) {
		        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVALPASS_COL_COM_NAME)) {
		            econCustomerMongo.setCompanyName(CommonUtil.hideCompanyName(econCustomerMongo.getCompanyName()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVALPASS_COL_CONTACTS)) {
                    econCustomerMongo.setContacts(CommonUtil.hideContacts(econCustomerMongo.getContacts()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVALPASS_COL_CONTACTSTEL)) {
                    econCustomerMongo.setContactsTel(CommonUtil.hideContactTel(econCustomerMongo.getContactsTel()));
                }
            }else if (flag == 2){ //审批驳回
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVALREFUSE_COL_COM_NAME)) {
                    econCustomerMongo.setCompanyName(CommonUtil.hideCompanyName(econCustomerMongo.getCompanyName()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVALREFUSE_COL_CONTACTS)) {
                    econCustomerMongo.setContacts(CommonUtil.hideContacts(econCustomerMongo.getContacts()));
                }
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVALREFUSE_COL_CONTACTSTEL)) {
                    econCustomerMongo.setContactsTel(CommonUtil.hideContactTel(econCustomerMongo.getContactsTel()));
                }
            }
			
			if (econCustomerMongo.getProductName()==null||econCustomerMongo.getProductName().equals("")) {
				String productName = "";
				String[] productPks = StringUtils.splitStrs(econCustomerMongo.getProductPks());
				if (productPks != null) {
					for (int i = 0; i < productPks.length; i++) {
						B2bEconomicsGoodsDto dto = economicsGoodsDao.getByPk(productPks[i]);
						if (dto!=null) {
							productName = productName + dto.getName() + ",";
						}
					}
					econCustomerMongo.setProductName(StringUtils.subStrs(productName));
				}
			}
		}
		
		pm.setTotalCount(counts);
		pm.setDataList(list);

		return pm;
	}


	@Override
	public List<B2bEconomicsCustomerGoodsDto> getGoodsByEconCustomerPk(String economicsCustomerPk) {
		Map<String, Object> map = new HashMap<>();
		map.put("economicsCustomerPk", economicsCustomerPk);
		List<B2bEconomicsCustomerGoodsDto> list = economicsCustomerGoodsExtDao.searchList(map);
		for (B2bEconomicsCustomerGoodsDto b2bEconomicsCustomerGoodsDto : list) {
			b2bEconomicsCustomerGoodsDto.setBankPk(
					b2bEconomicsCustomerGoodsDto.getBankPk() == null ? "" : b2bEconomicsCustomerGoodsDto.getBankPk());
		}
		return list;
	}

	@Override
	public B2bEconomicsCustomerExtDto getCustGoodsByEconomCustPk(String economCustPk,String accountPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", economCustPk);
		B2bEconomicsCustomerExtDto dto = b2bEconomicsCustomerExtDao.getByMap(map);
		if (dto!=null) {
		    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVING_COL_COM_NAME)) {
		        dto.setCompanyName(CommonUtil.hideCompanyName(dto.getCompanyName()));
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVING_COL_CONTACTS)) {
                dto.setContacts(CommonUtil.hideContacts(dto.getContacts()));
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVING_COL_CONTACTSTEL)) {
                dto.setContactsTel(CommonUtil.hideContactTel(dto.getContactsTel()));
            }
        }
		// 获取申请的产品信息
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("economicsCustomerPk", economCustPk);
		List<B2bEconomicsCustomerGoodsDto> goodsList = economicsCustomerGoodsExtDao.searchList(param);
		dto.setGoodsList(goodsList);
		return dto;
	}

	@Override
	public int updateEconomicsAudit(B2bEconomicsCustomerExtDto customerExtDto, Integer status) {
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (customerExtDto.getPk() != null && !"".equals(customerExtDto.getPk())) {
			// update
			result = b2bEconomicsCustomerExtDao.updateEconomicsCustomer(customerExtDto);
			if (status != Constants.FOUR) {
				List<B2bEconomicsCustomerGoodsDto> list = new ArrayList<B2bEconomicsCustomerGoodsDto>();
				if (customerExtDto.getEconGoodsPkD() != null && !customerExtDto.getEconGoodsPkD().equals("")) {
					String[] econGoodsPk = StringUtils.splitStrs(customerExtDto.getEconGoodsPkD());
					String[] effectStartTimes = StringUtils.splitStrs(customerExtDto.getEffectStartTimesD());
					String[] effectEndTimes = StringUtils.splitStrs(customerExtDto.getEffectEndTimesD());
					String[] limits = StringUtils.splitStrs(customerExtDto.getLimitsD());
					String[] totalRates = StringUtils.splitStrs(customerExtDto.getTotalRateD());
					String[] bankRates = StringUtils.splitStrs(customerExtDto.getBankRateD());
					String[] terms = StringUtils.splitStrs(customerExtDto.getTermD());
					String[] sevenRates = StringUtils.splitStrs(customerExtDto.getSevenRateD());
					for (int i = 0; i < econGoodsPk.length; i++) {
						B2bEconomicsCustomerGoodsDto dto = new B2bEconomicsCustomerGoodsDto();
						dto.setPk(econGoodsPk[i]);
						try {
							dto.setEffectStartTime(sdf.parse(effectStartTimes[i]));
							dto.setEffectEndTime(sdf.parse(effectEndTimes[i]));
						} catch (Exception e) {
							e.printStackTrace();
						}
						dto.setSuggestAmount(Double.parseDouble(limits[i]));
						dto.setTotalRate(Double.parseDouble(totalRates[i]));
						dto.setBankRate(Double.parseDouble(bankRates[i]));
						dto.setTerm(Integer.parseInt(terms[i]));
						dto.setSevenRate(Double.parseDouble(sevenRates[i]));
						list.add(dto);
					}
				}
				if (customerExtDto.getEconGoodsPkBt() != null && !customerExtDto.getEconGoodsPkBt().equals("")) {
					String[] econGoodsPk = StringUtils.splitStrs(customerExtDto.getEconGoodsPkBt());
					String[] effectStartTimes = StringUtils.splitStrs(customerExtDto.getEffectStartTimesBt());
					String[] effectEndTimes = StringUtils.splitStrs(customerExtDto.getEffectEndTimesBt());
					String[] limits = StringUtils.splitStrs(customerExtDto.getLimitsBt());
					String[] totalRates = StringUtils.splitStrs(customerExtDto.getTotalRateBt());
					String[] bankRates = StringUtils.splitStrs(customerExtDto.getBankRateBt());
					String[] terms = StringUtils.splitStrs(customerExtDto.getTermBt());
					for (int i = 0; i < econGoodsPk.length; i++) {
						B2bEconomicsCustomerGoodsDto dto = new B2bEconomicsCustomerGoodsDto();
						dto.setPk(econGoodsPk[i]);
						try {
							dto.setEffectStartTime(sdf.parse(effectStartTimes[i]));
							dto.setEffectEndTime(sdf.parse(effectEndTimes[i]));
						} catch (Exception e) {
							e.printStackTrace();
						}
						dto.setSuggestAmount(Double.parseDouble(limits[i]));
						dto.setTotalRate(Double.parseDouble(totalRates[i]));
						dto.setBankRate(Double.parseDouble(bankRates[i]));
						dto.setTerm(Integer.parseInt(terms[i]));
						list.add(dto);
					}
				}
				if (list!=null&&list.size()>0) {
					economicsCustomerGoodsExtDao.updateBatch(list);

				}
				
			}
		}
		return result;
	}


	@Override
	public List<ProcessEntity> searchProcess(String processInstanceId, List<Comment> commentList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ProcessEntity> list = new ArrayList<ProcessEntity>();
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("processInstanceId").is(processInstanceId));
		Query query = new Query(c);
		EconCustomerMongo  mongo = mongoTemplate.findOne(query, EconCustomerMongo.class);
		ProcessEntity   entity = new ProcessEntity();
		entity.setKey(0);
		entity.setUser("ZY");
		if (mongo!=null) {
			entity.setTime(sdf.format(mongo.getInsertTime()));
		}
		list.add(entity);
		 Comparator<Comment> comparator = sortComment();
        Collections.sort(commentList, comparator);  
		if (commentList!=null &&commentList.size()>0) {
			for (int i = 0; i < commentList.size(); i++) {
				ProcessEntity   processEntity = new ProcessEntity();
				processEntity.setKey(i+1);
				processEntity.setTime(sdf.format(commentList.get(i).getTime()));
				processEntity.setUser(commentList.get(i).getUserId());
				list.add(processEntity);
			}
		}
		
		Collections.sort(list);
		return list;
	}

	private Comparator<Comment> sortComment() {
		Comparator<Comment> comparator = new Comparator<Comment>() {
	            public int compare(Comment s1, Comment s2) {
	                    Date dt1 = s1.getTime();
	                    Date dt2 = s2.getTime();
	                    if (dt1.getTime() > dt2.getTime()) {
	                        return 1;
	                    } else if (dt1.getTime() < dt2.getTime()) {
	                        return -1;
	                    } else {
	                        return 0;
	                    }
	            }
	        };
		return comparator;
	}

	
	
}
