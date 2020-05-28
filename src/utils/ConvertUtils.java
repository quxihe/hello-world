package com.sinolife.lem.util;

/** 
* @author zhouchengliang.wb 
* @version 2020年3月5日 下午2:07:30 
*/

public class ConvertUtils {
	/**
	  * 字符串转unicode
     * @param str
     * @return
     */
    public static String str2Unicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
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
        return sb.toString();
    }
    
    /**
     * 字符串转unicode
     * 
     * @param str
     * @return
     */
    public static String strToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sb.append("\\u" + Integer.toHexString(c[i]));
        }
        return sb.toString();
    }
 
    /**
     * unicode转字符串
     * 
     * @param unicode
     * @return
     */
    public static String unicodeToStr(String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }
    
   public static void main(String[] args) {
	   System.out.println(unicodeToStr("\\ud83d\\ude04"));
	   System.out.println();
   }
}
