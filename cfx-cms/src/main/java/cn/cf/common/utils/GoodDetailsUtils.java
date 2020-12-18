package cn.cf.common.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import cn.cf.dto.B2bGoodsDto;
import cn.cf.util.StringUtils;

public class GoodDetailsUtils {

	/*
     * 将GoodDetails 转换成document 将GoodDetails 的值设置到document里面去...
     */
    public static Document GoodDetailsToDocument(B2bGoodsDto goodDetails) {

        Document document = new Document();

        StringField idfield = new StringField("id", goodDetails.getPk(), Store.YES);
//        TextField goodNamefield=null;
        TextField companyNamefield =null;
        TextField brandNamefield = null;
        TextField storeNamefield = null;
//        TextField productNamefield = null;
//        TextField specNamefield = null;
//        TextField seriesNamefield = null;
//        TextField gradeNamefield = null;
//        TextField varietiesNamefield = null;
//        TextField seedvarietyNamefield = null;
//
        TextField brandPkfield=null;
//        TextField productPkfield=null;
//        TextField specPkfield=null;
//        TextField seriesPkfield=null;
//        TextField gradePkfield=null;
//        TextField varietiesPkfield=null;
//        TextField seedvarietyPkfield=null;
//
//        TextField plantNamefield=null;
//        TextField wareNamefield=null;
//        TextField batchNumberfield=null;
//        TextField packNumberfield=null;
//        TextField specificationsfield = null;

        
//        if(!StringUtils.isBlank(goodDetails.getPlantName())){
//
//        	  plantNamefield = new TextField("plantName", goodDetails.getPlantName(),Store.YES);
//         }
//          if(!StringUtils.isBlank(goodDetails.getWareName())){
//
//        	  wareNamefield = new TextField("wareName", goodDetails.getWareName(),Store.YES);
//         }
//
//          if(!StringUtils.isBlank(goodDetails.getPackNumber())){
//
//        	  packNumberfield = new TextField("packNumber", goodDetails.getPackNumber(),Store.YES);
//         }
//
//          if(!StringUtils.isBlank(goodDetails.getBatchNumber())){
//
//        	  batchNumberfield = new TextField("batchNumber", goodDetails.getBatchNumber(),Store.YES);
//         }
//        if(!StringUtils.isBlank(goodDetails.getProductPk())){
//
//            productPkfield = new TextField("productPk", goodDetails.getProductPk(),Store.YES);
//        }
//
//
//        if(!StringUtils.isBlank(goodDetails.getSpecPk())){
//
//            specPkfield = new TextField("specPk", goodDetails.getSpecPk(),Store.YES);
//        }
//
//
//        if(!StringUtils.isBlank(goodDetails.getSeriesPk())){
//
//            seriesPkfield = new TextField("seriesPk", goodDetails.getSeriesPk(),Store.YES);
//        }
//
//
//        if(!StringUtils.isBlank(goodDetails.getGradePk())){
//
//            gradePkfield = new TextField("gradePk", goodDetails.getGradePk(),Store.YES);
//        }
//
//
//        if(!StringUtils.isBlank(goodDetails.getVarietiesPk())){
//
//            varietiesPkfield = new TextField("varietiesPk", goodDetails.getVarietiesPk(),Store.YES);
//        }
//
//
//        if(!StringUtils.isBlank(goodDetails.getSeedvarietyPk())){
//
//            seedvarietyPkfield = new TextField("seedvarietyPk", goodDetails.getSeedvarietyPk(),Store.YES);
//        }
//
//        if(!StringUtils.isBlank(goodDetails.getProductName())){
//
//            productNamefield = new TextField("productName", goodDetails.getProductName(),Store.YES);
//        }
//
//
//        if(!StringUtils.isBlank(goodDetails.getSpecName())){
//
//            specNamefield = new TextField("specName", goodDetails.getProductName(),Store.YES);
//        }
//
//        if(!StringUtils.isBlank(goodDetails.getSeriesName())){
//
//            seriesNamefield = new TextField("seriesName", goodDetails.getSeriesName(),Store.YES);
//        }
//
//        if(!StringUtils.isBlank(goodDetails.getSeriesName())){
//
//            gradeNamefield = new TextField("gradeName", goodDetails.getGradeName(),Store.YES);
//        }
//
//        if(!StringUtils.isBlank(goodDetails.getVarietiesName())){
//
//            varietiesNamefield = new TextField("varietiesName", goodDetails.getVarietiesName(),Store.YES);
//        }
//
//        if(!StringUtils.isBlank(goodDetails.getSeedvarietyName())){
//
//            seedvarietyNamefield = new TextField("seedvarietyName", goodDetails.getSeedvarietyName(),Store.YES);
//        }
//
//        if(!StringUtils.isBlank(goodDetails.getSpecifications())){
//
//            specificationsfield = new TextField("specifications", goodDetails.getSpecifications(),Store.YES);
//        }
        
        
        if(!StringUtils.isBlank(goodDetails.getBrandPk())){
        	
        	brandPkfield = new TextField("brandPk", goodDetails.getBrandPk(),Store.YES);
         }

         //TODO
//        if(!StringUtils.isBlank(goodDetails.getName())){
//
//        	  goodNamefield = new TextField("name", goodDetails.getName(),Store.YES);
//        }
        
        if(!StringUtils.isBlank(goodDetails.getCompanyName())){
        	
        	companyNamefield = new TextField("companyName", goodDetails.getCompanyName(),Store.YES);
        }
       
        
        if(!StringUtils.isBlank(goodDetails.getBrandName())){
        	
        	brandNamefield = new TextField("brandName", goodDetails.getBrandName(),Store.YES);
        }
        
        
       if(!StringUtils.isBlank(goodDetails.getStoreName())){
        	
    	   storeNamefield = new TextField("storeName", goodDetails.getStoreName(),Store.YES);
        }

        
        document.add(idfield);

//        if(null!=goodNamefield){
//        	document.add(goodNamefield);
//        }
        
        if(null!=companyNamefield){
        	document.add(companyNamefield);
        }
        
        if(null!=brandNamefield){
        	document.add(brandNamefield);
        }
        
        if(null!=storeNamefield){
        	document.add(storeNamefield);
        }
    
        
//        if(null!=productNamefield){
//        	document.add(productNamefield);
//        }
//
//        if(null!=specNamefield){
//        	document.add(specNamefield);
//        }
//
//        if(null!=seriesNamefield){
//        	document.add(seriesNamefield);
//        }
//
//        if(null!=gradeNamefield){
//        	document.add(gradeNamefield);
//        }
//
//        if(null!=varietiesNamefield){
//        	document.add(varietiesNamefield);
//        }
//
//        if(null!=seedvarietyNamefield){
//        	document.add(seedvarietyNamefield);
//        }
//
//        if(null!=productPkfield){
//            document.add(productPkfield);
//        }
//
//        if(null!=specPkfield){
//            document.add(specPkfield);
//        }
//
//        if(null!=seriesPkfield){
//            document.add(seriesPkfield);
//        }
//
//        if(null!=gradePkfield){
//            document.add(gradePkfield);
//        }
//
//        if(null!=varietiesPkfield){
//            document.add(varietiesPkfield);
//        }
//
//        if(null!=seedvarietyPkfield){
//            document.add(seedvarietyPkfield);
//        }
//
//        if(null!=plantNamefield){
//            document.add(plantNamefield);
//        }
//
//        if(null!=wareNamefield){
//            document.add(wareNamefield);
//        }
//        if(null!=batchNumberfield){
//            document.add(batchNumberfield);
//        }
//
//        if(null!=packNumberfield){
//            document.add(packNumberfield);
//        }
//
//        if(null!=specificationsfield){
//            document.add(specificationsfield);
//        }
//
        if(null!=brandPkfield){
        	document.add(brandPkfield);
        }
        
        return document;
    }
}
