package cn.cf.util;



import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class SignUtils {
    @SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,Map<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Entry entry = (Entry)it.next();
            String k = (String)entry.getKey();

            Object v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"encodeData".equals(k)) {
            	sb.append(k + "=" + v + "&");
            }

        }
        String sbb= sb.toString().substring(0, sb.length()-1);
//        sb.append("key=" + Key);  
        String sign = MD5Utils.MD5Encode(sbb, characterEncoding).toUpperCase();
        return sign;  
    }  
    
    
    /**
     * 将requese.getParamsMap所有参数转为treeMap进行ACCSII码排序
     * @param map
     * @return
     */
    public static Map<Object, Object> paramsToTreeMap(Map<String,String[]> map){
    	
    	Map<Object, Object> tree = new TreeMap<Object, Object>();
        Set<Entry<String, String[]>> set = map.entrySet();  
        Iterator<Entry<String, String[]>> it = set.iterator();  
        while (it.hasNext()) {  
            Entry<String, String[]> entry = it.next();  
            for (String i : entry.getValue()) {  
                tree.put(entry.getKey(),i);
            }  
        }  
    	return tree;
    }
}

