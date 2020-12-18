package cn.cf.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.constant.Block;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bTrancsationContractDto;
import cn.cf.dto.B2bTrancsationDto;
import cn.cf.dto.ExportOrderDto;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.B2bContractGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.Sessions;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bContractGoodsService;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.CommonService;
import cn.cf.service.FileOperationService;
import cn.cf.service.ForeignBankService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.DownLoadUtils;
import cn.cf.util.KeyUtils;
import cn.cf.util.Tool;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class FileOperationServiceImpl implements FileOperationService {
	
//	private final static Logger logger = LoggerFactory.getLogger(FileOperationServiceImpl.class);
	
	@Autowired
	private B2bOrderService b2bOrderService;
	
	@Autowired
	private B2bCompanyService b2bCompanyService;
	
	@Autowired
	private ForeignBankService foreignBankService;
	
	@Autowired
	private B2bContractService b2bContractService;
	
	@Autowired
	private B2bContractGoodsService b2bContractGoodsService;
	
	@Autowired
	private CommonService commonService;
	

	@Override
	public void exportOrder(List<ExportOrderDto> list,
							HttpServletRequest request, HttpServletResponse response, Sessions session,String searchType){
		String excelPath = PropertyConfig.getProperty("excel_url");
		InputStream resourceAsStream =null;
		B2bCompanyDto b2bCompanyDto = session.getCompanyDto();
		String block = b2bCompanyDto.getBlock();
		//1:供应商中心导出
		if (searchType.equals("2") || searchType.equals("3")) {
			if (block.equals(Block.CF.getValue())) {//cf
				resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("order_cf.xls");
			}
			if (block.equals(Block.SX.getValue())) {//sx
				resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("order_sx.xls");
			}
			if (block.contains(Block.CF.getValue()) && block.contains(Block.SX.getValue())) {//cf,sx
				resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("order.xls");
			}
			//2:采购商中心导出
		}else {
			resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("order.xls");
		}
		String fileName = "/order" + KeyUtils.getUUID() + ".xls";
		File pathFile = new File(excelPath);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String destFileName = excelPath + fileName;
		String downLoadName = "order"+DateUtil.formatYearMonthDayHMS(new Date());
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		OutputStream os = null;
		try {
			Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		     os = new BufferedOutputStream(new FileOutputStream(destFileName));
		    workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != os){
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null != os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DownLoadUtils.downloadExcel(response, request, destFileName, downLoadName);
	}
	
	
	/**
	 * 下载订单
	 */
	@Override
	public void downContract(String orderNumber, HttpServletRequest req,
			HttpServletResponse resp) {
		String pdfPath = PropertyConfig.getProperty("pdf_url");
		// 告诉浏览器用什么软件可以打开此文件
		resp.setHeader("content-Type", "application/pdf");
	    // 下载文件的默认名称
		resp.setHeader("Content-Disposition", "attachment;filename="+orderNumber+".pdf");
		B2bOrderDtoEx order = b2bOrderService.getOrder(orderNumber);
		B2bOrderDtoMa om = new B2bOrderDtoMa();
		om.UpdateMine(order);
		List<B2bOrderGoodsDtoEx> orderGoods = b2bOrderService.searchOrderGoodsList(orderNumber,true,false);
	    try {
	    	Document document = new Document();
	    	String amount  =  new BigDecimal(order.getActualAmount().toString()).toPlainString();
	    	Boolean flag = true;
	    	//已付款状态的存临时文件
	    	if(order.getOrderStatus()>1){
	    		//如果已有则下载
	    		flag = downloadLocal(resp, orderNumber);
	    	}
	    	if(flag){
	    		File pathFile = new File(pdfPath);
	    		if(!pathFile.exists()){
	    			pathFile.mkdir();
	    		}
	    		if(Block.CF.getValue().equals(om.getBlock())){
	    			cfOrder(orderNumber, resp, order, om, orderGoods, document,
	    					amount);
	    		}else{
	    			sxOrder(orderNumber, resp, order, om, orderGoods, document, amount);
	    		}
	    	}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(order.getOrderStatus()<=1){
				 File file = new File(pdfPath+orderNumber+".pdf");
				 if (file.exists() && file.isFile()) {
			            file.delete();
			     }
	    	}
		}
	}


	private void cfOrder(String orderNumber, HttpServletResponse resp,
			B2bOrderDtoEx order, B2bOrderDtoMa om,
			List<B2bOrderGoodsDtoEx> orderGoods, Document document,
			String amount) throws DocumentException, FileNotFoundException,
			IOException {
		String pdfPath = PropertyConfig.getProperty("pdf_url");
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath+orderNumber+".pdf"));
		PdfWriter.getInstance(document, resp.getOutputStream());
		B2bCompanyDtoEx purchase = b2bCompanyService.getCompany(order.getPurchaserPk());
		B2bCompanyDtoEx supplier = b2bCompanyService.getCompany(order.getSupplierPk());
		//使用itext-asian.jar中的字体
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont);     
		document.open();
		Paragraph text1 = new Paragraph("苏州纤联电子商务有限公司",new Font(baseFont, 20, Font.BOLD));
		Paragraph text2 = new Paragraph("全国统一免费电话："+PropertyConfig.getStrValueByKey("official_tel"),new Font(baseFont, 10));
		Paragraph text3 = new Paragraph("化纤汇官网："+PropertyConfig.getStrValueByKey("official_website"),new Font(baseFont, 10));
		text1.setAlignment(Element.ALIGN_CENTER);
		text2.setAlignment(Element.ALIGN_CENTER);
		text3.setAlignment(Element.ALIGN_CENTER);
		document.add(text1);
		document.add(text2);
		document.add(text3);
		PdfPTable table = new PdfPTable(13);
		table.setSpacingBefore(10);//设置表头间距
		table.setTotalWidth(500);//设置表格的总宽度
		table.setTotalWidth(new float[]{19,74,34,20,30,49,45,49,40,42,42,42,38});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table.setLockedWidth(true);
		table.addCell(getCell("销售合同", new Font(baseFont, 20, Font.BOLD), 13, 1,null));
		table.addCell(getCell("订单编号",font,2, 0,null));
		table.addCell(getCell(order.getOrderNumber(), font, 4, 1,null));
		table.addCell(getCell("时间", font, 2, 0,null));
		table.addCell(getCell(DateUtil.formatDateAndTimeSpace(order.getInsertTime()), font, 5, 1,null));
		table.addCell(getCell("甲方(供应商)", font, 2, 0,null));
		table.addCell(getCell(om.getSupplier().getSupplierName(), font, 4, 1,null));
		table.addCell(getCell("乙方(采购商)", font, 2, 0,null));
		table.addCell(getCell(om.getPurchaser().getPurchaserName(), font, 5, 1,null));
		table.addCell(getCell("地址", font, 2, 0,null));
		table.addCell(getCell((supplier.getProvinceName()==null?"":supplier.getProvinceName())+(supplier.getCityName()==null?"":supplier.getCityName())+(supplier.getAreaName()==null?"":supplier.getAreaName()), font, 4, 1,null));
		table.addCell(getCell("地址", font, 2, 0,null));
		table.addCell(getCell((purchase.getProvinceName()==null?"":purchase.getProvinceName())+(purchase.getCityName()==null?"":purchase.getCityName())+(purchase.getAreaName()==null?"":purchase.getAreaName()), font, 5, 1,null));
		table.addCell(getCell("联系人", font, 2, 0,null));
		table.addCell(getCell(supplier.getContacts(), font, 4, 1,null));
		table.addCell(getCell("联系人", font, 2, 0,null));
		table.addCell(getCell(purchase.getContacts(), font, 5, 1,null));
		table.addCell(getCell("手机", font, 2, 0,null));
		table.addCell(getCell(om.getSupplier().getContactsTel(), font, 4, 1,null));
		table.addCell(getCell("手机", font, 2, 0,null));
		table.addCell(getCell(om.getPurchaser().getContactsTel(), font, 5, 1,null));
		table.addCell(getCell("座机", font, 2, 0,null));
		table.addCell(getCell("", font, 4, 0,null));
		table.addCell(getCell("座机", font, 2, 0,null));
		table.addCell(getCell("", font, 5, 1,null));
		table.addCell(getCell("根据《中华人民共和国合同法》的有关规定，经买卖双方协商一致，签订本合同并遵照执行：", font, 13, 0,null));
		table.addCell(getCell("序号", font, 1, 1,null));
		table.addCell(getCell("商品信息", font, 1, 1,null));
		table.addCell(getCell("批号", font, 1, 1,null));
		table.addCell(getCell("箱数", font, 1, 1,null));
		table.addCell(getCell("重量(吨)", font, 1, 1,null));
		table.addCell(getCell("成交价格(元/吨)", font, 1, 1,null));
		table.addCell(getCell("商品结算(元)", font, 1, 1,null));
		table.addCell(getCell("实际运费(元/吨)", font, 1, 1,null));
		table.addCell(getCell("运费结算(元)", font, 1, 1,null));
		table.addCell(getCell("装车费(元/吨)", font, 1, 1,null));
		table.addCell(getCell("包装费(元/吨)", font, 1, 1,null));
		table.addCell(getCell("管理费(元/吨)", font, 1, 1,null));
		table.addCell(getCell("合计(元)", font, 1, 1,null));
		//------------------订单商品详情-------------------------------
		if(null!=orderGoods && orderGoods.size()>0){
			B2bOrderGoodsDtoEx ogEx = b2bOrderService.getTotalPrice(orderNumber);
			for (int i = 0; i < orderGoods.size(); i++) {
				B2bOrderGoodsDtoEx goods = orderGoods.get(i);
				B2bOrderGoodsDtoMa ogm = new B2bOrderGoodsDtoMa();
				ogm.UpdateMine(goods);
				table.addCell(getCell(i+1+"", font, 1, 1,null));
				String productName=ogm.getCfGoods().getProductName();
				String varName=null==ogm.getCfGoods().getVarietiesName()?"":ogm.getCfGoods().getVarietiesName();
				String seedName=null==ogm.getCfGoods().getSeedvarietyName()?"":ogm.getCfGoods().getSeedvarietyName();
				String specName=null==ogm.getCfGoods().getSpecName()?"":ogm.getCfGoods().getSpecName();
				String seriName=null==ogm.getCfGoods().getSeriesName()?"":ogm.getCfGoods().getSeriesName();
				String gradeName=null==ogm.getCfGoods().getGradeName()?"":ogm.getCfGoods().getGradeName();
				table.addCell(getCell(productName +"  "+ varName+"  "+ seedName+"  "+specName+"  "+seriName+"  "+gradeName, font, 1, 1,null));	 
				//table.addCell(getCell(goods.getProductName()+"\n\n"+goods.getVarietiesName()+"("+goods.getSeedvarietyName()+")"+"\n\n"+goods.getSpecName()==null?"":goods.getSpecName()+goods.getSeriesName()==null?"":"/"+goods.getSeriesName(),font,1, true,null));
				table.addCell(getCell(ogm.getCfGoods().getBatchNumber(), font, 1, 1,null));
				if(null == goods.getAfterWeight() || goods.getAfterWeight() == 0d){
					table.addCell(getCell(goods.getBoxes()+"", font, 1, 1,null));	
					table.addCell(getCell(new BigDecimal(goods.getWeight().toString()).toPlainString(), font, 1, 1,null));	
				}else{
					table.addCell(getCell(goods.getAfterBoxes()+"", font, 1, 1,null));	
					table.addCell(getCell(new BigDecimal(goods.getAfterWeight().toString()).toPlainString(), font, 1, 1,null));
				}
				table.addCell(getCell(new BigDecimal(goods.getPresentPrice().toString()).toPlainString(), font, 1, 1,null));	
				table.addCell(getCell(new BigDecimal(goods.getPresentTotalPrice().toString()).toPlainString(), font, 1, 1,null));	
				table.addCell(getCell(new BigDecimal(goods.getPresentFreight().toString()).toPlainString(), font, 1, 1,null));	
				table.addCell(getCell(new BigDecimal(goods.getPresentTotalFreight().toString()).toPlainString(), font, 1, 1,null));	
				table.addCell(getCell((null==ogm.getCfGoods().getLoadFee()?0d:ArithUtil.round(ogm.getCfGoods().getLoadFee(), 2))+"", font, 1, 1,null));
				table.addCell(getCell((null==ogm.getCfGoods().getPackageFee()?0d:ogm.getCfGoods().getPackageFee())+"", font, 1, 1,null));
				table.addCell(getCell((null==ogm.getCfGoods().getAdminFee()?0d:ogm.getCfGoods().getAdminFee())+"", font, 1, 1,null));
				if(i==0){
					table.addCell(getCell(ogEx.getTotalFreight()>0?amount+"(含运费)":amount, font, 1, 1,orderGoods.size()));
				}
			}
		}
		String paymentName = order.getPaymentName();
		if(null != order.getEconomicsGoodsName() && !"".equals(order.getEconomicsGoodsName())){
			paymentName = order.getEconomicsGoodsName();
		}
		table.addCell(getCell("实付金额", font, 2, 1,null));
		table.addCell(getCell(Tool.change(order.getActualAmount()), font, 4, 1,null));
		table.addCell(getCell("小写", font, 2, 1,null));
		table.addCell(getCell(amount, font, 5, 1,null));
		table.addCell(getCell("物流方式", font, 2, 1,null));
		table.addCell(getCell(order.getLogisticsModelName(), font, 11, 1,null));
		table.addCell(getCell("付款方式", font, 2, 1,null));
		table.addCell(getCell(paymentName, font,11, 1,null));
		String qualityValue = null;
		if(null != order.getEconomicsGoodsName() && !"".equals(order.getEconomicsGoodsName())){
			//调金融产品配置的描述
				B2bEconomicsGoodsDto eGoods =  foreignBankService.getEconomicsGoodsByPk(order.getEconomicsGoodsName(),order.getEconomicsGoodsType());
				if(null!=eGoods&&!"".equals(eGoods.getQualityValue())){
					qualityValue = eGoods.getQualityValue();
				}
					
				
		}
		if(null == qualityValue || "".equals(qualityValue)){
			qualityValue = "一   产品质量标准\n\n"+
					"1.1  产品标准按照甲方企业定等标准执行。\n\n1.2若产品不符合约定的质量标准，乙方应在收货后7个工作日内以书面方式提出，否则产品视作符合约定标准。\n\n"+
					"二   产品包装\n\n"+
					"采用行业惯用包装，能够满足搬运需要。\n\n"+
					"三   付款期限及付款方式\n\n"+
					"3.1  本合同签订之日乙方向甲方支付合同价款，本合同自动生效。\n\n"+
					"四   提货期限\n\n"+
					"4.1  合同载明货物自签订之日起，规定工作日内提完。\n\n"+
					"五   其他约定\n\n"+
					"5.1  合同履行过程中，甲、乙双方发生纠纷应友好协商解决。协商不成则提交供方所在地人民法院解决。\n\n"+
					"5.2  本合同文本不得涂改，若需修改，甲、乙双方应协商达成一致修改意见，并签订书面文本。\n\n"+
					"5.3  本合同一式二份，甲、乙双方各执一份，具同等法律效力。";
		}
		table.addCell(getCell(qualityValue,font,13,0,null));
		table.addCell(getCell("供应商(签章)\n\n代理人(签章)", font, 2, 1,null));
		table.addCell(getCell("", font, 4, 1,null));
		table.addCell(getCell("采购商(签章)\n\n代理人(签章)", font, 2, 1,null));
		table.addCell(getCell("", font,5, 1,null));
		document.add(table);
		document.close();
	}
	
	private void sxOrder(String orderNumber, HttpServletResponse resp,
			B2bOrderDtoEx order, B2bOrderDtoMa om,
			List<B2bOrderGoodsDtoEx> orderGoods, Document document,
			String amount) throws DocumentException, FileNotFoundException,
			IOException {
		String pdfPath = PropertyConfig.getProperty("pdf_url");
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath+orderNumber+".pdf"));
		PdfWriter.getInstance(document, resp.getOutputStream());
//		B2bCompanyDto purchase = companyService.getCompany(order.getPurchaserPk());
		B2bCompanyDtoEx supplier = b2bCompanyService.getCompany(order.getSupplierPk());
		//使用itext-asian.jar中的字体
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont);     
		document.open();
		Paragraph text1 = new Paragraph("需方传真：",new Font(baseFont, 7, Font.NORMAL));
		document.add(text1);
		Paragraph text2 = new Paragraph("产品购销合同",new Font(baseFont, 20, Font.BOLD));
		text2.setAlignment(Element.ALIGN_CENTER);
		document.add(text2);
		PdfPTable table1 = new PdfPTable(2);
		table1.setSpacingBefore(10);//设置表头间距
		table1.setTotalWidth(500);//设置表格的总宽度
		table1.setTotalWidth(new float[]{250,250});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table1.setLockedWidth(true);
		table1.addCell(getCell("合同编号："+orderNumber, font, 1, 2,null));
		table1.addCell(getCell("签订日期："+DateUtil.formatYearMonthAndDay(order.getInsertTime()), font, 1, 2,null));
		
		String supplierName = null==supplier.getName()?"":supplier.getName();
		String address = (null==supplier.getProvinceName()?"":supplier.getProvinceName())+
				(null==supplier.getCityName()?"":supplier.getCityName())+
				(null==supplier.getAreaName()?"":supplier.getAreaName());
		String purchaserName = null==om.getPurchaser().getPurchaserName()?"":
								om.getPurchaser().getPurchaserName();
		String saleName = null==order.getEmployeeName()?"":order.getEmployeeName();
		table1.addCell(getCell("供方："+ supplierName, font, 1, 2,null));
		table1.addCell(getCell("签订地点："+address,font, 1, 2,null));
		table1.addCell(getCell("需方："+purchaserName, font, 1, 2,null));
		table1.addCell(getCell("业务员："+saleName, font, 1, 2,null));
		document.add(table1);
		Paragraph text3 = new Paragraph("一、产品名称、证书名称、规格、重量、金额",font);
		document.add(text3);
		PdfPTable table2 = new PdfPTable(7);
		table2.setSpacingBefore(10);//设置表头间距
		table2.setTotalWidth(500);//设置表格的总宽度
		table2.setTotalWidth(new float[]{70,70,70,70,70,70,70});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table2.setLockedWidth(true);
		table2.addCell(getCell("产品名称", font, 1, 1,null));
		table2.addCell(getCell("证书名称", font, 1, 1,null));
		table2.addCell(getCell("规格",font,1, 1,null));
		table2.addCell(getCell("单位", font, 1, 1,null));
		table2.addCell(getCell("重量", font, 1, 1,null));
		table2.addCell(getCell("单价(元)", font, 1, 1,null));
		table2.addCell(getCell("总金额(元)", font, 1, 1,null));
		//------------------订单商品详情-------------------------------
		Double totalWeight = 0d;
		if(null!=orderGoods && orderGoods.size()>0){
			for (int i = 0; i < orderGoods.size(); i++) {
				B2bOrderGoodsDtoMa ogm = new B2bOrderGoodsDtoMa();
				ogm.UpdateMine(orderGoods.get(i));
				if(null !=ogm.getSxGoods()){
					 table2.addCell(getCell(null==ogm.getSxGoods().getRawMaterialParentName()?"":ogm.getSxGoods().getRawMaterialParentName()+"纱", font, 1, 1,null));
					 table2.addCell(getCell(null==ogm.getSxGoods().getCertificateName()?"无":ogm.getSxGoods().getCertificateName(), font, 1, 1,null));
					 table2.addCell(getCell(ogm.getSxGoods().getSpecName(), font, 1, 1,null));
					 table2.addCell(getCell("千克", font, 1, 1,null));
					 table2.addCell(getCell(new BigDecimal(ogm.getAfterWeight().toString()).toPlainString(), font, 1, 1,null));
					 table2.addCell(getCell(new BigDecimal(ogm.getPresentPrice().toString()).toPlainString(), font, 1, 1,null));	
					 Double totalPrice = ArithUtil.round(ArithUtil.mul(ogm.getPresentPrice(), ogm.getAfterWeight()), 2);
					 table2.addCell(getCell(new BigDecimal(totalPrice.toString()).toPlainString(), font, 1, 1,null));	
					 totalWeight +=ogm.getAfterWeight();
				 }
			}
			table2.addCell(getCell("合计：", font, 4, 3,null));
			table2.addCell(getCell(new BigDecimal(totalWeight.toString()).toPlainString(), font, 1, 1,null));	
			table2.addCell(getCell("", font, 1, 1,null));	
			table2.addCell(getCell(new BigDecimal(order.getActualAmount().toString()).toPlainString(), font, 1, 1,null));	
		}
		document.add(table2);
		String addressInfo ="";
		if(null != om.getAddress()){
			addressInfo = (null==om.getAddress().getProvinceName()?"":om.getAddress().getProvinceName())+
		    		(null==om.getAddress().getCityName()?"":om.getAddress().getCityName())+
		    		(null==om.getAddress().getAreaName()?"":om.getAddress().getAreaName())+
		    		(null==om.getAddress().getTownName()?"":om.getAddress().getTownName())+
		    		(null==order.getAddress()?"":order.getAddress())+"  "+ (null==om.getAddress().getContacts()?"":om.getAddress().getContacts()) +" "+ (null==om.getAddress().getContactsTel()?"":om.getAddress().getContactsTel());
		}
		Paragraph text4 = new Paragraph("二、要求及技术标准：达到国家一等品标准。\n\n"+
				    		"三、交货地点："+ addressInfo +"\n\n"+
				    		"四、运输方式及费用负担："+order.getLogisticsModelName()+"。\n\n"	+
				    		"五、交货时间：全款到后当天发完。\n\n"+
				    		"六、交货数量：按订货数量。\n\n"+
				    		"七、结算方式及期限：现汇，签订后当日款到，合同有效，逾期作废。\n\n"+
				    		"八、纺织袋、纸箱、（集装箱）。\n\n"+
				    		"九、违约责任：本合同是经双方友好协商签定的，若发生纠纷，可经双方友好协商解决，如协商不成，向供方所在地人民法院提起诉讼。\n\n"+
				    		"十、本合同签字或盖章有效，传真件有效，一式二份，双方各执一份。\n\n"+
				    		"十一、备注：请客户按照生产日期顺序使用。\n\n"+
				    		"十二、收款信息：",font);
		document.add(text4);
		
		PdfPTable table3 = new PdfPTable(2);
		table3.setSpacingBefore(10);//设置表头间距
		table3.setTotalWidth(500);//设置表格的总宽度
		table3.setTotalWidth(new float[]{250,250});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		if(null != om.getSupplier() && null != om.getSupplier().getBankName() && null != om.getSupplier().getBankAccount() && !"".equals(om.getSupplier().getBankName())
				&& !"".equals(om.getSupplier().getBankAccount())){
			table3.addCell(getCell("户名："+om.getSupplier().getSupplierName(), font, 2, 2,null));
			table3.addCell(getCell("开户行："+om.getSupplier().getBankName(), font, 2, 2,null));
			table3.addCell(getCell("账号："+om.getSupplier().getBankAccount(), font, 2, 2,null));
		}else{
			table3.addCell(getCell("户名：", font, 2, 2,null));
			table3.addCell(getCell("开户行：", font, 2, 2,null));
			table3.addCell(getCell("账号：", font, 2, 2,null));
		}
		table3.addCell(getCell("供方签章：\n\n", font, 1, 2,null));
		table3.addCell(getCell("需方签章：\n\n", font, 1, 2,null));
		document.add(table3);
		Paragraph text5 = new Paragraph("请签字盖章回传："+(null!=supplier?supplier.getContactsTel():""),font);		
		text5.setAlignment(Element.ALIGN_RIGHT);
		document.add(text5);
		document.close();
	}
	
	
	/**
	 * 
	 * @param value 值
	 * @param font  字体
	 * @param colspan 合并宽
	 * @param alginCenter 是否居中
	 * @param rowspan 合并高
	 * @return
	 */
	/**
	 * 
	 * @param value 值
	 * @param font  字体
	 * @param colspan 合并宽
	 * @param alginCenter 是否居中
	 * @param rowspan 合并高
	 * @return
	 */
	private PdfPCell getCell(String value,Font font,Integer colspan,Integer size,Integer rowspan){
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph(value,font);
		cell.setPhrase(p);
		cell.setColspan(colspan);
		font.setSize(6);
		if(rowspan!=null){
			cell.setRowspan(rowspan);
		}
		switch (size) {
		case 1:
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			break;
		case 2:
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			break;
		case 3:
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			break;
		default:
			break;
		}
		return cell;
	}
	
	/**
	 * 
	 * @param titles  一级标题
	 * @param titleFont  一级标题样式
	 * @param contents  二级内容
	 * @param contentFont  二级内容样式
	 * @param colspan 合并宽
	 * @param alginCenter 是否居中
	 * @param rowspan 合并高
	 * @return
	 */
	private PdfPCell getCell2(List<String> titles, Font titleFont,List<String> contents, Font contentFont,Integer colspan,boolean alginCenter,Integer rowspan){
		PdfPCell cell = new PdfPCell();
		Paragraph paragraph=new Paragraph("",titleFont);
		Paragraph tempTitle=null;
		Paragraph tempContent=null;
		for (int i = 0; i < titles.size(); i++) {
			tempTitle = new Paragraph(titles.get(i),titleFont);
			paragraph.add(tempTitle);
			tempContent = new Paragraph(contents.get(i), contentFont);
			paragraph.add(tempContent);
		}
		cell.setPhrase(paragraph);
		cell.setColspan(colspan);
		if(rowspan!=null){
			cell.setRowspan(rowspan);
		}
		if(alginCenter){
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		}
		return cell;
	}
	
	
    private boolean downloadLocal(HttpServletResponse response,String fileName) throws FileNotFoundException {
    	String pdfPath = PropertyConfig.getProperty("pdf_url");
    	boolean flag = true;
        try {
        	File pathFile = new File(pdfPath);
    		if(!pathFile.exists()){
    			pathFile.mkdir();
    		}
        	// 读到流中
        	File file = new File(pdfPath+fileName+".pdf");
        	if(!file.exists()){
        		return flag;
        	}
        	InputStream inStream = new FileInputStream(pdfPath+fileName+".pdf");// 文件的存放路径
        	byte[] b = new byte[100];
        	int len;
        	// 循环取出流中的数据
            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
                 flag = false;
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

	@Override
	public void exportGoods(List<B2bGoodsDtoEx> list, HttpServletRequest request,
			HttpServletResponse response) {
		String excelPath = PropertyConfig.getProperty("excel_url");
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("goods.xls");
		String fileName = "/goods" + KeyUtils.getUUID() + ".xls";
		File pathFile = new File(excelPath);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String destFileName = excelPath + fileName;
		String downLoadName = "goods"+DateUtil.formatYearMonthDayHMS(new Date());
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		OutputStream os = null;
		try {
			Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		     os = new BufferedOutputStream(new FileOutputStream(destFileName));
		    workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != os){
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null != os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DownLoadUtils.downloadExcel(response, request, destFileName, downLoadName);
		
	}

	/**
	 * 订单交易数据导出
	 */
	@Override
	public void exportTrancsation(List<B2bTrancsationDto> list,
			HttpServletRequest request, HttpServletResponse response) {
		String excelPath = PropertyConfig.getProperty("excel_url");
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("trancsation.xls");
		String fileName = "/trancsation" + KeyUtils.getUUID() + ".xls";
		File pathFile = new File(excelPath);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String destFileName = excelPath + fileName;
		String downLoadName = "trancsation"+DateUtil.formatYearMonthDayHMS(new Date());
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		OutputStream os = null;
		try {
			Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		     os = new BufferedOutputStream(new FileOutputStream(destFileName));
		    workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != os){
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null != os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DownLoadUtils.downloadExcel(response, request, destFileName, downLoadName);
		
	}

	/**
	 * 合同交易数据导出
	 */
	@Override
	public void exportTrancsationContract(List<B2bTrancsationContractDto> list,
			HttpServletRequest request, HttpServletResponse response) {
		String excelPath = PropertyConfig.getProperty("excel_url");
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("trancsationContract.xls");
		String fileName = "/trancsationContract" + KeyUtils.getUUID() + ".xls";
		File pathFile = new File(excelPath);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String destFileName = excelPath + fileName;
		String downLoadName = "trancsation"+DateUtil.formatYearMonthDayHMS(new Date());
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		OutputStream os = null;
		try {
			Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		     os = new BufferedOutputStream(new FileOutputStream(destFileName));
		    workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != os){
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null != os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DownLoadUtils.downloadExcel(response, request, destFileName, downLoadName);
	}
	


	@Override
	public void exportContract(List<B2bContractGoodsDtoEx> list,Sessions sessions,String searchType, HttpServletRequest request, HttpServletResponse response) {
		String excelPath = PropertyConfig.getProperty("excel_url");
		B2bCompanyDto b2bCompanyDto = sessions.getCompanyDto();
		String block = b2bCompanyDto.getBlock();
		InputStream resourceAsStream = null;
		//1:供应商中心导出
		if (searchType.equals("2") || searchType.equals("3")) {
			if (block.equals(Block.CF.getValue())) {//cf
				resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("contract_cf.xls");
			}
			if (block.equals(Block.SX.getValue())) {//sx
				resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("contract_sx.xls");
			}
			if (block.contains(Block.CF.getValue()) && block.contains(Block.SX.getValue())) {//cf,sx
				resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("contract.xls");
			}
		//2:采购商中心导出
		}else {
			resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("contract.xls");
		}
		String fileName = "/contract" + KeyUtils.getUUID() + ".xls";
		File pathFile = new File(excelPath);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String destFileName = excelPath + fileName;
		String downLoadName = "contract"+DateUtil.formatYearMonthDayHMS(new Date());
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		OutputStream os = null;
		try {
			Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
		     os = new BufferedOutputStream(new FileOutputStream(destFileName));
		    workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != os){
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null != os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DownLoadUtils.downloadExcel(response, request, destFileName, downLoadName);
		
	}


	
	//下载合同
	@Override
	public void downContractOrder(String contractNo, HttpServletRequest req,
			HttpServletResponse resp) {
		String pdfPath = PropertyConfig.getProperty("pdf_url");
		 // 告诉浏览器用什么软件可以打开此文件
		resp.setHeader("content-Type", "application/pdf");
	    // 下载文件的默认名称
		resp.setHeader("Content-Disposition", "attachment;filename="+contractNo+".pdf");
		B2bContractDto dto = b2bContractService.getB2bContract(contractNo);
		B2bContractDtoMa cm = new  B2bContractDtoMa();
		cm.UpdateMine(dto);
		List<B2bContractGoodsDtoEx> dtoGoods = b2bContractGoodsService.searchListByContractNo(contractNo, true);
	    try {
	    	Document document = new Document();
	    	Boolean flag = true;
	    	//已付款状态的存临时文件
	    	if(dto.getContractStatus()>1){
	    		//如果已有则下载
	    		flag = downloadLocal(resp, contractNo);
	    	}
	    	if(flag){
	    		File pathFile = new File(pdfPath);
	    		if(!pathFile.exists()){
	    			pathFile.mkdir();
	    		}
	    		if(Block.CF.getValue().equals(cm.getBlock())){
	    			cfContract(contractNo, resp, dto, cm, dtoGoods, document);
	    		}else{
	    			sxContract(contractNo, resp, dto, cm, dtoGoods, document);
	    		}
	    	}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(dto.getContractStatus()<=1){
				 File file = new File(pdfPath+contractNo+".pdf");
				 if (file.exists() && file.isFile()) {
			            file.delete();
			     }
	    	}
		}
	}


	private void cfContract(String contractNo, HttpServletResponse resp,
			B2bContractDto dto, B2bContractDtoMa cm,
			List<B2bContractGoodsDtoEx> dtoGoods, Document document)
			throws DocumentException, FileNotFoundException, IOException {
		String pdfPath = PropertyConfig.getProperty("pdf_url");
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath+contractNo+".pdf"));
		PdfWriter.getInstance(document, resp.getOutputStream());
		//B2bCompanyDtoEx purchase = b2bCompanyService.getCompany(dto.getPurchaserPk());
		B2bCompanyDtoEx supplier = b2bCompanyService.getCompany(dto.getSupplierPk());
		//使用itext-asian.jar中的字体
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont); 
		Font titleFont =new Font(baseFont, 20, Font.BOLD);
		document.open();
		PdfPTable table = new PdfPTable(13);
		table.setSpacingBefore(10);//设置表头间距
		table.setTotalWidth(500);//设置表格的总宽度
		table.setTotalWidth(new float[]{19,74,34,20,30,49,45,49,40,42,42,42,38});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table.setLockedWidth(true);
		table.addCell(getCell((null==supplier.getName()?"":supplier.getName())+"\n\n销  售  合  同", new Font(baseFont, 30, Font.BOLD), 13, 1,null));
		table.addCell(getCell("合同编号：",font,2, 0,null));
		table.addCell(getCell(dto.getContractNo(), font, 4, 0,null));
		table.addCell(getCell("签订地点：", font, 2, 0,null));
		table.addCell(getCell((supplier.getProvinceName()==null?"":supplier.getProvinceName())+(supplier.getCityName()==null?"":supplier.getCityName())+(supplier.getAreaName()==null?"":supplier.getAreaName()), font, 5, 0,null));
		table.addCell(getCell("签订日期：", font, 2, 0,null));
		table.addCell(getCell(DateUtil.formatYearMonthDay(dto.getStartTime())+"", font, 5, 0,null));
		table.addCell(getCell("", font, 7, 0,null));
		table.addCell(getCell("根据《中华人民共和国合同法》的有关规定，经甲乙双方协商一致，签订本合同并遵照执行：", font, 13, 0,null));
		// 1 产品名称、规格、等级、数量及价格
		table.addCell(getCell("1  产品名称、规格、等级、数量及价格", titleFont, 13, 0,null));
		table.addCell(getCell("名称及规格", font, 3, 1,null));
		table.addCell(getCell("等级", font, 2, 1,null));
		table.addCell(getCell("数量（吨）", font, 2, 1,null));
		table.addCell(getCell("单价（元/吨）", font, 2, 1,null));
		table.addCell(getCell("金额", font, 2, 1,null));
		table.addCell(getCell("备注", font, 2, 1,null));
		if(null!=dtoGoods && dtoGoods.size()>0){
			for (int i = 0; i < dtoGoods.size(); i++) {
				B2bContractGoodsDtoEx goods = dtoGoods.get(i);
				B2bContractGoodsDtoMa cgm = new  B2bContractGoodsDtoMa();
				cgm.UpdateMine(goods);
				Double contractPrice=goods.getContractPrice();
				Double freigjt=goods.getFreight();
				Double loadFee=(null==cgm.getCfGoods().getLoadFee()?0d:ArithUtil.round(cgm.getCfGoods().getLoadFee(), 2));
                Double packageFee=(null==cgm.getCfGoods().getPackageFee()?0d:cgm.getCfGoods().getPackageFee());
                Double adminFee=(null==cgm.getCfGoods().getAdminFee()?0d:cgm.getCfGoods().getAdminFee());
				Double a=ArithUtil.add(freigjt,loadFee);
				Double b=ArithUtil.add(packageFee,adminFee);
				Double c=ArithUtil.add(b,contractPrice);
				Double price=ArithUtil.add(a,c);
				String productName=cgm.getCfGoods().getProductName()==null?"":cgm.getCfGoods().getProductName();//品名
				String specifications=cgm.getCfGoods().getSpecifications()==null?"":cgm.getCfGoods().getSpecifications();//规格
				String gradeName =cgm.getCfGoods().getGradeName()==null?"":cgm.getCfGoods().getGradeName();//等级
				table.addCell(getCell(productName + specifications, font, 3, 1,null));
				table.addCell(getCell(gradeName, font, 2, 1,null));
				table.addCell(getCell(ArithUtil.roundBigDecimal(new BigDecimal(goods.getWeight()),5), font, 2, 1,null));
				table.addCell(getCell(new BigDecimal(price.toString()).toPlainString(),font, 2, 1,null));
				table.addCell(getCell(ArithUtil.roundBigDecimal(new BigDecimal(ArithUtil.mul(goods.getContractPrice(), goods.getWeight())),2), font, 2, 1,null));
				table.addCell(getCell("", font, 2, 1,null));
			}
		}
		table.addCell(getCell("合同金额人名币（大写）："+Tool.change(dto.getTotalAmount()), font, 9, 0,null));
		table.addCell(getCell("（小写）：", font, 2, 1,null));
		table.addCell(getCell(new BigDecimal(dto.getTotalAmount().toString()).toPlainString(), font, 2, 1,null));
		//2 产品质量标准
		List<String> titles2=new ArrayList<>();
		List<String> contents2=new ArrayList<>();
		String title2="2  产品质量标准\n\n";
		String qualityValue = "2.1  产品标准按照甲方企业定等标准执行。\n\n2.2  若产品不符合约定的质量标准，乙方应在收货后5个工作日内以书面方式提出，否则产品视作符合约定标准。";
		/*if (null != dto.getEconomicsGoodsPk() && !"".equals(dto.getEconomicsGoodsPk())) {
			// 调金融产品配置的描述
			B2bEconomicsGoodsDto eGoods = foreignBankService.getEconomicsGoodsByPk(dto.getEconomicsGoodsPk());
			qualityValue = eGoods.getQualityValue();
		}
		if(null == qualityValue || "".equals(qualityValue)){
			qualityValue = "2  产品质量标准\n\n"+
					"2.1  产品标准按照甲方企业定等标准执行。\n\n2.2  若产品不符合约定的质量标准，乙方应在收货后5个工作日内以书面方式提出，否则产品视作符合约定标准。";
		}*/
		titles2.add(title2);
		contents2.add(qualityValue);
		table.addCell(getCell2(titles2, titleFont, contents2, font, 13, false,null));
		
		//3 交货时间、交货地点及交换货方式
		String logisticsModelType="";
		if (null!=dto.getLogisticsModelType()) {
			if (dto.getLogisticsModelType()==1) {
				logisticsModelType="×客户自提   ×商家承运   √平台承运";
			}
			if (dto.getLogisticsModelType()==2) {
				logisticsModelType="×客户自提   √商家承运   ×平台承运";
			}
			if (dto.getLogisticsModelType()==3) {
				logisticsModelType="√客户自提   ×商家承运   ×平台承运";
			}
		}else {
			logisticsModelType="×客户自提   ×商家承运   ×平台承运";
		}
		
		//运费总价
		Double totalFreight=0.0;
		for (B2bContractGoodsDtoEx b2bContractGoodsDtoEx : dtoGoods) {
			totalFreight=totalFreight +ArithUtil.round(ArithUtil.mul(b2bContractGoodsDtoEx.getFreight(), b2bContractGoodsDtoEx.getWeight()),2);
		}
		List<String> titles3=new ArrayList<>();
		List<String> contents3=new ArrayList<>();
		String title3 = "3   交货时间、交货地点及交换货方式\n\n";
		String deliveryInfo=
				"3.1  交货时间："+DateUtil.formatYearMonthDay(dto.getStartTime())+"  交货地点：甲方工厂\n\n"+
				"3.2  交货方式："+logisticsModelType+"\n\n"+
				"运输运费合计"+new BigDecimal(totalFreight.toString()).toPlainString()+"元，运保费未含在合同条款中";
		titles3.add(title3);
		contents3.add(deliveryInfo);
		table.addCell(getCell2(titles3,titleFont,contents3,font, 13, false,null));
		
		// 4 产品包装
		String payType="";
		if (dto.getPaymentType()!=null) {
			if (dto.getPaymentType()==1) {
				payType="×在线支付    √线下支付";
			}else {
				payType="√在线支付    ×线下支付";
			}
		}else{
			payType="×在线支付    ×线下支付";
		}
		List<String> titles4=new ArrayList<>();
		List<String> contents4=new ArrayList<>();
		String title4="4  产品包装\n\n";
		String content4="  采用行业惯用包装，能够满足搬运需求。\n\n";
		String title5="5  付款期限及付款方式\n\n";
		String content5=
				"5.1  本合同签订之日乙方向甲方支付合同价款，本合同自动生效。\n\n"+
				"5.2  付款方式："+payType+"\n\n";
		String title6="6  提货期限\n\n";
		String content6=
				"6.1  合同载明货物自签订之日起，"+dto.getDays()+"日内提完。\n\n"+
				"6.2  乙方提货时需遵守甲方厂纪厂规并保证安全，因提货造成的甲方、乙方自身及第三方任何损害均由乙方负责处理，与甲方无关。\n\n";
		String title7="7  其他\n\n";
		String content7=
				"7.1  合同履行过程中，甲、乙双方发生纠纷应友好协商解决。协商不成则提交合同履行地人民法院解决。\n\n"+
				"7.2  本合同文本不得涂改，若需修改，甲、乙双方应协商达成一致修改意见，并签订书面文本。\n\n"+
				"7.3  本合同一式两份，甲、乙双方各执一份，具同等法律效力。\n\n"+
				"7.4  乙方未经甲方许可，不得自行使用和许可第三方使用甲方提供的包装、外观、产品标识。不得泄露任何合作的内容，包括价格、规格、数量等信息。\n\n"+
				"7.5  因乙方原因导致合同终止的，乙方应赔偿甲方销售差价损失，差价损失按合同签订日至合同截止日（含合同延期）期间的最低价格计算。";
		titles4.add(title4);
		titles4.add(title5);
		titles4.add(title6);
		titles4.add(title7);
		contents4.add(content4);
		contents4.add(content5);
		contents4.add(content6);
		contents4.add(content7);
		table.addCell(getCell2(titles4,titleFont,contents4,font, 13, false,null));
		
		//甲方
		B2bCompanyDtoEx jaCompanyDtoEx = b2bCompanyService.getCompany(dto.getSupplierPk());
		String jaInfo="";
		if (null!=jaCompanyDtoEx && null!=jaCompanyDtoEx.getPk()) {
			jaInfo = "甲方（卖方）\n\n"+
					"公司名称："+(null==cm.getSupplier().getSupplierName()?"":cm.getSupplier().getSupplierName())+"\n\n"+
					"开户银行："+(null==jaCompanyDtoEx.getBankName()?"":jaCompanyDtoEx.getBankName()) +"\n\n"+
					"银行账号："+(null==jaCompanyDtoEx.getBankAccount()?"":jaCompanyDtoEx.getBankAccount())+"\n\n"+
					"电话："+(null==jaCompanyDtoEx.getContactsTel()?"":jaCompanyDtoEx.getContactsTel())+"\n\n"+
					"地址："+(null==jaCompanyDtoEx.getProvinceName()?"":jaCompanyDtoEx.getProvinceName())+(null==jaCompanyDtoEx.getCityName()?"":jaCompanyDtoEx.getCityName())+(null==jaCompanyDtoEx.getAreaName()?"":jaCompanyDtoEx.getAreaName())+(null==jaCompanyDtoEx.getRegAddress()?"":jaCompanyDtoEx.getRegAddress())+"\n\n"+
					"甲方盖章：\n\n"+
					"甲方法定代表人或委托代理人签字：____________";
		}else {
			jaInfo = "甲方（卖方）\n\n"+
					"公司名称：\n\n"+
					"开户银行：\n\n"+
					"银行账号：\n\n"+
					"电话：\n\n"+
					"地址：\n\n"+
					"甲方盖章：\n\n"+
					"甲方法定代表人或委托代理人签字：____________";
		}
		
		
		//乙方
		B2bCompanyDto yiDto = b2bCompanyService.getCompany(cm.getPurchaser().getInvoicePk());
		String yiInfo="";
		if (null!=yiDto && null!=yiDto.getPk()) {
			yiInfo="乙方（买方）\n\n"+
					"公司名称："+(null==yiDto.getName()?"":yiDto.getName())+"\n\n"+
					"开户银行："+(null==yiDto.getBankName()?"":yiDto.getBankName())+"\n\n"+
					"银行账号："+(null==yiDto.getBankAccount()?"":yiDto.getBankAccount())+"\n\n"+
					"电话："+(null==yiDto.getContactsTel()?"":yiDto.getContactsTel())+"\n\n"+
					"地址："+(null==yiDto.getProvinceName()?"":yiDto.getProvinceName())+(null==yiDto.getCityName()?"":yiDto.getCityName())+(null==yiDto.getAreaName()?"":yiDto.getAreaName())+(null==yiDto.getRegAddress()?"":yiDto.getRegAddress())+"\n\n"+
					"乙方盖章：\n\n"+
					"乙方法定代表人或委托代理人签字：____________";
		}else {
			yiInfo="乙方（买方）\n\n"+
					"公司名称：\n\n"+
					"开户银行：\n\n"+
					"银行账号：\n\n"+
					"电话：\n\n"+
					"地址：\n\n"+
					"乙方盖章：\n\n"+
					"乙方法定代表人或委托代理人签字：____________";
		}
		
		table.addCell(getCell(jaInfo, font, 6, 0,null));
		table.addCell(getCell(yiInfo, font, 7, 0,null));
		document.add(table);
		document.close();
	}
	
	
	private void sxContract(String contractNo, HttpServletResponse resp,
			B2bContractDto dto, B2bContractDtoMa cm,
			List<B2bContractGoodsDtoEx> dtoGoods, Document document) throws DocumentException, FileNotFoundException,
			IOException {
		String pdfPath = PropertyConfig.getProperty("pdf_url");
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath+contractNo+".pdf"));
		PdfWriter.getInstance(document, resp.getOutputStream());
//		B2bCompanyDto purchase = companyService.getCompany(order.getPurchaserPk());
		B2bCompanyDtoEx supplier = b2bCompanyService.getCompany(cm.getSupplierPk());
		//使用itext-asian.jar中的字体
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont);     
		document.open();
		Paragraph text1 = new Paragraph("需方传真：",new Font(baseFont, 7, Font.NORMAL));
		document.add(text1);
		Paragraph text2 = new Paragraph("产品购销合同",new Font(baseFont, 20, Font.BOLD));
		text2.setAlignment(Element.ALIGN_CENTER);
		document.add(text2);
		PdfPTable table1 = new PdfPTable(2);
		table1.setSpacingBefore(10);//设置表头间距
		table1.setTotalWidth(500);//设置表格的总宽度
		table1.setTotalWidth(new float[]{250,250});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table1.setLockedWidth(true);
		table1.addCell(getCell("合同编号："+contractNo, font, 1, 2,null));
		table1.addCell(getCell("签订日期："+DateUtil.formatYearMonthAndDay(cm.getInsertTime()), font, 1, 2,null));
		String supplierName = null!=cm.getSupplier()?cm.getSupplier().getSupplierName():"";
		String address = (null==supplier.getProvinceName()?"":supplier.getProvinceName())+
				  (null==supplier.getCityName()?"":supplier.getCityName())+
					(null==supplier.getAreaName()?"":supplier.getAreaName());
		String purchaserName = (null!=cm.getPurchaser()?cm.getPurchaser().getPurchaserName():"");
		String saleName = (null==cm.getSalesman()?"":cm.getSalesman());
		table1.addCell(getCell("供方："+ supplierName, font, 1, 2,null));
		table1.addCell(getCell("签订地点："+address,font, 1, 2,null));
		table1.addCell(getCell("需方："+purchaserName, font, 1, 2,null));
		table1.addCell(getCell("业务员："+saleName, font, 1, 2,null));
		document.add(table1);
		Paragraph text3 = new Paragraph("一、产品名称、证书名称、规格、重量、金额",font);
		document.add(text3);
		PdfPTable table2 = new PdfPTable(7);
		table2.setSpacingBefore(10);//设置表头间距
		table2.setTotalWidth(500);//设置表格的总宽度
		table2.setTotalWidth(new float[]{70,70,70,70,70,70,70});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table2.setLockedWidth(true);
		table2.addCell(getCell("产品名称", font, 1, 1,null));
		table2.addCell(getCell("证书名称",font,1, 1,null));
		table2.addCell(getCell("规格",font,1, 1,null));
		table2.addCell(getCell("单位", font, 1, 1,null));
		table2.addCell(getCell("重量", font, 1, 1,null));
		table2.addCell(getCell("单价(元)", font, 1, 1,null));
		table2.addCell(getCell("总金额(元)", font, 1, 1,null));
		//------------------订单商品详情-------------------------------
		Double totalWeight = 0d;
		if(null!=dtoGoods && dtoGoods.size()>0){
			for (int i = 0; i < dtoGoods.size(); i++) {
				B2bContractGoodsDtoMa ogm = new B2bContractGoodsDtoMa();
				ogm.UpdateMine(dtoGoods.get(i));
				table2.addCell(getCell(null==ogm.getSxGoods().getRawMaterialParentName()?"":ogm.getSxGoods().getRawMaterialParentName()+"纱", font, 1, 1,null));
				table2.addCell(getCell(null==ogm.getSxGoods().getCertificateName()?"无":ogm.getSxGoods().getCertificateName(), font, 1, 1,null));
				table2.addCell(getCell(ogm.getSxGoods().getSpecName(), font, 1, 1,null));
				table2.addCell(getCell("千克", font, 1, 1,null));
				table2.addCell(getCell(new BigDecimal(ogm.getWeight().toString()).toPlainString(), font, 1, 1,null));
				table2.addCell(getCell(new BigDecimal(ogm.getContractPrice().toString()).toPlainString(), font, 1, 1,null));	
				Double totalPrice = ArithUtil.round(ArithUtil.mul(ogm.getContractPrice(), ogm.getWeight()), 2);
				table2.addCell(getCell(new BigDecimal(totalPrice.toString()).toPlainString(), font, 1, 1,null));	
				totalWeight +=ogm.getWeight();
			}
			table2.addCell(getCell("合计：", font, 4, 3,null));
			table2.addCell(getCell(new BigDecimal(totalWeight.toString()).toPlainString(), font, 1, 1,null));	
			table2.addCell(getCell("", font, 1, 1,null));	
			table2.addCell(getCell(new BigDecimal(cm.getTotalAmount().toString()).toPlainString(), font, 1, 1,null));	
		}
		document.add(table2);
		String addressInfo ="";
		if(null != cm.getAddress()){
			addressInfo = (null==cm.getAddress().getProvinceName()?"":cm.getAddress().getProvinceName())+
		    		(null==cm.getAddress().getCityName()?"":cm.getAddress().getCityName())+
		    		(null==cm.getAddress().getAreaName()?"":cm.getAddress().getAreaName())+
		    		(null==cm.getAddress().getTownName()?"":cm.getAddress().getTownName())+
		    		(null==cm.getAddress().getAddress()?"":cm.getAddress().getAddress())+"  "+ 
		    		(null==cm.getAddress().getContacts()?"":cm.getAddress().getContacts()) +" "+ 
		    		(null==cm.getAddress().getContactsTel()?"":cm.getAddress().getContactsTel());
		}
		Paragraph text4 = new Paragraph("二、要求及技术标准：达到国家一等品标准。\n\n"+
				    		"三、交货地点："+ addressInfo.toString() +"\n\n"+
				    		"四、运输方式及费用负担："+cm.getLogisticsModel()+"。\n\n"	+
				    		"五、交货时间：全款到后当天发完。\n\n"+
				    		"六、交货数量：按订货数量。\n\n"+
				    		"七、结算方式及期限：现汇，签订后当日款到，合同有效，逾期作废。\n\n"+
				    		"八、纺织袋、纸箱、（集装箱）。\n\n"+
				    		"九、违约责任：本合同是经双方友好协商签定的，若发生纠纷，可经双方友好协商解决，如协商不成，向供方所在地人民法院提起诉讼。\n\n"+
				    		"十、本合同签字或盖章有效，传真件有效，一式二份，双方各执一份。\n\n"+
				    		"十一、备注：请客户按照生产日期顺序使用。\n\n"+
				    		"十二、收款信息：",font);
		document.add(text4);
		
		PdfPTable table3 = new PdfPTable(2);
		table3.setSpacingBefore(10);//设置表头间距
		table3.setTotalWidth(500);//设置表格的总宽度
		table3.setTotalWidth(new float[]{250,250});//设置表格的各列宽度 使用以上两个函数，必须使用以下函数，将宽度锁定。
		if(null != cm.getSupplier() && null != cm.getSupplier().getBankName() && null != cm.getSupplier().getBankAccount() && !"".equals(cm.getSupplier().getBankName())
				&& !"".equals(cm.getSupplier().getBankAccount())){
			table3.addCell(getCell("户名："+cm.getSupplier().getSupplierName(), font, 2, 2,null));
			table3.addCell(getCell("开户行："+cm.getSupplier().getBankName(), font, 2, 2,null));
			table3.addCell(getCell("账号："+cm.getSupplier().getBankAccount(), font, 2, 2,null));
		}else{
			table3.addCell(getCell("户名：", font, 2, 2,null));
			table3.addCell(getCell("开户行：", font, 2, 2,null));
			table3.addCell(getCell("账号：", font, 2, 2,null));
		}
		table3.addCell(getCell("供方签章：\n\n", font, 1, 2,null));
		table3.addCell(getCell("需方签章：\n\n", font, 1, 2,null));
		document.add(table3);
		Paragraph text5 = new Paragraph("请签字盖章回传："+(null!=supplier?supplier.getContactsTel():""),font);		
		text5.setAlignment(Element.ALIGN_RIGHT);
		document.add(text5);
		document.close();
	}
	
	/**
	 * 取消科学计数法
	 * @param number
	 * @return
	 */
	public String numberFormat(double number) {
		NumberFormat nf = NumberFormat.getInstance();
		// 结果未做任何处理
		return nf.format(number);
	}


	@Override
	public void downloadDelivery(String deliveryNumber, Integer type,
			HttpServletResponse response) {
		
		if(type == 1){
			commonService.exportDeliveryReq(deliveryNumber, PropertyConfig.getProperty("pdf_url")+deliveryNumber+"_1.pdf", response);
//			commonService.exportDeliveryReq(deliveryNumber, "D://pdf文件//"+deliveryNumber+"_1.pdf", response);
		}
		if(type == 2){
			commonService.downloadDeliveryEntrust(deliveryNumber, PropertyConfig.getProperty("pdf_url"), response);
//			commonService.downloadDeliveryEntrust(deliveryNumber, "D://pdf文件//", response);
		}
	}
}
