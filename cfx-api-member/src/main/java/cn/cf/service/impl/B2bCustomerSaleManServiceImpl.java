package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bCustomerManagementDaoEx;
import cn.cf.dao.B2bCustomerSalesmanDaoEx;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bProductDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.model.B2bCustomerSalesman;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.util.KeyUtils;

@Service
public class B2bCustomerSaleManServiceImpl implements B2bCustomerSaleManService {

	@Autowired
	private B2bCustomerSalesmanDaoEx customerSalesmanDaoEx;

	@Autowired
	private B2bCompanyDaoEx b2bCompanyDaoEx;

	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;

	@Autowired
	private B2bProductDao b2bProductDao;
	
	@Autowired
	private B2bCustomerManagementDaoEx customerManagementDao;

	@Override
	public B2bMemberDto getSaleMan(String companyPk, String purchaserPk, String productPk, String storePk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaserPk", purchaserPk);// 采购商Pk
		map.put("filiale", companyPk);// 供应商pk
		map.put("productPk", productPk);// 品名
		map.put("storePk", storePk);// 店铺
		B2bMemberDto member = null;
		List<B2bCustomerSalesmanDto> cusSales = customerSalesmanDaoEx.searchMemberBySaleMan(map);
		// 排除品名查询
		if (null == cusSales || cusSales.size() == 0) {
			map.put("productPk", "");
			cusSales = customerSalesmanDaoEx.searchMemberBySaleMan(map);
		}
		// 排除分公司查询
		if (null == cusSales || cusSales.size() == 0) {
			map.put("productPk", productPk);
			map.put("filiale", "");
			cusSales = customerSalesmanDaoEx.searchMemberBySaleMan(map);
		}
		// 排除品名和分公司查询
		if (null == cusSales || cusSales.size() == 0) {
			map.put("productPk", "");
			cusSales = customerSalesmanDaoEx.searchMemberBySaleMan(map);
		}
		// 如果没有查到业务员则 查询供应商子公司/总公司的超级管理员
		if (null != cusSales && cusSales.size()> 0){
			if(null !=cusSales.get(0).getMemberPk() && !"".equals(cusSales.get(0).getMemberPk())){
				member = b2bMemberDaoEx.getByPk(cusSales.get(0).getMemberPk());
			}else{
				member = new B2bMemberDto();
				member.setEmployeeName(cusSales.get(0).getEmployeeName());
				member.setEmployeeNumber(cusSales.get(0).getEmployeeNumber());
			}
		}
        if(null==member||"".equals(member)){
			// 子公司
			List<B2bMemberDto> members = b2bMemberDaoEx.getAdmin(companyPk);
			if (null == members || members.size()==0) {
				// 总公司
				members = b2bMemberDaoEx.getAdminByParent(companyPk);
			}
			if(null !=members && members.size()>0){
				member = members.get(0);
			}
		}
		return member;
	}

	@Override
	public boolean isCanSuit(B2bCustomerSalesmanDto cus) {
		Integer saleType = 0;
		String filiale = (cus.getFiliale() == null) ? "" : cus.getFiliale();
		String productPk = (cus.getProductPk() == null) ? "" : cus.getProductPk();
		if (!"".equals(filiale) && !"".equals(productPk)) {
			saleType = 1;
		}
		if (!"".equals(filiale) && "".equals(productPk)) {
			saleType = 2;
		}
		if ("".equals(filiale) && !"".equals(productPk)) {
			saleType = 3;
		}
		if ("".equals(filiale) && "".equals(productPk)) {
			saleType = 4;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleType", saleType);
		map.put("purchaserPk", cus.getPurchaserPk());
		map.put("memberPk", cus.getMemberPk());
		map.put("storePk", cus.getStorePk());
		int saleresult = customerSalesmanDaoEx.countFilialeAndProductPkIs(map);
		if (saleresult > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isRepeat(B2bCustomerSalesmanDto cus) {
		if (null == cus.getFiliale()) {
			cus.setFiliale("");
			cus.setFilialeName("");
		}
		if (null == cus.getProductPk()) {
			cus.setProductPk("");
			cus.setProductName("");
		}
		int count = customerSalesmanDaoEx.isRepeat(cus);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public RestCode insertSaleman(B2bCustomerSalesmanDto cus) {
		RestCode restCode = RestCode.CODE_0000;
		B2bMemberDto member = b2bMemberDaoEx.getByPk(cus.getMemberPk());
		if (null != member) {
			cus.setMobile(member.getMobile());
			cus.setEmployeeName(member.getEmployeeName());
			cus.setEmployeeNumber(member.getEmployeeNumber());
			B2bCompanyDto company = b2bCompanyDaoEx.getByCustomerPk(cus.getPk());
			if (company != null) {
				cus.setPurchaserPk(company.getPk());
				cus.setPurchaserName(company.getName());
			}
			B2bCompanyDto filiale = b2bCompanyDaoEx.getByPk(cus.getFiliale());
			if (filiale != null) {
				cus.setFilialeName(filiale.getName());
			}
			B2bProductDto productDto = b2bProductDao.getByPk(cus.getProductPk());
			if (productDto != null) {
				cus.setProductName(productDto.getName());
			}
		} else {
			restCode = RestCode.CODE_M009;
		}

		B2bCustomerSalesman model = new B2bCustomerSalesman();
		model.UpdateDTO(cus);
		int result = customerSalesmanDaoEx.insert(model);
		if (result == 0) {
			restCode = RestCode.CODE_A002;
		}
		return restCode;
	}

	@Override
	public PageModel<B2bCustomerSalesmanDto> searchCusSalesManList(Map<String, Object> map) {
		PageModel<B2bCustomerSalesmanDto> pm = new PageModel<B2bCustomerSalesmanDto>();
		List<B2bCustomerSalesmanDto> list = customerSalesmanDaoEx.searchGrid(map);
		int count = customerSalesmanDaoEx.searchGridCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public RestCode deleteCusSalesman(String customerPk) {
		int result = customerSalesmanDaoEx.delete(customerPk);
		if (result == 0) {
			return RestCode.CODE_A002;
		}
		return RestCode.CODE_0000;
	}

	@Override
	public Map<String, Object> getGoodsByMember(Map<String, Object> map) {
		List<B2bCustomerSalesmanDto> cus = customerSalesmanDaoEx.searchGoodsBySaleMan(map);
		String sqlStr = "";
		if(null !=cus){
			for (B2bCustomerSalesmanDto cdto : cus) {
				if(!"".equals(sqlStr)){
					sqlStr +=" or";
				}
				if(map.containsKey("sxFlag")){
					if( null !=cdto.getFiliale() && !"".equals(cdto.getFiliale())){
						sqlStr +=" g.companyPk = '"+cdto.getFiliale()+"' ";
						continue;
					}
				}else{
					if(null!=cdto.getProductPk() && !"".equals(cdto.getProductPk()) &&  null !=cdto.getFiliale() && !"".equals(cdto.getFiliale())){
						sqlStr +="( productPk = '"+cdto.getProductPk()+"' and companyPk = '"+cdto.getFiliale()+"' )";
						continue;
					}
					if(null!=cdto.getProductPk() && !"".equals(cdto.getProductPk()) &&  (null ==cdto.getFiliale() || "".equals(cdto.getFiliale()))){
						sqlStr +="( productPk = '"+cdto.getProductPk()+"' )";
						continue;
					}
					if((null==cdto.getProductPk() || "".equals(cdto.getProductPk())) &&  null !=cdto.getFiliale() && !"".equals(cdto.getFiliale())){
						sqlStr +="( companyPk = '"+cdto.getFiliale()+"' )";
						continue;
					}	
				}
				
			}
			if(!"".equals(sqlStr)){
				map.put("sqlStr", sqlStr);
			}
		}else{
			map.put("sType", 1);
		}
		// 有匹配的商品类型
//		if (null !=dto) {
//			if (4 == Integer.parseInt(map.get("salesType").toString())) {
//				sqlStr = "";
//				map.put("sqlStr", sqlStr);
//			} else {
//				if (3 == Integer.parseInt(map.get("salesType").toString())) {
//					for (B2bCustomerSalesmanDto cussale : list) {
//						sqlStr += " productPk='" + cussale.getProductPk() + "' or";
//					}
//					sqlStr = sqlStr.substring(0, sqlStr.length() - 2) + " )";
//					map.put("sqlStr", sqlStr);
//				} else {
//					if (2 == Integer.parseInt(map.get("salesType").toString())) {
//						for (B2bCustomerSalesmanDto cussale : list) {
//							sqlStr += " companyPk='" + cussale.getFiliale() + "' or";
//						}
//						sqlStr = sqlStr.substring(0, sqlStr.length() - 2) + " )";
//						map.put("sqlStr", sqlStr);
//					} else {
//						map.put("salesmanType", 1);
//					}
//				}
//			}
//			if(null!=dto.getProductPk() && !"".equals(dto.getProductPk())){
//				sqlStr += " productPk='" + cussale.getProductPk() + "' or";
//			}
//		} else {// 有匹配的商品类型
//			sqlStr += " pk is null )";
//			map.put("sqlStr", sqlStr);
//		}
		
		return map;
	}


	@Override
	public void deleteByMemberPk(String memberPk) {
		customerSalesmanDaoEx.deleteByMemberPk(memberPk);
	}

	@Override
	public RestCode addCustomerManagement(B2bCustomerManagementDto dto) {
		RestCode restCode = RestCode.CODE_0000;
		if (dto.getPurchaserPk()==null||dto.getPurchaserPk().equals("")) {
			restCode = RestCode.CODE_C001;
		}else {
			Map<String, Object> mapC = new HashMap<String, Object>();
			mapC.put("purchaserPk", dto.getPurchaserPk());
			mapC.put("storePk", dto.getStorePk());
			mapC.put("pk", dto.getPk());
				int count = customerManagementDao.isCustomerReaped(mapC);
				if (count <= 0) {
					int result = 0;
					// 编辑
					B2bCustomerManagement model = new B2bCustomerManagement();
					model.UpdateDTO(dto);
					if (null != dto.getPk() && !dto.getPk().equals("")) {
						result = customerManagementDao.update(model);
					} else {// 新增
						model.setPk(KeyUtils.getUUID());
						result = customerManagementDao.insert(model);
					}
					if (result == 0) {
						restCode = RestCode.CODE_A001;
					}
				}else{
					restCode = RestCode.CODE_C010;
				}
		}
		
		
		return restCode;
	}

	@Override
	public Boolean isEmployee(String purchaserPk, String memberPk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("purchaserPk", purchaserPk);
		map.put("memberPk", memberPk);
		List<B2bCustomerSalesmanDto> list = customerSalesmanDaoEx.searchList(map);
		Boolean flag = false;
		if(null != list && list.size()>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public void insertOrUpdateSaleMan(
			B2bCustomerSalesman b2bCustomerSalesman) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("purchaserPk", b2bCustomerSalesman.getPurchaserPk());
		map.put("customerPk", b2bCustomerSalesman.getCustomerPk());
		map.put("employeeNumber", b2bCustomerSalesman.getEmployeeNumber());
		List<B2bCustomerSalesmanDto> list = customerSalesmanDaoEx.searchList(map);
		if(null == list || list.size() == 0){
			List<B2bMemberDto> memberList = b2bMemberDaoEx.searchMemberByEmployeeNumber(map);
			b2bCustomerSalesman.setPk(KeyUtils.getUUID());
			if(null !=memberList &&memberList.size()>0){
				b2bCustomerSalesman.setMobile(memberList.get(0).getMobile());
				b2bCustomerSalesman.setMemberPk(memberList.get(0).getPk());
			}
			customerSalesmanDaoEx.insert(b2bCustomerSalesman);
		}else{
			B2bCustomerSalesman saleMan = new B2bCustomerSalesman();
			saleMan.UpdateDTO(list.get(0));
			saleMan.setEmployeeName(b2bCustomerSalesman.getEmployeeName());
			customerSalesmanDaoEx.update(saleMan);
		}
	}

}
