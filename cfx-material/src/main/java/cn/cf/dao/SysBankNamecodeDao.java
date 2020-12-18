/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SysBankNamecode;
import cn.cf.dto.SysBankNamecodeDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysBankNamecodeDao {
	int insert(SysBankNamecode model);
	int update(SysBankNamecode model);
	int delete(String id);
	List<SysBankNamecodeDto> searchGrid(Map<String, Object> map);
	List<SysBankNamecodeDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysBankNamecodeDto getByBankno(java.lang.String bankno); 
	 SysBankNamecodeDto getByBankstatus(java.lang.String bankstatus); 
	 SysBankNamecodeDto getByBanktype(java.lang.String banktype); 
	 SysBankNamecodeDto getByBankclscode(java.lang.String bankclscode); 
	 SysBankNamecodeDto getByClearbank(java.lang.String clearbank); 
	 SysBankNamecodeDto getByLegalperson(java.lang.String legalperson); 
	 SysBankNamecodeDto getByTopbanklist(java.lang.String topbanklist); 
	 SysBankNamecodeDto getByTopbankno(java.lang.String topbankno); 
	 SysBankNamecodeDto getByTopbankname(java.lang.String topbankname); 
	 SysBankNamecodeDto getByRplbankno(java.lang.String rplbankno); 
	 SysBankNamecodeDto getByPeoplebankno(java.lang.String peoplebankno); 
	 SysBankNamecodeDto getByCcpcnodeno(java.lang.String ccpcnodeno); 
	 SysBankNamecodeDto getByCitycode(java.lang.String citycode); 
	 SysBankNamecodeDto getByBankname(java.lang.String bankname); 
	 SysBankNamecodeDto getByBankaliasname(java.lang.String bankaliasname); 
	 SysBankNamecodeDto getBySignflag(java.lang.String signflag); 
	 SysBankNamecodeDto getByAddress(java.lang.String address); 
	 SysBankNamecodeDto getByPostcode(java.lang.String postcode); 
	 SysBankNamecodeDto getByTelephone(java.lang.String telephone); 
	 SysBankNamecodeDto getByEmail(java.lang.String email); 
	 SysBankNamecodeDto getByRemark(java.lang.String remark); 
	 SysBankNamecodeDto getByCnapsgeneration(java.lang.String cnapsgeneration); 
	 SysBankNamecodeDto getBySaccstatus(java.lang.String saccstatus); 
	 SysBankNamecodeDto getBySaccaltdate(java.lang.String saccaltdate); 
	 SysBankNamecodeDto getBySaccalttime(java.lang.String saccalttime); 
	 SysBankNamecodeDto getByRemark1(java.lang.String remark1); 
	 SysBankNamecodeDto getByChangetype(java.lang.String changetype); 
	 SysBankNamecodeDto getByUpdatedate(java.lang.String updatedate); 
	 SysBankNamecodeDto getByUpdateno(java.lang.String updateno); 
	 SysBankNamecodeDto getByEffectdate(java.lang.String effectdate); 
	 SysBankNamecodeDto getByInvaliddate(java.lang.String invaliddate); 

}
