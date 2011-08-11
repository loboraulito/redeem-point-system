package com.integral.common.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
public class AbstractHibernateJDBCDao extends HibernateDaoSupport {
    protected Log logger = LogFactory.getLog(getClass());

    protected Class entityClass;

    protected Class getEntityClass() {
        return entityClass;
    }

    public List getAll() {
        return getHibernateTemplate().loadAll(getEntityClass());
    }

    public void save(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public void removeById(Serializable id) {
        remove(getHibernateTemplate().get(getEntityClass(), id));
    }

    public void remove(Object o) {
        getHibernateTemplate().delete(o);
    }

}
