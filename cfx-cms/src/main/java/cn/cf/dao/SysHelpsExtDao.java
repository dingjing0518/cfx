package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysHelpsExtDto;

public interface SysHelpsExtDao extends SysHelpsDao{

	int searchGridExtCount(Map<String, Object> map);

	List<SysHelpsExtDto> searchGridExt(Map<String, Object> map);

	int deleteHelps(String pk);

	int getByMaxPk();

}
