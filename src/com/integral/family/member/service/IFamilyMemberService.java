package com.integral.family.member.service;

import java.util.List;

import com.integral.family.member.bean.FamilyMember;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IFamilyMemberService {
    /**
     * <p>Discription:[通过系统用户ID查询他所在家庭的成员]</p>
     * @param systemUserId
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyMember> findSelfFamilyMemberList(String systemUserId, int start, int limit);
    /**
     * <p>Discription:[查询用户所在家庭成员数量]</p>
     * @param systemUserId
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findSelfFamilyMemberListCount(String systemUserId);
    
    /**
     * <p>Discription:[添加家庭成员]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(FamilyMember instance);
    
    /**
     * <p>Discription:[以家庭成员属性查询家庭成员信息]</p>
     * @param propertyName
     * @param value
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyMember> findByProperty(String propertyName, Object value);
    /**
     * <p>Discription:[以家庭成员信息查询成员信息]</p>
     * @param instance
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyMember> findByExample(FamilyMember instance);
    
    /**
     * <p>Discription:[批量删除家庭成员信息]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(List<FamilyMember> persistentInstances);
    
    /**
     * <p>Discription:[批量新增/修改家庭成员信息]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdateAll(List<FamilyMember> persistentInstances);
}
