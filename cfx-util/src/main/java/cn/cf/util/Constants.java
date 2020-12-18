package cn.cf.util;

/**
 * 鐢悂鍣洪崐锟� * @author sea
 * 
 */
public class Constants {

	/**
	 * 项目名
	 */

	/**
	 * 用户session有效时间（ 默认7天7*24*60*60,单位为FNE）
	 */
	public static final int USER_SESSION_TIME_OUT = 3 * 24 * 60;

	public static final int ONE = 1;

	public static final int TWO = 2;

	public static final int THREE = 3;

	public static final int FOUR = 4;

	public static final int FIVE = 5;

	public static final int SIX = 6;

	public static final int SEVEN = 7;

	public static final int EIGHT = 8;

	public static final int TEN = 10;
	
	public static final int MINUS_ONE = -1;
	
	public static final int MINUS_TWO = -2;
	
	public static final int ZERO = 0;

	// 跳转地址
	/********************************* 上传图片常用变量 begin ***************************************************/

	public static final int IMG = 1; // 图片
	public static final int TXT = 2; // 文本
	public static final int VIDEO = 3; // 视频
	public static final int AUDIO = 4; // 音频
	public static final int XLS = 5; // 表格
	public static final int FILE=6;
	public static final int JSON=7;
	public static final int wz = 8; // 上传微众
	public static final int REPORT = 9; //征信文件目录
	public static final int TEMP = 999; // 上传图片测试

	public static final int PICTURE_SIZE = 10 * 1024 * 1024; // 图片限制大小(2m左右)
	public static final int TXT_SIZE = 20 * 1024 * 1024; // 文本限制大小(5M左右)
	public static final int VIDEO_SIZE = 100 * 1024 * 1024; // 视频限制大小(10M左右)
	public static final int AUDIO_SIZE = 100 * 1024 * 1024; // 音频限制大小(10M左右)
	public static final int XLS_SIZE = 50 * 1024 * 1024; // 表格限制大小(5M左右)
	public static final int FILE_SIZE = 100 * 1024 * 1024; // 表格限制大小(10M左右)


	// 这个目录主要存用户头像
	public static String FILE_IMG = "img/";
	// 这个目录主要存用户视频
	public static String FILE_VIDEO = "video/";
	// 这个目录主要存用户TXT
	public static String FILE_TXT = "txt/";
	// 这个目录主要存用户AUDIO
	public static String FILE_AUDIO = "audio/";
	// 这个目录主要存用户xls
	public static String FILE_XLS = "xls/";
	// 这个目录主要存用户file
		public static String FILE_FILE = "file/";
	// 上传文件test目录
	public static String FILE_TEMP = "temp/";
    // 微众税银路径
	public static String FILE_WZ = "wz/";
	 // 微众税银路径
	public static String FILE_REPORT = "report/";
	// 上传文件到根目录
	public static String ROOT_FILE = "";
	/********************************* 上传图片常用变量 end ***************************************************/
    public static String picURL="http://huaxianhui.oss-cn-shanghai.aliyuncs.com/";
    
}
