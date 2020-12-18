package cn.cf.common.creditpay.guangda;

import cebenc.softenc.SoftEnc;
import cn.cf.property.PropertyConfig;
/**
 * Copyright (c) 2018, 光大银行加密平台接口
 * All rights reserved.
 * 
 * Description：
 * History：
 * Date           Author               Desc
 * ------------------------------------------------
 * 2018-02-06     lvjianbin            creator
 */
public class MacUtils {
	
	/**
	 * 1.第三方发起密钥交换
	 * 2.第三方报文加MAC
	 * 3.第三方验MAC
	 */
	public static void main(String[] args) {
		/**此demo中的报文仅为实例，实际报文格式以借口为准*/
		// 发送密钥交换请求报文
		
		//解析密钥交换应答报文，更新密钥
//		String xml="000540<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><out><head><Version>1.0.1</Version><InstId>BJCEB</InstId><AnsTranCode>BJCEBRWKRes</AnsTranCode><TrmSeqNum>admin180207110116</TrmSeqNum></head><tout><partnerCode>746</partnerCode><returnCode>00</returnCode><errorDescription></errorDescription><keyName>ZPK</keyName><keyValue>B6B2D144258D172E75CFFD53077D8ED4</keyValue><verifyValue>0B116824875863F9</verifyValue><keyName1>ZAK</keyName1><keyValue1>D13A9DD478ABF1CB633DC4BAE1593CEA</keyValue1><verifyValue1>77F7AF1A9018A7F0</verifyValue1></tout></out>";
		System.out.println( "**********更新密钥***开始**********");
//		updateWorkKey(xml);
		System.out.println( "**********更新密钥***结束**********");
		
		//请求报文加MAC，MAC值为16位
		String reqXML="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><in><head><Version>1.0.1</Version><InstId>BJCEB</InstId><AnsTranCode>BJCEBQBIReq</AnsTranCode><TrmSeqNum>00711497180212094224</TrmSeqNum></head><tin><billKey>finance|160396371336101889</billKey><companyId>020001792</companyId><beginNum>1</beginNum><queryNum>1</queryNum><filed1></filed1><filed2></filed2><filed3></filed3><filed4></filed4></tin></in>";
		//生成MAC
		System.out.println( "**********计算MAC***开始**********");
		String mac16=toMAC(reqXML);
		System.out.println("生成MAC："+mac16);
		//将MAC拼接在报文最后
		reqXML=reqXML+mac16;
		System.out.println("加MAC后的请求报文："+reqXML);
		System.out.println( "**********计算MAC***结束**********");
		
		//应答报文验MAC
		System.out.println( "**********应答报文验MAC***开始**********");
		String resXML="000313<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><out><head><Version>1.0.1</Version><InstId>000000000000000</InstId><AnsTranCode>Error</AnsTranCode><TrmSeqNum>00711497180212094224</TrmSeqNum></head><tout><errorCode>DEF0002</errorCode><errorMessage></errorMessage><errorDetail></errorDetail></tout></out>32E5677A7BF5B546";
		//截取报文中最后16位
		String resMAC=resXML.substring(resXML.length() - 16,resXML.length());
		System.out.println("应答报文中的MAC："+resMAC);
		//根据应答报文生成MAC
		//去头和尾，头是报文长度，尾是MAC
		resXML=resXML.substring( 0, resXML.length() - 16 );
		//如果没有头，则不需要
		resXML=resXML.substring( 6, resXML.length());
		String MAC=toMAC(resXML);
		System.out.println("应答报文生成的MAC："+MAC);
		//判断收到的MAC与生成的是否一致
		if(resMAC.equals(MAC)){
			System.out.println("MAC校验通过!!");
		}else{
			System.out.println("MAC校验不通过!!");
		}
		System.out.println( "**********应答报文验MAC***结束**********");
	}
	
	//更新密钥文件
	public static void updateWorkKey(String keyValue,String verifyValue) {
		//接收到的xml报文
		//例如     000540<?xml version="1.0" encoding="ISO-8859-1"?><out><head><Version>1.0.1</Version><InstId>BJCEB</InstId><AnsTranCode>BJCEBRWKRes</AnsTranCode><TrmSeqNum>admin180207110116</TrmSeqNum></head><tout><partnerCode>746</partnerCode><returnCode>00</returnCode><errorDescription></errorDescription><keyName>ZPK</keyName><keyValue>E5A915D81EB9B8BE816F4E46011B91C4</keyValue><verifyValue>032E5A89CDC16AE2</verifyValue><keyName1>ZAK</keyName1><keyValue1>D13A9DD478ABF1CB633DC4BAE1593CEA</keyValue1><verifyValue1>77F7AF1A9018A7F0</verifyValue1></tout></out>
		
		//获取报文中的keyValue、verifyValue标签中的值
//		String keyValue = "DF00BA0E7F83FB8F63B232321D1E454D";
//		String verifyValue = "BADA25DB94E37891";
		System.out.println("keyValue:"+keyValue+",verifyValue:"+verifyValue);
		
		// 密钥文件目录
		//.0.SYS和.1.SYS为申请的主密钥文件，以下方法会生成.3.SYS，为工作密钥，即计算MAC所使用密钥
		String fileDir = PropertyConfig.getProperty("gd_mac_path");
		System.out.println("密钥文件路径=====["+fileDir+"]");

		try {
			SoftEnc.Init(fileDir);
			//将工作密钥更新到本地密钥文件中
			SoftEnc.WriteMACK(keyValue,verifyValue);
			System.out.println("工作密钥更新成功!!!!");
		} catch (Exception e) {
			System.out.println("工作密钥更新成功!!!!");
			e.printStackTrace();
		}
	}
	//使用MAC密钥对报文计算MAC
	public static String toMAC(String xml) {
		String mac16="";
		try {
			//生成MAC
			mac16 = SoftEnc.GenMac(xml.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mac16;
	}
}
