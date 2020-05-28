package com.sinolife.activity.util.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinolife.activity.util.date.DateUtil;
import com.sinolife.sf.platform.http.HttpServletRequest;

/**
 * 字符串处理工具类
 * 
 * @author r3crm
 * @since 2013-3-19
 */
public class StringUtil {
	
	public static final Logger logger=LoggerFactory.getLogger(StringUtil.getClassesPath());

	/** 将首字母转为大写，其他不变 */
	public static String firstUp(String str) {
		char ch[];
		ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		String newString = new String(ch);
		return newString;
	}

	/** 将首字母转为小写，其他不变 */
	public static String firstLower(String str) {
		String first = str.substring(0, 1);
		String orther = str.substring(1);
		return first.toLowerCase() + orther;
	}

	/**
	 * 通过get或set方法名获得字段名称
	 * 
	 * @param methodName
	 *            方法名称
	 */
	public static String getFieldName(String methodName) {
		return firstLower(methodName.substring(3));
	}

	/** 将符合数据库的命名转为java的命名 */
	public static String pareseUnderline(String code) {
		String[] strs = code.split("_");
		String first = strs[0].toLowerCase();
		if (strs.length == 1) {
			return first;
		}
		StringBuffer sb = new StringBuffer(first);
		for (int i = 1; i < strs.length; i++) {
			sb.append(firstUpOnly(strs[i]));
		}
		return sb.toString();
	}

	/** 将首字母转为大写，其他变小写 */
	public static String firstUpOnly(String str) {
		String first = str.substring(0, 1);
		String orther = str.substring(1);
		return first.toUpperCase() + orther.toLowerCase();
	}

	/** 将大写字母转换为小写字母,并加上分隔符 */
	public static String pareseUpCase(String code, String replace) {
		char[] old = code.toCharArray();
		char[] news = code.toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < old.length; i++) {
			if (old[i] == news[i]) {
				sb.append(news[i]);
			} else {
				sb.append(replace + news[i]);
			}
		}
		return sb.toString();
	}

	/** 将符合java的命名转为数据库的命名 */
	public static String pareseUpCase(String code) {
		return pareseUpCase(code, "_");
	}

	public static void addLine(StringBuffer sb, String str) {
		sb.append(str);
		sb.append(System.getProperty("line.separator"));
	}

	/** 将字符串转为uicode */
	public static String getUnicode(String str) {
		if (str == null) {
			return "";
		}
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char ch : chars) {
			String temp = Integer.toHexString(ch);
			if (temp.length() == 1) {
				temp = "000" + temp;
			}
			if (temp.length() == 2) {
				temp = "00" + temp;
			}
			if (temp.length() == 3) {
				temp = "0" + temp;
			}
			sb.append("\\u" + temp);
		}
		return sb.toString();
	}

	/**
	 * 将字符串转为map 字符串:userName=张三,userCode=zhangsan,age 结果:map.put(userName,张三);
	 * map.put(userCode,zhangsan); map.put(age,null);
	 */
	@SuppressWarnings("rawtypes")
	public static Map toMap(String str) {
		return toMap(str, ",");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map toMap(String str, String splitString) {
		Map map = new HashMap();
		if (str == null || str.equals("")) {
			return map;
		}
		String values[] = str.split(splitString);
		for (int i = 0; i < values.length; i++) {
			String tempValue = values[i];
			int pos = tempValue.indexOf("=");
			String key = "";
			String value = "";
			if (pos > -1) {
				key = tempValue.substring(0, pos);
				value = tempValue.substring(pos + splitString.length());
			} else {
				key = tempValue;
			}
			map.put(key, value);
		}

		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map toMap(List<String> strs) {
		Map map = new HashMap();
		if (strs == null) {
			return map;
		}
		for (String st : strs) {
			map.put(st, null);
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getIds(List<String> pageId, String prefix) {
		List list = new ArrayList();
		if (pageId == null) {
			return list;
		}
		for (String id : pageId) {
			list.add(id);
		}
		return list;
	}

	/** 判断对象是否为空 */
	public static boolean isNull(Object object) {
		if (object instanceof String) {
			return isNull((String) object);
		}
		return object == null;
	}

	public static String getPropertyName(String methodName) {
		String propertyName = null;
		if (methodName.startsWith("get")) {
			propertyName = methodName.substring("get".length());
		} else if (methodName.startsWith("is")) {
			propertyName = methodName.substring("is".length());
		} else if (methodName.startsWith("set")) {
			propertyName = methodName.substring("set".length());
		}
		if (propertyName == null || propertyName.length() == 0) {
			return null;
		}

		return StringUtil.firstLower(propertyName);
	}

	/** 判断字符串是否为空 */
	public static boolean isNull(String value) {
		return value == null || value.equals("");
	}

	/** 判断字符串是否为空 */
	public static boolean isNotNull(String value) {
		return value != null && !value.equals("");
	}

	/**
	 * 获得指定字符串转化为Bytes数组后,数组的长度. <br>
	 * <br>
	 * <b>示例: </b> <br>
	 * StringUtils.getBytesLength(&quot;中国人&quot;) 返回 6
	 * StringUtils.getBytesLength(&quot;Cmm&quot;) 返回 3
	 * StringUtils.getBytesLength(&quot;&quot;) 返回 0
	 * StringUtils.getBytesLength(null) 返回 -1
	 * 
	 * @param str
	 *            指定的字符串,字符串的值不能为null
	 * @return 指定字符串转化为Bytes数组后,数组的长度
	 * @throws
	 */
	public static int getBytesLength(String str) {
		int length = 0;
		if (str == null) {
			length = -1;
		} else {
			try {
				char[] cs = str.toCharArray();
				for (int i = 0; i < cs.length; i++) {
					if ((cs[i] >= '0' && cs[i] <= '9')
							|| (cs[i] >= 'a' && cs[i] <= 'z')
							|| (cs[i] >= 'A' && cs[i] <= 'Z')) {
						length += 1;
					} else {
						length += 2;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return length;
	}

	/** 判断一个字符串是否是数字,可以包含小数点 */
	public static boolean isNum(String str) {

		return (str.replaceAll("^\\d+[.]?\\d+$", "").length() == 0);

	};

	/**
	 * 截取中文字符串
	 * 
	 * @param oldString
	 * @param length
	 * @return
	 */

	public static String subChineseStr(String oldString, int length) {
		int len = 0;
		int lenZh = 0;
		if (oldString == null) {
			return null;
		}
		if ("".equals(oldString)) {
			return "";
		}

		char c;
		for (int i = 0; i < oldString.length(); i++) {
			c = oldString.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
					|| (c >= 'A' && c <= 'Z')) {
				// 字母, 数字
				len++;
			} else {
				if (Character.isLetter(c)) { // 中文
					len += 4;
				} else { // 符号或控制字符
					len++;
				}
			}
			if (len <= length) {
				lenZh += 1; // 用来SUBSTRING
			}
		}
		if (len > 0 && len <= length) {
			return oldString;
		} else if (len > length) {
			return oldString.substring(0, lenZh);
		}
		return null;
	}

	/** 将字符串集合转换为字符串 */
	public static String toStirng(Collection<String> strs, String split) {
		StringBuffer sb = new StringBuffer();
		for (String st : strs) {
			sb.append(st + split);
		}
		return sb.substring(0, sb.length() - split.length());
	}

	public static String getLine() {
		return System.getProperty("line.separator");
	}

	public static String appendStr(String source, String append, int no) {
		StringBuilder sb = new StringBuilder(source);
		for (int i = 0; i < no; i++) {
			sb.append(append);
		}
		return sb.toString();
	}

	public static String toSqlString(String[] ids) {
		StringBuffer sb = new StringBuffer();
		for (String st : ids) {
			sb.append("'" + st + "'" + ",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	/** 将null转换为其他字符 */
	public static String nullToMessage(String str, String msg) {
		if (str != null) {
			return str;
		} else {
			return msg;
		}
	}

	/** 如果字符串是以某个字段串结尾的就截掉 */
	public static String getLastBefore(String str, String tail) {
		int index = str.lastIndexOf(tail);
		if (index != -1) {
			return str.substring(0, index);
		} else {
			return str;
		}
	}

	/** 获得第一个字符以后的字符串 */
	public static String getFirstAfter(String str, String tail) {
		int index = str.indexOf(tail);
		if (index != -1) {
			return str.substring(index + tail.length());
		} else {
			return str;
		}
	}

	/** 获得最有一个字符以后的字符串 */
	public static String getLastAfter(String str, String pos) {
		int index = str.lastIndexOf(pos);
		if (index != -1) {
			return str.substring(index + pos.length());
		} else {
			return str;
		}
	}

	/** 截取特定字符以前的内容 */
	public static String getFirstBefore(String str, String pos) {
		int index = str.indexOf(pos);
		if (index != -1) {
			return str.substring(0, index);
		} else {
			return str;
		}
	}

	/**
	 * 将某个字符串里的内容用正则替换
	 * 
	 * @param text
	 *            原来的内容
	 * @param regx
	 *            正则表达式
	 * @param before
	 *            替换后的内容前面部分
	 * @param end
	 *            替换后的的内容后面部分
	 * @param remain
	 *            是否保留原先正则里的内容
	 * */
	public static String replace(String text, String regx, String before,
			String end, boolean remain) {
		if (before == null) {
			before = "";
		}
		if (end == null) {
			end = "";
		}
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(text);
		String[] texts = text.split(regx);
		StringBuffer sb = new StringBuffer(texts[0]);
		int i = 1;
		while (matcher.find()) {
			String group = matcher.group();
			sb.append(before);
			if (remain) {
				sb.append(group);
			}
			sb.append(end);
			sb.append(texts[i]);
			i++;
		}
		return sb.toString();
	}

	public static String pickUpFirst(String str, String regx) {
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * 在字符串前后追加
	 * 
	 * @param text
	 *            原来的内容
	 * @param regx
	 *            正则表达式
	 * @param before
	 *            前面追加的内容
	 * @param end
	 *            后面追加的内容
	 * */
	public static String append(String text, String regx, String before,
			String end) {
		return StringUtil.replace(text, regx, before, end, true);
	}

	/** 创建一个字符串 */
	public static String createString(String str, int no, String split) {
		if (no == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < no; i++) {
			sb.append(str + split);
		}
		return sb.substring(0, sb.length() - split.length());
	}

	/** 判断一个字符串是否是数字 */
	public static boolean isInteger(String str) {
		return str.replaceFirst(RegexStringUtil.integer, "").length() == 0;
	}

	/** 判断一个字符串是否是英文字符 */
	public static boolean isLetter(String str) {
		return str.replaceFirst(RegexStringUtil.letter, "").length() == 0;
	}

	/** 判断是否符合命名规范,以字母开头,只包含字符数字或者下划线 */
	public static boolean isNamingConvention(String str) {
		return str.replaceFirst(RegexStringUtil.namingConvention, "").length() == 0;
	}

	/** 代码生成用 */
	public static String stringBuilder(String str, int pool) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < pool; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/****
	 * 获取classes文件夹的绝对路径
	 * 
	 * @return 返回classes文件夹的绝对路径
	 */
	public static String getClassesPath() {
		String _classesPath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();

		if (_classesPath.startsWith("/"))
			_classesPath = _classesPath.substring(1, _classesPath.length());

		if (_classesPath.indexOf("/") != -1) {
			_classesPath = _classesPath.replace("/", File.separator);
		}

		if (!_classesPath.endsWith(File.separator)) {
			_classesPath += File.separator;
		}

		return _classesPath;
	}

	public static Map<String, Object> getInfoFromIdNo(String idNo)
			throws ParseException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String birthDay = null;
		String sexCode = null;
		if (idNo.length() == 15) {
			birthDay = "19" + idNo.substring(6, 8) + "-"
					+ idNo.substring(8, 10) + "-" + idNo.substring(10, 12);
			sexCode = idNo.substring(14, 15);
		} else if (idNo.length() == 18) {
			birthDay = idNo.substring(6, 10) + "-" + idNo.substring(10, 12)
					+ "-" + idNo.substring(12, 14);
			sexCode = idNo.substring(16, 17);
		} else {
			return resMap;
		}
		int i = Integer.parseInt(sexCode);
		i = i % 2;
		if (i == 0) {
			sexCode = "2";
		} else if (i == 1) {
			sexCode = "1";
		}
		resMap.put("birthDay", DateUtil.parse(birthDay, "yyyy-MM-dd"));
		resMap.put("sexCode", sexCode);
		return resMap;
	}

	/**
	 * 将字符串数组分割为指定 Size 的数组列表
	 * 
	 * @param array
	 *            原始字符串数组
	 * @param size
	 *            子数组大小
	 * @return
	 */
	public static List<String[]> splitArray(String[] array, int size) {
		List<String[]> arrayList = new ArrayList<String[]>();
		int arrayLength = array.length;
		for (int i = 0; i <= arrayLength / size; i++) {
			int from = i * size;
			int to = (i + 1) * size;
			if (arrayLength / size == i) {
				to = arrayLength;
			}
			String[] childArray = Arrays.copyOfRange(array, from, to);
			arrayList.add(childArray);
		}
		return arrayList;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataEmpty(String str) {
		if (StringUtils.isEmpty(str) || "null".equals(str)
				|| "undefined".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static String formatNumber(int number) {
		String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
				"十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十" };
		return str[number];
	}

	/**
	 * 根据生日转化生日
	 * 
	 * @param idNo
	 * @return
	 */
	public static String getBirthFromIdNo(String idNo) {
		String birthStr = "";
		Pattern pattern = null;
		if (idNo.length() == 15) {
			pattern = Pattern.compile("^(\\d{6})(\\d{6})(.*)");
		} else if (idNo.length() == 18) {
			pattern = Pattern.compile("^(\\d{6})(\\d{8})(.*)");
		}
		if (pattern != null) {
			Matcher match = pattern.matcher(idNo);
			if (match.find() && match.groupCount() > 0) {
				birthStr = match.group(2);
				if (birthStr.length() == 6) {
					birthStr = "19" + birthStr;
				}
				if (StringUtil.isNotNull(birthStr)) {
					try {
						Date abirtDate = DateUtil.parse(birthStr, "yyyyMMdd");
						birthStr = DateUtil.dateToString(abirtDate,
								"yyyy-MM-dd");
					} catch (Exception e) {
						birthStr = "";
						e.printStackTrace();
					}
				}
			}
		}
		return birthStr;
	}

	/**
	 * 获取Http请求参数字符串
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getReqParametersStr(HttpServletRequest req) {
		String reqParaStr = "";
		try {
			if (req != null) {
				Map<String, String[]> map = req.getParameterMap();
				if (!map.isEmpty()) {
					for (Map.Entry<String, String[]> entry : map.entrySet()) {
						String key = entry.getKey();
						String[] values = entry.getValue();
						if (values != null && values.length > 0
								&& !isDataEmpty(key)) {
							for (int i = 0; i < values.length; i++) {
								reqParaStr = reqParaStr + key + "=" + values[i]
										+ "&";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			reqParaStr = "";
		}
		if (reqParaStr.endsWith("&")) {
			reqParaStr = reqParaStr.substring(0, reqParaStr.length() - 1);
		}
		return reqParaStr;
	}

	public static String ClobToString(CLOB clob) throws SQLException,
			IOException {
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * 匹配是否为数字（传入包含中文、负数、位数很长的数字的字符串也能正常匹配）
	 * 
	 * @param str
	 *            可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
	 * @return
	 */
	public static boolean isNumeric(String str) {
		// 该正则表达式可以匹配所有的数字 包括负数
		Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
		String bigStr;
		try {
			bigStr = new BigDecimal(str).toString();
		} catch (Exception e) {
			return false;// 异常 说明包含非数字。
		}

		Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 生僻字转换
	 * @author renjia.wang001
	 * @param str
	 * @return
	 */
	public static String stringToUnicode(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer();
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}
	
	/**
	 * 字符串日期降序排序
	 * @author wupingyan.wb
	 *
	 */
	public static class DateStringSortDesc implements Comparator<String>{
		@Override
		public int compare(String s1, String s2) {
			int result=0;
			try {
				if(s1.compareTo(s2)>0) {
					result=-1;
				}else if(s1.compareTo(s2)<0) {
					return 1;
				}
			}catch(Exception e) {
				logger.error("DateStringSortDesc Exception:",e);
			}
			return result;
		}
	}
	

}
