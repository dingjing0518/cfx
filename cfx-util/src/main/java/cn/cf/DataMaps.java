package cn.cf;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataMaps {
	public static Map<Integer,String>  sysCategory = new LinkedHashMap<>();
	static{
		sysCategory.put(1,"电商系统" );
		sysCategory.put(2, "物流系统");
		sysCategory.put(3, "金融系统");
	}
}
