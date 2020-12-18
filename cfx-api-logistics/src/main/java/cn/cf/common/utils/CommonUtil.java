package cn.cf.common.utils;

import java.text.DecimalFormat;

public class CommonUtil {

	public static Double formatDoubleFour(Double dataD){
		DecimalFormat format = new DecimalFormat("#.0000"); 
		if(dataD != null){
			return  Double.valueOf(format.format(dataD));
		}else{
			return 0d;
		}
	}
}
