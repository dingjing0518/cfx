package cn.cf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.CallBackContract;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bContract;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bTrancsationContractService;
import cn.cf.service.FileOperationService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 合同管理
 * @author fjm
 *
 */
@RestController
@RequestMapping("trade")
public class B2bContractController extends BaseController {
	
	@Autowired
	private B2bContractService b2bContractService;
	
	@Autowired
	private B2bFacadeService b2bFacadeService;
	
	@Autowired
	private FileOperationService fileOperationService;
	
	@Autowired
	private B2bTrancsationContractService trancsationContractService;
	
	private final static Logger logger = LoggerFactory.getLogger(B2bContractController.class);
	
    /**
     * 合同列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "searchContractList", method = RequestMethod.POST)
    public String searchContractList(HttpServletRequest req) {
    	Map<String, Object> map = this.paramsToMap(req);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		map.put("orderName", ServletUtils.getStringParameter(req, "orderName", "insertTime"));
		map.put("orderType", ServletUtils.getStringParameter(req, "orderType", "desc"));
		String searchType = ServletUtils.getStringParameter(req, "searchType","1");
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		Sessions session = this.getSessions(req);
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
        return RestCode.CODE_0000.toJson(b2bContractService.searchListByType(searchType,store,company, map,session,memberDto));

    }
    
    
    /**
     * 合同数量
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "searchContractCount", method = RequestMethod.POST)
    public String searchContractCount(HttpServletRequest req) {
    	Map<String, Object> map = this.paramsToMap(req);
		String searchType = ServletUtils.getStringParameter(req, "searchType","1");
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		Sessions session = this.getSessions(req);
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
        return RestCode.CODE_0000.toJson(b2bContractService.searchStatusByType(searchType,store, company, map,session,memberDto));

    }
    /**
     * 合同详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getContractDetails", method = RequestMethod.POST)
    public String getContractDetails(HttpServletRequest req) {
    	String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
    	String rest = null;
    	if("".equals(contractNo)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		rest = RestCode.CODE_0000.toJson(b2bContractService.getB2bContractDetails(contractNo));
    	}
        return rest;

    }
    
    /**
     * 提交合同
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "submitContract", method = RequestMethod.POST)
    public String submitContract(HttpServletRequest req) {
    	B2bCompanyDto company = this.getCompanyBysessionsId(req);
    	B2bMemberDto member = this.getMemberBysessionsId(req);
		String contacts = ServletUtils.getStringParameter(req, "contracts","");
    	String rest = null;
    	if("".equals(contacts)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		Integer source = this.getSource(req);
    		CallBackContract back = b2bFacadeService.submitContract(contacts, company, member,source);
    		if(null == back){
    			rest = RestCode.CODE_S999.toJson();
    		}else if(null != back.getoList() && back.getoList().size()>0){
    			rest = RestCode.CODE_0000.toJson(back.getoList());
    			b2bFacadeService.afterContract(member, back.getoList());
    		}else{
    			rest = back.getRest();
    		}
    	}
    	return rest;
    }
    
    /**
     * 合同审核
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "auidtContract", method = RequestMethod.POST)
    public String auidtContract(HttpServletRequest req) {
    	String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
    	Integer type = ServletUtils.getIntParameter(req, "type",1);
    	B2bMemberDto member = this.getMemberBysessionsId(req);
    	String rest = null;
    	if("".equals(contractNo)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		rest = b2bFacadeService.auditContract(member,contractNo,type);
    	}
    	return rest;
    }
    
    /**
     * 删除合同
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "delContract", method = RequestMethod.POST)
    public String delContract(HttpServletRequest req) {
    	String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
    	String rest = null;
    	if("".equals(contractNo)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		try {
    			B2bContract contract = new B2bContract();
    			contract.setContractNo(contractNo);
    			contract.setIsDelete(2);
     			b2bContractService.updateContractStatus(contract);
     			rest = RestCode.CODE_0000.toJson();
			} catch (Exception e) {
				logger.error("delContract",e);
				rest = RestCode.CODE_S999.toJson();
			} 
    	}
    	return rest;
    }
    
    /**
     *合同列表导出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "exportContract", method = RequestMethod.POST)
    public void exportContractList(HttpServletRequest req,HttpServletResponse resp) {
    	try {
			Map<String, Object> map = this.paramsToMap(req);
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			B2bStoreDto store = this.getStoreBysessionsId(req);
			String searchType = ServletUtils.getStringParameter(req, "searchType","1");
			B2bMemberDto memberDto = this.getMemberBysessionsId(req);
			Sessions session = this.getSessions(req);
			List<B2bContractGoodsDtoEx> list = b2bContractService.exportContractList(searchType,store,company, map,session,memberDto);
			fileOperationService.exportContract(list,session,searchType,req, resp);
		} catch (Exception e) {
			logger.error("exportContract", e);
		}
    	return;
    }
    
    
	/**
	 * 合同交易数据统计
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/contractTransactionList", method = RequestMethod.POST)
	public String contractTransactionList(HttpServletRequest req,HttpServletResponse resp){
		Map<String, Object> map = this.paramsToMap(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		map.put("start", null==map.get("start")?0:map.get("start"));
		map.put("limit", null==map.get("limit")?10:map.get("limit"));
		return RestCode.CODE_0000.toJson(trancsationContractService.searchPageTrancsationList(map,store,company));
	}
	
	/**
	 * 合同交易数据导出
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/contractExportTransaction", method = RequestMethod.POST)
	public void contractExportTransaction(HttpServletRequest req,HttpServletResponse resp){
		Map<String, Object> map = this.paramsToMap(req);
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		fileOperationService.exportTrancsationContract(trancsationContractService.searchTrancsationList(map,store, company), req, resp);
		return;
	}
	
	
	 /**
     * 提交审批
     * @param request
     * @return
     */
    @RequestMapping(value = "/submitAudit", method = RequestMethod.POST)
    public String submitAudit(HttpServletRequest req) {
    	String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
    	String rest = null;
    	if("".equals(contractNo)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		rest = b2bContractService.submitAudit(contractNo);
    	}
        return rest;
    }
    
    
    /**
	 * 合同商品列表
	 * 重量未转换完的商品
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "contractGoods", method = RequestMethod.POST)
	public String contractGoods(HttpServletRequest req){
		String rest = null;
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		if( "".equals(contractNo)){
			rest = RestCode.CODE_A001.toJson();
		}else  {
			rest = RestCode.CODE_0000.toJson(b2bContractService.searchListByContractNo(contractNo));
		}
		return rest;
	}
}
