package edu.mum.rideshare.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {
	private SecretKey key;
	private Cipher cipher;
	private IvParameterSpec iv;
	private static byte[] iv1 = { (byte) 0x12, (byte) 0x34, (byte) 0x56,
			(byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	public static DES getInstance(String key) {
		    DES des=null;
			try {
				des = getInstance(key.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return des;
	
	}

	public DES() {
		this.iv = new IvParameterSpec(iv1);
		
	}

	/**
	 * @param key
	 * @return得到加密后的密钥
	 */
	public static DES getInstance(byte key[]) {
		DES des = new DES();
		
			try {
				if (des.key == null) {
					DESKeySpec dks = new DESKeySpec(key);
					SecretKeyFactory keyFactory = SecretKeyFactory
							.getInstance("DES");
					des.key = keyFactory.generateSecret(dks);
				}
				des.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return des;
	}

	/*
	 * public static void main(String[] args) { System.out.print("xyz"); DES des
	 * = new DES(); System.out.print(des.encrypt("19760519")); }
	 */
	public byte[] desEncrypt(byte[] plainText) throws Exception {

		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}
	/*
	 * public static void main(String[] args) { System.out.print("xyz"); DES des
	 * = new DES(); System.out.print(des.encrypt("19760519")); }
	 */
	public byte[] desDecode(byte[] plainText) throws Exception {

		cipher.init(Cipher.DECRYPT_MODE, key,iv);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	/**
	 * @param input
	 * @return  加密的结果
	 */
	public String getResult(String input,int mode) {
		String result = "input";
		try {
			if (input == null) return null;
			if (mode==0) {
				byte[] encodes=desEncrypt(input.getBytes("utf-8"));
				result = base64Encode(encodes);
			}
			else {
				byte[] codes=Base64.decode(input.getBytes("utf-8"), Base64.URL_SAFE);
				byte[] decodes=desDecode(codes);
				result=new String(decodes,"utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String base64Encode(byte[] s) {
		if (s == null)
			return null;
		return Base64.encodeToString(s, Base64.URL_SAFE);

	}
	
}