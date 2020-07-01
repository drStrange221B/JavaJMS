package com.emrys.jms.jmsfundamentals.messagestructure;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
//		Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext();)
		{
			JMSProducer producer = jmsContext.createProducer();
//			producer.send(requestQueue, "Message has been send from producer!");
			
			Map<String, TextMessage> requestMessages = new HashMap<String, TextMessage>(); 
			
			TextMessage producerMessage = jmsContext.createTextMessage("Message has been send from producer!");
			TemporaryQueue replyQueue = jmsContext.createTemporaryQueue();
			producerMessage.setJMSReplyTo(replyQueue);
			
			producer.send(requestQueue, producerMessage);
			
			requestMessages.put(producerMessage.getJMSMessageID(), producerMessage);
			System.out.println("MessageId: " + producerMessage.getJMSMessageID());
			
			
			JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
			
//			String messageReceived = consumer.receiveBody(String.class);
			TextMessage messageReceived = (TextMessage)consumer.receive();
			System.out.println(messageReceived.getText());
			
			JMSProducer replyProducer = jmsContext.createProducer();
//			replyProducer.send(replyQueue, "you are awesome");
			
			TextMessage replyMessage = jmsContext.createTextMessage("you are awesome !");
			replyMessage.setJMSCorrelationID(messageReceived.getJMSMessageID());
			replyProducer.send(messageReceived.getJMSReplyTo(), replyMessage);
			
			JMSConsumer replyConsumer = jmsContext.createConsumer(messageReceived.getJMSReplyTo());
			TextMessage receiveBody = (TextMessage)replyConsumer.receive();
			System.out.println(receiveBody.getText() + "  " + receiveBody.getJMSCorrelationID());
			System.out.println((requestMessages.get(receiveBody.getJMSCorrelationID())).getText());
			
			
		}

	}

}
