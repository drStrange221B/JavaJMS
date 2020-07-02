package com.emrys.jms.jmsfundamentals.messagestructure;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.emrys.jms.jmsfundamentals.models.Patient;

public class MessageTypesDemo {
	
	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue)context.lookup("queue/myQueue");
		
		try( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
			BytesMessage bytesMessage = jmsContext.createBytesMessage();
			bytesMessage.writeUTF("Check of Byte Message");
			bytesMessage.writeLong(12345l);
//			producer.send(queue, bytesMessage);
			
//			JMSConsumer consumer = jmsContext.createConsumer(queue);
//			BytesMessage body = (BytesMessage) consumer.receive();
//			System.out.println(body.readUTF());
//			System.out.println(body.readLong());
//			
			
			StreamMessage streamMessage = jmsContext.createStreamMessage();
			streamMessage.writeBoolean(true);
			streamMessage.writeFloat(2.5f);
			
//			producer.send(queue, streamMessage);
			
//			JMSConsumer consumerStream = jmsContext.createConsumer(queue);
//			StreamMessage streamMessageReceived= (StreamMessage) consumerStream.receive();
//			
//			System.out.println("MessageReceived: " + streamMessageReceived.readBoolean());
//			System.out.println("MessageReceived: " + streamMessageReceived.readFloat());
			
			
			MapMessage mapMessage = jmsContext.createMapMessage();
			
			mapMessage.setBoolean("isCreditAvailable", true);
			
//			producer.send(queue, mapMessage);
//			
//			JMSConsumer consumerMap = jmsContext.createConsumer(queue);
//			MapMessage recievedMapMessage = (MapMessage) consumerMap.receive();
//			
//			System.out.println("Recieved Message: " + recievedMapMessage.getBoolean("isCreditAvailable"));
			
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			Patient patient = new Patient(123, "John Doe");
			objectMessage.setObject(patient);
			
//			producer.send(queue, objectMessage);
//			
//			JMSConsumer consumerObject = jmsContext.createConsumer(queue);
//			ObjectMessage recievedObject = (ObjectMessage) consumerObject.receive();
//			
//			Patient object = (Patient) recievedObject.getObject();
//			System.out.println("Message object recievied is : " + object.toString());
			
			
			producer.send(queue, patient);
			
			JMSConsumer consumerObject = jmsContext.createConsumer(queue);
			Patient receiveBody = consumerObject.receiveBody(Patient.class);
			System.out.println("Message object recievied is : " + receiveBody.toString());
			
			
			
			
			
		}
	}

}
