package cn.cf.service.manage;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.MangoBackstageInfo;
import cn.cf.entity.MangoOperationInfo;
import cn.cf.entity.SendMails;
import cn.cf.entity.Sms;

public interface SmsService {
    /**
     * 短信记录列表
     * 
     * @param qm
     * @return
     */
    PageModel<Sms> searchsmsRecordList(QueryModel<Sms> qm);

    /**
     * 站内信列表
     * 
     * @param qm
     * @return
     */
    PageModel<SendMails> searchsendMailsList(QueryModel<SendMails> qm);

    /**
     * API日志列表
     * 
     * @param qm
     * @return
     */
    PageModel<MangoOperationInfo> searchOperationApiLogsList(QueryModel<MangoOperationInfo> qm);

    /**
     * 后台日志列表
     * 
     * @param qm
     * @return
     */
    PageModel<MangoBackstageInfo> searchOperationBackLogsList(QueryModel<MangoBackstageInfo> qm);

}
