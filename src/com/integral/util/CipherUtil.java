package com.integral.util;

import java.security.MessageDigest;

/**
 * MD5加密密码以及密码校验的类
 * 
 * @author swpigris81@126.com
 */
public class CipherUtil {
    // 十六进制下数字到字符的映射数组
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"                       };

    /**
     * 加密字符串
     * 
     * @author swpigris81@126.com Description:
     * @param inputString
     * @return
     */
    public static String generatePassword(String inputString) {
        return encodeByMD5(inputString);
    }

    /**
     * 验证密码是否正确
     * 
     * @author swpigris81@126.com Description:
     * @param password
     * @param inputString
     * @return
     */
    public static boolean validatePassword(String password, String inputString) {
        if (password.equals(encodeByMD5(inputString))) {
            return true;
        }
        return false;
    }

    /**
     * 加密算法
     * 
     * @author swpigris81@126.com Description:
     * @param originString
     * @return
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * 转换字节数组为十六进制字符串
     * @author swpigris81@126.com
     * Description:
     * @param b 字节数组
     * @return
     */
    private static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    /**
     *  将一个字节转化成十六进制形式的字符串
     * @author swpigris81@126.com
     * Description:
     * @param b
     * @return
     */
    private static String byteToHexString(byte b){
        int n = b;
        if (n < 0){
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    public static void main(String [] args){
        String name = "user{user}";
        System.out.println(encodeByMD5(name));
    }
}
