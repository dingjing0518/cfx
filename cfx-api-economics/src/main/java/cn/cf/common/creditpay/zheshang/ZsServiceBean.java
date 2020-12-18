package cn.cf.common.creditpay.zheshang;

import java.util.HashMap;
import java.util.Map;

import cn.cf.property.PropertyConfig;

import com.koalii.svs.SvsSign;
import com.ruimin.intfin.sdk.config.EntAppConfig;
import com.ruimin.intfin.sdk.service.OpenApiService;
import com.ruimin.intfin.sdk.service.impl.OpenApiServiceImpl;

public class ZsServiceBean {
	 public Map<String, EntAppConfig> getConfs()
	  {
	    return this.confs;
	  }
	  
	  public Map<String, SvsSign> getSvsSigs()
	  {
	    return this.svsSigs;
	  }
	  
	  public void setSvsSigs(Map<String, SvsSign> svsSigs)
	  {
	    this.svsSigs = svsSigs;
	  }
	  
	  public void setConfs(Map<String, EntAppConfig> confs)
	  {
	    this.confs = confs;
	  }
	  
	  private Map<String, EntAppConfig> confs = new HashMap<String, EntAppConfig>();
	  private Map<String, SvsSign> svsSigs = new HashMap<String, SvsSign>();
	  
	  public OpenApiService getService()
	  {
	    OpenApiServiceImpl service = new OpenApiServiceImpl();
	    service.setAppname("default");
	    if (this.confs.size() <= 0) {
	      initConfig();
	    }
	    EntAppConfig conf = (EntAppConfig)this.confs.get(service.getAppname());
	    
	    SvsSign svsSig = (SvsSign)this.svsSigs.get(service.getAppname());
	    service.setConfig(conf);
	    service.setSvsSig(svsSig);
	    return service;
	  }
	  
	  public OpenApiService getService(String appname)
	  {
	    OpenApiServiceImpl service = new OpenApiServiceImpl();
	    service.setAppname(appname);
	    if (this.confs.size() == 0) {
	      initConfig();
	    }
	    EntAppConfig conf = (EntAppConfig)this.confs.get(service.getAppname());
	    
	    SvsSign svsSig = (SvsSign)this.svsSigs.get(service.getAppname());
	    service.setConfig(conf);
	    service.setSvsSig(svsSig);
	    return service;
	  }
	  
	  public void initConfig()
	  {
	    try
	    {
	      EntAppConfig conf = new EntAppConfig();
	      conf.setAppid(PropertyConfig.getProperty("zs_appid"));
	      conf.setOpenid(PropertyConfig.getProperty("zs_openid"));
	      conf.setBaseurl(PropertyConfig.getProperty("zs_baseurl"));
	      conf.setFileurl(PropertyConfig.getProperty("zs_fileurl"));
	      conf.setHgtwurl(PropertyConfig.getProperty("zs_hgtwurl"));
	      
	      conf.setAppkey(PropertyConfig.getProperty("zs_appkey"));
	      conf.setCfcafile(PropertyConfig.getProperty("zs_cfcafile"));
	      conf.setCfcapwd(PropertyConfig.getProperty("zs_cfcapwd"));
	      
	      String timeout = PropertyConfig.getProperty("zs_tokentimeout");
	      if ((timeout != null) && ( !"".equals(timeout.trim()))) {
	        conf.setTokenTimeOut(Long.parseLong(timeout));
	      }else{
	    	  conf.setTokenTimeOut(1140000L);
	      }
	      this.confs.put("default", conf);
	      if ((conf.getCfcafile() != null) && (!"".equals(conf.getCfcafile().trim())))
	      {
	        SvsSign svsSig = new SvsSign();
	        svsSig.initSignCertAndKey(conf.getCfcafile(), conf.getCfcapwd());
	        this.svsSigs.put("default", svsSig);
	      }
	      String appnames = PropertyConfig.getProperty("app.names");
	      if ((appnames != null) && (appnames.indexOf(',') > 1))
	      {
	        String[] names = appnames.split(",");
	        for (String name : names)
	        {
	          EntAppConfig appConf = new EntAppConfig();
	          appConf.setAppid(PropertyConfig.getProperty("app." + name + ".appid"));
	          appConf.setOpenid(PropertyConfig.getProperty("app." + name + ".openid"));
	          appConf.setBaseurl(PropertyConfig.getProperty("baseurl"));
	          appConf.setFileurl(PropertyConfig.getProperty("fileurl"));
	          appConf.setHgtwurl(PropertyConfig.getProperty("hgtwurl"));
	          
	          appConf.setAppkey(PropertyConfig.getProperty("app." + name + ".appkey"));
	          appConf.setCfcafile(PropertyConfig.getProperty("app." + name + ".cfcafile"));
	          appConf.setCfcapwd(PropertyConfig.getProperty("app." + name + ".cfcapwd"));
	          
	          String apptimeout = PropertyConfig.getProperty("app." + name + ".tokenTimeOut");
	          if ((apptimeout != null) && ( !"".equals(apptimeout.trim()))) {
	            appConf.setTokenTimeOut(Long.parseLong(apptimeout));
	          }else{
	        	  appConf.setTokenTimeOut(1140000L);
	          }
	          this.confs.put(name, appConf);
	          if ((appConf.getCfcafile() != null) && (!"".equals(appConf.getCfcafile().trim())))
	          {
	            SvsSign sig = new SvsSign();
	            sig.initSignCertAndKey(conf.getCfcafile(), conf.getCfcapwd());
	            this.svsSigs.put(name, sig);
	          }
	        }
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
}
