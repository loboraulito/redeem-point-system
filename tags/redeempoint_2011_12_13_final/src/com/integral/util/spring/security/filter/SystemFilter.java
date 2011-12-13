package com.integral.util.spring.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.integral.util.spring.security.ResourceDetailsMonitor;

/** 
 * <p>Description: [系统的权限Filter]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 17, 2011
 */
public class SystemFilter extends AbstractSecurityInterceptor implements Filter {
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private ResourceDetailsMonitor resourceDetailsMonitor;
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return ResourceDetailsMonitor resourceDetailsMonitor.
     */
    public ResourceDetailsMonitor getResourceDetailsMonitor() {
        return resourceDetailsMonitor;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param resourceDetailsMonitor The resourceDetailsMonitor to set.
     */
    public void setResourceDetailsMonitor(
            ResourceDetailsMonitor resourceDetailsMonitor) {
        this.resourceDetailsMonitor = resourceDetailsMonitor;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return
     * @author cdai
     * @update May 17, 2011 cdai[变更描述]
     */

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return
     * @author cdai
     * @update May 17, 2011 cdai[变更描述]
     */

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(
            FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @author cdai
     * @update May 17, 2011 cdai[变更描述]
     */

    @Override
    public void destroy() {
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws IOException
     * @throws ServletException
     * @author cdai
     * @update May 17, 2011 cdai[变更描述]
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        /*
        if(resourceDetailsMonitor!=null){
            resourceDetailsMonitor.refresh();
        }
        */
        invoke(fi);
    }
    
    public void invoke(FilterInvocation fi) throws IOException, ServletException{
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try{
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }finally{
            super.afterInvocation(token, null);
        }
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param arg0
     * @throws ServletException
     * @author cdai
     * @update May 17, 2011 cdai[变更描述]
     */

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
