package com.emrys.jms.jmsfundamentals.guarenteedmessaging;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageProducer {

	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
		
		Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//				JMSContext jmsContext = cf.createContext(JMSContext.AUTO_ACKNOWLEDGE);
				JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED);
				
				)
		{
		
			JMSProducer producer = jmsContext.createProducer();
			producer.send(requestQueue, "This for message guarenteed ");
			producer.send(requestQueue, "This for message guarenteed ");
			jmsContext.commit();
			producer.send(requestQueue, "This for message guarenteed ");
			jmsContext.rollback();;
			
		}

	}

}
