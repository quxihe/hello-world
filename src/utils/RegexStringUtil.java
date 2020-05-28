package com.sinolife.activity.util.web;

/**
 * 活动-正则表达式
 * @author renjia.wang001
 */
public class RegexStringUtil {
	// 匹配一行
	public static String line="[\\S ]+[\\S| ]*";
	// 匹配非空的一行
	public static String lineTrim="[\\S| ]+";
	// 匹配整形
	public static String integer="\\d+";
	// java注释
	public static String javaComment="/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/";
	// 字母
	public static String letter="([a-z]|[A-Z])*";
	// 数字字母或者下划线 ,以字母开头(java命名规范)
	public static String namingConvention="^([a-z]|[A-Z])+([\\_]|\\d|[a-z]|[A-Z])*$";
	// 匹配数字
	public static String number="^\\d+[.]?\\d+$";
	
	// 字母数字
	public static String letterNum="^[A-Za-z0-9]+$";
	
	/**包含某些字母的一行**/
	public static String getLine(String content){
		return "[\\S ]*"+content+"[\\S| ]*";
	}
}




