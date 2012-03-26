package com.integral.util;

import java.io.IOException;
import java.util.Arrays;

public class Test {

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param args
     * @author:[代超]
     * @throws IOException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public static void main(String[] args) throws IOException {
        //String a = "abc";
        //String b = "abc";
        System.out.println(get());
        
        int[] x = new int[6];
        
        //Arrays.fill(x, 1);
        /*
        for (int i = 0; i < x.length; i++) {
            System.in.read();
            System.out.println(x);
        }
        */
        //foo();
    }
    
    public static boolean get() {
        boolean b = false;
        try {
            b = false;
            return b;
        } finally {
            b = true;
            return b;
        }
    }
    private static void foo() {
        try {
            System.out.println("try");
            foo();
        } catch (Throwable e) {
            System.out.println("catch");
            foo();
        } finally {
            System.out.println("finally");
            foo();
        }
    }


}
