package com.integral.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * List Utilities
 * 
 * @author Michael Leo
 * @since 2010/05/20
 */
public class ListUtils {
    private static Log log = LogFactory.getLog(ListUtils.class);
    private static final char SEPARATOR = '|';

    /**
     * Remove the duplicate element in List according to the specified keys in
     * List bean and return a new list.</br> If the parameters are empty or
     * exception occurred, original list will be returned.
     * 
     * @param list
     *            To be processed list
     * @param keys
     *            The fields in List bean as keys
     * @return
     */
    public static <T> List<T> removeDuplication(List<T> list, String... keys) {
        if (list == null || list.isEmpty()) {
            System.err.println("List is empty.");
            return list;
        }

        if (keys == null || keys.length < 1) {
            System.err.println("Missing parameters.");
            return list;
        }

        for (String key : keys) {
            if (StringUtils.isBlank(key)) {
                System.err.println("Key is empty.");
                return list;
            }
        }

        List<T> newList = new ArrayList<T>();
        Set<String> keySet = new HashSet<String>();

        for (T t : list) {
            StringBuffer logicKey = new StringBuffer();
            for (String keyField : keys) {
                try {
                    logicKey.append(BeanUtils.getProperty(t, keyField));
                    logicKey.append(SEPARATOR);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return list;
                }
            }
            if (!keySet.contains(logicKey.toString())) {
                keySet.add(logicKey.toString());
                newList.add(t);
            }
            else {
                log.info("Removing the duplicate element which has [" + logicKey + "] for key");
                //System.err.println(logicKey + " has duplicated.");
            }
        }
        return newList;
    }

    public static void main(String[] args) {
        List<TestBean> list = new ArrayList<TestBean>();

        TestBean tb1 = new TestBean();
        tb1.setField1(34);
        tb1.setField3("aaa");

        TestBean tb2 = new TestBean();
        tb2.setField1(344);
        tb2.setField3("ab");

        TestBean tb3 = new TestBean();
        tb3.setField1(3344);
        tb3.setField3("ab");

        TestBean tb4 = new TestBean();
        tb4.setField1(3344);
        tb4.setField3("ab");

        list.add(tb1);
        list.add(tb2);
        list.add(tb3);
        list.add(tb4);
        Date d1 = new Date();
        System.out.println(d1.getTime());
        List<TestBean> list2 = removeDuplication(list, "field1", "field3");
        Date d2 = new Date();
        System.out.println(d2.getTime());
        System.out.println(d2.getTime() - d1.getTime());
        for (TestBean b : list) {
            System.out.println(b);
        }
        System.out.println("---------------------");
        for (TestBean b : list2) {
            System.out.println(b);
        }
    }
}
