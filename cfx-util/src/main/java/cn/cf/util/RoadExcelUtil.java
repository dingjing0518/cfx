package cn.cf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class RoadExcelUtil {
//    public static void main(String[] args) {
//        Workbook wb =null;
//        Sheet sheet = null;
//        Row row = null;
//        List<Map<String,String>> list = null;
//        String cellData = null;
//        File file = new File("D:\\abc\\客户导入");
//        File [] files = file.listFiles();
//        List<File> imglist=new ArrayList<>();
//        List<String> imgnamelist=new ArrayList<>();
//        String filePath = "D:\\a.xlsx";
//        for (int i = 0; i < files.length; i++)
//        {
//            File file1 = files[i];
//            file1.getName();//根据后缀判断
//            if (!file1.getName().contains("xlsx")){
//                imglist.add(file1);
//            }
//        }
//        for (int i=0;i<imglist.size();i++) {
//        File file2 =imglist.get(1);
//        String uploadpath = OSSUtils.ossMangerUpload(file2, Constants.TEMP);
//        imgnamelist.add(uploadpath);
//       }
//        String columns[] = {"employeeName","companyName","organizationCode","mobile","contracts","contractsTel","provinceName","cityName","areaName","address","employeeNumber","bankName","bankAccount"};
//        wb = readExcel(filePath);
//        if(wb != null){
//            //用来存放表中数据
//            list = new ArrayList<Map<String,String>>();
//            //获取第一个sheet
//            sheet = wb.getSheetAt(0);
//            //获取最大行数
//            int rownum = sheet.getPhysicalNumberOfRows();
//            //获取第一行
//            row = sheet.getRow(0);
//            //获取最大列数
//            int colnum = row.getPhysicalNumberOfCells();
//            for (int i = 1; i<rownum; i++) {
//                Map<String,String> map = new LinkedHashMap<String,String>();
//                row = sheet.getRow(i);
//                if(row !=null){
//                    for (int j=0;j<colnum;j++){
//                        cellData = (String) getCellFormatValue(row.getCell(j));
//                        map.put(columns[j], cellData);
//                    }
//                }else{
//                    break;
//                }
//                list.add(map);
//            }
//        }
//        //遍历解析出来的list
//        RoadExcelUtil roadExcelUtil=new RoadExcelUtil();
//        List<CustomerImport> customerImportList=roadExcelUtil.parse(list,CustomerImport.class);
////        for (Map<String,String> map : list) {
////            for (Entry<String,String> entry : map.entrySet()) {
////                System.out.print(entry.getKey()+":"+entry.getValue()+",");
////            }
////            System.out.println();
////        }
//
//    }
    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                wb = new XSSFWorkbook(is);
            }else{
                wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    DecimalFormat df = new DecimalFormat("#");
                    cellValue = df.format(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
//list<Mpa>转list
public static List parse(List list,Class obj){
    //生成集合
    ArrayList ary = new ArrayList();
    //遍历集合中的所有数据
    for(int i = 0; i<list.size(); i++){
        try {
            ////生成对象实历 将MAP中的所有参数封装到对象中
            Object o = addProperty( (Map)list.get(i),obj.newInstance() );
            //把对象加入到集合中
            ary.add(o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //返回封装好的集合
    return ary;
}
    public static Object addProperty(Map map,Object obj){
        //遍历所有名称
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            //取得名称
            String name = it.next().toString();
            //取得值
            String value = map.get(name).toString();

            try{
                //取得值的类形
                Class type = PropertyUtils.getPropertyType(obj, name);

                if(type!=null){
                    //设置参数
                    PropertyUtils.setProperty(obj, name, ConvertUtils.convert(value, type));

                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }
        return obj;

    }

}


