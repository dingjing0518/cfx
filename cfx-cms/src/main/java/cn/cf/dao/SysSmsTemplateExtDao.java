package cn.cf.dao;

import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.dto.SysSmsTemplateExtDto;

import java.util.List;
import java.util.Map;

public interface SysSmsTemplateExtDao {

	SysSmsTemplateExtDto getByNames(String smsName);
	List<SysSmsTemplateDto> searchGrid(Map<String, Object> map);
	List<SysSmsTemplateDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	int update(SysSmsTemplateDto dto);
	//SysSmsTemplateExtDto getByName(String string);
}
