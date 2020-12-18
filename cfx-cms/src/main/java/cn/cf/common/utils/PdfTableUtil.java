package cn.cf.common.utils;

import cn.cf.property.PropertyConfig;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PdfTableUtil {

    /**
     *
     * @param value
     *            值
     * @param font
     *            字体
     * @param colspan
     *            合并宽
     * @param alginCenter
     *            是否居中
     * @param rowspan
     *            合并高
     * @return
     */
    public static PdfPCell getCell(String value, Font font, Integer colspan,
                             boolean alginCenter, Integer rowspan) {
        PdfPCell cell = new PdfPCell();
   
        Paragraph p = new Paragraph(value, font);
        cell.setPhrase(p);
        cell.setColspan(colspan);
        font.setSize(6);
        if (rowspan != null) {
            cell.setRowspan(rowspan);
        }
        if (alginCenter) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        }
        return cell;
    }
    public static boolean downloadLocal(HttpServletResponse response,
                                  HttpServletRequest req, String fileName)
            throws FileNotFoundException {
        boolean flag = false;
        try {

            String tempPath = PropertyConfig.getProperty("FILE_PATH");
            File file = new File(tempPath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            String filePath = tempPath + "/" + fileName + ".pdf";
            // 读到流中
            FileInputStream inStream = new FileInputStream(filePath);// 文件的存放路径
            byte[] b = new byte[100];
            int len;
            // 循环取出流中的数据
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
                flag = true;
            }
            inStream.close();
            // 删除临时文件
            delTempFile(tempPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void delTempFile(String tempPath) {
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


//    public static void cellLayout(PdfPCell cell, Rectangle rect,PdfContentByte[] canvas) {
//        PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
//        cb.setLineDash(new float[] { 3.0f, 3.0f }, 0);
//        cb.stroke();
//    }

    public static void cellLayout(PdfPCell cell, Rectangle position,PdfContentByte[] canvases) {
// TODO Auto-generated method stub
        PdfContentByte cb = canvases[PdfPTable.LINECANVAS];
        cb.saveState();
        cb.setLineWidth(0.5f);
        cb.setLineDash(new float[] { 5.0f, 5.0f }, 0);
        cb.moveTo(position.getLeft(), position.getBottom());
        cb.lineTo(position.getRight(), position.getBottom());
        cb.stroke();
        cb.restoreState();
    }

}
