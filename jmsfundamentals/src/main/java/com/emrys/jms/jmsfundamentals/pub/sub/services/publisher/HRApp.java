package com.emrys.jms.jmsfundamentals.pub.sub.services.publisher;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Employee;

public class HRApp {

	public static void main(String[] args) throws NamingException {

		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/myTopic");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
			Employee employee = new Employee(123, "Rojaya","Maharjan","Software Architect",
					"rojaya.mh3@gmail.com", "123 456 7890");
			
//			JMSProducer send = jmsContext.createProducer().send(topic, employee);
			
			
			//testing sharable subscription
			
			for(int i=1; i<=10; i++)
			{
				jmsContext.createProducer().send(topic, employee);
			}
			
			System.out.println("Message Sent");
			
		}
	}

}
