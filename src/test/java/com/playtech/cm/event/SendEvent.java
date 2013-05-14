package com.playtech.cm.event;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.subethamail.wiser.Wiser;
//import javax.jms.Session;
import javax.jms.*;
//import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

/**
 * User: Denis Veselovskiy
 * Date: 5/4/12
 * Time: 3:37 PM
 */
public class SendEvent {

    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public static void main(String[] args) throws Exception {

        Wiser wiser = new Wiser();
        wiser.setPort(25); // Default is 25
        wiser.start();

//        InitDb prepareDB = new InitDb("clean.xml");
//        InitDb prepareDB2 = new InitDb("demodb-init.xml");

        Event event = new Event();

        event.setEventType("LOGIN_EVENT");
        event.setEventId(UUID.randomUUID().toString());
        event.setEventTimeStamp(new Date());
        Map<String, String> payloadMap = new HashMap<String, String>();
        payloadMap.put("param1", "value1");
        payloadMap.put("param2", "value2");
        payloadMap.put("param3", "value3");
        event.setPayload(payloadMap);
        event.setCasinoId("MEGACasino");
        event.setPlayerLoginName("MEGACasinoPlayer");
        Platform platform = new Platform();
        platform.setName("Platform1");
        event.setPlatform(platform);

        sendEvent(event);
        Thread.sleep(3000);

        String mes = null;
        try {
            mes = wiser.getMessages().get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            wiser.stop();
        }
        System.out.println(mes);
        System.out.println(mes.split(" "));
        wiser.stop();
    }

    private static void sendEvent(Event event) throws JsonGenerationException, JsonMappingException, IOException {
        // Creating json mapper
        ObjectMapper mapper = createMapperFactory();

        // converting object to string
        String json = mapper.writeValueAsString(event);
        sendMessage("protrack.external", json);
        System.out.println("\nSend event: " + json);
        System.out.println();
    }

    private static void sendMessage(final String destinationName, final String messageText) {
        // filling environment properties
        Hashtable<Object, Object> properties = new Hashtable<Object, Object>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://jms.ums.playtech.com:61616");

        // sending message
        InitialContext initialContext = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            initialContext = new InitialContext(properties);
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext
                    .lookup("ConnectionFactory");
            Destination destination = (Destination) initialContext
                    .lookup("dynamicTopics/" + destinationName);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage(messageText);
            producer = session.createProducer(destination);
            producer.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

            // closing everything that could be closed
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ObjectMapper createMapperFactory() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        return mapper;
    }

}
