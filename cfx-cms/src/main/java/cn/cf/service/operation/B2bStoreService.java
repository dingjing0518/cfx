package cn.cf.service.operation;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bStoreAlbumDto;
import cn.cf.dto.B2bStoreExtDto;
import cn.cf.entity.B2bStoreAlbumEntry;

public interface B2bStoreService {
	/**
	 * 搜索店铺列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bStoreExtDto> searchStoreList(QueryModel<B2bStoreExtDto> qm);
	/**
	 * 根据条件查询店铺
	 * @param map
	 * @return
	 */
	List<B2bStoreExtDto> searchStoreList(Map<String,Object> map);
	/**
	 * 搜索公司相册类表
	 * @param qm
	 * @return
	 */
	PageModel<B2bStoreAlbumEntry> searchStoreAlbumList(QueryModel<B2bStoreAlbumDto> qm);
	/**
	 * 修改店铺
	 * @param dto
	 * @return
	 */
	int updateStore(B2bStoreExtDto dto);
	/**
	 * 更新公司相册
	 * @param dto
	 * @return
	 */
	int updateStoreAlbum(B2bStoreAlbumDto dto);
	/**
	 * 可绑定的店铺列表
	 */
	PageModel<B2bStoreExtDto> searchStoreBindList(QueryModel<B2bStoreExtDto> qm);

}
