package com.integral.family.member.dao;

import java.util.List;
import java.util.Map;

import com.integral.family.member.bean.FamilyMember;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IFamilyMemberDAO {
    /**
     * <p>Discription:[自定义查询方法]</p>
     * @param sql
     * @param isHql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyMember> findByParams(String sql, boolean isHql, int start, int limit,
            Map<String, Object> params);
}
