package cn.cf.service.enconmics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bEconomicsCreditcustomerExDao;
import cn.cf.dao.B2bEconomicsCustomerExtDao;
import cn.cf.dao.B2bEconomicsCustomerGoodsExDao;
import cn.cf.dao.B2bEconomicsGoodsExDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bEconomicsCreditcustomerDto;
import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.B2bEconomicsCustomerGoodsDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.MemberShip;
import cn.cf.model.B2bEconomicsCreditcustomer;
import cn.cf.service.enconmics.EconomicsCreditCustomerService;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class EconomicsCreditCustomerServiceImpl implements EconomicsCreditCustomerService {

	@Autowired
	private B2bEconomicsCreditcustomerExDao economicsCreditcustomerDao;

	@Autowired
	private B2bEconomicsCustomerExtDao economicsCustomerDao;

	@Autowired
	private B2bEconomicsGoodsExDao economicsGoodsDao;

	@Resource
	private RuntimeService runtimeService;
	
	@Autowired
	private  B2bEconomicsCustomerGoodsExDao   economicsCustomerGoodsExDao;
	
	@Autowired
	private B2bCompanyExtDao companyExtDao;

	@Override
	public PageModel<B2bEconomicsCreditcustomerDtoEx> searchEconCustList(QueryModel<B2bEconomicsCreditcustomerDtoEx> qm,
			MemberShip currentMemberShip) {
		PageModel<B2bEconomicsCreditcustomerDtoEx> pm = new PageModel<B2bEconomicsCreditcustomerDtoEx>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("contactsTel", qm.getEntity().getContactsTel());
		map.put("updateTimeBegin", qm.getEntity().getUpdateStartTime());
		map.put("updateTimeEnd", qm.getEntity().getUpdateEndTime());
		map.put("insertTimeBegin", qm.getEntity().getInsertStartTime());
		map.put("insertTimeEnd", qm.getEntity().getInsertEndTime());
		map.put("status", qm.getEntity().getStatus());

		map.put("colCompanyName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_APPLY_RECORDS_COL_COM_NAME));
        map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_APPLY_RECORDS_COL_CONTACTS));
        map.put("colContactsTel",  CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_APPLY_RECORDS_COL_CONTACTSTEL));
		
		int totalCount = economicsCreditcustomerDao.searchEcCounts(map);
		List<B2bEconomicsCreditcustomerDtoEx> list = economicsCreditcustomerDao.searchEcList(map);

		if (list != null && list.size() > 0) {
			for (B2bEconomicsCreditcustomerDtoEx creditCustomer : list) {
				if (null != currentMemberShip.getActGroupDto() && null != currentMemberShip.getActGroupDto().getId()) {
					creditCustomer.setRoleName(currentMemberShip.getActGroupDto().getId());
				}
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public void updateB2bEconomicsCreditcustomer(B2bEconomicsCreditcustomer customer, boolean isUpdateStatic) {
		if (isUpdateStatic) {
			customer.setStaticTime(new Date());
		}
		economicsCreditcustomerDao.updateCreditCustomer(customer);
	}

	@Override
	public String acceptanceCustomer(String pk) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		Boolean flag = true;
		String  customerPk = "";
		// 判断是否可以受理
		B2bEconomicsCreditcustomerDto customer = economicsCreditcustomerDao.getByPk(pk);
		B2bEconomicsCustomerDto dto = economicsCustomerDao.getByCompanyPk(customer.getCompanyPk());

		B2bEconomicsCustomerExtDto ecustomer = new B2bEconomicsCustomerExtDto();
		ecustomer.setCompanyPk(customer.getCompanyPk());
		ecustomer.setCompanyName(customer.getCompanyName());
		ecustomer.setContacts(customer.getContacts());
		ecustomer.setContactsTel(customer.getContactsTel());
		ecustomer.setInsertTime(customer.getInsertTime());
		ecustomer.setAuditStatus(1);
		ecustomer.setSource(customer.getSource());
		ecustomer.setAssidirPk(customer.getAssidirPk());
		ecustomer.setAssidirName(customer.getAssidirName());
		ecustomer.setGoodsName(customer.getGoodsName());
		
		// 如果还未受理过则插入数据
		if (null == dto) {
			customerPk= KeyUtils.getUUID();
			ecustomer.setPk(customerPk);
			ecustomer.setStaticTime(customer.getStaticTime());
			economicsCustomerDao.insertEconomicsCustomer(ecustomer);
			//批量插入审批产品
			insertGoodsBatch(customerPk, customer);
		} else {
		     customerPk= dto.getPk();
			// 如果已受理判断是否可以进入受理
			if (null != dto.getProcessInstanceId() && !"".equals(dto.getProcessInstanceId())) {
				ProcessInstance pi = runtimeService.createProcessInstanceQuery()
						.processInstanceId(dto.getProcessInstanceId()).singleResult();
				
				if (pi == null) {
					// 该流程实例已经完成了
					ecustomer.setPk(customerPk);
					ecustomer.setProcessInstanceId("");
					if (!isRepeatCustomerGoods(customer, dto)) {//产品是否已申请过
						ecustomer.setStaticTime(customer.getStaticTime());
					}
					economicsCustomerDao.updateEconomicsCustomer(ecustomer);
					//删除原有产品，插入新产品
					economicsCustomerGoodsExDao.deleteByB2bEconomicsCustomerPk(customerPk);
					insertGoodsBatch(customerPk, customer);
				} else {
					flag = false;
					// 该流程实例未结束的
					msg = Constants.ACCEPTANCE_MSG;
				}
			} else {
				ecustomer.setPk(customerPk);
				if (!isRepeatCustomerGoods(customer, dto)) {//商品是否已申请过
					ecustomer.setStaticTime(customer.getStaticTime());
				}
				economicsCustomerDao.updateEconomicsCustomer(ecustomer);
				//删除原有产品，插入新产品
                economicsCustomerGoodsExDao.deleteByB2bEconomicsCustomerPk(customerPk);
                insertGoodsBatch(customerPk, customer);
			}
		}
		if (flag) {
			B2bEconomicsCreditcustomer c = new B2bEconomicsCreditcustomer();
			c.setPk(pk);
			c.setStatus(2);
			c.setUpdateTime(new Date());
			this.updateB2bEconomicsCreditcustomer(c, false);
		}
		return msg;
	}

	private Boolean isRepeatCustomerGoods(B2bEconomicsCreditcustomerDto customer, B2bEconomicsCustomerDto dto) {
		Boolean flag = true;
		String[] goodsPKs = StringUtils.splitStrs(customer.getProductPks());
		for (int i = 0; i < goodsPKs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("b2bEconomicsCustomerPk", dto.getPk());
			map.put("economicsGoodsPk", goodsPKs[i]);
			List<B2bEconomicsCustomerGoodsDto>  list  = economicsCustomerGoodsExDao.searchList(map);
			if (list.size()==0) {
				flag = false;
				break;
			}
		}
		
		return flag ;
	}

	private void insertGoodsBatch(String b2bEconomicsCustomerPk, B2bEconomicsCreditcustomerDto customer) {
		List<B2bEconomicsCustomerGoodsDto>  goodsList = new ArrayList<B2bEconomicsCustomerGoodsDto>();
		String[] goodsPKs = StringUtils.splitStrs(customer.getProductPks());
		if (goodsPKs!=null) {
			for (int i = 0; i < goodsPKs.length; i++) {
				B2bEconomicsGoodsDto  goodsDto = economicsGoodsDao.getByPk(goodsPKs[i]);
				if (goodsDto!=null) {
					B2bEconomicsCustomerGoodsDto  customerGoodsDto = new B2bEconomicsCustomerGoodsDto();
					customerGoodsDto.setPk(KeyUtils.getUUID());
					customerGoodsDto.setEconomicsGoodsPk(goodsPKs[i]);
					customerGoodsDto.setEconomicsGoodsName(goodsDto.getName());
					customerGoodsDto.setGoodsType(goodsDto.getGoodsType());
					customerGoodsDto.setEconomicsCustomerPk(b2bEconomicsCustomerPk);
					goodsList.add(customerGoodsDto);
				}
			}
			economicsCustomerGoodsExDao.insertBatch(goodsList);
		}
	}

	@Override
	public String insert(B2bEconomicsCreditcustomer model) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (isExitCreditcustomer(model.getCompanyPk())) {
			msg = Constants.ADD_ACCEPTANCE_MSG;
		}else{
			B2bCompanyDto companyExtDto = companyExtDao.getByPk(model.getCompanyPk());
			model.setPk(KeyUtils.getUUID());
			model.setCompanyName(companyExtDto.getName());
			model.setContacts(companyExtDto.getContacts());
			model.setContactsTel(companyExtDto.getContactsTel());
			model.setInsertTime(new Date());
			model.setStatus(1);
			model.setAssiFlag(1);
			economicsCreditcustomerDao.insert(model);
		}
		return msg;
	}
	/**
	 * 是否可以提交申请
	 * @param companyPk
	 * @return
	 */
	private boolean isExitCreditcustomer(String companyPk) {
		boolean flag = false ;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("status", 1);
		List<B2bEconomicsCreditcustomerDto>  list = economicsCreditcustomerDao.searchGrid(map);
		if (list!=null&&list.size()>0) {
			 flag = true ;
		}else{
			map = new HashMap<String, Object>();
			map.put("companyPk", companyPk);
			map.put("status", 2);
			list = economicsCreditcustomerDao.searchGrid(map);
			if (list!=null&&list.size()>0) {
				int counts = economicsCustomerDao.isProcess(companyPk);
				if (counts > 0) {
					 flag = true ;
				}
			}

		}
		return flag;
	}

}
