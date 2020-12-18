package cn.cf.common;

import cn.cf.dto.B2bOrderExtDto;
import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class PoiOrderExcelUtil {

    /**
     * 创建excel
     * @param list 是需要写入excel中的数据
     * @return
     */
    private static XSSFWorkbook createUserListExcel(List<B2bOrderExtDto> list, String[] title){
        // 1.创建HSSFWorkbook，一个HSSFWorkbook对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 2.在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("sheet1");
        // 3.设置表头，即每个列的列名
        // 3.1创建第一行
        XSSFRow row = sheet.createRow(0);
        // 此处创建一个序号列
        row.createCell(0).setCellValue("序号");
        // 将列名写入
        for (int i = 0; i < title.length; i++) {
            // 给列写入数据,创建单元格，写入数据
            row.createCell(i+1).setCellValue(title[i]);
        }
        // 写入正式数据
        for (int i = 0; i < list.size(); i++) {

            B2bOrderExtDto obj = list.get(i);

//            String jStr = "{\"pk\":\"00ba1c61f44045449b112006a81651be\",\"productPk\":\"8\",\"productName\":\"POY\",\"specifications\":\"规格1\",\"specPk\":\"379df4277f5e43beb3fda43dab04101d\",\"specName\":\"规格大类1\",\"seriesPk\":\"5ebe674a051948939ed09b2a7ff98015\",\"seriesName\":\"规格系列1\",\"gradePk\":\"b7b5722194374e1393eef10b0e86bbba\",\"gradeName\":\"等级1\",\"varietiesPk\":\"d0e26f5d46664294b8bbd5858ec1059d\",\"varietiesName\":\"品种1\",\"seedvarietyPk\":\"33fbd76c45984e37934bd3c8bf02380e\",\"seedvarietyName\":\"子品种1\",\"packNumber\":\"包装形式1\",\"batchNumber\":\"主批号1\",\"plantPk\":\"ec8f703517b911e99bf57cd30ac4f848\",\"plantName\":\"厂区名称1\",\"warePk\":\"ec902c1c17b911e99bf57cd30ac4f848\",\"wareName\":\"仓库名称1\",\"suitRangeDescribe\":\"备注1\",\"purposeDescribe\":\"用途1\",\"distinctNumber\":\"区分号1\",\"packageFee\":\"10.00\",\"loadFee\":\"0.00\",\"adminFee\":\"0.00\",\"unit\":\"1\"}";
//            //json解析
//            JSONObject jObject = JSONObject.fromObject(jStr);
//            jObject.get("productPk");
            // 创建行
//            row = sheet.createRow(i+1);
//            // 序号
//            row.createCell(0).setCellValue(i+1);
//
//            row.createCell(1).setCellValue(obj.getOrderNumber());
//            sheet.autoSizeColumn(1, true);
//            row.createCell(2).setCellValue(obj.getPurchaserName()==null?"":obj.getPurchaserName());
//            row.createCell(3).setCellValue(obj.getAfterBoxes()==null?0d:obj.getAfterBoxes());
//            row.createCell(4).setCellValue(obj.getAddress()== null?"":obj.getAddress());
        }
        /**
         * 上面的操作已经是生成一个完整的文件了，只需要将生成的流转换成文件即可；
         * 下面的设置宽度可有可无，对整体影响不大
         */
        // 设置单元格宽度
        int curColWidth = 0;
        for (int i = 0; i <= title.length; i++) {
            // 列自适应宽度，对于中文半角不友好，如果列内包含中文需要对包含中文的重新设置。
            sheet.autoSizeColumn(i, true);
            // 为每一列设置一个最小值，方便中文显示
            curColWidth = sheet.getColumnWidth(i);
            if(curColWidth<2500){
                sheet.setColumnWidth(i, 2500);
            }
            // 第3列文字较多，设置较大点。
            sheet.setColumnWidth(3, 8000);
        }
        return wb;
    }


    /**
     * 订单列表导出
     * @param listresult
     */
    public static String downOrderList(List<B2bOrderExtDto> listresult, String[] title,String name, HttpServletResponse response){
        // getTime()是一个返回当前时间的字符串，用于做文件名称
        String tempPath = PropertyConfig.getProperty("FILE_PATH");
        String fileName = name + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".xls";
        String destFileNamePath = tempPath + "" + fileName;// 生成文件路径
        // 1.生成Excel
        XSSFWorkbook userListExcel = createUserListExcel(listresult,title);
        FileInputStream is = null;
        OutputStream out = null;
        try{
            // 输出成文件
            File file = new File(fileName);
            if(file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
            // TODO 生成的wb对象传输
            FileOutputStream outputStream = new FileOutputStream(new File(destFileNamePath));
            userListExcel.write(outputStream);
            outputStream.close();
            //下载表格
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            is = new FileInputStream(destFileNamePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while((len = is.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            out.flush();
            is.close();
            out.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return name;
    }
}
