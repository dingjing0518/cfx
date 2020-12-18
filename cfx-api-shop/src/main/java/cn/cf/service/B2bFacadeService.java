package cn.cf.service;

import java.io.IOException;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bStoreAlbumDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.dto.C2bMarrieddealDto;
import cn.cf.dto.C2bMarrieddealDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bPackNumber;

public interface B2bFacadeService {
	/**
	 * 商品搜索
	 * 
	 * @param map
	 * @return
	 */

//	public Object search(Map<String, Object> map);

 
 

	/**
	 * 编辑/添加厂区
	 * 
	 * @param dto
	 * @param memberPk 
	 * @return
	 */
	RestCode addPlant(B2bPlantDto dto, String memberPk);

	/**
	 * 编辑/添加仓库
	 * 
	 * @param dto
	 * @param memberPk 
	 * @return
	 */
	RestCode addWare(B2bWareDto dto, String memberPk);

	String exportGood(Map<String, Object> map) throws ParsePropertyException, InvalidFormatException, IOException;
	 

	/**
	 * 新增/编辑包装批号
	 * 
	 * @param packNumberModel
	 * @param company
	 * @return
	 */
	RestCode addPackNumber(B2bPackNumber packNumberModel, Sessions session);

	/**
	 * 代采业务员商品列表
	 * 
	 * @param map
	 * @param isAdmin
	 * @param member
	 * @param company 
	 * @return
	 */
	PageModel<B2bGoodsDtoEx> searchGoodsListBySalesMan(Map<String, Object> map, Integer isAdmin, B2bMemberDto member, B2bCompanyDto company);

	/**
	 * 添加子公司
	 *
	 * @param dto
	 * @param memberPk 
	 * @return
	 */
	RestCode addSubCompany(B2bCompanyDtoEx dto, String memberPk);
	

	String addMarrieddeal(C2bMarrieddealDto marrieddeal, B2bMemberDto memberDto, B2bCompanyDto companyDto);

	void updateMarrieddeal(C2bMarrieddealDto marrieddeal, Integer type);

	C2bMarrieddealDto searchC2bMarrieddealByPk(String marrieddealPk);

	void reSummitMarrieddeal(String marrieddealPk);

	PageModel<C2bMarrieddealDtoEx> searchC2bMarrieddealByMem(Map<String, Object> map);
	
	//修改公司头像
	void saveHeadPortrait(String memberPk,String headPortrait);
	
	
	/**
	 * 首次进行某种操作添加积分
	 * @param memberPk
	 * @param companyPk
	 * @param dimenType
	 */
	void  addPoint( String memberPk,String companyPk,String dimenType);
	
	
	/**
	 * 查询某个公司的销售中的商品
	 * @param map
	 * @return
	 */
	PageModel<B2bGoodsDtoEx> getB2bGoodsList(Map<String, Object> map);
	
	String[] findCompanys(String companyPk,Integer companyType);

	public RestCode addStoreAlbum(String storePk, String url);

	public RestCode deleteStoreAlbum(String pk);

	public PageModel<B2bStoreAlbumDto> searchStoreAlbum(Map<String, Object> map);

	public RestCode addWareNumber(B2bWarehouseNumberDto dto);
	
	
	
    
    
}
