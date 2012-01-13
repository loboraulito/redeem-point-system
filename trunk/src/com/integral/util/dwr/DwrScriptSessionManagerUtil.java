package com.integral.util.dwr;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.impl.DefaultScriptSession;
import org.directwebremoting.impl.DefaultScriptSessionManager;

import com.integral.system.message.bean.SystemMessage;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class DwrScriptSessionManagerUtil extends DefaultScriptSessionManager {
    private static Log log = LogFactory.getLog(DwrScriptSessionManagerUtil.class);
    public static final String SS_ID = "DWR_ScriptSession_Id";
    public DwrScriptSessionManagerUtil() {
        try{
            this.addScriptSessionListener(new ScriptSessionListener(){

                @Override
                public void sessionCreated(ScriptSessionEvent event) {
                    //获取新创建的SS
                    ScriptSession scriptSession = event.getSession();
                    WebContext webContext = WebContextFactory.get();
                    HttpServletRequest httpServletRequest = webContext.getHttpServletRequest();
                    //得到产生的httpSession
                    HttpSession httpSession = httpServletRequest.getSession();
                    //得到当前消息
                    SystemMessage message = (SystemMessage) httpSession.getAttribute("sessionMessage");
                    //如果当前消息为空，销毁这个scriptsession
                    if(message == null){
                        scriptSession.invalidate();
                        httpSession.invalidate();
                        return;
                    }
                    String ssId = (String) httpSession.getAttribute(SS_ID);//查找SS_ID
                    if(ssId != null){
                        //说明已经存在旧的scriptSession.注销这个旧的scriptSession
                        DefaultScriptSession old=sessionMap.get(ssId);
                        if(old != null){
                            invalidate(old);
                        }
                        httpSession.setAttribute(SS_ID, scriptSession.getId());
                        //此处将messageId和scriptSession绑定
                        scriptSession.setAttribute("messageId",message.getMessageId());
                        log.info("create " + scriptSession.getId() + ", put messageId into scriptSession as "+ message.getMessageId());
                    }
                }

                @Override
                public void sessionDestroyed(ScriptSessionEvent event) {
                    log.info("destroy scriptSession : " + event.getSession().getId());
                }
            });
            //ReqReverseAjax.manager=this;//将自己暴露ReverseAjax业务处理类
            
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }
    
    public Collection getAllScriptSessions(){  
        return sessionMap.values();  
    }
}
