package cn.cf.common.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import cn.cf.dto.B2bGoodsDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.util.StringUtils;

public class GoodDetailsUtils {

	/*
	 * 将GoodDetails 转换成document 将GoodDetails 的值设置到document里面去...
	 */
	public static Document GoodDetailsToDocument(B2bGoodsDto goodDetails) {
		Document document = new Document();
		StringField idfield = new StringField("id", goodDetails.getPk(), Store.YES);
		TextField companyNamefield = null;
		TextField brandNamefield = null;
		TextField storeNamefield = null;
		TextField productNamefield = null;
		TextField specNamefield = null;
		TextField seriesNamefield = null;
		TextField gradeNamefield = null;
		TextField varietiesNamefield = null;
		TextField seedvarietyNamefield = null;
		TextField brandPkfield = null;
		TextField productPkfield = null;
		TextField specPkfield = null;
		TextField seriesPkfield = null;
		TextField gradePkfield = null;
		TextField varietiesPkfield = null;
		TextField seedvarietyPkfield = null;
		TextField plantNamefield = null;
		TextField wareNamefield = null;
		TextField batchNumberfield = null;
		TextField packNumberfield = null;
		TextField specificationsfield = null;
		
		B2bGoodsDtoMa b2bGoodsDtoMa = new B2bGoodsDtoMa();
		b2bGoodsDtoMa.UpdateMine(goodDetails);
		CfGoods cfGoods = b2bGoodsDtoMa.getCfGoods();
		if (!StringUtils.isBlank(cfGoods.getPlantName())) {
			plantNamefield = new TextField("plantName", cfGoods.getPlantName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getWareName())) {
			wareNamefield = new TextField("wareName", cfGoods.getWareName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getPackNumber())) {
			packNumberfield = new TextField("packNumber", cfGoods.getPackNumber(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getBatchNumber())) {
			batchNumberfield = new TextField("batchNumber", cfGoods.getBatchNumber(), Store.YES);
		}
		if (!StringUtils.isBlank(goodDetails.getBrandPk())) {
			brandPkfield = new TextField("brandPk", goodDetails.getBrandPk(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getProductPk())) {
			productPkfield = new TextField("productPk", cfGoods.getProductPk(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSpecPk())) {
			specPkfield = new TextField("specPk", cfGoods.getSpecPk(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSeriesPk())) {
			seriesPkfield = new TextField("seriesPk", cfGoods.getSeriesPk(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getGradePk())) {
			gradePkfield = new TextField("gradePk", cfGoods.getGradePk(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getVarietiesPk())) {
			varietiesPkfield = new TextField("varietiesPk", cfGoods.getVarietiesPk(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSeedvarietyPk())) {
			seedvarietyPkfield = new TextField("seedvarietyPk", cfGoods.getSeedvarietyPk(), Store.YES);
		}
		if (!StringUtils.isBlank(goodDetails.getCompanyName())) {
			companyNamefield = new TextField("companyName", goodDetails.getCompanyName(), Store.YES);
		}
		if (!StringUtils.isBlank(goodDetails.getBrandName())) {
			brandNamefield = new TextField("brandName", goodDetails.getBrandName(), Store.YES);
		}
		if (!StringUtils.isBlank(goodDetails.getStoreName())) {
			storeNamefield = new TextField("storeName", goodDetails.getStoreName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getProductName())) {
			productNamefield = new TextField("productName", cfGoods.getProductName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSpecName())) {
			specNamefield = new TextField("specName", cfGoods.getProductName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSeriesName())) {
			seriesNamefield = new TextField("seriesName", cfGoods.getSeriesName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getGradeName())) {
			gradeNamefield = new TextField("gradeName", cfGoods.getGradeName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getVarietiesName())) {
			varietiesNamefield = new TextField("varietiesName", cfGoods.getVarietiesName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSeedvarietyName())) {
			seedvarietyNamefield = new TextField("seedvarietyName", cfGoods.getSeedvarietyName(), Store.YES);
		}
		if (!StringUtils.isBlank(cfGoods.getSpecifications())) {
			specificationsfield = new TextField("specifications", cfGoods.getSpecifications(), Store.YES);
		}
		document.add(idfield);
		if (null != companyNamefield) {
			document.add(companyNamefield);
		}

		if (null != brandNamefield) {
			document.add(brandNamefield);
		}

		if (null != storeNamefield) {
			document.add(storeNamefield);
		}

		if (null != productNamefield) {
			document.add(productNamefield);
		}

		if (null != specNamefield) {
			document.add(specNamefield);
		}

		if (null != seriesNamefield) {
			document.add(seriesNamefield);
		}

		if (null != gradeNamefield) {
			document.add(gradeNamefield);
		}

		if (null != varietiesNamefield) {
			document.add(varietiesNamefield);
		}

		if (null != seedvarietyNamefield) {
			document.add(seedvarietyNamefield);
		}

		if (null != brandPkfield) {
			document.add(brandPkfield);
		}

		if (null != productPkfield) {
			document.add(productPkfield);
		}

		if (null != specPkfield) {
			document.add(specPkfield);
		}

		if (null != seriesPkfield) {
			document.add(seriesPkfield);
		}

		if (null != gradePkfield) {
			document.add(gradePkfield);
		}

		if (null != varietiesPkfield) {
			document.add(varietiesPkfield);
		}

		if (null != seedvarietyPkfield) {
			document.add(seedvarietyPkfield);
		}

		if (null != plantNamefield) {
			document.add(plantNamefield);
		}

		if (null != wareNamefield) {
			document.add(wareNamefield);
		}

		if (null != batchNumberfield) {
			document.add(batchNumberfield);
		}

		if (null != packNumberfield) {
			document.add(packNumberfield);
		}

		if (null != specificationsfield) {
			document.add(specificationsfield);
		}
		return document;
	}
}
