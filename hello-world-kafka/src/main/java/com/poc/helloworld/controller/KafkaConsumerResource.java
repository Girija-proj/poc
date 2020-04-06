package com.poc.helloworld.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.poc.helloworld.kafka.KafkaConsumerService;

public class KafkaConsumerResource {

	private static final Logger logger = Logger.getLogger(KafkaConsumerResource.class);

	@Autowired
	private KafkaConsumerService kafkaConsumerService;

	public void startConsumer() {
		Thread kafkaConsumerThread = new Thread(() -> {
			logger.info("Starting Kafka consumer thread.");
			kafkaConsumerService.startConsumer();
		});
		kafkaConsumerThread.start();
	}

}
