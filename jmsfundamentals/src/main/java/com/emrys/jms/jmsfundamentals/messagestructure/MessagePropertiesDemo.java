package com.emrys.jms.jmsfundamentals.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePropertiesDemo {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue)context.lookup("queue/myQueue");
		
		try( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
			TextMessage textMessage = jmsContext.createTextMessage("Message send to check for the delay from producer");
			textMessage.setBooleanProperty("loggedIn", true);
			textMessage.setStringProperty("userToken", "abc123abc123");
			
			producer.send(queue, textMessage);
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			Message body = consumer.receive();
			
			System.out.println(body.getBooleanProperty("loggedIn"));
			System.out.println(body.getStringProperty("userToken"));
			System.out.println(body.getBody(String.class));
		}
	}

}
