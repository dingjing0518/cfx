package cn.cf.common.utils;

import cn.cf.property.PropertyConfig;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;
    /**
     * 压缩excel文件
     * @param sourceFilePath 文件集合
     * @param name 导出文件标识名称
     * @return
     */
    public static String fileToZip(List<File> sourceFilePath, String name) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        String zipFilePath = outPutPath(name);
        try {
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                zipFile.delete();
            } else {
                if (sourceFilePath != null && sourceFilePath.size() > 0) {
                    fos = new FileOutputStream(zipFile);
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));
                    byte[] bufs = new byte[CACHE_SIZE * 10];
                    for (int i = 0; i < sourceFilePath.size(); i++) {
                        String fileName = sourceFilePath.get(i).getName();
                        if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")|| fileName.endsWith(".csv")) {
                            //创建ZIP实体，并把要压缩的文件添加进压缩包实体对象
                            ZipEntry zipEntry = new ZipEntry(sourceFilePath.get(i).getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFilePath.get(i));
                            bis = new BufferedInputStream(fis, CACHE_SIZE * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, CACHE_SIZE * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                            //删除文件
                            if (sourceFilePath.get(i).isFile()){
                                sourceFilePath.get(i).delete();
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭流
            try {
                if (null != bis) bis.close();
                if (null != zos) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return zipFilePath;
    }

    private static String outPutPath(String name) {

        String tempPath = PropertyConfig.getProperty("FILE_PATH");
        File filePath = new File(tempPath);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }
        String fileName = name + ".zip";
        String zipFilePath = tempPath + "/" + fileName;// 生成文件路径

        return zipFilePath;
    }


}
