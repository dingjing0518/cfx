package cn.cf.controller.economics;

import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.json.JsonUtils;
import cn.cf.util.ServletUtils;

/**
 * 流程部署管理
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class DeployController extends BaseController {

    @Resource
    private RepositoryService repositoryService;

    /**
     * 页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/deployIndex")
    public ModelAndView account(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/deployIndex");
        return mav;
    }

    /**
     * 流程部署列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/deployPage")
    @ResponseBody
    public String deployPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String s_name = ServletUtils.getStringParameter(request, "name", "");

        PageModel<Deployment> pm = new PageModel<Deployment>();
        List<Deployment> deployList = repositoryService.createDeploymentQuery()// 创建流程查询实例
                .orderByDeploymenTime().desc() // 降序
                .deploymentNameLike("%" + s_name + "%") // 根据Name模糊查询
                .listPage(start, limit);
        Long deployCount = repositoryService.createDeploymentQuery().deploymentNameLike("%" + s_name + "%").count();
        pm.setDataList(deployList);
        pm.setTotalCount(deployCount.intValue());
        
        return JsonUtils.convertToStringEx(pm, new String[] { "resources" });
        /*  JSONObject jo = new JSONObject();
        
       SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().add("resources");
        
        jo.put("total", pm.getTotalCount() == 0 ? pm.getDataList().size() : pm.getTotalCount());
        jo.put("page",  pm.getPageNo());
        jo.put("rows", JSONObject.parseArray(JSONObject.toJSONString(pm.getDataList(),filter)));
  
       
        return JSONObject.toJSONString(jo);*/
        
      

    }

    /**
     * 删除
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delDeploy")
    @ResponseBody
    public String delDeploy(HttpServletResponse response, HttpServletRequest request) throws Exception {

        String id = ServletUtils.getStringParameter(request, "id", "");
        repositoryService.deleteDeployment(id, true);
        String msg = Constants.RESULT_SUCCESS_MSG;
        return msg;

    }

    /**
     * 编辑/新增
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addDeploy")
    @ResponseBody
    public String addDeploy(HttpServletResponse response, @RequestParam(value = "deployFile") MultipartFile deployFile) throws Exception {
        repositoryService.createDeployment() // 创建部署
                .name(deployFile.getOriginalFilename()) // 需要部署流程名称
                .addZipInputStream(new ZipInputStream(deployFile.getInputStream()))// 添加ZIP输入流
                .deploy();// 开始部署
        String msg = Constants.RESULT_SUCCESS_MSG;
        return msg;

    }

}
