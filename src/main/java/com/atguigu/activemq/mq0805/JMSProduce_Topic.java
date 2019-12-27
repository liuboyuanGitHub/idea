package com.atguigu.activemq.mq0805;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Java Message Service java消息服务，他是java消息中间件的规范 ms相当于消息中间件
 *
 */

public class JMSProduce_Topic {
    public static final String MQ_URL="tcp://192.168.40.130:61616";
    public static final String TOPIC_NAME="topic0805";
    public static void main(String[] args) throws JMSException {
        //1 获得ActiveMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2 由activeMQConnectionFactory获取connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //3 启动连接准备建立会话
        connection.start();
        //4 获得session，两个参数先是默认
        //4.1 是否开启事务
        //4.2 签收模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5 获得目的地，此例是队列
        Topic topic = session.createTopic(TOPIC_NAME);
        //6 获取消息生产者,生产什么内容？生产出来放在哪里？(目前是放在队列中)
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //7 生产message内容
        for (int i = 1; i <=6 ; i++) {
            TextMessage textMessage = session.createTextMessage("msg-----" + i);
            producer.send(textMessage);
        }
        //8 释放连接资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("*******msg send ok !");

    }
}
