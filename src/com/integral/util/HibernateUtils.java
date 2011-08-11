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
    public static Session getHibernateSession(SessionFactory sessionFactory){
        return sessionFactory.openSession();
    }
    
    public static Properties getHibernateProperties() {
        return Environment.getProperties();
    }

    public static Dialect getHibernateDialect(Properties props) {
        return DialectFactory.buildDialect(props, null, 0);
    }

    public static String getHibernateLimitString(Dialect dialect, String query, int offset, int limit) {
        return dialect.getLimitString(query, offset, limit);
    }
}
