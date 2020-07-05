package com.emrys.jms.jmsfundamentals.p2p.listeners;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Patient;

public class EligibilityCheckListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		
		ObjectMessage objectMessage = (ObjectMessage) message;
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();) {
			
			InitialContext context = new InitialContext();
			Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
			MapMessage replyMessage = jmsContext.createMapMessage();
			
			
			Patient patient = (Patient) objectMessage.getObject();
			String insuracneProvider = patient.getInsuracneProvider();
			System.out.println("Insurance Provider: " + insuracneProvider);
			
			if(insuracneProvider.equalsIgnoreCase("atna")) {
				if(patient.getCopay()<=40 && patient.getAmountToBepayed()<=40) {
					replyMessage.setBoolean("eligible", true);
				}else {
					replyMessage.setBoolean("eligible", false);
				}
			}
			
			JMSProducer producer = jmsContext.createProducer();
			producer.send(replyQueue, replyMessage);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
