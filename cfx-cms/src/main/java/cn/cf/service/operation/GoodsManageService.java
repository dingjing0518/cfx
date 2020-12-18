package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bBrandExtDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.B2bGradeDto;
import cn.cf.dto.B2bGradeExtDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bProductExtDto;
import cn.cf.dto.B2bSpecDto;
import cn.cf.dto.B2bSpecExtDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bVarietiesDto;
import cn.cf.dto.B2bVarietiesExtDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxTechnologyDto;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.model.ManageAccount;

public interface GoodsManageService {
	/**
	 * 商品上下架列表查询
	 *
	 * @param qm
	 * @return
	 */
	PageModel<B2bGoodsExtDto> searchGoodsUpAndDownList(QueryModel<B2bGoodsExtDto> qm, ManageAccount account,int type);


	/**
	 * 商品上下架导出功能
	 * 
	 * @param qm
	 * @return
	 */
	List<B2bGoodsExtDto> exportGoodsList (QueryModel<B2bGoodsExtDto> qm, ManageAccount account, Integer type);

	/**
	 * 商品导出记录
	 *
	 * @param params
	 * @return
	 */
	int saveGoodsExcelToOss (GoodsDataTypeParams params, ManageAccount account,int type);

	/**
	 * 更新商品
	 * 
	 * @param dto
	 * @return
	 */
	int updateGoods(B2bGoodsExtDto dto);

	
	/**
	 * 品名列表查询
	 * 
	 * @param qm
	 * @return
	 */
	PageModel<B2bProductExtDto> searchProductList(
			QueryModel<B2bProductExtDto> qm);

	/**
	 * 商品规格大类查询列表
	 * 
	 * @param qm
	 * @return
	 */
	PageModel<B2bSpecExtDto> searchSpecList(QueryModel<B2bSpecExtDto> qm);

	/**
	 * 商品品种查询列表
	 * 
	 * @param qm
	 * @return
	 */
	PageModel<B2bVarietiesExtDto> searchVarietiesList(
			QueryModel<B2bVarietiesExtDto> qm);

	/**
	 * 编辑商品品种
	 * 
	 * @param dto
	 * @return
	 */
	int updateVarieties(B2bVarietiesExtDto dto);

	/**
	 * 商品品牌查询列表
	 * 
	 * @param qm
	 * @return
	 */
	PageModel<B2bBrandExtDto> searchBrandList(QueryModel<B2bBrandExtDto> qm);

	/**
	 * 修改商品品名数据
	 * 
	 * @param dto
	 * @return
	 */
	int updateProduct(B2bProductExtDto dto);

	/**
	 * 修改规格大类信息
	 * 
	 * @param dto
	 * @return
	 */
	int updateSpec(B2bSpecExtDto dto);

	/**
	 * 修改商品品牌
	 * 
	 * @param dto
	 * @return
	 */
	int updateB2bBarnd(B2bBrandExtDto dto);

	/**
	 * 添加商品时需要获取的厂家品牌的方法
	 * 
	 * @return
	 */
	List<B2bGoodsBrandDto> getB2bGoodsBrandList(String block);

	/**
	 * 添加商品时需要获取的品名的方法
	 * 
	 * @return
	 */
	List<B2bProductDto> getB2bProductList();

	/**
	 * 添加商品时需要获取的品种的方法
	 * 
	 * @return
	 */
	List<B2bVarietiesDto> getB2bVarietiesPidList();

	/**
	 * 添加商品时需要获取的所有品种的方法
	 *
	 * @return
	 */
	List<B2bVarietiesDto> getB2bVarietiesList();

	/**
	 * 添加商品时需要获取的规格大类的方法
	 * 
	 * @return
	 */
	List<B2bSpecDto> getb2bSpecPid();

	/**
	 * 添加商品时需要获取的等级的方法
	 * 
	 * @return
	 */
	List<B2bGradeDto> getB2bGradeList();

	/**
	 * 添加商品时需要获取的公司信息的方法
	 * 
	 * @param companyType
	 * @param parentPk
	 * @param modelType  cf,sx
	 * @return
	 */
	List<B2bCompanyDto> getB2bCompayDtoByType(Integer companyType,
			String parentPk, String modelType);

	/**
	 * 添加商品时选择公司需查询是否存在子公司
	 * 
	 * @param parentPk
	 * @return
	 */
	List<B2bCompanyDto> getB2bChildCompay(String parentPk);

	/**
	 * 添加商品时查询店铺
	 * 
	 * @param companyPk
	 * @return
	 */
	List<B2bStoreDto> getB2bStoreByCompayPk(String companyPk);

	/**
	 * 添加商品时查询产区
	 * 
	 * @param storePk
	 * @return
	 */
	List<B2bPlantDto> getB2bPlantByStorePk(String storePk);
	
	/**
	 * 添加商品时查询仓库
	 * @param plant
	 * @return
	 */
	List<B2bWareDto> getB2bWareByPlant(String plant);

	/**
	 * 添加商品时查询厂家品牌
	 * 
	 * @param storePk
	 * @return
	 */
	List<B2bGoodsBrandDto> getB2bGoodsBrandByStorePk(String storePk);

	/**
	 * 添加商品时查询规格系列
	 * 
	 * @param parentPk
	 * @return
	 */
	List<B2bSpecDto> getb2bSpecByPid(String parentPk);

	/**
	 * 添加商品时查询子品种
	 * 
	 * @param parentPk
	 * @return
	 */
	List<B2bVarietiesDto> getB2bVarietiesByPidList(String parentPk);

//	/**
//	 * 添加商品时查询子品种
//	 * 
//	 * @param goods
//	 * @return
//	 */
//	String insertGoods(B2bGoods goods);
	/**
	 * 更新等级
	 * @param b2bGrade
	 * @return
	 */
	int updateGrade(B2bGradeExtDto b2bGrade);
	/**
	 * 搜索等级列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bGradeExtDto> searchGrade(QueryModel<B2bGradeExtDto> qm);
	/**
	 * 根据等级名称获取等级
	 * @param name
	 * @return
	 */
	B2bGradeDto getGradeByName(String name);

	
	/**
	 * 查询商品，添加到商品价格趋势
	 * @param qm
	 * @return
	 */
	PageModel<B2bGoodsExtDto> searchGoodsToPriceTrendList(QueryModel<B2bGoodsExtDto> qm);

	/**
	 * 查询工艺列表
	 * @return
	 */
	List<SxTechnologyDto> searchSxTechnologyList();

	/**
	 * 查询原料一级
	 * @return
	 */
	List<SxMaterialDto> searchSxMaterialList();

	/**
	 * 查询原料二级
	 * @return
	 */
	List<SxMaterialDto> searchSxSecondMaterialList(String parentPk);


	List<B2bGoodsExtDto> getExportGoodsNumbers(B2bGoodsExtDto qm);


}
