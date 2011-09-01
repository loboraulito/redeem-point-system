package com.integral.util.sql;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

/**
 * ant执行sql文件 
 * @author swpigris81@126.com
 *
 */
public class AntSqlExecute {
    private String dbHost = "localhost";
    private String dbPort = "3306";
    private String dbName = "information_schema";
    private String dbUser = "swpigris81";
    private String dbPsw = "******";
    private String dbUrl = "";
    
    public AntSqlExecute(){
        this.dbUrl = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?useUnicode=true&amp;characterEncoding=UTF-8";
    }

    public AntSqlExecute(String dbHost, String dbName, String dbPort,
            String dbPsw, String dbUrl, String dbUser) {
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbPort = dbPort;
        this.dbPsw = dbPsw;
        this.dbUrl = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?useUnicode=true&amp;characterEncoding=UTF-8";;
        this.dbUser = dbUser;
    }
    
    public static void main(String[] args){
        String sqlFile = "d:/swpigris81.sql";
        String proFile = "d:/procedure_new.sql";
        AntSqlExecute execute = new AntSqlExecute();
        //execute.executeSql(sqlFile,"");
        execute.executeSql(proFile,";;","swpigris81");
    }
    
    public void executeSql(String sqlFile,String delimiter,String dbName){
        SQLExec sqlexec=new SQLExec();
        sqlexec.setDriver("com.mysql.jdbc.Driver");
        sqlexec.setPassword(this.dbPsw);
        sqlexec.setUrl(this.dbUrl);
        sqlexec.setUserid(this.dbUser);
        //保证sql文件中格式不被ant改变。
        sqlexec.setKeepformat(true);
        //设置sql的结尾符号，默认为：“;”
        if(!"".equals(delimiter) && delimiter != null){
            sqlexec.setDelimiter(delimiter);
            //sqlexec.addText("use "+dbName+";;");
        }
        //设置编码格式
        sqlexec.setEncoding("UTF-8");
        sqlexec.setSrc(new File(sqlFile));
        //出现异常情况，如何处理
        //sqlexec.setOnerror((SQLExec.OnError)(EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));
        //是否打印，默认打印到控制台
        sqlexec.setPrint(true);
        //sqlexec.setOutput(new File("d:/abc.out")); 
        sqlexec.setProject(new Project());
        sqlexec.execute();
    }
    

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPsw() {
        return dbPsw;
    }

    public void setDbPsw(String dbPsw) {
        this.dbPsw = dbPsw;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

}
