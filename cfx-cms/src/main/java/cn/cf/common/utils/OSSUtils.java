/**
 * 
 */
package cn.cf.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import cn.cf.property.PropertyConfig;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import cn.cf.common.Constants;
import cn.cf.json.JsonUtils;

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
	 * @param type
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
			filePathName = Constants.picURL + Constants.FILE_XLS + imgFile.getName();//返回完整路径
			break;
		case Constants.FILE:
			if (imgFile.length() > Constants.FILE_SIZE) {
				error.put("code", -1);
				error.put("msg", "您上传的文件已超出最大限制510M，请重新上传");
				return JsonUtils.convertToString(error);
			}
			filePathName = Constants.FILE_FILE + fileName;
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
	 * @param type
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
		String fileName = "regions.json";
		String filePathName = null;
		switch (type) {
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
		return filePathName;
	}


	/**
	 * 上传头像 type(1 图片、2 文本、3 视频、4 音频、5 表格)
	 *
	 * @param imgFile
	 * @param type
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static String ossUploadXls(File imgFile, int type) {
		JSONObject error = new JSONObject();
		if (null == imgFile) {
			error.put("code", -1);
			error.put("msg", "上传文件为空");
			return JsonUtils.convertToString(error);
		}
		String fileExt = imgFile.getName().substring(
				imgFile.getName().lastIndexOf(".") + 1);

		String filePathName = null;
		switch (type) {
			case Constants.FILE:
				if (imgFile.length() > Constants.FILE_SIZE) {
					error.put("code", -1);
					error.put("msg", "您上传的文件已超出最大限制510M，请重新上传");
					return JsonUtils.convertToString(error);
				}
				filePathName = Constants.FILE_FILE + imgFile.getName();
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
				filePathName = Constants.FILE_XLS + imgFile.getName();
				break;
			case Constants.CSV:
				if (!"CSV".contains(fileExt.toUpperCase())) {
					error.put("code", -1);
					error.put("msg", "文件格式有误，请上传表格(csv)文件");
					return JsonUtils.convertToString(error);
				}
				if (imgFile.length() > Constants.CSV_SIZE) {
					error.put("code", -1);
					error.put("msg", "您上传的文件已超出最大限制5M，请重新上传");
					return JsonUtils.convertToString(error);
				}
				filePathName = Constants.FILE_CSV + imgFile.getName();
				break;
			case Constants.TXT:
				if (!"TXT".contains(fileExt.toUpperCase())) {
					error.put("code", -1);
					error.put("msg", "文件格式有误，请上传表格(TXT)文件");
					return JsonUtils.convertToString(error);
				}
				if (imgFile.length() > Constants.TXT_SIZE) {
					error.put("code", -1);
					error.put("msg", "您上传的文件已超出最大限制5M，请重新上传");
					return JsonUtils.convertToString(error);
				}
				filePathName = Constants.FILE_TXT + imgFile.getName();
				break;
			default:
				break;
		}

		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
		if (!ossClient.doesBucketExist(BUCKETNAME)) {
			ossClient.createBucket(BUCKETNAME);
		}
		PutObjectResult result = ossClient.putObject(BUCKETNAME, filePathName,imgFile);
		return Constants.picURL + filePathName;//返回完整路径
	}

	public static void main(String[] args) {
		//File file = new File("D://goodicon.png");
		//String uploadpath = OSSUtils.ossMangerUpload(file, Constants.TEMP);
		//System.out.println(uploadpath);
	}


}
