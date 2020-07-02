package com.emrys.jms.jmsfundamentals.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageDelayDemo {

	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue)context.lookup("queue/myQueue");
		
		try( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
			producer.setDeliveryDelay(3000);
			producer.send(queue, "Message send to check for the delay from producer");
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			String body = consumer.receiveBody(String.class);
			
			
			System.out.println("Message received after delay is :" + body );
		}
		
		
	}

}
