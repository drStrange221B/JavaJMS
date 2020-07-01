package com.emrys.jms.jmsfundamentals;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class QueueBrowserDemo {
    
    public static void main( String[] args ) {
    	InitialContext initialContext = null;
    	Connection connection=null;
    	
			try {
				initialContext = new InitialContext();
				ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
				connection = cf.createConnection();
				Session session = connection.createSession();
				Queue queue = (Queue) initialContext.lookup("queue/myQueue");
				MessageProducer producer = session.createProducer(queue);
				TextMessage message1 = session.createTextMessage("I am the creator of my destiny 1");
				TextMessage message2 = session.createTextMessage("I am the creator of my destiny 2");
				TextMessage message3 = session.createTextMessage("I am the creator of my destiny 3");
				
				producer.send(message1);
				producer.send(message2);
				producer.send(message3);
				
				QueueBrowser browser = session.createBrowser(queue);
				
				Enumeration enumeration = browser.getEnumeration();
				 
				while(enumeration.hasMoreElements())
				{
					TextMessage message = (TextMessage) enumeration.nextElement();
					
					System.out.println("Browsed Message: " + message.getText());
				}
				MessageConsumer consumer = session.createConsumer(queue);
				connection.start();
				TextMessage messageReceived1 = (TextMessage) consumer.receive();
				TextMessage messageReceived2 = (TextMessage) consumer.receive();
				TextMessage messageReceived3 = (TextMessage) consumer.receive();
				
				System.out.println("Message Received: " + messageReceived1.getText());
				System.out.println("Message Received: " + messageReceived2.getText());
				System.out.println("Message Received: " + messageReceived3.getText());
				
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				if(initialContext !=null)
				{
					try {
						initialContext.close();
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
