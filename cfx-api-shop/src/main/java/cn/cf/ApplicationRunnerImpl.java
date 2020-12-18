package cn.cf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.cf.dao.B2bBrandDaoEx;
import cn.cf.dao.B2bGradeDaoEx;
import cn.cf.dao.B2bProductDaoEx;
import cn.cf.dao.B2bSpecDaoEx;
import cn.cf.dao.SysPropertyDao;
import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bGradeDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bSpecDto;
import cn.cf.dto.SysPropertyDto;
import cn.cf.property.PropertyConfig;
import cn.cf.util.FileUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * springboot启动完成之后执行
 * @description:
 * @author FJM
 * @date 2019-9-11 下午1:03:12
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
	
	@Autowired
	private B2bProductDaoEx productDaoEx;
	
	@Autowired
	private B2bSpecDaoEx b2bSpecDaoEx;
	
	@Autowired
	private B2bGradeDaoEx b2bGradeDaoEx;
	
	@Autowired
	private B2bBrandDaoEx b2bBrandDaoEx;
	
	@Autowired
	private SysPropertyDao propertyDao;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 品名 、规格大类 、规格系列 、等级 、品牌 生成json文件
	 */
    @Override
    public void run(ApplicationArguments args) throws Exception {
    	//只查系统的
    	Map<String, Object> pmap = new HashMap<String, Object>();
    	pmap.put("type", 1);
    	List<SysPropertyDto> properties = propertyDao.searchList(pmap);
    	if(null != properties && properties.size()>0){
    		for(SysPropertyDto p : properties){
    			PropertyConfig.init(p.getContent());
    		}
    		logger.info("------------>properties属性加载完毕!");
    	}
    	
    	logger.info("------------>goodsinfo文件开始创建!");
    	Map<String,Object> map = new HashMap<String, Object>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        map.put("orderName", "LENGTH(name)");
        map.put("orderType", "desc");
        List<B2bProductDto> plist = productDaoEx.searchGrid(map);
        
        List<B2bGradeDto> glist = b2bGradeDaoEx.searchGrid(map);
        
        List<B2bBrandDto> blist = b2bBrandDaoEx.searchGrid(map);
        
        List<B2bSpecDto> seList = b2bSpecDaoEx.searchChList(map);
        map.put("parentPk", "-1");
        List<B2bSpecDto> eList = b2bSpecDaoEx.searchGrid(map);
        JSONObject json = new JSONObject();
        List<Map<String,Object>> mlist = new ArrayList<Map<String,Object>>();
        Map<String,Object> m = new HashMap<String, Object>();
        if(null != plist && plist.size()>0){
        	for(B2bProductDto p : plist){
        		if(null == p.getName() || "".equals(p.getName())){
        			continue;
        		}
        		m = new HashMap<String, Object>();
        		m.put("pk", p.getPk());
        		m.put("name", p.getName());
        		mlist.add(m);
        	}
        	json.put("productPk", mlist);
        }
        if(null != eList && eList.size()>0){
        	mlist = new ArrayList<Map<String,Object>>();
        	for(B2bSpecDto s : eList){
        		if(null == s.getName() || "".equals(s.getName())){
        			continue;
        		}
        		m = new HashMap<String, Object>();
        		m.put("pk", s.getPk());
        		m.put("name", s.getName());
        		mlist.add(m);
        	}
        	json.put("specPk", mlist);
        }
        if(null != seList && seList.size()>0){
        	mlist = new ArrayList<Map<String,Object>>();
        	for(B2bSpecDto s : seList){
        		if(null == s.getName() || "".equals(s.getName())){
        			continue;
        		}
        		m = new HashMap<String, Object>();
        		m.put("pk", s.getPk());
        		m.put("name", s.getName());
        		m.put("parentPk", s.getParentPk());
        		mlist.add(m);
        	}
        	json.put("seriesPk", mlist);
        }
        if(null != glist && glist.size()>0){
        	mlist = new ArrayList<Map<String,Object>>();
        	for(B2bGradeDto g : glist){
        		if(null == g.getName() || "".equals(g.getName())){
        			continue;
        		}
        		m = new HashMap<String, Object>();
        		m.put("pk", g.getPk());
        		m.put("name", g.getName());
        		mlist.add(m);
        	}
        	json.put("gradePk", mlist);
        }
        if(null != blist && blist.size()>0){
        	mlist = new ArrayList<Map<String,Object>>();
        	for(B2bBrandDto b : blist){
        		if(null == b.getName() || "".equals(b.getName())){
        			continue;
        		}
        		m = new HashMap<String, Object>();
        		m.put("pk", b.getPk());
        		m.put("name", b.getName());
        		mlist.add(m);
        	}
        	json.put("brandPk", mlist);
        }
        FileUtil.writeTxtFile(json.toJSONString(), new File(PropertyConfig.getProperty("goods_json")));
        logger.info("------------>goodsinfo文件生成完毕!");
    }
}
