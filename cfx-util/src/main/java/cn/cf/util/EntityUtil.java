package cn.cf.util;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

public class EntityUtil {
	
	 /**
     * 判断对象中属性值是否全都不为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNotNull(Object object) {
        if (null == object) {
            return false;
        }
 
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) == null || StringUtils.isBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
