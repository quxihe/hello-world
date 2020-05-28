package com.sinolife.util;

import java.util.UUID;

public class UUIDUtil
{
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public String greater(char flag) {
		int ran = (int) (Math.random() * 900) + 100;
		String result = flag + String.valueOf(ran) + getUuid();
		return result;
	}

	public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String getUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 20; i++) {
			String str = uuid.substring(i * 1, i * 1 + 2);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 36]);
		}
		return shortBuffer.toString();
	}
	
	public static String getUUID() {
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	
	public static String getUuid20() {	        
		StringBuffer shortBuffer = new StringBuffer();	        
		String uuid = UUID.randomUUID().toString().replace("-", "");	   
		for (int i = 0; i < 8; i++) {	        
			String str = uuid.substring(i * 4, i * 4 + 4);	        
			int x = Integer.parseInt(str, 16);	        
			shortBuffer.append(chars[x % 36]);	        
		}	        
		return shortBuffer.toString();       
	}
	
	public static String getUuid32() {
		return UUID.randomUUID()
				   .toString()
				   .replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDUtil.getUUID()+ System.currentTimeMillis());
		System.out.println(UUIDUtil.getUuid());
		System.out.println(UUIDUtil.getUuid20());
	}
}
