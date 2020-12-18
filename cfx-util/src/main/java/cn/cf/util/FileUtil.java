package cn.cf.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.cf.property.PropertyConfig;

/**
 * @date 2011-6-24
 * @author 王彬 文件处理工具类
 */

public class FileUtil {
	private static Logger logger =  LoggerFactory.getLogger(FileUtil.class);

	public static String fileUpload(HttpServletRequest request) {
		String relativeUploadPath = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
					.getFile("file");
			// 获得文件名：

			String originalPath = file.getOriginalFilename();

			String dateDir = (new SimpleDateFormat("yyyy/MM/dd"))
					.format(new Date()) + "/"; // 使用上传时的年月日构造文件存放路径
			// 创建文件上传目录
			String fullUploadPath = PropertyConfig.getProperty("FILE_PATH",
					"/opt/tomcat/webapps/") + dateDir;
			relativeUploadPath = dateDir;
			File path = new File(fullUploadPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			// 使用UUID生成新的随机文件名称
			UUID uuid = UUID.randomUUID();
			// 文件扩展名
			String extName = originalPath.substring(originalPath
					.lastIndexOf("."));
			String newName = uuid.toString() + extName;
			relativeUploadPath += newName;
			fullUploadPath = fullUploadPath + newName;

			File uploadFile = new File(fullUploadPath);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (Exception e) {
			logger.info("***** 图片上传出错 *****");
		} finally {
		}
		return relativeUploadPath;
	}

	/**
	 * 文件上传操作公共方法
	 * 
	 * @author 王彬2011-6-25
	 * @param request
	 *            ：页面请求对象
	 * @param clazz
	 *            ：表单提交数据对应的封装类类型（请保持表单输入框id和名称与实体类属性名一致）
	 * @return : Map<String, Object<?>> 包括三个key 1、key = "flag" :
	 *         true-表示上传成功，false表示上传失败 2、key = "savePath" :
	 *         文件保存相对路径，可用于数据库文件保存路径 3、key = "object" :
	 *         表单提交数据封装的对象，类型为传入的clazz类型，可用于保存表单的普通输入项提交数据到数据库
	 * @throws Exception
	 */
	public static Map<String, Object> uploadFile(HttpServletRequest request,
			Class<?> clazz) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", false);
		result.put("savePath", "");
		result.put("object", null);
		try {
			// 判断表单是否为上传表单类型，不是就不执行上传操作，上传要求form表单enctype类型:multipart/form-data
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				// 构造表单提交的数据所对应的封装类对象
				Object obj = clazz.newInstance();
				// 获取所有属性
				Field[] fields = clazz.getDeclaredFields();
				// 获取form表单提交数据
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload sfUpload = new ServletFileUpload(factory);
				sfUpload.setHeaderEncoding("UTF-8"); // 控制中文乱码
				// 解析Request对象，把表单中每一个输入项包装成FileItem
				List<FileItem> fileItems = sfUpload.parseRequest(request);
				Iterator<FileItem> iter = fileItems.iterator();
				// 遍历所有表单项，获取提交数据
				String savePath = "";
				FileItem fileItem = null;
				while (iter.hasNext()) {
					FileItem item = iter.next();
					// 普通表单输入项
					// if ("type".equalsIgnoreCase(item.getFieldName())) {
					// type = item.getString("UTF-8");
					// } else
					if (item.isFormField()) {
						// 获取输入项提交值
						String itemValue = item.getString("UTF-8");
						result.put(item.getFieldName(), itemValue);
						if (StringUtils.isNotBlank(itemValue)) {
							// 遍历封装类的属性，利用反射机制设置属性值
							for (Field field : fields) {
								if (field.getName().equalsIgnoreCase(
										item.getFieldName())) {
									// 不论是private还是protected修饰符的field都设置为可以直接赋值
									field.setAccessible(true);
									field.set(
											obj,
											convertType(itemValue,
													field.getType()));
									break;
								}
							}

						}
					} else {
						fileItem = item;
					}
				}
				if (null != fileItem) {
					// 文件上传表单项
					String originalPath = fileItem.getName();
					if (StringUtils.isBlank(originalPath)) {
						return result;
					} else {
						savePath = saveUploadFile(fileItem);
					}

				}
				if (StringUtils.isNotBlank(savePath)) {
					result.put("flag", true);
					result.put("savePath", savePath);
				}
				result.put("object", obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传操作出现异常，文件上传失败！" + e);
		}

		return result;

	}

	/**
	 * 文件上传操作 - 表单数据封装类型转换方法
	 * 
	 * @author Phoenix.zw 2011-6-25
	 * @param obj
	 *            ：需要被转换的表单数据对象
	 * @param clazz
	 *            ：转换后的类型
	 * @return 转换后的类型
	 * @throws Exception
	 */
	private static Object convertType(Object obj, Class<?> clazz)
			throws Exception {
		if (null == obj || obj.getClass().equals(clazz)) {
			return obj;
		} else if (obj.getClass().equals(BigDecimal.class)) {
			if (clazz.equals(Integer.class)) {
				return ((BigDecimal) obj).intValue();
			} else if (clazz.equals(Double.class)) {
				return ((BigDecimal) obj).doubleValue();
			} else if (clazz.equals(Long.class)) {
				return ((BigDecimal) obj).longValue();
			} else if (clazz.equals(Float.class)) {
				return ((BigDecimal) obj).floatValue();
			} else if (clazz.equals(Boolean.class)) {
				return 1 == ((BigDecimal) obj).intValue();
			} else if (clazz.equals(String.class)) {
				return ((BigDecimal) obj).toString();
			} else {
				try {
					return clazz.getMethod("fromInt",
							new Class[] { Integer.class }).invoke(null,
							((BigDecimal) obj).intValue());
				} catch (Exception e) {
					logger.debug("创建枚举对象（" + clazz.getName() + "）时失败", e);
					return null;
				}
			}
		} else if (obj.getClass().equals(Timestamp.class)) {
			return Timestamp.valueOf(obj.toString());
		} else {
			logger.debug("封装上传文件表单数据时缺少类型转换的映射！需要被转换的对象类型为："
					+ obj.getClass().getName() + ";期望被转换的类型为："
					+ clazz.getName());
			if ("int".equals(clazz.getName())) {
				return Integer.parseInt(obj.toString());
			}
			return String.valueOf(obj);
		}

	}

	/**
	 * 文件上传操作 - 保存执行方法
	 * 
	 * @author Phoenix.zw 2011-6-25
	 * @param item
	 *            ：文件上传-页面提交的上传文件项
	 * @return
	 * @throws Exception
	 */
	private static String saveUploadFile(FileItem item) throws Exception {
		// 文件原始路径
		String originalPath = item.getName();
		// 构造文件保存路径
		// String rootPath = PropertyConfig.getProperty("ROOT_UPLOAD_PATH"); //
		// 根目录
		// String prefixPath = PropertyConfig.getProperty("PREFIX_UPLOAD_PATH");
		// // 上传文件所在主目录
		String dateDir = (new SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date()) + "/"; // 使用上传时的年月日构造文件存放路径
		// 创建文件上传目录
		String fullUploadPath = PropertyConfig.getProperty("FILE_PATH",
				"/opt/tomcat/webapps/") + dateDir;
		String relativeUploadPath = dateDir;
		File path = new File(fullUploadPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		// 使用UUID生成新的随机文件名称
		UUID uuid = UUID.randomUUID();
		// 文件扩展名
		String extName = originalPath.substring(originalPath.lastIndexOf("."));
		String newName = uuid.toString() + extName;
		relativeUploadPath += newName;
		fullUploadPath = fullUploadPath + newName;
		// relativeUploadPath = relativeUploadPath + newName;
		// 上传的文件为网络文件，从网络读取
		if (originalPath.trim().toLowerCase().startsWith("http://")) {
			File file = new File(fullUploadPath);
			// 建立网络连接
			URL url = new URL(originalPath);
			logger.debug("网络文件地址：" + url.getHost() + " -- " + url.toString());
			HttpURLConnection huconn = (HttpURLConnection) url.openConnection();
			huconn.setConnectTimeout(180000);
			huconn.setReadTimeout(180000);
			// 读取并转存文件
			DataInputStream dis = new DataInputStream(huconn.getInputStream());
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buff = new byte[2048];
			int num = -1;
			while ((num = dis.read(buff)) != -1) {
				fos.write(buff, 0, num);
			}
			dis.close();
			fos.flush();
			fos.close();
		} else {
			// 上传的文件为本地文件，直接写入
			File file = new File(fullUploadPath);
			item.write(file);
			item.delete();

		}

		return relativeUploadPath;
	}

	public static String saveUploadFile(String originalPath) throws Exception {
		// 构造文件保存路径
		// String rootPath = PropertyConfig.getProperty("ROOT_UPLOAD_PATH"); //
		// 根目录
		// String prefixPath = PropertyConfig.getProperty("PREFIX_UPLOAD_PATH");
		// // 上传文件所在主目录
		String dateDir = (new SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date()) + "/"; // 使用上传时的年月日构造文件存放路径
		// 创建文件上传目录
		String fullUploadPath = PropertyConfig.getProperty("FILE_PATH",
				"/opt/tomcat/webapps/") + dateDir;
		String relativeUploadPath = dateDir;
		File path = new File(fullUploadPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		// 使用UUID生成新的随机文件名称
		UUID uuid = UUID.randomUUID();
		// 文件扩展名
		String extName = originalPath.substring(originalPath.lastIndexOf("."));
		String newName = uuid.toString() + extName;
		relativeUploadPath += newName;
		fullUploadPath = fullUploadPath + newName;

		File file = new File(fullUploadPath);
		// 建立网络连接
		URL url = new URL(originalPath);
		logger.debug("网络文件地址：" + url.getHost() + " -- " + url.toString());
		HttpURLConnection huconn = (HttpURLConnection) url.openConnection();
		huconn.setConnectTimeout(180000);
		huconn.setReadTimeout(180000);
		// 读取并转存文件
		DataInputStream dis = new DataInputStream(huconn.getInputStream());
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buff = new byte[2048];
		int num = -1;
		while ((num = dis.read(buff)) != -1) {
			fos.write(buff, 0, num);
		}
		dis.close();
		fos.flush();
		fos.close();
		return relativeUploadPath;
	}
	/**
	 * 将文件转化成字符串
	 * @param path
	 * @return
	 * @throws IOException
	 */
    public static String readFile(String path) throws IOException {
    	File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileReader fr = new FileReader(path);
//        BufferedReader br=new BufferedReader(fr);
        
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
        BufferedReader br = new BufferedReader(isr);
        
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp+" ");
            temp=br.readLine();
        }
        fr.close();
        br.close();
        return sb.toString();
    }
    
    /**
     * 将字符串写入xml文件
     * @param Data
     * @param filePath
     */
	public static void writeXmlFile(String Data, String filePath) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		File distFile = null;
		try {
			distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(Data));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			writeXmlFile(Data,filePath);
		}
	}
	
	/**
     * 将字符串写入txt文件
     * @param Data
     * @param filePath
     */
	public static void writeTxtFile(String data,File file){
		FileOutputStream outSTr = null;
		BufferedOutputStream buff = null;
        try {
        	if (!file.getParentFile().exists()){
        		file.getParentFile().mkdirs();
        	}
        	outSTr = new FileOutputStream(file);
            buff = new BufferedOutputStream(outSTr);
            buff.write(data.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
            	 buff.flush();
                 buff.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	public static void main(String[] args) {
		try {
			FileUtil.saveUploadFile("blob:http://127.0.0.1/eacf65b5-a30f-4b12-9d9a-b1d1cf0f061b");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
