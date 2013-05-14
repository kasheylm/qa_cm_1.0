package com.playtech.cm.jms;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.support.JmsUtils;
import org.testng.annotations.Test;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 1/28/13
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsUtil {

    private final static String queue = "core.scheduler.jobs.q.json.veselovskiy";
    private static String input = "{\"triggers\":[{\"jobName\":\"protrack-job-2855\",\"jobGroup\":\"protrack-activity-2855\",\"schedulerName\":\"CoreScheduler\",\"startTime\":\"2013-03-26 17:22:00\",\"endTime\":null,\"schedule\":{\"type\":\"cron\",\"cron\":\"0 22 0/1 * * ?\",\"misfirePolicy\":2},\"replace\":true,\"triggerId\":\"protrack-trigger-2855-0\",\"triggerGroup\":\"protrack-activity-2855\"}],\"jobs\":[{\"group\":\"protrack-activity-2855\",\"name\":\"protrack-job-2855\",\"schedulerName\":\"CoreScheduler\",\"type\":\"jms-job\",\"data\":{\"type\":\"jms-data\",\"maxRetries\":5,\"messageData\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><request><activityId>2854</activityId><casinoId>81004</casinoId><casinoName>playtech81004</casinoName><columnKeys>username,losses,net_loss,average_bet,currency</columnKeys><params><param name=\\\"startdate\\\">2012-07-03</param><param name=\\\"enddate\\\">2012-07-03</param><param name=\\\"gamelosscalculation\\\">1</param><param name=\\\"suspended\\\">3</param><param name=\\\"frozen\\\">3</param><param name=\\\"wantmail\\\">3</param><param name=\\\"invalidmail\\\">3</param><param name=\\\"donotsendemail\\\">3</param><param name=\\\"internal\\\">0</param></params><playerIdIndex>0</playerIdIndex><reportCode>55671</reportCode></request>\",\"transactional\":false,\"persistent\":false,\"messageType\":\"QUEUE\",\"targetName\":\"protrack.job.result.veselovskiy\"},\"replace\":true,\"runNow\":false}]}";

    @Test
    public void checkEvent() {
        sendEvent();
        String json =  readMessage();
        if (json == null) {
            System.out.println("No message found.");
            System.exit(1);
        }
        String xml = (JsonPath.read(json, "$.jobs[0].data.messageData"));
        System.out.println(xml);
    }

     static void sendEvent() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:jms.xml");

        ConnectionFactory pmsConnectionFactory = applicationContext.getBean("pmsConnectionFactory", ConnectionFactory.class);
        JmsTemplate jmsTemplate = new JmsTemplate(pmsConnectionFactory);
        System.out.println("Sending messages...");

        jmsTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object doInJms(Session session) throws JMSException {
                MessageProducer producer = null;
                try {
                    final Destination destination = session.createQueue(queue);
                    producer = session.createProducer(destination);
                    TextMessage textMessage = session.createTextMessage(input);
                    producer.setTimeToLive(10000);
                    producer.send(textMessage);
                }
                finally {
                    JmsUtils.closeMessageProducer(producer);
                }
                return null;
            }
        });

        System.out.println("Done");
    }

     static String readMessage() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:jms.xml");
        ConnectionFactory pmsConnectionFactory = applicationContext.getBean("pmsConnectionFactory", ConnectionFactory.class);
        JmsTemplate jmsTemplate = new JmsTemplate(pmsConnectionFactory);
        System.out.println("Reading messages...");
        jmsTemplate.setReceiveTimeout(60000);
        Message message = jmsTemplate.receive(queue);
        if (message == null) {
            System.out.println("Timeout");
            return null;
        }
        TextMessage textMessage = (TextMessage) message;
        String payload = null;
        try {
            payload = textMessage.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println(payload);
        System.out.println("Done");
        return payload;
    }

    static String convertPayloadToXml(String json) {
        return JsonPath.read(json, "$.jobs[0].data.messageData");
    }
}
