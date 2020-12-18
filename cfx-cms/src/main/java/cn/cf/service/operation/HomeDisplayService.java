package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bFriendLinkDto;
import cn.cf.dto.B2bFriendLinkExtDto;
import cn.cf.dto.B2bKeywordHotDto;
import cn.cf.dto.B2bKeywordHotExtDto;
import cn.cf.dto.B2bPriceMovementExtDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bSpecDto;
import cn.cf.dto.B2bVarietiesDto;
import cn.cf.dto.SxHomeSecondnavigationDto;
import cn.cf.dto.SxHomeSecondnavigationExtDto;
import cn.cf.dto.SxHomeThirdnavigationExtDto;
import cn.cf.dto.SysHomeBannerMassageDto;
import cn.cf.dto.SysHomeBannerMassageExtDto;
import cn.cf.dto.SysHomeBannerProductnameDto;
import cn.cf.dto.SysHomeBannerProductnameExtDto;
import cn.cf.dto.SysHomeBannerSeriesDto;
import cn.cf.dto.SysHomeBannerSeriesExtDto;
import cn.cf.dto.SysHomeBannerSpecDto;
import cn.cf.dto.SysHomeBannerSpecExtDto;
import cn.cf.dto.SysHomeBannerVarietiesDto;
import cn.cf.dto.SysHomeBannerVarietiesExtDto;
import cn.cf.entity.B2bPriceMovementEntity;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.SxPriceMovementEntity;
import cn.cf.model.*;

public interface HomeDisplayService {
	/**
	 * 根据map条件查询商品品名集合
	 * @return
	 */
    List<B2bProductDto> getB2bProductList();
    /**
     * 查询所有品名集合
     * @return
     */
    List<B2bProductDto> getAllProductList();
    /**
     * 搜索品名首页图片管理列表
     * @param qm
     * @return
     */
    PageModel<SysHomeBannerProductnameDto> searchHomeBannerProductnameList(QueryModel<SysHomeBannerProductnameExtDto> qm);
    /**
     * 更新品名首页图片管理表
     * @param dto
     * @return
     */
    int updateHomeBannerProductname(SysHomeBannerProductnameExtDto dto);
    /**
     * 新增品名首页图片管理
     * @param dto
     * @return
     */
    int insertHomeBannerProductname(SysHomeBannerProductname dto);
    /**
     * 修改品名首页图片管理表状态和删除数据
     * @param dto
     * @return
     */
    int updateIsVisAndDelHomePgProNameBanner(SysHomeBannerProductnameExtDto dto);
    /**
     * 修改品名首页图片管理表
     * @param dto
     * @return
     */
    int updateHomeBannerProductnameSort(SysHomeBannerProductnameDto dto);
    /**
     * 修改品种首页显示图片排序
     * @param dto
     * @return
     */
    int updateHomeBannerVarietiesnameSort(SysHomeBannerVarietiesDto dto);
    /**
     * 修改规格首页显示图片排序
     * @param dto
     * @return
     */
    int updateHomeBannerSpecnameSort(SysHomeBannerSpecDto dto);
    /**
     * 修改规格系列首页显示图片
     * @param dto
     * @return
     */
    int updateHomeBannerSeriesnameSort(SysHomeBannerSeriesDto dto);
    /**
     * 搜索消息首页显示图片列表
     * @param qm
     * @return
     */
    PageModel<SysHomeBannerMassageExtDto> searchHomeBannerMessageList(QueryModel<SysHomeBannerMassageExtDto> qm);
    /**
     * 修改消息首页显示图片
     * @param dto
     * @return
     */
    int updateHomeBannerMessage(SysHomeBannerMassageExtDto dto);
    /**
     * 修改消息首页显示图片状态和删除
     * @param dto
     * @return
     */
    int updateIsVisAndDelMessageBanner(SysHomeBannerMassageExtDto dto);
    /**
     * 添加消息首页显示图片
     * @param dto
     * @return
     */
    int insertHomeBannerMessage(SysHomeBannerMassage dto);
    /**
     * 查询所有首页显示商品品名
     * @return
     */
    List<SysHomeBannerProductnameDto> getAllSysBannerProductNameList();
    /**
     * 查询所有首页显示商品品名
     * @return
     */
    List<SysHomeBannerProductnameDto> getSysBannerProductNameToOtherList();

    /**
     * 修改消息首页显示排序
     * @param dto
     * @return
     */
    int updateHomeBannerMassagenameSort(SysHomeBannerMassageDto dto);
    /**
     * 获取partentPk为-1的父商品品种
     * @return
     */
    List<B2bVarietiesDto> getB2bVarietiesPidList();
    /**
     * 查询规格系列集合
     * @return
     */
    List<B2bSpecDto> getb2bSpecs();
    /**
     * 根据父类pk查询规格集合
     * @param parentPk
     * @return
     */
    List<B2bSpecDto> getb2bSpecByPid(String parentPk);
    /**
     * 搜索首页显示商品品种列表
     * @param qm
     * @return
     */
    PageModel<SysHomeBannerVarietiesExtDto> searchHomeBannerVarietiesList(QueryModel<SysHomeBannerVarietiesExtDto> qm);
   /**
    * 搜索首页显示规格列表
    * @param qm
    * @return
    */
    PageModel<SysHomeBannerSpecExtDto> searchHomeBannerSpecList(QueryModel<SysHomeBannerSpecExtDto> qm);
    /**
     * 搜索首页显示规格系列列表
     * @param qm
     * @return
     */
    PageModel<SysHomeBannerSeriesExtDto> searchHomeBannerSeriesList(QueryModel<SysHomeBannerSeriesExtDto> qm);
    /**
     * 更新首页显示商品品种
     * @param dto
     * @return
     */
    int updateHomeBannerVarieties(SysHomeBannerVarietiesExtDto dto);
    /**
     * 更新首页显示商品品种状态和删除
     * @param dto
     * @return
     */
    int updateIsVisAndDelPgVarietiesBanner(SysHomeBannerVarietiesExtDto dto);
    /**
     * 新增首页显示商品品种
     * @param dto
     * @return
     */
    int insertHomeBannerVarieties(SysHomeBannerVarieties dto);
    /**
     * 更新首页显示商品规格
     * @param dto
     * @return
     */
    int updateHomeBannerSpec(SysHomeBannerSpecExtDto dto);
    /**
     * 修改首页显示规格状态和删除
     * @param dto
     * @return
     */
    int updateIsVisAndDelHomePgSpecBanner(SysHomeBannerSpecExtDto dto);
    /**
     * 新增首页显示商品规格
     * @param dto
     * @return
     */
    int insertHomeBannerSpec(SysHomeBannerSpec dto);
    /**
     * 更新首页显示商品规格系列
     * @param dto
     * @return
     */
    int updateHomeBannerSeries(SysHomeBannerSeriesExtDto dto);
    /**
     * 修改首页显示商品规格系列状态和删除
     * @param dto
     * @return
     */
    int updateIsVisAndDelHomePgSeriesBanner(SysHomeBannerSeriesExtDto dto);
    /**
     * 新增首页显示商品规格系列
     * @param dto
     * @return
     */
    int insertHomeBannerSeries(SysHomeBannerSeries dto);
    /**
     * 根据商品品名Pk查询商品规格集合
     * @param productnamePk
     * @return
     */
    List<SysHomeBannerSpecExtDto> getSysHomeBannerSpecByProductNamePk(String productnamePk);
    /**
     * 根据商品规格pk查询规格
     * @param pk
     * @return
     */
    SysHomeBannerSpecDto getSysHomeBannerSpecPk(String pk);
    /**
     * 根据商品品种pk查询首页显示商品品种集合
     * @param productnamePk
     * @return
     */
    List<SysHomeBannerVarietiesDto> searchSysHomeBannerVarieties(String productnamePk);
    /**
     * 搜索热搜关键字列表
     * @param qm
     * @return
     */
    PageModel<B2bKeywordHotDto> searchB2bKeywordHot(QueryModel<B2bKeywordHotExtDto> qm);
    /**
     * 修改热搜关键字数据
     * @param keywordHot
     * @return
     */
    int updateB2bKeywordHot(B2bKeywordHot keywordHot);
    /**
     * 搜索友情链接列表
     * @param qm
     * @return
     */
    PageModel<B2bFriendLinkDto> searchB2bFriendLink(QueryModel<B2bFriendLinkExtDto> qm);
    
    /**
     * 修改友情链接数据
     * @param link
     * @return
     */
    int updateB2bFriendLink(B2bFriendLink link);
    
    /**
     * 搜索价格趋势列表
     * @param qm
     * @return
     */
    PageModel<B2bPriceMovementExtDto> searchPriceTrendList(QueryModel<B2bPriceMovementExtDto> qm);

    /**
     * 导出价格趋势数据
     * @param dto
     * @return
     */
    List<B2bPriceMovementExtDto> exportPriceTrendList(B2bPriceMovementExtDto dto);


    /**
     * 保存导出价格趋势记录
     * @param params
     * @return
     */
    int savePriceTrendExcelToOss(GoodsDataTypeParams params, ManageAccount account);
    /**
     * 更新价格趋势表
     * @param dto
     * @return
     */
    int updateB2bPriceMovement(B2bPriceMovementExtDto dto);
    
    /**
     * 编辑保存在mongo中的历史数据
     * @param dto
     * @return
     */
    int updateB2bPriceMovementEntity(B2bPriceMovementEntity dto);


    /**
     * 编辑保存在mongo中的纱线历史数据
     * @param dto
     * @return
     */
    int updateSxPriceMovementEntity(SxPriceMovementEntity dto);
    
    /**
     * 新增价格趋势历史数据
     * @param dto
     * @return
     */
    int isShowB2bPriceMovementEntity(B2bPriceMovementEntity dto);

    /**
     * 显示或不显示价格趋势历史数据
     * @param dto
     * @return
     */
    int isShowSxPriceMovementEntity(SxPriceMovementEntity dto);
    
    /**
     * 删除在mongo中的历史数据
     * @param dto
     * @return
     */
    int deleteB2bPriceMovementEntity(B2bPriceMovementEntity dto);

    /**
     * 删除纱线在mongo中的历史数据
     * @param dto
     * @return
     */
    int deleteSxPriceMovementEntity(SxPriceMovementEntity dto);
    
    /**
     * 新增历史数据保存到mongo中
     * @param dto
     * @return
     */
    int insertB2bPriceMovementEntity(B2bPriceMovementEntity dto);

    /**
     * 新增纱线历史数据保存到mongo中
     * @param dto
     * @return
     */
    int insertSxPriceMovementEntity(SxPriceMovementEntity dto);
    
    /**
     * 编辑价格趋势商品查询
     * @param qm
     * @return
     */
    PageModel<B2bPriceMovementExtDto> searchEditPriceTrendList(QueryModel<B2bPriceMovementExtDto> qm);
    
    /**
     * 添加商品到价格趋势表
     * @param pk
     * @return
     */
    String  insertGoodsToPriceTrendList(String pk);


    /**
     * 添加纱线商品到价格趋势表
     * @param pk
     * @return
     */
    String  insertSxGoodsToPriceTrendList(String pk);


    /**
     * 价格趋势历史数据查询
     * @param qm
     * @return
     */
    PageModel<B2bPriceMovementEntity> searchPriceTrendHistoryList(QueryModel<B2bPriceMovementExtDto> qm);

    /**
     * 纱线价格趋势历史数据查询
     * @param qm
     * @return
     */
    PageModel<SxPriceMovementEntity> searchSxPriceTrendHistoryList(QueryModel<B2bPriceMovementExtDto> qm);
    
    /**
     * 导出价格趋势历史数据
     * @param dto
     * @return
     */
    List<B2bPriceMovementEntity> exportPriceTrendHistoryList(B2bPriceMovementExtDto dto);

    /**
     * 导出价格趋势历史数据
     * @param dto
     * @return
     */
    List<SxPriceMovementEntity> exportSxPriceTrendHistoryList(B2bPriceMovementExtDto dto);

    /**
     * 保存导出价格趋势历史数据记录
     * @param params
     * @return
     */
    int savePriceTrendHistoryToOss(GoodsDataTypeParams params,ManageAccount account);
    
    
    /**
     * 二级导航页面
     * @param qm
     * @return
     */
	PageModel<SxHomeSecondnavigationExtDto> searchHomeSecondNavigationList(QueryModel<SxHomeSecondnavigationExtDto> qm);
	
	/**
	 * 三级导航页面
	 * @param qm
	 * @return
	 */
	PageModel<SxHomeThirdnavigationExtDto> searchHomeThirdNavigationList(QueryModel<SxHomeThirdnavigationExtDto> qm);
	
	 /**
	  * 一级菜单查询二级
	  * @param type
	  * @return
	  */
	String changeSecondNavigation(Integer type);
	
	
	/**
	 * 编辑二级导航栏
	 * @param dto
	 * @return
	 */
	Integer updateSecondNavigation(SxHomeSecondnavigationExtDto dto);
	/**
	 * 编辑二级导航栏排序
	 * @param dto
	 * @return
	 */
	Integer updateSecondNavigationSort(SxHomeSecondnavigationExtDto dto);
	/**
	 * 获取化纤，纱线的二级导航
	 * @return
	 */
	List<SxHomeSecondnavigationDto> getAllSecondNavigationList();
	/**
	 * 二级导航级联三级
	 * @param pk
	 * @return
	 */
	String changeThirdNavigation(String pk);
	
	/**
	 * 编辑三级导航
	 * @param dto
	 * @return
	 */
	Integer updateThirdNavigation(SxHomeThirdnavigationExtDto dto);
	/**
	 * 三级导航修改排序
	 * @param dto
	 * @return
	 */
	Integer updateThirdNavigationSort(SxHomeThirdnavigationExtDto dto);
}
