package com.emrys.jms.jmsfundamentals.message.filtering;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Claim;

public class ClaimManagement {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		
		Topic topic = (Topic) context.lookup("topic/myTopic");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
//			JMSConsumer consumer = jmsContext.createConsumer(topic, "hospitalId=2");
//			JMSConsumer consumer = jmsContext.createConsumer(topic, "claimAmount BETWEEN 1000 AND 5000");
//			JMSConsumer consumer = jmsContext.createConsumer(topic,"doctorName= 'Rojaya Maharjan'");
//			JMSConsumer consumer = jmsContext.createConsumer(topic,"doctorName like '%j%'");
//			JMSConsumer consumer = jmsContext.createConsumer(topic,"doctorType IN ('gyno','Nero')");
			JMSConsumer consumer = jmsContext.createConsumer(topic,"doctorType IN ('gyno','Nero') or "
					+ "JMSPriority BETWEEN 3 AND 6");
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
//			objectMessage.setIntProperty("hospitalId", 1);
//			objectMessage.setDoubleProperty("claimAmount", 1000);
//			objectMessage.setStringProperty("doctorName", "Rojaya Maharjan");
//			objectMessage.setStringProperty("doctorType", "Nero");
			objectMessage.setStringProperty("doctorType", "psyco");
			Claim claim = new Claim(1,"Rojaya Maharjon", "Nero", "Atna",1000.00);
			objectMessage.setObject(claim);
			
			producer.send(topic, objectMessage);
			
			Claim receiveBody = consumer.receiveBody(Claim.class);
			
			System.out.println(receiveBody);
		}
	}

}
