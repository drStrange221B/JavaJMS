package com.emrys.jms.jmsfundamentals.p2p.clinic;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.p2p.listeners.EligibilityCheckListener;

public class ClinicalApp {

	public static void main(String[] args) throws NamingException, InterruptedException {
		System.out.println(" app running ............ !");
		
		InitialContext context = new InitialContext();
		Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
			consumer.setMessageListener(new EligibilityCheckListener());
			
			Thread.sleep(10000);
			
		} 
	}

}
