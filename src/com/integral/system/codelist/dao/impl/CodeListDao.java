package com.integral.system.codelist.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.codelist.dao.ICodeListDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CodeListDao extends HibernateDaoSupport implements ICodeListDao {
    private static final Log log = LogFactory.getLog(CodeListDao.class);
    protected void initDao() {
        //do nothing
    }
}
