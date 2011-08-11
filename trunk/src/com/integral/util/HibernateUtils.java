package com.integral.util;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
public class HibernateUtils {
    /**
     * <p>Discription:[已过时]</p>
     * @param sessionFactory
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Session getHibernateSession(SessionFactory sessionFactory){
        return sessionFactory.openSession();
    }
    
    /**
     * <p>Discription:[已过时]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Properties getHibernateProperties() {
        return Environment.getProperties();
    }
    /**
     * <p>Discription:[已过时]</p>
     * @param props
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Dialect getHibernateDialect(Properties props) {
        return DialectFactory.buildDialect(props, null, 0);
    }
    /**
     * <p>Discription:[获取经过组装分页之后的sql语句]</p>
     * @param dialect Hibernate配置中的方言
     * @param query sql语句
     * @param offset 分页start
     * @param limit 分页limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static String getHibernateLimitString(Dialect dialect, String query, int offset, int limit) {
        return dialect.getLimitString(query, offset, limit);
    }
}
