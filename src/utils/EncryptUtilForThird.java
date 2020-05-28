package com.sinolife.lem.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 第三方积分商城对接加密解密
 * @author qian.xiong001
 *
 */
public class EncryptUtilForThird {
	
	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public static final String CHARSET = "utf-8";
	public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding"; 
	
	private final static char[] strDigits = {'0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	
	/**
	 * 生成安全码
	 * @param userId
	 * @param appkey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static  String  jsonIdByMap(Map<String,Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		List<String> keyList = new ArrayList<String>(map.keySet());
		String  md5 = "";
		Collections.sort(keyList);
		for(int i=0;i<keyList.size();i++){
			md5 += map.get(keyList.get(i))+"_";
		}
		md5 = md5.substring(0,md5.length()-1);
		md5+="@sino-life.com";
		return EncryptUtilForThird.MD5(md5);
	}
	
	/**
	 * MD5加密
	 * @param s
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public final static String MD5(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {  
		byte[] btInput = s.getBytes(CHARSET);  
		//获得MD5摘要算法的 MessageDigest 对象  
        MessageDigest mdInst = MessageDigest.getInstance("MD5");  
        //使用指定的字节更新摘要  
        mdInst.update(btInput);  
        //获得密文  
        byte[] md = mdInst.digest();  
        //把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = strDigits[byte0 >>> 4 & 0xf];  
                str[k++] = strDigits[byte0 & 0xf];  
            }  
            return new String(str);  
    }  
	
	/**
	 * sha1编码
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String sha1(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(data.getBytes(CHARSET));
		StringBuilder buf = new StringBuilder();
		byte[] bits = md.digest();
		for (int i = 0; i < bits.length; i++) {
			int a = bits[i];
			if (a < 0)
				a += 256;
			if (a < 16)
				buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		return buf.toString();
	}
	
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	public static String parseByte2HexStr(byte buf[]) {  
	        StringBuilder sb = new StringBuilder();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	} 
	
	/** 
	 * AES加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 16位
	 * @return 
	 */  
	public static String encryptAES(String content, String password){  
	       
	     try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(CHARSET), "AES");
			 Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			 //cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			 cipher.init(Cipher.ENCRYPT_MODE, key);
			 byte[] encryptedData = cipher.doFinal(content.getBytes(CHARSET));
			 return EncryptUtilForThird.parseByte2HexStr(encryptedData);
		} catch (Exception e) {
			
		}
		return StringUtils.EMPTY;
	}  
	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public static byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}
	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥  16位
	 * @return 
	 */  
	public static String decryptAES(String content, String password){ 	       
	 	 try {
			byte[] byteMi = EncryptUtilForThird.parseHexStr2Byte(content);
			 SecretKeySpec key = new SecretKeySpec(password.getBytes(CHARSET), "AES");
			 Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			 cipher.init(Cipher.DECRYPT_MODE, key);
			 byte[] decryptedData = cipher.doFinal(byteMi);
			 return new String(decryptedData);
		} catch (Exception e) {
			
		}
		return StringUtils.EMPTY;
	} 
	
	/** 
	 * Base64加密 
	 *  
	 * @param str 需要加密的内容 
	 * @return 
	 */ 
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes(CHARSET);
		} catch (UnsupportedEncodingException e) {

		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	/** 
	 * Base64解密 
	 *  
	 * @param s 需要解密 的内容 
	 * @return 
	 */ 
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, CHARSET);
			} catch (Exception e) {

			}
		}
		return result;
	}
}
