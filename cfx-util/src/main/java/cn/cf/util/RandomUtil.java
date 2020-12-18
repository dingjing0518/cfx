package cn.cf.util;

public class RandomUtil {

	
	/**
	 * 生成六位随机密码
	 * @return
	 */
	public static String getRandomPassword(){
		double random = Math.random();
		String randomPassword = Double.toString(random).substring(2, 8);
		return randomPassword;
	}
	
	/**
	 * 随机生成XX位数字
	 * @param counts
	 * @return
	 */
	public static String getRandomCounts(int counts){
		double random = Math.random();
		String randomPassword = Double.toString(random).substring(2, counts);
		return randomPassword;
	}
	public static void main(String[] args) {

	}

}
