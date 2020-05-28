package com.sinolife.lem.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

/**
 * 加密解密类
 */
@Component("encryptUtils")
public class EncryptUtils {
	private static String Algorithm = "DES";
	private String key = "CB7A92E3D3491981";
	// 定义 加密算法,可用 DES,DESede,Blowfish
	static boolean debug = false;

	/**
	 * 构造子注解.
	 */
	public EncryptUtils() {

	}

	/**
	 * 生成密钥
	 * 
	 * @return byte[] 返回生成的密钥
	 * @throws exception
	 *             扔出异常.
	 */
	public static byte[] getSecretKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
		SecretKey deskey = keygen.generateKey();
		System.out.println("生成密钥:" + bytesToHexString(deskey.getEncoded()));
		if (debug)
			System.out.println("生成密钥:" + bytesToHexString(deskey.getEncoded()));
		return deskey.getEncoded();

	}

	/**
	 * 将指定的数据根据提供的密钥进行加密
	 * 
	 * @param input
	 *            需要加密的数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encryptData(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		if (debug) {
			System.out.println("加密前的二进串:" + byte2hex(input));
			System.out.println("加密前的字符串:" + new String(input));

		}
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		if (debug)
			System.out.println("加密后的二进串:" + byte2hex(cipherByte));
		return cipherByte;

	}

	/**
	 * 将给定的已加密的数据通过指定的密钥进行解密
	 * 
	 * @param input
	 *            待解密的数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decryptData(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		if (debug)
			System.out.println("解密前的信息:" + byte2hex(input));
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		if (debug) {
			System.out.println("解密后的二进串:" + byte2hex(clearByte));
			System.out.println("解密后的字符串:" + (new String(clearByte)));

		}
		return clearByte;

	}

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param byte[] b 输入要转换的字节码
	 * @return String 返回转换后的16进制字符串
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";

		}
		return hs.toUpperCase();

	}

	/**
	 * 字符串转成字节数组.
	 * 
	 * @param hex
	 *            要转化的字符串.
	 * @return byte[] 返回转化后的字符串.
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 字节数组转成字符串.
	 * 
	 * @param String
	 *            要转化的字符串.
	 * @return 返回转化后的字节数组.
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 从数据库中获取密钥.
	 * 
	 * @param deptid
	 *            企业id.
	 * @return 要返回的字节数组.
	 * @throws Exception
	 *             可能抛出的异常.
	 */
	public static byte[] getSecretKey(long deptid) throws Exception {
		byte[] key = null;
		String value = null;
		// CommDao dao=new CommDao();
		// List list=dao.getRecordList("from Key k where k.deptid="+deptid);
		// if(list.size()>0){
		// value=((com.csc.sale.bean.Key)list.get(0)).getKey();
		value ="CB7A92E3D3491981";
		key = hexStringToByte(value);
		// }
		if (debug)
			System.out.println("密钥:" + value);
		return key;
	}

	public String encryptData2(String data) {
		String en = null;
		try {
			byte[] key = hexStringToByte(this.key);
			en = bytesToHexString(encryptData(data.getBytes(), key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return en;
	}

	public String decryptData2(String data) {
		String de = null;
		try {
			byte[] key = hexStringToByte(this.key);
			de = new String(decryptData(hexStringToByte(data), key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return de;
	}
	
	@SuppressWarnings({ "static-access"})
	public static void main(String[] args) throws Exception {  
		  EncryptUtils utils = new EncryptUtils();
		  String key ="Sinolife20180101";
		  byte[] keys = utils.hexStringToByte(key);
		  
		  String str="{\"unionId\":\"olHR9jhoxTDMRDhFIKV818kNmEss\",\"openId\":\"oCYD60EXFszDGU_byes5CBTO_2Ts\"}";
		  str="11613906";
		  String mdata = utils.encryptData2(str);
		  System.out.println("返元1:"+mdata);
		  System.out.println("返元2:"+utils.bytesToHexString(utils.encryptData(str.getBytes(), keys)));
		  String mdata2 =utils.bytesToHexString(utils.encryptData(str.getBytes(), keys));
		  mdata = "D5FA42329D3743A10F64E0A4E2D6E93D4199B72F8223790F8B908FD24C174024A085BF9BEF40BF80564D128B87A111DC05A19F72EB275D9E30814830F958B5F0B52110FDA07EAFB02637656574440CCD057995D3820E5091FD698CE09EB50E790F7F788280309E67";
		  System.out.println("返元3:"+ utils.decryptData2(mdata));
		  System.out.println("返元4:"+ new String (utils.decryptData(utils.hexStringToByte(mdata2), keys)));
		  
		  
	  }
}