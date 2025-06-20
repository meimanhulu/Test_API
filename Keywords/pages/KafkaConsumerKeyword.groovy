package pages

import org.apache.kafka.clients.consumer.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.ByteArrayDeserializer  // IMPOR DITAMBAHKAN DI SINI
import com.kms.katalon.core.annotation.Keyword
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import groovy.json.JsonSlurper
import java.util.Arrays
import java.util.concurrent.TimeUnit

class KafkaConsumerKeyword {
    @Keyword
    def consumeMessages() {
        // Set properties for Kafka Consumer
        Properties properties = new Properties()
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092") // Kafka Broker address
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group") // Consumer group ID
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()) // String Deserializer for key
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName()) // Fixed: ByteArrayDeserializer for value

        // Create a Kafka Consumer
        Consumer<String, byte[]> consumer = new KafkaConsumer<>(properties)

        try {
            // Subscribe to the Kafka topic
            consumer.subscribe(Arrays.asList("inventory-topic")) // Topic name

            // Track time for performance testing
            long startTime = System.nanoTime()
            int messageCount = 0  // Counter for the number of consumed messages
            long timeoutMillis = 5000  // Timeout for consumer to wait for messages (5 seconds)
            long startPollingTime = System.nanoTime()

            // Poll for new messages and consume
            while (true) {
                // Check if the consumer has been polling for too long (timeout)
                if (System.nanoTime() - startTime > TimeUnit.MILLISECONDS.toNanos(timeoutMillis)) {
                    println("Timeout reached. Stopping consumer.")
                    break  // Exit the loop after timeout
                }

                def records = consumer.poll(1000)  // Polling every 1 second

                // Check if any records are found
                if (!records.isEmpty()) {
                    long pollTime = System.nanoTime() - startPollingTime // Time spent in poll
                    println("Polling took: ${TimeUnit.NANOSECONDS.toMillis(pollTime)} ms")

                    records.forEach { record ->
                        println("Consumed record with key " + record.key() + " and value " + new String(record.value()))
                        
                        // Parsing the JSON data (assuming value is in JSON format)
                        def jsonData = new JsonSlurper().parseText(new String(record.value()))
                        println("Parsed JSON: ${jsonData}")

                        messageCount++
                    }
                }

                // Stop after a certain number of messages have been consumed
                if (messageCount >= 10) {
                    println("Consumed 10 messages. Stopping consumer.")
                    break  // Exit after consuming 10 messages
                }
            }

            // Output the total time it took for consuming messages
            long totalTime = System.nanoTime() - startTime
            println("Total time taken for consuming messages: ${TimeUnit.NANOSECONDS.toMillis(totalTime)} ms")
            println("Total messages consumed: ${messageCount}")

        } catch (Exception e) {
            // Handle connection issues or other errors
            println("Error: Unable to connect to Kafka Broker or consume messages. ${e.message}")
        } finally {
            // Close the consumer connection when done
            consumer.close()
            println("Kafka Consumer connection closed.")
        }
    }
}
