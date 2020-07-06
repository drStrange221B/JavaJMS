package com.emrys.jms.jmsfundamentals.pub.sub.services.subscribers.payroll;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Employee;

public class PayrollApp {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/myTopic");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
			JMSConsumer consumer = jmsContext.createConsumer(topic);
			Message receive = consumer.receive();
			
			Employee employee = receive.getBody(Employee.class);
			
			System.out.println(employee.getFirstName());
		}
		

	}

}
