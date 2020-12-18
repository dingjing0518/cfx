package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.dao.B2bAddressDaoEx;
import cn.cf.dto.B2bAddressDto;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.model.B2bAddress;
import cn.cf.service.B2bAddressService;
import cn.cf.service.CommonService;
import cn.cf.util.KeyUtils;

@Service
public class B2bAddressServiceImpl implements B2bAddressService {

	private final static Logger logger = LoggerFactory.getLogger(SysNewsServiceImpl.class);

	@Autowired
	private B2bAddressDaoEx addressDao;
	
	@Autowired
	private CommonService commonService;

	@Override
	public B2bAddressDto getAddress(String pk) {
		B2bAddressDto address=addressDao.getByPk(pk);
		if(null!=address&&null!=address.getIsDelete()&&address.getIsDelete()==1){
			return address;
		}
		return null ;
	}

	@Override
	public PageModel<B2bAddressDto> searchAddressList(Map<String, Object> map) {
		PageModel<B2bAddressDto> pm = new PageModel<B2bAddressDto>();
		map.put("isDelete", 1);
		pm.setDataList(addressDao.searchGrid(map));
		pm.setTotalCount(addressDao.searchGridCount(map));
		if (null != map.get("start")) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	@Override
	public String addAddress(B2bAddressDto address, String memberPk) {
		int count = addressDao.searchExistence(address);
		String rest = null;
		// 收货地址已存在
		if (count > 0) {
			return RestCode.CODE_D001.toJson();
		}
		// 验证字段长度
		if (null!=address.getSigningCompany() && !"".equals(address.getSigningCompany()) && address.getSigningCompany().length() >= 200) {
			return RestCode.CODE_D003.toJson();
		}
		if (null!=address.getContacts() && !"".equals(address.getContacts()) && address.getContacts().length() >= 20) {
			return RestCode.CODE_D004.toJson();
		}
		if (null!=address.getContactsTel() && !"".equals(address.getContactsTel()) && address.getContactsTel().length() > 11) {
			return RestCode.CODE_D005.toJson();
		}
		if (null!=address.getAddress() && !"".equals(address.getAddress()) && address.getAddress().length() >= 800) {
			return RestCode.CODE_D006.toJson();
		}
		B2bAddress model = new B2bAddress();
		model.UpdateDTO(address);
		// 如果新增的地址为默认收货地址 则将其他地址设为非默认
		if (null != address.getIsDefault() && address.getIsDefault() == 1) {
			addressDao.updateNoDefault(model.getCompanyPk());
		} else {
			model.setIsDefault(2);
		}
		model.setPk(KeyUtils.getUUID());
		model.setInsertTime(new Date());
		model.setIsDelete(1);
		model.setSyncStatus(2);
		addressDao.insert(model);
		// 添加首次完善收货地址积分
		addAddressPoint(memberPk, model);
		rest = RestCode.CODE_0000.toJson(model);
		return rest;
	}

	private void addAddressPoint(String memberPk, B2bAddress model) {
		try {
			//会员体系加积分
			String dimenType = MemberSys.ACCOUNT_DIMEN_ADDR.getValue();
			Map<String, String> paraMap=new HashMap<>();
			paraMap.put("dimenType", dimenType);
			paraMap.put("companyPk", model.getCompanyPk());
			/*String result= HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/searchPointList", paraMap);
			try {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonarray = JSONArray.fromObject(JsonUtils.getJsonData(result));  
			if (jsonarray == null || jsonarray.size() == 0) {
				paraMap.remove("dimenType");
				paraMap.remove("companyPk");
				paraMap.put("memberPk", memberPk);
				paraMap.put("companyPk", model.getCompanyPk());
				paraMap.put("pointType", "1");
				paraMap.put("active", dimenType);
				HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/addPoint", paraMap);
			}*/
			List<MangoMemberPoint> list = commonService.searchPointList(paraMap);
			if (null == list || list.size()==0) {
				commonService.addPoint(memberPk, model.getCompanyPk(), 1, dimenType);
			}
		} catch (Exception e) {
			logger.error("addAddressPoint", e);
			e.printStackTrace();
		}
	}

	@Override
	public String updateAddress(B2bAddressDto address) {
		String rest = null;
		// 验证字段长度
		if (null!=address.getSigningCompany() && !"".equals(address.getSigningCompany()) && address.getSigningCompany().length() >= 200) {
			return RestCode.CODE_D003.toJson();
		}
		if (null!=address.getContacts() && !"".equals(address.getContacts()) && address.getContacts().length() >= 20) {
			return RestCode.CODE_D004.toJson();
		}
		if (null!=address.getContactsTel() && !"".equals(address.getContactsTel()) && address.getContactsTel().length() > 11) {
			return RestCode.CODE_D005.toJson();
		}
		if (null!=address.getAddress() && !"".equals(address.getAddress()) && address.getAddress().length() >= 800) {
			return RestCode.CODE_D006.toJson();
		}
		B2bAddressDto adto =  addressDao.getByPk(address.getPk());
		if(null == adto || adto.getIsDelete() == 2){
			return RestCode.CODE_D007.toJson();
		}
		if (null==address.getIsDefault()||"".equals(address.getIsDefault())) {
			int count = addressDao.searchExistence(address);
			// 收货地址已存在
			if (count > 0) {
				return RestCode.CODE_D001.toJson();
			}
		}
		B2bAddress model = new B2bAddress();
		model.UpdateDTO(address);
		// 如果新增的地址为默认收货地址 则将其他地址设为非默认
		if (null != address.getIsDefault() && address.getIsDefault() == 1) {
			addressDao.updateNoDefault(model.getCompanyPk());
		}
		model.setSyncStatus(2);
		addressDao.updateEx(model);
		rest = RestCode.CODE_0000.toJson(model);
		return rest;
	}

	@Override
	public String delAddress(String pk) {
		String rest = RestCode.CODE_0000.toJson();
		B2bAddress address = new B2bAddress();
		address.setPk(pk);
		address.setIsDelete(2);
		int result = addressDao.update(address);
		if (result != 1) {
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}

}
