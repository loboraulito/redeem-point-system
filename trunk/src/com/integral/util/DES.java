package com.integral.util;

/**
 * @author tianya 简单的DES加密解密算法
 */
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * DES加密的，文件中共有两个方法,加密、解密
 */
public class DES {
    private String       Algorithm = "DES";
    private KeyGenerator keygen;
    private SecretKey    deskey;
    private Cipher       cipher;
    private byte[]       encryptorData;
    private byte[]       decryptorData;

    /**
     * 初始化 DES 实例
     */
    public DES() {
        init();
    }

    /**
     * 初始化 DES 加密算法的一些参数
     */
    public void init() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        try {
            keygen = KeyGenerator.getInstance(Algorithm);
            byte key[] = "12312345".getBytes();
            deskey = new SecretKeySpec(key, "DES");
            // deskey = keygen.generateKey();
            for (int i = 0; i < deskey.getEncoded().length; i++) {
                // System.out.println(deskey.getEncoded()[i]);
            }
            cipher = Cipher.getInstance(Algorithm);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 对 byte[] 进行加密
     * 
     * @param datasource
     *                要加密的数据
     * @return 返回加密后的 byte 数组
     */
    public byte[] createEncryptor(byte[] datasource) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            encryptorData = cipher.doFinal(datasource);
        } catch (java.security.InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (javax.crypto.BadPaddingException ex) {
            ex.printStackTrace();
        } catch (javax.crypto.IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
        return encryptorData;
    }

    /**
     * 将字符串加密
     * 
     * @param datasource
     * @return
     * @throws Exception
     */
    public byte[] createEncryptor(String datasource) throws Exception {
        return createEncryptor(datasource.getBytes());
    }

    /**
     * 对 datasource 数组进行解密
     * 
     * @param datasource
     *                要解密的数据
     * @return 返回加密后的 byte[]
     */
    public byte[] createDecryptor(byte[] datasource) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            decryptorData = cipher.doFinal(datasource);
        } catch (java.security.InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (javax.crypto.BadPaddingException ex) {
            ex.printStackTrace();
        } catch (javax.crypto.IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
        return decryptorData;
    }

    /**
     * 将 DES 加密过的 byte数组转换为字符串
     * 
     * @param dataByte
     * @return
     */
    public String byteToString(byte[] dataByte) {
        String returnStr = null;
        //BASE64Encoder be = new BASE64Encoder();
        //returnStr = be.encode(dataByte);
        returnStr = Base64Coder.encodeLines(dataByte);
        return returnStr;
    }

    /**
     * 将字符串转换为DES算法可以解密的byte数组
     * 
     * @param dataByte
     * @return
     * @throws Exception
     */
    public byte[] stringToByte(String datasource) throws Exception {
        //BASE64Decoder bd = new BASE64Decoder();
        //byte[] sorData = bd.decodeBuffer(datasource);
        
        byte[] sorData = Base64Coder.decodeLines(datasource);
        return sorData;
    }

    /**
     * 输出 byte数组
     * 
     * @param data
     */
    public void printByte(byte[] data) {
        // System.out.println("*********开始输出字节流**********");
        // System.out.println("字节流: "+data.toString());
        for (int i = 0; i < data.length; i++) {
            // System.out.println("第 "+i+"字节为："+ data[i]);
        }
        // System.out.println("*********结束输出字节流**********");
    }

    public static void main(String args[]) throws Exception {
        // 加密源数据
        String encryptorString = "QUbltI4Gmm";

        DES des = new DES();

        // 加密获得的byte数组
        byte[] encryptorByte = des.createEncryptor(encryptorString);

        // 加密后的byte[] 转换来的字符串
        String byteToString = des.byteToString(encryptorByte);

        System.out.println("加密前的数据：" + encryptorString);
        System.out.println("加密后的byte[]");
        des.printByte(encryptorByte);
        System.out.println("加密后的数据：" + byteToString);

        /*
         * 可以对字符串进行一系列的处理
         */

        // 解密后的字符串
        String decryptorString = null;

        // 将byteToString转换为原来的byte[]
        byte[] stringToByte = des.stringToByte(byteToString);
        // 将stringToByte解密后的byte[]
        byte[] decryptorByte = des.createDecryptor(stringToByte);
        System.out.println("解密后" + des.byteToString(decryptorByte));
        // 解密后的byte[]转换为原来的字符串
        decryptorString = new String(decryptorByte);

        System.out.println("解密前的数据：" + byteToString);
        System.out.println("转换来的解密的byte[]");
        des.printByte(stringToByte);
        System.out.println("解密后的数据：" + decryptorString);
    }
}
