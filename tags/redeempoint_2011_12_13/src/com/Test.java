package com;

import org.springframework.security.web.util.RegexUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.util.AntPathMatcher;

/**
 * 
 * 类描述  description of the class
 * 创建者 cdai : swpigris81@126.com
 * @version V1.0 创建时间: May 13, 2011
 */
public class Test {

    /**
     * 
     * 方法描述 
     * @author cdai : swpigris81@126.com
     * @createTime  May 13, 2011
     * @param args
     */
    public static void main(String[] args) {
        String url = "/balance/accountbalance.action?method=begin";
        String resUrl = "/abc/balance/accountbalance.action?method=begin";
        UrlMatcher urlMatcher = new RegexUrlPathMatcher();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(urlMatcher.pathMatchesUrl(urlMatcher.compile(url), resUrl));
        System.out.println(pathMatcher.matchStart(url, resUrl));
    }
}
