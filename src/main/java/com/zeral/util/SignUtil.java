package com.zeral.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于验证signture,成为开发者
 * 
 * @author Zeral
 *
 */
public class SignUtil {
		//生成sign
		public static String generateSign(String tocken, String timestamp, String nonce) {
			String[] paramArr = new String[] { tocken, timestamp, nonce };
			//对token、timestamp、nonce 进行字典排序，并拼接成字符串
			Arrays.sort(paramArr);
			StringBuilder sb = new StringBuilder(paramArr[0]);
			sb.append(paramArr[1]).append(paramArr[2]);
			String ciphertext = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] digest = md.digest(sb.toString().getBytes());// 对接后的字符串进行sha1加密
				ciphertext = byteToStr(digest);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return ciphertext.toLowerCase();
		}
		// 生成带地址的加密字符串
		public static String generateSign(String jsapiTicket, String timestamp, String noncestr, String url) {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("noncestr", noncestr);
			paramMap.put("jsapi_ticket", jsapiTicket);
			paramMap.put("timestamp", timestamp);
			paramMap.put("url", url);
			Object[] paramArr = paramMap.keySet().toArray();
			//对token、timestamp、nonce 进行字典排序，并拼接成字符串
			Arrays.sort(paramArr);
			StringBuilder sb = new StringBuilder(paramArr[0]+"="+paramMap.get(paramArr[0]));
			sb.append("&"+paramArr[1]+"="+paramMap.get(paramArr[1]))
			.append("&"+paramArr[2]+"="+paramMap.get(paramArr[2]))
			.append("&"+paramArr[3]+"="+paramMap.get(paramArr[3]));
			String ciphertext = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] digest = md.digest(sb.toString().getBytes());// 对接后的字符串进行sha1加密
				ciphertext = byteToStr(digest);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return ciphertext.toLowerCase();
		}
		
		//验证sign
		public static Boolean validSign(String signature, String tocken, String timestamp, String nonce) {
			String ciphertext = generateSign(tocken, timestamp, nonce);
			// 将sha1加密后的字符串与  signature 进行比较
			return ciphertext != null ? ciphertext.equals(signature) : false;
		}

		private static String byteToStr(byte[] byteArray) {
			String rst = "";
			for (int i = 0; i < byteArray.length; i++) {
				rst += byteToHex(byteArray[i]);
			}
			return rst;
		}
		
		private static String byteToHex(byte b) {
			char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			char[] tempArr = new char[2];
			tempArr[0] = Digit[(b >>> 4) & 0X0F];
			tempArr[1] = Digit[b & 0X0F];
			String s = new String(tempArr);
			return s;
		}
}
