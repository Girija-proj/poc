package com.poc.helloworld.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

	private static final Logger logger = Logger.getLogger(KafkaConsumerService.class);

	@Value("${zookeeper.groupId}")
	private String zookeeperGroupId;

	@Value("${kafka.bootstrap.servers}")
	private String kafkaBootstrapServers;

	@Value("${kafka.topic.test}")
	private String testTopic;
	
	public void startKafkaConsumer() {
		Thread kafkaConsumerThread = new Thread(() -> {
			logger.info("Starting Kafka consumer thread.");
			startConsumer();
		});
		kafkaConsumerThread.start();
	}

	private Properties getConsumerProp() {
		Properties consumerProperties = new Properties();
		consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
		consumerProperties.put("group.id", zookeeperGroupId);
		consumerProperties.put("zookeeper.session.timeout.ms", "6000");
		consumerProperties.put("zookeeper.sync.time.ms", "2000");
		consumerProperties.put("auto.commit.enable", "false");
		consumerProperties.put("auto.commit.interval.ms", "1000");
		consumerProperties.put("consumer.timeout.ms", "-1");
		consumerProperties.put("max.poll.records", "1");
		consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		return consumerProperties;
	}

	private KafkaConsumer<String, String> initialiseKafkaConsumer() {

		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(getConsumerProp());
		kafkaConsumer.subscribe(Arrays.asList(testTopic));
		return kafkaConsumer;
	}

	public void startConsumer() {

		KafkaConsumer<String, String> kafkaConsumer = initialiseKafkaConsumer();

		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

			for (ConsumerRecord<String, String> record : records) {
				String msg = record.value();
				logger.info("Msg consumed :" + msg);

				Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

				commitMessage.put(new TopicPartition(record.topic(), record.partition()),
						new OffsetAndMetadata(record.offset() + 1));

				kafkaConsumer.commitSync(commitMessage);

				logger.info("Offset committed to Kafka.");

			}

		}

	}

}
