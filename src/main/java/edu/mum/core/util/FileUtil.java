package edu.mum.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class provides APIs to manipulate files, such as readFile, writeFile,
 * mkdirs, delete, copy, etc. <br>
 * <br>
 * Example: <br>
 * 
 * <pre>
 *  FileUtil.createDir(&quot;/tashome/tmp/test&quot;);
 *  FileUtil.writeToFile(&quot;this is a test contenet&quot;, new File(&quot;/tashome/tmp/test/source.txt&quot;));
 *  Byte[] contentAry = FileUtil.readFile(&quot;/tashome/tmp/test/source.txt&quot;);
 *  FileUtil.WriteFile(&quot;/tashome/tmp/test/dest.txt&quot;, contentAry);
 *  FileUtil.copy(&quot;/tashome/tmp/test/source.txt&quot;, &tilde;/tashome/tmp/test/dest.txt&quot;, true);
 * </pre>
 */
public class FileUtil {
    
    /**
     * @deprecated
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
    
    private static Logger log = Logger.getLogger(FileUtil.class);
    
    private static byte[] tmpFileLock = new byte[0];
    
    /**
     * Read a file into byte array without encoding specified
     * 
     * @param filePath the file path.
     * @return the file content in bytes.
     */
    public static byte[] readFile(String filePath) throws Exception {
        File file = new File(filePath.trim());
        return readFile(file);
    }
    
    /**
     * Read a file into byte array without encoding specified
     * 
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] readFile(File file) throws Exception {
        byte[] data = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        try {
            log.debug("readFile: " + file.getAbsolutePath());
            fis = new FileInputStream(file);
            copyFile(fis, bos);
            data = bos.toByteArray();
        } catch (Exception e) {
            log.error("readFile failed", e);
            throw e;
        } finally {
            closeFis(fis);
            closeFos(bos);
        }
        return data;
    }
    
    /**
     * Read a file with encoding specified
     * 
     * @param filePath
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath, String encoding)
            throws Exception {
        return readFile(new File(filePath.trim()), encoding);
    }
    
    /**
     * Read a file with encoding specified
     * 
     * @param pFile
     * @param encoding
     * @return String
     * @throws Exception
     */
    public static String readFile(File file, String encoding) throws Exception {
        String content = "";
        if (file == null)
            return content;
        FileInputStream fis = null;
        BufferedReader br = null;
        String line = "";
        try {
            fis = new FileInputStream(file);
            if(StringUtils.isBlank(encoding)){
            	encoding = DEFAULT_ENCODING;
            }
            br = new BufferedReader(new InputStreamReader(fis, encoding));
            while (((line = br.readLine()) != null)) {
                content += line;
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            closeBr(br);
            closeFis(fis);
        }
        return content;
    }
    
    /**
     * Read a file from specific file path and encoding. Callback is provided to reduce
     * the chance of loading large file into memory. 
     * 
     * @param filePath
     * @param encoding
     * @param cb
     * @throws Exception
     */
    public static void readFile(String filePath, String encoding, FileReadCb cb) throws Exception{
        readFile(new File(filePath.trim()), encoding, cb);
    }
    
    /**
     * Read a file with specified encoding. Callback is provided to reduce
     * the chance of loading large file into memory. 
     * 
     * @param file
     * @param encoding
     * @param cb
     * @throws Exception
     */
    public static void readFile(File file, String encoding, FileReadCb cb) throws Exception {
        if (file == null) {
            throw new Exception("File not found");
        }
        FileInputStream fis = null;
        BufferedReader br = null;
        String line = "";
        try {
            fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis, encoding));
            while (((line = br.readLine()) != null)) {
                cb.process(line);    
            }
        } catch (Exception e) {
            log.error(e, e);
            throw e; 
        } finally {
            closeBr(br);
            closeFis(fis);
        }
    }
    
    /**
     * Writes byte[] content to the file
     * 
     * @param content
     * @param filePath
     * @throws Exception
     */
    public static void writeFile(byte[] content, String filePath)
            throws Exception {
        writeFile(content, new File(filePath.trim()));
    }
    
    /**
     * Writes byte[] content to the file
     * 
     * @param content a <code>byte[]</code> value
     * @param destFile a <code>File</code> value
     * @exception PushEngineException if an error occurs
     */
    public static void writeFile(byte[] content, File destFile)
            throws Exception {
        FileOutputStream fos = null;
        try {
            checkAndCreateParentDir(destFile);
            fos = new FileOutputStream(destFile);
            fos.write(content);
        } catch (IOException e) {
            log.error(e, e);
            throw e;
        } finally {
            closeFos(fos);
        }
    }
    
    /**
     * Writes String content to the file without encoding specified
     * 
     * @param content
     * @param filePath
     * @throws Exception
     */
    public static void writeFile(String content, String filePath)
            throws Exception {
        writeFile(content, new File(filePath));
    }
    
    /**
     * Writes String content to the file without encoding specified
     * 
     * @param content a <code>String</code> value
     * @param destFile a <code>File</code> value
     * @exception PushEngineException if an error occurs
     */
    public static void writeFile(String content, File destFile)
            throws Exception {
        BufferedWriter bw = null;
        FileOutputStream fos = null;
        try {
            checkAndCreateParentDir(destFile);
            fos = new FileOutputStream(destFile);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(content, 0, content.length());
        } catch (Exception e) {
            log.error(e, e);
            throw e;
        } finally {
            closeBw(bw);
            closeFos(fos);
        }
    }
    
    /**
     * Writes String content to the file with encoding
     * 
     * @param content
     * @param filePath
     * @param encoding
     * @throws Exception
     */
    public static void writeFile(String content, String filePath,
            String encoding) throws Exception {
        writeFile(content, new File(filePath.trim()), encoding);
    }
    
    /**
     * Writes String content to the file with encoding
     * 
     * @param content a <code>String</code> value
     * @param destFile a <code>File</code> value
     * @exception PushEngineException if an error occurs
     */
    public static void writeFile(String content, File destFile, String encoding)
            throws Exception {
        BufferedWriter bw = null;
        FileOutputStream fos = null;
        try {
            checkAndCreateParentDir(destFile);
            fos = new FileOutputStream(destFile);
            bw = new BufferedWriter(new OutputStreamWriter(fos, encoding));
            bw.write(content, 0, content.length());
        } catch (Exception e) {
            log.error(e, e);
            throw e;
        } finally {
            closeBw(bw);
            closeFos(fos);
        }
    }
    
    /**
     * This function writes a buffer of data into a file.
     * 
     * @deprecated call writeFile(byte[] content, String destFile)
     * @param path the directory where the file is stored.
     * @param file the name of the file.
     * @param data the file content.
     */
    public static String writeFile(String path, String file, byte[] data)
            throws Exception {
        mkdirs(false, path);
        
        StringBuffer sb = new StringBuffer();
        sb.append(File.separator);
        sb.append(path.trim());
        sb.append(File.separator);
        sb.append(file.trim());
        writeFile(sb.toString(), data);
        return sb.toString();
    }
    
    /**
     * This function writes a buffer of data into a file.
     * 
     * @deprecated
     * @param path the directory where the file is stored.
     * @param file the name of file.
     * @return the file content in bytes.
     */
    public static byte[] getFile(String path, String file) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(path.trim());
        // sb.append(File.separator);
        sb.append(file.trim());
        log.debug("file full path= " + sb.toString());
        return readFile(sb.toString());
    }
    
    /**
     * This function writes a buffer of data into a file.
     * 
     * @deprecated call writeFile(byte[] data, String destFile)
     * @param filePath the path name of file.
     * @param data the file content.
     */
    public static void writeFile(String filePath, byte[] data) throws Exception {
        FileOutputStream fos = null;
        try {
            log.debug("*** write File: ." + filePath);
            checkAndCreateParentDir(new File(filePath));
            fos = new FileOutputStream(filePath);
            fos.write(data);
        } catch (Exception e) {
            log.error("writeFile failed", e);
            throw e;
        } finally {
            closeFos(fos);
        }
    }
    
    /**
     * This function list the file names of files under the input directory with
     * names start with prefix.
     */
    public static String[] listFiles(String dir, String prefix)
            throws Exception {
        return listFiles(dir, prefix, true); 
    }
    
    public static String[] listFiles(String dir, String pattern, boolean prefix)
        throws Exception {
        File dirFile = new File(dir);
        List<String> list = new Vector<String>();
        String[] files = dirFile.list();
        for (int i = 0; i < files.length; i++) {
            boolean add = false; 
            if (prefix) {
                add = files[i].startsWith(pattern); 
            } else {
                add = files[i].endsWith(pattern);
            }
                
            if (add) {
                list.add(files[i]);
            }
        }
        files = new String[list.size()];
        list.toArray(files);
        return files;
    }
    
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }
    
    /**
     * This function creates a temporary directory in the specified dir with the
     * prefix.
     * 
     * @param dir the directory name where the tmp dir is to be created.
     * @param prefix the prefix of the tmp dir name.
     * @return the temporary directory file
     */
    public static File createTmpDir(String dir, String prefix) throws Exception {
        File dirFile = new File(dir);
        
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        
        File tempFile = File.createTempFile(prefix, "", dirFile);
        tempFile.delete();
        tempFile.mkdir();
        return tempFile;
    }
    
    /**
     * overload for copyDir with default overwrite parameter = true
     */
    public static boolean copyDir(File srcFile, File destFile) {
        return copyDir(srcFile, destFile, true);
    }
    
    /**
     * Copy one dir to the dest dir. It overwrites any files if present in dest
     * dir.
     * 
     * @param srcFile Source dir
     * @param destFile Destination dir
     * @return <code>boolean</code> true if and only if all files were copied
     * @throws IOException on error
     */
    public static boolean copyDir(File srcFile, File destFile, boolean overwrite) {
        log.debug("Copy srcFile[" + srcFile.getAbsolutePath()
                + "] to destFile[" + destFile.getAbsolutePath() + "] and"
                + " overwrite file if present");
        boolean result = false;
        if (srcFile.isDirectory()) {
            if (!destFile.isDirectory() && !destFile.mkdirs()) {
                return false;
            }
            // Now copy all files from source to dest
            File[] listFiles = srcFile.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                File curFile = listFiles[i];
                if (curFile.isDirectory()) {
                    result = copyDir(curFile, new File(destFile, curFile
                        .getName()), overwrite);
                } else {
                    result = copyFile(curFile,
                        new File(destFile, curFile.getName()), overwrite); // overwrite
                }
                if (result == false) {
                    // if error, then stop at this point
                    return result;
                }
            }
        }
        return result;
    }
    
    /**
     * rename to copyFile(File srcFile, File destFile, boolean overwrite)
     * 
     * @deprecated rename to copyFile(File srcFile, File destFile, boolean
     *             overwrite)
     * @param srcFile
     * @param destFile
     * @param overwrite
     * @return
     */
    public static boolean copy(File srcFile, File destFile, boolean overwrite) {
        return copyFile(srcFile, destFile, overwrite);
    }
    
    /**
     * Copy one file to the other with optionally overwriting the dest file
     * 
     * @param srcFile Source file
     * @param destFile Destination file
     * @param overwrite whether to overwrite the dest file if it exists
     * @return <code>boolean</code> true if and only if file was copied
     * @throws IOException on error
     */
    public static boolean copyFile(File srcFile, File destFile,
            boolean overwrite) {
        log.debug("Copy srcFile[" + srcFile.getAbsolutePath()
                + "] to destFile[" + destFile.getAbsolutePath() + "] and"
                + " overwrite[" + overwrite + "]");
        if (!srcFile.exists()) {
            log.debug("copyFile: Source file[" + srcFile + "] not found");
            return false;
        }
        if (!destFile.exists()) {
            checkAndCreateParentDir(destFile);
        } else {
            if (!overwrite) {
                log.debug("copyFile: overwrite == false");
                return false;
            }
        }
        FileInputStream fileIn = null;
        FileOutputStream fileOut = null;
        boolean success = false;
        try {
            fileIn = new FileInputStream(srcFile);
            fileOut = new FileOutputStream(destFile);
            success = copyFile(fileIn, fileOut);
        } catch (IOException e) {
            log.error(e, e);
            return false;
        } finally {
            closeFis(fileIn);
            closeFos(fileOut);
        }
        return success;
    }
    
    /**
     * Creates Directory for the given path.
     * 
     * @param pDirPath a <code>String</code> value
     * @exception SecurityException - If a security manager exists and its
     *                SecurityManager.checkRead(java.lang.String) method does
     *                not permit verification of the existence of the named
     *                directory and all necessary parent directories; or if the
     *                SecurityManager.checkWrite(java.lang.String) method does
     *                not permit the named directory and all necessary parent
     *                directories to be created
     */
    public static void createDir(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }
    
    /**
     * Remove the directory, include all the files and directories under the
     * directory
     * 
     * @param dir
     */
    public static void removeDir(String dir) {
        File f = new File(dir);
        removeDir(f);
    }

    public static void removeFile(String filePath) {
        File f = new File(filePath);
        f.delete();
    }
    
    /**
     * Remove the directory, include all the files and directories under the
     * directory
     * 
     * @param f
     */
    public static void removeDir(File f) {
        if (!f.exists())
            return;
        Stack<File> sk = new Stack<File>();
        sk.push(f);
        delete(sk);
    }
    
    private static void delete(Stack<File> sk) {
        while (!sk.empty()) {
            File ff = sk.pop();
            if (ff.delete())
                continue;
            sk.push(ff);
            File[] fs = ff.listFiles();
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].isDirectory())
                    sk.push(fs[i]);
                if (fs[i].isFile())
                    fs[i].delete();
            }
        }
    }
    
    /**
     * Remove all the files and directories under the directory
     * 
     * @param dir
     */
    public static void clearDir(String dir) {
        File f = new File(dir);
        clearDir(f);
    }
    
    /**
     * Remove all the files and directories under the directory
     * 
     * @param f
     */
    public static void clearDir(File f) {
        if (!f.exists())
            return;
        Stack<File> sk = new Stack<File>();
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            sk.push(files[i]);
        }
        delete(sk);
    }
    
    /**
     * If the parent of the file does not exists, the parent directory will be
     * created
     * 
     * @param file
     * @return true if parent directory exists or created successfully
     */
    public static boolean checkAndCreateParentDir(File file) {
        File parentFile = file.getParentFile();
        
        if (!parentFile.exists()) {
            return parentFile.mkdirs();
        }
        
        return true;
    }
    
    /**
     * call createDir(String dirPath)
     * 
     * @deprecated rename to createDir(String dirPath)
     * @param pDirPath
     * @throws IOException
     */
    public static void createDirectory(String pDirPath) throws IOException {
        File dirFile = new File(pDirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }
    
    /**
     * call writeFile(byte[] content, File destFild)
     * 
     * @deprecated rename to writeFile(byte[] content, File destFile)
     * @param content
     * @param contentFile
     * @throws Exception
     */
    public static void writeToFile(byte[] content, File contentFile)
            throws Exception {
        writeFile(content, contentFile);
    }
    
    /**
     * @deprecated rename to writeFile(String content, File destFile)
     * @param content
     * @param contentFile
     * @throws Exception
     */
    public static void writeToFile(String content, File contentFile)
            throws Exception {
        writeToFile(content, contentFile, DEFAULT_ENCODING);
    }
    
    /**
     * call writeFile(String content, File destFile, String encoding)
     * 
     * @deprecated rename to writeFile(String content, File contentFile, String
     *             encoding)
     * @param content
     * @param contentFile
     * @param encoding
     * @throws Exception
     */
    public static void writeToFile(String content, File contentFile,
            String encoding) throws Exception {
        writeFile(content, contentFile, encoding);
    }
    
    /**
     * Removes the content of specified directory.
     * 
     * @deprecated call removeDir(String dir)
     * @param pDirPath a <code>String</code> value
     */
    public static void removeDirectoryContent(String pDirPath) throws Exception {
        File dirFile = new File(pDirPath);
        if ((dirFile.exists()) && (dirFile.isDirectory())) {
            deleteTree(dirFile);
        }
    }
    
    /**
     * @deprecated
     * @param pFilePath
     * @return
     * @throws Exception
     */
    public static StringBuffer readFileContent(String pFilePath)
            throws Exception {
        File lFile = new File(pFilePath);
        return readFileContent(lFile);
    }
    
    /**
     * @deprecated
     * @param pFile
     * @return
     * @throws Exception
     */
    public static StringBuffer readFileContent(File pFile) throws Exception {
        return readFileContent(pFile, DEFAULT_ENCODING);
    }
    
    /**
     * @deprecated
     * @param pFile
     * @param encoding
     * @return
     * @throws Exception
     */
    public static StringBuffer readFileContent(File pFile, String encoding)
            throws Exception {
        if (pFile == null) {
            throw new Exception("File Object is null");
        }
        if (!pFile.exists()) {
            throw new Exception("File [" + pFile + "] does not exist");
        }
        try {
            FileInputStream inputStream = new FileInputStream(pFile);
            InputStreamReader streamReader = new InputStreamReader(inputStream,
                encoding);
            // FileReader lFileReader = new FileReader(pFile);
            BufferedReader lBufferedReader = new BufferedReader(streamReader);
            StringBuffer lStringBuffer = new StringBuffer();
            String lLine = null;
            while ((lLine = lBufferedReader.readLine()) != null) {
                lStringBuffer.append(lLine + "\n");
            }
            return lStringBuffer;
        } catch (IOException e) {
            log.error(e, e);
            throw new Exception("IOException while reading file content : "
                    + " [" + pFile + "]", e);
        }
    }
    
    /**
     * @deprecated
     * @param pFile
     * @return
     * @throws IOException
     */
    public static String getFileContent(File pFile) throws IOException {
        return getFileContent(pFile, DEFAULT_ENCODING);
    }
    
    /**
     * Read a file with encoding specified
     * 
     * @deprecated call readFile(File file, String encoding) throws Exception
     * @param pFile
     * @param encoding
     * @return String
     * @throws IOException
     */
    public static String getFileContent(File pFile, String encoding)
            throws IOException {
        String lContent = "";
        if (pFile == null)
            return lContent;
        FileInputStream fis = null;
        BufferedReader br = null;
        String lLineContent = "";
        try {
            fis = new FileInputStream(pFile);
            br = new BufferedReader(new InputStreamReader(fis, encoding));
            while (((lLineContent = br.readLine()) != null)) {
                lContent += lLineContent;
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            closeBr(br);
            closeFis(fis);
        }
        return lContent;
    }
    
    /**
     * @deprecated call readFile(File file)
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] getContentFromFile(File file) throws Exception {
        log.debug("getContentFromFile [" + file + "]");
        byte[] outdata = new byte[((int) file.length())];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(outdata);
        } catch (Exception e) {
            log.error(e, e);
            throw new Exception("Error occured in reading Chunked file", e);
        } finally {
            closeFis(fis);
        }
        return outdata;
    }
    
    /**
     * Removes the content of specified directory, but not the directory.
     * 
     * @deprecated call clearDir(String dir)
     * @param pDirPath a <code>String</code> value
     */
    public static void removeContents(String pDirPath) throws Exception {
        File dirFile = new File(pDirPath);
        if ((dirFile.exists()) && (dirFile.isDirectory())) {
            deleteContents(dirFile);
        }
    }
    
    /**
     * Delete the file or if it a directory, then delete recursively
     * 
     * @deprecated call clearDir(File f)
     */
    public static boolean deleteContents(File startDirFile) {
        log.debug("DeleteContents[" + startDirFile.getAbsolutePath() + "]");
        boolean result = true;
        if (startDirFile == null) {
            return false;
        }
        if (startDirFile.isDirectory()) {
            File[] listFiles = startDirFile.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                File curFile = listFiles[i];
                if (curFile.isDirectory()) {
                    result = deleteTree(curFile);
                } else {
                    result = curFile.delete();
                }
                if (result == false) {
                    // if error, then stop at this point
                    return result;
                }
            }
        }
        return result;
    }
    
    private static boolean copyFile(InputStream from, OutputStream to)
            throws IOException {
        BufferedInputStream bIn = new BufferedInputStream(from);
        BufferedOutputStream bOut = new BufferedOutputStream(to);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = bIn.read(b)) > 0) {
            bOut.write(b, 0, len);
        }
        bOut.flush();
        return true;
    }
    
    /**
     * Delete the file or if it a directory, then delete recursively
     * 
     * @deprecated call removeDir(File f)
     */
    public static boolean deleteTree(File startDirFile) {
        log.debug("DeleteTree[" + startDirFile.getAbsolutePath() + "]");
        
        boolean result = false;
        if (startDirFile == null) {
            return false;
        }
        if (startDirFile.isDirectory()) {
            File[] listFiles = startDirFile.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                File curFile = listFiles[i];
                if (curFile.isDirectory()) {
                    result = deleteTree(curFile);
                } else {
                    result = curFile.delete();
                }
                if (result == false) {
                    // if error, then stop at this point
                    return result;
                }
            }
        }
        
        // Now delete the current file/dir
        result = startDirFile.delete();
        return result;
    }
    
    private static void closeFis(InputStream fis) {
        if (fis == null)
            return;
        try {
            fis.close();
        } catch (Exception e) {
            // ignore
            log.error(e, e);
        }
    }
    
    private static void closeFos(OutputStream fos) {
        if (fos == null)
            return;
        try {
            fos.close();
        } catch (Exception e) {
            // ignore
            log.error(e, e);
        }
    }
    
    private static void closeBw(BufferedWriter bw) {
        if (bw == null)
            return;
        try {
            bw.close();
        } catch (Exception e) {
            log.error(e, e);
        }
    }
    
    private static void closeBr(BufferedReader br) {
        if (br == null)
            return;
        try {
            br.close();
        } catch (Exception e) {
            log.error(e, e);
        }
    }
    
    /**
     * call createDir(String dirPath)
     * 
     * @deprecated
     * @param override
     * @param path
     * @param dir
     */
    public static void mkdirs(boolean override, String path, String dir) {
        File file = new File(path, dir);
        if (override == true) {
            file.mkdirs();
        } else {
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
    }
    
    /**
     * call createDir(String dirPath)
     * 
     * @deprecated
     * @param override
     * @param path
     */
    public static void mkdirs(boolean override, String path) {
        File file = new File(path);
        if (override == true) {
            file.mkdirs();
        } else {
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
    }

	/**
	 * Returns whether the specified file exsits.
	 */
    public static boolean exists(String filePath) {
        File file = new File(filePath.trim());
		return file.exists();
	}
    
    /**
     * Returns total lines of specified file.
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static int countLines(String filePath) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(new File(
            filePath)));
        int lineCount = 0;
        char[] buffer = new char[4096];
        for (int charsRead = reader.read(buffer); charsRead >= 0; charsRead = reader
            .read(buffer)) {
            for (int charIndex = 0; charIndex < charsRead; charIndex++) {
                if (buffer[charIndex] == '\n') {
                    lineCount++;
                }
            }
        }
        reader.close();
        return lineCount;
    }
    
    /**
     * Returns total lines of specified file.
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    private int countLines(File file) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(file));
        int lineCount = 0;
        char[] buffer = new char[4096];
        for (int charsRead = reader.read(buffer); charsRead >= 0; charsRead = reader
            .read(buffer)) {
            for (int charIndex = 0; charIndex < charsRead; charIndex++) {
                if (buffer[charIndex] == '\n') {
                    lineCount++;
                }
            }
        }
        reader.close();
        return lineCount;
    }
    public static void main(String[] arg){
//    	FileUtil.copyDir(new File("C:\\workspace\\exp\\exported_data_ob"), new File("C:\\workspace\\exp\\2"));
    	String s="";
		try {
			s = new String(FileUtil.readFile("c:/biz.env.properties"));
		}
		catch (Exception e) {
			// TODO 
			e.printStackTrace();
		}
    	System.out.println(s);
    }
}
