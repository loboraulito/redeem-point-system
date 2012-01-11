package com.integral.util.spring.quartz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.util.Tools;
import com.integral.util.jms.JmsReceiver;
import com.integral.util.jms.JmsSender;

public class JobBean {
    private static Log log = LogFactory.getLog(JobBean.class);
    
    private DataSourceTransactionManager transactionManager;
    private JmsSender jmsSender;
    private JmsReceiver jmsReceiver;

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(
            DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public JmsSender getJmsSender() {
        return jmsSender;
    }

    public void setJmsSender(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }

    public JmsReceiver getJmsReceiver() {
        return jmsReceiver;
    }

    public void setJmsReceiver(JmsReceiver jmsReceiver) {
        this.jmsReceiver = jmsReceiver;
    }

    /**
     * 定时器执行函数
     * @author swpigris81@126.com
     * Description:
     */
    public void run(){
        log.info("run at "+Tools.dateToString3(new Date()));
        this.jmsSender.sendMessage();
        this.jmsReceiver.receiveMessage();
    }
}
