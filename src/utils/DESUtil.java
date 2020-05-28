package com.sinolife.lem.util;

import org.apache.log4j.Logger;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
	 private static final String DES = "DES";
	    private static final Logger logger = Logger.getLogger(DESUtil.class);

	    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
	        // DES算法要求有一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	        // 从原始密匙数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	        // Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance(DES);
	        // 用密匙初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
	        // 正式执行加密操作
	        return cipher.doFinal(src);
	    }

	    /**
	     *
	     * @param password 密码
	     * @param key 加密字符串
	     * @return
	     */
	    public static final String encrypt(String password, String key) {
	        try {
	            return byte2String(encrypt(password.getBytes(), key.getBytes()));
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	        }
	        return null;
	    }

	    public static String byte2String(byte[] b) {
	        StringBuilder hs = new StringBuilder("");
	        String stmp;
	        for (int n = 0; n < b.length; n++) {
	            stmp = java.lang.Integer.toHexString(b[n] & 0XFF);
	            if (stmp.length() == 1) {

	                hs.append("0").append(stmp);
	            } else {
	                hs.append(stmp);
	            }
	        }
	        return hs.toString().toUpperCase();
	    }

	    /**
	     *
	     * @param src 数据源
	     * @param key 密钥，长度必须是8的倍数
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
	        // DES算法要求有一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	        // 从原始密匙数据创建一个DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	        // Cipher对象实际完成解密操作
	        Cipher cipher = Cipher.getInstance(DES);
	        // 用密匙初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

	        // 正式执行解密操作
	        return cipher.doFinal(src);
	    }

	    public static final String decrypt(String data, String key) {
	        try {
	            return new String(decrypt(string2Byte(data.getBytes()), key.getBytes()));
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	        }
	        return null;
	    }

	    public static byte[] string2Byte(byte[] b) {
	        if ((b.length % 2) != 0)
	            throw new IllegalArgumentException("长度不是偶数");
	        byte[] b2 = new byte[b.length / 2];
	        for (int n = 0; n < b.length; n += 2) {
	            String item = new String(b, n, 2);
	            b2[n / 2] = (byte) Integer.parseInt(item, 16);
	        }
	        return b2;
	    }


	    public static void main(String[] args) {
	        String encryptString = encrypt("{\"encryptedData\":{\"unionid\":\"test\",\"openid\":\"test\"}}",
	                "CB7A92E3D3491981");
	        System.out.println(encryptString);
	        System.out.println(
	                decrypt("D5FA42329D3743A10F64E0A4E2D6E93D4199B72F8223790F8B908FD24C174024A085BF9BEF40BF80564D128B87A111DC05A19F72EB275D9E30814830F958B5F0B52110FDA07EAFB02637656574440CCD057995D3820E5091FD698CE09EB50E790F7F788280309E67",
	                        "CB7A92E3D3491981"));
	    }
}
