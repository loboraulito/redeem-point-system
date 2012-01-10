package com.integral.util.spring.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.util.Tools;

public class JobBean {
    private static Log log = LogFactory.getLog(JobBean.class);
    
    private DataSourceTransactionManager transactionManager;

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(
            DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    /**
     * 定时器执行函数
     * @author swpigris81@126.com
     * Description:
     */
    public void run(){
        log.info("run at "+Tools.dateToString3(new Date()));
    }
}
