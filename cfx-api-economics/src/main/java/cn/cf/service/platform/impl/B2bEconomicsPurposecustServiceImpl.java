package cn.cf.service.platform.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.constant.SmsCode;
import cn.cf.dao.B2bEconomicsPurposecustDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bEconomicsPurposecustDto;
import cn.cf.jedis.JedisUtils;
import cn.cf.model.B2bEconomicsPurposecust;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.platform.B2bEconomicsPurposecustService;
import cn.cf.util.KeyUtils;

@Service
public class B2bEconomicsPurposecustServiceImpl implements
		B2bEconomicsPurposecustService {
	
	@Autowired
	private B2bEconomicsPurposecustDao b2bEconomicsPurposecustDao;
	
	
	@Autowired
	private ForeignCompanyService companyService;

	@Override
	public List<B2bEconomicsPurposecustDto> getB2bEconomicsPurposecust(
			String companyPk, Integer status) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("status", status);
		return b2bEconomicsPurposecustDao.searchGrid(map);
	}
	
	@Override
	public String addEconomicsPurposecust(
			B2bEconomicsPurposecustDto dto,String smsCode) {
		String rest = null;
		RestCode code = RestCode.CODE_0000;
		Boolean flag = true;
		//短信验证码
		String messageCode = JedisUtils.get(SmsCode.SMS_CODE.getValue() + dto.getContactsTel()) != null ? JedisUtils
				.get(SmsCode.SMS_CODE.getValue() + dto.getContactsTel()).toString() : "";
		if(!messageCode.equals(smsCode)){
			code = RestCode.CODE_S004;
			flag = false;
		}
		Map<String,Object> status = null;
		if(flag){
			status =  new HashMap<String,Object>();
			//查询公司是否已经注册
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", dto.getCompanyName());
			map.put("isDelete", 1);
			List<B2bCompanyDto> list = companyService.getCompanyDtoByMap(map);
			if(null != list && list.size()>0){
				//返回已注册状态 跳登录页面
				status.put("status", 3);
				code = RestCode.CODE_C008;
				flag = false;
			//查询是否申请过意向客户
			}else{
				map.put("companyName", dto.getCompanyName());
				//返回处理中状态跳转处理中页面
//				status.put("status", 1);
				map.put("type", dto.getType());
				List<B2bEconomicsPurposecustDto> dtos = b2bEconomicsPurposecustDao.searchGrid(map);
				if(null != dtos && dtos.size()>0){
					//返回当前意向客户信息状态跳转对应的页面
					status.put("status", dtos.get(0).getStatus());
					code = RestCode.CODE_C009;
					flag = false;
				}
			}
		}
		if(flag){
			B2bEconomicsPurposecust model = new B2bEconomicsPurposecust();
			model.UpdateDTO(dto);
			model.setInsertTime(new Date());
			model.setPk(KeyUtils.getUUID());
			model.setStatus(1);
			model.setType(dto.getType());
			int result =  b2bEconomicsPurposecustDao.insert(model);
			if(result != 1){
				code = RestCode.CODE_S999;
			}
		}
		if(null != status){
			rest = code.toJson(status);
		}else{
			rest = code.toJson();
		}
		return rest;
	}
}
