package com.integral.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 转换请求数据
 * 
 */
public class RequestUtil {
    /**
     * 获取请求参数，将参数Map中只有一个元素的数组转成那个元素对应的值。
     * 去掉以method为名称的参数
     * @param request
     *            HttpServletRequest
     * @return 转换后的参数Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRequestMap(HttpServletRequest request) {
        Map<String, Object[]> srcMap = request.getParameterMap();
        Map<String, Object> destMap = new HashMap<String, Object>();
        Set keySet = srcMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if("method".equals(key)){
                continue;
            }
            Object[] objs = srcMap.get(key);
            Object value = objs;
            if (objs.length == 1) {
                value = objs[0];
            }
            destMap.put(key, value);
        }
        return destMap;
    }

    /**
     * 将字符串按pattern格式转为日期
     * 
     * @param source
     *            待转换的字符串
     * @param pattern
     *            日期格式
     * @return 转换后的日期
     * @throws ParseException
     */
    public static Date parseDate(String source, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        if (source == null || source.trim().length() <= 0) {
            return null;
        }
        return format.parse(source);
    }
    /**
     * 将字符串形式的日期按pattern格式转为日期形式
     * 
     * @param source
     *            待转换的字符串
     * @param pattern
     *            日期格式
     * @return 转换后的日期
     * @throws ParseException
     */
    public static Date parseStirngToDate(String source, String pattern) throws ParseException {
        DateFormat format = new SimpleDateFormat(pattern);
        if (source == null || source.trim().length() <= 0) {
            return null;
        }
        return format.parse(source);
    }

    /**
     * 将map中key对应的值用value替代，如果map中没有key对应的值，增加key-value
     * 
     * @param map
     *            需要替换key-value对的Map
     * @param key
     *            被替换值的key
     * @param value
     *            替换值
     */
    public static void replaceValue(Map<String, Object> map, String key, Object value) {
        map.remove(key);
        map.put(key, value);
    }

    /**
     * 将map中的字符串日期按提供的日期格式替换为Date
     * 
     * @param map
     *            需要替换key-value对的Map
     * @param key
     *            被替换值的key
     * @param pattern
     *            日期格式
     * @throws ParseException
     */
    public static void repalceAsDate(Map<String, Object> map, String key, String pattern) throws ParseException {
        //Date date = parseDate((String) map.get(key), pattern);
        if(!(map.get(key) instanceof Date)){
            Date date = Tools.StringToDate((String) map.get(key), pattern);
            //Date date = parseStirngToDate((String) map.get(key), pattern);
            replaceValue(map, key, date);
        }
    }
    
    /**
     * 将map中的字符串日期按提供的日期格式替换成String类型的日期
     * 
     * @param map
     *            需要替换key-value对的Map
     * @param key
     *            被替换值的key
     * @param pattern
     *            日期格式
     * @throws ParseException
     */
    public static void repalceDateAsString(Map<String, Object> map, String key, String pattern) throws ParseException {
        //Date date = parseDate((String) map.get(key), pattern);
        Date date = null;
        if(!(map.get(key) instanceof Date)){
            date = Tools.StringToDate((String) map.get(key), pattern);
        }else{
            date = (Date) map.get(key);
        }
        String d = Tools.dateToString(date, pattern);
        //Date date = parseStirngToDate((String) map.get(key), pattern);
        replaceValue(map, key, d);
    }
    
    /**
     * 将map中，值为“”或者是null的key删除。
     * @author swpigris81@126.com
     * Description:
     * @param map 被替换的map
     */
    public static void replaceNull(Map<String, Object> map){
        Set keySet = map.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object obj = map.get(key);
            if(obj==null || "".equals(obj)){
                it.remove();
                //要使下面的这句话正常运行，那么在迭代的时候，上面的这句话就不可少，否则报异常
                map.remove(key);
            }
        }
    }
    /**
     * <p>Discription:[解析url]</p>
     * @param url:/redeempoint/namespace/actionName.action?method=method
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Map<String, String> getRequestUrls(String url){
        Map<String, String> urlMap = new HashMap<String, String>();
        if(url == null || "".equals(url.trim())){
            return urlMap;
        }
        if(url.startsWith("/redeempoint")){
            url = url.substring("/redeempoint".length());
        }
        if(url.startsWith("/") && url.length() > 1){
            url = url.substring(1);
        }
        String [] urls = url.split("/");
        String namespace = "";
        if(urls != null && urls.length > 0){
            namespace = urls[0];
        }
        String actionName = "";
        String method = "";
        if(urls.length > 1){
            String u = urls[1];
            if(u != null && u.length() > 0){
                String us[] = u.split("\\?");
                if(us != null && us.length > 0){
                    actionName = us[0];
                    actionName = actionName.substring(0, actionName.lastIndexOf(".action"));
                }
                if(us != null && us.length > 1){
                    method = us[1];
                    method = method.substring("method=".length());
                }
            }
        }
        urlMap.put("namespace", "/" + namespace);
        urlMap.put("actionName", actionName);
        urlMap.put("method", method);
        return urlMap;
    }
}
