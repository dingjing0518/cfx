package cn.cf.utils;


import com.jcraft.jsch.*;
import org.apache.poi.util.IOUtils;
 
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by zhenghao on 2018/9/18.
 */
public class SftpUtils {
 
    private ChannelSftp sftp;
 
    private Session session;
    /**
     * SFTP 登录用户名
     */
    private String username;
    /**
     * SFTP 登录密码
     */
    private String password;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * SFTP 服务器地址IP地址
     */
    private String host;
    /**
     * SFTP 端口
     */
    private int port;
 
 
    /**
     * 构造基于密码认证的sftp对象
     */
    public SftpUtils(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }
 
    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SftpUtils(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }
 
    public SftpUtils() {
    }
 
 
    /**
     * 连接sftp服务器
     */
    public void login() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }
 
            session = jsch.getSession(username, host, port);
 
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
 
            session.setConfig(config);
            session.connect();
 
            Channel channel = session.openChannel("sftp");
            channel.connect();
 
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
 
 
    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     */
    public boolean upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            sftp.put(input, sftpFileName);  //上传文件
            return true;
        } catch (SftpException e) {
            return false;
        }
    }
 
    public void cd(String directory) throws SftpException {
        if (directory != null && !"".equals(directory) && !"/".equals(directory)) {
            sftp.cd(directory);
        }
 
    }
 
 
    /**
     * 下载文件。
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) {
        System.out.println("download:" + directory + " downloadFile:" + downloadFile + " saveFile:" + saveFile);
 
        File file = null;
        try {
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (SftpException e) {
            e.printStackTrace();
            if (file != null) {
                file.delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (file != null) {
                file.delete();
            }
        }
 
    }

    /**
     * 解压文件
     * @param zipPath 要解压的目标文件
     * @param descDir 指定解压目录
     * @return 解压结果：成功，失败
     */
    public boolean decompressZip(String zipPath, String descDir) {
        File zipFile = new File(zipPath);
        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, Charset.forName("gbk"));//防止中文目录，乱码
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //保存文件路径信息
                System.err.println("当前zip解压之后的路径为：" + outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
            flag = true;
            //关闭。
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
 
    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
 
        byte[] fileData = IOUtils.toByteArray(is);
 
        return fileData;
    }
 
 
    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        sftp.rm(deleteFile);
    }
 
 
    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }


    
    //上传文件测试
    public static void main(String[] args) throws SftpException, IOException {
    	SftpUtils sftp = new SftpUtils("huaxianhui","106.120.68.100", 8001, "D://bank//gs//id_rsa");
        sftp.login();
       sftp.download("/RiskReport/download/", "7c6c254a-16cb-4654-b42c-5e2e5beadf44.zip", "D://bank//gt//6.zip");
        sftp.logout();
    }
}
