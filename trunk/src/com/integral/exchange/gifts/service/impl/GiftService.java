package com.integral.exchange.gifts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.common.dao.impl.BaseDao;
import com.integral.exchange.gifts.bean.GiftInfo;
import com.integral.exchange.gifts.dao.IGiftDao;
import com.integral.exchange.gifts.service.IGiftService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public class GiftService implements IGiftService {
    private IGiftDao giftDao;
    private BaseDao baseDao;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IGiftDao giftDao.
     */
    public IGiftDao getGiftDao() {
        return giftDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param giftDao The giftDao to set.
     */
    public void setGiftDao(IGiftDao giftDao) {
        this.giftDao = giftDao;
    }
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
     * <p>Discription:[查询所有的礼品]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findAll(){
        return this.giftDao.findAll();
    }
    
    public List findByPage(int start, int limit){
        return this.baseDao.queryPageByHQL("From GiftInfo model", null, start, limit);
    }
    
    public List findByPageWithSupplier(String supplier, int start, int limit){
        return this.baseDao.queryPageByHQL("From GiftInfo model where model.supplierId = ?", new String[]{supplier}, start, limit);
    }
    
    public Long findAllGiftSize(){
        long size = 0L;
        String sql = "select count(gift_info.gift_id) as giftsize from gift_info";
        List list = this.baseDao.queryBySQL(sql, null);
        if(list!=null){
            size = NumberUtils.toLong((String.valueOf(list.get(0))), 0L);
        }
        return size;
    }
    @Override
    public Long findAllSupplierGiftSize(String supplier) {
        long size = 0L;
        String sql = "select count(gift_info.gift_id) as giftsize from gift_info where gift_info.supplier_id = ?";
        List list = this.baseDao.queryBySQL(sql, new String[]{supplier});
        if(list!=null){
            size = NumberUtils.toLong((String.valueOf(list.get(0))), 0L);
        }
        return size;
    }
    
    /**
     * <p>Discription:[保存或修改礼品信息]</p>
     * @param entity
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(GiftInfo entity){
        this.giftDao.saveOrUpdate(entity);
    }
    
    /**
     * <p>Discription:[根据礼品ID查询礼品信息]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public GiftInfo findById(String id){
        return this.giftDao.findById(id);
    }
    
    /**
     * <p>Discription:[批量删除礼品信息]</p>
     * @param entities
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAllGifts(String[] entities){
        List<GiftInfo> gifts = new ArrayList<GiftInfo>();
        if(entities != null && entities.length > 0){
            for(int i=0; i<entities.length; i++){
                GiftInfo gift = new GiftInfo();
                gift.setGiftId(entities[i]);
                gifts.add(gift);
            }
        }
        if(gifts.size()<1){
            return;
        }
        this.giftDao.deleteAll(gifts);
    }
}
