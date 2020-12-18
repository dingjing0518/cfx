package cn.cf;




import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;




@RestController  
public class ErrorHandlerController implements ErrorController {  
  
    /** 
     * 出异常后进入该方法，交由下面的方法处理 
     */  
    @Override  
    public String getErrorPath() {  
        return "/error";  
    }  
  
    @RequestMapping("/error")  
    public String error() {  
    	JSONObject js = new JSONObject();
		js.put("msg", "服务器内部错误");
		js.put("code", "500");
		return js.toString();
    }  
}  
