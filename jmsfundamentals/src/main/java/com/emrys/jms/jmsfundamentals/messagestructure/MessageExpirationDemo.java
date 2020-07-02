package com.emrys.jms.jmsfundamentals.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageExpirationDemo {

	public static void main(String[] args) throws NamingException, InterruptedException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue)context.lookup("queue/myQueue");
		Queue expiryQueue = (Queue)context.lookup("queue/expiryQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext =	cf.createContext()){
			
			JMSProducer producer = jmsContext.createProducer();
			producer.setTimeToLive(2000);
			TextMessage createTextMessage = jmsContext.createTextMessage("Sample message from Producer");
			producer.send(queue, createTextMessage);
			
			Thread.sleep(5000);
			
			JMSConsumer createConsumer = jmsContext.createConsumer(queue);
			Message receiveBody = createConsumer.receive(2000);
			
			System.out.println(receiveBody + "   checking...");
			
			
			//extract expired message from ExpiryQueue
			
			System.out.println("Message from expiry queue: " + jmsContext.createConsumer(expiryQueue).receiveBody(String.class));
			
			
			
		}
		
		

	}

}
