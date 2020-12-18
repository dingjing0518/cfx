package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.dto.MarketingPersonnelExtDto;
import cn.cf.entity.SalemanSaleDetailReport;
import cn.cf.model.MarketingPersonnel;

public interface MarketingPersonnelExtDao extends MarketingPersonnelDao {

    List<MarketingPersonnelDto> getPersonByType(@Param("type") Integer type);

    int searchPersonnels(Map<String, Object> map);

    List<MarketingPersonnelExtDto> searchPersonnelList(Map<String, Object> map);

   // List<SalemanSaleDetailReport> searchSupAccountCompany(Map<String, Object> map);

    //List<SalemanSaleDetailReport> searchPurAccountCompany(Map<String, Object> map);

    List<SalemanSaleDetailReport> searchSalemanSaleList(@Param("date") String date);

    List<MarketingPersonnelExtDto> getRegions();

    MarketingPersonnelDto getByMap(Map<String, Object> map);

    int updateExt(MarketingPersonnel dto);

    int updateRegion(@Param("regionPk") String regionPk);

    List<MarketingPersonnelDto> getDistributePerson();

	List<MarketingPersonnelDto> getAccountByOrder(String date);

	Integer getPurCustomNum(Map<String, Object> map);

	Integer getSupCustomNum(Map<String, Object> map);

	List<B2bOrderGoodsDto> getPurOrderByAccount(Map<String, Object> map);

	List<B2bOrderGoodsDto> getSupOrderByAccount(Map<String, Object> map);

	ManageAccountDto getAccountByMap(Map<String, Object> map);

	List<ManageAccountExtDto> getRegionalAccountByMap();

}
