package com.integral.system.codelist.service.impl;

import com.integral.common.dao.IBaseDao;
import com.integral.system.codelist.dao.ICodeListDao;
import com.integral.system.codelist.dao.ICodeListDataDao;
import com.integral.system.codelist.service.ICodeListService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CodeListServiceImpl implements ICodeListService {
    private ICodeListDao codeListDao;
    private ICodeListDataDao codeListDataDao;
    private IBaseDao baseDao;
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return ICodeListDao codeListDao.
     */
    public ICodeListDao getCodeListDao() {
        return codeListDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeListDao The codeListDao to set.
     */
    public void setCodeListDao(ICodeListDao codeListDao) {
        this.codeListDao = codeListDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return ICodeListDataDao codeListDataDao.
     */
    public ICodeListDataDao getCodeListDataDao() {
        return codeListDataDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeListDataDao The codeListDataDao to set.
     */
    public void setCodeListDataDao(ICodeListDataDao codeListDataDao) {
        this.codeListDataDao = codeListDataDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IBaseDao baseDao.
     */
    public IBaseDao getBaseDao() {
        return baseDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param baseDao The baseDao to set.
     */
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
}
