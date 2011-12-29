package com.integral.system.common.service.impl;

import java.util.List;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.common.service.ICommonService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CommonServiceImpl implements ICommonService {
    private BaseDao baseDao;

    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    public List getAddressFromCodeData(String codeId, String ... dataKey) throws Exception{
        String sql = "SELECT CONCAT(IFNULL((SELECT codelist.datavalue FROM point_system_codelist_data codelist" +
        		" WHERE codelist.codeid=? AND codelist.datakey = ?)," +
        		" ''), IFNULL((SELECT codelist.datavalue FROM point_system_codelist_data codelist" +
        		" WHERE codelist.codeid=? AND codelist.datakey = ?)," +
        		" ''), IFNULL((SELECT codelist.datavalue FROM point_system_codelist_data codelist" +
        		" WHERE codelist.codeid=? AND codelist.datakey = ?),'')" +
        		" ) AS address";
        try{
            if(dataKey != null && codeId != null && !"".equals(codeId)){
                Object [] obj = new Object[]{codeId, dataKey[0], codeId, dataKey[1], codeId, dataKey[2]};
                return this.baseDao.queryBySQL(sql, obj);
            }else{
                return null;
            }
        }catch(Exception e){
            throw e;
        }
    }
}
