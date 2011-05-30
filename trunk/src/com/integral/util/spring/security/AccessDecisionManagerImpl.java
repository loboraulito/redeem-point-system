package com.integral.util.spring.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-17
 */
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    private static final Log log = LogFactory
            .getLog(AccessDecisionManagerImpl.class);

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     * @author 代超
     * @update 2011-5-17 代超[变更描述]
     */

    @Override
    public void decide(Authentication authentication, Object object,
            Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        log.info("AccessDecisionManagerImpl : "+object.toString());//object is a URL.
        Iterator<ConfigAttribute> ite=configAttributes.iterator();
        while(ite.hasNext()){
            ConfigAttribute ca=ite.next();
            String needRole=((SecurityConfig)ca).getAttribute();
            for(GrantedAuthority ga:authentication.getAuthorities()){
                if(needRole.equals(ga.getAuthority())){  //ga is user's role.
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param arg0
     * @return
     * @author 代超
     * @update 2011-5-17 代超[变更描述]
     */

    @Override
    public boolean supports(ConfigAttribute arg0) {
        return true;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param arg0
     * @return
     * @author 代超
     * @update 2011-5-17 代超[变更描述]
     */

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
