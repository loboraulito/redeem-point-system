package com.integral.util.spring.security;

import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public class MethodSecurityMetadataSource extends
        AbstractMethodSecurityMetadataSource {

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param arg0
     * @param arg1
     * @return
     * @author cdai
     * @update May 18, 2011 cdai[变更描述]
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> clazz) {
        return null;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return
     * @author cdai
     * @update May 18, 2011 cdai[变更描述]
     */

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

}
