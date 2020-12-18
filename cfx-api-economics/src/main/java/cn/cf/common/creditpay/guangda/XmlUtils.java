package cn.cf.common.creditpay.guangda;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

public class XmlUtils {
	/**
	 * 报文头部
	 * @param code
	 * @return
	 */
	private String getHead(String code){
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><In><Head>"+
        "<ChnlNo>303</ChnlNo>"+
        "<FTranCode>"+code+"</FTranCode>"+
        "<InstID>03030002</InstID>"+
        "<TrmSeqNum>"+KeyUtils.getFlowNumber()+"</TrmSeqNum>"+
        "<TranDateTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</TranDateTime>"+
        "<ErrCode></ErrCode>"+
        "</Head>";
		return xml;
	}
	
	public  String getXml(String xml){
		if(null == xml || "".equals(xml)){
			return "000000";
		}
		String xmlLength = xml.length()+"";
		int length = xmlLength.length();
		for (int i = 0; i < 6-length; i++) {
			xmlLength = "0"+xmlLength;
		}
		return xmlLength+xml;
	}
	
	public JSONObject getJsonByXml(String xml) throws JSONException{
		JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(xml.substring(6, xml.length()-16));
		} catch (JSONException e) {
			e.printStackTrace();
		} 
       return xmlJSONObj.getJSONObject("Out");
	}
	
	/**
	 * 申请工作密钥 9000
	 * @return
	 */
	public String guangda9000(){
		StringBuffer sb = new StringBuffer();
		sb.append(getHead("9000"));
		sb.append("<Body>");
		sb.append("<operationDate>"+DateUtil.formatYearMonthDayTwo(new Date())+"</operationDate>");
		sb.append("</Body></In>");
		return getXml(sb.toString());
	}
	/**
	 * 额度查询 G337
	 * @param customerNo
	 * @param customerName
	 * @param organizationCode
	 * @param macCode
	 * @return
	 */
	public String guangdaG337(String companyName,String organizationCode){
		StringBuffer sb = new StringBuffer();
		sb.append(getHead("G337"));
		sb.append("<Body>");
		sb.append("<CHANNEL_CODE>03030002</CHANNEL_CODE>");//接入机构号
		sb.append("<SELLER_NAME>"+companyName+"</SELLER_NAME>");//客户名称
		sb.append("<SELLER_CODE>"+organizationCode+"</SELLER_CODE>");//社会信用代码
		sb.append("<EDLEIX>1</EDLEIX>");//额度类型1 综合授信 3 单笔单批批复
		sb.append("<EDZTDM>01</EDZTDM>");//额度状态 01-正常 03-解冻
		sb.append("<BUSI_ClASS>37</BUSI_ClASS>");//37-网贷易
		sb.append("</Body></In>");
//		String macCode = MacUtils.toMAC(sb.toString());
//		sb.append(macCode);
		return sb.toString();
	}
	/**
	 * 放款进度查询 G330
	 * @param orderNumber
	 * @param customerName
	 * @param organizationCode
	 * @param macCode
	 * @return
	 */
	public String guangdaG330(String orderNumber,String purchaserName,String organizationCode){
		StringBuffer sb = new StringBuffer();
		sb.append(getHead("G330"));
		sb.append("<Body>");
		sb.append("<CHANNEL_CODE>03030002</CHANNEL_CODE>");//接入机构号
		sb.append("<ORDER_NO>"+orderNumber+"</ORDER_NO>");//订单号
		sb.append("<ORG_LEGAL_MANE>"+purchaserName+"</ORG_LEGAL_MANE>");//公司信息
		sb.append("<UNIFIED_SOCOAL_CREDIT_CODE>"+organizationCode+"</UNIFIED_SOCOAL_CREDIT_CODE>");//社会信用代码
		sb.append("</Body></In>");
//		String macCode = MacUtils.toMAC(sb.toString());
//		sb.append(macCode);
		return sb.toString();
	}
	
	/**
	 * 订单支付 G332
	 * @param orderNumber
	 * @param customerName
	 * @param organizationCode
	 * @param price
	 * @param companyName
	 * @param account
	 * @param bankNo
	 * @param bankName
	 * @param macCode
	 * @return
	 */
	public String guangdaG332(String orderNumber,String purchaserName,String organizationCode,Double price,String supplierName,String account,String bankNo,String  bankName){
		StringBuffer sb = new StringBuffer();
		sb.append(getHead("G332"));
		sb.append("<Body>");
		sb.append("<CHANNEL_CODE>03030002</CHANNEL_CODE>");//接入机构号
		sb.append("<ORDER_NO>"+orderNumber+"</ORDER_NO>");//订单号
		sb.append("<ORDER_APPLY_DATE>"+DateUtil.formatYearMonthDayTwo(new Date())+"</ORDER_APPLY_DATE>");//订单提交日
		sb.append("<ORG_LEGAL_MANE>"+purchaserName+"</ORG_LEGAL_MANE>");//公司名称
		sb.append("<UNIFIED_SOCOAL_CREDIT_CODE>"+organizationCode+"</UNIFIED_SOCOAL_CREDIT_CODE>");//社会信用代码
		sb.append("<ORDER_AMOUNT>"+price+"</ORDER_AMOUNT>");//订单价格
		sb.append("<USE_TYPE>原料费</USE_TYPE>");//用途类型
		sb.append("<ORDER_PURPOSES>采购</ORDER_PURPOSES>");//订单用途
		sb.append("<PAYEE_NAME>"+supplierName+"</PAYEE_NAME>");//收款人名称
		sb.append("<PAYEE_USERNAME>"+supplierName+"</PAYEE_USERNAME>");//收款人户名
		sb.append("<PAYEE_ACCMMOUNT>"+account+"</PAYEE_ACCMMOUNT>");//收款人账号
		sb.append("<PAYEE_BANK_NO>"+bankNo+"</PAYEE_BANK_NO>");//收款人行号
		sb.append("<PAYEE_BANK>"+bankName+"</PAYEE_BANK>");//收款人行名
		sb.append("</Body></In>");
//		String macCode = MacUtils.toMAC(sb.toString());
//		sb.append(macCode);
		return sb.toString();
	}
	
	/**
	 * 贷款明细查询  G335
	 * @param orderNumber
	 * @param customerName
	 * @param organizationCode
	 * @param iouNumber
	 * @param macCode
	 * @return
	 */
	public String guangdaG335(String orderNumber,String purchaserName,String organizationCode,String iouNumber,Integer start){
		StringBuffer sb = new StringBuffer();
		sb.append(getHead("G335"));
		sb.append("<Body>");
		sb.append("<CHANNEL_CODE>03030002</CHANNEL_CODE>");//接入机构号
		sb.append("<UNIFIED_SOCOAL_CREDIT_CODE>"+organizationCode+"</UNIFIED_SOCOAL_CREDIT_CODE>");//社会信用代码
		sb.append("<ORG_LEGAL_MANE>"+purchaserName+"</ORG_LEGAL_MANE>");//公司信息
		sb.append("<ORDER_NO>"+orderNumber+"</ORDER_NO>");//订单号
		sb.append("<LOUS_NO>"+iouNumber+"</LOUS_NO>");//借据编号
		sb.append("<QISHI>"+start+"</QISHI>");//起始笔数
		sb.append("<BISHU>20</BISHU>");//显示笔数
		sb.append("</Body></In>");
//		String macCode = MacUtils.toMAC(sb.toString());
//		sb.append(macCode);
		return sb.toString();
	}
	
	/**
	 * 取消订单  G336
	 * @param orderNumber
	 * @param purchaserName
	 * @param organizationCode
	 * @param macCode
	 * @return
	 */
	public String guangdaG336(String orderNumber,String purchaserName,String organizationCode){
		StringBuffer sb = new StringBuffer();
		sb.append(getHead("G336"));
		sb.append("<Body>");
		sb.append("<CHANNEL_CODE>03030002</CHANNEL_CODE>");//接入机构号
		sb.append("<ORDER_NO>"+orderNumber+"</ORDER_NO>");//订单号
		sb.append("<ORG_LEGAL_MANE>"+purchaserName+"</ORG_LEGAL_MANE>");//公司名称
		sb.append("<UNIFIED_SOCOAL_CREDIT_CODE>"+organizationCode+"</UNIFIED_SOCOAL_CREDIT_CODE>");//社会信用代码
		sb.append("</Body></In>");
//		String macCode = MacUtils.toMAC(sb.toString());
//		sb.append(macCode);
		return sb.toString();
	}
}
