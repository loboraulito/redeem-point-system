package com.integral.family.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.family.member.bean.FamilyMember;
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
    
    /**
     * <p>Discription:[通过系统用户ID查询他所在家庭的成员]</p>
     * @param systemUserId
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyMember> findSelfFamilyMemberList(String systemUserId, int start, int limit){
        /*
        String sql = "SELECT fm.familyMemberId, fm.family_id, fm.family_member_name, fm.system_member_id," +
        		" fm.family_member_card, fm.family_member_birthdate, fm.family_member_birthplace, fm.family_member_sex," +
        		" fm.family_member_height, fm.family_member_educational, fm.family_member_profession, fm.family_member_deaddate" +
        		" FROM family_member AS fm WHERE fm.family_id IN ( SELECT fr.family_id AS fid FROM family_member fr " +
        		" WHERE fr.system_member_id = :systemUserId )";
        */
        String hql = "FROM FamilyMember AS fm where fm.familyId IN (SELECT fr.familyId FROM FamilyMember AS fr WHERE fr.systemMemberId = :systemUserId)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("systemUserId", systemUserId);
        return this.familyMemberDao.findByParams(hql, true, start, limit, params);
    }
    public int findSelfFamilyMemberListCount(String systemUserId){
        String hql = "FROM FamilyMember AS fm where fm.familyId IN (SELECT fr.familyId FROM FamilyMember AS fr WHERE fr.systemMemberId = :systemUserId)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("systemUserId", systemUserId);
        return this.familyMemberDao.findCountByParams(hql, true, -1, -1, params);
    }
}
