/*
 * @(#)DESUtil.java	2015-6-18上午11:24:01
 * Copyright 2015  lc All rights reserved.
 */
package edu.mum.rideshare.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * 
 * 类<strong>DESUtil.java</strong>{此类功能描述}
 * 
 * @author: LY
 * @version: 1.0 Date: 2013-05-01 上午11:24:01
 */
public class DESUtil {

	private Key key;

	private final String keyStr = "abc123";
	public DESUtil() {
		this.key =getKey(keyStr);// 生成密匙
	}

	/**
	 * 根据参数生成KEY
	 */
	public Key getKey(String strKey) {
		try {

			File file = new File("key.jsk");
			if (file.exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				return (Key)in.readObject();
			}
			else {
				Key temp = generateKey(strKey);
				FileOutputStream f = new FileOutputStream("key.jsk");
				ObjectOutputStream s = new ObjectOutputStream(f);
				s.writeObject(temp);
				s.flush();
				return temp;
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
	}

	private Key generateKey(String strKey) throws Exception {
		KeyGenerator _generator = KeyGenerator.getInstance("DES");
		_generator.init(new SecureRandom(strKey.getBytes()));
		return _generator.generateKey();

	}

	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param file 要加密的文件 如c:/test/srcFile.txt
	 * @param destFile 加密后存放的文件名 如c:/加密后文件.txt
	 */
	public byte[] encrypt(byte[] in) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		// cipher.init(Cipher.ENCRYPT_MODE, getKey());
		cipher.init(Cipher.ENCRYPT_MODE, this.key);
		return cipher.doFinal(in);

	}

	/**
	 * 文件采用DES算法解密文件
	 * 
	 * @param file 已加密的文件 如c:/加密后文件.txt * @param destFile 解密后存放的文件名 如c:/ test/解密后文件.txt
	 */
	public byte[] decrypt(byte[] str1) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, this.key);
		return cipher.doFinal(str1);
	}

	public static void main(String[] args) throws Exception {
		//DESUtil td = new DESUtil();
		//String encryptStr;
		//encryptStr = new String(td.encrypt("Java实现文件的RSA和DES加密算法.doc".getBytes())); // 加密
		//System.out.println("==========>" + encryptStr);
		//System.out.println("==========>" + new String(td.decrypt(td.encrypt("Java实现文件的RSA和DES加密算法.doc".getBytes()))));
      //System.out.println("doGenerate:");
      //DESUtil.generateKeypari();
     // DESUtil.generateSignature("This is URL INFO we will decode");
      //DESUtil.readFile(ApplicationListener.REAL_PATH+"sk.dat");
		//DESUtil.generatePair();
		//ObjectInputStream in=new ObjectInputStream(new FileInputStream(ApplicationListener.REAL_PATH+"info.dat"));
 	    //String s=(String)in.readObject();		
 	    //DESUtil.validData(s.getBytes());
	}
	
//	/***
//	 * 根据RSA 在WebApp应用下生成一对PrivateKey PublicKey文件,可以将生成好的PublicKey文件交付给另外的系统
//	*/
//	public static void generageKey(){
//		try {
//			KeyPairGenerator keyGenerator=KeyPairGenerator.getInstance(AppConstants.QUEUE_VERIFY_ALGORITHM);
//			SecureRandom sec=new SecureRandom();
//			sec.setSeed(AppConstants.RSA_SEED.getBytes());
//			keyGenerator.initialize(1024,sec);
//			KeyPair key=keyGenerator.generateKeyPair();
//			PublicKey publicKey=key.getPublic();
//			PrivateKey privateKey=key.getPrivate();
//			///
//			String strPublic=bytesToHexStr(publicKey.getEncoded());
//			String strPrivate=bytesToHexStr(privateKey.getEncoded());
//			writeFile(AppConstants.PrivateFileName,strPrivate);
//			writeFile(AppConstants.PublicFileName,strPublic);
//		}catch (Exception ext){
//			ext.printStackTrace();
//			
//		}
//	}//generateKey
//
//    /**
//     * 根据WebApp应用下的PrivateKey生成签名文件	 
//     * @param info 需要加密的明文，方便对照参考
//     */
//	 public static void signDataFile(String info){
//		    String result=getSignedData(info);
//			try {
//				writeFile(AppConstants.SignatureFileName,result);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//	 }
	 
//	 /**
//	     * 根据WebApp应用下的PrivateKey生成签名文件	 
//	     * @param info 需要加密的明文，方便对照参考
//	     */
//		 public static String getSignedData(String info){
//	        if (null==info||info.trim().equals("")){
//	        	return "" ;        	
//	        } 
//			String result=""; 
//			try {
//				String privateKey=readFile(AppConstants.PrivateFileName);
//				PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(hexStrToBytes(privateKey));
//				KeyFactory keyFactory;
//				keyFactory = KeyFactory.getInstance(AppConstants.QUEUE_VERIFY_ALGORITHM);
//				PrivateKey skey=keyFactory.generatePrivate(keySpec);
//				//String urlParam="userId=20000&visacenterId=10041";
//				String urlParam=info;
//				Signature sign=Signature.getInstance("MD5withRSA");
//				sign.initSign(skey);
//				sign.update(urlParam.getBytes("UTF-8"));
//				byte[] signedBytes=sign.sign();
//				String signedStr=bytesToHexStr(signedBytes);
//				result=signedStr;
//				} catch (InvalidKeyException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}catch (SignatureException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoSuchAlgorithmException e) {
//				    // TODO Auto-generated catch block
//				    e.printStackTrace();
//			    } catch (InvalidKeySpecException e) {
//				   // TODO Auto-generated catch block
//				   e.printStackTrace();
//			    } catch (Exception ex){
//			    	ex.printStackTrace();
//			    }
//			return result;
//		 }
//	 
	 /***
	 * 根据明文和签名文件，公匙验证传输是否安全 
	 * @param info  明文，可以访问的参数
	 * @param signatureStr 针对info生成的签名文件
	 * @return
	 */
//	 public static boolean validData(String info,String signatureStr){
//		 if (null==info||info.trim().equals("")){
//			 return false;
//		 }
//		 boolean f=false;
//		 try {
//			 String publicKey=readFile(AppConstants.PublicFileName);
//			 KeyFactory keyF=KeyFactory.getInstance(AppConstants.QUEUE_VERIFY_ALGORITHM);
//			 X509EncodedKeySpec  keySpec=new X509EncodedKeySpec(hexStrToBytes(publicKey));
//			 PublicKey pKey=keyF.generatePublic(keySpec); 
//			 //
//			 //String urlParam="userId=20000&visacenterId=10041";
//			 String urlParam=info;
//			 //String infoStr=readFile(AppConstants.SignatureFileName);
//			 //byte[] singed=hexStrToBytes("6C12C595CCB9406D190003DF80D19038115FFF091BDF81B6BF14550315B74F55183972E1942A286950CC26766D3F30B4531D38F4F679723B1EAD5AD0E4BAD3B1B0713237711EB43D9F649C3F8D6D65F5730F88604ECF0288E02BBCB6582548FDA95EE025994C71D747CF2D220ADFA429FD8A295137D49743A9A6D7B670434C8E");
//			 byte[] singed=hexStrToBytes(signatureStr);
//			 Signature sin=Signature.getInstance("MD5withRSA");
//			 sin.initVerify(pKey);
//			 sin.update(urlParam.getBytes());
//			 f=sin.verify(singed);
//			} catch (SignatureException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (InvalidKeyException e2) {
//				
//			} catch (InvalidKeySpecException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace(); 
//		    } catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		    }catch (Exception et){
//		    	et.printStackTrace();
//		    }
//		 System.out.println(f);
//		 return f;
//
//
//	 }

	    public static final String bytesToHexStr(byte[] bcd){
		   StringBuffer s = new StringBuffer(bcd.length * 2);
		   for (int i = 0; i <bcd.length; i++) {
			   s.append(bcdLookup[(bcd[i]>>>4)&0x0f]);
			   s.append(bcdLookup[bcd[i]& 0x0f]);
			}
		   return s.toString();
		}
	    public static final byte[] hexStrToBytes(String s) {
	    	byte[] bytes;
	    	bytes = new byte[s.length() / 2];
	    	for (int i = 0; i <bytes.length; i++) {
               bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),16);
	    	}   
            return bytes;
	    }   
	    private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	    
	    private static String readFile(String fileName) throws Exception {
		    String fileContent = "";
		    File f = new File(fileName);
		    FileReader fileReader = new FileReader(f);
		    BufferedReader reader = new BufferedReader(fileReader);
		    String line;
		    while((line=reader.readLine())!=null){
              fileContent+=line;
            }
		    reader.close();
		    return fileContent;
		  }
	    
	    private static void writeFile(String fileName,String fileContent) throws Exception {
	    	FileOutputStream of=new FileOutputStream(fileName);
	    	of.write(fileContent.getBytes());
	    	
	    }
	 

}
