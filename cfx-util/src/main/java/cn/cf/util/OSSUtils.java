/**
 * 
 */
package cn.cf.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.util.UriUtils;

import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

/**
 * @author bin
 * 
 */
public class OSSUtils {
	public static String ACCESS_KEY_ID = null;
	public static String ACCESS_KEY_SECRET = null;
	public static String ENDPOINT = null;
	public static String BUCKETNAME = null;
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

	/**
	 * 上传头像 type(1 图片、2 文本、3 视频、4 音频、5 表格)
	 * 
	 * @param imgFile
	 * @param filePathName
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static String ossMangerUpload(File imgFile, int type) {
		JSONObject error = new JSONObject();
		if (null == imgFile) {
			error.put("code", -1);
			error.put("msg", "上传文件为空");
			return JsonUtils.convertToString(error);
		}
		if (type < Constants.IMG) {
			error.put("code", -1);
			error.put("msg", "上传文件格式有误");
			return JsonUtils.convertToString(error);
		}
		String fileExt = imgFile.getName().substring(
				imgFile.getName().lastIndexOf(".") + 1);
		String fileName = "";
		if (!"PDF".contains(fileExt.toUpperCase())) {//pdf的文件不改码
			 fileName = UUID.randomUUID().toString().replaceAll("-", "")
					+ "." + fileExt;
		}else {
			fileName =  imgFile.getName();
		}
		String filePathName = null;
		switch (type) {
		case Constants.IMG:
			if (!"GIF,JPG,JPEG,PNG,BMP".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传图片(gif,jpg,jpeg,png,bmp)文件");
				return JsonUtils.convertToString(error);
			}
			if (imgFile.length() > Constants.PICTURE_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制2M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_IMG + fileName;
			break;
		case Constants.TXT:
			if (!"TXT".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传文本(txt)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.TXT_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制5M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_TXT + fileName;
			break;
		case Constants.VIDEO:
			if (!"MP4,3GP,AVI,WMV,RM,RMVB".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传视频(mp4,3gp,avi,wmv,rm,rmvb)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.VIDEO_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制100M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_VIDEO + fileName;
			break;
		case Constants.AUDIO:
			if (!"MP3,WAV,WMA".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传音频(mp3,wav,wma)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.AUDIO_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制100M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_AUDIO + fileName;
			break;
		case Constants.XLS:
			if (!"XLS".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传表格(xls)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.XLS_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制5M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_XLS + fileName;
			break;
		case Constants.FILE:
			if (imgFile.length() > Constants.FILE_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制510M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_FILE + fileName;
			break;
		case Constants.wz:
			fileName = imgFile.getName();
			filePathName = Constants.FILE_WZ + fileName;
			break;
		case Constants.REPORT:
			fileName = imgFile.getName();
			filePathName = Constants.FILE_REPORT + fileName;
			break;
		default:
			filePathName = Constants.FILE_TEMP + fileName;
			break;
		}
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,
				ACCESS_KEY_SECRET);
		if (!ossClient.doesBucketExist(BUCKETNAME)) {
			// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
			ossClient.createBucket(BUCKETNAME);
		}
		PutObjectResult result = ossClient.putObject(BUCKETNAME, filePathName,
				imgFile);
//		System.out.println(result.getETag());
		return filePathName;
	}

	
	/**
	 * 上传头像 type(1 图片、2 文本、3 视频、4 音频、5 表格)
	 * 上传地区专用
	 * 上传地区专用
	 * 上传地区专用
	 * 上传地区专用
	 * @param imgFile
	 * @param filePathName
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static String ossRegionsUpload(File imgFile, int type) {
		JSONObject error = new JSONObject();
		if (null == imgFile) {
			error.put("code", -1);
			error.put("msg", "上传文件为空");
			return JsonUtils.convertToString(error);
		}
		if (type < Constants.IMG) {
			error.put("code", -1);
			error.put("msg", "上传文件格式有误");
			return JsonUtils.convertToString(error);
		}
		String fileExt = imgFile.getName().substring(
				imgFile.getName().lastIndexOf(".") + 1);
		/*	String fileName = UUID.randomUUID().toString().replaceAll("-", "")
				+ "." + fileExt;*/
		String fileName = "regions.json";
		String filePathName = null;
		switch (type) {
		case Constants.IMG:
			if (!"GIF,JPG,JPEG,PNG,BMP".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传图片(gif,jpg,jpeg,png,bmp)文件");
				return JsonUtils.convertToString(error);
			}
			if (imgFile.length() > Constants.PICTURE_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制2M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_IMG + fileName;
			break;
		case Constants.TXT:
			if (!"TXT".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传文本(txt)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.TXT_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制5M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_TXT + fileName;
			break;
		case Constants.VIDEO:
			if (!"MP4,3GP,AVI,WMV,RM,RMVB".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传视频(mp4,3gp,avi,wmv,rm,rmvb)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.VIDEO_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制100M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_VIDEO + fileName;
			break;
		case Constants.AUDIO:
			if (!"MP3,WAV,WMA".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传音频(mp3,wav,wma)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.AUDIO_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制100M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_AUDIO + fileName;
			break;
		case Constants.XLS:
			if (!"XLS".contains(fileExt.toUpperCase())) {
				error.put("code", -1);
				error.put("msg", "文件格式有误，请上传表格(xls)文件");
				return JsonUtils.convertToString(error);
			}

			if (imgFile.length() > Constants.XLS_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制5M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_XLS + fileName;
			break;
		case Constants.FILE:
			if (imgFile.length() > Constants.FILE_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制510M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_FILE + fileName;
			break;
		case Constants.JSON:
			if (imgFile.length() > Constants.FILE_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制510M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.ROOT_FILE + fileName;
			break;
		default:
			filePathName = Constants.FILE_TEMP + fileName;
			break;
		}
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,
				ACCESS_KEY_SECRET);
		if (!ossClient.doesBucketExist(BUCKETNAME)) {
			// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
			ossClient.createBucket(BUCKETNAME);
		}
		PutObjectResult result = ossClient.putObject(BUCKETNAME, filePathName,
				imgFile);
//		System.out.println(result.getETag());
		return filePathName;
	}

public static String downloadOSS(String urllink){
	OSSClient	ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,
			ACCESS_KEY_SECRET);
	String date =OSSUtils.generateRandomFilename();
	String filePath=PropertyConfig.getProperty("customer_zip_path")+date+"zip";
	try {
		URL httpurl = new URL(UriUtils.encodePath(urllink, "UTF-8"));
		File file = new File(filePath);
			FileUtils.copyURLToFile(httpurl, file);
	} catch (MalformedURLException e){
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}
	finally {
	       	//关闭oss连接
		      if (ossClient != null){
			   ossClient.shutdown();
		           }
	}
	return filePath;


}
	public static String generateRandomFilename(){
		String RandomFilename = "";
		Random rand = new Random();//生成随机数
		int random = rand.nextInt();

		Calendar calCurrent = Calendar.getInstance();
		int intDay = calCurrent.get(Calendar.DATE);
		int intMonth = calCurrent.get(Calendar.MONTH) + 1;
		int intYear = calCurrent.get(Calendar.YEAR);
		String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" + String.valueOf(intDay) + "_";
		RandomFilename = now + String.valueOf(random > 0 ? random : ( -1) * random) + ".";


		return RandomFilename;
	}
	public static boolean deleteDir(String path){
		File file = new File(path);
		if(!file.exists()){
			//判断是否待删除目录是否存在
			System.err.println("The dir are not exists!");
			return false;		}
		String[] content = file.list();//取得当前目录下所有文件和文件夹
		 for(String name : content){			File temp = new File(path, name);
			 if(temp.isDirectory()){//判断是否是目录
			 	 deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
				  temp.delete();//删除空目录
				  }else{
			 	if(!temp.delete()){//直接删除文件
			 		 System.err.println("Failed to delete " + name);				}			}		}
		 return true;	}

	public static  void delete(String path){
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,
				ACCESS_KEY_SECRET);
		// 删除文件。
		ossClient.deleteObject(BUCKETNAME, path);

		// 关闭OSSClient。
		ossClient.shutdown();
	}

}
