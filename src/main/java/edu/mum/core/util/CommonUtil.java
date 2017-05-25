package edu.mum.core.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.mum.core.exception.SystemException;

/**
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Michael.zhou
 * @version 1.0
 */

public class CommonUtil {
	private static Log log = LogFactory.getLog("CommonUtil.class");
	public static String APP_DIR = "";
	public static String WEBINF_DIR = "";
    public static final int DEFAULT_BUFFER_SIZE = 10 * 1024;
	public CommonUtil() {
	}

	public static void deleteFile(String imagePath) {
		log.debug("imagePath:" + imagePath);
		try {
			if (imagePath != null) {
				File file = new File(imagePath);
				if (file != null && file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			log.error("Fail to delete file!", e);
		}
	}

	public static void deleteDirectory(String path) {
		log.debug("path:" + path);
		try {
			if (path != null) {
				File dir = new File(path);
				if (dir != null && dir.exists()) {
					File files[] = dir.listFiles();
					for (int i = 0; i < files.length; i++) {
						files[i].delete();
					}
					dir.delete();
				}
			}
		} catch (Exception e) {
			log.error("Fail to delete directory!", e);
		}
	}

	public static File[] listFile(String path) {
		log.debug("path:" + path);
		if (path != null) {
			File dir = new File(path);
			if (dir != null && dir.exists()) {
				return dir.listFiles();
			}
		}
		return null;
	}
 
	public static void main(String[] args) {
		File fileBuf[] = listFile("content");
		for(int i = 0;i< fileBuf.length;i++){
			System.out.println(fileBuf[i]);
		}
//		System.out.println(fileBuf.length);
	}
	
	public static void copyAllFilesToDir(String fromDir, String toDir) throws SystemException{
		log.debug("fromDir " + fromDir);
		log.debug("toDir : " + toDir);
		File files[] = listFile(fromDir);
		FileInputStream in = null;
		FileOutputStream out = null;	
		try {
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					in = new FileInputStream(files[i]);
					byte outByte[] = new byte[in.available()];
					in.read(outByte);
					File dest = null;
					dest = new File(toDir + File.separator + files[i].getName());
					if (!dest.exists())
						dest.createNewFile();
					out = new FileOutputStream(dest);
					out.write(outByte);
					out.flush();
					in.close();
					out.close();
				}
			}
		} catch (Exception e) {
			log.error("Fail to delete file!", e);
			throw new SystemException("copyAllFilesToDir error:" + e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				log.error("关闭文件流失败!", ioe);
				throw new SystemException("copyAllFilesToDir error:" + ioe.getMessage());
			}
		}
	}

	public static void writeBinFile(String saveDirPath, String fileName,
			byte[] data) throws SystemException {
		log.debug("uploadFile begin!");
		FileOutputStream fileOutput = null;
		BufferedOutputStream bfOutputStream = null;
		try {
			File tempFile = new File(saveDirPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			tempFile = null;
			String fileSeparator = "";
			if (!saveDirPath.endsWith(File.separator)) {
				fileSeparator = File.separator;
			}
			StringBuffer fileNameFullPath = new StringBuffer(saveDirPath)
					.append(fileSeparator).append(fileName);
			tempFile = new File(fileNameFullPath.toString());
			if (tempFile.exists()) {
				tempFile.delete();
			}
			tempFile = null;
			fileOutput = new FileOutputStream(fileNameFullPath.toString());
			bfOutputStream = new BufferedOutputStream(fileOutput, DEFAULT_BUFFER_SIZE);
			bfOutputStream.write(data);
			bfOutputStream.flush();
			bfOutputStream.close();
			fileOutput.flush();
			fileOutput.close();
			fileOutput = null;
			bfOutputStream = null;
		} catch (Exception e) {
			log.error("failed!", e);
			throw new SystemException("写文件失败:" + e.getMessage());
		} finally {
			try {
				if (fileOutput != null) {
					fileOutput.close();
				}
				if (bfOutputStream != null) {
					bfOutputStream.close();
				}
			} catch (IOException ioe) {
				log.error("关闭文件流失败!", ioe);
				throw new SystemException("写文件失败:" + ioe.getMessage());
			}
		}
		log.debug("writeBinFile success!");
		log.debug("writeBinFile end!");
	}

	public static void mkdirs(boolean override, String path, String dir) {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		} 
		dir = path + dir;
		log.debug("mkdirs:" + dir);
		File file = new File(dir);
		if (override == true) {
			file.mkdirs();
		} else {
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
		}
	}

	/**
	 * format data
	 * 
	 * @param str
	 * @param i
	 * @return
	 */
	public static String replicate(String str, int i) {
		if (str != null && str.length() < i) {
			while (str.length() < i) {
				str = "0" + str;
			}
		}
		return str;
	}
	public static String getMaxString(String pattern,int number){
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		String code = df.format(number);
		return code;
	}
	public static String getString(String oriStr, String oriCharset, String newCharset){
		log.debug("oriString:" + oriStr);
		try {
			oriStr = new String(oriStr.getBytes(oriCharset),newCharset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("encoding failed.",e);
		}
		return oriStr;
	}
	public static byte[] inputStream2Bytes(InputStream in) {
		ByteArrayOutputStream fos = null;
		byte[] rtnByteArr = null;
 		try {

			byte[] b = new byte[DEFAULT_BUFFER_SIZE];
			int borb = -1;
			fos = new ByteArrayOutputStream();
			while ((borb = in.read(b)) != -1) {
			        fos.write(b, 0, borb);
			}
			rtnByteArr = fos.toByteArray();
		}
		catch (Exception e) {
			log.error("",e);
		}finally{
			if(fos!=null){
				try {
					fos.close();
				}
				catch (IOException e) {
					log.error("fos.close error.",e);
				}
			}
		}
		return rtnByteArr;
    }
	public static String readFile(String filePath) {
		if(filePath==null ||filePath.trim().length()==0){
			log.error("File name should not be null.");
			return null;
		}
		File file = new File(filePath);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		String text = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			log.error("read file failed.",e);
		} catch (IOException e) {
			log.error("read file failed.",e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				log.error("close reader failed.",e);
			}
		}
		return contents.toString();
	}
}
