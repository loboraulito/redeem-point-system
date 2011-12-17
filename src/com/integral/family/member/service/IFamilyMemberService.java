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
}
