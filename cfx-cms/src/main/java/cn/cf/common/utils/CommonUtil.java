package cn.cf.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import cn.cf.dto.ManageAuthorityDto;
import cn.cf.entity.AccountAuthColEntity;
import cn.cf.model.ManageAccount;
import cn.cf.util.SpringContextsUtil;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import cn.cf.common.Constants;
import cn.cf.json.JsonUtils;
@Component
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    private static MongoTemplate mongoTemplate = null;
    private static final Pattern p_html = Pattern.compile("<[a-zA-z]{1,9}((?!>).)*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern t_html = Pattern.compile("</[a-zA-z]{1,9}>", Pattern.CASE_INSENSITIVE);

    public static boolean writeFile(String strFilePath, String strContent) {
        if (strFilePath.equals("") || strContent.equals("")) {
            return false;
        }
        try {
            FileWriter curFileWriter = new FileWriter(strFilePath, true);
            curFileWriter.write(strContent);
            curFileWriter.close();
        } catch (Exception e) {
            logger.error("write file error:", e);
            return false;
        }
        return true;
    }

    public static boolean writeFile(String strFilePath, String strContent, boolean renewMark) {
        if (strFilePath.equals("") || strContent.equals("")) {
            return false;
        }
        try {
            FileWriter curFileWriter = new FileWriter(strFilePath, renewMark);
            curFileWriter.write(strContent);
            curFileWriter.close();
        } catch (Exception e) {
            logger.error("write file error:", e);
            return false;
        }
        return true;
    }

    public static String getPath() {

        String path = getClassFilePath(CommonUtil.class);
        path = path.substring(0, path.lastIndexOf("classes") + 8);
        return path;
    }

    public static String getLocalPath() {
        String path = CommonUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        // path = path.substring(0, path.lastIndexOf("com"));
        return path;
    }

    public static String getClassFilePath(Class<?> clazz) {
        try {
            return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("url decode error:", e);
            return "";
        }
    }

    private static File getClassFile(Class<?> clazz) {
        URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
        if (path == null) {
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/" + name + ".class");
        }
        return new File(path.getFile());
    }

    /**
     * 根据传入url得到域名
     * 
     * @param url
     * @return
     */
    public static String getDomainName(String url) {
        if ((url == null) || ((url.trim()).length() == 0)) {
            return "";
        }
        url = url.toLowerCase();
        StringTokenizer stDomain = new StringTokenizer(url, "/");
        int count = stDomain.countTokens();
        if (count == 1) {
            if (url.startsWith("www.")) {
                return url.substring(4);
            }
        }
        String[] linkArr = new String[count];
        for (int i = 0; i < count; i++) {
            linkArr[i] = stDomain.nextToken();
        }

        String newLink = "";
        String domainName = linkArr[1];

        String nation = "ad,ae,af,ag,ai,al,am,ao,ar,at,au,az,bb,bd,be,bf,bg,bh,bi,bj,bl,bm,bn,bo,br,bs,bw,by,bz,ca,cf,cg,ch,ck,cl,cm,cn,co,cr,cs,cu,cy,cz,de,dj,dk,do,dz,ec,ee,eg,es,et,fi,fj,fr,ga,gb,gd,ge,gf,gh,gi,gm,gn,gr,gt,gu,gy,hk,hn,ht,hu,id,ie,il,in,iq,ir,is,it,jm,jo,jp,ke,kg,kh,kp,kr,kt,kw,kz,la,lb,lc,li,lk,lr,ls,lt,lu,lv,ly,ma,mc,md,mg,ml,mm,mn,mo,ms,mt,mu,mv,mw,mx,my,mz,na,ne,ng,ni,nl,no,np,nr,nz,om,pa,pe,pf,pg,ph,pk,pl,pr,pt,py,qa,ro,ru,sa,sb,sc,sd,se,sg,si,sk,sl,sm,sn,so,sr,st,sv,sy,sz,td,tg,th,tj,tm,tn,to,tr,tt,tw,tz,ua,ug,us,uy,uz,vc,ve,vn,ye,yu,za,zm,zr,zw";
        String kindvprov = "ac,ah,biz,bj,cc,com,cq,edu,fj,gd,gov,gs,gx,gz,ha,hb,he,hi,hk,hl,hn,info,io,jl,js,jx,ln,mo,mobi,net,nm,nx,org,qh,sc,sd,sh,sn,sx,tj,tm,travel,tv,tw,ws,xj,xz,yn,zj";
        int NodeCount = 2;

        StringTokenizer st = new StringTokenizer(domainName, ".");
        count = st.countTokens();
        String[] newLinkArr = new String[count];
        for (int i = 0; i < count; i++) {
            newLinkArr[i] = st.nextToken();
        }
        // 如果倒数第一个，即主域名标识是国家标�?
        if (nation.indexOf(newLinkArr[newLinkArr.length - 1]) >= 0) {
            // 先确定有三个节点
            NodeCount = 3;
            // 如果国家前一标识不是类型或省级标识，则只有二个节�?
            if (kindvprov.indexOf(newLinkArr[newLinkArr.length - 2]) < 0) {
                NodeCount = 2;
            }
        } else // 否则倒数第一个，即主域名标识是类型标�?
        {
            // 也只有两个节�?
            NodeCount = 2;
        }
        // 假如类型标识即是主域名，则实际节点数�?��小于当前得到的结�?
        int istart = newLinkArr.length - NodeCount;
        if (istart < 0) {
            istart = 0;
        }
        // 根据节点数定出的�?��节点，取出主域名，这样就不会取到二级域名
        for (int i = istart; i < newLinkArr.length; i++) {
            // 如果�?��节点是WWW，象www.net.cn,www.com.cn,则www根据节点数确定取与不�?
            if (!newLinkArr[i].equalsIgnoreCase("www")) {
                if (newLink.length() > 0) {
                    newLink += ".";
                }
                newLink += newLinkArr[i];
            } else {
                // 如果是www，并且节点数�?，这类域名必须要有WWW才能在数据库的URL中找到唯�?
                if (NodeCount == 3) {
                    if (i == istart) {
                        if (newLink.length() > 0) {
                            newLink += ".";
                        }
                        newLink += newLinkArr[i];
                    }
                }
            }
        }

        return newLink;
    }

    public static String getIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = null;
        // �?��服务器反向代理时�?��变更代理获取规则，否则将得到反向代理IP.
        ip = request.getRemoteAddr();
        return ip;
    }

    public static String isProxy() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String forwardedIp = request.getHeader("x-forwarded-for");
        String remoteIp = request.getRemoteAddr();
        logger.debug("----------forwardedIp:" + forwardedIp);
        logger.debug("----------remoteIp:" + remoteIp);
        if (forwardedIp != null && !"".equalsIgnoreCase(forwardedIp)) {
            if (forwardedIp.split(",").length > 1) {
                return "1";
            }
            if (remoteIp != null && remoteIp.length() != 0) {
                return "1";
            }
        }
        return "0";
    }

    /**
     * 判断字符串是否为空 返回true表示为空，false不为空
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {

        if (str != null && !"".equals(str)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 判断传入的字符串是否为空或空字符串，返回true否 false为是
     * 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        boolean isempty = false;
        if (str != null && !"".equals(str)) {
            isempty = true;
        }
        return isempty;
    }

    /**
     * 取本周一
     * 
     * @return
     */
    public static String selfWeekOne() {
        // 今天
        DateTime today = DateTime.now();
        // 本周的今天
        DateTime sameDayLastWeek = today.minusWeeks(0);// 0本周,1上周,...
        // 本周的周一
        DateTime mondayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.MONDAY);
        return mondayLastWeek.toString("yyyy-MM-dd");
    }

    /**
     * 取上周一
     * 
     * @return
     */
    public static String lastWeekOne() {
        // 今天
        DateTime today = DateTime.now();
        // 上周的今天
        DateTime sameDayLastWeek = today.minusWeeks(1);// 0本周,1上周,...
        // 上周的周一
        DateTime mondayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.MONDAY);
        return mondayLastWeek.toString("yyyy-MM-dd");
    }

    /**
     * 取上周日
     * 
     * @return
     */
    public static String lastWeekDay() {
        // 今天
        DateTime today = DateTime.now();
        // 上周的今天
        DateTime sameDayLastWeek = today.minusWeeks(1);// 0本周,1上周,...
        // 上周的周日
        DateTime sundayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.SUNDAY);
        return sundayLastWeek.toString("yyyy-MM-dd");
    }

    /**
     * 昨日
     * 
     * @return
     */
    public static String yesterDay() {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(DateUtils.addDays(date, -1));
    }

    /**
     * 获取某天的这个星期的最后一天，例如：输入2018-11-14 星期三，获取的是当前时间所在星期的星期日2018-11-18
     * 
     * @param day
     * @return
     */
    public static String someWeekLastDay(DateTime day) {

        DateTime sameDayLastWeek = day.minusWeeks(0);// 0本周,1上周,...
        // 上周的周日
        DateTime sundayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.SUNDAY);
        return sundayLastWeek.toString("yyyy-MM-dd");
    }

    /**
     * 获取月份指定某一天
     * 
     * @param addMonth
     *            相对当前时间月份，正数为以后月份，负数为以前月份
     * @param day
     *            指定一个月的某一天的日期。例如：21号
     * @return
     */
    public static String specifyMonth(int addMonth, int day) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(new Date());// 设置当前日期
        calendar.add(Calendar.MONTH, addMonth);// 月份减一
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 取上昨天的 1:年 2：月 已21号到20号为一个月的周期 3:日
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    public static Integer yesterday(int flag) {
        int time = 0;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(cal.DATE, -1);
        if (flag == 1) {
            if (cal.get(cal.MONTH) == 11 && cal.get(cal.DATE) > 20) {
                time = cal.get(cal.YEAR) + 1;
            } else {
                time = cal.get(cal.YEAR);
            }
        }
        if (flag == 2) {
            if (cal.get(cal.DATE) <= 20) {
                time = cal.get(cal.MONTH) + 1;// 老外把一月份整成了0，翻译成中国月份要加1
            } else {
                if (cal.get(cal.MONTH) == 11) {
                    time = 1;
                } else {
                    time = cal.get(cal.MONTH) + 2;
                }

            }
        }
        if (flag == 3) {
            time = cal.get(cal.DATE);
        }
        return time;

    }

    public static void main(String[] args) {

    }

    /**
     * 获取当前日期星期几
     * 
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        return weekDays[w];
    }

    /**
     * 获取月份指定某一天
     * 
     * @param addMonth
     *            相对当前时间月份，正数为以后月份，负数为以前月份
     * @param day
     *            指定一个月的某一天的日期。例如：21号
     * @return
     */
    public static String specifyMonth(int addMonth, int day, Date date) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, addMonth);// 月份减一
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取月份
     * 
     * @param addMonth
     *            相对当前时间月份，正数为以后月份，负数为以前月份,0表示当前月
     * @return
     */
    public static int specifyMonths(int addMonth, Date date) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, addMonth);// 月份减一
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取两个时间相差的年，月
     * 
     * @param startCal
     * @param endCal
     * @return
     */
    public static Map<String, Object> getYearAndMonth(String startCal, String endCal) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startCal);
            Date endDate = sdf.parse(endCal);
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            if (endCalendar.compareTo(startCalendar) < 0) {
                System.out.println("后一时次的日期小于前一时次的日期，请重新输入。");
                // return;
            }
            int day = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
            int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
            int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

            if (day < 0) {
                month--;
            }
            if (month < 0) {
                month += 12;
                year--;
            }
            System.out.println("两者相差年月为：" + year + "年" + month + "个月");

            Map<String, Object> map = new HashMap<>();
            map.put("year", year);
            map.put("month", month);
            return map;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某时间到某时间段的所有日期列表
     * 
     * @param startCal
     * @param endCal
     * @return
     */
    public static List<String> getFromDateToDate(String startCal, String endCal) {

        Map<String, Object> map = CommonUtil.getYearAndMonth(startCal, endCal);
        int yearInt = (int) map.get("year");
        int monthInt = (int) map.get("month");
        int numbers = ((yearInt) * 12) + monthInt;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date Cal = null;
        try {
            Cal = sdf.parse(startCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(Cal);// 设置日期

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");

        List<String> list = new ArrayList<>();
        list.add(fmt.format(calendar.getTime()));
        for (int m = 0; m < numbers; m++) {
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 20);
            list.add(fmt.format(calendar.getTime()));
        }
        return list;
    }

    /**
     * 取上月21的日期
     * 
     * @return
     */
    public static String lastMonthDay() {
        Calendar c = Calendar.getInstance();// 今天的时间
        c.add(Calendar.MONTH, -1);// 今天的时间月份-1支持1月的上月
        c.set(Calendar.DAY_OF_MONTH, 21);// 设置上月21号
        Date date = c.getTime();
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sFormat.format(date);
    }

    /**
     * 今天
     * 
     * @return
     */
    public static String today() {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }

    /**
     * 跨平台加密
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public static Map<String, String> getEncodeParam(Map<String, String> map) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("encodeData", EncodeUtil.des3Encrypt3Base64(JsonUtils.convertToString(map)));
        return paramMap;
    }

    /**
     * 解密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String deciphData(String data) throws Exception {
        if (data != null && !data.equals(Constants.GATEWAY_MSG)) {
            String[] result = EncodeUtil.des3Decrypt3Base64(data);
            if (result != null) {
                return result[1];
            } else {
                return null;
            }
        } else {
            return data;
        }
    }

    public static Double formatDoubleFour(Double dataD) {
        DecimalFormat format = new DecimalFormat("#.0000");
        if (dataD != null) {
            return Double.valueOf(format.format(dataD));
        } else {
            return 0d;
        }
    }

    public static Double formatDoubleFive(Double dataD) {
        DecimalFormat format = new DecimalFormat("#.00000");
        if (dataD != null) {
            return Double.valueOf(format.format(dataD));
        } else {
            return 0d;
        }
    }

    public static Double formatDoubleTwo(Double dataD) {
        DecimalFormat format = new DecimalFormat("#.00");
        if (dataD != null) {
            return Double.valueOf(format.format(dataD));
        } else {
            return 0d;
        }
    }

    /**
     * 获取两个日期字符串之间的日期集合(含前不含后)
     * 
     * @param startTime
     *            :String
     * @param endTime
     *            :String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            // 用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() < endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                // 把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 比较两个日期的大小，返回比较小的
     * 
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static String compare_date(String DATE1, String DATE2) {
        String result = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                // System.out.println("dt1 在dt2前");
                result = DATE1;
            } else if (dt1.getTime() < dt2.getTime()) {
                // System.out.println("dt1在dt2后");
                result = DATE2;
            } else {
                result = DATE1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    static {
        mongoTemplate = (MongoTemplate) SpringContextsUtil.getApplicationContext().getBean("mongoTemplate",MongoTemplate.class);
    }

    /**
     * 判断存在权限列显示权限
     * 
     * @param accountPk
     *            ，登陆用户pk
     * @param name
     *            ，权限名
     * @return
     */
    public static boolean isExistAuthName(String accountPk, String name) {
        Criteria c = new Criteria();
        c.andOperator(Criteria.where("key").is(accountPk + "_" + name));
        Query query = new Query(c);
        long count = mongoTemplate.count(query, AccountAuthColEntity.class);

        boolean flag = false;
        if(count > 0){
            flag = true;
        }
        return flag;
    }

    /**
     * 手机号
     * 
     * @param contactsTel
     * @return
     */
    public static String hideContactTel(String contactsTel) {
        return contactsTel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 从左边起第3~7个字隐藏
     *
     * @param col
     * @return
     */
    public static String hideCompanyName(String col) {
        String result = "";
        if (col != null && !col.equals("")) {
            char[] name = col.toCharArray();
            if (name.length > 2 && name.length < 7) {
                result = col.replace(col.substring(2, name.length), "*****");
            } else if (name.length >= 7) {
                result = col.replace(col.substring(2, 7), "*****");
            }
        }
        return result;
    }

    /**
     * 姓名只保留名
     *
     * @param contacts
     * @return
     */
    public static String hideContacts(String contacts) {
        String result = "";
        if (contacts != null && !contacts.equals("")) {
            char[] name = contacts.toCharArray();
            if (name.length > 0) {
                name[0] ='*';
            }
            result=String.valueOf(name);
        }
        return result;
    }

    /**
     * 按照权限设置，格式化字段显示
     * 
     * @param regex
     * @param colName
     * @return
     */
    public static String setColRegexParams(String regex, String colName) {
        if (colName != null && !"".equals(colName)) {
            StringBuilder sb = new StringBuilder(colName);
            String str = "";
            // 判断字符串长度是否大于3
            if (colName.length() >= 3) {
                str = sb.replace(2, 7, regex).toString();
            } else {
                str = colName + "*****";
            }
            return str;
        } else {
            return "";
        }

    }

    /**
     * 格式化手机号显示
     *
     * @param regex
     * @param colName
     * @return
     */
    public static String setColRegexPhone(String regex, String colName) {
        if (colName != null && !"".equals(colName)) {
            StringBuilder sb = new StringBuilder(colName);
            String str = "";
            // 判断字符串长度是否大于3
            if (colName.length() >= 3) {
                str = sb.replace(3, 7, regex).toString();
            } else {
                str = colName + "*****";
            }
            return str;
        } else {
            return "";
        }

    }

    /**
     * 去除掉字符串富文本标签
     * @param html
     * @return
     */
    public static String getTextByHtml(String html) {

        Matcher m_script = p_html.matcher(html);

        html = m_script.replaceAll("");

        Matcher l_script = t_html.matcher(html);

        return l_script.replaceAll("");

    }


    /**
     * 传入判断null，返回空字符
     * @param str
     * @return
     */
    public static String isNullReturnString(String str){
        return str == null ?"":str;
    }


    /**
     * 把用户列权限存入map，用于列表判断
     * @param account
     * @param setDtoList
     * @return
     */
    public static Map<String,String> checkColAuth(ManageAccount account, List<ManageAuthorityDto> setDtoList){

        Map<String,String> map = new HashMap<>();

        if (setDtoList != null && setDtoList.size() > 0){
            for (ManageAuthorityDto dto:setDtoList){
                map.put(dto.getName(),account.getRolePk()+"_"+dto.getName());
            }
        }
       return map;
    }
    /**
     * 计算两日前相差的天数
     * @param begin_date
     * @param end_date
     * @return
     * @throws Exception
     */
    public static int getInterval(Date begin_date, Date end_date) throws Exception{
        int day = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(begin_date != null){
            String begin = sdf.format(begin_date);
            begin_date  = sdf.parse(begin);
        }
        if(end_date!= null){
            String end= sdf.format(end_date);
            end_date= sdf.parse(end);
        }
        day = (int) ((end_date.getTime()-begin_date.getTime())/(24*60*60*1000));
        return day;
    }


    /**
     * 判断开始时间，和结束时间逻辑
     * 用于处理导出时时间的判断
     * @param startTime
     * @param endTime
     * @param
     * @return
     * @throws Exception
     */
    public static Map<String,String> checkExportTime(String startTime, String endTime,String insertTime) throws Exception{
        Map<String,String> map = new HashMap<>();
        // 如果传入时间都为空
        if (!isNotEmpty(startTime) && !isNotEmpty(endTime)){
            map.put("startTime","");//开始时间为固定值
            map.put("endTime","");//结束时间为导出时添加时间
        }
        //如果传入的结束时间为空，开始时间不为空
        if (isNotEmpty(startTime) && !isNotEmpty(endTime)) {
            map.put("startTime",startTime);//
            map.put("endTime","");//结束时间为导出时添加时间
        }
        //如果传入开始时间为空，结束时间不为空
        if (!isNotEmpty(startTime) && isNotEmpty(endTime)) {
            map.put("startTime","");//
            map.put("endTime",endTime);//
        }
        //如果传入的时间都不为空
        if (isNotEmpty(startTime) && isNotEmpty(endTime)) {
            map.put("startTime",startTime);//
            map.put("endTime",endTime);//
        }
        return map;
    }


    /**
     * 判断更新时间 开始时间，和结束时间逻辑
     * 用于处理导出时时间的判断
     * @param startTime
     * @param endTime
     * @param
     * @return
     * @throws Exception
     */
    public static String checkUpdateExportTime(String startTime, String endTime) throws Exception{
        String str = "";
        //如果传入的结束时间为空，开始时间不为空
        if (isNotEmpty(startTime) && !isNotEmpty(endTime)) {
            str = "大于等于 " + startTime;
        }
        //如果传入开始时间为空，结束时间不为空
        if (!isNotEmpty(startTime) && isNotEmpty(endTime)) {
            str = "2016-12-01--" + endTime;
        }
        //如果传入的时间都不为空
        if (isNotEmpty(startTime) && isNotEmpty(endTime)) {
            str = startTime + "--" + endTime;
        }
        return str;
    }
    
    
	public static String getDateShow(String paramStr, String start, String end, String dateName) {
		if (CommonUtil.isNotEmpty(start)||CommonUtil.isNotEmpty(end)) {
			paramStr = paramStr +dateName;
			if (CommonUtil.isNotEmpty(end)) {
				if (CommonUtil.isNotEmpty(start)) {
					paramStr = paramStr	+ start;
				}else {
					paramStr = paramStr	+Constants.ORIGINALTIME;
				}
				paramStr = paramStr	+"--" + end + ";";
			}else {
				paramStr = paramStr	+"大于等于"+start+ ";";
			}
		}
		return paramStr;
	}
  
}