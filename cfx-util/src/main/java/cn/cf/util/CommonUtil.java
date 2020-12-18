package cn.cf.util;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtil {
	private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	 /** 
	   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	   * 
	   * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
	   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
	   * 
	   * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
	   * 192.168.1.100 
	   * 
	   * 用户真实IP为： 192.168.1.110 
	   * 
	   * @param request 
	   * @return 
	   */ 
	  public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    }
	    if(null!=ip){
	    	 if(ip.contains(",")){
	 	    	ip = ip.split(",")[0];
	 	    }
	    } 
	   
	    return ip; 
	  } 
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

	public static boolean writeFile(String strFilePath, String strContent,
			boolean renewMark) {
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
		String path = CommonUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		// path = path.substring(0, path.lastIndexOf("com"));
		return path;
	}

	public static String getClassFilePath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassFile(clazz)
					.getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("url decode error:", e);
			return "";
		}
	}

	private static File getClassFile(Class<?> clazz) {
		URL path = clazz.getResource(clazz.getName().substring(
				clazz.getName().lastIndexOf(".") + 1)
				+ ".class");
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
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String ip = null;
		// �?��服务器反向代理时�?��变更代理获取规则，否则将得到反向代理IP.
		ip = request.getRemoteAddr();
		return ip;
	}

	public static String isProxy() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
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
	 * 判断字符串是否为空
	 * 返回true表示为空，false不为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){

		if(str != null && !"".equals(str)){
			return false;
		} else{
			return true;
		}

	}


	public static void main(String[] args) {

	}
}
