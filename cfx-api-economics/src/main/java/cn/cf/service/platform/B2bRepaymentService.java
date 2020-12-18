package cn.cf.service.platform;

import cn.cf.PageModel;
import cn.cf.entity.B2bRepaymentRecord;

import java.util.Map;

/**
 * @author FJM
 * @description:
 * @date 2020/6/3
 */
public interface B2bRepaymentService {

    PageModel<B2bRepaymentRecord> searchListByPage(Map<String,Object> map);

    B2bRepaymentRecord getById(String id);
}
