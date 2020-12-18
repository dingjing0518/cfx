package cn.cf.controller.economics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.json.JsonUtils;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class ProcessDefinitionController extends BaseController{
    @Autowired
    private RepositoryService repositoryService;
    
    /**
     * 页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/processDefinitionIndex")
    public ModelAndView processDefinitionIndex(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/processDefinitionIndex");
        return mav;
    }
    
    
    /**
     * 流程定义分页查询
     * @return
     * @throws Exception 
     */
    @RequestMapping("/processDefinitionPage")
    @ResponseBody
    public String processDefinitionPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String s_name = ServletUtils.getStringParameter(request, "name", "");
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        Long count=repositoryService.createProcessDefinitionQuery()
                .processDefinitionNameLike("%"+s_name+"%")
                .count();
        List<ProcessDefinition> processDefinitionList=repositoryService.createProcessDefinitionQuery()
                .orderByDeploymentId().desc()
                .processDefinitionNameLike("%"+s_name+"%")
                .listPage(start, limit);
        
        PageModel<ProcessDefinition> pm = new PageModel<ProcessDefinition>();
        pm.setDataList(processDefinitionList);
        pm.setTotalCount(count.intValue());
        return JsonUtils.convertToStringEx(pm,new String[]{"identityLinks","processDefinition"});
        //return "";
    }
}
