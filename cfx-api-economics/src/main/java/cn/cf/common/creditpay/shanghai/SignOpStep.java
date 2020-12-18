package cn.cf.common.creditpay.shanghai;

//
//Copyright (C) 2005 ECC CORPORATION
//
//ALL RIGHTS RESERVED BY ECC CORPORATION, THIS PROGRAM
//MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS
//FURNISHED BY ECC CORPORATION, NO PART OF THIS PROGRAM
//MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
//WITHOUT THE PRIOR WRITTEN PERMISSION OF ECC CORPORATION.
//USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
//OF THE PROGRAM
//
//       ECC CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
//
//$Log: SignOpStep.java,v $
//Revision 1.3  2008/09/05 03:02:48  zhougy
//*** empty log message ***
//
//Revision 1.2  2007/11/30 06:07:24  zhougy
//修改示例程序
//


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

import cn.cf.property.PropertyConfig;

public class SignOpStep  {
    String verifyValue=null;
   

     SignOpStep(String verifyValue) {
         
         this.verifyValue=verifyValue;
        // TODO Auto-generated constructor stub
    }

    public String sign(){
        // TODO Auto-generated method stub
        InetAddress addr = null;
        Socket socket = null;
        String verifyheader = null;
		try {
			verifyheader = "<?xml version=\"1.0\" encoding=\"gbk\"?>\n"
				+"<msg>\n"
				+"<msg_head>\n"
				+"<msg_type>0</msg_type>\n" 
				+"<msg_id>1005</msg_id>\n" 
				+"<msg_sn>0</msg_sn>\n" 
				+"<version>1</version>\n" 
				+"</msg_head>\n" 
				+"<msg_body>\n" 
				+"<origin_data_len>"+this.verifyValue.getBytes("GBK").length+"</origin_data_len>\n" 
				+"<origin_data>"+this.verifyValue+"</origin_data>\n" 
				+"</msg_body>\n" 
				+"</msg>";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
           
            String verifyIp =  PropertyConfig.getProperty("sh_api_ip");
            int verifyPort =Integer.valueOf(PropertyConfig.getProperty("sh_api_port"));
            //verifyValue=getSendStr(verifyValue);
            //System.out.println("发送的原文包："+verifyValue);
                addr = InetAddress.getByName(verifyIp);
                socket = new Socket(addr, verifyPort);
                BufferedWriter wr =
                    new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(),"GBK"));                
                wr.write(verifyheader);
                wr.flush();
                //接收
                BufferedReader rd =
                    new BufferedReader(
                        new InputStreamReader(socket.getInputStream(),"GBK"));
                String line = null;
                StringBuffer sb = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                   // System.out.println(line);
                    sb.append(line);
                }
                //处理返回
                String returnStr = sb.toString();
                //System.out.println("返回的密文："+returnStr);
               
                     
                wr.close();
                rd.close();
                socket.close();
                return returnStr;
    }catch(Exception e){
    	e.printStackTrace();
        return "";
    }
    }
    
    
   
    
    public static String getNodeValue(String returnStr, String tagName) {
        int startIdx, endIdx;
        String beginTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        startIdx = returnStr.indexOf(beginTag) + beginTag.length();
        endIdx = returnStr.indexOf(endTag, startIdx);
        return returnStr.substring(startIdx, endIdx);
    }



}
