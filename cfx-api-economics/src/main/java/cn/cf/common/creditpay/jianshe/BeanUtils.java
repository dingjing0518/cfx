package cn.cf.common.creditpay.jianshe;

import java.util.Date;

import cn.cf.common.creditpay.jianshe.IFSPQRCodeInfoGetBean.Request;
import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;

import com.ccb.sdk.bean.SDKRequestHead;

public class BeanUtils {

	
	public IFSPQRCodeInfoGetBean getQrCode(String orderNumber,String userId,String mobile,String cardType,String cardNumber,
			String name,String customerNumber,String failTime,String amount,String purchaseName,String creditNumber,
			String supplierName,String bankAccount,String bankCode,String bankNo,String organizationCode){
		IFSPQRCodeInfoGetBean bean = new IFSPQRCodeInfoGetBean();
		// 设置请求报文head的信息
		SDKRequestHead head = bean.getRequestHead();
		// 增加请求流水号
		head.setRqs_Jrnl_No(String.valueOf(System.currentTimeMillis()));
		// 封装业务数据
		IFSPQRCodeInfoGetBean.Request request = (Request) bean.getReq();
		// 设置请求参数
		request.setTxn_Chnl_ID("500010");
		request.setTpl_Vno("1.0");
        request.setChnl_TpCd("5000");
        request.setTermTp("20");
	    request.setTxn_Cd("IFSPQRCodeInfoGet");
	    //业务参数
	    request.setOrder_Code(orderNumber);//订单编号
	    request.setUsr_ID(userId);//用户Id
	    request.setAply_Psn_Ph_No(mobile);//实控人手机号
	    request.setAply_Psn_Crdt_TpCd(cardType);//实控人证件类型
	    request.setAply_Psn_Crdt_No(cardNumber);//实控人证件号码
	    request.setAply_Psn_Nm(name);//实控人姓名
	    request.setLoan_AccNo(customerNumber);//主承诺账号
	    request.setOrder_Fail_Date(failTime);//订单失效时间
	    request.setOrder_Date(DateUtil.formatDateAndTime(new Date()));//订单时间
	    request.setExpdtr_Amt(amount);//支用金额
	    request.setAply_Ent_Nm(purchaseName);//付款人姓名（企业名称）
	    request.setCrg_TLmt_ID(creditNumber);//授信编号
	    request.setRcvpymtps_Accnm(supplierName);//收款人户名
	    request.setRppdbnk_Accno(bankAccount);//收款人开户行账号 
	    request.setAccno_Bkcgycd(bankCode);//收款人账号行别代码 1：建行 2：他行
	    request.setRppdbnk_Py_Bnkcd(bankNo);//收款人开户行支付联行号(收款行为他行时必填)
	    request.setChnl_ID(PropertyConfig.getProperty("js_channel_no"));//渠道编号
//	    request.setChnl_ID("950");//渠道编号
	    request.setUnn_Soc_Cr_Cd(organizationCode);//组织机构代码
		return bean;
	}
	
	public IFSPOrderResultBean getOrderResult(String orderNumber){
		IFSPOrderResultBean bean = new IFSPOrderResultBean();
		// 修改请求报文head的信息
				SDKRequestHead head = bean.getRequestHead();
				// 增加请求流水号
				head.setRqs_Jrnl_No(String.valueOf(System.currentTimeMillis()));
				// 封装业务数据
				IFSPOrderResultBean.Request request = (cn.cf.common.creditpay.jianshe.IFSPOrderResultBean.Request) bean.getReq();
				// 设置请求参数
				request.setTxn_Chnl_ID("500010");
				request.setTpl_Vno("1.0");
				request.setTermTp("10");
		        request.setChnl_TpCd("5000");  
			    request.setTxn_Cd("IFSPOrderResult");
			    request.setChnl_ID(PropertyConfig.getProperty("js_channel_no"));
//			    request.setChnl_ID("950");
			    request.setOrder_Code(orderNumber);
		return bean;
		
	}
}
