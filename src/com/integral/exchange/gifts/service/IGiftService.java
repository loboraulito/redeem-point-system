package com.integral.exchange.gifts.service;

import java.util.List;

import com.integral.exchange.gifts.bean.GiftInfo;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public interface IGiftService {
    /**
     * <p>Discription:[查询所有的礼品]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findAll();
    
    /**
     * 分页查询礼品信息
     * <p>Discription:[方法功能中文描述]</p>
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByPage(int start, int limit);
    /**
     * 查询所有礼品数量
     * <p>Discription:[方法功能中文描述]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Long findAllGiftSize();
    /**
     * <p>Discription:[分页查询供应商的礼品信息]</p>
     * @param supplier
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByPageWithSupplier(String supplier, int start, int limit);
    /**
     * <p>Discription:[查询供应商的礼品数量]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Long findAllSupplierGiftSize(String supplier);
    
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
}
