package cn.cf.controller.economics;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.dto.MemberShip;
import cn.cf.entity.EconomicsCustomerSource;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bEconomicsCreditcustomer;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.B2bEconomicsGoodsService;
import cn.cf.service.enconmics.EconomicsCreditCustomerService;
import cn.cf.service.manage.AccountManageService;
import cn.cf.service.operation.CustomerManageService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class CustomerRecordsController extends BaseController{
	
	@Autowired
	private EconomicsCreditCustomerService economicsCreditCustomerService;
	
	@Autowired
	private AccountManageService accountManageService;
	
	@Autowired
	private CustomerManageService customerManageService;
	
	@Autowired
	private B2bEconomicsGoodsService  b2bEconomicsGoodsService;
	
	/**
	 * 申请客户记录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("economicsCustomerRecordsManage")
	public ModelAndView economicsCustomer(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/economicsCustomerRecordsManage");
		mv.addObject("acccountList", accountManageService.getAllAccountList());
		List<EconomicsCustomerSource>sources=new ArrayList<EconomicsCustomerSource>();
		sources.add(new EconomicsCustomerSource("1","盛虹推荐"));
		sources.add(new EconomicsCustomerSource("2","自主申请"));
		sources.add(new EconomicsCustomerSource("3","新凤鸣推荐"));
		sources.add(new EconomicsCustomerSource("4","营销推荐"));
		sources.add(new EconomicsCustomerSource("5","其他供应商推荐"));
		sources.add(new EconomicsCustomerSource("6","CMS后台申请"));
		mv.addObject("sourceList",sources);
		mv.addObject("purCompanyList",customerManageService.getPurCompanyList());
		mv.addObject("economicGoodsList",b2bEconomicsGoodsService.searchList());
		return mv;
	}
	
	/**
	 * 申请客户记录列表
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchRecordsCustList")
	@ResponseBody
	public String searchRecordsCustList(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCreditcustomerDtoEx customerExtDto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		ManageAccount account = getAccount(request);
		customerExtDto.setAccountPk(account.getPk());
		QueryModel<B2bEconomicsCreditcustomerDtoEx> qm = new QueryModel<B2bEconomicsCreditcustomerDtoEx>(customerExtDto, start,
				limit, sort, dir);
		
		MemberShip  currentMemberShip =  this.getMemberShipByAccount(account.getAccount());
		PageModel<B2bEconomicsCreditcustomerDtoEx> pm = economicsCreditCustomerService.searchEconCustList(qm, currentMemberShip);
		return JsonUtils.convertToString(pm);
	}
	
	/**
	 * 编辑客户记录信息(分配客户/取消)
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateCreditCustomerRecords")
	@ResponseBody
	public String updateCreditCustomerRecords(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCreditcustomer customer) throws Exception {
	 	String	msg = Constants.RESULT_SUCCESS_MSG;
		try {
			economicsCreditCustomerService.updateB2bEconomicsCreditcustomer(customer,true);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 受理
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("accepttanceCustomer")
	@ResponseBody
	public String accepttanceCustomer(HttpServletRequest request, HttpServletResponse response,
			String  pk) throws Exception {
	 	String	msg = null;
		try {
			msg = economicsCreditCustomerService.acceptanceCustomer(pk);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
		
	}
	
	
	
	/**
	 * 后台添加申请客户记录
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("insertCreditcustomer")
	@ResponseBody
	public String insertCreditcustomer(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCreditcustomer  model) throws Exception {
	 	String	msg = null;
		try {
			msg = economicsCreditCustomerService.insert(model);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
		
	}
}
