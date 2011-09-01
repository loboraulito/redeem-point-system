package com.integral.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integral.util.properties.PropertiesReader;

public class SqlBean {
    private static PropertiesReader reader;
    public static String sql;
    
    public SqlBean() throws Exception {
        reader = PropertiesReader.getInstance();
        sql = reader.getProperty("sql");
    }
    /**
     * <p>Discription:[获取数据库连接对象]</p>
     * @return
     * @throws Exception
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private static Connection getConn() throws Exception{
        if(reader == null){
            reader = PropertiesReader.getInstance();
        }
        String className = reader.getProperty("driverName");
        String url = reader.getProperty("url");
        String userName = reader.getProperty("user");
        String password = reader.getProperty("password");
        Connection conn = null;
        try{
            //加载驱动
            Class.forName(className);
            //通过DriverManager获取数据库连接对象
            conn = DriverManager.getConnection(url, userName, password);
        }catch(Exception e){
            throw e;
        }
        return conn;
    }
    /**
     * <p>Discription:[关闭数据查询操作相关对象]</p>
     * @param params
     * @author:[代超]
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private static void closeDB(Object ... params) throws Exception{
        if(params != null){
            for(Object obj : params){
                try{
                    if(obj instanceof ResultSet){
                        ((ResultSet)obj).close();
                        obj = null;
                    }
                    if(obj instanceof PreparedStatement){
                        ((PreparedStatement)obj).close();
                        obj = null;
                    }
                    if(obj instanceof Connection){
                        ((Connection)obj).close();
                        obj = null;
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }
    }
    /**
     * <p>Discription:[创建预编译的sql查询语句]</p>
     * @param sql
     * @return
     * @throws Exception
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private static PreparedStatement getPreparedStatement(String sql) throws Exception{
        if(sql == null || "".equals(sql.trim())){
            sql = SqlBean.sql;
        }
        PreparedStatement pst = null;
        try{
            pst = getConn().prepareStatement(sql);
        }catch(Exception e){
            throw e;
        }finally{
            closeDB(getConn());
        }
        return pst;
    }
    /**
     * <p>Discription:[为预编译的sql查询语句赋予参数变量值]</p>
     * @param pst
     * @param params
     * @author:[代超]
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private static void setParam(PreparedStatement pst, Object ... params) throws Exception{
        int length = params.length;
        for(int i=0;i<length;i++){
            try{
                pst.setObject(i+1, params[i]);
            }catch(Exception e){
                throw e;
            }
        }
    }
    /**
     * <p>Discription:[执行简单的增、删、改操作]</p>
     * @param sql
     * @param params
     * @return
     * @throws Exception
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int excuteUpdate(String sql, Object ... params) throws Exception{
        if(sql == null || "".equals(sql.trim())){
            sql = SqlBean.sql;
        }
        int rows = 0;
        PreparedStatement pst = getPreparedStatement(sql);
        setParam(pst, params);
        try{
            rows = pst.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally{
            closeDB(pst);
        }
        return rows;
    }
    /**
     * <p>Discription:[执行sql查询语句]</p>
     * @param sql
     * @param params
     * @return
     * @throws Exception
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static List<Map<Object, Object>> excuteQuery(String sql, Object ... params) throws Exception{
        if(sql == null || "".equals(sql.trim())){
            sql = SqlBean.sql;
        }
        List <Map<Object, Object>> lst = new ArrayList<Map<Object,Object>>();
        Map<Object, Object> map = null;
        ResultSet rs = null;
        ResultSetMetaData rsd = null;
        PreparedStatement pst = getPreparedStatement(sql);
        setParam(pst, params);
        try{
            rs = pst.executeQuery();
            if(rs != null){
                rsd = rs.getMetaData();
                while(rs.next()){
                    int columnCont = rsd.getColumnCount();
                    map = new HashMap<Object, Object>();
                    for(int i=1; i<columnCont; i++){
                        map.put(rsd.getColumnName(i), rs.getObject(i));
                    }
                    lst.add(map);
                }
            }
        }catch(Exception e){
            throw e;
        }finally{
            closeDB(new Object[]{rs, pst});
        }
        return lst;
    }
}
