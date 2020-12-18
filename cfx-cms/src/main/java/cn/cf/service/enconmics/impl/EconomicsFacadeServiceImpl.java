package cn.cf.service.enconmics.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.service.enconmics.CustomerService;
import cn.cf.service.enconmics.EconomicsFacadeService;

@Service
public class EconomicsFacadeServiceImpl implements EconomicsFacadeService {
    @Autowired
    private TaskService taskService;

    @Autowired
    private CustomerService customerService;

    @Override
    public PageModel<B2bEconomicsCustomerDto> searchCustomer(int start, int limit, String groupId, String accountPk) {
        long total = 0l;
        PageModel<B2bEconomicsCustomerDto> pm = new PageModel<B2bEconomicsCustomerDto>();
        List<B2bEconomicsCustomerDto> dataList = new ArrayList<B2bEconomicsCustomerDto>();
        if (groupId != null) {
            // 获取总记录数
            total = taskService.createTaskQuery().processDefinitionKey("economicsAuditProcess")
                    // 根据用户id查询
                    .taskCandidateGroup(groupId).count(); // 获取总记录数
            List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("economicsAuditProcess").taskCandidateGroup(groupId).orderByTaskCreateTime().desc()
                    // 返回带分页的结果集合
                    .listPage(start, limit);
            Map<String, Object> map = new HashMap<>();
            for (Task t : taskList) {
                String customerPk = (String) taskService.getVariable(t.getId(), "economCustPk");
                map.put("pk", customerPk);
                B2bEconomicsCustomerExtDto customerExt = customerService.getByMap(map);
                if (customerExt != null) {
                    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVING_COL_COM_NAME)) {
                        customerExt.setCompanyName(CommonUtil.hideCompanyName(customerExt.getCompanyName()));
                    }
                    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVING_COL_CONTACTS)) {
                        customerExt.setContacts(CommonUtil.hideContacts(customerExt.getContacts()));
                    }
                    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_APPROVAL_APPROVING_COL_CONTACTSTEL)) {
                        customerExt.setContactsTel(CommonUtil.hideContactTel(customerExt.getContactsTel()));
                    }
                    customerExt.setTaskId(t.getId());
                    dataList.add(customerExt);
                }
            }
        }
        pm.setDataList(dataList);
        pm.setTotalCount((int) total);
        return pm;
    }
    
  

}
