/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bLotteryRecord;
import cn.cf.dto.B2bLotteryRecordDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bLotteryRecordDao {
	int insert(B2bLotteryRecord model);
	int update(B2bLotteryRecord model);
	int delete(String id);
	List<B2bLotteryRecordDto> searchGrid(Map<String, Object> map);
	List<B2bLotteryRecordDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bLotteryRecordDto getByPk(java.lang.String pk); 
	 B2bLotteryRecordDto getByActivityPk(java.lang.String activityPk); 
	 B2bLotteryRecordDto getByAwardPk(java.lang.String awardPk); 
	 B2bLotteryRecordDto getByName(java.lang.String name); 
	 B2bLotteryRecordDto getByAwardName(java.lang.String awardName); 
	 B2bLotteryRecordDto getByProvinceName(java.lang.String provinceName); 
	 B2bLotteryRecordDto getByProvince(java.lang.String province); 
	 B2bLotteryRecordDto getByCityName(java.lang.String cityName); 
	 B2bLotteryRecordDto getByCity(java.lang.String city); 
	 B2bLotteryRecordDto getByAreaName(java.lang.String areaName); 
	 B2bLotteryRecordDto getByArea(java.lang.String area); 
	 B2bLotteryRecordDto getByTown(java.lang.String town); 
	 B2bLotteryRecordDto getByTownName(java.lang.String townName); 
	 B2bLotteryRecordDto getByAddressPk(java.lang.String addressPk); 
	 B2bLotteryRecordDto getByContacts(java.lang.String contacts); 
	 B2bLotteryRecordDto getByContactsTel(java.lang.String contactsTel); 
	 B2bLotteryRecordDto getByNote(java.lang.String note); 
	 B2bLotteryRecordDto getByMemberPk(java.lang.String memberPk); 
	 B2bLotteryRecordDto getByMemberName(java.lang.String memberName); 
	 B2bLotteryRecordDto getByAddress(java.lang.String address); 
	 B2bLotteryRecordDto getByCompanyPk(java.lang.String companyPk); 
	 B2bLotteryRecordDto getByCompanyName(java.lang.String companyName); 
	 B2bLotteryRecordDto getByMobile(java.lang.String mobile); 

}
