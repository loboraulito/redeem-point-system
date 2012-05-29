package com.integral.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * <p>Description: [文件工具类]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FileUtil {
    private static Log log = LogFactory.getLog(FileUtil.class);
    private static final int BUFFER_SIZE = 16 * 1024;
    /**
     * 使用Java NIO写大文件
     * @param file
     * @param data
     * @throws IOException
     */
    public static void writeBigFile(File file, String data) throws IOException{
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fcout = raf.getChannel();
        ByteBuffer wBuffer = ByteBuffer.allocateDirect(1024*1024);
        try{
            raf.seek(raf.length());
            fcout.write(wBuffer.wrap(data.getBytes()), fcout.size());
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }finally{
            wBuffer.flip();
            wBuffer.compact();
            fcout.close();
            raf.close();
        }
    }
    
    /**
     * 直接读取文件以获取文件
     * 
     * @param filePath：文件路径
     * @return 文件类容
     * @throws IOException
     */
    public static String readFileByFileReader(String filePath)
            throws IOException {
        String content = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new FileReader(decodeFilePath(filePath)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (FileNotFoundException e) {
            // 文件不存在
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            // 文件读取错误
            e.printStackTrace();
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 文件流关闭错误
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return content;
    }

    /**
     * 将文件转成一个流，读取这个流，获取文件
     * 
     * @param filePath：文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readFileByFileInputStream(String filePath)
            throws IOException {
        String content = "";
        BufferedReader reader = null;
        File file = new File(decodeFilePath(filePath));
        try {
            // 指定读取文件流的格式
            InputStreamReader in = new InputStreamReader(new FileInputStream(
                    file), "UTF-8");
            reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (FileNotFoundException e) {
            // 文件不存在
            e.printStackTrace();
            throw e;
        } catch (UnsupportedEncodingException e) {
            // 文件流格式转化错误
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            // 文件读取错误
            e.printStackTrace();
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 读取流的关闭异常
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    /**
     * 基于字符流写文件 wirteFileByBufferedWriter
     * 
     * @param filePath：文件路径
     * @param content：要写的文件内容
     */
    public static void wirteFileByBufferedWriter(String filePath, String content) {
        File file = new File(decodeFilePath(filePath));
        if (file.exists()) {
            log.info("文件已存在");
        } else {
            log.info("文件不存在，需要创建新文件");
            try {
                if (file.createNewFile()) {
                    log.info("创建新文件成功");
                } else {
                    log.info("创建新文件失败");
                }
            } catch (IOException e) {
                // 创建文件异常
                e.printStackTrace();
            }
        }
        BufferedWriter output = null;
        try {
            // 是否是追加写文件，false：重写（默认），true：追加
            output = new BufferedWriter(new FileWriter(file, false));
            output.write(content);
            log.info("文件写入完成");
        } catch (IOException e) {
            // 输出流异常
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // 输出流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 基于字节流写文件 writeFileByBufferedOutputStream
     * 
     * @param filePath：写入文件名
     * @param content：写入内容
     * @throws IOException
     */
    public static void writeFileByBufferedOutputStream(String filePath,
            String content) throws IOException {
        // BufferedOutputStream output = null;
        OutputStreamWriter output = null;
        File file = new File(decodeFilePath(filePath));
        if (file.exists()) {
            log.info("文件已存在");
        } else {
            log.info("文件不存在，需要创建新文件");
            try {
                if (file.createNewFile()) {
                    log.info("创建新文件成功");
                } else {
                    log.info("创建新文件失败");
                }
            } catch (IOException e) {
                // 文件创建异常
                e.printStackTrace();
                throw e;
            }
        }
        try {
            // 是否重写文件，true：追加、false：重写（默认）
            // output = new BufferedOutputStream(new
            // FileOutputStream(file,false));
            // 注意utf-8的编码，若不编码，那么写出的文件中存在中文的话，将变成乱码
            output = new OutputStreamWriter(new FileOutputStream(file, false),
                    "utf-8");
            output.write(content);
            output.flush();
            log.info("文件写入完成");
        } catch (FileNotFoundException e) {
            // 文件不存在
            e.printStackTrace();
        } catch (IOException e) {
            // 文件写错误
            e.printStackTrace();
            throw e;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // 关闭写入流错误
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制src文件到dst目标文件，带编码以及缓冲池
     * 
     * @author swpigris81@126.com Description:
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copyFile(File src, File dst) throws Exception {
        try {
            String content = readFileByFileInputStream(src.getPath());
            //InputStream in = null;
            //OutputStream out = null;
            InputStreamReader in = null;
            OutputStreamWriter out = null;
            try {
                /*
                byte[] buffer = new byte[BUFFER_SIZE];
                in = new BufferedInputStream(new FileInputStream(src),
                        BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst),
                        BUFFER_SIZE);
                */
                in = new InputStreamReader(new FileInputStream(
                        src), "UTF-8");
                out = new OutputStreamWriter(new FileOutputStream(dst),"UTF-8");
                char[] buffer = new char[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 使用commons-io包里面的复制文件的方法
     * @author swpigris81@126.com
     * Description:
     * @param srcFile：源文件
     * @param destDir：目标文件夹
     * @throws IOException 
     */
    public static void copyFileCommonIo(File srcFile, File destDir) throws IOException{
        FileUtils.copyFile(srcFile, destDir);
    }
    /**
     * 复制目录-common-io
     * @author swpigris81@126.com
     * Description:
     * @param srcDir：源目录
     * @param destDir：目标目录
     * @throws IOException
     */
    public static void copyDirectory(File srcDir,File destDir) throws IOException{
        FileUtils.copyDirectoryToDirectory(srcDir, destDir);
    }
    /**
     * 移动目录-common-io
     * @author swpigris81@126.com
     * Description:
     * @param srcDir:源目录
     * @param destDir：目标目录
     * @throws IOException
     */
    public static void moveDirectory(File srcDir,File destDir) throws IOException{
        FileUtils.moveDirectoryToDirectory(srcDir, destDir, true);
    }
    
    /**
     * 移动文件-common-io
     * @author swpigris81@126.com
     * Description:
     * @param srcFile：源文件
     * @param destDir：目标目录
     * @throws IOException
     */
    public static void moveFile(File srcFile,File destDir) throws IOException{
        FileUtils.moveFileToDirectory(srcFile, destDir, true);
    }

    /**
     * 复制文件到指定目录
     * 
     * @author swpigris81@126.com Description:
     * @param src
     * @param dstPath：目标文件的目录地址
     * @throws Exception
     */
    public static void copyFile(File src, String dstPath) throws Exception {
        File file = new File(dstPath);
        copyFile(src, file);
    }

    /**
     * 读取文件成为一个流
     * 
     * @author swpigris81@126.com Description:
     * @param file
     * @return
     */
    public static InputStream getFileInputStream(File file) {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return in;
    }

    /**
     * 删除文件的操作
     * 
     * @author swpigris81@126.com Description:
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(decodeFilePath(filePath));
        if (file.exists()) {
            file.delete();
        }
    }
    
    /**
     * 对文件的路径进行解码工作。返回解码之后的文件路径
     * 
     * @author swpigris81@126.com Description:
     * @param filePath
     * @return
     */
    public static String decodeFilePath(String filePath) {
        if(filePath!=null){
            filePath = filePath.replace("/", "\\");
        }
        try {
            return URLDecoder.decode(filePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 异常情况的话，返回原始路径
            return filePath;
        }
    }
    
    /**
     * 对文件的路径进行解码工作。返回解码之后的文件路径
     * 
     * @author swpigris81@126.com Description:
     * @param filePath
     * @return
     */
    public static String encodeFilePath(String filePath) {
        try {
            return URLEncoder.encode(filePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 异常情况的话，返回原始路径
            return filePath;
        }
    }

    /**
     * 格式化文件大小
     * 
     * @author swpigris81@126.com Description:
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        BigDecimal flagSize = new BigDecimal("1024");
        BigDecimal filesize = new BigDecimal(size + "");
        filesize = filesize.divide(flagSize, 1, RoundingMode.UP);
        if (filesize.compareTo(flagSize) == -1) {
            return filesize.toString() + "KB";
        }
        filesize = filesize.divide(flagSize, 2, RoundingMode.HALF_UP);
        if (filesize.compareTo(flagSize) == -1) {
            return filesize.toString() + "MB";
        }
        filesize = filesize.divide(flagSize, 2, RoundingMode.HALF_UP);
        if (filesize.compareTo(flagSize) == -1) {
            return filesize.toString() + "GB";
        }
        filesize = filesize.divide(flagSize, 2, RoundingMode.HALF_UP);
        if (filesize.compareTo(flagSize) == -1) {
            return filesize.toString() + "TB";
        }
        filesize = filesize.divide(flagSize, 2, RoundingMode.HALF_UP);
        if (filesize.compareTo(flagSize) == -1) {
            return filesize.toString() + "PB";
        }
        return size + "";
    }
}
