package cn.cf.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bGoodsService;
import cn.cf.util.ComparatorByLength;
import cn.cf.util.RegexUtils;
import cn.cf.util.StringUtils;

@Component
public class LuceneDao {
	@Autowired
	private B2bGoodsService b2bGoodsService;
	private static Map<String, Object> setSuccess;
	private static String Flag="";
  

	 
	public     String luceneBySearchKey(String searchKey){
		String str="";
		int len=searchKey.length();
		if(len>0){
			try {
				str=Contrast(searchKey);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(str.length()==0){
				str= " and goodsInfo is null";
			} 
		}else{
			return " and goodsInfo is null";
		}
// 		System.out.println(searchKey+"---------------"+str);
		
		
		
		return str;
		
	}

 
 
	 
//public static void main(String[] args) {
// 	 String str="包装物 新增商家品310D/312F  1/6poy123D12  3d456/1 23D/12ad/33 22/55f 77d/88f 450d 175d a174dxx a1888faaa   122a4455x"; 
//  	   str="poy PTT(半消光)";
//	 try {
//		 Contrast(str);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}

@SuppressWarnings({ "deprecation"})
private  static      String Contrast(String searchKey) throws IOException    {
	 setSuccess=new HashMap<String, Object>();
	File jsonFile = new File(PropertyConfig.getProperty("goods_json"));
	String json= FileUtils.readFileToString(jsonFile);//json对比文件
	JSONObject jsonObject = JSONObject.fromObject(json);//转json格式
	String endStr="";//返回字段
	Map<String, Object>map=searchMapAll(searchKey);
	String [] data =StringUtils.splitMap(map.get("data").toString());
	String productStr="";
	String brandStr="";
	String gradeStr="";
	String specStr="";
	String seriesStr="";
	for(int i=0;i<data.length;i++){
		String dataStr=data[i].trim();
		Flag=dataStr;
		String pts=searchProducts(jsonObject.getJSONArray("productPk"),dataStr);//绝对匹配
		if(!"".equals(pts)){
			productStr+=pts;
		}
		String bts=searchBrands(jsonObject.getJSONArray("brandPk"),dataStr);
		if(!"".equals(bts)){
			brandStr+=bts;
			
		}
		if(map.containsKey(dataStr)){
			Map<String, Object> specMap=StringUtils.stringToMap(map.get(dataStr).toString());
			if(specMap.containsKey("series")){//规格系列 常规格式
        		String sStr=findseriesArray(jsonObject.getJSONArray("seriesPk"),specMap.get("series").toString());
        		if(!"".equals(sStr)){
        			seriesStr+="\""+sStr+"\",";
        		}
			}else if(specMap.containsKey("seriesF")){//xxxxf格式
        		String sStr=findseriesArray(jsonObject.getJSONArray("seriesPk"),specMap.get("seriesF").toString());
        		if(!"".equals(sStr)){
        			seriesStr+="\""+sStr+"\",";
        		}
			}else if(specMap.containsKey("num")){//纯数字格式
				String numF=specMap.get("num").toString();
				String  num=findseriesArray(jsonObject.getJSONArray("seriesPk"),numF);
				if("".equals(num)){//规格系列匹配不到，就匹配规格系列
					numF=replaceToD(numF);//F替换D
					String  sStr=findseriesArray(jsonObject.getJSONArray("specPk"),numF);
					if(!"".equals(sStr)){
						specStr+="\""+sStr+"\",";
						
					}
				}else{
					seriesStr+="\""+num+"\",";
				}
			}else if(specMap.containsKey("spec")){//匹配规格大类
				String spStr=findseriesArray(jsonObject.getJSONArray("specPk"),specMap.get("spec").toString());
				if(!"".equals(spStr)){
					specStr+="\""+spStr+"\",";
					
				}
			}
		}
		if(!"".equals(Flag)){
			String gStr=searchGrades(jsonObject.getJSONArray("gradePk"));
			if(!"".equals(gStr)){
				gradeStr+="\""+gStr+"\",";
				
			}
		}

//		Flag=RegexUtils.getSpecialText(Flag);//过滤特殊符号
//		 if(Flag.matches(RegexUtils.REGEX_PLUS_OR_MINUD)){//只含+或-  则不返回数据  
//	    	   Flag="";
//	       }
//		if(Flag.length()>3){
//			endSet.add(Flag);
//		}else if(!"".equals(Flag)){
//			minStr+=Flag+"|";
//		}
		}
	
	if(!"".equals(productStr)){
		productStr=productStr.substring(0, productStr.length()-1);
			endStr+="  SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"productPk\":',1),'\"productPk\":\"'),''),'\"',1)  in ("+productStr+") ";
			
	}
	if(!"".equals(brandStr)){
		brandStr=brandStr.substring(0, brandStr.length()-1);
		if(endStr.length()>0){
			endStr+=" and ";
		}
		endStr+=" b.brandPk IN    ("+brandStr+")  ";
		 
	}
	if(!"".equals(gradeStr)){
		gradeStr=gradeStr.substring(0, gradeStr.length()-1);
		if(endStr.length()>0){
			endStr+=" and ";
		}
		endStr+="   SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"gradePk\":',1),'\"gradePk\":\"'),''),'\"',1)  in ("+gradeStr+") ";
	}
 	if(endStr.length()>0){
		endStr=" ( "+endStr+" )";
	}
	if(!"".equals(specStr)){
		specStr=specStr.substring(0, specStr.length()-1);
		 if(endStr.length()>0){
			 endStr+="  or ";
		 }
		endStr+="    SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"specPk\":',1),'\"specPk\":\"'),''),'\"',1)  in ("+specStr+") ";
	}
	if(!"".equals(seriesStr)){
		seriesStr=seriesStr.substring(0, seriesStr.length()-1);
		 if(endStr.length()>0){
			 endStr+="  or ";
		 }
		endStr+="    SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"seriesPk\":',1),'\"seriesPk\":\"'),''),'\"',1)  in ("+seriesStr+") ";
	}
	 if(endStr.length()>0){
		 endStr+="  or ";
	 }
	endStr+="     SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"packNumber\":',1),'\"packNumber\":\"'),''),'\"',1) like    '%"+searchKey+"%'"
    		+" or SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"batchNumber\":',1),'\"batchNumber\":\"'),''),'\"',1)  like    '%"+searchKey+"%'"
    		+" or SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"varietiesName\":',1),'\"varietiesName\":\"'),''),'\"',1)  like    '%"+searchKey+"%'"
    		+" or SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"specifications\":',1),'\"specifications\":\"'),''),'\"',1) like   '%"+searchKey+"%' ";
	 
	endStr=" and ("+endStr+" )";
//	if(!"".equals(endStr)){
//		endStr=" and ( "+endStr+" )";
//	}
//	if(endSet.size()>0){
//		for (String e:endSet) {
//			e=SynonymAnalyzerUtil.analyzeChinesefour(e);
//			e=e.replace(" ", "|").trim();
//			e=e.substring(0, e.length()-1);
//			minStr+=e;
//		}
//
//	}else{
//		if(minStr.length()>0){
//			minStr=minStr.substring(0,minStr.length()-1);
//		}
//		
//	}
//	
//	if(minStr.length()>0){
//		if(minStr.matches(RegexUtils.REGEX_CONTSIN_SPECIAL)){//特殊符号需转义
//			minStr=editRegexSpecialSymbol(minStr);//转义
//		}
//		endStr+="  and ( SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"packNumber\":',1),'\"packNumber\":\"'),''),'\"',1) REGEXP   '"+minStr+"'"
//		    		+" or SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"batchNumber\":',1),'\"batchNumber\":\"'),''),'\"',1) REGEXP   '"+minStr+"'"
//		    		+" or SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"varietiesName\":',1),'\"varietiesName\":\"'),''),'\"',1) REGEXP   '"+minStr+"'"
//		    		+" or SUBSTRING_INDEX(REPLACE(goodsInfo,CONCAT(SUBSTRING_INDEX(goodsInfo,'\"specifications\":',1),'\"specifications\":\"'),''),'\"',1) REGEXP   '"+minStr+"' )";
//
//	}
	return endStr;
	}

 
  


private static String searchGrades(JSONArray jsonArray) {
	String  gradeReplace=matchesTrue(Flag);//剩余字符过滤规格格式数据
	//提取中文
    Set<String> Text=StringUtils.getlist(gradeReplace, RegexUtils.REGEX_TEXT);
    Iterator<String> it = Text.iterator();
	while (it.hasNext()) {
		String end = it.next();
		gradeReplace=gradeReplace.replace(end, "");
	}
	String gStr=findArrayForAbsoluteMatching(jsonArray,gradeReplace);//等级
	return gStr;
}




/***
 * 品名优先匹配   1：精确匹配文件     2：前者不行，则提取英文，英文-英文，中文，分别精确匹配
 * @param jsonArray
 * @param dataStr
 * @return
 */
private static String searchProducts(JSONArray jsonArray, String dataStr) {
	dataStr=matchesTrue(dataStr);
	String str="";
	String pStr=findArrayForAbsoluteMatching(jsonArray,dataStr);//品名
	if(!"".equals(pStr)){
		str+="\""+pStr+"\",";

	}else{
		//提取英文和有英文-英文组合的
		Set<String> products=RegexUtils.getlist(dataStr, RegexUtils.REGEX_LETTER_LETTER);
		//提取中文
	    Set<String> pText=StringUtils.getlist(dataStr, RegexUtils.REGEX_TEXT);
	    products.addAll(pText);
		if(products.size()>0){
			Iterator<String> it = products.iterator();
			while (it.hasNext()) {
				String pro=findArrayForAbsoluteMatching(jsonArray,it.next());//品名
				if(!"".equals(pro)){
					str+="\""+pro+"\",";
				}
			}
		}
		 
	}
	return str;
}
private static String findArrayForAbsoluteMatching(JSONArray jsonArray, String keyStr) {
	String str="";
	if(!"".equals(keyStr)){
		for (int i = 0; i < jsonArray.size(); i++) {
			 JSONObject job = jsonArray.getJSONObject(i);  
			   String name= job.getString("name"); 
			 if(keyStr.equalsIgnoreCase(name)){
				   str=job.getString("pk");
				   if(Flag.toUpperCase().indexOf(name.toUpperCase())!=-1){
					   int index=Flag.toUpperCase().indexOf(name.toUpperCase());
				   if(Flag.length()>0){
					   if(index!=-1){
						   Flag=Flag.substring(0, index)+Flag.substring(index+name.length(), Flag.length());
					       if(Flag.matches(RegexUtils.REGEX_PLUS_OR_MINUD)){
					    	   Flag="";
					       }
					   }
					   
				   }
				   }
				   
				   break;
			   }
			   
			 
		}
	}


	return str;
	
}



/***
 * 品牌提取中文，模糊匹配  ，将文件中所有相似的匹配出来
 * @param jsonArray
 * @param keyStr
 * @return
 */
private static String searchBrands(JSONArray jsonArray, String dataStr) {
	String str="";
	Set<String> brands=StringUtils.getlist(dataStr, RegexUtils.REGEX_TEXT);//提取中文
	if(brands.size()>0){
		Iterator<String> it = brands.iterator();
		while (it.hasNext()) {
			String bra=findArrayForBrands(jsonArray,it.next());//品牌
			if(!"".equals(bra)){
				str+=bra;
			}
		}
	}
	return str;
}

private static String findArrayForBrands(JSONArray jsonArray, String keyStr) {
	String str="";
	String namelengh="";
	if(!"".equals(keyStr)){
		for (int i = 0; i < jsonArray.size(); i++) {
			 JSONObject job = jsonArray.getJSONObject(i);  
			   String name= job.getString("name"); 
			   if(keyStr.toUpperCase().indexOf(name.toUpperCase())!=-1||name.toUpperCase().indexOf(keyStr.toUpperCase())!=-1){
//				   str=job.getString("pk");
				   str+="\""+job.getString("pk")+"\",";
				   if(name.length()>namelengh.length()){
					   namelengh=name;
				   }
			   }
		}
		   if(Flag.toUpperCase().indexOf(namelengh.toUpperCase())!=-1){
			   int index=Flag.toUpperCase().indexOf(namelengh.toUpperCase());
		   if(Flag.length()>0){
			   if(index!=-1){
				   Flag=Flag.substring(0, index)+Flag.substring(index+namelengh.length(), Flag.length());
			       if(Flag.matches(RegexUtils.REGEX_PLUS_OR_MINUD)){
			    	   Flag="";
			       }
			   }
			   
		   }
		   }
	}


	return str;
}




private static String replaceToD(String numF) {
	if(!"".equals(numF)){
		  String[] nNumfs=StringUtils.splitMap(numF);
		    for(String fs:nNumfs){
		    	String f=fs.trim();
		    	if(f.matches(RegexUtils.REGEX_NUMBER_ANDF)){
					numF=numF.replace(f, f.substring(0, f.length()-1)+"D");
				}
		    }
	}
	
	return numF;
}

//private static String editRegexSpecialSymbol(String minStr2) {
//	 String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}" };  
//     for (String key : fbsArr) {  
//         if (minStr2.contains(key)) {  
//        	 minStr2 = minStr2.replace(key, "\\\\" + key);  
//         }  
//     }
//	return minStr2;
//}

private static String matchesTrue(String dataStr) {
	String gradeReplace=dataStr;
	Pattern p1=null;
	Matcher m1=null; 
	if(dataStr.matches(RegexUtils.REGEX_CONTSIN_NUMBER_DF)){//.*D/.*F格式 (最全的格式)
			  p1=Pattern.compile(RegexUtils.REGEX_NUMBER_DF); //提取D/.*F格式，d前面的相邻数字
			  m1=p1.matcher(dataStr); 
	 }else  if(dataStr.matches(RegexUtils.REGEX_CONTSIN_NUMBER_D_NUMBER)){//
		      p1=Pattern.compile(RegexUtils.REGEX_NUMBER_D_NUMBER);  
			  m1=p1.matcher(dataStr); 
			 
	 }else  if(dataStr.matches(RegexUtils.REGEX_CONTSIN_NUMBER_D)){
		      p1=Pattern.compile(RegexUtils.REGEX_NUMBER_D); //数字+d
			  m1=p1.matcher(dataStr); 
			 
	 }else{
		 return gradeReplace;
	 }
	while (m1.find()){
		gradeReplace=gradeReplace.replace(m1.group(0), "");
	}
	return gradeReplace;
}

private static String findseriesArray(JSONArray jsonArray,
		String keyStr) {
	 String [] keys=StringUtils.splitMap(keyStr);
	 String str="";
	 for(int i=0;i<keys.length;i++){
		 String ikey=keys[i].trim();
			for (int j = 0; j< jsonArray.size(); j++) {
				 JSONObject job = jsonArray.getJSONObject(j);   
				   String name= job.getString("name"); 
				   if(name.equalsIgnoreCase(ikey)){ 
					   str=job.getString("pk");
							 if(setSuccess.containsKey(ikey)){
								 if(Flag.length()>0){
								   Flag=Flag.replace(setSuccess.get(ikey).toString(), "");
								   if(Flag.matches(RegexUtils.REGEX_PLUS_OR_MINUD)){
							    	   Flag="";
							       }
								 }
							} 
							
			
					   break;
				   }
			}
	 }
		return str;
}

private static String findArray(JSONArray jsonArray,String keyStr) {
	String str="";
	if(!"".equals(keyStr)){
		for (int i = 0; i < jsonArray.size(); i++) {
			 JSONObject job = jsonArray.getJSONObject(i);  
			   String name= job.getString("name"); 
			 
			   if(keyStr.toUpperCase().indexOf(name.toUpperCase())!=-1||name.toUpperCase().indexOf(keyStr.toUpperCase())!=-1){
				   str=job.getString("pk");
				   if(Flag.toUpperCase().indexOf(name.toUpperCase())!=-1){
					   int index=Flag.toUpperCase().indexOf(name.toUpperCase());
				   if(Flag.length()>0){
					   if(index!=-1){
						   Flag=Flag.substring(0, index)+Flag.substring(index+name.length(), Flag.length());
					       if(Flag.matches(RegexUtils.REGEX_PLUS_OR_MINUD)){
					    	   Flag="";
					       }
					   }
					   
				   }
				   }
				   
				   break;
			   }
			   
			 
		}
	}


	return str;
	
}

private static Map<String, Object> searchMapAll(
		String searchKey) {
	Map<String, Object>map=new HashMap<String, Object>();
	Set<String> data=new HashSet<String>();	 //所有searchKey
	String [] arr = searchKey.split("\\s+");//按空格拆分
	 for(String ss : arr){
		 data.add(ss); 
		 Map<String, Object>m=addSeriesMap(ss);
		 if(m.size()>0){
			 map.put(ss, m);
		 }
		
	 }
	 map.put("data", data);
	return map;
}

private static Map<String, Object> addSeriesMap(String ss) {
	 Map<String, Object>m=new HashMap<String, Object>();
	 String specOne="";
	 String seriesStr = ""; //提取规格系列
	if(ss.matches(RegexUtils.REGEX_CONTSIN_NUMBER_DF)){//.*D/.*F格式 (最全的格式)
			Pattern p1=Pattern.compile(RegexUtils.REGEX_NUMBER_DF); //提取D/.*F格式，d前面的相邻数字
			Matcher m1=p1.matcher(ss); 
			while (m1.find()){
				specOne=m1.group(1);
				seriesStr=m1.group(1)+"D";
				break;
			}
			Pattern p2=Pattern.compile(specOne+RegexUtils.REGEX_DF); 
			Matcher m2=p2.matcher(ss); 
			while (m2.find()){
				seriesStr+="/"+m2.group(3)+"F";
				break;
			}
			if(!"".equals(seriesStr)){
				m.put("series", seriesStr);
				setSuccess.put(seriesStr, m1.group(0));
			}
	 }else  if(ss.matches(RegexUtils.REGEX_CONTSIN_NUMBER_D_NUMBER)){//
		 Pattern p1=Pattern.compile(RegexUtils.REGEX_NUMBER_D_NUMBER);  
			Matcher m1=p1.matcher(ss); 
			while (m1.find()){
				specOne=m1.group(1);
				seriesStr=m1.group(1)+"D";
				break;
			}
			Pattern p2=Pattern.compile(RegexUtils.REGEX_D_NUMBER); 
			Matcher m2=p2.matcher(ss); 
			while (m2.find()){
				seriesStr+="/"+m2.group(3)+"F";
				break;
			}
			if(!"".equals(seriesStr)){
				m.put("series", seriesStr);
				setSuccess.put(seriesStr, m1.group(0));
			}
		 }else  if(ss.matches(RegexUtils.REGEX_CONTSIN_NUMBER_D)){
		 Pattern p1=Pattern.compile("(\\d+)((?i)d)"); //数字+d
			Matcher m1=p1.matcher(ss); 
			while (m1.find()){
				seriesStr=m1.group(1)+"D";
				break;
			}
			if(!"".equals(seriesStr)){
				m.put("spec", seriesStr);
				setSuccess.put(seriesStr, m1.group(0));
			}
	 }else  if(ss.matches(RegexUtils.REGEX_CONTSIN_NUMBER_F)){//数字+f
		 Pattern p1=Pattern.compile(RegexUtils.REGEX_NUMBER_F); //提取D/.*F格式，d前面的相邻数字
			Matcher m1=p1.matcher(ss); 
			while (m1.find()){
				seriesStr=m1.group(1);
				break;
			}
			if(!"".equals(seriesStr)){
				m.put("seriesF", seriesSplit(seriesStr,m1.group(0),1));
			
			}
		
	 }else  if(ss.matches(RegexUtils.REGEX_CONTSIN_NUMBER)){//纯数字
		 Pattern p1=Pattern.compile("(\\d+)"); //提取D/.*F格式，d前面的相邻数字
			Matcher m1=p1.matcher(ss); 
			while (m1.find()){
				seriesStr=m1.group(1);
				break;
			}
			if(!"".equals(seriesStr)){
				m.put("num", seriesSplit(seriesStr,m1.group(0),2));
			}

	 }
	return m;
}
/***
 * 
 * @param groupStr
 * @param gourp0
 * @param type 1 xxxxf   2xxxx纯数字
 * @return
 */
private static   TreeSet<String> seriesSplit(String groupStr,String gourp0,int type) {
	TreeSet<String> groupSet = new TreeSet<String>(new ComparatorByLength());
	if (groupStr.length() < 3) {
		groupSet.add(groupStr + "F");
		setSuccess.put(groupStr + "F", gourp0);
		if(type==2){
			setSuccess.put(groupStr + "D", gourp0);
		}
	} else if (groupStr.length() == 6) {
		groupSet.add(groupStr.substring(0, 3) + "D/"+ groupStr.substring(3, groupStr.length()) + "F");
		setSuccess.put(groupStr.substring(0, 3) + "D/"+ groupStr.substring(3, groupStr.length()) + "F", gourp0);
	} else if (groupStr.length() > 2&&groupStr.length()<6) {
		groupSet.add(groupStr.substring(0, 2) + "D/"+ groupStr.substring(2, groupStr.length()) + "F");
		setSuccess.put(groupStr.substring(0, 2) + "D/"+ groupStr.substring(2, groupStr.length()) + "F", gourp0);
		if (groupStr.length()== 3) {
			groupSet.add(groupStr.substring(0, 3) + "F");
			setSuccess.put(groupStr.substring(0, 3) + "F", gourp0);
			if(type==2){
				setSuccess.put(groupStr.substring(0, 3) + "D", gourp0);
			}
		}else if (groupStr.length()>3) {
			groupSet.add(groupStr.substring(0, 3) + "D/"+ groupStr.substring(3, groupStr.length()) + "F");
			setSuccess.put(groupStr.substring(0, 3) + "D/"+ groupStr.substring(3, groupStr.length()) + "F", gourp0);
		}
	}
	return groupSet;
}
 
 
}
