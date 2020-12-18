package cn.cf.util; 

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

 
 

public class InterestUtil {



	/***
	 * 还款本金(公式:借款本金*期限*银行利率/360)
	 * @param date1放款时间
	 * @param date2还款时间
	 * @param amount本次还款金额
	 * @param rate 利率
	 * @return
	 * @throws ParseException
	 *
	 */
	public static double  getAmount(String date1,String date2,Double amount ,Double rate) {
		if(null==amount||0==amount)
		if(null==rate){
			rate=0.0;
		}
		int days=DateUtil.getCalculatedDays(date1, date2);//期限天数
		if(days>0){
			double a=ArithUtil.div(amount,(ArithUtil.div(rate,360)*days)+1);
			return a;
		}else{
			return 0;
		}


	}


	/***
	 * 利息(公式:借款本金*期限*银行利率/360)
	 * @param date1放款时间
	 * @param date2还款时间
	 * @param amount本金
	 * @param rate 利率
	 * @return
	 * @throws ParseException 
	 * 
	 */
	public static double  getInterest(String date1,String date2,Double amount ,Double rate) {
		if(null==amount||0==amount){
			return 0;
		}
		if(null==rate){
			rate=0.0;
		}
		    int days=DateUtil.getCalculatedDays(date1, date2);//期限天数
		    if(days>0){
		    	Double interest = ArithUtil.divBigDecimal(ArithUtil.divBigDecimal(ArithUtil.mulBigDecimal(ArithUtil.mulBigDecimal(amount, days),rate),100), 360).setScale(2, RoundingMode.CEILING).doubleValue();
				return interest;
		    }else{
		    	return 0;
		    }
		   
		
	}


	/***
	 * 总服务费 (公式:还款本金*（平台利率-银行利率）/360*（还款时间-放款时间）+（7-（还款时间-放款时间））*还款本金*平台7日利率/360)
	 *  @param date1放款时间
	 * @param date2还款时间
	 * @param amount 本金
	 * @param totalRate 平台利率
	 * @param bankRate  银行利率
	 * @param daysRate  七天利率
	 * @param type 1化纤白条    2  化纤贷 
	 * @return
	 * @throws ParseException
	 */
	public static double getCoverCharges(String date1,String date2,Double amount ,Double totalRate,Double bankRate, Double daysRate,Integer type)  {
		if(null==amount||0==amount){
			return 0;
		}
		if(null==totalRate){
			totalRate=0.0;
		}
		if(null==bankRate){
			bankRate=0.0;
		}
		if(null==daysRate){
			daysRate=0.0;
		}
		if(null==type){
			type=0;
		}
		int days=DateUtil.getCalculatedDays(date1, date2);//放款天数
		if(days>0){
			BigDecimal rate=ArithUtil.divBigDecimal(ArithUtil.sub(totalRate, bankRate), 100);
			BigDecimal CoverCharges=ArithUtil.mulBigDecimal(ArithUtil.divBigDecimal(ArithUtil.mulBigDecimal(amount, rate), 360), days);//服务费
			BigDecimal otherPrice=new BigDecimal(0);//不满七天费用
			//type:2 化纤贷 type:4 化纤贷委托放款
			if((type==2 || type ==4)&&days>0){
				double otherdays = ArithUtil.sub(7, days);
				if(otherdays>0){
					otherPrice=ArithUtil.divBigDecimal(ArithUtil.mulBigDecimal(ArithUtil.mulBigDecimal(otherdays, amount), ArithUtil.divBigDecimal(daysRate,100)), 360);
				 
				}
			}
			BigDecimal value=ArithUtil.addBigDecimal(CoverCharges, otherPrice);
			return value.setScale(2, RoundingMode.CEILING).doubleValue();
		}else{
			return 0;
		}
		
		
	}
	/**服务费（不包含七天费率 公式:还款本金*（总利率-银行利率）/360*期限）
	 * @param date1放款时间
	 * @param date2还款时间
	 * @param amount 本金
	 * @param totalRate 平台利率
	 * @param bankRate  银行利率
	 * @return
	 */
	public static double getServiceCharge(String date1,String date2,Double amount ,Double totalRate,Double bankRate)  {
		if(null==amount||0==amount){
			return 0;
		}
		if(null==totalRate){
			totalRate=0.0;
		}
		if(null==bankRate){
			bankRate=0.0;
		}
		int days=DateUtil.getCalculatedDays(date1, date2);//放款天数
		if(days>0){
			BigDecimal rate=ArithUtil.divBigDecimal(ArithUtil.sub(totalRate, bankRate), 100);
			BigDecimal CoverCharges=ArithUtil.mulBigDecimal(ArithUtil.divBigDecimal(ArithUtil.mulBigDecimal(amount, rate), 360), days);//服务费
			return CoverCharges.setScale(2, RoundingMode.CEILING).doubleValue();
		}else{
			return 0;
		}
		
	}
	
	/***
	 * 七日服务费(公式:（7-（还款时间-放款时间））*还款本金*平台7日利率/360)
	 * @param date1放款时间
	 * @param date2还款时间
	 * @param amount 本金
	 * @param daysRate  七天利率
	 * @return
	 */
	public static double getSevenDayServiceCharges(String date1,String date2,Double amount  , Double daysRate )  {
		if(null==amount||0==amount){
			return 0;
		}
		if(null==daysRate){
			daysRate=0.0;
		}
		int days=DateUtil.getCalculatedDays(date1, date2);//放款天数
		if(days>0){
			BigDecimal otherPrice=new BigDecimal(0);//不满七天费用
				double otherdays = ArithUtil.sub(7, days);
				if(otherdays>0){
					otherPrice=ArithUtil.divBigDecimal(ArithUtil.mulBigDecimal(ArithUtil.mulBigDecimal(otherdays, amount), ArithUtil.divBigDecimal(daysRate,100)), 360);
				 
				}
			 
				return otherPrice.setScale(2, RoundingMode.CEILING).doubleValue();
		}else{
			return 0;
		}
		
		
	}
 
}