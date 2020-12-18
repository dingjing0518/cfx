/**
 *
 */
package cn.cf.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author bin
 *
 */
public class ArithUtil {
    private static final int DEF_DIV_SCALE = 10;

    // 这个类不能实例化
    private ArithUtil() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */

    public static String addString(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    public static String addStrD(Double v1, String v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    public static BigDecimal addBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return addBigDecimal(b1,b2);
    }
    public static BigDecimal addBigDecimal(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }
    /**
     * 提供精确的减法运算。
     *
     * @param v1
     *            被减数
     * @param v2
     *            减数
     * @return 两个参数的差
     */

    public static double sub(double v1, double v2) {
        return subBigDecimal(v1, v2).doubleValue();
    }

    public static BigDecimal subBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return subBigDecimal(b1, b2);
    }

    public static BigDecimal subBigDecimal(BigDecimal b1, double v2) {
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return subBigDecimal(b1, b2);
    }

    public static BigDecimal subBigDecimal(double v1, BigDecimal b2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        return subBigDecimal(b1, b2);
    }

    public static BigDecimal subBigDecimal(BigDecimal b1, BigDecimal b2) {
        // TODO Auto-generated method stub
        return b1.subtract(b2);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */

    public static double mul(double v1, double v2) {
        return mulBigDecimal(v1, v2).doubleValue();
    }

    public static BigDecimal mulBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return mulBigDecimal(b1,b2);
    }
    public static BigDecimal mulBigDecimal(BigDecimal b1, double v2) {
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return mulBigDecimal(b1,b2);
    }
    public static BigDecimal mulBigDecimal(double v1, BigDecimal b2 ) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        return mulBigDecimal(b1,b2);
    }
    public static BigDecimal mulBigDecimal(BigDecimal b1, BigDecimal b2 ) {
        return b1.multiply(b2);
    }
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal divBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return divBigDecimal(b1, b2, DEF_DIV_SCALE);
    }

    public static BigDecimal divBigDecimal(BigDecimal b1, double v2) {
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return divBigDecimal(b1, b2, DEF_DIV_SCALE);
    }

    public static BigDecimal divBigDecimal(double v1, BigDecimal b2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        return divBigDecimal(b1, b2, DEF_DIV_SCALE);
    }

    public static BigDecimal divBigDecimal(BigDecimal b1, BigDecimal b2,
                                           int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String roundStr(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        Double d = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (Math.round(d) - d == 0) {
            return String.valueOf(d.intValue());
        }
        return String.valueOf(d);
    }

    /**
     * 提供精确的小数位四舍五入处理。(处理高级科学计数 千亿级以上)
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */

    public static String roundBigDecimal(BigDecimal b, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal one = new BigDecimal("1");
        BigDecimal big = b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
        return BigDecimal.valueOf(Double.parseDouble(big.toString()))
                .stripTrailingZeros().toPlainString();
    }

    /**
     * 四舍五入把double转化int整型，0.5进一，小于0.5不进一
     *
     * @param number
     * @return
     */
    public static int getInt(String number) {
        BigDecimal bd = new BigDecimal(number).setScale(0,
                BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    public static int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0,
                BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    public static int getceilInt(double number) {
        return (int) Math.ceil(number);
    }

    public static String twoZeroByDouble(Double val) {
        String nval = null;
        if (null != val) {
            String[] vals = val.toString().split("\\.");
            nval = vals[0];
            if (vals.length == 1) {
                nval = nval + ".00";
            } else {
                if (vals[1].length() == 1) {
                    nval = nval + "." + vals[1] + "0";
                } else {
                    nval = val.toString();
                }
            }
        }
        return nval;
    }
    
    
    
	
	/**
	 * 璁＄畻澶氫釜鍊肩殑鍜�
	 * @param list
	 * @return
	 */
	public static double addList(List<Double> list ) {
		BigDecimal count=new BigDecimal(0);
		for(Double s:list) {	  	 
	    count = count.add(new BigDecimal(Double.toString(s)));  	
	    }
		return count.doubleValue();
	}
    public static void main(String[] args) {
		System.out.println(ArithUtil.getInt("2"));
	}

}
