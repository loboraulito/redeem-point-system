package com.integral.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/** 
 * <p>Description: [文件工具类]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FileUtil {
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
}
