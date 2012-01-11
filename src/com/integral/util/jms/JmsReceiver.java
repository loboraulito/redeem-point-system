package com.integral.util.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

/** 
 * <p>Description: [JMS接受消息]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class JmsReceiver {
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
    public static void main(String[] args) {
        JmsReceiver receiver = new JmsReceiver();
        TextMessage message = (TextMessage) receiver.getJmsTemplate().receive(receiver.getDestination());
        if(message != null){
            try {
                log.info("接受到消息：" + message.getText());
            }
            catch (JMSException e) {
                e.printStackTrace();
                log.error("接受消息失败！");
            }
        }
    }
    
    public void receiveMessage(){
        TextMessage message = (TextMessage) this.jmsTemplate.receive(this.destination);
        if(message != null){
            try {
                log.info("接受到消息：" + message.getText());
            }
            catch (JMSException e) {
                e.printStackTrace();
                log.error("接受消息失败！");
            }
        }
    }
}
