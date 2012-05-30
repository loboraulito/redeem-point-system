package com.integral.util;

/** 
 * <p>Description: [字符串公共工具类]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class StringUtils {

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param args
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public static void main(String[] args) {

    }
    /**
     * <p>Discription:[转换obj对象为字符串]</p>
     * @param obj obj对象
     * @param defaultString 当obj对象为null时，返回的默认字符串
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static String convertToString(Object obj, String defaultString){
        if(obj == null){
            return defaultString;
        }
        return obj.toString();
    }
    /**
     * <p>Discription:[把\符号转换成\\符号，避免转义字符]</p>
     * @param str
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static String convertChar(String str){
        if(str == null || "".equals(str.trim())){
            return "";
        }
        return str.replaceAll("\\\\", "\\\\\\\\");
    }
}
