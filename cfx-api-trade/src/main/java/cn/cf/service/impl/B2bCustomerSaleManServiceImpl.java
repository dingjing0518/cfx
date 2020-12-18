package cn.cf.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.CommonService;

@Service
public class B2bCustomerSaleManServiceImpl implements B2bCustomerSaleManService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CommonService commonService;
	
	@Override
	public B2bMemberDto getSaleMan(String companyPk, String purchaserPk,
			String productPk, String storePk) {
		B2bMemberDto memberDto = null;
		try {
			/*Map<String,String> saleMap = new HashMap<String, String>();
			saleMap.put("companyPk", companyPk);
			saleMap.put("purchaserPk", purchaserPk);
			saleMap.put("productPk", productPk);
			saleMap.put("storePk", storePk);
			String rest = HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/getSaleMan", saleMap);
			rest = EncodeUtil.des3Decrypt3Base64(rest)[1];
			if(null != rest && !"".equals(rest)){
				 memberDto = JsonUtils.toJSONBean(JsonUtils.getJsonData(rest), B2bMemberDto.class);
			}*/
			memberDto = commonService.getSaleMan(companyPk, purchaserPk, productPk, storePk);
		} catch (Exception e) {
			logger.error("getSaleMan",e);
		}
		return memberDto;
	}

	@Override
	public void addCustomerManagement(String purchaserPk, String storePk) {
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("purchaserPk", purchaserPk);
		map.put("storePk", storePk);
		HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") + "member/addCustomerByManagement", map);*/
		B2bCustomerManagementDto b2bCustomerManagementDto = new B2bCustomerManagementDto();
		b2bCustomerManagementDto.setPurchaserPk(purchaserPk);
		b2bCustomerManagementDto.setStorePk(storePk);
		commonService.addCustomerManagement(b2bCustomerManagementDto);
	}

	

}
