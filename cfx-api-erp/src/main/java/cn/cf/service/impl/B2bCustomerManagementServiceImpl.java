package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bCustomerManagementDao;
import cn.cf.dao.B2bCustomerSalesmanDaoEx;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bProductDaoEx;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.B2bCorrespondenceInfoEx;
import cn.cf.entity.ErpCorrespondenceInfo;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.service.B2bCustomerManagementService;
import cn.cf.util.KeyUtils;

@Service
public class B2bCustomerManagementServiceImpl implements B2bCustomerManagementService {

	private final static Logger logger = LoggerFactory.getLogger(B2bCustomerManagementServiceImpl.class);

	@Autowired
	private B2bCustomerManagementDao customerManagementDao;

	@Autowired
	private B2bCustomerManagementService b2bCustomerManagementService;

	@Autowired
	private B2bCompanyDaoEx b2bCompanyDaoEx;

	@Autowired
	private B2bStoreDaoEx b2bStoreDaoEx;

	@Autowired
	private B2bMemberDaoEx b2bMemberDao;

	@Autowired
	private B2bProductDaoEx b2bProductDao;

	@Autowired
	private B2bCustomerSalesmanDaoEx b2bCustomerSalesmanDao;

	@Override
	public B2bCustomerManagementDto getByStorePkAndPurchaserPk(Map<String, Object> map) {
		return customerManagementDao.getByStorePkAndPurchaserPk(map);
	}

	@Override
	@Transactional
	public int insert(B2bCustomerManagement management) {
		return customerManagementDao.insert(management);
	}

	@Override
	public void insertCustomerSale(List<B2bCorrespondenceInfoEx> list, B2bTokenDto b2btoken) {

		// B2bStoreDto storeDto =
		// b2bStoreDaoEx.getByPk(b2btoken.getCompanyPk());
		for (B2bCorrespondenceInfoEx info : list) {

			// 更新 cf_b2b_customer_management
			String customerPk = insertCustomerManage(null, info);
			// 更新cf_b2b_customer_salesman
			List<B2bCustomerSalesmanDto> customerSalesmanLst = new ArrayList<B2bCustomerSalesmanDto>();
			List<String> strs = new ArrayList<String>();
			for (ErpCorrespondenceInfo erpCorrespondenceInfo : info.getCorrespondenceInfo()) {
				// 排除员工号和员工姓名一样的
				if (!strs.contains(erpCorrespondenceInfo.getEmployeeName() + erpCorrespondenceInfo.getEmployeeNumber())) {
					strs.contains(erpCorrespondenceInfo.getEmployeeName() + erpCorrespondenceInfo.getEmployeeNumber());

					B2bCustomerSalesmanDto csdto = salesmanInfo(erpCorrespondenceInfo, info.getId(), customerPk,b2btoken);
					if (null != csdto) {
						customerSalesmanLst.add(csdto);
					}
				}
			}
			strs.clear();
			if (customerSalesmanLst != null && customerSalesmanLst.size() > 0) {
				batchOperator(customerSalesmanLst, customerPk);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void batchOperator(List<B2bCustomerSalesmanDto> customerSalesmanLst, String customerPk) {
		try {
			b2bCustomerSalesmanDao.deleteByCustomerPk(customerPk);

			// 批量插入
			b2bCustomerSalesmanDao.insertBatch(customerSalesmanLst);

		} catch (Exception e) {
			logger.error("batchOperator",e);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	private B2bCustomerSalesmanDto salesmanInfo(ErpCorrespondenceInfo erpCorrespondenceInfo, String purchaserPk,
			String customerPk, B2bTokenDto b2btoken) {

		B2bCustomerSalesmanDto salesmanDto = null;
		if (erpCorrespondenceInfo.getEmployeeName() != null && !"".equals(erpCorrespondenceInfo.getEmployeeName())
				&& erpCorrespondenceInfo.getEmployeeNumber() != null
				&& !"".equals(erpCorrespondenceInfo.getEmployeeNumber())) {

			B2bCompanyDto purchaserCompayDto = b2bCompanyDaoEx.getByPk(purchaserPk);
			B2bStoreDto store = b2bStoreDaoEx.getByPk(b2btoken.getStorePk());

			// 根据员工号和员工名称获取会员
			Map<String, Object> employeeMap = new HashMap<String, Object>();
			employeeMap.put("employeeName", erpCorrespondenceInfo.getEmployeeName());
			employeeMap.put("employeeNumber", erpCorrespondenceInfo.getEmployeeNumber());
			employeeMap.put("companyPk", null==store?"":store.getCompanyPk());
			B2bMemberDto memberDto = null;
			List<B2bMemberDto> memberDtolst = b2bMemberDao.getByEmployeeByCompany(employeeMap);
			if (null != memberDtolst && memberDtolst.size() > 0) {
				memberDto = memberDtolst.get(0);
			}
			// 存在更新cf_b2b_customer_salesman，不存在添加cf_b2b_customer_salesman
			
				salesmanDto = new B2bCustomerSalesmanDto();
				salesmanDto.setPk(KeyUtils.getUUID());
				salesmanDto.setCustomerPk(customerPk);
				salesmanDto.setEmployeeName(erpCorrespondenceInfo.getEmployeeName());
				salesmanDto.setEmployeeNumber(erpCorrespondenceInfo.getEmployeeNumber());
				if (memberDto != null) {
				salesmanDto.setMemberPk(memberDto.getPk());
				salesmanDto.setMobile(memberDto.getMobile());
				}
				salesmanDto.setProductName(erpCorrespondenceInfo.getProductName());
				salesmanDto.setPurchaserPk(purchaserPk);
				salesmanDto.setPurchaserName(purchaserCompayDto.getName());
				salesmanDto.setStorePk(b2btoken.getStorePk());
				salesmanDto.setProductPk("");
				salesmanDto.setFiliale("");
				salesmanDto.setFilialeName(erpCorrespondenceInfo.getCompanyName());
				if(null != erpCorrespondenceInfo.getProductName() && !"".equals(erpCorrespondenceInfo.getProductName())){
					B2bProductDto product = b2bProductDao.getByName(erpCorrespondenceInfo.getProductName());
					if (null != product) {
						salesmanDto.setProductPk(product.getPk());
					}
				}
				if(null != erpCorrespondenceInfo.getCompanyName() && !"".equals(erpCorrespondenceInfo.getCompanyName())){
					B2bCompanyDto company = b2bCompanyDaoEx.getByName(erpCorrespondenceInfo.getCompanyName());
					if(null != company){
						salesmanDto.setFiliale(company.getPk());
					}
				}
				logger.info("cf_b2b_customer_salesman insert:" + JsonUtils.convertToString(salesmanDto));
				return salesmanDto;
		} else {
			logger.info("B2bMemberDto:" + "employeeName:" + erpCorrespondenceInfo.getEmployeeName() + " employeeNumber:"
					+ erpCorrespondenceInfo.getEmployeeNumber() + " not found!");
		}

		return salesmanDto;
	}

	
	private String insertCustomerManage(String storePk, B2bCorrespondenceInfoEx info) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaserPk", info.getId());
		map.put("storePk", info.getStorePk());
		// 根据店铺id和采购商id查询是否存在
		B2bCustomerManagementDto customerManagement = b2bCustomerManagementService.getByStorePkAndPurchaserPk(map);
		if (customerManagement == null) {// 如果不存在则添加
			B2bCustomerManagement management = new B2bCustomerManagement();
			management.setPk(KeyUtils.getUUID());
			management.setStorePk(info.getStorePk());
			management.setPurchaserPk(info.getId());
			b2bCustomerManagementService.insert(management);
			return management.getPk();
		}
		return customerManagement.getPk();
	}

	@Override
	public void editCustomerSale(List<B2bCorrespondenceInfoEx> list,B2bTokenDto b2btoken) {
		for (B2bCorrespondenceInfoEx info : list) {
			// 更新 cf_b2b_customer_management
			String customerPk = insertCustomerManage(null, info);
			// 更新cf_b2b_customer_salesman
			List<B2bCustomerSalesmanDto> customerSalesmanLst = new ArrayList<B2bCustomerSalesmanDto>();
			List<String> strs = new ArrayList<String>();
			for (ErpCorrespondenceInfo erpCorrespondenceInfo : info.getCorrespondenceInfo()) {
				// 排除员工号和员工姓名一样的
				if (!strs.contains(erpCorrespondenceInfo.getEmployeeName() + erpCorrespondenceInfo.getEmployeeNumber())) {
					strs.contains(erpCorrespondenceInfo.getEmployeeName() + erpCorrespondenceInfo.getEmployeeNumber());
					B2bCustomerSalesmanDto csdto = salesmanInfo(erpCorrespondenceInfo, info.getId(), customerPk,b2btoken);
					if (null != csdto) {
						customerSalesmanLst.add(csdto);
					}
				}
			}
			strs.clear();
			if (customerSalesmanLst != null && customerSalesmanLst.size() > 0) {
				batchOperator(customerSalesmanLst, customerPk);
			}
		}
	}

}
