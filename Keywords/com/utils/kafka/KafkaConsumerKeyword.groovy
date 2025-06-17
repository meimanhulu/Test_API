package com.utils.kafka

import org.apache.kafka.clients.consumer.*
import org.apache.kafka.common.serialization.StringDeserializer
import com.kms.katalon.core.annotation.Keyword
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import groovy.json.JsonSlurper

class KafkaConsumerKeyword {
	@Keyword
	def consumeMessage() {
		// Mengonfigurasi Kafka Consumer
		Properties props = new Properties()
		props.put('bootstrap.servers', 'localhost:9092')
		props.put('group.id', 'test-group')
		props.put('enable.auto.commit', 'true')
		props.put('auto.commit.interval.ms', '1000')
		props.put('key.deserializer', 'org.apache.kafka.common.serialization.StringDeserializer')
		props.put('value.deserializer', 'org.apache.kafka.common.serialization.StringDeserializer')

		// Membuat Kafka Consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)
		consumer.subscribe(Arrays.asList('inventory-topic'))

		// Mengambil pesan dari Kafka
		println "Menunggu pesan dari Kafka..."
		while (true) {
			def records = consumer.poll(100)
			records.each { record ->
				println "Pesan diterima: ${record.value()}"
				// Parsing JSON dan memverifikasi data
				def jsonResponse = new JsonSlurper().parseText(record.value())
				println "ID: ${jsonResponse.id}, Name: ${jsonResponse.name}"
			}
		}
	}
}
