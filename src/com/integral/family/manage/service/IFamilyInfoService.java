package com.integral.family.manage.service;

import java.util.List;

import com.integral.family.manage.bean.FamilyInfo;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IFamilyInfoService {
    /**
     * <p>Discription:[根据用户ID查询所在家庭信息列表]</p>
     * @param userId
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyInfo> findFamilyListByUserId(String userId, int start, int limit);
    /**
     * <p>Discription:[根据用户ID查询所在家庭信息列表数量]</p>
     * @param userId
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findFamilyListSizeByUserId(String userId);
    
    /**
     * <p>Discription:[创建家庭信息]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(FamilyInfo instance);
}
