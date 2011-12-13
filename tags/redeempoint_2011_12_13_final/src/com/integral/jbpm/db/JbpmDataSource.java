package com.integral.jbpm.db;

import bitronix.tm.resource.jdbc.PoolingDataSource;



public class JbpmDataSource {
    
    public static void main(String [] args){
        PoolingDataSource ds=new PoolingDataSource();
        ds.setUniqueName("jdbc/testDS1");
        ds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        ds.setClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        ds.setMaxPoolSize(3);
        ds.setAllowLocalTransactions(true);
        ds.getDriverProperties().put("user", "swpigris81");
        ds.getDriverProperties().put("password", "812877");
        ds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/redeempoint?useUnicode=true&amp;characterEncoding=UTF-8");
        ds.init();
    }
}
