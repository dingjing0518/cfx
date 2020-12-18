package cn.cf.util;

import java.util.Comparator;

public class ComparatorByLength  implements Comparator<Object>{
	 @Override
	    public int compare(Object o1, Object o2) {
	        String s1 = (String)o1;
	        String s2 = (String)o2;
	        
	        int temp = s2.length()-s1.length();
	        
	        return temp==0? s1.compareTo(s2):temp;
	    }
}
