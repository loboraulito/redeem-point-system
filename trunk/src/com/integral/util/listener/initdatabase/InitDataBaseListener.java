package com.integral.util.listener.initdatabase;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.integral.util.DES;
import com.integral.util.properties.PropertiesReader;
import com.integral.util.sql.AntSqlExecute;
import com.integral.util.sql.SqlBean;

public class InitDataBaseListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("+++++++++++++++++++++++++++Listener start up++++++++++++++++++++++++++++++++");
        SqlBean sqlBean = null;
        try {
            //初始化读取数据库配置文件
            sqlBean = new SqlBean();
            List<Map<Object,Object>> list = SqlBean.excuteQuery("", "redeempoint");
            if(list != null && list.size()>0){
                sqlBean = null;
                return;
            }else{
                initDataBase(event);
                sqlBean = null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                initDataBase(event);
            }
            catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("+++++++++++++++++++++++++++Error++++++++++++++++++++++++++++++++");
            }
            sqlBean = null;
        }
        sqlBean = null;
        System.out.println("+++++++++++++++++++++++++++Listener done++++++++++++++++++++++++++++++++");
    }
    /**
     * <p>Discription:[初始化数据库]</p>
     * @param event
     * @author:[代超]
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void initDataBase(ServletContextEvent event) throws Exception{
        PropertiesReader reader = PropertiesReader.getInstance();
        DES des = new DES();
        String userName = reader.getProperty("user");
        try{
            byte[] stringToByte = des.stringToByte(userName);
            byte[] decryptorByte = des.createDecryptor(stringToByte);
            userName = new String(decryptorByte);
        }catch(Exception e){
            userName = reader.getProperty("user");
        }
        
        String password = reader.getProperty("password");
        try{
            byte[] stringToByte = des.stringToByte(password);
            byte[] decryptorByte = des.createDecryptor(stringToByte);
            password = new String(decryptorByte);
        }catch(Exception e){
            password = reader.getProperty("password");
        }
        AntSqlExecute sqlExecute = new AntSqlExecute("localhost","information_schema","3306",password,"",userName);
        String realPath = event.getServletContext().getRealPath("/");
        String sqlPath = realPath + "doc/redeempoint.sql";
        sqlExecute.executeSql(sqlPath, "", "");
    }
}
