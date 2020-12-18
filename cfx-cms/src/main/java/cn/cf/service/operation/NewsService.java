package cn.cf.service.operation;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysCategoryExtDto;
import cn.cf.dto.SysNewsExtDto;
import cn.cf.entity.ExportSysNewsStorageEntity;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.model.SysNews;
@Transactional
public interface NewsService {
	/**
	 * 根据Pk查询资讯
	 * @param pk
	 * @return
	 */
	SysNewsExtDto getSysNewsByPk(String pk);
	/**
	 * 根据Pk查询资讯分类
	 * @param pk
	 * @return
	 */
	List<SysCategoryExtDto> getCategorys(String pk);
	/**
	 * 更新资讯
	 * @param sysNews
	 * @param categoryPk
	 * @return
	 */
	int updateSysNews(SysNews sysNews, String categoryPk);
	/**
	 * 更新资讯状态
	 * @param sysNews
	 * @return
	 */
	int updateSysNewsStatus(SysNews sysNews);

	/**
	 * 更新资讯推送状态，并推送资讯到app
	 * @param sysNews
	 * @return
	 */
	int updateNewsPushStatus(SysNews sysNews);
	/**
	 * 搜索资讯列表
	 * @param qm
	 * @return
	 */
	PageModel<SysNewsStorageEntity> searchSysNewsStorageList(QueryModel<SysNewsStorageEntity> qm);
	/**
	 * 搜索资讯分类列表
	 * @param qm
	 * @return
	 */
	//List<ExportSysNewsStorageEntity> searchExportSysNewsStorageList(SysNewsStorageEntity qm);

}
