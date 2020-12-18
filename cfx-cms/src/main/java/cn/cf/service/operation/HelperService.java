package cn.cf.service.operation;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysHelpsDto;
import cn.cf.dto.SysHelpsExtDto;

@Transactional
public interface HelperService {
	/**
	 * 搜索帮助列表
	 * @param qm
	 * @return
	 */
	public PageModel<SysHelpsExtDto> searchsysHelpdata(QueryModel<SysHelpsExtDto> qm);
	/**
	 * 修改帮助
	 * @param sysHelpsDto
	 * @return
	 */
	public int updateSysHelps(SysHelpsDto sysHelpsDto);
	/**
	 * 删除帮助
	 * @param pk
	 * @return
	 */
	public int delSysHelps(String pk);
	/**
	 * 根据pk获取帮助对象
	 * @param pk
	 * @return
	 */
	public SysHelpsDto getSysHelpsByPk(String pk);
	/**
	 * 新增帮助
	 * @param sysHelpsDto
	 * @return
	 */
	public int insertSysHelps(SysHelpsDto sysHelpsDto);

}
