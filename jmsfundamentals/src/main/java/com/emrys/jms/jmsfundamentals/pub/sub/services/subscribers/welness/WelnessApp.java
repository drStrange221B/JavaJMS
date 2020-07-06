package com.emrys.jms.jmsfundamentals.pub.sub.services.subscribers.welness;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Employee;

public class WelnessApp {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/myTopic");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
//			JMSConsumer consumer = jmsContext.createConsumer(topic);
			
			
			JMSConsumer consumer = jmsContext.createSharedConsumer(topic, "sharedConsumer");
			JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
			

			for(int i=1; i<=10; i+=2) {
				Message receive = consumer.receive();
				Employee employee = receive.getBody(Employee.class);
				System.out.println("consumer " + employee.getFirstName());
				
				Message receive1 = consumer1.receive();
				Employee employee1 = receive1.getBody(Employee.class);
				System.out.println("consumer 1 " + employee1.getFirstName());
			}
//			Message receive = consumer.receive();
//			Employee employee = receive.getBody(Employee.class);
//			System.out.println(employee.getFirstName());
		}

	}

}
