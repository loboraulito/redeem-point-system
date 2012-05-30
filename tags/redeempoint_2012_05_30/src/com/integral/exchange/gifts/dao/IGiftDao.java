package com.integral.exchange.gifts.dao;

import java.util.Collection;
import java.util.List;

import com.integral.exchange.gifts.bean.GiftInfo;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public interface IGiftDao {
    /**
     * <p>Discription:[查询所有的礼品]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findAll();
    /**
     * <p>Discription:[保存或修改礼品信息]</p>
     * @param entity
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(GiftInfo entity);
    /**
     * <p>Discription:[根据礼品ID查询礼品信息]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public GiftInfo findById(String id);
    /**
     * <p>Discription:[批量删除礼品信息]</p>
     * @param entities
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(Collection<GiftInfo> entities);
}
