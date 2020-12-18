package cn.cf.dao;

import cn.cf.dto.SysSmsTemplateDtoEx;

public interface SysSmsTemplateDaoEx extends SysSmsTemplateDao{
	
	SysSmsTemplateDtoEx getByName(String name);
}
