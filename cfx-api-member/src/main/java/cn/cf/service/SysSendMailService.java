package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.common.SendMails;


public interface SysSendMailService {

	PageModel<SendMails> searchsendMailList(Map<String, Object> map);


	RestCode updateIsRead(String id);

}
