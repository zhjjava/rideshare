/*
 * @(#)ZipFileUtil.java	2011-8-10����06:07:22
 * Copyright 2015  lc All rights reserved.
 */
package edu.mum.rideshare.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 

 * 
 * @author: mz
 * @version: 1.0 Date: 2011-8-10 ����06:07:22
 */
public class ZipFileUtil {
	private static Log log = LogFactory.getLog(ZipFileUtil.class);

	// public static void writeZipOutStream(File file, ZipOutputStream zout) throws Exception {
	// if (file == null) {
	// log.error("Expected valid file data.");
	// return;
	// }
	// if (file.isDirectory()) {
	// writeZipOutStream(file.listFiles(), zout);
	// }
	// else {
	// zout.putNextEntry(new ZipEntry(file.getName()));
	// zout.write(FileUtil.readFile(file));
	// }
	//
	// }
	//
	// public static void writeZipOutStream(File[] files, ZipOutputStream zout) throws Exception {
	// if (files == null) {
	// log.error("Expected valid files data.");
	// return;
	// }
	// // zout.putNextEntry(new ZipEntry(file.getName()));
	// // zout.write(FileUtil.readFile(file));
	// for (File file : files) {
	// writeZipOutStream(file, zout);
	// }
	// }
	//
	// public static void genZipFile(File sourceFile, OutputStream outStream) throws Exception {
	// // ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
	// if (sourceFile == null) {
	// return;
	// }
	// ZipOutputStream zout = null;
	// try {
	// zout = new ZipOutputStream(outStream);
	// writeZipOutStream(sourceFile, zout);
	// }
	// catch (Exception e) {
	// log.error("Generate zip file failed.", e);
	// throw e;
	// }
	// finally {
	// try {
	// zout.close();
	// }
	// catch (IOException e) {
	// // TODO
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// public static void main(String[] arg) {
	// FileOutputStream out = null;
	// try {
	// out = new FileOutputStream(new File("c:/cc.zip"));
	// ZipFileUtil.genZipFile(new File("C:/mmmm"), out);
	// }
	// catch (Exception e) {
	// // TODO
	// e.printStackTrace();
	// }
	// finally {
	// if (out != null) {
	// try {
	// out.close();
	// }
	// catch (IOException e) {
	// // TODO
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	private ZipFile zipFile;

	private ZipOutputStream zipOut; // 压缩Zip

	private ZipEntry zipEntry;

	private static int bufSize; // size of bytes

	private byte[] buf;

	private int readedBytes;

	public ZipFileUtil() {

		this(512);

	}

	public ZipFileUtil(int bufSize) {

		this.bufSize = bufSize;

		this.buf = new byte[this.bufSize];

	}

	// 压缩文件夹内的文件

	public void doZip(String zipDirectory,String outputFile) {// zipDirectoryPath:需要压缩的文件夹名

		File file;

		File zipDir;

		zipDir = new File(zipDirectory);

		String zipFileName = outputFile;// 压缩后生成的zip文件名

		try {

			this.zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));

			handleDir(zipDir, this.zipOut,zipDir.toString());
		}
		catch (Exception ioe) {
			ioe.printStackTrace();

		}
		finally {
			if (this.zipOut != null) {
				try {
					this.zipOut.close();
				}
				catch (IOException e) {
					// TODO
					e.printStackTrace();
				}
			}
		}

	}

	// 由doZip调用,递归完成目录文件读取

	private void handleDir(File dir, ZipOutputStream zipOut, String zipSourceRootDir) throws IOException {

		FileInputStream fileIn;

		File[] files;

		files = dir.listFiles();

		if (files==null || files.length == 0) {// 如果目录为空,则单独创建之.

//			// ZipEntry的isDirectory()方法中,目录以"/"结尾.
//
//			this.zipOut.putNextEntry(new ZipEntry(dir.toString() + "/"));
//
//			this.zipOut.closeEntry();

		}

		else {// 如果目录不为空,则分别处理目录和文件.

			for (File fileName : files) {

				System.out.println(fileName.getPath());

				if (fileName.isDirectory()) {

					handleDir(fileName, this.zipOut,zipSourceRootDir);

				}

				else {

					fileIn = new FileInputStream(fileName);
                    String fileNameShort = fileName.toString();
                    int lenOfPrefix =0;
                    if(zipSourceRootDir.endsWith("/") || zipSourceRootDir.endsWith("\\")){
                    	lenOfPrefix = zipSourceRootDir.length();
                    } else{
                    	lenOfPrefix = zipSourceRootDir.length()+1;
                    }
                    fileNameShort = fileNameShort.substring(lenOfPrefix);//need to ignore \\ or /, File.seperator
                    System.out.println("fileNameShort:"+fileNameShort);    
					this.zipOut.putNextEntry(new ZipEntry(fileNameShort));

					while ((this.readedBytes = fileIn.read(this.buf)) > 0) {

						this.zipOut.write(this.buf, 0, this.readedBytes);

					}

					this.zipOut.closeEntry();

				}

			}

		}

	}

	// 解压指定zip文件

	public void doUnzip(String unZipfileName,String unzipDir) {// unZipfileName需要解压的zip文件名
		FileOutputStream fileOut = null;
		File file;
		InputStream inputStream;
		try {

			this.zipFile = new ZipFile(unZipfileName);

			for (Enumeration entries = this.zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
				String name =entry.getName();
				//System.out.println("entry.getName():"+entry.getName());
				name = name.replaceAll("\\\\", "/");
				file = new File(unzipDir+"/"+name);
				//System.out.println("entry.getName2():"+name);
				if (entry.isDirectory()) {
					file.mkdirs();
					//System.out.println("dir:"+name);
				} else {
					// 如果指定文件的目录不存在,则创建之.
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					inputStream = zipFile.getInputStream(entry);
					fileOut = new FileOutputStream(file);
					while ((this.readedBytes = inputStream.read(this.buf)) > 0) {
						fileOut.write(this.buf, 0, this.readedBytes);
					}
					fileOut.close();
					inputStream.close();

				}

			}

			this.zipFile.close();

		}
		catch (IOException ioe) {

			ioe.printStackTrace();

		}
		finally {
			if (this.zipOut != null) {
				try {
					this.zipOut.close();
				}
				catch (IOException e) {
					// TODO
					e.printStackTrace();
				}
			}
			if (fileOut != null) {
				try {
					fileOut.close();
				}
				catch (IOException e) {
					// TODO
					e.printStackTrace();
				}
			}
		}

	}

	// 设置缓冲区大小

	public void setBufSize(int bufSize) {

		this.bufSize = bufSize;

	}


	public static void main(String[] args) throws Exception {

		// if (args.length == 2) {
		//
		// String name = args[1];

		ZipFileUtil zip = new ZipFileUtil();

		// if (args[0].equals("-zip"))

//		 zip.doZip("C:\\exported_data_ob1","c:\\123.zip");

		// else if (args[0].equals("-unzip"))

		zip.doUnzip("c:\\exported_data_bj.zip","c:\\exported_data_ob1");

		// }

		// else {
		//
		// System.out.println("Usage:");
		//
		// System.out.println("压缩:java ZipFileUtil -zip directoryName");
		//
		// System.out.println("解压:java ZipFileUtil -unzip fileName.zip");
		//
		// throw new Exception("Arguments error!");
		//
		// }

	}

}
