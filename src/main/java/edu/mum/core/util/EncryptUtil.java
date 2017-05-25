
package edu.mum.core.util;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * <b>Description</b>
 * </p>
 * EncryptUtil Class This is used to encode passwords programmatically
 * 
 */
public class EncryptUtil {
    private static final Log log = LogFactory.getLog(EncryptUtil.class);
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    private static byte[] hexStringToByte(String hexString) {
        int length = hexString.length() / 2;
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            int tempInt = Integer.parseInt(hexString
                    .substring(i * 2, i * 2 + 2), 16);
            b[i] = (byte) tempInt;
        }
        return b;
    }
    private static final String Algorithm = "DESede";
    // 24字节的密钥
    private static final byte[] keyBytes = {
    (byte) 0xAF, (byte) 0x22, (byte) 0x4F, (byte) 0x58,
    (byte) 0xDE, (byte) 0x10, (byte) 0x40, (byte) 0xBB,
    (byte) 0x28, (byte) 0x25, (byte) 0x79, (byte) 0x51,
    (byte) 0xCB, (byte) 0xEF, (byte) 0xAB, (byte) 0x66,
    (byte) 0xBE, (byte) 0x29, (byte) 0x7C, (byte) 0xDD,
    (byte) 0x30, (byte) 0x40, (byte) 0x36, (byte) 0xE2
    };

    public static String encrypt(String inStr) {
        StringBuffer inoutStr = new StringBuffer(inStr);
        inoutStr.delete(0, inoutStr.length());
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] digest = c1.doFinal(inStr.getBytes());
            inoutStr.append(byte2hex(digest));
        } catch (Exception e) {
            log.error("",e);
            return "error.encrypt";
        }
        return inoutStr.toString();
    }

    public static String decrypt(String inStr) {
        StringBuffer inoutStr = new StringBuffer(inStr);
        inoutStr.delete(0, inoutStr.length());
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
            // 解密
            Cipher cliper = Cipher.getInstance(Algorithm);
            cliper.init(Cipher.DECRYPT_MODE, deskey);
            byte[] digest = cliper.doFinal(hexStringToByte(inStr));
            inoutStr.append(new String(digest));
        } catch (Exception e) {
            log.error("",e);
            return "error.encrypt";
        }

        return inoutStr.toString();

    }
    
      /**
       * 获取随机密码
       * @param accountBuf StringBuffer
       * @return String
       */
      public static String getARandomPassword() {       
        StringBuffer accountBuf = new StringBuffer();
        int pwdLength = 8;
        String pwdchars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int iRandNum;      
        Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < pwdLength; i++) {
          iRandNum = rnd.nextInt(pwdchars.length());
          accountBuf.append(pwdchars.charAt(iRandNum));
        }
        return accountBuf.toString();
      }
      
      /**
       * 123456加密后为766C6BEDDE90663C
       * @param args
       */
      public static void main(String[] args) {
          System.out.println(getARandomPassword());
          System.out.println(getARandomPassword());
          String oo =getARandomPassword();
          System.out.println("123456 encrypted:"+encrypt("123456"));
          System.out.println("oooo:"+oo);
          String s =encrypt(oo);         
          System.out.println("encrypt:" +s);         
          System.out.println("decrypt:" +decrypt(s));         
      }
}
