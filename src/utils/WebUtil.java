package com.sinolife.activity.util.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sinolife.activity.util.web.processor.JsonDateValueProcessor;
import com.sinolife.efs.grown.utils.PropertiesUtil;
import com.sinolife.sf.platform.runtime.PlatformContext;
import com.sinolife.util.CfgContants;

/**
 * 活动-Web工具类
 * 
 * @author renjia.wang001
 * 
 */
public class WebUtil {

	private static final Logger logger = Logger.getLogger(WebUtil.class);

	@SuppressWarnings("static-access")
	public final static String auth_auto_url = PlatformContext
			.getRuntimeConfig().get(CfgContants.weixin_wcpm_auth_auto_url,
					String.class);

	@SuppressWarnings("static-access")
	public final static String auth_auto_url_ylg = PlatformContext
			.getRuntimeConfig().get(CfgContants.weixin_ylg_auth_auto_url,
					String.class);

	public static String isProductEnv = PropertiesUtil.getPropertyByKey(
			"cfg.properties", "lem.test.isProductEnv");

	public static String testIpAddress = PropertiesUtil.getPropertyByKey(
			"cfg.properties", "lem.test.ipAddress");

	public static String testIpDockerAddress = PropertiesUtil.getPropertyByKey(
			"cfg.properties", "lem.test.docker.ipAddress");

	public static void getJsonMessages(HttpServletResponse response, Object msg) {
		try {
			response.setContentType("text/json; charset=utf-8");
			PrintWriter witer = response.getWriter();
			witer.print(JsonUtil.getJSONString(msg));
			witer.flush();
			witer.close();
		} catch (Exception e) {

		}

	}

	public static void getJsonMessages(HttpServletResponse response,
			Object msg, String format) throws IOException {
		PrintWriter write = response.getWriter();
		response.setContentType("text/json; charset=utf-8");
		JsonConfig config = new JsonConfig();
		if (StringUtils.isEmpty(format)) {
			format = "yyyy-MM-dd";
		}
		config.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor(format));
		JSONObject o = JSONObject.fromObject(msg, config);
		write.write(o.toString());
		write.flush();
		write.close();
		o.clear();
	}

	/**
	 * 获取用户ip地址
	 * 
	 * @param req
	 * @return
	 */
	public static String getClientUserIp(HttpServletRequest req) {
		String ip = req.getHeader("clientip");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	public static String getBasePath(HttpServletRequest req) {
		return req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath() + "/";
	}

	/**
	 * 屏蔽电话号码
	 * 
	 * @param value
	 * @return
	 */
	public static String maskPhone(String value) {
		if (StringUtils.isNotEmpty(value) && value.length() > 7) {
			StringBuffer sb = new StringBuffer("*******");

			return sb.append(value.substring(7)).toString();
		} else {
			return "";
		}

	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位 如果为0就设置为一个默认值为622080000
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		} else {
			cookie.setMaxAge(622080000);
		}
		response.addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	public static String getCookieValue(String cookieName,
			HttpServletRequest req) {
		Cookie[] cks = req.getCookies();
		if (null != cks) {
			for (Cookie ck : cks) {
				if (ck.getName().equals(cookieName)) {
					return ck.getValue();
				}
			}
		}
		return "";
	}

	// /**
	// * 解密字符串
	// * @param enWxUserId
	// * @return
	// */
	// public static String getWxUserId(String enWxUserId){
	// EncryptUtils utils=new EncryptUtils();
	// return utils.decryptData2(enWxUserId);
	// }

	/**
	 * 构建查询参数
	 * 
	 * @param paramMap
	 * @param url
	 * @return
	 */
	public static String buildAuthForwardUrl(Map<String, String> paramMap,
			String url) {
		String authUrl = auth_auto_url;

		StringBuffer forwardUrl = new StringBuffer(authUrl);
		forwardUrl.append(url);
		if (null != paramMap) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				forwardUrl.append(";");
				forwardUrl.append(entry.getKey());
				forwardUrl.append("=");
				forwardUrl.append(entry.getValue());
			}
		}
		return forwardUrl.toString();

	}

	/**
	 * 获取后缀以外的路径
	 * 
	 * @param viewName
	 * @return
	 */
	public static String getExtFilenameExtension(String viewName) {
		int index = viewName.lastIndexOf('.');
		if (index != -1) {
			String path = viewName.substring(0, index);
			path = path.replace("/", File.separator);
			return path;
		} else {
			return null;
		}
	}

	public static String getLocaleIpNoHost() {
		InetAddress ads = null;
		try {
			ads = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {

		}
		String ip = ads.getHostAddress();
		return ip;
	}

	public static String getLocaleIp() {
		InetAddress ads = null;
		try {
			ads = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {

		}
		String ip = ads.getHostAddress() + ":" + WebUtil.getSelfPort();
		return ip;
	}

	public static String getRandomServiceIp() {
		if ("1".equals(isProductEnv)) {
			return testIpDockerAddress;
		}
		// int k = (int)(Math.random()*(9));
		// String[] serviceIpArr =
		// {"172.28.32.18","172.28.32.19","172.28.32.20","172.28.32.21","172.28.32.22","172.28.32.162","172.28.32.163","172.28.32.164","172.28.32.165"};
		// return serviceIpArr[k];

		// int k = (int)(Math.random()*(10));
		// String[] serviceIpDockerArr =
		// {"172.28.1.35:50030","172.28.1.35:50031","172.28.1.35:50032","172.28.1.35:50033","172.28.1.35:50034","172.28.1.36:50030","172.28.1.36:50031","172.28.1.36:50032","172.28.1.36:50033","172.28.1.36:50034"};
		// return serviceIpDockerArr[k];

		return getLocaleIp();

	}

	/**
	 * 获取服务器端口号
	 */
	public static int getSelfPort() {
		MBeanServer mBeanServer = null;
		int selfPort = 0;
		ArrayList<MBeanServer> mBeanServers = MBeanServerFactory
				.findMBeanServer(null);
		mBeanServer = mBeanServers.get(0);
		Set<ObjectName> objectNames;
		try {
			objectNames = mBeanServer.queryNames(new ObjectName(
					"Catalina:type=Connector,*"), null);
			for (ObjectName objectName : objectNames) {
				Object _protocol = mBeanServer.getAttribute(objectName,
						"protocol");
				if (_protocol.toString().toLowerCase().trim()
						.startsWith("http")) {
					selfPort = Integer.parseInt(mBeanServer.getAttribute(
							objectName, "port").toString());
					break;
				}
			}
		} catch (Exception e) {
			logger.error("get selfPort fail", e);
		}
		return selfPort;
	}

	/**
	 * 封装防止静态页面缓存的参数
	 * 
	 * @param url
	 * @return
	 */
	public static String packageForwarUrlWithNocachPara(String url) {
		long timeStamp = new Date().getTime();
		if (url.indexOf("?") > -1) {
			url = url + "&nocachPar=" + timeStamp;
		} else {
			url = url + "?nocachPar=" + timeStamp;
		}
		return url;
	}

	/**
	 * 构建养老罐查询参数
	 * 
	 * @param paramMap
	 * @param url
	 * @return
	 */
	public static String buildAuthForwardUrlPension(
			Map<String, String> paramMap, String url) {
		String authUrl = auth_auto_url_ylg;

		StringBuffer forwardUrl = new StringBuffer(authUrl);
		forwardUrl.append(url);
		if (null != paramMap) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				forwardUrl.append(";");
				forwardUrl.append(entry.getKey());
				forwardUrl.append("=");
				forwardUrl.append(entry.getValue());
			}
		}
		return forwardUrl.toString();
	}

}
