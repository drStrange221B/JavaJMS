package com.emrys.jms.jmsfundamentals.p2p.clientapp;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Patient;

public class ClientApp {
	
	public static void main(String... args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		
		Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext =cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
			
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			
			Patient patient = new Patient(123, "John Doe", "Atna", 20.00, 20.00);
			
			objectMessage.setObject(patient);
			
			producer.send(requestQueue, objectMessage);
			

			JMSConsumer createConsumer = jmsContext.createConsumer(replyQueue);
			MapMessage mapMessage =  (MapMessage) createConsumer.receive(30000);
			
			System.out.println("Eligiblity is : " + mapMessage.getBoolean("eligible"));
		}
	}

}
