package cn.cf.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.SmsCode;
import cn.cf.dto.LgCarDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgDriverDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.dto.OrderDeliveryDto;
import cn.cf.dto.PageModelOrderVo;
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.entity.Sessions;
import cn.cf.exception.ErrorParameterException;
import cn.cf.jedis.JedisUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.LgCarService;
import cn.cf.service.LgDeliveryOrderService;
import cn.cf.service.LgDriverService;
import cn.cf.service.LgMemberService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.SysService;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 物流系统 “承运商中心”所有接口
 * 
 * @author wangc
 *
 */
@RestController
@RequestMapping("/logistics/supplier")
public class LgSupplierController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(LgSupplierController.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private LgMemberService lgMemberService;

    @Autowired
    private SysService sysService;
    
	@Autowired
	private B2bFacadeService commonService;
	
	@Autowired
	private LgCarService lgCarService;
	
	@Autowired
	private LgDriverService lgDriverService;

	@Autowired
	private LgDeliveryOrderService lgDeliveryOrderService;
	
	@Autowired
	private CuccSmsService commonSmsService;

	/**
	 * 登录接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "verificationLogin", method = RequestMethod.POST)
	public String verificationLogin(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		String sessionId = request.getSession().getId();
		String mobile = ServletUtils.getStringParameter(request, "mobile", "");
		String password = ServletUtils.getStringParameter(request, "password","");
		Integer source = this.getSource(request);
		if (null == mobile || "".equals(mobile)|| null == password||"".equals(password)) {
			restCode = RestCode.CODE_A007;
			return restCode.toJson();
		}
		LgMemberDtoEx lgMemberDto = lgMemberService.getMemberByMobile(mobile);
		//用户不存在
		if (null == lgMemberDto) {
			restCode = RestCode.CODE_M001;
			return restCode.toJson();
		}
		//该用户状态是删除状态
		if(lgMemberDto != null && 2==lgMemberDto.getIsDelete()){
			restCode = RestCode.CODE_M001;
			return restCode.toJson();
		}
		//用户禁用
		if(lgMemberDto != null && 2==lgMemberDto.getIsVisable()){
			restCode = RestCode.CODE_M006;
			return restCode.toJson();
		}
		//密码错误
		if (!password.equals(lgMemberDto.getPassword())) {
			restCode = RestCode.CODE_M002;
			return restCode.toJson();
		}
		//单点登陆限制
		Object oldSession =  JedisUtils.get("login-lg"+mobile);
		String oldSessionId = null;
		 if (!(oldSession instanceof Boolean)) {
			oldSessionId = oldSession.toString();
			boolean falge = JedisUtils.del(oldSessionId);
			boolean falge1 = JedisUtils.del("login-lg"+mobile);
			if(!falge || !falge1){
				return RestCode.CODE_S999.toJson();
			}
		}
		Sessions session = commonService.addSessions(lgMemberDto, sessionId,source);
		return restCode.toJson(session);
	}

	@RequestMapping(value = "getCompanyBySession", method = RequestMethod.POST)
	public String getCompanySession(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		String sessionId = ServletUtils.getStringParameter(request,"sessionId", "");
		LgCompanyDto dto = null;
		if (null != sessionId && !"".equals(sessionId)) {
			dto = this.getLgCompanyBysessionsId(request);
		}
		if (null != dto) {
			return restCode.toJson(dto);
		} else {
			return restCode.toJson();
		}
	}

	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "doLogout", method = RequestMethod.POST)
	public String doLogout(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		String sessionId = ServletUtils.getStringParameter(request,"sessionId", "");
		if (null==sessionId||"".equals(sessionId)) {
			return RestCode.CODE_A007.toJson();
		}
		LgMemberDto member = this.getLgMemberBysessionsId(request);
		if(null == member){
			return RestCode.CODE_S999.toJson();
		}
		boolean falge = JedisUtils.del(sessionId);
		if (!falge) {
			restCode = RestCode.CODE_S999;
		}
		boolean falge1 = JedisUtils.del("login-lg"+member.getMobile());
		if (!falge1) {
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	
	/**
	 * 验证是否注册
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "isRegister", method = RequestMethod.POST)
	public String isRegister(HttpServletRequest req) {
		String result="";
		try {
			String mobile = ServletUtils.getStringParameter(req, "mobile");
			result = lgMemberService.verificationMobile(mobile).toJson();
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 获取短信验证码接口
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "verificationCode", method = RequestMethod.POST)
	public String verificationCode(HttpServletRequest req) {
		RestCode restCode=RestCode.CODE_0000;
		try {
			LgMemberDtoEx lgMemberDtoEx = new LgMemberDtoEx();
			lgMemberDtoEx.bind(req);
			SysSmsTemplateDto sdto = sysService.getSmsByName("verification");// register注册验证码
			if (sdto != null) {
				return	 commonSmsService.sendCuccCode(lgMemberDtoEx.getMobile());
			} else {
				restCode = RestCode.CODE_SMS001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode =RestCode.CODE_S999;
		}
		return restCode.toJson();
	}

	/**
	 * 校验短信验证码接口
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	public String checkCode(HttpServletRequest req) {
		String result="";
		try {
			String mobile = ServletUtils.getStringParameter(req, "mobile", "");
			String code = ServletUtils.getStringParameter(req, "code", "");
			logger.info("checkCode param:mobile:"+mobile+",code:"+code);
			// 验证手机验证码
			String messageCode = JedisUtils.get("Code" + mobile) != null ? JedisUtils
					.get("Code" + mobile).toString() : "";
			if (!"".equals(messageCode)) {
				if (!messageCode.equals(code)) {
					return RestCode.CODE_S006.toJson();
				}
			} else {
				return RestCode.CODE_S006.toJson();
			}
			result = RestCode.CODE_0000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("checkCode exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		logger.info("checkCode result:"+result);
		return result;
	}

	/**
	 * 密码重置
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	public String resetPassword(HttpServletRequest req) {
		String result="";
		try {
			LgMemberDto member = new LgMemberDto();
			String mobile = ServletUtils.getStringParameter(req, "mobile", "");
			member.setMobile(mobile);
			String password = ServletUtils.getStringParameter(req, "password","");
			logger.info("resetPassword params:mobile:"+member+",password:"+password);
			RestCode code = lgMemberService.backPassWord(mobile, password);
			if (code.getCode() == "0000") {
				Map<String, String> map = new HashMap<String, String>();
				sysService.sendMessageContent(member, SmsCode.FIND_PWD.getValue(), map);
			}
			result = code.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("resetPassword exception:", e);
			result= RestCode.CODE_S999.toJson();
		}
		logger.info("resetPassword result:"+result);
		return result;
	}
	
    
	/**
	 * 承运商账户信息
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getAccountInfo", method = RequestMethod.POST)
	public String accountInfo(HttpServletRequest req) {
		String result="";
		Sessions session = this.getSessions(req);
		try {
			LgMemberDto member = lgMemberService.getMemberByMobile(session.getMobile());
			result = RestCode.CODE_0000.toJson(member);
		} catch (Exception e) {
			logger.error("getAccountInfo"+e);
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		logger.info("getAccountInfo result:"+result);
		return result;
	}
	
    /**
     * 承运商修改密码
     * @param req
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest req) {
        try {
        	String result="";
            Sessions session = this.getSessions(req);
            String password = ServletUtils.getStringParameter(req, "password","");
            String newPassword = ServletUtils.getStringParameter(req, "newPassword", "");
            if (null == password || "".equals(password)) {
            	result=RestCode.CODE_A001.toJson();
            	logger.info("updatePassword result:"+result);
                return result;
            }
            if (null == newPassword || "".equals(newPassword)) {
            	result=RestCode.CODE_A001.toJson();
            	logger.info("updatePassword result:"+ result);
                return result;
            }
            LgMemberDto dto = lgMemberService.getMemberByMobile(session.getMobile());
            if (null == dto) {
            	result = RestCode.CODE_M001.toJson();
            	logger.info("updatePassword result:"+ result);
                return result;
            }
            if (!password.equals(dto.getPassword())) {
            	result = RestCode.CODE_M002.toJson();
            	logger.info("updatePassword result:"+ result);
                return result;
            }
            int resultTemp = lgMemberService.updatePassword(dto.getPk(), newPassword);
            if (resultTemp == 1) {
                Map<String, String> map = new HashMap<String, String>();
                sysService.sendMessageContent(dto,SmsCode.ED_PWD.getValue() , map);
            }
            result = RestCode.CODE_0000.toJson();
            return result;
        } catch (Exception e) {
        	logger.error("updatePassword exception:"+ e);
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }
    
    
    
    /**
     * 承运商车辆列表信息
     * @param req
     * @return
     */
    @RequestMapping(value = "carList", method = RequestMethod.POST)
    public String address(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            Sessions session = this.getSessions(req);
            map.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
            String searchStr = ServletUtils.getStringParameter(req, "searchStr", "");
            if (!searchStr.equals(""))
                map.put("plateNumber", map.get("searchStr"));
            String time1 = ServletUtils.getStringParameter(req, "begtime", "");
            if (!time1.equals("") && !time1.equals("0"))
                map.put("updateTimeBegin", time1 + " 00:00:00");
            String time2 = ServletUtils.getStringParameter(req, "endtime", "");
            if (!time2.equals("") && !time2.equals("0"))
                map.put("updateTimeEnd", time2 + " 23:59:59");
            PageModel<LgCarDto> car = lgCarService.searchCarList(map);
            result = RestCode.CODE_0000.toJson(car);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("carList exception============="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    /**
     * 承运商车辆详情信息
     * @param req
     * @return
     */
    @RequestMapping(value = "getCarInfo", method = RequestMethod.POST)
    public String carInfo(HttpServletRequest req) {
        String result="";
    	try {
            String pk = ServletUtils.getStringParameter(req, "pk", "");
            LgCarDto dto = lgCarService.searchCarByPk(pk);
            result = RestCode.CODE_0000.toJson(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCarInfo exception=============="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    
    /**
     * 新增车辆
     * @param req
     * @return
     */
    @RequestMapping(value = "addCar", method = RequestMethod.POST)
    public String addCar(HttpServletRequest req) {
        try {
            LgCarDto dto = new LgCarDto();
            dto.bind(req);
            RestCode code = RestCode.CODE_0000;
            if (!"0000".equals(code.getCode())) {
                return code.toJson();
            }
            if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
                Sessions session = this.getSessions(req);
                dto.setCompanyPk(session.getCompanyPk()==null?"":session.getCompanyPk());
            }
            int count = lgCarService.searchEntity(dto);
            if (count > 0) {
                return RestCode.CODE_M005.toJson();
            }
            LgCarDto d = lgCarService.addCar(dto);
            return RestCode.CODE_0000.toJson(d);
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }
    
    
    /**
     * 修改车辆
     * @param req
     * @return
     */
    @RequestMapping(value = "updateCar", method = RequestMethod.POST)
    public String updateAddrss(HttpServletRequest req) {
        String result="";
    	try {
            LgCarDto dto = new LgCarDto();
            dto.bind(req);
            if (dto == null || dto.getPk() == null || "".equals(dto.getPk())) {
                return RestCode.CODE_A001.toJson();
            }
            Sessions session = this.getSessions(req);
            dto.setCompanyPk(session.getCompanyPk()==null?"":session.getCompanyPk());
            if (lgCarService.searchEntity(dto)>0) {
            	 return RestCode.CODE_M005.toJson();
			}
            lgCarService.updateCar(dto);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateCar exception============="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    /**
     * 删除车辆
     * @param req
     * @return
     */
    @RequestMapping(value = "delCar", method = RequestMethod.POST)
    public String delAddrss(HttpServletRequest req) {
        String result="";
    	try {
            String pk = ServletUtils.getStringParameter(req, "pk", "");
            if (pk == null || "".equals(pk)) {
                return RestCode.CODE_A001.toJson();
            }
            lgCarService.delCar(pk);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delCar exception=============="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("delCar result:=============="+result);
    	return result;
    }
    
    
    /**
     * 司机列表信息
     * @param req
     * @return
     */
    @RequestMapping(value = "searchDriverList", method = RequestMethod.POST)
    public String searchDriverList(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            Sessions session = this.getSessions(req);
            map.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
            String searchStr = ServletUtils.getStringParameter(req, "searchStr", "");
            if (!searchStr.equals(""))
                map.put("mobile",map.get("searchStr"));
            String time1 = ServletUtils.getStringParameter(req, "begtime", "");
            if (!time1.equals("") && !time1.equals("0"))
                map.put("updateTimeBegin", time1 + " 00:00:00");
            String time2 = ServletUtils.getStringParameter(req, "endtime", "");
            if (!time2.equals("") && !time2.equals("0"))
                map.put("updateTimeEnd", time2 + " 23:59:59");
            PageModel<LgDriverDto> driver = lgDriverService.searchLgDriver(map);
            result = RestCode.CODE_0000.toJson(driver);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("searchDriverList exception============"+e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("searchDriverList result=============="+result);
    	return result;
    }
    
    
    /**
     * 司机详情信息
     * @param req
     * @return
     */
    @RequestMapping(value = "getDriverInfo", method = RequestMethod.POST)
    public String getDriverInfo(HttpServletRequest req) {
        String result="";
    	try {
            String pk = ServletUtils.getStringParameter(req, "pk", "");
            LgDriverDto dto =  lgDriverService.getByPk(pk);
            result = RestCode.CODE_0000.toJson(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getDriverInfo exception============"+e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("getDriverInfo result======="+result);
    	return result;
    }
    
    /**
     * 新增司机
     * @param req
     * @return
     */
	@RequestMapping(value = "addDriver", method = RequestMethod.POST)
	public String addDriver(HttpServletRequest req) {
		String result = "";
		try {
			LgDriverDto dto = new LgDriverDto();
			dto.bind(req);
			RestCode code = RestCode.CODE_0000;
			if (!"0000".equals(code.getCode())) {
				return code.toJson();
			}
			if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
				Sessions session = this.getSessions(req);
				dto.setCompanyPk(session.getCompanyPk() == null ? "" : session.getCompanyPk());
			}
			int count = lgDriverService.searchEntity(dto);
			if (count > 0) {
				return RestCode.CODE_A009.toJson();
			}
			LgDriverDto d = lgDriverService.add(dto);
			result = RestCode.CODE_0000.toJson(d);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addDriver exception:============="+e);
			result = RestCode.CODE_S999.toJson();
		}
		logger.info("addDriver result============"+result);
		return result;
	}
	
	
    /**
     * 修改司机信息
     * @param req
     * @return
     */
    @RequestMapping(value = "updateDriver", method = RequestMethod.POST)
    public String updateDriver(HttpServletRequest req) {
        String result="";
    	try {
            LgDriverDto dto = new LgDriverDto();
            dto.bind(req);
            if (dto == null || dto.getPk() == null || "".equals(dto.getPk())) {
                return RestCode.CODE_A001.toJson();
            }
            Sessions session = this.getSessions(req);
            dto.setCompanyPk(session.getCompanyPk()==null?"":session.getCompanyPk());
            int count=lgDriverService.searchEntity(dto);
			if(count>0){
				return RestCode.CODE_M008.toJson();
			}
			lgDriverService.updateDriver(dto);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateDriver exception:============="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("updateDriver result============="+result);
    	return result;
    }
    /**
     * 删除司机
     * @param req
     * @return
     */
    @RequestMapping(value = "delDriver", method = RequestMethod.POST)
    public String delDriver(HttpServletRequest req) {
        String result="";
    	try {
            String pk = ServletUtils.getStringParameter(req, "pk", "");
            if (pk == null || "".equals(pk)) {
                return RestCode.CODE_A001.toJson();
            }
            lgDriverService.delDriver(pk);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delDriver exception:"+e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("delDriver result:=========="+result);
    	return result;
    }
	
	
    /**
     * 订单管理_我的订单
     * @param req
     * @return
     */
    @RequestMapping(value = "deliveryOrderList", method = RequestMethod.POST)
    public String deliveryOrderList(HttpServletRequest req){
    	try {
    		Map<String, Object> par = this.paramsToMap(req);
    		par.put("start", ServletUtils.getIntParameterr(req, "start", 0));
    		par.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
    		par.put("orderStatus", ServletUtils.getIntParameterr(req, "orderStatus", 0));//0:全部
    		Sessions session = this.getSessions(req);
    		LgMemberDtoEx member= lgMemberService.getMemberByPk(session.getMemberPk());
    		//超级管理员
    		if (null!=member.getParantPk() &&  "-1".equals(member.getParantPk())) {
    			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
    			//par.put("isAbnormal", 1);
    			if (null!=par.get("orderStatus")&&par.get("orderStatus").toString().equals("0")) {
    				par.remove("orderStatus");
    				par.put("orderStatuses", "6,5,4,3");
    			}
    			par.put("isMember", 2);//超级管理员登录
    			PageModelOrderVo<OrderDeliveryDto> dataList = lgDeliveryOrderService.selectDeliveryList(par);
    			return RestCode.CODE_0000.toJson(dataList);
			}else{
				//普通业务员
				par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
    			//par.put("isAbnormal", 1);
    			if (null!=par.get("orderStatus")&&par.get("orderStatus").equals("0")) {
    				par.remove("orderStatus");
    				par.put("orderStatuses", "6,5,4,3");
    			}
    			par.put("isMember", 1);//业务员登录
    			par.put("memberPk", member.getPk());
    			PageModelOrderVo<OrderDeliveryDto> dataList = lgDeliveryOrderService.selectDeliveryList(par);
    			return RestCode.CODE_0000.toJson(dataList);
			}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return RestCode.CODE_S999.toJson();
    	}
    }
    
    
    /**
     * 订单管理-我的订单-导出订单
     * @param request
     * @throws Exception 
     */
	@RequestMapping(value = "exportOrder", method = RequestMethod.POST)
	public void exportOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> par = this.paramsToMap(request);
		Sessions session = this.getSessions(request);
		LgMemberDtoEx member= lgMemberService.getMemberByPk(session.getMemberPk());
		List<OrderDeliveryDto> list=new ArrayList<>();
		//超级管理员登录
		if (null!=member.getParantPk() && "-1".equals(member.getParantPk())) {
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			if (null!=par.get("orderStatus")&&par.get("orderStatus").equals("0")) {
				par.remove("orderStatus");
				par.put("orderStatuses", "6,5,4,3");
			}
			//par.put("isAbnormal", 1);
			par.put("isMember", 2);//超级管理员登录
			list = lgDeliveryOrderService.exportDeliveryOrder(par);
		}else {
			//普通业务员登录
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			if (null!=par.get("orderStatus")&&par.get("orderStatus").equals("0")) {
				par.remove("orderStatus");
				par.put("orderStatuses", "6,5,4,3");
			}
			//par.put("isAbnormal", 1);
			par.put("isMember", 1);//业务员登录
			par.put("memberPk", member.getPk());
			list = lgDeliveryOrderService.exportDeliveryOrder(par);
		}
		
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("SupplierDeliveryOrder.xls");
		String path = PropertyConfig.getProperty("excel_url");
		File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String fileName = "/order" + KeyUtils.getUUID() + ".xls";
		String destFileName = path + fileName;
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName));
		workbook.write(os);
		os.flush();
		os.close();
		download(response, request, destFileName);
		
	}
    
    
    /**
     * 订单管理_异常订单
     * @param req
     * @return
     */
    @RequestMapping(value = "abDeliveryOrderList", method = RequestMethod.POST)
    public String abDeliveryOrderList(HttpServletRequest req){
    	try {
    		Map<String, Object> par = this.paramsToMap(req);
    		par.put("start", ServletUtils.getIntParameterr(req, "start", 0));
    		par.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
    		Sessions session = this.getSessions(req);
    		LgMemberDtoEx member= lgMemberService.getMemberByPk(session.getMemberPk());
    		//超级管理员登录
    		if (null!=member.getParantPk() && "-1".equals(member.getParantPk())) {
    			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
    			par.put("isAbnormal", 2);
    			par.put("isMember", 2);//超级管理员登录
    			PageModelOrderVo<OrderDeliveryDto> dataList = lgDeliveryOrderService.selectAbDeliveryList(par);
    			return RestCode.CODE_0000.toJson(dataList);
			}else {
				par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
    			par.put("isAbnormal", 2);
    			par.put("isMember", 1);//普通业务员登录
    			par.put("memberPk", member.getPk());
    			PageModelOrderVo<OrderDeliveryDto> dataList = lgDeliveryOrderService.selectAbDeliveryList(par);
    			return RestCode.CODE_0000.toJson(dataList);
			}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return RestCode.CODE_S999.toJson();
    	}
    }
    
    /**
     * 订单管理-异常订单-导出
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="exportAbOrder",method= RequestMethod.POST)
    public void exportAbOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	Sessions session = this.getSessions(request);
    	LgMemberDtoEx member= lgMemberService.getMemberByPk(session.getMemberPk());
		List<OrderDeliveryDto> list=new ArrayList<>();
    	//超级管理员登录
		if (null!=member.getParantPk() && "-1".equals(member.getParantPk())) {
			Map<String,Object> par = this.paramsToMap(request);
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			par.put("isAbnormal", 2);
			par.put("isMember", 2);//超级管理员登录
			list = lgDeliveryOrderService.exportDeliveryOrder(par);
		}else {
			Map<String,Object> par = this.paramsToMap(request);
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			par.put("isAbnormal", 2);
			par.put("isMember", 1);//业务员登录
			par.put("memberPk", member.getPk());
			list = lgDeliveryOrderService.exportDeliveryOrder(par);
		}
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("SupplierDeliveryAbOrder.xls");
		String path = PropertyConfig.getProperty("excel_url");
		File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String fileName = "/order" + KeyUtils.getUUID() + ".xls";
		String destFileName = path + fileName;
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName));
		workbook.write(os);
		os.flush();
		os.close();
		download(response, request, destFileName);
    }
    
    
    /**
     * 订单详细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/deliveryOrderDetail", method=RequestMethod.POST)
    public String deliveryOrderDetail(HttpServletRequest request,HttpServletResponse response){
    	String result="";
    	try {
    		Map<String, Object> par = this.paramsToMap(request);
    		Sessions session = this.getSessions(request);
    		par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			HashMap<String, Object> data = lgDeliveryOrderService.selectDetailByDeliveryPkOnSupplier(par);
			result = RestCode.CODE_0000.toJson(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("deliveryOrderDetail exception:"+e);
			result = RestCode.CODE_S999.toJson();
		}
    	logger.info("deliveryOrderDetail result:"+result);
    	return result;
    }
    
    /**
     * 待处理_订单拆分
     * @param request
     * @return
     */
    @RequestMapping(value="/splitOrder",method = RequestMethod.POST)
    public String  orderSplit(HttpServletRequest request){
    	String result="";
    	try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			Integer boxes = ServletUtils.getIntParameterr(request, "boxes", 0);
			if (boxes==0) {
				return RestCode.CODE_P000.toJson();
			}
			String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
			logger.info("splitOrder params:deliveryPk:"+deliveryPk+",boxes:"+boxes);
			lgDeliveryOrderService.orderSplit(deliveryPk, boxes);
			result = RestCode.CODE_0000.toJson();
		} catch(ErrorParameterException parameter){
			result = RestCode.CODE_P000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("splitOrder exception",e);
			result = RestCode.CODE_S999.toJson();
		}
    	logger.info("splitOrder result:"+result);
    	return result;
    }
    
    
    /**
     * 指派车辆时，查询可选车辆
     */
    @RequestMapping(value="/getCars4Assign",method =RequestMethod.POST)
    public String getCars4Assign(HttpServletRequest request){
    	String result="";
    	try {
			Map<String, Object> map = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			map.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			List<LgCarDto>   dataList = lgCarService.getCars4Assign(map);
			result = RestCode.CODE_0000.toJson(dataList);
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error("getCarsByCompanyPk exception:", e);
			 result = RestCode.CODE_S999.toJson();
		}
    	logger.info("getCarsByCompanyPk result:"+result);
    	return result;
    }
    
    
    /**
     * 指派车辆时，查询可选司机
     * @param req
     * @return
     */
    @RequestMapping(value = "getDrivers4Assign", method = RequestMethod.POST)
    public String getDrivers4Assign(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            map.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
            List<LgDriverDto> list = lgDriverService.getDrivers4Assign(map);
            result = RestCode.CODE_0000.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getDrivers4Assign exception:", e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("getDrivers4Assign result:"+result);
    	return result;
    }
    
    /**
     * 待处理_指派车辆
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/assignVehicle",method = RequestMethod.POST)
    public String assignVehicle(HttpServletRequest request) {
    	String result="";
    	try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			String deliveryPks = par.get("deliveryPks").toString();
			String carPk = par.get("carPk").toString();
			String driverPk = par.get("driverPk").toString();
			if (null==deliveryPks||"".equals(deliveryPks)||null==carPk||"".equals(carPk)||null ==driverPk||"".equals(driverPk)) {
				return RestCode.CODE_A001.toJson();
			}
			logger.info("assignVehicle params:deliveryPks:"+deliveryPks+",carPk:"+carPk+",driverPk:"+driverPk);
			boolean flag = lgDeliveryOrderService.assignVehicle(par);
			if (!flag) {
				return RestCode.CODE_S999.toJson();
			}
			//??调ERP"派车状态”接口
			
			result = RestCode.CODE_0000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
    	logger.info("assignVehicle result:"+result);
    	return result;
    }

	/**
	 * 提货中_确认提货
	 * 
	 * @return
	 */
	@RequestMapping(value = "/confirmDelivery", method = RequestMethod.POST)
	public String confirmDelivery(HttpServletRequest request) {
		String result = "";
		try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk() == null ? "" : session.getCompanyPk());
			String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
			if (null==deliveryPk||"".equals(deliveryPk)) {
				return RestCode.CODE_S999.toJson();
			}
			logger.info("confirmDelivery param====deliveryPk:"+deliveryPk);
			// 检查订单的状态
			int orderStatus = lgDeliveryOrderService.checkOrderStatusByPk(deliveryPk);
			if (orderStatus != 5) {
				return RestCode.CODE_S999.toJson();
			}
			lgDeliveryOrderService.deliveryConfirmation(deliveryPk);
			result = RestCode.CODE_0000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("confirmDelivery exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		logger.info("confirmDelivery result:"+result);
		return result;
	}
	
	
    /**
     * 提货中_取消指派
     * @param request
     * @return
     */
    @RequestMapping(value="/cancelAssign",method=RequestMethod.POST)
    public String cancelAssign(HttpServletRequest request){
    	String result="";
    	try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
			if (null==deliveryPk||"".equals(deliveryPk)) {
				return RestCode.CODE_S999.toJson();
			}
			//检查订单的状态
			int orderStatus=lgDeliveryOrderService.checkOrderStatusByPk(deliveryPk);
			if (orderStatus!=5) {
				return RestCode.CODE_S999.toJson();
			}
			lgDeliveryOrderService.cancelAssign(deliveryPk);
			result = RestCode.CODE_0000.toJson(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("cancelAssign exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
    	logger.info("cancelAssign resull:"+result);
    	return result;
    }
    
    
	/**
	 * 配送中_确认送达
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/confirmReceived", method = RequestMethod.POST)
	public String confirmReceived(String orderNumber, Integer status, HttpServletRequest request) {
		String result = "";
		try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk() == null ? "" : session.getCompanyPk());
			String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
			if (null==deliveryPk||"".equals(deliveryPk)) {
				return RestCode.CODE_S999.toJson();
			}
			logger.info("confirmReceived param=====deliveryPk:"+deliveryPk);
			//订单状态: 10已关闭10 9待付款9 8待财务确认8 6财务已确认,待指派车辆6 5提货中5 4配送中4 3已签收3 2已取消2 1完成1
			// 检查订单的状态
			int orderStatus = lgDeliveryOrderService.checkOrderStatusByPk(deliveryPk);
			if (orderStatus != 4) {
				return RestCode.CODE_S999.toJson();
			}
			//承运商确认送达  状态为已签收
			lgDeliveryOrderService.confirmReceived(deliveryPk);
			// 如果该订单包括子订单已全部签收，调商城接口，确认已经签收
			LgDeliveryOrderDto lgDto = lgDeliveryOrderService.getBypk(deliveryPk);
			//Source订单来源: 0:物流系统,1:电商商家承运，2:电商平台承运，3:erp系统
			if (lgDto.getSource() == 1) {
				// 根据orderPk查询所有订单（包括子订单）的订单状态
				List<Integer> statusList = lgDeliveryOrderService.getAllStatusByOrderPk(lgDto.getOrderPk());
				boolean flag = true;
				for (Integer integer : statusList) {
					if (integer > 3) {
						flag = false;
					}
				}
				if (flag) {
					// ??调商城同步订单状态接口

					// ??调ERP同步订单状态接口

				}
			}
			result = RestCode.CODE_0000.toJson(null);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		logger.info("confirmDelivery result========="+result);
		return result;
	}
	
	
	/**
	 * 异常反馈_订单详情
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/getAbDeliveryDetail",method=RequestMethod.POST)  
    public String getAbDeliveryDetail(HttpServletRequest request){
    	String result="";
    	try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
			String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
			if (null==deliveryPk||"".equals(deliveryPk)) {
				result = RestCode.CODE_S999.toJson();
			}
			logger.info("getAbDeliveryDetail param===deliveryPk:"+deliveryPk);
			LgDeliveryOrderDto dto = lgDeliveryOrderService.getBypk(deliveryPk);
			result = RestCode.CODE_0000.toJson(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getAbDeliveryDetail exception:", e);
    		result = RestCode.CODE_S999.toJson();
		}
    	logger.info("getAbDeliveryDetail result:"+result);
    	return result;
    }
    
	/**
	 * 派送中——异常反馈
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/abnormalFeedback", method = RequestMethod.POST)
	public String abnormalFeedback(HttpServletRequest request) {
		String result="";
		try {
			Map<String, Object> par = this.paramsToMap(request);
			Sessions session = this.getSessions(request);
			par.put("companyPk", session.getCompanyPk() == null ? "" : session.getCompanyPk());
			String deliveryPk = par.get("deliveryPk").toString();
			String picUrls = par.get("picUrls").toString();
			String abnormalRemark = par.get("remark").toString();
			if (null==deliveryPk||"".equals(deliveryPk)||null==picUrls||"".equals(picUrls)||null==abnormalRemark||"".equals(abnormalRemark)) {
				return RestCode.CODE_A007.toJson();
			}
			lgDeliveryOrderService.abnormalFeedback(par);
			result = RestCode.CODE_0000.toJson();
		} catch (ErrorParameterException par) {
			result = RestCode.CODE_P000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("abnormalFeedback exception:",e);
			result = RestCode.CODE_S999.toJson();
		}
		logger.info("abnormalFeedback result:"+result);
		return result;
	}
	
	
	
    /**
     * 打印提货单（PDF文档）
     */
    @RequestMapping(value ="/downloadDeliveryPDF",method=RequestMethod.POST)   
    public String downloadDeliveryPDF(HttpServletResponse response, HttpServletRequest request,String deliveryPk){
		response.setHeader("content-Type", "application/pdf");
		String fileName = KeyUtils.getOrderNumber() + ".pdf";
		try {
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("deliveryPk", deliveryPk);
			OrderDeliveryDto vo = lgDeliveryOrderService.selectDetailByDeliveryPk(map);
			if (vo == null) {
				vo = new OrderDeliveryDto();
			}
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			// 使用itext-asian.jar中的字体
			BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			Font normalFont = new Font(baseFont, 10.5f, Font.NORMAL);
			Font boldFont = new Font(baseFont, 10.5f, Font.BOLD);
			Font titleFont = new Font(baseFont, 15.5f, Font.BOLD);
			document.open();
			InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("logo.png");
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = resourceAsStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in2b = swapStream.toByteArray();
			//1：客户联
			PdfPTable tableKeHu1 = new PdfPTable(2);
			tableKeHu1.setTotalWidth(500);// 设置表格的总宽度
			tableKeHu1.setTotalWidth(new float[] { 150, 350 });// 设置表格的各列宽度 ， 使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableKeHu1.setLockedWidth(true);
			Image img = Image.getInstance(in2b);
			resourceAsStream.close();
			swapStream.close();
			img.scaleAbsolute(90.0f, 60.0f);
			PdfPCell tempCell = new PdfPCell();
			tempCell.addElement(img);
			tempCell.setFixedHeight(40);
			tempCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tableKeHu1.addCell(tempCell);
			tableKeHu1.addCell(getCell("化纤汇提/送货货单（客户联）", titleFont, 1, 1, null));
			PdfPTable tableKeHu2 = new PdfPTable(7);
			tableKeHu2.setTotalWidth(500);// 设置表格的总宽度
			tableKeHu2.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度 ， 使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableKeHu2.setLockedWidth(true);
			tableKeHu2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableKeHu2.addCell(getCell("合同号", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(vo.getOrderPK(), normalFont, 2, 4, null));
			tableKeHu2.addCell(getCell("订单号", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(vo.getOrderPK(), normalFont, 3, 4, null));
			tableKeHu2.addCell(getCell("提货地址", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(vo.getFromProvinceName() + vo.getFromCityName() + vo.getFromAreaName()+ vo.getFromTownName() + vo.getFromAddress(), normalFont, 2, 4, null));
			tableKeHu2.addCell(getCell("联系人", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(vo.getFromContacts(), normalFont, 3, 4, null));
			tableKeHu2.addCell(getCell("提货工厂", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(vo.getFromCompanyName(), normalFont, 2, 4, null));
			tableKeHu2.addCell(getCell("联系方式", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(vo.getFromContactsTel(), normalFont, 3, 4, null));
			tableKeHu2.addCell(getCell("发货仓库码头号", normalFont, 2, 3, null));
			tableKeHu2.addCell(getCell(null, normalFont, 2, 4, null));
			tableKeHu2.addCell(getCell("提货时间", normalFont, 1, 3, null));
			tableKeHu2.addCell(getCell(formatDate(vo.getDeliveryTime(), "yyyy-MM-dd HH:mm"), normalFont, 3, 4, null));
			tableKeHu2.addCell(getCell("商品明细", boldFont, 7, 1, null));
			tableKeHu2.addCell(getCell("", normalFont, 6, 4, null));
			PdfPTable tableKeHu3 = new PdfPTable(7);
			tableKeHu3.setTotalWidth(500);// 设置表格的总宽度
			tableKeHu3.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度, 使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableKeHu3.setLockedWidth(true);
			tableKeHu3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableKeHu3.addCell(getCell2("名称", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2("规格", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2("批号", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2("等级", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2("件数", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2("重量", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2("过磅重", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2(vo.getProductName() == null ? "" : vo.getProductName(), normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2((vo.getSpecName()==null?"":vo.getSpecName())+"("+(vo.getSeriesName()==null?"":vo.getSeriesName())+")", normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2(vo.getBatchNumber() == null ? "" : vo.getBatchNumber(), normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2(vo.getGradeName() == null ? "" : vo.getGradeName(), normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2(vo.getBoxes() == null ? "" : vo.getBoxes().toString(), normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2(vo.getWeight() == null ? "" : new BigDecimal(vo.getWeight().toString()).toPlainString(), normalFont, 1, true, null));
			tableKeHu3.addCell(getCell2(" ", normalFont, 1, true, null));
			PdfPTable tableKeHu4 = new PdfPTable(7);
			tableKeHu4.setTotalWidth(500);// 设置表格的总宽度
			tableKeHu4.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75});// 设置表格的各列宽度 ,使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableKeHu4.setLockedWidth(true);
			tableKeHu4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableKeHu4.addCell(getCell("发货人签名", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell("", normalFont, 3, 1, null));
			tableKeHu4.addCell(getCell("司机签名", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell("", normalFont, 2, 1, null));
			tableKeHu4.addCell(getCell("送货信息", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell("", normalFont, 6, 3, null));
			tableKeHu4.addCell(getCell("送货地址", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell(vo.getToProvinceName() + vo.getToCityName() + vo.getToAreaName() + vo.getToTownName()+ vo.getToAddress(), normalFont, 2, 4, null));
			tableKeHu4.addCell(getCell("联系人", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell(vo.getToContacts(), normalFont, 3, 4, null));
			tableKeHu4.addCell(getCell("送货客户名字", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell(vo.getFromContacts(), normalFont, 2, 4, null));
			tableKeHu4.addCell(getCell("联系方式", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell(vo.getToContactsTel(), normalFont, 3, 4, null));
			tableKeHu4.addCell(getCell("卸货仓库码头", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell("", normalFont, 2, 4, null));
			tableKeHu4.addCell(getCell("送货要求时间", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell(formatDate(vo.getArrivedTimeStart(), "yyyy-MM-dd HH:mm") + " ~ "+ formatDate(vo.getArrivedTimeEnd(), "yyyy-MM-dd HH:mm"), normalFont, 3, 4, null));
			tableKeHu4.addCell(getCell("送货要求", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell("", normalFont, 4, 1, null));
			tableKeHu4.addCell(getCell("收货人签名", normalFont, 1, 3, null));
			tableKeHu4.addCell(getCell("", normalFont, 1, 1, null));
			document.add(tableKeHu1);
			document.add(tableKeHu2);
			document.add(tableKeHu3);
			document.add(tableKeHu4);
			
			//2：司机联
			PdfPTable tableSiJi1 = new PdfPTable(2);
			tableSiJi1.setSpacingBefore(10);// 设置表头间距
			tableSiJi1.setTotalWidth(500);// 设置表格的总宽度
			tableSiJi1.setTotalWidth(new float[] { 150, 350 });// 设置表格的各列宽度 ， 使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableSiJi1.setLockedWidth(true);
			resourceAsStream.close();
			swapStream.close();
			tableSiJi1.addCell(tempCell);
			tableSiJi1.addCell(getCell("化纤汇提/送货货单（司机联）", titleFont, 1, 1, null));
			PdfPTable tableSiJi2 = new PdfPTable(7);
			tableSiJi2.setTotalWidth(500);// 设置表格的总宽度
			tableSiJi2.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableSiJi2.setLockedWidth(true);
			tableSiJi2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableSiJi2.addCell(getCell("合同号", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(vo.getOrderPK(), normalFont, 2, 4, null));
			tableSiJi2.addCell(getCell("订单号", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(vo.getOrderPK(), normalFont, 3, 4, null));
			tableSiJi2.addCell(getCell("提货地址", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(vo.getFromProvinceName() + vo.getFromCityName() + vo.getFromAreaName()
					+ vo.getFromTownName() + vo.getFromAddress(), normalFont, 2, 4, null));
			tableSiJi2.addCell(getCell("联系人", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(vo.getFromContacts(), normalFont, 3, 4, null));
			tableSiJi2.addCell(getCell("提货工厂", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(vo.getFromCompanyName(), normalFont, 2, 4, null));
			tableSiJi2.addCell(getCell("联系方式", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(vo.getFromContactsTel(), normalFont, 3, 4, null));
			tableSiJi2.addCell(getCell("发货仓库码头号", normalFont, 2, 3, null));
			tableSiJi2.addCell(getCell(null, normalFont, 2, 1, null));
			tableSiJi2.addCell(getCell("提货时间", normalFont, 1, 3, null));
			tableSiJi2.addCell(getCell(formatDate(vo.getDeliveryTime(), "yyyy-MM-dd HH:mm"), normalFont, 3, 4, null));
			tableSiJi2.addCell(getCell("商品明细", boldFont, 7, 1, null));
			tableSiJi2.addCell(getCell("", normalFont, 6, 1, null));
			PdfPTable tableSiJi3 = new PdfPTable(7);
			tableSiJi3.setTotalWidth(500);// 设置表格的总宽度
			tableSiJi3.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度,使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableSiJi3.setLockedWidth(true);
			tableSiJi3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableSiJi3.addCell(getCell2("名称", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2("规格", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2("批号", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2("等级", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2("件数", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2("重量", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2("过磅重", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2(vo.getProductName() == null ? "" : vo.getProductName(), normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2((vo.getSpecName()==null?"":vo.getSpecName())+"("+(vo.getSeriesName()==null?"":vo.getSeriesName())+")", normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2(vo.getBatchNumber() == null ? "" : vo.getBatchNumber(), normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2(vo.getGradeName() == null ? "" : vo.getGradeName(), normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2(vo.getBoxes() == null ? "" : vo.getBoxes().toString(), normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2(vo.getWeight() == null ? "" : new BigDecimal(vo.getWeight().toString()).toPlainString(), normalFont, 1, true, null));
			tableSiJi3.addCell(getCell2(" ", normalFont, 1, true, null));
			PdfPTable tableSiJi4 = new PdfPTable(7);
			tableSiJi4.setTotalWidth(500);// 设置表格的总宽度
			tableSiJi4.setTotalWidth(new float[] {70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度, 使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableSiJi4.setLockedWidth(true);
			tableSiJi4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableSiJi4.addCell(getCell("发货人签名", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell("", normalFont, 3, 4, null));
			tableSiJi4.addCell(getCell("司机签名", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell("", normalFont, 2, 4, null));
			tableSiJi4.addCell(getCell("送货信息", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell("", normalFont, 6, 4, null));
			tableSiJi4.addCell(getCell("送货地址", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell(vo.getToProvinceName() + vo.getToCityName() + vo.getToAreaName() + vo.getToTownName()
					+ vo.getToAddress(), normalFont, 2, 4, null));
			tableSiJi4.addCell(getCell("联系人", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell(vo.getToContacts(), normalFont, 3, 4, null));
			tableSiJi4.addCell(getCell("送货客户名字", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell(vo.getFromContacts(), normalFont, 2, 4, null));
			tableSiJi4.addCell(getCell("联系方式", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell(vo.getToContactsTel(), normalFont, 3, 4, null));
			tableSiJi4.addCell(getCell("卸货仓库码头", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell("", normalFont, 2, 1, null));
			tableSiJi4.addCell(getCell("送货要求时间", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell(formatDate(vo.getArrivedTimeStart(), "yyyy-MM-dd HH:mm:ss") + " ~ "
					+ formatDate(vo.getArrivedTimeEnd(), "yyyy-MM-dd HH:mm:ss"), normalFont, 3, 4, null));
			tableSiJi4.addCell(getCell("送货要求", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell("", normalFont, 4, 1, null));
			tableSiJi4.addCell(getCell("收货人签名", normalFont, 1, 3, null));
			tableSiJi4.addCell(getCell("", normalFont, 1, 1, null));
			document.newPage();
			document.add(tableSiJi1);
			document.add(tableSiJi2);
			document.add(tableSiJi3);
			document.add(tableSiJi4);
			
			//3：存联
			PdfPTable tableCun1 = new PdfPTable(2);
			tableCun1.setSpacingBefore(3);// 设置表头间距
			tableCun1.setTotalWidth(500);// 设置表格的总宽度
			tableCun1.setTotalWidth(new float[] { 150, 350 });// 设置表格的各列宽度 ， 使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableCun1.setLockedWidth(true);
			resourceAsStream.close();
			swapStream.close();
			tableCun1.addCell(tempCell);
			tableCun1.addCell(getCell("化纤汇提/送货货单（存联）", titleFont, 1, 1, null));
			PdfPTable tableCun2 = new PdfPTable(7);
			tableCun2.setTotalWidth(500);// 设置表格的总宽度
			tableCun2.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度,使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableCun2.setLockedWidth(true);
			tableCun2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableCun2.addCell(getCell("合同号", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(vo.getOrderPK(), normalFont, 2, 4, null));
			tableCun2.addCell(getCell("订单号", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(vo.getOrderPK(), normalFont, 3, 4, null));
			tableCun2.addCell(getCell("提货地址", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(vo.getFromProvinceName() + vo.getFromCityName() + vo.getFromAreaName()
					+ vo.getFromTownName() + vo.getFromAddress(), normalFont, 2, 4, null));
			tableCun2.addCell(getCell("联系人", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(vo.getFromContacts(), normalFont, 3, 4, null));
			tableCun2.addCell(getCell("提货工厂", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(vo.getFromCompanyName(), normalFont, 2, 4, null));
			tableCun2.addCell(getCell("联系方式", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(vo.getFromContactsTel(), normalFont, 3, 4, null));
			tableCun2.addCell(getCell("发货仓库码头号", normalFont, 2, 3, null));
			tableCun2.addCell(getCell(null, normalFont, 2, 4, null));
			tableCun2.addCell(getCell("提货时间", normalFont, 1, 3, null));
			tableCun2.addCell(getCell(formatDate(vo.getDeliveryTime(), "yyyy-MM-dd HH:mm"), normalFont, 3, 4, null));
			tableCun2.addCell(getCell("商品明细", boldFont, 7, 1, null));
			tableCun2.addCell(getCell("", normalFont, 6, 4, null));
			PdfPTable tableCun3 = new PdfPTable(7);
			tableCun3.setTotalWidth(500);// 设置表格的总宽度
			tableCun3.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度，使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableCun3.setLockedWidth(true);
			tableCun3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableCun3.addCell(getCell2("名称", normalFont, 1, true, null));
			tableCun3.addCell(getCell2("规格", normalFont, 1, true, null));
			tableCun3.addCell(getCell2("批号", normalFont, 1, true, null));
			tableCun3.addCell(getCell2("等级", normalFont, 1, true, null));
			tableCun3.addCell(getCell2("件数", normalFont, 1, true, null));
			tableCun3.addCell(getCell2("重量", normalFont, 1, true, null));
			tableCun3.addCell(getCell2("过磅重", normalFont, 1, true, null));
			tableCun3.addCell(getCell2(vo.getProductName() == null ? "" : vo.getProductName(), normalFont, 1, true, null));
			tableCun3.addCell(getCell2((vo.getSpecName()==null?"":vo.getSpecName())+"("+(vo.getSeriesName()==null?"":vo.getSeriesName())+")", normalFont, 1, true, null));
			tableCun3.addCell(getCell2(vo.getBatchNumber() == null ? "" : vo.getBatchNumber(), normalFont, 1, true, null));
			tableCun3.addCell(getCell2(vo.getGradeName() == null ? "" : vo.getGradeName(), normalFont, 1, true, null));
			tableCun3.addCell(getCell2(vo.getBoxes() == null ? "" : vo.getBoxes().toString(), normalFont, 1, true, null));
			tableCun3.addCell(getCell2(vo.getWeight() == null ? "" : new BigDecimal(vo.getWeight().toString()).toPlainString(), normalFont, 1, true, null));
			tableCun3.addCell(getCell2(" ", normalFont, 1, true, null));
			PdfPTable tableCun4 = new PdfPTable(7);
			tableCun4.setTotalWidth(500);// 设置表格的总宽度
			tableCun4.setTotalWidth(new float[] { 70, 80, 70, 70, 60, 75, 75 });// 设置表格的各列宽度,使用以上两个函数，必须使用以下函数，将宽度锁定。
			tableCun4.setLockedWidth(true);
			tableCun4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableCun4.addCell(getCell("发货人签名", normalFont, 1, 3, null));
			tableCun4.addCell(getCell("", normalFont, 3, 4, null));
			tableCun4.addCell(getCell("司机签名", normalFont, 1, 3, null));
			tableCun4.addCell(getCell("", normalFont, 2, 4, null));
			tableCun4.addCell(getCell("送货信息", normalFont, 1, 3, null));
			tableCun4.addCell(getCell("", normalFont, 6, 4, null));
			tableCun4.addCell(getCell("送货地址", normalFont, 1, 3, null));
			tableCun4.addCell(getCell(vo.getToProvinceName() + vo.getToCityName() + vo.getToAreaName() + vo.getToTownName()
					+ vo.getToAddress(), normalFont, 2, 4, null));
			tableCun4.addCell(getCell("联系人", normalFont, 1, 3, null));
			tableCun4.addCell(getCell(vo.getToContacts(), normalFont, 3, 4, null));
			tableCun4.addCell(getCell("送货客户名字", normalFont, 1, 3, null));
			tableCun4.addCell(getCell(vo.getFromContacts(), normalFont, 2, 4, null));
			tableCun4.addCell(getCell("联系方式", normalFont, 1, 3, null));
			tableCun4.addCell(getCell(vo.getToContactsTel(), normalFont, 3, 4, null));
			tableCun4.addCell(getCell("卸货仓库码头", normalFont, 1, 3, null));
			tableCun4.addCell(getCell("", normalFont, 2, 1, null));
			tableCun4.addCell(getCell("送货要求时间", normalFont, 1, 3, null));
			tableCun4.addCell(getCell(formatDate(vo.getArrivedTimeStart(), "yyyy-MM-dd HH:mm:ss") + " ~ "
					+ formatDate(vo.getArrivedTimeEnd(), "yyyy-MM-dd HH:mm:ss"), normalFont, 3, 4, null));
			tableCun4.addCell(getCell("送货要求", normalFont, 1, 3, null));
			tableCun4.addCell(getCell("", normalFont, 4, 1, null));
			tableCun4.addCell(getCell("收货人签名", normalFont, 1, 3, null));
			tableCun4.addCell(getCell("", normalFont, 1, 1, null));
			document.newPage();
			document.add(tableCun1);
			document.add(tableCun2);
			document.add(tableCun3);
			document.add(tableCun4);

			// 自提委托书
		/*	if (null != vo.getMandateUrl() && !"".equals(vo.getMandateUrl())) {
				String[] mandateUrls = vo.getMandateUrl().split(",");
				for (String string : mandateUrls) {
					try {
						Image imgTemp = Image.getInstance(string);
						imgTemp.setAlignment(Image.MIDDLE);
						imgTemp.scaleToFit(500, 600);// 大小
						document.add(imgTemp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}*/
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }
	
	
	
	
	
	
	
    
	public int getPercent2(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		p2 = 530 / w * 100;
		System.out.println("--" + p2);
		p = Math.round(p2);
		return p;

	}
    
    
    /**
     * 承运商开票订单列表
     *1:sessionId
     *2:订单编号
     *3：下单时间，开始
     *4：下单时间，结束
     *5：订单状态
     *6：开票状态
     *
     *返回数据：全部数量，进行中数量，未开票数量，已开票数量
     * @param req
     * @return
     */
    @RequestMapping(value = "supplierBillOrderList", method = RequestMethod.POST)
    public String supplierBillOrderList(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            //承运商主键
            map.put("purAndSup", session.getCompanyPk()==null?"":session.getCompanyPk());
            //订单编号
            map.put("orderPk",ServletUtils.getStringParameter(req, "orderPk",""));
            //开始时间
            map.put("orderTimeStart", ServletUtils.getStringParameter(req, "orderTimeStart",""));
            //结束时间
            map.put("orderTimeEnd", ServletUtils.getStringParameter(req, "orderTimeEnd",""));
            //订单状态
            map.put("orderStatus", ServletUtils.getStringParameter(req, "orderStatus"));
            //物流供应商开票状态
            map.put("supplierInvoiceStatus", ServletUtils.getStringParameter(req, "supplierInvoiceStatus"));
            //分页参数
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            PageModelOrderVo<OrderDeliveryDto> orderDeliveryDtoPageModelOrderVo = lgDeliveryOrderService.supplierBillsDeliveryOrder(map);
            result = RestCode.CODE_0000.toJson(orderDeliveryDtoPageModelOrderVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("supplierBillsDO exception:", e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("supplierBillsDO result:"+result);
    	return result;
    }
    
    
    
    /**
     * 承运商开票管理页面导出订单
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "exportSupplierBillOrderList", method = RequestMethod.POST)
    public void exportSupplierBillOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sessions session = this.getSessions(request);
        Map<String, Object> map = this.paramsToMap(request);
        map.put("companyPk", session.getCompanyPk()==null?"":session.getCompanyPk());
        //订单编号
        map.put("orderPk",ServletUtils.getStringParameter(request, "orderPk",""));
        //开始时间
        map.put("orderTimeStart", ServletUtils.getStringParameter(request, "orderTimeStart",""));
        //结束时间
        map.put("orderTimeEnd", ServletUtils.getStringParameter(request, "orderTimeEnd",""));
        //订单状态
        map.put("orderStatus", ServletUtils.getStringParameter(request, "orderStatus"));
        //物流供应商开票状态
        map.put("supplierInvoiceStatus", ServletUtils.getStringParameter(request, "supplierInvoiceStatus"));
        List<OrderDeliveryDto> list = lgDeliveryOrderService.exportInvoiceDeliveryOrder(map);
        //String templateFileName = CommonUtil.getPath() + "SupplierDeliveryOrder.xls";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("SupplierDeliveryOrder.xls");
        String path = PropertyConfig.getProperty("excel_url");
        File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
        String fileName = "/order" + KeyUtils.getUUID() + ".xls";
        String destFileName = path + fileName;
        File file = new File(destFileName);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> beans = new HashMap<>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
  		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName));
  		workbook.write(os);
  		os.flush();
  		os.close();
  		download(response, request, destFileName);
    }
    
    
    /**
     * 承运商开票操作
     * @param req
     * @return
     */
    @RequestMapping(value = "supplierBillOrders", method = RequestMethod.POST)
    public String supplierBillOrders(HttpServletRequest req) {
    	String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            map.put("purAndSup", session.getCompanyPk()==null?"":session.getCompanyPk());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    		String month =sdf.format(new Date())+map.get("month").toString();
    	    //参数：月份month，发票金额billAccount，订单数量orderCount
    	    String pks = map.get("pks").toString();
    	    if (null==month||"".equals(month)||null==map.get("orderCount").toString()||"".equals(map.get("orderCount").toString())
    	    		||null==pks||"".equals(pks)) {
    	    	return RestCode.CODE_S999.toJson();
			}
    	    //所有开票的订单中，验证是否存在已经开过票的
    	    String[] strings = cn.cf.common.utils.StringUtils.splitStrs(pks);
    	    for (String pk : strings) {
    	    	LgDeliveryOrderDto dto = lgDeliveryOrderService.getBypk(pk);
    	    	if (dto.getSupplierInvoiceStatus()==2||dto.getSupplierInvoiceStatus()==3) {
    	    		 return RestCode.CODE_S999.toJson();
				}
			}
    	    boolean b=lgDeliveryOrderService.supplierBillOrders(map);
    	    result = RestCode.CODE_0000.toJson(b);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("supplierBillOrders excepetion:", e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("supplierBillOrders result:"+result);
    	return result;
    }
    
    
    
    /**
     * 物流供应商收入明细
     *1:sessionId
     *2:订单编号
     *3：下单时间，开始
     *4：下单时间，结束
     *5: 订单状态
     *6: 最后更新时间，开始
     *7: 最后更新时间，结束
     * @param req
     * @return
     */
    @RequestMapping(value = "supplierIncomeDetail", method = RequestMethod.POST)
    public String supplierIncomeDetail(HttpServletRequest req) {
        try {
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            //承运商主键
            map.put("purAndSup", session.getCompanyPk()==null?"":session.getCompanyPk());
            //订单编号
            map.put("orderPk",ServletUtils.getStringParameter(req, "orderPk",""));
            //下单时间，开始
            map.put("orderTimeStart", ServletUtils.getStringParameter(req, "orderTimeStart",""));
            //下单时间，结束
            map.put("orderTimeEnd", ServletUtils.getStringParameter(req, "orderTimeEnd",""));
            //最后更新时间，开始
            map.put("updateTimeStart", ServletUtils.getStringParameter(req, "updateTimeStart",""));
            //最后更新时间，结束
            map.put("updateTimeEnd", ServletUtils.getStringParameter(req, "updateTimeEnd",""));
            //订单状态
            map.put("orderStatus", ServletUtils.getStringParameter(req, "orderStatus"));
            //分页参数
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            PageModelOrderVo<OrderDeliveryDto> orderDeliveryDtoPageModelOrderVo = lgDeliveryOrderService.supplierIncomeDetail(map);
            return RestCode.CODE_0000.toJson(orderDeliveryDtoPageModelOrderVo);
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }
    
    /**
     * 供应商收入明细导出
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierIncomeDetailExport", method = RequestMethod.POST)
    public void supplierIncomeDetailExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sessions session = this.getSessions(request);
        Map<String, Object> map = this.paramsToMap(request);
        //承运商主键
        map.put("purAndSup", session.getCompanyPk()==null?"":session.getCompanyPk());
        //订单编号
        map.put("orderPk",ServletUtils.getStringParameter(request, "orderPk",""));
        //下单时间，开始
        map.put("orderTimeStart", ServletUtils.getStringParameter(request, "orderTimeStart",""));
        //下单时间，结束
        map.put("orderTimeEnd", ServletUtils.getStringParameter(request, "orderTimeEnd",""));
        //最后更新时间，开始
        map.put("updateTimeStart", ServletUtils.getStringParameter(request, "updateTimeStart",""));
        //最后更新时间，结束
        map.put("updateTimeEnd", ServletUtils.getStringParameter(request, "updateTimeEnd",""));
        //订单状态
        map.put("orderStatus", ServletUtils.getStringParameter(request, "orderStatus"));
        List<OrderDeliveryDto> list = lgDeliveryOrderService.supplierIncomeDetailExport(map);
        //String templateFileName = CommonUtil.getPath() + "SupplierIncomeDetailExport.xls";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("SupplierIncomeDetailExport.xls");
        String path = PropertyConfig.getProperty("excel_url");
        File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
        String fileName = "/order" + KeyUtils.getUUID() + ".xls";
        String destFileName = path + fileName;
        File file = new File(destFileName);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> beans = new HashMap<>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
  		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName));
  		workbook.write(os);
  		os.flush();
  		os.close();
  		download(response, request, destFileName);
    
        
    }
    
    
    
    
    /**
     * 
     * @param value
     * @param font
     * @param colspan
     * @param alginCenter  1：水平居中，垂直居中；2：水平居中，垂直居底；3：水平居右，垂直居中；4：水平居左，垂直居中
     * @param rowspan
     * @return
     */
	public PdfPCell getCell(String value, Font font, Integer colspan,int alginCenter, Integer rowspan) {
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph(value, font);
		cell.setPhrase(p);
		cell.setColspan(colspan);
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		if (rowspan != null) {
			cell.setRowspan(rowspan);
		}
		if (alginCenter==1) {//水平居中，垂直居中
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		}
		if (alginCenter==2) {//水平居中，垂直居底
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		}
		if (alginCenter==3) {//水平居右，垂直居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		}
		if (alginCenter==4) {//水平居左，垂直居中
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		}
		cell.setMinimumHeight(30);
		return cell;
	}
	
	public PdfPCell getCell2(String value, Font font, Integer colspan,
			boolean alginCenter, Integer rowspan) {
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph(value, font);
		cell.setPhrase(p);
		cell.setColspan(colspan);
		//font.setSize(6);
		if (rowspan != null) {
			cell.setRowspan(rowspan);
		}
		if (alginCenter) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
		}
		cell.setMinimumHeight(25);
		return cell;
	}
	
	public String formatDate(Date date,String format){
		if(date==null){
			return "";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format); 
		String str =sdf.format(date);
		return str;
	}
    
	public void download(HttpServletResponse response, HttpServletRequest request,String filePath){
		File file = new File(filePath);
		InputStream inputStream = null;
		OutputStream outputStream = null;
		byte[] b = new byte[1024];
		int len = 0;
		try {
			inputStream = new FileInputStream(file);
			outputStream = response.getOutputStream();
			response.setContentType("application/force-download");
			String filename = "订单报表.xls";
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentLength((int) file.length());
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		
	}
	
	
}