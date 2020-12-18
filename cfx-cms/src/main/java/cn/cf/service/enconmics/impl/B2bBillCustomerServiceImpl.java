package cn.cf.service.enconmics.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bBillCusgoodsApplyExtDao;
import cn.cf.dao.B2bBillCusgoodsExtDao;
import cn.cf.dao.B2bBillCustomerExtDao;
import cn.cf.dao.B2bBillSigncardExtDao;
import cn.cf.dao.B2bBillSigningExtDao;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dto.B2bBillCusgoodsApplyDto;
import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsExtDto;
import cn.cf.dto.B2bBillCustomerApplyExtDto;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillCustomerExtDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bBillSigncardExtDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bBillSigningExtDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.BillCustomerMongo;
import cn.cf.model.B2bBillCusgoods;
import cn.cf.model.B2bBillCustomer;
import cn.cf.model.B2bBillSigncard;
import cn.cf.model.B2bBillSigning;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.B2bBillCustomerService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
public class B2bBillCustomerServiceImpl implements B2bBillCustomerService {

	@Autowired
	private B2bBillCustomerExtDao billCustomerExtDao;

	@Autowired
	private B2bBillCusgoodsExtDao b2bBillCusgoodsExtDao;

	@Autowired
	private B2bBillCusgoodsApplyExtDao billCusgoodsApplyExtDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bCompanyExtDao companyExtDao;

	@Autowired
	private B2bBillSigningExtDao b2bBillSigningExtDao;
	@Autowired
	private B2bBillSigncardExtDao b2bBillSigncardExtDao;

	@Override
	public B2bBillCustomerDto getByCompanyPk(String companyPk) {
		return billCustomerExtDao.getByCompanyPk(companyPk);
	}

	@Override
	public void insertCustomerInfo(B2bBillCustomerApplyExtDto dto, int status) {
		B2bBillCustomerDto customerDto =  	 billCustomerExtDao.getByCompanyPk(dto.getCompanyPk());
		String customerPk = "";
		B2bBillCustomer model = new B2bBillCustomer();
		Date creditAuditTime = new Date();
		Date creditInsertTime = (null == dto.getInsertTime()) ? new Date() : dto.getInsertTime();
		
		model.setCompanyPk(dto.getCompanyPk());
		model.setCompanyName(dto.getCompanyName());
		model.setStatus(status);
		model.setContacts(dto.getContacts());
		model.setContactsTel(dto.getContactsTel());
		model.setAuditTime(creditAuditTime);
		model.setInsertTime(creditInsertTime);
		model.setIsDelete(Constants.ONE);
		model.setAssidirName(dto.getAssidirName());
		model.setAssidirPk(dto.getAssidirPk());
		model.setProcessInstanceId(dto.getProcessInstanceId());
		model.setAddress(dto.getAddress());
		if (customerDto!=null) {
			customerPk=customerDto.getPk();
			model.setPk(customerPk);
			billCustomerExtDao.updateExt(model);
		}else{
			customerPk = KeyUtils.getUUID();
			model.setPk(customerPk);
			billCustomerExtDao.insert(model);
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("customerPk", dto.getPk());
		List<B2bBillCusgoodsApplyDto> list = billCusgoodsApplyExtDao.searchList(param);
		if (list.size() > 0) {
			for (B2bBillCusgoodsApplyDto customerGoodsDto : list) {
				
				param = new HashMap<String, Object>();
				param.put("customerPk", customerPk);
				param.put("goodsPk", customerGoodsDto.getGoodsPk());
				B2bBillCusgoodsDto cusgoodsDto = b2bBillCusgoodsExtDao.getCusGoodsByMap(param);
				if (cusgoodsDto!=null) {
					B2bBillCusgoodsExtDto cusgoods = new B2bBillCusgoodsExtDto();
					cusgoods.setPk(cusgoodsDto.getPk());
					if (customerGoodsDto.getSuggestAmount()!=null && customerGoodsDto.getSuggestAmount()>0) {
						cusgoods.setPlatformAmount(ArithUtil.mul(customerGoodsDto.getSuggestAmount(), 10000));
						b2bBillCusgoodsExtDao.updateObj(cusgoods);
					}
				}else{
					B2bBillCusgoods b2bCreditGoods = new B2bBillCusgoods();
					b2bCreditGoods.setPk(KeyUtils.getUUID());
					b2bCreditGoods.setCompanyPk(dto.getCompanyPk());
					b2bCreditGoods.setGoodsPk(customerGoodsDto.getGoodsPk());
					if (customerGoodsDto.getSuggestAmount()!=null && customerGoodsDto.getSuggestAmount()>0) {
						b2bCreditGoods.setPlatformAmount(ArithUtil.mul(customerGoodsDto.getSuggestAmount(), 10000));
					}else{
						b2bCreditGoods.setPlatformAmount(0d);
					}
					b2bCreditGoods.setUseAmount(0d);
					b2bCreditGoods.setGoodsName(customerGoodsDto.getGoodsName());
					b2bCreditGoods.setGoodsShotName(customerGoodsDto.getGoodsShotName());
					b2bCreditGoods.setBindStatus(0);
					b2bCreditGoods.setCustomerPk(customerPk);
					
					b2bCreditGoods.setIsVisable(Constants.ONE);
					
					
					b2bBillCusgoodsExtDao.insert(b2bCreditGoods);
				}
			
			}
		}
	}

	@Override
	public void updateCustomerInfo(String companyPk) {
		B2bBillCustomerDto billCustomerDto = billCustomerExtDao.getByCompanyPk(companyPk);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("companyPk").is(companyPk), Criteria.where("status").is(3));
		Query query = new Query(criteria);
		query.with(new Sort(new Order(Direction.DESC, "approveTime")));
		BillCustomerMongo econCustomerMongo = mongoTemplate.findOne(query, BillCustomerMongo.class);
		if (econCustomerMongo != null && !econCustomerMongo.equals("")) {
			B2bBillCustomerExtDto model = new B2bBillCustomerExtDto();
			model.setPk(billCustomerDto.getPk());
			model.setCompanyPk(econCustomerMongo.getCompanyPk());
			model.setCompanyName(econCustomerMongo.getCompanyName());
			model.setStatus(Constants.TWO);
			model.setContacts(econCustomerMongo.getContacts());
			model.setContactsTel(econCustomerMongo.getContactsTel());
			model.setAuditTime(econCustomerMongo.getApproveTime());
			model.setInsertTime(econCustomerMongo.getInsertTime());
			model.setIsDelete(1);
			model.setAssidirName(econCustomerMongo.getAssidirName());
			model.setAssidirPk(econCustomerMongo.getAssidirPk());
			model.setProcessInstanceId(econCustomerMongo.getProcessInstanceId());
			model.setAddress(econCustomerMongo.getAddress());
			billCustomerExtDao.updateObj(model);

			// B2bBillCusgoodsDto cusgoodsDto =
			// b2bBillCusgoodsExtDao.getByCompanyPk(companyPk);
			b2bBillCusgoodsExtDao.deleteByCompanyPk(companyPk);
			List<B2bBillCusgoodsApplyDto> lst = econCustomerMongo.getGoodsList();
			for (B2bBillCusgoodsApplyDto customerGoodsDto : lst) {
				B2bBillCusgoods b2bCreditGoods = new B2bBillCusgoods();
				b2bCreditGoods.setPk(KeyUtils.getUUID());
				b2bCreditGoods.setCompanyPk(companyPk);
				b2bCreditGoods.setGoodsPk(customerGoodsDto.getGoodsPk());
				b2bCreditGoods.setGoodsName(customerGoodsDto.getGoodsName());
				b2bCreditGoods.setGoodsShotName(customerGoodsDto.getGoodsShotName());
				b2bCreditGoods.setBindStatus(0);
				b2bCreditGoods.setCustomerPk(billCustomerDto.getPk());
				b2bCreditGoods.setIsVisable(Constants.ONE);
				b2bBillCusgoodsExtDao.insert(b2bCreditGoods);
			}

		}

	}

	@Override
	public List<B2bCompanyDto> supCompanyList() {
		Map<String, Object> map = new HashMap<>();
		map.put("companyType", 2);
		map.put("isDelete", 1);
		map.put("auditSpStatus", 2);
		return companyExtDao.searchList(map);
	}

	@Override
	public PageModel<B2bBillSigningExtDto> searchBillSuppSigningList(QueryModel<B2bBillSigningDto> qm, String accountPk) {
		PageModel<B2bBillSigningExtDto> pm = new PageModel<B2bBillSigningExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		map.put("companyName", qm.getEntity().getCompanyName());
		if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_BILL_SUPPSIGNING_MG_COL_COMPANYNAME)) {
			map.put("colCompanyName", ColAuthConstants.EM_BILL_SUPPSIGNING_MG_COL_COMPANYNAME);
		}
		int totalCount = b2bBillSigningExtDao.searchGridExtCount(map);
		List<B2bBillSigningExtDto> list = b2bBillSigningExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public String updateBillSuppSigning(B2bBillSigning model) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (model.getPk() != null && !model.getPk().equals("")) {
			b2bBillSigningExtDao.updateExt(model);
		} else {
			if (isExtCompany(model.getCompanyPk())) {
				B2bCompanyDto dto = companyExtDao.getByPk(model.getCompanyPk());
				model.setPk(KeyUtils.getUUID());
				model.setCompanyName(dto.getName());
				model.setOrganizationCode(dto.getOrganizationCode());
				model.setIsDelete(1);
				model.setInsertTime(new Date());
				b2bBillSigningExtDao.insert(model);
			} else {
				msg = Constants.SUPPSIGNING_NAME;
			}
		}
		return msg;
	}

	private boolean isExtCompany(String companyPk) {
		int num = b2bBillSigningExtDao.isExtCompany(companyPk);
		if (num > 0) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public PageModel<B2bBillSigncardExtDto> getBankCard(QueryModel<B2bBillSigncardExtDto> qm, ManageAccount account) {
		PageModel<B2bBillSigncardExtDto> pm = new PageModel<B2bBillSigncardExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", 1000);
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("companyPk", qm.getEntity().getCompanyPk());
		int counts = b2bBillSigncardExtDao.searchGridCount(map);
		setBillSigncardparams(account, map);
		List<B2bBillSigncardExtDto> list = b2bBillSigncardExtDao.searchGridEx(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	private void setBillSigncardparams(ManageAccount account, Map<String, Object> map) {
		if (!CommonUtil.isExistAuthName(account.getPk(),
				ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_COMPANYNAME)) {
			map.put("companyNameCol", ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_COMPANYNAME);
		}
		if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_BANKNUM)) {
			map.put("bankAccountCol", ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_BANKNUM);
		}
		if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_ACCNAME)) {
			map.put("bankNameCol", ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_ACCNAME);
		}
		if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_ACCNUM)) {
			map.put("bankNoCol", ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_ACCNUM);
		}
		if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_BANKNAME)) {
			map.put("ecbankNameCol", ColAuthConstants.EM_BILL_SUPPSIGNING_ACCOUNT_COL_BANKNAME);
		}

	}

	@Override
	public int delBillSigncard(String pk) {
		return b2bBillSigncardExtDao.delete(pk);
	}

	@Override
	public Boolean checkCompanyAndBank(B2bBillSigncardDto dto) {
		Boolean flag;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", dto.getPk());
		map.put("companyPk", dto.getCompanyPk());
		map.put("ecbankPk", dto.getEcbankPk());
		int num = b2bBillSigncardExtDao.checkCompanyAndBank(map);
		if (num > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	@Override
	public Map<String, Object> updateBillSigncard(B2bBillSigncardDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		B2bBillSigncard bankcard = new B2bBillSigncard();
		bankcard.setBankAccount(dto.getBankAccount());
		bankcard.setBankName(dto.getBankName());
		bankcard.setBankNo(dto.getBankNo());
		bankcard.setPk(KeyUtils.getUUID());
		bankcard.setStatus(Constants.ZERO);
		bankcard.setCompanyPk(dto.getCompanyPk());
		bankcard.setEcbankPk(dto.getEcbankPk());
		bankcard.setEcbankName(dto.getEcbankName());
		bankcard.setBankclscode(dto.getBankclscode());
		result = b2bBillSigncardExtDao.insert(bankcard);
		if (result > 0) {
			map.put("pk", bankcard.getPk());
		}
		map.put("result", result);
		return map;
	}

}
