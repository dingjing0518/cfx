package cn.cf.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.constant.Constants;
import cn.cf.dao.CmsDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.MarketingOrderMemberDto;
import cn.cf.service.CmsService;

@Service
public class CmsServiceImpl implements CmsService {
    
    
    @Autowired
    private CmsDao cmsDao;


    @Override
    public  String insertSaleManByOrder(Map<String, Object> map) {
        String msg = Constants.RESULT_SUCCESS_MSG;
        try {
            String orderPk =   map.get("orderPk").toString();
            int type =  Integer.parseInt(map.get("type").toString()) ;
            MarketingOrderMemberDto  marketingOrderMemberDto = cmsDao.getMarketingOrderMemberByPk(orderPk);
            if (marketingOrderMemberDto==null) {//一个订单仅调用一次
            	
            	  if (type== Constants.ONE){//化纤普通订单
                      B2bOrderDto orderDto = cmsDao.getCfOrderByOredrPk(orderPk);
                      if(orderDto.getBlock().equals("cf")){
                          insertOrderMember(orderPk,orderDto.getStorePk(),orderDto.getPurchaserPk(), Constants.ONE);
                      }
                 }else if (type== Constants.THREE) {//化纤的合同订单
              	    B2bContractDto orderDto = cmsDao.getCfContractByOredrPk(orderPk);
              	    if(orderDto.getBlock().equals("cf")){
                      insertOrderMember(orderPk,orderDto.getStorePk(),orderDto.getPurchaserPk(), Constants.THREE);
              	    }
                 }
			}
         
        } catch (Exception e) {
            msg = Constants.RESULT_FAIL_MSG;
            
            e.printStackTrace();
        }
        return msg;
    }

    private void insertOrderMember(String orderPk,String storePk , String purchaserPk,Integer type) {
        MarketingOrderMemberDto stDto =  cmsDao.getSaleByStorePk(storePk);
        MarketingOrderMemberDto purDto =  cmsDao.getSaleByPurCompanyPk(purchaserPk);
        MarketingOrderMemberDto  dto = new MarketingOrderMemberDto();
        dto.setOrderPk(orderPk);
    	dto.setType(type);
    	dto.setPurchaserPk(purchaserPk);
    	dto.setStorePk(storePk);
    	//采购商
    	if(purDto!=null){
    		dto.setPurProvince(purDto.getPurProvince());
        	dto.setPurCity(purDto.getPurCity());
        	dto.setPurArea(purDto.getPurArea());
        	dto.setPurAddress(purDto.getPurAddress());
        	dto.setPurAccount(purDto.getPurAccount());
    	}else{//采购商未分配
    		B2bCompanyDto   companyDto = cmsDao.getByPk(purchaserPk);
    		dto.setPurProvince(companyDto.getProvince());
        	dto.setPurCity(companyDto.getCity());
        	dto.setPurArea(companyDto.getArea());
        	dto.setPurAddress(companyDto.getProvince()+companyDto.getCity()+companyDto.getArea());
    	}
    	//供应商
    	if(stDto!=null){
    	dto.setStProvince(stDto.getStProvince());
    	dto.setStCity(stDto.getStCity());
    	dto.setStArea(stDto.getStArea());
    	dto.setStAddress(stDto.getStAddress());
    	dto.setStAccount(stDto.getStAccount());
    	}else{
    		B2bCompanyDto   companyDto = cmsDao.getByStorePk(storePk);
    		dto.setStProvince(companyDto.getProvince());
        	dto.setStCity(companyDto.getCity());
        	dto.setStArea(companyDto.getArea());
        	dto.setStAddress(companyDto.getProvince()+companyDto.getCity()+companyDto.getArea());
    	}
        cmsDao.insertOrderMember(dto);
             
    }




}
