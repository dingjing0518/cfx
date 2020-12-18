package cn.cf.service.operation;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.dto.SysSmsTemplateDto;

public interface SmsTemplateService {
    /**
     * 短信列表
     * 
     * @param qm
     * @return
     */
    PageModel<SysSmsTemplateDto> searchsmsTemplateList(QueryModel<SysSmsTemplateDto> qm);

    /**
     * 新增/编辑短信
     * 
     * @param dto
     * @return
     */
    int updateSmsTemplate(SysSmsTemplateDto dto);

    /**
     * 获取短信模板的角色
     * 
     * @param qm
     * @param smsName
     * @return
     */
    PageModel<B2bRoleExtDto> getRoleListBySms(QueryModel<B2bRoleExtDto> qm, String smsName);

    /**
     * 新增/编辑角色
     * 
     * @param list
     * @return
     */
    int insertSmsRole(List<Map<String, Object>> list);

}
