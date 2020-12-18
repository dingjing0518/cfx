/**
 * 
 */
package cn.cf.service;

import java.util.Map;
import java.util.SortedMap;

import cn.cf.dto.B2bTokenDto;





/**
 * @author bin
 * 
 */
public interface SysService {

	B2bTokenDto searchToken(Map<String, Object> map);



	SortedMap<Object, Object> mySignByParameters(B2bTokenDto b2btoken);

	
}
