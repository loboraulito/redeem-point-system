package com.integral.util.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * <p>Description: [使用apache的ActiveMQ5.5.1发送和接受消息]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
public class JmsSender {
    private static Log log = LogFactory.getLog(JmsSender.class);
    private JmsTemplate jmsTemplate;
    private Destination destination;
    
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }


    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public Destination getDestination() {
        return destination;
    }


    public void setDestination(Destination destination) {
        this.destination = destination;
    }


    /**
     * <p>Discription:[测试方法]</p>
     * @param args
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static void main(String[] args){
        JmsSender sender = new JmsSender();
        sender.getJmsTemplate().send(sender.getDestination(), new MessageCreator(){

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("This is a test JMS sender");
            }
            
        });
        log.info("Sent a JMS message!");
    }
    
    public void sendMessage(){
        this.jmsTemplate.send(this.destination, new MessageCreator(){
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("This is a test JMS sender");
            }
        });
        log.info("Sent a JMS message!");
    }
}
