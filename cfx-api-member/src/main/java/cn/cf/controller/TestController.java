package cn.cf.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.entity.CustomerDataImport;
import cn.cf.service.MemberFacadeService;
import cn.cf.util.OSSUtils;

@RestController
@RequestMapping("/member")
public class TestController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MemberFacadeService memberFacadeService;
	
	@RequestMapping("test")
	public String test(){

		Query qu = new Query(Criteria.where("status").is(
				1));
		List<CustomerDataImport> list = mongoTemplate.find(qu,
				CustomerDataImport.class);
		if(null != list && list.size()>0){
			for(CustomerDataImport dto : list){
				RestCode code = memberFacadeService.customerImport(dto.getUrl(), dto.getStorePk());
				Update update = new Update();
				if(RestCode.CODE_0000.getCode().equals(code.getCode())){
					update.set("status", 2);//成功
				}else{
					update.set("status", 3);//失败
					update.set("remark", code.getMsg());
				}
				mongoTemplate.upsert(new Query(Criteria.where("id").is(dto.getId())),
						update, CustomerDataImport.class);
			}
		}
	
		return RestCode.CODE_0000.toJson();
	}
	
	public static void main(String[] args) {
		System.out.println(OSSUtils.ossMangerUpload(new File("D://360极速浏览器下载//测试会员绑定，总公司及其子公司归属起会员子公司.zip"), 6));

	}
}
