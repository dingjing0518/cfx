package cn.cf.service.platform.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import cn.cf.dto.B2bFinanceRecordsDtoEx;
import cn.cf.property.PropertyConfig;
import cn.cf.service.platform.FileOperationService;
import cn.cf.util.DateUtil;
import cn.cf.util.DownLoadUtils;
import cn.cf.util.KeyUtils;

@Service
public class FileOperationServiceImpl implements FileOperationService{
	
	private static String excelPath = PropertyConfig.getProperty("excel_url");
	
	@Override
	public void exportFinanceRecords(List<B2bFinanceRecordsDtoEx> list,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("finaceRecord.xls");
		String fileName = "/financeRecords" + KeyUtils.getUUID() + ".xls";
		File path = new File(excelPath);
		if(!path.exists()){
			path.mkdir();
		}
		String destFileName = excelPath + fileName;
		String downLoadName = "financeRecords"+DateUtil.formatYearMonthDayHMS(new Date());
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

}
