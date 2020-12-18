package cn.cf.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.property.PropertyConfig;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import cn.cf.util.DateUtil;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

public class ExportUtil<T> {

	/**
	 * 
	 * @param name
	 *            要导出的文件名称
	 * @param list
	 *            要导出的实体
	 * @param response
	 *            请求返回
	 */
	public void exportUtil(String name, List<T> list, HttpServletResponse response,HttpServletRequest request) {
		
		URL url = this.getClass().getClassLoader().getResource("template");
		String templateFileNameFilePath = url.getPath() + "/" + name + ".xls"; // 模板路径

		String tempPath = PropertyConfig.getProperty("FILE_PATH");
		File file = new File(tempPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		String fileName = name + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".xls";
		String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		FileInputStream is = null;
		OutputStream out = null;
		try {
			transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			is = new FileInputStream(destFileNamePath);
//			 3.通过response获取ServletOutputStream对象(out)
			int len = 0;  
            byte[] buffer = new byte[1024];  
            out = response.getOutputStream();  
           while((len = is.read(buffer)) > 0) {  
               out.write(buffer,0,len);  
           	}  
           out.flush();
           is.close();
           out.close();
			// 删除临时文件
			// delTempFile(tempPath);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("unused")
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
	
/**
 * 
 * @param name 导出文件名
 * @param fieldsArray 传入参数数组
 * @param response
 * @param request
 */
public String exportPDFUtil(String name, String[] fieldsArray,Integer pageNumbers, HttpServletResponse response,HttpServletRequest request) {
		URL url = this.getClass().getClassLoader().getResource("template");
		String templateFileNameFilePath = url.getPath() + "/" + name + ".pdf"; // 模板路径

		String tempPath = PropertyConfig.getProperty("FILE_PATH");
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
	        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", 
	        		BaseFont.NOT_EMBEDDED); 
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
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			doc.close();   
		}
       return destFileNamePath;
	}

public String exportDynamicUtil(String templateName,String newName, List<T> list) {

		URL url = this.getClass().getClassLoader().getResource("template");
		String templateFileNameFilePath = url.getPath() + "/" + templateName + ".xls"; // 模板路径

		String tempPath = PropertyConfig.getProperty("FILE_PATH");
		File file = new File(tempPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String fileName = newName + ".xls";
		String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		FileInputStream is = null;
		try {
			transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
			is = new FileInputStream(destFileNamePath);
			is.close();
			// 删除临时文件
			// delTempFile(tempPath);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return destFileNamePath;
	}



	public static void main(String[] args) {

	}
	
	/**
	 * 导出以csv的格式保存
	 * @param templateName
	 * @param newName
	 * @param list
	 * @return
	 */
	public String exportCSVDynamicUtil(String templateName,String newName, List<T> list) {

		URL url = this.getClass().getClassLoader().getResource("template");
		String templateFileNameFilePath = url.getPath() + "/" + templateName + ".xls"; // 模板路径

		String tempPath = PropertyConfig.getProperty("FILE_PATH");
		File file = new File(tempPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		String fileName = newName + ".csv";
		String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("counts", list.size());
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		FileInputStream is = null;
		try {
			transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
			is = new FileInputStream(destFileNamePath);
			is.close();
			// 删除临时文件
			// delTempFile(tempPath);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return destFileNamePath;
	}
}
