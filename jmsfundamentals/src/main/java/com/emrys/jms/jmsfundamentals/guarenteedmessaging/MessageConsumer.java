package com.emrys.jms.jmsfundamentals.guarenteedmessaging;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageConsumer {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//				JMSContext jmsContext = cf.createContext();
//				JMSContext jmsContext = cf.createContext(JMSContext.CLIENT_ACKNOWLEDGE);
//				JMSContext jmsContext = cf.createContext(JMSContext.DUPS_OK_ACKNOWLEDGE);
				JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED);
				){
			
			JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
			TextMessage message = (TextMessage) consumer.receive();
			jmsContext.commit();
			
			System.out.println("Message recieved : "+ message.getText());
			
			TextMessage message1 = (TextMessage) consumer.receive();
			
			System.out.println("Message recieved : "+ message1.getText());
			
//			message.acknowledge();  use only of Client Acknowledge 
			
			
		}
		
	}

}
