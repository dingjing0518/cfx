package cn.cf.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.dao.B2bCompanyDao;
import cn.cf.dao.B2bCustomerManagementDao;
import cn.cf.dao.B2bCustomerManagementDaoEx;
import cn.cf.dao.B2bCustomerSalesmanDao;
import cn.cf.dao.B2bCustomerSalesmanDaoEx;
import cn.cf.dao.B2bMemberDao;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entry.CustomerImport;
import cn.cf.entry.RespCustomer;
import cn.cf.model.B2bCompany;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.model.B2bCustomerSalesman;
import cn.cf.model.B2bMember;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bCustomerMangementService;
import cn.cf.service.MemberCompanyService;
import cn.cf.service.SysRegionsService;
import cn.cf.util.KeyUtils;
import cn.cf.util.MD5Utils;
import cn.cf.util.OSSUtils;
import cn.cf.util.RegexUtils;
import cn.cf.util.RoadExcelUtil;

@Service
public class B2bCustomerMangementServiceImpl implements
		B2bCustomerMangementService {

	@Autowired
	private B2bCustomerManagementDaoEx customerManagementDao;
	@Autowired
	private B2bCompanyDao b2bCompanyDao;
	@Autowired
	private B2bMemberDao b2bMemberDao;
	@Autowired
	private B2bCustomerManagementDao b2bCustomerManagementDao;
	@Autowired
	private B2bCustomerSalesmanDao b2bCustomerSalesmanDao;
	@Autowired
	private B2bCustomerSalesmanDaoEx b2bCustomerSalesmanDaoEx;
	@Autowired
	private SysRegionsService sysRegionsService;
	@Autowired
	private B2bMemberRoleServiceImpl b2bMemberRoleService;
	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;
	@Autowired
	private MemberCompanyService b2bCompanyService;

	@Override
	public RestCode addCustomerManagement(B2bCustomerManagementDto dto) {
		RestCode restCode = RestCode.CODE_0000;
		if (dto.getPurchaserPk() == null || dto.getPurchaserPk().equals("")) {
			restCode = RestCode.CODE_C001;
		} else {
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
			} else {
				restCode = RestCode.CODE_C010;
			}
		}

		return restCode;

	}

	@SuppressWarnings("unchecked")
	@Override
	public RespCustomer customerList(File file) {
		RespCustomer rc = new RespCustomer();
		String code = RestCode.CODE_0000.getCode();
		String msg = null;
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		List<Map<String, String>> list = null;
		String cellData = null;
		File[] files = file.listFiles();
		List<File> imglist = new ArrayList<>();
		// String filePath = "D:\\osstest\\客户导入.xlsx";
		String filePath = null;
		for (int i = 0; i < files.length; i++) {
			File file1 = files[i];
			file1.getName();// 根据后缀判断
			if (!file1.getName().contains("xlsx")
					|| !file1.getName().contains("xls")) {
				imglist.add(file1);
			} else if (file1.getName().contains("xlsx")
					|| file1.getName().contains("xls")) {
				filePath = PropertyConfig.getProperty("customer_file_path")
						+ file1.getName();
			}
		}

		String columns[] = { "employeeName", "companyName", "organizationCode",
				"mobile", "contracts", "contractsTel", "provinceName",
				"cityName", "areaName", "address", "employeeNumber",
				"bankName", "bankAccount", "blUrl", "ecUrl" };
		wb = RoadExcelUtil.readExcel(filePath);
		if (wb != null) {
			// 用来存放表中数据
			list = new ArrayList<Map<String, String>>();
			// 获取第一个sheet
			sheet = wb.getSheetAt(0);
			// 获取最大行数
			int rownum = sheet.getPhysicalNumberOfRows();
			// 获取第一行
			row = sheet.getRow(0);
			// 获取最大列数
			int colnum = row.getPhysicalNumberOfCells();
			for (int i = 1; i < rownum; i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < colnum; j++) {
						cellData = (String) RoadExcelUtil
								.getCellFormatValue(row.getCell(j));
						if (null != cellData && !"".equals(cellData)) {
							map.put(columns[j], cellData);
						}
					}
					if (null != map && map.size() > 0) {
						list.add(map);
					}

				} else {
					break;
				}
			}
		}
		// 遍历解析出来的list 并将图片上传到oss的地址赋值给集合
		List<CustomerImport> customerImportList = RoadExcelUtil.parse(list,
				CustomerImport.class);
		// 图片超过100k
//		if (imglist.size() == 0) {
//			code = RestCode.CODE_Z000.getCode();
//			msg = "图片不能为空";
//		}
		if (code.equals(RestCode.CODE_0000.getCode())) {
			//String organizationCodes = "";
			// 正则验证
			 for (CustomerImport c : customerImportList) {
				// if(!RegexUtils.isTwoToFourChinese(c.getEmployeeName())){
				// code = RestCode.CODE_Z000.getCode();
				// msg = "负责人:"+c.getEmployeeName()+"格式不正确";
				// break;
				// }
				if (null == c.getCompanyName() || "".equals(c.getCompanyName())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "客户:" + c.getCompanyName() + "格式不正确";
					break;
				}
				if (!RegexUtils.isNumberAndKey(c.getOrganizationCode())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "信用代码:" + c.getOrganizationCode() + "格式不正确";
					break;
				}
				
				if (null != c.getCompanyName() && !"".equals(c.getCompanyName())&&!RegexUtils.isMobile(c.getMobile())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "手机号:" + c.getMobile() + "格式不正确";
					break;
				}
				// if(!RegexUtils.isTwoToFourChinese(c.getContracts())){
				// code = RestCode.CODE_Z000.getCode();
				// msg = "联系人:"+c.getContracts()+"格式不正确";
				// break;
				// }
				if (!RegexUtils.isNumberPlus(c.getContractsTel())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "公司电话:" + c.getContractsTel() + "格式不正确";
					break;
				}
				if (null == c.getAddress() || "".equals(c.getAddress())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "营业地址:" + c.getAddress() + "格式不正确";
					break;
				}
				if (!RegexUtils.isNumberAndKey(c.getEmployeeNumber())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "销售工号:" + c.getEmployeeNumber() + "格式不正确";
					break;
				}
				if (null != c.getBankAccount()
						&& !"".equals(c.getBankAccount())
						&& !RegexUtils.isNumberPlus(c.getBankAccount())) {
					code = RestCode.CODE_Z000.getCode();
					msg = "银行卡号:" + c.getBankAccount() + "格式不正确";
					break;
				}
				Map<String, Object> addressMap = new HashMap<String, Object>();
				addressMap.put("parentPk", "-1");
				addressMap.put("name", c.getProvinceName());
				SysRegionsDto province = sysRegionsService
						.getRegionsPk(addressMap);
				if (null == province) {
					code = RestCode.CODE_Z000.getCode();
					msg = "省:" + c.getProvinceName() + "未匹配成功";
					break;
				} else {
					addressMap.put("parentPk", province.getPk());
					addressMap.put("name", c.getCityName());
					SysRegionsDto provinceCity = sysRegionsService
							.getRegionsPk(addressMap);
					if (null == provinceCity) {
						code = RestCode.CODE_Z000.getCode();
						msg = "省:" + c.getProvinceName() + ",市:"
								+ c.getCityName() + "未匹配成功";
						break;
					} else {
						if (null != c.getAreaName()
								&& !"".equals(c.getAreaName())) {
							addressMap.put("parentPk", provinceCity.getPk());
							addressMap.put("name", c.getAreaName());
							SysRegionsDto provinceArea = sysRegionsService
									.getRegionsPk(addressMap);
							if (null == provinceArea) {
								code = RestCode.CODE_Z000.getCode();
								msg = "省:" + c.getProvinceName() + ",市:"
										+ c.getCityName() + ",区:"
										+ c.getAreaName() + "未匹配成功";
								break;
							}
						}
					}
				}
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("organizationCode", c.getOrganizationCode());
//				List<B2bCompanyDto> companyList = b2bCompanyDao.searchGrid(map);
//				if (organizationCodes.contains(c.getOrganizationCode() + ",")) {
//					code = RestCode.CODE_Z000.getCode();
//					msg = "信用代码:" + c.getOrganizationCode() + "有重复数据";
//					break;
//				}
//				organizationCodes += c.getOrganizationCode() + ",";
				c.setOrganizationCode(c.getOrganizationCode().toUpperCase());
//				for (int j = 0; j < imglist.size(); j++) {
//					if (imglist.get(j).getName().toUpperCase()
//							.contains(c.getOrganizationCode())
//							&& imglist.get(j).getName().contains("_1")) {
//						c.setBlUrl("1");
//					} else if (imglist.get(j).getName().toUpperCase()
//							.contains(c.getOrganizationCode())
//							&& imglist.get(j).getName().contains("_2")) {
//						c.setEcUrl("2");
//					}
//					if (j == imglist.size() - 1
//							&& (null == c.getBlUrl() || null == c.getEcUrl())) {
//						code = RestCode.CODE_Z000.getCode();
//						msg = "信用代码:" + c.getOrganizationCode() + "对应图片不完整";
//						break ko;
//					}
//				}
			}
		}
		if (code.equals(RestCode.CODE_0000.getCode()) && null !=imglist && imglist.size()>0) {
			for (int i = 0; i < customerImportList.size(); i++) {
				CustomerImport customerImport = customerImportList.get(i);
				for (int j = 0; j < imglist.size(); j++) {
					if (imglist.get(j).getName().toUpperCase()
							.contains(customerImport.getOrganizationCode())
							&& imglist.get(j).getName().contains("_1")) {
						String uploadpath = OSSUtils.ossMangerUpload(
								imglist.get(j), 1);
						customerImportList.get(i).setBlUrl(
								PropertyConfig.getStrValueByKey("oss_url")
										+ uploadpath);
					} else if (imglist.get(j).getName().toUpperCase()
							.contains(customerImport.getOrganizationCode())
							&& imglist.get(j).getName().contains("_2")) {
						String uploadpath = OSSUtils.ossMangerUpload(
								imglist.get(j), 1);
						customerImportList.get(i).setEcUrl(
								PropertyConfig.getStrValueByKey("oss_url")
										+ uploadpath);
					}
				}
				// if(!EntityUtil.checkObjAllFieldsIsNotNull(customerImportList.get(i))){
				// throw new RuntimeException();
				// }
			}
		}
		rc.setCode(code);
		rc.setMsg(msg);
		rc.setCustomerList(customerImportList);
		return rc;

	}

	@Override
	public int b2bCompany(List<CustomerImport> list, String storePk) {
		for (CustomerImport c : list) {
			String parentPk = "-1";
			B2bMemberDto mDto=null;
			if(null!=c.getMobile()&&!"".equals(c.getMobile())){
			  mDto= b2bMemberDao.getByMobile(c.getMobile());
			}
			B2bCompanyDto cdto = b2bCompanyDao.getByOrganizationCode(c
					.getOrganizationCode());
			if (null != cdto) {
				parentPk = cdto.getParentPk();
			}
			if (null != mDto && null != mDto.getCompanyPk()
					&& !"".equals(mDto.getCompanyPk())) {
				B2bCompanyDto company = b2bCompanyDao.getByPk(mDto
						.getCompanyPk());
				parentPk = "-1".equals(company.getParentPk()) ? company.getPk()
						: company.getParentPk();
				if (null != cdto && cdto.getPk().equals(parentPk)) {
					parentPk = cdto.getParentPk();
				}
			}
			B2bCompanyDto cDto = insertOrUpdateCompany(c, parentPk, cdto);
			if(null!=c.getMobile()&&!"".equals(c.getMobile())){ 
			 insertOrUpdateMember(c.getMobile(), cDto);
			}
			B2bCustomerManagementDto cmDto = insertMangerment(cDto, storePk);
			insertSaleMan(cmDto, c);
			if (!"-1".equals(parentPk)) {
				b2bCompanyService.updateParentPk(cDto.getPk(), parentPk);
			}
		}
		return 1;
	}

	private void insertSaleMan(B2bCustomerManagementDto cmDto, CustomerImport c) {
		b2bCustomerSalesmanDaoEx.deleteByCustomerPk(cmDto.getPk());
		B2bCustomerSalesman b2bCustomerSalesman = new B2bCustomerSalesman();
		b2bCustomerSalesman.setPk(KeyUtils.getUUID());
		b2bCustomerSalesman.setCustomerPk(cmDto.getPk());
		b2bCustomerSalesman.setEmployeeNumber(c.getEmployeeNumber());
		b2bCustomerSalesman.setEmployeeName(c.getEmployeeName());
		b2bCustomerSalesman.setPurchaserPk(cmDto.getPurchaserPk());
		b2bCustomerSalesman.setPurchaserName(c.getCompanyName());
		b2bCustomerSalesman.setStorePk(cmDto.getStorePk());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("employeeNumber", c.getEmployeeNumber());
		map.put("storePk", cmDto.getStorePk());
		List<B2bMemberDto> memberList = b2bMemberDaoEx
				.searchMemberByEmployeeNumber(map);
		if (null != memberList && memberList.size() > 0) {
			b2bCustomerSalesman.setMemberPk(memberList.get(0).getPk());
			b2bCustomerSalesman.setMobile(memberList.get(0).getMobile());
		}
		b2bCustomerSalesmanDao.insert(b2bCustomerSalesman);
	}

	private B2bCustomerManagementDto insertMangerment(B2bCompanyDto cDto,
			String storePk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaserPk", cDto.getPk());
		map.put("storePk", storePk);
		List<B2bCustomerManagementDto> list = b2bCustomerManagementDao
				.searchGrid(map);
		B2bCustomerManagementDto bcdto = null;
		if (null == list || list.size() == 0) {
			bcdto = new B2bCustomerManagementDto();
			bcdto.setPk(KeyUtils.getUUID());
			bcdto.setStorePk(storePk);
			bcdto.setPurchaserPk(cDto.getPk());
			B2bCustomerManagement bc = new B2bCustomerManagement();
			bc.UpdateDTO(bcdto);
			b2bCustomerManagementDao.insert(bc);
		} else {
			bcdto = list.get(0);
		}
		return bcdto;
	}

	private B2bCompanyDto insertOrUpdateCompany(CustomerImport c,
			String parentPk, B2bCompanyDto cdto) {
		B2bCompanyDto b2bcompany = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == cdto) {
			b2bcompany = new B2bCompanyDto();
			b2bcompany.setPk(KeyUtils.getUUID());
			b2bcompany.setName(c.getCompanyName());
			b2bcompany.setInsertTime(new Date());
			b2bcompany.setAuditSpStatus(0);
		} else {
			b2bcompany = cdto;
		}
		b2bcompany.setIsDelete(1);
		b2bcompany.setAuditStatus(2);

		b2bcompany.setParentPk(parentPk);
		b2bcompany.setUpdateTime(new Date());
		b2bcompany.setCompanyType(1);
		b2bcompany.setAuditTime(new Date());
		b2bcompany.setIsVisable(1);
		b2bcompany.setIsPerfect(2);
		// 查询省市区
		map.put("parentPk", "-1");
		map.put("name", c.getProvinceName());
		SysRegionsDto province = sysRegionsService.getRegionsPk(map);
		if (!(null == province)) {
			b2bcompany.setProvince(province.getPk());
			b2bcompany.setProvinceName(province.getName());
		}
		map.put("parentPk", province.getPk());
		map.put("name", c.getCityName());
		SysRegionsDto provinceCity = sysRegionsService.getRegionsPk(map);
		if (!(null == provinceCity)) {
			b2bcompany.setCity(provinceCity.getPk());
			b2bcompany.setCityName(provinceCity.getName());
		}
		map.put("parentPk", provinceCity.getPk());
		map.put("name", c.getAreaName());
		SysRegionsDto provinceArea = sysRegionsService.getRegionsPk(map);
		if (!(null == provinceArea)) {
			b2bcompany.setArea(provinceArea.getPk());
			b2bcompany.setAreaName(provinceArea.getName());
		}
		b2bcompany.setRegAddress(c.getAddress());
		b2bcompany.setContactsTel(c.getContractsTel());
		b2bcompany.setContacts(c.getContracts());
		b2bcompany.setBlUrl(c.getBlUrl());
		b2bcompany.setOrganizationCode(c.getOrganizationCode());
		b2bcompany.setBankName(c.getBankName());
		b2bcompany.setBankAccount(c.getBankAccount());
		b2bcompany.setEcUrl(c.getEcUrl());
		b2bcompany.setInfoUpdateTime(new Date());
		B2bCompany model = new B2bCompany();
		model.UpdateDTO(b2bcompany);
		if (null == cdto) {
			b2bCompanyDao.insert(model);
		} else {
			b2bCompanyDao.update(model);
		}
		return b2bcompany;
	}

	private B2bMemberDto insertOrUpdateMember(String mobile, B2bCompanyDto cDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", cDto.getPk());
		int count = b2bMemberDao.searchGridCount(map);
		B2bMemberDto mdto = b2bMemberDao.getByMobile(mobile);
		if (null == mdto) {
			mdto = new B2bMemberDto();
			mdto.setPk(KeyUtils.getUUID());
			mdto.setMobile(mobile);
			mdto.setCompanyPk(cDto.getPk());
			mdto.setCompanyName(cDto.getName());
			mdto.setInsertTime(new Date());
			mdto.setPassword(MD5Utils.MD5Encode(mobile, "utf-8"));
			mdto.setAuditStatus(2);
			mdto.setIsVisable(1);
			mdto.setAuditTime(new Date());
			mdto.setUpdateTime(new Date());
			mdto.setRegisterSource(1);

			mdto.setParentPk("-1");
			mdto.setLevel(1);
			mdto.setInvitationCode(KeyUtils.getRandom(6));
			mdto.setExperience(0d);
			mdto.setShell(0d);
			mdto.setLevelName("青铜会员");
			B2bMember model = new B2bMember();
			model.UpdateDTO(mdto);
			b2bMemberDao.insert(model);
		} else {
			if (null == mdto.getCompanyPk() || "".equals(mdto.getCompanyPk())) {
				mdto.setCompanyPk(cDto.getPk());
				mdto.setCompanyName(cDto.getName());
			}
			mdto.setAuditStatus(2);
			mdto.setIsVisable(1);
			mdto.setAuditTime(new Date());
			mdto.setUpdateTime(new Date());
			B2bMember model = new B2bMember();
			model.UpdateDTO(mdto);
			b2bMemberDao.update(model);
		}
		// 将会员设为超级管理员
		if (count == 0) {
			b2bMemberRoleService.setAdmin(mdto.getPk());
		}
		return mdto;
	}

	@Override
	public String insertOrUpdate(B2bCustomerManagement b2bCustomerManagement) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storePk", b2bCustomerManagement.getStorePk());
		map.put("purchaserPk", b2bCustomerManagement.getPurchaserPk());
		List<B2bCustomerManagementDto> list = customerManagementDao
				.searchGrid(map);
		String pk = KeyUtils.getUUID();
		if (null != list && list.size() > 0) {
			pk = list.get(0).getPk();
		} else {
			b2bCustomerManagement.setPk(pk);
			customerManagementDao.insert(b2bCustomerManagement);
		}
		return pk;
	}

	// public static void main(String[] args) {
	// File file=new File("D:/开发文档/20100409.zip");
	// System.out.println(OSSUtils.ossMangerUpload(file,8));
	// }
}