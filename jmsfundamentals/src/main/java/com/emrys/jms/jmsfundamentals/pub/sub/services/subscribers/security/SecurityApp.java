package com.emrys.jms.jmsfundamentals.pub.sub.services.subscribers.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Employee;

public class SecurityApp {

	public static void main(String[] args) throws JMSException, NamingException, InterruptedException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/myTopic");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
//			JMSConsumer consumer = jmsContext.createConsumer(topic);// this is not durable consumer
			
//			creating durable consumer
//			need to set ClientID 
			jmsContext.setClientID("securityApp");
			JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
			consumer.close();
			
			Thread.sleep(10000);
			
			consumer = jmsContext.createDurableConsumer(topic, "subscription1");
			Message receive = consumer.receive();
			
			Employee employee = receive.getBody(Employee.class);
			
			System.out.println(employee.getFirstName());
		}
	}

}
