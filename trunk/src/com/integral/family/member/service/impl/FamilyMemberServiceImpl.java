package com.integral.family.member.service.impl;

import com.integral.common.dao.impl.BaseDao;
import com.integral.family.member.dao.IFamilyMemberDAO;
import com.integral.family.member.service.IFamilyMemberService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyMemberServiceImpl implements IFamilyMemberService {
    private BaseDao baseDao;
    
    private IFamilyMemberDAO familyMemberDao;

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return BaseDao baseDao.
     */
    public BaseDao getBaseDao() {
        return baseDao;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param baseDao The baseDao to set.
     */
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IFamilyMemberDAO familyMemberDao.
     */
    public IFamilyMemberDAO getFamilyMemberDao() {
        return familyMemberDao;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param familyMemberDao The familyMemberDao to set.
     */
    public void setFamilyMemberDao(IFamilyMemberDAO familyMemberDao) {
        this.familyMemberDao = familyMemberDao;
    }
}
