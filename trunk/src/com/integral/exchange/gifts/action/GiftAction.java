package com.integral.exchange.gifts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
import com.integral.exchange.gifts.service.IGiftService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public class GiftAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IGiftService giftService;

    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IGiftService giftService.
     */
    public IGiftService getGiftService() {
        return giftService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param giftService The giftService to set.
     */
    public void setGiftService(IGiftService giftService) {
        this.giftService = giftService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return DataSourceTransactionManager transactionManager.
     */
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param transactionManager The transactionManager to set.
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param request The request to set.
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param response The response to set.
     */
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    /**
     * <p>Discription:[礼品管理页面]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    
    public String giftManageList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        int page = NumberUtils.toInt(request.getParameter("page"), 0);
        
        List list = this.giftService.findByPage(start+limit*(page-1), limit+limit*(page-1));
        long giftSize = this.giftService.findAllGiftSize();
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            JsonFormat jf = new JsonFormat();
            jf.setQuoteName(true);
            //设置Unicode编码
            jf.setAutoUnicode(true);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", true);
            map.put("totalCount", list.size());
            BigDecimal recordSize = new BigDecimal(""+giftSize);
            BigDecimal pageCount = new BigDecimal("0");
            pageCount = recordSize.divide(new BigDecimal(""+limit), BigDecimal.ROUND_CEILING);
            map.put("pageCount", pageCount.intValue());
            map.put("giftList", list);
            out.print(Json.toJson(map, jf));
            //out.print("{\"success\":\"true\",\"totalCount\":\"34\"}");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
