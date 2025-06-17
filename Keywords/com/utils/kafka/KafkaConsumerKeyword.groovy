package com.utils.kafka

import org.apache.kafka.clients.consumer.*
import org.apache.kafka.common.serialization.StringDeserializer
import com.kms.katalon.core.annotation.Keyword

public class KafkaConsumerKeyword {

	@Keyword
	def consumeKafkaMessage(String topicName, String bootstrapServers) {
		Properties props = new Properties()
		props.put("bootstrap.servers", bootstrapServers)
		props.put("group.id", "katalon-consumer-group")
		props.put("key.deserializer", StringDeserializer.class.getName())
		props.put("value.deserializer", StringDeserializer.class.getName())
		props.put("auto.offset.reset", "earliest")

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)
		consumer.subscribe(Arrays.asList(topicName))

		println("Waiting for messages from Kafka topic: " + topicName)

		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10))
		for (ConsumerRecord<String, String> record : records) {
			println("Message received: " + record.value())
		}

		consumer.close()
	}
}
