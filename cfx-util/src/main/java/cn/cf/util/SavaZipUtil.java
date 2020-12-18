package cn.cf.util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import cn.cf.property.PropertyConfig;
//解压zip文件
public class SavaZipUtil {
    public static String ACCESS_KEY_ID = null;
    public static String ACCESS_KEY_SECRET = null;
    public static String ENDPOINT = null;
    public static String BUCKETNAME = null;
    private static final int  BUFFER_SIZE = 2 * 1024;
    static {
        try {
            ACCESS_KEY_ID = PropertyConfig.getProperty("oss_access_key_id");
            ACCESS_KEY_SECRET = PropertyConfig
                    .getProperty("oss_access_key_secret");
            ENDPOINT = PropertyConfig.getProperty("oss_endpoint");
            BUCKETNAME = PropertyConfig.getProperty("oss_bucketname");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String saveZip(String source,String path) {

//        String source="D:\\客户导入.zip";
        String password = "password";
        try {
            File zipFile = new File(source);
            ZipFile zFile =
                    new ZipFile(zipFile);
// ZipFile指向磁盘上的.zip文件

            zFile.setFileNameCharset("UTF-8");

            File destDir =
                    new File(path);
// 解压目录

            if (zFile.isEncrypted()) {

                zFile.setPassword(password.toCharArray());
// 设置密码

            }
            zFile.extractAll(path);
// 将文件抽出到解压目录(解压)


            List<net.lingala.zip4j.model.FileHeader>
                    headerList = zFile.getFileHeaders();

            List<File> extractedFileList =
                    new ArrayList<File>();
            for (FileHeader
                    fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {

                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));

                }
            }
            File[] extractedFiles =
                    new File[extractedFileList.size()];

            extractedFileList.toArray(extractedFiles);


//            for (File
//                    f : extractedFileList) {
//                System.out.println(f.getAbsolutePath() + "....");
//            }
        } catch (ZipException
                e) {
        }
                 return  null;
    }


    /**     * 压缩文件     *
     * * @param sourceFilePath 源文件路径
     * * @param zipFilePath    压缩后文件存储路径
     * * @param zipFilename    压缩文件名     */
    public static void compressToZip(String sourceFilePath, String zipFilePath, String zipFilename)
    {
        File sourceFile = new File(sourceFilePath);
        File zipPath = new File(zipFilePath);
        if (!zipPath.exists()) {
            zipPath.mkdirs();
        }
        File zipFile = new File(zipPath + File.separator + zipFilename);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            writeZip(sourceFile, "", zos);            //文件压缩完成后，删除被压缩文件
            // boolean flag = deleteDir(sourceFile);

                 }
        catch (Exception e) {
            e.printStackTrace();
             throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**     * 遍历所有文件，压缩
     * *
     * * @param file       源文件目录
     * * @param parentPath 压缩文件目录
     * * @param zos        文件流     */
    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.isDirectory()) {            //目录
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {            //文件
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {                //指定zip文件夹
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }


    }

    // 删除指定文件夹下所有文件

    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {			return flag;		}
        if (!file.isDirectory()) {			return flag;		}
        String[] tempList = file.list();		File temp = null;
        for (int i = 0; i < tempList.length; i++) {			if (path.endsWith(File.separator)) {
            temp = new File(path + tempList[i]);			}
            else {
            temp = new File(path + File.separator + tempList[i]);			}
            if (temp.isFile()) {				temp.delete();			}
            if (temp.isDirectory()) {				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                flag = true;			}		}		return flag;	}


    public static void delFolder(String folderPath) {
        try {
        delAllFile(folderPath); // 删除完里面所有内容
         }
         catch (Exception e) {			e.printStackTrace();		}	}


}