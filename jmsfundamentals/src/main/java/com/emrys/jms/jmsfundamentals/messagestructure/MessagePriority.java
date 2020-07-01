package com.emrys.jms.jmsfundamentals.messagestructure;

import javax.jms.Connection;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {

	public static void main(String... args) throws NamingException {

		InitialContext initialContext = new InitialContext();
		Queue queue = (Queue) initialContext.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();) {
			JMSProducer producer = jmsContext.createProducer();

			String[] messages = new String[3];
			messages[0]="Message One";
			messages[1] = "Message Two";
			messages[2]="Message Three";
			
			producer.setPriority(3);
			producer.send(queue, messages[0]);
			
			producer.setPriority(1);
			producer.send(queue, messages[1]);
			
			producer.setPriority(9);
			producer.send(queue, messages[2]);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			for(int i=0; i<3; i++) {
				Message receive = consumer.receive();
				try {
					System.out.println( receive.getJMSPriority() + "  " + receive.getBody(String.class) );
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
