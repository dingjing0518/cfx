/**
 * 
 */
package cn.cf.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.ActionEnter;

import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dto.SysRegionsDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.InformationCenterService;
import net.sf.json.JSONObject;

/**
 * @author bin
 * 
 */
@Controller
@RequestMapping("/")
public class CommonController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	private InformationCenterService informationCenterService;
	/**
	 * 查询地区
	 * 
	 * @param request
	 * @param parentPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchSysRegionsList")
	public String updateB2bCompany(HttpServletRequest request,String parentPk) {
		List<SysRegionsDto> list =  informationCenterService.searchSysRegionsList(parentPk);
		return JsonUtils.convertToString(list);
	}

	@RequestMapping("ueditor")
	public void ueditor(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		logger.error("===============ueditor_rootPath:"+rootPath);
		String configPath = this.getClass().getClassLoader().getResource("conf").getPath();
		logger.error("===============ueditor_configPath:"+configPath);
		try {
			String exec = new ActionEnter(request, rootPath,configPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "uploadPhoto")
	public String uploadPhoto(HttpServletRequest request,
			@RequestParam(value = "file") MultipartFile file) throws Exception {
		// TODO閼惧嘲褰囨稉濠佺炊閸ュ墽澧栭崷鏉挎絻
		String path = request.getSession().getServletContext()
				.getRealPath("upload");
		String fileName = file.getOriginalFilename();
		int randomCode = (new Random()).nextInt(8999) + 1000;// 閼惧嘲褰囬梾蹇旀簚閺侊拷
		fileName = randomCode + fileName;
		File targetFile0 = new File(path, fileName);
		//
		try {
			if (!targetFile0.exists()) {
				targetFile0.mkdirs();
			}
			file.transferTo(targetFile0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (targetFile0 != null) {
				fileName = OSSUtils.ossMangerUpload(targetFile0, Constants.IMG);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", Constants.picURL + fileName);
			targetFile0.delete();
			return JsonUtils.convertToString(map);
		}
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "uploadfile")
	public String uploadfile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "file") MultipartFile file)
			throws Exception {
		JSONObject js=new JSONObject();
		String path = request.getSession().getServletContext()
				.getRealPath("upload");
		String fileName = file.getOriginalFilename();
		int randomCode = (new Random()).nextInt(8999) + 1000;// 閼惧嘲褰囬梾蹇旀簚閺侊拷
		fileName = randomCode + fileName;
		File targetFile0 = new File(path, fileName);
		//
		try {
			if (!targetFile0.exists()) {
				targetFile0.mkdirs();
			}
			file.transferTo(targetFile0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (targetFile0 != null) {
				fileName = OSSUtils.ossMangerUpload(targetFile0, Constants.FILE);

			}
			js.put("state", "SUCCESS");
			js.put("url", Constants.picURL + fileName);
			targetFile0.delete();
			return js.toString();
		}
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping("uploadDoc")
	public String uploadDoc(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "upfile") MultipartFile file)
			throws Exception {
		JSONObject js=new JSONObject();
		String path = request.getSession().getServletContext()
				.getRealPath("upload");
		String fileName = file.getOriginalFilename();
		int randomCode = (new Random()).nextInt(8999) + 1000;// 閼惧嘲褰囬梾蹇旀簚閺侊拷
		fileName = randomCode + fileName;
		File targetFile0 = new File(path, fileName);
		//
		try {
			if (!targetFile0.exists()) {
				targetFile0.mkdirs();
			}
			file.transferTo(targetFile0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			if (targetFile0 != null) {
//				fileName = OSSUtils.ossMangerUpload(targetFile0, Constants.FILE);
//
//			}
			js.put("state", "SUCCESS");
			js.put("url", fileName);
			//targetFile0.delete();
			return js.toString();
		}
	}
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
		@SuppressWarnings("finally")
		@ResponseBody
		@RequestMapping("uploadImg")
		public String uploadImg(HttpServletRequest request,
				HttpServletResponse response,
				@RequestParam(value = "upfile") MultipartFile file)
				throws Exception {
			logger.error("===============uploadImg:=============");	
			JSONObject js=new JSONObject();
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			String fileName = file.getOriginalFilename();
			int randomCode = (new Random()).nextInt(8999) + 1000;// 閼惧嘲褰囬梾蹇旀簚閺侊拷
			fileName = randomCode + fileName;
			File targetFile0 = new File(path, fileName);
			//
			try {
				if (!targetFile0.exists()) {
					targetFile0.mkdirs();
				}
				file.transferTo(targetFile0);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (targetFile0 != null) {
					fileName = OSSUtils.ossMangerUpload(targetFile0, Constants.IMG);

				}
				js.put("state", "SUCCESS");
				js.put("url", Constants.picURL + fileName);
				
				targetFile0.delete();
				return js.toString();
			}
		}
		
		
	
}
