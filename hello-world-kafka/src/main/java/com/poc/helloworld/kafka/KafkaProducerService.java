package com.poc.helloworld.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService  { 

	private static final Logger logger = Logger.getLogger(KafkaProducerService .class);
	
	@Value("${kafka.bootstrap.servers}")
	private String kafkaBootstrapServers;
	
	@Value("${kafka.topic.test}")
	private String testTopic;

	
	private Properties getProducerProp() {
		Properties producerProperties = new Properties();
		producerProperties.put("bootstrap.servers", kafkaBootstrapServers);
		producerProperties.put("acks", "all");
		producerProperties.put("retries", 0);
		producerProperties.put("batch.size", 16384);
		producerProperties.put("linger.ms", 1);
		producerProperties.put("buffer.memory", 33554432);
		producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return producerProperties;
	}
	
	
	public org.apache.kafka.clients.producer.KafkaProducer< String, String> getKafkaProd(){
		org.apache.kafka.clients.producer.KafkaProducer<String,String> kafkaProd = new org.apache.kafka.clients.producer.KafkaProducer<>(getProducerProp());
		return  kafkaProd;
	}
	
	
	/**
	 * Function to send a message to Kafka
	 * @param payload The String message that we wish to send to the Kafka topic
	 * @param producer The KafkaProducer object
	 * @param topic The topic to which we want to send the message
	 */
	private static void sendKafkaMessage(String payload,org.apache.kafka.clients.producer.KafkaProducer<String, String> producer,   String topic)
	{
	    logger.info("Sending Kafka message: " + payload);
	    producer.send(new ProducerRecord<>(topic, payload));
	}

	
	public void sendKafkaMsg() {
		
		/*
		Creating a loop which iterates 10 times, from 0 to 9, and sending a
		simple message to Kafka.
		 */
		for (int index = 0; index < 10; index++) { 
		    sendKafkaMessage("The index is now: " + index, getKafkaProd(), testTopic);
		}

	}
	
}
