package com.emrys.jms.jmsfundamentals;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
	
	
	

	public static void main(String[] args) {
		
		InitialContext initial =null;
		Connection connection = null;
		try {
			initial = new InitialContext();
		
			ConnectionFactory cf = (ConnectionFactory)initial.lookup("ConnectionFactory");
			 connection = cf.createConnection();
		Topic topic = (Topic)initial.lookup("topic/myTopic");
		Session session = connection.createSession();
		
		MessageProducer producer = session.createProducer(topic);
		
		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);
		
		TextMessage message = session.createTextMessage("All the power is with in me. I can do anything and everything!");
		
		producer.send(message);
		
		System.out.println("Message Send: " + message.getText());
		
		connection.start();
		
		TextMessage message1 = (TextMessage) consumer1.receive();
		
		TextMessage message2 = (TextMessage) consumer2.receive();
		
		System.out.println("Message received at consumer1: " + message1.getText());
		
		System.out.println("Message received at consumer2: " + message2.getText());
		
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if(initial !=null)
			{
				try {
					initial.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(connection !=null)
			{
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		

	}

}
