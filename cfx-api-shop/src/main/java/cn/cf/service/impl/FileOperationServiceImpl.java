package cn.cf.service.impl;

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
import cn.cf.constant.Block;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.property.PropertyConfig;
import cn.cf.service.FileOperationService;
import cn.cf.util.DateUtil;
import cn.cf.util.DownLoadUtils;
import cn.cf.util.KeyUtils;


@Service
public class FileOperationServiceImpl implements FileOperationService {
	
	

	@Override
	public void exportGoods(List<B2bGoodsDtoEx> list, HttpServletRequest request,HttpServletResponse response,Sessions sessions) {
		B2bCompanyDto b2bCompanyDto = sessions.getCompanyDto();
		String block = b2bCompanyDto.getBlock();
		InputStream resourceAsStream = null;
		if (block.equals(Block.CF.getValue())) {//cf
			resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("goods_cf.xls");
		}
		if (block.equals(Block.SX.getValue())) {//sx
			resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("goods_sx.xls");
		}
		if (block.contains(Block.CF.getValue()) && block.contains(Block.SX.getValue())) {//cf,sx
			resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("goods.xls");
		}
		String fileName = "/goods" + KeyUtils.getUUID() + ".xls";
		File pathFile = new File(PropertyConfig.getProperty("excel_url"));
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		String destFileName = PropertyConfig.getProperty("excel_url") + fileName;
		String downLoadName = "goods"+DateUtil.formatYearMonthDayHMS(new Date());
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
		}
		Map<String, Object> beans = new HashMap<>();
		beans.put("dto", list);
		beans.put("dates", DateUtil.formatYearMonthDay(new Date()));
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
