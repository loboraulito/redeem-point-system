package com.integral.util.listener.initdatabase;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.integral.util.sql.AntSqlExecute;
import com.integral.util.sql.SqlBean;

public class InitDataBaseListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("+++++++++++++++++++++++++++Listener start up++++++++++++++++++++++++++++++++");
        try {
            String sql = "select * from information_schema.SCHEMATA where SCHEMA_NAME= ? ";
            List<Map<Object,Object>> list = SqlBean.excuteQuery(sql, "redeempoint");
            if(list != null && list.size()>0){
                return;
            }else{
                initDataBase(event);
            }
        }
        catch (Exception e) {
            initDataBase(event);
        }
        System.out.println("+++++++++++++++++++++++++++Listener done++++++++++++++++++++++++++++++++");
    }
    /**
     * <p>Discription:[初始化数据库]</p>
     * @param event
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void initDataBase(ServletContextEvent event){
        AntSqlExecute sqlExecute = new AntSqlExecute("localhost","information_schema","3306","812877","","swpigris81");
        String realPath = event.getServletContext().getRealPath("/");
        String sqlPath = realPath + "doc/redeempoint.sql";
        sqlExecute.executeSql(sqlPath, "", "");
    }
}
