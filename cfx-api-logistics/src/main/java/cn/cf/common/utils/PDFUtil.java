package cn.cf.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.transaction.SystemException;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import cn.cf.dto.OrderDeliveryDto;

public class PDFUtil {

	/**
	 * 生成pdf模版
	 * @param pdfTemplate
	 * @param pdfParam
	 * @param ttfPath
	 * @param mandateUrls
	 * @return
	 * @throws SystemException
	 */
	public byte[] generatePdfByTemplate(byte[] pdfTemplate, String[] pdfParam, String ttfPath, String[] mandateUrls)
			throws SystemException {
		PdfReader pdfReader = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfStamper stamper = null;
		try {
			// 读取pdf模板
			pdfReader = new PdfReader(pdfTemplate);
			stamper = new PdfStamper(pdfReader, baos);
			// 获取所有表单字段数据
			AcroFields form = stamper.getAcroFields();
			form.setGenerateAppearances(true);
			// 设置
			ArrayList<BaseFont> fontList = new ArrayList<>();
			fontList.add(getMsyhBaseFont(ttfPath + "Duality.ttf"));
			form.setSubstitutionFonts(fontList);
			int i = 0;
			// 填充form
			for (String formKey : form.getFields().keySet()) {
				form.setField(formKey, pdfParam[i++]);
			}
			// 如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.setFormFlattening(true);
			stamper.close();
			return baos.toByteArray();
		} catch (DocumentException | IOException e) {

		} finally {
			if (stamper != null) {
				try {
					stamper.close();
				} catch (DocumentException | IOException e) {

				}
			}
			if (pdfReader != null) {
				pdfReader.close();
			}
		}
		throw new SystemException("pdf generate failed");
	}

	/**
	 *  设置字体
	 * @param ttfPath
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	private BaseFont getMsyhBaseFont(String ttfPath) throws IOException, DocumentException {
		try {
			return BaseFont.createFont(ttfPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException | IOException e) {

		}
		return getDefaultBaseFont();
	}
	
	private BaseFont getDefaultBaseFont() throws IOException, DocumentException {
		return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	}

	public String formatDate(Date date,String format){
		if(date==null){
			return "";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format); 
		String str =sdf.format(date);
		return str;
	}
	/**
	 * 下载PDF
	 * @param form
	 * @param templatePath
	 * @param out
	 * @throws SystemException
	 */
	public void download(OrderDeliveryDto form, String templatePath, OutputStream out) throws SystemException {
		String[] fieldsArray = { form.getDeliveryNumber(), form.getOrderPK(), 
				form.getFromProvinceName() + form.getFromCityName() + form.getFromAreaName() + form.getFromTownName()+form.getFromAddress(),
				form.getFromContacts(), form.getFromCompanyName(), form.getFromContactsTel(),
				formatDate(form.getDeliveryTime(), "yyyy-MM-dd HH:mm:ss"),form.getProductName(),
				form.getSpecName(), form.getBatchNumber(), form.getGradeName(),
				form.getBoxes() == null ? "0" : String.valueOf(form.getBoxes()),
				form.getWeight() == null ? "0.0" : String.valueOf(CommonUtil.formatDoubleFour(form.getWeight())),
				form.getToProvinceName() + form.getToCityName() + form.getToAreaName() + form.getToTownName()+ form.getToAddress(),
				form.getToContacts(), form.getToCompanyName(), form.getToContactsTel(),
				formatDate(form.getArrivedTimeStart(), "yyyy-MM-dd HH:mm") + " ~ "+ formatDate(form.getArrivedTimeEnd(), "yyyy-MM-dd HH:mm"), 
				form.getRemark(), form.getDeliveryNumber(), form.getOrderPK(),
				form.getFromProvinceName() + form.getFromCityName() + form.getFromAreaName() + form.getFromTownName()+form.getFromAddress(),
				form.getFromContacts(), form.getFromCompanyName(), form.getFromContactsTel(),
				formatDate(form.getDeliveryTime(), "yyyy-MM-dd HH:mm:ss"), form.getProductName(), form.getSpecName(), form.getBatchNumber(),
				form.getGradeName(), form.getBoxes() == null ? "0" : String.valueOf(form.getBoxes()),
				form.getWeight() == null ? "0.0" : String.valueOf(CommonUtil.formatDoubleFour(form.getWeight())),
				form.getToProvinceName() + form.getToCityName() + form.getToAreaName() + form.getToTownName()+ form.getToAddress(),
				form.getToContacts(), form.getToCompanyName(), form.getToContactsTel(),
				formatDate(form.getArrivedTimeStart(), "yyyy-MM-dd HH:mm") + " ~ "+ formatDate(form.getArrivedTimeEnd(), "yyyy-MM-dd HH:mm:ss"), 
				form.getRemark(), form.getDeliveryNumber(), form.getOrderPK(),
				form.getFromProvinceName() + form.getFromCityName() + form.getFromAreaName() + form.getFromTownName()+form.getFromAddress(), 
				form.getFromContacts(), form.getFromCompanyName(), form.getFromContactsTel(),
				formatDate(form.getDeliveryTime(), "yyyy-MM-dd HH:mm:ss"), form.getProductName(), form.getSpecName(), form.getBatchNumber(),
				form.getGradeName(), form.getBoxes() == null ? "0" : String.valueOf(form.getBoxes()),
				form.getWeight() == null ? "0.0" : String.valueOf(CommonUtil.formatDoubleFour(form.getWeight())),
				form.getToProvinceName() + form.getToCityName() + form.getToAreaName() + form.getToTownName()+ form.getToAddress(), 
				form.getToContacts(), form.getToCompanyName(), form.getToContactsTel(),
				formatDate(form.getArrivedTimeStart(), "yyyy-MM-dd HH:mm") + " ~ "+ formatDate(form.getArrivedTimeEnd(), "yyyy-MM-dd HH:mm"),
				form.getRemark() };
		String filePath;
		byte[] pdfTemplate;
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		try {
			// 获取模板文件路径
			filePath = ResourceUtils.getURL(templatePath + "deliveryFormForm.pdf").getPath();
			// 获取模板文件字节数据
			pdfTemplate = IOUtils.toByteArray(new FileInputStream(filePath));
			// 自提委托书
			String[] mandateUrls = null;
			if (null != form.getMandateUrl() && !"".equals(form.getMandateUrl())) {
				mandateUrls = form.getMandateUrl().split(",");
			}
			// 获取渲染数据后pdf字节数组数据
			byte[] pdfByteArray = generatePdfByTemplate(pdfTemplate, fieldsArray, templatePath, mandateUrls);
			pdfOutputStream.write(pdfByteArray);
			pdfOutputStream.writeTo(out);
			pdfOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pdfOutputStream.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
