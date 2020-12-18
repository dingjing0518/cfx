/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bStoreAlbum;
import cn.cf.dto.B2bStoreAlbumDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bStoreAlbumDao {
	int insert(B2bStoreAlbum model);
	int update(B2bStoreAlbum model);
	int delete(String id);
	List<B2bStoreAlbumDto> searchGrid(Map<String, Object> map);
	List<B2bStoreAlbumDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bStoreAlbumDto getByPk(java.lang.String pk); 
	 B2bStoreAlbumDto getByStorePk(java.lang.String storePk); 
	 B2bStoreAlbumDto getByUrl(java.lang.String url); 

}
