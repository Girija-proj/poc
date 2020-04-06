package com.poc.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.poc.helloworld.kafka.KafkaConsumerService;
import com.poc.helloworld.kafka.KafkaProducerService;

@RestController
@RequestMapping("/api/v1")
public class HelloWorldResource {

	@Autowired
	private KafkaProducerService kafkaProducer;
	
	@Autowired
	private KafkaConsumerService kafkaConsumer ;
	
	@GetMapping("/hello")
	public String greeting() {
		kafkaProducer.sendKafkaMsg();
		kafkaConsumer.startKafkaConsumer();
		return "Hello-World4";
	}
	
}
