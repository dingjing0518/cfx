package cn.cf.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.property.PropertyConfig;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ExportPDFUtil {

	/**
	 * 
	 * @param name 模板文件名(不加扩展名)
	 * @param fieldsArray 传入参数数组
	 * @param pageNumbers 导出PDF文件页数
	 * @param dir 模板文件所在文件目录
	 * @param response
	 * @param request
	 */
	public void exportPDFUtil(String name, String[] fieldsArray,Integer pageNumbers,String dir, HttpServletResponse response,HttpServletRequest request) {
			URL url = this.getClass().getClassLoader().getResource(dir);
			String templateFileNameFilePath = url.getPath() + "/" + name + ".pdf"; // 模板路径


			String tempPath = PropertyConfig.getProperty("FILE_PATH");
//			String path = request.getSession().getServletContext().getRealPath("/");
//			String tempPath = path + "temp";
			File file = new File(tempPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			String fileName = name + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".pdf";
			String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
			PdfReader reader;
	        FileOutputStream out;
	        ByteArrayOutputStream bos;
	        PdfStamper stamper;
	        Document doc = null;
	        try {
				out = new FileOutputStream(destFileNamePath);//输出路径
				reader = new PdfReader(templateFileNameFilePath);//读取pdf模板路径
		        bos = new ByteArrayOutputStream();
		        stamper = new PdfStamper(reader, bos);
		        AcroFields form = stamper.getAcroFields();
		        BaseFont bf = BaseFont.createFont(url.getPath() + "/Duality.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		        
		         form.addSubstitutionFont(bf);
		        java.util.Iterator<String> it = form.getFields().keySet().iterator();
		        int i = 0;
		        while(it.hasNext()){
	                String columnName = it.next().toString();
	                form.setField(columnName, fieldsArray[i++]);
	            }
		        stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
		        stamper.close();
	            
	            doc = new Document();
	            PdfCopy copy = new PdfCopy(doc, out);
	            doc.open();
	            
	            if(pageNumbers != null && pageNumbers > 0){
	            	for (int j = 1; j < pageNumbers+1; j++) {
	            		 PdfImportedPage importPage = copy.getImportedPage(
	                             new PdfReader(bos.toByteArray()), j);
	                     copy.addPage(importPage);
					}
	            }else{
	            	 PdfImportedPage importPage = copy.getImportedPage(
	                         new PdfReader(bos.toByteArray()), 1);
	                 copy.addPage(importPage);	
	            }
	            doc.close();
	         //删除临时文件
	         delTempFile(tempPath);  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}finally{
				doc.close();
				delTempFile(tempPath);
			}
		}
	
	
	private void delTempFile(String tempPath) {
		File file = new File(tempPath);
		if (file.exists()) {
			String[] tempList = file.list();
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				temp = new File(tempPath + File.separator + tempList[i]);
				if (temp.isFile()) {
					temp.delete();
				}
			}
		}
	}
}
