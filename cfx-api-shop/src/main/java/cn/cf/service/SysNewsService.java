package cn.cf.service;




import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.SysNewsDto;
import cn.cf.entity.SysNewsStorageEntity;

public interface SysNewsService {

	PageModel<SysNewsStorageEntity> searchNews_old(Map<String, Object> map) throws Exception;
	SysNewsDto getSysNewsByPk(String pk,String categoryPk) throws Exception;
 
	PageModel<SysNewsStorageEntity> searchNews (Map<String, Object> map) ;
}
